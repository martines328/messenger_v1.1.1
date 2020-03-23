package com.example.messenger_v11.MessageRoom.usersRoom;

import android.app.DownloadManager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface UsersDao {

    @Query("SELECT * FROM users")
    LiveData<List<UsersEntity>> getAllUsers();


    @Insert
    void insertUsersToDB(UsersEntity usersEntity);


    @Delete
    void deleteUsersFromDB(UsersEntity usersEntity);



}
