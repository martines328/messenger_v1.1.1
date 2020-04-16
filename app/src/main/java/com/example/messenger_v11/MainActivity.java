package com.example.messenger_v11;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.messenger_v11.Authorization.AuthActivity;
import com.example.messenger_v11.Cipher.Aes256;
import com.example.messenger_v11.Cipher.AesInitManager;
import com.example.messenger_v11.MessageRoom.MessageDao;
import com.example.messenger_v11.MessageRoom.MessageDataBase;
import com.example.messenger_v11.MessageRoom.usersRoom.UsersDao;
import com.example.messenger_v11.MessageRoom.usersRoom.UsersEntity;
import com.example.messenger_v11.SocketNetwork.SocketConnectingManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.messenger_v11.Cipher.Aes256.encrypt;
import static com.example.messenger_v11.SocketNetwork.NetworkService.closeConnection;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    public final static String TAG = "mylogs";
    public static Context appContext;
    public static MessageDao messageDao;
    public static UsersDao usersDao;
    public static Context context;
    SharedPreferences sharedPreferences;
    public final static Person person = new Person();

    Utils utils;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE
                ,WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_main);

        createCopyOfObject();
        AesInitManager.getInstance();
        loginDistribution();
        initDB();
        drawerLayout();


        utils.setNameOfPerson();


        createFAB_Dioalog();




    }


    void createCopyOfObject(){
        context = MainActivity.this;
        appContext = this;
        utils = new Utils(this);
    }




    private void loginDistribution(){

        if (utils.checkUserLogin() == false) {

            Intent intent = new Intent(context, AuthActivity.class);
            context.startActivity(intent);
        }else {
            SocketConnectingManager.getInstance();
        }
    }




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

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_message, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }



    public void createFAB_Dioalog(){

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
           LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
           View layout = layoutInflater.inflate(R.layout.dialog_add_user_fat
                   , findViewById(R.id.enterUser_edittext));
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
            EditText editText = layout.findViewById(R.id.enterUser_edittext);
            builder.setView(layout);


            builder.setTitle(R.string.fatButtonText);
            builder.setMessage(R.string.fatButtonLText);



            builder.setPositiveButton(R.string.fatPositive, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    UsersEntity usersEntity = new UsersEntity();

                    String userNicknameET = editText.getText().toString();
                    try {
                        String encryptedUSerNAme = encrypt(userNicknameET);

                    Log.i("ciphertest", encryptedUSerNAme);

                    if (userNicknameET.equals("") || userNicknameET == null){
                        dialog.cancel();
                        Toast.makeText(context, "Enter valid name", Toast.LENGTH_SHORT).show();
                    } else {
                        usersEntity.setNickname(encryptedUSerNAme);
                        usersEntity.setSendTo(encryptedUSerNAme);
                        usersDao.insertUsersToDB(usersEntity);
                        Toast.makeText(context, userNicknameET, Toast.LENGTH_SHORT).show();
                    }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });

            builder.setNegativeButton(R.string.fatNegative, (dialog, which) -> dialog.cancel());
            builder.create();
            builder.show();

        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

            utils.clearNicknameSharedPref();
            utils.clearUsernamePasswordSharePref();
            Toast.makeText(appContext, "logout", Toast.LENGTH_SHORT).show();
            SocketConnectingManager.clearSingletonConnection();
            closeConnection();
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



    @RequiresApi(api = Build.VERSION_CODES.O)
    boolean checkCopyName(String nameTocheck){
        boolean[] checkresult  = {true};

        LiveData<List<UsersEntity>> allusername = usersDao.getAllUsers();
        allusername.observe(this, usersEntities -> {

            for (UsersEntity userEntity: usersEntities) {
                String name = userEntity.getNickname();
                String decryptName = Aes256.decrypt(name);

                if (decryptName.equals(nameTocheck)) checkresult[0] = true;
                else checkresult[0] = false;
            }
        });



        Log.i("checkname", String.valueOf(checkresult[0]));
        return checkresult[0];




    }




    public static Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }






}
