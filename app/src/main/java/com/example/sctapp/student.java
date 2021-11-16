package com.example.sctapp;

import java.io.Serializable;

public class student implements Serializable {

    String name, grade, bus_number, seat_number, student_id;
    String class1, class2, class3, class4, class5, class6, class7, class8;
    boolean highlight;

    public student() {

    }

    public student(String name, String grade, String student_id){
        this.name = name;
        this.grade = grade;
        this.student_id = student_id;
    }

    public student(String name, String grade, String bus_number, String seat_number, String student_id, boolean highlight) {
        this.name = name;
        this.grade = grade;
        this.bus_number = bus_number;
        this.seat_number = seat_number;
        this.student_id = student_id;
        this.highlight = highlight;
    }

    public student(String class1, String class2, String class3, String class4, String class5, String class6, String class7, String class8){
        this.class1 = class1;
        this.class2 = class2;
        this.class3 = class3;
        this.class4 = class4;
        this.class5 = class5;
        this.class6 = class6;
        this.class7 = class7;
        this.class8 = class8;
    }

    public student(String name, String grade, String bus_number, String seat_number, String student_id, boolean highlight, String class1, String class2, String class3, String class4, String class5, String class6, String class7, String class8){
        this.name = name;
        this.grade = grade;
        this.bus_number = bus_number;
        this.seat_number = seat_number;
        this.student_id = student_id;
        this.highlight = highlight;
        this.class1 = class1;
        this.class2 = class2;
        this.class3 = class3;
        this.class4 = class4;
        this.class5 = class5;
        this.class6 = class6;
        this.class7 = class7;
        this.class8 = class8;
    }

    public String returnRoomNumber(String classNumber){
        if (classNumber.equals("1")){
            return class1;
        }
        else if(classNumber.equals("2")){
            return class2;
        }
        else if(classNumber.equals("3")){
            return class3;
        }
        else if(classNumber.equals("4")){
            return class4;
        }
        else if(classNumber.equals("5")){
            return class5;
        }
        else if(classNumber.equals("6")){
            return class6;
        }
        else if(classNumber.equals("7")){
            return class7;
        }
        else if(classNumber.equals("8")){
            return class8;
        }
        else {
            return null;
        }
    }

    public String getClass_1() {
        return class1;
    }

    public void setClass_1(String class1) {
        this.class1 = class1;
    }

    public String getClass_2() {
        return class2;
    }

    public void setClass_2(String class2) {
        this.class2 = class2;
    }

    public String getClass_3() {
        return class3;
    }

    public void setClass_3(String class3) {
        this.class3 = class3;
    }

    public String getClass_4() {
        return class4;
    }

    public void setClass_4(String class4) {
        this.class4 = class4;
    }

    public String getClass_5() {
        return class5;
    }

    public void setClass_5(String class5) {
        this.class5 = class5;
    }

    public String getClass_6() {
        return class6;
    }

    public void setClass_6(String class6) {
        this.class6 = class6;
    }

    public String getClass_7() {
        return class7;
    }

    public void setClass_7(String class7) {
        this.class7 = class7;
    }

    public String getClass_8() {
        return class8;
    }

    public void setClass_8(String class8) {
        this.class8 = class8;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public void setHighlight(){
        this.highlight = highlight;
    }
}
