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
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>" name="main" />
</jsp:include>

</head>
<body>
	<div class="container">
		<div class="card card-register mx-auto mt-5">
			<div class="card-header">APP編輯</div>
			<div class="card-body">
				<form name="form" method="post" class="form-control" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/app" enctype="multipart/form-data">
					<input type="hidden" name="<%=Constant.ACTION_TYPE%>" id="<%=Constant.ACTION_TYPE%>" value="uploadApk" />
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="account">APP編號</label> 
								<input type="text" class="form-control" disabled="disabled" value="<%=app.getAppId() == null ? "" : app.getAppId()  %>" />
								<input type="hidden" id="serial" name="serial" value="<%=app.getAppId() == null ? "" : app.getAppId()  %>" />
							</div>
							<div class="col-md-6">
								<label for="kind">上傳APK</label> 
								<input type="file" id="apk" name="apk">
							</div>
						</div>
					</div>
					
					<jsp:include page="/WEB-INF/views/include/confirm.jsp">
						<jsp:param value="true" name="isEnabled" />
						<jsp:param value="true" name="isApproved" />
						<jsp:param value="true" name="showPanel"/>
						<jsp:param value="uploadApk" name="action"/>
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
	
	var registerUploadFileChangeEvent = function() {
		$("#apk").on("change", function() {
			var file = this.files[0];
			console.log(file.type);
			//if (file.type.indexOf("android") < 0) {
			if(false) {	
    			alert("上傳檔案類型有誤");
    		}
			else {
				var fd = new FormData();				
				/*var reader = new FileReader();            			
				reader.onload = function() {
					var result = this.result;						
					var jsonObj = {
					    "header": {					       
					        "action": "uploadApk"     
					    },
					    "body": {
					        "appId": "<%=app.getAppId() %>",
					        "apkB64": result
					    }
					};					
					$.ajax({
						url: "<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/action/admin/service/adminAction",
						method: "POST",
						contentType: "application/json",
						data: jsonObj,
						success: function(res) {
							console.log("**********");
							console.log(res);
						}
					});
				};
				reader.readAsDataURL(file);*/
				
			}
		});
	};
	
	$(document).ready(function() {		
		ggd.util.previewFileUploadIMG({
			targetFile: $("#kv"),
    		targetImg: $("#kvimg")
		});
		
		registerKindChangeEvent();
		
		//registerUploadFileChangeEvent();
	});
	
</script>

</html>