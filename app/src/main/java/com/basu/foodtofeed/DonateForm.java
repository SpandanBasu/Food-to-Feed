package com.basu.foodtofeed;

public class DonateForm {
    private String name;
    private String number;
    private String address;
    private int foodAmount;

    public DonateForm(String name, String number, String address, int foodAmount) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.foodAmount = foodAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
    }
}
