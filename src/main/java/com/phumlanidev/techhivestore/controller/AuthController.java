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
 * <p> comment </p>.
 */
@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<ResponseDto> register(@RequestBody UserDto userDTO) {
    authService.registerUser(userDTO);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(Constant.STATUS_CODE_CREATED, "You have successfully Registered."));
  }


  @PostMapping("/login")
  public ResponseEntity<ResponseDto> login(@RequestBody LoginDto loginDto) {
    String token = authService.login(loginDto);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseDto(HttpStatus.OK.toString(), "You have been successfully Authenticated"));
  }
}
