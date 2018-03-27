package tbox.data.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import baytony.util.Util;
import ggd.core.db.HibernateDao;
import tbox.data.vo.Company;
import tbox.data.vo.CompanyEntity;

@Repository("CompanyDao")
public class CompanyDao extends HibernateDao<Company, String> {
	
	private final static Logger log = LoggerFactory.getLogger(CompanyDao.class);
	
	
//	private static final String SQL_UPDATE_COMP_INFO = 
//			"update company set "
//			+ " name = ?, "
//			+ " area_id = ?, "
//			+ " logo_url = ?, "
//			+ " background_url = ?, "
//			+ " fast_key1 = ?, "
//			+ " fast_key2 = ?, "
//			+ " fast_key3 = ?, "
//			+ " fast_key4 = ?, "
//			+ " group_id = ?"
//			+ " where EIN = ?";
	
	private static final String SQL_FIND_EIN_BY_ACCOUNT = 
			"select distinct c.EIN " + 
			"	from company  "+ 
			"    inner join adm_group ag on c.group_id = ag.group_id " + 
			"    inner join adm_user au on ag.group_id = au.group_id " + 
			"    where au.account = ?";
	
	private static final String SQL_FIND_ALL = 
			"select EIN, name, a.area_name area, n.isEnabled, n.isApproved " + 
			"	from company c " + 
			"    inner join area a on  c.area_id = a.area_id " + 
			"    inner join adm_group n on c.group_id = n.group_id";
	
	
//	private static final String SQL_ADD_NEW_COMPANY =
//			"insert into company(EIN, name, area_id, logo_url, background_url, fast_key1, fast_key2, fast_key3, fast_key4, group_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
//	public void save(String EIN, String name, String areaId, String logo, String bg, String fastKey1, String fastKey2, String fastKey3, String fastKey4, String grpId) {
//		Profiler p = new Profiler();
//		log.trace("START: {}.save(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, fastKey1: {}, fastKey2: {}, fastKey3: {}, fastKey4: {}, grpId: {}", this.getClass(), EIN, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId);
//		super.executeUpateQuery(SQL_ADD_NEW_COMPANY, EIN, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId);
//		log.info("END: {}.save(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, fastKey1: {}, fastKey2: {}, fastKey3: {}, fastKey4: {}, grpId: {}, exec TIME: {} ms.", this.getClass(), EIN, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId, p.executeTime());
//	}
//	
//	
//	public void update(String EIN, String name, String areaId, String logo, String bg, String fastKey1, String fastKey2, String fastKey3, String fastKey4, String grpId) {
//		Profiler p = new Profiler();
//		log.trace("START: {}.update(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, fastKey1: {}, fastKey2: {}, fastKey3: {}, fastKey4: {}, grpId: {}", this.getClass(), EIN, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId);
//		super.executeUpateQuery(SQL_UPDATE_COMP_INFO, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId, EIN);
//		log.info("END: {}.update(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, fastKey1: {}, fastKey2: {}, fastKey3: {}, fastKey4: {}, grpId: {}, exec TIME: {} ms.", this.getClass(), EIN, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId, p.executeTime());
//	}
	
	
	
	private static final String SQL_ADD_NEW_COMP =
			"insert into company (EIN, name, area_id, logo_url, background_url, group_id) values (?, ?, ?, ?, ?, ?)";
	
	private static final String SQL_ADD_FAST_APP =
			"insert into fast_app (type, app_id, EIN, sort) values (?, ?, ?, ?)";
	
	private static final String SQL_DELETE_FAST_APP =
			"delete from fast_app where type = ? and EIN = ?";
	

	private static final String SQL_UPDATE_COMP_INFO =
			"update company set name = ?, area_id = ?, logo_url = ?, background_url = ?, group_id = ? where EIN = ?";
	
	public void update(String EIN, String name, String areaId, String logo, String bg, String grpId) {
		Profiler p = new Profiler();
		log.trace("START: {}.update(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, grpId: {}", this.getClass(), EIN, name, areaId, logo, bg, grpId);
		super.executeUpateQuery(SQL_UPDATE_COMP_INFO, name, areaId, logo, bg, grpId, EIN);
		log.info("END: {}.update(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, grpId: {}, exec TIME: {} ms.", this.getClass(), EIN, name, areaId, logo, bg, grpId, p.executeTime());
	}
	
