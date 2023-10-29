package com.example.citrainitiative.models;


public class General_Firebase_Data_Getter_and_Setter {

    String imageUrl, place, locatedWithin, goodFor, details, googleMapLink, productName, imageHeadline;

    public General_Firebase_Data_Getter_and_Setter() {
    }

    public String getImageHeadline() {
        return imageHeadline;
    }

    public void setImageHeadline(String imageHeadline) {
        this.imageHeadline = imageHeadline;
    }

    public General_Firebase_Data_Getter_and_Setter(String imageUrl, String place, String locatedWithin, String goodFor, String details, String googleMapLink, String productName, String imageHeadline) {
        this.imageUrl = imageUrl;
        this.place = place;
        this.locatedWithin = locatedWithin;
        this.goodFor = goodFor;
        this.details = details;
        this.googleMapLink = googleMapLink;
        this.productName = productName;
        this.imageHeadline = imageHeadline;

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getlocatedWithin() {
        return locatedWithin;
    }

    public void setlocatedWithin(String locatedWithin) {
        this.locatedWithin = locatedWithin;
    }

    public String getGoodFor() {
        return goodFor;
    }

    public void setGoodFor(String goodFor) {
        this.goodFor = goodFor;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
        this.details = details.replace("\\n", "\n");
    }

    public String getGoogleMapLink() {
        return googleMapLink;
    }

    public void setGoogleMapLink(String googleMapLink) {
        this.googleMapLink = googleMapLink;
    }
}