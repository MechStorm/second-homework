package com.ivan.homework.service;

import com.ivan.homework.models.Department;

import java.util.List;

public interface DepartmentService {
    Department getByID(int depID);

    List<Department> getAll();

    Department create(Department department);

    Department update(Department department);


    boolean delete(int id);
}
