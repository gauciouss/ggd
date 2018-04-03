package tbox.data.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.core.db.HibernateDao;
import tbox.data.vo.KV;

@Repository("KVDao")
public class KVDao extends HibernateDao<KV, Integer> {

	private final static Logger log = LoggerFactory.getLogger(KVDao.class);
	
	private static final String SQL_DELETE_KV = "delete from kv where kv_serial_no = ?";
	private static final String SQL_DELETE_KV_MAPPING = "delete from kv_comp_mapping where kv_serial_no = ?";
	
	public void delete(Integer serialNo) {
		Profiler p = new Profiler();
		log.trace("START: {}.delete(), serialNo: {}", this.getClass(), serialNo);
		super.executeUpateQuery(SQL_DELETE_KV_MAPPING, serialNo);
		super.executeUpateQuery(SQL_DELETE_KV, serialNo);
		log.info("END: {}.delete(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo);
	}
}
