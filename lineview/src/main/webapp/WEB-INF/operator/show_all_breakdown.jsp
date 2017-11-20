<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机台故障信息</title>
<link href="<c:url value='/jqgrid/css/ui.jqgrid-bootstrap.min.css'/>" rel="stylesheet" />
<link href="<c:url value='/jqgrid/css/bootstrap.min.css'/>" rel="stylesheet"/>

<script type="text/javascript" charset="utf-8"src="<c:url value='/jqgrid/js/jquery-3.0.0.min.js'/>"></script>	
<script type="text/javascript" charset="utf-8"src="<c:url value='/jqgrid/js/jquery.jqGrid.min.js'/>"></script>
<script type="text/javascript" charset="utf-8"src="<c:url value='/jqgrid/js/grid.locale-cn.min.js'/>"></script>
<script type="text/javascript" charset="utf-8"src="<c:url value='/jqgrid/js/layer/layer.min.js'/>"></script>
<script type="text/javascript" charset="utf-8"src="<c:url value='/jqgrid/js/layer/extend/layer.ext.min.js'/>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value='/jqgrid/js/form.min.js'/>"></script>
<script>
	//数据集合，使用ajax请求响应
	var rows = [];
		$(function(){
			//ajax响应
			var machine_name = $("#machine_name").html();
			var m_name=machine_name.replace(/\s/g, "");
			switch (m_name) {
	        case "洗瓶机":
	            m_name="1";
	            break;
	        case "灌酒机":
	           	m_name="2";
	            break;
	        case "杀菌机":
	            m_name="3";
	            break;
	        case "贴标机-1":
	            m_name="4";
	            break;
	        case "贴标机-2":
	        	m_name="5";
	        	break;
	        default:
	            break;
	        }
			$.get("/lineview/getStatusOfMachine", { "machineFlg": m_name } , function(data){
				//遍历回来的数据，添加到展示数组中
				for(var i=0;i<data.faultName.length;i++){
					var temp = {"name":data.faultName[i],"number":data.faultTimes[i],"id":(i+1)};
					rows.push(temp);
				}
				rows =rows.sort(getSortFun("desc", "number"));
				//加载表格
				loadGrid(rows);
			} );
			//json对象排序
			function getSortFun(order, sortBy) {
			    var ordAlpah = (order == 'asc') ? '>' : '<';
			    var sortFun = new Function('a', 'b', 'return a.' + sortBy + ordAlpah + 'b.' + sortBy + '?1:-1');
			    return sortFun;
			}
		});
	//装载表格，参数为数据集合
	function loadGrid(rows) {
		 $("#grid").jqGrid(
				{
					height : 400,
					rownumbers : true,
					sortname : 'id',
					sortorder : "desc",
					colModel : [
							{
								label : '故障信息',
								name:  'name',
								index: 'name',
								align : 'center',
								width : 260
							},
							{
								label : '出现次数',
								 name : 'number',
								index : 'number', 
								sortable : false,
								align : 'center',
								width : 160
							},
							 ],
				});
			//遍历添加到表格中
			for(var i = 0; i < rows.length; i++) {
				$("#grid").jqGrid('addRowData', i + 1, rows[i]);
			}
	};
	 //查看信息
	function onDtl(id){
		var trOBJ = document.getElementById(id);
		layer.alert("故障信息："+$(trOBJ).find("td").eq(1).html()+"</br>出现次数:"+$(trOBJ).find("td").eq(2).html()+"次");
	};
	
	/*//显示全部
	function showAll() {
		//清空表格之前的内容
		$("#grid tr:not(:first)").empty("");
		$("#userName").val("");
		$("#honestName").val("");
		onQ();
	};
	//查询
	 //返回数组中的元素索引
			Array.prototype.weizhi=function(obj){
			  var i=this.length;
			  for(i;i!=0;i-=1){
			    if(this[i]===obj){
			      return i;
			    } 
			  }return false;
			};
	function onQ() {
		var querryArr = [];
		for(var i = 0; i < rows.length; i++) {
			var arrName = rows[i].name;
			var arrNum = rows[i].number;
			var isContain = arrName.weizhi($("#userName").val());
			var isContainTime = arrNum.weizhi($("#honestName").val());
			if (isContain!="-1"&&isContainTime!="-1") {
				querryArr.push(rows[i]);
			}
			if (isContain!="-1") {
			}
		}
		//清空表格之前的内容
		$("#grid tr:not(:first)").empty("");
		//遍历添加到表格中
		for(var i = 0; i < querryArr.length; i++) {
			$("#grid").jqGrid('addRowData', i + 1, querryArr[i]);
		}
	}; */
	//窗口变化时自适应宽度
	window.onresize = function() {
		$("#grid").setGridWidth(document.body.clientWidth - 12);
	};
</script>
 <style>
.in{height:30px;border-radius:4px;}
.getS{background:#69Ea7c;color:#fff;padding:5px;padding-left:20px;padding-right:20px;border-radius:4px;}
.getS:HOVER{background:#49D44d;cursor:pointer;}
.mesd{color:#C12E2A}
.mesq{color:#4CAE4C}
</style>
</head>
<body>
	<h2 align="center" style="color: #388E8E;font-family: 幼圆">
	<font id="machine_name">
	<c:choose>
		<c:when test="${param.machineName eq 'XPJ'}">洗瓶机</c:when>
		<c:when test="${param.machineName eq 'GJJ'}">灌酒机</c:when>
		<c:when test="${param.machineName eq 'SJJ'}">杀菌机</c:when>
		<c:when test="${param.machineName eq 'TBJ-1'}">贴标机-1</c:when>
		<c:when test="${param.machineName eq 'TBJ-2'}">贴标机-2</c:when>
	</c:choose>
	</font>
	故障详细信息</h2>
<div class="row">
	<div class="col-sm-12" style="background:#eee">
		<table id="grid" style="background:#eff;"></table>
		<div id="pager" style="height:35px;"></div>
	</div>
</div>
</body>
</html>