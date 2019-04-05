
package edu.uchicago.kjhawryluk.YelpDataIngestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "business_id",
    "name",
    "neighborhood",
    "address",
    "city",
    "state",
    "postal_code",
    "latitude",
    "longitude",
    "stars",
    "review_count",
    "is_open",
    "attributes",
    "categories",
    "hours"
})
public class JacksonYelpBusiness {

    @JsonProperty("business_id")
    private String businessId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("neighborhood")
    private String neighborhood;
    @JsonProperty("address")
    private String address;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("stars")
    private Double stars;
    @JsonProperty("review_count")
    private Integer reviewCount;
    @JsonProperty("is_open")
    private byte isOpen;
    @JsonProperty("attributes")
    private JacksonBusinessAttributes attributes;
    @JsonProperty("categories")
    private List<String> categories = null;
    @JsonProperty("hours")
    private JacksonBusinessHours hours;

    @JsonProperty("business_id")
    public String getBusinessId() {
        return businessId;
    }

    @JsonProperty("business_id")
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("neighborhood")
    public String getNeighborhood() {
        return neighborhood;
    }

    @JsonProperty("neighborhood")
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postal_code")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("stars")
    public Double getStars() {
        return stars;
    }

    @JsonProperty("stars")
    public void setStars(Double stars) {
        this.stars = stars;
    }

    @JsonProperty("review_count")
    public Integer getReviewCount() {
        return reviewCount;
    }

    @JsonProperty("review_count")
    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    @JsonProperty("is_open")
    public byte getIsOpen() {
        return isOpen;
    }

    @JsonProperty("is_open")
    public void setIsOpen(String isOpen) {
        this.isOpen = (byte)(isOpen.equals("1") ? 1: 0);
    }

    @JsonProperty("attributes")
    public JacksonBusinessAttributes getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(JacksonBusinessAttributes attributes) {
        this.attributes = attributes;
    }

    @JsonProperty("categories")
    public List<String> getCategories() {
        return categories;
    }

//    @JsonProperty("categories")
//    public void setCategories(List<String> categories) {
//        this.categories = categories;
//    }

    @JsonProperty("categories")
    public void setCategories(String category) {
    	if(category != null) {
	    	List<String> categories = Arrays.asList(category.split(", "));
	        this.categories = categories;
    	}
    }
    
    @JsonProperty("hours")
    public JacksonBusinessHours getHours() {
        return hours;
    }

    @JsonProperty("hours")
    public void setHours(JacksonBusinessHours hours) {
        this.hours = hours;
    }
    
    //Convert jackson object to thift object for serialization.
    public YelpBusiness convertToYelpBusiness(ObjectMapper mapper) {
    	YelpBusiness yelpBusiness = new  YelpBusiness(
    		    this.businessId,
    		    this.name,
    		    this.city,
    		    this.state,
    		    this.stars);
    	if(this.address != null) {
        	yelpBusiness.setAddress(this.address);
    	}
    	if(this.neighborhood != null) {
        	yelpBusiness.setNeighborhood(this.neighborhood);
    	}
    	if(this.postalCode != null) {
        	yelpBusiness.setPostalCode(this.postalCode);
    	}
    	if(this.longitude != null) {
        	yelpBusiness.setLongitude(this.longitude);
    	}
    	if(this.latitude != null) {
        	yelpBusiness.setLatitude(this.latitude);
    	}
    	if(this.reviewCount != null) {
        	yelpBusiness.setReviewCount(this.reviewCount);
    	}
        yelpBusiness.setIsOpen(this.isOpen);
    	if(this.attributes != null) {
        	try {
				yelpBusiness.setAttributes(mapper.writeValueAsString(this.attributes));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
    	}
    	if(this.categories != null && this.categories.size() > 0) {
        	yelpBusiness.setCategories(this.categories);
    	}
    	if(this.hours != null) {
        	yelpBusiness.setHours(this.hours.convertToBusinessHours());
    	}
    	return yelpBusiness;
    }
}
