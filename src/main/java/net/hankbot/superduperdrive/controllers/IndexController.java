package net.hankbot.superduperdrive.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

  private Logger logger = LoggerFactory.getLogger(IndexController.class);

  @GetMapping
  public String displayHome(Model model) {
    return "redirect:/home";
  }

}
