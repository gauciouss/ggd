package ggd.dispatcher.main;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ggd.core.CoreException;
import ggd.core.dispatcher.Dispatcher;

@Component("main.login")
public class LoginDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(IndexDispatcher.class);

	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		
	}

}
