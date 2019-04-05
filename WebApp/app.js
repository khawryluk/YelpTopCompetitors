'use strict';
const http = require('http');
var assert = require('assert');
const express= require('express');
const app = express();
const mustache = require('mustache');
const filesystem = require('fs');
const url = require('url');
const hbase = require('hbase-rpc-client');
const hostname = '127.0.0.1';
const port = 6090;

var client = hbase({
   // zookeeperHosts: ["localhost:2181"]
    zookeeperHosts: ["10.0.0.2:2181"], 
   zookeeperRoot: "/hbase-unsecure" 
});

client.on('error', function(err) {
  console.log(err)
})
app.use(express.static('public'));
app.get('/',function (req, res) {
	var businessid = req.query.businessid;
	var rank = req.query.rank;
	if(businessid){
		renderCompanyPage(res, businessid);
	} else{
		var start = '1';
		var end = '10';
		if(rank){
		start = rank;
		end = (parseInt(rank) + 9).toString();
		}
		
		start = start.padStart(8, '0');
		end = end.padStart(8, '0');
		renderRanking(res, start, end);
}
});

//Gets info for a list.
function getMainCompanyInfo(row){
	var starsAndReviews = getStarsAndReviews(row);
	var stars = starsAndReviews.stars;
	var reviews = starsAndReviews.reviews;
	var info = {
		name : row.cols['yelpBusiness:name'].value.toString(),
		businessid : row.cols['yelpBusiness:businessid'].value.toString(),
	    city :  row.cols['yelpBusiness:city'].value.toString(),
	    state :  row.cols['yelpBusiness:state'].value.toString(),
	    stars :  stars,
	    reviewcount :  reviews
	}
return info;
}

//Renders a list of 10 companies
function renderRanking(res, start, end){
 const scan = client.getScanner("KjhawrylukAllBusinesses",start , end );
    console.log(scan.toArray(function(err, results) {
	var resultsArray = results.map(row => getMainCompanyInfo(row));
    var template = filesystem.readFileSync("homepage.mustache").toString();
    var html = mustache.render(template,  {
    	businessListings : resultsArray

  });
  	res.send(html);
}))
}

//Checks if there's a value and returns the string
function getHbaseString(res){
	if(res)
		return res.value.toString();
}

//Checks if there is a value and returns the int
function getHbaseInt(res){
	if(res)
		return res.value.readIntBE(0, 8);
}

//Parses the stars and reviews from the row
function getStarsAndReviews(row){
	var stars = 0;
	var reviews = 0;
	if(row.cols['yelpBusiness:review_count']){
		reviews =  row.cols['yelpBusiness:review_count'].value.readIntBE(0, 8);
	};
	if(row.cols['yelpBusiness:star_sum']){
		var stars_sum =Math.round(row.cols['yelpBusiness:star_sum'].value.readIntBE(0, 8))
		if (reviews > 0 )
		stars = (Math.round(stars_sum / reviews* 100) / 100).toFixed(1);
	};
	var starsAndReviews = {
		stars:stars,
		reviews:reviews
	};
	return starsAndReviews

}



//Render's a specific company's page.
function renderCompanyPage(res, id){
	const get = new hbase.Get(id.toString().padStart(8, '0'));
     console.log(client.get("KjhawrylukYelpBusinessesInfoAndCompetitors", get, function(err, row) {
 	assert.ok(!err, "get returned an error: #{err}");
 	if(!row){
             res.send("<html><body>No such business found</body></html>");
             return;
         }

	var template = filesystem.readFileSync("company.mustache").toString();

	var starsAndReviews = getStarsAndReviews(row);
	var stars = starsAndReviews.stars;
	var reviews = starsAndReviews.reviews;

	var html = mustache.render(template,  {
	    name : row.cols['yelpBusiness:name'].value.toString(),
	    neighborhood :row.cols['yelpBusiness:neighborhood'].value.toString(),
	    address :  row.cols['yelpBusiness:address'].value.toString(),
	    city :  row.cols['yelpBusiness:city'].value.toString(),
	    state :  row.cols['yelpBusiness:state'].value.toString(),
	    postalcode : row.cols['yelpBusiness:postalcode'].value.toString(),
	    stars :  stars,
	    reviewcount :  reviews,
	    catagories :  row.cols['yelpBusiness:categories'].value.toString(),
	    competitor1id:  getHbaseString(row.cols['yelpBusiness:competitor1id']),
	    competitor1name : getHbaseString(row.cols['yelpBusiness:competitor1name']),
		competitor1ReviewCount: getHbaseInt(row.cols['yelpBusiness:competitor1ReviewCount']),
		competitor1PositiveReviewCount:  getHbaseInt(row.cols['yelpBusiness:competitor1PositiveReviewCount']),
		competitor2id:  getHbaseString(row.cols['yelpBusiness:competitor2id']),
		competitor2name:  getHbaseString(row.cols['yelpBusiness:competitor2name']),
		competitor2ReviewCount:  getHbaseInt(row.cols['yelpBusiness:competitor2ReviewCount']),
		competitor2PositiveReviewCount: getHbaseInt(row.cols['yelpBusiness:competitor2PositiveReviewCount']),
		competitor3id:  getHbaseString(row.cols['yelpBusiness:competitor3id']),
		competitor3name:  getHbaseString(row.cols['yelpBusiness:competitor3name']),
		competitor3ReviewCount:  getHbaseInt(row.cols['yelpBusiness:competitor3ReviewCount']),
		competitor3PositiveReviewCount:  getHbaseInt(row.cols['yelpBusiness:competitor3PositiveReviewCount'])
  });
  	res.send(html);
	}))
}

	
/* Send simulated yelp review to kafka */
var kafka = require('kafka-node');
var Producer = kafka.Producer;
var KeyedMessage = kafka.KeyedMessage;
var kafkaClient = new kafka.KafkaClient({kafkaHost:'10.0.0.2:6667'});
var kafkaProducer = new Producer(kafkaClient);


app.get('/review.html',function (req, res) {
	console.log("this happened.")
    var businessid = req.query['businessid'];
    var username = (req.query['username']);
    var stars = (req.query['stars']) ;
    var review = {
	businessid : businessid,
	stars : stars,
	user : username
    };

    kafkaProducer.send([{ topic: 'KjhawrylukYelpBusinessesInfoAndCompetitors', messages: JSON.stringify(review)}],
			   function (err, data) {
			       console.log(data);
			   });
    console.log(review);
    res.redirect('/reviewForm.html');
});

app.listen(port);
