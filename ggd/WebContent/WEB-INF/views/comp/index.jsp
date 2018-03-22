<%@page import="tbox.data.vo.CompanyEntity"%>
<%@page import="tbox.data.vo.Company"%>
<%@page import="baytony.util.Util"%>
<%@page import="java.util.List"%>
<%@page import="ggd.core.common.Constant"%>
<%@page import="ggd.core.common.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Config display = (Config) request.getAttribute(Constant.DISPLAY_CONFIG);
	Config common = (Config) request.getAttribute(Constant.COMMON_CONFIG);
	List<CompanyEntity> comps = (List<CompanyEntity>) request.getAttribute(Constant.DATA_LIST);
	String actionResult = (String) request.getAttribute(Constant.ACTION_RESULT);
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="首頁" name="title" />
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>" name="main" />
</jsp:include>
</head> 
<body>
<form method="post" name="form" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/comp">
<input type="hidden" name="<%=Constant.ACTION_TYPE %>" id="<%=Constant.ACTION_TYPE %>"/>
<input type="hidden" name="EIN" id="EIN"/>
<div>	
	<div class="card-header">
		<i class="fa fa-table"></i>廠商管理
		<a href="#" class="btn btn-info" id="saveBtn">新增</a>
	</div>
	<div class="card-body">		
		<div class="table-responsive">
			<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
				<thead>
					<tr>
						<th>統一編號</th>
						<th>公司名稱</th>
						<th>所在地區</th>
						<th>核准</th>
						<th>啟用</th>
					</tr>
				</thead>
				<tbody>	
				<%
					if(!Util.isEmpty(comps)) {	
						for(CompanyEntity comp : comps) {
				%>
					<tr class="data" EIN="<%=comp.getEIN() %>">
						<td><%=comp.getEIN() %></td>
						<td><%=comp.getName() %></td>
						<td><%=comp.getArea() %></td>
						<td><%=comp.isEnabled() %></td>
						<td><%=comp.isApproved() %></td>
					</tr>
				<%		
						}
					}
					else {
				%>
					<tr>
						<td colspan="5">查無資料</td>
					</tr>
				<%	} %>
				</tbody>
			</table>
		</div>
	</div>
</div>
</form>
</body>
<jsp:include page="/WEB-INF/views/include/script.jsp">	
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>" name="main" />
</jsp:include>
<script>

		(function() {
			
			$(".data").on("click", function() {
				var EIN = $(this).attr("EIN");
				$("#<%=Constant.ACTION_TYPE%>").val("edit");
				$("#EIN").val(EIN);
				document.form.submit();
			});
			
			$("#searchBtn").on("click", function() {
				$("#<%=Constant.ACTION_TYPE%>").val("search");
				document.form.submit();
			});
			
			$("#saveBtn").on("click", function() {
				$("#<%=Constant.ACTION_TYPE%>").val("edit");
				document.form.submit();
			});

			<%
				if(!Util.isEmpty(comps)) {
			%>
			$("#dataTable").DataTable();
			<%
				}
			%>
			
			
			var ar = "<%=actionResult%>";			
			if(ar == "1") 
				alert("執行成功");			
			else if(ar == "0")
				alert("執行失敗");
			
		})();
	
</script>
</html>

