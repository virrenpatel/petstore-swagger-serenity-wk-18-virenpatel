package io.swagger.petstore.petcurdtest;

import io.restassured.response.ValidatableResponse;
import io.swagger.petstore.testbase.TestBase;
import io.swagger.petstore.userinfo.UserSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static io.swagger.petstore.utils.TestUtils.getRandomDigits;
import static io.swagger.petstore.utils.TestUtils.getRandomValue;
@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {

    static int id = getRandomDigits();
    static String username = "manan" + getRandomValue();
    static String firstName = "Manan";
    static String lastName = "Shah";
    static String email = username + "@gmail.com";
    static String password = "abc123" + getRandomValue();
    static String phone = "020221340" + getRandomDigits();
    static int userStatus = 1;

    @Steps
    UserSteps userSteps;

    @Title("This method will create a new user record")
    @Test
    public void test001() {
        userSteps.createUserByUserName(id, username, firstName, lastName, email, password, phone, userStatus);
    }

    @Title("This method will get and verify existing user record")
    @Test
    public void test002() {
        ValidatableResponse response = userSteps.getUserByUserName(username).statusCode(200);
        HashMap<String, ?> newUsername = response.extract().path("");
        Assert.assertTrue(newUsername.containsValue(username));
    }

    @Title("This method will update and verify an existing user record")
    @Test
    public void test003() {
        lastName = "Shah";
        ValidatableResponse response = userSteps.updateUserByUserName(id, username, firstName, lastName, email, password, phone, userStatus);
        HashMap<String, ?> update = response.extract().path("");
        Integer newId = new Integer(id);
        String id = newId.toString();
        Assert.assertTrue(update.containsValue(id));
    }

    @Title("This method will delete and verify an existing user record")
    @Test
    public void test004() {
        userSteps.deleteUserByUserName(username);
        userSteps.getUserByUserName(username).statusCode(404);
    }
}
