#!/usr/bin/env bash
rm -rf cacerts.keystore
keytool -import -alias alias -keystore cacerts.keystore -file ssl/iedcs_rafaelferreira_pt.crt
