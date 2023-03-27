package com.techja.managerstudents.view;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.adapter.StudentAdapter;
import com.techja.managerstudents.dao.StudentDAO;
import com.techja.managerstudents.databinding.ActInforStudentBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.model.StudentEntity;

import java.util.List;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class InforStudentAct extends BaseAct<ActInforStudentBinding> {
    private ActivityResultLauncher<Intent> myLauncher;
    public static final String DATA_STUDENT = "DATA_STUDENT";
    private StudentDAO studentDAO;
    private StudentAdapter studentAdapter;
    private List<StudentEntity> listStudents;
    ItemTouchHelper.SimpleCallback simpleCallback;

    @Override
    protected ActInforStudentBinding initViewBinding() {
        return ActInforStudentBinding.inflate(getLayoutInflater());
    }

    protected void initViews() {
        CreateMenu();
        dataBase();
        setupRecyclerView();
        ActivityResultApi();
        deleteItemStudent();
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.recyclerStudent);

    }

    private void deleteItemStudent() {
        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder
                    viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                StudentEntity student = listStudents.get(position);
                listStudents.remove(position);
                studentDAO.deleteStudent(student);
                Toast.makeText(InforStudentAct.this, "XÓA SINH VIÊN THÀNH CÔNG",
                        Toast.LENGTH_SHORT).show();
                studentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX,
                                    float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY,
                        actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeLeftLabel("XÓA")
                        .setSwipeLeftLabelColor(ContextCompat.getColor(getApplicationContext(), R.color.white))
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

    }

    private void ActivityResultApi() {
        myLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> setupRecyclerView());
    }

    private void setupRecyclerView() {
        studentAdapter = new StudentAdapter();
        listStudents = studentDAO.getAllStudents();
        studentAdapter.setData(listStudents, this::onClickStudent);

        binding.recyclerStudent.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerStudent.setAdapter(studentAdapter);
    }

    private void onClickStudent(StudentEntity student) {
        Intent intent = new Intent(this, CheckEditStudentAct.class);
        Bundle data = new Bundle();
        data.putSerializable(DATA_STUDENT, student);
        intent.putExtras(data);
        myLauncher.launch(intent);
        Animatoo.INSTANCE.animateSlideDown(this);
    }

    private void dataBase() {
        studentDAO = Room.databaseBuilder(this, AppDatabase.class, "ManagerStudent").
                allowMainThreadQueries()
                .build().getStudentDAO();
    }

    @Override
    protected void clickViews(View view) {
    }

    private void CreateMenu() {
        Toolbar toolbar = findViewById(R.id.toolbar);
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
