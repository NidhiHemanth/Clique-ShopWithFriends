package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class IndividualSessionScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_session_screen);
        Toast.makeText(getApplicationContext(),"Individual Sessions Screen Reached!", Toast.LENGTH_SHORT).show();
    }
}