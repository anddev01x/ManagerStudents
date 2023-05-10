package com.techja.managerstudents.view;


import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.techja.managerstudents.R;
import com.techja.managerstudents.dao.ClassRoomDAO;
import com.techja.managerstudents.dao.StudentDAO;
import com.techja.managerstudents.databinding.ActAddStudentsBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.model.BaseAct;
import com.techja.managerstudents.model.ClassroomEntity;
import com.techja.managerstudents.model.StudentEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AddStudentsAct extends BaseAct<ActAddStudentsBinding> {
    private StudentDAO studentDAO;
    private ClassRoomDAO classRoomDAO;


    @Override
    protected void initViews() {
        studentDAO = AppDatabase.getInstance(this).getStudentDAO();
        classRoomDAO = AppDatabase.getInstance(this).getClassRoomDAO();
        setSnpinner();
        binding.imgBack.setOnClickListener(this);
        binding.tvDone.setOnClickListener(this);
        binding.tvSelect.setOnClickListener(this);
    }

    private void setSnpinner() {
        List<ClassroomEntity> listClass = classRoomDAO.getAllCLass();
        List<String> listIdClass = new ArrayList<>();
        for(ClassroomEntity classroom :listClass){
            listIdClass.add(classroom.getIdClass());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listIdClass);
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
//            Animatoo.INSTANCE.animateZoom(this);
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
                studentDAO.insertStudent(student);
                Intent intent = new Intent(AddStudentsAct.this, SplashLoadingAct.class);
                startActivity(intent);

                // Show Splash loading...
                new Handler().postDelayed(() -> {
                    // Do something
                    Intent intent1 = new Intent(AddStudentsAct.this,
                            InforStudentAct.class);
                    Toast.makeText(AddStudentsAct.this,
                            "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show();
                    startActivity(intent1);
                    finish();
                }, 800);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "GPA không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
