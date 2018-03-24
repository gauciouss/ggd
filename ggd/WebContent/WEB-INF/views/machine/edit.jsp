<%@page import="tbox.dispatcher.main.MachineDispatcher"%>
<%@page import="tbox.data.vo.MachineBox"%>
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
	MachineBox box = (MachineBox) request.getAttribute(Constant.DATA_LIST);
	List<CompanyEntity> comps = (List<CompanyEntity>) request.getAttribute(MachineDispatcher.ALL_COMPANY);
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="機上盒編輯" name="title" />
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
			<div class="card-header">機上盒編輯</div>
			<div class="card-body">
				<form name="form" method="post" class="form-control" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/machine">
					<input type="hidden" name="<%=Constant.ACTION_TYPE%>" id="<%=Constant.ACTION_TYPE%>" value="edit" />
					<input type="hidden" name="serial", id="serial" value="<%=box.getSerialNo() == null ? "" : box.getSerialNo() %>"/>
					<div class="form-group">
						<div class="form-row">
							<label for="serial">機器序號</label> 
							<input type="text" class="form-control" disabled="disabled" value="<%=box.getMachineSN() == null ? "" : box.getMachineSN()  %>" />
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
							<label for="serial">網卡序號</label> 
							<input type="text" class="form-control" disabled="disabled" value="<%=box.getEthernetMAC() == null ? "" : box.getEthernetMAC()  %>" />
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
							<label for="serial">WIFI網卡序號</label> 
							<input type="text" class="form-control" disabled="disabled" value="<%=box.getWifiMAC() == null ? "" : box.getWifiMAC()  %>" />
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

	
	var setDefaultValue = function() {
	};
	
	$(document).ready(function() {		
		setDefaultValue();
		
	});
	
</script>

</html>