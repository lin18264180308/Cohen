<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h3>Success Page!</h3>
    <span>request user:${requestScope.user}</span><br />
    <span>session user:${sessionScope.user}</span><br />
    <span>request string:${requestScope.string}</span><br />
    <span>session string:${sessionScope.string}</span><br />
</body>
</html>