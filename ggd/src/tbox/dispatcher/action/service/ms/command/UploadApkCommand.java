package tbox.dispatcher.action.service.ms.command;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import baytony.util.Util;
import ggd.core.common.Constant;
import tbox.TBoxException;
import tbox.service.TBoxService;
import tbox.service.entity.ApkInfoEntity;

@Component("UploadApkCommand")
public class UploadApkCommand {
	
	private final static Logger log = LoggerFactory.getLogger(UploadApkCommand.class);
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;

	public void execute(ModelAndView view, Map<String, List<FileItem>> multiparts) throws TBoxException, UnsupportedEncodingException {
		Profiler p = new Profiler();
		
		String appId = this.getParameterValue("serial", multiparts);
		log.trace("START: {}.doUploadApk(), appId: {}", this.getClass(), appId);
		List<FileItem> fileList = multiparts.get("apk");
		if(!Util.isEmpty(fileList)) {
			FileItem item = fileList.get(0);
			ApkInfoEntity apk = service.saveApk2Disk(item, appId, item.getName(), true);
			view.addObject(Constant.JSON_RESPONSE, apk);
		}	
		
		
		log.info("END: {}.execute(), appId: {}, exec TIME: {} ms", this.getClass(), appId, p.executeTime());
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
