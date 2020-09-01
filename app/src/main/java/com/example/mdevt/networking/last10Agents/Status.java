package com.example.mdevt.networking.last10Agents;

public class Status {
    String name,imageUrl,phone,surName;
    public Status(String name,String phone,String surName, String imageUrl){
        this.name=name;
        this.imageUrl=imageUrl;
        this.phone=phone;
        this.surName=surName;

    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhone() {
        return phone;
    }

    public String getSurName() {
        return surName;
    }
}
