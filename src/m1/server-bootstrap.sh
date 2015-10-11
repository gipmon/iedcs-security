#!/usr/bin/env bash

apt-get update
apt-get install -y python python-pip python-dev
cd /vagrant/iedcs-server
pip install -r requirements.txt
