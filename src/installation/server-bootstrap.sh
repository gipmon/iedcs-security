#!/usr/bin/env bash

apt-get update
apt-get install -y docker.io apache2 libapache2-mod-proxy-html libxml2-dev encfs

mkdir -p /opt/media-webstore/
mv /vagrant/Docker/python/site/media/ /vagrant/Docker/python/site/media-tmp/
mkdir -p /vagrant/Docker/python/site/media/

password="p4g1rr"

encfs -S -o nonempty /opt/media-webstore/ /vagrant/Docker/python/site/media/ << EOF
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

cp -r /vagrant/Docker/python/site/media-tmp/ /vagrant/Docker/python/site/media/
sleep 2
rm -rf /vagrant/Docker/python/site/media-tmp/

cd /vagrant/Docker/python/site

# database
if [ ! -f db.sqlite3 ]; then
  apt-get install -y python python-pip python-dev
  pip install -r requirements.txt
  python manage.py migrate
  python manage.py insert_books
  python manage.py restriction add restriction 'restriction_country' 'restriction_country' 'You are restricted by country!'
  python manage.py restriction add restriction 'restriction_hour' 'restriction_hour' 'You are restricted by hour!'
  BOOK_IDENTIFIER=$(python manage.py restriction list books | grep "Household organization" | awk '{print $1;}')
  python manage.py restriction add restrict_book $(echo $BOOK_IDENTIFIER) 'restriction_country'
  BOOK_IDENTIFIER=$(python manage.py restriction list books | grep "IBM 1401 Programming Systems" | awk '{print $1;}')
  python manage.py restriction add restrict_book $(echo $BOOK_IDENTIFIER) 'restriction_hour'
fi

cd /vagrant/Docker/python
docker build -t ubuntu/iedcs .
docker run -d -it -p 8002:80 --name apache -t ubuntu/iedcs
docker start apache
docker exec apache service apache2 start

#IPTABLES
iptables -I FORWARD -p tcp --dport 8002 -j DROP
iptables -I DOCKER -p tcp --dport 8002 -j DROP
iptables -I INPUT -p tcp --dport 8002 -j DROP

echo "192.168.33.10   iedcs.rafaelferreira.pt" > /etc/hosts

echo "ServerName localhost" | sudo tee /etc/apache2/conf-available/servername.conf
sudo cp /vagrant/Apache/ports.conf /etc/apache2/ports.conf
sudo a2enconf servername
sudo service apache2 restart

cp /vagrant/Apache/iedcs.rafaelferreira.pt.conf /etc/apache2/sites-available/iedcs.rafaelferreira.pt.conf
a2ensite iedcs.rafaelferreira.pt.conf
sudo a2enmod ssl
a2dissite 000-default.conf
sudo rm /etc/apache2/sites-available/000-default.conf

# https
sudo cp /vagrant/SSL/comodo/iedcs.rafaelferreira.pt.key /etc/ssl/certs/iedcs.rafaelferreira.pt.key
sudo cp /vagrant/SSL/comodo/iedcs_rafaelferreira_pt.ca-bundle /etc/ssl/certs/iedcs_rafaelferreira_pt.ca-bundle
sudo cp /vagrant/SSL/comodo/iedcs_rafaelferreira_pt.crt /etc/ssl/certs/iedcs_rafaelferreira_pt.crt

sudo cp /vagrant/SSL/CA/192.168.33.10.pem /etc/ssl/private/192.168.33.10.pem
sudo cp /vagrant/SSL/CA/iedcs.rafaelferreira.pt.crt /etc/ssl/private/iedcs.rafaelferreira.pt.crt
sudo cp /vagrant/SSL/CA/IEDCS-CA.pem /etc/ssl/certs/IEDCS-CA.pem

sudo cp /vagrant/Apache/default-ssl.conf /etc/apache2/sites-available/default-ssl.conf
a2ensite default-ssl.conf

# mod_rewrite
sudo a2enmod proxy
sudo a2enmod proxy_http
sudo a2enmod proxy_ajp
sudo a2enmod rewrite
sudo a2enmod deflate
sudo a2enmod headers
sudo a2enmod proxy_balancer
sudo a2enmod proxy_connect
sudo a2enmod proxy_html
sudo a2enmod xml2enc

sudo service apache2 restart

sh /vagrant/reload-apache.sh
