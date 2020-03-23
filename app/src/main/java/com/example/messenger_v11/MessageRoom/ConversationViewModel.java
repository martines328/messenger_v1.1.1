package com.example.messenger_v11.MessageRoom;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ConversationViewModel extends ViewModel {

    private LiveData<List<MessageInfoEntity>> messageInfoEntityList;
    private LiveData<List<MessageInfoEntity>> getAllMessageByNameList;
    private MessageRepository messageRepository;
    private String sendToNAme;
    private Context context;


    public ConversationViewModel(Context context , String sendToNAme) {
        this.sendToNAme = sendToNAme;
        this.context  =context;
        //Log.i("nameuser", sendToNAme);
        messageRepository = new MessageRepository(sendToNAme);
        messageInfoEntityList = messageRepository.getAllMessage();

        getAllMessageByNameList = messageRepository.getMessageListByNAme(); // getMessageList by name


    }


   public LiveData<List<MessageInfoEntity>> getAllMessagList(){
        return messageInfoEntityList;
    }

    public  LiveData<List<MessageInfoEntity>> getMEssageByName(){
        return getAllMessageByNameList;
    }







}
