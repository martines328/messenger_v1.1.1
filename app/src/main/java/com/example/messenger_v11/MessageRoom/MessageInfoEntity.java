package com.example.messenger_v11.MessageRoom;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "message")
public class MessageInfoEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;


    @ColumnInfo(name = "fromPeople")
    public String fromPeople;

    @ColumnInfo(name = "toPeople")
    public String toPeople;


    @ColumnInfo(name = "messages")
    public String messages;

   /* @ColumnInfo(sendToName = "messagetype")
    public int messagetype;

    public int getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(int messagetype) {
        this.messagetype = messagetype;
    }*/

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromPeople() {
        return fromPeople;
    }

    public void setFromPeople(String fromPeople) {
        this.fromPeople = fromPeople;
    }

    public String getToPeople() {
        return toPeople;
    }

    public void setToPeople(String toPeople) {
        this.toPeople = toPeople;
    }
}
