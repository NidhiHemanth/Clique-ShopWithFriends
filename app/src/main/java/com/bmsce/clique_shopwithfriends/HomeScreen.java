package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    static int offset = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Log.d("me->", "home activity");
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        LayoutParams navParams = bottomNavigationView.getLayoutParams();

        FrameLayout frameL = findViewById(R.id.frame_layout);
        LayoutParams frameParams = frameL.getLayoutParams();

        frameParams.height = metrics.heightPixels - navParams.height - offset;
        frameL.setLayoutParams(frameParams);

        Log.d("me->", "home activity");
        loadFragment(new HomeFragment());
        Log.d("me->", "home activity");

        bottomNavigationView.setSelectedItemId(R.id.rom);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            HomeFragment.mEditor.putString(getString(R.string.session_token), HomeFragment.token.getText().toString());
            HomeFragment.mEditor.commit();
            HomeFragment.mEditor.putString(getString(R.string.session_id), HomeFragment.session.getText().toString());
            HomeFragment.mEditor.commit();

            switch (item.getItemId())
            {
                case R.id.rom :
                    loadFragment(new HomeFragment());
                    break;
                case R.id.sesh :
                    loadFragment(new SessionFragment());
                    break;
                case R.id.prof :
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
