package com.example.messenger_v11.MessageRoom.usersRoom;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class UsersViewModel  extends ViewModel {

    LiveData<List<UsersEntity>> mAllUserList;
    LiveData<UsersEntity> deleteSelectUserFromUserList;
    UsersRepository usersRepository;

    String deleteUserFromUsrList;


    public UsersViewModel( String deleteUserFromUsrList) {
        this.deleteUserFromUsrList =deleteUserFromUsrList;

        usersRepository = new UsersRepository(deleteUserFromUsrList);

        mAllUserList = usersRepository.getAllUsers();
        deleteSelectUserFromUserList = usersRepository.deleteUserFromUserList();



    }


    public LiveData<List<UsersEntity>> getmAllUserList(){
        return  mAllUserList;
    }

    public LiveData<UsersEntity> getDeleteSelectUserFromUserList(){return deleteSelectUserFromUserList;}


}
