package com.nothing.tech.api.redisTask.respo;

import com.nothing.tech.api.redisTask.model.CountryStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CountryStandardRepository extends JpaRepository<CountryStandard,Long>,
        JpaSpecificationExecutor<CountryStandard> {

}
