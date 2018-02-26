<%@page import="ggd.auth.dispatcher.FunctionDispatcher"%>
<%@page import="ggd.auth.vo.AdmFunc"%>
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
	AdmFunc func = (AdmFunc) request.getAttribute(Constant.DATA_LIST);
	AdmUser loginUser = (AdmUser) session.getAttribute(Constant.USER);
	List<AdmFunc> roots = (List<AdmFunc>) request.getAttribute(FunctionDispatcher.ALL_ROOT_FUNCTIONS);
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="首頁" name="title" />
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>"
		name="main" />
</jsp:include>
<jsp:include page="/WEB-INF/views/include/script.jsp">
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>"
		name="main" />
</jsp:include>
</head>
<body>
	<div class="container">
		<div class="card card-register mx-auto mt-5">
			<div class="card-header">編輯功能</div>
			<div class="card-body">
				<form name="form" method="post" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/auth/func">				
					<input type="hidden" name="<%=Constant.ACTION_TYPE%>" id="<%=Constant.ACTION_TYPE%>" value="confirm" />
					<input type="hidden" name="funcId" id="funcId" value="<%=Util.isEmpty(func.getFuncId()) ? "" : func.getFuncId() %>" />			
					<div class="form-group">
						<div class="form-row">
							<label for="funcName">功能名稱</label> 
							<input type="text" id="funcName" class="form-control" name="funcName" value="<%=Util.isEmpty(func.getFuncName()) ? "" : func.getFuncName() %>" />
						</div>
					</div>
					<div class="form-group">
							<label for="isRoot">根節點</label> 
							<select id="isRoot" name="isRoot" class="form-control">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
					</div>
					<div class="form-group">
						<label for="isRoot">上層節點</label>
						<select id="parentId" name="parentId" disabled="disabled"  class="form-control">
								<option value="">請選擇</option>
								<%	for(AdmFunc root : roots) { %>
								<option value="<%=root.getFuncId()%>"><%=root.getFuncName() %></option>
								<%	} %>
							</select>
					</div>
					<div class="form-group">
						<div class="form-row">
							<label for="url">功能連結</label> 
							<input type="text" id="url" name="url" class="form-control" disabled="disabled" value="<%=Util.isEmpty(func.getUrl()) ? "" : func.getUrl() %>"/>
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
							<label for="sort">功能排序</label> 
							<input type="text" id="sort" name="sort" class="form-control" value="<%=func.getSort() %>"/>
						</div>
					</div>

					

					<jsp:include page="/WEB-INF/views/include/confirm.jsp">
						<jsp:param value="<%=func.isEnabled()%>" name="isEnabled" />
						<jsp:param value="<%=func.isApproved()%>" name="isApproved" />
						<jsp:param value="<%=loginUser.getGroup().isManager()%>" name="isManager" />
					</jsp:include>
				</form>
			</div>
		</div>
	</div>
</body>
<script>


	var isRoot = <%=func.isRoot()%>;
	if(isRoot) {
		$("#isRoot").val("1");
	}
	else {
		
		$("#parentId").prop("disabled", false).val("<%=func.getParent() == null ? "" : func.getParent().getFuncId() %>");
		$("#url").prop("disabled", false);
	}
	
	
	//TODO open or close parentId and url
	$("#isRoot").on("change", function() {		
		if($(this).val() == "1") {
			$("#parentId").prop("disabled", true);
			$("#url").prop("disabled", true);
		}
		else {
			$("#parentId").prop("disabled", false);
			$("#url").prop("disabled", false);
		}
	});

</script>

</html>