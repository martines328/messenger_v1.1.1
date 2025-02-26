package com.example.messenger_v11.ui.message;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.messenger_v11.MainActivity;
import com.example.messenger_v11.MessageRoom.ConversationViewModel;
import com.example.messenger_v11.MessageRoom.ConversationViewModelFactory;
import com.example.messenger_v11.R;
import com.example.messenger_v11.SocketNetwork.Message;
import com.example.messenger_v11.SocketNetwork.MessageQueue;
import com.example.messenger_v11.MessageRoom.AddOutputMessageToDB;


import static com.example.messenger_v11.Cipher.Aes256.encrypt;
import static com.example.messenger_v11.MainActivity.person;


public class Conversation extends AppCompatActivity implements View.OnClickListener {

    String sendToName;



    EditText messageTextET;
    ImageButton sendMessageButton;

    ConversationAdapter conversationAdapter ;
    RecyclerView messageRecyclreView;
    ConversationViewModel conversationViewModel;
    Intent intent ;








    @SuppressLint("NewApi")
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


        /*conversationViewModel.getAllMessagList().observe(this, messageInfoEntities -> {


            conversationAdapter.setListOfMessage(messageInfoEntities);
            messageRecyclreView.setAdapter(conversationAdapter);

        });*/
      conversationViewModel.getMEssageByName().observe(this, messageInfoEntities -> {
          conversationAdapter.setListOfMessage(messageInfoEntities);
          messageRecyclreView.setAdapter(conversationAdapter);


      });

        //TODO refactor all encrypt and recrypt method

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (messageTextET != null) {
            String message = messageTextET.getText().toString();
            if (message.equals("")) {

                Toast.makeText(this, "Enter Message Please", Toast.LENGTH_SHORT).show();
            } else {
                String encryptedMessage = null;
                try {
                        encryptedMessage = message;

                        
                    Log.i("ciphertest", "conversation encr   " + encryptedMessage);

                    new AddOutputMessageToDB(this, person.getNameOfPerson()
                            , sendToName, encryptedMessage).execute();

                    messageTextET.getText().clear();

                    String person = MainActivity.person.getNameOfPerson();
                        MessageQueue.getInstance().addOutputMessage(new Message(person /// FROM HERE WE SEND MESSAGE WITH EXISTING NAME
                                , sendToName, message));


                } catch (Exception e) {
                    Log.i("mylog", e.toString());
                }

            }
        }
    }
}
