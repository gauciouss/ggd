package tbox.dispatcher.action.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import tbox.core.TBoxData;

@Component("service.Functional")
public class FunctionalDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(FunctionalDispatcher.class);

	/* (non-Javadoc)
	 * @see ggd.core.dispatcher.Dispatcher#handler(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		// TODO Auto-generated method stub
		Profiler p = new Profiler();
		TBoxData tbox = (TBoxData) view.getModel().get(Constant.DISPATCH_DATA);
		log.trace("START: {}.handler(), tbox: {}", this.getClass(), tbox);
	}

}
