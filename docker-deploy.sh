#!/bin/sh
NAME="${1}"
version=$(./utils/get-site-version.sh)

dockerlogin -u="ukhomeofficedigital+egar_robot" -p=${DOCKER_PASSWORD} quay.io
docker tag $NAME:$version quay.io/ukhomeofficedigital/$NAME:$version
docker push quay.io/ukhomeofficedigital/$NAME:$version

#docker tag $NAME:$version pipe.egarteam.co.uk/$NAME:latest
#docker push pipe.egarteam.co.uk/$NAME:latest
