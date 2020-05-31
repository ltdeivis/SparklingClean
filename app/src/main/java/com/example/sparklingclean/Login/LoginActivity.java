package com.example.sparklingclean.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sparklingclean.DB.Appointment;
import com.example.sparklingclean.DB.DB_Interface;
import com.example.sparklingclean.DB.User;
import com.example.sparklingclean.Firebase.DB.ClientHandler;
import com.example.sparklingclean.Firebase.DB.DatabaseTester;
import com.example.sparklingclean.Firebase.DB.FirebaseActivity;
import com.example.sparklingclean.Firebase.DB.FirebaseListener;
import com.example.sparklingclean.Firebase.DB.UserHandler;
import com.example.sparklingclean.MainActivity;
import com.example.sparklingclean.MainGUI.AppontmentScreen.AppointmentActivity;
import com.example.sparklingclean.Providers.UserProvider;
import com.example.sparklingclean.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;
    private Button nextBtn;
    private Button finishBtn;
    private Button registerBtn;
    private Button returnBtn;
    private String loginUsername;
    private String loginPassword;
    private String username;
    private String password;
    private Boolean isRegister1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginBtnHandler(v);
            }
        });

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerBtnHandler();
            }
        });
    }

    private void loginBtnHandler(View v) {
        loginUsername = ((EditText) findViewById(R.id.usernameTxt)).getText().toString();
        loginPassword = ((EditText) findViewById(R.id.passowrdTxt)).getText().toString();

        final Authentication authentication = new Authentication(loginUsername, loginPassword);

        FirebaseListener loginListener = new FirebaseListener() {
            @Override
            public void onLoadFinish() {
                if(authentication.getCurrentUser() != null) {
                    Log.d("Login", "Logged in as " + authentication.getCurrentUser().firstName);
                    //startActivity(new Intent(LoginActivity.this, FirebaseActivity.class));
                    startActivity(new Intent(LoginActivity.this, AppointmentActivity.class));
                }
                else {
                    Log.d("Login", "Login Failed");
                }
            }
        };

        authentication.addDataListener(loginListener);
        authentication.loginUser();
    }

    private void registerBtnHandler(){
        setContentView(R.layout.activity_register1);
        isRegister1 = true;

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextBtnHandler();
            }
        });

        returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnBtnHandler();
            }
        });
    }

    private void nextBtnHandler(){
        if(validate()){
            username = ((EditText) findViewById(R.id.registerUsernameTxt)).getText().toString();
            password = ((EditText) findViewById(R.id.registerPasswordTxt)).getText().toString();

            final UserHandler handler = new UserHandler();

            FirebaseListener usernameCheckListener = new FirebaseListener() {
                @Override
                public void onLoadFinish() {
                    if(handler.getUser() != null) {
                        Log.d("Register", "User already exists with that method");
                    }
                    else {
                        setContentView(R.layout.activity_register2);
                        isRegister1 = false;

                        finishBtn = findViewById(R.id.finishBtn);
                        finishBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finishBtnHandler();
                            }
                        });

                        returnBtn = findViewById(R.id.returnBtn);
                        returnBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                returnBtnHandler();
                            }
                        });
                    }
                }
            };

            handler.addDataLoadListener(usernameCheckListener);
            handler.findUser(username);
        }
   }

    private boolean validate() {
        if(isRegister1){
            String password = ((EditText) findViewById(R.id.registerPasswordTxt)).getText().toString();
            String confirmPw = ((EditText) findViewById(R.id.confirmPasswordTxt)).getText().toString();

            if(password.equals(confirmPw)){
                return true;
            } else {
                Toast.makeText(getApplicationContext(),"Password do not match.",Toast.LENGTH_SHORT).show();
                TextView pswLbl = (TextView) findViewById(R.id.enterPasswordLbl);
                TextView cpswLbl = (TextView) findViewById(R.id.confirmPasswordLbl);

                pswLbl.setTextColor(Color.RED);
                cpswLbl.setTextColor(Color.RED);
                return false;
            }
        } else {
            String firstName = ((EditText) findViewById(R.id.registerFn)).getText().toString();
            String lastName = ((EditText) findViewById(R.id.registerLn)).getText().toString();
            String email = ((EditText) findViewById(R.id.registerEmail)).getText().toString();
            String dob = ((EditText) findViewById(R.id.registerDoB)).getText().toString();
            String address = ((EditText) findViewById(R.id.registerAddress)).getText().toString();
            String telNum = ((EditText) findViewById(R.id.registerTelNum)).getText().toString();

            if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || dob.isEmpty()
                || address.isEmpty() || telNum.isEmpty()) {
                return false;
            }
            
            return true;
        }
    }

    private void finishBtnHandler(){
        if(validate()) {
            Log.d("Register", "input validated");
            String type = "client";
            String firstName = ((EditText) findViewById(R.id.registerFn)).getText().toString();
            String lastName = ((EditText) findViewById(R.id.registerLn)).getText().toString();
            String email = ((EditText) findViewById(R.id.registerEmail)).getText().toString();
            String dob = ((EditText) findViewById(R.id.registerDoB)).getText().toString();
            String address = ((EditText) findViewById(R.id.registerAddress)).getText().toString();
            String telNum = ((EditText) findViewById(R.id.registerTelNum)).getText().toString();

            Authentication authentication = new Authentication(username, password);
            authentication.registerUser(firstName, lastName, address, email, telNum, dob, type);
            Log.d("Register", "userinfo - " + firstName + lastName + address + email + telNum + dob + type);

            if (authentication.getUserHandler() == null) {
                Log.d("Register", "Failed to register");
            } else {
                UserProvider currentUserProvider = UserProvider.getInstance();
                currentUserProvider.setCurrentUser(authentication.getUserHandler());

                //TODO : Finish client handler code
                ClientHandler clientHandler = new ClientHandler();

                //TODO : Open mai GUI here
                openMainGUI();
            }

        } else {
            Log.d("Register", "input not validated");
        }
   }

   private void returnBtnHandler(){
        if(isRegister1){
            setContentView(R.layout.activity_login);

            registerBtn = findViewById(R.id.registerBtn);
            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerBtnHandler();
                }
            });
        } else{
            registerBtnHandler();
        }
   }

   private void openMainGUI() {
       //startActivity(new Intent(LoginActivity.this, FirebaseActivity.class));
       startActivity(new Intent(LoginActivity.this, Appointment.class));
   }

   //Don't delete yet might come in use
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
