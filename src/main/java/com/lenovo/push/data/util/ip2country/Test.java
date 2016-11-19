package com.lenovo.push.data.util.ip2country;

import com.lenovo.push.data.util.ip2country.util.IP2Long;

public class Test {
	public static void main(String[] args) {
		String line = "pushmarketing.feedback.rinter2_2c91bc544eb35bd0014ee337f4e70099. 2015073112015-08-03 13:56:53";
		String[] parts = line.split("\001");
		for (int i = 0; i < parts.length; i++) {
			System.out.println(parts[i]);
		}
		System.out.println(parts.length);
	}
}
