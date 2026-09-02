#!/bin/sh
# OPOPPR Load Database

# get directory in which this script is stored.
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

while getopts :f:u:p: option
do
        case "${option}"
        in
                f) FILE=${OPTARG};;
                u) USER=${OPTARG};;
                p) PASS=$OPTARG;;
        esac
done

echo "-----Initializing database------"
$MYSQL_HOME/bin/mysql -u $USER -p$PASS --local_infile=1 opoppr -e "source ${DIR}/importLoadOPOPPR-pre.sql; LOAD DATA LOCAL INFILE '$FILE' INTO TABLE noa_pp_lat5_temp FIELDS TERMINATED BY '|' IGNORE 1 LINES; source ${DIR}/importLoadOPOPPR.sql; commit;"

echo "-----Preparing Count------"
COUNT=""
$MYSQL_HOME/bin/mysql -u $USER -p$PASS opoppr -e "SELECT count(*) FROM noa_pp_lat5_temp"
echo $COUNT


echo "OPOPPR LOAD FILE:$FILE - Script done. "