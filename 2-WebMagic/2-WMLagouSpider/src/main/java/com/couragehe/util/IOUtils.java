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
/**
 * 将集合的内容按行写入文件中进行持久化
 * 需要用的时候在按行读出返回一个列表
 * @author CourageHe
 *
 */
public class IOUtils {
	/**
	 * 将集合每个元素按行写入文件
	 * @param coll
	 */
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
	
	/**
	 * 按行读取文件返回集合
	 * @param from 起始点
	 * @param end  终结点
	 * @return  列表
	 */
	public static List<String> getLinkList(){
		List<String> list = new ArrayList<String>();
		FileInputStream fis;
		try {
			fis = new FileInputStream("link.txt");
			BufferedReader br= new BufferedReader(new InputStreamReader(fis));
			String str;
			while((str=br.readLine())!=null) {
					list.add(str);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; 
	}
	
}

