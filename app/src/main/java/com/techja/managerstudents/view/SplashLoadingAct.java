package com.techja.managerstudents.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.techja.managerstudents.databinding.ActSplashLoadingBinding;

public class SplashLoadingAct extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.techja.managerstudents.databinding.ActSplashLoadingBinding binding
                = ActSplashLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}