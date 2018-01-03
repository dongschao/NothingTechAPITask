package com.nothing.tech.api.scheduleTask.model;

import java.util.List;

/**
 * Created by admin on 2018/1/2.
 */
public class RobotGroupMessageAt {
    private Boolean isAtAll;
    private List<String> atMobiles;

    public Boolean getAtAll() {
        return isAtAll;
    }

    public void setAtAll(Boolean atAll) {
        isAtAll = atAll;
    }

    public List<String> getAtMobiles() {
        return atMobiles;
    }

    public void setAtMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
    }
}
