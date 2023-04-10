package com.techja.managerstudents.view;


import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.dao.StudentDAO;
import com.techja.managerstudents.databinding.ActAddStudentsBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.model.BaseAct;
import com.techja.managerstudents.model.StudentEntity;

import java.text.DecimalFormat;

public class AddStudentsAct extends BaseAct<ActAddStudentsBinding> {
    private StudentDAO studentDAO;


    @Override
    protected void initViews() {
        studentDAO = AppDatabase.getInstance(this).getStudentDAO();
        setSnpinner();
        binding.imgBack.setOnClickListener(this);
        binding.tvDone.setOnClickListener(this);
        binding.tvSelect.setOnClickListener(this);
    }

    private void setSnpinner() {
        String[] idClass = {"CNPM01", "CNPM02", "CNPM03", "CNPM04"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, idClass);
        binding.idSpinner.setAdapter(adapter);
    }


    @Override
    protected ActAddStudentsBinding initViewBinding() {
        return ActAddStudentsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void clickViews(View view) {
        if (R.id.img_back == view.getId()) {
            Intent intent = new Intent(this, InforStudentAct.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateSlideRight(this);
        }
        if (R.id.tv_done == view.getId()) {
            addStudents();
        }
    }

    private void addStudents() {

        String idStudent = binding.edtIdStudent.getText().toString().trim();
        String fullName = binding.edtFullName.getText().toString().trim();
        String birthdate = binding.edtBirthdate.getText().toString().trim();
        String gmail = binding.edtGmail.getText().toString().trim();
        String address = binding.edtAddress.getText().toString().trim();
        String gpa = binding.edtGpa.getText().toString();
        String idClassroom = binding.idSpinner.getSelectedItem().toString().trim();
        DecimalFormat df = new DecimalFormat("#.##");

        if (TextUtils.isEmpty(idStudent) || TextUtils.isEmpty(fullName) || TextUtils.isEmpty(gpa)
                || TextUtils.isEmpty(birthdate)) {
            Toast.makeText(this, "Nhập thông tin cần thiết", Toast.LENGTH_SHORT).show();
            return;
        }
        if (studentDAO.checkStudentById(idStudent) != null) {
            Toast.makeText(this, "Trùng mã sinh viên", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Float.parseFloat(gpa) > 4 || Float.parseFloat(gpa) < 0) {
            Toast.makeText(this, "GPA không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        } else {
            try {
                float gpaFloat = Float.parseFloat(gpa);
                StudentEntity student = new StudentEntity(idStudent, fullName, birthdate, gmail,
                        address, (float) Double.parseDouble(df.format(gpaFloat)), idClassroom);
                Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show();
                studentDAO.insertStudent(student);
                Intent intent = new Intent(this, InforStudentAct.class);
                startActivity(intent);
                Animatoo.INSTANCE.animateSlideRight(this);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "GPA không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
