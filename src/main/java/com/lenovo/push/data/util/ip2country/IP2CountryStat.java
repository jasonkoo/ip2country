package com.lenovo.push.data.util.ip2country;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import com.lenovo.push.data.util.ip2country.dbip.DBIPDB;
import com.lenovo.push.data.util.ip2country.entity.NetworkBlockEntity;
import com.lenovo.push.data.util.ip2country.geolite2.Geolite2DB;
import com.lenovo.push.data.util.ip2country.software77.Software77DB;
import com.lenovo.push.data.util.ip2country.util.IP2Long;
import com.lenovo.push.data.util.ip2country.util.Long2IP;
import com.lenovo.push.data.util.ip2country.util.RangeBinarySearch;


/**
 * Hello world!
 *
 */
public class IP2CountryStat 
{
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
    
    private void test1() {
 	   loadDB();
 	   
 	   long total = 0;
 	   long notmatched = 0;
 	   //PrintWriter writer = new PrintWriter("C:\\Users\\gulei2\\Desktop\\geolite2-accuracy.txt");
 	   
 	   for (int i = 0; i < geolite2db.size(); i++) {
 		   //writer.println("------------" + (i+1) + "---------------------");
 		   NetworkBlockEntity nbe = geolite2db.get(i);
 		   String countryCode = nbe.getCountryCode();
 		   if (countryCode == null) {
 			   System.out.println("#################" + (i+1) + "," + Long2IP.longToIp(nbe.getStart()) + "," + Long2IP.longToIp(nbe.getEnd()) + "##################");
 			  System.out.println("software77db");
 			   System.out.println(RangeBinarySearch.search(software77db, nbe.getStart()));
 			   System.out.println(RangeBinarySearch.search(software77db, nbe.getEnd()));
 			   System.out.println("dbipdb");
 			   System.out.println(RangeBinarySearch.search(dbipdb, nbe.getStart()));
			   System.out.println(RangeBinarySearch.search(dbipdb, nbe.getEnd()));
 			   // writer.println("#################" + (i+1) + "," + Long2IP.longToIp(nbe.getStart()) + "," + Long2IP.longToIp(nbe.getEnd()) + "##################");
 			  total += nbe.getEnd() - nbe.getStart() + 1;
 		   } else {
 			 /*  for (long j = nbe.getStart(); j <= nbe.getEnd(); j++) {
 				   total++;
 				   String s1 = RangeBinarySearch.search(ip2CountryTool.software77db, j);
 				   String s2 = RangeBinarySearch.search(ip2CountryTool.dbipdb, j);
 				   if (countryCode.equals(s1) || countryCode.equals(s2)) {
 					   matched++;
 				   } else {
 					   System.out.println(Long2IP.longToIp(nbe.getStart()) + "\t" + Long2IP.longToIp(nbe.getEnd()) + "\t" + nbe.getCountryCode() + "\t" + s1 + "\t" + s2);
 					  // writer.println(Long2IP.longToIp(nbe.getStart()) + "\t" + Long2IP.longToIp(nbe.getEnd()) + "\t" + nbe.getCountryCode() + "\t" + s1 + "\t" + s2);
 				   }
 			   }*/
 			   total += nbe.getEnd() - nbe.getStart() + 1;
 			   String s1 = RangeBinarySearch.search(software77db, nbe.getEnd());
 			   String s2 = RangeBinarySearch.search(dbipdb, nbe.getEnd());
 			   if (!countryCode.equals(s1) && !countryCode.equals(s2)) {
 				   notmatched += nbe.getEnd() - nbe.getStart() + 1;
 				   //System.out.println(Long2IP.longToIp(nbe.getStart()) + "\t" + Long2IP.longToIp(nbe.getEnd()) + "\t" + nbe.getCountryCode() + "\t" + s1 + "\t" + s2);
 			   }
 		   }
 		  // writer.println();
 	   }
 	   //writer.println((double)matched / (double)total);
 	   System.out.println("total: " + total + ", not matched:" + notmatched);
 	   System.out.println("relative accuracy: " + (1 - (double)notmatched / (double)total));
 	  // writer.close();
    }
    
