package service;

import com.ivan.homework.dao.HobbiesDAO;
import com.ivan.homework.models.Employee;
import com.ivan.homework.models.Hobbies;
import com.ivan.homework.service.HobbiesService;
import com.ivan.homework.service.HobbiesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HobbiesServiceTest {
    HobbiesService hobbiesService;
    @Mock
    HobbiesDAO hobbiesDAO;

    @BeforeEach
    public void start() {
        hobbiesService = new HobbiesServiceImpl(hobbiesDAO);
    }

    @Test
    public void testGetAllHobbies() {
        List<Hobbies> hobbies = new ArrayList<>();
        hobbies.add(new Hobbies(1, "Art"));
        hobbies.add(new Hobbies(2, "Videogames"));

        when(hobbiesDAO.getAll()).thenReturn(hobbies);

        assertEquals(hobbies, hobbiesService.getAll());
    }

    @Test
    public void testGetHobbyById() {
        Hobbies hobby = new Hobbies();
        hobby.setId(1);
        hobby.setName("Art");

        when(hobbiesDAO.getByID(hobby.getId())).thenReturn(hobby);

        assertEquals(hobby, hobbiesService.getByID(hobby.getId()));
    }

    @Test
    public void testCreateNewHobby() {
        Hobbies hobby = new Hobbies();
        hobby.setId(1);
        hobby.setName("Art");

        when(hobbiesDAO.create(hobby)).thenReturn(hobby);

        assertEquals(hobby, hobbiesService.create(hobby));
    }

    @Test
    public void testUpdateHobby() {
        Hobbies hobby = new Hobbies();
        hobby.setId(1);
        hobby.setName("Art");

        when(hobbiesDAO.update(hobby)).thenReturn(hobby);

        assertEquals(hobby, hobbiesService.update(hobby));
    }

    @Test
    public void testDeleteHobby() {
        Hobbies hobby = new Hobbies();
        hobby.setId(1);
        hobby.setName("Art");

        when(hobbiesDAO.delete(hobby.getId())).thenReturn(true);

        assertTrue(hobbiesService.delete(hobby.getId()));
    }
}
