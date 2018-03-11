package tbox.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import baytony.util.Profiler;
import baytony.util.Util;
import tbox.TBoxException;
import tbox.core.TBoxCodeMsg;
import tbox.data.dao.AppClzDao;
import tbox.data.dao.AppQuery;
import tbox.data.dao.AreaDao;
import tbox.data.dao.AreaQuery;
import tbox.data.dao.CompanyDao;
import tbox.data.dao.KVDao;
import tbox.data.dao.KVKindDao;
import tbox.data.dao.KVQuery;
import tbox.data.dao.MachineDao;
import tbox.data.vo.AppClz;
import tbox.data.vo.AppEntity;
import tbox.data.vo.Area;
import tbox.data.vo.Company;
import tbox.data.vo.CompanyEntity;
import tbox.data.vo.KV;
import tbox.data.vo.KVEntity;
import tbox.data.vo.KVKind;
import tbox.data.vo.MachineBox;
import tbox.proxy.cwb.gov.tw.OpendataAPI;
import tbox.proxy.cwb.gov.tw.OpendataAPI.Entity;
import tbox.service.TBoxService;

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
	
	
	
	
	
	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findAllAppKind()
	 */
	@Override
	public List<AppClz> findAllAppKind() throws TBoxException {
		return appClzDao.findAll();
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
		Company comp = compDao.findById(EIN);
		if(!Util.isEmpty(comp.getFastKey1()))
			list.add(this.getAppInfo(comp.getFastKey1()));
		if(!Util.isEmpty(comp.getFastKey2()))
			list.add(this.getAppInfo(comp.getFastKey2()));
		if(!Util.isEmpty(comp.getFastKey3()))
			list.add(this.getAppInfo(comp.getFastKey3()));
		if(!Util.isEmpty(comp.getFastKey4()))
			list.add(this.getAppInfo(comp.getFastKey4()));
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
	public int importMachineBoxData(List<MachineBox> boxes) throws TBoxException {
		// TODO Auto-generated method stub
		return 0;
	}



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#findBy(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public MachineBox findMachine(String sn, String mac, String wifi) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.findMachine(), sn: {}, mac: {}, wifi: {}", this.getClass(), sn, mac, wifi);
		List<MachineBox> list = machineDao.findBy(sn, mac, wifi);
		log.debug("sn: {}, mac: {}, wifi: {}, machine size: {}", sn, mac, wifi, list.size());
		log.info("END: {}.findMachine(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		if(Util.isEmpty(list))
			throw new TBoxException(TBoxCodeMsg.EX_005);
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



	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#addCompany(tbox.data.vo.Company)
	 */
	@Override
	public void addCompany(Company comp) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.addCompany(), comp: {}", this.getClass(), comp);
		compDao.save(comp);
		log.info("END: {}.addCompany(), comp: {}, exec TIME: {} ms.", this.getClass(), comp, p.executeTime());
	}

	/* (non-Javadoc)
	 * @see tbox.service.TBoxService#updateCompInfo(tbox.data.vo.Company)
	 */
	@Override
	public void updateCompInfo(Company comp) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.updateCompInfo(), comp: {}", this.getClass(), comp);
		compDao.update(comp);
		log.info("END: {}.updateCompInfo(), comp: {}, exec TIME: {} ms.", this.getClass(), comp, p.executeTime());
	}

	
}
