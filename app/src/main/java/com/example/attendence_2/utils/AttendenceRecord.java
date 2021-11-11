package com.example.attendence_2.utils;

import java.util.ArrayList;

public class AttendenceRecord {
    ArrayList<String> present_list;
    String standard;

    public AttendenceRecord() {
    }

    public AttendenceRecord(ArrayList<String> present_list, String standard) {
        this.present_list = present_list;
        this.standard = standard;
    }

    public ArrayList<String> getPresent_list() {
        return present_list;
    }

    public void setPresent_list(ArrayList<String> present_list) {
        this.present_list = present_list;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }
}
