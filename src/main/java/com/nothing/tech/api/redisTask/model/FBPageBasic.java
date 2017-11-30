package com.nothing.tech.api.redisTask.model;


import java.io.Serializable;

public class FBPageBasic implements Serializable {

    private Long fbPageId;

    private String name;

    private String url;

    public FBPageBasic(Long fbPageId, String name, String url) {
        this.fbPageId = fbPageId;
        this.name = name;
        this.url = url;
    }

    public Long getFbPageId() {
        return fbPageId;
    }

    public void setFbPageId(Long fbPageId) {
        this.fbPageId = fbPageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
