package tbox;

import ggd.core.CoreException;
import ggd.core.common.CodeMsg;

public class TBoxException extends CoreException {

	private static final long serialVersionUID = -8081744106155339188L;
	private final String code;

	public TBoxException(String code) {
		super(CodeMsg.getCodeMessage(code));
		this.code=code;
	}
	
	public TBoxException(String code, String defineMsg) {
		super(defineMsg);
		this.code=code;
	} 

	public TBoxException(String code,Throwable arg0) {
		super(CodeMsg.getCodeMessage(code), arg0);
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
}
