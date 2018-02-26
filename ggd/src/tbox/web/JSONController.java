/**
 * 
 */
package tbox.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import ggd.core.common.Constant;
import ggd.core.controller.CommonController;
import ggd.core.dispatcher.DispatchData;
import ggd.core.dispatcher.DispatchDataImpl;
import ggd.core.dispatcher.Dispatcher;
import ggd.core.entity.ServiceResponse;
import ggd.core.util.WebUtil;

/**
 * @author baytony
 *
 */
@RestController
@RequestMapping(value = "/action")
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET})
public class JSONController extends CommonController{
	
	private final static Logger log = LoggerFactory.getLogger(JSONController.class);
	
	private static final String BEAN_ENTITY = "%1$s.%2$s";
	
	@Autowired
	private ApplicationContext context;
	
	@Override
	public TYPE getType() {
		return TYPE.JSON;
	}
	
	@RequestMapping(path = PATH, method = RequestMethod.GET)
	public ServiceResponse get(@PathVariable(CATAGORY) String category, @PathVariable(COMMAND) String command,
			@RequestParam("arg") String arg, HttpServletRequest request){
		log.trace("Get() exec start. arg: {}", arg);
		Profiler p = new Profiler();
		try{
			if(Constant.TEST_MODE){
				ServiceResponse res = doRequest(category, command, arg, request);
				setServiceResponse(res, p);
				return res;
			}else{
				return null;
			}
		}finally{
			log.info("Get() exec complete. arg: {}. exec time: {} ms.", arg, p.executeTime());
		}
		
	}
	
	@RequestMapping(path = PATH, method = RequestMethod.POST)
	public ServiceResponse post(@PathVariable(CATAGORY) String category, @PathVariable(COMMAND) String command, 
			@RequestParam("arg") String arg, HttpServletRequest request){
		log.trace("Post() exec start. arg: {}", arg);
		Profiler p = new Profiler();
		try{
			ServiceResponse res = doRequest(category, command, arg, request);
			setServiceResponse(res, p);
			return res;
		}finally{
			log.info("Post() exec complete.  arg: {}. exec time: {} ms.", arg, p.executeTime());
		}
	}
	
	
	
	private ServiceResponse doRequest(String category, String command, String arg, HttpServletRequest request){
		ServiceResponse.Header header = new ServiceResponse.Header();
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
		catch (Exception e) {
			log.warn("doRequest() ERROR! MSG : {}", e.getMessage(), e);
			header.setExt(e.getMessage());
			//e.printStackTrace();
		}
		return new ServiceResponse(header, view.getModel().get(Constant.JSON_RESPONSE));
	}
	
	
	private ModelAndView createModelAndView(String folder, String jsp, String arg, HttpServletRequest request) {
		ModelAndView view = new ModelAndView(StringUtil.concat(Constant.SLASH, folder, jsp));		
		DispatchData data = new DispatchDataImpl(folder, jsp, arg, WebUtil.getClientIpAddr(request));
		view.addObject(Constant.DISPATCH_DATA, data);
		view.addObject(Constant.ARG, arg);
		//TODO 這裏要改，當機上盒來呼叫的時候怎麼辦
		if(data.getUserInfo() != null) {
			view.addObject(Constant.USER_INFO, data.getUserInfo());
		}
		
		return view;
		
	}
	
	private void setServiceResponse(ServiceResponse res, Profiler p){
		res.getHeader().setInTime(String.valueOf(p.getStartTime()));
		res.getHeader().setExeTime(p.executeTime());
		res.getHeader().setOutTime(String.valueOf(p.getEndTime()));
	}
	
}
