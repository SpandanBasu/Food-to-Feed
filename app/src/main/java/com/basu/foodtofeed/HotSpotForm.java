package com.basu.foodtofeed;

public class HotSpotForm {
    private String address;
    private String foodAmount;
    private String time;
    public HotSpotForm(String address, String foodAmount, String time) {
        this.address = address;
        this.foodAmount = foodAmount;
        this.time=time;
    }

    public HotSpotForm() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(String foodAmount) {
        this.foodAmount = foodAmount;
    }
}
