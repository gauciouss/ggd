package ggd.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import ggd.core.common.Constant;
import ggd.core.config.XML_Config;

public class SpringWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	private final Class<?>[] config;
	
	public SpringWebInitializer() {
		super();
		List<Class<?>> tmp = new ArrayList<Class<?>>();
		tmp.add(SpringWebConfig.class);
		tmp.add(DispatcherConfig.class);
		tmp.add(XML_Config.class);
		switch(Constant.MACHINE){
		case TEST :  
			tmp.add(XML_TEST_Config.class);
			break;
		case PRODUCTION :
			tmp.add(XML_PROD_Config.class);
			break;
		default : 
			tmp.add(XML_DEV_Config.class);
		}
		config = tmp.toArray(new Class<?>[tmp.size()]);
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return config;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/ui/*" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
	
	

}
