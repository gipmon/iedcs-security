#!/usr/bin/env bash
javac PlayerKeysRelease.java
java PlayerKeysRelease

mkdir ../Player/dist/release

keytool -genkey -keystore ../Player/JarSignature.KeyStore -alias player
jarsigner -keystore ../Player/JarSignature.KeyStore -signedjar ../Player/dist/release/Player.signed.jar ../Player/dist/Player.jar player
cp ../Player/JarSignature.KeyStore ../Player/dist/release/JarSignature.KeyStore
mv private.key ../Player/dist/private.key
mv public.key ../Player/dist/release/public.key
