FROM ubuntu

RUN apt-get update && apt-get install -y python python-pip python-dev apache2 libapache2-mod-wsgi encfs

RUN mkdir -p /var/www

ADD site /var/www

WORKDIR /var/www

RUN pip install -r requirements.txt

# DATABASE
RUN if [ ! -f db.sqlite3 ]; then python manage.py migrate; fi
RUN if [ ! -f db.sqlite3 ]; then python manage.py restriction add restriction 'restriction_country' 'restriction_country' 'You are restricted by country!'; fi
RUN if [ ! -f db.sqlite3 ]; then python manage.py restriction add restriction 'restriction_hour' 'restriction_hour' 'You are restricted by hour!'; fi
RUN if [ ! -f db.sqlite3 ]; then BOOK_IDENTIFIER=$(python manage.py restriction list books | grep "Household organization" | awk '{print $1;}'); fi
RUN if [ ! -f db.sqlite3 ]; then python manage.py restriction add restrict_book $(echo $BOOK_IDENTIFIER) 'restriction_country'; fi
RUN if [ ! -f db.sqlite3 ]; then BOOK_IDENTIFIER=$(python manage.py restriction list books | grep "IBM 1401 Programming Systems" | awk '{print $1;}'); fi
RUN if [ ! -f db.sqlite3 ]; then python manage.py restriction add restrict_book $(echo $BOOK_IDENTIFIER) 'restriction_hour'; fi

RUN echo "ServerName localhost" | sudo tee /etc/apache2/conf-available/servername.conf
ADD ./ports.conf /etc/apache2/ports.conf
RUN sudo a2enconf servername

ADD ./iedcs.rafaelferreira.pt.conf /etc/apache2/sites-available/iedcs.rafaelferreira.pt.conf

RUN a2ensite iedcs.rafaelferreira.pt.conf
RUN sudo a2enmod ssl
RUN a2dissite 000-default.conf
RUN sudo rm /etc/apache2/sites-available/000-default.conf

# ENCFS
ADD ./encfs.sh /opt/encfs.sh
# RUN sh /opt/encfs.sh

# HTTPS Keys
ADD ./ssl/iedcs.rafaelferreira.pt.key /etc/ssl/certs/iedcs.rafaelferreira.pt.key
ADD ./ssl/iedcs_rafaelferreira_pt.ca-bundle /etc/ssl/certs/iedcs_rafaelferreira_pt.ca-bundle
ADD ./ssl/iedcs_rafaelferreira_pt.crt /etc/ssl/certs/iedcs_rafaelferreira_pt.crt

ADD ./CA/192.168.33.10.pem /etc/ssl/private/192.168.33.10.pem
ADD ./CA/iedcs.rafaelferreira.pt.crt /etc/ssl/private/iedcs.rafaelferreira.pt.crt
ADD ./CA/IEDCS-CA.pem /etc/ssl/certs/IEDCS-CA.pem

ADD ./default-ssl.conf /etc/apache2/sites-available/default-ssl.conf

RUN a2ensite default-ssl.conf
RUN sudo a2enmod rewrite
