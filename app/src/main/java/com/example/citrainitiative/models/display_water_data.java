package com.example.citrainitiative.models;

public class display_water_data {
    String place, locatedWithin, imageUrl;

    public display_water_data() {
    }

    public display_water_data(String place, String locatedWithin, String imageUrl) {
        this.place = place;
        this.locatedWithin = locatedWithin;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
