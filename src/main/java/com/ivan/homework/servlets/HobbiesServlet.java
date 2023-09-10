package com.ivan.homework.servlets;

import com.google.gson.Gson;
import com.ivan.homework.dao.HobbiesDAO;
import com.ivan.homework.dao.HobbiesDAOImpl;
import com.ivan.homework.models.Hobbies;
import com.ivan.homework.service.HobbiesService;
import com.ivan.homework.service.HobbiesServiceImpl;
import com.ivan.homework.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/hobbies")
public class HobbiesServlet extends HttpServlet {
    private Gson gson;
    private HobbiesService hobbiesService;
    private PrintWriter writer;

    @Override
    public void init() throws ServletException {
        try {
            gson = new Gson();
            DBConnection conn = new DBConnection();
            HobbiesDAO hobbiesDAO = new HobbiesDAOImpl(conn);
            hobbiesService = new HobbiesServiceImpl(hobbiesDAO);
        } catch (SQLException e) {
            throw new RuntimeException("Error with connect to db");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null) {
            int hobID = Integer.parseInt(id);
             Hobbies hobby = hobbiesService.getByID(hobID);

            if (hobby == null) {
                resp.setStatus(404);
            } else {
                writer = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                writer.println(gson.toJson(hobby));
                resp.setStatus(200);
                writer.close();
            }
        } else {
            List<Hobbies> hobbies = hobbiesService.getAll();
            writer = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            writer.println(gson.toJson(hobbies));
            resp.setStatus(200);
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null) {
            writer = resp.getWriter();

            String name = req.getParameter("name");
            Hobbies hobby = new Hobbies(name);

            writer.println(gson.toJson(hobbiesService.create(hobby)));
        } else {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null) {
            writer = resp.getWriter();

            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");

            if (id == 0 || name == null) {
                resp.setStatus(400);
                return;
            }

            Hobbies hobby = new Hobbies(id, name);
            writer.println(gson.toJson(hobbiesService.update(hobby)));
            resp.setStatus(200);
        } else {
            resp.setStatus(404);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        Hobbies hobby = hobbiesService.getByID(id);

        if (hobby == null) {
            resp.setStatus(404);
        } else {
            boolean deleteSuccess = hobbiesService.delete(id);
            if (deleteSuccess) {
                writer.println("Hobby with id " + id + "is successfully deleted");
                resp.setStatus(200);
            } else {
                resp.setStatus(404);
            }
        }
    }
}
