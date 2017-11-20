<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="page_main_tool.jsp"%>
<%@ include file="page_tool_file.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Lineview</title>
<!--graph-->
    <link rel="stylesheet" href="<c:url value='/operator_style/css/graph.css'/>">
<!--//graph-->
<!-- 按钮弹窗 -->
<script type="text/javascript" src="<c:url value='/operator_style/js/jumpPage.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/operator_style/css/mainJump.css'/>" />
<!-- /按钮弹窗 -->
<!-- 滚动字幕 -->
<link rel="stylesheet" href="<c:url value='/operator_style/css/index.css'/>" />
<!-- //滚动字幕 -->
<!-- 图片热区提示消息样式 -->
<style type="text/css">
#Con {text-align:center}
.mapDiv { 
    width:210px; 
    height:100px; 
    padding: 5px; 
    background-color:#CDC8B1;
    color:#369;  
    position:absolute; 
    display: none; 
    word-break:break-all;
    border: 2px solid #CDC8B1; 
    }
</style>
<!-- 机台弹窗样式 -->
<style>
    .pop {  display: none;  width: 1200px; min-height: 470px;  max-height: 750px;  height:120%;  position: absolute;  margin-top: 0.5%;  left: -20%;  bottom: 0;  right: 0;  margin: auto;  padding: 25px;  z-index: 130;  border-radius: 8px;  background-color: #fff;  box-shadow: 0 3px 18px rgba(0, 0, 0, 0.5);  }
    .pop-top{  height:40px;  width:100%;  border-bottom: 1px #E5E5E5 solid;  }
    .pop-top h2{  float: left;  display:black}
    .pop-top span{  float: right;  cursor: pointer;  font-weight: bold; display:black}
    .pop-foot{  height:50px;  line-height:50px;  width:100%;  border-top: 1px #E5E5E5 solid;  text-align: right;  }
    .pop-cancel, .pop-ok {  padding:8px 15px;  margin:15px 5px;  border: none;  border-radius: 5px;  background-color: #337AB7;  color: #fff;  cursor:pointer;  }
    .pop-cancel {  background-color: #FFF;  border:1px #CECECE solid;  color: #000;  }
    .pop-content{  height: 380px;  }
    .pop-content-left{  float: left;  }
    .pop-content-right{  width:310px;  float: left;  padding-top:20px;  padding-left:20px;  font-size: 16px;  line-height:35px;  }
    .bgPop{  display: none;  position: absolute;  z-index: 129;  left: -800px;  top: -300px;  width: 2000px;  height:172%;  background: rgba(0,0,0,.2);  }
</style>
</head>
<body>
    <div id="wrapper">
        <nav class="navbar-default navbar-static-top" role="navigation">
            <!-- 头导航栏 -->
            <jsp:include page="page_header.jsp" />
            <!-- 左侧导航栏 -->
            <jsp:include page="page_left.jsp" />
        </nav>
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="content-main">
                <!--banner-->
                <div class="banner" >
                    <h2>
                        <span style="float: left;">查询类型:</span> <a href="javascript:void(0)" id="show_time_type" style="float: left;">实时</a><span style="float: left;">当前分析基于:</span>
                        <a href="javascript:void(0)" style="float: left;"><font id="start_time" style="float: left;">时间间隔：30分钟~</font><font id="end_time" style="float: left;"></font><font id="end_time_dingshi" style="display: none;float: left;"></font></a>
                            <script type="text/javascript"> 
                                        //动态显示时间 
                                   setInterval("document.getElementById('end_time').innerHTML=new Date().toLocaleString();",1000);  
                                   setInterval("document.getElementById('end_time_aloneMachine').innerHTML=new Date().toLocaleString();",1000);  
                              </script>
                        <span style="float: left;">瓶产量:</span><a style="float: left;">未采集</a><span style="float: left;">瓶剔除:</span> <a style="float: left;">未采集</a>
                        <span style="float: left;">瓶损失:</span> <a style="float: left;">未采集</a> <span style="float: left;">损失时间:</span> <a style="float: left;">数据不足</a>;
                    </h2>
                </div>
                <!--//banner-->
                <!--content-->
                <div class="content-top" >
                    <div class="col-md-4 " style="width: 1px;"></div>
                    <div class="col-md-8 content-top-2" style="height: 570px;width: 98%;margin-left: 1.6%;">

                        <!---中心页面动态图表实现---->
                        <!----->
                        <div class="content-graph"  style="height: 100%;position:relative;width: 100%;;">
                            <script src="<c:url value='/operator_style/js/jquery.flot.js'/>"></script>
                            <!-- 整线一览div -->
                            <Div id="Con" class="ConDiv">
                            <img alt="ceshi" src="<c:url value='/operator_style/images/ZONGXIAN.png'/>" usemap="#imgMap" style="border: 2px solid white;float: left;padding: 20px;z-index:0" width="100%" height="100%">
                            <!-- 图片区域点击Map -->
                            <map name="imgMap" id="imgMap" style="outline-color: red;">
                            <!-- 四大机台 -->
                                <!-- 洗瓶机 -->
                                <area shape="rect" coords="250,186,348,264"  onclick="show_pop('洗瓶机：',true);" id="XPJ" alt="洗瓶机"/>
                                <!-- EBI -->
                                <area shape="poly" coords="215,334,244,326,273,335,245,344" onclick="show_pop('EBI：',true);"  id="EBI" alt="EBI"/>
                                <!-- 灌酒机 -->
                                <area shape="circle" coords="296,458,48" onclick="show_pop('灌酒机：',true);"  id="GJJ" alt="灌酒机"/>
                                <!-- FBI -->
                                <area shape="poly" coords="322,518,357,509,388,520,358,531" onclick="show_pop('FBI：',true);"  id="FBI" alt="FBI"/>
                                <!-- 杀菌机 -->
                                <area shape="rect" coords="607,428,716,508" onclick="show_pop('杀菌机：',true);" id="SJJ" alt="杀菌机"/>
                                <!-- 贴标机A -->
                                <area shape="poly" coords="642,305,633,317,637,331,650,339,664,343,672,340,683,335,689,328,693,318,690,308,682,303" onclick="show_pop('贴标机--1：',true);" id="TBJ_A" alt="贴标机-1"/>    
                                <!-- 贴标机B -->
                                <area shape="poly" coords="747,327,738,337,732,348,736,360,745,364,757,367,767,367,780,364,790,354,792,345,789,335,777,327" onclick="show_pop('贴标机--2：',true);"  id="TBJ_B" alt="贴标机-2"/>                        
                            <!-- //四大机台 -->
                            <!-- 其他机台 -->
                                <!-- 美容机-1 -->
                                <area shape="rect" coords="638,271,689,293"  onclick="show_pop('美容机--1：');" id="MRJ_1" alt="美容机-1"/>
                                <!-- 美容机-2 -->
                                <area shape="rect" coords="736,297,789,317"  onclick="show_pop('美容机--2：');" id="MRJ_2" alt="美容机-2"/>
                                <!-- 装箱机 -->
                                <area shape="rect" coords="614,207,684,237"  onclick="show_pop('装箱机：');" id="ZXJ" alt="装箱机"/>
                                <!-- 成箱机-2 -->
                                <area shape="poly" coords="797,232,790,238,798,248,838,247,844,239,833,231"  onclick="show_pop('成&emsp;&emsp;箱&emsp;&emsp;机--2：');" id="CXJ_2" alt="成箱机-2"/>
                                <!-- 成箱机-1 -->
                                <area shape="poly" coords="730,219,722,228,727,238,754,239,765,237,772,233,773,227,769,222,765,221"  onclick="show_pop('成&emsp;&emsp;箱&emsp;&emsp;机--1：');" id="CXJ_1" alt="成箱机-1"/>
                                <!-- 封箱机 -->
                                <area shape="rect" coords="544,193,569,236"  onclick="show_pop('封箱机：');" id="FXJ" alt="封箱机"/>
                                <!-- 码垛机 -->
                                <area shape="rect" coords="127,244,164,285"  onclick="show_pop('码垛机：');" id="MDJ" alt="码垛机"/>
                                <!-- 卸垛机-1 -->
                                <area shape="rect" coords="325,28,344,58"  onclick="show_pop('卸垛机--1：');" id="XDJ_1" alt="卸垛机-1"/>
                                <!-- 卸垛机-2 -->
                                <area shape="rect" coords="364,37,383,67"  onclick="show_pop('卸垛机--2：');" id="XDJ_1" alt="卸垛机-2"/>
                                <!-- 预洗机 -->
                                <area shape="rect" coords="272,119,325,134"  id="YXJ" alt="预洗机"/>
                            <!-- //其他机台 -->
                            <!-- 动力设备（电机） -->
                                <!-- 电机2-8 -->
                                <area shape="circle" coords="465,279,9"   id="DJ_2_8" alt="电机2-8"/>    
                                <!-- 电机2-10 -->
                                <area shape="circle" coords="466,297,8"   id="DJ_2_10" alt="电机2-10"/>    
                                <!--电机2-12 -->
                                <area shape="circle" coords="465,320,9"   id="DJ_2_12" alt="电机2-12"/>
                                <!-- 电机2-11 
                                <area shape="circle" coords="465,338,8"   id="DJ_2_11" alt="电机2-11"/>     -->
                                <!-- 电机2-17 -->
                                <area shape="circle" coords="439,297,9"   id="DJ_2_17" alt="电机2-17"/>    
                                <!-- 电机2-16 -->
                                <area shape="circle" coords="438,320,8"   id="DJ_2_16" alt="电机2-16"/>    
                                <!-- 电机2-15 -->
                                <area shape="circle" coords="438,339,,8"   id="DJ_2_15" alt="电机2-15"/>
                                <!-- 电机2-14 -->
                                <area shape="circle" coords="450,360,8"   id="DJ_2_14" alt="电机2-14"/>    
                                
                                <!-- 电机2-18 -->
                                <area shape="circle" coords="341,282,8"   id="DJ_2_18" alt="电机2-18"/>    
                                <!-- 电机3-19 -->
                                <area shape="circle" coords="237,292,8"   id="DJ_3_19" alt="电机3-19"/>    
                                <!-- 电机3-21 -->
                                <area shape="circle" coords="237,306,8"   id="DJ_3_19" alt="电机3-21"/>    
                                <!-- 电机3-22 
                                <area shape="circle" coords="236,321,8"   id="DJ_3_19" alt="电机3-22"/>    -->
                                <!-- 电机3-20 -->
                                <area shape="circle" coords="259,298,8"   id="DJ_3_20" alt="电机3-20"/>    
                                <!-- 电机M+1 
                                <area shape="circle" coords="258,321,8"   id="DJ_M+1" alt="电机M+1"/>-->    
                                
                                <!-- 电机M23 -->
                                <area shape="circle" coords="300,362,8"   id="DJ_M23" alt="电机M23"/>    
                                <!-- 电机M25 -->
                                <area shape="circle" coords="298,385,8"   id="DJ_M25" alt="电机M25"/>    
                                <!-- 电机M24 -->
                                <area shape="circle" coords="420,384,8"   id="DJ_M24" alt="电机M24"/>
                                <!-- 电机M26 -->
                                <area shape="circle" coords="236,405,8"   id="DJ_M26" alt="电机M26"/>    
                                
                                <!-- 电机3-H5 -->
                                <area shape="circle" coords="278,519,8"   id="DJ_3_H5" alt="电机3-H5"/>
                                <!-- 电机3-H6 -->
                                <area shape="circle" coords="479,519,8"   id="DJ_3_H6" alt="电机3-H6"/>    
                                <!-- 电机2-29 -->
                                <area shape="circle" coords="519,519,8"   id="DJ_2_29" alt="电机2-29"/>    
                                <!-- 电机2-30 -->
                                <area shape="circle" coords="542,503,8"   id="DJ_2_30" alt="电机2-30"/>    
                                <!-- 电机2-31 -->
                                <area shape="circle" coords="541,484,8"   id="DJ_2_31" alt="电机2-31"/>
                                
                                <!-- 电机2-33 -->
                                <area shape="circle" coords="543,416,8"   id="DJ_2_33" alt="电机2-33"/>
                                <!-- 电机2-32 -->
                                <area shape="circle" coords="565,430,8"   id="DJ_2_32" alt="电机2-32"/>        
                                <!-- 电机4-36 -->
                                <area shape="circle" coords="757,537,8"   id="DJ_4_36" alt="电机4-36"/>    
                                <!-- 电机4-37 -->
                                <area shape="circle" coords="756,512,8"   id="DJ_4_37" alt="电机4-37"/>    
                                
                                <!-- 电机4-42 -->
                                <area shape="circle" coords="789,504,8"   id="DJ_4_42" alt="电机4-42"/>        
                                <!-- 电机4-43 -->
                                <area shape="circle" coords="791,487,8"   id="DJ_4_43" alt="电机4-43"/>        
                                <!-- 电机4-44 -->
                                <area shape="circle" coords="791,470,8"   id="DJ_4_44" alt="电机4-44"/>        
                                <!-- 电机4-38 -->
                                <area shape="circle" coords="817,504,8"   id="DJ_4_38" alt="电机4-38"/>        
                                <!-- 电机4-39 -->
                                <area shape="circle" coords="817,486,8"   id="DJ_4_39" alt="电机4-39"/>        
                                <!-- 电机4-40 -->
                                <area shape="circle" coords="820,471,8"   id="DJ_4_40" alt="电机4-40"/>    
                                
                                <!-- 电机4-49 -->
                                <area shape="circle" coords="748,394,8"   id="DJ_4_49" alt="电机4-49"/>
                                <!-- 电机4-45 -->
                                <area shape="circle" coords="617,394,8"   id="DJ_4_45" alt="电机4-45"/>    
                                
                                <!-- 电机4-41 -->
                                <area shape="circle" coords="493,380,8"   id="DJ_4_41" alt="电机4-41"/>
                                <!-- 电机4-46 -->
                                <area shape="circle" coords="492,346,8"   id="DJ_4_46" alt="电机4-46"/>    
                                <!-- 电机4-47 -->
                                <area shape="circle" coords="493,320,8"   id="DJ_4_47" alt="电机4-47"/>    
                                <!-- 电机4-48 -->
                                <area shape="circle" coords="518,270,8"   id="DJ_4_48" alt="电机4-48"/>    
                                
                                <!-- 电机6-1 -->
                                <area shape="circle" coords="569,286,8"   id="DJ_6_1" alt="电机6-1"/>        
                                <!-- 电机6-2 -->
                                <area shape="circle" coords="569,297,8"   id="DJ_6_2" alt="电机6-2"/>        
                                <!-- 电机6-3 -->
                                <area shape="circle" coords="570,311,8"   id="DJ_6_3" alt="电机6-3"/>        
                                <!-- 电机6-4 -->
                                <area shape="circle" coords="571,321,8"   id="DJ_6_4" alt="电机6-4"/>        
                                <!-- 电机6-5 -->
                                <area shape="circle" coords="571,336,8"   id="DJ_6_5" alt="电机6-5"/>        
                                <!-- 电机6-6 -->
                                <area shape="circle" coords="578,355,8"   id="DJ_6_6" alt="电机6-6"/>    
                                <!-- 电机6-7 -->
                                <area shape="circle" coords="663,353,8"   id="DJ_6_7" alt="电机6-7"/>
                                        
                                <!-- 电机5-1 -->
                                <area shape="circle" coords="555,310,8"   id="DJ_5_1" alt="电机5-1"/>        
                                <!-- 电机5-2 -->
                                <area shape="circle" coords="555,320,8"   id="DJ_5_2" alt="电机5-2"/>        
                                <!-- 电机5-3 -->
                                <area shape="circle" coords="555,335,8"   id="DJ_5_3" alt="电机5-3"/>
                                <!-- 电机5-4 -->
                                <area shape="circle" coords="556,347,8"   id="DJ_5_4" alt="电机5-4"/>
                                <!-- 电机5-5 -->
                                <area shape="circle" coords="557,376,8"   id="DJ_5_5" alt="电机5-5"/>
                                <!-- 电机5-6 -->
                                <area shape="circle" coords="648,377,8"   id="DJ_5_6" alt="电机5-6"/>
                                <!-- 电机5-7 -->
                                <area shape="circle" coords="748,376,8"   id="DJ_5_7" alt="电机5-7"/>    
                                
                                <!-- 电机5-8 -->
                                <area shape="circle" coords="762,320,8"   id="DJ_5_8" alt="电机5-8"/>    
                                <!-- 电机5-9 -->
                                <area shape="circle" coords="775,278,8"   id="DJ_5_9" alt="电机5-9"/>    
                                <!-- 电机5-11 -->
                                <area shape="circle" coords="798,261,8"   id="DJ_5_11" alt="电机5-11"/>    
                                <!-- 电机5-12 -->
                                <area shape="circle" coords="747,256,8"   id="DJ_5_12" alt="电机5-12"/>    
                                
                                <!-- 电机4-54 -->
                                <area shape="circle" coords="661,297,8"   id="DJ_4_54" alt="电机4-54"/>    
                                
                                <!-- 电机M62 -->
                                <area shape="circle" coords="663,267,8"   id="DJ_M62" alt="电机M62"/>    
                                <!-- 电机4-55 -->
                                <area shape="circle" coords="623,272,8"   id="DJ_4_55" alt="电机4-55"/>    
                                <!-- 电机4-57 -->
                                <area shape="circle" coords="615,260,8"   id="DJ_4_57" alt="电机4-57"/>    
                                <!-- 电机4-61 -->
                                <area shape="circle" coords="647,256,8"   id="DJ_4_61" alt="电机4-61"/>    
                                <!-- 电机4-56 -->
                                <area shape="circle" coords="664,256,8"   id="DJ_4_56" alt="电机4-56"/>    
                                <!-- 电机4-58 -->
                                <area shape="circle" coords="686,255,8"   id="DJ_4_58" alt="电机4-58"/>    
                                <!-- 电机4-59 -->
                                <area shape="circle" coords="707,241,8"   id="DJ_4_59" alt="电机4-59"/>
                                
                                <!-- 电机7-1 -->
                                <area shape="circle" coords="816,225,8"   id="DJ_7-1" alt="电机7-1"/>    
                                <!-- 电机7-2 -->
                                <area shape="circle" coords="820,213,8"   id="DJ_7-2" alt="电机7-2"/>    
                                <!-- 电机7-3 -->
                                <area shape="circle" coords="747,212,8"   id="DJ_7_3" alt="电机7-3"/>    
                                <!-- 电机7-4 -->
                                <area shape="circle" coords="755,202,8"   id="DJ_7_4" alt="电机7-4"/>    
                                <!-- 电机7-5 -->
                                <area shape="circle" coords="774,202,8"   id="DJ_7_5" alt="电机7-5"/>    
                                <!-- 电机7-6 -->
                                <area shape="circle" coords="791,201,8"   id="DJ_7_6" alt="电机7-6"/>    
                                <!-- 电机7-7 -->
                                <area shape="circle" coords="809,201,8"   id="DJ_7_7" alt="电机7-7"/>
                                
                                <!-- 电机7-9 -->
                                <area shape="circle" coords="733,180,8"   id="DJ_7_9" alt="电机7-9"/>        
                                <!-- 电机7-29 -->
                                <area shape="circle" coords="662,181,8"   id="DJ_7_29" alt="电机7-29"/>        
                                <!-- 电机7-10 -->
                                <area shape="circle" coords="639,197,8"   id="DJ_7_10" alt="电机7-10"/>    
                                
                                <!-- 电机7-11 -->
                                <area shape="circle" coords="592,239,8"   id="DJ_7_11" alt="电机7-11"/>        
                                <!-- 电机7-12 -->
                                <area shape="circle" coords="593,256,8"   id="DJ_7_12" alt="电机7-12"/>        
                                <!-- 电机7-13 -->
                                <area shape="circle" coords="593,270,8"   id="DJ_7_13" alt="电机7-13"/>    
                                <!-- 电机7-14 -->
                                <area shape="circle" coords="570,271,8"   id="DJ_7_14" alt="电机7-14"/>        
                                <!-- 电机7-25 -->
                                <area shape="circle" coords="541,241,8"   id="DJ_7_25" alt="电机7-25"/>        
                                <!-- 电机7-24 -->
                                <area shape="circle" coords="571,240,8"   id="DJ_7_24" alt="电机7-24"/>
                                
                                <!-- 电机7-17 -->
                                <area shape="circle" coords="541,189,8"   id="DJ_7_17" alt="电机7-17"/>        
                                <!-- 电机7-18 -->
                                <area shape="circle" coords="572,189,8"   id="DJ_7_18" alt="电机7-18"/>        
                                <!-- 电机7-19 -->
                                <area shape="circle" coords="540,178,8"   id="DJ_7_19" alt="电机7-19"/>    
                                <!-- 电机7-20 -->
                                <area shape="circle" coords="572,178,8"   id="DJ_7_20" alt="电机7-20"/>        
                                <!-- 电机7-21 -->
                                <area shape="circle" coords="538,168,8"   id="DJ_7_21" alt="电机7-21"/>        
                                <!-- 电机7-22 -->
                                <area shape="circle" coords="573,165,8"   id="DJ_7_22" alt="电机7-22"/>                    
                                <!-- 电机7-23 -->
                                <area shape="circle" coords="557,142,8"   id="DJ_7_23" alt="电机7-23"/>
                                
                                <!-- 电机2-6 -->
                                <area shape="circle" coords="338,178,8"   id="DJ_2_6" alt="电机2-6"/>        
                                <!-- 电机2-5 -->
                                <area shape="circle" coords="298,178,8"   id="DJ_2_6" alt="电机2-5"/>        
                                <!-- 电机2-4 -->
                                <area shape="circle" coords="250,170,8"   id="DJ_2_4" alt="电机2-4"/>    
                                <!-- 电机2-3 -->
                                <area shape="circle" coords="276,159,8"   id="DJ_2_3" alt="电机2-3"/>        
                                <!-- 电机2-2 -->
                                <area shape="circle" coords="333,159,8"   id="DJ_2_2" alt="电机2-2"/>        
                                <!-- 电机2-1 -->
                                <area shape="circle" coords="380,160,8"   id="DJ_2_1" alt="电机2-1"/>                    
                                <!-- 电机2-1+1 -->
                                <area shape="circle" coords="384,133,8"   id="DJ_7_23" alt="电机2-1+1"/>    
                                
                                <!-- 电机8-14 -->
                                <area shape="circle" coords="219,126,8"   id="DJ_8_14" alt="电机8-14"/>    
                                <!-- 电机8-13 -->
                                <area shape="circle" coords="194,115,8"   id="DJ_8_13" alt="电机8-13"/>        
                                <!-- 电机8-12 -->
                                <area shape="circle" coords="245,99,8"   id="DJ_8_12" alt="电机8-12"/>        
                                <!-- 电机8-11 -->
                                <area shape="circle" coords="278,99,8"   id="DJ_8_11" alt="电机8-11"/>    
                                <!-- 电机8-10 -->
                                <area shape="circle" coords="362,103,8"   id="DJ_8_10" alt="电机8-10"/>        
                                <!-- 电机8-9 -->
                                <area shape="circle" coords="372,91,8"   id="DJ_8_9" alt="电机8-9"/>        
                                <!-- 电机8-8 -->
                                <area shape="circle" coords="334,84,8"   id="DJ_8_8" alt="电机8-8"/>                    
                                <!-- 电机8-7 -->
                                <area shape="circle" coords="298,84,8"   id="DJ_8_7" alt="电机8-7"/>    
                                <!-- 电机8-6 -->
                                <area shape="circle" coords="260,84,8"   id="DJ_8_6" alt="电机8-6"/>        
                                <!-- 电机8-5 -->
                                <area shape="circle" coords="216,86,8"   id="DJ_8_6" alt="电机8-5"/>        
                                <!-- 电机8-4 -->
                                <area shape="circle" coords="218,70,8"   id="DJ_8_4" alt="电机8-4"/>    
                                <!-- 电机8-3 -->
                                <area shape="circle" coords="260,69,8"   id="DJ_8_3" alt="电机8-3"/>        
                                <!-- 电机8-2 -->
                                <area shape="circle" coords="301,70,8"   id="DJ_8_2" alt="电机8-2"/>        
                                <!-- 电机8-1 -->
                                <area shape="circle" coords="357,71,8"   id="DJ_8_1" alt="电机8-1"/>    
                                
                                <!-- 电机1-22 -->
                                <area shape="circle" coords="130,144,8"   id="DJ_1_22" alt="电机1-22"/>    
                                <!-- 电机1-23 -->
                                <area shape="circle" coords="68,144,8"   id="DJ_1_23" alt="电机1-23"/>        
                                <!-- 电机1-18 -->
                                <area shape="circle" coords="69,169,8"   id="DJ_1_18" alt="电机1-18"/>        
                                <!-- 电机1-19 -->
                                <area shape="circle" coords="68,195,8"   id="DJ_1_19" alt="电机1-19"/>    
                                <!-- 电机1-17 -->
                                <area shape="circle" coords="70,232,8"   id="DJ_1_17" alt="电机1-17"/>        
                                <!-- 电机1-16 -->
                                <area shape="circle" coords="69,256,8"   id="DJ_1_16" alt="电机1-16"/>        
                                <!-- 电机1-25 -->
                                <area shape="circle" coords="68,282,8"   id="DJ_1_25" alt="电机1-25"/>                    
                                <!-- 电机1-26 -->
                                <area shape="circle" coords="70,310,8"   id="DJ_1_26" alt="电机1-26"/>    
                                <!-- 电机1-27 -->
                                <area shape="circle" coords="105,320,8"   id="DJ_1_27" alt="电机1-27"/>        
                                <!-- 电机1-29 -->
                                <area shape="circle" coords="146,296,8"   id="DJ_1_29" alt="电机1-29"/>                    
                                <!-- 
                                    <c:choose>
                                        <c:when test='${响应回来的数据对象对应的状态==1}'><font style='color: #00CD00;'>正常</font></c:when>
                                        <c:when test='${响应回来的数据对象对应的状态==0}'><font style='color: #CD0000;'>停机</font></c:when>
                                        <c:otherwise><font style='color: #FFFFFF;'>未采集</font></c:otherwise>
                                    </c:choose>    
                                 -->    
                            <!-- //动力设备（电机） -->    
                            </map>
                            <!-- 图片热区提示 -->
                            <script language="javascript">
                                $(function(){
                                    $("area").each(function(){
                                            var name=$(this).attr("alt");    
                                            $(this).mouseover(function(e){
                                                var currentMstatus="";
                                                var currentMcolor="";
                                                /* ----STRT---链道电机json对象------- 
                             var MACHINE_NAME = [{m_name:"电机2-8",m_id:"M2-8"},
                                                 {m_name:"电机2-10",m_id:"M2-10"},
                                                 {m_name:"电机2-12",m_id:"M2-12"},
                                                 {m_name:"电机2-11",m_id:"M2-11"},
                                                 {m_name:"电机2-17",m_id:"M2-17"},
                                                 {m_name:"电机2-16",m_id:"M2-16"},
                                                 {m_name:"电机2-15",m_id:"M2-15"},
                                                 {m_name:"电机2-14",m_id:"M2-14"},
                                                 {m_name:"电机2-18",m_id:"M2-18"},
                                                 {m_name:"电机3-19",m_id:"M3-19"},
                                                {m_name:"电机3-21",m_id:"M3-21"},
                                                {m_name:"电机3-22",m_id:"M3-22"},
                                                {m_name:"电机3-20",m_id:"M3-20"},
                                                {m_name:"电机M+1",m_id:"M1+1"},
                                                {m_name:"电机M23",m_id:"M23"},
                                                {m_name:"电机M25",m_id:"M25"},
                                                {m_name:"电机M24",m_id:"M24"},
                                                {m_name:"电机M26",m_id:"M26"},
                                                {m_name:"电机3-H5",m_id:"M3_H5"},
                                                {m_name:"电机3-H6",m_id:"M3-H6"},
                                                {m_name:"电机2-29",m_id:"M2-29"},
                                                {m_name:"电机2-30",m_id:"M2-30"},
                                                {m_name:"电机2-31",m_id:"M2-31"},
                                                {m_name:"电机2-33",m_id:"M2-33"},
                                                {m_name:"电机2-32",m_id:"M2-32"},
                                                {m_name:"电机4-36",m_id:"M4-36"},
                                                {m_name:"电机4-37",m_id:"M4-37"},
                                                {m_name:"电机4-42"},m_id:"M4-42"},
                                                {m_name:"电机4-43",m_id:"M4-43"},
                                                {m_name:"电机4-44",m_id:"M4-44"},
                                                {m_name:"电机4-38",m_id:"M4-38"},
                                                {m_name:"电机4-39",m_id:"M4-39"},
                                                {m_name:"电机4-40",m_id:"M4-40"},
                                                {m_name:"电机4-49",m_id:"M4-49"},
                                                {m_name:"电机4-45",m_id:"M4-45"},
                                                {m_name:"电机4-41",m_id:"M4-41"},
                                                {m_name:"电机4-46",m_id:"M4-46"},
                                                {m_name:"电机4-47",m_id:"M4-47"},
                                                {m_name:"电机4-48",m_id:"M4-48"},
                                                {m_name:"电机6-1",m_id:"M6-1"},
                                                {m_name:"电机6-2",m_id:"M6-2"},
                                                {m_name:"电机6-3",m_id:"M6-3"},
                                                {m_name:"电机6-4",m_id:"M6-4"},
                                                {m_name:"电机6-5",m_id:"M6-5"},
                                                {m_name:"电机6-6",m_id:"M6-6"},
                                                {m_name:"电机6-7",m_id:"M6_7"},
                                                {m_name:"电机5-1"},m_id:"M5-1"},
                                                {m_name:"电机5-2"},m_id:"M5-2"},
                                                {m_name:"电机5-3"},m_id:"M5-3"},
                                                {m_name:"电机5-4",m_id:"M5-4"},
                                                {m_name:"电机5-5",m_id:"M5-5"},
                                                {m_name:"电机5-6",m_id:"M5-6"},
                                                {m_name,"电机5-7",m_id:"M5-87"}];
                                          ----END---链道电机json对象------- */
                                                switch (name) {
                                                case "洗瓶机":
                                                    currentMcolor = $("#XPJ_D").css("border-color");
                                                    break;
                                                case "灌酒机":
                                                    currentMcolor = $("#GJJ_D").css("border-color");
                                                    break;
                                                case "杀菌机":
                                                    currentMcolor = $("#SJJ_D").css("border-color");
                                                    break;
                                                case "贴标机-1":
                                                    currentMcolor = $("#TBJ_A_D").css("border-color");
                                                    break;
                                                 case "贴标机-2":
                                                    currentMcolor = $("#TBJ_B_D").css("border-color");
                                                    break;
                                                 case "电机2-8":
                                                        currentMcolor = $("#M2_8").css("background-color");
                                                    break;
                                                    case "电机2-10":
                                                                currentMcolor = $("#M2_10").css("background-color");
                                                    break;
                                                    
                                                    case "电机2-12":
                                                                currentMcolor = $("#M2_12").css("background-color");
                                                    break;
                                                    /* case "电机2-11":
                                                                currentMcolor = $("#M2_11").css("background-color");
                                                    break; */
                                                    
                                                    case "电机2-17":
                                                                currentMcolor = $("#M2_17").css("background-color");
                                                    break;
                                                    case "电机2-16":
                                                                currentMcolor = $("#M2_16").css("background-color");
                                                    break;    
                                                    case "电机2-15":
                                                                currentMcolor = $("#M2_15").css("background-color");
                                                    break;
                                                    case "电机2-14":
                                                                currentMcolor = $("#M2_14").css("background-color");
                                                    break;        
                                                    case "电机2-18":
                                                                currentMcolor = $("#M2_18").css("background-color");
                                                    break;                                               
                                                    case "电机3-19":
                                                                currentMcolor = $("#M3_19").css("background-color");
                                                    break;    
                                                    case "电机3-21":
                                                                currentMcolor = $("#M3_21").css("background-color");
                                                    break;
                                                    /* case "电机3-22":
                                                                currentMcolor = $("#M3_22").css("background-color");
                                                    break; */    
                                                    case "电机3-20":
                                                                currentMcolor = $("#M3_20").css("background-color");
                                                    break;
                                                    /* case "电机M+1":
                                                                currentMcolor = $("#M1+1").css("background-color");
                                                    break;     */
                                                    case "电机M23":
                                                                currentMcolor = $("#M23").css("background-color");
                                                    break;
                                                    case "电机M25":
                                                                currentMcolor = $("#M25").css("background-color");
                                                    break;
                                                    case "电机M24":
                                                                currentMcolor = $("#M24").css("background-color");
                                                    break;
                                                    case "电机M26":
                                                                currentMcolor = $("#M26").css("background-color");
                                                    break;
                                                    case "电机3-H5":
                                                                currentMcolor = $("#3_H5").css("background-color");
                                                    break;
                                                    case "电机3-H6":
                                                                currentMcolor = $("#3_H6").css("background-color");
                                                    break;
                                                    case "电机2-29":
                                                                currentMcolor = $("#M2_29").css("background-color");
                                                    break;
                                                    case "电机2-30":
                                                                currentMcolor = $("#M2_30").css("background-color");
                                                    break;
                                                    case "电机2-31":
                                                                currentMcolor = $("#M2_31").css("background-color");
                                                    break;
                                                    case "电机2-33":
                                                                currentMcolor = $("#M2_33").css("background-color");
                                                    break;
                                                    case "电机2-32":
                                                                currentMcolor = $("#M2_32").css("background-color");
                                                    break;
                                                    case "电机4-36":
                                                                currentMcolor = $("#M4_36").css("background-color");
                                                    break;
                                                    case "电机4-37":
                                                                currentMcolor = $("#M4_37").css("background-color");
                                                    break;
                                                    case "电机4-42":
                                                                currentMcolor = $("#M4_42").css("background-color");
                                                    break;
                                                    case "电机4-43":
                                                                currentMcolor = $("#M4_43").css("background-color");
                                                    break;
                                                    case "电机4-44":
                                                                currentMcolor = $("#M4_44").css("background-color");
                                                    break;
                                                    case "电机4-38":
                                                                currentMcolor = $("#M4_38").css("background-color");
                                                    break;
                                                    case "电机4-39":
                                                                currentMcolor = $("#M4_39").css("background-color");
                                                    break;
                                                    case "电机4-40":
                                                                currentMcolor = $("#M4_40").css("background-color");
                                                    break;
                                                    case "电机4-49":
                                                                currentMcolor = $("#M4_49").css("background-color");
                                                    break;
                                                    case "电机4-45":
                                                                currentMcolor = $("#M4_45").css("background-color");
                                                    break;
                                                    case "电机4-41":
                                                                currentMcolor = $("#M4_41").css("background-color");
                                                    break;
                                                    case "电机4-46":
                                                                currentMcolor = $("#M4_46").css("background-color");
                                                    break;
                                                    case "电机4-47":
                                                                currentMcolor = $("#M4_47").css("background-color");
                                                    break;
                                                    case "电机4-48":
                                                                currentMcolor = $("#M4_48").css("background-color");
                                                    break;
                                                    case "电机5-1":
                                                                currentMcolor = $("#M5_1").css("background-color");
                                                    break;
                                                    case "电机5-2":
                                                                currentMcolor = $("#M5_2").css("background-color");
                                                    break;
                                                    case "电机5-3":
                                                                currentMcolor = $("#M5_3").css("background-color");
                                                    break;
                                                    case "电机5-4":
                                                                currentMcolor = $("#M5_4").css("background-color");
                                                    break;
                                                    case "电机5-5":
                                                                currentMcolor = $("#M5_5").css("background-color");
                                                    break;
                                                    case "电机5-6":
                                                                currentMcolor = $("#M5_6").css("background-color");
                                                    break;
                                                    case "电机5-7":
                                                                currentMcolor = $("#M5_7").css("background-color");
                                                    break;
                                                    /* case "电机6-1":
                                                                currentMcolor = $("#M6_1").css("background-color");
                                                    break;
                                                    case "电机6-2":
                                                                currentMcolor = $("#M6_2").css("background-color");
                                                    break;
                                                    case "电机6-3":
                                                                currentMcolor = $("#M6_3").css("background-color");
                                                    break;
                                                    case "电机6-4":
                                                                currentMcolor = $("#M6_4").css("background-color");
                                                    break;
                                                    case "电机6-5":
                                                                currentMcolor = $("#M6_5").css("background-color");
                                                    break;
                                                    case "电机6-6":
                                                                currentMcolor = $("#M6_6").css("background-color");
                                                    break; */
                                                            
                                                default:
                                                    break;
                                                }
                                                /* for(var machine in MACHINE_NAME){
                                                    if(name==MACHINE_NAME[machine].m_name){
                                                        currentMcolor = $("#"+MACHINE_NAME[machine].m_id).css("border-color");
                                                    }
                                                } */ 
                                              //机台名称确定以及电机某些信息隐藏
                                                var isHid = "";
                                                var m_name="";
                                                switch (name){
                                                    case "洗瓶机":
                                                        m_name="1";
                                                        isHid = "";
                                                        break;
                                                    case "灌酒机":
                                                           m_name="2";
                                                           isHid = "";
                                                        break;
                                                    case "杀菌机":
                                                        m_name="3";
                                                        isHid = "";
                                                        break;
                                                    /* case "贴标机-1":
                                                        m_name="5";
                                                        isHid = "";
                                                        break; */
                                                    case "贴标机-2":
                                                        m_name="4";
                                                        isHid = "";
                                                        break;
                                                    default:
                                                        isHid = "hidden";
                                                        break;
                                                }
                                                var machineHiddenStatus = "";
                                                switch (currentMcolor) {
                                                case "rgb(248, 248, 255)":
                                                    currentMstatus = "酒机停机主要源";
                                                    machineHiddenStatus = "hidden";
                                                    break;
                                                case "rgb(220, 20, 60)":
                                                    currentMstatus = "自身原因停机";
                                                    if((m_name=="1")||(m_name=="2")||(m_name=="3")||(m_name=="4")||(m_name=="5")){
                                                        machineHiddenStatus = "hidden";
                                                    }else{
                                                        machineHiddenStatus = "";
                                                    }
                                                    break;
                                                case "rgb(255, 255, 0)":
                                                    currentMstatus = "计划停机";
                                                    machineHiddenStatus = "hidden";
                                                    break;
                                                case "rgb(50, 205, 50)":
                                                    currentMstatus = "正常";
                                                    machineHiddenStatus = "";
                                                    break;
                                                case "rgb(30, 144, 255)":
                                                    currentMstatus = "前缺导致停机";
                                                    machineHiddenStatus = "hidden";
                                                    break;
                                                case "rgb(0, 0, 139)":
                                                    currentMstatus = "后堵导致停机";
                                                    machineHiddenStatus = "hidden";
                                                    break;
                                                default:
                                                	machineHiddenStatus = "";
                                                    break;
                                                }
                                                var dom="<div class='mapDiv'></div>";
                                                $("body").append(dom);
/* ----------------------------START----------------------自定义事件触发请求----------------------------------------------------- */
                                              $.ajax({
                                                 url: "/lineview/getStatusOfMachine",
                                                 type: "get",
                                                 data: {"machineFlg": m_name },
                                                 async:false,
                                                 success: function(data) {
                                                     var domChild = "<p>机器名称："+
                                                                        "<span class='name' style='color:#8B0000;'></span><br/>"+
                                                                        "<span>运行状态："+
                                                                            "<strong id='mc_case_pie' style='color: "+currentMcolor+";'>"+currentMstatus+"</strong>"+
                                                                        "</span><br/>"+
                                                                        "<span "+isHid+machineHiddenStatus+">平均速度：<font style='color: #CD0000;'>"+parseInt(data.averageSpeed)+"瓶/小时</font></span><br/>"+
                                                                        "<span "+isHid+machineHiddenStatus+">实时速度：<font style='color: #CD0000;'>"+parseInt(data.transientSpeed)+"瓶/小时</font></span><br/>"+
                                                                    "</p>";
                                                    $(".mapDiv").append(domChild);
                                                    $(".name").text(name);
                                                 },
                                             });
/* ----------------------------END----------------------自定义事件触发请求----------------------------------------------------- */
                                                /* var $x=-70;
                                                var $y=-110; */
                                                $(".mapDiv").css({
                                                        top: (e.pageY + (-110))+"px",
                                                        left: (e.pageX + (-70))+"px"
                                                    }).show("fast");
                                                });
                                            }).mouseout(function(){                
                                                    $(".mapDiv").remove();
                                            }).mousemove(function(e){
                                                    $(".mapDiv").css({
                                                        top: (e.pageY + (-110))+"px",
                                                        left: (e.pageX + (-70))+"px"
                                                    });
                                            });
                                });
                                </script>
                            </Div>
                            <!-- //整线一览div -->
                            <!-- 机台状态柱状图div -->
                            <!-- 柱状动态图表样式 （考虑到js的加载顺序，故所有的js放置位置确定）-->
                            <script type="text/javascript" src="<c:url value='/operator_style/js/status_page.js'/>"></script>
                            <!-- //柱状动态图表样式 -->
                            <Div id="coverdiv" hidden="hidden" style="width: 100%;height: 100%;border-top: 2px solid #104E8B; ">
                                <!-- 分析时间段数据隐藏控制 -->
                                <input type="text" id="analyze_timelegth" hidden="hidden" value="30"/>
                                <div id="machine_list">
                                    <!-- 卸垛机 -->
                                    <div id="machine10" class="checkboxWrapper theme1 extraSmallCheckboxSize">
                                        <input type="checkbox" id="sample10" name="m10" value="10">
                                        <label for="sample10">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" style="font-size: 10px;" onclick="show_pop('卸垛机：',true);" ><font>卸垛机</font></a>
                                        </label>
                                        <div id="show_are">
                                            <div id="div10" class="div" name="卸垛机"></div>
                                        </div>
                                    </div>
                                    <!-- //卸垛机 --> 
                                     <!-- 卸垛机 链道-->
                                    <div id="machine10_1" class="checkboxWrapper theme1 extraSmallCheckboxSize">
                                        <input type="checkbox" id="sample10_1" name="m10_1" value="10_1">
                                        <label for="sample10_1">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)"  style="font-size: 10px;"><font>卸垛机至洗瓶机链道</font></a>
                                        </label>
                                        <div id="show_are">
                                            <div id="div10_1" class="div" name="卸垛机至洗瓶机链道"></div>
                                        </div>
                                    </div>
                                    <!-- //卸垛机 链道--> 
                                    <!-- 洗瓶机 -->
                                    <div id="machine1" class="checkboxWrapper theme1 extraSmallCheckboxSize">
                                        <input type="checkbox" id="sample1" name="m1" value="1">
                                        <label for="sample1">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" style="font-size: 10px;" onclick="show_pop('洗瓶机：',true);" ><font>洗瓶机</font></a>
                                        </label>
                                        <div id="show_are">
                                            <div id="div1" class="div" name="洗瓶机"></div>
                                        </div>
                                    </div>
                                    <!-- //洗瓶机 --> 
                                    <!-- 链道1.1 -->
                                    <div id="machine1_1" class="checkboxWrapper theme1 extraSmallCheckboxSize">
                                        <input type="checkbox" id="sample1_1" name="m1_1" value="1_1">
                                        <label for="sample1_1">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" style="font-size: 10px;" ><font>洗瓶机至EBI链道</font></a>
                                        </label>
                                        <div id="show_are">
                                            <div id="div1_1" class="div" name="洗瓶机至EBI链道"></div>
                                        </div>
                                    </div>
                                    <!-- //链道1.1 --> 
                                    <!-- 链道1.2 -->
                                    <div id="machine1_1_1" class="checkboxWrapper theme1 extraSmallCheckboxSize">
                                        <input type="checkbox" id="sample1_1_1" name="m1_1_1" value="1_1_1">
                                        <label for="sample1_1_1">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" style="font-size: 10px;" ><font>EBI至灌酒机链道</font></a>
                                        </label>
                                        <div id="show_are">
                                            <div id="div1_1_1" class="div" name="EBI至灌酒机链道"></div>
                                        </div>
                                    </div>
                                    <!-- //链道1.2 --> 
                                     <!-- 灌酒机 -->
                                    <div id="machine2" class="checkboxWrapper theme1 extraSmallCheckboxSize">
                                        <input type="checkbox" id="sample2" name="m2" value="2">
                                        <label for="sample2">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" style="font-size: 10px;" onclick="show_pop('灌酒机：',true);"><font>灌酒机</font></a>
                                        </label>
                                        <div id="show_are" >
                                            <div id="div2" class="div" name="灌酒机"></div>
                                        </div>
                                    </div>
                                    <!-- //灌酒机 --> 
                                     <!-- 链道2 -->
                                    <div id="machine2_2" class="checkboxWrapper theme1 extraSmallCheckboxSize">
                                        <input type="checkbox" id="sample2_2" name="m2_2" value="2_2">
                                        <label for="sample2_2">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" style="font-size: 10px;" ><font>灌酒机至杀菌机链道</font></a>
                                        </label>
                                        <div id="show_are" >
                                            <div id="div2_2" class="div" name="灌酒机至杀菌机链道"></div>
                                        </div>
                                    </div>
                                    <!-- //链道2 --> 
                                     <!-- 杀菌机 -->
                                    <div id="machine3" class="checkboxWrapper theme1 extraSmallCheckboxSize">
                                        <input type="checkbox" id="sample3" name="m3" value="3">
                                        <label for="sample3">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" style="font-size: 10px;" onclick="show_pop('杀菌机：',true);" ><font>杀菌机</font></a>
                                        </label>
                                        <div id="show_are" >
                                            <div id="div3" class="div" name="杀菌机"></div>
                                        </div>
                                    </div>
                                    <!-- //杀菌机 --> 
                                     <!-- 链道3 -->
                                    <div id="machine3_3" class="checkboxWrapper theme1 extraSmallCheckboxSize">
                                        <input type="checkbox" id="sample3_3" name="m3_3" value="3_3">
                                        <label for="sample3_3">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" style="font-size: 10px;" ><font>杀菌机至贴标机链道</font></a>
                                        </label>
                                        <div id="show_are" >
                                            <div id="div3_3" class="div" name="杀菌机至贴标机链道"></div>
                                        </div>
                                    </div>
                                    <!-- //链道3 --> 
                                     <!-- 贴标机—A -->
                                    <div id="machine4" class="checkboxWrapper theme1 extraSmallCheckboxSize" >
                                        <input type="checkbox" id="sample4" name="m4" value="4">
                                        <label for="sample4">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" style="font-size: 10px;" onclick="show_pop('贴标机--1：',true);"><font>贴标机</font></a>
                                        </label>
                                        <div id="show_are" >
                                            <div id="div4" class="div" name="贴标机-1"></div>
                                        </div>
                                    </div>
                                    <!-- //贴标机—A --> 
                                     <!-- 贴标机至码垛机 -->
                                    <div id="machine11_1" class="checkboxWrapper theme1 extraSmallCheckboxSize" >
                                        <input type="checkbox" id="sample11_1" name="m11__1" value="11_1">
                                        <label for="sample11_1">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" style="font-size: 10px;"><font>贴标机至码垛机链道</font></a>
                                        </label>
                                        <div id="show_are" >
                                            <div id="div11_1" class="div" name="贴标机至码垛机链道"></div>
                                        </div>
                                    </div>
                                    <!-- //贴标机至码垛机 --> 
                                     <!-- 码垛机 -->
                                    <div id="machine11" class="checkboxWrapper theme1 extraSmallCheckboxSize" >
                                        <input type="checkbox" id="sample11" name="m11" value="11">
                                        <label for="sample11">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" style="font-size: 10px;" onclick="show_pop('码垛机：',true);"><font>码垛机</font></a>
                                        </label>
                                        <div id="show_are" >
                                            <div id="div11" class="div" name="码垛机"></div>
                                        </div>
                                    </div>
                                    <!-- //码垛机 --> 
                                     <!-- 贴标机-B 
                                    <div id="machine5" class="checkboxWrapper theme1 extraSmallCheckboxSize">
                                        <input type="checkbox" id="sample5" name="m5" value="5">
                                        <label for="sample5">
                                            <i>
                                                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                                                width="50px" height="50px" viewBox="0 0 50 50" enable-background="new 0 0 50 50" xml:space="preserve">
                                                    <circle fill="none" stroke="#B7B7B7" stroke-width="3" stroke-miterlimit="10" cx="25.11" cy="24.883" r="23.519"/>
                                                    <path fill="none" stroke-width="3" stroke-miterlimit="10" d="M48.659,25c0,12.998-10.537,23.534-23.534,23.534
                                                    S1.591,37.998,1.591,25S12.127,1.466,25.125,1.466c9.291,0,17.325,5.384,21.151,13.203L19,36l-9-14"/>
                                                </svg>
                                            </i>
                                            <a href="javascript:void(0)" onclick="show_pop('贴标机--2：',true);"><font>贴标机-2</font></a>
                                        </label>
                                        <div id="show_are">
                                            <div id="div5" class="div" name="贴标机-2"></div>
                                        </div>
                                    </div>
                                    //贴标机-B --> 
                                </div>
                                <button style="font-size: 10px;float: left;margin-right: 2%;" id="btn_care" >关注模式</button>
                                <button style="font-size: 10px;float: left;margin-right: 2%;" id="btn_exit_care">取消关注</button>
                                <button style="font-size: 10px;float: left" id="btn_add_note">添加备注</button>
                                <!-- <div style="width: 70%;height: 14%;border: 1px solid #A0522D;font-style:微软雅黑;font-size: 10px;margin-top: 6%;margin-left:1%; color: #668B8B;padding: 5px;">
                                    <h6>说明：</h6>&emsp;&emsp;1、相应机台点击可跳转单机台显示模式；<br/>
                                                    &emsp;&emsp;2、添加备注按钮点击显示添加备注；<br/>
                                                    &emsp;&emsp;3、选中对应机台前复选框，点击关注模式，即可对比机台信息，点击取消关注，则显示全部机台信息；
                                    
                                </div> -->
                            </Div>
                            <script type="text/javascript">
                                $(document).ready(function(){
                                    $("#clickme").click(function(){
                                        $("#getDIVcon").toggle();
                                        $("#Con").toggle("slow");
                                        $("#coverdiv").toggle("slow");
                                      });
                                });
                            </script>
                            
                        <!-- 机台信息弹窗（内部内容使用后台数据动态替换） -->
                            <input id="confirmShowDnStatus" type="hidden"  value="false"/>
                            <!--遮罩层-->
                            <div class="bgPop"></div>
                            <!--弹出框-->
                            <div class="pop">
                                <div class="pop-top">
                                    <h1 id="machine_name"  style="color: #000080;font-family: 华文琥珀;"></h1>
                                    <span class="pop-close" onclick="hiden_bgpop();">Ｘ</span>
                                </div>
                                <div class="pop-content">
                                    <p>
                                        <span style="float: left;">查询类型：</span> <a href="javascript:void(0)" style="float: left;" id="show_time_type_aloneMachine">实时</a> &emsp;
                                        <span style="float: left;">；当前分析基于：</span>
                                        <a style="float: left;" href="javascript:void(0)" id="show_time_quantum_aloneMachine"><font style="float: left;" id="start_time_aloneMachine">时间间隔：30分钟~</font><font style="float: left;" id="end_time_aloneMachine"></font><font id="end_time_dingshi_aloneMachine" style="display: none;float: left;"></font></a>
                                    </p>
                                    <div class="pop-content-left">
                                        <img id="machine_name_img" src="" alt="机台照片" class="teathumb" style="width: 25%;height: 30%;float:right;">
                                        <div align="center" style="width: 30%;border-left: 2px solid red;border-top: 2px solid red;border-right: 2px solid red;border-bottom: 2px solid red;float: left;font-size: 20px;height: 60px;padding-top: 15px;">设备利用率：<font id="SBLYL" style="color: green;">数据不足&emsp;</font></div>
                                        <div align="center" style="width: 20%;border-top: 2px solid red;border-right: 2px solid red;border-bottom: 2px solid red;float: left;font-size: 20px;height: 60px;padding-top: 15px;">绩效：<font id="JX" style="color: green;">数据不足&emsp;</font></div>
                                        <div align="center" style="width: 20%;border-top: 2px solid red;border-right: 2px solid red;border-bottom: 2px solid red;float: left;font-size: 20px;height: 60px;padding-top: 15px;">质量：<font id="ZL" style="color: green;">数据不足&emsp;</font></div>
                                        <div align="center" style="width: 70%;border-left: 2px solid red;border-right: 2px solid red;border-bottom: 2px solid red;float: left;font-size: 20px;height: 60px;padding-top: 15px;">瓶产量：
                                            <font id="PCL" style="font-family: 幼圆;font-size: 15px;color:#D2691E;"></font>
                                        &emsp;瓶剔除：<font  style="font-family: 幼圆;font-size: 15px;color:#D2691E;">未采集</font>
                                        &emsp;瓶损失:<font  style="font-family: 幼圆;font-size: 15px;color:#D2691E;">未采集</font>
                                        &emsp;损失时间：<font style="font-family: 幼圆;font-size: 15px;color:#D2691E;">数据不足</font></div>
                                        <div style="border-right: 2px solid #CD0000;border-bottom: 2px solid #CD0000;border-left: 2px solid #CD0000;float: left;height: 35%;width: 31%;">
                                            <table style="font-size: 12px;">
                                                <tr><th>停机分类</th><th>停机次数</th><th>停机时间</th><th>详细信息</th><th>停机时间</th></tr>
                                                <tr><td>大停机</td><td id="bigStop"></td><td id="bigTime"></td><td>MTBF</td><td id="mtbf"></td></tr>
                                                <tr><td>小停机</td><td id="smallStop"></td><td id="smallTime"></td><td>MTTR</td><td id="mttr"></td></tr>
                                                <tr><td>总计</td><td id="count"></td><td id="countTime"></td><td>MTTR大停机</td><td id="bigMttr"></td></tr>
                                            </table>
                                        </div>
                                        <div id="JTSSZT" style="height: 35%;width: 60%;float: left;">
                                        <!-- 实时动态图表样式 -->
                                        <script src="<c:url value='/operator_style/js/HighChart/highcharts.js'/>"></script>
                                        <script src="<c:url value='/operator_style/js/HighChart/exporting.js'/>"></script>
                                        <script src="<c:url value='/operator_style/js/HighChart/highcharts-zh_CN.js'/>"></script>
                                        <script>
                                            // 监听hidden值改变后的监听事件
                                            $(document).on('confirmShowDnStatus:changed', function (e, val) {
                                                var timer = null;
                                                var timer1 = null; 
                                                if(val){
                                                	  /*-------START-------实时获取当前被触发的机台故障信息------AJAX--------------*/
                                                    //参数：机台名：1,2,3,4；当前班次开始时间
                                                   var startTimeOld = $("#startTime").html().replace(/:/g, '-');
                                                   var currentTime = new Date();
                                                   var startTime_alone = currentTime.getFullYear()+"-"+(currentTime.getMonth()+1)+"-"+currentTime.getDate()+"-"+startTimeOld;
                                                   var machine_name_alone= $("#machine_name").html();
                                                   switch (machine_name_alone){
                                                   case "洗瓶机：":
                                                       machine_name_alone="1";
                                                       break;
                                                   case "灌酒机：":
                                                       machine_name_alone="2";
                                                       break;
                                                   case "杀菌机：":
                                                       machine_name_alone="3";
                                                       break;
                                                   case "贴标机--1：":
                                                       machine_name_alone="5";
                                                       break;
                                                   case "贴标机--2：":
                                                       machine_name_alone="4";
                                                       break;
                                                   default:
                                                       break;
                                                   }
                                                   setInterval(function(){
                                                       machine_name_alone = $("#machine_name").html();
                                                       switch (machine_name_alone){
                                                       case "洗瓶机：":
                                                           machine_name_alone="1";
                                                           break;
                                                       case "灌酒机：":
                                                           machine_name_alone="2";
                                                           break;
                                                       case "杀菌机：":
                                                           machine_name_alone="3";
                                                           break;
                                                       case "贴标机--1：":
                                                           machine_name_alone="5";
                                                           break;
                                                       case "贴标机--2：":
                                                           machine_name_alone="4";
                                                           break;
                                                       default:
                                                           break;
                                                       }
                                                   },2000);
                                                   $.ajax({
                                                       url: "/lineview/currentBreakDownOfAloneMachine",
                                                       type: "get",
                                                       data:  {
                                                           "startTime":startTime_alone,
                                                           "machineFlg":machine_name_alone,
                                                           "time" : new Date()
                                                       },
                                                       success: function(data) {
                                                           //传递回来的机台实时故障信息和开始时间
                                                           $("#currentBreakDownOfAloneMachine").html("<b>"+data.faultName+"</b>");
                                                           $("#currentBreakDownOfAloneMachine_startTime").html("<b>"+new Date(data.beginTime).toLocaleString()+"</b>");
                                                           $("#currentBreakDownOfAloneMachine_dutationTime").html("<b>"+(Number(new Date().getTime()-Number(data.beginTime))/(1000*60)).toFixed(1)+"分钟</b>");
                                                       }
                                                     });
                                                   timer1 =  setInterval(function(){
                                                           $.ajax({
                                                                 url: "/lineview/currentBreakDownOfAloneMachine",
                                                                 type: "get",
                                                                 data:  {
                                                                     "startTime":startTime_alone,
                                                                     "machineFlg":machine_name_alone,
                                                                     "time" : new Date()
                                                                 },
                                                                 success: function(data) {
                                                                     //传递回来的机台实时故障信息和开始时间
                                                                     $("#currentBreakDownOfAloneMachine").html("<b>"+data.faultName+"</b>");
                                                                     $("#currentBreakDownOfAloneMachine_startTime").html("<b>"+new Date(data.beginTime).toLocaleString()+"</b>");
                                                                     $("#currentBreakDownOfAloneMachine_dutationTime").html("<b>"+(Number(new Date().getTime()-Number(data.beginTime))/(1000*60)).toFixed(1)+"分钟</b>");
                                                                 }
                                                               });
                                                       },3000);
                                                    /*-------END--------实时获取当前被触发的机台故障信息--------------------*/
                                                    var machine_name= $("#machine_name").html();
                                                    switch (machine_name){
                                                    case "洗瓶机：":
                                                        machine_name="1";
                                                        break;
                                                    case "灌酒机：":
                                                        machine_name="2";
                                                        break;
                                                    case "杀菌机：":
                                                        machine_name="3";
                                                        break;
                                                    case "贴标机--1：":
                                                        machine_name="4";
                                                        break;
                                                    case "贴标机--2：":
                                                        machine_name="5";
                                                        break;
                                                    default:
                                                        break;
                                                    }
                                                    setInterval(function(){
                                                        machine_name = $("#machine_name").html();
                                                        switch (machine_name){
                                                        case "洗瓶机：":
                                                            machine_name="1";
                                                            break;
                                                        case "灌酒机：":
                                                            machine_name="2";
                                                            break;
                                                        case "杀菌机：":
                                                            machine_name="3";
                                                            break;
                                                        case "贴标机--1：":
                                                            machine_name="4";
                                                            break;
                                                        case "贴标机--2：":
                                                            machine_name="5";
                                                            break;
                                                        default:
                                                            break;
                                                        }
                                                    },3000);
                                    /* ---------------START------------------------单机台指标信息---------------------------------- */
                                                    var startTime=$("#startTime").html();//当前班次开始时间
                                                    var countTime = $("#countTime").html();//大小停机总时间
                                                        //获取相应的绩效数据等
                                                        $.ajax({
                                                          url: "/lineview/machineCalculationAction",
                                                          type: "get",
                                                          data:  {
                                                              "startTime":startTime,
                                                              "countTime":countTime,
                                                              "machineName":machine_name,
                                                              "time" : new Date()
                                                          },
                                                          success: function(data) {
                                                              $("#SBLYL").html(data.equipmentUR+" %");
                                                              $("#JX").html(data.performance+" %");
                                                          }
                                                        });
                                    /* ---------------END------------------------单机台指标信息---------------------------------- */
                                        /***--------------------------图表模块------------------------------***/                                                        
                                                        //实时动态图表样式
                                                        Highcharts.setOptions({
                                                            global: {
                                                                useUTC: false
                                                            }
                                                        });
                                                    function activeLastPointToolip(chart) {
                                                        var points = chart.series[0].points;
                                                        chart.tooltip.refresh(points[points.length -1]);
                                                    }
                                                    $('#JTSSZT').highcharts({
                                                        chart: {
                                                            type: 'spline',
                                                            animation: Highcharts.svg, // 老版IE不支持
                                                            marginRight: 10,
                                                            events: {
                                                                load: function () {
                                                                    // 每秒循环
                                                                    var series = this.series[0],
                                                                        chart = this;
                                                                    timer = setInterval(function () {
                                                                        var x = (new Date()).getTime(); // current time
                                                                            //此处为采集的数据
                                                            /* --------START-------------实时数据 一条 循环执行------------------------------- */
                                                                        $.get("/lineview/a/currentStatus", {"machineFlg":machine_name,"time":new Date()},function(currentdata){
                                                                            var y = currentdata;
                                                                            series.addPoint([x, y], true, true);
                                                                            activeLastPointToolip(chart);
                                                                        });
                                                            /* -----------END----------实时数据 一条 循环执行-------------------------------*/
                                                                    }, 2500);
                                                                }
                                                            }
                                                        },
                                                        title: {
                                                            text: '机台实时运行状态'
                                                        },
                                                        xAxis: {
                                                            type: 'datetime',
                                                            tickPixelInterval: 200
                                                        },
                                                        yAxis: {
                                                            title: {
                                                                text: '状态值'
                                                            },
                                                            plotLines: [{
                                                                value: 0,
                                                                width: 1,
                                                                color: '#9ACD32'
                                                            }]
                                                        },
                                                        tooltip: {
                                                            formatter: function () {
                                                                var status="";
                                                                if(this.y==1){
                                                                    status = "正常";
                                                                }else{
                                                                    status = "停机";
                                                                }
                                                                return '<b>' + this.series.name + '</b><br/>' +
                                                                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                                                                    '<br/>'+status;
                                                            }
                                                        },
                                                        legend: {
                                                            enabled: false
                                                        },
                                                        exporting: {
                                                            enabled: false
                                                        },
                                                        series: [{
                                                            name: '当前机器状态',
                                                            data: (function () {
                                                                // generate an array of random data
                                                                var data = [],
                                                                    time = (new Date()).getTime(),
                                                                    i;
/* ----------------------------START----------------------动态图表请求 实时状态初始化----------------------------------------------------- */
                                                                  $.ajax({
                                                                     url: "/lineview/a/initData",
                                                                     type: "post",
                                                                     data: {"machineFlg": machine_name},
                                                                     async:false,
                                                                     success: function(backdata) {
                                                                         //响应成功，请求次数加1
                                                                        /*  times++; */
                                                                            for (i = -(backdata.length-1); i <= 0; i += 1) {
                                                                                    data.push({
                                                                                        x: time + i * 5000,
                                                                                        //此处为初始化的数据
                                                                                        y: backdata[(i+(backdata.length-1))],
                                                                                    });
                                                                            }
                                                                     }
                                                                    });
/* ----------------------------END----------------------实时动态图表请求 数据初始化----------------------------------------------------- */
                                                                return data;
                                                            }()),
                                                            zones: [{
                                                                value: 0,
                                                                color: '#CD0000',
                                                            }, {
                                                                value: 0.5,
                                                                color: '#CD0000'
                                                            },{
                                                                color: '#7CFC00'
                                                            }]
                                                        }]
                                                    }, function(c) {
                                                        activeLastPointToolip(c);
                                                    });
                                        /***--------------------------图表模块------------------------------***/        
                                                }else{
                                                    //不走循环请求则请求次数减1
                                                    times--;
                                                    clearInterval(timer);
                                                    clearInterval(timer1);
                                                }
                                            });
                                        </script>
                                        </div>
                                        <br/>
                                        <div style="float:left;width: 20%;border:2px #CD0000 solid;margin-top: 30px;">
                                            <table style="font-size: 12px;">
                                                <tr><td>平均速度</td><td id="avg"></td></tr>
                                                <tr><td>额定速度</td><td id="rated"></td></tr>
                                            </table>
                                        </div>
                                        <div id="JTSSSCXL1" style="height: 35%;width: 73%;float: left;">
                                            <script>
                                            //实时动态图表样式
                                                Highcharts.setOptions({
                                                global: {
                                                    useUTC: false
                                                }
                                            });
                                            function activeLastPointToolip(chart) {
                                                var points = chart.series[0].points;
                                                chart.tooltip.refresh(points[points.length -1]);
                                            }
                                            $('#JTSSSCXL1').highcharts({
                                                chart: {
                                                    type: 'spline',
                                                    animation: Highcharts.svg, // 老版的IE不让动
                                                    marginRight: 10,
                                                    events: {
                                                        load: function () {
                                                            // 设置每秒更新图表
                                                            var series = this.series[0],
                                                                chart = this;
                                                            setInterval(function () {
                                                                var x = (new Date()).getTime(), // 当前时间
                                                                    y = 1;
                                                                    series.addPoint([x, y], true, true);
                                                                    activeLastPointToolip(chart);
                                                            }, 2500);
                                                        }
                                                    }
                                                },
                                                title: {
                                                    text: '机台实时生产效率'
                                                },
                                                xAxis: {
                                                    type: 'datetime',
                                                    tickPixelInterval: 150
                                                },
                                                yAxis: {
                                                    title: {
                                                        text: '生产效率'
                                                    },
                                                    plotLines: [{
                                                        value: 0,
                                                        width: 1,
                                                        color: '#101010'
                                                    }]
                                                },
                                                tooltip: {
                                                    formatter: function () {
                                                        return '<b>' + this.series.name + '</b><br/>' +
                                                            Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                                                            Highcharts.numberFormat(this.y, 2);
                                                    }
                                                },
                                                legend: {
                                                    enabled: false
                                                },
                                                exporting: {
                                                    enabled: false
                                                },
                                                series: [{
                                                    name: '当前生产效率',
                                                    data: (function () {
                                                        // generate an array of random data
                                                        var data = [],
                                                            time = (new Date()).getTime(),
                                                            i;
                                                        for (i = -60; i <= 0; i += 1) {
                                                            data.push({
                                                                x: time + i * 1000,
                                                                //此处为初始化的数据
                                                                y: 1,
                                                            });
                                                        }
                                                        return data;
                                                    }())
                                                }]
                                            }, function(c) {
                                                activeLastPointToolip(c);
                                            });
                                        </script>
                                        </div>
                                        <!-- 故障信息滚动 -->
                                        <div class="Top_Record">
                                            <div class="record_Top"><p>机台故障信息</p></div>
                                            <div class="topRec_List">
                                                  <dl>
                                                    <dd>故障信息</dd>
                                                    <dd>次数</dd>
                                                </dl>
                                                <!-- Ajax请求响应回来的数据添加记录 -->
                                                <div class="maquee">
                                                    <ul id="faultName">
                                                    </ul>
                                                </div>
                                            </div>
                                          </div>
                                        <!-- /滚动字幕 -->
                                        <script type="text/javascript"> 
                                              function autoScroll(obj){  
                                                    $(obj).find("ul").animate({  
                                                        marginTop : "-39px"  
                                                    },500,function(){  
                                                        $(this).css({marginTop : "0px"}).find("li:first").appendTo(this);  
                                                    });
                                                }  
                                                $(function(){  
                                                    setInterval('autoScroll(".maquee")',2000);
                                                      
                                                });
                                        </script> 
                                        <div style="float:left;width: 31%;;margin-left: 10px;font-size: 15px;margin-top: 5%;">
                                            <!-- START 当前触发故障信息 -->
                                            <span style="font-size: 15px;font-family: 幼圆;color:#8B4513"><b>当前触发故障：</b></span>
                                            <span id="currentBreakDownOfAloneMachine" style="font-size: 15px;font-family: 微软雅黑;color:#FF0000"><b></b></span><br/>
                                            <span style="font-size: 15px;font-family: 幼圆;color:#8B4513"><b>开&ensp;始&emsp;时&ensp;间：</b></span>
                                            <span id="currentBreakDownOfAloneMachine_startTime" style="font-size: 15px;font-family: 微软雅黑;color:#FF0000"><b></b></span><br />
                                            <span  style="font-size: 15px; font-family: 幼圆; color: #8B4513"><b>持&ensp;续&emsp;时&ensp;间：</b></span>
                                            <span id="currentBreakDownOfAloneMachine_dutationTime" style="font-size: 15px; font-family: 微软雅黑; color: #FF0000"></span><br/>
                                            <br/>
                                            <!-- END 当前触发故障信息 -->
                                            <button id="showAllMsg" onclick="jumpAllMsg(this);" value="">查看所有故障信息</button>
                                            <script type="text/javascript">
                                                function jumpAllMsg(obj){
                                                    var machineName = obj.value;
                                                    switch (machineName) {
                                                    case "洗瓶机：":
                                                        machineName="XPJ";
                                                        break;
                                                    case "灌酒机：":
                                                        machineName="GJJ";
                                                        break;
                                                    case "杀菌机：":
                                                        machineName="SJJ";
                                                        break;
                                                    case "贴标机--1：":
                                                        machineName="TBJ-1";
                                                        break;
                                                    case "贴标机--2：":
                                                        machineName="TBJ-2";
                                                        break;
                                                    default:
                                                        break;
                                                    }
                                                    //页面跳转，带着参数
                                                    window.open("/lineview/show_all_breakdown.jsp?machineName="+machineName,"newwindow","top=50,left=100,width=1200,height=600,modal=yes,location=no,resizable=1, menuBar=0, toolBar=0, scrollbars=yes, Status=yes, resizable=1");
                                                }
                                            </script>
                                        </div>
                                    </div>
                                    <div class="pop-content-right"></div>
                                </div>
                            </div>
                            
                            <script>
                                function hiden_bgpop(){
                                    $('.bgPop,.pop').hide("200");
                                    var flag = false;
                                    $("#confirmShowDnStatus").val(flag);
                                    $(document).trigger("confirmShowDnStatus:changed",flag);
                                }
                                function show_pop(machine_name,flag){
                                    //直接赋值
                                    $("#machine_name").html(machine_name);
                                    /* document.getElementById("machine_name").innerHTML=machine_name; */
                                    document.getElementById("showAllMsg").value=machine_name;
                                    //-------------
                                    //发送获得平均速度请求
                                    var startTime=$("#startTime").html();
                                    var machine_name=machine_name.split("：")[0];
                                    var m_name="";
                                    //机台图片
                                    var imgUrl = "";
                                    switch (machine_name){
                                    case "洗瓶机":
                                        m_name="1";
                                        imgUrl = "<c:url value='/images/xpj.jpg'/>";
                                        break;
                                    case "灌酒机":
                                           m_name="2";
                                           imgUrl = "<c:url value='/images/gjj.jpg'/>";
                                        break;
                                    case "杀菌机":
                                        m_name="3";
                                        imgUrl = "<c:url value='/images/xjj.jpg'/>";
                                        break;
                                    case "贴标机--1":
                                        m_name="4";
                                        imgUrl = "<c:url value='/images/tbj.jpg'/>";
                                        break;
                                    case "贴标机--2":
                                        m_name="5";
                                        imgUrl = "<c:url value='/images/tbj.jpg'/>";
                                        break;
                                    default:
                                        break;
                                    }
                                    if(""==startTime){
                                        //未选择班组，参数不足，无法提交
                                        swal({
                                            type : 'error',
                                            title : '错误！',
                                            html : '时间: ' + new Date().toLocaleString()+'<br/>'
                                                    +'未选择班组，无进入单机台模式，请选择之后继续操作！',
                                            showCancelButton : true,
                                            showConfirmButton : false,
                                            cancelButtonText : '关闭'
                                        });
                                    }else{
                                        $('.bgPop,.pop').show("200");
                                        //机台图片显示
                                        $("#machine_name_img").attr("src",imgUrl);
                                        $.post("/lineview/getStatusOfMachine", { "machineFlg": m_name },function(data){
                                            //平均速度
                                            var avg=parseInt(data.averageSpeed);
                                            var s_startTime = startTime.split(":")[0];
                                            var endTime = new Date().getHours();
                                            var timeLength = endTime-s_startTime;
                                            var PCL = avg*timeLength;
                                            $("#PCL").html(PCL+" 瓶");
                                            //额定速度
                                            var rated=data.ratedSpeed;
                                            $("#avg").html(avg+":瓶/时");
                                            $("#rated").html(rated+":瓶/时");
                                            //机台故障信息
                                            for(var i=0;i<data.faultName.length;i++){
                                                //只显示次数不等于0的故障信息
                                                if(data.faultTimes[i]!="0"){
                                                    $("#faultName").append( "<li><div>"+data.faultName[i]+"</div><div>"+data.faultTimes[i]+"</div></li>");
                                                }
                                            }
                                            //计算总次数
                                            var count=data.stopInfomation.timesOfBigStop+data.stopInfomation.timesOfSmallStop;
                                            //总计时间
                                            var time1=data.stopInfomation.durationOfBigStop+data.stopInfomation.durationOfSmallStop;
                                            //大停机次数
                                            $("#bigStop").html(parseInt(data.stopInfomation.timesOfBigStop));
                                            //大停机时间
                                            $("#bigTime").html(parseInt(data.stopInfomation.durationOfBigStop)+"分钟");
                                            //小停机次数
                                            $("#smallStop").html(parseInt(data.stopInfomation.timesOfSmallStop));
                                            //小停机时间
                                            $("#smallTime").html(parseInt(data.stopInfomation.durationOfSmallStop)+"分钟");
                                            //总计次数
                                            $("#count").html(count);
                                            //总计时间
                                            $("#countTime").html(parseInt(time1)+"分钟");
                                            //计算MTBF
                                        // var mtbf=((new Date().getTime())/1000*60)-time1)/(count-0);
                                        //    $("#mtbf").html(mtbf);
                                            //计算MTTR
                                            var mttr=parseInt((time1)/(count-0));
                                            $("#mttr").html(mttr +"分钟");
                                            //总计mttr大停机时间
                                            var countMttr=time1;
                                            $("#bigMttr").html(countMttr +"分钟"); 
                                        });
                                        
                                        //设置超时定时器
                                        setTimeout(function(){
                                            $.post("/lineview/getStatusOfMachine", { "machineFlg": m_name },function(data){
                                            //平均速度
                                            var avg=parseInt(data.averageSpeed);
                                            var s_startTime = startTime.split(":")[0];
                                            var endTime = new Date().getHours();
                                            var timeLength = endTime-s_startTime;
                                            var PCL = avg*timeLength;
                                            $("#PCL").html(PCL+" 瓶");
                                            //额定速度
                                            var rated=data.ratedSpeed;
                                            $("#avg").html(avg+":瓶/时");
                                            $("#rated").html(rated+":瓶/时");
                                            //机台故障信息
                                            //每次点击之后清空之前的内容
                                            $("#faultName").empty();
                                            for(var i=0;i<data.faultName.length;i++){
                                                //只显示次数不等于0的故障信息
                                                if(data.faultTimes[i]!="0"){
                                                    $("#faultName").append( "<li><div>"+data.faultName[i]+"</div><div>"+data.faultTimes[i]+"</div></li>");
                                                }
                                            }
                                            //计算总次数
                                            var count=data.stopInfomation.timesOfBigStop+data.stopInfomation.timesOfSmallStop;
                                            //总计时间
                                            var time1=data.stopInfomation.durationOfBigStop+data.stopInfomation.durationOfSmallStop;
                                            //大停机次数
                                            $("#bigStop").html(parseInt(data.stopInfomation.timesOfBigStop));
                                            //大停机时间
                                            $("#bigTime").html(parseInt(data.stopInfomation.durationOfBigStop)+"分钟");
                                            //小停机次数
                                            $("#smallStop").html(parseInt(data.stopInfomation.timesOfSmallStop));
                                            //小停机时间
                                            $("#smallTime").html(parseInt(data.stopInfomation.durationOfSmallStop)+"分钟");
                                            //总计次数
                                            $("#count").html(count);
                                            //总计时间
                                            $("#countTime").html(parseInt(time1)+"分钟");
                                            //计算MTBF
                                        // var mtbf=((new Date().getTime())/1000*60)-time1)/(count-0);
                                        //    $("#mtbf").html(mtbf);
                                            //计算MTTR
                                            var mttr=parseInt((time1)/(count-0));
                                            $("#mttr").html(mttr +"分钟");
                                            //总计mttr大停机时间
                                            var countMttr=time1;
                                            $("#bigMttr").html(countMttr +"分钟"); 
                                        });
                                        },5000);
                                        
                                        $("#confirmShowDnStatus").val(flag);
                                        //定义hidden改变的触发自定义事件
                                        $(document).trigger("confirmShowDnStatus:changed",flag);
                                    }
                                }
                            </script>
                            <!-- //机台信息弹窗 -->
                            <!-- //图片区域点击Map -->
                            <!-- 区域匹配DIV -->
                            <div id="getDIVcon">
                            <!-- 四大机台     绿色：#00FF00 ；红色：red ；黄色：yellow -->
                                <!-- 洗瓶机 -->
                                <div id="XPJ_D"  style="border:10px solid RED;width: 9%;height: 14%;margin-left: 23.7%;margin-top: 17.6%;"></div>
                                <!-- 灌酒机 -->
                                <div id="GJJ_D" style="border:10px solid RED;width: 11%;height: 14.8%;border-radius:50%;margin-left: 22.6%;margin-top: 14.2%;"></div>
                                <!-- 杀菌机 -->
                                <div id="SJJ_D" style="border:10px solid RED;width: 10.8%;height: 14%;margin-left: 57%;margin-top: -6.8%;"></div>
                                <!-- 贴标机A -->
                                <div id="TBJ_A_D" style="width: 6.5%;height: 8%;border-radius:50%;margin-left: 59.3%;margin-top: -19.8%;"></div>
                                <!-- 贴标机B -->
                                <div id="TBJ_B_D" style="border:10px solid RED;width: 6.5%;height: 8%;border-radius:50%;margin-left: 68.5%;margin-top: -1.8%;"></div>
                            <!-- //四大机台 -->
                            <!-- 动力设备（电机） -->
                                <!-- 电机2-8 -->
                                <div id="M2_8"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 43.2%;margin-top: -9%;"></div>
                                <!-- 电机2-10 -->
                                <div id="M2_10"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 43.2%;margin-top: 0.5%;"></div>
                                <!-- 电机2-12 -->
                                <div id="M2_12"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 43.2%;margin-top: 1%;"></div>
                                <!-- 电机2-11 -->
                                <div id="M2_11"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 43.2%;margin-top: 0.75%;"></div>
                                <!-- 电机2-17 -->
                                <div id="M2_17"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 40.8%;margin-top: -5.1%;"></div>
                                <!-- 电机2-16 -->
                                <div id="M2_16"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 40.8%;margin-top: 1%;"></div>
                                <!-- 电机2-15 -->
                                <div id="M2_15"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 40.8%;margin-top: 0.75%;"></div>
                                <!-- 电机2-14 -->
                                <div id="M2_14"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 41.6%;margin-top: 0.98%;"></div>
                                <!-- 电机2-18 -->
                                <div id="M2_18"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 31.5%;margin-top: -8.8%;"></div>
                                <!-- 电机3-19 -->
                                <div id="M3_19"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 21.8%;margin-top: 0.05%;"></div>
                                <!-- 电机3-21 -->
                                <div id="M3_21"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 21.8%;margin-top: 0.1%;"></div>
                                <!-- 电机3-22 -->
                                <div id="M3_22"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 21.8%;margin-top: 0.1%;"></div> 
                                <!-- 电机3-20 -->
                                <div id="M3_20"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 23.8%;margin-top: -3.45%;"></div>
                                <!-- 电机M1+1 -->
                                <div id="M1+1"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 23.7%;margin-top: 1.2%;"></div>
                                
                                <!-- 电机M23 -->
                                <div id="M23"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 27.5%;;margin-top: 2.9%;"></div>
                                <!-- 电机M25 -->
                                <div id="M25"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 27.5%;margin-top: 1%;"></div>
                                <!-- 电机M24 -->
                                <div id="M24"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 38.9%;margin-top: -1.2%;"></div>
                                
                                <!-- 电机M26 -->
                                <div id="M26"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 21.7%;margin-top: 0.7%;"></div>
                                <!-- 电机3-H5 -->
                                <div id="3_H5"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 25.6%;margin-top: 9.8%;"></div>
                                <!-- 电机3-H6 -->
                                <div id="3_H6"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 44.6%;margin-top: -1.1%;"></div>
                                <!-- 电机2-29 -->
                                <div id="M2_29"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 48.2%;margin-top: -1.1%;"></div>
                                <!-- 电机2-30 -->
                                <div id="M2_30"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 50.5%;margin-top: -2.8%;"></div>
                                <!-- 电机2-31 -->
                                <div id="M2_31"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 50.5%;margin-top: -2.9%;"></div>
                                <!-- 电机2-33 -->
                                <div id="M2_33"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 50.3%;margin-top: -7.5%;"></div>
                                <!-- 电机2-32 -->
                                <div id="M2_32"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 52.5%;margin-top: 0.1%;"></div>
                                
                                <!-- 电机4-36 -->
                                <div id="M4_36"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 70.5%;margin-top: 9%;"></div>
                                <!-- 电机4-37 -->
                                <div id="M4_37"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 70.5%;margin-top: -3.5%;"></div>
                                
                                <!-- 电机4-42 -->
                                <div id="M4_42"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 73.8%;margin-top: -2%;"></div>
                                <!-- 电机4-43 -->
                                <div id="M4_43"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 73.8%;margin-top: -2.5%;"></div>
                                <!-- 电机4-44 -->
                                <div id="M4_44"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 73.8%;margin-top: -2.7%;"></div>
                                <!-- 电机4-40 -->
                                <div id="M4_40"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 76.5%;margin-top: -1.2%;"></div>
                                <!-- 电机4-39 -->
                                <div id="M4_39"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 76.5%;margin-top: 0.1%;"></div>
                                <!-- 电机4-38 -->
                                <div id="M4_38"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 76.3%;margin-top: 0.5%;"></div>
                                
                                <!-- 电机4-49 -->
                                <div id="M4_49"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 69.8%;margin-top: -11.2%;"></div>
                                <!-- 电机4-45 -->
                                <div id="M4_45"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 57.5%;margin-top: -1.2%;"></div>
                                
                                <!-- 电机4-41 -->
                                <div id="M4_41"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 45.7%;margin-top: -2.5%;"></div>
                                <!-- 电机4-46 -->
                                <div id="M4_46"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 45.7%;margin-top: -4.4%;"></div>
                                <!-- 电机4-47 -->
                                <div id="M4_47"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 45.7%;margin-top: -3.4%;"></div>
                                <!-- 电机4-48 -->
                                <div id="M4_48"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 48.2%;margin-top: -5.8%;"></div>
                                
                                <!-- 电机6-1 -->
                                <div id="M6_1"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 53.1%;margin-top: 0.5%;"></div>
                                <!-- 电机6-2 -->
                                <div id="M6_2"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 53.1%;margin-top: -0.1%;"></div>
                                <!-- 电机6-3 -->
                                <div id="M6_3"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 53.1%;margin-top: -0.1%;"></div>
                                <!-- 电机6-4 -->
                                <div id="M6_4"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 53.1%;margin-top: 0.1%;"></div>
                                <!-- 电机6-5 -->
                                <div id="M6_5"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 53.1%;margin-top: 0.1%;"></div>
                                
                                <!-- 电机5-1 -->
                                <div id="M5_1"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 51.7%;margin-top: -3.6%;"></div>
                                <!-- 电机5-2 -->
                                <div id="M5_2"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 51.7%;margin-top: 0.1%;"></div>
                                <!-- 电机5-3 -->
                                <div id="M5_3"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 51.7%;margin-top: 0.1%;"></div>
                                <!-- 电机5-4 -->
                                <div id="M5_4"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 51.7%;margin-top: -0.1%;"></div>
                                <!-- 电机5-5 -->
                                <div id="M5_5"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 51.7%;margin-top: 1.6%;"></div>
                                <!-- 电机6-6 -->
                                <div id="M6_6"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 53.8%;margin-top: -3.4%;"></div>
                                <!-- 电机6-7 -->
                                <div id="M6_7_"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 61.9%;margin-top: -1.1%;"></div>
                                <!-- 电机5-6 -->
                                <div id="M5_6"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 60.5%;margin-top: 1%;"></div>
                                <!-- 电机5-7 -->
                                <div id="M5_7"  style="width: 16px;height: 12px;border-radius:50%;margin-left: 69.9%;margin-top: -1%;"></div>
                            <!-- //动力设备（电机） -->
                            <!-- //区域匹配DIV -->
                            </div>
                            </div>
                            <div class="graph-container"></div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!---//中心页面动态图表实现---->


                <div class="content-mid">
                    <div class="col-md-7 mid-content-top">
                        <!--//sreen-gallery-cursual---->
                        <!-- requried-jsfiles-for owl -->
                        <link href="<c:url value='/operator_style/css/owl.carousel.css'/>" rel="stylesheet" />
                        <script src="<c:url value='/operator_style/js/owl.carousel.js'/>"></script>
                        <script>
                            $(document).ready(function() {
                                $("#owl-demo").owlCarousel({
                                    items : 3,
                                    lazyLoad : true,
                                    autoPlay : true,
                                    pagination : true,
                                    nav : true,
                                });
                            });
                        </script>
                        <!-- //requried-jsfiles-for owl -->
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!----->
                <div style="background-color:#F2F2F2;height: 2%;"> </div>
                <div class="content-bottom">
                    <div class="copy">
                        <p>
                            &copy; 2017 MRONG. 版权所有：济南铭容科技
                        </p>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <!--//content-->
        </div>
    <!---->
    <!--滚动 js-->
    <script src="<c:url value='/operator_style/js/scripts.js'/>"></script>
    <!--//滚动 js-->
    <script src="<c:url value='/operator_style/js/bootstrap.min.js'/>"></script>
</body>
</html>