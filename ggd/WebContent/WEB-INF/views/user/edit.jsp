<%@page import="ggd.dispatcher.auth.UserDispatcher"%>
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
	AdmUser user = (AdmUser) request.getAttribute(Constant.DATA_LIST);
	AdmUser loginUser = (AdmUser) session.getAttribute(Constant.USER);
	List<AdmGroup> groups = (List<AdmGroup>) request.getAttribute(UserDispatcher.ALL_APPROVED_GROUPS);
	System.out.println(loginUser.getGroup());	
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/script.jsp">
	<jsp:param value="使用者管理-修改" name="title" />
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>"
		name="main" />
</jsp:include>
</head>
<body>
	<form name="form" method="post" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/auth/user">
		<input type="hidden" name="<%=Constant.ACTION_TYPE %>" id="<%=Constant.ACTION_TYPE %>" value="confirm"/>
		<div class="form-group">
			<label for="account">帳號</label> 
			<input type="hidden" name="account" value="<%=user.getAccount() %>"/>
		</div>
		<div class="form-group">
			<label for="password">密碼</label> 
			<input type="password" class="form-control" id="password" name="password" value="<%=user.getPwd() %>"/>				
		</div>
		<div class="form-group">
			<label for="name">姓名</label> 
			<input type="text" class="form-control" id="name" name="name" value="<%=user.getName() %>"/>				
		</div>
		<div class="form-group">
			<label for="email">email</label> 
			<input type="email" class="form-control" id="email" name="email" value="<%=user.getEmail() %>"/>				
		</div>
		<div class="form-group">
			<label for="address">地址</label> 
			<input type="text" class="form-control" id="address" name="address" value="<%=user.getAddress() %>"/>				
		</div>
		<div class="form-group">
			<label for="tel">電話</label> 
			<input type="tel" class="form-control" id="tel" name="tel" value="<%=user.getTel() %>"/>				
		</div>
		<div class="form-group">
			<label for="phone">手機</label> 
			<input type="tel" class="form-control" id="phone" name="phone" value="<%=user.getPhone() %>"/>				
		</div>
		<div class="form-group">
			<label for="group">群組</label> 
			<select id="group" name="group" class="form-control">
				<%for(AdmGroup group : groups) { %>
				<option value="<%=group.getGroupId() %>"><%=group.getGroupName() %></option>
				<%}%>
			</select>				
		</div>
		<jsp:include page="/WEB-INF/views/include/confirm.jsp">
			<jsp:param value="<%=user.isEnabled() %>" name="isEnabled"/>
			<jsp:param value="<%=user.isApproved() %>" name="isApproved"/>
			<jsp:param value="<%=loginUser.getGroup().isManager() %>" name="isManager"/>
		</jsp:include>
	</form>
</body>

<script>

	<%
		String groupId = user.getGroup() == null ? "" : user.getGroup().getGroupId();
	%>
	var group = "<%=groupId %>";
	$("#group").val(group);

</script>

</html>