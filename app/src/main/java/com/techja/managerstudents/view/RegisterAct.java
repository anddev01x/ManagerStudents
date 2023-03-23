package com.techja.managerstudents.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.databinding.ActRegisterBinding;
import com.techja.managerstudents.db.UserDAO;
import com.techja.managerstudents.db.UserDatabase;
import com.techja.managerstudents.db.UserEntity;

public class RegisterAct extends AppCompatActivity implements View.OnClickListener {
    public static final String FULL_NAME = "FULL_NAME";
    private ActRegisterBinding binding;
    private UserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        dataBase();
    }

    private void dataBase() {
        userDAO = Room.databaseBuilder(this, UserDatabase.class, "User").
                allowMainThreadQueries()
                .build().getUserDAO();
    }

    private void initViews() {
        binding.btRegister.setOnClickListener(this);
        binding.tvLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
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
                intent.putExtra(FULL_NAME, fullName);
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
