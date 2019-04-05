DROP  TABLE KjhawrylukAllBusinesses;
create external table KjhawrylukAllBusinesses (
id string,
businessid string,
name string, 
neighborhood string,
address string,
city string, 
state string,
postalcode string,
star_sum bigint,
review_count bigint,
categories string)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,
yelpBusiness:businessid,
yelpBusiness:name,
yelpBusiness:neighborhood,
yelpBusiness:address,
yelpBusiness:city,
yelpBusiness:state,
yelpBusiness:postalcode,
yelpBusiness:star_sum#b,
yelpBusiness:review_count#b,
yelpBusiness:categories'
)
TBLPROPERTIES ('hbase.table.name' = 'KjhawrylukAllBusinesses');
Insert into KjhawrylukAllBusinesses
select 
lpad(ROW_NUMBER() OVER (Order by r.reviewcount desc)
, 8, "0") as Id, r.*
from(
select
b.businessid, 
b.name, 
b.neighborhood, 
b.address, 
b.city, 
b.state, 
b.postalcode,
b.star_sum,
b.reviewcount,
concat_ws(', ', b.categories)
from (select * from KjhawrylukYelpBusinessesWithStarSum order by reviewcount desc limit 100) b)r;