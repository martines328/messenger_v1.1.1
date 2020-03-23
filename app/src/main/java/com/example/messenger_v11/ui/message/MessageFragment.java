package com.example.messenger_v11.ui.message;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger_v11.MessageRoom.usersRoom.UsersEntity;
import com.example.messenger_v11.MessageRoom.usersRoom.UsersViewModel;
import com.example.messenger_v11.Person;
import com.example.messenger_v11.R;
import com.example.messenger_v11.SocketNetwork.SocketConnectingManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.example.messenger_v11.MainActivity.usersDao;

public class MessageFragment extends Fragment {

    private MessageViewModel messageViewModel;
    TextView newtext;

    private static RecyclerView messagerecyclerOfView;
    private UserListAdapter userListAdapter;
    UsersViewModel mUsersViewModel;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_message, container, false);

        SocketConnectingManager.getInstance();



        messagerecyclerOfView = root.findViewById(R.id.recyclerViewMessage);
        messagerecyclerOfView.setLayoutManager(new LinearLayoutManager(getContext()));


        //mUsersViewModel = new ViewModelProvider(, null).get(UsersViewModel.class);

        userListAdapter = new UserListAdapter(getContext());

        mUsersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
      //  messagerecyclerOfView.setAdapter(userListAdapter);




        mUsersViewModel.getmAllUserList().observe(this, new Observer<List<UsersEntity>>() {
            @Override
            public void onChanged(List<UsersEntity> usersEntities) {

                userListAdapter.setUSers(usersEntities);
                messagerecyclerOfView.setAdapter(userListAdapter);


            }
        });


       // LiveData<List<UsersEntity>> allListMessage =  usersDao.getAllUsers();


       // userListAdapter = new UserListAdapter( allListMessage,getContext());




        return root;
    }
}