package com.bmsce.clique_shopwithfriends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {
    Intent Meghana;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toast.makeText(getApplicationContext(), "Home Screen Reached!", Toast.LENGTH_SHORT).show();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.rom :   Toast.makeText(getApplicationContext(), "Home Screen Reached!", Toast.LENGTH_SHORT).show();
                                   break;
                case R.id.sesh : Meghana = new Intent(HomeScreen.this, SessionsScreen.class);
                                 startActivity(Meghana);
                                 break;
                case R.id.prof : Meghana = new Intent(HomeScreen.this, ProfileScreen.class);
                                 startActivity(Meghana);
                                 break;
            }
            return true;
        });
    }
}
