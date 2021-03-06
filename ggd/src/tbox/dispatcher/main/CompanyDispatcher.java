package tbox.dispatcher.main;

import java.io.File;
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
import baytony.util.Util;
import ggd.auth.AuthService;
import ggd.auth.vo.AdmGroup;
import ggd.auth.vo.AdmUser;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import ggd.core.util.StandardUtil;
import tbox.TBoxException;
import tbox.data.vo.AppEntity;
import tbox.data.vo.Area;
import tbox.data.vo.Company;
import tbox.data.vo.CompanyEntity;
import tbox.data.vo.FastApp;
import tbox.service.TBoxService;

@Component("main.comp")
public class CompanyDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(CompanyDispatcher.class);
	
	public static final String ALL_APPROVED_GROUPS = CompanyDispatcher.class + "_GROUP";
	public static final String ALL_AREA = CompanyDispatcher.class + "_AREA";
	public static final String LOGO_BASE64 = CompanyDispatcher.class + "_LOGO";
	public static final String BG_BASE64 = CompanyDispatcher.class + "_BG";
	public static final String FAST_INDEX_APPS = CompanyDispatcher.class + "_IDX_FAST_APPS";
	public static final String FAST_CTRL_PNL_APPS = CompanyDispatcher.class + "_CTRL_PNL_FAST_APPS";
	public static final String ALL_APPS = CompanyDispatcher.class + "_ALL_APPS";
	
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;
	
	@Autowired
	@Qualifier("AuthService")
	private AuthService authService;
	
	@Autowired
	@Qualifier("FILE_PHYSICAL_PATH")
	private String physicalPath;
	

	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		this.checkSessionAlive(view, request);
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
			case "index":
			default:
				doIndex(view, request);
				break;
		}
		log.info("END: {}.handler(), action: {}, exec TIME: {} ms.", this.getClass(), action, p.executeTime());
	}
	
	
	
	private void doConfirm(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		String EIN = request.getParameter("EIN");
		String compName = request.getParameter("compName");
		String area = request.getParameter("area");
		String group = request.getParameter("group");
		String logo = request.getParameter("logoB64");
		String bg = request.getParameter("bgB64");
		
		//首頁快捷APP
		String idxApp1 = request.getParameter("idxApp1");
		String idxApp2 = request.getParameter("idxApp2");
		String idxApp3 = request.getParameter("idxApp3");
		String idxApp4 = request.getParameter("idxApp4");
		
		//遙控器快捷APP
		String ctrlApp1 = request.getParameter("ctrlApp1");
		String ctrlApp2 = request.getParameter("ctrlApp2");
		String ctrlApp3 = request.getParameter("ctrlApp3");
		String ctrlApp4 = request.getParameter("ctrlApp4");
		
		log.trace("START: {}.doConfirm(), EIN: {}, compName: {}, area: {}, group: {}, idxApp1: {}, idxApp2: {}, idxApp3: {}, idxApp4: {}, ctrlApp1: {}, ctrlApp2: {}, ctrlApp3: {}, ctrlApp4: {}", this.getClass(), EIN, compName, area, group, idxApp1, idxApp2, idxApp3, idxApp4, ctrlApp1, ctrlApp2, ctrlApp3, ctrlApp4);
		
		if(Util.isEmpty(logo) || Util.isEmpty(bg)) {
			log.warn("******** logo or bg base64 value is empty !!!!!!!!!");
		}
		
		try {
			String imgPath = "images/" + EIN + "/";
			
			//硬碟實體路徑
			String imgAbsPath = physicalPath + "/" + imgPath;
			
			Company comp = service.findCompanyByEIN(EIN);
			log.debug("EIN: {}, comp is null ? {}", EIN, comp == null);
			if(comp == null) {
				log.debug("EIN: {}, do action: save.", EIN);
				if(!Util.isEmpty(logo))
					StandardUtil.writeBase64ToFile(logo, imgAbsPath, "logo.png");
				if(!Util.isEmpty(bg))
					StandardUtil.writeBase64ToFile(bg, imgAbsPath, "bg.png");
				//service.addCompany(EIN, compName, area, imgPath + "logo.png", imgPath + "bg.png", fk1, fk2, fk3, fk4, group);
				service.addCompany(EIN, compName, area, compName, imgPath + "bg.png", idxApp1, idxApp2, idxApp3, idxApp4, ctrlApp1, ctrlApp2, ctrlApp3, ctrlApp4, group);
			}
			else {
				log.debug("EIN: {}, do action: update.", EIN);
				if(!Util.isEmpty(logo))
					StandardUtil.writeBase64ToFile(logo, imgAbsPath, "logo.png");
				if(!Util.isEmpty(bg))
					StandardUtil.writeBase64ToFile(bg, imgAbsPath, "bg.png");
				service.updateCompInfo(EIN, compName, area, imgPath + "logo.png", imgPath + "bg.png", idxApp1, idxApp2, idxApp3, idxApp4, ctrlApp1, ctrlApp2, ctrlApp3, ctrlApp4, group);
			}
			
			view.addObject(Constant.ACTION_RESULT, "1");
			log.trace("END: {}.doConfirm(), EIN: {}, compName: {}, area: {}, group: {}, fk1: {}, fk2: {}, fk3: {}, fk4: {}, ck1: {}, ck2: {}, ck3: {}, ck4: {}, exec TIME: {} ms.", this.getClass(), EIN, compName, area, group, idxApp1, idxApp2, idxApp3, idxApp4, ctrlApp1, ctrlApp2, ctrlApp3, ctrlApp4, p.executeTime());
		}
		catch(TBoxException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		
		this.doIndex(view, request);
	}
	
	
	
	private void doEdit(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();		
		String EIN = request.getParameter("EIN"); 
		log.trace("START: {}.deEdit(), EIN: {}", this.getClass(), EIN);
		try {
			Company comp = Util.isEmpty(EIN) ? new Company() : service.findCompanyByEIN(EIN);
			List<AdmGroup> groups = authService.findAllGroup(true, true);
			List<Area> areas = service.findAllArea();
			
			String logoB64 = Constant.EMPTY;
			String logoPath = comp.getLogoURL();
			File logoFile = new File(physicalPath + "/" + logoPath);
			if(!Util.isEmpty(logoPath) && logoFile.exists())
				logoB64 = StandardUtil.readFileToBase64(physicalPath + "/" + logoPath);
			
			String bgB64 = Constant.EMPTY;
			String bgPath = comp.getBackgroundURL();
			File bgFile = new File(physicalPath + "/" + bgPath);
			if(!Util.isEmpty(bgPath) && bgFile.exists())
				bgB64 = StandardUtil.readFileToBase64(physicalPath + "/" + bgPath);
			
			
			List<FastApp> indexFasts = service.findIndexFastApp(EIN);
			List<FastApp> ctrlPFasts = service.findControlPanelFastApp(EIN);
			
			AdmUser user = (AdmUser) request.getSession().getAttribute(Constant.USER);
			List<AppEntity> all_apps = service.findAllApps(user.getGroup());
			
			view.addObject(ALL_APPS, all_apps);
			view.addObject(FAST_INDEX_APPS, indexFasts);
			view.addObject(FAST_CTRL_PNL_APPS, ctrlPFasts);
			view.addObject(LOGO_BASE64, logoB64);
			view.addObject(BG_BASE64, bgB64);
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
	
	private void checkSessionAlive(ModelAndView view, HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(Constant.USER);
		if(obj == null)
			view.setViewName("error/error");
	}
}
