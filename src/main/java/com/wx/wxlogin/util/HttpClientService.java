package com.wx.wxlogin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service
public class HttpClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

    @Autowired(required=false)
    private CloseableHttpClient httpClient;

    @Autowired(required=false)
    private RequestConfig requestConfig;
    
    /**
     * 执行get请求
     * 
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url,Map<String, String> params,String encode) throws Exception {
        LOGGER.info("执行GET请求，URL = {}", url);
        if(null != params){
            URIBuilder builder = new URIBuilder(url);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());
            }
            url = builder.build().toString();
        }
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                if(encode == null){
                    encode = "UTF-8";
                }
                return EntityUtils.toString(response.getEntity(), encode);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            // 此处不能关闭httpClient，如果关闭httpClient，连接池也会销毁
        }
        return null;
    }
    
    public String doGet(String url, String encode) throws Exception{
        return this.doGet(url, null, encode);
    }
    
    public String doGet(String url) throws Exception{
        return this.doGet(url, null, null);
    }

    /**
     * 带参数的get请求
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, String> params) throws Exception {
        return this.doGet(url, params, null);
    }

    /**
     * 执行POST请求
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String doPost(String url, Map<String, String> params,String encode) throws Exception {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        if (null != params) {
            // 设置2个post参数，一个是scope、一个是q
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = null;
            if(encode!=null){
                formEntity = new UrlEncodedFormEntity(parameters,encode);
            }else{
                formEntity = new UrlEncodedFormEntity(parameters);
            }
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }


    /**
     * 执行POST请求
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String doPost(String url, Map<String, String> params) throws Exception {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        if (null != params) {
            // 设置2个post参数，一个是scope、一个是q
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

	public String getHttpClientStr(String http) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response=null;
		String httpClientStr="";
	    try {
	        HttpPost httppost = new HttpPost(http);
	        response = httpclient.execute(httppost);
           // System.out.println(DateUtil.getDateTime()+"--"+response.getStatusLine());
            httpClientStr = EntityUtils.toString(response.getEntity());
            EntityUtils.consume(response.getEntity());
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    finally {
	    	response.close();
	     //   httpclient.close();
	    }
	    return httpClientStr;
	}
    
	
    public String doPostJson2(String url, String json) throws Exception {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("authorization", "crf_offLine_loanCore");
        httpPost.setConfig(requestConfig);
        if(null != json){
            //设置请求体为 字符串
            StringEntity stringEntity = new StringEntity(json,"UTF-8");
            httpPost.setEntity(stringEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            System.out.println(response);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
            	System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
            	return  "200";
            }else{
            	 LOGGER.debug("调用外部数据接口返回:"+url+json+response.getStatusLine().getStatusCode());
            	return String.valueOf(response.getStatusLine().getStatusCode());
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
	
    public String doPostJson(String url, String json) throws Exception {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("authorization", "crf_offLine_loanCore");
        httpPost.setConfig(requestConfig);
        
        if(null != json){
            //设置请求体为 字符串
            StringEntity stringEntity = new StringEntity(json,"UTF-8");
            httpPost.setEntity(stringEntity);
        }
        long start = System.currentTimeMillis();
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
            long end = System.currentTimeMillis();
            LOGGER.debug("调用接口:"+url+",耗时:"+(end-start)+"毫秒,"+"\t请求参数："+json+"\t返回："+response.getStatusLine().getStatusCode());
        }
        return null;
    }
    
    public String doPostJson(String url, JSONObject json, List<File> files) throws Exception {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        
//        if(null != json){
//            //设置请求体为 字符串
//            StringEntity stringEntity = new StringEntity(json.toString(),"UTF-8");
//            httpPost.setEntity(stringEntity);
//        }
        if(files != null && files.size()>0){
//        	MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//        	for(File file :files){
//        		multipartEntityBuilder.addBinaryBody("files", file);
//        	}
        	MultipartEntityBuilder create = MultipartEntityBuilder.create();
        	for(File file : files){
        		FileBody bin = new FileBody(file);
        		create.addPart("files", bin);
        	}
        	if(null != json){
        		StringBody comment = new StringBody(json.toString(), ContentType.APPLICATION_JSON);
        		create.addPart("comment", comment);
//        		StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
//        		stringEntity.setContentType("application/json");
        		
        	}
        	HttpEntity reqEntity = create.build();
        	httpPost.setEntity(reqEntity);
        	
        	
        }else if(null != json){
            //设置请求体为 字符串
            StringEntity stringEntity = new StringEntity(json.toString(),"UTF-8");
            httpPost.setEntity(stringEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
    
    /**
     * 与信而富现金贷统一用户平台HttpPut调用的接口
     * @param url
     * @param json
     * @param params
     * @return
     * @throws Exception
     */
    public JSONObject doPutXRFCash(String url,String json,Map<String, String> params)throws Exception{
    	JSONObject resultJson = new JSONObject();
    	if(params != null&& !params.isEmpty()){
    		int i = 0;
    		for (Map.Entry<String, String> entry : params.entrySet()) {
                if(i == 0){
                	url = url + "?"+entry.getKey()+"="+entry.getValue();
                }else {
					url = url + "&"+entry.getKey()+"="+entry.getValue();
				}
                i++;
            }
    	}
    	HttpPut httpPut = new HttpPut(url.replaceAll(" ", "%20"));
    	httpPut.setHeader("Content-Type", "application/json");
    	httpPut.setConfig(requestConfig);
         if(null != json){
             //设置请求体为 字符串
             StringEntity stringEntity = new StringEntity(json,"UTF-8");
             httpPut.setEntity(stringEntity);
         }

         CloseableHttpResponse response = null;
         long start = 0;
         try {
             // 执行请求
             response = httpClient.execute(httpPut);
             
             resultJson.put("statusCode", response.getStatusLine().getStatusCode());
             if(response.getStatusLine().getStatusCode() == 400){
            	 resultJson.put("data", JSONObject.parse(EntityUtils.toString(response.getEntity(), "UTF-8")));
             }else{
            	 resultJson.put("data", EntityUtils.toString(response.getEntity(), "UTF-8"));
             }
            	 
             
         } finally {
             if (response != null) {
                 response.close();
             }
             long end = System.currentTimeMillis();
             LOGGER.debug("调用接口:"+url+",耗时:"+(end-start)+"毫秒,"+"\t请求参数："+json+"\t返回："+resultJson.toString());
         }
         return resultJson;
    }
    
    /**
     * 与信而富现金贷统一用户平台HttpPost调用的接口
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public JSONObject doPostXRFCash(String url,String json,Map<String, String> params)throws Exception{
    	JSONObject resultJson = new JSONObject();
    	if(params != null&& !params.isEmpty()){
    		int i = 0;
    		for (Map.Entry<String, String> entry : params.entrySet()) {
                if(i == 0){
                	url = url + "?"+entry.getKey()+"="+entry.getValue();
                }else {
					url = url + "&"+entry.getKey()+"="+entry.getValue();
				}
                i++;
            }
    	}
    	HttpPost httpPost = new HttpPost(url.replaceAll(" ", "%20"));
    	httpPost.setHeader("Content-Type", "application/json");
    	httpPost.setConfig(requestConfig);
         if(null != json){
             //设置请求体为 字符串
             StringEntity stringEntity = new StringEntity(json,"UTF-8");
             httpPost.setEntity(stringEntity);
         }
         long start = 0;
         CloseableHttpResponse response = null;
         try {
             // 执行请求
        	 start = System.currentTimeMillis();
             response = httpClient.execute(httpPost);
             
             resultJson.put("statusCode", response.getStatusLine().getStatusCode());
             if(response.getStatusLine().getStatusCode() == 400){
            	 resultJson.put("data", JSONObject.parse(EntityUtils.toString(response.getEntity(), "UTF-8")));
             }else{
            	 resultJson.put("data", EntityUtils.toString(response.getEntity(), "UTF-8"));
             }
             
         } finally {
             if (response != null) {
                 response.close();
             }
             long end = System.currentTimeMillis();
             LOGGER.debug("调用接口:"+url+",耗时:"+(end-start)+"毫秒,"+"\t请求参数："+json+"\t返回："+resultJson.toString());
         }
         return resultJson;
    }
    
    /**
     * 与信而富现金贷统一用户平台HttpPost调用的接口
     * @param url
     * @param params 
     * @return
     * @throws Exception
     */
    public JSONObject doPostXRFCash(String url, Map<String, String> params) throws Exception {
    	JSONObject resultJson = new JSONObject();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        if (null != params) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            resultJson.put("statusCode", response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode() == 400){
           	 resultJson.put("data", JSONObject.parse(EntityUtils.toString(response.getEntity(), "UTF-8")));
            }else{
           	 resultJson.put("data", EntityUtils.toString(response.getEntity(), "UTF-8"));
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return resultJson;
    }
    
    /**
	 * 获取json-post传来的参数
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	 public JSONObject httpProcess(HttpServletRequest request) throws IOException, JSONException {
	        StringBuffer sb = new StringBuffer() ;   
	        InputStream is = request.getInputStream();   
	        BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));   
	        String s = "" ;   
	        while((s=br.readLine())!=null){   
	            sb.append(s) ;   
	        }   
	        if(sb.toString().length()<=0){  
	            return null;  
	        }else {  
	            return JSONObject.parseObject(sb.toString());
	        }  
	 } 
	 
	 
	 public String postWay(List<NameValuePair> nvps,String url){
		 //创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//实例化HttpClient  
		CloseableHttpClient httpclient = httpClientBuilder.build();  
  	String resultContent = "";
  	try {
  		// 自行根据具体业务设置
  		// 如果接受应答超时, 可能需要调用辅助查询接口
  		RequestConfig reqeustConfig = RequestConfig.custom()
  				.setConnectionRequestTimeout(25000)                        //请求超时
  				.setSocketTimeout(30000).build();                          //响应超时
  		
  		//以post方式请求网页 
      	HttpPost httppost = new HttpPost(url);
      	
      	//将POST参数以UTF-8编码并包装成表单实体对象  
      	UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
      	httppost.setEntity(formEntity);
      	
      	//执行请求并获取结果  
      	httppost.setConfig(reqeustConfig);
          HttpResponse response = httpclient.execute(httppost);
          
          //从response中取出HttpEntity对象
          HttpEntity httpEntity = response.getEntity();
          System.out.println(response.getStatusLine().getStatusCode());
          resultContent = EntityUtils.toString(httpEntity, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {  
	        // 当不再需要HttpClient实例时,关闭连接管理器以确保释放所有占用的系统资源  
	        try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }  
  	
  	return resultContent;
  }
	 
	 /**
	  * 富有开户
	  * @param json
	  * @param url
	  * @return
	  */
	 public String postMethod(String json,String url) {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			
			post.setHeader("Content-Type", "application/json");
			post.setHeader("Authorization", "Basic YWRtaW46");
			String result = "";
			
			try {
				StringEntity s = new StringEntity(json, "utf-8");
				s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				post.setEntity(s);
				
				//发送请求
				HttpResponse httpResponse = client.execute(post);
				
				//获取响应输入流
				InputStream inStream = httpResponse.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
				StringBuilder strber = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null)
						strber.append(line + "\n");
				inStream.close();
				
				result = strber.toString();
				//System.out.println(result);
				
				if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					System.out.println("请求服务器成功，做相应处理");
				} else {
					System.out.println("请求服务端失败"+httpResponse.getStatusLine().getStatusCode());
				}
				
			} catch (Exception e) {
				System.out.println("请求异常");
				throw new RuntimeException(e);
			}
			return result;
		}
	 
		/**
		 * @author 胡晓欢
		 * @2017年12月25日下午4:22:04
		 * @功能：照片上传到新影像系统
		 */
		public static String formUpload(String url, JSONObject jsonObject,
				String sourcePath) {
			String resultStr = "";
			// 获取httpClient实例
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpClientContext context = HttpClientContext.create();
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(10000).setConnectTimeout(10000).build();// 设置请求和传输超时时间
			// 创建httppost
			HttpPost httppost = new HttpPost(url);
			httppost.setConfig(requestConfig);
			ContentType strContent = ContentType.create("text/plain",
					Charset.forName("UTF-8"));
			// strContent=ContentType.create("multipart/form-data",Charset.forName("UTF-8"));
			try {

				MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
						.create();
				multipartEntityBuilder.setMode(HttpMultipartMode.RFC6532);
				System.out.println("formUpload参数:" + jsonObject);
				if (jsonObject != null && !jsonObject.isEmpty()) {
					Set<String> keys = jsonObject.keySet();
					for (String key : keys) {
						multipartEntityBuilder.addPart(key, new StringBody(
								new String(jsonObject.getString(key).getBytes(),
										"utf-8"), strContent));

						// multipartEntityBuilder.addPart(key, new StringBody(
						// jsonObject.getString(key),strContent));
					}
				}
				System.out.println("formUpload文件:" + sourcePath);
				// 把文件转换成流对象FileBody
				FileBody fileBody = new FileBody(new File(sourcePath));
				multipartEntityBuilder.addPart("file", fileBody);

				httppost.setEntity(multipartEntityBuilder.build());
				System.out.println("开始请求" + httppost.getURI());
				CloseableHttpResponse response = httpclient.execute(httppost,
						context);
				try {
					HttpEntity entity = response.getEntity();
					System.out.println("请求返回状态" + response.getStatusLine().toString());
					if (entity != null) {
						resultStr = EntityUtils.toString(entity, "UTF-8");
						System.out.println("请求返回内容: " + resultStr);
						return resultStr;
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					response.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return resultStr;
		}
	
	/**
	 * 
	 * @param url
	 * @param json
	 * @param path
	 * @param file_type
	 * @param data
	 * @return
	 */
	public String sendFile(String url,String json,String path,String file_type,String data) {
		String result = "fail";
		try {
			BasicHttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
			HttpConnectionParams.setSoTimeout(params, 60 * 1000);
			HttpClient client = new DefaultHttpClient(params);
			HttpPost post = new HttpPost(url);
			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null,
			Charset.forName("GBK"));
			entity.addPart(file_type, new FileBody(new File(path)));
			entity.addPart(data, new StringBody(json, Charset.forName("GBK")));
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity responseEntity = response.getEntity();
				result = EntityUtils.toString(responseEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
}
