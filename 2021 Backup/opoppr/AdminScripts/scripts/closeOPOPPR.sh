#!/bin/sh
# OPOPPR Close Database

# get directory in which this script is stored.
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

while getopts :u:p: option
do
        case "${option}"
        in
                u) USER=${OPTARG};;
                p) PASS=$OPTARG;;
        esac
done

echo "-----Closing database------"
$MYSQL_HOME/bin/mysql -u $USER -p$PASS opoppr -e "update account a inner join form f on a.ACCOUNT_ID = f.ACCOUNT_ID set a.DISABLED=true where f.STATUS_ID in (1,2) and a.BILL_NUMBER not in ('OPATEST', 'OPAADMIN'); commit;"
