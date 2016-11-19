package com.lenovo.push.data.util.ip2country;

import java.util.List;

import com.lenovo.push.data.util.ip2country.entity.NetworkBlockEntity;
import com.lenovo.push.data.util.ip2country.merged.MergedDB;
import com.lenovo.push.data.util.ip2country.util.IP2Long;
import com.lenovo.push.data.util.ip2country.util.RangeBinarySearch;

public class IP2CountryTool {
	private List<NetworkBlockEntity> mergedDB;
	public IP2CountryTool() {
		if (mergedDB == null) {
			mergedDB = new MergedDB("/ip2country.csv").loadDB();
		}
	}
	
	public String ip2Country(String ip) {
		long target = IP2Long.ipToLong(ip);
		return RangeBinarySearch.search(mergedDB, target);
	}
	
	public static void main(String[] args) {
		IP2CountryTool tool = new IP2CountryTool();
		System.out.println(tool.ip2Country("111.202.176.87"));
	}
}
