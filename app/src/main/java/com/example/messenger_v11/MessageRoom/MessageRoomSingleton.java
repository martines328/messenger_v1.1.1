package com.example.messenger_v11.MessageRoom;

import android.content.Context;
import android.util.Log;

public class MessageRoomSingleton {

    Context context ;
    public static MessageDao dao;
    private static volatile MessageRoomSingleton INSTANCE;





    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private MessageRoomSingleton() {
    }




    public static MessageRoomSingleton getINSTANCE(){
        MessageRoomSingleton localInstance = INSTANCE;

        if (localInstance == null){
            synchronized (MessageRoomSingleton.class){
                localInstance = INSTANCE;
                if (localInstance == null){



                    localInstance = INSTANCE = new MessageRoomSingleton();
                   Log.i("getInstance", "GETINSTANCE");
                   // initiateDB(getINSTANCE().getContext());

                }
            }
        }


        return localInstance;

    }

}


