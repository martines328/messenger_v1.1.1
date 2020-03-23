package com.example.messenger_v11.MessageRoom.usersRoom;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import static com.example.messenger_v11.MainActivity.usersDao;

public class UsersRepository {

   private LiveData<List<UsersEntity>> allUsersList;


    public UsersRepository() {

        allUsersList = usersDao.getAllUsers();


    }



    LiveData<List<UsersEntity>> getAllUsers(){
        return allUsersList;
    }

}
