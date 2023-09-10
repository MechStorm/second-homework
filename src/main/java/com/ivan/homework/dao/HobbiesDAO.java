package com.ivan.homework.dao;

import com.ivan.homework.models.Hobbies;

import java.util.List;

public interface HobbiesDAO {
    Hobbies getByID(int hobbyID);

    List<Hobbies> getAll();

    Hobbies create(Hobbies hobby);

    Hobbies update(Hobbies hobby);

    boolean delete(int id);
}
