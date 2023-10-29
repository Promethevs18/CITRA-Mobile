package com.example.citrainitiative.models;

public class display_store_data {
    String productName, startPrice, imageHeadline, shortDesc;

    public display_store_data() {
    }

    public display_store_data(String productName, String startPrice, String imageHeadline, String shortDesc) {
        this.productName = productName;
        this.startPrice = startPrice;
        this.imageHeadline = imageHeadline;
        this.shortDesc = shortDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getproductName() {
        return productName;
    }

    public void setproductName(String productName) {
        this.productName = productName;
    }

    public String getstartPrice() {
        return startPrice;
    }

    public void setstartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getimageHeadline() {
        return imageHeadline;
    }

    public void setimageHeadline(String imageHeadline) {
        this.imageHeadline = imageHeadline;
    }
}
