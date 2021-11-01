package com.it.test;

import org.testng.annotations.Test;

import com.it.service.Bicycling;

	
/**
 * 测试计划/线程组/测试对象/测试案例
 * @author Lugrayma
 * @version 1.0
 *
 */
public class BicyclingTest {
	 
	  @Test
	  public void bicyclingTest() throws Exception{
		  
		  Bicycling.bicycling(); 
		  
	  }
//	  
//	public static void main(String[] args) {
//		attendanceTest();
//	}
//
//	private static void attendanceTest() {
//		// TODO Auto-generated method stub
//		Attendance.attendance();
//	}
	  
}
