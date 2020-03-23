package com.example.messenger_v11.MessageRoom;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ConversationViewModelFactory implements ViewModelProvider.Factory {


    Context context;
    String sendToName;

    public ConversationViewModelFactory(Context context, String sendToName) {
        this.context = context;
        this.sendToName = sendToName;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ConversationViewModel(context, sendToName);
    }
}
