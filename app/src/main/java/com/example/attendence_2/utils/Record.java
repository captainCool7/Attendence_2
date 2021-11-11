package com.example.attendence_2.utils;

import java.util.ArrayList;

public class Record {
    public Record() {}

    ArrayList<AttendenceRecord> records;

    public ArrayList<AttendenceRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<AttendenceRecord> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        String result="";
        for(AttendenceRecord r : records){
            result+=r.getStandard()+" "+r.getPresent_list().toString();
        }
        return result;
    }
}
