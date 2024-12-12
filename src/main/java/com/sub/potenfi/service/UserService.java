package com.sub.potenfi.service;

import com.sub.potenfi.dto.UserDTO;
import com.sub.potenfi.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userDao;

    public void registerUser(UserDTO userDto) {
        userDao.registerUser(userDto); // DAO 호출
    }
    
    public void deleteUser(String userId) {
        try {
            int rowsAffected = userDao.deleteUser(userId);
            if (rowsAffected == 0) {
                throw new RuntimeException("User not found: " + userId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }

	public void updateUserNickname(String userId, String newNickname) {
        UserDTO user = userDao.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found: " + userId);
        }
        user.setUserName(newNickname);
        userDao.updateUser(user);
	}

	public void updateUserName(String userId, String newUserName) {
	    if (userId == null || userId.isEmpty()) {
	        throw new IllegalArgumentException("User ID cannot be null or empty");
	    }

	    userDao.updateUserName(userId, newUserName);
		
	}
}
