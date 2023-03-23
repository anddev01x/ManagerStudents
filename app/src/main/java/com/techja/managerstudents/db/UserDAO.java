package com.techja.managerstudents.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM UserEntity WHERE username = :username and password =:password ")
    UserEntity getUser(String username, String password);

    @Query("SELECT * FROM UserEntity WHERE username = :username")
    boolean getUserByName(String username);

    @Query("SELECT fullName FROM UserEntity WHERE username = :username")
    String getFullNameByUserName(String username);

    @Insert
    void registerUser(UserEntity userEntity);
}
