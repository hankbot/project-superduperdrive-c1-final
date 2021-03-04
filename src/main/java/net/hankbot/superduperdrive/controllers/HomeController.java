package net.hankbot.superduperdrive.controllers;

import net.hankbot.superduperdrive.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

  UserService userService;

  private Logger logger = LoggerFactory.getLogger(HomeController.class);

  public HomeController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String displayHome(Model model) {
    logger.info("Hello Home");
    return "home";
  }

}
