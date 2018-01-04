package com.nothing.tech.api.scheduleTask.respository;

import com.nothing.tech.api.scheduleTask.model.DDPersonMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DDPersonMessageRepository extends JpaRepository<DDPersonMessage,String>,
        JpaSpecificationExecutor<DDPersonMessage> {
    /**
     * *查询指定手机号码对应的userid
     * */
    @Query("select dd.userid from  DDPersonMessage dd where dd.mobile = ?1")
    String queryByUserId(String mobile);
}
