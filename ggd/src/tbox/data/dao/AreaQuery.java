package tbox.data.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.core.db.HibernateQuery;

@Repository("AreaQuery")
public class AreaQuery extends HibernateQuery {

	
	private static final String SQL_QUERY_CWB_CODE = 
			"select distinct b.cwb_code " + 
			"	from area b  " + 
			"    inner join machine_box m on m.area_id = b.area_id " + 
			"    where m.machine_sn = ? "; 
			//"      and m.ethernet_mac = ? " + 
			//"      and m.wifi_mac = ?";
	
	/**
	 * 查詢中央氣象局API代碼
	 * @param sn 機器序號
	 * @param mac 網卡序號
	 * @param wifi wifi 網卡序號 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findCWBCode(String sn) {
		Profiler p = new Profiler();
		log.trace("START: {}.findCWBCode(), sn: {}", this.getClass());
		//String code = ((List<String>) super.findBySql(SQL_QUERY_CWB_CODE, sn, mac, wifi)).get(0);
		String code = ((List<String>) super.findBySql(SQL_QUERY_CWB_CODE, sn)).get(0);
		log.debug("sn: {}, cwbCode: {}", sn, code);
		log.info("END: {}.findCWBCode(), sn: {}, exec TIME: {} ms.", this.getClass(), sn, p.executeTime());
		return code;
	}
}
