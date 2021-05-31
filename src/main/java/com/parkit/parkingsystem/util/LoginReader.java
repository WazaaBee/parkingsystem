package com.parkit.parkingsystem.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginReader {
	
	private static String userName;
	private static String password;
	private static Properties login = new Properties();
	
	public static String readUserName() {
		FileInputStream in = null;
		try {
    		in = new FileInputStream("src/main/resources/login.properties");
    		login.load(in);
        	userName = login.getProperty("username");
    	}catch(IOException e) {
    		e.printStackTrace();
    	}finally {
    		if (in != null) {
    			try {
    				in.close();
    			}catch(IOException e){
    				e.printStackTrace();
    			}
    		}
		}
		return userName;
	}
	
	public static String readPassword() {
		FileInputStream in = null;
		try {
    		in = new FileInputStream("src/main/resources/login.properties");
    		login.load(in);
        	password = login.getProperty("password");
    	}catch(IOException e) {
    		e.printStackTrace();
    	}finally {
    		if (in != null) {
    			try {
    				in.close();
    			}catch(IOException e){
    				e.printStackTrace();
    			}
    		}
		}
		return password;
	}
}
