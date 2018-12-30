#!/usr/bin/env bash
cd ./newsarchivescrapper
echo "Building the application"
./mvnw clean package -DskipTests=true
cd ..

vmc=$(sysctl vm.max_map_count -n)
echo "vm.max_map_count is [$vmc]"
if [ $vmc -lt 262144 ]; then
	echo "Setting vm.max_map_count=262144 for purpose of Elasticsearch container"
	sudo sysctl -w vm.max_map_count=262144
fi

echo "Building docker containers"
docker-compose build
echo "Run docker containers"
docker-compose up

