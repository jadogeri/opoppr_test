#!/bin/bash
# OPOPPR Close Database

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

echo "-----Closing database------"
mysql -h $DB_HOST -P $DB_PORT -u $USER -p$PASS opoppr -e "update form f set f.STATUS_ID=4 where f.STATUS_ID in (1,2) and f.FILING_YEAR = ${YEAR}; commit;"

echo "-----Preparing Count------"
COUNT=""
mysql -h $DB_HOST -P $DB_PORT -u $USER -p$PASS opoppr -e "SELECT count(*) FROM form f where f.STATUS_ID=4;"
echo $COUNT

echo "Script done."
