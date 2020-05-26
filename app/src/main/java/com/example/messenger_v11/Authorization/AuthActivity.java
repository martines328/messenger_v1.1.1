package com.example.messenger_v11.Authorization;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.messenger_v11.R;
import com.example.messenger_v11.SocketNetwork.SocketConnectingManager;
import com.example.messenger_v11.Utils;

import butterknife.*;

import static com.example.messenger_v11.SocketNetwork.NetworkService.getAuthResult;
import static com.example.messenger_v11.Utils.getSHA256;


public class AuthActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Utils utils;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        utils = new Utils(this);


        _loginButton.setOnClickListener(v -> login());


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(AuthActivity.this,
                R.style.AuthDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = getSHA256( _passwordText.getText().toString());



        _emailText.getText().clear();
        _passwordText.getText().clear();

        sharedPreferences = getSharedPreferences("userSettings", MODE_PRIVATE);
        editor  =sharedPreferences.edit();
        editor.putString("username", email);
        editor.putString("password", password);
        editor.commit();

        new android.os.Handler().postDelayed(
                () -> {
                    try {
                        SocketConnectingManager.getInstance();
                        Thread.sleep(4000);

                    boolean test = getAuthResult();

                    if (test == true) {
                        onLoginSuccess();
                        progressDialog.dismiss();

                    }else {

                         sharedPreferences = getSharedPreferences("userSettings",MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences.edit();
                        editor1.remove("username");
                        editor1.remove("password");
                        editor1.commit();
                        SocketConnectingManager.clearSingletonConnection();
                        progressDialog.dismiss();
                        onLoginFailed();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        }

                }, 2000);
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String password = _passwordText.getText().toString();

        if (password.isEmpty() || password.length() <= 2 || password.length() >= 16) {
            _passwordText.setError("between 8 and 16 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }



}
