package com.ivan.homework.service;

import com.ivan.homework.dao.HobbiesDAO;
import com.ivan.homework.models.Hobbies;

import java.util.List;

public class HobbiesServiceImpl implements HobbiesService{
    private final HobbiesDAO hobbiesDAO;

    public HobbiesServiceImpl(HobbiesDAO hobbiesDAO) {
        this.hobbiesDAO = hobbiesDAO;
    }

    @Override
    public Hobbies getByID(int hobbyID) {
        return hobbiesDAO.getByID(hobbyID);
    }

    @Override
    public List<Hobbies> getAll() {
        return hobbiesDAO.getAll();
    }

    @Override
    public Hobbies create(Hobbies hobby) {
        return hobbiesDAO.create(hobby);
    }

    @Override
    public Hobbies update(Hobbies hobby) {
        return hobbiesDAO.update(hobby);
    }

    @Override
    public boolean delete(int id) {
        return hobbiesDAO.delete(id);
    }
}
