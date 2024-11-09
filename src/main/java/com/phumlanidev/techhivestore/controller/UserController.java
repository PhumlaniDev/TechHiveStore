package com.phumlanidev.techhivestore.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> comment </p>.
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

  /**
   * <p> comment </p>.
   */
  @GetMapping("/user")
  public String getHelloWorldFromUser() {
    return "Hello World from user";
  }

  /**
   * <p> comment </p>.
   */
  @GetMapping("/admin")
  public String getHelloWorldFromAdmin() {
    return "Hello World from admin";
  }
}
