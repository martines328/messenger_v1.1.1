package com.example.messenger_v11.SocketNetwork;

import android.app.Notification;
import android.util.Log;

import com.example.messenger_v11.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.example.messenger_v11.MainActivity.person;

public class NetworkService extends Thread {


    private Socket socket = null;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;

    public NetworkService(String host, int port, String nickname){
        try {



           /* Socket s = new Socket(host,port);
            JSONObject obj = new JSONObject();
            obj.put("type", "Stream Message");
            obj.put("subType", "Requeste");
            obj.put("body", "Open");
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF(obj.toString());
            String response = dis.readUTF();
            obj = new JSONObject();
            obj.put("type","Stream Message");
            obj.put("subType","Response");
            obj.put("body", "Accepted");


            Log.i("responseserv", response);*/





            socket = new Socket(host,port);
            socket.setKeepAlive(true);
            openStreams();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void openStreams() throws IOException {
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        dos.writeUTF(person.getNameOfPerson());
        new MessageSenderService(dos).start();
    }


    @Override
    public void run() {


        try {
            while (true) {
                Log.i("mylog", "hi from input service");
                String mes = dis.readUTF();
                getMessage(new Message(mes));
                Log.i("mylog", "input message -- " + mes);



/**/

            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void getMessage(Message msg){
        MessageQueue.getInstance().addInputMessage(msg);
        Log.i("msg", msg.getMessage());
    }



}
