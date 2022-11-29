package com.dca.feed_me.Model;

public class User {

    String Email,Name,Phone,Type;

    public User() {
    }

    public User(String email, String name, String phone, String type, String username) {
        Email = email;
        Name = name;
        Phone = phone;
        Type = type;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
