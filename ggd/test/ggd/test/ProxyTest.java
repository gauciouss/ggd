package ggd.test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ggd.core.config.XML_Config;
import tbox.config.DispatcherConfig;
import tbox.config.SpringWebInitializer;
import tbox.config.XML_DEV_UNIT_Config;
import tbox.proxy.cwb.gov.tw.OpendataAPI;
import tbox.proxy.cwb.gov.tw.OpendataAPI.Entity;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { XML_Config.class, XML_DEV_UNIT_Config.class, SpringWebInitializer.class,
		DispatcherConfig.class })
public class ProxyTest {

	@Autowired
	@Qualifier("CWBOpendataAPI")
	private OpendataAPI cwbAPI;
	
	@Test
	public void testGetCWBData() {
		System.out.println("******* START testGetCWBData() *******");
		try {
			Entity entity = cwbAPI.call("F-C0032-010");
			System.out.println(entity);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("******* END testGetCWBData() *******");
	}
	
	@Test
	public void testGetWeather() {
		try {
			URL url = new URL("http://opendata.cwb.gov.tw/opendataapi?dataid=F-C0032-009&authorizationkey=CWB-38B9E773-0C53-4688-9789-90AFF00C8A5F");
			HttpURLConnection URLConn = (HttpURLConnection) url .openConnection();
//			URLConn.setDoOutput(true); 
//		    URLConn.setDoInput(true); 
		    ((HttpURLConnection) URLConn).setRequestMethod("GET"); 
//		    URLConn.setUseCaches(false); 
//		    URLConn.setAllowUserInteraction(true); 
//		    HttpURLConnection.setFollowRedirects(true); 
//		    URLConn.setInstanceFollowRedirects(true); 
		    java.io.BufferedReader rd = new java.io.BufferedReader( new java.io.InputStreamReader(URLConn.getInputStream(), "UTF-8")); 
		    String line; 
		    while ((line = rd.readLine()) != null) { 
		    	  System.out.println(line); 
		      } 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
