package ggd.config;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import ggd.core.interceptor.CrossDomainInterceptor;

 
@EnableWebMvc
@Configuration
@ComponentScan({ "ggd.web", "ggd.auth", "ggd.core", "ggd.dispatcher" })
@Import({DispatcherConfig.class})
public class SpringWebConfig extends WebMvcConfigurerAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(SpringWebConfig.class);
 
	
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		log.debug("{} in viewResolver().", this.getClass());
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new CrossDomainInterceptor());
	}
	
	
 
}