package com.lenovo.push.data.util.ip2country.util;

public class Long2IP {
	public static String longToIp(long ip) {
		StringBuilder result = new StringBuilder(15);
	 
		for (int i = 0; i < 4; i++) {
	 
			result.insert(0,Long.toString(ip & 0xff));
	 
			if (i < 3) {
				result.insert(0,'.');
			}
	 
			ip = ip >> 8;
		}
		return result.toString();
	  }
	public static void main(String[] args) {
		System.out.println(Long2IP.longToIp(16909056));

	}

}
