package com.example.messenger_v11.NDK;

public class AESKeyGener {

    static {

        System.loadLibrary("key");
    }



    public static native String getNativeKey();

    private static native  String getNativeSalt();

    private static native  String getNativeIV();


}
