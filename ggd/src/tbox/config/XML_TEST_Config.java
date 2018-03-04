package tbox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value = { 
		"file:/usr/local/tomcat/apache-tomcat-8.5.20/tox_config/Common.xml",
		"file:/usr/local/tomcat/apache-tomcat-8.5.20/tox_config/db.xml", 
		"file:/usr/local/tomcat/apache-tomcat-8.5.20/tox_config/URL.test.xml",
		"file:/usr/local/tomcat/apache-tomcat-8.5.20/tox_config/Display.xml"
		})
public class XML_TEST_Config {

}