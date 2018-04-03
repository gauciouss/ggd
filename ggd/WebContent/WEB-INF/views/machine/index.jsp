<%@page import="tbox.dispatcher.main.MachineDispatcher"%>
<%@page import="tbox.data.vo.MachineEntity"%>
<%@page import="tbox.data.vo.AppClz"%>
<%@page import="ggd.core.util.StandardUtil"%>
<%@page import="tbox.data.vo.AppEntity"%>
<%@page import="tbox.data.vo.KVEntity"%>
<%@page import="baytony.util.Util"%>
<%@page import="ggd.auth.vo.AdmUser"%>
<%@page import="java.util.List"%>
<%@page import="ggd.core.common.Constant"%>
<%@page import="ggd.core.common.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Config display = (Config) request.getAttribute(Constant.DISPLAY_CONFIG);
	Config common = (Config) request.getAttribute(Constant.COMMON_CONFIG);
	List<MachineEntity> machines = (List<MachineEntity>) request.getAttribute(Constant.DATA_LIST);
	String actionResult = (String) request.getAttribute(Constant.ACTION_RESULT);
	Integer importCount = (Integer) request.getAttribute(MachineDispatcher.IMPORT_COUNT);
%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/header.jsp">
	<jsp:param value="機上盒管理管理" name="title" />
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>" name="main" />
</jsp:include>
</head> 
<body>
<form method="post" name="form" action="<%=common.getValue(Constant.MAIN_PATH_HOST)%>ui/view/main/machine">
<input type="hidden" name="<%=Constant.ACTION_TYPE %>" id="<%=Constant.ACTION_TYPE %>"/>
<input type="hidden" name="serialNo" id="serialNo"/>
<div>	
	<div class="card-header">
		<i class="fa fa-table"></i>機上盒管理管理
		<input type="file" name="machineCSV" id="machineCSV"/>
		<a href="#" class="btn btn-info" id="importBtn">匯入</a>
		<a href="#" class="btn btn-info" id="saveBtn">新增</a>
		<a href="<%=common.getValue(Constant.MAIN_PATH_HOST)%>/doc/machine_data.csv" class="btn btn-info" id="downloadSample" download="machine_data.csv">下載範本</a>
		<input type="hidden" name="csvData" id="csvData"/>
	</div>
	<div class="card-body">		
		<div class="table-responsive">
			<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
				<thead>
					<tr>
						<th>序號</th>						
						<th>網卡序號</th>
						<th>WIFI網卡序號</th>
						<th>所屬公司</th>
						<th>地區</th>
						<th>是否啟用</th>
						<th>啟用日期</th>	
						<th>授權時間</th>			
					</tr>
				</thead>
				<tbody>	
				<%
					if(!Util.isEmpty(machines)) {	
						for(MachineEntity machine : machines) {
				%>
					<tr class="data" serialNo="<%=machine.getSerialNo() %>">
						<td><%=machine.getMachineSN() %></td>
						<td><%=machine.getMac() %></td>
						<td><%=machine.getWifiMac() %></td>
						<td><%=machine.getCompanyName() %></td>
						<td><%=machine.getAreaName() %></td>
						<td><%=machine.isEnabled() == null || machine.isEnabled() == false ? "未啟用" : "啟用" %></td>
						<td><%=machine.getStartDate() %></td>
						<td><%=machine.getAuthStartDate() == null ? "" : machine.getAuthStartDate() %> ~ <%=machine.getAuthEndDate() == null ? "" : machine.getAuthEndDate() %></td>
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
	</div>
</div>
</form>
</body>
<jsp:include page="/WEB-INF/views/include/script.jsp">	
	<jsp:param value="<%=common.getValue(Constant.MAIN_PATH_HOST)%>" name="main" />
</jsp:include>
<script>

		var importCount = <%=importCount %>;

		var csvToArray = function(strData, strDelimiter) {
			strDelimiter = (strDelimiter || ",");

	        // Create a regular expression to parse the CSV values.
	        var objPattern = new RegExp(
	            (
	                // Delimiters.
	                "(\\" + strDelimiter + "|\\r?\\n|\\r|^)" +

	                // Quoted fields.
	                "(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|" +

	                // Standard fields.
	                "([^\"\\" + strDelimiter + "\\r\\n]*))"
	            ),
	            "gi"
	            );


	        // Create an array to hold our data. Give the array
	        // a default empty first row.
	        var arrData = [[]];

	        // Create an array to hold our individual pattern
	        // matching groups.
	        var arrMatches = null;


	        // Keep looping over the regular expression matches
	        // until we can no longer find a match.
	        while (arrMatches = objPattern.exec( strData )){

	            // Get the delimiter that was found.
	            var strMatchedDelimiter = arrMatches[ 1 ];

	            // Check to see if the given delimiter has a length
	            // (is not the start of string) and if it matches
	            // field delimiter. If id does not, then we know
	            // that this delimiter is a row delimiter.
	            if (
	                strMatchedDelimiter.length &&
	                strMatchedDelimiter !== strDelimiter
	                ){

	                // Since we have reached a new row of data,
	                // add an empty row to our data array.
	                arrData.push( [] );

	            }

	            var strMatchedValue;

	            // Now that we have our delimiter out of the way,
	            // let's check to see which kind of value we
	            // captured (quoted or unquoted).
	            if (arrMatches[ 2 ]){

	                // We found a quoted value. When we capture
	                // this value, unescape any double quotes.
	                strMatchedValue = arrMatches[ 2 ].replace(
	                    new RegExp( "\"\"", "g" ),
	                    "\""
	                    );

	            } else {

	                // We found a non-quoted value.
	                strMatchedValue = arrMatches[ 3 ];

	            }


	            // Now that we have our value string, let's add
	            // it to the data array.
	            arrData[ arrData.length - 1 ].push( strMatchedValue );
	        }

	        // Return the parsed data.
	        return( arrData );	    
		};

		(function() {
			
			$(".data").on("click", function() {
				var serialNo = $(this).attr("serialNo");
				$("#<%=Constant.ACTION_TYPE%>").val("edit");
				$("#serialNo").val(serialNo);
				document.form.submit();
			});
			
			$("#saveBtn").on("click", function() {
				$("#<%=Constant.ACTION_TYPE%>").val("save");
				document.form.submit();
			});
			
			
			$("#machineCSV").on("change", function() {
				console.log("do upload csv");
				var file = this.files[0];
				var reader = new FileReader();
				reader.onload = function() {
					var result = this.result;
					var array = csvToArray(result, ",");
					array.splice(0, 1);
					var jArray = [];
					$.each(array, function(i, v) {
						if(ggd.util.isEmpty(v[0]) && ggd.util.isEmpty(v[1]) && ggd.util.isEmpty(v[2])) {
							return;
						}						
						var json = {
							machineSN: v[0],
							mac: v[1],
							wifiMac: v[2],
							area: v[3],
							EIN: v[4],
							authStart: v[5],
							authEnd: v[6]
						};						
						jArray.push(json);						
					});
					$("#csvData").val(JSON.stringify(jArray));
				};
				reader.readAsText(file);
			});
			
			
			$("#importBtn").on("click", function() {
				if(ggd.util.isEmpty($("#csvData").val())) {
					alert("請選擇需上傳的csv檔");
					return false;
				}
				$("#<%=Constant.ACTION_TYPE%>").val("upload");
				document.form.submit();
			});

			$("#dataTable").DataTable();
			
			var ar = "<%=actionResult%>";			
			if(ar == "1") {
				var msg = "執行成功";
				if(importCount > 0) {
					msg += "，共匯入" + importCount + "筆";
				}
				alert(msg);
			}
			else if(ar == "0")
				alert("執行失敗");
			
		})();
	
</script>
</html>

