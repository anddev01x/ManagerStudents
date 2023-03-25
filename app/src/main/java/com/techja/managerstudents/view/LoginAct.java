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
import com.techja.managerstudents.databinding.ActLoginBinding;
import com.techja.managerstudents.db.UserDAO;
import com.techja.managerstudents.db.UserDatabase;
import com.techja.managerstudents.db.UserEntity;

public class LoginAct extends AppCompatActivity implements View.OnClickListener {
    public static final String USER_NAME = "USER_NAME";
    private ActLoginBinding binding;
    private UserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActLoginBinding.inflate(getLayoutInflater());
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
        binding.btLogin.setOnClickListener(this);
        binding.tvSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
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
                Toast.makeText(this, "Tài khoản hoặc mật khẩu không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
