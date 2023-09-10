package servlets;

import com.ivan.homework.models.Hobbies;
import com.ivan.homework.service.HobbiesServiceImpl;
import com.ivan.homework.servlets.HobbiesServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HobbiesServletTest {
    @Mock
    private HobbiesServiceImpl hobbiesService;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    private HobbiesServlet hobbiesServlet;
    @Mock
    private PrintWriter writer;

    @BeforeEach
    public void start() {
        StringWriter stringWriter = new StringWriter();
        hobbiesServlet = new HobbiesServlet();
        hobbiesServlet.init();
        hobbiesServlet.setHobbiesService(hobbiesService);
        writer = new PrintWriter(stringWriter);
    }

    @Test
    public void testGetAllHobbies() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn(null);

        List<Hobbies> hobbies = new ArrayList<>();
        hobbies.add(new Hobbies(1, "Art"));
        hobbies.add(new Hobbies(2, "Videogames"));
        when(hobbiesService.getAll()).thenReturn(hobbies);
        when(resp.getWriter()).thenReturn(writer);

        hobbiesServlet.doGet(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");

        assertEquals(2, hobbies.size());
    }

    @Test
    public void testGetByIdHobbies() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn("1");

        Hobbies hobby = new Hobbies(1, "Art");

        when(hobbiesService.getByID(1)).thenReturn(hobby);
        when(resp.getWriter()).thenReturn(writer);

        hobbiesServlet.doGet(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");

        assertEquals(1, hobby.getId());
        assertEquals("Art", hobby.getName());
    }

    @Test
    public void testDeleteFromHobbies() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn("1");
        when(resp.getWriter()).thenReturn(writer);

        Hobbies hobby = new Hobbies(1, "Art");
        when(hobbiesService.getByID(1)).thenReturn(hobby);
        when(hobbiesService.delete(1)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(writer);

        hobbiesServlet.doDelete(req, resp);

        verify(resp).setStatus(200);
        assertEquals("Hobby with id " + hobby.getId()  + " is successfully deleted", stringWriter.toString());
    }
}
