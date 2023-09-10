package com.example.garib_generalapplicationforruralincomebuilding;

public class Job {
    private String Company_name;
    private String Description3;
    private String Discription_1;
    private String Label;
    private String Location;
    private String Role;
    private String Title_URL;
    private String attribute_snippet;
    private String date;
    private String hiringmultiplecandidatescaption;
    private String type;

    // Constructor (you can generate getters and setters for these fields)

    public Job() {
        // Default constructor required for Firebase
    }

    public Job(String Company_name, String Description3, String Discription_1, String Label,
               String Location, String Role, String Title_URL, String attribute_snippet,
               String date, String hiringmultiplecandidatescaption, String type) {
        this.Company_name = Company_name;
        this.Description3 = Description3;
        this.Discription_1 = Discription_1;
        this.Label = Label;
        this.Location = Location;
        this.Role = Role;
        this.Title_URL = Title_URL;
        this.attribute_snippet = attribute_snippet;
        this.date = date;
        this.hiringmultiplecandidatescaption = hiringmultiplecandidatescaption;
        this.type = type;
    }

    // Generate getters and setters for each field

    public String getCompany_name() {
        return Company_name;
    }

    public void setCompany_name(String Company_name) {
        this.Company_name = Company_name;
    }

    public String getDescription3() {
        return Description3;
    }

    public void setDescription3(String Description3) {
        this.Description3 = Description3;
    }

    public String getDiscription_1() {
        return Discription_1;
    }

    public void setDiscription_1(String Discription_1) {
        this.Discription_1 = Discription_1;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String Label) {
        this.Label = Label;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getTitle_URL() {
        return Title_URL;
    }

    public void setTitle_URL(String Title_URL) {
        this.Title_URL = Title_URL;
    }

    public String getAttribute_snippet() {
        return attribute_snippet;
    }

    public void setAttribute_snippet(String attribute_snippet) {
        this.attribute_snippet = attribute_snippet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHiringmultiplecandidatescaption() {
        return hiringmultiplecandidatescaption;
    }

    public void setHiringmultiplecandidatescaption(String hiringmultiplecandidatescaption) {
        this.hiringmultiplecandidatescaption = hiringmultiplecandidatescaption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
