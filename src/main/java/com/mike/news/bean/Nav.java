package com.mike.news.bean;

public class Nav {
    public Integer id;
    public String nav;
    public Integer type; //0 represents pure news, 1 represents can buy

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
