package com.example.messenger_v11.MessageRoom;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDao {


    @Query("SELECT * FROM message")
    LiveData<List<MessageInfoEntity>> getAllMessages();


    @Query("SELECT * FROM message WHERE fromPeople = :fromPeople and toPeople = :toPeople " +
            "or fromPeople = :toPeople and toPeople = :fromPeople")
     LiveData<List<MessageInfoEntity>> getFromPeopleName( String fromPeople,String toPeople);


    @Insert
    void insertMessage(MessageInfoEntity messageInfoEntity);

    @Delete
    void deleteMessage(MessageInfoEntity messageInfoEntity);

    @Update
    void updateMessage(MessageInfoEntity messageInfoEntity);


}
