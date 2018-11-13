package com.example.t3hjeff.bookshelf;

public class UserProfileInfo {
    public String name;
    public String phone;
    public String address;
    public String bdate;
    public int sharecount;
    public int userrating;
    public String email;

    public UserProfileInfo(String name, String phone, String address, String bdate, int sharecount, int userrating, String email) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.bdate = bdate;
        this.sharecount = sharecount;
        this.userrating = userrating;
        this.email = email;
    }

}
