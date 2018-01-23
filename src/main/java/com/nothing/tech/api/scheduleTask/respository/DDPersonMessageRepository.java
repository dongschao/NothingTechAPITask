package com.nothing.tech.api.scheduleTask.respository;

import com.nothing.tech.api.scheduleTask.model.DDPersonMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DDPersonMessageRepository extends JpaRepository<DDPersonMessage,String>,
        JpaSpecificationExecutor<DDPersonMessage> {
    /**
     * *查询指定手机号码对应的userid
     * */
    @Query("select dd.userid from  DDPersonMessage dd where dd.mobile = ?1")
    String queryByUserId(String mobile);
    @Query("select  count(*)  from  DDPersonMessage where  userid = ?1")
    int queryIsUser(String user);
    @Modifying
    @Query("update DDPersonMessage dd set dd.mobile=?1 where dd.userid=?2 and dd.id>0")
    void updateMoblie(String moblie,String userid);
//    @Query("insert into DDPersonMessage ('name','userid') VALUES (?1, ?2)")
//    void insertUserMessage(String name,String userid);
}
