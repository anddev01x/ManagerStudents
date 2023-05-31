package com.techja.managerstudents.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.adapter.StudentAdapter;
import com.techja.managerstudents.dao.StudentDAO;
import com.techja.managerstudents.databinding.ActInforStudentBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.model.BaseAct;
import com.techja.managerstudents.model.StudentEntity;

import java.util.ArrayList;
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
        createMenu();
        studentDAO = AppDatabase.getInstance(this).getStudentDAO();
        setUpRecyclerView();
        searchView();
        ActivityResultApi();
        deleteItemStudent();
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.recyclerStudent);

    }

    private void searchView() {
        binding.searchView.clearFocus();
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                filterList(text);
                return true;
            }
        });
    }

    private void filterList(String text) {
        List<StudentEntity> filterList = new ArrayList<>();
        for (StudentEntity student : listStudents) {
            if (student.getIdStudent().toLowerCase().contains(text.toLowerCase()) ||
                    student.getFullName().toLowerCase().contains(text.toLowerCase()) ||
                    student.getIdClass().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(student);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(this, "Không có kết quả", Toast.LENGTH_SHORT).show();
        } else {
            studentAdapter.setFilterList(filterList);
        }
    }

    private void deleteItemStudent() {
        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder
                    viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                StudentEntity student = listStudents.get(position);
                listStudents.remove(position);
                Toast.makeText(InforStudentAct.this,
                        "Xóa sinh viên thành công", Toast.LENGTH_SHORT).show();
                studentDAO.deleteStudent(student);
                studentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX,
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
                result -> {
                    Log.i("RESULT", "ActivityResultApi: Back");
                    InforStudentAct.this.setUpRecyclerView();
                });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setUpRecyclerView() {
        listStudents = studentDAO.getAllStudents();
        studentAdapter = new StudentAdapter(listStudents, this::onClickStudent);
        binding.recyclerStudent.setAdapter(studentAdapter);
//        DividerItemDecoration decoration = new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL);
//        decoration.setDrawable(getDrawable(R.drawable.divider));
//        binding.recyclerStudent.addItemDecoration(decoration);
    }

    private void onClickStudent(StudentEntity student) {
        Intent intent = new Intent(this, CheckEditStudentAct.class);
        Bundle data = new Bundle();
        data.putSerializable(DATA_STUDENT, student);
        intent.putExtras(data);
        myLauncher.launch(intent);
        Animatoo.INSTANCE.animateZoom(this);
    }

    @Override
    protected void clickViews(View view) {
    }

    private void createMenu() {
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
    public void onBackPressed() {
        BackHome();
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
        else if (R.id.sort == item.getItemId()) {
            sortItemStudent();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortItemStudent() {
        listStudents = studentDAO.sortStudentById();
        studentAdapter.setSortItem(listStudents);
    }

    private void BackHome() {
        Intent intent = new Intent(this, HomeAct.class);
        startActivity(intent);
        Animatoo.INSTANCE.animateSplit(this);
    }

    private void addStudents() {
        Intent intent = new Intent(this, AddStudentsAct.class);
        startActivity(intent);
        Animatoo.INSTANCE.animateZoom(this);
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
