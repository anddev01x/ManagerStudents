package com.techja.managerstudents.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.techja.managerstudents.model.UserEntity;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM tbl_Users WHERE username = :username and password =:password ")
    UserEntity getUser(String username, String password);

    @Query("SELECT * FROM tbl_Users WHERE username = :username")
    boolean getUserByName(String username);

    @Query("SELECT fullName FROM tbl_Users WHERE username = :username")
    String getFullNameByUserName(String username);

    @Insert
    void registerUser(UserEntity userEntity);
}
