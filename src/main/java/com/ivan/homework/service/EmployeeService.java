package com.ivan.homework.service;

import com.ivan.homework.models.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getByID(int empID);

    List<Employee> getAll();
}
