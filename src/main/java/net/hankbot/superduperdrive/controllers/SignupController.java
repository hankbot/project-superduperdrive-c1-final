package net.hankbot.superduperdrive.controllers;

import net.hankbot.superduperdrive.models.User;
import net.hankbot.superduperdrive.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

  private UserService userService;
  private Logger logger = LoggerFactory.getLogger(SignupController.class);

  public SignupController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String displaySignup(Model model) {
    return "signup";
  }

  @PostMapping
  public String processSignup(@ModelAttribute User user, Model model) {
    boolean signupSuccess = false;
    boolean signupError = false;
    String signupErrorMessage = "";

    // Check for existing username
    if (userService.lookupUserId(user.getUsername()) > 0) {
      signupError = true;
      signupErrorMessage = "The entered username is not available, please select a different one";
      model.addAttribute("signupError", signupError);
      model.addAttribute("signupErrorMessage", signupErrorMessage);
      logger.info("Duplicate username");
      return "signup";
    }

    // Check for success
    if (userService.addUser(user) == false) {
      signupError = true;

      signupErrorMessage = "You account was not created, please try again";

      model.addAttribute("signupError", signupError);
      model.addAttribute("signupErrorMessage", signupErrorMessage);

      return "signup";
    }

    // Success
    signupSuccess = true;
    model.addAttribute("signupSuccess", signupSuccess);

    return "signup";
  }
}
