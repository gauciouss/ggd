package tbox.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import tbox.TBoxException;
import tbox.data.vo.Area;
import tbox.data.vo.Company;
import tbox.data.vo.CompanyEntity;
import tbox.data.vo.KV;
import tbox.data.vo.KVEntity;
import tbox.data.vo.KVKind;

@Transactional
public interface TBoxService {
	
	/**
	 * 查詢所有訊息種類
	 * @return
	 * @throws TBoxException
	 */
	public List<KVKind> findAllKVKind() throws TBoxException;
	
	/**
	 * 查詢訊息
	 * @param serial
	 * @return
	 * @throws TBoxException
	 */
	public KV findKVBySerialNo(int serial) throws TBoxException;
	
	/**
	 * 查詢所有訊息
	 * @param account
	 * @param type
	 * @return
	 * @throws TBoxException
	 */
	public List<KVEntity> findKVsByAccount(String account, int type) throws TBoxException;
	
	/**
	 * 查詢所有訊息
	 * @param EIN
	 * @param type
	 * @return
	 * @throws TBoxException
	 */
	public List<KVEntity> findKVsByComp(String EIN, int type) throws TBoxException;
	
	/**
	 * 查詢統一編號
	 * @param account 
	 * @return
	 * @throws TBoxException
	 */
	public String getEINByAccount(String account) throws TBoxException;
	
	/**
	 * 查詢所有行政區域
	 * @return
	 * @throws TBoxException
	 */
	public List<Area> findAllArea() throws TBoxException;

	/**
	 * 查詢所有廠商
	 * @return
	 */
	public List<CompanyEntity> findAllComp() throws TBoxException;
	
	/**
	 * 查詢廠商
	 * @param EIN 統一編號
	 * @return
	 * @throws TBoxException
	 */
	public Company findCompanyByEIN(String EIN) throws TBoxException;
	
	/**
	 * 新增廠商
	 * @param comp
	 * @return
	 */
	public void addCompany(Company comp) throws TBoxException;
	
	/**
	 * 更新廠商資訊
	 * @param comp
	 * @return
	 * @throws TBoxException
	 */
	public void updateCompInfo(Company comp) throws TBoxException;
	
	
}
