package com.example.messenger_v11.MessageRoom.usersRoom;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import static com.example.messenger_v11.MainActivity.usersDao;

public class UsersRepository {

   private LiveData<List<UsersEntity>> allUsersList;
   private LiveData<UsersEntity> mDeleteUserFromUserList;
    private String deleteUser;


    public UsersRepository(String deleteUser) {

        this.deleteUser = deleteUser;
        allUsersList = usersDao.getAllUsers();
      //  mDeleteUserFromUserList = usersDao.deleteUsersFromDB(deleteUser);


    }



    LiveData<UsersEntity> deleteUserFromUserList(){return  mDeleteUserFromUserList;}
    LiveData<List<UsersEntity>> getAllUsers(){
        return allUsersList;
    }

}
