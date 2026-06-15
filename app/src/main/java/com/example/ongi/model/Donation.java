package com.example.ongi.model;

public class Donation {

    private String name;
    private String category;
    private String phone;
    private String address;
    private String homepage;
    private int image;

    public Donation(String name,
                    String category,
                    String phone,
                    String address,
                    String homepage,
                    int image) {

        this.name = name;
        this.category = category;
        this.phone = phone;
        this.address = address;
        this.homepage = homepage;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getImage() {
        return image;
    }
}