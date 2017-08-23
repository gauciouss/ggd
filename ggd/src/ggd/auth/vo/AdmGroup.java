package ggd.auth.vo;

import java.sql.Timestamp;
import java.util.List;

public class AdmGroup {

	private String groupId;
	
	private String groupName;
	
	private Timestamp createDate;
	
	private Timestamp updateDate;
	
	private boolean isEnabled;
	
	private boolean isApproved;
	
	private List<AdmFunc> funcs;
	
	private List<AdmUser> users;
}
