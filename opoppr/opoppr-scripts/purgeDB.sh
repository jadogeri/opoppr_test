#!/bin/bash
# OPOPPR Load Database

# get directory in which this script is stored.
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
DB_HOST="localhost"
DB_PORT="3306"

while getopts u:p:h:P:y: option
do
        case "${option}"
        in
                u) USER=${OPTARG};;
                p) PASS=$OPTARG;;
                h) DB_HOST=${OPTARG};;
                P) DB_PORT=${OPTARG};;
                y) YEAR=${OPTARG};;
        esac
done

echo "-----Purging database------"
mysql -h $DB_HOST -P $DB_PORT -u $USER -p$PASS --local_infile=1 opoppr -e "set @YEAR=\"${YEAR}\"; source ${DIR}/purgeDB.sql; commit;"

echo "-----Preparing Count------"
COUNT=""
mysql -h $DB_HOST -P $DB_PORT -u $USER -p$PASS opoppr -e "SELECT count(*) FROM form"
echo $COUNT


echo "OPOPPR PURGE - Script done. "