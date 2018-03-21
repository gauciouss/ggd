package tbox.proxy.cwb.gov.tw;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import baytony.util.net.http.HttpCallBack;
import baytony.util.net.http.HttpProxy;
import ggd.core.common.Config;
import ggd.core.common.Constant;
import ggd.core.config.XML_Config;

@Repository("CWBOpendataAPI")
public class OpendataAPI extends HttpProxy {
	
	private static final Logger log = LoggerFactory.getLogger(OpendataAPI.class);
	
	@Autowired
	@Qualifier(XML_Config.COMMON_CONFIG)
	private Config cCfg;
	
	@Autowired
	@Qualifier(XML_Config.URL_CONFIG)
	private Config uCfg;
	
	
	public Entity call(String code) throws IOException, Exception {
		Parser p = new Parser(uCfg.getValue("CWB") + uCfg.getValue("opendataapi"), code, cCfg.getValue("CWB_AUTH_KEY"));
		super.getUTF8(p);
		Entity t = p.getResponse();
		log.debug("get CWB data: {}", t);
		return t;
	}
	
	private class Parser implements HttpCallBack<Entity> {
		
		private String url;
		
		private String code;
		
		private String authKey;
		
		private Entity entity;
		
		public Parser(String url, String code, String authKey) {
			this.url = url;
			this.code = code;
			this.authKey = authKey;
		}
		
		@Override
		public void doReader(URL url, String encode, InputStream reader) throws UnsupportedEncodingException, IOException, Exception {
			Profiler p = new Profiler();
			log.trace("START: {}.doReader(), url: {}, encode: {}", this.getClass(), url, encode);
			StringBuilder sb = new StringBuilder();
			InputStreamReader isr = new InputStreamReader(reader, encode);
			int i = -1;
			while((i = isr.read()) != -1) {
				char c = (char) i;
				sb.append(c);
			}			
			log.debug("code: {}, data: {}", this.code, sb.toString());
			if(sb.length() > 0) {
				this.entity = new Entity();
				readXML(sb.toString(), encode);
			}			
			
			log.trace("END: {}.doReader(), url: {}, encode: {}, exec TIME: {} ms.", this.getClass(), url, encode, p.executeTime());
		}
		
		private void readXML(String xml, String encode) throws ParserConfigurationException, SAXException, IOException {
			SaxHandler handler = new SaxHandler(this.entity);
			SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser parser = factory.newSAXParser();
	        parser.parse(new InputSource(new ByteArrayInputStream(xml.getBytes(encode))), handler);
		}

		@Override
		public Entity getResponse() {
			return entity;
		}

		@Override
		public URL getUrl() {
			try {
				return new URL(url + "?dataid=" + code + "&authorizationkey=" + authKey);
			} catch (MalformedURLException e) {
				log.error(StringUtil.getStackTraceAsString(e));
			}
			return null;
		}

		@Override
		public String getParams(String encode) {
			return Constant.EMPTY;
		}

		@Override
		public Map<String, String> getRequestProperties() {
			return null;
		}
	}
	
	private class SaxHandler extends DefaultHandler {
		
		private Entity entity;
		
		private int flag = 0;
		private boolean isReady2SetValue = false;
		
		public SaxHandler(Entity entity) {
			this.entity = entity;
		}
		 
	    /* 此方法有三个参数
	       arg0是传回来的字符数组，其包含元素内容
	       arg1和arg2分别是数组的开始位置和结束位置 */
	    @Override
	    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
	        String content = new String(arg0, arg1, arg2);
	        //System.out.println(content);	 
	        if(isReady2SetValue) {
	        	System.out.println("===> ready to set value: " +content);
	        	switch(flag) {
			        case 1:
			        	entity.locationName = content;
			        	break;
			        case 2:
			        	entity.desc = content;
			        	break;
			        case 3: 
			        	entity.today = content;
			        	break;
			        case 4:
			        	entity.future = content;
			        	break;
	        	}
	        }
	        
	        super.characters(arg0, arg1, arg2);
	    }
	 
	 
	    /*arg0是名称空间
	      arg1是包含名称空间的标签，如果没有名称空间，则为空
	      arg2是不包含名称空间的标签
	      arg3很明显是属性的集合 */
	    @Override
	    public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
	        if("locationName".equals(arg2) || "parameterValue".equals(arg2)) {
	        	isReady2SetValue = true;
	        	this.flag++;
	        }	       
	        super.startElement(arg0, arg1, arg2, arg3);
	    }
	    
	    @Override
	    public void endElement(String uri, String localName, String qName) throws SAXException {
	    	isReady2SetValue = false;
	    	super.endElement(uri, localName, qName);
	    }
	}
	

	public static class Entity implements Serializable {
		
		private static final long serialVersionUID = 3406969887442081442L;

		/**
		 * 地區名稱
		 */
		private String locationName;
		
		/**
		 * 天氣說明
		 */
		private String desc;
		
		/**
		 * 今日預報
		 */
		private String today;
		
		/**
		 * 明日預報
		 */
		private String future;

		/**
		 * @return the locationName
		 */
		public String getLocationName() {
			return locationName;
		}

		/**
		 * @param locationName the locationName to set
		 */
		public void setLocationName(String locationName) {
			this.locationName = locationName;
		}

		/**
		 * @return the desc
		 */
		public String getDesc() {
			return desc;
		}

		/**
		 * @param desc the desc to set
		 */
		public void setDesc(String desc) {
			this.desc = desc;
		}

		/**
		 * @return the today
		 */
		public String getToday() {
			return today;
		}

		/**
		 * @param today the today to set
		 */
		public void setToday(String today) {
			this.today = today;
		}

		/**
		 * @return the futures
		 */
		public String getFuture() {
			return future;
		}

		/**
		 * @param futures the futures to set
		 */
		public void setFuture(String future) {
			this.future = future;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Entity [locationName=");
			builder.append(locationName);
			builder.append(", desc=");
			builder.append(desc);
			builder.append(", today=");
			builder.append(today);
			builder.append(", future=");
			builder.append(future);
			builder.append("]");
			return builder.toString();
		}
	}
}
