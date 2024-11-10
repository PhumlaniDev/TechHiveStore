package com.phumlanidev.techhivestore.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Comment: this is the placeholder for documentation.
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

  /**
   * Comment: this is the placeholder for documentation.
   */
  @GetMapping("/user")
  public String getHelloWorldFromUser() {
    return "Hello World from user";
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @GetMapping("/admin")
  public String getHelloWorldFromAdmin() {
    return "Hello World from admin";
  }
}
