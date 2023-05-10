package com.techja.managerstudents.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.techja.managerstudents.model.StudentEntity;

import java.util.List;

@Dao
public interface StudentDAO {

    @Query("SELECT * FROM tbl_Students")
    List<StudentEntity> getAllStudents();

    @Insert
    void insertStudent(StudentEntity studentEntity);

    @Query("SELECT * FROM tbl_Students WHERE idStudent = :idStudent")
    StudentEntity checkStudentById(String idStudent);


    @Update()
    void upDateStudent(StudentEntity student);

    @Delete
    void deleteStudent(StudentEntity student);

    @Query("SELECT * FROM tbl_Students ORDER BY idStudent ASC")
    List<StudentEntity> sortStudentById();


}
