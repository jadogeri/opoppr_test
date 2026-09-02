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

# Alternate Option:
# CREATE TABLE () (SELECT data FROM other_table ) ENGINE=CSV  ;
#   - This will produce a file with the table name and a .csv extention
#     but this may not be enabled on our VM

/Applications/mampstack-5.4.23-0/mysql/bin/mysql -u $USER -p$PASS opoppr -e 
SELECT JUR,
     PARID, 
     ALTID,
     TAXYR,
     OWNERNAME,
     ADDR1,
     ADDR2,
     CITYNAME,
     STATECODE,
     ZIP1,
     PIN,
     CONTACT_NAME,
     CONTACT_PHONE,
     CONTACT_FAX,
     CONTACT_EMAIL,
     PROPERTY_ADDRESS,
     TAXPAYER_NAME,
     TAXPAYER_PREPARED_DATE,
     TAX_PREPARER_NAME,
     TAX_PREPARER_PHONE,
     TAX_PREPARER_EMAIL,
     TAX_PREPARER_PREPARED_DATE 
FROM noa_pp_lat5 AS m INNER JOIN form AS f ON f.FORM_ID = m.FORM_ID 
WHERE STATUS_ID = 3
INTO OUTFILE '/tmp/noa_pp_lat5.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';

/Applications/mampstack-5.4.23-0/mysql/bin/mysql -u $USER -p$PASS opoppr -e 
SELECT f.JUR,
 f.PARID,
 f.TAXYR,
 f.CATEGORY,
 f.PPTYPE,
 f.FILEYR,
 f.YRACQD,
 f.NOUNITS,
 f.ACQUISITION_COST,
 f.EFFECTIVE_LIFE,
 f.CONSIGNER_OWNER_NAME,
 f.CONSIGNER_MAILING_ADDR,
 f.CONSIGNER_RENTAL_AMT,
 f.ITEM_DESCRIPTION,
 f.CONSIGNER_TEL_NO
FROM noa_pp_lat_5_filing AS f
INNER JOIN noa_pp_lat5 as p ON f.NOA_PP_LAT_5_ID = p.NOA_PP_LAT_5_ID
INNER JOIN form AS gp ON p.FORM_ID = gp.FORM_ID
WHERE gp.STATUS_ID = 3
INTO OUTFILE '/tmp/noa_pp_lat_5_filing.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';

/Applications/mampstack-5.4.23-0/mysql/bin/mysql -u $USER -p$PASS opoppr -e 
SELECT f.JUR,
 f.PARID,
 f.TAXYR,
 f.FILEYR,
 f.INVENTORY_TYPE,
 f.INVENTORY_MONTH,
 f.INVENTORY_AMT
FROM noa_pp_lat_5_inventories AS f
INNER JOIN noa_pp_lat5 AS p ON f.NOA_PP_LAT_5_ID = p.NOA_PP_LAT_5_ID 
INNER JOIN form AS gp ON p.FORM_ID = gp.FORM_ID 
WHERE gp.STATUS_ID = 3
AND f.INVENTORY_AMT > 0
INTO OUTFILE '/tmp/noa_pp_lat_5_inventories.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
