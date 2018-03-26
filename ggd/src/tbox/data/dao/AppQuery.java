package tbox.data.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import baytony.util.Util;
import ggd.core.common.Constant;
import ggd.core.db.HibernateQuery;
import tbox.data.vo.AppEntity;
import tbox.data.vo.AppVersion;

@Repository("AppQuery")
public class AppQuery extends HibernateQuery {
	
	private final static Logger log = LoggerFactory.getLogger(AppQuery.class);
	
	private static final String SQL_MAX_APP_ID =
			"select max(app_id) from app";
	
	/**
	 * 查詢商城首頁APP
	 */
	private static final String SQL_FIND_APPS_BY_MACHINE =
			"select distinct a.app_id, a.app_name, a.app_eng_name, a.app_desc, a.icon_path, a.pkg_name, ver.version, ver.publish_time, ver.link, clz.clz_id, clz.clz_name  " + 
			"	from (  " + 
			"		select app.app_id, app_name, app_eng_name, app_desc, app.clz_id, app.icon_path, app.pkg_name, max(ver1.version) version  " + 
			"			from app app  " + 
			"			join app_version ver1 on app.app_id = ver1.app_id      " + 
			"			group by app.app_id, app.app_name) a  " + 
			"	inner join app_version ver on (a.app_id = ver.app_id and a.version = ver.version)  " + 
			"	inner join app_clz clz on a.clz_id = clz.clz_id  " + 
			"	inner join comp_app_mapping cam on a.app_id = cam.app_id      " + 
			"where cam.EIN = ?    ";
	
	
	/**
	 * 查詢APP last version
	 */
	private static final String SQL_APP_MAX_VERSION = 
			"select max(version) " + 
			"	from app_version " + 
			"where app_id = ?";
	
	
	/**
	 * 查詢APP的資訊
	 */
	private static final String SQL_APP_INFO = 
			"select app.app_id, app.icon_path, app.clz_id, clz.clz_name, app.app_name, app.app_eng_name, app.app_desc, ver.link, ver.version, ver.publish_time, app.pkg_name " + 
			"	from app app " + 
			"    inner join app_clz clz on app.clz_id = clz.clz_id " + 
			"    inner join app_version ver on app.app_id = ver.app_id " + 
			"    where app.app_id = ? " + 
			"    and ver.version = ?";
	
	
	/**
	 * 查詢APP的資訊
	 */
	private static final String SQL_APP_INFO2 = 
			"select app.app_id, app.icon_path, app.clz_id, clz.clz_name, app.app_name, app.app_eng_name, app.app_desc, ver.link, ver.version, ver.publish_time, app.pkg_name " + 
			"	from app app " + 
			"	inner join app_clz clz on app.clz_id = clz.clz_id " + 
			"	inner join (" + 
			"		select ver1.app_id, ver1.ver version, ver2.link, ver2.publish_time " + 
			"			from (select app_id, max(version) ver from app_version group by app_id) ver1 " + 
			"			inner join (select app_id, link, publish_time, version from app_version) ver2 on ver1.app_id = ver2.app_id and ver1.ver = ver2.version) ver " + 
			"	on app.app_id = ver.app_id " + 
			"	where app.app_id = ?";
	
	
	
	
	/**
	 * 查詢所有APP
	 */
	private static final String SQL_FIND_ALL_APP = 
			"select a.app_id, a.app_name, a.app_eng_name, a.icon_path, a.pkg_name, a.app_desc, ver.version, ver.publish_time, ver.link, clz.clz_id, clz.clz_name    " + 
			"				from (    " + 
			"					select app.app_id, app_name, app_eng_name, app_desc, app.clz_id, app.icon_path, app.pkg_name, max(ver1.version) version    " + 
			"						from app app    " + 
			"						join app_version ver1 on app.app_id = ver1.app_id        " + 
			"						group by app.app_id, app.app_name, app.app_eng_name, app.app_desc) a    " + 
			"				inner join app_version ver on (a.app_id = ver.app_id and a.version = ver.version)    " + 
			"				inner join app_clz clz on a.clz_id = clz.clz_id    " + 
			"			 order by ver.publish_time desc, a.app_id desc;";
	
	
	
