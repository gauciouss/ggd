/**
 * 
 */
package tbox.dispatcher.action.service.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import tbox.TBoxException;
import tbox.core.TBoxData;
import tbox.core.TBoxInfo;
import tbox.data.vo.KVEntity;
import tbox.data.vo.MachineBox;
import tbox.service.TBoxService;

/**
 * @author admin
 *
 */
@Component("IndexInfoCommand")
public class IndexInfoCommand implements Command {
	
	private final static Logger log = LoggerFactory.getLogger(RegisterCommand.class);
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;

	/* (non-Javadoc)
	 * @see tbox.dispatcher.action.service.command.Command#execute(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest, tbox.core.TBoxData)
	 */
	@Override
	public void execute(ModelAndView view, HttpServletRequest request, TBoxData tbox) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.execute(), tbox: {}", this.getClass(), tbox);
		TBoxInfo info = tbox.getTBoxInfo();
		
		
		log.info("END: {}.execute(), tbox: {}, exec TIME: {}", this.getClass(), tbox, p.executeTime());
	}
	
	private List<KVEntity> getKV2(TBoxInfo box) throws TBoxException {
		return service.findKVsByMachine(box.getMachineSN(), box.getMAC(), box.getWIFIMAC(), 2);
	}
	
	private List<KVEntity> getKV1(TBoxInfo box) throws TBoxException {
		return service.findKVsByMachine(box.getMachineSN(), box.getMAC(), box.getWIFIMAC(), 1);
	}
	
	private tbox.proxy.cwb.gov.tw.OpendataAPI.Entity getWeather(TBoxInfo box) throws TBoxException {
		return service.getWeatherReport(box.getMachineSN(), box.getMAC(), box.getWIFIMAC());
	}
	
}
