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


public class SimpleTest {
	
	
	
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
