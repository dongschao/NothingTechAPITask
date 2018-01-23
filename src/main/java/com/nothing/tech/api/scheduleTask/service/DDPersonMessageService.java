package com.nothing.tech.api.scheduleTask.service;


public interface DDPersonMessageService {
    String queryAllPerson(String mobile);

    int queryUserId(String userid);

    void updataMobile(String mobile,String userid);
//    void insertUserMessage(String name,String userid);
}
