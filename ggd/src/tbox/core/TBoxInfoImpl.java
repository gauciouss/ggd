package tbox.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

import ggd.core.CoreException;
import ggd.core.common.CodeMsg;
import ggd.core.util.JSONUtil;

public class TBoxInfoImpl implements TBoxInfo {

	private String machineSN;

	private String MAC;

	private String WIFIMAC;

	private String action;

	@JsonIgnore
	private final JsonNode params;

	public TBoxInfoImpl(JsonNode node, String clientIP) throws CoreException {
		JsonNode header = node.get(TBoxConstant.HEADER);
		this.machineSN = JSONUtil.getString(header, TBoxConstant.SN, CodeMsg.getCodeMessage(TBoxCodeMsg.CORE_001));
		this.MAC = JSONUtil.getString(header, TBoxConstant.MAC, CodeMsg.getCodeMessage(TBoxCodeMsg.CORE_002));
		this.WIFIMAC = JSONUtil.getString(header, TBoxConstant.WIFI_MAC, CodeMsg.getCodeMessage(TBoxCodeMsg.CORE_003));
		this.action = JSONUtil.getString(header, TBoxConstant.ACTION, CodeMsg.getCodeMessage(TBoxCodeMsg.CORE_004));
		this.params = node.get(TBoxConstant.BODY);
	}
	
	@Override
	public String getAction() {
		return this.action;
	}

	public JsonNode getParams() {
		return params;
	}

	@Override
	public String getMachineSN() {
		// TODO Auto-generated method stub
		return machineSN;
	}

	@Override
	public String getMAC() {
		return MAC;
	}

	@Override
	public String getWIFIMAC() {
		return WIFIMAC;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TBoxInfoImpl [machineSN=");
		builder.append(machineSN);
		builder.append(", MAC=");
		builder.append(MAC);
		builder.append(", WIFIMAC=");
		builder.append(WIFIMAC);
		builder.append(", action=");
		builder.append(action);
		builder.append(", params=");
		builder.append(params);
		builder.append("]");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((MAC == null) ? 0 : MAC.hashCode());
		result = prime * result + ((WIFIMAC == null) ? 0 : WIFIMAC.hashCode());
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((machineSN == null) ? 0 : machineSN.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TBoxInfoImpl other = (TBoxInfoImpl) obj;
		if (MAC == null) {
			if (other.MAC != null)
				return false;
		} else if (!MAC.equals(other.MAC))
			return false;
		if (WIFIMAC == null) {
			if (other.WIFIMAC != null)
				return false;
		} else if (!WIFIMAC.equals(other.WIFIMAC))
			return false;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (machineSN == null) {
			if (other.machineSN != null)
				return false;
		} else if (!machineSN.equals(other.machineSN))
			return false;
		return true;
	}

}
