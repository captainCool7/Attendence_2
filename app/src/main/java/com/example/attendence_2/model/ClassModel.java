package com.example.attendence_2.model;

import java.util.ArrayList;

public class ClassModel {
    private String standard;
    ArrayList<StudentModel> student_list = new ArrayList<>();

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public ArrayList<StudentModel> getStudent_list() {
        return student_list;
    }

    public void setStudent_list(ArrayList<StudentModel> student_list) {
        this.student_list = student_list;
    }
}
