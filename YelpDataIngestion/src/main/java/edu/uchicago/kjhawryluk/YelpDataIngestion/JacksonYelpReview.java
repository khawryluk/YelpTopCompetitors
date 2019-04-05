package edu.uchicago.kjhawryluk.YelpDataIngestion;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"review_id",
"user_id",
"business_id",
"stars",
"date",
"text",
"useful",
"funny",
"cool"
})
public class JacksonYelpReview {


/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@JsonProperty("review_id")
private String reviewId;
@JsonProperty("user_id")
private String userId;
@JsonProperty("business_id")
private String businessId;
@JsonProperty("stars")
private int stars;
@JsonProperty("date")
private String date;
@JsonProperty("text")
private String text;
@JsonProperty("useful")
private int useful;
@JsonProperty("funny")
private int funny;
@JsonProperty("cool")
private int cool;

@JsonProperty("review_id")
public String getReviewId() {
return reviewId;
}

@JsonProperty("review_id")
public void setReviewId(String reviewId) {
this.reviewId = reviewId;
}

@JsonProperty("user_id")
public String getUserId() {
return userId;
}

@JsonProperty("user_id")
public void setUserId(String userId) {
this.userId = userId;
}

@JsonProperty("business_id")
public String getBusinessId() {
return businessId;
}

@JsonProperty("business_id")
public void setBusinessId(String businessId) {
this.businessId = businessId;
}

@JsonProperty("stars")
public int getStars() {
return stars;
}

@JsonProperty("stars")
public void setStars(Integer stars) {
this.stars = stars;
}

@JsonProperty("date")
public String getDate() {
return date;
}

@JsonProperty("date")
public void setDate(String date) {
this.date = date;
}

@JsonProperty("text")
public String getText() {
return text;
}

@JsonProperty("text")
public void setText(String text) {
this.text = text;
}

@JsonProperty("useful")
public int getUseful() {
return useful;
}

@JsonProperty("useful")
public void setUseful(Integer useful) {
this.useful = useful;
}

@JsonProperty("funny")
public int getFunny() {
return funny;
}

@JsonProperty("funny")
public void setFunny(Integer funny) {
this.funny = funny;
}

@JsonProperty("cool")
public int getCool() {
return cool;
}

@JsonProperty("cool")
public void setCool(Integer cool) {
this.cool = cool;
}

public YelpReview convertToYelpReview() {
	return new YelpReview(this.reviewId, 
			this.userId, 
			this.businessId, 
			this.stars, 
			this.date, 
			this.text, 
			this.useful, 
			this.funny, 
			this.cool);
}

}