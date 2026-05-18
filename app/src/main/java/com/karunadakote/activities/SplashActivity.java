package com.karunadakote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.karunadakote.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo     = findViewById(R.id.iv_splash_logo);
        TextView  title    = findViewById(R.id.tv_splash_title);
        TextView  subtitle = findViewById(R.id.tv_splash_subtitle);
        TextView  tagline  = findViewById(R.id.tv_splash_tagline);

        // Fade + scale animation for logo
        ScaleAnimation scale = new ScaleAnimation(
            0.5f, 1f, 0.5f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        );
        scale.setDuration(1200);
        AlphaAnimation fade = new AlphaAnimation(0f, 1f);
        fade.setDuration(1200);

        AnimationSet logoAnim = new AnimationSet(true);
        logoAnim.addAnimation(scale);
        logoAnim.addAnimation(fade);
        logo.startAnimation(logoAnim);

        // Staggered text fade-ins
        animateFadeIn(title,    800,  400);
        animateFadeIn(subtitle, 1000, 700);
        animateFadeIn(tagline,  800, 1100);

        // Navigate after 3 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 3000);
    }

    private void animateFadeIn(android.view.View v, long dur, long delay) {
        AlphaAnimation a = new AlphaAnimation(0f, 1f);
        a.setDuration(dur);
        a.setStartOffset(delay);
        a.setFillAfter(true);
        v.startAnimation(a);
    }
}
