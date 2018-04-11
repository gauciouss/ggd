package tbox.dispatcher.action.service.command;

import java.io.Serializable;
import java.sql.Timestamp;

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
import tbox.data.vo.OSVersion;
import tbox.service.TBoxService;

@Component("app.os.version")
public class OSVersionCommand implements Command {

	private final static Logger log = LoggerFactory.getLogger(OSVersionCommand.class);

	@Autowired
	@Qualifier("TBoxService")
	private TBoxService service;
	
	@Autowired
	@Qualifier("FILE_SERVER_PATH")
	private String fileServerPath;

	@Override
	public void execute(ModelAndView view, HttpServletRequest request, TBoxData tbox) throws TBoxException {
		Profiler p = new Profiler();
		log.trace("START: {}.execute().", this.getClass());
		OSVersion version = service.getNewestOSVersion();
		OSVersionAdapeter adapter = new OSVersionAdapeter(version);
		view.addObject(Constant.JSON_RESPONSE, adapter);
		log.info("END: {}.execute(), exec TIME: {} ms.", this.getClass(), p.executeTime());
	}

	class OSVersionAdapeter implements Serializable {

		private static final long serialVersionUID = 2024612468256540445L;
		private Integer serialNo;
		private String version;
		private String description;
		private String link;
		private Timestamp publishTime;

		public OSVersionAdapeter(OSVersion os) {
			this.serialNo = os.getSerialNo();
			this.version = os.getVersion();
			this.description = os.getDescription();
			this.link = os.getLink();
			this.publishTime = os.getPublishTime();
		}

		/**
		 * @return the serialNo
		 */
		public Integer getSerialNo() {
			return serialNo;
		}

		/**
		 * @return the version
		 */
		public String getVersion() {
			return version;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @return the link
		 */
		public String getLink() {
			return fileServerPath + link;
		}

		/**
		 * @return the publishTime
		 */
		public Timestamp getPublishTime() {
			return publishTime;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("OSVersionAdapeter [serialNo=");
			builder.append(serialNo);
			builder.append(", version=");
			builder.append(version);
			builder.append(", description=");
			builder.append(description);
			builder.append(", link=");
			builder.append(link);
			builder.append(", publishTime=");
			builder.append(publishTime);
			builder.append("]");
			return builder.toString();
		}

	}

}
