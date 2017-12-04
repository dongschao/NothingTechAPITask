package com.nothing.tech.api.scheduleTask.model;


public class ResponseResult {

    private Integer state;

    private String msg;

    private Object data=null;

    public ResponseResult(Integer state, String msg, String data) {
        this.state = state;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult() {
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

