package tbox.data.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import baytony.util.Util;
import ggd.core.db.HibernateDao;
import tbox.data.vo.OSVersion;

@Repository("OSVersionDao")
public class OSVersionDao extends HibernateDao<OSVersion, Integer> {
	
	private final static Logger log = LoggerFactory.getLogger(OSVersionDao.class);
	
	private static final String HQL_ALL_VERSION = "from OSVersion order by publishTime desc";
	
	private static final String HQL_DELETE_VERSION = "delete from OSVersion v where v.serialNo = ?";
	
	public void deleteVersion(int serialNo) {
		Profiler p = new Profiler();
		log.trace("{}.deleteVersion(), serialNo: {}.", this.getClass(), serialNo);
		super.executeUpateQuery(HQL_DELETE_VERSION, serialNo);
		log.info("{}.deleteVersion(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo, p.executeTime());
	}
	
	public OSVersion findLastVersion() {
		Profiler p = new Profiler();
		log.trace("START: {}.findLastVersion().");
		List<OSVersion> list = this.findAll();		
		OSVersion version = Util.isEmpty(list) ? null : list.get(0);
		log.debug("last version: {}", version);
		log.info("END: {}.findLastVersion(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		return version;
	}

	@SuppressWarnings("unchecked")
	public List<OSVersion> findAll() {
		return (List<OSVersion>) super.findByHql(HQL_ALL_VERSION);
	}
}
