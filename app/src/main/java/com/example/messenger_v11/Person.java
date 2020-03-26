package com.example.messenger_v11;

import android.content.SharedPreferences;

public class Person {
    
    
    public String nameOfPerson= "";
    public String fragmentOfMessage;
    public int avatarPhotoId;
    public  String sendTo;

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getNameOfPerson() {
        return nameOfPerson;
    }
    public void setNameOfPerson(String nameOfPerson) {
        this.nameOfPerson = nameOfPerson;
    }

    public String getFragmentOfMessage() {
        return fragmentOfMessage;
    }

    public void setFragmentOfMessage(String fragmentOfMessage) {
        this.fragmentOfMessage = fragmentOfMessage;
    }

    public int getAvatarPhotoId() {
        return avatarPhotoId;
    }

    public void setAvatarPhotoId(int avatarPhotoId) {
        this.avatarPhotoId = avatarPhotoId;
    }
}
