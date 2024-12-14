package com.sub.potenfi.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sub.potenfi.dto.UserDTO;

@Mapper
public interface UserMapper {

    UserDTO getUserById(String userId);

    int registerUser(UserDTO userDto);

    int updateUser(UserDTO userDto);

    int deleteUser(String userId);

	void updateUserName(String userId, String newUserName);

	static void insertSubscription(Map<String, Object> subscription) {
		// TODO Auto-generated method stub
		
	}

	void updateUserBudget(@Param("userId") String userId, @Param("budget") int budget);

}
