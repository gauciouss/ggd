<%@page import="ggd.core.common.Config"%>
<%@page import="ggd.core.common.Constant"%>
<%@page import="ggd.auth.vo.AdmUser"%>
<%
	Object user = session.getAttribute(Constant.USER);
%>

<script>

	<% if(user == null) { %>
	
		window.parent.location.href = "/ggd/ui/view/main/login";	
	
	<% }%>

</script>
