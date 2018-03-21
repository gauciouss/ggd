package tbox.dispatcher.action.service.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import ggd.core.common.Constant;
import tbox.TBoxException;
import tbox.core.TBoxData;
import tbox.data.vo.Area;
import tbox.dispatcher.action.service.command.entity.AreaEntity;
import tbox.service.TBoxService;

@Component("home.area")
public class AreaCommand implements Command {
	
	private final static Logger log = LoggerFactory.getLogger(AreaCommand.class);
	
	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;

	@Override
	public void execute(ModelAndView view, HttpServletRequest request, TBoxData tbox) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.execute().", this.getClass());
		List<Area> areas = service.findAllArea();
		List<AreaEntity> entities = new ArrayList<AreaEntity>();
		areas.forEach(area -> {
			entities.add(new AreaEntity(area));
		});
		view.addObject(Constant.JSON_RESPONSE, entities);
		log.info("END: {}.execute(), exec TIME: {} ms.", this.getClass(), p.executeTime());
	}

}
