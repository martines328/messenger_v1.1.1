package com.example.messenger_v11.SocketNetwork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.messenger_v11.MainActivity;
import com.example.messenger_v11.R;
import com.example.messenger_v11.Utils;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import static com.example.messenger_v11.MainActivity.context;
import static com.example.messenger_v11.MainActivity.getContext;
import static com.example.messenger_v11.Utils.setNameOfPerson;

public class NetworkService extends Thread {
    public static final String log = "cripto";
    SharedPreferences sharedPreferences;
    public static OpenRoomManager openRoomManager;
    public static CreateRoomManager createRoomManager;
    MessageAnalizer messageAnalizer;
    Utils utils;


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






    static String host;
    static int port;


    protected static SSLSocket socket ;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;

    public NetworkService(String host, int port){
        try {


          this.port = port;
          this.host = host;

            socket = createSSLSocket(host,port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            socket.setKeepAlive(true);
           // socket.setSoTimeout(20000);
            createRoomManager = new CreateRoomManager(socket);
            messageAnalizer = new MessageAnalizer();
            utils = new Utils();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    SSLSocket createSSLSocket(String host, int port){
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream inputStream = context.getResources().openRawResource(R.raw.client);
        keyStore.load(inputStream,"client".toCharArray());
        inputStream.close();

        KeyManagerFactory keyManagerFactory  = KeyManagerFactory.getInstance("X509");
        keyManagerFactory.init(keyStore,"client".toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManager[] tm = tmf.getTrustManagers();
        sslContext.init(keyManagerFactory.getKeyManagers(),tm,new SecureRandom());

        SSLSocketFactory sslSF = sslContext.getSocketFactory();
        SSLSocket s = (SSLSocket) sslSF.createSocket(host,port);
        Log.i("test","Test");
        s.startHandshake();
            Log.i("mylogsocket", s.toString());


        return s;



        } catch (Exception e) {
            e.printStackTrace();
            Log.i("sslsocket", e.toString());
        }
        return null;
    }



    private boolean openStreams() throws IOException {
        try {


            Log.i("cripto ", "openStream  start " );

            dos.writeUTF(OPEN_REQUEST );
            dos.flush();
            JSONObject response = new JSONObject(dis.readUTF());
            if (response.getString("Type").equals("Response")
                    && response.getString("SubType").equals("Connect")
                    && response.getString("MessageType").equals("Stream")
                    && response.getString("Status").equals("OK")){
                Log.i("cripto ", "openStream true" );
                return true;
            }else return false;






        }catch (Exception e){
            Log.i("cripto",e.toString());
        }
        return false;
    }


    private  boolean openCriptedAuthStream() {    /// place where we will be send message
        try {

            dos.writeUTF(OPEN_REQUEST );
            dos.flush();
            JSONObject response = new JSONObject(dis.readUTF());
            if (response.getString("Type").equals("Response")
                    && response.getString("SubType").equals("Connect")
                    && response.getString("MessageType").equals("Stream")
                    && response.getString("Status").equals("OK")){
                Log.i("cripto ", "openStream true" );
                new MessageSenderService(dos).start();


                while (true) {
                    String mes = dis.readUTF();
                    Log.i("mylog", "input message -- " + mes);
                    messageAnalizer.analizeMessage(mes);



                }

            }else return false;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }






    private void authentication(){
        sharedPreferences = getContext().getSharedPreferences("userSettings",Context.MODE_PRIVATE);

        try {
            if (openStreams() == true){


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


                dos.writeUTF(request.toString());
                dos.flush();
                String getResponse = dis.readUTF();
                JSONObject resultResponse = new JSONObject(getResponse);
                Log.i("cripto", " response AUTh " + resultResponse );
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
                        setNameOfPerson();
                        openCriptedAuthStream();
                    }
                    else   setAuthResult(false);

                }else if (statusResponse.equals("Failed")){
                    setAuthResult(false);

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



   /* @Override
    public void run() {



        try {



        authentication();
        Log.i("network", "auth network");



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/

    @Override
    public void run() {
        super.run();


        authentication();
        Log.i("network", "auth network");


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
