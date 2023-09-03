package com.ivan.homework.models;

import java.util.List;

public class Hobbies {
    private int id;
    private String name;

    private List<Employee> employees;

    public Hobbies() {
    }

    public Hobbies(String name) {
        this.name = name;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
