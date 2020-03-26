package com.example.messenger_v11.MessageRoom;

import android.content.Context;
import android.os.AsyncTask;

import com.example.messenger_v11.SocketNetwork.Message;

import static com.example.messenger_v11.MainActivity.messageDao;


public class AddOutputMessageToDB extends AsyncTask<Void,Void,Void> {

    Context context ;
    String from, to, message;

    public AddOutputMessageToDB(Message nMessage) {
        this.nMessage = nMessage;
    }

    Message nMessage;

    public AddOutputMessageToDB(Context context, String from, String to, String message) {
        this.context = context;
        this.from = from;
        this.to = to;
        this.message = message;


    }



    @Override
    protected Void doInBackground(Void... voids) {

        MessageInfoEntity entity = new MessageInfoEntity();
        entity.setFromPeople(from);
        entity.setToPeople(to);
        entity.setMessages(message);
        messageDao.insertMessage(entity);

        return null;
    }




}
