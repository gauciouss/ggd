package tbox.data.dao;

import org.springframework.stereotype.Repository;

import ggd.core.db.HibernateDao;
import tbox.data.vo.AppClz;

@Repository("AppClzDao")
public class AppClzDao extends HibernateDao<AppClz, Integer> {

}
