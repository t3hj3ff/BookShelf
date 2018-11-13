package com.example.t3hjeff.bookshelf;

public class ReturnUsers {
    private String name;
    private String bdate;

    public ReturnUsers() {
    }

    public ReturnUsers(String name, String bdate) {
        this.name = name;
        this.bdate = bdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }
}
