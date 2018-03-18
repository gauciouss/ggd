package tbox.data.dao;

import org.springframework.stereotype.Repository;

import ggd.core.db.HibernateDao;
import tbox.data.vo.App;

@Repository("AppDao")
public class AppDao extends HibernateDao<App, String> {

}
