package ggd.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ggd.auth.AuthService;
import ggd.auth.vo.AdmUser;
import ggd.config.DispatcherConfig;
import ggd.config.SpringWebInitializer;
import ggd.config.XML_DEV_UNIT_Config;
import ggd.core.config.XML_Config;
import ggd.dispatcher.main.IndexDispatcher;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { XML_Config.class, XML_DEV_UNIT_Config.class, SpringWebInitializer.class,
		DispatcherConfig.class })
@Transactional
public class ServiceTest {
	
	private final static Logger log = LoggerFactory.getLogger(ServiceTest.class);

	@Autowired
	@Qualifier("AuthService")
	private AuthService service;
	
	@Test
	public void testAuth() {
		log.trace("******* START: {}.testAuth() *******", this.getClass());
		AdmUser user = service.authenticate("admin", "123456");
		log.info("******* END: {}.testAuth(), user: {}", this.getClass(), user);
	}
}
