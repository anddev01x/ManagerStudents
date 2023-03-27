package com.techja.managerstudents.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_Students")
public class StudentEntity {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String idStudent;
    private String fullName;
    private String birthdate;
    private String gmail;
    private String address;
    private float gpa;
    private String idClass;

    public StudentEntity(@NonNull String idStudent, String fullName, String birthdate,
                         String gmail, String address, float gpa, String idClass) {
        this.idStudent = idStudent;
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.gmail = gmail;
        this.address = address;
        this.gpa = gpa;
        this.idClass = idClass;
    }

    @NonNull
    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(@NonNull String idStudent) {
        this.idStudent = idStudent;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }
}
