<%@page import="ggd.core.util.JSONUtil"%>
<%@page import="com.fasterxml.jackson.databind.JsonNode"%>
<%@page import="ggd.auth.vo.AdmFunc"%>
<%@page import="java.util.Set"%>
<%@page import="ggd.auth.vo.AdmUser"%>
<%@page import="ggd.core.common.Constant"%>
<%@page import="ggd.core.common.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%!

	private static final String TREE_NODE_PARAM = "{id: '%1$s', pId: '%2$s', name: '%3$s', funcURL: '%4$s', click: false}";
	
	public String createTreeNodeJson(Set<AdmFunc> funcs) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int i = 0;
		for(AdmFunc func : funcs) {
			if(i != 0) 
				sb.append(",");
			if(func.isRoot()) {
				String p = String.format(TREE_NODE_PARAM, func.getFuncId(), "0", func.getFuncName(), "");
				sb.append(p);
			}
			else {
				String s = String.format(TREE_NODE_PARAM, func.getFuncId(), func.getParent().getFuncId(), func.getFuncName(), func.getUrl());
				sb.append(s);
			}			
			i++;
		}
		sb.append("]");
		return sb.toString();
	}

%>
	
<%
	Config display = (Config) request.getAttribute(Constant.DISPLAY_CONFIG);
	Config common = (Config) request.getAttribute(Constant.COMMON_CONFIG);
	AdmUser user = (AdmUser) session.getAttribute(Constant.USER);
	Set<AdmFunc> funcs = user.getGroup().getFuncs();
	String tn = this.createTreeNodeJson(funcs);
%>	
<!DOCTYPE>
<html>
<head>
<title><%=display.getValue("PAGE_TITLE")%> - 首頁</title>
<link rel="stylesheet" type="text/css" href="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ggd-js/zTree/zTreeStyle/zTreeStyle.css">
</head>
<body>
	<div class="ui-layout-center">
		<iframe id="mainFunc" height="100%" width="100%" frameBorder="0" src=""></iframe>
	</div>
	<div class="ui-layout-north">North</div>
	<div class="ui-layout-west">
		<ul id="tree" class="ztree"></ul>
	</div>
</body>

<script src="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ggd-js/jquery-1.11.3.min.js"></script>
<script src="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ggd-js/jquery.layout.js"></script>
<script src="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ggd-js/zTree/jquery.ztree.all.min.js"></script>
<script>

	var tn = <%=tn %>;
	
	zTreeSetting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: undefined,
			onClick: function(event, treeId, treeNode, clickFlag) {
				console.log(treeNode);
				var pid = treeNode.pId;
				if(pid !== undefined && pid != null && pid.length > 0) {
					var url = treeNode.funcURL;
					console.log(url);
					$("#mainFunc").attr("src", "<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/" + url);
				}
			}
		}
	};

	$(document).ready(function () {
		$.fn.zTree.init($("#tree"), zTreeSetting, tn);
		$('body').layout({ applyDemoStyles: true });
	});
	
</script>

</html>