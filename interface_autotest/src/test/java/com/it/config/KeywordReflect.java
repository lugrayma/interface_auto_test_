package com.it.config;

import java.util.HashMap;
import java.util.Map;


//关键字池，建立关键字与关键方法的映射表：

 

public  class KeywordReflect {

     

    public static Map<String, Map<String, String>> KEYWORD_POOLS 
    = new HashMap<String, Map<String, String>>();

     

    static{

        KEYWORD_POOLS.put("post", KeywordReflect.methodInfo(ActionKeyWords.class.getName(), "post"));
                
        KEYWORD_POOLS.put("get", KeywordReflect.methodInfo(ActionKeyWords.class.getName(), "get"));
        
        KEYWORD_POOLS.put("jsonPost", KeywordReflect.methodInfo(ActionKeyWords.class.getName(), "jsonPost"));
        

    }

     

    public static Map<String, String> methodInfo(String className, String methodName){

        Map<String, String> methodInfo = new HashMap<String, String>();

        methodInfo.put("class", className);

        methodInfo.put("method", methodName);

        return methodInfo;

    }

     

} 
