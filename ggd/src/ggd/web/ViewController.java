/**
 * 
 */
package ggd.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import baytony.util.Util;
import ggd.config.DispatcherConfig;
import ggd.core.acl.AclManager;
import ggd.core.common.Config;
import ggd.core.common.Constant;
import ggd.core.config.XML_Config;
import ggd.core.controller.CommonController;
import ggd.core.dispatcher.DispatchData;
import ggd.core.dispatcher.DispatchDataImpl;
import ggd.core.dispatcher.Dispatcher;
import ggd.core.util.WebUtil;

/**
 *
 */
@Controller
@RequestMapping(value = "/view")
public class ViewController extends CommonController {
	
	public static final String REDIRECT = "redirect:%1$s";
	private final static Logger log = LoggerFactory.getLogger(ViewController.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	@Qualifier(XML_Config.URL_CONFIG)
	private Config urlConfig;
	
	@Autowired
	@Qualifier(XML_Config.COMMON_CONFIG)
	private Config commonConfig;
	
	@Autowired
	@Qualifier(XML_Config.DISPLAY_CONFIG)
	private Config displayConfig;
	
	@Autowired
	@Qualifier(DispatcherConfig.GGD_ACL)
	private AclManager acl;
	
	
	
	private static final String BEAN_ENTITY = "%1$s.%2$s";
	
	
	/* (non-Javadoc)
	 * @see masterlink.rh.core.controller.CommonController#getType()
	 */
	@Override
	public TYPE getType() {
		return TYPE.VIEW;
	}
	
	@RequestMapping(path = PATH, method = RequestMethod.GET)
	public ModelAndView get(@PathVariable(CATAGORY) String category, @PathVariable(COMMAND) String command
			, String arg, HttpServletRequest request, HttpServletResponse res) {		
		log.debug("{}.get() exec start. action: {}, type: {}, arg: {}.", this.getClass(), category, command, arg);
		return doRequest(category, command, arg, request, res);
	}
	
	@RequestMapping(path = PATH, method = RequestMethod.POST)
	public ModelAndView post(@PathVariable(CATAGORY) String category, @PathVariable(COMMAND) String command
			, String arg, HttpServletRequest request, HttpServletResponse res) {		
		log.debug("{}.post() exec start. action: {}, type: {}, arg: {}.", this.getClass(), category, command, arg);
		return doRequest(category, command, arg, request, res);
	}
	
	private ModelAndView doRequest(String category, String command, String arg, HttpServletRequest request, HttpServletResponse res){
		Profiler p = new Profiler();
		ModelAndView view = null;
			try {
				view = createModelAndView(category, command, arg, request);
				String path = this.acl(view);
				if(!Util.isEmpty(path)){
					return new ModelAndView(path);
				}
				
				
				view.addObject(Constant.SESSION, request.getSession());
				
				
				String beanEntity = String.format(BEAN_ENTITY, category, command);
				log.debug("context.getBean(\"{}\")", beanEntity);
				
				if(context.containsBean(beanEntity)){
					Dispatcher d = context.getBean(beanEntity, Dispatcher.class);
					if(d != null){
						log.trace("Folder : {}, Found : Dispatcher {}.", category, d.getClass());
						d.handler(view, request);
					} else {
						log.trace("No Dispatcher found for folder: {}. Call JSP: {}.", category, view.getViewName());
					}
				} else {
					log.trace("No Dispatcher found for folder: {}. Call JSP: {}.", category, view.getViewName());
				}
				return view;
			} catch (Throwable e) {
				log.error("doRequest() ERROR in /{}/{}, arg: {} !", category, command, arg, e);
				return (ModelAndView) handleException(e, request);
			} finally {  
				res.setHeader("Pragma", "No-cache");
				res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				res.setDateHeader("Expires", -1);
				log.info("{}.doRequest() complete. view: {}, arg: {}. exec time: {} ms.", this.getClass(), view.getViewName(), arg, p.getExecuteTime());
			}
	}
	
	
	private String acl(ModelAndView view){
		String path = acl.checkAcl(view.getViewName());
		boolean check = checkJson(view);
		if(!Util.isEmpty(path) && check){
			log.info("Acl checkJson: {}, redirect to path : {},", check, path);
			return String.format(REDIRECT, path);
		}
		return null;
	}
	
	private boolean checkJson(ModelAndView view){
		DispatchData data = (DispatchData) view.getModel().get(Constant.DISPATCH_DATA);
		if(data.getUserInfo() == null || data.getUserInfo().getUser() == null){
			return true;
		}
		local.set(data);
		return false;
	}
	
	
	private ModelAndView createModelAndView(String category, String command, String arg, HttpServletRequest request){
		ModelAndView view = new ModelAndView(StringUtil.concat(Constant.SLASH, category, command));
		view.addObject(Constant.URL_CONFIG, urlConfig);
		view.addObject(Constant.COMMON_CONFIG, commonConfig);
		view.addObject(Constant.DISPLAY_CONFIG, displayConfig);
		//以下是給HTML及ＪＳ快數取得URL用的
		
		DispatchData data = new DispatchDataImpl(category, command, arg, WebUtil.getClientIpAddr(request));
		
		view.addObject(Constant.DISPATCH_DATA, data);
		view.addObject(Constant.ARG, arg);
		if(data.getUserInfo() != null){
			view.addObject(Constant.USER_INFO, data.getUserInfo());
		} else {
			log.info("No UserInfo found for: {}. arg : {}", view.getViewName(), arg);
		}
		
		Enumeration<String> es = request.getParameterNames();
		while(es != null && es.hasMoreElements()) {
			String name = es.nextElement();
			view.addObject(name, request.getParameter(name));
		}
		
		
		
		
		return view;
		
	}

	
}
