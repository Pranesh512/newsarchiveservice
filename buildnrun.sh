#!/usr/bin/env bash
cd ./newsarchivescrapper
./mvnw clean package -DskipTests=true
cd ..
docker-compose build
docker-compose up
