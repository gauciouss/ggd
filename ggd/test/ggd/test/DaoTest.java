package ggd.test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

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
import ggd.core.config.XML_Config;
import tbox.config.DispatcherConfig;
import tbox.config.SpringWebInitializer;
import tbox.config.XML_DEV_UNIT_Config;
import tbox.data.dao.AreaDao;
import tbox.data.dao.CompanyDao;
import tbox.data.vo.Area;
import tbox.data.vo.Company;

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
	
	@Autowired
	@Qualifier("AreaDao")
	private AreaDao areaDao;
	
	@Autowired
	@Qualifier("CompanyDao")
	private CompanyDao cDao;
	
	@Test
	public void testFindAllUsers() {
		System.out.println("******* START testFindAllUsers() *******");
		List<AdmUser> list = admUserDao.findUsers();
		System.out.println("******* list size: " + list.size());
		//System.out.println(list);
		for(AdmUser user : list) {
			System.out.println(user);
		}
		
		
		System.out.println("******* END testFindAllUsers() *******");
	}
	
	@Test
	public void testAddNewGroup() {
		System.out.println("******* START testGetArea() *******");
		Area a = areaDao.findById(111);
		AdmGroup grp = admGroupDao.findById("GRP0000001");
		Company c = new Company("89125522", "TEST", a, "", "", "", "", "", "", grp);
		cDao.save(c);
		//cDao.save("85475566", "TEST", a, "", "", "", "", "", "", true);
		System.out.println(cDao.findById("89125522"));
		System.out.println("******* END testGetArea() *******");
	}
	
	@Test
	public void testGetArea() {
		System.out.println("******* START testGetArea() *******");
		List<Area> list = areaDao.findAll();
		System.out.println(list);
		System.out.println("******* END testGetArea() *******");
	}

	
	@Test
	public void testAddNewUser() {
		System.out.println("******* START testAddNewUser() *******");
		Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
		AdmGroup grp = admGroupDao.findById("GRP0000001");
		AdmUser user = new AdmUser("power", "12345678", "AAA", "power@gmail.com", "", "", "", now, null, true, true, grp);
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
