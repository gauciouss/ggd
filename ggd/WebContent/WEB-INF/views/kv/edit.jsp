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
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="訊息編輯" name="title" />
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
			<div class="card-header">訊息編輯</div>
			<div class="card-body">
				<form name="form" method="post"
					action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/auth/user">
					<input type="hidden" name="<%=Constant.ACTION_TYPE%>"
						id="<%=Constant.ACTION_TYPE%>" value="confirm" />
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="account">訊息編號</label> 
								<input type="text" id="serial" class="form-control" name="serial" disabled="disabled" value="<%=kv.getSerialNo() %>" />
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
							<input type="text" id="clickLink" class="form-control" name="clickLink" value="<%=kv.getClickLink() %>" />
						</div>
					</div>
					<div class="form-group" id="imgTag">
						<div class="form-row">
							<input type="file" id="kv" class="form-control"/>
							<img id="kvimg" class="form-control"/>
						</div>
					</div>
					
					<div class="form-group">
						<div class="form-row">
						<label for="account">發送對象</label>
						<%	for(CompanyEntity comp : comps) { %>						
							<input type="checkbox" class="form-check-input" value="<%=comp.getEIN() %>"/> 				
  							<label class="form-check-label" for="defaultCheck1"><%=comp.getName() %></label>
						<%	} %>
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

	var setDefaultValue = function() {
		$("#kind").val(kind);
		if(kind == 4 || ggd.util.isEmpty($("#kvimg").attr("src"))) {
			$("#kvimg").hide();
		}
	};
	
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
	
	$(document).ready(function() {
		setDefaultValue();
		ggd.util.previewFileUploadIMG({
			targetFile: $("#kv"),
    		targetImg: $("#kvimg")
		});
		
		registerKindChangeEvent();
	});
	
</script>

</html>