#!/usr/bin/env bash
rm -rf cacerts.keystore

keytool -import -alias iedcs -keystore cacerts.keystore -file CA/iedcs.rafaelferreira.pt.crt
