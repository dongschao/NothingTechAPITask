package com.nothing.tech.api.scheduleTask.model;

import javax.persistence.*;

/**
 * Created by admin on 2018/1/18.
 */
@Entity
@Table(name = "t_group")
public class DDUserGroup {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String groupName;
        private String mobiles;
        private String userNames;

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMobiles() {
            return mobiles;
        }

        public void setMobiles(String mobiles) {
            this.mobiles = mobiles;
        }
}
