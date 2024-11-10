package com.phumlanidev.techhivestore.controller;

import com.phumlanidev.techhivestore.auth.AuthService;
import com.phumlanidev.techhivestore.auth.ResponseDto;
import com.phumlanidev.techhivestore.config.JwtResponse;
import com.phumlanidev.techhivestore.constant.Constant;
import com.phumlanidev.techhivestore.dto.LoginDto;
import com.phumlanidev.techhivestore.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Comment: this is the placeholder for documentation.
 */
@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  /**
   * Comment: this is the placeholder for documentation.
   */
  @PostMapping("/register")
  public ResponseEntity<ResponseDto> register(@RequestBody UserDto userDto) {
    authService.registerUser(userDto);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(Constant.STATUS_CODE_CREATED,
                    "You have successfully Registered."));
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
    String token = authService.login(loginDto);
    return ResponseEntity.ok(new JwtResponse(token));
  }
}
