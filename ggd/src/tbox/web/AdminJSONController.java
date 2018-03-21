/**
 * 
 */
package tbox.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping(value = "/action/admin")
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET})
public class AdminJSONController extends CommonController{
	
	private final static Logger log = LoggerFactory.getLogger(AdminJSONController.class);
	
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
	
	private String readArgFromInputStream(HttpServletRequest request) {
		InputStream is = null;
		try {
			is = request.getInputStream();
			StringBuilder sb = new StringBuilder();
			int i = -1;
			while((i = is.read()) != -1) {
				sb.append((char) i);
			}
			String str = sb.toString();
			str = URLDecoder.decode(sb.toString(), Constant.UTF8);
			return str;
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return "";
	}
	
	@RequestMapping(path = PATH, method = RequestMethod.GET)
	public ServiceResponse get(@PathVariable(CATAGORY) String category, @PathVariable(COMMAND) String command, HttpServletRequest request){
		log.trace("Get() exec start.");
		Profiler p = new Profiler();
		String arg = Constant.EMPTY;
		try{
			arg = readArgFromInputStream(request);
			if(Constant.TEST_MODE){
				ServiceResponse res = doRequest(category, command, arg, request);
				setServiceResponse(res, p);
				return res;
			}else{
				return null;
			}
		}finally{
			log.info("Get() exec complete. arg: {}. exec time: {} ms.", p.executeTime());
		}
		
	}
	
	@RequestMapping(path = PATH, method = RequestMethod.POST)
	public ServiceResponse post(@PathVariable(CATAGORY) String category, @PathVariable(COMMAND) String command, HttpServletRequest request){
		log.trace("Post() exec start.");
		Profiler p = new Profiler();
		String arg = Constant.EMPTY;
		try{
			arg = readArgFromInputStream(request);
			ServiceResponse res = doRequest(category, command, arg, request);
			setServiceResponse(res, p);
			return res;
		}finally{
			log.info("Post() exec complete. arg: {}. exec time: {} ms.", arg, p.executeTime());
		}
	}
	
	
	
	private ServiceResponse doRequest(String category, String command, String arg, HttpServletRequest request){
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
	
	
	private ModelAndView createModelAndView(String folder, String jsp, String arg, HttpServletRequest request) {
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
