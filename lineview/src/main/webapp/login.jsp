<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/style.css" rel='stylesheet' type='text/css' />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>登录</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/operator_style/js/jquery-3.2.1.js"></script>   
<script type="text/javascript">
	function check(){
		
	var name = $('#name').val();
	var password = $('#password').val();
		if(!name){
			$('#msg').html('请填写您的用户名');
	        return false;
	    }
	    if(!password){
	    	$('#msg').html('请填写您的密码');
	        return false;
	    } 
		
	}
</script>
</head>
<body>
	<div class="main">
		<div class="login-form">
			<h1>LineView</h1>
			<h2 align="center"><font color="red"><s:property value="#session.userName"/></font></h2>
			<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="msg"></span></font>
				
				<div class="head">
					<img src="images/user.png" alt=""/>
				</div>
				<form action="user-login.action" method="post">
					<input type="text" class="text" name="user.userName" placeholder="用户名" id="name" />
					<input type="password" name="user.userPassword" placeholder="密码" id="password" />
					<div class="submit">
						<input type="submit" onclick="return check()" value="登录" >
					</div>	
					<p><a href="#">忘记密码 ?</a></p>
				</form>
				<div class="copy-right" >
					<p>Copyright &copy; 2017.mrong</p> 
				</div>	
			</div>
	</div>			 	 		
</body>
</html>