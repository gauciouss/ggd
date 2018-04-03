package tbox.dispatcher.main;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
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
import tbox.TBoxException;
import tbox.core.TBoxCodeMsg;
import tbox.data.vo.AppClz;
import tbox.data.vo.AppEntity;
import tbox.service.TBoxService;

@Component("main.app")
public class AppDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(AppDispatcher.class);
	
	public static final String ALL_APP_KIND = AppDispatcher.class + "_KINDS";
	
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
		
		String action = Constant.EMPTY;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		Map<String, List<FileItem>> multiparts = null;
		try {
			multiparts = upload.parseParameterMap(request);
			action = this.getParameterValue(Constant.ACTION_TYPE, multiparts);
			
		} 
		catch (Exception e) {
			action = request.getParameter(Constant.ACTION_TYPE);
		}
		action = Util.isEmpty(action) ? "index" : action;
		log.trace("START: {}.handler(), action: {}", this.getClass(), action);
		try {
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
				case "cancel":
					doCancel(view, request);
					break;
				case "delete":
					doDelete(view, request);
					break;
				case "index":
					doIndex(view, request);
					break;			
				default:
					doIndex(view, request);
					break;
			}
		}
		catch(TBoxException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
		}
		log.info("END: {}.handler(), action: {}, exec TIME: {} ms.", this.getClass(), action, p.executeTime());
	}
	
	private void doDelete(ModelAndView view, HttpServletRequest request) throws TBoxException {
		Profiler p = new Profiler();
		String serial = request.getParameter("serial");
		log.trace("START: {}.doDelete(), serial: {}", this.getClass(), serial);
		service.deleteApp(serial);
		log.info("END: {}.doDelete(), serial: {}, exec TIME: {} ms.", this.getClass(), serial, p.executeTime());
		view.addObject(Constant.ACTION_RESULT, "1");
		this.doIndex(view, request);
	}
	
	private void doCancel(ModelAndView view, HttpServletRequest request) throws TBoxException {
		Profiler p = new Profiler();
		String serialNo = request.getParameter("serialNo");
		log.trace("START: {}.doCancel(), serialNo: {}", this.getClass(), serialNo);
		service.deleteApp(serialNo);
		log.info("END: {}.doCancel(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo, p.executeTime());
		this.doIndex(view, request);
	}
	
	private void doConfirm(ModelAndView view, HttpServletRequest request) throws TBoxException {
		Profiler p = new Profiler();
		String serial = request.getParameter("serial");
		String kind = request.getParameter("kind");
		String appName = request.getParameter("appName");
		String appEngName = request.getParameter("appEngName");
		String version = request.getParameter("version");
		String pkgName = request.getParameter("pkgName");
		String appDesc = request.getParameter("appDesc").trim();
		log.trace("START: {}.doConfirm(), serial: {}, kind: {}, appName: {}, appEngName: {}, version: {}, pkgName: {}, appDesc: {}", this.getClass(), serial, kind, appName, appEngName, version, pkgName, appDesc);
		try {
			service.saveOrUpdateAppInfo(serial, Integer.parseInt(kind), appName, appEngName, version, pkgName, appDesc);
			view.addObject(Constant.ACTION_RESULT, "1");
		} catch (NumberFormatException e) {
			log.error(StringUtil.getStackTraceAsString(e));		
			throw new TBoxException(TBoxCodeMsg.EX_004, e);
		} catch (TBoxException e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw e;
		};
		log.info("END: {}.doConfirm(), serial: {}, kind: {}, appName: {}, appEngName: {}, version: {}, pkgName: {}, appDesc: {}, exec TIME: {} ms.", this.getClass(), serial, kind, appName, appEngName, version, pkgName, appDesc, p.executeTime());
		this.doIndex(view, request);
	}
	
	private void doEdit(ModelAndView view, HttpServletRequest request) throws TBoxException {
		Profiler p = new Profiler();		
		String serialNo = request.getParameter("serialNo"); 
		log.trace("START: {}.deEdit(), serialNo: {}", this.getClass(), serialNo);
		try {
			AppEntity app = service.findLastVersionApp(serialNo);
			if(app == null) {
				app = new AppEntity();
				String nextId = service.getNextAppId();
				app.setAppId(nextId);
			}
			view.setViewName("app/edit");
				
			List<AppClz> kinds = service.findAllAppKind();
			view.addObject(ALL_APP_KIND, kinds);
			view.addObject(Constant.DATA_LIST, app);
			
		}
		catch(TBoxException e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw e;
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_004, e);
		}
		log.info("END: {}.doEdit(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo, p.executeTime());
	}
	
	
	private void doIndex(ModelAndView view, HttpServletRequest request) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.doIndex()", this.getClass());
		try {
			AdmUser loginUser = (AdmUser) request.getSession().getAttribute(Constant.USER);
			List<AppEntity> list = service.findAllApps(loginUser.getGroup());
			view.addObject(Constant.DATA_LIST, list);
			view.setViewName("app/index");
		}
		catch(TBoxException e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw e;
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_004, e);
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
