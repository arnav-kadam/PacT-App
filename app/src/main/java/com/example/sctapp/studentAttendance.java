package com.example.sctapp;

import java.io.Serializable;

public class studentAttendance {

    boolean school, bus;
    String date, student_id, identifier;

    public studentAttendance() {

    }

    public studentAttendance(String date, String student_id, boolean school, boolean bus, String identifier) {
        this.date = date;
        this.student_id = student_id;
        this.school = school;
        this.bus = bus;
        this.identifier = identifier;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public boolean isSchool() {
        return school;
    }

    public void setSchool(boolean school) {
        this.school = school;
    }

    public boolean isBus() {
        return bus;
    }

    public void setBus(boolean bus) {
        this.bus = bus;
    }

    public String getIdentifier(){
        return identifier;
    }

    public void setIdentifier(String identifier){
        this.identifier = identifier;
    }

}
