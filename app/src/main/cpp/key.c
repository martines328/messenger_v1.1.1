//
// Created by Andrii on 2020-04-01.


#include "stdio.h"
#include "jni.h"



JNIEXPORT jstring JNICALL
Java_com_example_messenger_1v11_NDK_AESKeyGener_getNativeKey(JNIEnv *env, jclass clazz) {

    jstring key = "B1C431EBB89355028E2C0E597EBFBF4239BA21573D50EC61E94622AAB1D7D94A";

    return (*env)->NewStringUTF(env,key);


}

JNIEXPORT jstring JNICALL
Java_com_example_messenger_1v11_NDK_AESKeyGener_getNativeSalt(JNIEnv *env, jclass clazz) {

    jstring salt = "638D4D9C7491EA49";
    return (*env)->NewStringUTF(env,salt);


}

JNIEXPORT jstring JNICALL
Java_com_example_messenger_1v11_NDK_AESKeyGener_getNativeIV(JNIEnv *env, jclass clazz) {

    jstring iv = "4081441C59BC04BBFA08C3E854DECC77";
    return (*env)->NewStringUTF(env,iv);
}