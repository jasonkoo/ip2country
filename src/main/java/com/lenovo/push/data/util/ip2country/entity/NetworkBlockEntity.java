package com.lenovo.push.data.util.ip2country.entity;

public class NetworkBlockEntity implements Comparable<NetworkBlockEntity> {
	private Long start;
	private Long end;
	private String countryCode;
	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	public Long getEnd() {
		return end;
	}
	public void setEnd(Long end) {
		this.end = end;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public int compareTo(NetworkBlockEntity o) {		
		return this.getStart().compareTo(o.getStart());
	}
}
