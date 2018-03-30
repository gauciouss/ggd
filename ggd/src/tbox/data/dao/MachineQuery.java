package tbox.data.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.core.db.HibernateQuery;
import tbox.data.vo.MachineEntity;

@Repository("MachineQuery")
public class MachineQuery extends HibernateQuery {

	private static final String SQL_FIND_ALL_MACHINE = 
			"select m.serial_no, m.machine_sn, m.ethernet_mac, m.wifi_mac, c.name, m.isEnabled, m.start_date, m.authorized_start_date, m.authorized_end_date " + 
			"	from machine_box m " + 
			"    inner join company c on m.EIN = c.EIN";
	
	/**
	 * 查詢所有機上盒
	 * @return
	 */
	public List<MachineEntity> findAllMachine() {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllMachine().", this.getClass());
		List<MachineEntity> list = super.findBySql(SQL_FIND_ALL_MACHINE, MachineEntity.class);
		log.info("END: {}.findAllMachine(), machine size: {}, exec TIME: {} ms.", this.getClass(), list.size(), p.executeTime());
		return list;
	}
}
