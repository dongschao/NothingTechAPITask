//package com.nothing.tech.api.redisTask.task;
//
//
//import com.alibaba.fastjson.JSON;
//import com.nothing.tech.api.redisTask.common.Constant;
//import com.nothing.tech.api.redisTask.model.CountryStandard;
//import com.nothing.tech.api.redisTask.model.FBPageBasic;
//import com.nothing.tech.api.redisTask.model.FBPageQueryParam;
//import com.nothing.tech.api.redisTask.model.FacebookPage;
//import com.nothing.tech.api.redisTask.service.CountryStandardService;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.DefaultTypedTuple;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ZSetOperations;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import com.nothing.tech.api.redisTask.service.FacebookPageService;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Component
//public class SyncDataTask {
//
//    private static final Logger LOGGER = Logger.getLogger(SyncDataTask.class);
//
//    @Autowired
//    private FacebookPageService facebookPageService;
//
//    @Autowired
//    private RedisTemplate<String,Object> redisTemplate;
//
//    @Value("${page.sync.every.count}")
//    private Integer count;
//
//    @Autowired
//    private CountryStandardService countryStandardService;
//
//    @Scheduled(fixedDelay = 60000)
//    public void syncDataFromMongo(){
//        LOGGER.info("start syncDataFromMongo!!");
//
//        List<CountryStandard> countryStandardList = countryStandardService.queryAll();
//        if (countryStandardList.size()==0){
//            LOGGER.info("国家指定采集标准未指定");
//            return;
//        }
//
//        for (CountryStandard countryStandard:countryStandardList){
//            String country = countryStandard.getCountry();
//            FBPageQueryParam fbPageQueryParam = new FBPageQueryParam();
//            fbPageQueryParam.setCount(count);
//            fbPageQueryParam.setFollowCount(countryStandard.getStandard());
//            fbPageQueryParam.setCountry(country);
//            fbPageQueryParam.setOrientation(1);
//            /**
//             * 首先判断是否为首次同步，即判断redis中是否存在同步过的ID
//             * */
//            String syncID = (String) redisTemplate.opsForValue().get(
//                    Constant.SYNC_ID+country);
//            if (StringUtils.isEmpty(syncID)){
//                fbPageQueryParam.setId(null);
//                List<FacebookPage> facebookPageList =
//                        facebookPageService.queryByPageParam(fbPageQueryParam);
//                /**
//                 * 判断数据库中是否有数据
//                 * */
//                if (facebookPageList!=null&&facebookPageList.size()>0){
//                    /**
//                     * 第一步，将查询数据保存到redis中
//                     * */
//                    saveDate2Redis(facebookPageList,countryStandard);
//                    /**
//                     * 第二步，更新已经获取过的ID信息，便于下次查询
//                     * */
//                    FacebookPage facebookPageLast =
//                            facebookPageList.get(facebookPageList.size()-1);
//                    syncID = facebookPageLast.getId();
//                    redisTemplate.opsForValue().set(Constant.SYNC_ID+country,syncID);
//                }
//            }
//            while (true){
//                syncID=(String) redisTemplate.opsForValue().get(Constant.SYNC_ID+country);
//                LOGGER.info("获取上一次最后一条的ID:"+syncID);
//                fbPageQueryParam.setId(syncID);
//                List<FacebookPage> facebookPageList =
//                        facebookPageService.queryByPageParam(fbPageQueryParam);
//                LOGGER.info("fbPageQueryParam:"+ JSON.toJSONString(fbPageQueryParam));
//                LOGGER.info("查询获得的pageList长度:"+facebookPageList.size());
//                if (facebookPageList.size()>0){
//                    /**
//                     * 第一步，将查询数据保存到redis中
//                     * */
//                    saveDate2Redis(facebookPageList,countryStandard);
//                    /**
//                     * 第二步，更新已经获取过的ID信息，便于下次查询
//                     * */
//                    FacebookPage facebookPageLast =
//                            facebookPageList.get(facebookPageList.size()-1);
//                    syncID = facebookPageLast.getId();
//                    redisTemplate.opsForValue().set(Constant.SYNC_ID+country,syncID);
//                }else {
//                    break;
//                }
//            }
//        }
//
//    }
//
//    public void saveDate2Redis(List<FacebookPage> facebookPageList,CountryStandard countryStandard){
//        for (FacebookPage facebookPage:facebookPageList){
//            Set<ZSetOperations.TypedTuple<Object>> tupleSet = new HashSet<>();
//            FBPageBasic fbPageBasic = new FBPageBasic(facebookPage.getFbPageId(),
//                    facebookPage.getName(),facebookPage.getPageURL());
//            ZSetOperations.TypedTuple<Object> fbUserBasicTypedTuple =
//                    new DefaultTypedTuple<>(fbPageBasic, (double) System.nanoTime());
//            tupleSet.add(fbUserBasicTypedTuple);
//            redisTemplate.opsForZSet().add(Constant.SPECIAL_PAGE+
//                    countryStandard.getStandard()+":"+countryStandard.getCountry(),tupleSet);
//        }
//    }
//}
