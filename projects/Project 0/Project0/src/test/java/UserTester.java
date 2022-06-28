import com.revature.daos.UserPostgreSQL;
import com.revature.models.Item;
import com.revature.models.User;
import com.revature.services.SystemService;
import com.revature.services.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTester {

    private static UserService sut;
    private static SystemService sys;

    @BeforeAll
    public static void setUp() {
        sut = new UserService();
        sys = new SystemService();
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
    public void createAndGetUser(){
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setPassword("passwd");
        testUser.setEmployee(false);

        sut.createUser(testUser);
        User actualUser = sut.getUser(testUser);
        User expectedUser = new User();
        expectedUser.setUserId(18);
        expectedUser.setUsername("test");
        expectedUser.setPassword("passwd");
        expectedUser.setEmployee(false);

        assertEquals(expectedUser, actualUser);

    }

    @Test
    public void getUserById(){
        int id = 18;
        User expectedUser = new User();
        expectedUser.setUserId(18);
        expectedUser.setUsername("test");
        expectedUser.setPassword("passwd");
        expectedUser.setEmployee(false);

        User acutalUser = sut.getUserById(id);

        assertEquals(expectedUser, acutalUser);
    }

    @Test
    public void getByUsername(){
        String username = "test";
        User expectedUser = new User();
        expectedUser.setUserId(18);
        expectedUser.setUsername("test");
        expectedUser.setPassword("passwd");
        expectedUser.setEmployee(false);

        User acutalUser = sut.getByUsername(username);

        assertEquals(expectedUser, acutalUser);
    }

    @Test
    public void getOwnedItems(){
        List<Item> expectedList = new ArrayList<>();
        User expectedUser = new User();
        expectedUser.setUserId(18);
        expectedUser.setUsername("test");
        expectedUser.setPassword("passwd");
        expectedUser.setEmployee(false);
        expectedUser.setOwnedItems(expectedList);

        List<Item> actualList = sut.getOwnedItems(expectedUser);

        assertEquals(expectedList, actualList);
    }

    @Test
    public void makeOffer(){
        User expectedUser = new User();
        expectedUser.setUserId(18);
        expectedUser.setUsername("test");
        expectedUser.setPassword("passwd");
        expectedUser.setEmployee(false);

        Item expectedItem = new Item();
        expectedItem.setId(17);
        expectedItem.setName("orange");
        expectedItem.setPrice(5.35f);
        expectedItem.setAvailable(true);

        float expectedAmount = 10f;

        sut.makeOffer(expectedUser,expectedItem,expectedAmount);
        float actualAmount = sys.getItemOffer(expectedUser,expectedItem);

        assertEquals(expectedAmount, actualAmount);
    }

    @Test
    public void acceptOffer(){
        User expectedUser = new User();
        expectedUser.setUserId(18);
        expectedUser.setUsername("test");
        expectedUser.setPassword("passwd");
        expectedUser.setEmployee(false);

        Item expectedItem = new Item();
        expectedItem.setId(17);
        expectedItem.setName("orange");
        expectedItem.setPrice(5.35f);
        expectedItem.setAvailable(true);

        boolean expectedAccepted = true;

        sut.acceptOffer(expectedUser, expectedItem);
        boolean acutalAccepted = sys.isOfferAccepted(expectedUser, expectedItem);

        assertEquals(expectedAccepted, acutalAccepted);
    }

}
