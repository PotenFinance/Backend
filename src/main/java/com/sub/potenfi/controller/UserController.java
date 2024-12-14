package com.sub.potenfi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sub.potenfi.dto.UpdateBudgetRequest;
import com.sub.potenfi.dto.UserDTO;
import com.sub.potenfi.service.UserService;

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
    public ResponseEntity<Map<String, Object>> editUserProfile(@RequestParam String userId, @RequestParam String newUserName) {
        try {
            userService.updateUserName(userId, newUserName);
            return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "UserName updated successfully"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", 400,
                "message", "Invalid request: " + e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "message", "User not found: " + e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "status", 500,
                "message", "Internal server error: " + e.getMessage()
            ));
        }
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
