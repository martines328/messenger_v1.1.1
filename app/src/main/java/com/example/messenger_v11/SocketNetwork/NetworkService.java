package com.example.messenger_v11.SocketNetwork;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.messenger_v11.CryptoStrumok.Strumok;
import com.example.messenger_v11.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.example.messenger_v11.MainActivity.getContext;
import static com.example.messenger_v11.Utils.getSHA256;

public class NetworkService extends Thread {
    Strumok strumok;
    public static final String log = "cripto";
    SharedPreferences sharedPreferences;


    static boolean authResult;



    private static final String OPEN_REQUEST =
            "{" +
                    "\"Type\":\"Request\"," +
                    "\"SubType\":\"Connect\"," +
                    "\"MessageType\":\"Stream\"" +
                    "}";

    private static final String OPEN_RESPONSE =
            "{" +
                    "\"Type\":\"Response\"," +
                    "\"SubType\":\"Connect\"," +
                    "\"MessageType\":\"Stream\"," +
                    "\"Status\":\"OK\"" +
                    "}";

    private static final String AUTH_REQUEST =
            "{" +
                    "\"Type\":\"Request\"," +
                    "\"SubType\":\"Get\"," +
                    "\"MessageType\":\"Auth\"" +
                    "}";








    private static Socket socket = null;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;

    public NetworkService(String host, int port, String nickname){
        try {




            socket = new Socket(host,port);
            socket.setKeepAlive(true);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            //openStreams();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private boolean openStreams() throws IOException {
        try {


            Log.i("cripto ", "openStream  start " );

            dos.writeUTF(OPEN_REQUEST );
            JSONObject response = new JSONObject(dis.readUTF());
            if (response.getString("Type").equals("Response")
                    && response.getString("SubType").equals("Connect")
                    && response.getString("MessageType").equals("Stream")
                    && response.getString("Status").equals("OK")){
                Log.i("cripto ", "openStream true" );
                return true;
            }else return false;



            //dos.writeUTF(person.getNameOfPerson());
            //new MessageSenderService(dos).start();


        }catch (Exception e){
            Log.i("cripto",e.toString());
        }
        return false;
    }



    private void confirmCriptoStrumok(){

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean openCriptoStream(){
        Log.i("cripto ", "openCriptoStream start" );
        try {
            if (openStreams() == true && confirmCripto() == true) {

                dos.writeUTF(strumok.encript(OPEN_REQUEST));
                JSONObject response = new JSONObject(strumok.decript(dis.readUTF()));


                if (response.getString("Type").equals("Response")
                    &&response.getString("SubType").equals("Connect")
                    &&response.getString("MessageType").equals("Stream")
                    && response.getString("Status").equals("OK")){

                    Log.i("cripto ", "openCriptoStream true" );
                    return true;
                }else return false;

            }
            else return false;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private  boolean confirmCripto() {
        try {
            Log.i("cripto ", "confirmCripto   start" );
            JSONObject response = new JSONObject(dis.readUTF());

            if (response.getString("Type").equals("Request")
                &&response.getString("SubType").equals("Get")
                && response.getString("MessageType").equals("Stream")
                ){

                String randomText = response.getJSONObject("Body").getString("Msg");
                JSONObject request = new JSONObject();

                request.put("Type","Response");
                request.put("SubType","Get");
                request.put("MessageType","Stream");
                request.put("Body", new JSONObject()
                        .put("Msg", getSHA256(strumok.encript(randomText))));

                Log.i("cripto", "confirm cripto true");

                dos.writeUTF(request.toString());
                return true;

            }else return false;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    private  void openCriptedAuthStream() {    /// place where we will be send message
        try {

            dos.writeUTF(strumok.encript(OPEN_REQUEST));

            JSONObject response = new JSONObject(strumok.decript(dis.readUTF()));


            Log.i("cripto","openCriptedAuthStream  "+ response.toString().equals(new JSONObject(OPEN_RESPONSE).toString()));

           /* if (response.toString().equals(new JSONObject(OPEN_RESPONSE).toString()) == true){
                while (true) {
                    Log.i("mylog", "hi from input service");
                    String mes = dis.readUTF();
                    getMessage(new Message(mes));
                    Log.i("mylog", "input message -- " + mes);
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void authentication(){
        sharedPreferences = getContext().getSharedPreferences("userSettings",Context.MODE_PRIVATE);

        if (openCriptoStream() == true){
            try {

                Log.i("cripto ", "auth start" );

                JSONObject request = new JSONObject();

                request.put("Type","Request");
                request.put("SubType","Get");
                request.put("MessageType","Auth");
                request.put("Body",new JSONObject()
                        .put("Login"
                                , sharedPreferences.getString("username", ""))
                        .put("Password"
                                , sharedPreferences.getString("password", "")));


                dos.writeUTF(strumok.encript(request.toString()));
                String getResponse = strumok.decript(dis.readUTF());
                JSONObject resultResponse = new JSONObject(getResponse);
                String statusResponse = resultResponse.getString("Status");


                if (statusResponse.equals("OK")){

                    SharedPreferences nickSharedPref = getContext().getSharedPreferences("nickname", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = nickSharedPref.edit();
                    String responseNickname = resultResponse.getJSONObject("Body").getString("Nick");
                    Log.i("cripto ", "response nickname  " + responseNickname );

                    if (responseNickname!=null && responseNickname!="")
                    {
                        setAuthResult(true);
                        Log.i("cripto", "authResult "+ getAuthResult());
                        editor.putString("nick", responseNickname);
                        editor.commit();
                    }
                    else   setAuthResult(false);
                    openCriptedAuthStream();
                }else if (statusResponse.equals("Failed")){
                    setAuthResult(false);

                }

            }catch (Exception e){
                Log.i("cripto" ,e.toString());
            }
        } /// open stream -> confirm cripto ->

    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {

        strumok = new Strumok();
        authentication();

       /* try {
            while (true) {
                Log.i("mylog", "hi from input service");
                String mes = dis.readUTF();
                getMessage(new Message(mes));
                Log.i("mylog", "input message -- " + mes);



*//**//*

            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
*/


    }




    private void getMessage(Message msg){
        MessageQueue.getInstance().addInputMessage(msg);
        Log.i("msg", msg.getMessage());
    }


    public  void setAuthResult(boolean authResult) {
        this.authResult = authResult;
    }
    public static boolean getAuthResult() {
        return authResult;
    }



    public static  void closeConnection(){
        try {
            socket.close();
            Log.i("cripto", "Connection closed");
        } catch (IOException e) {
            Log.i("cripto",e.toString());
        }
    }

}
