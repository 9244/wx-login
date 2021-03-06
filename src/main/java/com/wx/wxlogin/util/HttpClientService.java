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
     * ??????get??????
     * 
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url,Map<String, String> params,String encode) throws Exception {
        LOGGER.info("??????GET?????????URL = {}", url);
        if(null != params){
            URIBuilder builder = new URIBuilder(url);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());
            }
            url = builder.build().toString();
        }
        // ??????http GET??????
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            // ????????????
            response = httpClient.execute(httpGet);
            // ???????????????????????????200
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
            // ??????????????????httpClient???????????????httpClient????????????????????????
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
     * ????????????get??????
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
     * ??????POST??????
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String doPost(String url, Map<String, String> params,String encode) throws Exception {
        // ??????http POST??????
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        if (null != params) {
            // ??????2???post??????????????????scope????????????q
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // ????????????form??????????????????
            UrlEncodedFormEntity formEntity = null;
            if(encode!=null){
                formEntity = new UrlEncodedFormEntity(parameters,encode);
            }else{
                formEntity = new UrlEncodedFormEntity(parameters);
            }
            // ????????????????????????httpPost?????????
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // ????????????
            response = httpClient.execute(httpPost);
            // ???????????????????????????200
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
     * ??????POST??????
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String doPost(String url, Map<String, String> params) throws Exception {
        // ??????http POST??????
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        if (null != params) {
            // ??????2???post??????????????????scope????????????q
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // ????????????form??????????????????
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            // ????????????????????????httpPost?????????
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // ????????????
            response = httpClient.execute(httpPost);
            // ???????????????????????????200
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
        // ??????http POST??????
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("authorization", "crf_offLine_loanCore");
        httpPost.setConfig(requestConfig);
        if(null != json){
            //?????????????????? ?????????
            StringEntity stringEntity = new StringEntity(json,"UTF-8");
            httpPost.setEntity(stringEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // ????????????
            response = httpClient.execute(httpPost);
            System.out.println(response);
            // ???????????????????????????200
            if (response.getStatusLine().getStatusCode() == 200) {
            	System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
            	return  "200";
            }else{
            	 LOGGER.debug("??????????????????????????????:"+url+json+response.getStatusLine().getStatusCode());
            	return String.valueOf(response.getStatusLine().getStatusCode());
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
	
    public String doPostJson(String url, String json) throws Exception {
        // ??????http POST??????
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("authorization", "crf_offLine_loanCore");
        httpPost.setConfig(requestConfig);
        
        if(null != json){
            //?????????????????? ?????????
            StringEntity stringEntity = new StringEntity(json,"UTF-8");
            httpPost.setEntity(stringEntity);
        }
        long start = System.currentTimeMillis();
        CloseableHttpResponse response = null;
        try {
            // ????????????
            response = httpClient.execute(httpPost);
            // ???????????????????????????200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
            long end = System.currentTimeMillis();
            LOGGER.debug("????????????:"+url+",??????:"+(end-start)+"??????,"+"\t???????????????"+json+"\t?????????"+response.getStatusLine().getStatusCode());
        }
        return null;
    }
    
    public String doPostJson(String url, JSONObject json, List<File> files) throws Exception {
        // ??????http POST??????
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        
//        if(null != json){
//            //?????????????????? ?????????
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
            //?????????????????? ?????????
            StringEntity stringEntity = new StringEntity(json.toString(),"UTF-8");
            httpPost.setEntity(stringEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // ????????????
            response = httpClient.execute(httpPost);
            // ???????????????????????????200
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
     * ???????????????????????????????????????HttpPut???????????????
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
             //?????????????????? ?????????
             StringEntity stringEntity = new StringEntity(json,"UTF-8");
             httpPut.setEntity(stringEntity);
         }

         CloseableHttpResponse response = null;
         long start = 0;
         try {
             // ????????????
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
             LOGGER.debug("????????????:"+url+",??????:"+(end-start)+"??????,"+"\t???????????????"+json+"\t?????????"+resultJson.toString());
         }
         return resultJson;
    }
    
    /**
     * ???????????????????????????????????????HttpPost???????????????
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
             //?????????????????? ?????????
             StringEntity stringEntity = new StringEntity(json,"UTF-8");
             httpPost.setEntity(stringEntity);
         }
         long start = 0;
         CloseableHttpResponse response = null;
         try {
             // ????????????
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
             LOGGER.debug("????????????:"+url+",??????:"+(end-start)+"??????,"+"\t???????????????"+json+"\t?????????"+resultJson.toString());
         }
         return resultJson;
    }
    
    /**
     * ???????????????????????????????????????HttpPost???????????????
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

            // ????????????form??????????????????
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            // ????????????????????????httpPost?????????
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // ????????????
            response = httpClient.execute(httpPost);
            // ???????????????????????????200
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
	 * ??????json-post???????????????
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
		 //??????HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//?????????HttpClient  
		CloseableHttpClient httpclient = httpClientBuilder.build();  
  	String resultContent = "";
  	try {
  		// ??????????????????????????????
  		// ????????????????????????, ????????????????????????????????????
  		RequestConfig reqeustConfig = RequestConfig.custom()
  				.setConnectionRequestTimeout(25000)                        //????????????
  				.setSocketTimeout(30000).build();                          //????????????
  		
  		//???post?????????????????? 
      	HttpPost httppost = new HttpPost(url);
      	
      	//???POST?????????UTF-8????????????????????????????????????  
      	UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
      	httppost.setEntity(formEntity);
      	
      	//???????????????????????????  
      	httppost.setConfig(reqeustConfig);
          HttpResponse response = httpclient.execute(httppost);
          
          //???response?????????HttpEntity??????
          HttpEntity httpEntity = response.getEntity();
          System.out.println(response.getStatusLine().getStatusCode());
          resultContent = EntityUtils.toString(httpEntity, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {  
	        // ???????????????HttpClient?????????,???????????????????????????????????????????????????????????????  
	        try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }  
  	
  	return resultContent;
  }
	 
	 /**
	  * ????????????
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
				
				//????????????
				HttpResponse httpResponse = client.execute(post);
				
				//?????????????????????
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
					System.out.println("???????????????????????????????????????");
				} else {
					System.out.println("?????????????????????"+httpResponse.getStatusLine().getStatusCode());
				}
				
			} catch (Exception e) {
				System.out.println("????????????");
				throw new RuntimeException(e);
			}
			return result;
		}
	 
		/**
		 * @author ?????????
		 * @2017???12???25?????????4:22:04
		 * @???????????????????????????????????????
		 */
		public static String formUpload(String url, JSONObject jsonObject,
				String sourcePath) {
			String resultStr = "";
			// ??????httpClient??????
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpClientContext context = HttpClientContext.create();
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(10000).setConnectTimeout(10000).build();// ?????????????????????????????????
			// ??????httppost
			HttpPost httppost = new HttpPost(url);
			httppost.setConfig(requestConfig);
			ContentType strContent = ContentType.create("text/plain",
					Charset.forName("UTF-8"));
			// strContent=ContentType.create("multipart/form-data",Charset.forName("UTF-8"));
			try {

				MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
						.create();
				multipartEntityBuilder.setMode(HttpMultipartMode.RFC6532);
				System.out.println("formUpload??????:" + jsonObject);
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
				System.out.println("formUpload??????:" + sourcePath);
				// ???????????????????????????FileBody
				FileBody fileBody = new FileBody(new File(sourcePath));
				multipartEntityBuilder.addPart("file", fileBody);

				httppost.setEntity(multipartEntityBuilder.build());
				System.out.println("????????????" + httppost.getURI());
				CloseableHttpResponse response = httpclient.execute(httppost,
						context);
				try {
					HttpEntity entity = response.getEntity();
					System.out.println("??????????????????" + response.getStatusLine().toString());
					if (entity != null) {
						resultStr = EntityUtils.toString(entity, "UTF-8");
						System.out.println("??????????????????: " + resultStr);
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
