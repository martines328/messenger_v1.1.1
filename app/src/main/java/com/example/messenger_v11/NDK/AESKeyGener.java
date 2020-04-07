package com.example.messenger_v11.NDK;

public class AESKeyGener {

    static {

        System.loadLibrary("key");
    }



    public static native String getNativeKey();

    public static native  String getNativeSalt();

    public static native  String getNativeIV();






}
