#!/bin/bash

docker run -p 127.0.0.1:13306:3306 -p 172.17.0.1:3306:3306 --detach --name ssc-webapp-mariaDB -e MARIADB_ROOT_PASSWORD=GoodPass --restart=always mariadb:10