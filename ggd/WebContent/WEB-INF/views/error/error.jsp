<%@page import="ggd.core.common.Config"%>
<%@page import="ggd.core.common.Constant"%>
<%@page import="ggd.auth.vo.AdmUser"%>
<%
	Object user = session.getAttribute(Constant.USER);
	Config common = (Config) request.getAttribute(Constant.COMMON_CONFIG);
%>

<script>

	<% if(user == null) { %>
	
		window.parent.location.href = "<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ui/view/main/login";	
	
	<% }%>

</script>
