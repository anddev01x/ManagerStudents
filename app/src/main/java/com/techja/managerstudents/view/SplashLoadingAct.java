package com.techja.managerstudents.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.techja.managerstudents.databinding.ActSplashLoadingBinding;

public class SplashLoadingAct extends AppCompatActivity {
    private ActSplashLoadingBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActSplashLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}