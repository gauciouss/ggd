package tbox.dispatcher.action.service.command;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;

import baytony.util.Profiler;
import ggd.core.common.Constant;
import tbox.TBoxException;
import tbox.core.TBoxData;
import tbox.core.TBoxInfo;
import tbox.service.MachineService;

@Component("pwd.setting")
public class SettingPwdCommand implements Command {
	
	private final static Logger log = LoggerFactory.getLogger(SettingPwdCommand.class);
	
	@Autowired
	@Qualifier("MachineService")
	private MachineService machineService;

	@Override
	public void execute(ModelAndView view, HttpServletRequest request, TBoxData tbox) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.execute(), tbox: {}", this.getClass(), tbox);
		TBoxInfo box = tbox.getTBoxInfo();		
		JsonNode node = box.getParams();
		String pwd = node.get("pwd").asText();
		log.debug("tbox password: {}", pwd);
		machineService.setPassword(box.getMachineSN(), pwd);
		view.addObject(Constant.JSON_RESPONSE, new Adapter(true));
		log.info("END: {}.execute(), tbox: {}, exec TIME: {} ms.", this.getClass(), tbox, p.executeTime());
	}
	
	class Adapter implements Serializable {
		private static final long serialVersionUID = -5664931581028473438L;
		private long time = System.currentTimeMillis();
		private boolean isSuccess = false;
		
		public Adapter(boolean isSuccess) {
			this.isSuccess = isSuccess;
		}
		
		public long getTime() {
			return time;
		}
		
		public boolean isSuccess() {
			return isSuccess;
		}
	}

}
