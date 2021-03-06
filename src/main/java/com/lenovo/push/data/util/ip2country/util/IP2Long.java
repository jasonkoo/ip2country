package com.lenovo.push.data.util.ip2country.util;

public class IP2Long {
	public static long ipToLong(String ipAddress) {
		 
		long result = 0;
	 
		String[] ipAddressInArray = ipAddress.split("\\.");
	 
		for (int i = 3; i >= 0; i--) {
	 
			long ip = Long.parseLong(ipAddressInArray[3 - i]);
	 
			//left shifting 24,16,8,0 and bitwise OR
	 
			//1. 192 << 24
			//1. 168 << 16
			//1. 1   << 8
			//1. 2   << 0
			result |= ip << (i * 8);
	 
		}
	 
		return result;
	  }
}
