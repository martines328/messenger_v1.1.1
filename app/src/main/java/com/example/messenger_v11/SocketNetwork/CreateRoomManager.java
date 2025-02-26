package com.example.messenger_v11.SocketNetwork;

import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.net.ssl.SSLSocket;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CreateRoomManager extends Thread {


    SSLSocket socket;
    static DataInputStream dis;
    static DataOutputStream dos;


    public CreateRoomManager(SSLSocket socket) {
        try {
            this.socket = socket;
            dis = new DataInputStream( socket.getInputStream());
            dos = new DataOutputStream( socket.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






        public Observable<Boolean> createRoom(String nickname, String interlocutor){

        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            JSONObject requestRoom = new JSONObject();
            requestRoom.put("Type", "Request");
            requestRoom.put("SubType", "Put");
            requestRoom.put("MessageType", "Room");
            requestRoom.put("Body", new JSONObject()
                    .put("Creator",  nickname)
                    .put("Interlocutor", interlocutor));

            dos.writeUTF(requestRoom.toString());

           emitter.onNext(true);
           emitter.onComplete();

           /* String response = dis.readUTF();
            JSONObject responseRoom = new JSONObject(response);
            Log.i("createroom", " response CR " + response );

            String statusResponse = responseRoom.getString("Status");

            if (statusResponse.equals("OK"))  emitter.onNext(true);

            else emitter.onNext( false);
            emitter.onComplete();*/
        }).observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.computation());
        }


}
