package com.example.messenger_v11.Cipher;

import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.example.messenger_v11.NDK.AESKeyGener;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class Aes256   {

    public static AESKeyGener aesKeyGener;


    public Aes256() {
      aesKeyGener = new AESKeyGener();
    }

    private  String secretKey = AESKeyGener.getNativeKey();
    private  String salt = AESKeyGener.getNativeSalt();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String strToEncrypt)
    {
        try
        {
            byte[] iv = { 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

           /* SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 128);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");*/
            PBEKeySpec spec = new PBEKeySpec(aesKeyGener.getNativeKey().toCharArray(),
                    aesKeyGener.getNativeSalt().getBytes(), 500, 256);
            SecretKey key = SecretKeyFactory.getInstance("PBKDF2withHmacSHA1").generateSecret(spec);

            Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
            return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            Log.i("cipher","Error while encrypting: " + e.toString());
        }
        return null;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String strToDecrypt) {


        try
        {
            byte[] iv = { 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

           /* SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 128);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");*/

            PBEKeySpec spec = new PBEKeySpec(aesKeyGener.getNativeKey().toCharArray(),
                    aesKeyGener.getNativeSalt().getBytes(), 500, 256);
            SecretKey key = SecretKeyFactory.getInstance("PBKDF2withHmacSHA1").generateSecret(spec);

            Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            Log.i("cipher","Error while decrypting: " + e.toString());
        }
        return null;

    }


}

