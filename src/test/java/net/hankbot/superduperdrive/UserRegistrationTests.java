package net.hankbot.superduperdrive;

import net.hankbot.superduperdrive.pageobjects.HomePage;
import net.hankbot.superduperdrive.pageobjects.LoginPage;
import net.hankbot.superduperdrive.pageobjects.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.UUID;

public class UserRegistrationTests extends TestBase {

  private static final String path = "/signup";

  @Test
  public void getRegistrationPage() {
    goToPage(RegistrationPage.PATH);
    Assertions.assertEquals("Sign Up", driver.getTitle());
  }

  @Test
  public void registerNewUser() {
    goToPage(RegistrationPage.PATH);
    RegistrationPage page = new RegistrationPage(driver);

    page.setFirstName("Test");
    page.setLastName(UUID.randomUUID().toString());
    page.setUsername(UUID.randomUUID().toString());
    page.setPassword(UUID.randomUUID().toString());

    page.getSubmitButton().click();

    Assertions.assertEquals("Login", driver.getTitle());
    Assertions.assertEquals(RegistrationPage.MESSAGE_SIGNUP_SUCCESS, driver.findElement(By.id("registration-success")).getText());

  }

  @Test
  public void registerDuplicateUsername() {
    goToPage(RegistrationPage.PATH);
    RegistrationPage page = new RegistrationPage(driver);

    // Register a user
    HashMap<String, String> userInfo;
    userInfo = page.registerUser();

    Assertions.assertEquals(RegistrationPage.MESSAGE_SIGNUP_SUCCESS, page.getMessage().getText());

    // Begin again
    goToPage(RegistrationPage.PATH);

    // Use same username
    page.setFirstName("Test");
    page.setLastName(UUID.randomUUID().toString());
    page.setUsername(userInfo.get("username"));
    page.setPassword(UUID.randomUUID().toString());

    page.getSubmitButton().click();

    Assertions.assertEquals(RegistrationPage.MESSAGE_SIGNUP_DUPLICATE_USERNAME, page.getErrorMessage().getText());
  }

  public void registerWithMissingFields() {

  }

  @Test
  public void RegisteredUserCanLoginThenLogout() {
    goToPage(RegistrationPage.PATH);
    RegistrationPage registrationPage = new RegistrationPage(driver);

    // Register a user
    HashMap<String, String> userInfo;
    userInfo = registrationPage.registerUser();

    // Login as user
    goToPage(LoginPage.PATH);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.setUsername(userInfo.get("username"));
    loginPage.setPassword(userInfo.get("password"));
    loginPage.getSubmitButton().click();

    Assertions.assertEquals("Home", driver.getTitle());

    // Logout
    HomePage homePage = new HomePage(driver);
    homePage.getLogoutButton().click();

    // Now back at login
    Assertions.assertEquals("Login", driver.getTitle());

    // Try to go home, but redirected to Login
    goToPage(HomePage.PATH);
    Assertions.assertEquals("Login", driver.getTitle());
    Assertions.assertTrue(driver.getCurrentUrl().contains("/login"));

  }

  @Test
  /**
   * Non-authenticated user should be able to access
   * login
   * signup
   * but not home
   */
  public void nonAuthenticatedAccessCheck() {
    goToPage(RegistrationPage.PATH);
    Assertions.assertEquals("Sign Up", driver.getTitle());

    goToPage(LoginPage.PATH);
    Assertions.assertEquals("Login", driver.getTitle());

    // Try to go home, but redirected to Login
    goToPage(HomePage.PATH);
    Assertions.assertEquals("Login", driver.getTitle());
    Assertions.assertTrue(driver.getCurrentUrl().contains("/login"));
  }

}
