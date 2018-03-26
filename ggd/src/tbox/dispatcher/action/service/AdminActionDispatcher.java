package tbox.dispatcher.action.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import baytony.util.Util;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import tbox.TBoxException;
import tbox.core.TBoxCodeMsg;
import tbox.dispatcher.action.service.ms.command.UploadApkCommand;

@Component("service.admin")
public class AdminActionDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(AdminActionDispatcher.class);
	
	@Autowired
	@Qualifier("UploadApkCommand")
	private UploadApkCommand uploadApkCmd;

	/* (non-Javadoc)
	 * @see ggd.core.dispatcher.Dispatcher#handler(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		Map<String, List<FileItem>> multiparts = (Map<String, List<FileItem>>) view.getModel().get(Constant.ARG);
		try {
			String action = this.getParameterValue(Constant.ACTION_TYPE, multiparts);
			log.trace("START: {}.handler(), action: {}", this.getClass(), action);
			switch(action) {
				case "uploadApk":
					uploadApkCmd.execute(view, multiparts);
					break;
			}
		}
		catch(TBoxException e) {
			throw e;
		}
		catch (IOException e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_004, e);
		}
		log.info("END: {}.handler(), exec TIME: {} ms.", this.getClass(), p.executeTime());
	}
	
	private String getParameterValue(String par, Map<String, List<FileItem>> multiparts) throws UnsupportedEncodingException {
		String result = Constant.EMPTY;
		List<FileItem> items = multiparts.get(par);
		if(!Util.isEmpty(items)) {
			FileItem item = items.get(0);
			result = item.isFormField() ? new String(item.getString().getBytes(Constant.ISO88591), Constant.UTF8) : new String(item.getName().getBytes(Constant.ISO88591), Constant.UTF8);
		}
		return result;
	}

}
