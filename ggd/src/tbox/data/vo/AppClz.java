package tbox.data.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_clz")
public class AppClz implements Serializable {

	private static final long serialVersionUID = 3799102116177292924L;

	@Id
	@Column(name = "clz_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer clzId;

	@Column(name = "clz_name")
	private String clzName;

	/**
	 * @return the clzId
	 */
	public Integer getClzId() {
		return clzId;
	}

	/**
	 * @param clzId
	 *            the clzId to set
	 */
	public void setClzId(Integer clzId) {
		this.clzId = clzId;
	}

	/**
	 * @return the clzName
	 */
	public String getClzName() {
		return clzName;
	}

	/**
	 * @param clzName
	 *            the clzName to set
	 */
	public void setClzName(String clzName) {
		this.clzName = clzName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppClz [clzId=");
		builder.append(clzId);
		builder.append(", clzName=");
		builder.append(clzName);
		builder.append("]");
		return builder.toString();
	}

	public AppClz(Integer clzId) {
		super();
		this.clzId = clzId;
	}

	//
}
