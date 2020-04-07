package com.example.messenger_v11.Cipher;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;


public class AesInitManager {


    private volatile static AesInitManager INSTANCE;


    private AesInitManager() {
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static AesInitManager getInstance(){

        AesInitManager localINSTANCE = INSTANCE;

        if (localINSTANCE == null){
            synchronized (AesInitManager.class){
                localINSTANCE = INSTANCE;
                if (localINSTANCE == null){
                    initKeys();

                }
            }
        }

        return localINSTANCE;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void initKeys(){
        INSTANCE =new AesInitManager();
        Aes256 aes256 = new Aes256();
        RSA512 rsa512 = new RSA512();
        Log.i("ciphertest", rsa512.toString());

    }



}
