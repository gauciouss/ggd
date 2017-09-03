<%@page import="baytony.util.Util"%>
<%@page import="ggd.auth.vo.AdmUser"%>
<%@page import="java.util.List"%>
<%@page import="ggd.core.common.Constant"%>
<%@page import="ggd.core.common.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Config display = (Config) request.getAttribute(Constant.DISPLAY_CONFIG);
	Config common = (Config) request.getAttribute(Constant.COMMON_CONFIG);
	List<AdmUser> users = (List<AdmUser>) request.getAttribute(Constant.DATA_LIST);
	Integer iPage = (Integer) request.getAttribute(Constant.PAGE);	
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/script.jsp">
	<jsp:param value="使用者管理" name="title"/>
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST) %>" name="main"/>
</jsp:include>
</head>
<body>
<form method="post" name="form" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/auth/user">
<input type="hidden" name="<%=Constant.ACTION_TYPE %>" id="<%=Constant.ACTION_TYPE %>"/>
<h3>使用者管理</h3>
<div>

使用者帳號/姓名: <input type="text" class="text" name="account" id="account"/>&nbsp;&nbsp;
<input type="button" value="查詢" id="searchBtn"/>

</div>
<div>	
	<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th>帳號</th>
			<th>姓名</th>
			<th>核准</th>
			<th>啟用</th>
		</tr>
	</thead>
	<tbody>	
	<%
		if(!Util.isEmpty(users)) {	
			for(AdmUser user : users) {
	%>
		<tr class="user" account="<%=user.getAccount() %>">
			<td><%=user.getAccount() %></td>
			<td><%=user.getName() %></td>
			<td><%=user.isEnabled() %></td>
			<td><%=user.isApproved() %></td>
		</tr>
	<%		
			}
		}
		else {
	%>
		<tr>
			<td colspan="4">查無資料</td>
		</tr>
	<%	} %>
	</tbody>
	</table>
</div>
</form>
</body>
</html>

<script>

	(function() {
		
		$(".user").on("click", function() {
			var account = $(this).attr("account");
			$("#<%=Constant.ACTION_TYPE%>").val("edit");
			$("#account").val(account);
			document.form.submit();
		});
		
		$("#searchBtn").on("click", function() {
			$("#<%=Constant.ACTION_TYPE%>").val("search");
			document.form.submit();
		});
	})();

</script>