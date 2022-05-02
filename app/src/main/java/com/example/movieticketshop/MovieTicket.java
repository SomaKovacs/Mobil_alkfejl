package com.example.movieticketshop;

public class MovieTicket {
    private String id;
    private String title;
    private String length;
    private String description;
    private String price;
    private String ageLimit;
    private float ratingInfo;
    private int imageResource;
    private int cartedCount;


    public MovieTicket(String title, String length, String description, String price, String ageLimit, float ratingInfo, int imageResource, int cartedCount) {
        this.title = title;
        this.length = length;
        this.description = description;
        this.price = price;
        this.ageLimit = ageLimit;
        this.ratingInfo = ratingInfo;
        this.imageResource = imageResource;
    }

    public MovieTicket(){
    }

    public String getTitle() {
        return title;
    }

    public String getLength() {
        return length;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getAgeLimit() {
        return ageLimit;
    }

    public float getRatingInfo() {
        return ratingInfo;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getCartedCount() {
        return cartedCount;
    }

    public String _getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
