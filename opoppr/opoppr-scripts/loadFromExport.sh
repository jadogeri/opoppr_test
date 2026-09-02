#!/bin/bash
# OPOPPR Load Database

# get directory in which this script is stored.
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
DB_HOST="localhost"
DB_PORT="3306"

while getopts f:u:p:h:P:s: option
do
        case "${option}"
        in
                f) FILE=${OPTARG};;
                u) USER=${OPTARG};;
                p) PASS=$OPTARG;;
                h) DB_HOST=${OPTARG};;
                P) DB_PORT=${OPTARG};;
                s) FORM_STATUS=${OPTARG};;
        esac
done

echo "-----Loading from Export------"
cp "${FILE}" /tmp/import.zip
cd /tmp
unzip import.zip
sed -i.bak "s/\r$//g;s/\"NULL\"/NULL/g;s/\"null\"/NULL/g;s/null/NULL/g" *.csv
mysql -h $DB_HOST -P $DB_PORT -u $USER -p$PASS --local_infile=1 opoppr -e "set @FORM_STATUS=\"${FORM_STATUS}\"; source ${DIR}/loadFromExport.sql;"
rm import.zip
rm *.csv

echo "-----Preparing Count------"
COUNT=""
mysql -h $DB_HOST -P $DB_PORT -u $USER -p$PASS opoppr -e "SELECT count(*) FROM noa_pp_lat5_temp"
echo $COUNT


echo "OPOPPR LOAD FILE:$FILE - Script done. "