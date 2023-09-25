package repository;

import com.ivan.homework.dao.HobbiesDAO;
import com.ivan.homework.dao.HobbiesDAOImpl;
import com.ivan.homework.models.Hobbies;
import com.ivan.homework.util.DBConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Testcontainers
public class HobbiesDAOTest {

    private static HobbiesDAO hobbiesDAO;

    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:5.7.34")
            .withDatabaseName("my_db")
            .withUsername("bestuser")
            .withPassword("bestuser")
            .withInitScript("db.sql");

    @BeforeAll
    static void startDb() {
        mySQLContainer.start();
    }

    @BeforeEach
    public void createTables() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        dbConnection.setUrl(mySQLContainer.getJdbcUrl());
        hobbiesDAO = new HobbiesDAOImpl(dbConnection);
    }

    @AfterEach
    public void clearDB() {
        try (Connection connection = DriverManager.getConnection(mySQLContainer.getJdbcUrl(), "bestuser", "bestuser")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM hobbies");
            statement.executeUpdate("ALTER TABLE hobbies AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createHobbyTest() {
        hobbiesDAO.create(new Hobbies("Music"));
        hobbiesDAO.create(new Hobbies("Art"));
        hobbiesDAO.create(new Hobbies("Traveling"));

        Assertions.assertEquals(3, hobbiesDAO.getAll().size());
    }

    @Test
    public void getAllHobbiesTest() {
        hobbiesDAO.create(new Hobbies("Music"));
        hobbiesDAO.create(new Hobbies("Art"));
        hobbiesDAO.create(new Hobbies("Traveling"));
        hobbiesDAO.create(new Hobbies("Videogames"));
        hobbiesDAO.create(new Hobbies("Sport"));

        Assertions.assertEquals(5, hobbiesDAO.getAll().size());
    }

    @Test
    public void getByIDHobbyTest() {
        hobbiesDAO.create(new Hobbies("Music"));
        hobbiesDAO.create(new Hobbies("Art"));
        hobbiesDAO.create(new Hobbies("Traveling"));

        Assertions.assertEquals("Art", hobbiesDAO.getByID(2).getName());
    }

    @Test
    public void updateHobbyTest() {
        Hobbies hobbies = hobbiesDAO.create(new Hobbies("Music"));
        hobbies = hobbiesDAO.update(new Hobbies(hobbies.getId(), "Foreign languages"));

        Assertions.assertEquals("Foreign languages", hobbies.getName());
    }

    @Test
    public void deleteHobbyTest() {
        hobbiesDAO.create(new Hobbies("Music"));
        hobbiesDAO.create(new Hobbies("Art"));
        hobbiesDAO.create(new Hobbies("Traveling"));
        hobbiesDAO.create(new Hobbies("Videogames"));
        hobbiesDAO.create(new Hobbies("Sport"));

        boolean isDeleted = hobbiesDAO.delete(1);

        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(4, hobbiesDAO.getAll().size());
    }

    @AfterAll
    static void stopDb() {
        mySQLContainer.stop();
    }
}
