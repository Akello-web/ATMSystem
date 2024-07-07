package kz.projects.atmSystem.controllers;

import kz.projects.atmSystem.model.MyUserDetails;
import kz.projects.atmSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountsController {

  private final UserService userService;

  @GetMapping("/balance")
  public Double getHelloUser(){
    MyUserDetails currentUser = userService.getCurrentSessionUser();
    return currentUser.getUser().getBalance();
  }

  @GetMapping(value = "/check-user")
  public MyUserDetails getCurrentUser(){
    return userService.getCurrentSessionUser();
  }

}
