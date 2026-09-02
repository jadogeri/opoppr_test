delete
from noa_pp_lat_5_inventories
where NOA_PP_LAT_5_ID in (
  select n.NOA_PP_LAT_5_ID
  from noa_pp_lat5 n
  inner join form f on n.FORM_ID = f.FORM_ID
  where f.FILING_YEAR = @YEAR);

delete
from noa_pp_lat_5_filing
where NOA_PP_LAT_5_ID in (
  select n.NOA_PP_LAT_5_ID
  from noa_pp_lat5 as n
  inner join form as f on n.FORM_ID = f.FORM_ID
  where f.FILING_YEAR = @YEAR);

delete
from noa_pp_lat5
where FORM_ID in (
  select f.FORM_ID 
  from form f
  where f.FILING_YEAR = @YEAR);

delete
from noa_pp_lat5_temp;

delete
from noa_pp_lat_5_filing_temp;

delete
from noa_pp_lat_5_inventories_temp;

delete
from form f
where f.FILING_YEAR = @YEAR;