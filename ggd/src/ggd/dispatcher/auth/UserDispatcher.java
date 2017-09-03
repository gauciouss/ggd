package ggd.dispatcher.auth;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import ggd.auth.AuthService;
import ggd.auth.vo.AdmGroup;
import ggd.auth.vo.AdmUser;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import ggd.core.util.StandardUtil;

@Component("auth.user")
public class UserDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(UserDispatcher.class);
	
	public static final String ALL_APPROVED_GROUPS = UserDispatcher.class + "_GROUP";
	
	@Autowired
	@Qualifier("AuthService")
	private AuthService service;
	
	@Autowired
	@Qualifier("DEFAULT_PAGE_SIZE")
	private Integer defaultPageSize;

	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		String action = request.getParameter(Constant.ACTION_TYPE);
		action = StringUtil.isEmptyString(action) ? "index" : action;
		log.trace("START: {}.handler(), action: {}", this.getClass(), action);
		switch(action) {
			case "edit":
				doEdit(view, request);
				break;			
			case "save":
				break;
			case "confirm":
				doConfirm(view, request);
				break;
			case "search":
				doSearch(view, request);
				break;				
			case "index":
			default:
				doIndex(view, request);
				break;
		}
		log.info("END: {}.handler(), action: {}, exec TIME: {} ms.", this.getClass(), action, p.executeTime());
	}
	
	private void doConfirm(ModelAndView view, HttpServletRequest request) {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String tel = request.getParameter("tel");
		String phone = request.getParameter("phone");
		String group = request.getParameter("group");
		String isEnabled = request.getParameter("isEnabled");
		String isApproved = request.getParameter("isApproved");
		log.debug("START: {}.doConfirm(), account: {}, password: {}, name: {}, email: {}, address: {}, tel: {}, phone: {}, group: {}, isEnabled: {}, isApproved: {}", this.getClass(), account, password, name, email, address, tel, phone, group, isEnabled, isApproved);
		service.updateUser(account, password, name, email, address, tel, phone, group);
		this.doIndex(view, request);
	}
	
	private void doEdit(ModelAndView view, HttpServletRequest request) {
		String account = request.getParameter("account"); 
		AdmUser user = service.findUserById(account);
		List<AdmGroup> groups = service.findAllGroup(true, true);
		view.addObject(Constant.DATA_LIST, user);
		view.addObject(ALL_APPROVED_GROUPS, groups);
		view.setViewName("user/edit");
	}
	
	private void doSearch(ModelAndView view, HttpServletRequest request) {
		String account = request.getParameter("account");
		List<AdmUser> users = service.findUsers(account);
		view.addObject(Constant.DATA_LIST, users);
		view.addObject(Constant.PAGE, 1);
		view.setViewName("user/index");
	}
	
	private void doIndex(ModelAndView view, HttpServletRequest request) {		
		String page = request.getParameter(Constant.PAGE);
		int iPage = StandardUtil.toInteger(page);
		iPage = iPage == 0 ? 1 : iPage;
		List<AdmUser> users = service.findUsers(iPage, defaultPageSize);
		view.addObject(Constant.DATA_LIST, users);
		view.addObject(Constant.PAGE, iPage);
		view.setViewName("user/index");
	}

	
}
