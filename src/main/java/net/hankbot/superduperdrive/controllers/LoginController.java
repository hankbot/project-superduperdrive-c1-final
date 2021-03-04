package net.hankbot.superduperdrive.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

  private Logger logger = LoggerFactory.getLogger(LoginController.class);

  @GetMapping
  public String displayLogin(Model model) {
    logger.info("Hello Login");
    return "login";
  }

}
