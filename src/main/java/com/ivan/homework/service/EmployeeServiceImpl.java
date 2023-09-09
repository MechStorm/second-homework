package com.ivan.homework.service;

import com.ivan.homework.dao.EmployeeDAO;
import com.ivan.homework.models.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee getByID(int empID) {
        return employeeDAO.getByID(empID);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDAO.getAll();
    }

    @Override
    public Employee create(Employee employee) {
        return employeeDAO.create(employee);
    }

    @Override
    public boolean addHobbytoEmployee(int empID, int hobbyID) {
        return employeeDAO.addHobbyToEmployee(empID, hobbyID);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeDAO.update(employee);
    }

    @Override
    public boolean delete(int id) {
        return employeeDAO.delete(id);
    }
}
