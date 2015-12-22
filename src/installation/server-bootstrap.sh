#!/usr/bin/env bash

apt-get update
apt-get install -y python python-pip python-dev apache2 libapache2-mod-wsgi encfs
cd /var/www/
pip install -r requirements.txt

mkdir -p /opt/media-webstore/
mv /var/www/media/ /var/www/media-tmp/
mkdir -p /var/www/media/

password = "p4g1rr"

encfs -S -o nonempty /opt/media-webstore/ /var/www/media/ << EOF
x
1
256
1024
1
no
yes
no
8
yes
$password
$password
EOF

cp -r /var/www/media-tmp/* /var/www/media/
sleep 2
rm -rf /var/www/media-tmp/

# database
if [ ! -f db.sqlite3 ]; then
  python manage.py migrate
  python manage.py insert_books
  python manage.py restriction add restriction 'restriction_country' 'restriction_country' 'You are restricted by country!'
  python manage.py restriction add restriction 'restriction_hour' 'restriction_hour' 'You are restricted by hour!'
  BOOK_IDENTIFIER=$(python manage.py restriction list books | grep "Household organization" | awk '{print $1;}')
  python manage.py restriction add restrict_book $(echo $BOOK_IDENTIFIER) 'restriction_country'
  BOOK_IDENTIFIER=$(python manage.py restriction list books | grep "IBM 1401 Programming Systems" | awk '{print $1;}')
  python manage.py restriction add restrict_book $(echo $BOOK_IDENTIFIER) 'restriction_hour'
fi


echo "ServerName localhost" | sudo tee /etc/apache2/conf-available/servername.conf
sudo cp /vagrant/ports.conf /etc/apache2/ports.conf
sudo a2enconf servername
sudo service apache2 restart

cp /vagrant/iedcs.rafaelferreira.pt.conf /etc/apache2/sites-available/iedcs.rafaelferreira.pt.conf
a2ensite iedcs.rafaelferreira.pt.conf
sudo a2enmod ssl
a2dissite 000-default.conf
sudo rm /etc/apache2/sites-available/000-default.conf

# https
sudo cp /vagrant/ssl/iedcs.rafaelferreira.pt.key /etc/ssl/certs/iedcs.rafaelferreira.pt.key
sudo cp /vagrant/ssl/iedcs_rafaelferreira_pt.ca-bundle /etc/ssl/certs/iedcs_rafaelferreira_pt.ca-bundle
sudo cp /vagrant/ssl/iedcs_rafaelferreira_pt.crt /etc/ssl/certs/iedcs_rafaelferreira_pt.crt

sudo cp /vagrant/CA/192.168.33.10.pem /etc/ssl/private/192.168.33.10.pem
sudo cp /vagrant/CA/iedcs.rafaelferreira.pt.crt /etc/ssl/private/iedcs.rafaelferreira.pt.crt
sudo cp /vagrant/CA/IEDCS-CA.pem /etc/ssl/certs/IEDCS-CA.pem
sudo mkdir /etc/ssl/certs/PTEIDCC
sudo cp /vagrant/CA/CC/PTEID_CA_Bundle.crt /etc/ssl/certs/PTEIDCC

sudo cp /vagrant/default-ssl.conf /etc/apache2/sites-available/default-ssl.conf
a2ensite default-ssl.conf

# mod_rewrite
sudo a2enmod rewrite


sudo service apache2 restart
