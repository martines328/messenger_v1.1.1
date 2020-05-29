package com.example.messenger_v11.SocketNetwork;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.messenger_v11.Cipher.Aes256;
import com.example.messenger_v11.MessageRoom.usersRoom.UsersEntity;

import org.json.JSONObject;


import static com.example.messenger_v11.MainActivity.usersDao;

public class MessageAnalizer {
    Aes256 aes256;
    UsersEntity usersEntity;


    public MessageAnalizer() {

        aes256 = new Aes256();
        usersEntity = new UsersEntity();

    }


    public void analizeMessage(String messageToAnalize) {

        try {


            JSONObject msg = new JSONObject(messageToAnalize);
            String type = msg.getString("Type");
            String subType = msg.getString("SubType");
            String messageType = msg.getString("MessageType");


            if (messageType.equals("Room") && type.equals("Response")) {


                Log.i("createRoom", "dis true");
                JSONObject responseRoom = new JSONObject(messageToAnalize);
                Log.i("messageLog", " response CR " + responseRoom);

                String encryptedINTERLOCUTOR = (responseRoom.getJSONObject("Body").getString("Interlocutor"));
                String encryptedCreator = (responseRoom.getJSONObject("Body").getString("Creator"));

                if (responseRoom.has("Status") ) {
                    String statusResponse = responseRoom.getString("Status");


                    if (statusResponse.equals("OK")) {
                        usersEntity.setNickname(encryptedINTERLOCUTOR);
                        usersEntity.setSendTo(encryptedINTERLOCUTOR);
                        usersDao.insertUsersToDB(usersEntity);
                        Log.i("createroom", "create result TRUE");
                    }
                } else {
                    usersEntity.setNickname(encryptedCreator);
                    usersEntity.setSendTo(encryptedCreator);
                    usersDao.insertUsersToDB(usersEntity);
                }
            }


            if (messageType.equals("Message") && type.equals("Request")) {
                getMessage(new Message(messageToAnalize));
                Log.i("messageLog", messageToAnalize);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    private void getMessage(Message msg) {
        MessageQueue.getInstance().addInputMessage(msg);
        Log.i("msg", msg.getMessage());
    }


}
