package com.ivan.homework.models;

public class Department {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String yearsWorks;


    public Department() {
    }

    public Department(String name, String phoneNumber, String email, String yearsWorks) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.yearsWorks = yearsWorks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYearsWorks() {
        return yearsWorks;
    }

    public void setYearsWorks(String yearsWorks) {
        this.yearsWorks = yearsWorks;
    }
}
