package com.nothing.tech.api.redisTask.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Document(collection = "facebookPage")
@TypeAlias("FacebookPage")
public class FacebookPage {

    @Id
    @JsonIgnore
    private String id;

    /**
     * The page facebook id
     * */
    private Long fbPageId;

    /**
     * The page name id
     * */
    private String nameId;

    /**
     * The page name
     * */
    private String name;

    /**
     * The page phone
     * */
    private String phone;

    /**
     * The page email
     * */
    private String email;

    /**
     * The page language
     * */
    private String language;

    /**
     * The page country
     * */
    private String country;

    /**
     * The facebook url of this page
     * */
    private String pageURL;

    /**
     * The logo url of this page
     * */
    private String logoURL;

    /**
     * The point of this page
     * */
    private Float point;

    /**
     * the current like count of this page
     * */
    private Integer likeCount;

    /**
     * the current follow count of this page
     * */
    private Integer followCount;

    /**
     * the types of this page
     * */
    private List<String> pageTypes;

    /**
     * the location of this page
     * */
    private String location;

    /**
     * the founded date of this page
     * */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date foundedDate;

    /**
     * the description of this page
     * */
    private String description;

    /**
     * whether verified of this page
     * */
    private Boolean verified;

    /**
     * new insert time or update time
     * */
    @JsonIgnore
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime = new Date();


    public Long getFbPageId() {
        return fbPageId;
    }

    public void setFbPageId(Long fbPageId) {
        this.fbPageId = fbPageId;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public Float getPoint() {
        return point;
    }

    public void setPoint(Float point) {
        this.point = point;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public List<String> getPageTypes() {
        return pageTypes;
    }

    public void setPageTypes(List<String> pageTypes) {
        this.pageTypes = pageTypes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getFoundedDate() {
        return foundedDate;
    }

    public void setFoundedDate(Date foundedDate) {
        this.foundedDate = foundedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
