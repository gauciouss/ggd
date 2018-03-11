package tbox.data.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.core.db.HibernateQuery;
import tbox.data.vo.AppEntity;

@Repository("AppQuery")
public class AppQuery extends HibernateQuery {
	
	private final static Logger log = LoggerFactory.getLogger(AppQuery.class);
	
	/**
	 * 查詢商城首頁APP
	 */
	private static final String SQL_FIND_APPS_BY_MACHINE =
			"select a.app_id, a.app_name, a.icon_path, a.pkg_name, ver.version, ver.publish_time, ver.link, clz.clz_id, clz.clz_name  " + 
			"	from (  " + 
			"		select app.app_id, app_name, app.clz_id, app.icon_path, app.pkg_name, max(ver1.version) version  " + 
			"			from app app  " + 
			"			join app_version ver1 on app.app_id = ver1.app_id      " + 
			"			group by app.app_id, app.app_name) a  " + 
			"	inner join app_version ver on (a.app_id = ver.app_id and a.version = ver.version)  " + 
			"	inner join app_clz clz on a.clz_id = clz.clz_id  " + 
			"	inner join comp_app_mapping cam on a.app_id = cam.app_id      " + 
			"where cam.EIN = ?    ";
	
	
	
	private static final String SQL_APP_MAX_VERSION = 
			"select max(version) " + 
			"	from app_version " + 
			"where app_id = ?";
	
	
	private static final String SQL_APP_INFO = 
			"select app.app_id, app.icon_path, app.clz_id, clz.clz_name, app.app_name, ver.link, ver.version, ver.publish_time, app.pkg_name " + 
			"	from app app " + 
			"    inner join app_clz clz on app.clz_id = clz.clz_id " + 
			"    inner join app_version ver on app.app_id = ver.app_id " + 
			"    where app.app_id = ? " + 
			"    and ver.version = ?";
	
	
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
