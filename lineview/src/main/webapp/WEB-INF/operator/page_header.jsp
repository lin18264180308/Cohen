<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="page_tool_file.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
    $(function() {
        //点击班组选择按钮事件
        $("#ClickMeBan").click(function() {
            var url = "a/teamSelection.action";
            //先声明一维
            var shifts = new Array(4);
            //声明二维，每一个一维数组里面的一个元素都是一个数组； 
            for ( var k = 0; k < 4; k++) {
                shifts[k] = new Array();
            }

            $.post(url, {"time" : new Date()},function(data) {
                var newdate = new Date();
                for ( var i = 0; i < data.shifts.length; i++) {
                    shifts[i][0] = data.shifts[i].id;
                    shifts[i][1] = data.shifts[i].startTime;
                    shifts[i][2] = data.shifts[i].stopTime;
                    shifts[i][3] = data.shifts[i].shiftDescribe;
                    var startTime = shifts[i][1]
                            .split(":")[0];
                    var stopTime = shifts[i][2]
                            .split(":")[0];
                    if (startTime <= newdate
                            .getHours()
                            && newdate
                                    .getHours() < stopTime) {
                        //如果和当前时间段一样则加入到页面中
                        $("#shiftid").val(
                                shifts[i][0]);
                        $("#startTime").html(
                                shifts[i][1]);
                        $("#stopTime").html(
                                shifts[i][2]);
                        $("#shiftDescribe")
                                .html(
                                        shifts[i][3]);
                    }
                }

                //循环添加进班组
                $("#banzhu_choose").children(
                        'option').remove();
                $("#banzhu_choose").append(
                        "<option value='Value'>"
                                + "请选择"
                                + "</option>");
                for ( var y = 0; y < data.teams.length; y++) {
                    $("#banzhu_choose").append("<option value='Value'>" + data.teams[y] + "</option>");
                }
            });
        });
        //班组保存按钮
        $("#banzhu_savebt").click(
            function() {
                //班组id
                var textt = $("#banzhu_choose").find("option:selected").text();
                //班次
                var shiftid = $("#shiftid").val();
                if ("请选择" == textt) {
                    swal('警告!', '未选择班组，请选择后重新修改!', 'warning');
                    $('#code').hide();
                    $('#goodcover').hide();
                } else {
                    var u = "a/teamSave.action?shiftid=" + shiftid + "&teamid=" + textt;
                    $.post(u, {"time" : new Date()}, function() {
                        swal('成功!', '班组已经成功修改!', 'success');
                        $('#code').hide();
                        $('#goodcover').hide();
                        $("#show_banci").html($("#shiftDescribe").text());
                        $("#show_banzu").html(textt + "组");
                    });
                }
            });

        var modeName = "请选择";
        //点击生产模式事件
        $('#ClickMeMo').click(function() {
            //定义ajax请求地址
            var url = "a/modeChangeGet.action";
            //发送请求，回调方法
            // $("#production_variety").remove();
            // $("#production_variety").append("<option value='Value'>" + "请选择" + "</option>");
            $.post(url, {'time' : new Date()}, function(data) {
            	$("#machine_models").empty();
                //遍历数据的模式名称加入到select标签中
                $("#machine_models").append( "<option>请选择</option>")
                for ( var i = 0; i < data.length; i++) {
                    //alert(data.modes[i].patternClasses);
                    $("#machine_models").append("<option value='"+data[i].patternClasses+"'>" + data[i].modeName + "</option>");
                }
            });
        });

        // 全局默认modeClass属性
        $("#production_variety").attr("disabled", true).css("background-color", "#E0EEE0");

        // 选择模式触发事件
        $("#machine_models").change(function() {
            // 获取当前被选中的标签
            var text = $("#machine_models").find("option:selected").text();
            //判断是否为生产模式
            if (text == "生产模式") {
                $.get("a/getVarietie.action",{"time":new Date()},function(data){
                    $("#production_variety").empty();
                    $("#production_variety").append( "<option>请选择</option>")
                    for ( var i = 0; i < data.length; i++) {
                        $("#production_variety").append(
                                "<option value='"+data[i]+"'>"
                                        + data[i]
                                        + "</option>");
                    }
                });
                $("#production_variety").attr("disabled", false);
                // 设置背景色为白色
                $("#production_variety").css("background-color",
                        "#FFFFFF");
            } else {
                $("#production_variety").attr("disabled", true).css(
                        "background-color", "#E0EEE0");
                // 不是生产模式，modeClass标签回归到初始标签
                $("#production_variety").empty();
                $("#production_variety").append("<option value='Value'>" + "请选择" + "</option>");
                $('#production_variety').prop('selectedIndex', 0);
                // alert($("#modeClass").find("option:selected").text());
                // var testdd=$("#modeClass").find("option:selected").text();    
            }
        });
        //模式保存按钮
        $("#closebtMoWileClick")
                .click(
                        function() {
                            var opertor = $("#userName").val();
                            modeName = $("#machine_models").find(
                                    "option:selected").text();
                            modeClass = $("#production_variety").find(
                                    "option:selected").text();
                            var patternClasses=$("#machine_models").val();
                            // alert("patternClasses"+patternClasses);
                            if ("请选择" == modeName) {
                                swal('失败!', '请填写模式名称！', 'error');
                                $("#goodcoverMo").hide();
                                $("#codeMo").hide();
                            } else if ("生产模式" == modeName) {
                                if ("请选择" == modeClass) {
                                    swal('失败!', '请填写品种！', 'error');
                                    $("#goodcoverMo").hide();
                                    $("#codeMo").hide();
                                } else {
                                    //生产模式下所有属性都填上，执行的操作
                                    var url1 = "a/modeChange.action";
                                    var args = {
                                            "modeRecord.operator":opertor,
                                            "modeRecord.modeName":modeName,
                                            "modeRecord.modeClass":modeClass,
                                            "modeRecord.patternClasses":patternClasses,
                                            "time":new Date()
                                    }
                                    var banzuid=$("#show_banzu").html().split("组")[0];
                                    //$.post("a/halt-haltRecord.action?teamid="+banzuid,{"time":new Date()},function(){});
                                    $.post(url1, args, function() {
                                    });
                                    swal('成功!', '模式已经成功切换!', 'success');
                                    $("#show_models").text(modeName);
                                    $("#show_variety").text(modeClass);
                                    $("#goodcoverMo").hide();
                                    $("#codeMo").hide();
                                }
                            } else {
                                //非生产模式下保存
                                var url1 = "a/modeChange.action?modeRecord.operator="
                                        + opertor
                                        + "&modeRecord.modeName="
                                        + modeName
                                        + "&modeRecord.modeClass="
                                        + modeClass
                                        +"&modeRecord.patternClasses="+patternClasses;
                                var banzuid=$("#show_banzu").html().split("组")[0];
                                //$.post("a/halt-haltRecord.action?teamid="+banzuid,{"time":new Date()},function(){});
                                $.post(url1, {"time" : new Date()}, function() {

                                });
                                $("#show_models").text(modeName);
                                $("#show_variety").text("无");
                                $("#goodcoverMo").hide();
                                $("#codeMo").hide();
                            }

                        });
        //班组重置
        $("#teamReset").click(function() {
            $('#team').prop('selectedIndex', 0);
        });

    });
    //模式重置
    function resetMO(){
            document.getElementById("machine_models").children[0].selected=true;
            document.getElementById("production_variety").children[0].selected=true;
            document.getElementById("production_variety").disabled=true;
    }
