package com.nothing.tech.api.scheduleTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.CorpMessageCorpconversationAsyncsendRequest;
import com.dingtalk.api.request.CorpMessageCorpconversationGetsendprogressRequest;
import com.dingtalk.api.request.CorpMessageCorpconversationGetsendresultRequest;
import com.dingtalk.api.response.CorpMessageCorpconversationAsyncsendResponse;
import com.dingtalk.api.response.CorpMessageCorpconversationGetsendprogressResponse;
import com.dingtalk.api.response.CorpMessageCorpconversationGetsendresultResponse;
import com.nothing.tech.api.scheduleTask.model.ResultToken;
import com.nothing.tech.api.scheduleTask.model.RobotGroupMessage;
import com.nothing.tech.api.scheduleTask.model.RobotGroupMessageAt;
import com.nothing.tech.api.scheduleTask.model.RobotGroupMessageText;
import com.nothing.tech.api.scheduleTask.utils.HttpUtil;
import com.taobao.api.ApiException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.List;



@CrossOrigin(origins = "*", maxAge = 10800)
@RestController
public class SendMessageController {
    // CorpId
    private static String CorpId = "ding4e90c20927bcf74335c2f4657eb6378f";
    //CorpSecret
    private static String CorpSecret = "6Ah-fZ_HBP5diNVCYti4xxWTkHt_yfexge22C1prXFoBcG8KsZotjMpp254lJBXq";
    private static Long AgentId = 155179175L;
    @RequestMapping(value = "/nothing/mail/send", method = RequestMethod.POST)
    @ResponseBody
    public  String SendEmail(@RequestParam String to,@RequestParam String cc,@RequestParam String subject,@RequestParam String content) throws MessagingException, IOException, InterruptedException {
        if (StringUtils.isEmpty(to)&&StringUtils.isEmpty(cc)&&StringUtils.isEmpty(subject)&&StringUtils.isEmpty(content)){
         return "error";
        }
        SendMailController cp = new SendMailController("cp", "bejavagod.com");
        System.out.println(to);
        System.out.println(cc);
        System.out.println(subject);
        System.out.println(content);
        cp.sendMessage(to,cc,subject,content);
        //cp.checkInbox(SendMailController.SHOW_AND_CLEAR);
       // Thread.sleep(500);
        return "success";
    }

    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=3bea869912d7d9c9688c1b11e921126e5bbfe82fa463bba0dce58d2dc1ff05dd";
    @RequestMapping(value = "/nothing/ddRobotGroup/send", method = RequestMethod.POST)
    @ResponseBody
    public String SendRobotGroup(@RequestParam String content,@RequestParam List<String> mobiles) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        RobotGroupMessageAt robotGroupMessageAt = new RobotGroupMessageAt();
        robotGroupMessageAt.setAtAll(false);
        robotGroupMessageAt.setAtMobiles(mobiles);
        RobotGroupMessageText robotGroupMessageText = new RobotGroupMessageText();
        robotGroupMessageText.setContent(content);
        RobotGroupMessage robotGroupMessage = new RobotGroupMessage();
        robotGroupMessage.setMsgtype("text");
        robotGroupMessage.setAt(robotGroupMessageAt);
        robotGroupMessage.setText(robotGroupMessageText);
        String textMsg = JSON.toJSONString(robotGroupMessage);
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);
        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String result= EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }
        return "success";
    }

    @RequestMapping(value = "/nothing/ddNoticeMessage/send", method = RequestMethod.POST)
    @ResponseBody
    public String SendEnterpriseNotice(@RequestParam String content,@RequestParam String users) throws ApiException {

        String accessToken = getAccessToken();
        DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
        CorpMessageCorpconversationAsyncsendRequest req = new CorpMessageCorpconversationAsyncsendRequest();
        req.setMsgtype("text");
        req.setAgentId(AgentId);
        req.setUseridList(users);
        /* dept部门 */
     //   req.setDeptIdList("123,456");
        req.setToAllUser(false);
        RobotGroupMessageText text = new RobotGroupMessageText();
        text.setContent(content);
        req.setMsgcontentString(JSON.toJSONString(text));
        CorpMessageCorpconversationAsyncsendResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        return rsp.getBody();
    }
    public static String getAccessToken() {
        String param = "corpid=" + CorpId  +  "&corpsecret=" + CorpSecret;
        String result = HttpUtil.sendGet("https://oapi.dingtalk.com/gettoken", param);
        ResultToken resultToken = JSON.parseObject(result, ResultToken.class);
        return resultToken.getAccess_token();
    }

    public static JSONObject uploadMedia(String url, File file) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpPost.setConfig(requestConfig);

        HttpEntity requestEntity = MultipartEntityBuilder.create().addPart("media",
                new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
        httpPost.setEntity(requestEntity);

        try {
            response = httpClient.execute(httpPost, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");

                JSONObject result = JSON.parseObject(resultStr);
                if (result.getInteger("errcode") == 0) {
                    // 成功
                    result.remove("errcode");
                    result.remove("errmsg");
                    return result;
                } else {
                    System.out.println("request url=" + url + ",return value=");
                    System.out.println(resultStr);
                    int errCode = result.getInteger("errcode");
                    String errMsg = result.getString("errmsg");
                    // throw new OApiException(errCode, errMsg);
                }
            }
        } catch (IOException e) {
        System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
        e.printStackTrace();
        } finally {
        if (response != null) try {
        response.close();
        } catch (IOException e) {
        e.printStackTrace();
        }
        }
        return null;
    }
    @RequestMapping(value = "/nothing/ddNoticeMessage/select", method = RequestMethod.POST)
    @ResponseBody
    public String selectTask(@RequestParam Long taskID) throws ApiException {
        String accessToken = getAccessToken();
        DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
        CorpMessageCorpconversationGetsendprogressRequest req = new CorpMessageCorpconversationGetsendprogressRequest();
        req.setAgentId(AgentId);
        req.setTaskId(taskID);
        CorpMessageCorpconversationGetsendprogressResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        return rsp.getBody();
    }
    @RequestMapping(value = "/nothing/ddNoticeMessage/getSendResult", method = RequestMethod.POST)
    @ResponseBody
    public String getSendResult(@RequestParam Long taskID) throws ApiException {
        String accessToken = getAccessToken();
        DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
        CorpMessageCorpconversationGetsendresultRequest req = new CorpMessageCorpconversationGetsendresultRequest();
        req.setAgentId(AgentId);
        req.setTaskId(taskID);
        CorpMessageCorpconversationGetsendresultResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        return rsp.getBody();
    }



}
