<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="page_tool_file.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 表格导出操作样式 -->
<style type="text/css">
#exportA:VISITED {
	color: white;
	text-decoration: none;
}

#exportA:LINK {
	color: white;
	text-decoration: none;
}

#exportA:HOVER {
	color: #548B54;
	text-decoration: none;
}

#exportA:ACTIVE {
	color: #548B54;
	text-decoration: none;
}
</style>
<!-- /表格导出操作样式 -->
<!-- 表格内容操作按钮样式 -->
<style type="text/css">
.raise:hover,.raise:focus {
	box-shadow: 0 0.5em 0.5em -0.4em var(- -hover);
	-webkit-transform: translateY(-0.25em);
	transform: translateY(-0.25em);
}

#raise {
	color: var(- -color);
	-webkit-transition: 0.25s;
	transition: 0.25s;
}

#raise:hover {
	border-color: var(- -hover);
	color: #fff;
}

#raise {
	background: none;
	border: 2px solid;
	font: inherit;
	line-height: 1;
}
</style>
<!-- /表格内容操作按钮样式 -->
<!-- 双击单元的内容进行修改JS和样式、点击删除按钮删除操作JS -->
<style type="text/css">
.spanTd {
	color: #A1A1A1;
	font-family: "微软雅黑";
}

.inputText {
	font-size: 10px;
	width: 100px;
}
</style>
<script>
	/*
	 *此处是双击表格修改内容，然后取消点击保存 
	 */
	window.onload = function() {
		var Tb = document.getElementById("Tb");
		Tb.ondblclick = Handler;
	};
	function Handler(e) {
		e = e || event;
		var tag = e.srcElement || e.target;
		if (tag.tagName == "TABLE" || tag.tagName == "TR")
			return;
		if (tag.tagName != "TD")
			tag = tag.parentNode;
		tag.getElementsByTagName("span")[0].style.display = "block";
		var inp = tag.getElementsByTagName("input")[0];
		inp.onblur = function() {
			this.style.display = "none";
			var sp = this.parentNode.getElementsByTagName("span")[0];
			/*此处与数据库进行交互，更新Ajax进行动态改变*/
			 var url="";
			    if("outageClassification"==sp.id){
			    	url="a/remark-update.action?remark.id="+id+"&namevalue="+this.value+"&name=outageClassification";
			    }
			    if("firstClassStopClassification"==sp.id){
			    	url="a/remark-update.action?remark.id="+id+"&namevalue="+this.value+"&name=firstClassStopClassification";
			    }
				if("secondClassStopClassification"==sp.id){
					url="a/remark-update.action?remark.id="+id+"&namevalue="+this.value+"&name=secondClassStopClassification";
			    }
				if("faultDetails"==sp.id){
					url="a/remark-update.action?remark.id="+id+"&namevalue="+this.value+"&name=faultDetails";
				}
			    $.post(url,{"time":new Date()},function(){
			    	
			    });
			sp.innerHTML = this.value;
			sp.style.display = "";
		};
		inp.style.display = "";
		inp.focus();
	}
