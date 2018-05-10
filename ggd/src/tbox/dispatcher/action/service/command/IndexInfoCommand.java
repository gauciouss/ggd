/**
 * 
 */
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
import baytony.util.Util;
import ggd.core.common.Constant;
import tbox.TBoxException;
import tbox.core.TBoxData;
import tbox.core.TBoxInfo;
import tbox.data.vo.AppEntity;
import tbox.data.vo.KVEntity;
import tbox.dispatcher.action.service.command.entity.IndexInfoAdapter;
import tbox.dispatcher.action.service.command.entity.IndexInfoAdapter.App;
import tbox.dispatcher.action.service.command.entity.IndexInfoAdapter.KVS;
import tbox.dispatcher.action.service.command.entity.IndexInfoAdapter.KVS.KV;
import tbox.dispatcher.action.service.command.entity.IndexInfoAdapter.Msg;
import tbox.dispatcher.action.service.command.entity.IndexInfoAdapter.Weather;
import tbox.service.TBoxService;

/**
 * @author admin
 *
 */
@Component("home.index")
public class IndexInfoCommand implements Command {
	
	private final static Logger log = LoggerFactory.getLogger(IndexInfoCommand.class);
	
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
		log.trace("START: {}.execute(), tbox: {}", this.getClass(), tbox);
		TBoxInfo box = tbox.getTBoxInfo();
		KVS kvs = new KVS(getKV1(box), getKV2(box));
		IndexInfoAdapter adapter = new IndexInfoAdapter(getControlApp(box), getIdxFastApp(box), getMsg(box), getMarquee(box), kvs, getWeather(box), getScreenProtection(box));
		view.addObject(Constant.JSON_RESPONSE, adapter);
		log.info("END: {}.execute(), tbox: {}, exec TIME: {}", this.getClass(), tbox, p.executeTime());
	}
	
	private List<App> getIdxFastApp(TBoxInfo box) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getControlApp(), box: {}", this.getClass(), box);
		List<AppEntity> entities = service.findIndexFastApp(box.getMachineSN(), box.getMAC(), box.getWIFIMAC());
		List<App> apps = new ArrayList<App>();
		if(!Util.isEmpty(entities)) {
			for(AppEntity entity : entities) {
				if(entity != null)
					apps.add(new App(entity, fileServerPath));
			}
		}
		
		log.info("END: {}.getControlApp(), box: {}, exec TIME: {} ms.", this.getClass(), box, p.executeTime());
		return apps;
	}
	
	private List<App> getControlApp(TBoxInfo box) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getControlApp(), box: {}", this.getClass(), box);
		List<AppEntity> entities = service.findControlPanelApp(box.getMachineSN(), box.getMAC(), box.getWIFIMAC());
		log.debug("AppEntity entities: {}", entities);
		List<App> apps = new ArrayList<App>();
		if(!Util.isEmpty(entities)) {
			for(AppEntity entity : entities) {
				if(entity != null)
					apps.add(new App(entity, fileServerPath));
			}	
		}
		log.info("END: {}.getControlApp(), box: {}, exec TIME: {} ms.", this.getClass(), box, p.executeTime());
		return apps;
	}
	
	private List<Msg> getMarquee(TBoxInfo box) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getMsg(), box: {}", this.getClass(), box);
		List<KVEntity> entities = service.findKVsByMachine(box.getMachineSN(), 4);
		List<Msg> list = new ArrayList<Msg>();
		for(KVEntity entity : entities) {
			list.add(new Msg(entity));
		}
		log.info("END: {}.getMsg(), box: {}, exec TIME: {} ms.", this.getClass(), box, p.executeTime());
		return list;
	}
	
	
	private List<Msg> getMsg(TBoxInfo box) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getMsg(), box: {}", this.getClass(), box);
		List<KVEntity> entities = service.findKVsByMachine(box.getMachineSN(), 6);
		List<Msg> list = new ArrayList<Msg>();
		for(KVEntity entity : entities) {
			list.add(new Msg(entity));
		}
		log.info("END: {}.getMsg(), box: {}, exec TIME: {} ms.", this.getClass(), box, p.executeTime());
		return list;
	}
	
	private String getScreenProtection(TBoxInfo box) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getScreenProtection(), box: {}", this.getClass(), box);
		List<KVEntity> entities = service.findKVsByMachine(box.getMachineSN(), 7);
		String path = Util.isEmpty(entities) ? "" : fileServerPath + entities.get(0).getImgPath();
		log.info("END: {}.getScreenProtection(), box: {}, path: {}, exec TIME: {} ms.", this.getClass(), box, path, p.executeTime());
		return path;
	}
	
	private List<KV> getKV2(TBoxInfo box) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getKV2(), box: {}", this.getClass(), box);
		List<KVEntity> entities = service.findKVsByMachine(box.getMachineSN(), 2);
		List<KV> list = new ArrayList<KV>();
		for(KVEntity entity : entities) {
			list.add(new KV(entity, fileServerPath));
		}
		log.info("END: {}.getKV2(), box: {}, exec TIME: {} ms.", this.getClass(), box, p.executeTime());
		return list;
	}
	
	private List<KV> getKV1(TBoxInfo box) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getKV1(), box: {}", this.getClass(), box);
		List<KVEntity> entities = service.findKVsByMachine(box.getMachineSN(), 1);
		List<KV> list = new ArrayList<KV>();
		for(KVEntity entity : entities) {
			list.add(new KV(entity, fileServerPath));
		}
		log.info("END: {}.getKV1(), box: {}, exec TIME: {} ms.", this.getClass(), box, p.executeTime());
		return list;
	}
	
	private Weather getWeather(TBoxInfo box) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.getWeather(), box: {}", this.getClass(), box);
		tbox.proxy.cwb.gov.tw.OpendataAPI.Entity entity = service.findWeatherReport(box.getMachineSN());
		Weather weather = new Weather(entity, fileServerPath);		
		log.info("END: {}.getWeather(), box: {}, exec TIME: {} ms.", this.getClass(), box, p.executeTime());
		return weather;
	}
	
}
