<%@page import="ggd.core.util.JSONUtil"%>
<%@page import="com.fasterxml.jackson.databind.JsonNode"%>
<%@page import="ggd.auth.vo.AdmFunc"%>
<%@page import="java.util.Set"%>
<%@page import="ggd.auth.vo.AdmUser"%>
<%@page import="ggd.core.common.Constant"%>
<%@page import="ggd.core.common.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%!

	private static final String TREE_NODE_PARAM = "{id: '%1$s', pId: '%2$s', name: '%3$s', funcURL: '%4$s', isRoot: %5$s}";
	
	public String createTreeNodeJson(Set<AdmFunc> funcs) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int i = 0;
		for(AdmFunc func : funcs) {
			if(i != 0) 
				sb.append(",");
			if(func.isRoot()) {
				String p = String.format(TREE_NODE_PARAM, func.getFuncId(), "0", func.getFuncName(), "", func.isRoot());
				sb.append(p);
			}
			else {
				String s = String.format(TREE_NODE_PARAM, func.getFuncId(), func.getParent().getFuncId(), func.getFuncName(), func.getUrl(), func.isRoot());
				sb.append(s);
			}			
			i++;
		}
		sb.append("]");
		return sb.toString();
	}

%>

<%
	Config display = (Config) request.getAttribute(Constant.DISPLAY_CONFIG);
	Config common = (Config) request.getAttribute(Constant.COMMON_CONFIG);
	AdmUser user = (AdmUser) session.getAttribute(Constant.USER);
	Set<AdmFunc> funcs = user.getGroup().getFuncs();
	String tn = this.createTreeNodeJson(funcs);
	
	
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="首頁" name="title" />
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>" name="main" />
</jsp:include>
</head>

<body class="fixed-nav sticky-footer bg-dark" id="page-top">
  <!-- Navigation-->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <a class="navbar-brand" href="index.html"><%=display.getValue("PAGE_TITLE") %></a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <!-- 左方 menu 區 start -->
      <ul class="navbar-nav navbar-sidenav" id="menu">        
        
      </ul>
      <!-- 左方 menu 區 end -->

     
      <ul class="navbar-nav ml-auto">
        <li class="nav-item dropdown">
          <!-- <a class="nav-link dropdown-toggle mr-lg-2" id="messagesDropdown" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i class="fa fa-fw fa-envelope"></i>
            <span class="d-lg-none">Messages
              <span class="badge badge-pill badge-primary">12 New</span>
            </span>
            <span class="indicator text-primary d-none d-lg-block">
              <i class="fa fa-fw fa-circle"></i>
            </span>
          </a> -->
          <div class="dropdown-menu" aria-labelledby="messagesDropdown">
            <h6 class="dropdown-header">New Messages:</h6>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">
              <strong>David Miller</strong>
              <span class="small float-right text-muted">11:21 AM</span>
              <div class="dropdown-message small">Hey there! This new version of SB Admin is pretty awesome! These messages clip off when they reach the end of the box so they don't overflow over to the sides!</div>
            </a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">
              <strong>Jane Smith</strong>
              <span class="small float-right text-muted">11:21 AM</span>
              <div class="dropdown-message small">I was wondering if you could meet for an appointment at 3:00 instead of 4:00. Thanks!</div>
            </a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">
              <strong>John Doe</strong>
              <span class="small float-right text-muted">11:21 AM</span>
              <div class="dropdown-message small">I've sent the final files over to you for review. When you're able to sign off of them let me know and we can discuss distribution.</div>
            </a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item small" href="#">View all messages</a>
          </div>
        </li>
        <li class="nav-item dropdown">
          <!-- <a class="nav-link dropdown-toggle mr-lg-2" id="alertsDropdown" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i class="fa fa-fw fa-bell"></i>
            <span class="d-lg-none">Alerts
              <span class="badge badge-pill badge-warning">6 New</span>
            </span>
            <span class="indicator text-warning d-none d-lg-block">
              <i class="fa fa-fw fa-circle"></i>
            </span>
          </a> -->
          <div class="dropdown-menu" aria-labelledby="alertsDropdown">
            <h6 class="dropdown-header">New Alerts:</h6>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">
              <span class="text-success">
                <strong>
                  <i class="fa fa-long-arrow-up fa-fw"></i>Status Update</strong>
              </span>
              <span class="small float-right text-muted">11:21 AM</span>
              <div class="dropdown-message small">This is an automated server response message. All systems are online.</div>
            </a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">
              <span class="text-danger">
                <strong>
                  <i class="fa fa-long-arrow-down fa-fw"></i>Status Update</strong>
              </span>
              <span class="small float-right text-muted">11:21 AM</span>
              <div class="dropdown-message small">This is an automated server response message. All systems are online.</div>
            </a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">
              <span class="text-success">
                <strong>
                  <i class="fa fa-long-arrow-up fa-fw"></i>Status Update</strong>
              </span>
              <span class="small float-right text-muted">11:21 AM</span>
              <div class="dropdown-message small">This is an automated server response message. All systems are online.</div>
            </a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item small" href="#">View all alerts</a>
          </div>
        </li>
        <!-- <li class="nav-item">
          <form class="form-inline my-2 my-lg-0 mr-lg-2">
            <div class="input-group">
              <input class="form-control" type="text" placeholder="Search for...">
              <span class="input-group-append">
                <button class="btn btn-primary" type="button">
                  <i class="fa fa-search"></i>
                </button>
              </span>
            </div>
          </form>
        </li> -->
        <li class="nav-item">
          <a class="nav-link" data-toggle="modal" data-target="#exampleModal">
            <i class="fa fa-fw fa-sign-out"></i>登出</a>
        </li>
      </ul>
    </div>
  </nav>
  <div class="content-wrapper">
    <!-- 右方 content 區 start -->
    <div class="container-fluid">
		<iframe id="iCtn" frameBorder="0" style="width: 100%; height: 100%;"></iframe>
    </div>
	<!-- 右方 content 區 end -->
	


    <!-- /.container-fluid-->
    <!-- /.content-wrapper-->
    <footer class="sticky-footer">
      <div class="container">
        <div class="text-center">
          <small>Copyright © Your Website 2018</small>
        </div>
      </div>
    </footer>
    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fa fa-angle-up"></i>
    </a>
    <!-- Logout Modal-->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">警告</h5>
            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">×</span>
            </button>
          </div>
          <div class="modal-body">確定要登出嗎？</div>
          <div class="modal-footer">
            <button class="btn btn-secondary" type="button" data-dismiss="modal">取消</button>
            <a class="btn btn-primary" href="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ui/view/main/login">登出</a>
          </div>
        </div>
      </div>
    </div>
    
  </div>
