package com.example.sparklingclean.MainGUI.AppontmentScreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sparklingclean.Firebase.DB.Appointment;
import com.example.sparklingclean.Firebase.DB.AppointmentHandler;
import com.example.sparklingclean.Firebase.DB.FirebaseListener;
import com.example.sparklingclean.Firebase.DB.UserHandler;
import com.example.sparklingclean.Providers.UserProvider;
import com.example.sparklingclean.R;

import java.util.List;

public class AppointmentActivity extends AppCompatActivity {

    private ListView listView;
    private String mTitle[] = {"Facebook", "Whatsapp", "Twitter", "Instagram", "Youtube"};
    private String mDescription[] = {"Facebook Description", "Whatsapp Description", "Twitter Description", "Instagram Description", "Youtube Description"};
    private int images[] = {R.drawable.fb_thumbnail_round, R.drawable.whatsapp_thumbnail_round, R.drawable.twitter_thumbnail_round, R.drawable.instagram_thumbnail_round, R.drawable.youtube_thumbnail_round};
    private AppointmentHandler appointmentHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        listView = findViewById(R.id.listView);

        loadList();

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            if(i == 0) {
                Toast.makeText(AppointmentActivity.this, "Facebook Description", Toast.LENGTH_SHORT).show();
            }
            else if(i == 1) {
                Toast.makeText(AppointmentActivity.this, "What's App Description", Toast.LENGTH_SHORT).show();
            }
            else if(i == 2) {
                Toast.makeText(AppointmentActivity.this, "Twitter Description", Toast.LENGTH_SHORT).show();
            }
            else if(i == 3) {
                Toast.makeText(AppointmentActivity.this, "Instagram Description", Toast.LENGTH_SHORT).show();
            }
            else if(i == 4) {
                Toast.makeText(AppointmentActivity.this, "Youtube Description", Toast.LENGTH_SHORT).show();
            }
        });

        Button pendingBtn = findViewById(R.id.pendingBtn);
        pendingBtn.setOnClickListener(view -> {
            List<Appointment> appointments = appointmentHandler.getPendingAppointments();
            mTitle = new String[appointments.size()];
            mDescription = new String[appointments.size()];
            images = new int[appointments.size()];

            for(int i = 0; i < appointments.size(); i++) {
                mTitle[i] = appointments.get(i).appDate;
                mDescription[i] = appointments.get(i).appTime;
                images[i] = R.drawable.ic_access_alarm_black_24dp;
            }

            MyAdapter adapter = new MyAdapter(AppointmentActivity.this, mTitle, mDescription, images);
            listView.setAdapter(adapter);
        });

        Button allBtn = findViewById(R.id.allBtn);
        allBtn.setOnClickListener(view -> {
            List<Appointment> appointments = appointmentHandler.getAppointments();
            mTitle = new String[appointments.size()];
            mDescription = new String[appointments.size()];
            images = new int[appointments.size()];

            for(int i = 0; i < appointments.size(); i++) {
                mTitle[i] = appointments.get(i).appDate;
                mDescription[i] = appointments.get(i).appTime;
                images[i] = R.drawable.ic_access_alarm_black_24dp;
            }

            MyAdapter adapter = new MyAdapter(AppointmentActivity.this, mTitle, mDescription, images);
            listView.setAdapter(adapter);
        });
    }

    private void loadList() {
        UserProvider userProvider = UserProvider.getInstance();
        UserHandler userHandler = userProvider.getCurrentUser();

        appointmentHandler = new AppointmentHandler();

        FirebaseListener listener = new FirebaseListener() {
            @Override
            public void onLoadFinish() {
                List<Appointment> appointments = appointmentHandler.getAppointments();
                mTitle = new String[appointments.size()];
                mDescription = new String[appointments.size()];
                images = new int[appointments.size()];

                for(int i = 0; i < appointments.size(); i++) {
                    mTitle[i] = appointments.get(i).appDate;
                    mDescription[i] = appointments.get(i).appTime;
                    images[i] = R.drawable.ic_access_alarm_black_24dp;
                }

                MyAdapter adapter = new MyAdapter(AppointmentActivity.this, mTitle, mDescription, images);
                listView.setAdapter(adapter);
            }
        };
        appointmentHandler.addDataLoadListener(listener);
        appointmentHandler.findClientAppointment(userHandler.getUuid());
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImage[];


        MyAdapter(Context c, String title[], String description[], int images[]) {
            super(c, R.layout.list_row, R.id.main_title, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImage = images;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.list_row, parent, false);
            ImageView rowImage = row.findViewById(R.id.image);
            TextView rowTitle = row.findViewById(R.id.main_title);
            TextView rowDesc = row.findViewById(R.id.desc);

            rowImage.setImageResource(rImage[position]);
            rowTitle.setText(rTitle[position]);
            rowDesc.setText(rDescription[position]);

            return row;
        }
    }
}
