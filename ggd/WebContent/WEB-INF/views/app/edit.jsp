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
	List<AppClz> kinds = (List<AppClz>) request.getAttribute(AppDispatcher.ALL_APP_KIND);
	App app = (App) request.getAttribute(Constant.DATA_LIST);
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="APP編輯" name="title" />
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
			<div class="card-header">APP編輯</div>
			<div class="card-body">
				<form name="form" method="post" class="form-control"
					action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/app">
					<input type="hidden" name="<%=Constant.ACTION_TYPE%>" id="<%=Constant.ACTION_TYPE%>" value="confirm" />
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="account">APP編號</label> 
								<input type="text" id="serial" class="form-control" name="serial" disabled="disabled" value="<%=app.getAppId() == null ? "" : app.getAppId()  %>" />
							</div>
							<div class="col-md-6">
								<label for="kind">類別</label> 
								<select id="kind" name="kind" class="form-control">
									<option value="">請選擇</option>
									<%
										for(AppClz kind : kinds) {
									%>
									<option value="<%=kind.getClzId() %>"><%=kind.getClzName() %></option>
									<%
										}
									%>
								</select>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<input type="file" id="apk" name="apk">
						<a href="#" id="upload">上傳</a>						
					</div>
					
					
					

					<jsp:include page="/WEB-INF/views/include/confirm.jsp">
						<jsp:param value="true" name="isEnabled" />
						<jsp:param value="true" name="isApproved" />
						<jsp:param value="true" name="showPanel"/>
						<jsp:param value="<%=loginUser.getGroup().isManager()%>" name="isManager" />
					</jsp:include>
				</form>
			</div>
		</div>
	</div>
</body>


<script>

	var kind = <%=app.getClz() != null ? app.getClz().getClzId() : "undefined" %>;

	
	var registerKindChangeEvent = function() {
		$("#kvimg").on("change", function() {
			if($(this).val() == 4) {
				$("#imgTag").hide();
			}
			else {
				$("#imgTag").show();
			}
		});
	};
	
	var registerUploadBtnEnvent = function() {
		var fd = new FormData();
		fd.append("apk", $("#apk")[0].files[0]);
		fd.append("serial", "<%=app.getAppId() %>")
		fd.append("<%=Constant.ACTION_TYPE %>", "uploadApk");
		$.ajax({
			//url: "<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/app",
			url: "<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/multipart/service/admin",
			data: fd,
			contentType: false,
			processData: false,
			type: "POST",
			success: function(res) {
				console.log("******* do success *******");
				console.log(res);
			}
		});
	};
	
	$(document).ready(function() {		
		ggd.util.previewFileUploadIMG({
			targetFile: $("#kv"),
    		targetImg: $("#kvimg")
		});
		
		registerKindChangeEvent();
		
		$("#upload").on("click", registerUploadBtnEnvent);
		
	});
	
</script>

</html>