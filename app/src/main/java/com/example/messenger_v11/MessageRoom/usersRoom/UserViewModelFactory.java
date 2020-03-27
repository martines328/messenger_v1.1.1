package com.example.messenger_v11.MessageRoom.usersRoom;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    String deleteUserFromUsrList;

    public UserViewModelFactory( String deleteUserFromUsrList) {
        this.deleteUserFromUsrList = deleteUserFromUsrList;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UsersViewModel(deleteUserFromUsrList);
    }
}
