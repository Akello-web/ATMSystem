package kz.projects.atmSystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

  private final UserServiceImpl userService;

  @PostMapping(value = "register")
  @Operation(summary = "Register user", description = "Register a user and add to database")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully registered",
                  content = @Content(schema = @Schema(implementation = UserDetails.class))),
          @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
  })
  public ResponseEntity<User> registerUser(@RequestBody User user){
    return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
  }

  @PostMapping(value = "/login")
  @Operation(summary = "Login user", description = "Authenticate a user and create a session")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                  content = @Content(schema = @Schema(implementation = UserDetails.class))),
          @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
  })
  public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest){
    try {
      UserDetails userDetails = userService.loginUser(authRequest.getUsername(), authRequest.getPassword());
      return ResponseEntity.ok(userDetails);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }
}

