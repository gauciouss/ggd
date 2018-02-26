<%@page import="ggd.core.common.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="form-group">	
	<label for="isEnabled">啟用 / 取消啟用</label> 
	<select class="form-control" name="isEnabled" id="isEnabled">
		<option value=0>取消啟用</option>
		<option value=1>啟用</option>
	</select>		
</div>

<div class="form-group">	
 	<label for="isApproved"> 開通 / 取消開通</label>
	<select class="form-control" name="isApproved" id="isApproved">
		<option value=0>取消開通</option>
		<option value=1>開通</option>
	</select>	
</div>





	
<div class="form-group confirmArea">
	<input type="button" class="btn btn-default" id="confirm" value="儲存" />
	<input type="button" class="btn btn-default" id="cancel" value="取消"/>	
</div>

<script>

var isEnabled = ${param.isEnabled} == true ? 1 : 0;
var isApproved = ${param.isApproved} == true ? 1 : 0;
var isManager = ${param.isManager} == true ? 1 : 0;

(function() {
	
	if(!isManager) {
		$("#isApproved").hide();
	}
	$("#isEnabled").val(isEnabled).trigger("change");
	$("#isApproved").val(isApproved).trigger("change");
	
	$("#confirm").on("click", function() {
		$("#<%=Constant.ACTION_TYPE %>").val("confirm");
		document.form.submit();
	});
	
	$("#cancel").on("click", function() {
		$("#<%=Constant.ACTION_TYPE %>").val("index");
		document.form.submit();
	});
	
})();

</script>