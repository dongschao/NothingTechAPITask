package com.nothing.tech.api.redisTask.respo;

import com.nothing.tech.api.redisTask.model.FacebookPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacebookPageRepository extends MongoRepository<FacebookPage,String> {

    Page<FacebookPage> queryByFollowCountGreaterThanEqual(Integer followerCount,Pageable pageable);

}
