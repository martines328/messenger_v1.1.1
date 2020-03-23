package com.example.messenger_v11;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.messenger_v11.Authorization.AuthActivity;
import com.example.messenger_v11.MessageRoom.MessageDao;
import com.example.messenger_v11.MessageRoom.MessageDataBase;
import com.example.messenger_v11.MessageRoom.usersRoom.UsersDao;
import com.example.messenger_v11.MessageRoom.usersRoom.UsersEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    public final static String TAG = "mylogs";
    public static Context appContext;
    public static MessageDao messageDao;
    public static UsersDao usersDao;
    public Context context = MainActivity.this;
    public final static Person person = new Person();
    SharedPreferences sharedPreferences;
    String login, password;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext = this;

        person.setNameOfPerson("test2");
        person.setSendTo("test3");



        checkUserLogin();


        initDB();


        drawerLayout();
        createFATAlertDioalog();




    }




  /*  private void makeNotification() {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context,"conversation")
                        .setContentTitle("You have new Message")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);



       *//* NotificationManagerCompat notificationManage = NotificationManagerCompat.from(context);
        notificationManage.notify(1,builder.build());
*//*
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }
*/


    private void initDB(){
        if (usersDao == null){
            usersDao = MessageDataBase.getInstance(this).usersDao();
            Log.i("initDB" , "userInitDB");

        }

        if (messageDao == null) {
            messageDao = MessageDataBase.getInstance(this).messageDao();

            Log.i("initDB", "MessageinitDB");

        }

    }


    protected void drawerLayout(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_message, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);






    }



    public void createFATAlertDioalog(){



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
               View layout = layoutInflater.inflate(R.layout.dialog_add_user_fat
                       , findViewById(R.id.enterUser_edittext));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                EditText editText = layout.findViewById(R.id.enterUser_edittext);
                builder.setView(layout);


                builder.setTitle(R.string.fatButtonText);
                builder.setMessage(R.string.fatButtonLText);



                builder.setPositiveButton(R.string.fatPositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        UsersEntity usersEntity = new UsersEntity();

                        String userNicknameET = editText.getText().toString();

                        usersEntity.setNickname(userNicknameET);
                        usersEntity.setSendTo(userNicknameET);
                        usersDao.insertUsersToDB(usersEntity);
                        Toast.makeText(context, userNicknameET, Toast.LENGTH_SHORT).show();




                    }
                });

                builder.setNegativeButton(R.string.fatNegative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });


                builder.create();
                builder.show();

            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();

        if (id == R.id.action_logout){

            Toast.makeText(appContext, "logout", Toast.LENGTH_SHORT).show();
            sharedPreferences  =getSharedPreferences("userSettings",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("username");
            editor.remove("password");
            editor.commit();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id  = menuItem.getItemId();

        if (id == R.id.nav_message){
            Toast.makeText(this, "you chose home item menu ", Toast.LENGTH_SHORT).show();
        }


        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    boolean checkUserLogin(){
        sharedPreferences = getSharedPreferences("userSettings", MODE_PRIVATE);
        login = sharedPreferences.getString("username", "");
        password =  sharedPreferences.getString("password","");
        boolean loginResult = false;
        if (login.equals("") || password.equals("")
                || login == null || password == null){
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            return false;
        }
        else{

            return true;
        }


    }




}
