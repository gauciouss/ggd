package tbox.data.dao;

import org.springframework.stereotype.Repository;

import ggd.core.db.HibernateDao;
import tbox.data.vo.Area;

@Repository("AreaDao")
public class AreaDao extends HibernateDao<Area, Integer> {

	
}
