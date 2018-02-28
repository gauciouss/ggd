package tbox.data.dao;

import org.springframework.stereotype.Repository;

import ggd.core.db.HibernateDao;
import tbox.data.vo.KV;

@Repository("KVDao")
public class KVDao extends HibernateDao<KV, Integer> {

}
