package tbox.dispatcher.action.service.command.entity;

import java.io.Serializable;
import java.util.List;

import tbox.data.vo.AppEntity;
import tbox.data.vo.KVEntity;
import tbox.proxy.cwb.gov.tw.OpendataAPI;

public class IndexInfoAdapter implements Serializable {

	private static final long serialVersionUID = 3021172280798396302L;

	private List<App> controlPanel;
	
	private List<App> idxFastApp;

	private List<Msg> msg;

	private KVS kv;

	private Weather weather;

	public IndexInfoAdapter(List<App> controlPanel, List<App> idxFastApp, List<Msg> msg, KVS kv, Weather weather) {
		super();
		this.controlPanel = controlPanel;
		this.msg = msg;
		this.kv = kv;
		this.weather = weather;
		this.idxFastApp = idxFastApp;
	}


	/**
	 * @return the controlPanel
	 */
	public List<App> getControlPanel() {
		return controlPanel;
	}
	
	

	/**
	 * @return the idxFastApp
	 */
	public List<App> getIdxFastApp() {
		return idxFastApp;
	}



	/**
	 * @return the msg
	 */
	public List<Msg> getMsg() {
		return msg;
	}

	/**
	 * @return the kv
	 */
	public KVS getKv() {
		return kv;
	}

