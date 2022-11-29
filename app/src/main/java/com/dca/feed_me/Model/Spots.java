package com.dca.feed_me.Model;

public class Spots {
    String Name,Address,Pincode,Landmark;

    public Spots(){
    }

    public Spots(String name, String address, String pincode, String landmark) {
        Name = name;
        Address = address;
        Pincode = pincode;
        Landmark = landmark;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }
}
