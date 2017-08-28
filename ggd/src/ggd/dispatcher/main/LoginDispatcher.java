package ggd.dispatcher.main;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ggd.core.CoreException;
import ggd.core.dispatcher.Dispatcher;

@Component("main.login")
public class LoginDispatcher implements Dispatcher {

	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		
	}

}
