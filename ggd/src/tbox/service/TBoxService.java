package tbox.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.transaction.annotation.Transactional;

import ggd.auth.vo.AdmGroup;
import tbox.TBoxException;
import tbox.data.vo.App;
import tbox.data.vo.AppClz;
import tbox.data.vo.AppEntity;
import tbox.data.vo.Area;
import tbox.data.vo.Company;
import tbox.data.vo.CompanyEntity;
import tbox.data.vo.FastApp;
import tbox.data.vo.KV;
import tbox.data.vo.KVEntity;
import tbox.data.vo.KVKind;
import tbox.data.vo.MachineBox;
import tbox.data.vo.MachineEntity;
import tbox.data.vo.OSVersion;
import tbox.service.entity.ApkInfoEntity;

@Transactional
public interface TBoxService {
	
	/**
	 * 刪除KV
	 * @param serialNo
	 * @throws TBoxException
	 */
	public void deleteKV(int serialNo) throws TBoxException;	
	
	/**
	 * 刪除app
	 * @param serialNo
	 * @throws TBoxException
	 */
	public void deleteApp(String serialNo) throws TBoxException;	
	
	
	/**
	 * 取得 os version
	 * @param serialNo
	 * @return
	 * @throws TBoxException
	 */
	public OSVersion getOSVerion(int serialNo) throws TBoxException;
	
	
	/**
	 * 取得最新版本的 os version
	 * @return
	 * @throws TBoxException
	 */
	public OSVersion getNewestOSVersion() throws TBoxException;
	
	/**
	 * 更新os version版本資訊
	 * @param version
	 * @throws TBoxException
	 */
	public void updateOSVersion(OSVersion version) throws TBoxException;	
	
	/**
	 * 刪除os version版本
	 * @param serialNo
	 * @throws TBoxException
	 */
	public void deleteOSVersion(int serialNo) throws TBoxException;
	
	/**
	 * 新增OS version
	 * @param version
	 * @throws TBoxException
	 */
	public void saveNewVersion(OSVersion version) throws TBoxException;	
	
	/**
	 * os version列表
	 * @return
	 * @throws TBoxException
	 */
	public List<OSVersion> findAllVersion() throws TBoxException;	
	
	/**
	 * 查詢快捷APP
	 * @param EIN
	 * @return
	 * @throws TBoxException
	 */
	public List<FastApp> findControlPanelFastApp(String EIN) throws TBoxException;
	
	/**
	 * 查詢快捷APP
	 * @param EIN
	 * @return
	 * @throws TBoxException
	 */
	public List<FastApp> findIndexFastApp(String EIN) throws TBoxException;
	
	
	/**
	 * 新增/更新APP資訊 
	 * @param serial
	 * @param clzId
	 * @param appName
	 * @param appEngName
	 * @param version
	 * @param pkgName
	 * @param appDesc
	 * @throws TBoxException
	 */
	public void saveOrUpdateAppInfo(String appId, int clzId, String appName, String appEngName, String version, String pkgName, String appDesc) throws TBoxException;
		
	
	/**
	 * 儲存.apk 檔
	 * @param item
	 * @param appId
	 * @param apkName
	 * @return
	 * @throws TBoxException
	 */
	public ApkInfoEntity saveApk2Disk(FileItem item, String appId, String apkName, boolean isTemp) throws TBoxException;	
	
	/**
	 * 取得apk相關資訊
	 * @param apkB64
	 * @param appId
	 * @return
	 * @throws TBoxException
	 */
	public ApkInfoEntity getApkInfo(String apkB64, String appId) throws TBoxException;
	
	
	/**
	 * 刪除預定的APP
	 * @param appId
	 * @throws TBoxException
	 */
	public void deleteBookingAppID(String appId) throws TBoxException;
	
	/**
	 * 取得下一個APP id
	 * @return
	 * @throws TBoxException
	 */
	public String getNextAppId() throws TBoxException;
	
	/**
	 * 查詢APP
	 * @param id
	 * @return
	 * @throws TBoxException
	 */
	public App findAppById(String id) throws TBoxException;
	
	/**
	 * 查詢APP
	 * @param id appId -> MAPP0000001
	 * @return
	 * @throws TBoxException
	 */
	public AppEntity findLastVersionApp(String id) throws TBoxException;
	
	
	/**
	 * 取得所有APPs
	 * @param grp
	 * @return
	 * @throws TBoxException
	 */
	public List<AppEntity> findAllApps(AdmGroup grp) throws TBoxException;	
	
	/**
	 * 取得所有app類別
	 */
	public List<AppClz> findAllAppKind() throws TBoxException;
	
	
	/**
	 * 取得app類別
	 */
	public AppClz findAppKindById(int serialNo) throws TBoxException;
	
	
	/**
	 * 新增APP類別
	 * @param name
	 * @param base64
	 * @throws TBoxException
	 */
	public void addNewAppClz(String name, String base64) throws TBoxException;
	
	/**
	 * 更新APP類別資訊
	 * @param serialNo
	 * @param name
	 * @param base64
	 * @throws TBoxException
	 */
	public void updateAppClz(int serialNo, String name, String base64) throws TBoxException;
	
	
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
	public List<AppEntity> findIndexFastApp(String sn, String mac, String wifi) throws TBoxException;
	
	
	/**
	 * 查詢遙控器快捷APP資訊
	 * @param sn
	 * @param mac
	 * @param wifi
	 * @return
	 * @throws TBoxException
	 */
	public List<AppEntity> findControlPanelApp(String sn, String mac, String wifi) throws TBoxException;
	
	/**
	 * 查詢遙控器快捷APP資訊
	 * @param EIN
	 * @return
	 * @throws TBoxException
	 */
	public List<AppEntity> findControlPanelApp(String EIN) throws TBoxException;
	
