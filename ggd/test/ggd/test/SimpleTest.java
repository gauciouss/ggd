package ggd.test;

import java.io.File;
import java.io.FileInputStream;
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
import ggd.core.util.JSONUtil;
import tbox.dispatcher.action.service.command.entity.IndexInfoEntity;
import tbox.dispatcher.action.service.command.entity.IndexInfoEntity.App;
import tbox.dispatcher.action.service.command.entity.IndexInfoEntity.KVS;
import tbox.dispatcher.action.service.command.entity.IndexInfoEntity.KVS.KV;
import tbox.dispatcher.action.service.command.entity.IndexInfoEntity.Kind;


public class SimpleTest {
	
	@Test
	public void testIndexAdapter() {
		List<Kind> kinds = new ArrayList<Kind>();
		for(int i=0 ; i<3 ; i++) {
			Kind k = new Kind(String.valueOf(i+1), "test" + i, "");
			kinds.add(k);
		}
		
		List<App> apps = new ArrayList<App>();
		for(int i=0 ; i<10 ; i++) {
			App app = new App(String.valueOf(i), String.valueOf(i), "test" + i, "app" + i, "", "1.0", System.currentTimeMillis(), "com.youtube.www");
			apps.add(app);
		}
		List<KV> kv1 = new ArrayList<KV>();
		for(int i=0 ; i<5 ; i++) {
			KV kv = new KV("", "", "test" + i, String.valueOf(i));
			kv1.add(kv);
		}
		
		List<KV> kv2 = new ArrayList<KV>();
		for(int i=0 ; i<5 ; i++) {
			KV kv = new KV("", "", "test" + i, String.valueOf(i));
			kv2.add(kv);
		}
		
//		IndexInfoEntity adapter = new IndexInfoEntity(apps, kinds, new KVS(kv1, kv2)); 
//		
//		try {
//			System.out.println(JSONUtil.toJsonString(adapter));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
