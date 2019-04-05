add jar /home/mpcs53013/2018mpcs53013-kjhawryluk/YelpDataIngestion/target/uber-YelpDataIngestion-0.0.1-SNAPSHOT.jar;

CREATE EXTERNAL TABLE IF NOT EXISTS YelpUsers
  ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.thrift.ThriftDeserializer'
    WITH SERDEPROPERTIES (
      'serialization.class' = 'edu.uchicago.kjhawryluk.YelpDataIngestion.YelpUser',
      'serialization.format' =  'org.apache.thrift.protocol.TBinaryProtocol')
  STORED AS SEQUENCEFILE 
  LOCATION '/kjhawryluk-finalproject/users';

-- Test our table
select * from YelpUsers limit 5;
