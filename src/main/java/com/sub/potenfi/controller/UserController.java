package com.sub.potenfi.controller;

import com.sub.potenfi.dto.UpdateBudgetRequest;
import com.sub.potenfi.dto.UserDTO;
import com.sub.potenfi.service.UserService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody UserDTO userDto) {
        if (userDto.getUserId() == null || userDto.getUserId().isEmpty()) {
            throw new RuntimeException("UserId is required.");
        }
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
    
    @PostMapping("/edit-name")
    public String editUserProfile(@RequestParam String userId, @RequestParam String newUserName) {
        userService.updateUserName(userId, newUserName);
        return "UserName updated successfully";
    }
    
    // Budget 업데이트 API
    @PostMapping("/update-budget")
    public ResponseEntity<String> updateBudget(@RequestBody UpdateBudgetRequest request) {
        try {
            // Budget 업데이트 요청 처리
            userService.updateBudget(request.getUserId(), request.getBudget());
            return ResponseEntity.ok("Budget updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update budget: " + e.getMessage());
        }
    }
}
