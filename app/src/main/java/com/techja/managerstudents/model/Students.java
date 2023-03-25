package com.techja.managerstudents.model;

public class Students {
    private String idStudent;
    private String fullName;
    private String birthdate;
    private String idClass;
    private String gmail;
    private String address;
    private float gpa;
    private int year;

    public Students(String idStudent, String fullName, String birthdate, String idClass, String gmail, String address, float gpa, int year) {
        this.idStudent = idStudent;
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.idClass = idClass;
        this.gmail = gmail;
        this.address = address;
        this.gpa = gpa;
        this.year = year;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
