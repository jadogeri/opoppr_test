delete from PROPERTY_ASSET_TEMP;

load data local infile '/tmp/import.csv' into table PROPERTY_ASSET_TEMP FIELDS TERMINATED by ',' ENCLOSED by '\"' lines terminated by '\n' IGNORE 1 LINES;

alter table NOA_PP_LAT_5_FILING
drop foreign key fk_NOA_PP_LAT_5_FILING_PROPERTY_ASSET1;

truncate table PROPERTY_ASSET;

alter table PROPERTY_ASSET auto_increment = 1;

insert into PROPERTY_ASSET(SECTION_NUMBER, CATEGORY, PPTYPE, ASSET_DESCRIPTION, EFFECTIVE_LIFE)
  select SECTION_NUMBER, CATEGORY, PPTYPE, ASSET_DESCRIPTION, EFFECTIVE_LIFE from PROPERTY_ASSET_TEMP;

update NOA_PP_LAT_5_FILING f
set PROPERTY_ASSET_ID = (select pa.PROPERTY_ASSET_ID from PROPERTY_ASSET as pa where f.PPTYPE = pa.PPTYPE and f.CATEGORY = pa.CATEGORY);

alter table NOA_PP_LAT_5_FILING
add constraint fk_NOA_PP_LAT_5_FILING_PROPERTY_ASSET1 foreign key (PROPERTY_ASSET_ID) references PROPERTY_ASSET(PROPERTY_ASSET_ID)
on delete no action
on update no action;

commit;