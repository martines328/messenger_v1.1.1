package com.example.messenger_v11.Authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messenger_v11.R;

public class AuthActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        username  = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        sharedPreferences = getSharedPreferences("userSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor  =sharedPreferences.edit();
        login.setEnabled(true);

        login.setOnClickListener( view -> {


            if (username.getText().toString().equals("test")){

                editor.putString("username", username.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.commit();

                username.getText().clear();
                password.getText().clear();
                finish();
            }else {
                Toast.makeText(this, "Retry please", Toast.LENGTH_SHORT).show();
            }


        });



    }
}
