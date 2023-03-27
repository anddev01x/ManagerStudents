package com.techja.managerstudents.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.techja.managerstudents.dao.StudentDAO;
import com.techja.managerstudents.dao.UserDAO;
import com.techja.managerstudents.model.StudentEntity;
import com.techja.managerstudents.model.UserEntity;

@Database(entities = {UserEntity.class, StudentEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO getUserDAO();
    public abstract StudentDAO getStudentDAO();
}
