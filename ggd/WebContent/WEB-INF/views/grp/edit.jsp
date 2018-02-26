<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="ggd.auth.vo.AdmFunc"%>
<%@page import="ggd.auth.dispatcher.GroupDispatcher"%>
<%@page import="baytony.util.Util"%>
<%@page import="ggd.auth.vo.AdmGroup"%>
<%@page import="java.util.List"%>
<%@page import="ggd.core.common.Constant"%>
<%@page import="ggd.auth.vo.AdmUser"%>
<%@page import="ggd.core.common.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Config display = (Config) request.getAttribute(Constant.DISPLAY_CONFIG);
	Config common = (Config) request.getAttribute(Constant.COMMON_CONFIG);
	AdmGroup grp = (AdmGroup) request.getAttribute(Constant.DATA_LIST);
	AdmUser loginUser = (AdmUser) session.getAttribute(Constant.USER);	
	List<AdmFunc> funcs = (List<AdmFunc>) request.getAttribute(GroupDispatcher.ALL_APPROVE_FUNCS);
	Set<AdmFunc> selectedFunc = grp.getFuncs();
	
	String selectedFuncStr = "undefined";
	StringBuilder sb = new StringBuilder();
	if(!Util.isEmpty(selectedFunc)) {
		int f = 0;
		sb.append("[");
		for(AdmFunc fc : selectedFunc) {
			if(fc.isRoot()) continue;
			if(f != 0) sb.append(",");
			sb.append("{\"id\":\"" + fc.getFuncId() + "\"}");
			f++;
		}
		sb.append("]");
		selectedFuncStr = sb.toString();
	}
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="首頁" name="title" />
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>" name="main" />
</jsp:include>
<link rel="stylesheet" type="text/css" href="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ggd-js/multi-select/multi-select.css">
<jsp:include page="/WEB-INF/views/include/script.jsp">
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>" name="main" />
</jsp:include>
<script src="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ggd-js/multi-select/jquery.multi-select.js"></script>
</head>
<body>
	<div class="container">
		<div class="card card-register mx-auto mt-5">
			<div class="card-header">編輯群組</div>
			<div class="card-body">
				<form name="form" method="post" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/auth/grp">				
					<input type="hidden" name="<%=Constant.ACTION_TYPE%>" id="<%=Constant.ACTION_TYPE%>" value="confirm" />
					<input type="hidden" name="groupId" id="groupId" value="<%=Util.isEmpty(grp.getGroupId()) ? "" : grp.getGroupId()%>" />			
					<div class="form-group">
						<div class="form-row">
							<label for="groupName">群組名稱</label> 
							<input type="text" id="groupName" class="form-control" name="groupName" value="<%=Util.isEmpty(grp.getGroupName()) ? "" : grp.getGroupName()%>" />
						</div>
					</div>
					<div class="form-group">
							<label for="isManager">管理者</label> 
							<select id="isManager" name="isManager" class="form-control">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
					</div>
					
					<div class="form-group">
						<label for="isManager">可執行功能設定</label>
						<div class="form-inline">
							<select multiple="multiple" id="funcs" name="funcs" class="form-control">
								<%
									Map<AdmFunc, List<AdmFunc>> map = new HashMap<AdmFunc, List<AdmFunc>>();
									for(AdmFunc func : funcs) {
										if(func.isRoot()) {
											List<AdmFunc> list = map.get(func);
											if(Util.isEmpty(list)) {
												list = new ArrayList<AdmFunc>();
												map.put(func, list);
											}
										}
										else {
											List<AdmFunc> list = map.get(func.getParent());
											list.add(func);
										}
									}
								%>
								
								<%
									Set<AdmFunc> keys = map.keySet();
									for(AdmFunc key : keys) {
								%>
										<optgroup label="<%=key.getFuncName()%>">
								<%
										List<AdmFunc> list = map.get(key);
											for(AdmFunc func : list) {
												if(selectedFunc.contains(func)) {
								%>												
												<option value="<%=func.getFuncId()%>" selected="selected"><%=func.getFuncName() %></option>								
								<%				
												}
												else {
								%>
												<option value="<%=func.getFuncId()%>"><%=func.getFuncName() %></option>
								<%
												}
											}
								%>
										</optgroup>
								<%
									}
								%>						
							</select>							
						</div>
					</div>
					<input type="hidden" id="execFunc" name="execFunc"/>

					<jsp:include page="/WEB-INF/views/include/confirm.jsp">
						<jsp:param value="<%=grp.isEnabled()%>" name="isEnabled" />
						<jsp:param value="<%=grp.isApproved()%>" name="isApproved" />
						<jsp:param value="<%=loginUser.getGroup().isManager()%>" name="isManager" />
					</jsp:include>
				</form>
			</div>
		</div>
	</div>
</body>
<script>


	var isManager = <%=grp.isManager()%>;
	if(isManager) {
		$("#isManager").val("1").trigger("change");	
	}	
	
	var fJson = <%=selectedFuncStr %>;
	$("#execFunc").val(JSON.stringify(fJson));
	
	$("#funcs").multiSelect({
		afterSelect: function(val) {			
			fJson.push({"id":val[0]});
			$("#execFunc").val(JSON.stringify(fJson));
		},
		afterDeselect: function(val) {			
			var m = fJson.map(function(d) {
				return d["id"];
			});			
			var index = m.indexOf(val[0]);
			fJson.splice(index, 1);
		}
	});
	
	
</script>

</html>