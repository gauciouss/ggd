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

	@Column(name = "icon_path")
	private String iconPath;

	/**
	 * @return the clzId
	 */
	public Integer getClzId() {
		return clzId;
	}

	/**
	 * @return the clzName
	 */
	public String getClzName() {
		return clzName;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
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
		builder.append(", iconPath=");
		builder.append(iconPath);
		builder.append("]");
		return builder.toString();
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
		result = prime * result + ((clzId == null) ? 0 : clzId.hashCode());
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
		AppClz other = (AppClz) obj;
		if (clzId == null) {
			if (other.clzId != null)
				return false;
		} else if (!clzId.equals(other.clzId))
			return false;
		return true;
	}

}
