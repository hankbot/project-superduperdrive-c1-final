package net.hankbot.superduperdrive.pageobjects;

import net.hankbot.superduperdrive.models.SuperCredential;
import net.hankbot.superduperdrive.models.SuperNote;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class HomePage {

  private WebDriver driver;

  @FindBy(id = "logout-button")
  private WebElement logoutButton;

  @FindBy(id = "add-note-button")
  private WebElement addNoteButton;

  @FindBy(id = "save-note-button")
  private WebElement saveNoteButton;

  @FindBy(id = "note-title")
  private WebElement inputNoteTitle;

  @FindBy(id = "note-description")
  private WebElement inputNoteDescription;

  @FindBy(id = "nav-notes-tab")
  private WebElement notesTab;

  @FindBy(id = "cred-url")
  private WebElement inputCredUrl;

  @FindBy(id = "cred-username")
  private WebElement inputCredUsername;

  @FindBy(id = "cred-password")
  private WebElement inputCredPassword;

  @FindBy(id = "nav-credentials-tab")
  private WebElement credentialsTab;

  @FindBy(id = "add-cred-button")
  private WebElement addCredButton;

  @FindBy(id = "save-cred-button")
  private WebElement saveCredentialButton;


  public static final String PATH = "/home";

  public HomePage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public WebElement getInputCredPassword() {
    return inputCredPassword;
  }

  public WebElement getNotesTab() {
    return notesTab;
  }

  public WebElement getCredentialsTab() {
    return credentialsTab;
  }

  public WebElement getLogoutButton() {
    return logoutButton;
  }

  public WebElement getAddNoteButton() {
    return addNoteButton;
  }

  public WebElement getSaveNoteButton() {
    return saveNoteButton;
  }

  public WebElement getAddCredButton() {
    return addCredButton;
  }

  public WebElement getSaveCredentialButton() {
    return saveCredentialButton;
  }

  public void setModalNoteTitle(String title) {
    this.inputNoteTitle.clear();
    this.inputNoteTitle.sendKeys(title);
  }

  public void setModalNoteDescription(String description) {
    this.inputNoteDescription.clear();
    this.inputNoteDescription.sendKeys(description);
  }

  public String getModalNoteTitle() {
    return this.inputNoteTitle.getText();
  }

  public String getModalNoteDescription() {
    return this.inputNoteDescription.getText();
  }

  public String getModalCredUrl() {
    return this.inputCredUrl.getText();
  }

  public String getModalCredUsername() {
    return this.inputCredUsername.getText();
  }

  public String getModalCredPassword() {
    return this.inputCredPassword.getText();
  }

  public void setModalCredUrl(String url) {
    this.inputCredUrl.clear();
    this.inputCredUrl.sendKeys(url);
  }

  public void setModalCredPassword(String password) {
    this.inputCredPassword.clear();
    this.inputCredPassword.sendKeys(password);
  }

  public void setModalCredUsername(String username) {
    this.inputCredUsername.clear();
    this.inputCredUsername.sendKeys(username);
  }


  /**
   * Creates a map of partially populated Note objects based on
   * info available from the onscreen Notes listing
   *
   * @return HashMap of SuperNote objects keyed on id
   */
  public HashMap<Integer, SuperNote> getNoteMap() {
    ArrayList<WebElement> noteTitleEls = new ArrayList<>(driver.findElements(By.cssSelector("#note-list td.note-list-title")));
    ArrayList<WebElement> noteDescriptionEls = new ArrayList<>(driver.findElements(By.cssSelector("#note-list td.note-list-description")));
    ArrayList<WebElement> noteEditButtons = new ArrayList<>(driver.findElements(By.cssSelector("#note-list button")));

    HashMap<Integer, SuperNote> noteMap = new HashMap<>();

    // Assumes title cannot be empty
    for (int i = 0; i < noteTitleEls.size(); i++) {
      noteMap.put(
          Integer.parseInt(noteEditButtons.get(i).getAttribute("data-id")),
          new SuperNote(
              Integer.parseInt(noteEditButtons.get(i).getAttribute("data-id")),
              noteTitleEls.get(i).getText(),
              noteDescriptionEls.get(i).getText(),
              null
          ));
    }

    return noteMap;
  }

  /**
   * Creates a map of partially populated Cred objects based on
   * info available from the onscreen Cred listing
   *
   * @return HashMap of SuperCredential objects keyed on id
   */
  public HashMap<Integer, SuperCredential> getCredMap() {
    ArrayList<WebElement> credUrlEls = new ArrayList<>(driver.findElements(By.cssSelector("#cred-list td.cred-list-url")));
    ArrayList<WebElement> credUsernameEls = new ArrayList<>(driver.findElements(By.cssSelector("#cred-list td.cred-list-username")));
    ArrayList<WebElement> credPasswordEls = new ArrayList<>(driver.findElements(By.cssSelector("#cred-list td.cred-list-password")));
    ArrayList<WebElement> credEditButtons = new ArrayList<>(driver.findElements(By.cssSelector("#cred-list button")));

    HashMap<Integer, SuperCredential> credMap = new HashMap<>();

    // Assumes url cannot be empty
    for (int i = 0; i < credUrlEls.size(); i++) {
      credMap.put(
          Integer.parseInt(credEditButtons.get(i).getAttribute("data-id")),
          new SuperCredential(
              Integer.parseInt(credEditButtons.get(i).getAttribute("data-id")),
              credUrlEls.get(i).getText(),
              credUsernameEls.get(i).getText(),
              null,
              credPasswordEls.get(i).getText(),
              null
          ));
    }

    return credMap;
  }

}
