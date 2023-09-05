package com.ivan.homework.dao;

import com.ivan.homework.models.Department;

import java.util.List;

public interface DepartmentDAO {
    Department getByID(int depID);

    List<Department> getAll();
}
