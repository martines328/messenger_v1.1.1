package com.example.messenger_v11.MessageRoom.usersRoom;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class UsersViewModel  extends ViewModel {

    LiveData<List<UsersEntity>> mAllUserList;
    UsersRepository usersRepository;


    public UsersViewModel() {

        usersRepository = new UsersRepository();

        mAllUserList = usersRepository.getAllUsers();


    }


    public LiveData<List<UsersEntity>> getmAllUserList(){
        return  mAllUserList;
    }



}
