import com.revature.daos.SystemPostgreSQL;
import com.revature.models.Item;
import com.revature.models.User;
import com.revature.services.ItemService;
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
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTester {

    private static SystemService sut;
    private static UserService us;
    private static ItemService is;
    private static Logger log = LogManager.getLogger(SystemTester.class);

    @BeforeAll
    public static void setUp() {
        sut = new SystemService();
        us = new UserService();
        is = new ItemService();
        String sql = "insert into item (item_name, price, availability) values ('test system', '0', 'true');" +
                "insert into customer (username, password) values ('test', 'pass')";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql);
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }
    }

    @AfterAll
    public static void tearDown() {

        System.out.println("Stopping all tests.");
        String sql = "delete from offer where amount = '0';" +
                "delete from payment where balance = '0';" +
                "delete from owned_items where item_id = ?" +
                "delete from item where item_name = 'test system';" +
                "delete from customer where username = 'test';";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);

            Item i = is.getByItemName("test system");
            ps.setInt(1, i.getId());

            ps.executeUpdate();
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }
    }

    @BeforeEach
    public void before() {
        System.out.println("Starting new test.");
    }

    @Test
    public void updateOwnership(){
        User testUser = us.getByUsername("test");
        Item testItem = is.getByItemName("test system");
        float amount = 0f;


        sut.updateOwnership(testUser, testItem, amount);

        User actualUser = us.getUser(testUser);

        assertNotNull(actualUser.getOwnedItems());
    }

    @Test
    public void rejectOffers(){
        Item i = is.getByItemName("test system");
        int itemId = i.getId();
        User u = us.getByUsername("test");
        int userId = u.getUserId();
        float amount = 0f;
        String expected = "rejected";
        String actual = "";

        String sql = "insert into offer (customer_id, item_id, amount) values (?,?,?)" ;
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1,userId);
            ps.setInt(2,itemId);
            ps.setFloat(3,amount);

            ps.executeUpdate();

        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        sut.rejectOtherOffers(i);

        String sql2 = "select * from offer where item_id = ? and accepted = 'rejected';";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql2);

            ps.setInt(1,itemId);

            ResultSet rs = ps.executeQuery();

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
        User u = us.getByUsername("test");
        Item i = is.getByItemName("test system");
        int userId = u.getUserId();
        int itemId = i.getId();
        float expectedDue = 0f;


        String sql = "insert into payment (customer_id, item_id, balance) values (?, ?, ?);";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1,userId);
            ps.setInt(2,itemId);
            ps.setFloat(3,expectedDue);

            ps.executeQuery();

        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        float due = sut.getWeeklyPayment(u);

        assertEquals(expectedDue,due);
    }

    @Test
    public void getItemPayment(){
        User u = us.getByUsername("test");
        Item i = is.getByItemName("test system");
        int userId = u.getUserId();
        int itemId = i.getId();
        float expectedDue = 0f;


        String sql = "insert into payment (customer_id, item_id, balance) values (?, ?, ?);";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1,userId);
            ps.setInt(2,itemId);
            ps.setFloat(3,expectedDue);

            ps.executeQuery();

        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        float due = sut.getItemPayment(u,i);

        assertEquals(expectedDue,due);
    }

    @Test
    public void getItemOffer(){
        User u = us.getByUsername("test");
        Item i = is.getByItemName("test system");
        int userId = u.getUserId();
        int itemId = i.getId();
        float expectedOffer = 0f;


        String sql = "insert into offer (customer_id, item_id, amount) values (?, ?, ?);";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1,userId);
            ps.setInt(2,itemId);
            ps.setFloat(3,expectedOffer);

            ps.executeQuery();

        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        float actualOffer = sut.getItemPayment(u,i);

        assertEquals(expectedOffer, actualOffer);
    }

    @Test
    public void isOfferAccepted(){
        User u = us.getByUsername("test");
        Item i = is.getByItemName("test system");
        int userId = u.getUserId();
        int itemId = i.getId();
        float expectedOffer = 0f;
        boolean expectedAcceptance = true;


        String sql = "insert into offer (customer_id, item_id, amount) values (?, ?, ?);" +
                "update offer set accepted = 'accepted' where item_id = ?";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1,userId);
            ps.setInt(2,itemId);
            ps.setFloat(3,expectedOffer);
            ps.setInt(4,itemId);

            ps.executeQuery();

        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        boolean actualAcceptance = sut.isOfferAccepted(u,i);

        assertEquals(expectedAcceptance, actualAcceptance);
    }

    @Test
    public void getAllOffers(){
        List actualList = sut.getAllOffers();

        assertNotNull(actualList);
    }

}