	/**
	 * 取得天氣預報
	 * @param sn
	 * @return
	 * @throws TBoxException
	 */
	public tbox.proxy.cwb.gov.tw.OpendataAPI.Entity findWeatherReport(String sn) throws TBoxException;
	
	/**
	 * 判斷是否為合法的機器
	 * @param sn
	 * @return
	 * @throws TBoxException
	 */
	public boolean isLegitimateMachine(String sn) throws TBoxException;	
	
	/**
	 * 啟動機上盒並記錄最後登入時間&IP，若已經啟動機上盒，則只記錄最後登入時間&IP
	 * @param sn
	 * @param mac
	 * @param wifi
	 * @throws TBoxException
	 */
	public long activeMachine(String sn, String mac, String wifi, String ip) throws TBoxException;
	
	/**
	 * 新增機上盒
	 * @param box
	 * @return
	 * @throws TBoxException
	 */
	public int saveMachineBox(MachineEntity box) throws TBoxException;	
	
	/**
	 * 更新機上盒
	 * @param box
	 * @return
	 * @throws TBoxException
	 */
	public int updateMachineBox(MachineEntity box) throws TBoxException;
	
	/**
	 * 匯入機上盒資料
	 * @param box
	 * @return
	 * @throws TBoxException
	 */
	public int importMachineBoxData(List<MachineEntity> boxes) throws TBoxException;
	
	/**
	 * 查詢機上盒資料
	 * @param sn
	 * @return
	 * @throws TBoxException
	 */
	public MachineBox findMachine(String sn) throws TBoxException;
	
	/**
	 * 查詢機上盒資料
	 * @param serialNo
	 * @return
	 * @throws TBoxException
	 */
	public MachineBox findMachine(Integer serialNo) throws TBoxException;
	
	/**
	 * 查詢機上盒資料
	 * @return
	 * @throws TBoxException
	 */
	public List<MachineEntity> findAllMachine() throws TBoxException;
	
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
	 * 查詢訊息
	 * @param serialNo
	 * @return
	 * @throws TBoxException
	 */
	public KVEntity findKVById(int serialNo) throws TBoxException;
	
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
	public List<KVEntity> findKVsByMachine(String sn, int kind) throws TBoxException;
	
	
	/**
	 * 新增廣告/跑馬燈訊息
	 * @param kind
	 * @param name
	 * @param imgB64
	 * @param clickLink
	 * @param msg
	 * @param createUser
	 * @param start
	 * @param end
	 * @param isEnabled
	 * @param isApproved
	 * @param EINs
	 * @param type
	 * @throws TBoxException
	 */
	public void addKV(int kind, String name, String imgB64, String clickLink, String msg, String createUser, Timestamp start, Timestamp end, boolean isEnabled, boolean isApproved, List<String> EINs, String type) throws TBoxException;
	
	/**
	 * 更新廣告/跑馬燈訊息
	 * @param serialNo
	 * @param kind
	 * @param name
	 * @param imgB64
	 * @param clickLink
	 * @param msg
	 * @param updateUser
	 * @param start
	 * @param end
	 * @param isEnabled
	 * @param isApproved
	 * @param EINs
	 * @param type
	 * @throws TBoxException
	 */
	public void updateKV(int serialNo, int kind, String name, String imgB64, String clickLink, String msg, String updateUser, Timestamp start, Timestamp end, boolean isEnabled, boolean isApproved, List<String> EINs, String type) throws TBoxException;
	
	
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
	 * @param EIN
	 * @param name
	 * @param areaId
	 * @param logo
	 * @param bg
	 * @param fastKey1
	 * @param fastKey2
	 * @param fastKey3
	 * @param fastKey4
	 * @param grpId
	 * @throws TBoxException
	 */
	//public void addCompany(String EIN, String name, String areaId, String logo, String bg, String fastKey1, String fastKey2, String fastKey3, String fastKey4, String grpId) throws TBoxException;
	
	
	/**
	 * 新增廠商
	 * @param EIN
	 * @param name
	 * @param areaId
	 * @param logo
	 * @param bg
	 * @param idxApp1
	 * @param idxApp2
	 * @param idxApp3
	 * @param idxApp4
	 * @param idxApp5
	 * @param idxApp6
	 * @param ctrlApp1
	 * @param ctrlApp2
	 * @param ctrlApp3
	 * @param ctrlApp4
	 * @param grpId
	 * @throws TBoxException
	 */
	public void addCompany(String EIN, String name, String areaId, String logo, String bg, String idxApp1, String idxApp2, String idxApp3, String idxApp4, String idxApp5, String idxApp6, String ctrlApp1, String ctrlApp2, String ctrlApp3, String ctrlApp4, String grpId) throws TBoxException;
	
	
	
	
	/**
	 * 更新廠商資訊
	 * @param EIN
	 * @param name
	 * @param areaId
	 * @param logo
	 * @param bg
	 * @param idxApp1
	 * @param idxApp2
	 * @param idxApp3
	 * @param idxApp4
	 * @param idxApp5
	 * @param idxApp6
	 * @param ctrlApp1
	 * @param ctrlApp2
	 * @param ctrlApp3
	 * @param ctrlApp4
	 * @param grpId
	 * @throws TBoxException
	 */
	public void updateCompInfo(String EIN, String name, String areaId, String logo, String bg, String idxApp1, String idxApp2, String idxApp3, String idxApp4, String idxApp5, String idxApp6, String ctrlApp1, String ctrlApp2, String ctrlApp3, String ctrlApp4, String grpId) throws TBoxException;
	
	/**
	 * 取得地區
	 * @param id
	 * @return
	 */
	public Area findArea(String id) throws TBoxException;
}
