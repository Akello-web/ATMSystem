package kz.projects.atmSystem.controllers;

import kz.projects.atmSystem.dto.AuthRequest;
import kz.projects.atmSystem.model.User;
import kz.projects.atmSystem.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final UserServiceImpl userService;

  @PostMapping(value = "register")
  public ResponseEntity<User> registerUser(@RequestBody User user){
    return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
  }

  @PostMapping(value = "/login")
  public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest){
    try {
      UserDetails userDetails = userService.loginUser(authRequest.getUsername(), authRequest.getPassword());
      return ResponseEntity.ok(userDetails);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }


}

