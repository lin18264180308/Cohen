/**
 * @ClassName: jumpPage.js
 * @Author NiWeiDaYe
 * @Description: 主要页面控制
 * @Return_param:
 * @Date: 2017-10-20
 */
/** -------------------------------------------START---------班组选择------------------------------------------------------------- */
$(function() {
	// alert($(window).height());
	$('#ClickMeBan').click(function() {
		$('#goodcover').show();
		$('#code').fadeIn();
	});
	$('#closebt').click(function() {
		$('#code').hide();
		$('#goodcover').hide();
	});
	$('#banzhu_resetbt').click(function() {
		$('#code').hide();
		$('#goodcover').hide();
	});
});
/** -------------------------------------------END---------班组选择------------------------------------------------------------- */
/** -------------------------------------------START--------模式切换------------------------------------------------------------- */
$(function() {
	// alert($(window).height());
	$('#ClickMeMo').click(function() {
		$('#goodcoverMo').show();
		$('#codeMo').fadeIn();
	});
	$('#closebtMo').click(function() {
		$('#codeMo').hide();
		$('#goodcoverMo').hide();
	});
	$('#modelcancle').click(function() {
		$('#code').hide();
		$('#goodcover').hide();
	});
});
/** -------------------------------------------END---------模式切换------------------------------------------------------------- */
/** -------------------------------------------START---------时间选择------------------------------------------------------------- */
/****计算时间****/
// 计算前n分钟的时间
function desendMinutes(date, minutes) {
	minutes = parseInt(minutes);
	var interTimes = minutes * 60 * 1000;
	interTimes = parseInt(interTimes);
	return new Date(Date.parse(date) - interTimes);
}
// 时间差
function GetDateDiff(startTime, endTime, diffType) {
	// 将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式
	startTime = startTime.replace(/\-/g, "/");
	endTime = endTime.replace(/\-/g, "/");
	// 将计算间隔类性字符转换为小写
	diffType = diffType.toLowerCase();
	var sTime = new Date(startTime); // 开始时间
	var eTime = new Date(endTime); // 结束时间
	// 作为除数的数字
	var timeType = 1;
	switch (diffType) {
	case "second":
		timeType = 1000;
		break;
	case "minute":
		timeType = 1000 * 60;
		break;
	case "hour":
		timeType = 1000 * 3600;
		break;
	case "day":
		timeType = 1000 * 3600 * 24;
		break;
	default:
		break;
	}
	return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(timeType));
}
/****格式化时间****/
Date.prototype.Format = function (fmt) {  
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
/****拼接DIV****/
var divSize=0;
function refreshStatus(el, div_color, div_width,machine_name) {
   // 当横向宽度道德一定值时，再次增加时需要将左端相应减少
   $(el).append($('<div showMassage="1" name="'+machine_name+'" style="height: 100%;float:left; width: ' + div_width.toFixed(2)
               + 'px; background-color: ' + div_color + '"></div>'));
   divSize++;
//   $(el).find("div").eq(divSize - 1).dblclick(jumpWindowForNote(this));
}
/****时间选择*****/
$(function() {
	// alert($(window).height());
	$('#ClickMeTime').click(function() {
		$('#goodcoverTime').show();
		$('#codeTime').fadeIn();
	});
	$('#closebtTime').click(function() {
		$('#codeTime').hide();
		$('#goodcoverTime').hide();
	});
	$('#retention_time').click(function() {
		/*
		 * 时间类型
		 */
		var type_time= $("#main").contents().find("#time_model").html();
		/*
		 * 时间值--实时
		 */
		//时间提示值
		var INFO_TIME="";
		//时间间隔（分钟）
		var TIME_LENGTH=0;
		//第一个页面中时间选择的值
		var info_timeONE= $("#main").contents().find("#info_price").html();
		//第二个页面中时间选择的值
		var info_timeTWO= $("#main").contents().find("input[name='chooseTime']:checked").val();
		//第三个页面中时间选择的值，计算出
			var date1= $("#main").contents().find("#banci_starttime").html(); //开始时间  
		    var date2 = new Date();    //结束时间  
		    var date3 = date2.getTime() - new Date(date1).getTime();   //时间差的毫秒数        
		    //------------------------------  
		    //计算出小时数  
		    var leave1=date3%(24*3600*1000);   
		    var hours=Math.floor(leave1/(3600*1000));  
		    //计算相差分钟数  
		    var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数  
		    var minutes=Math.floor(leave2/(60*1000));  
		var info_timeTHREE=hours*60+minutes;
		//遍历子页面(三个)，确定哪个页面是隐藏的
		var info_choosepage_one="",info_choosepage_two="",info_choosepage_three="";
		for ( var i = 0; i < 3; i++) {
			info_choosepage_one= $("#main").contents().find("#Section1").css("display");
			info_choosepage_two= $("#main").contents().find("#Section2").css("display");
			info_choosepage_three= $("#main").contents().find("#Section3").css("display");
		}
		var	time_priceONE= $("#main").contents().find("#time_price").html();
		//默认数据：实时显示，显示时间间隔30分钟
		/*
		 * 时间值--定时
		 */
		var info_pagetwo_startTime= $("#main").contents().find("#dingshi_startTime").val();
		var info_pagetwo_endTime= $("#main").contents().find("#dingshi_endTime").val();
		var dingshi_timelegth=0;
		if ((info_choosepage_one == "block")&&(time_priceONE==0)&&(info_pagetwo_startTime==info_pagetwo_endTime)) {
			/*
             * 修改失败（未选择）的弹窗
             */
    		swal(
    				  '失败!',
    				  '未选择时间修改内容，请重新选择!',
    				  'warning'
    		);
		}else{
			//时间类型显示
			if (type_time==0) {
				if (info_choosepage_one == "block") {
					INFO_TIME=info_timeONE;
					TIME_LENGTH=time_priceONE;
				}else if(info_choosepage_two == "block"){
					INFO_TIME="时间间隔："+info_timeTWO+"分钟";
					TIME_LENGTH=info_timeTWO;
				}else if(info_choosepage_three == "block"){
					INFO_TIME="时间间隔："+info_timeTHREE+"分钟";
					TIME_LENGTH=info_timeTHREE;
				}
				$("#end_time_dingshi_aloneMachine").css("display","none");
				$("#end_time_dingshi").css("display","block");
				$("#end_time_dingshi").css("display","none");
				$("#end_time").css("display","block");
				$("#start_time").html(INFO_TIME+"~");
				$("#start_time_aloneMachine").html(INFO_TIME+"~");
				$("#show_time_type").html("实时");
				$("#show_time_type_aloneMachine").html("实时");
				//默认为实时显示
				setInterval('$("#end_time").html(new Date().toLocaleString())',1000);
				setInterval('$("#end_time_aloneMachine").html(new Date().toLocaleString())',1000);
				//实时选择初始请求
				var end=new Date().Format("yyyy-MM-dd-hh-mm-ss");
				var start = new Date(new Date().getTime()-(Number(TIME_LENGTH)*60*1000)).Format("yyyy-MM-dd-hh-mm-ss");
/* --------------------------START--------------实时请求初始化----------------------------------------------- */
				$.ajax({
                    url: "/lineview/getStatusWithDynamicTimeChoose",
                    type: "get",
                    data: {
						"maxWidth" : 1000,
						"begin":start,
						"end":end
                    },
                    async:false,
                    success: function(data) {
                    	$("#responseIsTrue").html("1");
                    }
                });
/* -----------------------------END--------实时请求初始化------------------------------------------- */
			}else{
				//此处计算开始时间与结束时间之间的时间差
				dingshi_timelegth =	GetDateDiff(info_pagetwo_startTime, info_pagetwo_endTime, "minute");
				INFO_TIME="<br/>开始时间："+info_pagetwo_startTime+"<br/>结束时间："+info_pagetwo_endTime;
				TIME_LENGTH=dingshi_timelegth;
				$("#end_time_dingshi").css("display","block");
				$("#end_time").css("display","none");
				$("#end_time_dingshi_aloneMachine").css("display","block");
				$("#end_time_aloneMachine").css("display","none");
				$("#start_time").html(info_pagetwo_startTime+"~");
				$("#start_time_aloneMachine").html(info_pagetwo_startTime+"~");
				$("#end_time_dingshi").html(info_pagetwo_endTime);
				$("#end_time_dingshi_aloneMachine").html(info_pagetwo_endTime);
				$("#show_time_type").html("定时");
				$("#show_time_type_aloneMachine").html("定时");
/* --------------------------START-----------定时请求初始化------------------------------------------- */
					$.ajax({
	                    url: "/lineview/getStatusWithStaticTimeChoose",
	                    type: "get",
	                    data: {
					      "begin" : info_pagetwo_startTime,
					      "end" : info_pagetwo_endTime,
					      "maxWidth" : 1000
					   },
	                    async:false,
	                    success: function(data) {
	                    	var html='<center> <h2>静态柱状图</h2>'+
	                    				'<div id=staticDiv1" style="width: 1000px;height:30px;border-bottom:5px solid #CDC0B0;margin-top: 10px;">'+
											'<div id="showDiv1" style="float: left;"></div>'+
										'</div>'+
										'<div id="staticDiv2" style="width: 1000px;height:30px;border-bottom:5px solid #CDC0B0;margin-top: 10px;">'+
											'<div id="showDiv2" style="float: left;"></div>'+
										'</div>'+
										'<div id="staticDiv3" style="width: 1000px;height:30px;border-bottom:5px solid #CDC0B0;margin-top: 10px;">'+
											'<div id="showDiv3" style="float: left;"></div>'+
										'</div>'+
										'<div id="staticDiv4" style="width: 1000px;height:30px;border-bottom:5px solid #CDC0B0;margin-top: 10px;">'+
											'<div id="showDiv4" style="float: left;"></div>'+
										'</div>'+
										'<div id="staticDiv5" style="width: 1000px;height:30px;border-bottom:5px solid #CDC0B0;margin-top: 10px;">'+
											'<div id="showDiv5" style="float: left;"></div>'+
										'</div></center>';	
	                    	layer.open({
	                    		  type: 1,
	                    		  skin: 'layui-layer-rim', //加上边框
	                    		  area: ['1100px', '300px'], //宽高
	                    		  content: html
	                    		});
							$("#responseIsTrue").html("1");
					         // 遍历响应回来的数组，为div拼接
					    	  $('#showDiv1').empty();
				              for ( var i1 = 0; i1 < data.bottleWashingMachine.length; i1++) {
				                  refreshStatus($('#showDiv1'), data.bottleWashingMachine[i1].color, data.bottleWashingMachine[i1].width,"洗瓶机");
				              }
				              $('#showDiv2').empty();
				              for ( var i2 = 0; i2 < data.fillingMachine.length; i2++) {
				                  refreshStatus($('#showDiv2'), data.fillingMachine[i2].color, data.fillingMachine[i2].width,"灌酒机");
				              }
				              $('#showDiv3').empty();
				              for ( var i3 = 0; i3 < data.sterilizationMachine.length; i3++) {
				                  refreshStatus($('#showDiv3'), data.sterilizationMachine[i3].color, data.sterilizationMachine[i3].width,"杀菌机");
				              }
				              $('#showDiv4').empty();
				              for ( var i4 = 0; i4 < data.labelingMachine.length; i4++) {
				                  refreshStatus($('#showDiv4'), data.labelingMachine[i4].color, data.labelingMachine[i4].width,"贴标机-1");
				              }
	                    },
	                });
			      // 定时的数据不涉及整线一览
/* ------------------------------END-------定时请求初始化------------------------------------------- */
			}
	        if (($("#responseIsTrue").html())=="1") {
	        	/*
	             * 保存成功的弹窗
	             */
	    		if (INFO_TIME!="") {
	    			//ajax响应回来的信息为“TRUE”,修改基DIV宽度，达到时间间隔改变的目的
	    			//存放时间长度
	    			$("#analyze_timelegth").val(TIME_LENGTH);
	    			swal(
		    				  '成功!',
		    				  '时间已经成功修改!修改时间为：'+INFO_TIME,
		    				  'success'
		    		);
				}else{
					swal(
		    				  '失败!',
		    				  '未选择时间修改内容，请重新选择!',
		    				  'warning'
		    		);
				}
			}else{
				/*
	             * 保存失败的弹窗
	             */
	    		swal(
	    				  '失败!',
	    				  '时间修改失败，请重新修改!',
	    				  'error'
	    		);
			}
		}
		$('#codeTime').hide();
		$('#goodcoverTime').hide();
	});
});
/** -------------------------------------------END---------时间选择------------------------------------------------------------- */
/** -------------------------------------------START---------备注总览------------------------------------------------------------- */
function deleteRow(obj,id) {//参数为表格ID，触发对象  
	swal({
		title: "确定操作吗？",
		text: "你确定要删除这条记录吗？",
		type: "warning",
		showCancelButton: true,
		cancelButtonText:'取消',
		confirmButtonColor: '#DD6B55',
		confirmButtonText: '确定'
	}).then(function(isConfirm) {
		  if (isConfirm === true) {
			//获得触发对象的行号，parentElement的个数取决于触发对象为TR的第几级子项，input=>td=>tr，所以parentElement有两个  
		    var rowIndex = obj.parentElement.parentElement.rowIndex;  
		    //var table = document.getElementById(tableID).deleteRow(rowIndex);  
		    obj.parentElement.parentElement.parentElement.deleteRow(rowIndex); //再简化：省略tableID参数 
		    url="a/remark-delete.action?remark.id="+id;
		   $.post(url,{"time":new Date()},function(){
			 //后台执行删除数据操作
		   });
		   swal(
		      '删除!',
		      '您要删除的记录已成功被删除.',
		      'success'
		    );
		   
		  } else if (isConfirm === false) {
		    swal(
		      '取消',
		      '您的记录未被删除',
		      'error'
		    );
		  };
		   //执行重新查询操作
		  var url1="";
		  if($("#Tb tr").length<2){
		  	var trlength=$("#nowCurrPage").val()-1;
		  	
		  	url1="a/remark-findAll.action?currPage="+trlength;
		  }else{
		  	url1="a/remark-findAll.action?currPage="+$("#nowCurrPage").val();
		  }
		  if($("#Tb tr").length==1){
			  $("#currPage").hide();
			  $("#tempcurrPage").hide();
			  $("#yemian").html("无记录");
		  }else{
			  $.post(url1,{"time":new Date()},function(data){
					$('#first').nextAll().remove();
				    $('#yemian').remove();
					pinjie(data);
				});
		  }
		
		    
	});
}  
//拼接表格操作
function pinjie(data){
	for(var i=0;i<data.pageBean.list.length;i++){
			var str="<tr><td><span class='spanTd' dir='true' id='teamid'>"+data.pageBean.list[i].teamid+"</span><span  style='display:none'>"+data.pageBean.list[i].id+"</span></td>"+
					"<td><span setHidden='true'  class='spanTd' id='startTime'>"+data.pageBean.list[i].startTime.replace('T',' ')+"</span></td>"+
					"<td><span setHidden='true' class='spanTd' id='stopTime'>"+data.pageBean.list[i].stopTime.replace('T',' ')+"</span></td>"+
					"<td><span class='spanTd' id='timeLength'>"+GetDateDiff(data.pageBean.list[i].startTime.replace('T',' '),data.pageBean.list[i].stopTime.replace('T',' '), 'minute')+"分钟</span></td>"+
					"<td><span class='spanTd' id='outageClassification'>"+data.pageBean.list[i].outageClassification+"</span><input class='inputText'  type='text' value='内部原因'  style='display:none'/></td>"+
					"<td><span class='spanTd' id='firstClassStopClassification'>"+data.pageBean.list[i].firstClassStopClassification+"</span><input class='inputText'  type='text' value='电气'  style='display:none'/></td>"+
					"<td><span class='spanTd' id='secondClassStopClassification'>"+data.pageBean.list[i].secondClassStopClassification+"</span><input class='inputText'  type='text' value='灌酒机'  style='display:none'/></td>"+
					"<td><span class='spanTd' id='faultDetails'>"+data.pageBean.list[i].faultDetails+"</span><input class='inputText'  type='text' value='灌酒机无压力倒瓶'  style='display:none'/></td>"+
					"<td><button id='raise' class='raise' onclick='deleteRow(this,"+data.pageBean.list[i].id+")'>删除</button>&emsp;</td></tr>";
					
			$("#Tb").append(str);
			


		}
		//控制下边的页问题
		var str1="<div id='yemian'><input type='hidden' id='nowCurrPage' value='"+data.pageBean.currPage+"'/><span style='color:black;'>第<font color='green'>"+data.pageBean.currPage+"/"+data.pageBean.totalPage+"</font><font style='color:black;'>页&nbsp&nbsp总记录数</font><font color='green'>"+data.pageBean.totalCount+"</font><font style='color:black;'>条&nbsp&nbsp每页显示</font>"+data.pageBean.pageSize+"<font style='color:black;'>条&nbsp</font></span>";
			var str2="";
			if(data.pageBean.currPage!=1){
				var tempcurrPage=data.pageBean.currPage-1;
				str2="<a  onclick='first();'><input type='hidden' id='currPage'value='1'>[首页]</a>&nbsp;&nbsp;"+
   				"<a onclick='previousPage();'><input type='hidden' id='tempcurrPage'value='"+tempcurrPage+"'>[上一页]</a>&nbsp;&nbsp;";
			}
			if(data.pageBean.currPage!=data.pageBean.totalPage){
				var tempcurrPage1=data.pageBean.currPage+1;
				str2="<a onclick='nextPage();'><input type='hidden' id='tempcurrPage1' value='"+tempcurrPage1+"'/>[下一页]</a>&nbsp;&nbsp;"+
   				"<a onclick='lastPage();'><input type='hidden' id='totalPage' value='"+data.pageBean.totalPage+"'/>[尾页]</a>&nbsp;&nbsp;";
			}
			if("1"==data.pageBean.totalPage){
			str2="<span>当前页</span>";
			}
				var str3="</div>";	
		$("#export").prepend(str1+str2+str3);
	
}
//点击备注总览的首页操作
function first(){
	var url="a/remark-findAll.action?currPage="+$("#currPage").val();
	$.post(url,{"time":new Date()},function(data){
	$('#first').nextAll().remove();
	$('#yemian').remove();
	pinjie(data);	
		
	});
}
//点击备注总览的下一页操作
function nextPage(){
	var currPage=$("#tempcurrPage1").val();
	var url="a/remark-findAll.action?currPage="+currPage;
	$.post(url,{"time":new Date()},function(data){
	$('#first').nextAll().remove();
	$('#yemian').remove();
	pinjie(data);	
		
	});
}
//点击备注总览的上一页操作
function previousPage(){
	var url="a/remark-findAll.action?currPage="+$("#tempcurrPage").val();
	$.post(url,{"time":new Date()},function(data){
	$('#first').nextAll().remove();
	$('#yemian').remove();
	pinjie(data);	
		
	});
	
}
//点击备注总览的尾页操作
function lastPage(){
	var url="a/remark-findAll.action?currPage="+$("#totalPage").val();
	$.post(url,{"time":new Date()},function(data){
	$('#first').nextAll().remove();
	$('#yemian').remove();
		pinjie(data);	
	});
}
$(function() {
// alert($(window).height());

//点击备注总览的操作
$('#ClickMeBei').click(function() {
	$('#goodcoverBei').show();
	$('#codeBei').fadeIn();
	$('#first').nextAll().remove();
	$('#yemian').remove();
	var url="a/remark-findAll.action?currPage=1";
	$.post(url,{"time":new Date()},function(data){
		pinjie(data);
	});
	
});
$('#closebtBei').click(function() {
	$('#codeBei').hide();
	$('#goodcoverBei').hide();
});
});
/** -------------------------------------------END---------备注总览------------------------------------------------------------- */
