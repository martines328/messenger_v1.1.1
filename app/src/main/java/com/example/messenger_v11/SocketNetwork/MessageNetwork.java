package com.example.messenger_v11.SocketNetwork;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.messenger_v11.Person;
import com.example.messenger_v11.ui.message.Conversation;

import org.json.JSONException;

import java.io.DataInputStream;

import static com.example.messenger_v11.MainActivity.appContext;
import static com.example.messenger_v11.MainActivity.person;

public class MessageNetwork extends AsyncTask<String, Void, Void> {



    @Override
    protected Void doInBackground(String... strings) {

        while (true){
            try {
                MessageQueue.getInstance().addOutputMessage(new Message("messageNetwork"
                        ,"test4"  ,strings[0]));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
}
