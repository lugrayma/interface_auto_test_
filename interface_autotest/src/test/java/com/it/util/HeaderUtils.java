package com.it.util;


import java.util.Iterator;
import org.apache.log4j.Logger;
import com.it.util.HttpHelperAsync.Headers;
import net.sf.json.JSONObject;

public class HeaderUtils {
	
	private static Logger logger = Logger.getLogger(HeaderUtils.class);
	public static Headers setHeaders(Headers headers,String requestHeader) {
		
		if(requestHeader == "" || null == requestHeader ) {
			logger.info("头信息参数为空，无需设置头信息");
	 		
		}else {
			JSONObject obj = JSONObject.fromObject(requestHeader);
		    Iterator<String> sIterator = obj.keys();
		        while(sIterator.hasNext()) {
		    	    String key = sIterator.next();
			        String value = obj.getString(key); 
			        headers.put(key, value);
		        }
	    }
    return headers;
    }
}
