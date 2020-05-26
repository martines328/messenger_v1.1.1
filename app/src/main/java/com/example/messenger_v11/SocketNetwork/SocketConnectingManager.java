package com.example.messenger_v11.SocketNetwork;

import android.util.Log;

public class SocketConnectingManager {


    private static final String host = "35.224.227.214";
    private static final int port = 5222;


    private static volatile SocketConnectingManager INSTANCE;


    private SocketConnectingManager() {
    }

    public static SocketConnectingManager getInstance(){


        SocketConnectingManager localInstance = INSTANCE;

        if (localInstance == null){
            synchronized (SocketConnectingManager.class){
                localInstance = INSTANCE;
                if (localInstance == null){

                    initSingleton();
                }
            }
        }


        return localInstance;
    }





    private static void initSingleton(){

        INSTANCE = new SocketConnectingManager();

        Log.i("singleton", INSTANCE.toString());

        SocketInitial socketInitial = new SocketInitial();
        socketInitial.execute();









    }

    public static void clearSingletonConnection(){
        INSTANCE = null;
        //Log.i("singleton", INSTANCE.toString());

    }




}
