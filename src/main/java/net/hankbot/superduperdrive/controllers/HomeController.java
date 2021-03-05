package net.hankbot.superduperdrive.controllers;

import net.hankbot.superduperdrive.models.SuperFile;
import net.hankbot.superduperdrive.services.AuthenticationService;
import net.hankbot.superduperdrive.services.FileService;
import net.hankbot.superduperdrive.services.UserService;
import org.h2.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
  private AuthenticationService authenticationService;

  private Logger logger = LoggerFactory.getLogger(HomeController.class);

  public HomeController(UserService userService, FileService fileService, AuthenticationService authenticationService) {
    this.userService = userService;
    this.fileService = fileService;
    this.authenticationService = authenticationService;
  }

  @GetMapping("/home")
  public String displayHome(Principal principal, Model model) {
    // Retrieve file list for user
    ArrayList<SuperFile> fileList = fileService.userFileList(
        userService.lookupUserId(
            principal.getName()));

    for (SuperFile file : fileList) {
      logger.info("Filename is: " + file.getFilename());
    }

    model.addAttribute("fileList", fileList);

    return "home";
  }

  @PostMapping("/homeUploadFile")
  public String processUpload(@RequestParam("fileUpload") MultipartFile fileUpload, RedirectAttributes redirectAttributes, Principal principal) {
    logger.info("Begin upload");
    // Pass the fileUpload to the file service
    boolean uploadResult = fileService.addFileForUserId(
        userService.lookupUserId(principal.getName()),
        fileUpload
    );

    logger.info("Upload result: " + uploadResult);

    // create message about file upload
//    redirectAttributes.addFlashAttribute()

    return "redirect:/home";
  }

  @GetMapping(
      value = "/home-download-file",
      produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
  )
  public @ResponseBody byte[] downloadFile(@RequestParam Integer fileId) throws IOException {
    SuperFile file = fileService.fileForId(fileId);
    InputStream fileDataStream = new ByteArrayInputStream(file.getFileData());
    return IOUtils.readBytesAndClose(fileDataStream,0);
  }

  @GetMapping(value = "/home-view-image")
  public void downloadFile(@RequestParam Integer fileId, HttpServletResponse  response) throws IOException {
    SuperFile file = fileService.fileForId(fileId);
    response.setContentType(file.getContentType());
    response.setContentLength(Integer.parseInt(file.getFileSize()));
    InputStream fileDataStream = new ByteArrayInputStream(file.getFileData());
    IOUtils.copy(fileDataStream, response.getOutputStream());
  }

}
