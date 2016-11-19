package com.lenovo.push.data.util.ip2country.merged;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lenovo.push.data.util.ip2country.entity.NetworkBlockEntity;
import com.lenovo.push.data.util.ip2country.geolite2.GeonameidCountrycodeTable;

public class MergedDB {
	private String mergedDBPath;
	List<NetworkBlockEntity> mergedDB;
	
	public MergedDB(String mergedDBPath) {
		this.mergedDBPath = mergedDBPath;
	}
	public List<NetworkBlockEntity> loadDB() {
		if (mergedDB == null) {
			mergedDB = new ArrayList<NetworkBlockEntity>();
			try {
				File inFile = new File(MergedDB.class.getResource(mergedDBPath).getPath());
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				//br.readLine();
				String line;
				while ((line = br.readLine()) != null) {
					if (!line.startsWith("#")) {
						NetworkBlockEntity nbe = new NetworkBlockEntity();
						String[] parts = line.split(",");					
						nbe.setStart(Long.parseLong(parts[0].replaceAll("^\"|\"$", "")));
						nbe.setEnd(Long.parseLong(parts[1].replaceAll("^\"|\"$", "")));
						nbe.setCountryCode(parts[2].replaceAll("^\"|\"$", ""));
						mergedDB.add(nbe);
					}					
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mergedDB;
	}
}
