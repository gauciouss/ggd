package tbox.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import baytony.util.StringUtil;
import ggd.core.util.JSONUtil;

public class TBoxDataImpl implements TBoxData {
	
	private static final long serialVersionUID = -4156373961394841280L;

	private final static Logger log = LoggerFactory.getLogger(TBoxDataImpl.class);
	
	private String arg;
	
	private String folder;
	
	private String jsp;
	
	private TBoxInfo request;
	
	private boolean isJson;
	
	public TBoxDataImpl(String folder, String jsp, String arg, String clientIP) {
		this.folder = folder;
		this.jsp = jsp;
		this.arg = arg;
		JsonNode node = null;
		try {
			node = JSONUtil.parser(arg);
			log.trace("JsonNode Created! Start Parser MACHINE_INFO.");
			this.request = new TBoxInfoImpl(node, clientIP);
			this.isJson = true;
			log.debug("{}", this.toString());
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
			this.isJson = false;
		}
	}

	/* (non-Javadoc)
	 * @see tbox.core.TBoxData#getArg()
	 */
	@Override
	public String getArg() {
		return arg;
	}

	/* (non-Javadoc)
	 * @see tbox.core.TBoxData#getFolder()
	 */
	@Override
	public String getFolder() {
		return folder;
	}

	/* (non-Javadoc)
	 * @see tbox.core.TBoxData#getJsp()
	 */
	@Override
	public String getJsp() {
		return jsp;
	}

	/* (non-Javadoc)
	 * @see tbox.core.TBoxData#isArgJson()
	 */
	@Override
	public boolean isArgJson() {
		return isJson;
	}

	/* (non-Javadoc)
	 * @see tbox.core.TBoxData#getTBoxRequest()
	 */
	@Override
	public TBoxInfo getTBoxInfo() {
		return request;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TBoxDataImpl [arg=");
		builder.append(arg);
		builder.append(", folder=");
		builder.append(folder);
		builder.append(", jsp=");
		builder.append(jsp);
		builder.append(", request=");
		builder.append(request);
		builder.append(", isJson=");
		builder.append(isJson);
		builder.append("]");
		return builder.toString();
	}

	
}
