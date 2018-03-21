package tbox.dispatcher.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
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

import baytony.org.apache.commons.codec.binary.Base64;
import baytony.util.Profiler;
import baytony.util.StringUtil;
import baytony.util.Util;
import ggd.auth.AuthService;
import ggd.auth.vo.AdmUser;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import tbox.TBoxException;
import tbox.data.vo.App;
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
		switch(action) {
			case "edit":
				doEdit(view, request);
				break;			
			case "save":
				doEdit(view, request);
				break;
			case "confirm":
				//doConfirm(view, request);
				break;
			case "uploadApk":
				doUploadApk(view, request, multiparts);
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
	
	private void doUploadApk(ModelAndView view, HttpServletRequest request, Map<String, List<FileItem>> multiparts) {
		Profiler p = new Profiler();
		String appId = Constant.EMPTY;		
		try {
//			Part apk = request.getPart("apk");
//			String appId = request.getParameter("serialNo");
//			InputStream is = apk.getInputStream();
//			byte[] bs = new byte[(int) apk.getSize()];
//			is.read(bs, 0, bs.length);
//			is.close();
//			String b64 = Base64.encodeBase64String(bs);
//			StandardUtil.writeBase64ToFile(b64, physicalPath + "app/" + appId, apk.getName());
			appId = this.getParameterValue("serial", multiparts);
			log.trace("START: {}.doUploadApk(), appId: {}", this.getClass(), appId);
			List<FileItem> fileList = multiparts.get("apk");
			if(!Util.isEmpty(fileList)) {
				FileItem item = fileList.get(0);
				service.saveApk2Disk(item, appId, item.getName());
			}			
		} 
		catch (IOException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		} 
		catch (TBoxException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		finally {
			log.info("END: {}.doUploadApk(), appId: {}, exec TIME: {} ms.", this.getClass(), appId, p.executeTime());
		}
	}
	
	private void doEdit(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();		
		String serialNo = request.getParameter("serialNo"); 
		log.trace("START: {}.deEdit(), serialNo: {}", this.getClass(), serialNo);
		try {
			App app = service.findAppById(serialNo);
			if(app == null) {
				app = new App();
				String nextId = service.getNextAppId();
				app.setAppId(nextId);
				view.setViewName("app/upload");
			}
			else {
				view.setViewName("app/edit");
			}
				
			List<AppClz> kinds = service.findAllAppKind();
			view.addObject(ALL_APP_KIND, kinds);
			view.addObject(Constant.DATA_LIST, app);
			
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
			AdmUser loginUser = (AdmUser) request.getSession().getAttribute(Constant.USER);
			List<AppEntity> list = service.findAllApps(loginUser.getGroup());
			view.addObject(Constant.DATA_LIST, list);
			view.setViewName("app/index");
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
