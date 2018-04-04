<%@page import="ggd.core.util.StandardUtil"%>
<%@page import="baytony.util.date.DateUtil"%>
<%@page import="tbox.data.vo.KVEntity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="tbox.data.vo.Company"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
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
	KV kv = (KV) request.getAttribute(Constant.DATA_LIST);
	AdmUser loginUser = (AdmUser) session.getAttribute(Constant.USER);
	List<KVKind> kinds = (List<KVKind>) request.getAttribute(KVDispatcher.ALL_KV_KIND);
	List<CompanyEntity> comps = (List<CompanyEntity>) request.getAttribute(KVDispatcher.ALL_KV_COMP);
	KVEntity kvEntity = (KVEntity) request.getAttribute(KVDispatcher.KV_ENTITY);
	
	String startDate = Constant.EMPTY;
	String endDate = Constant.EMPTY;
	if(kvEntity != null) {
		startDate = StandardUtil.time2String(kvEntity.getStartDate(), "yyyy/MM/dd");
		endDate = StandardUtil.time2String(kvEntity.getEndDate(), "yyyy/MM/dd");	
	}
	
	
	String publishJson = "[]";
	Set<Company> publish = kv.getCompanies();	
	if(!Util.isEmpty(publish)) {
		StringBuilder sb = new StringBuilder("[");
		int f = 0;		
		for(Company comp: publish) {
			if(f != 0) sb.append(",");
			sb.append("{\"id\":\"" + comp.getEIN() + "\"}");
			f++;
		}
		sb.append("]");
		publishJson = sb.toString();
	}
	
	String kvB64 = (String) request.getAttribute(KVDispatcher.KV_BASE64);
	
	int isSave = kv.getSerialNo() == null ? 1 : 0;
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="訊息編輯" name="title" />
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>"
		name="main" />
</jsp:include>
<link rel="stylesheet" type="text/css" href="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ggd-js/multi-select/multi-select.css">
<link rel="stylesheet" type="text/css" href="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ggd-js/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
<jsp:include page="/WEB-INF/views/include/script.jsp">
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>"
		name="main" />
</jsp:include>
<script src="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ggd-js/multi-select/jquery.multi-select.js"></script>
<script src="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ggd-js/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ggd-js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-TW.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="card card-register mx-auto mt-5">
			<div class="card-header">訊息編輯</div>
			<div class="card-body">
				<form name="form" method="post"
					action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/kv">
					<input type="hidden" name="<%=Constant.ACTION_TYPE%>" id="<%=Constant.ACTION_TYPE%>" value="confirm" />
					<input type="hidden" name="serial" id="serial" value="<%=kv.getSerialNo() == null ? "" : kv.getSerialNo()%>"/>
					<input type="hidden" name="isSave" id="isSave" value="<%=isSave %>"/>
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="name">名稱</label>
								<input type="text" id="name" class="form-control" name="name" value="<%=kv.getName() == null ? "" : kv.getName() %>" />
							</div>
							<div class="col-md-6">
								<label for="kind">廣告類別</label> 
								<select id="kind" name="kind" class="form-control">
									<option value="">請選擇</option>
									<%
										for(KVKind kind : kinds) {
									%>
									<option value="<%=kind.getKind() %>"><%=kind.getKindName() %></option>
									<%
										}
									%>
								</select>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
							<label for="account">連結</label>
							<input type="text" id="clickLink" class="form-control" name="clickLink" value="<%=kv.getClickLink() == null ? "" : kv.getClickLink() %>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="form-row">
							<label for="msg">訊息</label>
								<textarea id="msg" name="msg" class="form-control" style="height: 250px"><%=kv.getMsg() == null ? "" : kv.getMsg()%></textarea>
						</div>
					</div>
					
					<div class="form-group" id="imgTag">
						<div class="form-row">
							<div class="col-md-6">
								<label for="kv">圖檔</label>
								<input type="file" id="kv" class="form-control"/>
								<img id="kvimg" class="form-control"/>
								<input type="hidden" name="kvB64" id="kvB64"/>
							</div>							
						</div>
					</div>
					
					<div class="form-group">
						<label for="publishComp">發送對象</label>
						<div class="form-row">
							<select multiple="multiple" id="publishComp" name="publishComp" class="form-control">
								
								<%
									List<CompanyEntity> temp = new ArrayList<CompanyEntity>();
									temp.addAll(comps);
									if(!Util.isEmpty(publish)) {																			
										for(Company comp : publish) {
								%>
									<option selected="selected" value="<%=comp.getEIN() %>"><%=comp.getName() %></option>
								<%
										
											for(CompanyEntity entity : comps) {
												if(comp.getEIN().equals(entity.getEIN())) {
													temp.remove(entity);
												}
											}
										}
									}
								%>
								
								
								<%	
									for(CompanyEntity entity : temp) {
								%>
									<option value="<%=entity.getEIN() %>"><%=entity.getName() %></option>
								<%
									}
								%>
										
							</select>							
						</div>
					</div>
					<input type="hidden" id="publish" name="publish"/>

					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="account">起</label>
								<div class="input-group date">
                					<input type="text" class="form-control" id="start" name="start" value="<%=startDate %>"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
              					</div>
							</div>
							<div class="col-md-6">
								<label for="kind">迄</label> 
								<div class="input-group date">
                					<input type="text" class="form-control" id="end" name="end" value="<%=endDate%>"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
              					</div>
							</div>
						</div>
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

	var kind = <%=kv.getKind()%>;
	var publishJson = <%=publishJson %>;
	var kvB64 = "<%=kvB64 %>";

	var setDefaultValue = function() {
		$("#kind").val(kind);
		if(kind == 4 || kind == 6) {
			$("#imgTag").hide();
		}
		
		
		if(!ggd.util.isEmpty(kvB64)) {
			$("#kvB64").val(kvB64);
			kvB64 = "data:image/png;base64," + kvB64;			
		}
	};
	
	var registerKindChangeEvent = function() {
		$("#kind").on("change", function() {
			if($(this).val() == 4 || $(this).val() == 6) {
				$("#imgTag").hide();
			}
			else {
				$("#imgTag").show();
			}
		});
	};
	
	var registerPublishSelector = function() {
		$("#publishComp").multiSelect({
			afterSelect: function(val) {
				publishJson.push({"id":val[0]});
			},
			afterDeselect: function(val) {
				var m = publishJson.map(function(d)  {
					return d["id"];
				});
				var index = m.indexOf(val[0]);
				publishJson.splice(index, 1);
			}
		});
	};
	
	var beforeSubmit = function() {
		$("#publish").val(JSON.stringify(publishJson));
	};
	
	$(document).ready(function() {
		setDefaultValue();
		ggd.util.previewFileUploadIMG({
			targetFile: $("#kv"),
    		targetImg: $("#kvimg")
		});
		
		registerKindChangeEvent();
		registerPublishSelector();
		
		 $('.input-group.date').datepicker({
			 language: "zh-TW",
			 format: "yyyy/mm/dd"
		 });
		 
		 ggd.util.previewFileUploadIMG({
			targetFile: $("#kv"),
	    	targetImg: $("#kvimg"),
	    	imgSrc: kvB64,
	    	callback: function(b64) {
	    		var sp = b64.split(",");	    		
	    		$("#kvB64").val(sp[1]);
	    	}
		});
	});
	
</script>

</html>