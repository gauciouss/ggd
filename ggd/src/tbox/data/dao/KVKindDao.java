package tbox.data.dao;

import org.springframework.stereotype.Repository;

import ggd.core.db.HibernateDao;
import tbox.data.vo.KVKind;

@Repository("KVKindDao")
public class KVKindDao extends HibernateDao<KVKind, Integer> {

}
