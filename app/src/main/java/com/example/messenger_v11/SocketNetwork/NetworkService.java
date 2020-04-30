package com.example.messenger_v11.SocketNetwork;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.messenger_v11.R;

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

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.example.messenger_v11.MainActivity.context;
import static com.example.messenger_v11.MainActivity.getContext;
import static com.example.messenger_v11.Utils.setNameOfPerson;

public class NetworkService extends Thread {
    public static final String log = "cripto";
    SharedPreferences sharedPreferences;
    public static OpenRoomManager openRoomManager;
    public static CreateRoomManager createRoomManager;
    MessageAnalizer messageAnalizer;


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
            createRoomManager = new CreateRoomManager(socket);
            messageAnalizer = new MessageAnalizer();


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
        s.startHandshake();
            Log.i("mylogsocket", s.toString());


        return s;



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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






        }catch (Exception e){
            Log.i("cripto",e.toString());
        }
        return false;
    }


    private  boolean openCriptedAuthStream() {    /// place where we will be send message
        try {

            dos.writeUTF(OPEN_REQUEST );
            JSONObject response = new JSONObject(dis.readUTF());
            if (response.getString("Type").equals("Response")
                    && response.getString("SubType").equals("Connect")
                    && response.getString("MessageType").equals("Stream")
                    && response.getString("Status").equals("OK")){
                Log.i("cripto ", "openStream true" );
                new MessageSenderService(dos).start();

                //Log.i("messageservice", "send message");

                while (true) {
                    Log.i("mylog", "hi from input service");
                    String mes = dis.readUTF();
                    //getMessage(new Message(mes));
                    messageAnalizer.analizeMEssage(mes);

                   // Log.i("mylog", "input message -- " + mes);
                }



            }else return false;




        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }




   /* @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Observable<Boolean> authentication(){
        return Observable.create( emitter -> {

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

                            Log.i("cripto", "authResult "+ getAuthResult());
                            editor.putString("nick", responseNickname);
                            editor.commit();
                            setNameOfPerson();
                            openCriptedAuthStream();
                            emitter.onNext(true);
                        }
                        else   emitter.onNext(false);

                    }else if (statusResponse.equals("Failed") )emitter.onNext(false);

                }
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onError(e);
            }


        });

    }*/


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {



        try {



        authentication();



        } catch (Exception e) {
            e.printStackTrace();
        }

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
