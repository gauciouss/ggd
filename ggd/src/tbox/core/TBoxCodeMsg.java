package tbox.core;

import java.util.HashMap;
import java.util.Map;

import baytony.util.Util;
import ggd.core.common.CodeMsg;

public class TBoxCodeMsg {
	
	/**
	 * 當CodeMsg查無設定訊息時，預設產生的說明．
	 */
	public static final String CODE_ERROR = "：未設定錯誤訊息.";

	
	/**
	 * CORE_001 = "CO-001"
	 * Request JSON裡缺少machine sn資訊.
	 */
	public static final String CORE_001 = "CO-001";
	
	/**
	 * CORE_002 = "CO-002"
	 * Request JSON裡缺少MAC資訊．
	 */
	public static final String CORE_002 = "CO-002";
	
	/**
	 * CORE_002 = "CO-003"
	 * Request JSON裡缺少WIFI MAC資訊．
	 */
	public static final String CORE_003 = "CO-003";
	
	/**
	 * CORE_002 = "CO-004"                    
	 * Request JSON裡缺少 action 資訊．
	 */
	public static final String CORE_004 = "CO-004";
	
	/**
	 * EX_001 = "EX-001"
	 * 序號、mac、wifi mac對應到多組機器
	 */
	public static final String EX_001 = "EX-001";
	
	/**
	 * EX_002 = "EX-002"
	 * 未註冊的機器
	 */
	public static final String EX_002 = "EX-002";
	
	/**
	 * 連接遠端機器發生錯誤
	 */
	public static final String EX_003 = "EX-003";
	
	/**
	 * 發生不明錯誤
	 */
	public static final String EX_004 = "EX-004";
	
	/**
	 * 無法反查註冊公司，非法的機器
	 */
	public static final String EX_005 = "EX-005";
	
	/**
	 * 檔案上傳失敗
	 */
	public static final String EX_006 = "EX-006";
	
	/**
	 * 檔案寫入硬碟失敗
	 */
	public static final String EX_007 = "EX-007";
	
	/**
	 * 重複的apk
	 */
	public static final String EX_008 = "EX-008";
	
	private static final Map<String, String> map = new HashMap<String, String>();
	
	static {
		//Core代碼設定
		map.put(CORE_001, "Request JSON裡缺少machine SN資訊.");
		map.put(CORE_002, "Request JSON裡缺少MAC資訊.");
		map.put(CORE_003, "Request JSON裡缺少WIFI MAC資訊.");
		map.put(CORE_004, "Request JSON裡缺少action資訊.");
		map.put(EX_001, "序號、mac、wifi mac對應到多組機器");
		map.put(EX_002, "非法的機器");
		map.put(EX_003, "未連接遠端機器發生錯誤的機器");
		map.put(EX_004, "發生不明錯誤");
		map.put(EX_005, "無法反查註冊公司，非法的機器");
		map.put(EX_006, "檔案上傳失敗");
		map.put(EX_007, "檔案寫入硬碟失敗");
		map.put(EX_008, "重複的apk");
	}

	public static final String getCodeMessage(String code) {
		String msg = map.get(code);
		if( Util.isEmpty(msg) ){
			msg = CodeMsg.getCodeMessage(code);
			if( Util.isEmpty(msg) )
				return CODE_ERROR;
		}
		return msg;
	}
}

