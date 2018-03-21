package tbox.dispatcher.action.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import ggd.core.util.JSONUtil;
import tbox.dispatcher.action.service.ms.command.AdminCommand;

@Component("service.adminAction")
public class AdminActionDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(AdminActionDispatcher.class);
	
	@Autowired
	private ApplicationContext context;

	/* (non-Javadoc)
	 * @see ggd.core.dispatcher.Dispatcher#handler(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		String arg = (String) view.getModel().get(Constant.ARG);
		log.trace("START: {}.handler(), arg: {}", this.getClass(), arg);
		try {
			JsonNode node = JSONUtil.parser(arg);
			JsonNode header = node.get("header");
			String action = header.get("action").asText();
			log.debug("action: {}", action);
			//AdminCommand cmd = context.getBean(action, AdminCommand.class);
			//cmd.execute(view, request);
		}
		catch (JsonProcessingException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch (IOException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.handler(), arg: {}, exec TIME: {} ms.", this.getClass(), arg, p.executeTime());
	}

}
