import com.revature.daos.SystemPostgreSQL;
import com.revature.models.Item;
import com.revature.models.User;
import com.revature.services.SystemService;
import com.revature.services.UserService;
import com.revature.util.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTester {

    private static SystemService sut;
    private static UserService us;
    private static Logger log = LogManager.getLogger(SystemTester.class);

    @BeforeAll
    public static void setUp() {
        sut = new SystemService();
        us = new UserService();
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Stopping all tests.");
    }

    @BeforeEach
    public void before() {
        System.out.println("Starting new test.");
    }

    @Test
    public void updateOwnership(){
        User testUser = new User();
        testUser.setUserId(18);
        testUser.setUsername("test");
        testUser.setPassword("passwd");
        testUser.setEmployee(false);

        Item testItem = new Item();
        testItem.setAvailable(true);
        testItem.setName("orange");
        testItem.setPrice(5.35f);
        testItem.setId(17);
        float amount = 50f;

        sut.updateOwnership(testUser, testItem, amount);

        User actualUser = us.getUser(testUser);

        assertEquals(testUser.getOwnedItems(), actualUser.getOwnedItems());
    }

    @Test
    public void rejectOffers(){
        Item i = new Item();
        i.setId(14);
        i.setName("banana");
        String expected = "rejected";
        String actual = "";

        sut.rejectOtherOffers(i);
        String sql = "select * from offer where item_id = '14' and accepted = 'rejected';";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
        ResultSet rs = c.createStatement().executeQuery(sql);

            while(rs.next()){
                actual = rs.getString("accepted");
            }
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        assertEquals(expected,actual);

    }

    @Test
    public void getWeeklyPayment(){
        User u = new User();
        u.setUserId(14);
        float expectedDue = 50f;


        float due = sut.getWeeklyPayment(u);
        System.out.println("Total amount due is " +due);

        assertEquals(expectedDue,due);
    }

    @Test
    public void getItemPayment(){
        User u = new User();
        Item i = new Item();
        u.setUserId(14);
        i.setId(2);
        float expectedDue = 50f;

        float due = sut.getItemPayment(u, i);

        assertEquals(expectedDue, due);
    }

    @Test
    public void getItemOffer(){
        User u = new User();
        Item i = new Item();
        u.setUserId(14);
        i.setId(7);
        float expectedAmount = 50f;

        float actualAmount = sut.getItemOffer(u,i);

        assertEquals(expectedAmount, actualAmount);
    }

    @Test
    public void isOfferAccepted(){
        User u = new User();
        Item i = new Item();
        u.setUserId(14);
        i.setId(2);
        boolean expected = true;

        boolean actual = sut.isOfferAccepted(u,i);

        assertEquals(expected, actual);
    }

    @Test
    public void getAllOffers(){
        List actualList = sut.getAllOffers();

        assertNotNull(actualList);
    }

}
