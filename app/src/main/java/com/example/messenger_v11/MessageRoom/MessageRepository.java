package com.example.messenger_v11.MessageRoom;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import static com.example.messenger_v11.MainActivity.messageDao;
import static com.example.messenger_v11.MainActivity.person;

public class MessageRepository {

    Intent intent;

    MessageDataBase messageDataBase ;
    LiveData<List<MessageInfoEntity>> mMessageInfoEntityList;
    LiveData<List<MessageInfoEntity>> getMessageByNAmeList;
    String sendToName;



    public MessageRepository(String sendToName) {



        this.sendToName = sendToName;


        mMessageInfoEntityList = messageDao.getAllMessages();

       getMessageByNAmeList  =messageDao.getFromPeopleName(person.nameOfPerson ,sendToName);
    }



    public LiveData<List<MessageInfoEntity>> getAllMessage(){
        return mMessageInfoEntityList ;


    }


    public LiveData<List<MessageInfoEntity>> getMessageListByNAme(){
        return getMessageByNAmeList;

    }





}
