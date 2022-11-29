package com.dca.feed_me.Model;

public class Donation {
    String name,phone,items,place,quantity,time;

    public Donation(){
    }

    public Donation(String phone) {
        this.phone = phone;
    }

    public Donation(String name, String phone, String items, String place, String quantity, String time) {
        this.name = name;
        this.items = items;
        this.place = place;
        this.quantity = quantity;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}