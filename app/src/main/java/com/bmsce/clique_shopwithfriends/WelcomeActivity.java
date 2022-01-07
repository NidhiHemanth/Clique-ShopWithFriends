package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {
            Intent home_activity = new Intent(this, HomeActivity.class);
            startActivity(home_activity);
            finish();
        }

        Button skip = (Button) findViewById(R.id.skip);
        skip.setOnClickListener(v -> {
            Intent login_activity = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(login_activity);
            finish();
        });
    }

}