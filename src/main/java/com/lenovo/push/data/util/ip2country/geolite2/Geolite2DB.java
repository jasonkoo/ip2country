package com.lenovo.push.data.util.ip2country.geolite2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.net.util.SubnetUtils;

import com.lenovo.push.data.util.ip2country.entity.NetworkBlockEntity;
import com.lenovo.push.data.util.ip2country.util.IP2Long;
/**
 * 
 * @author gulei2
 * 
 * Load Geolite2 database
 *
 */

public class Geolite2DB {
	private String ipDBPath;
	private String codeDBPath;
	
	List<NetworkBlockEntity> geoLite2DB;
	
	public Geolite2DB(String ipDBPath, String codeDBPah) {
		this.ipDBPath = ipDBPath;
		this.codeDBPath = codeDBPah;
	}
	
	public List<NetworkBlockEntity> loadDB() {
		if (geoLite2DB == null) {
			// Load idCodeTable first
			GeonameidCountrycodeTable gct = new GeonameidCountrycodeTable(codeDBPath);
			HashMap<String, String> idCodeTable = gct.getIdCodeTable();
			
			// Create geoLite2 database
			geoLite2DB = new ArrayList<NetworkBlockEntity>();
			try {
				File inFile = new File(Geolite2DB.class.getResource(ipDBPath).getPath());
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				br.readLine();
				String line;
				while ((line = br.readLine()) != null) {
					NetworkBlockEntity nbe = new NetworkBlockEntity();
					String[] parts = line.split(",");
					SubnetUtils su = new SubnetUtils(parts[0]);
					//Set to true to include the network and broadcast addresses.
					su.setInclusiveHostCount(true);
					nbe.setStart(IP2Long.ipToLong(su.getInfo().getLowAddress()));
					nbe.setEnd(IP2Long.ipToLong(su.getInfo().getHighAddress()));
					// Choose the stated IP range holder first.
					if (parts[2].equals("")) {
						nbe.setCountryCode(idCodeTable.get(parts[1]));
					} else {
						nbe.setCountryCode(idCodeTable.get(parts[2]));
					}
					//nbe.setCountryCode(idCodeTable.get(parts[1]));
					geoLite2DB.add(nbe);
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		return geoLite2DB;
	}
	
	public static void main(String[] args) {
		Geolite2DB dbLoader = new Geolite2DB("/geolite2/GeoLite2-Country-Blocks-IPv4.csv", "/geolite2/GeoLite2-Country-Locations-en.csv");
		List<NetworkBlockEntity> geoLite2DB = dbLoader.loadDB();
		Collections.sort(geoLite2DB);
		System.out.println(geoLite2DB.size());
		for (int i = 0; i < 10; i++) {
			NetworkBlockEntity nbe = geoLite2DB.get(i);
			System.out.println(nbe.getStart() + "," + nbe.getEnd() + "," + nbe.getCountryCode());
		}
		NetworkBlockEntity lastNBE = geoLite2DB.get(geoLite2DB.size() - 1);
		System.out.println(lastNBE.getStart() + "," + lastNBE.getEnd() + "," + lastNBE.getCountryCode());
	}
}
