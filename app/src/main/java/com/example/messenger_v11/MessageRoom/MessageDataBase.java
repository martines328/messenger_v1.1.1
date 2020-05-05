package com.example.messenger_v11.MessageRoom;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.messenger_v11.MessageRoom.usersRoom.UsersDao;
import com.example.messenger_v11.MessageRoom.usersRoom.UsersEntity;

@Database(entities = {MessageInfoEntity.class, UsersEntity.class}, version = 4, exportSchema = false)
public abstract class MessageDataBase  extends RoomDatabase {

    private static String messageDBname = "messageDBname";
    private static String usersDBname = "usersDBname";

    public static MessageDataBase INSTANCE;


    public abstract MessageDao messageDao();
    public abstract UsersDao usersDao();


    public static  MessageDataBase getInstance(Context context){
        if (INSTANCE == null ) {
            INSTANCE =  Room.databaseBuilder(context,
                    MessageDataBase.class, messageDBname)
                    .allowMainThreadQueries()
                    .addMigrations(migration_3_4)
                    .build();
        }
        return INSTANCE;
    }

    static final Migration migration_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
           database.execSQL("ALTER TABLE users " + " ADD COLUMN sendTo TEXT ");


          // database.execSQL("ALTER TABLE message" + "ADD COLUMN ");
        }
    };



}
