package com.example.messenger_v11.Cipher;

import android.util.Log;

public class AesKeyManager {


    private volatile static AesKeyManager INSTANCE;


    private AesKeyManager() {
    }


    public static AesKeyManager getInstance(){

        AesKeyManager localINSTANCE = INSTANCE;

        if (localINSTANCE == null){
            synchronized (AesKeyManager.class){
                localINSTANCE = INSTANCE;
                if (localINSTANCE == null){
                    initKeys();
                }
            }
        }

        return localINSTANCE;
    }


    private static void initKeys(){
        INSTANCE =new AesKeyManager();
        Aes256 aes256 = new Aes256();
        Log.i("ciphertest", aes256.toString());
    }



}
