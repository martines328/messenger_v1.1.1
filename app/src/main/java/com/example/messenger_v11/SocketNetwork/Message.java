package com.example.messenger_v11.SocketNetwork;

import org.json.JSONException;
import org.json.JSONObject;

public class Message  implements Comparable<Message>{



    private String message = null;
    private String from = null;
    private String to = null;
    private String body = null;
    private String type = null;
    private String subType = null;
    private String date = null;

    public Message(String message) throws JSONException {
        this.message = message;
        parce();
    }



    public Message(String from, String to, String body) throws JSONException {
        this.from = from;
        this.to = to;
        this.body = body;
        createSimpleMessage();
    }

    private void createSimpleMessage() throws JSONException {

        JSONObject object = new JSONObject();
        object.put("from",from);
        object.put("to",to);
        object.put("body",body);
        message = object.toString();

    }


    private void parce() throws JSONException {
        JSONObject object = new JSONObject(message);
        from = object.getString("from");
        to = object.getString("to");
        body = object.getString("body");

    }



    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getBody() {
        return body;
    }


    @Override
    public int compareTo(Message message) {
        if(message.getMessage().equals(this.message))
            return 1;
        else
            return 0;
    }
}
