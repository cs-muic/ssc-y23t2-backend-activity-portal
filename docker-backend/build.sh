#!/bin/bash

docker context use default
docker build -t jorntumrongwit/activity-portal-backend .
docker push jorntumrongwit/activity-portal-backend
docker context use remote-activity-portal
docker pull jorntumrongwit/activity-portal-backend
docker rm -f backend
docker-compose up -d