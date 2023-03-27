package com.techja.managerstudents.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.techja.managerstudents.model.StudentEntity;

import java.util.List;

@Dao
public interface StudentDAO {

    @Query("SELECT * FROM tbl_Students")
    List<StudentEntity> getAllStudents();

    @Insert
    void insertStudent(StudentEntity studentEntity);

    @Query("SELECT idStudent FROM tbl_Students WHERE idStudent = :idStudent")
    boolean getStudentById(String idStudent);
}
