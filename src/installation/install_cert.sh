#!/usr/bin/env bash
rm -rf cacerts.keystore
keytool -import -alias alias -keystore cacerts.keystore -file CA/iedcs.rafaelferreira.pt.crt
