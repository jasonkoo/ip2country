package com.lenovo.push.data.util.ip2country.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import com.lenovo.push.data.util.ip2country.dbip.DBIPDB;
import com.lenovo.push.data.util.ip2country.entity.NetworkBlockEntity;
import com.lenovo.push.data.util.ip2country.geolite2.Geolite2DB;
import com.lenovo.push.data.util.ip2country.software77.Software77DB;

public class DatabaseMerger {
	private List<NetworkBlockEntity> geolite2db;
    private List<NetworkBlockEntity> software77db;
    private List<NetworkBlockEntity> dbipdb;
    
    private void loadDB() {
    	Geolite2DB geolite2dbLoader = new Geolite2DB("/geolite2/GeoLite2-Country-Blocks-IPv4.csv", "/geolite2/GeoLite2-Country-Locations-en.csv");
    	geolite2db = geolite2dbLoader.loadDB();
    	Collections.sort(geolite2db);
    	
    	Software77DB s77dbLoader = new Software77DB("/software77/IpToCountry.csv");
    	software77db = s77dbLoader.loadDB();
    	Collections.sort(software77db);
    	
    	DBIPDB dbipdbLoader = new DBIPDB("/dbip/dbip-country.csv");
    	dbipdb = dbipdbLoader.loadDB();
    	Collections.sort(dbipdb);
    }
    
    private void mergeDB() {
    	for (int i = 0; i < dbipdb.size(); i++) {
    		NetworkBlockEntity nbe = dbipdb.get(i);
    		long targetip = nbe.getEnd();
    		String cc1 = RangeBinarySearch.search(software77db, targetip);
    		if (cc1 != null) {
    			nbe.setCountryCode(cc1);
    		}
    		String cc2 = RangeBinarySearch.search(geolite2db, targetip); 
    		if (cc2 != null) {
    			nbe.setCountryCode(cc2);
    		}
    	}
    }
    
    private void outputDB(String path) throws FileNotFoundException {
    	PrintWriter writer = new PrintWriter(path);
    	for (int i = 0; i < dbipdb.size(); i++) {
    		NetworkBlockEntity nbe = dbipdb.get(i);
    		writer.println(nbe.getStart() + "," + nbe.getEnd() + "," + nbe.getCountryCode());
    	}
    	writer.close();
    }
    
    public static void main(String[] args) {
    	String outputPath = "D:\\iCodebox\\pushplatform\\ip2country\\src\\main\\resources\\ip2country.csv";
		DatabaseMerger dm = new DatabaseMerger();
		dm.loadDB();
		dm.mergeDB();
		try {
			dm.outputDB(outputPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
