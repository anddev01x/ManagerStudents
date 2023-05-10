package com.techja.managerstudents.view;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.databinding.ActRegisterBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.dao.UserDAO;
import com.techja.managerstudents.model.BaseAct;
import com.techja.managerstudents.model.UserEntity;

public class RegisterAct extends BaseAct<ActRegisterBinding> {
    private UserDAO userDAO;

    @Override
    protected ActRegisterBinding initViewBinding() {
        return ActRegisterBinding.inflate(getLayoutInflater());
    }

    protected void initViews() {
        userDAO = AppDatabase.getInstance(this).getUserDAO();
        binding.btRegister.setOnClickListener(this);
        binding.tvLogin.setOnClickListener(this);
    }


    @Override
    protected void clickViews(View view) {
        if (view.getId() == R.id.bt_register) {
            String password = binding.edtPassword.getText().toString().trim();
            String username = binding.edtUser.getText().toString().trim();
            String fullName = binding.edtName.getText().toString().trim();
            String repass = binding.edtRepass.getText().toString().trim();
            UserEntity user = new UserEntity(username, password, fullName);
            if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
                Toast.makeText(this, "Nhập thông tin đầy đủ!", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(repass)) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            } else if (userDAO.getUserByName(username)) {
                Toast.makeText(this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            } else {
                userDAO.registerUser(user);
                Intent intent = new Intent(this, LoginAct.class);
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                Animatoo.INSTANCE.animateSlideRight(this);
            }
        }
        if (view.getId() == R.id.tv_login) {
            Intent intent = new Intent(this, LoginAct.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateSlideRight(this);
        }
    }
}
