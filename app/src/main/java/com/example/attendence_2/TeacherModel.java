package com.example.attendence_2;

import java.util.ArrayList;
import java.util.Vector;

public class TeacherModel {
    String name;
    String userName;
    ArrayList<Integer> class_no;

    public TeacherModel(String name, String userName,ArrayList<Integer> class_no) {
        this.name = name;
        this.userName = userName;
        this.class_no = class_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Integer> getClass_no() {
        return class_no;
    }

    public void setClass_no(ArrayList<Integer> class_no) {
        this.class_no = class_no;
    }
}
