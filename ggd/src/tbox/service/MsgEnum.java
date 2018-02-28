package tbox.service;

import ggd.core.common.Constant;

public enum MsgEnum {

	KV(1),
	MSG(2),
	ALL(0);
	
	private int value;
	 
    private MsgEnum(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public static String getName(int value) {
    	String rs = Constant.EMPTY;
    	switch(value) {
    		case 1:
    			rs = "廣告";
    			break;
    		case 2:
    			rs = "跑馬燈";
    			break;
    	}
    	return rs;
    }
    
    
}
