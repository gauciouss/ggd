package tbox.core;

import baytony.util.Util;
import ggd.core.common.CodeMsg;
import ggd.core.common.Constant;
import ggd.core.entity.ServiceResponse.Header;

public class TBoxServieResponseHeader extends Header {

	private String sn = Constant.EMPTY;
	private String mac = Constant.EMPTY;
	private String wife_mac = Constant.EMPTY;
	private String authorizedEnd = Constant.EMPTY;
	/**
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}
	/**
	 * @param sn the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}
	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * @return the wife_mac
	 */
	public String getWife_mac() {
		return wife_mac;
	}
	/**
	 * @param wife_mac the wife_mac to set
	 */
	public void setWife_mac(String wife_mac) {
		this.wife_mac = wife_mac;
	}
	/**
	 * @return the authorizedEnd
	 */
	public String getAuthorizedEnd() {
		return authorizedEnd;
	}
	/**
	 * @param authorizedEnd the authorizedEnd to set
	 */
	public void setAuthorizedEnd(String authorizedEnd) {
		this.authorizedEnd = authorizedEnd;
	}
	
	@Override
	public String getMsg() {	
		return TBoxCodeMsg.getCodeMessage(super.getCode());
	}
}