    private void test2() {
  	   loadDB();
  	   
  	   long total = 0;
  	   long notmatched = 0;
  	   //PrintWriter writer = new PrintWriter("C:\\Users\\gulei2\\Desktop\\geolite2-accuracy.txt");
  	   
  	   for (int i = 0; i < dbipdb.size(); i++) {
  		   //writer.println("------------" + (i+1) + "---------------------");
  		   NetworkBlockEntity nbe = dbipdb.get(i);
  		   String countryCode = nbe.getCountryCode();
  		   if (countryCode == null) {
  			  // writer.println("#################" + (i+1) + "," + Long2IP.longToIp(nbe.getStart()) + "," + Long2IP.longToIp(nbe.getEnd()) + "##################");
  		   } else {
  			 /*  for (long j = nbe.getStart(); j <= nbe.getEnd(); j++) {
  				   total++;
  				   String s1 = RangeBinarySearch.search(ip2CountryTool.software77db, j);
  				   String s2 = RangeBinarySearch.search(ip2CountryTool.dbipdb, j);
  				   if (countryCode.equals(s1) || countryCode.equals(s2)) {
  					   matched++;
  				   } else {
  					   System.out.println(Long2IP.longToIp(nbe.getStart()) + "\t" + Long2IP.longToIp(nbe.getEnd()) + "\t" + nbe.getCountryCode() + "\t" + s1 + "\t" + s2);
  					  // writer.println(Long2IP.longToIp(nbe.getStart()) + "\t" + Long2IP.longToIp(nbe.getEnd()) + "\t" + nbe.getCountryCode() + "\t" + s1 + "\t" + s2);
  				   }
  			   }*/
  			   total += nbe.getEnd() - nbe.getStart() + 1;
  			   String s1 = RangeBinarySearch.search(geolite2db, nbe.getEnd());
  			   String s2 = RangeBinarySearch.search(software77db, nbe.getEnd());
  			   if (!countryCode.equals(s1) && !countryCode.equals(s2)) {
  				   notmatched += nbe.getEnd() - nbe.getStart() + 1;
  				   //System.out.println(Long2IP.longToIp(nbe.getStart()) + "\t" + Long2IP.longToIp(nbe.getEnd()) + "\t" + nbe.getCountryCode() + "\t" + s1 + "\t" + s2);
  			   }
  		   }
  		  // writer.println();
  	   }
  	   //writer.println((double)matched / (double)total);
  	   System.out.println("total: " + total + ", not matched:" + notmatched);
  	   System.out.println("relative accuracy: " + (1 - (double)notmatched / (double)total));
  	  // writer.close();
    }
    
    public void test3() {
    	   loadDB();
      	   
      	   long total = 0;
      	   long notmatched = 0;
      	   //PrintWriter writer = new PrintWriter("C:\\Users\\gulei2\\Desktop\\geolite2-accuracy.txt");
      	   
      	   for (int i = 0; i < software77db.size(); i++) {
      		   //writer.println("------------" + (i+1) + "---------------------");
      		   NetworkBlockEntity nbe = software77db.get(i);
      		   String countryCode = nbe.getCountryCode();
      		   if (countryCode == null) {
      			   System.out.println("#################" + (i+1) + "," + Long2IP.longToIp(nbe.getStart()) + "," + Long2IP.longToIp(nbe.getEnd()) + "##################");
      			  
      			   // writer.println("#################" + (i+1) + "," + Long2IP.longToIp(nbe.getStart()) + "," + Long2IP.longToIp(nbe.getEnd()) + "##################");
      		   } else {
      			 /*  for (long j = nbe.getStart(); j <= nbe.getEnd(); j++) {
      				   total++;
      				   String s1 = RangeBinarySearch.search(ip2CountryTool.software77db, j);
      				   String s2 = RangeBinarySearch.search(ip2CountryTool.dbipdb, j);
      				   if (countryCode.equals(s1) || countryCode.equals(s2)) {
      					   matched++;
      				   } else {
      					   System.out.println(Long2IP.longToIp(nbe.getStart()) + "\t" + Long2IP.longToIp(nbe.getEnd()) + "\t" + nbe.getCountryCode() + "\t" + s1 + "\t" + s2);
      					  // writer.println(Long2IP.longToIp(nbe.getStart()) + "\t" + Long2IP.longToIp(nbe.getEnd()) + "\t" + nbe.getCountryCode() + "\t" + s1 + "\t" + s2);
      				   }
      			   }*/
      			   total += nbe.getEnd() - nbe.getStart() + 1;
      			   String s1 = RangeBinarySearch.search(geolite2db, nbe.getStart());
      			   String s2 = RangeBinarySearch.search(dbipdb, nbe.getStart());
      			   if (!countryCode.equals(s1) && !countryCode.equals(s2)) {
      				   notmatched += nbe.getEnd() - nbe.getStart() + 1;
      				   //System.out.println(Long2IP.longToIp(nbe.getStart()) + "\t" + Long2IP.longToIp(nbe.getEnd()) + "\t" + nbe.getCountryCode() + "\t" + s1 + "\t" + s2);
      			   }
      		   }
      		  // writer.println();
      	   }
      	   //writer.println((double)matched / (double)total);
    	   System.out.println("total: " + total + ", not matched:" + notmatched);
      	   System.out.println("relative accuracy: " + (1 - (double)notmatched / (double)total));
      	  // writer.close();
    }
	
    public static void main( String[] args ) throws FileNotFoundException
    {
	   IP2CountryStat tool = new IP2CountryStat();
	   tool.test2();
       //String ip = "42.104.0.7";
       //long target = IP2Long.ipToLong(ip);
       //System.out.println(RangeBinarySearch.search(ip2CountryTool.geolite2db, target));
       //System.out.println(RangeBinarySearch.search(ip2CountryTool.software77db, target));
       //System.out.println(RangeBinarySearch.search(ip2CountryTool.dbipdb, target)); 
   
    }
}
