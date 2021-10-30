package com.it.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.it.service.Im;


public class ImTest {

	 @BeforeTest
	  public void setUp() throws Exception {        
		  
	  }
	  
	  @Test
	  public void imTest() throws Exception {
		  Im.im(); 
	  }
	  
	  @AfterTest
	  public void tearDown(){
	      
	  }


}
