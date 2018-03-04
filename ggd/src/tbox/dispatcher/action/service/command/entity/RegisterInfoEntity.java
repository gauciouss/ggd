package tbox.dispatcher.action.service.command.entity;

import java.io.Serializable;

import baytony.util.Util;

public class RegisterInfoEntity implements Serializable {

	private static final long serialVersionUID = -5382958813113985977L;
	private String code;
	private String msg;
	private String lastLoginTime;
	private Boolean isFirstStart;

	public RegisterInfoEntity(String code, String msg, String lastLoginTime) {
		super();
		this.code = code;
		this.msg = msg;
		this.lastLoginTime = lastLoginTime;
		isFirstStart = Util.isEmpty(lastLoginTime);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @return the lastLoginTime
	 */
	public String getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @return the isFirstStart
	 */
	public Boolean getIsFirstStart() {
		return isFirstStart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegisterInfoEntity [code=");
		builder.append(code);
		builder.append(", msg=");
		builder.append(msg);
		builder.append(", lastLoginTime=");
		builder.append(lastLoginTime);
		builder.append(", isFirstStart=");
		builder.append(isFirstStart);
		builder.append("]");
		return builder.toString();
	}

}
