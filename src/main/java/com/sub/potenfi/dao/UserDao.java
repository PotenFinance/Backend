package com.sub.potenfi.dao;

import com.sub.potenfi.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    UserDto getUserById(String userId);

    int registerUser(UserDto userDto);

    int updateUser(UserDto userDto);

    int deleteUser(String userId);
}
