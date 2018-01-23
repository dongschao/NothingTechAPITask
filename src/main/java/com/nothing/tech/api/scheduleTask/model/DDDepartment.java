package com.nothing.tech.api.scheduleTask.model;

/**
 * Created by admin on 2018/1/18.
 */
public class DDDepartment {

    private Boolean createDeptGroup;
    private String name;
    private Long id;
    private Boolean autoAddUser;
    private Integer parentid;

    public Boolean getCreateDeptGroup() {
        return createDeptGroup;
    }

    public void setCreateDeptGroup(Boolean createDeptGroup) {
        this.createDeptGroup = createDeptGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAutoAddUser() {
        return autoAddUser;
    }

    public void setAutoAddUser(Boolean autoAddUser) {
        this.autoAddUser = autoAddUser;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }
}
