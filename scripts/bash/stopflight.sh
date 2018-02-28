#!/bin/sh
kubectl delete -f /home/centos/egar-flight-api/scripts/kube/aircraft-api-deployment.yaml
kubectl delete -f /home/centos/egar-flight-api/scripts/kube/aircraft-api-service.yaml
