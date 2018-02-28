<%@page import="ggd.core.common.Config"%>
<%@page import="tbox.dispatcher.main.LoginDispatcher"%>
<%@page import="ggd.core.common.Constant"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Config display = (Config) request.getAttribute(Constant.DISPLAY_CONFIG);
	Config common = (Config) request.getAttribute(Constant.COMMON_CONFIG);
	String msg = (String) request.getAttribute(LoginDispatcher.LOGIN_MSG);
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="登入" name="title"/>
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST) %>" name="main"/>
</jsp:include>
</head>
<body class="bg-dark">

	<div class="container">
    <div class="card card-login mx-auto mt-5">
      <div class="card-header">Login</div>
      <div class="card-body">
        <form method="post" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/login">
          <div class="form-group">
            <label for="account">登入帳號</label> 
            <input class="form-control" id="account" type="text" name="<%=LoginDispatcher.ACCOUNT %>" placeholder="輸入帳號">
          </div>
          <div class="form-group">
            <label for="password">密碼</label> 
            <input type="password" class="form-control" id="password" name="<%=LoginDispatcher.PASSWORD %>" placeholder="密碼">
          </div>                    
          <input type="submit" class="btn btn-primary btn-block" value="登入"/>
          
        </form>
      </div>
    </div>
  </div>
</body>
<jsp:include page="/WEB-INF/views/include/script.jsp">	
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>" name="main" />
</jsp:include>
<script>

	var msg = "<%=msg %>";
	
	$(document).ready(function() {
		if(!ggd.util.isEmpty(msg)) {
			ggd.message.showModal({
				msgContent: "帳號或密碼錯誤，或是尚未開通",
				msgTitle: "說明",
			});
		}
	});

</script>
</html>