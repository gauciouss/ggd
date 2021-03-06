<%@page import="tbox.data.vo.AppEntity"%>
<%@page import="tbox.data.vo.FastApp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ggd.core.util.JSONUtil"%>
<%@page import="tbox.data.vo.Area"%>
<%@page import="tbox.dispatcher.main.CompanyDispatcher"%>
<%@page import="tbox.data.vo.Company"%>
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
	Company comp = (Company) request.getAttribute(Constant.DATA_LIST);
	List<Area> areas = (List<Area>) request.getAttribute(CompanyDispatcher.ALL_AREA);
	List<AdmGroup> groups = (List<AdmGroup>) request.getAttribute(CompanyDispatcher.ALL_APPROVED_GROUPS);
	String json = JSONUtil.toJsonString(areas);
	
	String logo = (String) request.getAttribute(CompanyDispatcher.LOGO_BASE64);
	String bg = (String) request.getAttribute(CompanyDispatcher.BG_BASE64);
	
	List<FastApp> appsIdx = (List<FastApp>) request.getAttribute(CompanyDispatcher.FAST_INDEX_APPS);
	List<FastApp> appsCtrlP = (List<FastApp>) request.getAttribute(CompanyDispatcher.FAST_CTRL_PNL_APPS);
	List<AppEntity> allApps = (List<AppEntity>) request.getAttribute(CompanyDispatcher.ALL_APPS);
	
	String appsStr = Util.isEmpty(allApps) ? "undefined" : JSONUtil.toJsonString(allApps);
	String idxAppsStr = Util.isEmpty(appsIdx) ? "undefined" : JSONUtil.toJsonString(appsIdx);
	String ctrlAppsStr = Util.isEmpty(appsIdx) ? "undefined" : JSONUtil.toJsonString(appsCtrlP);
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="廠商編輯" name="title" />
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
			<div class="card-header">廠商編輯</div>
			<div class="card-body">
				<form name="form" method="post" class="form-control" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/comp">
					<input type="hidden" name="<%=Constant.ACTION_TYPE%>" id="<%=Constant.ACTION_TYPE%>" value="confirm" />
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="EIN">統一編號</label> 
								<input type="text" id="EIN" class="form-control" name="EIN" value="<%=Util.isEmpty(comp.getEIN()) ? "" : comp.getEIN()%>" />
							</div>
							<div class="col-md-6">
								<label for="compName">公司名稱</label> 
								<input type="text" class="form-control" id="compName" name="compName" value="<%=Util.isEmpty(comp.getName()) ? "" : comp.getName()%>" />
							</div>
						</div>
					</div>
					




					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="area">所在地區</label> 
								<select id="area" name="area" class="form-control">
									<option value="">請選擇</option>		
									<%
										for(Area area : areas) {
									%>
									<option value="<%=area.getAreaId()%>"><%=area.getAreaName() %></option>
									<%											
										}
									%>					
								</select>								
							</div>
							<div class="col-md-6">
								<label for="group">所屬群組</label> 
								<select id="group" name="group" class="form-control">
									<%
										for (AdmGroup group : groups) {
									%>
									<option value="<%=group.getGroupId()%>"><%=group.getGroupName()%></option>
									<%
										}
									%>
								</select>
							</div>
						</div>
					</div>
					
					<!-- 快捷APP -->
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6 form-group">
								<label>首頁快捷APP</label>
								<div class="panel-body">
									<div class="col-mod-3">
										<label for="fastKey1">快捷APP1</label>
										<select class="form-control apps idx-apps" id="idxApp1" name="idxApp1"></select>
									</div>
									<div class="col-mod-3">
										<label for="fastKey2">快捷APP2</label>										
										<select class="form-control apps idx-apps" id="idxApp2" name="idxApp2"></select>
									</div>
									<div class="col-mod-3">
										<label for="fastKey3">快捷APP3</label>
										<select class="form-control apps idx-apps" id="idxApp3" name="idxApp3"></select>
									</div>
									<div class="col-mod-3">
										<label for="fastKey4">快捷APP4</label>
										<select class="form-control apps idx-apps" id="idxApp3" name="idxApp4"></select>
									</div>
								</div>
							</div>
							<div class="col-md-6 form-group">
								<label>遙控器快捷APP</label>
								<div class="panel-body">
									<div class="col-mod-3">
										<label for="fastKey1">遙控器快捷APP1</label>
										<select class="form-control apps ctrl-apps" id="ctrlApp1" name="ctrlApp1"></select>
									</div>
									<div class="col-mod-3">
										<label for="fastKey2">遙控器快捷APP2</label>
										<select class="form-control apps ctrl-apps" id="ctrlApp2" name="ctrlApp2"></select>
									</div>
									<div class="col-mod-3">
										<label for="fastKey3">遙控器快捷APP3</label>
										<select class="form-control apps ctrl-apps" id="ctrlApp3" name="ctrlApp3"></select>
									</div>
									<div class="col-mod-3">
										<label for="fastKey4">遙控器快捷APP4</label>
										<select class="form-control apps ctrl-apps" id="ctrlApp4" name="ctrlApp4"></select>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					
					<!-- 圖檔 -->
					<div class="form-group" id="imgTag">	
						<div class="form-row">					
							<div class="col-md-6">
								<label for="logoFile">logo</label> 
								<input type="file" id="logoFile" name="logoFile" class="form-control"/>
								<input type="hidden" name="logoB64" id="logoB64" value="<%=logo %>"/>
								<img id="logo" name="logo" class="form-control"/>
							</div>
							<div class="col-md-6">
								<label for="bgFile">background-image</label> 
								<input type="file" id="bgFile" name="kvFile" class="form-control"/>
								<input type="hidden" name="bgB64" id="bgB64" value="<%=bg %>"/>
								<img id="bg" name="bg" class="form-control"/>
							</div>
						</div>
					</div>
					
					<div class="form-group" id="imgTag">
						
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
	
	var areas = <%=json %>;
	var logoB64 = "<%=logo %>";
	var bgB64 = "<%=bg %>";
	
	var apps = <%=appsStr %>;
	var idxApps = <%=idxAppsStr %>;
	var ctrlApps = <%=ctrlAppsStr %>;
	
	var setDefaultValue = function() {
		var group = "<%=comp.getGroup() == null ? "" : comp.getGroup().getGroupId()%>";
		$("#group").val(group);
		
		var aid = <%=comp.getArea() != null ? comp.getArea().getAreaId() : "undefined"%>;
		$("#area").val(aid).trigger("change");
		
		
		if(!ggd.util.isEmpty(logoB64)) {
			logoB64 = "data:image/png;base64," + logoB64;
		}
		
		if(!ggd.util.isEmpty(bgB64)) {
			bgB64 = "data:image/png;base64," + bgB64;
		}
		
		
		if(apps !== undefined) {
			$.each(apps, function(i, v) {
				$(".apps").append("<option value='" + v.appId + "'>" + v.name + "</option>");
			});
		}
		
		if(typeof(idxApps) != "undefined") {
			for(var i=0 ; i<idxApps.length ; i++) {
				var app = idxApps[i];
				if(typeof(app) != "undefined")
					$("#idxApp" + (i+1)).val(app.appId);
			}
		}
		
		if(typeof(ctrlApps) != "undefined") {
			for(var i=0 ; i<ctrlApps.length ; i++) {
				var app = ctrlApps[i];
				if(typeof(app) != "undefined")
					$("#ctrlApp" + (i+1)).val(app.appId);
			}
		}
	};
	
	$(document).ready(function() {
		
		setDefaultValue();
		
		ggd.util.previewFileUploadIMG({
			targetFile: $("#logoFile"),
    		targetImg: $("#logo"),
    		imgSrc: logoB64,
    		callback: function(b64) {
    			var sp = b64.split(",");    			
    			$("#logoB64").val(sp[1]);
    		}
		});
		
		ggd.util.previewFileUploadIMG({
			targetFile: $("#bgFile"),
    		targetImg: $("#bg"),
    		imgSrc: bgB64,
    		callback: function(b64) {
    			var sp = b64.split(",");
    			$("#bgB64").val(sp[1]);
    		}
		});
		
		
		
	});
	
</script>

</html>