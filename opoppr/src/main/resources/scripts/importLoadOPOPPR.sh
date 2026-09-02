#!/bin/sh
# OPOPPR Load Database
while getopts d:f:u:p: option
do
        case "${option}"
        in
                d) DIR=${OPTARG};;
                f) FILE=${OPTARG};;
                u) USER=${OPTARG};;
                p) PASS=$OPTARG;;
        esac
done

echo $DIR
echo $FILE
echo $USER
echo $PASS

echo "-----Importing CSV------"
/Applications/mampstack-5.4.23-0/mysql/bin/mysql -u $USER -p$PASS --local_infile=1 opoppr -e "LOAD DATA LOCAL INFILE '$DIR/$FILE' INTO TABLE noa_pp_lat5_temp FIELDS TERMINATED BY '|' IGNORE 1 LINES"

echo "-----Preparing Count------"
COUNT=""
COUNT=. /Applications/mampstack-5.4.23-0/mysql/bin/mysql -u $USER -p$PASS opoppr -e "SELECT count(*) FROM noa_pp_lat5_temp"
echo $COUNT


echo "OPOPPR LOAD FILE:$FILE - Script done. "