package com.it.service;



import com.it.config.Contants;
import com.it.excecutor.Excecutor;


public class Attendance {
	public static void attendance() {
		
		Excecutor.excecutor(Contants.path,Contants.excelSheet);
   }
}
