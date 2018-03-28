<%@page import="ggd.core.util.StandardUtil"%>
<%@page import="tbox.data.vo.OSVersion"%>
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
	OSVersion os = (OSVersion) request.getAttribute(Constant.DATA_LIST);
	
	String publishTime = Constant.EMPTY;
	if(os.getPublishTime() != null)
		publishTime = StandardUtil.time2String(os.getPublishTime(), "yyyy/MM/dd");
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="APP編輯" name="title" />
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>"
		name="main" />
</jsp:include>
<link rel="stylesheet" type="text/css" href="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ggd-js/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
<jsp:include page="/WEB-INF/views/include/script.jsp">
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>"
		name="main" />
</jsp:include>
<script src="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ggd-js/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ggd-js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-TW.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="card card-register mx-auto mt-5">
			<div class="card-header">OS版本編輯</div>
			<div class="card-body">
				<form name="form" method="post" class="form-control"
					action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/app">
					<input type="hidden" name="<%=Constant.ACTION_TYPE%>" id="<%=Constant.ACTION_TYPE%>" value="confirm" />				
					<input type="hidden" name="serial" id="serial" value="<%=os.getSerialNo() == null ? "" : os.getSerialNo()%>"/>	
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="version">版本</label> 
								<input type="text" id="versionStr" class="form-control version" disabled="disabled" value="<%=os.getVersion() == null ? "" : os.getVersion()  %>" />
								<input type="hidden" id="version" name="version" class="version" value="<%=os.getVersion() == null ? "" : os.getVersion()  %>"/>
							</div>							
							<div class="col-md-6">
								<label for="kind">發布時間</label> 
								<div class="input-group date">
                					<input type="text" class="form-control" id="publishTime" name="publishTime" value="<%=publishTime %>"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
              					</div>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="form-row">
							<label for="osDesc">說明</label>
							<textarea id="osDesc" name="osDesc" class="form-control">
								<%=os.getDescription() == null ? "" : os.getDescription().trim() %>
							</textarea>
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

	
	
	var registerUploadBtnEnvent = function() {
		$.blockUI({ message: "上傳中..." }); 
		var fd = new FormData();
		fd.append("apk", $("#apk")[0].files[0]);
		fd.append("<%=Constant.ACTION_TYPE %>", "uploadOS");
		$.ajax({
			url: "<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/multipart/service/admin",
			data: fd,
			contentType: false,
			processData: false,
			type: "POST",
			success: function(res) {
				console.log("******* do success *******");
				console.log(res);
				if(res.header.code == "00-000") {
					$(".version").val(res.body);
				}
				else {
					alert(res.header.msg);
				}
			},
			complete: $.unblockUI()
		});
	};
	
	
	
	
	
	var setDefaultValue = function() {
		
	};
	
	$(document).ready(function() {		
		setDefaultValue();
		$("#upload").on("click", registerUploadBtnEnvent);
		
		$('.input-group.date').datepicker({
			 language: "zh-TW",
			 format: "yyyy/mm/dd"
		 });
		
	});
	
</script>

</html>