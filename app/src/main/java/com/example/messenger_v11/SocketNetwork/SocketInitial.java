package com.example.messenger_v11.SocketNetwork;

import android.os.AsyncTask;


import static com.example.messenger_v11.MainActivity.person;

public class SocketInitial extends AsyncTask<Void, Void, Void> {

    private static final String host = "35.224.227.214";
    private static final int port = 5222;

    NetworkService networkService;


    @Override
    protected Void doInBackground(Void... voids) {

         new NetworkService(host,port).start();



        return null;
    }
}
