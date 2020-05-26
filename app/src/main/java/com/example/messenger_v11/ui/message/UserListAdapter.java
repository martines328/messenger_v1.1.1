package com.example.messenger_v11.ui.message;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.messenger_v11.MessageRoom.usersRoom.UsersEntity;
import com.example.messenger_v11.Person;
import com.example.messenger_v11.R;
import com.example.messenger_v11.SocketNetwork.OpenRoomManager;

import java.util.Collections;
import java.util.List;

import static com.example.messenger_v11.MainActivity.person;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    Context context;
    List<UsersEntity> allListMessage;
    private String deleteName;


    public UserListAdapter( Context context) {

        this.context = context;
        //this.allListMessage= allListMessage;



    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_fragment_item,parent
        ,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        UsersEntity entity = allListMessage.get(position);


        try {
        String decryptedUSerNAme = entity.getNickname();
            holder.nameOfPeople.setText(decryptedUSerNAme);
            String sendToName  =entity.getNickname();

            Log.i("ciphertest",sendToName);
            getDeleteUserName(sendToName);

            Glide.with(context).load(R.mipmap.label2).into(holder.peopleAvatar);


            holder.view.setOnClickListener(v -> {
                Intent intent = new Intent(context, Conversation.class);
                intent.putExtra("sendToName", sendToName);



                OpenRoomManager openRoomManager = new OpenRoomManager(person.getNameOfPerson(),sendToName);
                openRoomManager.start();
                context.startActivity(intent);

            });


        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    public String getDeleteUserName(String deleteName){

        this.deleteName = deleteName;
        return deleteName;
    }

    public String setDeleteName(){
        return deleteName;
    }


    public List<UsersEntity> setUSers(List<UsersEntity> usersEntityList){
        allListMessage = usersEntityList;
        Collections.reverse(allListMessage);
        return allListMessage;
    }



    @Override
    public int getItemCount() {
       if (allListMessage !=null)return allListMessage.size();
       else return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameOfPeople, messageFragment;
        ImageView peopleAvatar;
        View view;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfPeople = itemView.findViewById(R.id.nameOfPeople);
            peopleAvatar = itemView.findViewById(R.id.avatarMessage);
            view = itemView;
        }
    }
}
