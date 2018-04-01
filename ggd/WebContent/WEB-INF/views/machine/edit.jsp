<%@page import="ggd.core.util.StandardUtil"%>
<%@page import="ggd.core.util.JSONUtil"%>
<%@page import="tbox.data.vo.Area"%>
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
	List<Area> areas = (List<Area>) request.getAttribute(MachineDispatcher.ALL_AREA);
	
	
	String areaStr = Util.isEmpty(areas)? "undefined" : JSONUtil.toJsonString(areas);
	String compsStr = Util.isEmpty(comps) ? "undefined" : JSONUtil.toJsonString(comps);
	
	String startDate = Constant.EMPTY;
	String endDate = Constant.EMPTY;		
	
	if(box.getAuthorizedStartDate() != null) {
		startDate = StandardUtil.time2String(box.getAuthorizedStartDate(), "yyyy/MM/dd");
	}
	if(box.getAuthorizedEndDate() != null) {
		endDate = StandardUtil.time2String(box.getAuthorizedEndDate(), "yyyy/MM/dd");
	}
	
	
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
					<input type="hidden" name="serial" id="serial" value="<%=box.getSerialNo() == null ? "" : box.getSerialNo() %>"/>
					<div class="form-group">
						<div class="form-row">
							<label for="machineSN">機器序號</label> 
							<input type="text" class="form-control" name="machineSN" id="machineSN" value="<%=box.getMachineSN() == null ? "" : box.getMachineSN()  %>" />
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
							<label for="ethernetMac">網卡序號</label> 
							<input type="text" class="form-control" name="ethernetMac" id="ethernetMac" value="<%=box.getEthernetMAC() == null ? "" : box.getEthernetMAC()  %>" />
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
							<label for="wifiMac">WIFI網卡序號</label> 
							<input type="text" class="form-control" name="wifiMac" id="wifiMac" value="<%=box.getWifiMAC() == null ? "" : box.getWifiMAC()  %>" />
						</div>
					</div>
					
					<div class="form-control">
						<div class="form-row">
							<div class="col-md-6">
								<label for="area">地區</label>
								<select class="form-control" id="area" name="area"></select>
							</div>
							
							<div class="col-md-6">
								<label for="area">機台所屬公司</label>
								<select class="form-control" id="EIN" name="EIN"></select>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="account">起</label>
								<div class="input-group date">
                					<input type="text" class="form-control" id="authStart" name="authStart" value="<%=startDate %>"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
              					</div>
							</div>
							<div class="col-md-6">
								<label for="kind">迄</label> 
								<div class="input-group date">
                					<input type="text" class="form-control" id="authEnd" name="authEnd" value="<%=endDate%>"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
              					</div>
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

	var areas = <%=areaStr %>;
	var comps = <%=compsStr %>;
	var areaId = "<%=box.getArea().getAreaId() %>";
	var compId = "<%=box.getCompany().getEIN() %>";

	
	var setDefaultValue = function() {
		var area = $("#area");
		$.each(areas, function(i, v) {
			area.append("<option value='" + v.areaId + "'>" + v.areaName + "</option>");
		});		
		
		
		var EIN = $("#EIN");
		$.each(comps, function(i, v) {
			EIN.append("<option value='" + v.ein + "'>" + v.name + "</option>");
		});
		
		area.val(areaId);
		EIN.val(compId)	
		
		
	};
	
	$(document).ready(function() {		
		setDefaultValue();
		
	});
	
</script>

</html>