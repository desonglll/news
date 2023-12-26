package com.mike.news.bean;

public class User {
    // tbl_user表，表中有什么，这个类中就有什么
    public Integer id;
    public String username;
    public String password;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
