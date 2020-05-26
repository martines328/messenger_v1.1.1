package com.example.messenger_v11.SocketNetwork;

import android.os.AsyncTask;
import android.util.Log;



public class SocketInitial extends AsyncTask<Void, Void, Void> {

    private static final String host = "64.225.60.235";
    private static final int port = 5222;

    NetworkService networkService;


    @Override
    protected Void doInBackground(Void... voids) {

        Log.i("network", "network service");
           new  NetworkService(host,port).start();



        return null;
    }
}
