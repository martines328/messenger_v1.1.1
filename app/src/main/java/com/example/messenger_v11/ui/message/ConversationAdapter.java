package com.example.messenger_v11.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;


import com.example.messenger_v11.Person;
import com.example.messenger_v11.R;
import com.example.messenger_v11.MessageRoom.MessageInfoEntity;

import java.util.ArrayList;
import java.util.List;

import static com.example.messenger_v11.MainActivity.person;

public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // inputMessageType = 1 in MessageQueue
    // outputMessageType = 0 in AddOutputMessageToDB




    private static int TYPE_MY = 1;
    private static int TYPE_OTHER = 2;
    List<MessageInfoEntity> messagesList;
    Context context;
    String personName =  person.getNameOfPerson() ;


    public ConversationAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_MY){
            view = LayoutInflater.from(context).inflate(R.layout.conversation_owner_message,
                    parent,false);
            return new MyMessageType(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.conversation_another_message,
                    parent,false);
            return new OtherMessageType(view);

        }


    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    MessageInfoEntity messageInfoEntity = messagesList.get(position);


    String fromPeople = messageInfoEntity.fromPeople;
    String toPeople = messageInfoEntity.toPeople;

    getItemName(fromPeople, toPeople);


        if (getItemViewType(position) == TYPE_MY){
           ((MyMessageType) holder).myMessageTV.setText(messageInfoEntity.getMessages());

        }else {


           ((OtherMessageType) holder).anotherName.setText(messageInfoEntity.getFromPeople());
            ((OtherMessageType) holder).anotherMessageBody.setText(messageInfoEntity.getMessages());



        }


    }

    @Override
    public int getItemCount() {
        if (messagesList == null) return 1;
        else return messagesList.size();
    }

    @Override
    public int getItemViewType(int position) {

        MessageInfoEntity messageInfoEntity = messagesList.get(position);
        String messageFromPeople = messageInfoEntity.getFromPeople();


        if (messageFromPeople.equals(personName)  )
            return TYPE_MY;
        else if (!messageFromPeople.equals(personName)) return TYPE_OTHER;

        else return TYPE_OTHER ;

    }


    List<MessageInfoEntity> setListOfMessage(List<MessageInfoEntity> messageInfoEntityList){
        messagesList = messageInfoEntityList;
        return messagesList;
    }



    String [] getItemName(String fromPeople, String toPeople){
       String[] senddata = new String[2];
       senddata[0 ] = fromPeople;
       senddata[1] = toPeople;

        return senddata ;

    }




    // ------------------------------------------------------------------------------------------------------------------------------




    class MyMessageType extends RecyclerView.ViewHolder{


        TextView myMessageTV;



        public MyMessageType(@NonNull View itemView) {
            super(itemView);
            myMessageTV = itemView.findViewById(R.id.owner_message_body);


        }



    }


 // ------.......-----.......--------.........--------........----------........-----...


    class OtherMessageType extends RecyclerView.ViewHolder{

        ImageView anotherAvatar;
        TextView anotherName, anotherMessageBody;




        public OtherMessageType(@NonNull View itemView) {
            super(itemView);
            anotherAvatar = itemView.findViewById(R.id.anotherAvatar);
            anotherName = itemView.findViewById(R.id.anotherName);
            anotherMessageBody = itemView.findViewById(R.id.anotherMessageBody);



        }




    }




}
