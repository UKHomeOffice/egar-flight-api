#!/bin/sh
echo Starting Aircraft-API version: $AIRCRAFT_API_VER
rm -rf /home/centos/egar-flight-api/scripts/kube/aircraft-api-deployment.yaml; envsubst < "/home/centos/egar-flight-api/scripts/kube/aircraft-api-deployment-template.yaml" > "/home/centos/egar-flight-api/scripts/kube/aircraft-api-deployment.yaml";
kubectl create -f /home/centos/egar-flight-api/scripts/kube/aircraft-api-deployment.yaml
kubectl create -f /home/centos/egar-flight-api/scripts/kube/aircraft-api-service.yaml

