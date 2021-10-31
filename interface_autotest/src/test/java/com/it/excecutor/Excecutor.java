package com.it.excecutor;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.it.config.Contants;
import com.it.config.KeywordReflect;
import com.it.config.RegisterCenter;
import com.it.util.ExcelUtils;
import com.it.util.HttpHelperAsync.Response;

import net.sf.json.JSONObject;



//执行引擎
public class Excecutor {
	private static Logger logger = Logger.getLogger(Excecutor.class);
	public static String module = null;
	public static String caseNumber = null;
	public static String caseDescribe = null;
	public static String requestUrl = null;
	public static String keyWords = null;
	public static String requestParameter = null;
	public static String requestHeader = null;
	public static String verifyPoints  = null;
	public static String associationParameter = null;
	public static String isAtuo = null;
	public static Method[] methods;
	public static Map<String,String> tempParameters = new HashMap<String,String>();
	public static short valueIndex = 11;
	
	public static void excecutor(String filePath,String sheetName) {
		
		
        //method = actionKeyWords.getClass().getMethods();
	    ExcelUtils excel = ExcelUtils.getInstance();
	    excel.setFilePath(Excecutor.class.getResource(filePath).getPath());
	    excel.setSheetName(sheetName);
	    
	    
	    //获取sheet中行的1，2，3，4，5，6，7，8，9列的数据，如果希望扩展其他字段，可在此处添加
	    //XSSFSheet sheet = excel.createExcelSheet(sheetName);	 
	    
	    for(int rowNum=1;rowNum<=excel.getSheetMaxRow();rowNum++){
		       
	    		module = excel.readExcelCell(rowNum, 0);
		        caseNumber = excel.readExcelCell(rowNum, 1);
		        caseDescribe = excel.readExcelCell(rowNum, 2);
		        requestUrl = excel.readExcelCell(rowNum, 3);
		        keyWords = excel.readExcelCell(rowNum, 4);	            
				requestParameter = excel.readExcelCell(rowNum, 5);
				requestHeader = excel.readExcelCell(rowNum, 6);
				verifyPoints = excel.readExcelCell(rowNum, 7);				
				associationParameter = excel.readExcelCell(rowNum, 8);
				isAtuo = excel.readExcelCell(rowNum, 9);							
				logger.info("开始执行"+module+" "+caseNumber+" 自动化测试用例");
				Excecutor.login_action();	    	    
	    }
    }
	
	private static Method getMethod(String methodName, Object obj) {

	        try {

	            methods = obj.getClass().getMethods();

	            for (Method m : methods) {

	                if (m.getName().equals(methodName)) {
	                	//System.out.println(m+"----");
	                    return m;   
	                }else{
	                	//System.out.println("没找到方法");
	                }

	            }

	        } catch (SecurityException e) {

	            return null;

	        }

	        return null;

	    }
	 
	public static void login_action() {
		
		
		Map<String, String> keyMethod = KeywordReflect.KEYWORD_POOLS.get(keyWords);
		if(null == keyMethod) {
			logger.error("关键字方法 在映射表中找不到，请确认是否输入正确!");
		}else {
			String className= keyMethod.get("class");			
		    String methodName = keyMethod.get("method");
		    Object obj = RegisterCenter.OBJ_POOLS.get(className);
		    Method method = Excecutor.getMethod(methodName, obj);
		    try { 
        		    //System.out.println("------------");
        		    //System.out.println(method.getName());
                    Response requestResult =(Response) method.invoke(obj,changeParams(requestUrl),changeParams(requestParameter),changeParams(requestHeader));
                    logger.info("发送请求完成！结果为："+requestResult.getContent());                                   
                    
                    
                    if(null != associationParameter && !associationParameter.isEmpty()) {
                    	logger.info("关联参数有值，现在开始提取。。。");
                    	String[] str = associationParameter.split(";");
                    	//System.out.println(str[0]);
                        for(int i = 0;i < str.length;i++) {
                        	String[] str1 = str[i].split(",");
                        	String value = Excecutor.getJsonValue(requestResult.getContent(), Integer.parseInt(str1[0]), str1[1]);
                        	tempParameters.put(str1[1], value);
                        	logger.info("提取参数["+str1[1]+"]成功！值为："+value);
                        }
                      
                    	
                    }
                    Excecutor.verify_action(requestResult.getContent());
                    
                } catch (IllegalAccessException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace(); 
 
                } catch (IllegalArgumentException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace(); 

                } catch (InvocationTargetException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                } catch (NullPointerException e) {
                	
                	e.printStackTrace();
                	logger.info("请求结果中不包含检查点["+verifyPoints+"]，"+caseNumber+"用例验证不通过");
                }

          }

      
    }
    
	public static String getJsonValue(String result,int index,String key)

    {

           int indexloc,indexkey;

           String newstr;

           indexloc=result.indexOf("[");

           indexkey=result.indexOf(key);
//           System.out.println(indexloc+"---"+indexkey);
           //判断Data域的内容
           
           if (index==0)

           {

                   JSONObject jsonObj = JSONObject.fromObject(result);
//                   System.out.println(jsonObj.getString(key));
                   return jsonObj.getString(key);

           }

           else

           {
                  newstr=getNPro(result,index);                  
//                  System.out.println(newstr);
                  return getJsonValue(newstr,0,key);

           }

          

    }

    public static String  getNPro(String str,int n)

    {

        Matcher slashMatcher = Pattern.compile("\\{").matcher(str);

        int mIdx = 0;

        while(slashMatcher.find()) {

         if(mIdx ==n){

               break;

           }

        mIdx++;

        }

        str=str.substring(slashMatcher.start(),str.length());
        //System.out.println(slashMatcher.start()+"---"+str.length());
        //System.out.println(str.substring(0, str.indexOf("}")+1));
       // System.out.println(str);
        //return str.substring(0, str.indexOf("}")+1);
        return str;
     }

    public static String changeParams(String params) {
    	Properties pro = new Properties();
		String popath = Contants.ObjectReUrl;
		//System.out.println(Excecutor.class.getResource(popath).getPath());
		try{
			if(null !=params && !params.isEmpty()) {
				InputStream in = new BufferedInputStream(new FileInputStream(Excecutor.class.getResource(popath).getPath()));
			    pro.load(in);
			    String regEx = "\\$\\{([^\\}]+)?\\}";	
	    	    Pattern pattern = Pattern.compile(regEx);   	
	    	    Matcher matcher = pattern.matcher(params);
	    	    //System.out.println(matcher.group(1));
	    	    while(matcher.find()){
	    	    	if(null != pro.getProperty(matcher.group(1))) {
	    	    		params = params.replaceAll("\\$\\{" + matcher.group(1) + "\\}", pro.getProperty(matcher.group(1)));
	    		    }else if (null != tempParameters.get(matcher.group(1))) {
	    			    params = params.replaceAll("\\$\\{" + matcher.group(1) + "\\}", tempParameters.get(matcher.group(1)));
				    }else {
					    logger.info( "${" + matcher.group(1) + "}"+"没有匹配到，请确认输入是否正确或者在全局变量文件中设置或者临时变量引用！");
	    		    }
	    	   }
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		//System.out.println(params);
    	return params;
    }

    public static void  verify_action(String requestResult) {
    	if(requestResult.contains(verifyPoints)) {
    		logger.info("请求结果中包含检查点["+verifyPoints+"]，"+caseNumber+"用例验证通过");
    	}else {
    		logger.info("请求结果中不包含检查点["+verifyPoints+"]，"+caseNumber+"用例验证不通过");
    	}
    }

}
