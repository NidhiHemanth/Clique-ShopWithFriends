package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        TextView username = (TextView) findViewById(R.id.username);
        TextView email = (TextView) findViewById(R.id.email);

        username.setText(user != null ? user.getDisplayName() : "Not Specified");
        email.setText(user != null ? user.getEmail() : "Not Specified");

        Button sign_out = (Button) findViewById(R.id.sign_out);
        sign_out.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

}