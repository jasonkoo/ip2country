package com.lenovo.push.data.util.ip2country.software77;

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

/**
 * 
 *  @author gulei2
 *  
 *  Load software77 database
 *
 */
public class Software77DB {
	private String ipDBPath;
	
	List<NetworkBlockEntity> software77DB;
	
	public Software77DB(String ipDBPath) {
		this.ipDBPath = ipDBPath;
	}
	
	public List<NetworkBlockEntity> loadDB() {
		if (software77DB == null) {
			software77DB = new ArrayList<NetworkBlockEntity>();
			try {
				File inFile = new File(GeonameidCountrycodeTable.class.getResource(ipDBPath).getPath());
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				//br.readLine();
				String line;
				while ((line = br.readLine()) != null) {
					if (!line.startsWith("#")) {
						NetworkBlockEntity nbe = new NetworkBlockEntity();
						String[] parts = line.split(",");					
						nbe.setStart(Long.parseLong(parts[0].replaceAll("^\"|\"$", "")));
						nbe.setEnd(Long.parseLong(parts[1].replaceAll("^\"|\"$", "")));
						nbe.setCountryCode(parts[4].replaceAll("^\"|\"$", ""));
						software77DB.add(nbe);
					}					
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return software77DB;
	}
	
	public static void main(String[] args) {
		Software77DB s77dbLoader = new Software77DB("/software77/IpToCountry.csv");
		List<NetworkBlockEntity> s77DB = s77dbLoader.loadDB();
		Collections.sort(s77DB);
		System.out.println(s77DB.size());
		for (int i = 0; i < 10; i++) {
			NetworkBlockEntity nbe = s77DB.get(i);
			System.out.println(nbe.getStart() + "," + nbe.getEnd() + "," + nbe.getCountryCode());
		}
		NetworkBlockEntity lastNBE = s77DB.get(s77DB.size() - 1);
		System.out.println(lastNBE.getStart() + "," + lastNBE.getEnd() + "," + lastNBE.getCountryCode());
	}
}
