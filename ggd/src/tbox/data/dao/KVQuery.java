package tbox.data.dao;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import baytony.util.Util;
import ggd.core.db.HibernateQuery;
import tbox.data.vo.KVEntity;

@Repository("KVQuery")
public class KVQuery extends HibernateQuery {
	
	private final static Logger log = LoggerFactory.getLogger(KVQuery.class);
	
	private static final String SQL_FIND_ALL_KV_BY_ALL = 
			"select distinct kv.kv_serial_no, kind.kind_name kind, kv.name name, kv.img_path, kv.click_link, kv.msg, map.start_date, map.end_date, map.isEnabled "
			+ " from kv kv "
			+ " inner join kv_kind kind on kv.kind = kind.kind "
			+ " inner join kv_comp_mapping map on kv.kv_serial_no = map.kv_serial_no "
			+ " inner join company c on map.EIN = c.EIN "
			+ " inner join adm_user au on au.group_id = c.group_id"
			+ " where 1 = 1 ";
	
	private static final String SQL_FIND_ALL_KV_BY_COMP = SQL_FIND_ALL_KV_BY_ALL + "   and c.EIN = ? ";
	
	private static final String SQL_FIND_ALL_KV_BY_ACCOUNT = SQL_FIND_ALL_KV_BY_ALL + "   and au.account = ?";
	
	private static final String SQL_ADD_NEW_KV = "insert into kv (kind, img_path, click_link, msg, create_date, create_user, name) values (?, ?, ?, ?, ?, ?, ?)"; 
	
	private static final String SQL_UPDATE_KV = "update kv set img_path = ?, click_link = ?, msg = ?, kind = ?, update_date = ?, update_user = ?, name = ? where kv_serial_no = ?";
	
	private static final String SQL_FIND_KV_BY_MACHINE = 
			"select kv.kv_serial_no, kv.kind, kv.name, kv.img_path, kv.click_link, kv.msg, kcm.start_date, kcm.end_date, kcm.isEnabled isEnabled" + 
			"	from kv kv  " + 
			"    inner join kv_comp_mapping kcm on kv.kv_serial_no = kcm.kv_serial_no " + 
			"    inner join machine_box box on box.EIN = kcm.EIN " + 
			"    where kcm.isEnabled = true " +
			"      and kcm.isApproved = true " +
			"      and kv.kind = ? " +
			"      and box.machine_sn = ? " + 
			"      and box.ethernet_mac = ? " + 
			"      and box.wifi_mac = ? " +
			"      and ? between kcm.start_date and kcm.end_date";
	
	
	private static final String SQL_FIND_KV_BY_ID = 
			"select kv.kv_serial_no, kv.kind, kv.name, kv.img_path, kv.click_link, kv.msg, kcm.start_date, kcm.end_date, kcm.isEnabled isEnabled" + 
			"	from kv kv  " + 
			"    inner join kv_comp_mapping kcm on kv.kv_serial_no = kcm.kv_serial_no " + 
			"    inner join machine_box box on box.EIN = kcm.EIN " + 
			"    where kv.kv_serial_no = ? ";
	
	
	private static final String SQL_FIND_MAX_SERIAL =
			"select max(kv_serial_no) from kv";
	
	private static final String SQL_DEL_KV_PUBLISHER =
			"delete from kv_comp_mapping where kv_serial_no = ?";
	
	private static final String SQL_ADD_KV_PUBLISHER =
			"insert into kv_comp_mapping (EIN, kv_serial_no, start_date, end_date, isEnabled, isApproved) values (?, ?, ?, ?, ?, ?)";	
	
	
	
	public void deleteAllKV_Comp(int kvSerialNo) {
		Profiler p = new Profiler();
		log.trace("START: {}.deleteAllKV_Comp(), kvSerialNo: {}", this.getClass(), kvSerialNo);
		super.executeUpateQuery(SQL_DEL_KV_PUBLISHER, kvSerialNo);
		log.trace("END: {}.deleteAllKV_Comp(), kvSerialNo: {}, exec TIME: {} ms.", this.getClass(), kvSerialNo, p.executeTime());
	}
	
