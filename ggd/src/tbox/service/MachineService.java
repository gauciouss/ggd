package tbox.service;

import tbox.TBoxException;

public interface MachineService {

	/**
	 * 確認機上盒18+密碼
	 * @param sn 機上盒序號
	 * @param password 密碼
	 * @return
	 */
	public Boolean confirmPassword(String sn, String password) throws TBoxException;
	
	/**
	 * 設定機上盒密碼
	 * @param sn 機上盒序號
	 * @param password 密碼
	 * @throws TBoxException
	 */
	public void setPassword(String sn, String password) throws TBoxException;
}
