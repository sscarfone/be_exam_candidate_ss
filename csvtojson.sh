#!/bin/sh

SOURCE_DIR=${SOURCE_DIR="/tmp/source"}
SOURCE_PROPERTY="-Dcom.example.csvtojson.sourceDir=${SOURCE_DIR}"

DEST_DIR=${DEST_DIR="/tmp/dest"}
DEST_PROPERTY="-Dcom.example.csvtojson.destDir=${DEST_DIR}"

ERROR_DIR=${ERROR_DIR="/tmp/error"}
ERROR_PROPERTY="-Dcom.example.csvtojson.errorDir=${ERROR_DIR}"
java ${SOURCE_PROPERTY} ${DEST_PROPERTY} ${ERROR_PROPERTY} -jar target/csvtojson.jar
