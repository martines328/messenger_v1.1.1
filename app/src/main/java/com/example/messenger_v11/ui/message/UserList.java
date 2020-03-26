package com.example.messenger_v11.ui.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.user_list_fragment, container, false);

        SocketConnectingManager.getInstance();


        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();


        userRecyclerView = root.findViewById(R.id.recyclerViewMessage);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userRecyclerView.setItemAnimator(itemAnimator);


        //mUsersViewModel = new ViewModelProvider(, null).get(UsersViewModel.class);

        userListAdapter = new UserListAdapter(getContext());

        mUsersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
      //  userRecyclerView.setAdapter(userListAdapter);




        mUsersViewModel.getmAllUserList().observe(this, new Observer<List<UsersEntity>>() {
            @Override
            public void onChanged(List<UsersEntity> usersEntities) {

                userListAdapter.setUSers(usersEntities);
                userRecyclerView.setAdapter(userListAdapter);


            }
        });


       // LiveData<List<UsersEntity>> allListMessage =  usersDao.getAllUsers();


       // userListAdapter = new UserListAdapter( allListMessage,getContext());




        return root;
    }
}