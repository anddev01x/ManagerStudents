package com.techja.managerstudents.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDAO getUserDAO();
}
