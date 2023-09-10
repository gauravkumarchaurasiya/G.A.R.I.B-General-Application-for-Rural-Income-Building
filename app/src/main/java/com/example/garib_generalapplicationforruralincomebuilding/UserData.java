package com.example.garib_generalapplicationforruralincomebuilding;

public class UserData {
    private String city;
    private int age;
    private String sex;
    private String primaryBusiness;
    private double annualIncome;
    private int youngDependents;
    private String homeOwnership;
    private String houseArea;

    public UserData() {
        // Default constructor required for Firebase
    }

    public UserData(String city, int age, String sex, String primaryBusiness,
                    double annualIncome, int youngDependents, String homeOwnership,
                    String houseArea) {
        this.city = city;
        this.age = age;
        this.sex = sex;
        this.primaryBusiness = primaryBusiness;
        this.annualIncome = annualIncome;
        this.youngDependents = youngDependents;
        this.homeOwnership = homeOwnership;
        this.houseArea = houseArea;
    }

    // Getters and setters for all fields
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPrimaryBusiness() {
        return primaryBusiness;
    }

    public void setPrimaryBusiness(String primaryBusiness) {
        this.primaryBusiness = primaryBusiness;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public int getYoungDependents() {
        return youngDependents;
    }

    public void setYoungDependents(int youngDependents) {
        this.youngDependents = youngDependents;
    }

    public String getHomeOwnership() {
        return homeOwnership;
    }

    public void setHomeOwnership(String homeOwnership) {
        this.homeOwnership = homeOwnership;
    }

    public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }
}

