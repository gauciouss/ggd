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
import ggd.core.common.Config;
import ggd.core.common.Constant;
import ggd.core.config.XML_Config;
import ggd.core.dispatcher.Dispatcher;

@Component("main.login")
public class LoginDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(IndexDispatcher.class);
	
	public static final String ACCOUNT = LoginDispatcher.class + "_ACCOUNT";
	public static final String PASSWORD = LoginDispatcher.class + "_PASSWORD";
	
	@Autowired
	@Qualifier("AuthService")
	private AuthService authService;
	
	@Autowired
	@Qualifier(XML_Config.DISPLAY_CONFIG)
	private Config display;
	
	@Autowired
	@Qualifier(XML_Config.COMMON_CONFIG)
	private Config common;

	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		log.trace("START: {}.handler()", this.getClass());
		String account = request.getParameter(ACCOUNT);
		String pwd = request.getParameter(PASSWORD);
		log.debug("account: {}, pwd: {}", account, pwd);
		AdmUser user = authService.authenticate(account, pwd);		
		if(user != null) {
			request.getSession().setAttribute(Constant.USER_INFO, user);
			view.setViewName("main/index");
		}
		view.addObject(Constant.DISPLAY_CONFIG, display);
		view.addObject(Constant.COMMON_CONFIG, common);
		log.info("END: {}.handler(), user: {}, exec TIME: {} ms.", this.getClass(), user, p.executeTime());
	}

}
