package com.nothing.tech.api.scheduleTask.model;

import java.util.List;

/**
 * Created by admin on 2018/1/18.
 */
public class DDDepartmentResult {
    private String errmsg;
    private Integer errcode;
    private List<DDDepartment> department;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public List<DDDepartment> getDepartment() {
        return department;
    }

    public void setDepartment(List<DDDepartment> department) {
        this.department = department;
    }
}
