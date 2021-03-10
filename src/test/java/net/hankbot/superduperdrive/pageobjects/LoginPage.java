package net.hankbot.superduperdrive.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;

public class LoginPage {

  @FindBy(id = "inputUsername")
  private WebElement inputUsername;

  @FindBy(id = "inputPassword")
  private WebElement inputPassword;

  @FindBy(css = "button.btn.btn-primary")
  private WebElement submitButton;

  @FindBy(css = "div.alert.alert-danger")
  private WebElement errorMessage;

  public static final String PATH = "/login";

  public LoginPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }

    public WebElement getErrorMessage() {
    return errorMessage;
  }

  public WebElement getSubmitButton() {
    return submitButton;
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

  public void login(HashMap<String, String> userInfo) {
    this.setUsername(userInfo.get("username"));
    this.setPassword(userInfo.get("password"));
    this.getSubmitButton().click();
  }

}
