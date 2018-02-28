package tbox.data.dao;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.core.db.HibernateQuery;
import tbox.data.vo.KVEntity;

@Repository("KVQuery")
public class KVQuery extends HibernateQuery {
	
	private final static Logger log = LoggerFactory.getLogger(KVQuery.class);
	
	private static final String SQL_FIND_ALL_KV_BY_ALL = 
			"select distinct kv.kv_serial_no, kind.kind_name kind, kv.img_path, kv.click_link, kv.msg, map.start_date, map.end_date, map.isEnabled "
			+ " from KV kv "
			+ " inner join kv_kind kind on kv.kind = kind.kind "
			+ " inner join kv_comp_mapping map on kv.kv_serial_no = map.kv_serial_no "
			+ " inner join company c on map.EIN = c.EIN "
			+ " inner join adm_user au on au.group_id = c.group_id"
			+ " where 1 = 1 ";
	
	private static final String SQL_FIND_ALL_KV_BY_COMP = SQL_FIND_ALL_KV_BY_ALL + "   and c.EIN = ? ";
	
	private static final String SQL_FIND_ALL_KV_BY_ACCOUNT = SQL_FIND_ALL_KV_BY_ALL + "   and au.account = ?";
	
	private static final String SQL_ADD_NEW_KV = "insert into kv (kind, img_path, click_link, msg, create_date, create_user) values (?, ?, ?, ?, ?, ?)"; 
	
	private static final String SQL_UPDATE_KV = "update kv set img_path = ?, click_link = ?, msg = ?, kind = ?, updaet_date = ?, update_user = ? where kv_serial_no = ?";
	
	/**
	 * 更新廣告資訊
	 * @param serialNo
	 * @param kind
	 * @param imgPath
	 * @param clickLink
	 * @param msg
	 * @param user
	 */
	public void updateKV(int serialNo, int kind, String imgPath, String clickLink, String msg, String user) {
		Profiler p = new Profiler();
		log.trace("START: {}.updateKV(), kind: {}, imgPath: {}, clickLink: {}, msg: {}, user: {}, serialNo: {}", this.getClass(), kind, imgPath, clickLink, msg, user, serialNo);
		super.executeUpateQuery(SQL_UPDATE_KV, imgPath, clickLink, msg, kind, new Timestamp(System.currentTimeMillis()), user, serialNo);
		log.info("END: {}.updateKV(), kind: {}, imgPath: {}, clickLink: {}, msg: {}, user: {}, serialNo: {}, exec TIME: {} ms.", this.getClass(), kind, imgPath, clickLink, msg, user, serialNo, p.executeTime());
	}
	
	/**
	 * 新增廣告
	 * @param kind
	 * @param imgPath
	 * @param clickLink
	 * @param msg
	 * @param user
	 */
	public void addNewKV(int kind, String imgPath, String clickLink, String msg, String user) {
		Profiler p = new Profiler();
		log.trace("START: {}.addNewKV(), kind: {}, imgPath: {}, clickLink: {}, msg: {}, user: {}", this.getClass(), kind, imgPath, clickLink, msg, user);
		super.executeUpateQuery(SQL_ADD_NEW_KV, kind, imgPath, clickLink, msg, new Timestamp(System.currentTimeMillis()), user);
		log.info("END: {}.addNewKV(), kind: {}, imgPath: {}, clickLink: {}, msg: {}, user: {}, exec TIME: {} ms.", this.getClass(), kind, imgPath, clickLink, msg, user, p.executeTime());
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
