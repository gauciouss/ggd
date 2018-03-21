package ggd.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import baytony.org.apache.commons.codec.binary.Base64;
import ggd.core.util.StandardUtil;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.Icon;


public class SimpleTest {
	
	@Test
	public void testGetApkInfo() {		
		try {
			//ApkFile apkFile = new ApkFile("G:/AndroidStudioProjects/MyApplication/app/debug/app-debug.apk");
//			FileReader fr = new FileReader("C:\\Users\\admin\\Desktop\\base64.txt");
//			StringBuilder sb = new StringBuilder();
//			int k = -1;
//			while((k=fr.read()) != -1)
//				sb.append((char)k );
//			
//			byte[] bbs = Base64.decodeBase64(sb.toString());
			File f = new File("G:\\AndroidStudioProjects\\MyApplication\\app\\debug\\app-debug.apk");
//			FileOutputStream foss = new FileOutputStream(f);
//			foss.write(bbs);
//			foss.flush();
//			foss.close();
			
			
			ApkFile apkFile = new ApkFile(f);
			ApkMeta meta = apkFile.getApkMeta();
        	System.out.println("version: " + meta.getVersionCode());
        	System.out.println("versionName: " + meta.getVersionName());
        	System.out.println(meta.getName());
        	System.out.println(meta.getPackageName());
        	System.out.println("*****************");
//        	byte[] bs = apkFile.getIconFile().getData();
//        	FileOutputStream fos = new FileOutputStream(new File("C:/Users/admin/Desktop/icon.png"));
//        	fos.write(bs);
//        	fos.flush();
//        	fos.close();
        	List<Icon> icons = apkFile.getIconFiles();
        	int i = 0;
        	for(Icon icon : icons) {
        		String path = icon.getPath();
        		System.out.println(path);
        		if(path.contains(".png")) {
        			String n = "";
        			if(path.contains("mdpi")) {
        				n = "mdpi";
        			}
        			else if(path.contains("xxxhdpi")) {
						n = "xxxhdpi";
					}
        			else if(path.contains("xxhdpi")) {
						n = "xxhdpi";
					}
        			else if(path.contains("xhdpi")) {
						n = "xhdpi";
					}
        			else if(path.contains("hdpi")) {
        				n = "hdpi";
        			}
					
        			byte[] bs = icon.getData();
        			FileOutputStream fos = new FileOutputStream("C:/Users/admin/Desktop/icon_" + n + ".png");
        			fos.write(bs);
        			fos.flush();
        			fos.close();
        			i++;
        		}
        		
        	}
        	apkFile.close();
        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	   	
        
	}
	
	
	@Test
	public void testWriteBase64ToFile() {
		try {
			String b64 = StandardUtil.readFileToBase64("C:/Users/admin/Desktop/logo/heart.jpg");
			String str = StandardUtil.writeBase64ToFile(b64, "C:/Users/admin/Desktop/logo/test", "heart2.jpg");
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void readFile() {
		try {
			File f = new File("G:/外包/tbox/89125266/logo.jpg");
			FileInputStream fis = new FileInputStream(f);
			byte[] bs = new byte[(int) f.length()];			
			fis.read(bs);
			System.out.println(Base64.encodeBase64String(bs));
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void testParseJson2List() throws JsonParseException, JsonMappingException, IOException {
		String json = "[{\"id\":\"FUN0000002\"},{\"id\":\"FUN0000003\"},{\"id\":\"FUN0000004\"},{\"id\":\"FUN0000006\"}]";
		List<String> list = new ObjectMapper().readValue(json, new TypeReference<ArrayList<String>>(){}); // <- this is the problem area

		System.out.println(list);
	}
	
	public class ID implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5642703326131631994L;
		private String id;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
	}
}
