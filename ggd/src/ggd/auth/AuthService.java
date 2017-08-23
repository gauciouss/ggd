package ggd.auth;

import ggd.auth.vo.AdmFunc;
import ggd.auth.vo.AdmGroup;
import ggd.auth.vo.AdmUser;

public interface AuthService {

	/**
	 * 驗證帳號密碼
	 * @param account
	 * @param pwd
	 * @return
	 */
	public AdmUser authenticate(String account, String pwd);
	
	/**
	 * 新增使用者
	 * @param user
	 */
	public void addUser(AdmUser user);
	
	/**
	 * 更新使用者
	 * @param user
	 */
	public void updateUser(AdmUser user);
	
	/**
	 * 啟用使用者	
	 * @param account
	 * @param isEnable
	 */
	public void enableUser(String account, boolean isEnable);
	
	/**
	 * 允許使用者
	 * @param account
	 * @param isApprove
	 */
	public void approveUser(String account, boolean isApprove);
	
	/**
	 * 新增群組
	 * @param group
	 */
	public void addGroup(AdmGroup group);
	
	/**
	 * 更新群組資訊
	 * @param group
	 */
	public void updateGroup(AdmGroup group);
	
	/**
	 * 啟用群組
	 * @param grpId
	 * @param isEnable
	 */
	public void enableGroup(String grpId, boolean isEnable);
	
	/**
	 * 允許群組
	 * @param grpId
	 * @param isApprove
	 */
	public void approveGroup(String grpId, boolean isApprove);
	
	/**
	 * 新增功能
	 * @param func
	 */
	public void addFunc(AdmFunc func);
	
	/**
	 * 更新功能資訊
	 * @param func
	 */
	public void updateFunc(AdmFunc func);
	
	/**
	 * 啟用功能
	 * @param funcid
	 * @param isEnable
	 */
	public void enableFunc(String funcid, boolean isEnable);
	
	/**
	 * 允許功能
	 * @param funcid
	 * @param isApprove
	 */
	public void approveFunc(String funcid, boolean isApprove);
	
	/**
	 * 把使用者設定到群組中
	 * @param account
	 * @param groupId
	 */
	public void addUser2Group(String account, String groupId);
	
	/**
	 * 把使用者移出群組
	 * @param account
	 * @param groupId
	 */
	public void removeUserFromGroup(String account, String groupId);
	
	/**
	 * 把功能加入到群組中
	 * @param funcId
	 * @param groupId
	 */
	public void addFunc2Group(String funcId, String groupId);
	
	/**
	 * 把功能移出群組中
	 * @param funcId
	 * @param groupId
	 */
	public void removeFuncFromGroup(String funcId, String groupId);
}
