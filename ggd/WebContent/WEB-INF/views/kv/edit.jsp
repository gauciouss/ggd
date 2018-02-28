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
	AdmUser user = (AdmUser) request.getAttribute(Constant.DATA_LIST);
	AdmUser loginUser = (AdmUser) session.getAttribute(Constant.USER);
	List<AdmGroup> groups = (List<AdmGroup>) request.getAttribute(UserDispatcher.ALL_APPROVED_GROUPS);	
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
			<div class="card-header">編輯使用者</div>
			<div class="card-body">
				<form name="form" method="post"
					action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/auth/user">
					<input type="hidden" name="<%=Constant.ACTION_TYPE%>"
						id="<%=Constant.ACTION_TYPE%>" value="confirm" />
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="account">帳號</label> <input type="text" id="account" class="form-control"
									name="account"
									value="<%=Util.isEmpty(user.getAccount()) ? "" : user.getAccount()%>" />
							</div>
							<div class="col-md-6">
								<label for="password">密碼</label> <input type="password"
									class="form-control" id="password" name="password"
									value="<%=Util.isEmpty(user.getPwd()) ? "" : user.getPwd()%>" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="name">姓名</label> <input type="text"
									class="form-control" id="name" name="name"
									value="<%=Util.isEmpty(user.getName()) ? "" : user.getName()%>" />
							</div>
							<div class="col-md-6">
								<label for="email">email</label> <input type="email"
									class="form-control" id="email" name="email"
									value="<%=Util.isEmpty(user.getEmail()) ? "" : user.getEmail()%>" />
							</div>

						</div>
					</div>

					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="address">地址</label> <input type="text"
									class="form-control" id="address" name="address"
									value="<%=Util.isEmpty(user.getAddress()) ? "" : user.getAddress()%>" />
							</div>
							<div class="col-md-6">
								<label for="tel">電話</label> <input type="tel"
									class="form-control" id="tel" name="tel"
									value="<%=Util.isEmpty(user.getTel()) ? "" : user.getTel()%>" />
							</div>
						</div>
					</div>




					<div class="form-group">
						<div class="form-row">
							<div class="col-md-6">
								<label for="phone">手機</label> <input type="tel"
									class="form-control" id="phone" name="phone"
									value="<%=Util.isEmpty(user.getPhone()) ? "" : user.getPhone()%>" />
							</div>
							<div class="col-md-6">
								<label for="group">群組</label> <select id="group" name="group"
									class="form-control">
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

					<jsp:include page="/WEB-INF/views/include/confirm.jsp">
						<jsp:param value="<%=user.isEnabled()%>" name="isEnabled" />
						<jsp:param value="<%=user.isApproved()%>" name="isApproved" />
						<jsp:param value="true" name="showPanel"/>
						<jsp:param value="<%=loginUser.getGroup().isManager()%>" name="isManager" />
					</jsp:include>
				</form>
			</div>
		</div>
	</div>
</body>


<script>
	
	var group = "<%=user.getGroup() == null ? "" : user.getGroup().getGroupId()%>";
	$("#group").val(group);
</script>

</html>