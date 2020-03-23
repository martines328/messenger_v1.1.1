package com.example.messenger_v11.SocketNetwork;

import android.os.Bundle;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class MessageSenderService extends Thread {


    private DataOutputStream dos = null;

    public MessageSenderService(DataOutputStream dos){
        this.dos = dos;
    }

    @Override
    public void run() {


        try {
            while (true) {

                if (MessageQueue.getInstance().isEmptyOutputMessage()) {
                    this.sleep(200);
                } else {
                    sendMessage(MessageQueue.getInstance().getOutputMessage());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void sendMessage(Message msg) throws IOException {
        dos.writeUTF(msg.getMessage());
        Log.i("mylog", "message send - " + msg.getMessage());

    }


}
