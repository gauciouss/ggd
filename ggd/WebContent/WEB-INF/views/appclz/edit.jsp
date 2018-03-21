<%@page import="tbox.dispatcher.main.AppClzDispatcher"%>
<%@page import="tbox.data.vo.App"%>
<%@page import="tbox.data.vo.AppClz"%>
<%@page import="tbox.dispatcher.main.AppDispatcher"%>
<%@page import="tbox.data.vo.CompanyEntity"%>
<%@page import="tbox.dispatcher.main.KVDispatcher"%>
<%@page import="tbox.data.vo.KVKind"%>
<%@page import="tbox.data.vo.KV"%>
<%@page import="ggd.auth.dispatcher.UserDispatcher"%>
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
	AdmUser loginUser = (AdmUser) session.getAttribute(Constant.USER);
	AppClz appClz = (AppClz) request.getAttribute(Constant.DATA_LIST);
	String iconB64 = (String) request.getAttribute(AppClzDispatcher.ICON_BASE64);
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="APP類別編輯" name="title" />
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>"
		name="main" />
</jsp:include>
<jsp:include page="/WEB-INF/views/include/script.jsp">
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>" name="main" />
</jsp:include>

</head>
<body>
	<div class="container">
		<div class="card card-register mx-auto mt-5">
			<div class="card-header">APP類別編輯</div>
			<div class="card-body">
				<form name="form" method="post" class="form-control" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/appclz">
					<input type="hidden" name="<%=Constant.ACTION_TYPE%>" id="<%=Constant.ACTION_TYPE%>" value="edit" />
					<input type="hidden" name="serial", id="serial" value="<%=appClz.getClzId() == null ? "" : appClz.getClzId()  %>"/>
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="serial">類別編號</label> 
								<input type="text" class="form-control" disabled="disabled" value="<%=appClz.getClzId() == null ? "" : appClz.getClzId()  %>" />
							</div>
							<div class="col-md-6">
								<label for="name">類別名稱</label> 
								<input type="text" id="name" class="form-control" name="name" value="<%=appClz.getClzName() == null ? "" : appClz.getClzName()  %>" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="icon">類別圖檔</label>
								<input type="file" id="icon" class="form-control"/>
								<img id="iconImg" class="form-control"/>
								<input type="hidden" name="iconB64" id="iconB64"/>
							</div>
						</div>
					</div>
					
					<jsp:include page="/WEB-INF/views/include/confirm.jsp">
						<jsp:param value="true" name="isEnabled" />
						<jsp:param value="true" name="isApproved" />
						<jsp:param value="false" name="showPanel"/>						
						<jsp:param value="<%=loginUser.getGroup().isManager()%>" name="isManager" />
					</jsp:include>
				</form>
			</div>
		</div>
	</div>
</body>


<script>

	var iconB64 = "<%=iconB64 %>";
	
	var setDefaultValue = function() {
		if(!ggd.util.isEmpty(iconB64)) {
			$("#iconB64").val(iconB64);
			iconB64 = "data:image/png;base64," + iconB64;			
		}
	};
	
	$(document).ready(function() {		
		setDefaultValue();
		ggd.util.previewFileUploadIMG({
			targetFile: $("#icon"),
    		targetImg: $("#iconImg"),
    		imgSrc: iconB64,
	    	callback: function(b64) {
	    		var sp = b64.split(",");	    		
	    		$("#iconB64").val(sp[1]);
	    	}
		});
		
	});
	
</script>

</html>