	/**
	 * @return the weather
	 */
	public Weather getWeather() {
		return weather;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IndexInfoAdapter [controlPanel=");
		builder.append(controlPanel);
		builder.append(", idxFastApp=");
		builder.append(idxFastApp);
		builder.append(", msg=");
		builder.append(msg);
		builder.append(", kv=");
		builder.append(kv);
		builder.append(", weather=");
		builder.append(weather);
		builder.append("]");
		return builder.toString();
	}

	public static class App implements Serializable {

		private static final long serialVersionUID = 1741127693902869474L;
		private String appId;
		private String clzId;
		private String clzName;
		private String name;
		private String link;
		private String version;
		private long publishTime;
		private String pkgName;
		private String iconPath;

		public App(AppEntity entity, String physicalPath) {
			this.appId = entity.getAppId();
			this.clzId = String.valueOf(entity.getClzId());
			this.clzName = entity.getClzName();
			this.name = entity.getName();
			this.link = physicalPath + entity.getLink();
			this.version = entity.getVersion();
			this.publishTime = entity.getPublishTime().getTime();
			this.pkgName = entity.getPkgName();
			this.iconPath = physicalPath + entity.getIconPath();
		}
		
		public String getIconPath() {
			return iconPath;
		}
		
		/**
		 * @return the appId
		 */
		public String getAppId() {
			return appId;
		}

		/**
		 * @return the clzId
		 */
		public String getClzId() {
			return clzId;
		}

		/**
		 * @return the clzName
		 */
		public String getClzName() {
			return clzName;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the link
		 */
		public String getLink() {
			return link;
		}

		/**
		 * @return the version
		 */
		public String getVersion() {
			return version;
		}

		/**
		 * @return the publishTime
		 */
		public long getPublishTime() {
			return publishTime;
		}

		/**
		 * @return the pkgName
		 */
		public String getPkgName() {
			return pkgName;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("App [appId=");
			builder.append(appId);
			builder.append(", clzId=");
			builder.append(clzId);
			builder.append(", clzName=");
			builder.append(clzName);
			builder.append(", name=");
			builder.append(name);
			builder.append(", link=");
			builder.append(link);
			builder.append(", version=");
			builder.append(version);
			builder.append(", publishTime=");
			builder.append(publishTime);
			builder.append(", pkgName=");
			builder.append(pkgName);
			builder.append(", iconPath=");
			builder.append(iconPath);
			builder.append("]");
			return builder.toString();
		}

	}

	public static class KVS implements Serializable {

		private static final long serialVersionUID = 1201835872934806362L;

		private List<KV> kv1;

		private List<KV> kv2;

		public KVS(List<KV> kv1, List<KV> kv2) {
			super();
			this.kv1 = kv1;
			this.kv2 = kv2;
		}

		/**
		 * @return the serialversionuid
		 */
		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		/**
		 * @return the kv1
		 */
		public List<KV> getKv1() {
			return kv1;
		}

		/**
		 * @return the kv2
		 */
		public List<KV> getKv2() {
			return kv2;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("KVS [kv1=");
			builder.append(kv1);
			builder.append(", kv2=");
			builder.append(kv2);
			builder.append("]");
			return builder.toString();
		}

		public static class KV implements Serializable {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5555476467348738012L;
			private String link;
			private String imgPath;
			private String msg;
			private String serialNo;

			public KV(KVEntity entity) {
				super();
				this.link = entity.getClickLink();
				this.imgPath = entity.getImgPath();
				this.msg = entity.getMsg();
				this.serialNo = entity.getSerialNo().toString();
			}

			public KV(String link, String imgPath, String msg, String serialNo) {
				super();
				this.link = link;
				this.imgPath = imgPath;
				this.msg = msg;
				this.serialNo = serialNo;
			}

			/**
			 * @return the link
			 */
			public String getLink() {
				return link;
			}

			/**
			 * @return the imgPath
			 */
			public String getImgPath() {
				return imgPath;
			}

			/**
			 * @return the msg
			 */
			public String getMsg() {
				return msg;
			}

			/**
			 * @return the serialNo
			 */
			public String getSerialNo() {
				return serialNo;
			}

		}
	}

	public static class Msg implements Serializable {

		private static final long serialVersionUID = 8639158514594118179L;
		private String text;
		private long publishTime;

		public Msg(KVEntity entity) {
			super();
			this.text = entity.getMsg();
			this.publishTime = entity.getStartDate().getTime();
		}

		public Msg(String text, long publishTime) {
			super();
			this.text = text;
			this.publishTime = publishTime;
		}

		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}

		/**
		 * @return the publishTime
		 */
		public long getPublishTime() {
			return publishTime;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Msg [text=");
			builder.append(text);
			builder.append(", publishTime=");
			builder.append(publishTime);
			builder.append("]");
			return builder.toString();
		}

	}

	public static class Weather implements Serializable {

		private static final long serialVersionUID = 3891752179481472771L;
		private String location;
		private String desc;
		private String today;
		private String future;
		private String icon;
		private String fileServerPath;

		public Weather(OpendataAPI.Entity entity, String fileServerPath) {
			super();
			this.location = entity.getLocationName();
			this.desc = entity.getDesc();
			this.today = entity.getToday();
			this.future = entity.getFuture();
			this.fileServerPath = fileServerPath;
		}

		public Weather(String location, String desc, String today, String future, String fileServerPath) {
			super();
			this.location = location;
			this.desc = desc;
			this.today = today;
			this.future = future;
			this.fileServerPath = fileServerPath;
		}

		/**
		 * @return the location
		 */
		public String getLocation() {
			return location;
		}

		/**
		 * @return the desc
		 */
		public String getDesc() {
			return desc;
		}

		/**
		 * @return the today
		 */
		public String getToday() {
			return today;
		}

		/**
		 * @return the future
		 */
		public String getFuture() {
			return future;
		}
		
		public String getIcon() {
			if(desc.contains("晴")) icon = fileServerPath + "/weather/sunny.png";
			else if(desc.contains("陰")) icon = fileServerPath + "/weather/cloudy.png";
			else if(desc.contains("雨")) icon = fileServerPath + "/weather/rain.png";
			return icon;
		}
				

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Weather [location=");
			builder.append(location);
			builder.append(", desc=");
			builder.append(desc);
			builder.append(", today=");
			builder.append(today);
			builder.append(", future=");
			builder.append(future);
			builder.append(", icon=");
			builder.append(getIcon());
			builder.append("]");
			return builder.toString();
		}

	}
}
