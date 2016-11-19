package com.lenovo.push.data.util.ip2country.geolite2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class GeonameidCountrycodeTable {
	
	private String path;
	private HashMap<String, String> idCodeTable;
	
	public GeonameidCountrycodeTable(String path) {
		this.path = path;
	}
	
	private void init() {
		if (idCodeTable == null) {
			idCodeTable = new HashMap<String, String>();
			
			try {
				File inFile = new File(GeonameidCountrycodeTable.class.getResource(path).getPath());
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				br.readLine();
				String line;
				while ((line = br.readLine()) != null) {
					String[] parts = line.split(",");
					if (parts.length >= 5) {
						idCodeTable.put(parts[0], parts[4]);
					}	else {
						idCodeTable.put(parts[0], parts[2]);
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public HashMap<String, String> getIdCodeTable() {
		init();
		return this.idCodeTable;
	}
	
	
	public static void main(String[] args) {
		GeonameidCountrycodeTable gct = new GeonameidCountrycodeTable("/geolite2/GeoLite2-Country-Locations-en.csv");
		HashMap<String, String> idCodeTable = gct.getIdCodeTable();
		for (String key: idCodeTable.keySet()) {
			System.out.println(key + ": " + idCodeTable.get(key));
		}
		System.out.println(idCodeTable.size());

	}

}
