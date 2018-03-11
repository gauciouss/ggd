package tbox.dispatcher.action.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import tbox.core.TBoxData;
import tbox.core.TBoxInfo;
import tbox.dispatcher.action.service.command.AreaCommand;
import tbox.dispatcher.action.service.command.Command;
import tbox.dispatcher.action.service.command.IndexInfoCommand;
import tbox.dispatcher.action.service.command.RegisterCommand;

@Component("service.Functional")
public class FunctionalDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(FunctionalDispatcher.class);
	
//	@Autowired
//	@Qualifier("register")
//	private RegisterCommand registerCmd;
//	
//	@Autowired
//	@Qualifier("home.index")
//	private IndexInfoCommand indexCmd;
//	
//	@Autowired
//	@Qualifier("home.area")
//	private AreaCommand areaCmd;
	
	@Autowired
	private ApplicationContext context;

	/* (non-Javadoc)
	 * @see ggd.core.dispatcher.Dispatcher#handler(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		TBoxData tbox = (TBoxData) view.getModel().get(Constant.DISPATCH_DATA);
		TBoxInfo info = tbox.getTBoxInfo();
		log.trace("START: {}.handler(), tbox: {}", this.getClass(), tbox);
		Command cmd = context.getBean(info.getAction(), Command.class);
		cmd.execute(view, request, tbox);
//		switch(info.getAction()) {
//			case "register":
//				registerCmd.execute(view, request, tbox);
//				break;
//			case "home.index":
//				indexCmd.execute(view, request, tbox);
//				break;
//			case "home.area":
//				areaCmd.execute(view, request, tbox);
//				break;
//			case "apps.index":
//				break;
//		}
		log.info("END: {}.handler(), tbox: {}, exec TIME: {} ms.", this.getClass(), tbox, p.executeTime());
	}

}
