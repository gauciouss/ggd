<%@page import="tbox.data.vo.AppEntity"%>
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
	AppEntity app = (AppEntity) request.getAttribute(Constant.DATA_LIST);
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
								<label for="serial">APP編號</label> 
								<input type="text" class="form-control serial" disabled="disabled" value="<%=app.getAppId() == null ? "" : app.getAppId()  %>" />
								<input type="hidden" id="serial" name="serial" class="serial" value="<%=app.getAppId() == null ? "" : app.getAppId()  %>"/>
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
						<div class="form-row">
							<div class="col-md-6">
								<label for="appName">APP名稱</label>
								<input type="text" id="appName" name="appName" class="form-control" value="<%=app.getName() == null ? "" : app.getName() %>"/>
							</div>
							<div class="col-md-6">
								<label for="appEngName">APP英文名稱</label>
								<input type="text" id="appEngName" name="appEngName" class="form-control" value="<%=app.getEngName() == null ? "" : app.getEngName() %>"/>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="appDesc">版本</label>
								<input type="text" id="versionStr" class="form-control version" disabled="disabled" value="<%=app.getVersion() == null ? "" : app.getVersion() %>"/>
								<input type="hidden" id="version" name="version" class="version" value="<%=app.getVersion() == null ? "" : app.getVersion() %>"/>
							</div>
							<div class="col-md-6">
								<label for="pkgName">pkg名稱</label>
								<input type="text" class="form-control pkgName" disabled="disabled" value="<%=app.getPkgName() == null ? "" : app.getPkgName() %>"/>
								<input type="hidden" id="pkgName" name="pkgName" class="pkgName" value="<%=app.getPkgName() == null ? "" : app.getPkgName() %>"/>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="appDesc">APP說明</label>
								<textarea id="appDesc" name="appDesc" class="form-control">
									<%=app.getDesc() == null ? "" : app.getDesc().trim() %>
								</textarea>
							</div>
							<div class="col-md-6">
								<label for="hdpi">hdpi</label>
								<img id="hdpi" class="form-control"/>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<input type="file" id="apk" name="apk">
						<a href="#" id="upload" class="btn btn-primary">上傳</a>						
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

	var kind = <%=app.getClzId() != null ? app.getClzId() : "undefined" %>;
	var hdpi = "<%=common.getValue("FILE_SERVER_PATH") + "/" + app.getIconPath()%>";
	
	
	var registerUploadBtnEnvent = function() {
		$.blockUI({ message: "上傳中..." }); 
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
				if(res.header.code == "00-000") {
					var body = res.body;
					$("#appName").val(body.appName);
					$("#appEngName").val(body.appName);
					$(".pkgName").val(body.pkgName);
					$("#appDesc").text(body.desc);
					$(".version").val(body.versionName);
					var icons = body.iconPath;
					$.each(icons, function(i, p) {
						p = "/fileserver/app/" + $("#serial").val() + "/temp/" + p
						if(p.indexOf("-hdpi-") > 0) {
							$("#hdpi").attr("src", p);
						}
					});
					$("#hdpi").trigger("refresh");
				}
				else {
					alert(res.header.msg);
				}
			},
			complete: $.unblockUI()
		});
	};
	
	
	
	
	
	var setDefaultValue = function() {
		var iconPath = "<%=app.getIconPath() == null ? "" : app.getIconPath() %>";
		if(!ggd.util.isEmpty(iconPath)) {
			iconPath = "<%=common.getValue("FILE_SERVER_PATH")%>" + iconPath;
			console.log(iconPath);
			$("#hdpi").attr("src", iconPath);
		}
		
		$("#kind").val(kind);		
	};
	
	$(document).ready(function() {		
		setDefaultValue();
		$("#upload").on("click", registerUploadBtnEnvent);
		
	});
	
</script>

</html>