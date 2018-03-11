package tbox.data.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kv_kind")
public class KVKind implements Serializable {

	private static final long serialVersionUID = 2334048154205891299L;

	@Id
	@Column(name = "kind")
	private Integer kind;

	@Column(name = "kind_name")
	private String kindName;

	@Column(name = "icon_path")
	private String iconPath;

	/**
	 * @return the kind
	 */
	public Integer getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(Integer kind) {
		this.kind = kind;
	}

	/**
	 * @return the kindName
	 */
	public String getKindName() {
		return kindName;
	}

	/**
	 * @param kindName
	 *            the kindName to set
	 */
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * @param iconPath
	 *            the iconPath to set
	 */
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KVKind [kind=");
		builder.append(kind);
		builder.append(", kindName=");
		builder.append(kindName);
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
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
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
		KVKind other = (KVKind) obj;
		if (kind == null) {
			if (other.kind != null)
				return false;
		} else if (!kind.equals(other.kind))
			return false;
		return true;
	}

}
