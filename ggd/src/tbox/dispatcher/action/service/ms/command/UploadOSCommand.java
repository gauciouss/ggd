package tbox.dispatcher.action.service.ms.command;

import java.io.File;
import java.io.IOException;
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
import baytony.util.StringUtil;
import baytony.util.Util;
import ggd.core.common.Constant;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import tbox.TBoxException;
import tbox.core.TBoxCodeMsg;

@Component("UploadOSCommand")
public class UploadOSCommand {
	
	private final static Logger log = LoggerFactory.getLogger(UploadOSCommand.class);
	
	@Autowired
	@Qualifier("FILE_PHYSICAL_PATH")
	private String physicalPath;
	
	
	public void execute(ModelAndView view, Map<String, List<FileItem>> multiparts) throws TBoxException, UnsupportedEncodingException {
		Profiler p = new Profiler();
		
		String appId = this.getParameterValue("serial", multiparts);
		log.trace("START: {}.doUploadApk(), appId: {}", this.getClass(), appId);
		List<FileItem> fileList = multiparts.get("apk");
		if(!Util.isEmpty(fileList)) {
			FileItem item = fileList.get(0);
			String version = this.saveApk2Disk(item, item.getName(), true);
			view.addObject(Constant.JSON_RESPONSE, version);
		}	
		
		
		log.info("END: {}.execute(), appId: {}, exec TIME: {} ms", this.getClass(), appId, p.executeTime());
	}
	
	private String saveApk2Disk(FileItem item, String apkName, boolean isTemp) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.saveApk2Disk(), apkName: {}", this.getClass(),  apkName);
		//先搬到temp區，等確定儲存時再搬到正式區
		String path = isTemp ? physicalPath + "/os/temp/" : physicalPath + "/os/"; 
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		File file = new File(path + apkName);
		try {
			item.write(file);
			log.debug("apk file is exist ? {}", file.exists());
			log.debug("apk path: {}", path + apkName);
			ApkFile apkFile = new ApkFile(path + apkName);
			ApkMeta meta = apkFile.getApkMeta();
			
			String pkgName = meta.getPackageName();
			String version = meta.getVersionName();
			log.debug("pkg name: {}, version: {}", pkgName, version);
			apkFile.close();
			return version;
		} 
		catch(TBoxException e) {
			throw e;
		}
		catch(IOException e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_004, e.getMessage());
		}
		catch (Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
			throw new TBoxException(TBoxCodeMsg.EX_004, e.getMessage());
		}
		finally {
			log.info("END: {}.saveApk2Disk(), apkName: {}, exec TIME: {} ms.", this.getClass(), apkName, p.executeTime());
		}
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
