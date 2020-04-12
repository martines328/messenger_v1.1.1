//
// Created by Andrii on 2020-04-01.


#include "stdio.h"
#include "jni.h"



JNIEXPORT jstring JNICALL
Java_com_example_messenger_1v11_NDK_AESKeyGener_getNativeKey(JNIEnv *env, jclass clazz) {

    jstring key = "Messenger-v11";

    return (*env)->NewStringUTF(env,key);


}

JNIEXPORT jstring JNICALL
Java_com_example_messenger_1v11_NDK_AESKeyGener_getNativeSalt(JNIEnv *env, jclass clazz) {

    jstring salt = " messmessmess";
    return (*env)->NewStringUTF(env,salt);


}

JNIEXPORT jstring JNICALL
Java_com_example_messenger_1v11_NDK_AESKeyGener_getNativeIV(JNIEnv *env, jclass clazz) {

    jstring iv = "4081441C59BC04BBFA08C3E854DECC77";
    return (*env)->NewStringUTF(env,iv);
}

//-------------------------------------------------------------------------------------------------


JNIEXPORT jstring JNICALL
Java_com_example_messenger_1v11_Cipher_rsaKeyManager_rsaPublicKey(JNIEnv *env, jclass clazz) {


    jstring  pk = "-----BEGIN RSA PUBLIC KEY-----\n"
                  "MEgCQQDOaMOwqBLG9RImDof7NwWuwEZpa/CYffDfkJw6YL2LT7kTkvXkAX+HuNua\n"
                  "rSKB18TJTiGKlcnkOyiDZyly/WylAgMBAAE=\n"
                  "-----END RSA PUBLIC KEY-----";
    return (*env)->NewStringUTF(env,pk);

}

JNIEXPORT jstring JNICALL
Java_com_example_messenger_1v11_Cipher_rsaKeyManager_rsaPrivateKey(JNIEnv *env, jclass clazz) {

    jstring  rk = "-----BEGIN RSA PRIVATE KEY-----\n"
                  "MIIBOwIBAAJBAM5ow7CoEsb1EiYOh/s3Ba7ARmlr8Jh98N+QnDpgvYtPuROS9eQB\n"
                  "f4e425qtIoHXxMlOIYqVyeQ7KINnKXL9bKUCAwEAAQJAPzeG98wCND1K2a0JqL8p\n"
                  "VmQ9LHyKq6mm5FH+B0MpykiLlicJDFF6gOU71JHB2Wp/fxgkhXqLJkWvFFCDbeTF\n"
                  "4QIhAO66//Pazb7PFU9aDUNV915YsfBdSvO/1gvHNntEOiNzAiEA3Vc24fR1EZpW\n"
                  "qC+3xIAZoPyAZKG5OlPue2Vdk/sHmYcCIQDF76I8S9R/Oi/PBTkt9V8OiLewNYnp\n"
                  "o8sTJO9VMPKLZQIgQH8+fd83EdcQc1fcS8UsMq4BV9GMYQO+FXT3LLzUfRcCIQCY\n"
                  "UsmUQZ1seHE4tpSX1miryAFsUIYAuzzOpiGCkMEg4A==\n"
                  "-----END RSA PRIVATE KEY-----";
    return (*env)->NewStringUTF(env,rk);


}