package com.example.sctapp;

public class admin {

    String admin_id, admin_type, email, name;

    public admin(){

    }

    public admin(String admin_id, String admin_type, String email, String name){
        this.admin_id = admin_id;
        this.admin_type = admin_type;
        this.email = email;
        this.name = name;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_type() {
        return admin_type;
    }

    public void setAdmin_type(String admin_type) {
        this.admin_type = admin_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
