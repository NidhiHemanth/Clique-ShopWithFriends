package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setSelectedItemId(R.id.rom);
        bottomNavigationView.getMenu();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int navH = bottomNavigationView.getHeight();
        FrameLayout frameL = findViewById(R.id.frame_layout);
        LayoutParams params = frameL.getLayoutParams();
        params.height = metrics.heightPixels - navH - 80;
        frameL.setLayoutParams(params);

        loadFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.rom :
                    // Toast.makeText(getApplicationContext(), "Home Screen Reached!", Toast.LENGTH_SHORT).show();
                    // getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFrag).commit();
                    loadFragment(new HomeFragment());
                    break;
                case R.id.sesh :
                    // Meghana = new Intent(HomeScreen.this, SessionsScreen.class);
                    // startActivity(Meghana);
                    // getSupportFragmentManager().beginTransaction().replace(R.id.container, seshFrag).commit();
                    loadFragment(new SessionFragment());
                    break;
                case R.id.prof :
                    // Meghana = new Intent(HomeScreen.this, ProfileScreen.class);
                    // startActivity(Meghana);
                    // getSupportFragmentManager().beginTransaction().replace(R.id.container, profFrag).commit();
                    loadFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frame_layout, fragment);

        ft.commit();
    }
}
