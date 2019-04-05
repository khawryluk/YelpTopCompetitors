package edu.uchicago.kjhawryluk.YelpDataIngestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"user_id",
"name",
"review_count",
"yelping_since",
"useful",
"funny",
"cool",
"fans",
"elite",
"average_stars",
"compliment_hot",
"compliment_more",
"compliment_profile",
"compliment_cute",
"compliment_list",
"compliment_note",
"compliment_plain",
"compliment_cool",
"compliment_funny",
"compliment_writer",
"compliment_photos"
})
public class JacksonYelpUser {

@JsonProperty("user_id")
private String userId;
@JsonProperty("name")
private String name;
@JsonProperty("review_count")
private Integer reviewCount;
@JsonProperty("yelping_since")
private String yelpingSince;
@JsonProperty("useful")
private Integer useful;
@JsonProperty("funny")
private Integer funny;
@JsonProperty("cool")
private Integer cool;
@JsonProperty("fans")
private Integer fans;
@JsonProperty("elite")
private List<Integer> elite = null;
@JsonProperty("average_stars")
private Double averageStars;
@JsonProperty("compliment_hot")
private Integer complimentHot;
@JsonProperty("compliment_more")
private Integer complimentMore;
@JsonProperty("compliment_profile")
private Integer complimentProfile;
@JsonProperty("compliment_cute")
private Integer complimentCute;
@JsonProperty("compliment_list")
private Integer complimentList;
@JsonProperty("compliment_note")
private Integer complimentNote;
@JsonProperty("compliment_plain")
private Integer complimentPlain;
@JsonProperty("compliment_cool")
private Integer complimentCool;
@JsonProperty("compliment_funny")
private Integer complimentFunny;
@JsonProperty("compliment_writer")
private Integer complimentWriter;
@JsonProperty("compliment_photos")
private Integer complimentPhotos;

@JsonProperty("user_id")
public String getUserId() {
return userId;
}

@JsonProperty("user_id")
public void setUserId(String userId) {
this.userId = userId;
}

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

@JsonProperty("review_count")
public Integer getReviewCount() {
return reviewCount;
}

@JsonProperty("review_count")
public void setReviewCount(Integer reviewCount) {
this.reviewCount = reviewCount;
}

@JsonProperty("yelping_since")
public String getYelpingSince() {
return yelpingSince;
}

@JsonProperty("yelping_since")
public void setYelpingSince(String yelpingSince) {
this.yelpingSince = yelpingSince;
}

@JsonProperty("useful")
public Integer getUseful() {
return useful;
}

@JsonProperty("useful")
public void setUseful(Integer useful) {
this.useful = useful;
}

@JsonProperty("funny")
public Integer getFunny() {
return funny;
}

@JsonProperty("funny")
public void setFunny(Integer funny) {
this.funny = funny;
}

@JsonProperty("cool")
public Integer getCool() {
return cool;
}

@JsonProperty("cool")
public void setCool(Integer cool) {
this.cool = cool;
}

@JsonProperty("fans")
public Integer getFans() {
return fans;
}

@JsonProperty("fans")
public void setFans(Integer fans) {
this.fans = fans;
}

@JsonProperty("elite")
public List<Integer> getElite() {
return elite;
}

@JsonProperty("elite")
public void setElite(String elite) {
	this.elite = new ArrayList<Integer>();
	if(elite != null && !elite.equals("None")) {
    	for (String eliteYearString : elite.split(", ")){
    		this.elite.add(Integer.parseInt(eliteYearString));
    	}
	}
}

@JsonProperty("average_stars")
public Double getAverageStars() {
return averageStars;
}

@JsonProperty("average_stars")
public void setAverageStars(Double averageStars) {
this.averageStars = averageStars;
}

@JsonProperty("compliment_hot")
public Integer getComplimentHot() {
return complimentHot;
}

@JsonProperty("compliment_hot")
public void setComplimentHot(Integer complimentHot) {
this.complimentHot = complimentHot;
}

@JsonProperty("compliment_more")
public Integer getComplimentMore() {
return complimentMore;
}

@JsonProperty("compliment_more")
public void setComplimentMore(Integer complimentMore) {
this.complimentMore = complimentMore;
}

@JsonProperty("compliment_profile")
public Integer getComplimentProfile() {
return complimentProfile;
}

@JsonProperty("compliment_profile")
public void setComplimentProfile(Integer complimentProfile) {
this.complimentProfile = complimentProfile;
}

@JsonProperty("compliment_cute")
public Integer getComplimentCute() {
return complimentCute;
}

@JsonProperty("compliment_cute")
public void setComplimentCute(Integer complimentCute) {
this.complimentCute = complimentCute;
}

@JsonProperty("compliment_list")
public Integer getComplimentList() {
return complimentList;
}

@JsonProperty("compliment_list")
public void setComplimentList(Integer complimentList) {
this.complimentList = complimentList;
}

@JsonProperty("compliment_note")
public Integer getComplimentNote() {
return complimentNote;
}

@JsonProperty("compliment_note")
public void setComplimentNote(Integer complimentNote) {
this.complimentNote = complimentNote;
}

@JsonProperty("compliment_plain")
public Integer getComplimentPlain() {
return complimentPlain;
}

@JsonProperty("compliment_plain")
public void setComplimentPlain(Integer complimentPlain) {
this.complimentPlain = complimentPlain;
}

@JsonProperty("compliment_cool")
public Integer getComplimentCool() {
return complimentCool;
}

@JsonProperty("compliment_cool")
public void setComplimentCool(Integer complimentCool) {
this.complimentCool = complimentCool;
}

@JsonProperty("compliment_funny")
public Integer getComplimentFunny() {
return complimentFunny;
}

@JsonProperty("compliment_funny")
public void setComplimentFunny(Integer complimentFunny) {
this.complimentFunny = complimentFunny;
}

@JsonProperty("compliment_writer")
public Integer getComplimentWriter() {
return complimentWriter;
}

@JsonProperty("compliment_writer")
public void setComplimentWriter(Integer complimentWriter) {
this.complimentWriter = complimentWriter;
}

@JsonProperty("compliment_photos")
public Integer getComplimentPhotos() {
return complimentPhotos;
}

@JsonProperty("compliment_photos")
public void setComplimentPhotos(Integer complimentPhotos) {
this.complimentPhotos = complimentPhotos;
}

public YelpUser convertToYelpUser() {
	YelpUser user = new YelpUser(
			this.userId, 
			this.name);
	user.setReviewCount(reviewCount); 
	user.setYelpingSince(yelpingSince); 
	user.setUseful(useful);
	user.setFunny(funny);
	user.setCool(cool);
	user.setFans(fans);
	user.setElite(elite);
	user.setAverageStars(averageStars);
	user.setComplimentHot(complimentHot);
	user.setComplimentMore(complimentMore);
	user.setComplimentProfile(complimentProfile);
	user.setComplimentCute(complimentCute);
	user.setComplimentList(complimentList);
	user.setComplimentNote(complimentNote);
	user.setComplimentPlain(complimentPlain);
	user.setComplimentCool(complimentCool);
	user.setComplimentFunny(complimentFunny);
	user.setComplimentWriter(complimentWriter);
	user.setComplimentPhotos(complimentPhotos);
		return user;
}


}