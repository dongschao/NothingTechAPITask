package com.nothing.tech.api.scheduleTask;

import com.alibaba.fastjson.JSON;
import com.nothing.tech.api.scheduleTask.model.PostShortWebUrl;
import com.nothing.tech.api.scheduleTask.model.ResponseResult;
import com.nothing.tech.api.scheduleTask.utils.HttpAccessUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 转换短连接为长连接的定时类
 */
@Component
public class UpdatePostShortUrlToPostLongUrl {


    private String queryShortUrl = "http://db.anyelse.com/nothingTech/nothing/collect/facebook/page/post/queryShortURL";//获取短连接的接口
    private String updateShortUrl = "http://db.anyelse.com/nothingTech/nothing/collect/facebook/page/post/updateShortURL";//更新短连接的接口
    private String result;
    private List<PostShortWebUrl> postShortWebUrlList;
    private int requestNum = 50;//每次请求50条

    @Scheduled(fixedDelay = 1000)//方法执行后延迟1秒再次执行获取短连接
    public void getShortUrl() {
        try {
            result = HttpAccessUtil.getShortUrlMethod(queryShortUrl, requestNum);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (result != null) {
            ResponseResult responseResult = JSON.parseObject(result, ResponseResult.class);
            Object data = responseResult.getData();

            postShortWebUrlList = JSON.parseArray(data.toString(), PostShortWebUrl.class);//转为PostShortWebUrl对象的集合
            if (postShortWebUrlList.size() > 0) {
                getLongUrl();
            } else {
                return;
            }
        }


    }

    /**
     * 获取长连接
     */
    public void getLongUrl() {
        String longResult = null;

        for (PostShortWebUrl postShortWebUrl : postShortWebUrlList) {
            try {
                String shortUrl = postShortWebUrl.getShortUrl().trim().toString().replace(" ", "");//去除短连接空格
                if (shortUrl != null) {
                    longResult = HttpAccessUtil.getLongUrlMethod(shortUrl);//获取长连接
                    if (longResult != null) {
                        updateLongUrl(postShortWebUrl.getPostId(), longResult);//更新链接
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }


        }

        postShortWebUrlList.clear();
    }

    /**
     * 更新链接
     *
     * @param postId
     * @param longUrl
     * @throws Exception
     */
    public void updateLongUrl(Long postId, String longUrl) throws Exception {
//        System.out.println("longurl"+longUrl+"......."+postId);

        if (postId != null && postId != 0 && longUrl != null) {
            HttpAccessUtil.updateUrlMethod(updateShortUrl, postId, longUrl);
        }

    }


}
