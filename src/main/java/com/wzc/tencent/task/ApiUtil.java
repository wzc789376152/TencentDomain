package com.wzc.tencent.task;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiUtil {
	
	public String connet(String uri,String method){	
        String result = "";
        try {           
            URL url = new URL(uri);
        
            HttpURLConnection  connection = (HttpURLConnection)url.openConnection();// 新建连接实例  
                  
            connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫�???  
              
            connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫�???  
              
            connection.setDoInput(true);// 是否打开输出�??? true|false  
              
            connection.setDoOutput(true);// 是否打开输入流true|false  
              
            connection.setRequestMethod(method);// 提交方法POST|GET  
              
            connection.setUseCaches(false);// 是否缓存true|false  
              
            connection.connect();// 打开连接端口  
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));  
            String line;
            while ((line = in.readLine())!= null){  
                result += line;  
            }
            in.close();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }   
        return result;
	}
	public static String post(String uri){
		return new ApiUtil().connet(uri,"POST");
	}
	public static String get(String uri){
		return new ApiUtil().connet(uri,"GET");
	}
}
