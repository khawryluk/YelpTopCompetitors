namespace java edu.uchicago.kjhawryluk.YelpDataIngestion

struct BusinessHours {
	1: string Sunday;
	2: string Monday;
	3: string Tuesday;
	4: string Wednesday;
	5: string Thursday;
	6: string Friday;
	7: string Saturday;
}


struct YelpBusiness {
	1: required string businessId;
	2: required string name;
	3: optional string neighborhood;
	4: optional string address;
	5: required string city;
	6: required string state;
	7: optional string postalCode;
	8: optional double latitude;
	9: optional double longitude;
	10: required double stars;
	11: optional i32 reviewCount;
	12: optional byte isOpen;
	13: optional string attributes;
	14: optional list<string> categories;
	15: optional BusinessHours hours;	
} 

struct YelpReview {
	1: required string reviewId;
	2: required string userId;
	3: required string businessId;
	4: required i32 stars;
	5: required string date;
	6: required string text;
	7: required i32 useful;
	8: required i32 funny;
	9: required i32 cool;
}

struct YelpUser{
	1: required string userId;
	2: required string name;
	3: optional i32 reviewCount;
	4: optional string yelpingSince;
	5: optional i32 useful;
	6: optional i32 funny;
	7: optional i32 cool;
	8: optional i32 fans;
	9: optional list<i32> elite;
	10: optional double averageStars;
	11: optional i32 complimentHot;
	12: optional i32 complimentMore;
	13: optional i32 complimentProfile;
	14: optional i32 complimentCute;
	15: optional i32 complimentList;
	16: optional i32 complimentNote;
	17: optional i32 complimentPlain;
	18: optional i32 complimentCool;
	19: optional i32 complimentFunny;
	20: optional i32 complimentWriter;
	21: optional i32 complimentPhotos;
}