ADD JAR hdfs:///kjhawryluk/finalproject/uber-YelpDataIngestion-0.0.1-SNAPSHOT.jar;
DROP TABLE KjhawrylukYelpReviews;
CREATE EXTERNAL TABLE IF NOT EXISTS KjhawrylukYelpReviews
  ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.thrift.ThriftDeserializer'
    WITH SERDEPROPERTIES (
      'serialization.class' = 'edu.uchicago.kjhawryluk.YelpDataIngestion.YelpReview',
      'serialization.format' =  'org.apache.thrift.protocol.TBinaryProtocol')
  STORED AS SEQUENCEFILE 
  LOCATION '/kjhawryluk/finalproject/reviews/';


CREATE TABLE IF NOT EXISTS KjhawrylukYelpReviewsAbridged(
reviewid string,
userid string,
businessid string,
stars int,
text string)
stored as orc;
insert overwrite table KjhawrylukYelpReviewsAbridged 
select reviewid, userid, businessid, stars, text from KjhawrylukYelpReviews;