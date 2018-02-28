package tbox.data.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class KVEntity implements Serializable {

	private static final long serialVersionUID = 7086024057049481426L;

	@Id
	@Column(name = "kv_serial_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer serialNo;

	@Column(name = "kind")
	private Integer kind;

	@Column(name = "img_path")
	private String imgPath;

	@Column(name = "click_link")
	private String clickLink;

	@Column(name = "msg")
	private String msg;

	@Column(name = "start_date")
	private Timestamp startDate;
	
	@Column(name = "end_Date")
	private Timestamp endDate;
	
	@Column(name = "isEnabled")
	private boolean isEnabled;
	
	

	/**
	 * @return the serialNo
	 */
	public Integer getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the kind
	 */
	public Integer getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(Integer kind) {
		this.kind = kind;
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * @return the clickLink
	 */
	public String getClickLink() {
		return clickLink;
	}

	/**
	 * @param clickLink the clickLink to set
	 */
	public void setClickLink(String clickLink) {
		this.clickLink = clickLink;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the startDate
	 */
	public Timestamp getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Timestamp getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		KVEntity other = (KVEntity) obj;
		if (serialNo == null) {
			if (other.serialNo != null)
				return false;
		} else if (!serialNo.equals(other.serialNo))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KV [serialNo=");
		builder.append(serialNo);
		builder.append(", kind=");
		builder.append(kind);
		builder.append(", imgPath=");
		builder.append(imgPath);
		builder.append(", clickLink=");
		builder.append(clickLink);
		builder.append(", msg=");
		builder.append(msg);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", isEnabled=");
		builder.append(isEnabled);
		builder.append("]");
		return builder.toString();
	}
	
	

}
