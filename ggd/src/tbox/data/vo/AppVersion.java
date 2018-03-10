package tbox.data.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_version")
public class AppVersion {

	@EmbeddedId
	private PK pk;
	
	@Column(name = "publish_time")
	private Timestamp publishTime;
	
	@Column(name = "link")
	private String link;
	
	@Embeddable
	public static class PK implements Serializable {
		
		@Column(name = "app_id")
		private String appId;
		
		@Column(name = "version")
		private String version;

		/**
		 * @return the appId
		 */
		public String getAppId() {
			return appId;
		}

		/**
		 * @param appId the appId to set
		 */
		public void setAppId(String appId) {
			this.appId = appId;
		}

		/**
		 * @return the version
		 */
		public String getVersion() {
			return version;
		}

		/**
		 * @param version the version to set
		 */
		public void setVersion(String version) {
			this.version = version;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("PK [appId=");
			builder.append(appId);
			builder.append(", version=");
			builder.append(version);
			builder.append("]");
			return builder.toString();
		}

		public PK(String appId, String version) {
			super();
			this.appId = appId;
			this.version = version;
		}
		
		
	}
}
