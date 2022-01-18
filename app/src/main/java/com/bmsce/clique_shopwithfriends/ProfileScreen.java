package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileScreen extends AppCompatActivity {
    Intent move;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        Toast.makeText(getApplicationContext(), "Home Screen Reached!", Toast.LENGTH_SHORT).show();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.rom :   move = new Intent(ProfileScreen.this, HomeScreen.class);
                    startActivity(move);
                    break;
                case R.id.sesh : move = new Intent(ProfileScreen.this, SessionsScreen.class);
                    startActivity(move);
                    break;
                case R.id.prof : Toast.makeText(getApplicationContext(), "Profile Screen Reached!", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });

        Button Settings = (Button) findViewById(R.id.set);
        Settings.setOnClickListener(v -> {
            move = new Intent(ProfileScreen.this, SettingsScreen.class);
            startActivity(move);
        });
    }
}