package tbox.dispatcher.main;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import baytony.util.Util;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;

@Component("main.quickPanel")
public class QuickPanelDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(QuickPanelDispatcher.class);

	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		String action = request.getParameter(Constant.ACTION_TYPE);		
		action = Util.isEmpty(action) ? "index" : action;
		log.trace("START: {}.handler(), action: {}", this.getClass(), action);
		switch(action) {
			case "edit":
				doEdit(view, request);
				break;			
			case "save":
				doEdit(view, request);
				break;
			case "confirm":
				doConfirm(view, request);
				break;
			case "index":
				doIndex(view, request);
				break;			
			default:
				doIndex(view, request);
				break;
		}
		log.info("END: {}.handler(), action: {}, exec TIME: {} ms.", this.getClass(), action, p.executeTime());
		
	}
	
	private void doConfirm(ModelAndView view, HttpServletRequest request) {
		//TODO
	}
	
	private void doEdit(ModelAndView view, HttpServletRequest request) {
		//TODO
	}
	
	private void doIndex(ModelAndView view, HttpServletRequest request) {
		//TODO
	}

}
