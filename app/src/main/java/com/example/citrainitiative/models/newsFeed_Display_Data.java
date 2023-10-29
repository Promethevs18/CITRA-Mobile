package com.example.citrainitiative.models;

public class newsFeed_Display_Data {
    String headLine, date, full_details, imageHeadline, shortDesc;

    public newsFeed_Display_Data(String headLine, String date, String full_details, String imageHeadline, String shortDesc) {
        this.headLine = headLine;
        this.date = date;
        this.full_details = full_details;
        this.imageHeadline = imageHeadline;
        this.shortDesc = shortDesc;
    }

    public newsFeed_Display_Data() {
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFull_details() {
        return full_details;
    }

    public void setFull_details(String full_details) {
        this.full_details = full_details;
    }

    public String getImageHeadline() {
        return imageHeadline;
    }

    public void setImageHeadline(String imageHeadline) {
        this.imageHeadline = imageHeadline;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }
}