</script>
<!-- /双击单元的内容进行修改JS -->
<!-- 点击线上检查表导航 -->
<script type="text/javascript">
    
    function online_click(){
        var banzu = document.getElementById("show_banzu").innerHTML;
        var banci = document.getElementById("show_banci").innerHTML;
        var url="a/onlineCheck-getStatus.action";
        $.post(url,{"time":new Date()},function(data){
            swal({
                  title: '线上检查表',
                  width: '600',
                  html:
                        '<jsp:useBean id="time" class="java.util.Date">'+'</jsp:useBean>'+
                        '<h4>'+
                            '<span>当前时间：</span> '+
                            '<script type="text/javascript">'+  
                            'setInterval("document.getElementById("showtime_agin").innerHTML=new Date().toLocaleString()+"星期"+"日一二三四五六".charAt(new Date().getDay());",1000);'+
                            '</'+'script'+'>'+  
                            '<a id="showtime_agin">'+
                            '</a> &emsp; <span>当前该填写内容：</span>'+
                            '<a href="">红色单元格内容</a>'+
                        '</h4>'+
                        '<!-- 表格内容 -->'+
                        '<table style="font-size: 10px;margin: 1em;width: 1150px;">'+
                            '<tr>'+
                                '<th colspan="2">日期:&emsp;<font id="showtimeSlow"><fmt:formatDate value="${time}" type="both"/></font></th>'+
                                '<script type="text/javascript">'+  
                                'document.getElementById("showtimeSlow").innerHTML=new Date().toLocaleDateString()+"星期"+"日一二三四五六".charAt(new Date().getDay());'+
                                '</'+'script'+'>'+ 
                                '<th id="banci">'+banzu+'</th>'+
                                '<th id="banzu">'+banci+'</th>'+
                                '<th id="username">负责人:${session.userName }</th>'+
                            '</tr>'+
                            '<tr>'+
                                '<th rowspan="3" colspan="2">接班前10分钟</th>'+
                                '<td colspan="3">1、PEE是否按要求佩戴？<font style="float: right;">是：<input type="radio" name="pee" id="pee" value="0"/>否：<input type="radio" name="pee" id="pee" value="1"/></font></td>'+
                            '</tr>'+
                            '<tr>'+
                                '<td colspan="3">2、质量工具是否按放置在指定区域？<font style="float: right;">是：<input type="radio" name="qualityTools" id="qualityTools" value="0">否：<input type="radio" name="qualityTools" id="qualityTools" value="1"></font></td>'+
                            '</tr>'+
                            '<tr>'+
                                '<td colspan="3">3、地面及机台是否打扫干净？<font style="float: right;">是：<input type="radio" name="groundMachine" id="groundMachine" value="0">否：<input type="radio" name="groundMachine" id="groundMachine" value="1"></font></td>'+
                            '</tr>'+
                            
                            '<tr>'+
                                '<th rowspan="12">接班前10分钟</th>'+
                                '<th rowspan="4">：10</th>'+
                                '<th colspan="2">检查工作</th>'+
                                '<th>备注</th>'+
                            '</tr>'+
                            '<tr>'+
                                '<td colspan="2">1、检查洗瓶机抽氢风机是否正常工作？</td>'+
                                '<td><input type="text" id="hFan" width="100%"> </td>'+
                            '</tr>'+
                            '<tr>'+
                                '<td colspan="2">2、检查洗瓶机进出口光电开关、急停是否有效？</td>'+
                                '<td> <input type="text" id="photoelectricSwitch" width="100%"> </td>'+
                            '</tr>'+
                            '<tr>'+
                                '<td colspan="2">3、洗瓶机最后一道喷淋水品评？</td>'+
                                '<td> <input type="text" id="sprayPump" width="100%"> </td>'+
                            '</tr>'+
                            
                            '<tr>'+
                                '<th rowspan="4">：20</th>'+
                            '</tr>'+
                            '<tr>'+
                                '<td colspan="2">1#预喷淋压力：&emsp;<font style="color: red;">'+data[0]+'</font></td>'+
                                '<td colspan="1">2#预喷淋压力：&emsp;<font style="color: red;">'+data[1]+'</font></td>'+
                            '</tr>'+
                            '<tr>'+
                                '<td colspan="2">1#碱槽预喷淋压力：&emsp;<font style="color: red;">'+data[2]+'</font></td>'+
                                '<td colspan="1">2#碱槽预喷淋压力：&emsp;<font style="color: red;">'+data[3]+'</font></td>'+
                            '</tr>'+
                            '<tr>'+
                                '<td colspan="2">1#碱槽温度：&emsp;<font style="color: red;">'+data[4]+'</font></td>'+
                                '<td colspan="1">2#碱槽温度：&emsp;<font style="color: red;">'+data[5]+'</font></td>'+
                            '</tr>'+
                            
                            '<tr>'+
                                '<th rowspan="4">：30</th>'+
                            '</tr>'+
                            '<tr>'+
                                '<td colspan="2">洗瓶机喷淋软化水：&emsp;<font style="color: red;"><input type="text" id="bottleWasherWater" name="onlineCheck"></font></td>'+
                                '<td colspan="1">喷淋酿造水：&emsp;<font style="color: red;"><input type="text" id="sprayWater" name="onlineCheck"></font></td>'+
                            '</tr>'+
                            '<tr>'+
                                '<td colspan="2">杀菌机软化水：&emsp;<font style="color: red;"><input type="text" id="sterilizerWater" name="onlineCheck"></font></td>'+
                                '<td colspan="1">杀菌机蒸汽压力：&emsp;<font style="color: red;"><input type="text" id="sterilizerPressure" name="onlineCheck"></font></td>'+
                            '</tr>'+
                            '<tr>'+
                                '<td colspan="2">杀菌总空压：&emsp;<font style="color: red;"><input type="text" id="sterilizerAirPressure" name="onlineCheck"></font></td>'+
                                '<td colspan="1"></td>'+
                            '</tr>'+
                        '</table>'+
                        '<!-- /表格内容 -->'
                        ,
                  showCancelButton: true,
                  confirmButtonText: '上传',
                  cancelButtonText:'取消',
                  showLoaderOnConfirm: true,
                  preConfirm: function() {
                    return new Promise(function(resolve) {
                      setTimeout(function() {
                        resolve();
                      }, 2000);
                    });
                  },
                  allowOutsideClick: false
                }).then(function(isConfirm) {
                  if (isConfirm) {
                      //此处填写上述表单提交操作，如果Ajax返回成功提交，则进入下面的弹窗，否则显示添加失败
                      var operator=$("#username").html().split(":")[1];
                      var pee=$("input[name='pee']:checked").val();
                      var qualityTools=$("input[name='qualityTools']:checked").val();
                      var groundMachine=$("input[name='groundMachine']:checked").val();
                      var hFan=$("#hFan").val();
                      
                      var photoelectricSwitch=$("#photoelectricSwitch").val();
                      var sprayPump=$("#sprayPump").val();
                      var preSpray1=$("#preSpray1").val();
                      var preSpray2=$("#preSpray2").val();
                      var alkalipreSpray1=$("#alkalipreSpray1").val();
                      var alkalipreSpray2=$("#alkalipreSpray2").val();
                      var alkaliT1=$("#alkaliT1").val();
                      var alkaliT2=$("#alkaliT2").val();
                      var bottleWasherWater=$("#bottleWasherWater").val();
                      var sprayWater=$("#sprayWater").val();
                      var sterilizerWater=$("#sterilizerWater").val();
                      var sterilizerPressure=$("#sterilizerPressure").val();
                      var sterilizerAirPressure=$("#sterilizerAirPressure").val();
                      var flag="true";
                      if(null==pee){
                          flag="false";
                          swal({
                                type : 'warning',
                                title : '注意！',
                                html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                        +'请填写是否佩戴pee！',
                                showCancelButton : true,
                                showConfirmButton : false,
                                cancelButtonText : '关闭'
                            });
                      }
                      if(null==qualityTools){
                          flag="false";
                          swal({
                                type : 'warning',
                                title : '注意！',
                                html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                        +'请填写质量工具是否到位！',
                                showCancelButton : true,
                                showConfirmButton : false,
                                cancelButtonText : '关闭'
                            });
                      }
                      if(null==groundMachine){
                          flag="false";
                          swal({
                                type : 'warning',
                                title : '注意！',
                                html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                        +'请填写地面机台是否干净！',
                                showCancelButton : true,
                                showConfirmButton : false,
                                cancelButtonText : '关闭'
                            });
                      }
                      if(""==hFan){
                          flag="false";
                          swal({
                                type : 'warning',
                                title : '注意！',
                                html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                        +'请填写抽氢风机！',
                                showCancelButton : true,
                                showConfirmButton : false,
                                cancelButtonText : '关闭'
                            });
                      }
                      if(""==photoelectricSwitch){
                          flag="false";
                          swal({
                                type : 'warning',
                                title : '注意！',
                                html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                        +'请填写洗瓶机进出口光电开关急停是否有效！',
                                showCancelButton : true,
                                showConfirmButton : false,
                                cancelButtonText : '关闭'
                            });
                      }
                      if(""==sprayPump){
                          flag="false";
                          swal({
                                type : 'warning',
                                title : '注意！',
                                html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                        +'请填写洗瓶机最后一道喷淋水品评！',
                                showCancelButton : true,
                                showConfirmButton : false,
                                cancelButtonText : '关闭'
                            });
                      }
                      if(""==bottleWasherWater){
                          flag="false";
                          swal({
                                type : 'warning',
                                title : '注意！',
                                html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                        +'请填写洗瓶机喷淋软化水！',
                                showCancelButton : true,
                                showConfirmButton : false,
                                cancelButtonText : '关闭'
                            });
                      }
                      if(""==sprayWater){
                          flag="false";
                          swal({
                                type : 'warning',
                                title : '注意！',
                                html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                        +'请填写喷淋酿造水！',
                                showCancelButton : true,
                                showConfirmButton : false,
                                cancelButtonText : '关闭'
                            });
                      }
                      if(""==sterilizerWater){
                          flag="false";
                          swal({
                                type : 'warning',
                                title : '注意！',
                                html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                        +'请填写杀菌机软化水！',
                                showCancelButton : true,
                                showConfirmButton : false,
                                cancelButtonText : '关闭'
                            });
                      }
                      if(""==sterilizerPressure){
                          flag="false";
                          swal({
                                type : 'warning',
                                title : '注意！',
                                html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                        +'请填写杀菌机蒸汽压力！',
                                showCancelButton : true,
                                showConfirmButton : false,
                                cancelButtonText : '关闭'
                            });
                      }
                      if(""==sterilizerAirPressure){
                          flag="false";
                          swal({
                                type : 'warning',
                                title : '注意！',
                                html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                        +'请填写杀菌总空压！',
                                showCancelButton : true,
                                showConfirmButton : false,
                                cancelButtonText : '关闭'
                            });
                      }
                      if("true"==flag){
                          var url="a/onlineCheck-onlineCheck.action?onlineCheck.operator="+operator+"&onlineCheck.qualityTools="+qualityTools+"&onlineCheck.groundMachine="+
                          groundMachine+"&onlineCheck.pee="+pee+"&onlineCheck.photoelectricSwitch="+photoelectricSwitch+"&onlineCheck.sprayPump="+sprayPump+
                          "&onlineCheck.preSpray1="+preSpray1+"&onlineCheck.preSpray2="+preSpray2+"&onlineCheck.alkalipreSpray1="+alkalipreSpray1+
                          "&onlineCheck.alkalipreSpray2="+alkalipreSpray2+"&onlineCheck.alkaliT1="+alkaliT1+"&onlineCheck.alkaliT2="+alkaliT2+
                          "&onlineCheck.bottleWasherWater="+bottleWasherWater+"&onlineCheck.sprayWater="+sprayWater+"&onlineCheck.sterilizerWater="+sterilizerWater+
                          "&onlineCheck.sterilizerPressure="+sterilizerPressure+"&onlineCheck.sterilizerAirPressure="+sterilizerAirPressure+"&onlineCheck.fan1="+hFan;
                          $.post(url,{"time":new Date()},function(){
                              swal({
                                  type: 'success',
                                  title: '线上检查表上传完成!',
                                  html: '上传时间: ' + new Date().toLocaleString( ),
                                  showCancelButton: true,
                                  showConfirmButton:false,
                                  cancelButtonText:'关闭',
                                });  
                          }); 
                      }
                      
                  };
                }); 
        });
            
        
    };
