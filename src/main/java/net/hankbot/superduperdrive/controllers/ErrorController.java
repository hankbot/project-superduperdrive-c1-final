package net.hankbot.superduperdrive.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    model.addAttribute("errorCode", "Unknown Error");

    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());
      model.addAttribute("errorCode", statusCode);
    }

    return "error";
  }

  @Override
  public String getErrorPath() {
    return null;
  }

}
