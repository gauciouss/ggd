package ggd.test;

import java.util.List;
import java.util.Set;

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

import ggd.auth.AuthException;
import ggd.auth.AuthService;
import ggd.auth.vo.AdmFunc;
import ggd.auth.vo.AdmGroup;
import ggd.auth.vo.AdmUser;
import ggd.core.config.XML_Config;
import tbox.TBoxException;
import tbox.config.DispatcherConfig;
import tbox.config.SpringWebInitializer;
import tbox.config.XML_DEV_UNIT_Config;
import tbox.data.vo.Company;
import tbox.data.vo.CompanyEntity;
import tbox.proxy.cwb.gov.tw.OpendataAPI.Entity;
import tbox.service.TBoxService;

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
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService tboxService;
	
	@Test
	public void testGetWeather() {
		log.trace("******* START: {}.testGetWeather() *******", this.getClass());
		try {
			Entity entity = tboxService.getWeatherReport("54789963", "1A-2V-3S-FV-AS-FX", "");
			log.debug("{}", entity);
			log.info("******* END: {}.testGetWeather()", this.getClass());
		} catch (TBoxException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindAllCompany() {
		log.trace("******* START: {}.testFindAllCompany() *******", this.getClass());
		try {
			List<CompanyEntity> comps = tboxService.findAllComp();
			for(CompanyEntity comp : comps)
				System.out.println(comp);
			log.info("******* END: {}.testFindAllCompany()", this.getClass());
		} catch (TBoxException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void testFindGroup() {
		log.trace("******* START: {}.testFindGroup() *******", this.getClass());
		try {
			AdmGroup grp = service.findGroup("GRP0000001");
			log.debug("******* all funcs: {}", grp.getFuncs());
			log.info("******* END: {}.testFindGroup(), grp: {}", this.getClass(), grp);
		} catch (AuthException e) {
			e.printStackTrace();
		}
	}	
	
	
	@Test
	public void testAuth() {
		log.trace("******* START: {}.testAuth() *******", this.getClass());
		AdmUser user;
		try {
			user = service.authenticate("admin", "123456");
			Set<AdmFunc> funcs = user.getGroup().getFuncs();
			log.debug("******* all funcs: {}", funcs);
			log.info("******* END: {}.testAuth(), user: {}", this.getClass(), user);
		} catch (AuthException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testFindUsers() {
		log.trace("******* START: {}.testFindUsers() *******", this.getClass());
		List<AdmUser> users;
		try {
			users = service.findUsers();
			log.info("******* END: {}.testFindUsers(), size: {},", this.getClass(), users.size());
		} catch (AuthException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testAddNewFunc() {
		log.trace("******* START: {}.testAddNewFunc() *******", this.getClass());
		try {
			service.addFunc("測試功能ROOT", true, "", "", 1, true, true);
			log.info("******* END: {}.testAddNewFunc()", this.getClass());
		} catch (AuthException e) {
			e.printStackTrace();
		}
	}
}
