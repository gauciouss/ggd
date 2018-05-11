/**
 * 
 */
package tbox.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import baytony.util.Profiler;
import baytony.util.Util;
import ggd.core.common.CodeMsg;
import tbox.TBoxException;
import tbox.data.dao.MachineDao;
import tbox.data.vo.MachineBox;
import tbox.service.MachineService;

/**
 * @author admin
 *
 */
@Service("MachineService")
@Transactional
public class MachineServiceImpl implements MachineService {
	
	private final static Logger log = LoggerFactory.getLogger(MachineService.class);
	
	@Autowired
	@Qualifier("MachineDao")
	private MachineDao machineDao;

	@Override
	public Boolean confirmPassword(String sn, String password) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.confirmPassword(), sn: {}, password: {}", this.getClass(), sn, password);
		List<MachineBox> list = machineDao.findBy(sn);		
		MachineBox m = Util.isEmpty(list) ? null : list.get(0);
		//boolean b = Util.isEmpty(password) || m == null ? false : password.equals(m.getPassword());
		boolean b = false;
		
		if(m == null) {
			throw new TBoxException(CodeMsg.MO_001);
		}
		else {			
			if(Util.isEmpty(m.getPassword())) {
				throw new TBoxException(CodeMsg.MO_002);
			}
			else {
				b = password.equals(m.getPassword());
			}
		}
		
		log.debug("password is valid? {}", b);
		log.info("END: {}.confirmPassword(), sn: {}, password: {}, exec TIME: {} ms.", this.getClass(), sn, password, p.executeTime());
		return b;
	}
	
	@Override
	public void setPassword(String sn, String password) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.setPassword(), sn: {}, password: {}", this.getClass(), sn, password);
		
		if(Util.isEmpty(password))  
			throw new TBoxException(CodeMsg.MO_002);
		
		List<MachineBox> list = machineDao.findBy(sn);		
		MachineBox m = Util.isEmpty(list) ? null : list.get(0);
		
		if(m == null) {
			throw new TBoxException(CodeMsg.MO_001);
		}
		else {
			machineDao.setPassword(sn, password);
		}
		
		log.info("END: {}.setPassword(), sn: {}, password: {}, exec TIME: {} ms.", this.getClass(), sn, password, p.executeTime());
	}

}
