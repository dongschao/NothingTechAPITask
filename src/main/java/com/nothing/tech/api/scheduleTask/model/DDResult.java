package com.nothing.tech.api.scheduleTask.model;

import java.util.List;

/**
 * Created by admin on 2018/1/17.
 */
public class DDResult {
        private  Integer errorCode;
        private  String errmsg;
        private List<DDUserList> userList;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public List<DDUserList> getUserList() {
        return userList;
    }

    public void setUserList(List<DDUserList> userList) {
        this.userList = userList;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
