package tbox.service.impl;

import java.sql.Timestamp;
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
import tbox.data.dao.AreaDao;
import tbox.data.dao.CompanyDao;
import tbox.data.dao.KVDao;
import tbox.data.dao.KVKindDao;
import tbox.data.dao.KVQuery;
import tbox.data.dao.MachineDao;
import tbox.data.vo.Area;
import tbox.data.vo.Company;
import tbox.data.vo.CompanyEntity;
import tbox.data.vo.KV;
import tbox.data.vo.KVEntity;
import tbox.data.vo.KVKind;
import tbox.data.vo.MachineBox;
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
		if(!Util.isEmpty(list) && list.size() > 1) 
			throw new TBoxException(TBoxCodeMsg.EX_001);
		
		if(!Util.isEmpty(list))
			return list.get(0);
		log.info("END: {}.findMachine(), sn: {}, mac: {}, wifi: {}, exec TIME: {} ms.", this.getClass(), sn, mac, wifi, p.executeTime());
		return null;
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
	 * @see tbox.service.TBoxService#getEIPByAccount(java.lang.String)
	 */
	@Override
	public String getEINByAccount(String account) throws TBoxException {
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
