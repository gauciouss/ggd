package tbox.dispatcher.action.service.command;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import ggd.core.common.Constant;
import ggd.core.util.WebUtil;
import tbox.TBoxException;
import tbox.core.TBoxData;
import tbox.core.TBoxInfo;
import tbox.dispatcher.action.service.command.entity.RegisterInfoEntity;
import tbox.service.TBoxService;

@Component("register")
public class RegisterCommand implements Command {
	
	private final static Logger log = LoggerFactory.getLogger(RegisterCommand.class);
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;

	@Override
	public void execute(ModelAndView view, HttpServletRequest request, TBoxData tbox) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.execute(), tbox: {}", this.getClass(), tbox);
		TBoxInfo info = tbox.getTBoxInfo();		
		RegisterInfoEntity entity = null;
		long time = service.activeMachine(info.getMachineSN(), info.getMAC(), info.getWIFIMAC(), WebUtil.getClientIpAddr(request));
		entity = new RegisterInfoEntity("1", "啟動成功", String.valueOf(time));
		log.debug("tbox: {}, entity: {}", tbox, entity);
		view.addObject(Constant.JSON_RESPONSE, entity);
		log.info("END: {}.execute(), tbox: {}, exec TIME: {}", this.getClass(), tbox, p.executeTime());
	}
}
