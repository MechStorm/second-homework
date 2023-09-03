package com.ivan.homework.models;

public class Department {
    private int id;
    private String name;
    private int phoneNumber;
    private String email;
    private int yearsWorks;


    public Department() {
    }

    public Department(String name, int phoneNumber, String email, int yearsWorks) {
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getYearsWorks() {
        return yearsWorks;
    }

    public void setYearsWorks(int yearsWorks) {
        this.yearsWorks = yearsWorks;
    }
}
