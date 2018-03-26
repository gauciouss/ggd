package tbox.data.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.core.db.HibernateDao;
import tbox.data.vo.FastApp;

@Repository("FastAppDao")
public class FastAppDao extends HibernateDao<FastApp, Integer> {

	private final static Logger log = LoggerFactory.getLogger(FastAppDao.class);
	
	private static final String HQL_FIND_ALL_BY_TYPE = 
			"from FastApp f "
			+ "where f.type = ? "
			+ "  and f.EIN = ? "
			+ "order by f.sort";
	
	
	/**
	 * 查詢快捷APP
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FastApp> findAllByType(int type, String EIN) {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllByType(), type: {}, EIN: {}", this.getClass(), type, EIN);
		List<FastApp> list = (List<FastApp>) super.findByHql(HQL_FIND_ALL_BY_TYPE, type, EIN);
		log.info("END: {}.findAllByType(), type: {}, EIN: {}, exec TIME: {} ms.", this.getClass(), type, EIN, p.executeTime());
		return list;
	}
}
