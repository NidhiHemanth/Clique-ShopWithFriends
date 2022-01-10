package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        ImageView logo = (ImageView) findViewById(R.id.clique_logo);
        TextView clique = (TextView) findViewById(R.id.clique_app_name);

        logo.setVisibility(View.VISIBLE);
        clique.setVisibility(View.GONE);
        animFadeIn.reset();
        animFadeIn.setDuration(1000);
        logo.clearAnimation();
        logo.startAnimation(animFadeIn);

        clique.setVisibility(View.VISIBLE);
        animFadeIn.reset();
        animFadeIn.setDuration(500);
        clique.clearAnimation();
        clique.startAnimation(animFadeIn);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, SPLASH_TIME_OUT);
    }

}