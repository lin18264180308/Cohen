<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="jqgrid/js/jquery-3.0.0.min.js"></script>
<script type="text/javascript">
    $(function(){
        $("#btn").click(function(){
            var begin = "2017-11-01-14-08-00";
            var end = "2017-11-15-14-38-00";
            $.post("admin/performanceCurve", {"time" : new Date(),"begin" : begin,"end" : end}, function(data) {
                alert(data.performanceCurveOfDate.length);
            });
        });
        
    })
</script>
</head>
<body>
    <input type="button" id="btn" value="button1" />
</body>
</html>