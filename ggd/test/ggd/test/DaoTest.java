package ggd.test;

import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ggd.auth.dao.AdmGroupDao;
import ggd.auth.dao.AdmUserDao;
import ggd.auth.vo.AdmGroup;
import ggd.auth.vo.AdmUser;
import ggd.config.DispatcherConfig;
import ggd.config.SpringWebInitializer;
import ggd.config.XML_DEV_UNIT_Config;
import ggd.core.config.XML_Config;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { XML_Config.class, XML_DEV_UNIT_Config.class, SpringWebInitializer.class,
		DispatcherConfig.class })
@Transactional
public class DaoTest {
	
	@Autowired
	@Qualifier("AdmUserDao")
	private AdmUserDao admUserDao;
	
	@Autowired
	@Qualifier("AdmGroupDao")
	private AdmGroupDao admGroupDao;

	
	@Test
	public void testAddNewUser() {
		System.out.println("******* START testAddNewUser() *******");
		Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
		AdmGroup grp = admGroupDao.findById("GRP0000001");
		AdmUser user = new AdmUser("power", "12345678", "power@gmail.com", "", "", "", now, null, true, true, grp);
		admUserDao.save(user);
		System.out.println("******* END testAddNewUser() *******");
	}
	
	@Test
	public void testFindGroup() {
		System.out.println("******* START testFindGroup() *******");
		AdmGroup grp = admGroupDao.findById("GRP0000001");
		System.out.println(grp);
		System.out.println(grp.getUsers());
		System.out.println("******* END testFindGroup() *******");
	}
}
