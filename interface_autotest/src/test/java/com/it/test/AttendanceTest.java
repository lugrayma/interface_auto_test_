package com.it.test;

import org.testng.annotations.Test;

import com.it.service.Attendance;

	
/**
 * 测试计划/线程组/测试对象/测试案例
 * @author Lugrayma
 * @version 1.0
 *
 */
public class AttendanceTest {
	 
	  @Test
	  public void attendanceTest() throws Exception{
		  
		  Attendance.attendance(); 
		  
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
