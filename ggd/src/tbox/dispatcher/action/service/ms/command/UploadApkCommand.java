package tbox.dispatcher.action.service.ms.command;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import tbox.TBoxException;
import tbox.service.TBoxService;
import tbox.service.entity.ApkInfoEntity;

@Component("uploadApk")
public class UploadApkCommand implements AdminCommand {
	
	private final static Logger log = LoggerFactory.getLogger(UploadApkCommand.class);
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;

	/* (non-Javadoc)
	 * @see tbox.dispatcher.action.service.command.Command#execute(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest, tbox.core.TBoxData)
	 */
	@Override
	public void execute(ModelAndView view, HttpServletRequest request) throws TBoxException {
		Profiler p = new Profiler();
		String apkB64 = request.getParameter("apkB64");
		String appId = request.getParameter("appId");
		log.trace("START: {}.execute(), appId: {}", this.getClass(), appId);
		try {
			ApkInfoEntity apk = service.getApkInfo(apkB64, appId);
			view.addObject("apkInfo", apk);
		}
		catch(TBoxException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.execute(), appId: {}, exec TIME: {} ms", this.getClass(), appId, p.executeTime());
	}

}
