package tbox.dispatcher.main;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import baytony.util.Util;
import baytony.util.date.DateUtil;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import tbox.TBoxException;
import tbox.data.vo.OSVersion;
import tbox.service.TBoxService;

@Component("main.os")
public class OSVersionDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(OSVersionDispatcher.class);

	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;

	/* (non-Javadoc)
	 * @see ggd.core.dispatcher.Dispatcher#handler(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		String action = request.getParameter(Constant.ACTION_TYPE);		
		action = Util.isEmpty(action) ? "index" : action;
		log.trace("START: {}.handler(), action: {}", this.getClass(), action);
		switch(action) {
			case "edit":
				doEdit(view, request);
				break;			
			case "save":
				doEdit(view, request);
				break;
			case "confirm":
				doConfirm(view, request);
				break;
			case "index":
				doIndex(view, request);
				break;			
			default:
				doIndex(view, request);
				break;
		}
		log.info("END: {}.handler(), action: {}, exec TIME: {} ms.", this.getClass(), action, p.executeTime());
	}
	
	private void doConfirm(ModelAndView view, HttpServletRequest request) throws TBoxException {
		Profiler p = new Profiler();
		String serialNo = request.getParameter("serial");
		String version = request.getParameter("version");
		String publishTime = request.getParameter("publishTime");
		String osDesc = request.getParameter("osDesc");
		log.trace("START: {}.doConfirm(), serialNo: {}, version: {}, publishTime: {}, osDesc: {}", this.getClass(), serialNo, version, publishTime, osDesc);
		Timestamp ts = DateUtil.changeToTimestamp(new Date(publishTime));
		if(Util.isEmpty(serialNo)) {
			OSVersion bean = new OSVersion(version, osDesc, "/os/" + version + "/os.apk", ts);
			service.saveNewVersion(bean);
		}
		else {
			OSVersion bean = new OSVersion(Integer.parseInt(serialNo), version, osDesc, "/os/" + version + "/os.apk", ts);
			service.updateOSVersion(bean);
		}
		log.info("END: {}.doConfirm(), serialNo: {}, version: {}, publishTime: {}, osDesc: {}, exec TIME: {} ms.", this.getClass(), serialNo, version, publishTime, osDesc, p.executeTime());
		this.doIndex(view, request);
	}
	
	private void doEdit(ModelAndView view, HttpServletRequest request) throws NumberFormatException, TBoxException {
		Profiler p = new Profiler();		
		String serialNo = request.getParameter("serialNo"); 
		log.trace("START: {}.deEdit(), serialNo: {}", this.getClass(), serialNo);
		OSVersion obj = Util.isEmpty(serialNo) ? new OSVersion() : service.getOSVerion(Integer.parseInt(serialNo));
		view.addObject(Constant.DATA_LIST, obj);
		view.setViewName("os/edit");
		log.info("END: {}.doEdit(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo, p.executeTime());		
	}
	
	private void doIndex(ModelAndView view, HttpServletRequest request) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.doIndex()", this.getClass());
		List<OSVersion> list = service.findAllVersion();
		view.addObject(Constant.DATA_LIST, list);
		view.setViewName("os/index");
		log.info("END: {}.doIndex(), exec TIME: {} ms.", this.getClass(), p.executeTime());
	}
}
