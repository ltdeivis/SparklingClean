package com.example.sparklingclean.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sparklingclean.DB.User;
import com.example.sparklingclean.R;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn  = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginBtnHandler(v);
            }
        });
    }

    private void loginBtnHandler(View v) {
        String username = ((EditText) findViewById(R.id.usernameTxt)).getText().toString();
        String password = ((EditText) findViewById(R.id.passowrdTxt)).getText().toString();

        Authentication authentication = new Authentication(username, password);
        if(authentication.loginUser()) {
            User user = authentication.getCurrentUser();
            loginBtn.setText(user.getFirstName()); //TODO : DEBUG ONLY DELETE
            //Show main gui
        }
    }
}
