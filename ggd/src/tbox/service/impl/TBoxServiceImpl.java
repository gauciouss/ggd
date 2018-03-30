package tbox.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import baytony.util.Util;
import ggd.auth.vo.AdmGroup;
import ggd.core.common.Constant;
import ggd.core.util.StandardUtil;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.Icon;
import tbox.TBoxException;
import tbox.core.TBoxCodeMsg;
import tbox.core.TBoxConstant;
import tbox.data.dao.AppClzDao;
import tbox.data.dao.AppDao;
import tbox.data.dao.AppQuery;
import tbox.data.dao.AreaDao;
import tbox.data.dao.AreaQuery;
import tbox.data.dao.CompanyDao;
import tbox.data.dao.FastAppDao;
import tbox.data.dao.KVDao;
import tbox.data.dao.KVKindDao;
import tbox.data.dao.KVQuery;
import tbox.data.dao.MachineDao;
import tbox.data.dao.MachineQuery;
import tbox.data.dao.OSVersionDao;
import tbox.data.vo.App;
import tbox.data.vo.AppClz;
import tbox.data.vo.AppEntity;
import tbox.data.vo.AppVersion;
import tbox.data.vo.Area;
import tbox.data.vo.Company;
import tbox.data.vo.CompanyEntity;
import tbox.data.vo.FastApp;
import tbox.data.vo.KV;
import tbox.data.vo.KVEntity;
import tbox.data.vo.KVKind;
import tbox.data.vo.MachineBox;
import tbox.data.vo.MachineEntity;
import tbox.data.vo.OSVersion;
import tbox.proxy.cwb.gov.tw.OpendataAPI;
import tbox.proxy.cwb.gov.tw.OpendataAPI.Entity;
import tbox.service.TBoxService;
import tbox.service.entity.ApkInfoEntity;

@Service("TBoxService")
public class TBoxServiceImpl implements TBoxService {
	
	private final static Logger log = LoggerFactory.getLogger(TBoxServiceImpl.class);
	
	@Autowired
	@Qualifier("CompanyDao")
	private CompanyDao compDao;
	
	
	@Autowired
	@Qualifier("AreaDao")
	private AreaDao areaDao;
	
	@Autowired
	@Qualifier("KVQuery")
	private KVQuery kvQuery;
	
	@Autowired
	@Qualifier("KVDao")
	private KVDao kvDao;
	
	@Autowired
	@Qualifier("KVKindDao")
	private KVKindDao kvKindDao;
	
	@Autowired
	@Qualifier("MachineDao")
	private MachineDao machineDao;
	
	@Autowired
	@Qualifier("CWBOpendataAPI")
	private OpendataAPI cwbAPI;
	
	@Autowired
	@Qualifier("AreaQuery")
	private AreaQuery areaQuery;
	
	@Autowired
	@Qualifier("AppQuery")
	private AppQuery appQuery;
	
	@Autowired
	@Qualifier("AppClzDao")
	private AppClzDao appClzDao;
	
	@Autowired
	@Qualifier("AppDao")
	private AppDao appDao;
	
	@Autowired
	@Qualifier("FILE_PHYSICAL_PATH")
	private String physicalPath;
	
	@Autowired
	@Qualifier("MachineQuery")
	private MachineQuery machineQuery;
	
	@Autowired
	@Qualifier("FastAppDao")
	private FastAppDao fastAppDao;
	
