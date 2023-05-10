package com.techja.managerstudents.view;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techja.managerstudents.R;
import com.techja.managerstudents.adapter.ClassAdapter;
import com.techja.managerstudents.dao.ClassRoomDAO;
import com.techja.managerstudents.databinding.ActInforClassBinding;
import com.techja.managerstudents.db.AppDatabase;
import com.techja.managerstudents.model.BaseAct;
import com.techja.managerstudents.model.ClassroomEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class InforClassAct extends BaseAct<ActInforClassBinding> {
    private ClassRoomDAO classDAO;
    private ClassAdapter classAdapter;
    private List<ClassroomEntity> listClass;
    private ActivityResultLauncher<Intent> myLauncher;
    public static final String DATA_CLASS = "DATA_CLASS";
    ItemTouchHelper.SimpleCallback simpleCallback;

    @Override
    protected ActInforClassBinding initViewBinding() {
        return ActInforClassBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        createMenu();
        classDAO = AppDatabase.getInstance(this).getClassRoomDAO();
        setUpRecyclerView();
        searchView();
        ActivityResultApi();
        deleteItemClass();
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.recyclerClass);
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
        List<ClassroomEntity> filterList = new ArrayList<>();
        for (ClassroomEntity classroom : listClass) {
            if (classroom.getIdClass().toLowerCase().contains(text.toLowerCase()) ||
                    classroom.getNameClass().toLowerCase().contains(text.toLowerCase()) ||
                    classroom.getNameLecturers().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(classroom);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(this, "Không có kết quả", Toast.LENGTH_SHORT).show();
        } else {
            classAdapter.setFilterList(filterList);
        }
    }

    private void deleteItemClass() {
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
                ClassroomEntity classroom = listClass.get(position);
                listClass.remove(position);
                Toast.makeText(InforClassAct.this,
                        "Xóa lớp học thành công", Toast.LENGTH_SHORT).show();
                classDAO.deleteClass(classroom);
                classAdapter.notifyDataSetChanged();
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
                    InforClassAct.this.setUpRecyclerView();
                });
    }

    @SuppressLint("ResourceAsColor")
    private void createMenu() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Quản lý Lớp học");
        toolbar.setBackgroundColor(R.color.red);
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
            addClass();
        } else if (R.id.exit == item.getItemId()) {
            logOut();
        }
        else if (R.id.sort == item.getItemId()) {
            sortItemClass();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortItemClass() {
        listClass = classDAO.sortClassByName();
        classAdapter.setSortItem(listClass);
    }

    private void BackHome() {
        Intent intent = new Intent(this, HomeAct.class);
        startActivity(intent);
        Animatoo.INSTANCE.animateSplit(this);
    }

    private void addClass() {
        Intent intent = new Intent(this, AddClassAct.class);
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
            Animatoo.INSTANCE.animateZoom(InforClassAct.this);
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                (dialogInterface, i) -> alertDialog.dismiss());
        alertDialog.show();
    }

    private void setUpRecyclerView() {
        listClass = classDAO.getAllCLass();
        classAdapter = new ClassAdapter(listClass, this::onClickClass);
        binding.recyclerClass.setLayoutManager(new LinearLayoutManager(this));
        listClass.add(new ClassroomEntity("CNPM", "Cong Nghe Phan Mem"
                ,"Phan Thanh Son"));
        listClass.add(new ClassroomEntity("TTNT", "Tri Tue Nhan Tao"
                ,"Bui Van Hau"));
        listClass.add(new ClassroomEntity("CNPM", "An Toan Thong Tin"
                ,"Nguyen Quoc Anh"));
        binding.recyclerClass.setAdapter(classAdapter);
    }

    private void onClickClass(ClassroomEntity classroom) {
        Intent intent = new Intent(this, CheckEditClassAct.class);
        Bundle data = new Bundle();
        data.putSerializable(DATA_CLASS, classroom);
        intent.putExtras(data);
        myLauncher.launch(intent);
        Animatoo.INSTANCE.animateZoom(this);
    }

}