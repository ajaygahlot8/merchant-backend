package com.payvyne.merchant.rest.controller;

import com.payvyne.merchant.rest.ApiResponse;
import com.payvyne.merchant.rest.model.AuthenticationRequest;
import com.payvyne.merchant.rest.model.AuthenticationResponse;
import com.payvyne.merchant.security.JwtUtil;
import com.payvyne.merchant.security.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final JwtUtil jwtTokenUtil;

  private final MyUserDetailsService userDetailsService;

  public AuthenticationController(
      AuthenticationManager authenticationManager,
      JwtUtil jwtTokenUtil,
      MyUserDetailsService userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsService = userDetailsService;
  }

  @PostMapping(value = "/authenticate")
  public ApiResponse<AuthenticationResponse> createAuthenticationToken(
      @RequestBody AuthenticationRequest authenticationRequest) {

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUsername(), authenticationRequest.getPassword()));

    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String jwt = jwtTokenUtil.generateToken(userDetails);
    return ApiResponse.createSuccessResponse(AuthenticationResponse.builder().jwt(jwt).build());
  }
}
