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

import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.messenger_v11.MainActivity.person;
import static com.example.messenger_v11.MainActivity.usersDao;

public class Utils {

    SharedPreferences sharedPreferences;
    String nickname;
    Context context;



    public Utils(Context context) {
        this.context = context;

    }


    void setNameOfPerson(){
        sharedPreferences = context.getSharedPreferences("nickname",MODE_PRIVATE );
        person.setNameOfPerson(sharedPreferences.getString("nick", ""));
        Log.i("personNAme",sharedPreferences.getString("nick", "") );
    }

    boolean checkUserLogin(){
        sharedPreferences = context.getSharedPreferences("nickname", MODE_PRIVATE);
        nickname = sharedPreferences.getString("nick", "");
        Log.i("sharedlog",nickname);
        if (nickname.equals("") || nickname == null )  return false;
        else return true;



    }

    void  clearNicknameSharedPref(){
        sharedPreferences  =context.getSharedPreferences("nickname",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("nick");
        editor.commit();

    }
    void clearUsernamePasswordSharePref(){
        sharedPreferences  = context.getSharedPreferences("userSettings",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.commit();
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getSHA256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(
                    text.getBytes(StandardCharsets.UTF_8));
            return new String(Hex.encode(hash));
        } catch (NoSuchAlgorithmException e) {
            return " ";
        }
    }



}
