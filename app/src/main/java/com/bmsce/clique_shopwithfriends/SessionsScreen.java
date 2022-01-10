package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SessionsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_screen);
        Toast.makeText(getApplicationContext(),"Sessions Screen Reached!", Toast.LENGTH_SHORT).show();
        Button IndiSession = (Button) findViewById(R.id.ind);
        IndiSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Meghana = new Intent(SessionsScreen.this, IndividualSessionScreen.class);
                startActivity(Meghana);
            }
        });
    }
}