package tbox.config;
 
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
 
@Configuration
@ImportResource(value = {
		"file:c:/apache-tomcat-7.0.64/eopen_ap_config/URL.test.xml",
		"file:c:/apache-tomcat-7.0.64/eopen_ap_config/Common.xml",
		"file:c:/apache-tomcat-7.0.64/eopen_ap_config/Display.xml",
		"file:c:/apache-tomcat-7.0.64/eopen_ap_config/auth_core.xml",
		"file:c:/apache-tomcat-7.0.64/eopen_ap_config/db.xml"
		})
public class XML_PROD_Config {
 
}