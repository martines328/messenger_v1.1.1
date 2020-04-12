package com.example.messenger_v11;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.messenger_v11.Authorization.AuthActivity;
import com.example.messenger_v11.Cipher.Aes256;
import com.example.messenger_v11.MessageRoom.usersRoom.UsersEntity;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.messenger_v11.MainActivity.usersDao;

public class Utils {

    SharedPreferences sharedPreferences;
    String login, password;
    Context context;



    public Utils(Context context) {
        this.context = context;

    }









    boolean checkUserLogin(){
        sharedPreferences = context.getSharedPreferences("userSettings", MODE_PRIVATE);
        login = sharedPreferences.getString("email", "");
        password =  sharedPreferences.getString("password","");
        Log.i("prefedit", "chek user login work");
        Log.i("prefedit", login + password);
        boolean loginResult = false;
        if (login.equals("") || password.equals("")
                || login == null || password == null){
            Intent intent = new Intent(context, AuthActivity.class);
            context.startActivity(intent);
            return false;
        }
        else{

            return true;
        }


    }



}
