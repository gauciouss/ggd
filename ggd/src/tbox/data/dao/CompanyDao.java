package tbox.data.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.core.db.HibernateDao;
import tbox.data.vo.Company;

@Repository("CompanyDao")
public class CompanyDao extends HibernateDao<Company, String> {
	
	private final static Logger log = LoggerFactory.getLogger(CompanyDao.class);
	
	private static final String SQL_ADD_NEW_COMPANY = "insert into company (company_id, name, area_id, logo_url, background_url, fast_key1, fast_key2, fast_key3, fast_key4, isEnabled) values"
													+ "						(?, 		?, 	  ?,	   ?, 		 ?, 			 ?,			?, 		   ?, 		  ?, 		 ?)";
	
	/**
	 * 新增公司
	 * @param cid 公司統編
	 * @param name 公司名曾
	 * @param aid 郵遞區號
	 * @param logoURL 
	 * @param bgURL
	 * @param fkey1
	 * @param fkey2
	 * @param fkey3
	 * @param fkey4
	 * @param isEnabled
	 */
	public void addNewCompany(String cid, String name, String aid, String logoURL, String bgURL, String fkey1, String fkey2, String fkey3, String fkey4, boolean isEnabled) {
		Profiler p = new Profiler();
		log.trace("START: {}.addNewCompnay(), cid: {}, name: {}, aid: {}, logoURL: {}, bgURL: {}, fkey1: {}, fkey2: {}, fkey3: {}, fkey4: {}, isEnabled: {}", this.getClass(), cid, name, aid, logoURL, bgURL, fkey1, fkey2, fkey3, fkey4, isEnabled);
		super.executeUpateQuery(SQL_ADD_NEW_COMPANY, cid, name, aid, logoURL, bgURL, fkey1, fkey2, fkey3, fkey4, isEnabled);
		log.info("END: {}.addNewCompany(), cid: {}, name: {}, aid: {}, logoURL: {}, bgURL: {}, fkey1: {}, fkey2: {}, fkey3: {}, fkey4: {}, isEnabled: {}, exec TIME: {} ms.", this.getClass(), cid, name, aid, logoURL, bgURL, fkey1, fkey2, fkey3, fkey4, isEnabled, p.executeTime());
	} 
}
