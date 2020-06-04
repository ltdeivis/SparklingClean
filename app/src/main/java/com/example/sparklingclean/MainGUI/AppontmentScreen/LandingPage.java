package com.example.sparklingclean.MainGUI.AppontmentScreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sparklingclean.Providers.UserProvider;
import com.example.sparklingclean.R;

public class LandingPage extends AppCompatActivity {

    private Button appointmentBtn;
    private Button settingsBtn;
    private Button clientsBtn;
    private Button employeesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userType = UserProvider.getInstance().getCurrentUser().getUser().type;
        if (userType.equals("client")) {
            setContentView(R.layout.activity_client_landing);
        }else if(userType.equals("manager")){
            setContentView(R.layout.activity_manager_landing);

            employeesBtn = findViewById(R.id.employeesBtn);
            employeesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setContentView(R.layout.activity_employee);
                }
            });
        } else {
            setContentView(R.layout.activity_employee_landing);
        }

        appointmentBtn = findViewById(R.id.appointmentsBtn);
        appointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this, AppointmentActivity.class));
            }
        });

        clientsBtn = findViewById(R.id.clientsBtn);
        clientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_client);
            }
        });

    }
}
