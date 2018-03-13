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
				<form name="form" method="post" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/comp">
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
							<div class="col-md-3">
								<label for="fastKey1">快捷APP1</label>
								<input type="text" name="fastKey1" id="fastKey1" class="form-control"/>
							</div>
							<div class="col-md-3">
								<label for="fastKey2">快捷APP2</label>
								<input type="text" name="fastKey2" id="fastKey2" class="form-control"/>
							</div>
							<div class="col-md-3">
								<label for="fastKey3">快捷APP3</label>
								<input type="text" name="fastKey3" id="fastKey3" class="form-control"/>
							</div>
							<div class="col-md-3">
								<label for="fastKey4">快捷APP4</label>
								<input type="text" name="fastKey4" id="fastKey4" class="form-control"/>
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
		
	};
	
	$(document).ready(function() {
		
		setDefaultValue();
		
		ggd.util.previewFileUploadIMG({
			targetFile: $("#logoFile"),
    		targetImg: $("#logo"),
    		imgSrc: logoB64,
    		callback: function(b64) {
    			var sp = b64.split(",");    			
    			console.log(sp[1]);
    			$("#logoB64").val(sp[1]);
    		}
		});
		
		ggd.util.previewFileUploadIMG({
			targetFile: $("#bgFile"),
    		targetImg: $("#bg"),
    		imgSrc: bgB64,
    		callback: function(b64) {
    			var sp = b64.split(",");    			
    			console.log(sp[1]);
    			$("#bgB64").val(sp[1]);
    		}
		});
		
		
		
	});
	
</script>

</html>