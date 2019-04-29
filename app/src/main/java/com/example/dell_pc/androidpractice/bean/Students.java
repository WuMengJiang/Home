package com.example.dell_pc.androidpractice.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Students {
    @Id(autoincrement = true)
    private Long id;
    private String title;
    private String picUrl;
    @Generated(hash = 2071591124)
    public Students(Long id, String title, String picUrl) {
        this.id = id;
        this.title = title;
        this.picUrl = picUrl;
    }
    @Generated(hash = 174834727)
    public Students() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPicUrl() {
        return this.picUrl;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
   
}
