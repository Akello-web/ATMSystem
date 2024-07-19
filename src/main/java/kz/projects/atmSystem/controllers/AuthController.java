package kz.projects.atmSystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.projects.atmSystem.dto.AuthRequest;
import kz.projects.atmSystem.dto.UpdatePinRequest;
import kz.projects.atmSystem.dto.UserDTO;
import kz.projects.atmSystem.mapper.UserMapper;
import kz.projects.atmSystem.model.User;
import kz.projects.atmSystem.service.UserService;
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

  private final UserService userService;

  @PostMapping(value = "/register")
  @Operation(summary = "Register user", description = "Register a user and add to database")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully registered",
                  content = @Content(schema = @Schema(implementation = UserDetails.class))),
          @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
  })
  public ResponseEntity<UserDTO> registerUser(@RequestBody User user){
    return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
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
      UserDTO user = userService.loginUser(authRequest);
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }

  @GetMapping(value = "/current-user")
  @Operation(summary = "Get current user information",
          description = "Retrieve details of the currently authenticated user")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved current user info",
                  content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
  })
  public ResponseEntity<UserDTO> getCurrentUserInfo(){
    User currentUser = userService.getCurrentSessionUser();
    return new ResponseEntity<>(UserMapper.toDto(currentUser), HttpStatus.OK);
  }

  @PostMapping(value = "/update-pin")
  public ResponseEntity<String> changePin(@RequestBody UpdatePinRequest request){
    return new ResponseEntity<>(userService.updatePassword(request.oldPin(), request.newPin()), HttpStatus.OK);
  }
}

