/**
 * 
 */
package tbox.dispatcher.main;

import java.io.IOException;
import java.sql.Timestamp;
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
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import ggd.core.util.JSONUtil;
import tbox.TBoxException;
import tbox.data.vo.Area;
import tbox.data.vo.CompanyEntity;
import tbox.data.vo.MachineBox;
import tbox.data.vo.MachineEntity;
import tbox.service.TBoxService;

/**
 * @author admin
 *
 */
@Component("main.machine")
public class MachineDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(MachineDispatcher.class);
	
	public static final String IMPORT_COUNT = MachineDispatcher.class + "_COUNT";
	public static final String ALL_COMPANY = MachineDispatcher.class + "_ALL_COMPANY";
	public static final String ALL_AREA = MachineDispatcher.class +"_ALL_AREA";
	
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;

	/* (non-Javadoc)
	 * @see ggd.core.dispatcher.Dispatcher#handler(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void handler(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		String action = request.getParameter(Constant.ACTION_TYPE);		
		action = Util.isEmpty(action) ? "index" : action;
		log.trace("START: {}.handler(), action: {}", this.getClass(), action);
		try {
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
			case "upload":
				upload(view, request);
				break;
			case "index":
				doIndex(view, request);
				break;			
			default:
				doIndex(view, request);
				break;
		}
		}
		catch(TBoxException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(NumberFormatException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.handler(), action: {}, exec TIME: {} ms.", this.getClass(), action, p.executeTime());
	}
	
	private void upload(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		log.trace("START: {}.upload()", this.getClass());
		String csvData = request.getParameter("csvData");
		try {
			JsonNode node = JSONUtil.parser(csvData);
			int count = 0;
			List<MachineEntity> list = new ArrayList<MachineEntity>();
			for(JsonNode box : node) {
				String sn = box.get("machineSN").asText();
				String mac = box.get("mac") == null ? "" : box.get("mac").asText();
				String wifiMac = box.get("wifiMac") == null ? "" : box.get("wifiMac").asText();
				int area = box.get("area") == null ? 1 : box.get("area").asInt();
				String EIN = box.get("EIN") == null ? "" : box.get("EIN").asText();
				String authStart = box.get("authStart") == null ? "" : box.get("authStart").asText();
				String authEnd = box.get("authEnd") == null ? "" : box.get("authEnd").asText();				
				Timestamp authStartDate = Util.isEmpty(authStart) ? null : new Timestamp(new Date(authStart).getTime());
				Timestamp authEndDate = Util.isEmpty(authStart) ? null : new Timestamp(new Date(authEnd).getTime());				
				MachineEntity entity = new MachineEntity(sn, mac, wifiMac, EIN, area, authStartDate, authStartDate, authEndDate);
				list.add(entity);
			}
			count = service.importMachineBoxData(list);
			view.addObject(IMPORT_COUNT, count);
			view.addObject(Constant.ACTION_RESULT, "1");
			log.info("END: {}.upload(), exec TIME: {} ms.", this.getClass(), p.executeTime());
			this.doIndex(view, request);
		}
		catch (IOException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		} 
		catch (TBoxException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		
	}

	private void doConfirm(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		String serial = request.getParameter("serial");
		String machineSN = request.getParameter("machineSN");
		String ethernetMac = request.getParameter("ethernetMac");
		String wifiMac = request.getParameter("wifiMac");
		String area = request.getParameter("area");
		String EIN  = request.getParameter("EIN");
		String authStart = request.getParameter("authStart");
		String authEnd = request.getParameter("authEnd");
		log.trace("START: {}.doConfirm() exec start, machineSN: {}, ethernetMac: {}, wifiMac: {}, area: {}, EIN: {}, authStart: {}, authEnd: {}.", this.getClass(), machineSN, ethernetMac, wifiMac, area, EIN, authStart, authEnd);
		Timestamp authStartDate = Util.isEmpty(authStart) ? null : new Timestamp(new Date(authStart).getTime());
		Timestamp authEndDate = Util.isEmpty(authStart) ? null : new Timestamp(new Date(authEnd).getTime());	
		MachineEntity entity = new MachineEntity(machineSN, ethernetMac, wifiMac, EIN, Integer.parseInt(area), authStartDate, authStartDate, authEndDate);
		try {
			if(Util.isEmpty(serial)) {
				service.saveMachineBox(entity);
			}
			else {
				service.updateMachineBox(entity);
			}
			view.addObject(Constant.ACTION_RESULT, "1");
			log.info("END: {}.doConfirm() exec start, machineSN: {}, ethernetMac: {}, wifiMac: {}, area: {}, EIN: {}, authStart: {}, authEnd: {}, exec TIME: {} ms.", this.getClass(), machineSN, ethernetMac, wifiMac, area, EIN, authStart, authEnd, p.executeTime());
			
		}
		catch(Exception e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}		
		this.doIndex(view, request);		
	}
	
	private void doEdit(ModelAndView view, HttpServletRequest request) throws NumberFormatException, TBoxException {
		Profiler p = new Profiler();		
		String serialNo = request.getParameter("serialNo"); 
		log.trace("START: {}.deEdit(), serialNo: {}", this.getClass(), serialNo);
		MachineBox box = Util.isEmpty(serialNo) ? new MachineBox() : service.findMachine(Integer.parseInt(serialNo));
		view.setViewName("machine/edit");
		List<CompanyEntity> list = service.findAllComp();
		List<Area> areas = service.findAllArea();
		view.addObject(ALL_COMPANY, list);
		view.addObject(ALL_AREA, areas);
		view.addObject(Constant.DATA_LIST, box);
		log.info("END: {}.doEdit(), serialNo: {}, exec TIME: {} ms.", this.getClass(), serialNo, p.executeTime());
	}
	
	private void doIndex(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		log.trace("START: {}.doIndex()", this.getClass());
		try {
			List<MachineEntity> list = service.findAllMachine();
			log.debug("list machine size: {}", list.size());
			view.addObject(Constant.DATA_LIST, list);
			view.setViewName("machine/index");
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doIndex(), exec TIME: {} ms.", this.getClass(), p.executeTime());
	}
}
