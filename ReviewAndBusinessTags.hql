add jar /home/mpcs53013/2018mpcs53013-kjhawryluk/YelpDataIngestion/target/uber-YelpDataIngestion-0.0.1-SNAPSHOT.jar;

DROP Table ReviewsAndTags;
CREATE Table ReviewsAndTags(text string, stars double, categories array<string>);
INSERT INTO ReviewsAndTags
select r.text, b.stars, b.categories from (SELECT * FROM YelpReviews limit 10000) r join YelpBusinesses b on r.businessId = b.businessId;