package net.hankbot.superduperdrive.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.UUID;

public class RegistrationPage {

  @FindBy(id = "inputFirstName")
  private WebElement inputFirstName;

  @FindBy(id = "inputLastName")
  private WebElement inputLastName;

  @FindBy(id = "inputUsername")
  private WebElement inputUsername;

  @FindBy(id = "inputPassword")
  private WebElement inputPassword;

  @FindBy(id = "submit-registration")
  private WebElement submitButton;

  @FindBy(css = "div.alert.alert-dark")
  private WebElement message;

  @FindBy(css = "div.alert.alert-danger")
  private WebElement errorMessage;

  public static final String PATH = "/signup";
  public static final String MESSAGE_SIGNUP_SUCCESS = "You successfully signed up! Please continue to the login page.";
  public static final String MESSAGE_SIGNUP_DUPLICATE_USERNAME = "The entered username is not available, please select a different one";

  public RegistrationPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }

  public WebElement getMessage() {
    return message;
  }

  public WebElement getErrorMessage() {
    return errorMessage;
  }

  public HashMap<String, String> registerUser() {
    HashMap<String, String> userInfo = new HashMap<>();

    userInfo.put("username", UUID.randomUUID().toString().substring(0,10));
    userInfo.put("password", UUID.randomUUID().toString().substring(0,10));
    userInfo.put("firstName", UUID.randomUUID().toString().substring(0,10));
    userInfo.put("lastName", UUID.randomUUID().toString().substring(0,10));

    setFirstName(userInfo.get("firstName"));
    setLastName(userInfo.get("lastName"));
    setUsername(userInfo.get("username"));
    setPassword(userInfo.get("password"));

    getSubmitButton().click();

    return userInfo;
  }

  public WebElement getSubmitButton() {
    return submitButton;
  }

  public String getFirstName() {
    return inputFirstName.getText();
  }

  public void setFirstName(String firstName) {
    this.inputFirstName.sendKeys(firstName);
  }

  public String getLastName() {
    return inputLastName.getText();
  }

  public void setLastName(String lastName) {
    this.inputLastName.sendKeys(lastName);
  }

  public String getUsername() {
    return inputUsername.getText();
  }

  public void setUsername(String username) {
    this.inputUsername.sendKeys(username);
  }

  public void setPassword(String password) {
    this.inputPassword.sendKeys(password);
  }
}
