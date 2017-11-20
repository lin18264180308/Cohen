<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="page_main_tool.jsp"%>
<%@ include file="page_tool_file.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- BootStarp时间选项卡 -->
<link rel="stylesheet" type="text/css" href="<c:url value='/operator_style/css/bootstrap.css'/>">
<script src="<c:url value='/operator_style/js/bootstrap.min.js'/>"></script>
<!-- /BootStarp时间选项卡 -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bootstarp时间选项页</title>
<style type="text/css">
	.demo { padding: 2em 0; background: #fff; }
	a:hover, a:focus { outline: none; text-decoration: none; }
	.tab .nav-tabs { border-bottom: none; position: relative; }
	.tab .nav-tabs li { margin-right: 60px; z-index: 1; }
	.tab .nav-tabs li:after { content: ""; width: 100%; border: 1px solid #ccc6c6; position: absolute; top: 50%; right: -60%; z-index: -1; }
	.tab .nav-tabs li:last-child:after { border: none; }
	.tab .nav-tabs li a { display: block; padding: 15px 20px; background: #fff; font-size: 15px; font-weight: 600; color: #956cae; text-transform: uppercase; border-radius: 0; margin-right: 0; border: 2px solid #956cae; position: relative; overflow: hidden; z-index: 1; transition: all 0.3s ease 0s; }
	.tab .nav-tabs li.active a, .tab .nav-tabs li a:hover { color: #fff; border: 2px solid #956cae; }
	.tab .nav-tabs li a:after { content: ""; display: block; width: 100%; height: 0; position: absolute; top: 0; left: 0; z-index: -1; transition: all 0.3s ease 0s; }
	.tab .nav-tabs li.active a:after, .tab .nav-tabs li a:hover:after { height: 100%; background: #956cae; }
	.tab .tab-content { padding: 20px 10px; margin-top: 0; font-size: 14px; color: #999; line-height: 26px; }
	.tab .tab-content h3 { font-size: 24px; margin-top: 0; }
	
	@media only screen and (max-width: 767px) {
	.tab .nav-tabs li { margin: 0 25px 0 0; }
	}
	
	@media only screen and (max-width: 479px) {
	.tab .nav-tabs li { width: 100%; text-align: center; margin: 0 0 10px 0; }
	.tab .nav-tabs li:after { width: 0; height: 100%; top: auto; bottom: -60%; right: 50%; }
	}
</style>
</head>
<body>
	<div class="demo">
	  <div class="container">
	    <div class="row">
	      <div class="col-md-offset-3 col-md-6">
	        <div class="tab" role="tabpanel"> 
	          <!-- Nav tabs -->
	          <ul class="nav nav-tabs"  role="tablist">
	            <li role="presentation" style="margin: 5px;float: left;" class="active"><a href="#Section1" style="width: 100px;height: 20px;font-size: 10px;padding: 2px;" aria-controls="home" role="tab" data-toggle="tab">静态的</a></li>
	            <li role="presentation" style="margin: 5px;float: left;"><a href="#Section2" style="width: 100px;height: 20px;font-size: 10px;padding: 2px;" aria-controls="profile" role="tab" data-toggle="tab">实时的</a></li>
	            <li role="presentation" style="margin: 5px;float: left;"><a href="#Section3" style="width: 100px;height: 20px;font-size: 10px;padding: 2px;" aria-controls="messages" role="tab" data-toggle="tab">换班至今</a></li>
	          </ul>
	          <!-- Tab panes -->
	          <div class="tab-content tabs">
	            <div role="tabpanel" class="tab-pane fade in active" id="Section1">
	           <!-- 拖动条 -->
					<div class="range-holder">
						<div id="pr-slider" class="dragdealer">
							<div class="stripe">
								<div id="green-highlight"></div>
								<div id="orange-highlight"></div>
								<div id="blue-highlight"></div>
							
								<div class="handle">
									<div class="infobox">
										<div class="innerbox">
											<div id="time_model" hidden="hidden">0</div>
											<div id="time_price" hidden="hidden"></div>
											<div class="info-price" id="info_price"></div>
										</div>
									</div>	
									<div class="square">
										<span class="value"></span>
										<span class="menu-line"></span>
										<span class="menu-line"></span>
										<span class="menu-line"></span>
									</div>	
								</div>
							</div>
						</div>
					</div>
	            </div>
	            <div role="tabpanel" class="tab-pane fade" id="Section2" >
	            	<font style="font-size: 10px;color: #8B0000;">请选择时间间隔：</font>
	            			<p style="font-size: 10px;color: #8B0000;">
	            			15分钟：<input type="radio" name="chooseTime"  checked="checked" value="15">&emsp;
	            			1小时：<input type="radio" name="chooseTime"  value="60">&emsp;
	            			2&ensp;小时：<input type="radio" name="chooseTime"  value="120">
	            			</p>
	            			<p style="font-size: 10px;color: #8B0000;">
	            			4&ensp;小时：<input type="radio" name="chooseTime"  value="240">&emsp;
	            			8小时：<input type="radio" name="chooseTime"  value="480">&emsp;
	            			12小时：<input type="radio" name="chooseTime"  value="720">
	            			</p>
	            </div>
	            <div role="tabpanel" class="tab-pane fade" id="Section3">
					<font style="font-size: 10px;color: #8B0000;">当前班次开始时间：<span id="banci_starttime">2017/10/18 09:00:00</span></font><br>
					<font style="font-size: 10px;color: #8B0000;">当&emsp;&nbsp;前&nbsp;&emsp;&nbsp;班&nbsp;&emsp;次：早班</font>
	            </div>
	          </div>
	        </div>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>