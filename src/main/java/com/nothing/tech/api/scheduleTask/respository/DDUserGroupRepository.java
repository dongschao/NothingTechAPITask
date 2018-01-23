package com.nothing.tech.api.scheduleTask.respository;

import com.nothing.tech.api.scheduleTask.model.DDUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by admin on 2018/1/18.
 */
public interface DDUserGroupRepository extends JpaRepository<DDUserGroup,String>,
        JpaSpecificationExecutor<DDUserGroup> {







}
