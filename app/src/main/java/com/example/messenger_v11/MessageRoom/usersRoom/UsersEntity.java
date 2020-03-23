package com.example.messenger_v11.MessageRoom.usersRoom;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index (value = {"nickname", "key"}, unique = true) })
public class UsersEntity {

    @PrimaryKey(autoGenerate = true)
    int id;



    @ColumnInfo(name = "nickname")
    String nickname;

    @ColumnInfo(name = "key")
    String key;

    @ColumnInfo(name = "sendTo")
    String sendTo;

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
