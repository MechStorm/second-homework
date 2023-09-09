package com.ivan.homework.service;

import com.ivan.homework.dao.HobbiesDAO;
import com.ivan.homework.models.Hobbies;

public class HobbiesServiceImpl implements HobbiesService{
    private final HobbiesDAO hobbiesDAO;

    public HobbiesServiceImpl(HobbiesDAO hobbiesDAO) {
        this.hobbiesDAO = hobbiesDAO;
    }

    @Override
    public Hobbies getByID(int hobbyID) {
        return hobbiesDAO.getByID(hobbyID);
    }
}
