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
import tbox.TBoxException;
import tbox.core.TBoxData;
import tbox.data.vo.OSVersion;
import tbox.service.TBoxService;

@Component("app.os.version")
public class OSVersionCommand implements Command {
	
	private final static Logger log = LoggerFactory.getLogger(OSVersionCommand.class);
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;

	@Override
	public void execute(ModelAndView view, HttpServletRequest request, TBoxData tbox) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.execute().", this.getClass());
		OSVersion version = service.getNewestOSVersion();
		view.addObject(Constant.JSON_RESPONSE, version);
		log.info("END: {}.execute(), exec TIME: {} ms.", this.getClass(), p.executeTime());
	}

}