	private static final String SQL_DELETE_BOOKING_APP = "delete from app where app_id = ?";
	
	
	private static final String SQL_UPDATE_APP_INFO = 
			"update app set app_name = ?, app_eng_name = ?, clz_id = ?, icon_path = ?, app_desc = ?, pkg_name = ? where app_id = ?";
	
	
	private static final String SQL_ADD_APP_VERSION =
			"insert into app_version (app_id, version, publish_time, link) values (?, ?, ?, ?)";
	
	
	private static final String SQL_BOOKING_APP =
			"insert into app (app_id) values (?)";
	
	
	private static final String SQL_HAS_APP_EXIST_BY_PKG =
			"select count(*) from app where pkg_name = ?";
	
	
	private static final String SQL_FIND_VERSION_BY_PK =
			"select * from app_version where app_id = ? and version = ?";
	
	
	private static final String SQL_UPDATE_VERSION_INFO = 
			"";
	
	
	public AppVersion getVersion(String appId, String version) {
		Profiler p = new Profiler();
		log.trace("START: {}.getVersion(), appId: {}, version: {}", this.getClass(), appId, version);
		List<AppVersion> list = super.findBySql(SQL_FIND_VERSION_BY_PK, AppVersion.class, appId, version);
		AppVersion entity = Util.isEmpty(list) ? null : list.get(0);
		log.debug("appId: {}, version: {}, entity: {}", appId, version, entity);
		log.info("END: {}.getVersion(), appId: {}, version: {}, exec TIME: {} ms.", this.getClass(), appId, version, p.executeTime());
		return entity;
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean isAppExistByPkgName(String pkgName) {
		Profiler p = new Profiler();
		log.trace("START: {}.getAppByPkgName(), pkgName: {}", this.getClass(), pkgName);
		BigInteger count = ((List<BigInteger>) super.findBySql(SQL_HAS_APP_EXIST_BY_PKG, pkgName)).get(0);
		log.debug("pkgName: {}, count: {}", pkgName, count);
		log.info("END: {}.getAppByPkgName(), pkgName: {}, exec TIME: {} ms.", this.getClass(), pkgName, p.executeTime());		
		return count.intValue() != 0;
	}

	
	public int updateAppInfo(String appId, String appName, String appEngName, int clzId, String iconPath, String appDesc, String pkgName) {
		Profiler p = new Profiler();
		log.trace("START: {}.updateAppInfo(), appId: {}, appName: {}, appEngName: {}, clzId: {}, iconPath: {}, appDesc: {}, pkgName: {}", this.getClass(), appId, appName, appEngName, clzId, iconPath, appDesc, pkgName);
		int result = super.executeUpateQuery(SQL_UPDATE_APP_INFO, appName, appEngName, clzId, iconPath, appDesc, pkgName, appId);
		log.debug("update success ? {}", result == 1);		
		log.info("START: {}.updateAppInfo(), appId: {}, appName: {}, appEngName: {}, clzId: {}, iconPath: {}, appDesc: {}, pkgName: {}, exec TIME: {} ms.", this.getClass(), appId, appName, appEngName, clzId, iconPath, appDesc, pkgName, p.executeTime());
		return result;
	}
	
	public int saveAppVersion(String appId, String version, Timestamp publishTime, String link) {
		Profiler p = new Profiler();
		log.trace("START: {}.saveAppVersion(), appId: {}, version: {}, publishTime: {}, link: {}", this.getClass(), appId, version, publishTime, link);
		int result = super.executeUpateQuery(SQL_ADD_APP_VERSION, appId, version, publishTime, link);
		log.debug("add version success ? {}", result == 1);
		log.info("START: {}.saveAppVersion(), appId: {}, version: {}, publishTime: {}, link: {}, exec TIME: {} ms.", this.getClass(), appId, version, publishTime, link, p.executeTime());
		return result;
	}
	
	
	public void deleteBookingApp(String appId) {
		Profiler p = new Profiler();
		log.trace("START: {}.deleteBoogingApp(), appId: {}", this.getClass(), appId);
		super.executeUpateQuery(SQL_DELETE_BOOKING_APP, appId);
		log.info("END: {}.deleteBookingApp(), appId: {}, exec TIME: {} ms.", this.getClass(), appId, p.executeTime());
	}
	
	
	public AppEntity getNewestApp(String appId) {
		Profiler p = new Profiler();
		log.trace("START: {}.getNewestApp(), appId: {}", this.getClass(), appId);
		AppEntity entity = null;
		List<AppEntity> list = super.findBySql(SQL_APP_INFO2, AppEntity.class, appId);
		entity = Util.isEmpty(list) ? null : list.get(0);
		log.info("END: {}.getNewestApp(), appId: {}, exec TIME: {} ms.", this.getClass(), appId, p.executeTime());
		return entity;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public String bookingApp() {
		List<String> list = (List<String>) super.findBySql(SQL_MAX_APP_ID);
		String appId = Constant.EMPTY;
		if(Util.isEmpty(list)) {
			//return "APP0000001";
			appId = "APP0000001";
		}
		else {
			String max = list.get(0);
			int nid = Integer.parseInt(max.substring(3)) + 1;
			String str = String.valueOf(nid);
			while(str.length() < 7) {
				str = "0" + str;
			}
			str = "APP" + str;
			appId = str;
		}
		super.executeUpateQuery(SQL_BOOKING_APP, appId);
		return appId;
	}
	
	
	public List<AppEntity> getAllApps() {
		Profiler p = new Profiler();
		log.trace("START: {}.getAppsWithLastVersion()", this.getClass());
		List<AppEntity> list = super.findBySql(SQL_FIND_ALL_APP, AppEntity.class);
		log.info("END: {}.getAppsWithLastVersion(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		return list;
	}
	
	
	public List<AppEntity> getAppsWithLastVersion(String EIN) {
		Profiler p = new Profiler();
		log.trace("START: {}.getAppsWithLastVersion(), EIN: {}", this.getClass(), EIN);
		List<AppEntity> list = super.findBySql(SQL_FIND_APPS_BY_MACHINE, AppEntity.class, EIN);
		log.info("END: {}.getAppsWithLastVersion(), EIN: {}, exec TIME: {} ms.", this.getClass(), EIN, p.executeTime());
		return list;
	}
	
	
	public AppEntity getApp(String appId, String ver) {
		Profiler p = new Profiler();
		log.trace("START: {}.getApp(), appId: {}, ver: {}", this.getClass(), appId, ver);
		AppEntity entity = super.findBySql(SQL_APP_INFO, AppEntity.class, appId, ver).get(0);
		log.debug("entity: {}, appId: {}, ver: {}", entity, appId, ver);
		log.info("END: {}.getApp(), appId: {}, ver: {}, exec TIME: {} ms.", this.getClass(), appId, ver, p.executeTime());
		return entity;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getAppLastVersion(String appId) {
		Profiler p = new Profiler();
		log.trace("START: {}.getAppMaxVersion(), appId: {}", this.getClass(), appId);
		List<String> list = (List<String>) super.findBySql(SQL_APP_MAX_VERSION, appId);
		String ver = list.get(0);
		log.debug("app: {} -> max ver: {}", appId, ver);
		log.info("END: {}.getAppMaxVersion(), appId: {}, exec TIME: {} ms.", this.getClass(), appId, p.executeTime());
		return ver;
	}
	
}
