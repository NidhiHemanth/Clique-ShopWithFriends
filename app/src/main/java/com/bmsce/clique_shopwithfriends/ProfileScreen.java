package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProfileScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        Toast.makeText(getApplicationContext(),"Profile Screen Reached!", Toast.LENGTH_SHORT).show();
        Button Settings = (Button) findViewById(R.id.set);
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Meghana = new Intent(ProfileScreen.this, SettingsScreen.class);
                startActivity(Meghana);
            }
        });
    }
}