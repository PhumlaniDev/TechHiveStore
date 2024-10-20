package com.phumlanidev.techhivestore.controller;

import com.phumlanidev.techhivestore.config.JwtResponse;
import com.phumlanidev.techhivestore.dto.LoginDto;
import com.phumlanidev.techhivestore.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> comment </p>.
 */
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  /**
   * <p> comment </p>.
   */
//  @PostMapping("/register")
//  public ResponseEntity<UserDto> register(@RequestBody UserDto userDTO) {
//    UserDto registeredUser = authService.registerUser(userDTO);
//    return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
//  }

  /**
   * <p> comment </p>.
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
    String token = authService.login(loginDto);
    return ResponseEntity.ok(new JwtResponse(token));
  }
}
