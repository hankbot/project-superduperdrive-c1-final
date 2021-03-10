package net.hankbot.superduperdrive;

import net.hankbot.superduperdrive.models.SuperCredential;
import net.hankbot.superduperdrive.pageobjects.HomePage;
import net.hankbot.superduperdrive.pageobjects.LoginPage;
import net.hankbot.superduperdrive.pageobjects.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CredentialTests extends TestBase {

  @Test
  public void registeredUserCanCreateCredential() {
    goToPage(RegistrationPage.PATH);
    RegistrationPage registrationPage = new RegistrationPage(driver);

    // Register a user
    HashMap<String, String> userInfo;
    userInfo = registrationPage.registerUser();

    // Login as user
    goToPage(LoginPage.PATH);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(userInfo);

    Assertions.assertEquals("Home", driver.getTitle());

    // Navigate to Credentials
    HomePage homePage = new HomePage(driver);
    homePage.getCredentialsTab().click();

    // Create Credential
    homePage.getAddCredButton().click();

    String credentialUrl = UUID.randomUUID().toString().substring(0,10);
    String credentialUsername = UUID.randomUUID().toString().substring(0,10);
    String credentialPassword = UUID.randomUUID().toString().substring(0,10);

    homePage.setModalCredUrl(credentialUrl);
    homePage.setModalCredUsername(credentialUsername);
    homePage.setModalCredPassword(credentialPassword);
    homePage.getSaveCredentialButton().click();

    // Find Cred in list
    HashMap<Integer, SuperCredential> credMap = homePage.getCredMap();

    boolean foundCred = false;
    boolean passwordInPlaintext = true;
    boolean passwordIsEmpty = true;

    // Find the cred by matching URL and Username,
    for (Map.Entry<Integer, SuperCredential> credEntry: credMap.entrySet()) {
      if (credEntry.getValue().getUrl().equals(credentialUrl)
          && credEntry.getValue().getUsername().equals(credentialUsername)
      ) {
        // Confirm entered password is not displayed in listing as plaintext
        passwordInPlaintext = credentialPassword.equals(credEntry.getValue().getPassword());
        passwordIsEmpty = credEntry.getValue().getPassword().isEmpty();
        foundCred = true;
        break;
      }
    }

    Assertions.assertTrue(foundCred);
    Assertions.assertFalse(passwordIsEmpty);
    Assertions.assertFalse(passwordInPlaintext);
  }

  @Test
  public void registeredUserCanEditCredential() {
    goToPage(RegistrationPage.PATH);
    RegistrationPage registrationPage = new RegistrationPage(driver);

    // Register a user
    HashMap<String, String> userInfo;
    userInfo = registrationPage.registerUser();

    // Login as user
    goToPage(LoginPage.PATH);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(userInfo);

    Assertions.assertEquals("Home", driver.getTitle());

    // Navigate to Credentials
    HomePage homePage = new HomePage(driver);
    homePage.getCredentialsTab().click();

    // Create Credential
    homePage.getAddCredButton().click();

    String credentialUrl = UUID.randomUUID().toString().substring(0,10);
    String credentialUsername = UUID.randomUUID().toString().substring(0,10);
    String credentialPassword = UUID.randomUUID().toString().substring(0,10);

    homePage.setModalCredUrl(credentialUrl);
    homePage.setModalCredUsername(credentialUsername);
    homePage.setModalCredPassword(credentialPassword);
    homePage.getSaveCredentialButton().click();

    // Find Cred in list
    HashMap<Integer, SuperCredential> credMap = homePage.getCredMap();

    // Find the cred by matching URL and Username and click edit button
    for (Map.Entry<Integer, SuperCredential> credEntry: credMap.entrySet()) {
      if (credEntry.getValue().getUrl().equals(credentialUrl)
          && credEntry.getValue().getUsername().equals(credentialUsername)
      ) {
        String editButtonId = "edit-cred-button-" + credEntry.getValue().getCredentialId();
        driver.findElement(By.id(editButtonId)).click();
        break;
      }
    }

    // Edit the cred Password
    String newPassword = UUID.randomUUID().toString().substring(0,5);

    homePage.setModalCredPassword(newPassword);
    homePage.getSaveCredentialButton().click();

    // Re-open the cred for editing
    HashMap<Integer, SuperCredential> refreshedCredMap = homePage.getCredMap();

    // Find the cred by matching URL and Username and click edit button
    for (Map.Entry<Integer, SuperCredential> credEntry: refreshedCredMap.entrySet()) {
      if (credEntry.getValue().getUrl().equals(credentialUrl)
          && credEntry.getValue().getUsername().equals(credentialUsername)
      ) {
        String editButtonId = "edit-cred-button-" + credEntry.getValue().getCredentialId();
        driver.findElement(By.id(editButtonId)).click();
        break;
      }
    }

    // Confirm new password is visible, delay reading for ajax load
    WebDriverWait wait = new WebDriverWait(driver,7);
    wait.until(ExpectedConditions.attributeToBe(By.id("cred-password"), "value", newPassword));

    Assertions.assertEquals(newPassword, homePage.getInputCredPassword().getAttribute("value"));
  }

  @Test
  public void registeredUserCanDeleteCredential() {
    goToPage(RegistrationPage.PATH);
    RegistrationPage registrationPage = new RegistrationPage(driver);

    // Register a user
    HashMap<String, String> userInfo;
    userInfo = registrationPage.registerUser();

    // Login as user
    goToPage(LoginPage.PATH);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(userInfo);

    Assertions.assertEquals("Home", driver.getTitle());

    // Navigate to Credentials
    HomePage homePage = new HomePage(driver);
    homePage.getCredentialsTab().click();

    // Create Credential
    homePage.getAddCredButton().click();

    String credentialUrl = UUID.randomUUID().toString().substring(0,10);
    String credentialUsername = UUID.randomUUID().toString().substring(0,10);
    String credentialPassword = UUID.randomUUID().toString().substring(0,10);

    homePage.setModalCredUrl(credentialUrl);
    homePage.setModalCredUsername(credentialUsername);
    homePage.setModalCredPassword(credentialPassword);
    homePage.getSaveCredentialButton().click();

    // Find Cred in list
    HashMap<Integer, SuperCredential> credMap = homePage.getCredMap();

    boolean foundCred = false;

    // Find the cred by matching URL and Username and delete it
    for (Map.Entry<Integer, SuperCredential> credEntry: credMap.entrySet()) {
      if (credEntry.getValue().getUrl().equals(credentialUrl)
          && credEntry.getValue().getUsername().equals(credentialUsername)
      ) {
        String deleteButtonId = "delete-cred-button-" + credEntry.getValue().getCredentialId();
        driver.findElement(By.id(deleteButtonId)).click();
        foundCred = true;
        break;
      }
    }

    Assertions.assertTrue(foundCred);

    HashMap<Integer, SuperCredential> refreshedCredMap = homePage.getCredMap();

    // Refresh list after deletion, find the cred by matching URL and Username
    boolean foundDeletedCred = false;
    for (Map.Entry<Integer, SuperCredential> credEntry: refreshedCredMap.entrySet()) {
      if (credEntry.getValue().getUrl().equals(credentialUrl)
          && credEntry.getValue().getUsername().equals(credentialUsername)
      ) {
        foundDeletedCred = true;
        break;
      }
    }

    Assertions.assertFalse(foundDeletedCred);

  }

}
