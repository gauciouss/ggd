package tbox.dispatcher.action.service.command;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import tbox.TBoxException;
import tbox.core.TBoxData;

public interface Command {

	/**
	 * 執行
	 * @param view
	 * @param request
	 * @param tbox
	 * @throws TBoxException
	 */
	public void execute(ModelAndView view, HttpServletRequest request, TBoxData tbox) throws TBoxException;
}
