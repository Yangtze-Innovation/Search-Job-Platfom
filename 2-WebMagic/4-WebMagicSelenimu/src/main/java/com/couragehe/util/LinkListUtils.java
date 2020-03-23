package com.couragehe.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;


public class LinkListUtils {
	private static TreeSet<String> set= new TreeSet<String>();
	private static List<String> list= new ArrayList<String>();
	//链接去重
	public static void DuplicateHandle(List<String> linklist) {
		//去重
		for(String url: linklist) {
			set.add(url);
		}
		writeLinkList(set);
	}
	
	public  static void writeLinkList(Collection<String> coll) {
		File file = new File( "link.txt");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(String url : coll) {
				bw.write(url);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static List<String> getLinkList(int from,int end){
		FileInputStream fis;
		try {
			fis = new FileInputStream("link.txt");
			BufferedReader br= new BufferedReader(new InputStreamReader(fis));
			String str;
			int i = 0 ;
			while((str=br.readLine())!=null) {
				if(i>=from&&i<=end) {
					list.add(str);
				}
				i++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; 
	}
	
}
