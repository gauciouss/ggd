package tbox.core;

import java.io.Serializable;

public interface TBoxData extends Serializable {

	/**
	 * 取得request parameter裡arg傳入的參數. 
	 * 參數格式只允許兩種：JSON or 自訂.
	 * @return
	 */
	public String getArg();
	
	/**
	 * 取得URL裡傳入的Folder參數.
	 * @return
	 */
	public String getFolder();
	
	/**
	 * 取得URL裡傳入的jsp參數.
	 * @return
	 */
	public String getJsp();
	
	/**
	 * 判斷arg裡傳入的參數parser JSON時是否為JSON格式.
	 * @return
	 */
	public boolean isArgJson();
	
	public TBoxInfo getTBoxInfo();
}
