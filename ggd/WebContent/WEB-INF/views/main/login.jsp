<%@page import="ggd.core.common.Config"%>
<%@page import="ggd.dispatcher.main.LoginDispatcher"%>
<%@page import="ggd.core.common.Constant"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Config display = (Config) request.getAttribute(Constant.DISPLAY_CONFIG);
	Config common = (Config) request.getAttribute(Constant.COMMON_CONFIG);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title><%=display.getValue("PAGE_TITLE")%> - 登入</title>
<link rel="stylesheet" type="text/css"
	href="<%=common.getValue("MAIN_PATH")%>ggd-js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>
	
	<form method="post" action="<%=common.getValue("MAIN_PATH")%>ui/view/main/login">
		<div class="container">
			<div class="panel panel-default">
				<div class="form-group">
					<label for="account">登入帳號</label> 
						<input type="text" class="form-control" id="account" name="<%=LoginDispatcher.ACCOUNT %>" placeholder="登入帳號">
				</div>
				<div class="form-group">
					<label for="password">密碼</label> <input type="password" class="form-control" id="password" name="<%=LoginDispatcher.PASSWORD %>" placeholder="密碼">
				</div>		
				<button type="submit" class="btn btn-primary">登入</button>
			</div>
		</div>
	</form>
	
</body>
</html>