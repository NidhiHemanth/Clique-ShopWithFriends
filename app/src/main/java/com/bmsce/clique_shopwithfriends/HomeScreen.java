package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity {
    Intent Meghana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toast.makeText(getApplicationContext(),"Home Screen Reached!", Toast.LENGTH_SHORT).show();

        Button Room = (Button) findViewById(R.id.Rom);
        Room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meghana = new Intent(HomeScreen.this, RoomCodeScreen.class);
                startActivity(Meghana);
            }
        });
        Button Profile = (Button) findViewById(R.id.prof);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meghana = new Intent(HomeScreen.this, ProfileScreen.class);
                startActivity(Meghana);
            }
        });
        Button Session = (Button) findViewById(R.id.sesh);
        Session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meghana = new Intent(HomeScreen.this, SessionsScreen.class);
                startActivity(Meghana);
            }
        });




    }
}