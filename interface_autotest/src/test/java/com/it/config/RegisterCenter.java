//建立对象池，创建要执行关键方法的对象：

package com.it.config;

import java.util.HashMap;
import java.util.Map;


/**
 * 阿三大苏打
 * @author Administrator
 *
 */
public  class RegisterCenter {

    public static Map<String, Object> OBJ_POOLS = new HashMap<String, Object>();
    static{
        OBJ_POOLS.put(ActionKeyWords.class.getName(), new ActionKeyWords());
    }
} 