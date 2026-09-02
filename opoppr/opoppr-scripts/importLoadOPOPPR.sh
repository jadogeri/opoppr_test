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

echo "-----Initializing database------"
cp "${FILE}" /tmp/import.csv
cd /tmp
sed -i "s/\|\|/\|NULL\|/g" *.csv
mysql -h $DB_HOST -P $DB_PORT -u $USER -p$PASS --local_infile=1 opoppr -e "source ${DIR}/importLoadOPOPPR.sql;"

echo "-----Preparing Count------"
COUNT=""
mysql -h $DB_HOST -P $DB_PORT -u $USER -p$PASS opoppr -e "SELECT count(*) FROM noa_pp_lat5_temp"
echo $COUNT


echo "OPOPPR LOAD FILE:$FILE - Script done. "