</script>
<!-- //点击线上检查表导航 -->
</head>
<body>
	<!-- 左侧导航 -->
	<div class="navbar-default sidebar" role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">
				<li><a onclick="online_click();"
					class=" hvr-bounce-to-right"><i class="fa fa-indent nav_icon"></i>
						<span class="nav-label">线上检查表</span></a></li>
			</ul>
		</div>
		<div
			style="border: 1px solid #8FBC8F; width: 100%; float: right; color: #D2B48C;">
			当&nbsp;&nbsp;前&emsp;模&nbsp;&nbsp;式：<font style="color: #8B0000;font-family: 幼圆;"
				id="show_models"><% String modeName=(String)application.getAttribute("modeName");
				if(modeName!=null){%>
					${applicationScope.modeName}
				<%} else{%>
				模式未选择
				<%}%></font><br /> 当前生产品种：<br /> <font
				style="color: #8B0000; font-size: 8px;font-family: 幼圆; border-top: 1px solid red; width: 100%; float: right; text-align: center;"
				id="show_variety">
				<% String modeClass=(String)application.getAttribute("modeName");
				if(modeClass!=null){%>
					${applicationScope.modeClass}
				<%} else{%>
				当前品种未选择
				<%}%>
				</font>
		</div>
		&emsp;
		<div style="height: 400px; font-size: 10px; padding-left: 40px; color: #ABABAB;">
			<!-- START 三大指标 -->
			<div style="" >
				<div id="oee_chart" style="height: 135px;"></div>
			</div>
			<div style="" >
				<div id="gyl_chart" style="height: 135px;"></div>
			</div>
			<div style="" >
				<div id="lef_chart" style="height: 135px;"></div>
			</div>
			<span hidden id="oee">100</span><span hidden id="gyl">100</span><span hidden id="lef">100</span>
				<script src="<c:url value='/operator_style/js/echarts-all.js'/>"></script>
				<script type="text/javascript">
				    // 基于准备好的dom，初始化echarts图表
				    var myChart_oee = echarts.init(document.getElementById('oee_chart')); 
				    var myChart_gyl = echarts.init(document.getElementById('gyl_chart')); 
				    var myChart_lef = echarts.init(document.getElementById('lef_chart')); 
				    
				    var option = {
				        tooltip : {
				            formatter: "{a} <br/>{b} : {c}%"
				        },
				        series : [
				            {
				                name:'业务指标',
				                type:'gauge',				
								min:0,
								max:100,            
				                splitNumber: 5,       // 分割段数，默认为5
				                axisLine: {            // 坐标轴线
				                    lineStyle: {       // 属性lineStyle控制线条样式
				                        color: [[0.2, '#ff4500'],[0.8, '#48b'],[1, '#228b22']], 
				                        width: 5
				                    }
				                },
				                axisTick: {            // 坐标轴小标记
				                    splitNumber: 20,   // 每份split细分多少段
				                    length :12,        // 属性length控制线长
				                    lineStyle: {       // 属性lineStyle控制线条样式
				                        color: 'auto'
				                    }
				                },
				                axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
				                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
				                        color: 'auto',
				                    }
				                },
				                splitLine: {           // 分隔线
				                    show: true,        // 默认显示，属性show控制显示与否
				                    length :30,         // 属性length控制线长
				                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
				                        color: 'auto'
				                    }
				                },
				                pointer : {
				                    width : 5
				                },
				                title : {
				                    show : true,
				                    offsetCenter: [0, '-40%'],       // x, y，单位px
				                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
				                        fontWeight: 'bolder'
				                    }
				                },
				                detail : {
				                    formatter:'{value}%',
				                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
				                        color: 'auto',
				                        fontWeight: 'bolder'
				                    }
				                },
				                data:[{value: 50, name: '完成率'}]
				            }
				        ]
				    };
				
				    // 为echarts  -OEE对象加载数据 
				    myChart_oee.setOption(option);
				    option.series[0].data[0].value = Number($("#oee").html()).toFixed(1) ;
			        option.series[0].data[0].name = 'OEE';
			        myChart_oee.setOption(option,true);
				    setInterval(function (){
				        option.series[0].data[0].value = Number($("#oee").html()).toFixed(1) ;
				        option.series[0].data[0].name = 'OEE';
				        myChart_oee.setOption(option,true);
				    },3000);
				    // 为echarts  -GYL对象加载数据 
				    myChart_gyl.setOption(option);
				    option.series[0].data[0].value = Number($("#gyl").html()).toFixed(1) ;
			        option.series[0].data[0].name = 'GYL';
			        myChart_gyl.setOption(option,true);
				    setInterval(function (){
				        option.series[0].data[0].value = Number($("#gyl").html()).toFixed(1) ;
				        option.series[0].data[0].name = 'GYL';
				        myChart_gyl.setOption(option,true);
				    },3000);
				    // 为echarts  -LEF对象加载数据 
				    myChart_lef.setOption(option);
				    option.series[0].data[0].value = Number($("#lef").html()).toFixed(1) ;
			        option.series[0].data[0].name = 'LEF';
			        myChart_lef.setOption(option,true);
				    setInterval(function (){
				        option.series[0].data[0].value = Number($("#lef").html()).toFixed(1) ;
				        option.series[0].data[0].name = 'LEF';
				        myChart_lef.setOption(option,true);
				    },3000);
				</script>
			<!-- END 三大指标 -->
			<!-- //三大指标 -->
		</div>
		<button style="font-size: 10px; float: right;" id="clickme">柱状展示/整线一览</button>
		<br>
		<br>
		<!-- 备注总览和关注模式 -->
		<button style="font-size: 10px; float: right;" id="ClickMeBei">备&emsp;&emsp;注&ensp;总&emsp;&emsp;览</button>
		<!-- 备注总览弹窗覆盖 -->
		<div id="goodcoverBei"></div>
		<div id="codeBei">
			<div class="close1Bei">
				<a href="javascript:void(0)" id="closebtBei"><img
					src="<c:url value='/operator_style/images/close.gif'/>"></a>
			</div>
			<br>
			<br>
			<br>
			<div class="goodtxtBei">
				<center>
					<h1 style="color: #CD0000; font-family: 微软雅黑;">备注总览：</h1>
					<hr>
				</center>
				<table id="Tb">

					<tr id="first">
						<th style="width: 5%">班组</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>时长</th>
						<th>停机分类</th>
						<th>一级停机分类</th>
						<th>二级停机分类</th>
						<th>故障详细信息</th>
						<th>操作</th>
					</tr>






				</table>
				<!-- 表格导出操作 -->
				<div id="export">
					<button style="float: right; font-size: 12px;">
						<a id="exportA" data-type="xls" href="javascript:;">导出为EXCEL</a>
					</button>
					<button style="float: right; font-size: 12px;">
						<a id="exportA" data-type="doc" href="javascript:;">导出为WORD</a>
					</button>
				</div>
				<script src="<c:url value='/operator_style/js/Blob.js'/>"></script>
				<script src="<c:url value='/operator_style/js/FileSaver.js'/>"></script>
				<script src="<c:url value='/operator_style/js/tableExport.js'/>"></script>
				<script>
					var $exportLink = document.getElementById('export');
					$exportLink.addEventListener('click', function(e) {
						e.preventDefault();
						if (e.target.nodeName === "A") {
							tableExport('Tb', '备注明细', e.target
									.getAttribute('data-type'))
						}
					}, false);
				</script>
				<!-- /表格导出操作 -->
			</div>
		</div>
		<!-- /备注总览弹窗覆盖 -->
		<br /> <br />
	</div>
	</nav>
</body>
</html>