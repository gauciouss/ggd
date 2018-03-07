package tbox.data.dao;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.core.db.HibernateDao;
import tbox.data.vo.MachineBox;

@Repository("MachineDao")
public class MachineDao extends HibernateDao<MachineBox, Integer> {
	
	private final static Logger log = LoggerFactory.getLogger(MachineDao.class);

	private static final String HQL_FIND_BY_SN_MAC_WIFI =
			"from MachineBox m "
			+ " where m.machineSN = ? "
			+ "   and m.ethernetMAC = ? "
			+ "   and m.wifiMAC = ?";
	
	private static final String SQL_ACTIVE_MACHINE_BOX = 
			"update machine_box set isEnabled = true, start_date = ?, authorized_end_date = ? where machine_sn = ? and ethernet_mac = ? and wifi_mac = ?";
	
	private static final String SQL_UPDATE_LAST_LOGIN_INFO = 
			"update machine_box set last_login_time = ?, login_ip = ? where machine_sn = ? and ethernet_mac = ? and wifi_mac = ?";
	
	private static final String SQL_COUNT_MACHINE = "select count(*) from machine_box where machine_sn = ? and ethernet_mac = ? and wifi_mac = ?";
	
	public long recordLastLoginInfo(String sn, String mac, String wifi, String ip) {
		Profiler p = new Profiler();
		log.trace("START: {}.activeMachine(), sn: {}, mac: {}, wifi: {}, ip: {}", this.getClass(), sn, mac, wifi, ip);
		long now = System.currentTimeMillis();
		super.executeUpateQuery(SQL_UPDATE_LAST_LOGIN_INFO, new Timestamp(now), ip, sn, mac, wifi);
		log.info("END: {}.activeMachine(), sn: {}, mac: {}, wifi: {}, ip: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, ip, p.executeTime());
		return now;
	}
	
	public void activeMachine(String sn, String mac, String wifi, long authEndTime) {
		Profiler p = new Profiler();
		log.trace("START: {}.activeMachine(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		super.executeUpateQuery(SQL_ACTIVE_MACHINE_BOX, new Timestamp(System.currentTimeMillis()), new Timestamp(authEndTime), sn, mac, wifi);
		log.info("END: {}.activeMachine(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
	}
	
	@SuppressWarnings("unchecked")
	public List<MachineBox> findBy(String sn, String mac, String wifi) {
		Profiler p = new Profiler();
		log.trace("START: {}.findBy(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		List<MachineBox> list = (List<MachineBox>) super.findByHql(HQL_FIND_BY_SN_MAC_WIFI, sn, mac, wifi);
		log.info("END: {}.findBy(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		return list;
	}
	
	public boolean isLegitimateMachine(String sn, String mac, String wifi) {
		Profiler p = new Profiler();
		log.trace("START: {}.findBy(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		List<Integer> list = (List<Integer>) super.findBySql(SQL_COUNT_MACHINE, Integer.class, sn, mac, wifi);
		int count = list.get(0);
		log.debug("sn: {}, mac: {}, wifi: {}, count: {}", sn, mac, wifi, count);
		log.info("END: {}.findBy(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		return count == 1;
	}
}
