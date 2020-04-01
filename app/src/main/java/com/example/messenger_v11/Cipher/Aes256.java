package com.example.messenger_v11.Cipher;

import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.AttrRes;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Aes256  {

    private  static KeyGenerator keyGenerator;
    static byte[] IV = new byte[16];
    private static SecretKey secretKey;
    SecureRandom random;


    private static String secretKeyy = "0000000000000";
    private static String salt = "00000000000000000000";

    public Aes256() {

        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();
            random = new SecureRandom();
            random.nextBytes(IV);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }


    public static String encrypt(String strToEncrypt)
    {
        try
        {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            /*SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKeyy.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
             SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");*/
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(),"AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);
            //return Base64.encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            byte [] encodeData = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));
            return Base64.encodeToString(encodeData,Base64.DEFAULT);
        }
        catch (Exception e)
        {
            Log.i("mylog","Error while encrypting: " + e.toString());
        }
        return null;
    }




    public static String decrypt(String cipherText) {


        try
        {
             byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            //SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            //KeySpec spec = new PBEKeySpec(secretKeyy.toCharArray(), salt.getBytes(), 65536, 256);
            //SecretKey tmp = factory.generateSecret(spec);
            //SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(),"AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);
            byte [] decoderData = Base64.decode(cipherText,Base64.DEFAULT);
            byte[] decryptedData = cipher.doFinal(decoderData);
            return new String(decryptedData);

            //return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            Log.i("mylog","Error while decrypting: " + e.toString());
        }
        return null;

    }


}

