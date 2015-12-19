#!/usr/bin/env bash

apt-get update
apt-get install -y docker.io

cd /vagrant
docker build -t ubuntu/iedcs .
docker run -d -it --cap-add SYS_ADMIN -p 80:80 -p 443:443 -p  8080:8080 --name apache -t ubuntu/iedcs
docker start apache
docker exec apache sh /opt/encfs.sh
docker exec apache service apache2 start

# to remove one container:
# docker ps -a
# docker rm -f $container_id
