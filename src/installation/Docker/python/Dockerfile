FROM ubuntu

RUN apt-get update && apt-get install -y python python-pip python-dev apache2 libapache2-mod-wsgi

RUN mkdir -p /var/www

ADD site/requirements.txt /var/www/requirements.txt

WORKDIR /var/www

RUN pip install -r requirements.txt

RUN echo "ServerName localhost" | sudo tee /etc/apache2/conf-available/servername.conf
RUN sudo a2enconf servername

ADD ./Apache/docker.conf /etc/apache2/sites-available/docker.conf

RUN a2dismod deflate
RUN a2ensite docker.conf

RUN a2dissite 000-default.conf
RUN sudo rm /etc/apache2/sites-available/000-default.conf

RUN sudo a2enmod rewrite

ADD site /var/www
