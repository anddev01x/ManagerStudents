package com.techja.managerstudents.model;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.techja.managerstudents.R;

public abstract class BaseAct<T extends ViewBinding> extends AppCompatActivity implements View.OnClickListener {
    protected T binding;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initViewBinding();
        setContentView(binding.getRoot());
        initViews();
    }

    protected abstract void initViews();

    protected abstract T initViewBinding();

    @Override
    public final void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_item));
        clickViews(view);
    }

    protected void clickViews(View view) {
        //do nothing
    }
}
