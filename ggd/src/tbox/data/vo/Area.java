package tbox.data.vo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "area")
public class Area implements Serializable {

	private static final long serialVersionUID = -6805776139043048531L;

	@Id
	@Column(name = "area_id")
	private Integer areaId;

	@Column(name = "area_name")
	private String areaName;

	@OneToMany
	@JoinColumn(name = "parent_id", insertable = false, updatable = false)
	private Set<Area> citys;

	@Column(name = "sort")
	private Integer sort = 1;

	public Area() {
	}

	public Area(Integer areaId, String areaName, Set<Area> citys, Integer sort) {
		super();
		this.areaId = areaId;
		this.areaName = areaName;
		this.citys = citys;
		this.sort = sort;
	}

	/**
	 * @return the areaId
	 */
	public Integer getAreaId() {
		return areaId;
	}

	/**
	 * @param areaId
	 *            the areaId to set
	 */
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName
	 *            the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the citys
	 */
	public Set<Area> getCitys() {
		return citys;
	}

	/**
	 * @param citys
	 *            the citys to set
	 */
	public void setCitys(Set<Area> citys) {
		this.citys = citys;
	}

	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Area [areaId=");
		builder.append(areaId);
		builder.append(", areaName=");
		builder.append(areaName);
		builder.append(", citys=");
		builder.append(citys);
		builder.append(", sort=");
		builder.append(sort);
		builder.append("]");
		return builder.toString();
	}

}
