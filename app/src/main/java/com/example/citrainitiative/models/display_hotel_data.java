package com.example.citrainitiative.models;

public class display_hotel_data {
    String place, startPrice, imageUrl, shortDesc;

    public display_hotel_data() {
    }

    public display_hotel_data(String place, String startPrice, String imageUrl, String shortDesc) {
        this.place = place;
        this.startPrice = startPrice;
        this.imageUrl = imageUrl;
        this.shortDesc = shortDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getstartPrice() {
        return startPrice;
    }

    public void setstartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
