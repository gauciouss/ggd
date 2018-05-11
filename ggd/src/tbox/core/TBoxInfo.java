package tbox.core;

import com.fasterxml.jackson.databind.JsonNode;

public interface TBoxInfo {

	public String getMachineSN();

	public String getMAC();

	public String getWIFIMAC();

	public String getAction();
	
	public JsonNode getParams();

}
