package tbox.dispatcher.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.org.apache.commons.codec.binary.Base64;
import baytony.util.Profiler;
import baytony.util.StringUtil;
import baytony.util.Util;
import ggd.auth.AuthService;
import ggd.auth.vo.AdmGroup;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import tbox.TBoxException;
import tbox.data.vo.Area;
import tbox.data.vo.Company;
import tbox.data.vo.CompanyEntity;
import tbox.service.TBoxService;

@Component("main.comp")
public class CompanyDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(CompanyDispatcher.class);
	
	public static final String ALL_APPROVED_GROUPS = CompanyDispatcher.class + "_GROUP";
	public static final String ALL_AREA = CompanyDispatcher.class + "_AREA";
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;
	
	@Autowired
	@Qualifier("AuthService")
	private AuthService authService;
	
	@Autowired
	@Qualifier("IMG_PHYSICAL_PATH")
	private String physicalPath;
	

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
				doEdit(view, request);
				break;
			case "confirm":
				doConfirm(view, request);
				break;
			case "showIMG":
				doShowIMG(view, request);
				break;
			case "index":
			default:
				doIndex(view, request);
				break;
		}
		log.info("END: {}.handler(), action: {}, exec TIME: {} ms.", this.getClass(), action, p.executeTime());
	}
	
	private void doShowIMG(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		String EIN = request.getParameter("EIN");
		String type = request.getParameter("type");
		FileInputStream fis = null;
		try {
			Company comp = service.findCompanyByEIN(EIN);
			String path = Constant.EMPTY;
			switch(type) {
				case "logo":
					path = comp.getLogoURL();
					break;
				case "bg":
					path = comp.getBackgroundURL();
					break;
			}
			File f = new File(path);
			fis = new FileInputStream(f);
			byte[] bs = new byte[(int) f.length()];
			fis.read(bs);
			view.addObject(Constant.BASE64, Base64.encodeBase64String(bs));
			view.setViewName("common/image");
			
		} catch (IOException | TBoxException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error(StringUtil.getStackTraceAsString(e));
				}
			}
		}
		log.trace("START: {}.doShowIMG(), EIN: {}, type: {}, exec TIME: {} ms.", this.getClass(), EIN, type, p.executeTime());
	}
	
	private void doConfirm(ModelAndView view, HttpServletRequest request) {
//		Profiler p = new Profiler();
//		String account = request.getParameter("account");
//		String password = request.getParameter("password");
//		String name = request.getParameter("name");
//		String email = request.getParameter("email");
//		String address = request.getParameter("address");
//		String tel = request.getParameter("tel");
//		String phone = request.getParameter("phone");
//		String group = request.getParameter("group");
//		String isEnabled = request.getParameter("isEnabled");
//		String isApproved = request.getParameter("isApproved");
//		log.debug("START: {}.doConfirm(), account: {}, password: {}, name: {}, email: {}, address: {}, tel: {}, phone: {}, group: {}, isEnabled: {}, isApproved: {}", this.getClass(), account, password, name, email, address, tel, phone, group, isEnabled, isApproved);
//		try {
//			AdmUser user = service.findUserById(account);
//			if(user == null) {
//				service.addUser(new AdmUser(account, password, name, email, address, tel, phone, new Timestamp(System.currentTimeMillis()), null, Integer.parseInt(isEnabled) == 1, Integer.parseInt(isApproved) == 1, service.findGroup(group)));
//			}
//			else {
//				service.updateUser(account, password, name, email, address, tel, phone, group, Integer.parseInt(isEnabled) == 1, Integer.parseInt(isApproved) == 1);
//			}	
//			view.addObject(Constant.ACTION_RESULT, "1");
//			log.info("END: {}.doConfirm(), account: {}, password: {}, name: {}, email: {}, address: {}, tel: {}, phone: {}, group: {}, isEnabled: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), account, password, name, email, address, tel, phone, group, isEnabled, isApproved, p.executeTime());
//		}
//		catch(AuthException e) {
//			view.addObject(Constant.ACTION_RESULT, "0");
//			log.error(StringUtil.getStackTraceAsString(e));
//		}
//		catch(Exception e) {
//			view.addObject(Constant.ACTION_RESULT, "0");
//			log.error(StringUtil.getStackTraceAsString(e));
//		}
//		this.doIndex(view, request);
	}
	
	
	
	private void doEdit(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();		
		String EIN = request.getParameter("EIN"); 
		log.trace("START: {}.deEdit(), EIN: {}", this.getClass(), EIN);
		try {
			Company comp = Util.isEmpty(EIN) ? new Company() : service.findCompanyByEIN(EIN);
			List<AdmGroup> groups = authService.findAllGroup(true, true);
			List<Area> areas = service.findAllArea();
			view.addObject(Constant.DATA_LIST, comp);
			view.addObject(ALL_APPROVED_GROUPS, groups);
			view.addObject(ALL_AREA, areas);
			view.setViewName("comp/edit");
		}
		catch(TBoxException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doEdit(), EIN: {}, exec TIME: {} ms.", this.getClass(), EIN, p.executeTime());
	}
	
	
	private void doIndex(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		log.trace("START: {}.doIndex()", this.getClass());
		try {
			List<CompanyEntity> users = service.findAllComp();
			log.debug("users: {}", users);
			view.addObject(Constant.DATA_LIST, users);
			view.setViewName("comp/index");
		}
		catch(TBoxException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doIndex(), exec TIME: {} ms.", this.getClass(), p.executeTime());
	}
}
