# rc.local
#
# This script is executed at the end of each multiuser runlevel.
# Make sure that the script will "exit 0" on success or any other
# value on error.
#
# In order to enable or disable this script just change the execution
# bits.
#
# By default this script does nothing.

cd /vagrant/Docker/python
sudo docker build -t ubuntu/iedcs .
sudo docker rm -f apache
sudo docker run -d -it -p 8002:80 --name apache -t ubuntu/iedcs
sudo docker start apache
sudo docker exec apache service apache2 start

exit 0
