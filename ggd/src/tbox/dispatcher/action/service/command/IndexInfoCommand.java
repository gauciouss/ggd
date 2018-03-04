/**
 * 
 */
package tbox.dispatcher.action.service.command;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import tbox.core.TBoxData;
import tbox.core.TBoxInfo;

/**
 * @author admin
 *
 */
@Component("IndexInfoCommand")
public class IndexInfoCommand implements Command {
	
	private final static Logger log = LoggerFactory.getLogger(RegisterCommand.class);

	/* (non-Javadoc)
	 * @see tbox.dispatcher.action.service.command.Command#execute(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest, tbox.core.TBoxData)
	 */
	@Override
	public void execute(ModelAndView view, HttpServletRequest request, TBoxData tbox) {
		Profiler p = new Profiler();
		log.trace("START: {}.execute(), tbox: {}", this.getClass(), tbox);
		TBoxInfo info = tbox.getTBoxInfo();		
		
		log.info("END: {}.execute(), tbox: {}, exec TIME: {}", this.getClass(), tbox, p.executeTime());
	}

}
