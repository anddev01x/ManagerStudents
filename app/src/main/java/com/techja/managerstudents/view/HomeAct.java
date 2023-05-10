package com.techja.managerstudents.view;

import static com.techja.managerstudents.view.LoginAct.USER_NAME;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.databinding.ActHomeBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.dao.UserDAO;
import com.techja.managerstudents.model.BaseAct;
import com.techja.managerstudents.model.UserEntity;

public class HomeAct extends BaseAct<ActHomeBinding> {
    private static final String TEXT_VIEW_KEY = "TEXT_VIEW_KEY";
    private UserDAO userDAO;

    @Override
    protected ActHomeBinding initViewBinding() {
        return ActHomeBinding.inflate(getLayoutInflater());
    }


    protected void initViews() {
        userDAO = AppDatabase.getInstance(this).getUserDAO();
        setText();
        binding.imgviewWebsite.setOnClickListener(this);
        binding.imgviewDetail.setOnClickListener(this);
        binding.imgviewMngclass.setOnClickListener(this);
        binding.imgviewMngstudent.setOnClickListener(this);
        binding.tvLogout.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setText();
    }

    private void setText() {
        String userName = getIntent().getStringExtra(USER_NAME);
        if (userName != null) {
            UserEntity user = userDAO.getUserByUserName(userName);
            binding.tvUser.setText(user.getFullName());
        }
    }


    @Override
    protected void clickViews(View view) {
        if (view.getId() == R.id.imgview_website) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.vinhuni.edu.vn"));
            startActivity(intent);
        }
        if (view.getId() == R.id.imgview_detail) {
            Intent intent = new Intent(this, InforAppAct.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateZoom(HomeAct.this);
        }
        if (view.getId() == R.id.tv_logout) {
            Intent intent = new Intent(this, LoginAct.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateZoom(HomeAct.this);
        }
        if (view.getId() == R.id.imgview_mngstudent) {
            Intent intent = new Intent(this, InforStudentAct.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateZoom(this);
        }
        if (view.getId() == R.id.imgview_mngclass) {
            Intent intent = new Intent(this, InforClassAct.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateZoom(this);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("THÔNG BÁO");
        alertDialog.setMessage("Bạn muốn đăng xuất?");
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialogInterface, i) -> {
            Intent intent = new Intent(this, LoginAct.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateZoom(HomeAct.this);
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", (dialogInterface, i) -> alertDialog.dismiss());
        alertDialog.show();
    }
}