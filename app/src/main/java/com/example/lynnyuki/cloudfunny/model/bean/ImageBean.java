package com.example.lynnyuki.cloudfunny.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 图片实体
 */
@Entity
public class ImageBean {
    @Id
    private long id;
    private String name;
    private String create_at;
    private int width;
    private int height;
    private String url;
    @Generated(hash = 642168131)
    public ImageBean(long id, String name, String create_at, int width, int height,
            String url) {
        this.id = id;
        this.name = name;
        this.create_at = create_at;
        this.width = width;
        this.height = height;
        this.url = url;
    }
    @Generated(hash = 645668394)
    public ImageBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCreate_at() {
        return this.create_at;
    }
    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }
    public int getWidth() {
        return this.width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
}
