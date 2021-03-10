package net.hankbot.superduperdrive;

import net.hankbot.superduperdrive.models.SuperNote;
import net.hankbot.superduperdrive.pageobjects.HomePage;
import net.hankbot.superduperdrive.pageobjects.LoginPage;
import net.hankbot.superduperdrive.pageobjects.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NoteTests extends TestBase {

  @Test
  public void registeredUserCanCreateNote() {
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

    // Navigate to Notes
    HomePage homePage = new HomePage(driver);
    homePage.getNotesTab().click();

    // Create Note
    homePage.getAddNoteButton().click();

    String noteTitle = UUID.randomUUID().toString().substring(0,5);
    String noteDescription = UUID.randomUUID().toString().substring(0,10);

    homePage.setModalNoteTitle(noteTitle);
    homePage.setModalNoteDescription(noteDescription);
    homePage.getSaveNoteButton().click();

    // Find Note in list
    HashMap<Integer, SuperNote> noteMap = homePage.getNoteMap();

    boolean foundNote = false;
    for (Map.Entry<Integer, SuperNote> noteEntry: noteMap.entrySet()) {
      if (noteEntry.getValue().getNoteTitle().equals(noteTitle)
          && noteEntry.getValue().getNoteDescription().equals(noteDescription)) {
        foundNote = true;
        break;
      }
    }
    Assertions.assertTrue(foundNote);
  }

  @Test
  public void registeredUserCanEditNote() {
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

    // Navigate to Notes
    HomePage homePage = new HomePage(driver);
    homePage.getNotesTab().click();

    // Create Note
    homePage.getAddNoteButton().click();

    String noteTitle = UUID.randomUUID().toString().substring(0,5);
    String noteDescription = UUID.randomUUID().toString().substring(0,10);

    homePage.setModalNoteTitle(noteTitle);
    homePage.setModalNoteDescription(noteDescription);
    homePage.getSaveNoteButton().click();

    // Find Note in list and click edit button
    String noteId = "";

    HashMap<Integer, SuperNote> noteMap = homePage.getNoteMap();
    for (Map.Entry<Integer, SuperNote> noteEntry: noteMap.entrySet()) {
      if (noteEntry.getValue().getNoteTitle().equals(noteTitle)
          && noteEntry.getValue().getNoteDescription().equals(noteDescription)) {
        noteId = noteEntry.getKey().toString();
        String editButtonId = "edit-note-button-" + noteId;
        driver.findElement(By.id(editButtonId)).click();
        break;
      }
    }

    // Edit the note
    String newNoteTitle = UUID.randomUUID().toString().substring(0,5);
    String newNoteDescription = UUID.randomUUID().toString().substring(0,10);

    homePage.setModalNoteTitle(newNoteTitle);
    homePage.setModalNoteDescription(newNoteDescription);
    homePage.getSaveNoteButton().click();

    // Confirm new values in the list
    String foundTitle = driver.findElement(By.id("note-list-title-" + noteId)).getText();
    String foundDescription = driver.findElement(By.id("note-list-description-" + noteId)).getText();

    Assertions.assertEquals(newNoteTitle, foundTitle);
    Assertions.assertEquals(newNoteDescription, foundDescription);
  }

  @Test
  public void registeredUserCanDeleteNote() {
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

    // Navigate to Notes
    HomePage homePage = new HomePage(driver);
    homePage.getNotesTab().click();

    // Create Note
    homePage.getAddNoteButton().click();

    String noteTitle = UUID.randomUUID().toString().substring(0,5);
    String noteDescription = UUID.randomUUID().toString().substring(0,10);

    homePage.setModalNoteTitle(noteTitle);
    homePage.setModalNoteDescription(noteDescription);
    homePage.getSaveNoteButton().click();

    // Find Note in list and click delete button
    String noteId = "";

    HashMap<Integer, SuperNote> noteMap = homePage.getNoteMap();
    for (Map.Entry<Integer, SuperNote> noteEntry: noteMap.entrySet()) {
      if (noteEntry.getValue().getNoteTitle().equals(noteTitle)
          && noteEntry.getValue().getNoteDescription().equals(noteDescription)) {
        noteId = noteEntry.getKey().toString();
        String deleteButtonId = "delete-note-button-" + noteId;
        driver.findElement(By.id(deleteButtonId)).click();
        break;
      }
    }

    // Confirm note is no longer in the list
    HashMap<Integer, SuperNote> refreshedNoteMap = homePage.getNoteMap();
    boolean foundNote = refreshedNoteMap.containsKey(Integer.parseInt(noteId));

    Assertions.assertFalse(foundNote);
  }

}
