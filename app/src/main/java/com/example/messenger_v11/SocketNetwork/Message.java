package com.example.messenger_v11.SocketNetwork;

import android.util.Log;
import android.widget.DatePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Message  implements Comparable<Message>{



    private String Msg = null;
    private String From = null;
    private String To = null;
    private String Body = null;
    private String Type = null;
    private String SubType = null;
    private String Date = null;
    String message;

    public Message(String message) throws JSONException {
        this.Msg = message;
        parce(message);
    }



    public Message(String from, String to, String msg) throws JSONException {
        this.From = from;
        this.To = to;
        this.message = msg;
        createSimpleMessage();
    }

    private void createSimpleMessage() throws JSONException {

        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("SubType","Put");
        object.put("MessageType","Message");
        object.put("Body",new JSONObject()
                .put("From", From)
                .put("To", To)
                .put("Date", "23456789")
                .put("Msg", message));
        Msg = object.toString();
        Log.i("messageLog", Msg);

    }


    private void parce(String msg) throws JSONException {
        JSONObject object = new JSONObject(msg);
        From = object.getJSONObject("Body").getString("From");
        To = object.getJSONObject("Body").getString("To");
        message = object.getJSONObject("Body").getString("Msg");
        Log.i("messageLog parce ", object.toString());

    }



    public String getMessage() {
        return Msg;
    }

    public String getFrom() {
        return From;
    }

    public String getTo() {
        return To;
    }

    public String getBody() {
        return message;
    }


    @Override
    public int compareTo(Message message) {
        if(message.getMessage().equals(this.Msg))
            return 1;
        else
            return 0;
    }
}
