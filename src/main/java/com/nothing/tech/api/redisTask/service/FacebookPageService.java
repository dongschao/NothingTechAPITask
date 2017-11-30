package com.nothing.tech.api.redisTask.service;

import com.nothing.tech.api.redisTask.model.FBPageQueryParam;
import com.nothing.tech.api.redisTask.model.FacebookPage;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacebookPageService {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<FacebookPage> queryByPageParam(FBPageQueryParam fbPageQueryParam) {
        Query query = new Query();
        if(StringUtils.isEmpty(fbPageQueryParam.getId())){
            query.addCriteria(Criteria.where("country").is(fbPageQueryParam.getCountry())
                    .and("followCount").gte(fbPageQueryParam.getFollowCount()));
            Sort sort = new Sort(Sort.Direction.ASC,"id");
            query.with(sort);
        }else{
            ObjectId objID = new ObjectId(fbPageQueryParam.getId());
            query.addCriteria(Criteria.where("country").is(fbPageQueryParam.getCountry()));
            if (fbPageQueryParam.getOrientation()==1){
                query.addCriteria(Criteria.where("_id").gt(objID)
                        .and("followCount").gte(fbPageQueryParam.getFollowCount()));
            }else {
                query.addCriteria(Criteria.where("_id").lt(objID)
                        .and("followCount").gte(fbPageQueryParam.getFollowCount()));
            }
            query.limit(fbPageQueryParam.getCount());
        }
        List<FacebookPage> facebookPageList =
                mongoTemplate.find(query,FacebookPage.class);
        return facebookPageList;
    }

}
