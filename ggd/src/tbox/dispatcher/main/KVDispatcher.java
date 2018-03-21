package tbox.dispatcher.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import baytony.util.Util;
import baytony.util.date.DateUtil;
import ggd.auth.AuthService;
import ggd.auth.vo.AdmUser;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import ggd.core.util.JSONUtil;
import ggd.core.util.StandardUtil;
import tbox.TBoxException;
import tbox.data.vo.CompanyEntity;
import tbox.data.vo.KV;
import tbox.data.vo.KVEntity;
import tbox.data.vo.KVKind;
import tbox.service.TBoxService;

@Component("main.kv")
public class KVDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(CompanyDispatcher.class);
	
	public static final String ALL_KV_KIND = KVDispatcher.class + "_KINDS";
	public static final String ALL_KV_COMP = KVDispatcher.class + "_COMPS";
	public static final String KV_BASE64 = KVDispatcher.class + "_BASE64";
	public static final String KV_ENTITY = KVDispatcher.class + "_ENTITY";
	
	
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;
	
	@Autowired
	@Qualifier("AuthService")
	private AuthService authService;
	
	@Autowired
	@Qualifier("FILE_PHYSICAL_PATH")
	private String physicalPath;

	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		String action = request.getParameter(Constant.ACTION_TYPE);
		action = StringUtil.isEmptyString(action) ? "index" : action;
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
			default:
				doIndex(view, request);
				break;
		}
		log.info("END: {}.handler(), action: {}, exec TIME: {} ms.", this.getClass(), action, p.executeTime());
	}
	
	private void doConfirm(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		String name = request.getParameter("name");
		String kind = request.getParameter("kind");
		String publish = request.getParameter("publish");
		String clickLink = request.getParameter("clickLink");
		String isEnabled = request.getParameter("isEnabled");
		String isApproved = request.getParameter("isApproved");
		String serial = request.getParameter("serial");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String kvB64 = request.getParameter("kvB64");
		String msg = request.getParameter("msg");
		log.trace("START: {}.doConfirm(), serial: {}, name: {}, kind: {}, clickLink: {}, publish: {}, start: {}, end: {}, isEnabled: {}, isApproved: {}", this.getClass(), serial, name, kind, clickLink, publish, start, end, isEnabled, isApproved);
		try {
			JsonNode node = JSONUtil.parser(publish);
			List<String> EINs = new ArrayList<String>();
			
			node.forEach(n -> {
				EINs.add(n.get("id").asText());
			});
			
			AdmUser loginUser = (AdmUser) request.getSession().getAttribute(Constant.USER);
			Date d1 = new Date(start);
			Date d2 = new Date(end);
			if(Util.isEmpty(serial)) {
				//新增
				service.addKV(Integer.parseInt(kind), name, kvB64, clickLink, msg, loginUser.getAccount(), DateUtil.changeToTimestamp(d1), DateUtil.changeToTimestamp(d2), Integer.parseInt(isEnabled) == 1, Integer.parseInt(isApproved) == 1, EINs);
			}
			else {
				//更新			
				service.updateKV(Integer.parseInt(serial), Integer.parseInt(kind), name, kvB64, clickLink, msg, loginUser.getAccount(), DateUtil.changeToTimestamp(d1), DateUtil.changeToTimestamp(d2), Integer.parseInt(isEnabled) == 1, Integer.parseInt(isApproved) == 1, EINs);
			}
			view.addObject(Constant.ACTION_RESULT, "1");
		}
		catch(TBoxException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doConfirm(), serial: {}, name: {}, kind: {}, clickLink: {}, publish: {}, isEnabled: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), serial, name, kind, clickLink, publish, isEnabled, isApproved, p.executeTime());
		this.doIndex(view, request);
	}
	
	private void doEdit(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();		
		String serialNo = request.getParameter("serialNo"); 
		log.trace("START: {}.deEdit(), serialNo: {}", this.getClass(), serialNo);		
		try {
			
			KV kv = null;
			KVEntity entity = null;
			if(Util.isEmpty(serialNo)) {
				kv = new KV();
			}
			else {
				int sNo = Integer.parseInt(serialNo);
				kv = service.findKVBySerialNo(sNo);
				entity = service.findKVById(sNo);
			}
			
			List<KVKind> kinds = service.findAllKVKind();
			List<CompanyEntity> comps = service.findAllComp();
			
			String kvB64 = Constant.EMPTY;
			String kvPath = kv.getImgPath();
			log.debug("kvPath: {}", kvPath);
			if(!Util.isEmpty(kvPath)) {
				log.debug("kv physical path: {}", physicalPath + "/" + kvPath);
				kvB64 = StandardUtil.readFileToBase64(physicalPath + "/" + kvPath);
			}
			log.debug("kvB64: {}", kvB64);
			view.addObject(Constant.DATA_LIST, kv);
			view.addObject(KV_ENTITY, entity);
			view.addObject(KV_BASE64, kvB64);
			view.addObject(ALL_KV_KIND, kinds);
			view.addObject(ALL_KV_COMP, comps);
			view.setViewName("kv/edit");
		}
		catch(TBoxException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doEdit(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo, p.executeTime());
	}
	
	
	private void doIndex(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		log.trace("START: {}.doIndex()", this.getClass());
		try {
			AdmUser loginUser = (AdmUser) request.getSession().getAttribute(Constant.USER);
			List<KVEntity> kvs = service.findKVsByAccount(loginUser.getAccount(), 0);
			view.addObject(Constant.DATA_LIST, kvs);
			view.setViewName("kv/index");
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doIndex(), exec TIME: {} ms.", this.getClass(), p.executeTime());
	}
	
}
