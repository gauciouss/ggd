/**
 * 
 */
package tbox.dispatcher.main;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;

/**
 * @author admin
 *
 */
@Component("main.index")
public class IndexDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(IndexDispatcher.class);

	/* (non-Javadoc)
	 * @see ggd.core.dispatcher.Dispatcher#handler(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		Object obj = request.getSession().getAttribute(Constant.USER);
		log.trace("START: {}.handler(), user obj: {}", this.getClass(), obj);
		if(obj == null) { 
			view.setViewName("main/login");
		}
		log.info("END: {}.handler(), user: {}, exec TIME: {} ms.", this.getClass(), obj, p.executeTime());
	}

}
