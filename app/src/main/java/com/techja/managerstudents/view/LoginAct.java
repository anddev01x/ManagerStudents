package com.techja.managerstudents.view;


import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.room.Room;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.databinding.ActLoginBinding;
import com.techja.managerstudents.dao.UserDAO;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.model.BaseAct;
import com.techja.managerstudents.model.UserEntity;

public class LoginAct extends BaseAct<ActLoginBinding> {
    public static final String USER_NAME = "USER_NAME";
    private UserDAO userDAO;

    protected void initViews() {
        userDAO = AppDatabase.getInstance(this).getUserDAO();
        binding.btLogin.setOnClickListener(this);
        binding.tvSignup.setOnClickListener(this);
    }

    @Override
    protected ActLoginBinding initViewBinding() {
        return ActLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void clickViews(View view) {
        if (view.getId() == R.id.tv_signup) {
            Intent intent = new Intent(this, RegisterAct.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateSlideLeft(this);
        }
        if (view.getId() == R.id.bt_login) {
            String username = binding.edtUser.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();
            UserEntity user = userDAO.getUser(username, password);
            if (user != null) {
                Intent intent = new Intent(this, HomeAct.class);
                intent.putExtra(USER_NAME, username);
                startActivity(intent);
                Animatoo.INSTANCE.animateSplit(this);
            } else {
                Toast.makeText(this, "Tài khoản hoặc mật khẩu không hợp lệ!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
