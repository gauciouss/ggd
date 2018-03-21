package tbox.dispatcher.action.service.command.entity;

import java.io.Serializable;

import tbox.data.vo.Area;

public class AreaEntity implements Serializable {

	private static final long serialVersionUID = -1453789522185540723L;
	private String areaId;
	private String areaName;
	
	public AreaEntity(Area area) {
		this.areaId = area.getAreaId().toString();
		this.areaName = area.getAreaName();
	}

	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AreaEntity [areaId=");
		builder.append(areaId);
		builder.append(", areaName=");
		builder.append(areaName);
		builder.append("]");
		return builder.toString();
	}
	
	
}
