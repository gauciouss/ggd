/**
 * 
 */
package tbox.dispatcher.action.service.command.entity;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import tbox.TBoxException;
import tbox.core.TBoxData;
import tbox.dispatcher.action.service.command.Command;

/**
 * @author admin
 *
 */
@Component("app.os.version")
public class AppOSVersion implements Command {

	/* (non-Javadoc)
	 * @see tbox.dispatcher.action.service.command.Command#execute(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest, tbox.core.TBoxData)
	 */
	@Override
	public void execute(ModelAndView view, HttpServletRequest request, TBoxData tbox) throws TBoxException {
		// TODO Auto-generated method stub

	}

}
