package com.ivan.homework.dao;

import com.ivan.homework.models.Employee;

import java.util.List;

public interface EmployeeDAO {
    Employee getByID(int empID);

    List<Employee> getAll();

    Employee create(Employee employee);
}
