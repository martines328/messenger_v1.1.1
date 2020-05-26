package com.example.messenger_v11.SocketNetwork;


import android.util.Log;

import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;


import static com.example.messenger_v11.SocketNetwork.NetworkService.socket;

public class OpenRoomManager extends Thread {

    static DataInputStream dis;
    static DataOutputStream dos;
    String nickname, interlocutor;




    static boolean createResult;

    public OpenRoomManager(String nickname, String Intelocutor) {

        try {
            dis = new DataInputStream( socket.getInputStream());
            dos = new DataOutputStream( socket.getOutputStream());
            this.nickname = nickname;
            this.interlocutor = Intelocutor;


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public void run() {
        super.run();
        try {


            JSONObject requestRoom = new JSONObject();
            requestRoom.put("Type", "Request");
            requestRoom.put("SubType", "Post");
            requestRoom.put("MessageType", "Room");
            requestRoom.put("Body", new JSONObject()
                    .put("Action","In")
                    .put("Room", interlocutor+"_"+nickname )
                    .put("User1", nickname));

            dos.writeUTF(requestRoom.toString());
            dos.flush();
            Log.i("createroom", "open room" + requestRoom.toString());



           // String response = dis.readUTF();
           // Log.i("createroom", response);

        }catch (Exception e){
            e.printStackTrace();
        }





    }

}
