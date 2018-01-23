package com.nothing.tech.api.scheduleTask;

import com.alibaba.fastjson.JSON;
import com.nothing.tech.api.scheduleTask.model.*;
import com.nothing.tech.api.scheduleTask.respository.DDPersonMessageRepository;
import com.nothing.tech.api.scheduleTask.service.DDPersonMessageService;
import com.nothing.tech.api.scheduleTask.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/1/17.
 */
@CrossOrigin(origins = "*", maxAge = 10800)
@RestController
public class GetUserMessageTask {

    @Autowired
    private DDPersonMessageService personMessageService;

    @Autowired
    private DDPersonMessageRepository ddPersonMessageRepository;

    private static String parentid = "31722198";

    @RequestMapping(value = "/nothing/ddNoticeMessage/test", method = RequestMethod.GET)
    @ResponseBody
        public String getUserMessage(){
        saveParMessage();
        List<Long>depList = getDepartment();
        for (int i = 0;i<depList.size();i++){
            String Token = SendMessageController.getAccessToken();
            String result = HttpUtil.sendGet("https://oapi.dingtalk.com/user/simplelist","access_token="+Token+"&department_id="+depList.get(i));
            //  DDResult ddResult = (DDResult) JSON.parse(result);
            //  List<DDResult> ddResults = JSON.parseArray(result.toString(), DDResult.class);
            DDResult ddResult = JSON.parseObject(result, DDResult.class);

            System.out.println(ddResult.getErrmsg());
            List<DDUserList> ddUserLists = ddResult.getUserList();
            for (DDUserList list : ddUserLists) {
                System.out.println(list.getName()+list.getUserid()+"\n");
                if (personMessageService.queryUserId(list.getUserid())>0){
                    System.out.println("数据库有此条数据 跳过");
                }else {
//                    personMessageService.insertUserMessage(list.getName(),list.getUserid());
                    DDPersonMessage ddPersonMessage = new DDPersonMessage();
                    ddPersonMessage.setName(list.getName());
                    ddPersonMessage.setUserid(list.getUserid());
                    ddPersonMessageRepository.save(ddPersonMessage);
                }
            }
        }
            return "";
        }

        public List<Long> getDepartment(){
            List<Long> departList = new ArrayList<>();
            String Token = SendMessageController.getAccessToken();
            String result = HttpUtil.sendGet("https://oapi.dingtalk.com/department/list","access_token="+Token+"&id="+parentid);
            DDDepartmentResult ddResult = JSON.parseObject(result, DDDepartmentResult.class);
            for (DDDepartment parment:ddResult.getDepartment()) {
                departList.add(parment.getId());
            }
            return departList;
        }
        public void saveParMessage(){
            String Token = SendMessageController.getAccessToken();
            String result = HttpUtil.sendGet("https://oapi.dingtalk.com/user/simplelist","access_token="+Token+"&department_id="+parentid);
            //  DDResult ddResult = (DDResult) JSON.parse(result);
            //  List<DDResult> ddResults = JSON.parseArray(result.toString(), DDResult.class);
            DDResult ddResult = JSON.parseObject(result, DDResult.class);

            System.out.println(ddResult.getErrmsg());
            List<DDUserList> ddUserLists = ddResult.getUserList();
            for (DDUserList list : ddUserLists) {
                System.out.println(list.getName()+list.getUserid()+"\n");
                if (personMessageService.queryUserId(list.getUserid())>0){
                }else {
//                    personMessageService.insertUserMessage(list.getName(),list.getUserid());
                    DDPersonMessage ddPersonMessage = new DDPersonMessage();
                    ddPersonMessage.setName(list.getName());
                    ddPersonMessage.setUserid(list.getUserid());
                    ddPersonMessageRepository.save(ddPersonMessage);
                }

            }
}
            @RequestMapping(value = "/nothing/ddNoticeMessage/testsave", method = RequestMethod.GET)
            @ResponseBody
            public void saveMoblie(){
                List<DDPersonMessage> DDPersonMessages = ddPersonMessageRepository.findAll();
                for (DDPersonMessage message: DDPersonMessages) {
                    String Token = SendMessageController.getAccessToken();
                    String result = HttpUtil.sendGet("https://oapi.dingtalk.com/user/get","access_token="+Token+"&userid="+message.getUserid());
                    DDUserDetailMessage userDetailMessage = JSON.parseObject(result, DDUserDetailMessage.class);


                    System.out.println(userDetailMessage.getMobile()+"=============="+userDetailMessage.getUserid());

                    personMessageService.updataMobile(userDetailMessage.getMobile(),userDetailMessage.getUserid());

                }
            }
}
