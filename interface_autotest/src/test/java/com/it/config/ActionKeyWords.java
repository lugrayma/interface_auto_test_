package com.it.config;




import org.apache.log4j.Logger;

import com.it.util.HeaderUtils;
import com.it.util.HttpHelperAsync;
import com.it.util.HttpHelperAsync.Headers;
import com.it.util.HttpHelperAsync.Response;

import net.sf.json.JSONObject;



public class ActionKeyWords {
	private static Logger logger = Logger.getLogger(ActionKeyWords.class);
	private static long timeoutMillis = 0;

//JSONPOST请求方式
public static Response jsonPost(String requestUrl,String requestParameter,String requestHeader) throws Exception {
	logger.info("开始jsonPost请求...");
	Headers headers = new Headers();
	headers.put("Content-Type", HttpHelperAsync.APPLICATION_JSON);
	headers = HeaderUtils.setHeaders(headers,requestHeader);
	JSONObject parameters = null;
	parameters = JSONObject.fromObject(requestParameter);
	//requestUrl请求地址, headers请求头, parameters请求参数, timeoutMillis请求时间:实现卸载TimeCalcUtil类里面。
	return HttpHelperAsync.postJSON(requestUrl, headers, parameters, timeoutMillis);
}

//get请求
public static Response get(String requestUrl,String requestParameter,String requestHeader) throws Exception {
	logger.info("开始get请求...");
	Headers headers = new Headers();
	headers = HeaderUtils.setHeaders(headers,requestHeader);
	return HttpHelperAsync.get(requestUrl, headers, requestParameter, timeoutMillis);
}
//post请求
public static Response post(String requestUrl,String requestParameter,String requestHeader) throws Exception {
	logger.info("开始post请求...");
	Headers headers = new Headers();
	headers = HeaderUtils.setHeaders(headers,requestHeader);
	return HttpHelperAsync.post(requestUrl, headers, requestParameter, timeoutMillis);
}
}
