package com.nothing.tech.api.scheduleTask.impl;

import com.nothing.tech.api.scheduleTask.respository.DDPersonMessageRepository;
import com.nothing.tech.api.scheduleTask.service.DDPersonMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DDPersonMessageServiceImpl implements DDPersonMessageService {

    @Autowired
    private DDPersonMessageRepository personMessageRepository;

    @Override
    public String queryAllPerson(String mobile){
        return personMessageRepository.queryByUserId(mobile);
    }



}
