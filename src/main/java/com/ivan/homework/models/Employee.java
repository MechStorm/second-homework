package com.ivan.homework.models;

public class Employee {
    private int id;
    private String name;
    private String surname;
    private int workExp;
    private int salary;

    private int departmentID;

    public Employee() {
    }

    public Employee(String name, String surname, int workExp, int salary, int departmentID) {
        this.name = name;
        this.surname = surname;
        this.workExp = workExp;
        this.salary = salary;
        this.departmentID = departmentID;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getWorkExp() {
        return workExp;
    }

    public void setWorkExp(int workExp) {
        this.workExp = workExp;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
}
