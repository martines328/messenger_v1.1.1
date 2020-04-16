package com.example.messenger_v11.CryptoStrumok;

import com.example.messenger_v11.SocketNetwork.Message;

public class Strumok implements Criptography {


    @Override
    public String encript(String message) {
        return message;
    }

    @Override
    public Message encript(Message message) {
        return message;
    }

    @Override
    public String decript(String message) {
        return message;
    }


    @Override
    public Message decript(Message message) {
        return message;
    }


}
