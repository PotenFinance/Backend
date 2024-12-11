package com.sub.potenfi.controller;

import com.sub.potenfi.dto.UserDto;
import com.sub.potenfi.service.UserService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return "User registered successfully";
    }
    
    // 닉네임 수정 API
    @PostMapping("/update-nickname")
    public String updateNickname(@RequestBody Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        String newNickname = requestBody.get("newNickname");

        userService.updateUserNickname(userId, newNickname);
        return "Nickname updated successfully";
    }
}
