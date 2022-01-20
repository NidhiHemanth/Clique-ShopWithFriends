package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    SliderView sliderView;
    int[] images = {
        R.drawable.one,
        R.drawable.two,
        R.drawable.three
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {
            Intent home_activity = new Intent(this, HomeScreen.class);
            startActivity(home_activity);
            finish();
        }

        Button skip = findViewById(R.id.skip);
        skip.setOnClickListener(v -> {
            Intent login_activity = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(login_activity);
            finish();
        });

        sliderView = findViewById(R.id.image_slider);
        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
    }

}