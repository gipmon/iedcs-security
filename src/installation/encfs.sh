#!/usr/bin/env bash
# encfs
mkdir -p /opt/media-webstore/
mv /var/www/media/ /var/www/media-tmp/
mkdir -p /var/www/media/

usermod -aG fuse root

sleep 2

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
p4g1rr
p4g1rr
EOF

cp -r /var/www/media-tmp/* /var/www/media/
sleep 2
rm -rf /var/www/media-tmp/
