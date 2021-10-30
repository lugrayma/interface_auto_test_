package com.it.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.it.service.Weibo;


public class WeiboTest  {
	
		 @BeforeTest
		  public void setUp() throws Exception {        
			  
		  }
		  
		  @Test
		  public void weiboTest() throws Exception {
			  
			  Weibo.weibo(); 
		  }
		  
		  @AfterTest
		  public void tearDown(){
		      
		  }	
}
