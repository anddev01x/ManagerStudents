package com.techja.managerstudents.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.techja.managerstudents.model.ClassroomEntity;

import java.util.List;

@Dao
public interface ClassRoomDAO {

    @Query("SELECT * FROM tbl_Class")
    List<ClassroomEntity> getAllCLass();

    @Query("SELECT * FROM tbl_Class WHERE idClass = :idClass")
    ClassroomEntity checkClassById(String idClass);

    @Insert
    void insertClass(ClassroomEntity classroom);

    @Delete
    void deleteClass(ClassroomEntity classroom);

    @Update
    void upDateClass(ClassroomEntity oldClass);

    @Query("SELECT * FROM tbl_class ORDER BY idClass ASC")
    List<ClassroomEntity> sortClassByName();


}