	/**
	 * 根據KVID查詢
	 * @param serialNo
	 * @return
	 */
	public KVEntity findKVById(int serialNo) {
		Profiler p = new Profiler();
		log.trace("START: {}.findKVById(), serialNo: {}", this.getClass(), serialNo);
		KVEntity entity = super.findBySql(SQL_FIND_KV_BY_ID, KVEntity.class, serialNo).get(0);
		log.info("END: {}.findKVById(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo, p.executeTime());
		return entity;
	}
	
	/**
	 * 查詢所有訊息
	 * @param sn
	 * @param mac
	 * @param wifi
	 * @param kind
	 * @return
	 */
	public List<KVEntity> findAllKVByMachine(String sn, String mac, String wifi, int kind) {
		Profiler p = new Profiler();
		log.trace("START: {}.getKVByMachine(), sn: {}, mac: {}, wifi: {}, kind: {}", this.getClass(), sn, mac, wifi, kind);
		List<KVEntity> kvs = super.findBySql(SQL_FIND_KV_BY_MACHINE, KVEntity.class, kind, sn, mac, wifi, new Timestamp(System.currentTimeMillis()));
		log.info("END: {}.getKVByMachine(), sn: {}, mac: {}, wifi: {}, kind: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, kind, p.executeTime());
		return kvs;
	}
	
	/**
	 * 更新廣告資訊
	 * @param serialNo
	 * @param kind
	 * @param imgPath
	 * @param clickLink
	 * @param msg
	 * @param user
	 */
	public void updateKV(int serialNo, int kind, String imgPath, String clickLink, String msg, String user, String kvName) {
		Profiler p = new Profiler();
		log.trace("START: {}.updateKV(), kind: {}, imgPath: {}, clickLink: {}, msg: {}, user: {}, serialNo: {}", this.getClass(), kind, imgPath, clickLink, msg, user, serialNo);
		super.executeUpateQuery(SQL_UPDATE_KV, imgPath, clickLink, msg, kind, new Timestamp(System.currentTimeMillis()), user, kvName, serialNo);
		log.info("END: {}.updateKV(), kind: {}, imgPath: {}, clickLink: {}, msg: {}, user: {}, serialNo: {}, exec TIME: {} ms.", this.getClass(), kind, imgPath, clickLink, msg, user, serialNo, p.executeTime());
	}
	
	/**
	 * 新增廣告
	 * @param kind
	 * @param imgPath
	 * @param clickLink
	 * @param msg
	 * @param user
	 * @param kvName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addNewKV(int kind, String imgPath, String clickLink, String msg, String user, String kvName) {
		Profiler p = new Profiler();
		log.trace("START: {}.addNewKV(), kind: {}, imgPath: {}, clickLink: {}, msg: {}, user: {}", this.getClass(), kind, imgPath, clickLink, msg, user);
		super.executeUpateQuery(SQL_ADD_NEW_KV, kind, imgPath, clickLink, msg, new Timestamp(System.currentTimeMillis()), user, kvName);
		List<Integer> list = (List<Integer>) super.findBySql(SQL_FIND_MAX_SERIAL);
		int s = Util.isEmpty(list) ? 1 :list.get(0);				
		log.info("END: {}.addNewKV(), kind: {}, imgPath: {}, clickLink: {}, msg: {}, user: {}, exec TIME: {} ms.", this.getClass(), kind, imgPath, clickLink, msg, user, p.executeTime());
		return s;
	}
	
	/**
	 * 新增發布對象
	 * @param EIN
	 * @param kvSerial
	 * @param start
	 * @param end
	 * @param isEnabled
	 * @param isApproved
	 */
	public void addKVPublisher(String EIN, int kvSerial, Timestamp start, Timestamp end, boolean isEnabled, boolean isApproved) {
		Profiler p = new Profiler();
		log.trace("START: {}.addKVPublisher(), EIN: {}, kvSerial: {}, start: {}, end: {}, isEnabled: {}, isApproved: {}", this.getClass(), EIN, kvSerial, start, end, isEnabled, isApproved);
		super.executeUpateQuery(SQL_ADD_KV_PUBLISHER, EIN, kvSerial, start, end, isEnabled, isApproved);
		log.info("END: {}.addKVPublisher(), EIN: {}, kvSerial: {}, start: {}, end: {}, isEnabled: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), EIN, kvSerial, start, end, isEnabled, isApproved, p.executeTime());
	}
	
	
	/**
	 * 查詢所有廣告
	 * @param kind
	 * @return
	 */
	public List<KVEntity> findAll(int kind) {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllByComp(), EIN: {}, kind: {}", this.getClass(), kind);
		List<KVEntity> list = null;
		if(kind == 0) {
			list = super.findBySql(SQL_FIND_ALL_KV_BY_ALL, KVEntity.class);
		}
		else {
			String sql = SQL_FIND_ALL_KV_BY_ALL + " and kv.kind = ?";
			list = super.findBySql(sql, KVEntity.class, kind);
		}
		log.info("END: {}.findAllByComp(), EIN: {}, kind: {}, exec TIME: {} ms.", this.getClass(), kind, p.executeTime());
		return list;
	}

	/**
	 * 查詢所有廣告
	 * @param EIN
	 * @param kind
	 * @return
	 */
	public List<KVEntity> findAllByComp(String EIN, int kind) {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllByComp(), EIN: {}, kind: {}", this.getClass(), EIN, kind);
		List<KVEntity> list = null;
		if(kind == 0) {
			list = super.findBySql(SQL_FIND_ALL_KV_BY_COMP, KVEntity.class, EIN);
		}
		else {
			String sql = SQL_FIND_ALL_KV_BY_COMP + " and kv.kind = ?";
			list = super.findBySql(sql, KVEntity.class, EIN, kind);
		}
		log.debug("{} have KVs: {}", EIN, list);
		log.info("END: {}.findAllByComp(), EIN: {}, kind: {}, exec TIME: {} ms.", this.getClass(), EIN, kind, p.executeTime());
		return list;
	}
	
	/**
	 * 查詢所有廣告
	 * @param account
	 * @param kind
	 * @return
	 */
	public List<KVEntity> findAllByAccount(String account, int kind) {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllByAccount(), account: {}, kind: {}", this.getClass(), account, kind);
		List<KVEntity> list = null;
		if(kind == 0) {
			list = super.findBySql(SQL_FIND_ALL_KV_BY_ACCOUNT, KVEntity.class, account);
		}
		else {
			String sql = SQL_FIND_ALL_KV_BY_ACCOUNT + " and kv.kind = ?";
			list = super.findBySql(sql, KVEntity.class, account, kind);
		}
		log.debug("{} have KVs: {}", account, list);
		log.info("END: {}.findAllByAccount(), account: {}, kind: {}, exec TIME: {} ms.", this.getClass(), account, kind, p.executeTime());
		return list;
	}
}
