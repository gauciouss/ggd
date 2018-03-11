package tbox.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import tbox.TBoxException;
import tbox.data.vo.AppClz;
import tbox.data.vo.AppEntity;
import tbox.data.vo.Area;
import tbox.data.vo.Company;
import tbox.data.vo.CompanyEntity;
import tbox.data.vo.KV;
import tbox.data.vo.KVEntity;
import tbox.data.vo.KVKind;
import tbox.data.vo.MachineBox;

@Transactional
public interface TBoxService {
	
	/*8
	 * 取得所有app類別
	 */
	public List<AppClz> findAllAppKind() throws TBoxException;
	
	/**
	 * 取得商城APP
	 * @param EIN
	 * @return
	 * @throws TBoxException
	 */
	public List<AppEntity> findAppsWithLastVersion(String EIN) throws TBoxException;	
	
	
	/**
	 * 查詢首頁快捷APP資訊
	 * @param sn
	 * @param mac
	 * @param wifi
	 * @return
	 * @throws TBoxException
	 */
	public List<AppEntity> findControlPanelApp(String sn, String mac, String wifi) throws TBoxException;
	
	/**
	 * 查詢首頁快捷APP資訊
	 * @param EIN
	 * @return
	 * @throws TBoxException
	 */
	public List<AppEntity> findControlPanelApp(String EIN) throws TBoxException;
	
	/**
	 * 取得天氣預報
	 * @param sn
	 * @param mac
	 * @param wifi
	 * @return
	 * @throws TBoxException
	 */
	public tbox.proxy.cwb.gov.tw.OpendataAPI.Entity findWeatherReport(String sn, String mac, String wifi) throws TBoxException;
	
	/**
	 * 判斷是否為合法的機器
	 * @param sn
	 * @param mac
	 * @param wifi
	 * @return
	 * @throws TBoxException
	 */
	public boolean isLegitimateMachine(String sn, String mac, String wifi) throws TBoxException;	
	
	/**
	 * 啟動機上盒並記錄最後登入時間&IP，若已經啟動機上盒，則只記錄最後登入時間&IP
	 * @param sn
	 * @param mac
	 * @param wifi
	 * @throws TBoxException
	 */
	public long activeMachine(String sn, String mac, String wifi, String ip) throws TBoxException;
	
	/**
	 * 匯入機上盒資料
	 * @param box
	 * @return
	 * @throws TBoxException
	 */
	public int importMachineBoxData(List<MachineBox> boxes) throws TBoxException;
	
	/**
	 * 查詢機上盒資料
	 * @param sn
	 * @param mac
	 * @param wifi
	 * @return
	 * @throws TBoxException
	 */
	public MachineBox findMachine(String sn, String mac, String wifi) throws TBoxException;
	
	/**
	 * 查詢機上盒資料
	 * @param serialNo
	 * @return
	 * @throws TBoxException
	 */
	public MachineBox findMachine(Integer serialNo) throws TBoxException;
	
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
	 * @param kind
	 * @return
	 * @throws TBoxException
	 */
	public List<KVEntity> findKVsByAccount(String account, int kind) throws TBoxException;
	
	/**
	 * 查詢所有訊息
	 * @param EIN
	 * @param kind
	 * @return
	 * @throws TBoxException
	 */
	public List<KVEntity> findKVsByComp(String EIN, int kind) throws TBoxException;
	
	/**
	 * 查詢所有訊息
	 * @param sn
	 * @param mac
	 * @param wifi
	 * @param kind
	 * @return
	 * @throws TBoxException
	 */
	public List<KVEntity> findKVsByMachine(String sn, String mac, String wifi, int kind) throws TBoxException;
	
	/**
	 * 查詢統一編號
	 * @param account 
	 * @return
	 * @throws TBoxException
	 */
	public String findEINByAccount(String account) throws TBoxException;
	
	
	/**
	 * 查詢統一編號
	 * @param sn
	 * @param mac
	 * @param wifi
	 * @return
	 * @throws TBoxException
	 */
	public String findEINByMachine(String sn, String mac, String wifi) throws TBoxException;	
	
	
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
