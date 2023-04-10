package com.techja.managerstudents.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.techja.managerstudents.dao.ClassRoomDAO;
import com.techja.managerstudents.dao.StudentDAO;
import com.techja.managerstudents.dao.UserDAO;
import com.techja.managerstudents.model.ClassroomEntity;
import com.techja.managerstudents.model.StudentEntity;
import com.techja.managerstudents.model.UserEntity;

@Database(entities = {UserEntity.class, StudentEntity.class, ClassroomEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase instance;

    public abstract UserDAO getUserDAO();

    public abstract StudentDAO getStudentDAO();

    public abstract ClassRoomDAO getClassRoomDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "ManagerStudent").allowMainThreadQueries().build();
        }
        return instance;
    }
}