</body>
<jsp:include page="/WEB-INF/views/include/script.jsp">	
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>" name="main" />
</jsp:include>
<script>

	var menuJson = <%=tn %>;
	var pMs = new Map();

	var gotoURL = function(url) {
		$("#iCtn").attr("src", "<%=common.getValue(Constant.MAIN_PATH_HOST)%>/ui/view/" + url);
	};

	var createMenuTree = function() {
		
		$.each(menuJson, function(i, mObj) {
			if(mObj.pId == 0) {
				var pM = $("<li class='nav-item' data-toggle='tooltip' data-placement='right'>").attr("title", mObj.name);
				var rootNode = $("<a class='nav-link nav-link-collapse collapsed' data-toggle='collapse' data-parent='menu'>").attr("href", "#"+mObj.id);
				rootNode.append("<i class='fa fa-fw fa-sitemap'></i><span class='nav-link-text'>"+mObj.name+"</span>");
				pM.append(rootNode);
				console.log(pM);
				pM.append("<ul class='sidenav-second-level collapse' id='"+mObj.id+"'>");
				pMs.set(mObj.id, pM);
			}
		});
		
		$.each(menuJson, function(i, mObj) {
			if(mObj.pId != 0) {
				var pm = pMs.get(mObj.pId);				
				var ul = pm.find("ul[id='"+mObj.pId+"']");				
				var li = $("<li>");
				var a = $("<a>").attr("href", "javascript: gotoURL('"+mObj.funcURL+"')").text(mObj.name);
				li.append(a);
				ul.append(li);
			}
		});
		
		pMs.forEach(function(v, i) {
			$("#menu").append(v);
		});		
	};
	
	$(document).ready(function() {
		menuJson.sort(function(o, d) {
			var oid = o.id.replace("FUN", "");
			var did = d.id.replace("FUN", "");
			return oid > did
		});
		createMenuTree();	
	});
	
	

</script>
</html>