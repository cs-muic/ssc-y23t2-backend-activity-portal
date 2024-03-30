#!/bin/bash

docker context use default
docker build -t jorntumrongwit/activity-portal-backend .
docker push jorntumrongwit/activity-portal-backend
docker context use remote-activity-portal
docker rm -f backend
docker image rm jorntumrongwit/activity-portal-backend
docker pull jorntumrongwit/activity-portal-backend
docker-compose up --force-recreate -d