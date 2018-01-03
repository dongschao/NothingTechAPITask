package com.nothing.tech.api.scheduleTask.model;

/**
 * Created by admin on 2017/12/21.
 */
public class DDMessage {
    private String sender;
    private String cid;
    private String msgtype;
    private DDMessageContent text;

    public DDMessageContent getText() {
        return text;
    }

    public void setText(DDMessageContent text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}
