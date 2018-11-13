package com.example.t3hjeff.bookshelf;

public class Users {
    public String address,bdate,email,name,phone;
    public int sharecount,userrating;

    public Users(){

    }

    public Users(String address, String bdate, String email, String name, String phone, int sharecount, int userrating) {
        this.address = address;
        this.bdate = bdate;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.sharecount = sharecount;
        this.userrating = userrating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getSharecount() {
        return sharecount;
    }

    public void setSharecount(int sharecount) {
        this.sharecount = sharecount;
    }

    public int getUserrating() {
        return userrating;
    }

    public void setUserrating(int userrating) {
        this.userrating = userrating;
    }
}

