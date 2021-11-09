
package com.example.attendence_2.model;

import java.util.ArrayList;

public class TeacherModel {
    private String name;
    private String u_id;
    private String email;
    private ArrayList<Integer> class_no;
//    private TeacherModel currentTeacher;

    public TeacherModel(){}

    public TeacherModel(String name, String u_id, String email) {
        this.name = name;
        this.u_id = u_id;
        this.email = email;
    }

//    public TeacherModel(String name, String u_id, String email, ArrayList<Integer> class_no) {
//        this.name = name;
//        this.u_id = u_id;
//        this.email = email;
//        this.class_no = class_no;
//    }

    public TeacherModel(String name, String userName, ArrayList<Integer> class_no) {
        this.name = name;
        this.u_id = userName;
        this.class_no = class_no;
    }

//    public TeacherModel getCurrentTeacher() {
//        return currentTeacher;
//    }
//
//    public void setCurrentTeacher(TeacherModel currentTeacher) {
//        this.currentTeacher = currentTeacher;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Integer> getClass_no() {
        return class_no;
    }

    public void setClass_no(ArrayList<Integer> class_no) {
        this.class_no = class_no;
    }

    @Override
    public String toString() {
        return "TeacherModel{" +
                "name='" + name + '\'' +
                ", u_id='" + u_id + '\'' +
                ", email='" + email + '\'' +
                ", class_no=" + class_no +
                '}';
    }
}
