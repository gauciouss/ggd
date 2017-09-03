package ggd.dispatcher.main;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import ggd.auth.AuthService;
import ggd.auth.vo.AdmUser;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;

@Component("main.login")
public class LoginDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(IndexDispatcher.class);
	
	public static final String ACCOUNT = LoginDispatcher.class + "_ACCOUNT";
	public static final String PASSWORD = LoginDispatcher.class + "_PASSWORD";
	
	@Autowired
	@Qualifier("AuthService")
	private AuthService authService;
	

	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		log.trace("START: {}.handler()", this.getClass());
		String account = request.getParameter(ACCOUNT);
		String pwd = request.getParameter(PASSWORD);
		log.debug("account: {}, pwd: {}", account, pwd);
		AdmUser user = authService.authenticate(account, pwd);		
		if(user != null) {
			request.getSession().setAttribute(Constant.USER, user);
			view.setViewName("main/index");
		}
		log.info("END: {}.handler(), user: {}, exec TIME: {} ms.", this.getClass(), user, p.executeTime());
	}

}
