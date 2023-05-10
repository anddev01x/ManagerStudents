package com.techja.managerstudents.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tbl_Class")
public class ClassroomEntity implements Serializable {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String idClass;
    private String nameClass;
    private String nameLecturers;

    public ClassroomEntity(@NonNull String idClass, String nameClass, String nameLecturers) {
        this.idClass = idClass;
        this.nameClass = nameClass;
        this.nameLecturers = nameLecturers;
    }


    @NonNull
    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(@NonNull String idClass) {
        this.idClass = idClass;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getNameLecturers() {
        return nameLecturers;
    }

    public void setNameLecturers(String nameLecturers) {
        this.nameLecturers = nameLecturers;
    }
}