	public void saveFastApp(int type, String EIN, String...appIds) {
		Profiler p = new Profiler();
		log.trace("START: {}.saveFastApp(), type: {}, EIN: {}, appIds: {}", this.getClass(), type, EIN, appIds);
		int i = 1;
		for(String appId : appIds) {
			super.executeUpateQuery(SQL_ADD_FAST_APP, type, appId, EIN, i);
			i++;
		}
		log.info("END: {}.saveFastApp(), type: {}, EIN: {}, appIds: {}, exec TIME: {} ms.", this.getClass(), type, EIN, appIds, p.executeTime());
	}
	
	public void deleteFastApps(int type, String EIN) {
		Profiler p = new Profiler();
		log.trace("START: {}.deleteFastApps(), type: {}, EIN: {}", this.getClass(), type, EIN);
		super.executeUpateQuery(SQL_DELETE_FAST_APP, type, EIN);
		log.info("END: {}.deleteFastApps(), type: {}, EIN: {}, exec TIME: {} ms.", this.getClass(), type, EIN, p.executeTime());
	}
	
	public void save(String EIN, String name, String areaId, String logo, String bg, String grpId) {
		Profiler p = new Profiler();
		log.trace("START: {}.save(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, grpId: {}", this.getClass(), EIN, name, areaId, logo, bg, grpId);
		super.executeUpateQuery(SQL_ADD_NEW_COMP, EIN, name, areaId, logo, bg, grpId);
		log.trace("START: {}.save(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, grpId: {}, exec TIME: {} ms.", this.getClass(), EIN, name, areaId, logo, bg, grpId, p.executeTime());
	}
	
	public String getEIN(String account) {
		Profiler p = new Profiler();
		log.trace("START: {}.getEIN(), account: {}", this.getClass(), account);
		List<String> list = super.findBySql(SQL_FIND_EIN_BY_ACCOUNT, String.class, account);
		log.info("END: {}.getEIN(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
		return Util.isEmpty(list) ? "" : list.get(0);	
	}
	
	public List<CompanyEntity> findAllComp() {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllComp().", this.getClass());
		List<CompanyEntity> list = super.findBySql(SQL_FIND_ALL, CompanyEntity.class);
		log.info("END: {}.findAllComp(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		return list;
	}
	
//	private static final String SQL_ADD_NEW_COMPANY = "insert into company (company_id, name, area_id, logo_url, background_url, fast_key1, fast_key2, fast_key3, fast_key4, isEnabled) values"
//													+ "						(?, 		?, 	  ?,	   ?, 		 ?, 			 ?,			?, 		   ?, 		  ?, 		 ?)";
	
	
	
//	/**
//	 * 新增公司
//	 * @param cid 公司統編
//	 * @param name 公司名曾
//	 * @param aid 郵遞區號
//	 * @param logoURL 
//	 * @param bgURL
//	 * @param fkey1
//	 * @param fkey2
//	 * @param fkey3
//	 * @param fkey4
//	 * @param isEnabled
//	 */
//	public void addNewCompany(String cid, String name, String aid, String logoURL, String bgURL, String fkey1, String fkey2, String fkey3, String fkey4, boolean isEnabled) {
//		Profiler p = new Profiler();
//		log.trace("START: {}.addNewCompnay(), cid: {}, name: {}, aid: {}, logoURL: {}, bgURL: {}, fkey1: {}, fkey2: {}, fkey3: {}, fkey4: {}, isEnabled: {}", this.getClass(), cid, name, aid, logoURL, bgURL, fkey1, fkey2, fkey3, fkey4, isEnabled);
//		super.executeUpateQuery(SQL_ADD_NEW_COMPANY, cid, name, aid, logoURL, bgURL, fkey1, fkey2, fkey3, fkey4, isEnabled);
//		log.info("END: {}.addNewCompany(), cid: {}, name: {}, aid: {}, logoURL: {}, bgURL: {}, fkey1: {}, fkey2: {}, fkey3: {}, fkey4: {}, isEnabled: {}, exec TIME: {} ms.", this.getClass(), cid, name, aid, logoURL, bgURL, fkey1, fkey2, fkey3, fkey4, isEnabled, p.executeTime());
//	} 
}
