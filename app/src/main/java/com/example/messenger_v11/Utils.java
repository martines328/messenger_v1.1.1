package com.example.messenger_v11;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import org.bouncycastle.util.encoders.Hex;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.content.Context.MODE_PRIVATE;
import static com.example.messenger_v11.MainActivity.person;



public class Utils {

    static SharedPreferences sharedPreferences;
    static Context context;
    public static String personNamestattic;





    public Utils(Context context) {
        this.context = context;

    }


     public static String setNameOfPerson(){
        sharedPreferences = context.getSharedPreferences("nickname",MODE_PRIVATE );
        String name = sharedPreferences.getString("nick", "");
        person.setNameOfPerson(name);
        Log.i("personNAme",sharedPreferences.getString("nick", "") );
        return name;

    }

    boolean checkUserLogin(){
        sharedPreferences = context.getSharedPreferences("nickname", MODE_PRIVATE);
         String nickname = sharedPreferences.getString("nick", "");
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
