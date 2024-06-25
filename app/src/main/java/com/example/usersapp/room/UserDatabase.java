package com.example.usersapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DbUser.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
