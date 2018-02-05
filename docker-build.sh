#!/bin/sh
NAME="${1}"
# version=$(./utils/get-site-version.sh)
version=`cat ./pom.xml | grep version | tail -1 | sed 's/[^0-9,.]*//g'`
docker build -t $NAME:$version .
