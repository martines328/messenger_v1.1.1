package com.example.messenger_v11.Cipher;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class RSA512 {



    private final static String RSA = "RSA";
/*
    public static PublicKey publicKey;
    public static PrivateKey privateKey;*/



    static KeyPairGenerator kpg;
    static KeyPair kp;
    static byte[] encryptedBytes, decryptedBytes;
    static Cipher cipher, cipher1;
    static String encrypted, decrypted;


    /*private static String publicKey = "MDwwDQYJKoZIhvcNAQEBBQADKwAwKAIhAPmoKt1npdmdmuBUmAeapjdDd6KUesUX" +
            "SGOUJDKFNH33AgMBAAE=";
    private static String privateKey = "MIHDAgEAMA0GCSqGSIb3DQEBAQUABIGuMIGrAgEAAiEA+agq3Wel2Z2a4FSYB5qm" +
            "N0N3opR6xRdIY5QkMoU0ffcCAwEAAQIgYZXjU3HdOSfKzx8h4e8IQtIf5QE7sl2T" +
            "QraPmagHtZkCEQD9iXqDoyg2LEeYLROBakRtAhEA/BUKFZyvmquZ5HUx/GQlcwIR" +
            "ANF8NHRr7R/FumosZH971IkCEQDJEDr/6/8A3licKYIaLwLTAhB5w+U5Yg5KOUf0" +
            "p3n4qp6f";*/


   /* private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZ" +
            "QWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE" +
            "2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB";
    private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAUZV+" +
            "tjiNBKhlBZbKBnzeugpdYPhh5PbHanjV0aQ+LF7vetPYhbTiCVqA3a+Chmge44+prlqd3qQCYra6OYIe7oP" +
            "Vq4mETa1c/7IuSlKJgxC5wMqYKxYydb1eULkrs5IvvtNddx+9O/JlyM5sTPosgFHOzr4WqkVtQ71IkR+HrAg" +
            "MBAAECgYAkQLo8kteP0GAyXAcmCAkA2Tql/8wASuTX9ITD4lsws/VqDKO64hMUKyBnJGX/91kkypCDNF5oCs" +
            "dxZSJgV8owViYWZPnbvEcNqLtqgs7nj1UHuX9S5yYIPGN/mHL6OJJ7sosOd6rqdpg6JRRkAKUV+tmN/7Gh0+" +
            "GFXM+ug6mgwQJBAO9/+CWpCAVoGxCA+YsTMb82fTOmGYMkZOAfQsvIV2v6DC8eJrSa+c0yCOTa3tirlCkhBf" +
            "B08f8U2iEPS+Gu3bECQQCrG7O0gYmFL2RX1O+37ovyyHTbst4s4xbLW4jLzbSoimL235lCdIC+fllEEP96wP" +
            "Aiqo6dzmdH8KsGmVozsVRbAkB0ME8AZjp/9Pt8TDXD5LHzo8mlruUdnCBcIo5TMoRG2+3hRe1dHPonNCjgbd" +
            "ZCoyqjsWOiPfnQ2Brigvs7J4xhAkBGRiZUKC92x7QKbqXVgN9xYuq7oIanIM0nz/wq190uq0dh5Qtow7hshC" +
            "/dSK3kmIEHe8z++tpoLWvQVgM538apAkBoSNfaTkDZhFavuiVl6L8cWCoDcJBItip8wKQhXwHp0O3HLg10OE" +
            "d14M58ooNfpgt+8D8/8/2OOFaR0HzA+2Dm";*/

   /* private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwn7+FO/QiYDQNdKRnXNy" +
            "uJAWz3gxUOE2n3p9mUUzR0KhsFBtsDZOLFbycpSEo8unp2ANpuyN9/pZH4H17b1I" +
            "gtvPGn6+aztuZXCKPZkikpVjt/Yh/XktOMcq0VqYAl2O2V7oyeFt4Byp1uWeVsK7" +
            "jTGYe8Hycoa0+6If02CwCnW4DXAXU5SzqONHNHRfU2KztuEZSBDYAsQvP17chwMo" +
            "fJqy5xYs/azlR6hz875Yo+0Y7mznj4TBDwkmV2B/IC5KX5ksezWVMlmshpz4UhKV" +
            "aCmj6Ik5ojzBq2YZQVib3IJOZqWmPpkB1yzhgaaGxPP7a43uD7JFqZgsvZX3Q2tj" +
            "6QIDAQAB";
    private static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCfv4U79CJgNA1" +
            "0pGdc3K4kBbPeDFQ4Tafen2ZRTNHQqGwUG2wNk4sVvJylISjy6enYA2m7I33+lkf" +
            "gfXtvUiC288afr5rO25lcIo9mSKSlWO39iH9eS04xyrRWpgCXY7ZXujJ4W3gHKnW" +
            "5Z5WwruNMZh7wfJyhrT7oh/TYLAKdbgNcBdTlLOo40c0dF9TYrO24RlIENgCxC8/" +
            "XtyHAyh8mrLnFiz9rOVHqHPzvlij7RjubOePhMEPCSZXYH8gLkpfmSx7NZUyWayG" +
            "nPhSEpVoKaPoiTmiPMGrZhlBWJvcgk5mpaY+mQHXLOGBpobE8/trje4PskWpmCy9" +
            "lfdDa2PpAgMBAAECggEAEUl6SeAa1+3kE7SYjrcRLkhYTq5vK7Ioybn4aeQVr7LD" +
            "bklnxm69OgGsYPTEaU6TozPNYtXJtI3C2CMT+cKGLGNfIAq94AgTQt7i2qC+yuqO" +
            "R26QDNh1ZYOGUb9MnYE1Tiq6ZB8uReomSlx0W1/Jjb4nwHA3nWhNs8PwMjh4zSUL" +
            "fSQYQ9oaFCHVuVmyGYgNmizZyQ+Uzs8zme3UIreb5NlvFCygmNURkPaP9YEzmY9h" +
            "L1eqvZFhr2vrq8+cOKmbQQyAImZls+zdWPK4a6/omncTAZok3evDOC0hz2RrmAKo" +
            "7Jo3Jy5M0BmpxN93rWV3YEMvQl7GKtnrz6//j0fEhQKBgQD6iMJrF0qVAOzHmPZJ" +
            "vlLPz44FPEq/EmEwUQ6KHtqHYATv3BEO4yoOmN4b4NtfFIxhF0YOj5vTj4jJmHVG" +
            "LJfLFYJ4OPdPCgZQ1IRED/4+qVLRCb7tgRkC2/RG7gizPSS6/dvaYkAh/9tYuQ64" +
            "HtF06haDzdzWiQBV13eYO34pewKBgQDGvUIoLDAxdK3FIDLTH0X8JzSq7NFl4CtW" +
            "xzmCgaj53hNp7sYmQf3VOygAcAGlc/+w6KZWrpmLrWal+cwmQuwoERkoPkO4Gg+1" +
            "n18X6lgBu46juk5uDgnPP+/0UYtIoY1DR5nb+PAOt29WTqAiHPcdQtCyC2yMohr/" +
            "IQSbRajw6wKBgQCtOtDLgZBVVQ1jubVaKhpSjJ4Tqqu4UDoZUYKvKERL5K2lOFIn" +
            "LBWRgEvblO+6X5ftLk2Hle7WKuIXGq7ENeNDTf0nqaP+59yUGU3XTgOrSiL0XTvC" +
            "N0iW/IV8YexsGvQBw7Q33UWg/KurPlIaJ10G7TFiM37mbFZqBJEyFrjWbwKBgGqu" +
            "tZCzB7vEknM68Y17QCo3MYJ/VXiGeT0//JMd3wMiPvGihyOKUSPheXuipH+YYS0p" +
            "9gCaATqFfpCiIwB29CYd548+vUNaWgx43KcN2Og1MkCZhfh1+LYT8E8KZuY6MoZn" +
            "LSRz4QlFVrLRygwF2HEQqM6qaz6i1jMWGk7vedy5AoGBAOwPDYXh/3vc5cngSaRz" +
            "bdo0g8nafz88nC97OU1HdzGaUxFNNLDjX1w8TgeCtr0yGtjDS4PblyND8PDFl/yg" +
            "da0AQjNjukc48UZIWluDbWfuRKk9wKztT4BQM56hwR/ThZlYUxzbZEpfqLCkiKhm" +
            "sDVLlHpgtXrxcP9UAN2XM1ao";
*/





    @RequiresApi(api = Build.VERSION_CODES.O)
    public RSA512() {
        try {
           /* this.publicKey = rsaKeyManager.stringToPublicKey(rsapublicKey);
            this.privateKey = rsaKeyManager.stringToPrivate(rsaprivateKey);*/

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair pair = keyGen.generateKeyPair();
            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static PrivateKey privateKey;
    private static PublicKey publicKey;

/*


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String data) throws Exception {
        String encodeddata = java.util.Base64.getEncoder().encodeToString(data.getBytes());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte [] encrypted =  cipher.doFinal(encodeddata.getBytes());
        return Base64.encodeToString(encrypted,Base64.DEFAULT);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String encryptedData) throws Exception{
        byte [] byteTODecrypt = encryptedData.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte [] decrypted = cipher.doFinal(byteTODecrypt);
        String getData = decrypted.toString();
        return new String(decrypted);
    }
*/









}
