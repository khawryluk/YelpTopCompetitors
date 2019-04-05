ADD JAR hdfs:///kjhawryluk/finalproject/uber-YelpDataIngestion-0.0.1-SNAPSHOT.jar;

CREATE EXTERNAL TABLE IF NOT EXISTS KjhawrylukYelpBusinesses
  ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.thrift.ThriftDeserializer'
    WITH SERDEPROPERTIES (
      'serialization.class' = 'edu.uchicago.kjhawryluk.YelpDataIngestion.YelpBusiness',
      'serialization.format' =  'org.apache.thrift.protocol.TBinaryProtocol')
  STORED AS SEQUENCEFILE 
  LOCATION '/kjhawryluk/finalproject/businesses';

-- Test our table
select * from KjhawrylukYelpBusinesses limit 5;

//Get running sum of stars. 
CREATE TABLE IF NOT EXISTS KjhawrylukYelpBusinessesWithStarSum(
businessid string,
name string, 
neighborhood string,
address string,
city string, 
state string,
postalcode string,
star_sum bigint,
reviewcount bigint,
categories Array<string>)
stored as orc;

insert overwrite table KjhawrylukYelpBusinessesWithStarSum

select 
b.businessid, 
b.name, 
b.neighborhood, 
b.address, 
b.city, 
b.state, 
b.postalcode,
c.star_sum, 
c.reviewcount, 
b.categories
from KjhawrylukYelpBusinesses b
LEFT JOIN
(select businessid, sum(cast(stars as bigint)) as star_sum, count(1) as reviewcount
from KjhawrylukYelpReviewsAbridged
group by businessid
)c
on b.businessid = c.businessid;


//Join business table with competitors'review count.
drop table KjhawrylukYelpBusinessesInfoAndCompetitors1;
create external table KjhawrylukYelpBusinessesInfoAndCompetitors1 (
businessid string,
name string, 
neighborhood string,
address string,
city string, 
state string,
postalcode string,
star_sum bigint,
review_count bigint,
categories string,
competitor1id string,
competitor1name string,
competitor1ReviewCount bigint,
competitor1PositiveReviewCount bigint);

insert overwrite table KjhawrylukYelpBusinessesInfoAndCompetitors1
Select 
b.businessid, 
b.name, 
b.neighborhood, 
b.address, 
b.city, 
b.state, 
b.postalcode,
b.star_sum, 
b.reviewcount, 
b.categories ,
c1.competitorid,
c1.competitorname,
c1.competitorReviewCount,
c1.positiveReviewCount
from KjhawrylukYelpBusinessesWithStarSum b
LEFT JOIN 
(select * from KjhawrylukTopYelpCompetitorsWithCompetitorName 
where competitorrank = 1) c1
on b.businessid = c1.businessid;

//Get competitor with second most reviews.
DROP table KjhawrylukYelpBusinessesInfoAndCompetitors2;
create external table KjhawrylukYelpBusinessesInfoAndCompetitors2 (
businessid string,
name string, 
neighborhood string,
address string,
city string, 
state string,
postalcode string,
star_sum bigint,
review_count bigint,
categories string,
competitor1id string,
competitor1name string,
competitor1ReviewCount bigint,
competitor1PositiveReviewCount bigint,
competitor2id string,
competitor2name string,
competitor2ReviewCount bigint,
competitor2PositiveReviewCount bigint
);

insert overwrite table KjhawrylukYelpBusinessesInfoAndCompetitors2
Select 
b.*,
c2.competitorid,
c2.competitorname,
c2.competitorReviewCount,
c2.positiveReviewCount
from KjhawrylukYelpBusinessesInfoAndCompetitors1 b
LEFT JOIN 
(select * from KjhawrylukTopYelpCompetitorsWithCompetitorName 
where competitorrank = 2) c2
on b.businessid = c2.businessid;

//Store in hbase with competitor with third most positive reviews.
Drop table KjhawrylukYelpBusinessesInfoAndCompetitors;
create external table KjhawrylukYelpBusinessesInfoAndCompetitors (
businessid string,
name string, 
neighborhood string,
address string,
city string, 
state string,
postalcode string,
star_sum bigint,
review_count bigint,
categories string,
competitor1id string,
competitor1name string,
competitor1ReviewCount bigint,
competitor1PositiveReviewCount bigint,
competitor2id string,
competitor2name string,
competitor2ReviewCount bigint,
competitor2PositiveReviewCount bigint,
competitor3id string,
competitor3name string,
competitor3ReviewCount bigint,
competitor3PositiveReviewCount bigint)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,
yelpBusiness:name,
yelpBusiness:neighborhood,
yelpBusiness:address,
yelpBusiness:city,
yelpBusiness:state,
yelpBusiness:postalcode,
yelpBusiness:star_sum#b,
yelpBusiness:review_count#b,
yelpBusiness:categories,
yelpBusiness:competitor1id,
yelpBusiness:competitor1name,
yelpBusiness:competitor1ReviewCount#b,
yelpBusiness:competitor1PositiveReviewCount#b,
yelpBusiness:competitor2id,
yelpBusiness:competitor2name,
yelpBusiness:competitor2ReviewCount#b,
yelpBusiness:competitor2PositiveReviewCount#b,
yelpBusiness:competitor3id,
yelpBusiness:competitor3name,
yelpBusiness:competitor3ReviewCount#b,
yelpBusiness:competitor3PositiveReviewCount#b'

)
TBLPROPERTIES ('hbase.table.name' = 'KjhawrylukYelpBusinessesInfoAndCompetitors');


insert overwrite table  KjhawrylukYelpBusinessesInfoAndCompetitors
Select 
b.*,
c3.competitorid,
c3.competitorname,
c3.competitorReviewCount,
c3.positiveReviewCount
from KjhawrylukYelpBusinessesInfoAndCompetitors2 b
LEFT JOIN 
(select * from KjhawrylukTopYelpCompetitorsWithCompetitorName 
where competitorrank = 3) c3
on b.businessid = c3.businessid;

