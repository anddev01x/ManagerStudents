package com.techja.managerstudents.view;



import static com.techja.managerstudents.view.CheckEditClassAct.ID_CLASS;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.techja.managerstudents.R;
import com.techja.managerstudents.adapter.StudentAdapter;
import com.techja.managerstudents.dao.StudentDAO;
import com.techja.managerstudents.databinding.ActStudentsInClassBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.model.BaseAct;
import com.techja.managerstudents.model.StudentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentsInClass extends BaseAct<ActStudentsInClassBinding> {
    private StudentDAO studentDAO;
    private StudentAdapter studentAdapter;
    private List<StudentEntity> listStudentsInClass;

    @Override
    protected void initViews() {
        studentDAO = AppDatabase.getInstance(this).getStudentDAO();
        setUpRecyclerView();
        searchView();
        setUpToolbar();
    }

    private void setUpToolbar() {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("Danh sách Sinh viên");
            setSupportActionBar(toolbar);

            Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, InforClassAct.class);
        startActivity(intent);
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
        for (StudentEntity student : listStudentsInClass) {
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

    private void setUpRecyclerView() {
        String idClass = getIntent().getStringExtra(ID_CLASS);
        if(idClass != null){
        listStudentsInClass = studentDAO.listStudents(idClass);
        studentAdapter = new StudentAdapter(listStudentsInClass, this::onClickStudent);
        binding.recyclerStudentInClass.setAdapter(studentAdapter);
        }
    }

    private void onClickStudent(StudentEntity student) {

    }

    @Override
    protected ActStudentsInClassBinding initViewBinding() {
        return ActStudentsInClassBinding.inflate(getLayoutInflater());
    }
}