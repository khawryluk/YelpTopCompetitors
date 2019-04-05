
package edu.uchicago.kjhawryluk.YelpDataIngestion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Monday",
    "Tuesday",
    "Friday",
    "Wednesday",
    "Thursday",
    "Sunday",
    "Saturday"
})
public class JacksonBusinessHours {

    @JsonProperty("Monday")
    private String monday;
    @JsonProperty("Tuesday")
    private String tuesday;
    @JsonProperty("Friday")
    private String friday;
    @JsonProperty("Wednesday")
    private String wednesday;
    @JsonProperty("Thursday")
    private String thursday;
    @JsonProperty("Sunday")
    private String sunday;
    @JsonProperty("Saturday")
    private String saturday;

    @JsonProperty("Monday")
    public String getMonday() {
        return monday;
    }

    @JsonProperty("Monday")
    public void setMonday(String monday) {
        this.monday = monday;
    }

    @JsonProperty("Tuesday")
    public String getTuesday() {
        return tuesday;
    }

    @JsonProperty("Tuesday")
    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    @JsonProperty("Friday")
    public String getFriday() {
        return friday;
    }

    @JsonProperty("Friday")
    public void setFriday(String friday) {
        this.friday = friday;
    }

    @JsonProperty("Wednesday")
    public String getWednesday() {
        return wednesday;
    }

    @JsonProperty("Wednesday")
    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    @JsonProperty("Thursday")
    public String getThursday() {
        return thursday;
    }

    @JsonProperty("Thursday")
    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    @JsonProperty("Sunday")
    public String getSunday() {
        return sunday;
    }

    @JsonProperty("Sunday")
    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    @JsonProperty("Saturday")
    public String getSaturday() {
        return saturday;
    }

    @JsonProperty("Saturday")
    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    BusinessHours convertToBusinessHours() {
    	return new BusinessHours(this.sunday,
    			this.monday,
    			this.tuesday,
    			this.wednesday,
    			this.thursday,
    			this.friday,
    			this.saturday);
    }
}
