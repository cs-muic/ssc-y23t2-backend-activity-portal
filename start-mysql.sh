#!/bin/bash

docker run -p 127.0.0.1:13306:3306 --detach --name ssc-webapp-mariaDB -e MARIADB_ROOT_PASSWORD=GoodPass --restart=always mariadb:10