package com.techja.managerstudents.view;


import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.dao.ClassRoomDAO;
import com.techja.managerstudents.databinding.ActAddClassBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.model.BaseAct;
import com.techja.managerstudents.model.ClassroomEntity;

public class AddClassAct extends BaseAct<ActAddClassBinding> {
    private ClassRoomDAO classRoomDAO;

    @Override
    protected ActAddClassBinding initViewBinding() {
        return ActAddClassBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        classRoomDAO = AppDatabase.getInstance(this).getClassRoomDAO();
        binding.imgBackHome.setOnClickListener(this);
        binding.tvDoneClass.setOnClickListener(this);
        binding.tvSelectImg.setOnClickListener(this);
    }

    @Override
    protected void clickViews(View view) {
        if (R.id.img_back_home == view.getId()) {
            Intent intent = new Intent(this, InforStudentAct.class);
            startActivity(intent);
//            Animatoo.INSTANCE.animateSlideRight(this);
        }
        if (R.id.tv_done_class == view.getId()) {
            addClass();
        }
    }

    private void addClass() {
        String idClass = binding.edtIdClass.getText().toString().trim();
        String nameClass = binding.edtClass.getText().toString().trim();
        String nameLecturers = binding.edtLectures.getText().toString().trim();
        if (TextUtils.isEmpty(idClass) || TextUtils.isEmpty(nameClass) ||
                TextUtils.isEmpty(nameLecturers)) {
            Toast.makeText(this, "Nhập thông tin cần thiết", Toast.LENGTH_SHORT).show();
            return;
        }
        if (classRoomDAO.checkClassById(idClass) != null) {
            Toast.makeText(this, "Trùng mã lớp học", Toast.LENGTH_SHORT).show();
            return;
        }
        ClassroomEntity classroom = new ClassroomEntity(idClass, nameClass, nameLecturers);
        classRoomDAO.insertClass(classroom);
        Intent intent = new Intent(AddClassAct.this, SplashLoadingAct.class);
        startActivity(intent);

        // Show Splash loading...
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something
                Intent intent = new Intent(AddClassAct.this, InforClassAct.class);
                Toast.makeText(AddClassAct.this, "Thêm lớp học thành công", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        }, 800);

    }
}