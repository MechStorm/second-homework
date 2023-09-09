package com.ivan.homework.dao;

import com.ivan.homework.models.Employee;

import java.util.List;

public interface EmployeeDAO {
    Employee getByID(int empID);

    List<Employee> getAll();

    Employee create(Employee employee);

    boolean addHobbyToEmployee(int empID, int hobbyID);

    Employee update(Employee employee);

    boolean delete(int id);
}
