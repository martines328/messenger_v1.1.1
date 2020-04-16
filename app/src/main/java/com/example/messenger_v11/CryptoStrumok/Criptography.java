package com.example.messenger_v11.CryptoStrumok;

import com.example.messenger_v11.SocketNetwork.Message;

public interface Criptography {
    String encript(String message);

    Message encript(Message message);

    String decript(String message);

    Message decript(Message message);
}
