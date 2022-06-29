import com.revature.daos.ItemPostgresSQL;
import com.revature.models.Item;
import com.revature.services.ItemService;
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

import static org.junit.jupiter.api.Assertions.*;

public class ItemTester {

    private static ItemService sut;
    private static Logger log = LogManager.getLogger(ItemTester.class);

    @BeforeAll
    public static void setUp() {
        sut = new ItemService();
    }

    @AfterAll
    public static void tearDown() {

        System.out.println("Stopping all tests.");
        String sql = "delete from item where item_name = 'test insertItem';" +
                "delete from item where item_name = 'test changePrice';" +
                "delete from item where item_name = 'test changeAvailability';" +
                "delete from item where item_name = 'test getAllItems';" +
                "update item set availability = 'true';";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql);
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
    //Inserting an item will work, but test will fail since we have not implemented no duplicate items.
    public void insertItem() {
        Item expected = new Item();
        expected.setPrice(0f);
        expected.setName("test insertItem");
        expected.setAvailable(true);
        String sql = "insert into item (item_name, price, availability) values ('test insertItem', '0', 'false');";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql);
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        Item actual = sut.getItem(expected);
        //have to compare by name as id will be change in the database.
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    //Price is changed, but test fails due to the item not being updated
    public void changePrice() {
        Item expected = new Item();
        expected.setPrice(0f);
        expected.setName("test changePrice");
        expected.setAvailable(true);
        float expectedPrice = 1000;

        String sql = "insert into item (item_name, price, availability) values ('test changePrice', '0', 'false');";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql);
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        sut.changePrice(expected,expectedPrice);
        float actualPrice = expected.getPrice();
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    //Same thing as the test above, but with availability
    public void changeAvailability() {
        Item expected = new Item();
        expected.setPrice(0f);
        expected.setName("test changeAvailability");
        expected.setAvailable(true);


        String sql = "insert into item (item_name, price, availability) values ('test changeAvailability', '0', 'false');";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql);
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }
        sut.changeAvailability(expected, false);
        assertFalse(expected.isAvailable());

    }

    @Test
    public void getItemById(){
        int id = 5;
        String expectedName = "Yamato";
        String actual = "";

        Item i = sut.getItemById(id);
        actual = i.getName();

        assertEquals(expectedName, actual);
    }

    @Test
    public void getAllItems(){

        String sql = "update item set availability = 'false' where availability = 'true';" +
                "insert into item (item_name, price, availability) values ('test getAllItems','7','true');";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql);
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        assertNotNull(sut.getAllItems());
    }

    @Test
    public void removeItem(){
        Item i = new Item();
        i.setName("test removeItem");
        i.setAvailable(false);
        i.setPrice(7f);

        String sql = "insert into item (item_name, price, availability) values ('test removeItem','7','false');";
        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            c.createStatement().executeUpdate(sql);
        }catch (SQLException e) {
            log.error("SQL exception was thrown: " + e.fillInStackTrace());
        } catch (IOException e) {
            log.error("File with credentials not found.");
        }

        sut.removeItem(i);

        assertNull(sut.getItem(i));
    }
}
