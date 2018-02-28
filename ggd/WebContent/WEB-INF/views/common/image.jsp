
<%@page import="java.net.URLDecoder"%>
<%@page import="ggd.core.common.Config"%>
<%@page import="ggd.core.common.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Config common = (Config) request.getAttribute(Constant.COMMON_CONFIG);
	String b64 = (String) request.getAttribute(Constant.BASE64);
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
<img src="data:image/png;base64, <%=b64%>"/>
</body>

</html>

