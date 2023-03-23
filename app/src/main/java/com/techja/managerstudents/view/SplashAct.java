package com.techja.managerstudents.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;

public class SplashAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        Splash();
    }

    private void Splash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashAct.this, LoginAct.class);
                startActivity(intent);
                Animatoo.INSTANCE.animateZoom(SplashAct.this);
                finish();
            }
        }, 1800);
    }
}