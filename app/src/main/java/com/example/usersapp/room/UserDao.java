package com.example.usersapp.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<DbUser> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertUser(DbUser user);

    @Query("SELECT * FROM users WHERE uid = :uid LIMIT 1")
    DbUser getUserByUid(String uid);
}

