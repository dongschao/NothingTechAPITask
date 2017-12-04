package com.nothing.tech.api.scheduleTask.utils;


import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Http 请求访问工具类
 *
 * @author will_awoke
 * @version 2014-6-26
 * @see HttpAccessUtil
 * @since
 */

public class HttpAccessUtil {

    private static CloseableHttpClient client = HttpClientBuilder.create().build();

    static {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        //客户端和服务器建立连接的timeout
        requestConfigBuilder.setConnectTimeout(30000);
        //从连接池获取连接的timeout
        requestConfigBuilder.setConnectionRequestTimeout(30000);
        //连接建立后，request没有回应的timeout
        requestConfigBuilder.setSocketTimeout(30000);

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());
        clientBuilder.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(30000).build()); //连接建立后，request没有回应的timeout
        clientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        client = clientBuilder.build();
    }


    /**
     * 获取短连接
     * @param url 接口地址
     * @param count 请求的个数
     * @return
     * @throws Exception
     */
    public static String getShortUrlMethod(String url, int count) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/x-www-form-urlencoded");

//        StringEntity params = new StringEntity(json.toString(), "UTF-8");
//        post.setEntity(params);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("count", count + ""));
        try {
            post.setEntity(new UrlEncodedFormEntity(params));

        } catch (Exception e) {
            e.printStackTrace();
        }
        CloseableHttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println("result:" + result);
        return result.toString();
    }

    /**
     * 根据短连接获取长连接
     * @param url 短连接地址
     * @return 长连接字符串
     * @throws Exception
     */
    public static String getLongUrlMethod(String url) throws Exception {
        String longUrl = null;
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/x-www-form-urlencoded");

        CloseableHttpResponse response = client.execute(post);
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            if (header.getName().equals("Location")) {
//                System.out.println("longUrl........"+header.getElements()[0].getName().toString());
                longUrl = header.getElements()[0].getName().toString();
                break;
            }
        }
        return longUrl.toString();
    }

    /**
     * 更新短url为长url
     * @param url 更新接口
     * @param postId
     * @param realWebUrl 长连接地址
     * @return
     * @throws Exception
     */
    public static String updateUrlMethod(String url, long postId, String realWebUrl) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/x-www-form-urlencoded");

//        StringEntity params = new StringEntity(json.toString(), "UTF-8");
//        post.setEntity(params);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("postId", postId + ""));
        params.add(new BasicNameValuePair("realWebUrl", realWebUrl));
        try {
            post.setEntity(new UrlEncodedFormEntity(params));

        } catch (Exception e) {
            e.printStackTrace();
        }
        CloseableHttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println("result:" + result);
        return result.toString();
    }

}
