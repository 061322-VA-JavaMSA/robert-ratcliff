import com.revature.daos.UserPostgreSQL;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTester {

    private static UserService sut;
    private static SystemService sys;
    private static ItemService is;
    private static Logger log = LogManager.getLogger(UserTester.class);
    private static int ownedItemsId;
    private static int makeOfferId;
    private static int acceptOfferId;

    @BeforeAll
    public static void setUp() {
        sut = new UserService();
        sys = new SystemService();
        is = new ItemService();
    }

    @AfterAll
    public static void tearDown() {

        System.out.println("Stopping all tests.");
        String sql = "delete from owned_items where customer_id = ?;" +
                "delete from offer where customer_id = ?;" +
                "delete from offer where customer_id = ?;";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1, ownedItemsId);
            ps.setInt(2, makeOfferId);
            ps.setInt(3, acceptOfferId);

            ps.executeUpdate();
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        String sql2 = "delete from customer where username in ('test getUser','test getId', 'test getUsername'," +
                " 'test getOwnedItems', 'test makeOffer', 'test acceptOffer');" +
                "delete from item where item_name in ('test getOwnedItems', 'test makeOffer', " +
                "'test acceptOffer');";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql2);
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
    public void createAndGetUser(){
        User testUser = new User();
        testUser.setUsername("test getUser");
        testUser.setPassword("passwd");
        testUser.setEmployee(false);

        User actualUser = new User();

        sut.createUser(testUser);

        String sql = "select * from customer where username = 'test getUser';";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            ResultSet rs = c.createStatement().executeQuery(sql);

            if(rs.next()){
                 actualUser.setUserId(rs.getInt("id"));
                 actualUser.setUsername(rs.getString("username"));
                 actualUser.setPassword("password");
            }
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        assertEquals(testUser.getUsername(), actualUser.getUsername());

    }

    @Test
    public void getUserById(){
        int id = -1;
        String sql = "insert into customer (username, password) values ('test getId', 'pass');" +
                "select * from customer where username = 'test getId';";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            ResultSet rs = c.createStatement().executeQuery(sql);

            if(rs.next()){
                id = rs.getInt("id");
            }
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        assertNotEquals(-1, sut.getUserById(id));
    }

    @Test
    public void getByUsername(){

        String sql = "insert into customer (username, password) values ('test getUsername', 'pass');";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql);
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        assertNotNull(sut.getByUsername("test getUsername"));
    }

    @Test
    public void getOwnedItems(){
        String sql = "insert into customer (username, password) values ('test getOwnedItems', 'pass');" +
                "insert into item (item_name, price) values ('test getOwnedItems' , '0');";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql);
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        User expectedUser = sut.getByUsername("test getOwnedItems");
        Item expectedItem = is.getByItemName("test getOwnedItems");
        int userId = expectedUser.getUserId();
        ownedItemsId = expectedUser.getUserId();
        int itemId = expectedItem.getId();

        String sql2 = "insert into owned_items (customer_id, item_id) values (?,?);";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql2);

            ps.setInt(1, userId);
            ps.setInt(2, itemId);
            ps.executeUpdate();
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        List<Item> actualList = sut.getOwnedItems(expectedUser);

        assertNotNull(actualList);
    }

    @Test
    public void makeOffer(){
        String sql = "insert into customer (username, password) values ('test makeOffer', 'pass');" +
                "insert into item (item_name, price) values ('test makeOffer' , '0');";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql);
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        float expectedAmount = 0f;
        User expectedUser = sut.getByUsername("test makeOffer");
        Item expectedItem = is.getByItemName("test makeOffer");
        makeOfferId = expectedUser.getUserId();

        sut.makeOffer(expectedUser,expectedItem,expectedAmount);
        float actualAmount = sys.getItemOffer(expectedUser,expectedItem);

        assertEquals(expectedAmount, actualAmount);
    }

    @Test
    public void acceptOffer(){
        String sql = "insert into customer (username, password) values ('test acceptOffer', 'pass');" +
                "insert into item (item_name, price) values ('test acceptOffer' , '0');";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql);
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        User testUser = sut.getByUsername("test acceptOffer");
        Item testItem = is.getByItemName("test acceptOffer");
        int userId = testUser.getUserId();
        int itemId = testItem.getId();

        String sql2 = "insert into offer (customer_id, item_id, amount) values (?,?,'0');";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql2);

            ps.setInt(1, userId);
            ps.setInt(2, itemId);
            ps.executeUpdate();
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        boolean expectedAccepted = true;
        acceptOfferId = testUser.getUserId();

        sut.acceptOffer(testUser, testItem);
        boolean acutalAccepted = sys.isOfferAccepted(testUser, testItem);

        assertEquals(expectedAccepted, acutalAccepted);
    }

}
