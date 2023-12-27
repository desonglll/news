package com.mike.news.bean;

import com.common.dbaccess.annotation.NotNull;
import com.common.dbaccess.annotation.TableBean;

import java.util.Date;

public class News {
    public String datatime;

    public String getDatatime() {
        return datatime;
    }

    @TableBean(ignore = true)// Database will ignore this field.
    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public String nav;
    public Integer type;

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

    public Integer id;
    @NotNull
    public String title;
    @NotNull
    public String content;//not empty while add or change data
    public Date ctime;
    public Integer nid;
    public String image;
    public String photo;
    public Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
