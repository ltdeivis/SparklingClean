package com.example.sparklingclean.MainGUI.AppontmentScreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sparklingclean.R;

public class AppointmentActivity extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Facebook", "Whatsapp", "Twitter", "Instagram", "Youtube"};
    String mDescription[] = {"Facebook Description", "Whatsapp Description", "Twitter Description", "Instagram Description", "Youtube Description"};
    int images[] = {R.drawable.fb_thumbnail_round, R.drawable.whatsapp_thumbnail_round, R.drawable.twitter_thumbnail_round, R.drawable.instagram_thumbnail_round, R.drawable.youtube_thumbnail_round};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        listView = findViewById(R.id.listView);

        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
            }
        });
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
