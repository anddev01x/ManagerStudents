package com.techja.managerstudents.view;

import static com.techja.managerstudents.view.InforStudentAct.DATA_STUDENT;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.dao.StudentDAO;
import com.techja.managerstudents.databinding.ActCheckEditStudentBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.model.BaseAct;
import com.techja.managerstudents.model.StudentEntity;


public class CheckEditStudentAct extends BaseAct<ActCheckEditStudentBinding> {
    private StudentDAO studentDAO;
    private StudentEntity oldStudent;

    @Override
    protected ActCheckEditStudentBinding initViewBinding() {
        return ActCheckEditStudentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        getData();
        studentDAO = AppDatabase.getInstance(this).getStudentDAO();
        binding.imgBack.setOnClickListener(this);
        binding.tvUpdate.setOnClickListener(this);
    }


    private void getData() {
        oldStudent = (StudentEntity) getIntent().getSerializableExtra(DATA_STUDENT);
        if (oldStudent != null) {
            String idStudent = oldStudent.getIdStudent();
            String fullName = oldStudent.getFullName();
            String birthdate = oldStudent.getBirthdate();
            String gmail = oldStudent.getGmail();
            String address = oldStudent.getAddress();
            float gpa = oldStudent.getGpa();
            String idClass = oldStudent.getIdClass();
            binding.edtIdStudent.setText(idStudent);
            binding.edtFullName.setText(fullName);
            binding.edtBirthdate.setText(birthdate);
            binding.edtGmail.setText(gmail);
            binding.edtAddress.setText(address);
            binding.edtGpa.setText(String.valueOf(gpa));


            String[] idClas = {"CNPM01", "CNPM02", "CNPM03", "CNPM04"};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idClas);
            binding.idSpinner.setAdapter(adapter);
            int position = adapter.getPosition(idClass);
            binding.idSpinner.setSelection(position);

        }
    }

    @Override
    protected void clickViews(View view) {
        if (R.id.img_back == view.getId()) {
            Intent intent = new Intent(this, InforStudentAct.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateSlideUp(this);
        }
        if (R.id.tv_update == view.getId()) {
            UpdateStudent();
        }
    }


    private void UpdateStudent() {
        String idStudent = binding.edtIdStudent.getText().toString().trim();
        String fullName = binding.edtFullName.getText().toString().trim();
        String birthdate = binding.edtBirthdate.getText().toString().trim();
        String gmail = binding.edtGmail.getText().toString().trim();
        String address = binding.edtAddress.getText().toString().trim();
        String gpa = binding.edtGpa.getText().toString();
        String idClassroom = binding.idSpinner.getSelectedItem().toString().trim();

        if (TextUtils.isEmpty(idStudent) || TextUtils.isEmpty(fullName) || TextUtils.isEmpty(gpa)
                || TextUtils.isEmpty(birthdate)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Float.parseFloat(gpa) > 4 || Float.parseFloat(gpa) < 0) {
            Toast.makeText(this, "GPA không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (studentDAO.checkStudentById(idStudent) != null) {
            oldStudent.setIdStudent(idStudent);
            oldStudent.setFullName(fullName);
            oldStudent.setBirthdate(birthdate);
            oldStudent.setGmail(gmail);
            oldStudent.setAddress(address);
            oldStudent.setGpa(Float.parseFloat(gpa));
            oldStudent.setIdClass(idClassroom);

            studentDAO.upDateStudent(oldStudent);
            Toast.makeText(this, "Cập nhật sinh viên thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, InforStudentAct.class);
            setResult(101, intent);
            Animatoo.INSTANCE.animateZoom(this);
            finish();
        }
    }
}

//Cach 2 nhung k dung ActivityResultAPI
//        if (studentDAO.getIdStudent(idStudent) != null) {
//            oldStudent.setIdStudent(idStudent);
//            oldStudent.setFullName(fullName);
//            oldStudent.setBirthdate(birthdate);
//            oldStudent.setGmail(gmail);
//            oldStudent.setAddress(address);
//            oldStudent.setGpa(Float.parseFloat(gpa));
//            oldStudent.setIdClass(idClassroom);
//
//            studentDAO.upDateStudent(oldStudent);
//
//            Intent intent = new Intent(CheckEditStudentAct.this, SplashLoadingAct.class);
//            startActivity(intent);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(CheckEditStudentAct.this, InforStudentAct.class);
//                    Toast.makeText(CheckEditStudentAct.this,
//                            "Cập nhật sinh viên thành công", Toast.LENGTH_SHORT).show();
//                    setResult(101, intent);
//                    startActivity(intent);
//                    finish();
//                }
//            }, 700);
//        }

