package com.lenovo.push.data.util.ip2country.util;

import java.util.List;

import com.lenovo.push.data.util.ip2country.entity.NetworkBlockEntity;

public class RangeBinarySearch {
	public static String search(List<NetworkBlockEntity> list, long target) {
		int lo = 0; 
		int hi = list.size() - 1;
		int mid;
		while (lo <= hi) {
			mid = lo + (hi - lo) / 2;
			NetworkBlockEntity nbe = list.get(mid);
			if(nbe.getStart() <= target && nbe.getEnd() >= target) {
				return list.get(mid).getCountryCode();
			} else if (nbe.getStart() > target) {
				hi = mid - 1;
			} else { // nbe.getEnd < targe
				lo = mid + 1;
			}
		}
		// target was not found
		return null;
		
	}
}
