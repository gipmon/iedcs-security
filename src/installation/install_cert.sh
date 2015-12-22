#!/usr/bin/env bash
rm -rf cacerts.keystore

keytool -importcert -alias iedcs -keystore cacerts.keystore -file CA/iedcs.rafaelferreira.pt.crt
