package com.nothing.tech.api.scheduleTask.model;

/**
 * Created by admin on 2018/1/2.
 */
public class RobotGroupMessage {
    private String msgtype;
    private RobotGroupMessageAt at;
    private RobotGroupMessageText text;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public RobotGroupMessageAt getAt() {
        return at;
    }

    public void setAt(RobotGroupMessageAt at) {
        this.at = at;
    }

    public RobotGroupMessageText getText() {
        return text;
    }

    public void setText(RobotGroupMessageText text) {
        this.text = text;
    }
}
