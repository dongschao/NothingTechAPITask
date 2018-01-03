package com.nothing.tech.api.scheduleTask.utils;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
//        System.setProperty("http.proxySet","true");
//        System.setProperty("http.proxyHost","localhost");
//        System.setProperty("http.proxyPort",""+173);
//
//        System.setProperty("http.proxyHost","localhost");
//        System.setProperty("http.proxyPort",""+173);





        String longUrl = null;
        HttpPost post = new HttpPost("https://goo.gl/rUqWMG");
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

    public static  String getlongurlMethod(String url){
        System.setProperty("http.proxySet","true");
        System.setProperty("http.proxyHost","localhost");
        System.setProperty("http.proxyPort",""+217);

        System.setProperty("http.proxyHost","localhost");
        System.setProperty("http.proxyPort",""+217);

        String longUrl = null;
        StringBuffer sb = new StringBuffer();
        HttpGet get = new HttpGet("https://goo.gl/6oQxqH");
        try {
            SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
            HttpResponse response = client.execute(get);           //1

            HttpEntity entity = response.getEntity();
            InputStreamReader reader = new InputStreamReader(entity.getContent(),"utf-8");
            char [] charbufer;
            while (0<reader.read(charbufer=new char[10])){
                sb.append(charbufer);
            }
        }catch (IOException e){//1
            e.printStackTrace();
        }finally {
            get.releaseConnection();
        }

        return sb.toString();


    }
    public static String post(String url/*, Map<String,String> data*/){
        StringBuffer sb = new StringBuffer();
        HttpPost httpPost = new HttpPost("https://goo.gl/1WyK9H");
        List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
//        if(null != data) {
//            for (String key : data.keySet()) {
//                valuePairs.add(new BasicNameValuePair(key, data.get(key)));
//            }
//        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(valuePairs));
            HttpResponse response = client.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            BufferedInputStream bis = new BufferedInputStream(httpEntity.getContent());
            byte [] buffer;
            while (0<bis.read(buffer=new byte[128])){
                sb.append(new String(buffer,"utf-8"));
            }
        }catch (UnsupportedEncodingException e){//数据格式有误
            e.printStackTrace();
        }catch (IOException e){//请求出错
            e.printStackTrace();
        }finally {
            httpPost.releaseConnection();
        }
        return sb.toString();
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






    public static String get(String url){
        HttpURLConnection http = null;
        InputStream is = null;
        try {
            URL urlGet = new URL(url);
            http = (HttpURLConnection) urlGet.openConnection();

            http.setRequestMethod("GET");
            http.setInstanceFollowRedirects(false);
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setReadTimeout(10);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
            String url302 = http.getHeaderField("Location");
            if (url302==null){
                url302 = http.getHeaderField("location");
            }
            if (url302==null){
                url302 = url;
            }
            return url302;
        } catch (Exception e) {
           System.out.println(e.getMessage());


            return null;
        }finally {
            if(null != http) http.disconnect();
            try {
                if (null != is) is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }













}
