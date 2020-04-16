package com.example.messenger_v11.ui.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger_v11.MessageRoom.usersRoom.UserViewModelFactory;
import com.example.messenger_v11.MessageRoom.usersRoom.UsersEntity;
import com.example.messenger_v11.MessageRoom.usersRoom.UsersViewModel;
import com.example.messenger_v11.R;
import com.example.messenger_v11.SocketNetwork.SocketConnectingManager;

import java.util.List;

public class UserList extends Fragment {

    TextView newtext;

    private static RecyclerView userRecyclerView;
    private UserListAdapter userListAdapter;
    UsersViewModel mUsersViewModel;
    String deleteUserFromDB;
    UsersViewModel usersViewModel;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.user_list_fragment, container, false);




        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();


        userRecyclerView = root.findViewById(R.id.recyclerViewMessage);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userRecyclerView.setItemAnimator(itemAnimator);



        userListAdapter = new UserListAdapter(getContext());
        UserViewModelFactory factory = new UserViewModelFactory(userListAdapter.setDeleteName());
        mUsersViewModel = ViewModelProviders.of(this,factory).get(UsersViewModel.class);


        mUsersViewModel.getmAllUserList().observe(this, new Observer<List<UsersEntity>>() {
            @Override
            public void onChanged(List<UsersEntity> usersEntities) {

                userListAdapter.setUSers(usersEntities);
                userRecyclerView.setAdapter(userListAdapter);


            }
        });




        return root;
    }



    void deleteSelectuserFromList(){


        UserViewModelFactory factory = new UserViewModelFactory(userListAdapter.setDeleteName());
        usersViewModel = ViewModelProviders.of(this, factory).get(UsersViewModel.class);

        usersViewModel.getDeleteSelectUserFromUserList().observe(this, new Observer<UsersEntity>() {
            @Override
            public void onChanged(UsersEntity usersEntity) {

            }
        });




    }








}