</script>
</head>
<body onload="startclock()">
    <input type="text" id="userName" value="${session.userName }"
        hidden="hidden"></input>
    <input type="text" id="shiftid" hidden="hidden" value=""></input>
    <!-- 导航栏 -->
    <nav class="navbar-default navbar-static-top" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
            data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
            <span class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <h1 style="margin-top: 0px;">
            <a class="navbar-brand" href="<c:url value='/smart_packaging.jsp'/>">生产线智能包装</a>
        </h1>
    </div>
    <div class=" border-bottom">
        <div class="full-left">
            <section class="full-top">
            <button id="toggle">
                <i class="fa fa-arrows-alt"></i>
            </button>
            </section>
            &emsp;
            <button type="button" id="ClickMeBan">班组选择</button>
            <button type="button" id="ClickMeMo">模式切换</button>
            <button type="button" id="ClickMeTime">时间选择</button>
            <div class="clearfix"></div>
        </div>
    </div>
    <!-- 班组选择弹窗覆盖 -->
    <div id="goodcover"></div>
    <div id="code">
        <div class="close1">
            <a href="javascript:void(0)" id="closebt"><img
                src="<c:url value='/operator_style/images/close.gif'/>"></a>
        </div>
        <br>
        <br>
        <br>
        <div class="goodtxt">
            <!-- 
               当前时间UserBean对象实例化
                     <jsp:useBean id="time" class="java.util.Date">  
                        <jsp:setProperty name="time" property="hours" param="hh"/>  
                        <jsp:setProperty name="time" property="minutes" param="mm"/>  
                        <jsp:setProperty name="time" property="seconds" param="ss"/>  
                    </jsp:useBean>  
              /当前时间UserBean对象实例化
          -->
            <center>
                <h1 style="color: #CD0000; font-family: 微软雅黑;">班组选择</h1>
                <hr>
            </center>
            <p>
                <font style="float: left; margin-left: 100px;">班&emsp;&emsp;次：</font>
                <span id="startTime"></span>-- <span id="stopTime"></span> <br>
                <font style="float: left; margin-left: 100px;">班次描述：</font> <span
                    id="shiftDescribe"></span> <br> <font
                    style="float: left; margin-left: 100px;">班&emsp;&emsp;组：</font> <select
                    style="width: 160px;" id="banzhu_choose">


                </select>
            </p>
            <br>
            <br>
            <br>
            <br>
            <!-- 提交操作与取消操作 -->
            <button style="font-size: 10px;" id="banzhu_savebt">保存</button>
            &emsp;&emsp;
            <button style="font-size: 10px;" id="banzhu_resetbt">取消</button>
        </div>
    </div>
    <!-- /班组选择弹窗覆盖 --> <!-- 模式切换弹窗覆盖 -->
    <div id="goodcoverMo"></div>
    <div id="codeMo">
        <div class="close1Mo">
            <a href="javascript:void(0)" id="closebtMo"><img
                src="<c:url value='/operator_style/images/close.gif'/>"></a>
        </div>
        <br>
        <br>
        <br>
        <div class="goodtxtMo">
            <center>
                <h1 style="color: #CD0000; font-family: 微软雅黑;">模式切换</h1>
                <hr>
            </center>
            <p>
                
                <font style="float: left; margin-left: 100px;">模式名称：</font>
                <select style="width: 300px;" id="machine_models">
                    <option value="0">请选择</option>
                </select> 
                <br> <font style="float: left; margin-left: 100px;">品&emsp;&emsp;种：</font>
                <select style="width: 300px;" id="production_variety">
                    
                </select>
            </p>
            <br>
            <br>
            <br>
            <br>
            <!-- 提交操作与取消操作 -->
            <button style="font-size: 10px;" id="closebtMoWileClick">保存</button>
            &emsp;&emsp;
            <button style="font-size: 10px;" id="modelcancle" onclick="resetMO();">重置</button>
        </div>
    </div>
    <!-- /模式切换覆盖 --> <!-- 时间选择弹窗覆盖 -->
    <div id="goodcoverTime"></div>
    <div id="codeTime">
        <div class="close1Time">
            <a href="javascript:void(0)" id="closebtTime"><img
                src="<c:url value='/operator_style/images/close.gif'/>"></a>
        </div>
        <br>
        <br>
        <br>
        <div class="goodtxtTime">
            <center>
                <h1 style="color: #CD0000; font-family: 微软雅黑;">时间选择</h1>
                <span id="responseIsTrue" hidden></span>
                <hr>
            </center>
            <p>
                <!-- 选择操作 -->
                <button style="font-size: 10px; float: left;">
                    <a href="<c:url value='/to_now.jsp'/>" onclick="oneJumpIframe();"
                        style="text-decoration: none; color: black;" target="main">至现在</a>
                </button>
                <button style="font-size: 10px; float: left;">
                    <a href="<c:url value='/from_to.jsp'/>" onclick="twoJumpIframe();"
                        style="text-decoration: none; color: black;" target="main">从/至</a>
                    <script type="text/javascript">
                        function oneJumpIframe() {
                            $("#main").css("height", "180px");
                        }
                        function twoJumpIframe() {
                            $("#main").css("height", "500px");
                        }
                    </script>
                </button>
                <iframe id="main"
                    style="float: right; width: 550px; border: none; height: 200px;"
                    scrolling="no" name="main"></iframe>
            </p>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <!-- 提交操作 -->
            <button style="font-size: 10px; float: left;" id="retention_time">保存</button>
        </div>
    </div>
    <!-- /时间选择弹窗覆盖 --> <!-- 图标和按钮分组显示 --> <!-- 时钟显示 --> <object>
        <embed
            src="<c:url value='/operator_style/images/time/flash2005.swf'/>"
            width="100px" height="8%">
    </object> <!-- 此处显示当前班次，当前班组，传递当前班信息，如果未选择班组和班次，则不显示 --> <font
        style="font-size: 10px;"> <span id="show_banci"></span>&emsp;<span
        id="show_banzu">${applicationScope.teamid}组 </span>
    </font> <!-- 收集导航链接、表单和其他内容以进行切换 -->
    <div class="drop-men">
        <ul class=" nav_1">
            当前操作员：
            <li class="dropdown"><a href="#"
                class="dropdown-toggle dropdown-at" data-toggle="dropdown"><span
                    class=" name-caret" id="worker_name">${session.userName }<i class="caret"></i></span><img
                    src="<c:url value='/operator_style/images/wo.png'/>"></a>
                <ul class="dropdown-menu " role="menu">
                    <li><a href="user-logout.action"><i class="fa fa-calendar"></i>用户注销</a></li>
                </ul></li>

        </ul>
    </div>
    <!-- 导航栏完 -->
    <div class="clearfix"></div>