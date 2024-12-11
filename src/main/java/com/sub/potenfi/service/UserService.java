package com.sub.potenfi.service;

import com.sub.potenfi.dao.UserDao;
import com.sub.potenfi.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void registerUser(UserDto userDto) {
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
        UserDto user = userDao.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found: " + userId);
        }
        user.setUser_name(newNickname);
        userDao.updateUser(user);
	}
}
