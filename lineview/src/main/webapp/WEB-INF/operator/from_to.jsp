<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="<c:url value='/operator_style/css/styles_timeChose.css'/>" media="all">
<script src="<c:url value='/operator_style/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/operator_style/js/moment.js'/>"></script>
<script type="text/javascript" src="<c:url value='/operator_style/js/bootstrap-datetimepicker.js'/>"></script>
<script type="text/javascript">
$(document).ready(function(){
    if($(".iDate.full").length>0){
        $(".iDate.full").datetimepicker({
            locale: "zh-cn",
            format: "YYYY-MM-DD-hh-mm-ss",
            dayViewHeaderFormat: "YYYY年 MMMM"
        });
    }
    if($(".iDate.date").length>0){
        $(".iDate.date").datetimepicker({
            locale:"zh-cn",
            format:"YYYY-MM-DD",
            dayViewHeaderFormat:"YYYY年 MMMM"
        });
    }
})
</script>

</head>
<body>
	<!-- 从此处获取时间参数来传给后台 -->
	<br><br>
	<font style="font-size: 20px;font-family:华文琥珀;color: #000080;">点击选择时间：</font><br><br>
	<div id="time_model" hidden="hidden">1</div>
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
	<font style="font-size: 20px;color: #8B0000;">开始时间：</font>
	<div class="iDate full">
		<input type="text" id="dingshi_startTime">
		 <button type="button" class="addOn"></button>
	</div><br><br>
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
	<font style="font-size: 20px;color: #8B0000;">结束时间：</font>
	<div class="iDate full">
		<input type="text" id="dingshi_endTime">
		 <button type="button" class="addOn"></button>
	</div>
</body>
</html>