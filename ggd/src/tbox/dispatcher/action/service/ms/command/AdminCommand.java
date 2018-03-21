package tbox.dispatcher.action.service.ms.command;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import tbox.TBoxException;

public interface AdminCommand {

	/**
	 * 執行
	 * @param view
	 * @param request
	 * @throws TBoxException
	 */
	public void execute(ModelAndView view, HttpServletRequest request) throws TBoxException;
}
