#!/bin/bash
# OPOPPR Close Database

# get directory in which this script is stored.
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
DB_HOST="localhost"
DB_PORT="3306"

while getopts u:p:h:P: option
do
        case "${option}"
        in
                u) USER=${OPTARG};;
                p) PASS=$OPTARG;;
                h) DB_HOST=${OPTARG};;
                P) DB_PORT=${OPTARG};;
        esac
done

echo "-----Closing database------"
mysql -h $DB_HOST -P $DB_PORT -u $USER -p$PASS opoppr -e "update account a inner join form f on a.ACCOUNT_ID = f.ACCOUNT_ID set a.DISABLED=true where f.STATUS_ID in (1,2) and a.BILL_NUMBER not in ('OPATEST', 'OPAADMIN'); commit;"
