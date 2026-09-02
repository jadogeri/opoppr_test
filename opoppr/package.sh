#! /bin/bash

if [ "$#" -ne "1" ]; then
    echo "Usage: package.sh <version-string>"
    exit 1
fi

if [ ! -d "target" ]; then
    mkdir target/
fi

mv docker-compose.yml docker-compose.yml.bak
mv docker-compose.aws.yml docker-compose.yml
zip -r target/opoppr-$1.zip * .ebextensions/* .platform/* -x *.bak Dockerrun.aws.json docker-compose.*.yml target/\*
mv docker-compose.yml docker-compose.aws.yml
mv docker-compose.yml.bak docker-compose.yml

