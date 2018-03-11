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
			<div class="card-header">廠商編輯</div>
			<div class="card-body">
				<form name="form" method="post" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/comp" enctype="multipart/form-data">
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
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
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
					<!-- <div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="group">logo</label> 
								<input type="file" name="logo" id="logo" class="form-control"/>
								<% if(!Util.isEmpty(comp.getLogoURL())) { %>
								<a href="#" class="img" type="logo">預覽</a>
								<% } %>
							</div>
							<div class="col-md-6">
								<label for="group">背景圖</label> 
								<input type="file" name="bgimg" id="bgimg" class="form-control"/>
								<% if(!Util.isEmpty(comp.getBackgroundURL())) { %>
								<a href="#" class="img" type="bg">預覽</a>
								<% } %>
							</div>
						</div>
					</div> -->
					<div class="form-group" id="imgTag">
						<div class="form-row">
							<input type="file" id="logoFile" name="logoFile" class="form-control"/>
							<input type="hidden" is="logoB64" name="logoB64"/>
							<img id="logo" name="logo" class="form-control"/>
						</div>
					</div>
					
					<div class="form-group" id="imgTag">
						<div class="form-row">
							<input type="file" id="kv" class="form-control"/>
							<img id="kvimg" class="form-control"/>
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
	
	var areas = <%=json %>;
	
	var showIMG = function(type) {
		window.open("<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/comp?<%=Constant.ACTION_TYPE%>=showIMG&EIN=<%=comp.getEIN()%>&type=" + type, "img", "width=200,height=100,top=200");
	};
	
	var createCityOption = function(aid) {
		var city = $("#city");
		city.empty();
		if(!ggd.util.isEmpty(aid)) {
			$.each(areas, function(i, v) {
				if(v.parentId == aid) {
					city.append("<option value='" + v.areaId + "'>" + v.areaName + "</option>");
				}
			});
		}
		else {
			city.append("<option value=''>請選擇</option>");
		}
	};
	
	var setDefaultValue = function() {
		var group = "<%=comp.getGroup() == null ? "" : comp.getGroup().getGroupId()%>";
		$("#group").val(group);
		
		var cid = <%=comp.getArea() != null ? comp.getArea().getAreaId() : "undefined"%>;
		if(!ggd.util.isEmpty(cid)) {
			var aid = "";
			$.each(areas, function(i, area) {
				$.each(area.citys, function(i, city) {
					if(cid == city.areaId) {
						aid = area.areaId;
						return false;
					}
				});
				if(!ggd.util.isEmpty(aid))
					return false;
			});
			$("#area").val(aid).trigger("change");
			$("#city").val(cid);
		}
		
	};
	
	$(document).ready(function() {
		$("#area").on("change", function(i, v) {
			var aid = $(this).val();
			createCityOption(aid);
		});
		
		$(".img").on("click", function() {
			showIMG($(this).attr("type"));
		});
		
		setDefaultValue();
		
		
	});
	
</script>

</html>