package com.example.lynnyuki.cloudfunny.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 收藏管理实体Bean
 */
@Entity
public class LikeBean {
    @Id(autoincrement = true)
    private Long id;

    private String guid;

    private String imageUrl;

    private String title;

    private String url;

    private int type;

    private long time;

    @Generated(hash = 693919166)
    public LikeBean(Long id, String guid, String imageUrl, String title, String url,
            int type, long time) {
        this.id = id;
        this.guid = guid;
        this.imageUrl = imageUrl;
        this.title = title;
        this.url = url;
        this.type = type;
        this.time = time;
    }

    @Generated(hash = 1258777425)
    public LikeBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
