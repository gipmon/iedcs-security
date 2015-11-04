#!/usr/bin/env bash

apt-get update
apt-get install -y python python-pip python-dev apache2 libapache2-mod-wsgi
cd /var/www/
pip install -r requirements.txt
cp /vagrant/www.bkiedcs.tk.conf /etc/apache2/sites-available/www.bkiedcs.tk.conf
a2ensite www.bkiedcs.tk.conf
sudo a2enmod ssl
a2dissite 000-default.conf
sudo rm /etc/apache2/sites-available/000-default.conf

# https
sudo cp /vagrant/ssl/www_bkiedcs_tk.key /etc/ssl/certs/www_bkiedcs_tk.key
sudo cp /vagrant/ssl/www_bkiedcs_tk.ca-bundle /etc/ssl/certs/www_bkiedcs_tk.ca-bundle
sudo cp /vagrant/ssl/www_bkiedcs_tk.crt /etc/ssl/certs/www_bkiedcs_tk.crt

sudo cp /vagrant/default-ssl.conf /etc/apache2/sites-available/default-ssl.conf
a2ensite default-ssl.conf

# mod_rewrite
sudo a2enmod rewrite


sudo service apache2 restart
