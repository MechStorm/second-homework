package com.ivan.homework.service;

import com.ivan.homework.models.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getByID(int empID);

    List<Employee> getAll();

    Employee create(Employee employee);

    boolean addHobbytoEmployee(int empID, int hobbyID);

    Employee update(Employee employee);

    boolean delete(int id);
}
