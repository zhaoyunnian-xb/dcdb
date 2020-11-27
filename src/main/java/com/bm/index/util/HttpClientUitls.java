package com.bm.index.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author 马弦
 * @date 2017年10月23日 下午2:49
 * HttpClient工具类
 */
public class HttpClientUitls {
	
	/**
	 * get请求
	 * @return
	 */
	public static String doGet(String url) {
        try {
        	HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
 
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                
                return strResult;
            }
        } 
        catch (IOException e) {
        	e.printStackTrace();
        }
        
        return null;
	}
	
	/**
	 * post请求(用于key-value格式的参数)
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url){
		
		BufferedReader in = null;  
        try {  
            // 定义HttpClient  
            HttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法  
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));
            
            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
				   	//请求成功
            if(code == 200){
            	in = new BufferedReader(new InputStreamReader(response.getEntity()  
                        .getContent(),"utf-8"));
                StringBuffer sb = new StringBuffer("");  
                String line = "";  
                String NL = System.getProperty("line.separator");  
                while ((line = in.readLine()) != null) {  
                    sb.append(line + NL);  
                }
                
                in.close();  
                
                return sb.toString();
            }
            else{	//
            	System.out.println("状态码：" + code);
            	return null;
            }
        }
        catch(Exception e){
        	e.printStackTrace();
        	
        	return null;
        }
	}
	
	/**
	 * Pair请求
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 */
	public static String doPost(String url, List<NameValuePair> nvps) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = HttpClients.createDefault();
			httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			// 防止中文乱码
			httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, "UTF-8");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * post请求（用于请求json格式的参数）
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, String params) throws Exception {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost
    	httpPost.setHeader("Accept", "application/json"); 
    	httpPost.setHeader("Content-Type", "application/json");
    	String charSet = "UTF-8";
    	StringEntity entity = new StringEntity(params, charSet);
    	httpPost.setEntity(entity);        
        CloseableHttpResponse response = null;
        
        try {
        	
        	response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
            	HttpEntity responseEntity = response.getEntity();
            	String jsonString = EntityUtils.toString(responseEntity);
            	return jsonString;
            }
            else{
				 System.out.println("请求返回:"+state+"("+url+")");
			}
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return null;
	}
	
	public static String postFileMultiPart(String url, Map<String,File> fileParam, Map<String,String> textParam) throws ClientProtocolException, IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
            // 创建httpget.    
            HttpPost httppost = new HttpPost(url);
        	
            //setConnectTimeout：设置连接超时时间，单位毫秒。setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(1000*60*60).setConnectionRequestTimeout(1000*60*60).setSocketTimeout(1000*60*60).build();
            httppost.setConfig(defaultRequestConfig);
            
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			multipartEntityBuilder.setCharset(Charset.forName("utf-8"));
			
			if(fileParam!=null){
	            for(Entry<String,File> param : fileParam.entrySet()){
	            	multipartEntityBuilder.addBinaryBody(param.getKey(), param.getValue());
	            }
			}
			if(fileParam!=null){
				ContentType contentType = ContentType.create("text/plain",Charset.forName("UTF-8"));
	            for(Entry<String,String> param : textParam.entrySet()){
	            	multipartEntityBuilder.addTextBody(param.getKey(), param.getValue(), contentType);
	            }
			}
            
            HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);
            
            // 执行post请求.    
            CloseableHttpResponse response = httpclient.execute(httppost);
            
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();
                if (entity != null) { 
                	return EntityUtils.toString(entity,Charset.forName("UTF-8"));
                }
            } finally {  
                response.close();
                
            }
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
        return null;  
    }
	
	public static void main(String[] args) {
		try {
			String url = "http://141.113.32.197:8080/analysisfinal/api/analysisFile";
		/*	File file = new File("e:/bbb.doc");
			Map<String,String> textParam = new HashMap<String,String>();
			Map<String,File> fileParam = new HashMap<String,File>();
			textParam.put("projectName", "文书生成-起诉意见书");
			fileParam.put("file", file);
		   
			String yyresult = HttpClientUitls.postFileMultiPart(url, fileParam, textParam);
			System.out.println(yyresult);*/

		String result = doPost("http://141.112.36.121:8888/menhu/queryDbByName.do?name=luoyan");
		System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
