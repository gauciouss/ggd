/**
 * 
 */
package tbox.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import ggd.core.common.Constant;
import ggd.core.controller.CommonController;
import ggd.core.dispatcher.Dispatcher;
import ggd.core.entity.ServiceResponse;
import ggd.core.entity.ServiceResponse.Header;
import tbox.TBoxException;
import tbox.service.TBoxService;

/**
 * @author baytony
 *
 */
@RestController
@RequestMapping(value = "/multipart")
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET})
public class MultipartController extends CommonController{
	
	private final static Logger log = LoggerFactory.getLogger(MultipartController.class);
	
	private static final String BEAN_ENTITY = "%1$s.%2$s";
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;
	
	@Override
	public TYPE getType() {
		return TYPE.JSON;
	}
	
	private Map<String, List<FileItem>> readValueFromMultipart(HttpServletRequest request) throws FileUploadException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		Map<String, List<FileItem>> multiparts = upload.parseParameterMap(request);
		return multiparts;
	}
	
	@RequestMapping(path = PATH, method = RequestMethod.POST)
	public ServiceResponse post(@PathVariable(CATAGORY) String category, @PathVariable(COMMAND) String command, HttpServletRequest request) throws FileUploadException{
		log.trace("Post() exec start.");
		Profiler p = new Profiler();
		Map<String, List<FileItem>> arg = null;
		try{
			arg = readValueFromMultipart(request);
			ServiceResponse res = doRequest(category, command, arg, request);
			setServiceResponse(res, p);
			return res;
		}finally{
			log.info("Post() exec complete. arg: {}. exec time: {} ms.", arg, p.executeTime());
		}
	}
	
	
	
	private ServiceResponse doRequest(String category, String command, Map<String, List<FileItem>> arg, HttpServletRequest request){
		Header header = new Header();
		ModelAndView view = null;
		try {
			view = createModelAndView(category, command, arg, request);
			String beanName = String.format(BEAN_ENTITY, category, command);
			Dispatcher d = context.getBean(beanName, Dispatcher.class);
			if(d != null){
				log.trace("Folder : {}, Found : Dispatcher {}.", category, d.getClass());
				d.handler(view, request);
				header.setCode("00-000");
			} else {
				log.trace("No Dispatcher found for folder: {}. Call JSP: {}.", category, view.getViewName());
			}
		}
		catch(TBoxException e) {
			log.warn("doRequest() ERROR! MSG : {}", e.getMessage(), e);
			header.setCode(e.getCode());
			header.setMsg(e.getMessage());
		}
		catch (Exception e) {
			log.warn("doRequest() ERROR! MSG : {}", e.getMessage(), e);
			header.setExt(e.getMessage());
		}
		return new ServiceResponse(header, view.getModel().get(Constant.JSON_RESPONSE));
	}
	
	
	private ModelAndView createModelAndView(String folder, String jsp, Map<String, List<FileItem>> arg, HttpServletRequest request) {
		ModelAndView view = new ModelAndView(StringUtil.concat(Constant.SLASH, folder, jsp));		
		view.addObject(Constant.ARG, arg);
		return view;
		
	}
	
	private void setServiceResponse(ServiceResponse res, Profiler p){
		res.getHeader().setInTime(String.valueOf(p.getStartTime()));
		res.getHeader().setExeTime(p.executeTime());
		res.getHeader().setOutTime(String.valueOf(p.getEndTime()));
	}
	
}
