package com.nothing.tech.api.scheduleTask;

import com.alibaba.fastjson.JSON;
import com.nothing.tech.api.scheduleTask.model.PostShortWebUrl;
import com.nothing.tech.api.scheduleTask.model.ResponseResult;
import com.nothing.tech.api.scheduleTask.utils.HttpAccessUtil;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
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
    private int requestNum = 10;//每次请求50条

   // @Scheduled(fixedDelay = 60000)//方法执行后延迟1秒再次执行获取短连接
    public void getShortUrl() throws Exception {
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
                   longResult = HttpAccessUtil.get(shortUrl);//获取长连接
                    if (longResult != null) {
                        System.out.println(longResult);
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


    public void MailClientTest() throws IOException, MessagingException, InterruptedException {
//        SendMailController cp = new SendMailController("cp", "bejavagod.com");
//        SendMailController rose = new SendMailController("jack", "bejavagod.com");
//        SendMailController jack = new SendMailController("jack", "bejavagod.com");
////        String c1 = ContentModes.content1;
//        String c2 = ContentModes.content2;

        // CLEAR EVERYBODY'S INBOX
//        cp.checkInbox(SendMailController.CLEAR_MESSAGES);
//        rose.checkInbox(SendMailController.CLEAR_MESSAGES);
//        jack.checkInbox(SendMailController.CLEAR_MESSAGES);
//        Thread.sleep(500); // Let the server catch up
        // SEND A COUPLE OF MESSAGES TO BLUE (FROM RED AND GREEN)
//        cp.sendMessage(
//                "jack@bejavagod.com,392088240@qq.com",
//                "",
//                "Testing rose from cp",
//                "Dear" + "\n" +
//                        "This is a test message." +"\n"+
//                        "Good day! ");
//        rose.sendMessage(
//               "cp@bejavagod.com",
//                "392088240@qq.com",
//                "Testing cp from rose",
//                "Dear" + "\n" +
//                        "This is a test message." +"\n"+
//                        "Good day! ");
//        rose.sendMessage(
//                "cp@bejavagod.com",
//                "392088240@qq.com",
//                "New Situation!",
//                "sadasdasdasdasdads");

        // Let the server catch up
        // LIST MESSAGES FOR BLUE (EXPECT MESSAGES FROM RED AND GREEN)
//    jack.checkInbox(MailClient.SHOW_AND_CLEAR);
//        cp.checkInbox(SendMailController.SHOW_MESSAGES);
//        rose.checkInbox(SendMailController.SHOW_AND_CLEAR);

    }











}
