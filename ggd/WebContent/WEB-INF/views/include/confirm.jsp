<%@page import="ggd.core.common.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="form-group">
	<div class="btn-group" data-toggle="buttons">
		<label id="isEnabled-label" class="btn"> 
			<input type="radio" name="isEnabled" id="isEnabled" />
		</label> <label id="isApproved-label" class="btn invisible"> 
			<input type="radio" name="isApproved" id="isApproved" />
		</label>
	</div>
</div>
<div class="form-group confirmArea">
	<input type="button" class="btn btn-default" id="confirm" value="儲存" />
	<input type="button" class="btn btn-default" id="cancel" value="取消"/>	
</div>

<script>

var isEnabled = ${param.isEnabled};
var isApproved = ${param.isApproved};
var isManager = ${param.isManager};

(function() {
	
	isEnabled == false ? $("#isEnabled-label").removeClass("btn-danger").addClass("btn-success").val(isEnabled).html("啟用") : $("#isEnabled-label").removeClass("btn-success").addClass("btn-danger").val(isEnabled).html("取消啟用");
	isApproved == false ? $("#isApproved-label").removeClass("btn-danger").addClass("btn-success").val(isEnabled).html("啟用") : $("#isApproved-label").removeClass("btn-success").addClass("btn-danger").val(isEnabled).html("取消啟用");
	
	if(isManager) {
		$("#isApproved-label").removeClass("invisible");
	}
	
	$("#isEnabled-label").on("click", function() {			
		if(!$(this).val()) {
			$("#isEnabled-label").removeClass("btn-success").addClass("btn-danger").val(true).html("取消啟用");
		}
		else {
			$("#isEnabled-label").removeClass("btn-danger").addClass("btn-success").val(false).html("啟用")
		}
	});
	
	$("#isApproved-label").on("click", function() {
		if(!$(this).val()) {
			$("#isApproved-label").removeClass("btn-success").addClass("btn-danger").val(true).html("取消開通");
		}
		else {
			$("#isApproved-label").removeClass("btn-danger").addClass("btn-success").val(false).html("開通")
		}
	});
	
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