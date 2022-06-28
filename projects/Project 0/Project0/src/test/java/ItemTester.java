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
    }

    @BeforeEach
    public void before() {
        System.out.println("Starting new test.");
    }

    @Test
    //Inserting an item will work, but test will fail since we have not implemented no duplicate items.
    public void insertItem() {
        String expectedName = "banana";
        float expectedprice = 1.37f;
        Item i = new Item();
        i.setPrice(expectedprice);
        i.setName(expectedName);
        System.out.println("The item to be created is: " + i);
        sut.insertItem(i);
        Item ai = sut.getItem(i);
        System.out.println("The item taken out of database: " + ai);
        assertAll("properties",
                () -> {
                    String actualName = ai.getName();
                    assertNotNull(actualName);

                    assertAll("Item name",
                            () -> assertTrue(expectedName.equals(actualName)));

                    float actualPrice = ai.getPrice();
                    assertAll("Price",
                            () -> assertTrue(expectedprice == actualPrice));
                });
    }

    @Test
    //Price is changed, but test fails due to the item not being updated
    public void changePrice() {
        String expectedName = "banana";
        float expectedPrice = 2.37f;
        Item i = sut.getByItemName(expectedName);
        System.out.println("The item taken out of the database is " + i);
        sut.changePrice(i, expectedPrice);

        float actualPrice = i.getPrice();
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    //Same thing as the test above, but with availability
    public void changeAvailability() {
        String expectedName = "Yucca";
        boolean expectedAvail = true;
        Item i = sut.getByItemName(expectedName);
        System.out.println("The item taken out of the database is " + i);
        sut.changeAvailability(i, expectedAvail);
        boolean actualAvail = i.isAvailable();
        assertEquals(expectedAvail, actualAvail);

    }

    @Test
    public void getItemById(){
        int expectedId = 7;
        String expectedName = "Pepper - Sorrano";
        String actual = "";

        Item i = sut.getItemById(7);
        actual = i.getName();

        assertEquals(expectedName, actual);
    }

    @Test
    public void getAllItems(){

        String sql = "update item set availability = 'false' where availability = 'true';" +
                "insert into item (item_name, price, availability) values ('peach','7','true');";
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
        i.setName("peach");
        i.setAvailable(true);
        i.setPrice(7f);

        String sql = "insert into item (item_name, price, availability) values ('peach','7','true');";
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
