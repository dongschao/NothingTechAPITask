package com.nothing.tech.api.redisTask.service;

import com.nothing.tech.api.redisTask.model.FBPageQueryParam;
import com.nothing.tech.api.redisTask.model.FacebookPage;
import com.nothing.tech.api.redisTask.respo.FacebookPageRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacebookPageService {

    @Autowired
    private FacebookPageRepository facebookPageRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<FacebookPage> queryByPageParam(FBPageQueryParam fbPageQueryParam) {
        Query query = new Query();
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(0,fbPageQueryParam.getCount(),sort);
        if(fbPageQueryParam.getId()==null||fbPageQueryParam.getId()==""){
            Page<FacebookPage> facebookPagePage =
                    facebookPageRepository.queryByFollowCountGreaterThanEqual(fbPageQueryParam.getFollowCount(),
                            pageable);
            return facebookPagePage.getContent();
        }
        ObjectId objID = new ObjectId(fbPageQueryParam.getId());
        if (fbPageQueryParam.getOrientation()==1){
            query.addCriteria(Criteria.where("id").gt(objID)
                    .and("followCount").gte(fbPageQueryParam.getFollowCount()));
        }else {
            query.addCriteria(Criteria.where("id").lt(objID)
                    .and("followCount").gte(fbPageQueryParam.getFollowCount()));
        }

        query.limit(fbPageQueryParam.getCount());

        List<FacebookPage> facebookPageList =
                mongoTemplate.find(query,FacebookPage.class);
//        List<FacebookPage> facebookPageList2 =
//                mongoTemplate.find(query,FacebookPage.class,"facebookPage");
        return facebookPageList;
    }

}
