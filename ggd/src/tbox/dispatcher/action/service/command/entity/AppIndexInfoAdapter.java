package tbox.dispatcher.action.service.command.entity;

import java.util.List;

import tbox.data.vo.AppClz;
import tbox.dispatcher.action.service.command.entity.IndexInfoAdapter.App;
import tbox.dispatcher.action.service.command.entity.IndexInfoAdapter.KVS;

public class AppIndexInfoAdapter {

	private KVS kv;
	private List<AppClz> kinds;
	private List<App> apps;

	public AppIndexInfoAdapter(KVS kv, List<AppClz> kinds, List<App> apps) {
		super();
		this.kv = kv;
		this.kinds = kinds;
		this.apps = apps;
	}


	/**
	 * @return the kv
	 */
	public KVS getKv() {
		return kv;
	}

	/**
	 * @return the kinds
	 */
	public List<AppClz> getKinds() {
		return kinds;
	}

	/**
	 * @return the apps
	 */
	public List<App> getApps() {
		return apps;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppIndexInfoAdapter [kv=");
		builder.append(kv);
		builder.append(", kinds=");
		builder.append(kinds);
		builder.append(", apps=");
		builder.append(apps);
		builder.append("]");
		return builder.toString();
	}	
}
