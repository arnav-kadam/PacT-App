package com.example.sctapp;

public class User {

    String email, id, user_type;

    public User(){

    }

    public User(String email, String id, String user_type){

        this.email = email;
        this.id = id;
        this.user_type = user_type;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
