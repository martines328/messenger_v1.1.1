package com.example.messenger_v11.SocketNetwork;

public class SocketConnectingManager {




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


        SocketInitial socketInitial = new SocketInitial();
        socketInitial.execute();








    }




}
