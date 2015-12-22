#!/usr/bin/env bash
cd /vagrant/Docker/python
sudo docker build -t ubuntu/iedcs .
sudo docker rm -f apache
sudo docker run -d -it -p 8002:80 --name apache -t ubuntu/iedcs
sudo docker start apache
sudo docker exec apache service apache2 start
