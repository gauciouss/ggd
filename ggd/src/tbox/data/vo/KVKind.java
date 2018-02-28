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
	
	public KVKind() {
	}

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
		builder.append("]");
		return builder.toString();
	}

	public KVKind(Integer kind) {
		super();
		this.kind = kind;
	}

}
