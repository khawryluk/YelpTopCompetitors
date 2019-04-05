Drop Table KjhawrylukYelpCompetitors;
CREATE TABLE IF NOT EXISTS KjhawrylukYelpCompetitors(
businessid string,
competitorid string,
competitorReviewCount bigint,
positiveReviewCount bigint);
stored as orc;
insert overwrite table KjhawrylukYelpCompetitors

select businessid, competitorid, count(1) as competitorReviewCount, count(competitorStars > 3) as positiveReviewCount
From (
select b.businessid, c.businessid as competitorid, c.stars as competitorStars from
KjhawrylukYelpReviewsAbridged b 
join KjhawrylukYelpReviewsAbridged c on b.userid = c.userid
where b.businessid <> c.businessid
)d
group by businessid, competitorid
order by businessid, positiveReviewCount desc;

Drop Table KjhawrylukTopYelpCompetitors;
CREATE TABLE IF NOT EXISTS KjhawrylukTopYelpCompetitors(
businessid string,
competitorid string,
competitorrank bigint,
competitorReviewCount bigint,
positiveReviewCount bigint)
stored as orc;
insert overwrite table KjhawrylukTopYelpCompetitors

// For this query, I believe I needed to do 
// (select * from KjhawrylukYelpCompetitors order by businessid, positiveReviewCount desc)
// instead of KjhawrylukYelpCompetitors
// However, I kept getting errosr. 
SELECT 
businessid,
competitorid,
competitorrank,
competitorReviewCount,
positiveReviewCount
FROM
(
    SELECT
          c.*,
        ROW_NUMBER() OVER(PARTITION BY c.businessid) competitorrank 
    FROM KjhawrylukYelpCompetitors c
	 
) comp
WHERE comp.competitorrank <= 5;

CREATE INDEX topCompetitorRank on TABLE KjhawrylukTopYelpCompetitors(competitorrank) as 'COMPACT' WITH DEFERRED REBUILD;
ALTER INDEX topCompetitorRank ON KjhawrylukTopYelpCompetitors REBUILD;

CREATE TABLE IF NOT EXISTS KjhawrylukTopYelpCompetitorsWithCompetitorName(
businessid string,
competitorid string,
competitorrank bigint,
competitorReviewCount bigint,
positiveReviewCount bigint,
competitorname string)
stored as orc;


insert overwrite table KjhawrylukTopYelpCompetitorsWithCompetitorName

select c.*, b.name from
KjhawrylukTopYelpCompetitors c
join KjhawrylukYelpBusinessesWithStarSum b on c.competitorid = b.businessid;
