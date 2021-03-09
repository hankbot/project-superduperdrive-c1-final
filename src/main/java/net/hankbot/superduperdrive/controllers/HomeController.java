package net.hankbot.superduperdrive.controllers;

import net.hankbot.superduperdrive.models.SuperFile;
import net.hankbot.superduperdrive.models.SuperNote;
import net.hankbot.superduperdrive.services.AuthenticationService;
import net.hankbot.superduperdrive.services.FileService;
import net.hankbot.superduperdrive.services.NoteService;
import net.hankbot.superduperdrive.services.UserService;
import org.h2.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class HomeController {

  private UserService userService;
  private FileService fileService;
  private NoteService noteService;
  private AuthenticationService authenticationService;

  private Logger logger = LoggerFactory.getLogger(HomeController.class);

  public HomeController(UserService userService, FileService fileService, NoteService noteService, AuthenticationService authenticationService) {
    this.userService = userService;
    this.fileService = fileService;
    this.noteService = noteService;
    this.authenticationService = authenticationService;
  }

  @GetMapping("/home")
  public String displayHome(@ModelAttribute("note") SuperNote note, Principal principal, Model model) {
    Integer currentUserId = userService.lookupUserId(principal.getName());

    // Retrieve file list for user
    ArrayList<SuperFile> fileList = fileService.userFileList(currentUserId);
    model.addAttribute("fileList", fileList);

    // Notes list
    ArrayList<SuperNote> noteList = noteService.userNoteList(currentUserId);
    model.addAttribute("noteList", noteList);


    // Credentials list

    return "home";
  }

  @PostMapping("/home-save-note")
  public String processUpload(@ModelAttribute("note") SuperNote note, RedirectAttributes redirectAttributes, Principal principal, Model model) {
    logger.info("Begin note save");

    String message = "There was an error saving the note";

    Integer currentUserId = userService.lookupUserId(principal.getName());
    boolean noteResult = false;

    if (note.getNoteTitle().isEmpty()) {
      noteResult = false;
      message = "Note was not saved, Note title is required";
    }
    else if (note.getNoteId() == null) {
      noteResult = noteService.addNoteForUserId(currentUserId, note);
    } else {
      noteResult = noteService.updateNoteForUserId(currentUserId, note);
    }

    logger.info("noteResult: " + noteResult);

    if (noteResult) {
      message = "The note was saved";
    }

    redirectAttributes.addFlashAttribute("message", message);

    return "redirect:/home#nav-notes-tab";
  }

  @PostMapping("/home-upload-file")
  public String processUpload(@RequestParam("fileUpload") MultipartFile fileUpload, RedirectAttributes redirectAttributes, Principal principal) {
    logger.info("Begin upload");

    if (fileUpload.isEmpty()) {
      redirectAttributes.addFlashAttribute("message", "There was a error uploading the file");
      return "redirect:/home";
    }

    Integer currentUserId = userService.lookupUserId(principal.getName());

    // create message about file uploading
    String message = "There was an error uploading the file";

    // check for duplicate file names
    boolean duplicateFileNameResult = fileService.fileExistsForNameWithUserId(fileUpload.getOriginalFilename(), currentUserId);

    if (duplicateFileNameResult) {
      message = "The file was not uploaded, there is already a file with that name";
    }
    else {
      // Pass the fileUpload to the file service
      boolean uploadResult = fileService.addFileForUserId(currentUserId, fileUpload);

      logger.info("Upload result: " + uploadResult);

      if (uploadResult) {
        // positive
        message = "The file was uploaded";
      }
    }

    redirectAttributes.addFlashAttribute("message", message);

    return "redirect:/home";
  }

  @GetMapping(value = "/home-download-file")
  public void  downloadFile(@RequestParam Integer fileId, HttpServletResponse  response) throws IOException {
    SuperFile file = fileService.fileForId(fileId);
    response.setContentType(file.getContentType());
    response.setContentLength(Integer.parseInt(file.getFileSize()));
    response.setHeader("Content-Disposition", "inline; filename=" + file.getFilename());
    InputStream fileDataStream = new ByteArrayInputStream(file.getFileData());
    IOUtils.copy(fileDataStream, response.getOutputStream());
  }

  @GetMapping(value = "/home-view-image")
  public void viewImageFile(@RequestParam Integer fileId, HttpServletResponse  response) throws IOException {
    SuperFile file = fileService.fileForId(fileId);
    response.setContentType(file.getContentType());
    response.setContentLength(Integer.parseInt(file.getFileSize()));
    InputStream fileDataStream = new ByteArrayInputStream(file.getFileData());
    IOUtils.copy(fileDataStream, response.getOutputStream());
  }

  // THIS REALLY SHOULD NOT BE A GET
  @GetMapping(value = "/home-delete-file")
  public String processDeleteFile(@RequestParam Integer fileId, RedirectAttributes redirectAttributes, Principal principal) {
    logger.info("Begin deletion");

    boolean deleteResult = fileService.deleteFile(fileId);

    logger.info("Delete result: " + deleteResult);

    // create message about file deletion
    String message = "There was an error deleting the file";
    if (deleteResult) {
      // positive
      message = "The file was deleted";
    }

    redirectAttributes.addFlashAttribute("message", message);

    return "redirect:/home";
  }

  // THIS REALLY SHOULD NOT BE A GET
  @GetMapping(value = "/home-delete-note")
  public String processDeleteNote(@RequestParam Integer noteId, RedirectAttributes redirectAttributes, Principal principal) {
    logger.info("Begin deletion");
    Integer currentUserId = userService.lookupUserId(principal.getName());

    boolean deleteResult = noteService.deleteNote(noteId, currentUserId);

    logger.info("Delete result: " + deleteResult);

    // message about deletion
    String message = "There was an error deleting the note";
    if (deleteResult) {
      // positive
      message = "The note was deleted";
    }

    redirectAttributes.addFlashAttribute("message", message);

    return "redirect:/home#nav-notes-tab";
  }
}
