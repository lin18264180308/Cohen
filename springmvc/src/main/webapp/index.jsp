<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <form action="testModelAttribute" method="post">
        id:<input type="hidden" name="id" value="1" />
        username:<input type="text" name="username" value="zhangsan" /><br />
        province:<input type="text" name="address.province" value="山东" /><br />
        city:<input type="text" name="address.city" value="济南" /><br />
        <input type="submit" value="submit" />
    </form>
    <!-- <form action="testSessionAttribute" method="post">
        <input type="submit" value="submit" />
    </form> -->
    <!-- <form action="testModelMapParams" method="post">
        <input type="submit" value="submit" />
    </form> -->
    <br /><br />
    <!-- <form action="testModelAndViewParams" method="post">
        username:<input name="username" value="" /><br />
        password:<input name="password" value="" /><br />
        province:<input name="address.province" value="" /><br />
        city:<input name="address.city" value="" /><br />
        <input type="submit" value="submit" />
    </form> -->
    <!-- <form action="testPojoParams" method="post">
	    username:<input name="username" value="" /><br />
	    password:<input name="password" value="" /><br />
	    province:<input name="address.province" value="" /><br />
	    city:<input name="address.city" value="" /><br />
	    <input type="submit" value="submit" />
    </form> -->
    <!-- 
    <br /><br />
    <span>rest风格的crud</span>
    <form action="springmvc/testRest" method="post">
        <input type="submit" value="增" />
    </form>
    <form action="springmvc/testRest/1" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <input type="submit" value="删" />
    </form>
    <form action="springmvc/testRest/1" method="GET">
        <input type="submit" value="查" />
    </form>
    <form action="springmvc/testRest/1" method="post">
        <input type="hidden" name="_method" value="PUT">
        <input type="submit" value="改" />
    </form> -->
    <!-- <br /><br />
    <a href="testMethod1">testMethod1</a>
    <br /><br />
    <form action="testMethod2" method="post">
        username:<input name="username" value="" /><br />
        password:<input name="password" value="" /><br />
        <input type="submit" id="btn" value="Submit" />
    </form>
    <br />表单2<br />
    <form action="testMethod3" method="post">
        username:<input name="username" value="" /><br />
        password:<input name="password" value="" /><br />
        <input type="submit" id="btn" value="Submit" />
    </form> -->
</body>
</html>