	@Autowired
	@Qualifier("OSVersionDao")
	private OSVersionDao osvDao;
	
	
	
	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#deleteApp(java.lang.String)
	 */
	@Override
	public void deleteApp(String serialNo) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.deleteApp(), serialNo: {}", this.getClass(), serialNo);
		appQuery.deleteApp(serialNo);
		log.info("END: {}.deleteApp(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo, p.executeTime());
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#getOSVerion(int)
	 */
	@Override
	public OSVersion getOSVerion(int serialNo) throws TBoxException {
		return osvDao.findById(serialNo);
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#getNewestOSVersion()
	 */
	@Override
	public OSVersion getNewestOSVersion() throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getNewestOSVersion().", this.getClass());
		OSVersion version = osvDao.findLastVersion();
		log.info("END: {}.getNewestOSVersion(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		return version;
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#updateOSVersion(tbox.data.vo.OSVersion)
	 */
	@Override
	public void updateOSVersion(OSVersion version) throws TBoxException {
		this.moveOSFile();
		TBoxConstant.OS_VERSION = Constant.EMPTY;
		osvDao.update(version);
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#deleteOSVersion(int)
	 */
	@Override
	public void deleteOSVersion(int serialNo) throws TBoxException {
		TBoxConstant.OS_VERSION = Constant.EMPTY;
		osvDao.deleteVersion(serialNo);
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#saveNewVersion(tbox.data.vo.OSVersion)
	 */
	@Override
	public void saveNewVersion(OSVersion version) throws TBoxException {
		this.moveOSFile();
		TBoxConstant.OS_VERSION = Constant.EMPTY;
		osvDao.save(version);
	}
	
	
	////////////////////////////////////////////////////////////
	
	
	private void moveOSFile() throws TBoxException {
		String osTempPath = physicalPath + "/os/temp/";
		File f1 = new File(osTempPath);
		if(f1.exists()) {
			try {
				FileUtils.copyDirectory(f1, new File(physicalPath + "/os/"));
				FileUtils.deleteDirectory(f1);
			}
			catch(IOException e) {
				log.error(StringUtil.getStackTraceAsString(e));
				throw new TBoxException(TBoxCodeMsg.EX_007, e);
			}
		}
	}
	
	
	
	////////////////////////////////////////////////////////////


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findAllVersion()
	 */
	@Override
	public List<OSVersion> findAllVersion() throws TBoxException {
		return osvDao.findAll();
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findControlPanelFastApp()
	 */
	@Override
	public List<FastApp> findControlPanelFastApp(String EIN) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findControlPanelFastApp(), EIN: {}", this.getClass(), EIN);
		List<FastApp> list = this.findFastApp(2, EIN);
		log.info("END: {}.findControlPanelFastApp(), EIN: {}, exec TIME: {} ms.", this.getClass(), EIN, p.executeTime());
		return list;
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findIndexFastApp()
	 */
	@Override
	public List<FastApp> findIndexFastApp(String EIN) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findIndexFastApp(), EIN: {}", this.getClass(), EIN);
		List<FastApp> list = this.findFastApp(1, EIN);
		log.info("END: {}.findIndexFastApp(), EIN: {}, exec TIME: {} ms.", this.getClass(), EIN, p.executeTime());
		return list;
	}
	
	
	private List<FastApp> findFastApp(int type, String EIN) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findFastApp(), type: {}, EIN: {}", this.getClass(), type, EIN);
		List<FastApp> list = fastAppDao.findAllByType(type, EIN);
		log.info("END: {}.findFastApp(), type: {}, EIN: {}, exec TIME: {} ms.", this.getClass(), type, EIN, p.executeTime());
		return list;
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#deleteBookingAppID(java.lang.String)
	 */
	@Override
	public void deleteBookingAppID(String appId) throws TBoxException {
		appQuery.deleteBookingApp(appId);
	}


	
	private static final String[] ICON_PATH = {"/res/mipmap-hdpi-v4/ic_launcher.png", "/res/mipmap-mdpi-v4/ic_launcher.png", "/res/mipmap-xhdpi-v4/ic_launcher.png", "/res/mipmap-xxhdpi-v4/ic_launcher.png"};
	
	@Override
	public void saveOrUpdateAppInfo(String appId, int clzId, String appName, String appEngName, String version, String pkgName, String appDesc) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.saveOrUpdateAppInfo(), appId: {}, clzId: {}, appName: {}, appEngName: {}, version: {}, pkgName: {}, appDesc: {}", this.getClass(), appId, clzId, appName, appEngName, version, pkgName , appDesc);
		String path = "/app/" + appId;
		
		String iconDapth = Constant.EMPTY;
		for(String iPath : ICON_PATH) {
			String tp = physicalPath + path + "/temp/" + iPath;
			log.debug("icon path: {}", tp);
			if(new File(tp).exists()) {
				iconDapth = path + iPath;
				break;
			}
		}
		
		if(Util.isEmpty(iconDapth)) {
			File res = new File(physicalPath + path + "/temp/res/");
			File[] list = res.listFiles();
			for(File obj : list) {
				if(obj.isDirectory() && obj.getName().indexOf("dpi-") >= 0) {
					File[] dirSub = obj.listFiles();
					for(File pic : dirSub) {
						if(pic.getName().indexOf(".png") >= 0) {
							iconDapth = path + "/res/" + obj.getName() + "/" + pic.getName();
							break;
						}
					}
					break;					
				}
			}
		}
		
		log.debug("iconDapth: {}", iconDapth);		
		
		appQuery.updateAppInfo(appId, appName, appEngName, clzId, iconDapth, appDesc, pkgName);
		
		AppVersion verEntity = appQuery.getVersion(appId, version);
		if(verEntity == null)
			appQuery.saveAppVersion(appId, version, new Timestamp(System.currentTimeMillis()), path + "/" + appName + ".apk");
		
		String tempPath = physicalPath + "/app/" + appId + "/temp/";
		String dPath = physicalPath + "/app/" + appId + "/";
		try {
			File f1 = new File(tempPath);
			File f2 = new File(dPath);
			FileUtils.copyDirectory(f1, f2);
			FileUtils.deleteDirectory(new File(tempPath));
			
			File[] fileList = f2.listFiles();
			for(File file : fileList) {
				if(file.getName().contains(".apk")) {
					file.renameTo(new File(dPath + appName + ".apk"));
					break;
				}
			}
			
		} catch (IOException e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_007);
		}
		log.info("END: {}.saveOrUpdateAppInfo(), appId: {}, clzId: {}, appName: {}, appEngName: {}, version: {}, pkgName: {}, appDesc: {}, exec TIME: {} ms.", this.getClass(), appId, clzId, appName, appEngName, version, pkgName , appDesc, p.executeTime());
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#addKV(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void addKV(int kind, String name, String imgB64, String clickLink, String msg, String createUser, Timestamp start, Timestamp end, boolean isEnabled, boolean isApproved, List<String> EINs) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.addKV(), name: {}, kind: {}, clickLink: {}, msg: {}, createUser: {}, start: {}, end: {}, isEnabled: {}, isApproved: {}, EINs: {}", this.getClass(), name, kind, clickLink, msg, createUser, start, end, isEnabled, isApproved, EINs);
		try {
			int kvSerial = 0;
			if(kind != 4) {
				String fileName = System.currentTimeMillis() + ".jpg";	
				StandardUtil.writeBase64ToFile(imgB64, physicalPath + "/kv/", fileName);
				kvSerial = kvQuery.addNewKV(kind, "kv/" + fileName, clickLink, msg, createUser, name);
			}
			else {
				kvSerial = kvQuery.addNewKV(kind, "", clickLink, msg, createUser, name);
			}
			for(String EIN : EINs) {
				kvQuery.addKVPublisher(EIN, kvSerial, start, end, isEnabled, isApproved);
			}
		}
		catch (IOException e) {	
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_007, e.getMessage());
		} 
		catch (Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_004, e.getMessage());
		}
		finally {
			log.info("END: {}.addKV(), name: {}, kind: {}, clickLink: {}, msg: {}, createUser: {}, start: {}, end: {}, isEnabled: {}, isApproved: {}, EINs: {}, exec TIME: {} ms.", this.getClass(), name, kind, clickLink, msg, createUser, start, end, isEnabled, isApproved, EINs, p.executeTime());
		}
	}
	

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#updateKV(int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.sql.Timestamp, java.sql.Timestamp, boolean, boolean, java.util.List)
	 */
	@Override
	public void updateKV(int serialNo, int kind, String name, String imgB64, String clickLink, String msg, String updateUser, Timestamp start, Timestamp end, boolean isEnabled, boolean isApproved, List<String> EINs) throws TBoxException {	
		Profiler p = new Profiler();
		log.trace("START: {}.updateKV(), serialNo: {}, kind: {}, name: {}, clickLink: {}, msg: {}, updateUser: {}, start: {}, end: {}, isEnabled: {}, isApproved: {}, EINs: {}", this.getClass(), serialNo, kind, name, clickLink, msg, updateUser, start, end, isEnabled, isApproved, EINs);
		try {
			if(kind != 4) {
				String fileName = System.currentTimeMillis() + ".jpg";	
				StandardUtil.writeBase64ToFile(imgB64, physicalPath + "/kv/", fileName);
				kvQuery.updateKV(serialNo, kind, "kv/" + fileName, clickLink, msg, updateUser, name);;
			}
			else {
				kvQuery.updateKV(serialNo, kind, "", clickLink, msg, updateUser, name);;
			}
		}
		catch (IOException e) {	
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_007, e.getMessage());
		} 
		catch (Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_004, e.getMessage());
		}
		finally {
			log.info("END: {}.updateKV(), serialNo: {}, kind: {}, name: {}, clickLink: {}, msg: {}, updateUser: {}, start: {}, end: {}, isEnabled: {}, isApproved: {}, EINs: {}, exec TIME: {} ms.", this.getClass(), serialNo, kind, name, clickLink, msg, updateUser, start, end, isEnabled, isApproved, EINs, p.executeTime());
		}
		
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#writeApk(org.apache.tomcat.util.http.fileupload.FileItem, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("resource")
	@Override
	public ApkInfoEntity saveApk2Disk(FileItem item, String appId, String apkName, boolean isTemp) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.saveApk2Disk(), appId: {}, apkName: {}", this.getClass(), appId, apkName);
		ApkInfoEntity entity = null;
		//先搬到temp區，等確定儲存時再搬到正式區
		String path = Constant.EMPTY;
		if(isTemp)
			path = physicalPath + "/app/" + appId + "/temp/";
		else 
			path = physicalPath + "/app/" + appId + "/";
		
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		File file = new File(path + apkName);
		try {
			item.write(file);
			log.debug("apk file is exist ? {}", file.exists());
			log.debug("apk path: {}", path + apkName);
			ApkFile apkFile = new ApkFile(path + apkName);
			ApkMeta meta = apkFile.getApkMeta();
			
			String pkgName = meta.getPackageName();
			String version = meta.getVersionName();
			log.debug("pkg name: {}, version: {}", pkgName, version);
			
			List<Icon> icons = apkFile.getIconFiles();
			List<String> list = new ArrayList<String>();
			for(Icon icon : icons) {
	        		String iconPath = icon.getPath();
	        		if(!iconPath.contains(".png"))
	        			continue;
	        		byte[] bs = icon.getData();
	        		File f = new File(path + iconPath);
	        		if(!f.getParentFile().exists()) {
	        			f.getParentFile().mkdirs();
	        		}
	    			FileOutputStream fos = new FileOutputStream(path + iconPath);
	    			fos.write(bs);
	    			fos.flush();
	    			fos.close();
	    			list.add(iconPath);
	        	}
			apkFile.close();
			entity = new ApkInfoEntity(meta);
			entity.setIconPath(list);
			log.debug("ApkInfoEntity: {}", entity);
			return entity;
			
//			if(appQuery.isAppExistByPkgName(pkgName, version)) {
//				apkFile.close();
//				throw new TBoxException(TBoxCodeMsg.EX_008);
//			}
//			else {
//				List<Icon> icons = apkFile.getIconFiles();
//				List<String> list = new ArrayList<String>();
//				for(Icon icon : icons) {
//		        		String iconPath = icon.getPath();
//		        		if(!iconPath.contains(".png"))
//		        			continue;
//		        		byte[] bs = icon.getData();
//		        		File f = new File(path + iconPath);
//		        		if(!f.getParentFile().exists()) {
//		        			f.getParentFile().mkdirs();
//		        		}
//		    			FileOutputStream fos = new FileOutputStream(path + iconPath);
//		    			fos.write(bs);
//		    			fos.flush();
//		    			fos.close();
//		    			list.add(iconPath);
//		        	}
//				apkFile.close();
//				entity = new ApkInfoEntity(meta);
//				entity.setIconPath(list);
//				log.debug("ApkInfoEntity: {}", entity);
//				return entity;
//			}
		} 
		catch(TBoxException e) {
			throw e;
		}
		catch(IOException e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_004, e.getMessage());
		}
		catch (Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_004, e.getMessage());
		}
		finally {
			log.info("END: {}.saveApk2Disk(), appId: {}, apkName: {}, exec TIME: {} ms.", this.getClass(), appId, apkName, p.executeTime());
		}
	}

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#getApkInfo(java.lang.String)
	 */
	@Override
	public ApkInfoEntity getApkInfo(String apkB64, String appId) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getApkInfo(), appId: {}", this.getClass(), appId);
		ApkFile apk = null;
		try {
			String absPath = physicalPath + "/app/" + appId + "/";
			String apkPath = StandardUtil.writeBase64ToFile(apkB64, absPath, appId + ".apk");
			apk = new ApkFile(apkPath);
			ApkMeta meta = apk.getApkMeta();
			ApkInfoEntity entity = new ApkInfoEntity(meta);
			List<Icon> icons = apk.getIconFiles();
			List<String> paths = new ArrayList<String>();
			for(Icon icon : icons) {
				String path = icon.getPath();
				if(path.contains("png")) {
					String n = "";
        			if(path.contains("mdpi")) {
        				n = "mdpi";
        			}
        			else if(path.contains("xxxhdpi")) {
						n = "xxxhdpi";
					}
        			else if(path.contains("xxhdpi")) {
						n = "xxhdpi";
					}
        			else if(path.contains("xhdpi")) {
						n = "xhdpi";
					}
        			else if(path.contains("hdpi")) {
        				n = "hdpi";
        			}
        			
        			byte[] data = icon.getData();
        			String pp = "app/" + appId + "/icon_" + n + ".png";
        			paths.add(pp);
        			FileOutputStream fos = new FileOutputStream(absPath + "/icon_" + n + ".png");
        			fos.write(data);
        			fos.flush();
        			fos.close();
				}
			}
			entity.setIconPath(paths);
			apk.close();
			log.info("END: {}.getApkInfo(), appId: {}, exec TIME: {} ms.", this.getClass(), appId, p.executeTime());
			return entity;
		} 
		catch (IOException e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_006);
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_004);
		}
	}

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#getNextAppId()
	 */
	@Override
	public String getNextAppId() throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getNextAppId()", this.getClass());
		String nextId = appQuery.bookingApp();
		log.info("END: {}.getNextAppId(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		return nextId;
	}

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findAppById(java.lang.String)
	 */
	@Override
	public App findAppById(String id) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findAppById(), id: {}", this.getClass(), id);
		App app = appDao.findById(id);
		log.debug("id: {}, app: {}", id, app);
		log.info("END: {}.findAppById(), id: {}, exec TIME: {} ms.", this.getClass(), id, p.executeTime());
		return app;
	}
	
	

	@Override
	public AppEntity findLastVersionApp(String id) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findLastVersionApp(), appId: {}", this.getClass(), id);
		AppEntity entity = appQuery.getNewestApp(id);
		log.debug("appId: {}, entity: {}", id, entity);
		log.info("END: {}.findLastVersionApp(), appId: {}, exec TIME: {} ms.", this.getClass(), id, p.executeTime());
		return entity;
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findAllApps(ggd.auth.vo.AdmGroup)
	 */
	@Override
	public List<AppEntity> findAllApps(AdmGroup grp) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("{}.findAllApps(), grp: {}", this.getClass(), grp);
		List<AppEntity> list = null;
		//TODO 要分出每個廠商可管的APP，admin就是全部抓
		list = appQuery.getAllApps();		
		log.info("{}.findAllApps(), grp: {}, exec TIME: {} ms.", this.getClass(), grp, p.executeTime());
		return list;
	}

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findAllAppKind()
	 */
	@Override
	public List<AppClz> findAllAppKind() throws TBoxException {
		return appClzDao.findAll();
	}
	

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findAppKindById(int)
	 */
	@Override
	public AppClz findAppKindById(int serialNo) throws TBoxException {
		return appClzDao.findById(serialNo);
	}
	
	


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#addNewAppClz(java.lang.String, java.lang.String)
	 */
	@Override
	public void addNewAppClz(String name, String base64) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.addNewAppClz(), name: {}", this.getClass(), name);
		String iconName = System.currentTimeMillis() + ".png";
		try {
			StandardUtil.writeBase64ToFile(base64, physicalPath + "/appclz/", iconName);
			AppClz clz = new AppClz(name, "appclz/" + iconName);
			appClzDao.save(clz);
		} 
		catch (IOException e) {			
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_007, e);
		}
		log.info("END: {}.addNewAppClz(), name: {}, exec TIME: {} ms.", this.getClass(), name, p.executeTime());
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#updateAppClz(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateAppClz(int serialNo, String name, String base64) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.updateAppClz(), name: {}", this.getClass(), name);
		String iconName = System.currentTimeMillis() + ".png";
		try {
			StandardUtil.writeBase64ToFile(base64, physicalPath + "/appclz/", iconName);
			AppClz clz = new AppClz(serialNo, name, "appclz/" + iconName);
			appClzDao.update(clz);
		} 
		catch (IOException e) {			
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_007, e);
		}
		log.info("END: {}.updateAppClz(), name: {}, exec TIME: {} ms.", this.getClass(), name, p.executeTime());
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findEINByMachine(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String findEINByMachine(String sn, String mac, String wifi) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findEINByMachine(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		List<String> list = machineDao.findEINByMachine(sn, mac, wifi);
		log.debug("sn: {}, mac: {}, wifi: {}, EIN size: {}", sn, mac, wifi, list.size());
		log.info("END: {}.findEINByMachine(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		if(Util.isEmpty(list))
			throw new TBoxException(TBoxCodeMsg.EX_005);
		else if(!Util.isEmpty(list) && list.size() > 1) 
			throw new TBoxException(TBoxCodeMsg.EX_001);
		else 
			return list.get(0);
	}

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#getAppsWithLastVersion(java.lang.String)
	 */
	@Override
	public List<AppEntity> findAppsWithLastVersion(String EIN) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getAppsWithLastVersion(), EIN: {}.", this.getClass(), EIN);
		List<AppEntity> list = appQuery.getAppsWithLastVersion(EIN);
		log.debug("EIN: {}, all publish apps: {}", this.getClass(), list);
		log.info("END: {].getAppsWithLastVersion(), EIN: {}, exec TIME: {} ms.", this.getClass(), EIN, p.executeTime());
		return list;
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findIndexFastApp(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<AppEntity> findIndexFastApp(String sn, String mac, String wifi) throws TBoxException {
		Profiler p = new Profiler();			
		log.trace("START: {}.findIndexFastApp(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		MachineBox box = this.findMachine(sn, mac, wifi);
		List<AppEntity> list = new ArrayList<AppEntity>();
		List<FastApp> fps = this.findIndexFastApp(box.getCompany().getEIN());
		for(FastApp fp : fps) {
			list.add(this.getAppInfo(fp.getAppId()));
		}		
		log.info("END: {}.findIndexFastApp(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		return list;
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#getControlPanelAppByEIN(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<AppEntity> findControlPanelApp(String sn, String mac, String wifi) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getControlPanelApp(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		MachineBox box = this.findMachine(sn, mac, wifi);
		List<AppEntity> list = this.findControlPanelApp(box.getCompany().getEIN());
		log.info("END: {}.getControlPanelApp(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		return list;
	}

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#getControlPanelAppByEIN(java.lang.String)
	 */
	@Override
	public List<AppEntity> findControlPanelApp(String EIN) throws TBoxException {
		Profiler p = new Profiler();			
		log.trace("START: {}.getControlPanelAppByEIN(), EIN: {}", this.getClass(), EIN);
		List<AppEntity> list = new ArrayList<AppEntity>();
		List<FastApp> fps = this.findControlPanelFastApp(EIN);
		for(FastApp fp : fps) {
			list.add(this.getAppInfo(fp.getAppId()));
		}		
		log.info("END: {}.getControlPanelAppByEIN(), EIN: {}, exec TIME: {} ms.", this.getClass(), EIN, p.executeTime());
		return list;
	}
	
	private AppEntity getAppInfo(String appId) {
		String maxVer = appQuery.getAppLastVersion(appId);
		return appQuery.getApp(appId, maxVer);
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#getWeatherReport(java.lang.String)
	 */
	@Override
	public Entity findWeatherReport(String sn, String mac, String wifi) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getWeatherReport(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		Entity entity = null;
		try {
			String code = areaQuery.findCWBCode(sn, mac, wifi);
			entity = cwbAPI.call(code);
		} catch (IOException e) {
			throw new TBoxException(TBoxCodeMsg.EX_003, e.getMessage());
		} catch (Exception e) {
			throw new TBoxException(TBoxCodeMsg.EX_004, e.getMessage());
		}
		log.info("END: {}.getWeatherReport(),  sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		return entity;
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#isLegitimateMachine(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isLegitimateMachine(String sn, String mac, String wifi) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.isLegitimateMachine(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		boolean isLegitimate = machineDao.isLegitimateMachine(sn, mac, wifi);
		log.debug("isLegitimate: {}", isLegitimate);
		log.info("END: {}.isLegitimateMachine(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		return isLegitimate;
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#activeMachine(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public long activeMachine(String sn, String mac, String wifi, String ip) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.activeMachine(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		MachineBox box = this.findMachine(sn, mac, wifi);
		log.debug("is box enabled ? {}", box.isEnabled());
		if(!box.isEnabled()) {
			//啟動時間預設以現在時間往後加1年
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, 1);
			Timestamp now = new Timestamp(cal.getTimeInMillis());
			machineDao.activeMachine(sn, mac, wifi, now.getTime());
		}
		long time = machineDao.recordLastLoginInfo(sn, mac, wifi, ip);
		log.info("END: {}.activeMachine(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		return time;
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#importMachineBoxData(java.util.List)
	 */
	@Override
	public int importMachineBoxData(List<MachineEntity> boxes) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.importMachineBoxData(), boxes: {}", this.getClass(), boxes);
		int count = 0;
		for(MachineEntity box : boxes) {
			count += machineDao.addNewMachine(box.getMachineSN(), box.getWifiMac(), box.getMac(), box.getAreaId(), box.getEIN(), box.getAuthStartDate(), box.getAuthEndDate()); 
		}
		log.info("END: {}.importMachineBoxData(), boxes: {}, exec TIME: {} ms.", this.getClass(), boxes, p.executeTime());
		return count;
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findBy(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public MachineBox findMachine(String sn, String mac, String wifi) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findMachine(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		//List<MachineBox> list = machineDao.findBy(sn, mac, wifi);
		//改為只用機器序號作為驗證
		List<MachineBox> list = machineDao.findBy(sn);
		log.debug("sn: {}, mac: {}, wifi: {}, machine size: {}", sn, mac, wifi, list.size());
		log.info("END: {}.findMachine(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		if(Util.isEmpty(list))
			throw new TBoxException(TBoxCodeMsg.EX_002);
		else if(!Util.isEmpty(list) && list.size() > 1) 
			throw new TBoxException(TBoxCodeMsg.EX_001);		
		else 
			return list.get(0);
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findById(java.lang.Integer)
	 */
	@Override
	public MachineBox findMachine(Integer serialNo) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findMachine(), serialNo: {}", this.getClass(), serialNo);
		MachineBox box = machineDao.findById(serialNo);
		log.debug("serialNo: {}, box: {}", serialNo, box);
		log.info("END: {}.findMachine(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo, p.executeTime());
		return box;
	}
	
	


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findAllMachine()
	 */
	@Override
	public List<MachineEntity> findAllMachine() throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllMachine().", this.getClass());
		List<MachineEntity> list = machineQuery.findAllMachine();
		log.info("END: {}.findAllMachine(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		return list;		
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findAllKVKind()
	 */
	@Override
	public List<KVKind> findAllKVKind() throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllKVKind()", this.getClass());
		List<KVKind> list = kvKindDao.findAll();
		log.info("END: {}.findAllKVKind(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		return list;
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findKVBySerialNo(int)
	 */
	@Override
	public KV findKVBySerialNo(int serial) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findKVBySerialNo(), serialNo: {}", this.getClass(), serial);
		KV kv = kvDao.findById(serial);
		log.info("END: {}.findKVBySerialNo(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serial, p.executeTime());
		return kv;
	}
	
	


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findKVById(int)
	 */
	@Override
	public KVEntity findKVById(int serialNo) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findKVById(), serialNo: {}", this.getClass(), serialNo);
		KVEntity kv = kvQuery.findKVById(serialNo);
		log.info("END: {}.findKVById(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo, p.executeTime());
		return kv;
	}

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findKVsByAccount(java.lang.String, tbox.service.MsgEnum)
	 */
	@Override
	public List<KVEntity> findKVsByAccount(String account, int kind) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findKVsByAccount(), account: {}, kind: {}", this.getClass(), account, kind);
		List<KVEntity> list = "admin".equals(account) ? kvQuery.findAll(kind) : kvQuery.findAllByAccount(account, kind);
		log.debug("account: {} owns kvs: {}", account, list);
		log.info("END: {}.findKVsByAccount(), account: {}, kind; {}, exec TIME: {} ms.", this.getClass(), account, kind, p.executeTime());
		return list;
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findKVsByComp(java.lang.String, tbox.service.MsgEnum)
	 */
	@Override
	public List<KVEntity> findKVsByComp(String EIN, int kind) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findKVsByComp(), EIN: {}, kind: {}", this.getClass(), EIN, kind);
		List<KVEntity> list = kvQuery.findAllByComp(EIN, kind);
		log.debug("EIN: {} owns kvs: {}", EIN, list);
		log.info("END: {}.findKVsByComp(), EIN: {}, kind; {}, exec TIME: {} ms.", this.getClass(), EIN, kind, p.executeTime());
		return list;
	}
	

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findKVsByComp(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public List<KVEntity> findKVsByMachine(String sn, String mac, String wifi, int kind) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findKVsByMachine(), sn: {}, mac: {}, wifi: {}, kind: {}", this.getClass(), sn, mac, wifi, kind);
		List<KVEntity> list = kvQuery.findAllKVByMachine(sn, mac, wifi, kind);
		log.debug("sn: {}, mac: {}, wifi: {}, kind: {}, list: {}", sn, mac, wifi, kind, list);
		log.info("END: {}.findKVsByMachine(), sn: {}, mac: {}, wifi: {}, kind: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, kind, p.executeTime());
		return list;
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#getEIPByAccount(java.lang.String)
	 */
	@Override
	public String findEINByAccount(String account) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getEINByAccount(), account: {}", this.getClass(), account);
		String EIN = compDao.getEIN(account);
		log.debug("account: {}, EIN: {}", account, EIN);
		log.info("END: {}.getEINByAccount(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
		return EIN;
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findAllArea()
	 */
	@Override
	public List<Area> findAllArea() throws TBoxException {
		return areaDao.findAll();
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findAllComp()
	 */
	@Override
	public List<CompanyEntity> findAllComp() throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllComp().", this.getClass());
		List<CompanyEntity> list = compDao.findAllComp();
		log.info("END: {}.findAllComp(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		return list;
	}
	
	

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findCompanyByEIN(java.lang.String)
	 */
	@Override
	public Company findCompanyByEIN(String EIN) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findCompanyByEIN().", this.getClass());
		Company comp = compDao.findById(EIN);
		log.info("END: {}.findCompanyByEIN(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		return comp;
	}

	
	
	
	

//	/* (non-Javadoc)
//	 * @see tbox.service.TBoxService#addCompany(tbox.data.vo.Company)
//	 */
//	@Override
//	public void addCompany(String EIN, String name, String areaId, String logo, String bg, String fastKey1, String fastKey2, String fastKey3, String fastKey4, String grpId) throws TBoxException {
//		Profiler p = new Profiler();
//		log.trace("START: {}.addCompany(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, fastKey1: {}, fastKey2: {}, fastKey3: {}, fastKey4: {}, grpId: {}", this.getClass(), EIN, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId);
//		compDao.save(EIN, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId);
//		log.info("END: {}.addCompany(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, fastKey1: {}, fastKey2: {}, fastKey3: {}, fastKey4: {}, grpId: {}, exec TIME: {} ms.", this.getClass(), EIN, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId, p.executeTime());
//	}
//
//	/* (non-Javadoc)
//	 * @see tbox.service.TBoxService#updateCompInfo(tbox.data.vo.Company)
//	 */
//	@Override
//	public void updateCompInfo(String EIN, String name, String areaId, String logo, String bg, String fastKey1, String fastKey2, String fastKey3, String fastKey4, String grpId) throws TBoxException {
//		Profiler p = new Profiler();
//		log.trace("START: {}.updateCompInfo(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, fastKey1: {}, fastKey2: {}, fastKey3: {}, fastKey4: {}, grpId: {}", this.getClass(), EIN, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId);
//		compDao.update(EIN, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId);
//		log.info("END: {}.updateCompInfo(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, fastKey1: {}, fastKey2: {}, fastKey3: {}, fastKey4: {}, grpId: {}, exec TIME: {} ms.", this.getClass(), EIN, name, areaId, logo, bg, fastKey1, fastKey2, fastKey3, fastKey4, grpId, p.executeTime());
//	}

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#addCompany(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addCompany(String EIN, String name, String areaId, String logo, String bg, String idxApp1,
			String idxApp2, String idxApp3, String idxApp4, String ctrlApp1, String ctrlApp2, String ctrlApp3,
			String ctrlApp4, String grpId) throws TBoxException {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#updateCompInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateCompInfo(String EIN, String name, String areaId, String logo, String bg, String idxApp1,
			String idxApp2, String idxApp3, String idxApp4, String ctrlApp1, String ctrlApp2, String ctrlApp3,
			String ctrlApp4, String grpId) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.updateCompInfo(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, idxApp1: {}, idxApp2: {}, idxApp3: {}, idxApp4: {}, ctrlApp1: {}, ctrlApp2: {}, ctrlApp3: {}, ctrlApp4: {}, grpId: {}", this.getClass(), EIN, name, areaId, logo, bg, idxApp1, idxApp2, idxApp3, idxApp4, ctrlApp1, ctrlApp2, ctrlApp3, ctrlApp4, grpId);
		compDao.update(EIN, name, areaId, logo, bg, grpId);
		compDao.deleteFastApps(1, EIN);
		compDao.deleteFastApps(2, EIN);
		compDao.saveFastApp(1, EIN, idxApp1, idxApp2, idxApp3, idxApp4);
		compDao.saveFastApp(2, EIN, ctrlApp1, ctrlApp2, ctrlApp3, ctrlApp4);
		log.info("END: {}.updateCompInfo(), EIN: {}, name: {}, areaId: {}, logo: {}, bg: {}, idxApp1: {}, idxApp2: {}, idxApp3: {}, idxApp4: {}, ctrlApp1: {}, ctrlApp2: {}, ctrlApp3: {}, ctrlApp4: {}, grpId: {}, exec TIME: {} ms.", this.getClass(), EIN, name, areaId, logo, bg, idxApp1, idxApp2, idxApp3, idxApp4, ctrlApp1, ctrlApp2, ctrlApp3, ctrlApp4, grpId, p.executeTime());
	}


	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#getArea(java.lang.String)
	 */
	@Override
	public Area findArea(String id) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getArea(), areaId: {}", this.getClass(), id);
		Area area = areaDao.findById(Integer.parseInt(id));
		log.info("END: {}.getArea(), areaId: {}, exec TIME: {} ms.", this.getClass(), id, p.executeTime());
		return area;
	}

	
	
}
