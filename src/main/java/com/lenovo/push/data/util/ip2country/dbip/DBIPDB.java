package com.lenovo.push.data.util.ip2country.dbip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lenovo.push.data.util.ip2country.entity.NetworkBlockEntity;
import com.lenovo.push.data.util.ip2country.geolite2.GeonameidCountrycodeTable;
import com.lenovo.push.data.util.ip2country.util.IP2Long;
/**
 * 
 * @author gulei2
 * 
 * Load dbip database
 *
 */
public class DBIPDB {
	private String ipDBPath;
	
	List<NetworkBlockEntity> dbipDB;
	
	public DBIPDB(String ipDBPath) {
		this.ipDBPath = ipDBPath;
	}
	
	public List<NetworkBlockEntity> loadDB(){
		if (dbipDB == null) {
			dbipDB = new ArrayList<NetworkBlockEntity>();
			try {
				File inFile = new File(GeonameidCountrycodeTable.class.getResource(ipDBPath).getPath());
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				//br.readLine();
				String line;
				while ((line = br.readLine()) != null) {
					if (!line.startsWith("#") && !line.contains(":")) {
						NetworkBlockEntity nbe = new NetworkBlockEntity();
						String[] parts = line.split(",");					
						nbe.setStart(IP2Long.ipToLong((parts[0].replaceAll("^\"|\"$", ""))));
						nbe.setEnd(IP2Long.ipToLong((parts[1].replaceAll("^\"|\"$", ""))));
						nbe.setCountryCode(parts[2].replaceAll("^\"|\"$", ""));
						dbipDB.add(nbe);
					}					
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return dbipDB;
	}
	
	public static void main(String[] args) {
		DBIPDB dbipdbLoader = new DBIPDB("/dbip/dbip-country.csv");
		List<NetworkBlockEntity> dbipDB = dbipdbLoader.loadDB();
		Collections.sort(dbipDB);
		System.out.println(dbipDB.size());
		for (int i = 0; i < 10; i++) {
			NetworkBlockEntity nbe = dbipDB.get(i);
			System.out.println(nbe.getStart() + "," + nbe.getEnd() + "," + nbe.getCountryCode());
		}
		NetworkBlockEntity lastNBE = dbipDB.get(dbipDB.size() - 1);
		System.out.println(lastNBE.getStart() + "," + lastNBE.getEnd() + "," + lastNBE.getCountryCode());
 	}
}
