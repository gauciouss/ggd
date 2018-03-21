package tbox.dispatcher.main;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
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
import ggd.auth.vo.AdmUser;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import ggd.core.util.StandardUtil;
import net.dongliu.apk.parser.bean.IconPath;
import tbox.TBoxException;
import tbox.data.vo.AppClz;
import tbox.data.vo.AppEntity;
import tbox.service.TBoxService;

@Component("main.appclz")
public class AppClzDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(CompanyDispatcher.class);
	
	public static final String ALL_APP_KIND = AppClzDispatcher.class + "_KINDS";
	public static final String ICON_BASE64 = AppClzDispatcher.class + "_BASE64";
	
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
		//String action = request.getParameter(Constant.ACTION_TYPE);	
		//action = StringUtil.isEmptyString(action) ? "index" : action;
		
		String action = request.getParameter(Constant.ACTION_TYPE);		
		action = Util.isEmpty(action) ? "index" : action;
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
				doIndex(view, request);
				break;			
			default:
				doIndex(view, request);
				break;
		}
		log.info("END: {}.handler(), action: {}, exec TIME: {} ms.", this.getClass(), action, p.executeTime());
	}
	
	private void doConfirm(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		String name = request.getParameter("name");
		String serial = request.getParameter("serial");
		String iconB64 = request.getParameter("iconB64");
		log.trace("START: {}.doConfirm(), serial: {}, name: {}", this.getClass(), serial, name);
		try {
			if(Util.isEmpty(serial)) {
				//新增
				service.addNewAppClz(name, iconB64);
			}
			else {
				//更新			
				service.updateAppClz(Integer.parseInt(serial), name, iconB64);
			}
			view.addObject(Constant.ACTION_RESULT, "1");
		}
		catch(TBoxException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doConfirm(), serial: {}, name: {}, exec TIME: {} ms.", this.getClass(), serial, name, p.executeTime());
		this.doIndex(view, request);
	}
	
	
	private void doEdit(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();		
		String serialNo = request.getParameter("serialNo"); 
		log.trace("START: {}.deEdit(), serialNo: {}", this.getClass(), serialNo);
		try {
			AppClz clz = Util.isEmpty(serialNo) ? new AppClz() : service.findAppKindById(Integer.parseInt(serialNo));
			String iconB64 = Constant.EMPTY;
			if(!Util.isEmpty(serialNo)) {
				String iconPath = physicalPath + "/" + clz.getIconPath();
				File f = new File(iconPath);
				log.debug("icon path: {}", iconPath);
				if(!Util.isEmpty(iconPath) && f.exists()) {
					iconB64 = StandardUtil.readFileToBase64(iconPath);
				}
			}
			
			view.addObject(ICON_BASE64, iconB64);
			view.addObject(Constant.DATA_LIST, clz);
			view.setViewName("appclz/edit");
		}
		catch(TBoxException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doEdit(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo, p.executeTime());
	}
	
	
	private void doIndex(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		log.trace("START: {}.doIndex()", this.getClass());
		try {
			List<AppClz> list = service.findAllAppKind();
			view.addObject(Constant.DATA_LIST, list);
			view.setViewName("appclz/index");
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doIndex(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		
	}
	
	private String getParameterValue(String par, Map<String, List<FileItem>> multiparts) throws UnsupportedEncodingException {
		String result = Constant.EMPTY;
		List<FileItem> items = multiparts.get(par);
		if(!Util.isEmpty(items)) {
			FileItem item = items.get(0);
			result = item.isFormField() ? new String(item.getString().getBytes(Constant.ISO88591), Constant.UTF8) : new String(item.getName().getBytes(Constant.ISO88591), Constant.UTF8);
		}
		return result;
	}
	
}
