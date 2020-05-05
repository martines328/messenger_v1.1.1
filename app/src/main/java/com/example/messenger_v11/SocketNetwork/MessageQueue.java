package com.example.messenger_v11.SocketNetwork;

import android.util.Log;

import com.example.messenger_v11.MessageRoom.MessageInfoEntity;

import java.util.PriorityQueue;

import static com.example.messenger_v11.MainActivity.messageDao;

public class MessageQueue {

    private static volatile MessageQueue instance = null;

    private PriorityQueue<Message> inputMessageQueue = null;
    private PriorityQueue<Message> outputMessageQueue = null;


    private MessageQueue(){
        inputMessageQueue = new PriorityQueue<Message>();
        outputMessageQueue = new PriorityQueue<Message>();
    }

    public static MessageQueue getInstance(){
        MessageQueue localInstance = instance;
        if(localInstance == null){
            synchronized (MessageQueue.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new MessageQueue();
                }
            }
        }
        return localInstance;
    }

    public  Message getInputMessage(){
        Message msg = inputMessageQueue.remove();

        Log.i("mylog", "message is " + msg.getMessage());
        return msg;
    }

    public  void addInputMessage(Message msg){
        Log.i("messageLog","add input Message  "+ msg.toString());
        MessageInfoEntity entity = new MessageInfoEntity();
        entity.setFromPeople(msg.getFrom());
        String getfrom = msg.getFrom();
        String getto = msg.getTo();
        String msssg = msg.getBody();
        entity.setToPeople(msg.getTo());
        entity.setMessages(msg.getBody());
        //entity.setMessagetype(1);
        messageDao.insertMessage(entity);
        Log.i("mylog", "message from output message " + msg.getMessage());
        Log.i("tetsbd", "message -- " + msg.getBody());
        Log.i("tetsbd", "from -- " + msg.getFrom());
        Log.i("tetsbd", "to --" + msg.getTo());
        inputMessageQueue.add(msg);
    }

    public boolean isEmptyInputMessage(){
        return inputMessageQueue.isEmpty();
    }


    public  Message getOutputMessage(){
        Message msg = outputMessageQueue.remove();
        return msg;
    }

    public  void addOutputMessage(Message msg){

        outputMessageQueue.add(msg);
    }

    public boolean isEmptyOutputMessage(){
        return outputMessageQueue.isEmpty();
    }


}
