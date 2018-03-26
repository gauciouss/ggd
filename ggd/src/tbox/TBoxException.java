package tbox;

import ggd.core.CoreException;
import tbox.core.TBoxCodeMsg;

public class TBoxException extends CoreException {

	private static final long serialVersionUID = -8081744106155339188L;
	private final String code;

	public TBoxException(String code) {		
		super(code, TBoxCodeMsg.getCodeMessage(code));		
		this.code=code;
	}
	
	public TBoxException(String code, String defineMsg) {
		super(defineMsg);
		this.code=code;
	} 

	public TBoxException(String code,Throwable arg0) {
		super(TBoxCodeMsg.getCodeMessage(code), arg0);
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
}
