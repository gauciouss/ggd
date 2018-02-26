package tbox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value = {
		"file:C:/Users/admin/git/ggd/ggd/config/Common.xml",
		"file:C:/Users/admin/git/ggd/ggd/config/db.unit.xml", 
		"file:C:/Users/admin/git/ggd/ggd/config/URL.dev.xml",
		"file:C:/Users/admin/git/ggd/ggd/config/Display.xml"})
public class XML_DEV_UNIT_Config {

}