package com.nothing.tech.api.scheduleTask;

import com.alibaba.fastjson.JSON;
import com.nothing.tech.api.scheduleTask.model.DDPersonMessage;
import com.nothing.tech.api.scheduleTask.model.DDUserGroup;
import com.nothing.tech.api.scheduleTask.model.ResponseResult;
import com.nothing.tech.api.scheduleTask.respository.DDPersonMessageRepository;
import com.nothing.tech.api.scheduleTask.respository.DDUserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by admin on 2018/1/18.
 */
@CrossOrigin(origins = "*", maxAge = 10800)
@RestController
public class DDConfiguerController {
    @Autowired
    private DDPersonMessageRepository ddPersonMessageRepository;
    @Autowired
    private DDUserGroupRepository ddUserGroupRepository;
    
    @RequestMapping(value = "/nothing/ddConfiguer/getUserMessage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult ObtainingEmployeeInformation(){
            ResponseResult responseResult = new ResponseResult();
            responseResult.setState(0);
            responseResult.setMsg("ok");
            List<DDPersonMessage> DDPersonMessages = ddPersonMessageRepository.findAll();
            responseResult.setData(JSON.toJSONString(DDPersonMessages));
            return responseResult;
    }
    @RequestMapping(value = "/nothing/ddConfiguer/greatNewGroup", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult greatNewGroup(String mobiles,String groupName,String userNames){
        ResponseResult responseResult = new ResponseResult();
        DDUserGroup userGroup = new DDUserGroup();
        userGroup.setUserNames(userNames);
        userGroup.setGroupName(groupName);
        userGroup.setMobiles(mobiles);
        ddUserGroupRepository.save(userGroup);
        responseResult.setState(0);
        responseResult.setMsg("ok");
        return responseResult;
    }
    @RequestMapping(value = "/nothing/ddConfiguer/editGroup", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult editGroup(Integer groupId){

        ResponseResult responseResult = new ResponseResult();
        responseResult.setState(0);


        return responseResult;
    }
    @RequestMapping(value = "/nothing/ddConfiguer/deleteGroup", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult deleteGroup(Integer groupId){

        ResponseResult responseResult = new ResponseResult();


        ddUserGroupRepository.delete(groupId+"");
        return responseResult;
    }

    @RequestMapping(value = "/nothing/ddConfiguer/getGroupMessage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getGroupMessage(){

        ResponseResult responseResult = new ResponseResult();
        responseResult.setState(0);


        return responseResult;
    }
































}
