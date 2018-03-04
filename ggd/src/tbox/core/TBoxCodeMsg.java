package tbox.core;

import java.util.HashMap;
import java.util.Map;

import baytony.util.Util;

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
	
	private static final Map<String, String> map = new HashMap<String, String>();
	
	static {
		//Core代碼設定
		map.put(CORE_001, "Request JSON裡缺少machine SN資訊.");
		map.put(CORE_002, "Request JSON裡缺少MAC資訊.");
		map.put(CORE_003, "Request JSON裡缺少WIFI MAC資訊.");
		map.put(CORE_004, "Request JSON裡缺少action資訊.");
		map.put(EX_001, "序號、mac、wifi mac對應到多組機器");
		map.put(EX_002, "未註冊的機器");
	}

	public static final String getCodeMessage(String code) {
		String msg = map.get(code);
		if( Util.isEmpty(msg) ){
			return CODE_ERROR;
		}
		return msg;
	}
}
