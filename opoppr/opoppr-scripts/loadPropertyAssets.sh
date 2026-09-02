#!/bin/bash
# OPOPPR Load Database

# get directory in which this script is stored.
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
DB_HOST="localhost"
DB_PORT="3306"

while getopts f:u:p:h:P: option
do
        case "${option}"
        in
                f) FILE=${OPTARG};;
                u) USER=${OPTARG};;
                p) PASS=$OPTARG;;
                h) DB_HOST=${OPTARG};;
                P) DB_PORT=${OPTARG};;
        esac
done

echo "-----Loading Property Assets File------"
cp "${FILE}" /tmp/import.csv
cd /tmp
mysql -h $DB_HOST -P $DB_PORT -u $USER -p$PASS --local_infile=1 opoppr -e "source ${DIR}/loadPropertyAssets.sql;"
rm *.csv

echo "-----Preparing Count------"
COUNT=""
mysql -h $DB_HOST -P $DB_PORT -u $USER -p$PASS opoppr -e "SELECT count(*) FROM property_asset"
echo $COUNT

echo "OPOPPR LOAD FILE:$FILE - Script done. "