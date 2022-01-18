package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SessionsScreen extends AppCompatActivity {

    Intent move;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_screen);
        Toast.makeText(getApplicationContext(),"Sessions Screen Reached!", Toast.LENGTH_SHORT).show();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.rom :  move = new Intent(SessionsScreen.this, HomeScreen.class);
                    startActivity(move); break;
                case R.id.sesh :  Toast.makeText(getApplicationContext(), "Sessions Screen Reached!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.prof : move = new Intent(SessionsScreen.this, ProfileScreen.class);
                    startActivity(move);
                    break;
            }
            return true;
        });
        Button IndiSession = (Button) findViewById(R.id.ind);
        IndiSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(SessionsScreen.this, IndividualSessionScreen.class);
                startActivity(move);
            }
        });
    }
}