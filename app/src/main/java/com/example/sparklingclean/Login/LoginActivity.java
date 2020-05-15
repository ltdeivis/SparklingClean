package com.example.sparklingclean.Login;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sparklingclean.DB.DB_Interface;
import com.example.sparklingclean.DB.User;
import com.example.sparklingclean.Firebase.DB.DatabaseTester;
import com.example.sparklingclean.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupDB();

        loginBtn  = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginBtnHandler(v);
            }
        });
    }

    private void loginBtnHandler(View v) {
        username = ((EditText) findViewById(R.id.usernameTxt)).getText().toString();
        password = ((EditText) findViewById(R.id.passowrdTxt)).getText().toString();

        DatabaseTester firebaseTester = new DatabaseTester();

//        DoLogin doLogin = new DoLogin();
//        doLogin.execute();

//        Authentication authentication = new Authentication(username, password);
//        if(authentication.loginUser()) {
//            User user = authentication.getCurrentUser();
//            loginBtn.setText(user.getFirstName()); //TODO : DEBUG ONLY DELETE
//            //Show main gui
//        }
    }

    private class DoLogin extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            Authentication authentication = new Authentication(username, password);
            if(authentication.loginUser()) {
                User user = authentication.getCurrentUser();
                loginBtn.setText(user.getFirstName()); //TODO : DEBUG ONLY DELETE
                //Show main gui
                return "Login success";
            }
            return "Login failed";
        }
    }

    private void setupDB() {
        try {
            InputStream inputStream = getAssets().open("DB/db-info.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            if(inputStream != null) {
                String[] content = reader.readLine().split(",");

                DB_Interface db_interface = DB_Interface.DB_Interface();
                db_interface.setupConnection(content[0],content[1],content[2]);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
