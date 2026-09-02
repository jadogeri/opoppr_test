INSERT INTO account (bill_number, pin) 
SELECT altid, pin from noa_pp_lat5_temp;

INSERT INTO form (account_id, status_id, title, filing_year, form_type_id) 
SELECT a.account_id, 1, '2015 LAT 5', t.taxyr, 1 from account as a inner join noa_pp_lat5_temp as t on a.bill_number = t.altid;

insert into noa_pp_lat5 (JUR, PARID, ALTID, TAXYR, OWNERNAME, ADDR1, ADDR2, CITYNAME, STATECODE, ZIP1, PIN, CONTACT_NAME, CONTACT_PHONE, CONTACT_FAX, CONTACT_EMAIL, PROPERTY_ADDRESS, FORM_ID) 
select t.JUR, t.PARID, t.ALTID, t.TAXYR, t.OWNERNAME, t.ADDR1, t.ADDR2, t.CITYNAME, t.STATECODE, t.ZIP1, t.PIN, t.CONTACT_NAME, REPLACE(REPLACE(REPLACE(REPLACE(t.CONTACT_PHONE, '(', ''),')',''),'-',''),' ',''), t.CONTACT_FAX, t.CONTACT_EMAIL, t.PROPERTY_ADDRESS, f.FORM_ID 
from noa_pp_lat5_temp as t inner join account as a  on a.bill_number = t.altid inner join form as f on a.account_id = f.account_id;
