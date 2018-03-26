package tbox.dispatcher.action.service.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import ggd.core.common.Constant;
import tbox.TBoxException;
import tbox.core.TBoxData;
import tbox.core.TBoxInfo;
import tbox.data.vo.AppEntity;
import tbox.data.vo.KVEntity;
import tbox.dispatcher.action.service.command.entity.AppIndexInfoAdapter;
import tbox.dispatcher.action.service.command.entity.IndexInfoAdapter.App;
import tbox.dispatcher.action.service.command.entity.IndexInfoAdapter.KVS;
import tbox.dispatcher.action.service.command.entity.IndexInfoAdapter.KVS.KV;
import tbox.service.TBoxService;

@Component("apps.index")
public class AppIndexCommand implements Command {
	
	private final static Logger log = LoggerFactory.getLogger(AppIndexCommand.class);
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;
	
	@Autowired
	@Qualifier("FILE_SERVER_PATH")
	private String fileServerPath;

	/* (non-Javadoc)
	 * @see tbox.dispatcher.action.service.command.Command#execute(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest, tbox.core.TBoxData)
	 */
	@Override
	public void execute(ModelAndView view, HttpServletRequest request, TBoxData tbox) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.execute().", this.getClass());
		TBoxInfo box = tbox.getTBoxInfo();
		KVS kvs = new KVS(getKV3(box), getKV5(box));
		AppIndexInfoAdapter adapter = new AppIndexInfoAdapter(kvs, service.findAllAppKind(), this.getApps(box));
		view.addObject(Constant.JSON_RESPONSE, adapter);
		log.info("END: {}.execute(), exec TIME: {} ms.", this.getClass(), p.executeTime());
	}
	
	private List<App> getApps(TBoxInfo box) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getControlApp(), box: {}", this.getClass(), box);
		String ein = service.findEINByMachine(box.getMachineSN(), box.getMAC(), box.getWIFIMAC());		
		List<AppEntity> entities = service.findAppsWithLastVersion(ein);
		List<App> apps = new ArrayList<App>();
		for(AppEntity entity : entities) {
			apps.add(new App(entity, fileServerPath));
		}
		log.info("END: {}.getControlApp(), box: {}, exec TIME: {} ms.", this.getClass(), box, p.executeTime());
		return apps;
	}
	
	/**
	 * 取得市集廣告左
	 * @param box
	 * @return
	 * @throws TBoxException
	 */
	private List<KV> getKV3(TBoxInfo box) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getKV2(), box: {}", this.getClass(), box);
		List<KVEntity> entities = service.findKVsByMachine(box.getMachineSN(), box.getMAC(), box.getWIFIMAC(), 3);
		List<KV> list = new ArrayList<KV>();
		for(KVEntity entity : entities) {
			list.add(new KV(entity));
		}
		log.info("END: {}.getKV2(), box: {}, exec TIME: {} ms.", this.getClass(), box, p.executeTime());
		return list;
	}
	
	/**
	 * 取得市集廣告又右
	 * @param box
	 * @return
	 * @throws TBoxException
	 */
	private List<KV> getKV5(TBoxInfo box) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getKV1(), box: {}", this.getClass(), box);
		List<KVEntity> entities = service.findKVsByMachine(box.getMachineSN(), box.getMAC(), box.getWIFIMAC(), 5);
		List<KV> list = new ArrayList<KV>();
		for(KVEntity entity : entities) {
			list.add(new KV(entity));
		}
		log.info("END: {}.getKV1(), box: {}, exec TIME: {} ms.", this.getClass(), box, p.executeTime());
		return list;
	}

}
