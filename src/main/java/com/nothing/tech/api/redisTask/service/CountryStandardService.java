package com.nothing.tech.api.redisTask.service;

import com.nothing.tech.api.redisTask.model.CountryStandard;
import com.nothing.tech.api.redisTask.respo.CountryStandardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryStandardService {

    @Autowired
    private CountryStandardRepository countryStandardRepository;

    public List<CountryStandard> queryAll(){
        return countryStandardRepository.findAll();
    }
}
