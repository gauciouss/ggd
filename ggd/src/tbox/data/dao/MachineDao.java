package tbox.data.dao;

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
	
	
	private static final String SQL_FIND_EIN_BY_MACHINE = 
			"select EIN "
			+ " from machine_box m "
			+ " where m.machine_sn = ? "
			+ "   and m.ethernet_mac = ? "
			+ "   and m.wifi_mac = ?";
	

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
	
	
	private static final String SQL_ADD_NEW_MACHINE = 
			"insert into machine_box (machine_sn, wifi_mac, ethernet_mac, area_id, EIN, start_date, authorized_start_date, authorized_end_date) values (?, ?, ?, ?, ?, ?, ?, ?)";
	
	/**
	 * 新增新的機上盒
	 * @param machineSN
	 * @param wifiMac
	 * @param mac
	 * @param areaId
	 * @param EIN
	 * @param start
	 * @param end
	 * @return
	 */
	public int addNewMachine(String machineSN, String wifiMac, String mac, int areaId, String EIN, Timestamp start, Timestamp end) {
		Profiler p = new Profiler();
		log.trace("START: {}.addNewMachine(), machineSN: {}, wifiMac: {}, mac: {}, areaId: {}, EIN: {}, start: {}, end: {}", this.getClass(), machineSN, wifiMac, mac, areaId, EIN, start, end);
		int i = super.executeUpateQuery(SQL_ADD_NEW_MACHINE, machineSN, wifiMac, mac, areaId, EIN, start, start, end);
		log.info("END: {}.addNewMachine(), machineSN: {}, wifiMac: {}, mac: {}, areaId: {}, EIN: {}, start: {}, end: {}, exec TIME: {} ms.", this.getClass(), machineSN, wifiMac, mac, areaId, EIN, start, end, p.executeTime());
		return i;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> findEINByMachine(String sn, String mac, String wifi) {
		Profiler p = new Profiler();
		log.trace("START: {}.activeMachine(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		List<String> list = (List<String>) super.findBySql(SQL_FIND_EIN_BY_MACHINE, sn, mac, wifi);
		log.info("END: {}.activeMachine(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		return list;
	}
	
	
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
