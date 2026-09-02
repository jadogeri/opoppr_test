delete
from noa_pp_lat_5_inventories
where NOA_PP_LAT_5_ID in (
  select n.NOA_PP_LAT_5_ID
  from noa_pp_lat5 n
  inner join form f on n.FORM_ID = f.FORM_ID
  inner join account a on f.ACCOUNT_ID = a.ACCOUNT_ID
  where a.BILL_NUMBER not in ('OPATEST', 'OPAADMIN'));

delete
from noa_pp_lat_5_filing
where NOA_PP_LAT_5_ID in (
  select n.NOA_PP_LAT_5_ID
  from noa_pp_lat5 as n
  inner join form as f on n.FORM_ID = f.FORM_ID
  inner join account as a on f.ACCOUNT_ID = a.ACCOUNT_ID
  where a.BILL_NUMBER not in ('OPATEST', 'OPAADMIN'));

delete
from noa_pp_lat5
where FORM_ID in (
  select f.FORM_ID 
  from form f
  inner join account a on f.ACCOUNT_ID = a.ACCOUNT_ID
  where a.BILL_NUMBER not in ('OPAADMIN', 'OPATEST'));

delete
from noa_pp_lat5_temp;

delete
from form
where ACCOUNT_ID in (
  select a.ACCOUNT_ID
  from account as a
  where a.BILL_NUMBER not in ('OPAADMIN', 'OPATEST'));

delete
from account
where BILL_NUMBER not in ('OPAADMIN', 'OPATEST');