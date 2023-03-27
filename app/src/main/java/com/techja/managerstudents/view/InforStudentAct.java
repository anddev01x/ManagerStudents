package com.techja.managerstudents.view;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.adapter.StudentAdapter;
import com.techja.managerstudents.dao.StudentDAO;
import com.techja.managerstudents.databinding.ActInforStudentBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.model.StudentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class InforStudentAct extends BaseAct<ActInforStudentBinding> {
    private StudentDAO studentDAO;
    private StudentAdapter studentAdapter;
    private List<StudentEntity> listStudents;

    @Override
    protected ActInforStudentBinding initViewBinding() {
        return ActInforStudentBinding.inflate(getLayoutInflater());
    }

    protected void initViews() {
        CreateMenu();
        dataBase();
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        studentAdapter = new StudentAdapter();
        listStudents = new ArrayList<>();
        listStudents = studentDAO.getAllStudents();
        studentAdapter.setData(listStudents);

        binding.recyclerStudent.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerStudent.setAdapter(studentAdapter);
    }

    private void dataBase() {
        studentDAO = Room.databaseBuilder(this, AppDatabase.class, "Student").
                allowMainThreadQueries()
                .build().getStudentDAO();
    }

    @Override
    protected void clickViews(View view) {
    }

    private void CreateMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Quản lý Sinh viên");
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.home == item.getItemId()) {
            BackHome();
        } else if (R.id.add == item.getItemId()) {
            addStudents();
        } else if (R.id.exit == item.getItemId()) {
            logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void BackHome() {
        Intent intent = new Intent(this, HomeAct.class);
        startActivity(intent);
        Animatoo.INSTANCE.animateSplit(this);
    }

    private void addStudents() {
        Intent intent = new Intent(this, AddStudentsAct.class);
        startActivity(intent);
        Animatoo.INSTANCE.animateSlideLeft(this);
    }

    private void logOut() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("THÔNG BÁO");
        alertDialog.setMessage("Bạn muốn đăng xuất?");
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialogInterface, i) -> {
            Intent intent = new Intent(this, LoginAct.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateZoom(InforStudentAct.this);
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                (dialogInterface, i) -> alertDialog.dismiss());
        alertDialog.show();
    }
}
