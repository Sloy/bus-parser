#!/bin/bash
set -e # Stop script on error
set -x # Print the commands below

GIT_MAIN_BRANCH="master"
GIT_REMOTE="origin"
OUTPUT_DIR="sevibus-data/"
JAR_FILE="busparser-core/build/libs/busparser-core-all-1.0.jar"
DATE=$(date +%d-%m-%Y" "%H:%M:%S);
DATA_VERSION=$(date +%Y%m%d%H%M);
PUBLIC_FILES_URL="https://gitlab.com/Sloy/sevibus-data/raw/master/sql/"

# Update the output data
git submodule update --init --recursive

# Build the code
./gradlew fatJar

# Run the jar's main task to generate outputs
java -jar $JAR_FILE $OUTPUT_DIR


cd $OUTPUT_DIR
# Create info json

echo "{
    \"data_version\": $DATA_VERSION,
    \"app_min_build_version\": 94,
    \"data_location\": {
        \"paradas\": \"${PUBLIC_FILES_URL}paradas.sql\",
        \"lineas\": \"${PUBLIC_FILES_URL}lineas.sql\",
        \"relaciones\": \"${PUBLIC_FILES_URL}relaciones.sql\",
        \"secciones\": \"${PUBLIC_FILES_URL}secciones.sql\",
        \"tipolineas\": \"${PUBLIC_FILES_URL}tipolineas.sql\"
    }
}" > info.json

git add .
git ci -m "Dump on $DATE"
git push
