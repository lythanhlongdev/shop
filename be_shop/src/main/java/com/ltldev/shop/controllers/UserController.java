package com.ltldev.shop.controllers;

import com.ltldev.shop.dto.user.LoginDTO;
import com.ltldev.shop.dto.user.UserDTO;
import com.ltldev.shop.models.User;
import com.ltldev.shop.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO request, BindingResult result) {
        try {
//            GetErorrInRequest.errMessage(result);
            if (result.hasErrors() == true) {
                List<String> errMessage = result.getAllErrors()
                        .stream()
                        .map(fieldError -> fieldError.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errMessage);
            }
            if (!request.getPassword().equals(request.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Password dose not match");
            }
            userService.createUser(request);
            return ResponseEntity.ok().body("Register successfully ");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error register " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO request) {
        try {
            return ResponseEntity.ok().body("Login successfully ");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error Login " + e.getMessage());
        }
    }

}
