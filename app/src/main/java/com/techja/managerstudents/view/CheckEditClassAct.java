package com.techja.managerstudents.view;

import static com.techja.managerstudents.view.InforClassAct.DATA_CLASS;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.dao.ClassRoomDAO;
import com.techja.managerstudents.databinding.ActCheckEditClassBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.model.BaseAct;
import com.techja.managerstudents.model.ClassroomEntity;

public class CheckEditClassAct extends BaseAct<ActCheckEditClassBinding> {
    public static final String ID_CLASS = "ID_CLASS";
    private ClassRoomDAO classDAO;
    private ClassroomEntity oldClass;


    @Override
    protected void initViews() {
        getData();
        classDAO = AppDatabase.getInstance(this).getClassRoomDAO();
        binding.imgBackHome.setOnClickListener(this);
        binding.tvUpdateClass.setOnClickListener(this);
        binding.tvListStudents.setOnClickListener(this);
    }



    private void getData() {
        oldClass = (ClassroomEntity) getIntent().getSerializableExtra(DATA_CLASS);
        if (oldClass != null) {
            String idClass = oldClass.getIdClass();
            String nameClass = oldClass.getNameClass();
            String lecturer = oldClass.getNameLecturers();
            binding.edtIdClass.setText(idClass);
            binding.edtClass.setText(nameClass);
            binding.edtLectures.setText(lecturer);
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, InforClassAct.class);
        startActivity(intent);
    }

    @Override
    protected void clickViews(View view) {
        if (R.id.img_back_home == view.getId()) {
            Intent intent = new Intent(this, InforClassAct.class);
            startActivity(intent);
//            Animatoo.INSTANCE.animateSlideUp(this);
        }
        if (R.id.tv_update_class == view.getId()) {
            UpdateClass();
        }
        if (R.id.tv_listStudents == view.getId()) {
            String idClass = binding.edtIdClass.getText().toString();
            Intent intent = new Intent(this, StudentsInClass.class);
            intent.putExtra(ID_CLASS, idClass);
            startActivity(intent);
        }
    }

    private void UpdateClass() {
        String idClass = binding.edtIdClass.getText().toString().trim();
        String nameClass = binding.edtClass.getText().toString().trim();
        String lecturer = binding.edtLectures.getText().toString().trim();

        if (TextUtils.isEmpty(idClass) || TextUtils.isEmpty(nameClass)
                || TextUtils.isEmpty(lecturer)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (classDAO.checkClassById(idClass) != null) {
            oldClass.setIdClass(idClass);
            oldClass.setNameClass(nameClass);
            oldClass.setNameLecturers(lecturer);

            classDAO.upDateClass(oldClass);
            Toast.makeText(this, "Cập nhật lớp học thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, InforClassAct.class);
            setResult(101, intent);
            Animatoo.INSTANCE.animateZoom(this);
            finish();

        }
    }

    @Override
    protected ActCheckEditClassBinding initViewBinding() {
        return ActCheckEditClassBinding.inflate(getLayoutInflater());
    }
}