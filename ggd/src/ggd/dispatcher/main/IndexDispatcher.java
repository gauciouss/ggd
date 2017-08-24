/**
 * 
 */
package ggd.dispatcher.main;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ggd.core.CoreException;
import ggd.core.dispatcher.Dispatcher;

/**
 * @author admin
 *
 */
@Component("main.index")
public class IndexDispatcher implements Dispatcher {

	/* (non-Javadoc)
	 * @see ggd.core.dispatcher.Dispatcher#handler(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		// TODO Auto-generated method stub

	}

}
