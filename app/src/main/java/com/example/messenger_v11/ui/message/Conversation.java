package com.example.messenger_v11.ui.message;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.messenger_v11.MessageRoom.ConversationViewModel;
import com.example.messenger_v11.MessageRoom.ConversationViewModelFactory;
import com.example.messenger_v11.MessageRoom.MessageInfoEntity;
import com.example.messenger_v11.R;
import com.example.messenger_v11.SocketNetwork.Message;
import com.example.messenger_v11.SocketNetwork.MessageQueue;
import com.example.messenger_v11.MessageRoom.AddOutputMessageToDB;
import com.example.messenger_v11.SocketNetwork.SocketConnectingManager;

import org.json.JSONException;

import java.util.List;

import static com.example.messenger_v11.MainActivity.person;


public class Conversation extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "mylogs" ;
    String sendToName;



    EditText messageTextET;
    ImageButton sendMessageButton;

    ConversationAdapter conversationAdapter ;
    RecyclerView messageRecyclreView;
    ConversationViewModel conversationViewModel;
    Intent intent ;








    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);


        intent = getIntent();
         sendToName = intent.getStringExtra("sendToName");

        messageTextET = (EditText) findViewById(R.id.messageText);
        sendMessageButton = (ImageButton) findViewById(R.id.sendButton);
        messageRecyclreView = findViewById(R.id.message_recyclerView);

        sendMessageButton.setOnClickListener(this);


        conversationAdapter = new ConversationAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setStackFromEnd(true);
        messageRecyclreView.setLayoutManager(linearLayoutManager);




        ConversationViewModelFactory viewModelFactory = new ConversationViewModelFactory(this, sendToName);

        conversationViewModel = ViewModelProviders.of(this, viewModelFactory).get(ConversationViewModel.class);


       /* conversationViewModel.getAllMessagList().observe(this, new Observer<List<MessageInfoEntity>>() {
            @Override
            public void onChanged(List<MessageInfoEntity> messageInfoEntities) {


                conversationAdapter.setListOfMessage(messageInfoEntities);
                messageRecyclreView.setAdapter(conversationAdapter);

            }
        });*/
      conversationViewModel.getMEssageByName().observe(this, new Observer<List<MessageInfoEntity>>() {
          @Override
          public void onChanged(List<MessageInfoEntity> messageInfoEntities) {
              conversationAdapter.setListOfMessage(messageInfoEntities);
              messageRecyclreView.setAdapter(conversationAdapter);


          }
      });



    }


    @Override
    public void onClick(View v) {
        if (messageTextET != null) {
            String message = messageTextET.getText().toString();

            new AddOutputMessageToDB(this, person.getNameOfPerson()
                    ,sendToName, message).execute();

            messageTextET.getText().clear();



            try {
                MessageQueue.getInstance().addOutputMessage(new Message(person.getNameOfPerson() /// FROM HERE WE SEND MESSAGE WITH EXISTING NAME
                        ,sendToName,message));
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
}
