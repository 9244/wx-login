package com.wx.wxlogin.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @Des  http 工具类
 * @Date 2021/7/1 17:53
 * @Author WXxxx
 */
public class HttpClientUtil {

    // post 请求
    public static String doGet(String url, Map<String,Object> data ){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (MapUtils.isNotEmpty(data)){
                data.entrySet().stream().forEach( item -> {
                    uriBuilder.addParameter(item.getKey(),item.getValue().toString());
                });
            }
            URI uri = uriBuilder.build();
            HttpGet httpGet = new HttpGet(uri);
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }

        }catch (Exception e){

        }finally {
            try {
                httpClient.close();
                if (response != null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }


    /**远程获取要发送的信息*/
    public static String getOneS(){
        try {
            //创建客户端对象
            HttpClient client = HttpClients.createDefault();
            /*创建地址 https://du.shadiao.app/api.php*/
            HttpGet get = new HttpGet("https://chp.shadiao.app/api.php");
            //发起请求，接收响应对象
            HttpResponse response = client.execute(get);
            //获取响应体，响应数据是一种基于HTTP协议标准字符串的对象
            //响应体和响应头，都是封装HTTP协议数据。直接使用可能出现乱码或解析错误
            HttpEntity entity = response.getEntity();
            //通过HTTP实体工具类，转换响应体数据
            String responseString = EntityUtils.toString(entity, "utf-8");

            return responseString;

        } catch (IOException e) {
            throw  new RuntimeException("网站获取句子失败");
        }
    }
}
