package tbox.data.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ggd.auth.vo.AdmGroup;

@Entity
@Table(name = "company")
public class Company implements Serializable {

	private static final long serialVersionUID = 2790177970557710941L;

	@Id
	@Column(name = "company_id")
	private String cid;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "area_id", insertable = false, updatable = false)
	private Area area;

	@Column(name = "logo_url")
	private String logoURL;

	@Column(name = "background_url")
	private String backgroundURL;

	@Column(name = "fast_key1")
	private String fastKey1;

	@Column(name = "fast_key2")
	private String fastKey2;

	@Column(name = "fast_key3")
	private String fastKey3;

	@Column(name = "fast_key4")
	private String fastKey4;

	@OneToOne
	@JoinColumn(name = "group_id")
	private AdmGroup group;

	public Company() {
	}

	public Company(String cid, String name, Area area, String logoURL, String backgroundURL, String fastKey1,
			String fastKey2, String fastKey3, String fastKey4, AdmGroup group) {
		super();
		this.cid = cid;
		this.name = name;
		this.area = area;
		this.logoURL = logoURL;
		this.backgroundURL = backgroundURL;
		this.fastKey1 = fastKey1;
		this.fastKey2 = fastKey2;
		this.fastKey3 = fastKey3;
		this.fastKey4 = fastKey4;
		this.group = group;
	}

	/**
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * @param cid
	 *            the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * @return the logoURL
	 */
	public String getLogoURL() {
		return logoURL;
	}

	/**
	 * @param logoURL
	 *            the logoURL to set
	 */
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	/**
	 * @return the backgroundURL
	 */
	public String getBackgroundURL() {
		return backgroundURL;
	}

	/**
	 * @param backgroundURL
	 *            the backgroundURL to set
	 */
	public void setBackgroundURL(String backgroundURL) {
		this.backgroundURL = backgroundURL;
	}

	/**
	 * @return the fastKey1
	 */
	public String getFastKey1() {
		return fastKey1;
	}

	/**
	 * @param fastKey1
	 *            the fastKey1 to set
	 */
	public void setFastKey1(String fastKey1) {
		this.fastKey1 = fastKey1;
	}

	/**
	 * @return the fastKey2
	 */
	public String getFastKey2() {
		return fastKey2;
	}

	/**
	 * @param fastKey2
	 *            the fastKey2 to set
	 */
	public void setFastKey2(String fastKey2) {
		this.fastKey2 = fastKey2;
	}

	/**
	 * @return the fastKey3
	 */
	public String getFastKey3() {
		return fastKey3;
	}

	/**
	 * @param fastKey3
	 *            the fastKey3 to set
	 */
	public void setFastKey3(String fastKey3) {
		this.fastKey3 = fastKey3;
	}

	/**
	 * @return the fastKey4
	 */
	public String getFastKey4() {
		return fastKey4;
	}

	/**
	 * @param fastKey4
	 *            the fastKey4 to set
	 */
	public void setFastKey4(String fastKey4) {
		this.fastKey4 = fastKey4;
	}

	/**
	 * @return the group
	 */
	public AdmGroup getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(AdmGroup group) {
		this.group = group;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Company [cid=");
		builder.append(cid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", area=");
		builder.append(area);
		builder.append(", logoURL=");
		builder.append(logoURL);
		builder.append(", backgroundURL=");
		builder.append(backgroundURL);
		builder.append(", fastKey1=");
		builder.append(fastKey1);
		builder.append(", fastKey2=");
		builder.append(fastKey2);
		builder.append(", fastKey3=");
		builder.append(fastKey3);
		builder.append(", fastKey4=");
		builder.append(fastKey4);
		builder.append(", group=");
		builder.append(group);
		builder.append("]");
		return builder.toString();
	}

}
