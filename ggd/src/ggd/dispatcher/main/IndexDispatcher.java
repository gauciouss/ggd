/**
 * 
 */
package ggd.dispatcher.main;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import ggd.auth.dao.AdmUserDao;
import ggd.auth.vo.AdmUser;
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
		Object obj = request.getSession().getAttribute(Constant.USER_INFO);
		log.trace("START: {}.handler(), user obj: {}", this.getClass(), obj);
		AdmUser user = null;
		if(obj != null) { 
			user = (AdmUser) obj;
		}
		else {
			view.setViewName("main/login");
		}
	}

}
