package com.sub.potenfi.mapper;

import com.sub.potenfi.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDTO getUserById(String userId);

    int registerUser(UserDTO userDto);

    int updateUser(UserDTO userDto);

    int deleteUser(String userId);

	void updateUserName(String userId, String newUserName);

}
