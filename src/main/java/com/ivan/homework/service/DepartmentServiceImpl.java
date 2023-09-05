package com.ivan.homework.service;

import com.ivan.homework.dao.DepartmentDAO;
import com.ivan.homework.models.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentServiceImpl implements  DepartmentService{
    private final DepartmentDAO departmentDAO;

    public DepartmentServiceImpl(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Override
    public Department getByID(int depID) {
        return departmentDAO.getByID(depID);
    }

    @Override
    public List<Department> getAll() {
        return departmentDAO.getAll();
    }
}
