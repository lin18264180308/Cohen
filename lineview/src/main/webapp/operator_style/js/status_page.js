/**
 * @ClassName: status_page.js
 * @Author NiWeiDaYe
 * @Description: 柱状展示样式控制，添加备注控制，鼠标浮动控制
 * @Return_param:
 * @Date: 2017-10-20
 */
/**
 * 柱状动态图表控制器
 */

/**
 * 格式化时间
 */
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

$(function() {
   // 定义请求类型,默认为0
   var requestType = "0", begin = "", end = "", maxWidth = "";
   if ($("#show_time_type").html() == "实时") {
      requestType = "0";
      end = (new Date()).Format("yyyy-MM-dd-hh-mm-ss");
      start = (new Date(new Date().getTime()-(Number($("#analyze_timelegth").val())*60*1000))).Format("yyyy-MM-dd-hh-mm-ss");
      // 实时
      maxWidth = 1000;
   } else if ($("#show_time_type").html() == "定时") {
      requestType = "1";
      // 定时
      begin = $("#start_time").html();
      end = $("#end_time_dingshi").html();
      maxWidth = 1000;
   }
   // 时间变量
   var args = {
      "time" : new Date(),
      "begin" : begin,
      "end" : end,
      "maxWidth" : maxWidth,
   };
   // 实时与定时的请求控制
   if (requestType == "0") {// 实时
/* --------------------------START--------------页面初始化----------------------------------------------- */
	   $.ajax({
	         url: "/lineview/setParameters",
	         type: "get",
	         data: args,
	         async:false,
	         success: function() {
	        	 /* // 遍历响应回来的数组，为div拼接
	             for ( var i1 = 0; i1 < data.bottleWashingMachine.length; i1++) {
	                 refreshStatus($('#div1'), data.bottleWashingMachine[i1].color, data.bottleWashingMachine[i1].width,"洗瓶机");
	             }
	             for ( var i2 = 0; i2 < data.fillingMachine.length; i2++) {
	                 refreshStatus($('#div2'), data.fillingMachine[i2].color, data.fillingMachine[i2].width,"灌酒机");
	             }
	             for ( var i3 = 0; i3 < data.sterilizationMachine.length; i3++) {
	                 refreshStatus($('#div3'), data.sterilizationMachine[i3].color, data.sterilizationMachine[i3].width,"杀菌机");
	             }
	             for ( var i4 = 0; i4 < data.labelingMachine.length; i4++) {
	                 refreshStatus($('#div4'), data.labelingMachine[i4].color, data.labelingMachine[i4].width,"贴标机-1");
	             }*/
	         },
	     });
/* -----------------------------END--------页面初始化------------------------------------------- */
	   $.ajax({
           url: "/lineview/getStatus",
           type: "get",
           data:  {
               "time" : new Date()
           },
           success: function(data) {
         	  //三大指标
         	  $("#oee").html(data.oee);
         	  $("#gyl").html(data.gyl);
         	  $("#lef").html(data.lef);
         	  // 遍历响应回来的数组，为div拼接
               $('#div1').empty();
               for ( var i1 = 0; i1 < data.bottleWashingMachine.length; i1++) {
            	   var color = data.bottleWashingMachine[i1].color;
            	   if(color=="rgb(50, 205, 50)"){
            		   color = "rgb(144, 238, 144)";
            	   }
                   refreshStatus($('#div1'), color, data.bottleWashingMachine[i1].width,"洗瓶机");
               }
               $('#div1_1').empty();
               for ( var i1 = 0; i1 < data.lianDao1.length; i1++) {
            	   var color = data.lianDao1[i1].color;
            	   if(color=="rgb(50, 205, 50)"){
            		   color = "rgb(144, 238, 144)";
            	   }
            	   refreshStatus($('#div1_1'), color, data.lianDao1[i1].width,"洗瓶机至灌酒机链道");
               }
               $('#div1_1_1').empty();
               for ( var i1 = 0; i1 < data.lianDao4.length; i1++) {
            	   var color = data.lianDao4[i1].color;
            	   if(color=="rgb(50, 205, 50)"){
            		   color = "rgb(144, 238, 144)";
            	   }
            	   refreshStatus($('#div1_1_1'), color, data.lianDao4[i1].width,"洗瓶机至灌酒机链道");
               }
               // 页面机台背景框
               $("#XPJ_D").css("border","10px solid "+ data.bottleWashingMachine[(data.bottleWashingMachine.length - 1)].color);
               
               $('#div2').empty();
               for ( var i2 = 0; i2 < data.fillingMachine.length; i2++) {
            	   var color = data.fillingMachine[i2].color;
            	   if(color=="rgb(50, 205, 50)"){
            		   color = "rgb(144, 238, 144)";
            	   }
                   refreshStatus($('#div2'), color, data.fillingMachine[i2].width,"灌酒机");
               }
               $('#div2_2').empty();
               for ( var i2 = 0; i2 < data.lianDao2.length; i2++) {
            	   var color = data.lianDao2[i2].color;
            	   if(color=="rgb(50, 205, 50)"){
            		   color = "rgb(144, 238, 144)";
            	   }
            	   refreshStatus($('#div2_2'), color, data.lianDao2[i2].width,"灌酒机只杀菌机链道");
               }
                   // 页面机台背景框
               $("#GJJ_D").css("border","10px solid "+ data.fillingMachine[(data.fillingMachine.length - 1)].color);
               $('#div3').empty();
               for ( var i3 = 0; i3 < data.sterilizationMachine.length; i3++) {
            	   var color = data.sterilizationMachine[i3].color;
            	   if(color=="rgb(50, 205, 50)"){
            		   color = "rgb(144, 238, 144)";
            	   }
                   refreshStatus($('#div3'), color, data.sterilizationMachine[i3].width,"杀菌机");
               }
               $('#div3_3').empty();
               for ( var i3 = 0; i3 < data.lianDao3.length; i3++) {
            	   var color = data.lianDao3[i3].color;
            	   if(color=="rgb(50, 205, 50)"){
            		   color = "rgb(144, 238, 144)";
            	   }
            	   refreshStatus($('#div3_3'), color, data.lianDao3[i3].width,"杀菌机至贴标机链道");
               }
               // 页面机台背景框
               $("#SJJ_D").css("border","10px solid "+ data.sterilizationMachine[(data.sterilizationMachine.length - 1)].color);
               $('#div4').empty();
               for ( var i4 = 0; i4 < data.labelingMachine.length; i4++) {
            	   var color = data.labelingMachine[i4].color;
            	   if(color=="rgb(50, 205, 50)"){
            		   color = "rgb(144, 238, 144)";
            	   }
                   refreshStatus($('#div4'), color, data.labelingMachine[i4].width,"贴标机-1");
               }
               // 页面机台背景框
               $("#TBJ_B_D").css("border","10px solid "+ data.labelingMachine[(data.labelingMachine.length - 1)].color);
               /* --------------START-----------链道电机----------------------------------- */
               /**
                * 判断数组中是否包含那个元素
                */
               Array.prototype.in_array = function(e) {
             	  for(var i=0; i<this.length && this[i]!=e; i++);
             	  return !(i==this.length);
             	};
               //链道1
               var lianDao1Machine = ["M2_8",
											"M2_10",
											"M2_12",
											"M2_14",
											"M2_15",
											"M2_16",
											"M2_17",
											"M2_18",
											"M3_19",
											"M3_20",
											"M3_21"];
               if(data.lianDao1_reason!=null){
             	  for(var i =0;i<lianDao1Machine.length;i++){
             		  $("#"+lianDao1Machine[i]).css("background-color","rgb(50, 205, 50)");
             	  }
             	  for(var i=0;i<data.lianDao1_reason.length;i++){
             		  if(data.lianDao1_reason[i]=="M26"){
             			  $("#M23").css("background-color","rgb(220, 20, 60)");
             			  $("#M24").css("background-color","rgb(220, 20, 60)");
             			  $("#M25").css("background-color","rgb(220, 20, 60)");
             			  $("#M26").css("background-color","rgb(220, 20, 60)");
             		  }
             			  $("#"+data.lianDao1_reason[i]).css("background-color","rgb(220, 20, 60)");
             	  }
               }
               //链道1_1
               var lianDao1_1Machine = ["M26"];
               if(data.lianDao4_reason!=null){
             	  $("#M23").css("background-color","rgb(50, 205, 50)");
      			  $("#M24").css("background-color","rgb(50, 205, 50)");
      			  $("#M25").css("background-color","rgb(50, 205, 50)");
      			  $("#M26").css("background-color","rgb(50, 205, 50)");
             	  for(var i=0;i<data.lianDao4_reason.length;i++){
             		  if(data.lianDao4_reason[i]=="M26"){
              			  $("#M23").css("background-color","rgb(220, 20, 60)");
              			  $("#M24").css("background-color","rgb(220, 20, 60)");
              			  $("#M25").css("background-color","rgb(220, 20, 60)");
              			  $("#M26").css("background-color","rgb(220, 20, 60)");
              		  }
             	  }
               }
               //链道2
               var lianDao2Machine = ["3_H5",
											"3_H6",
											"M2_29",
											"M2_30",
											"M2_31",
											"M2_32",
											"M2_33"];
               if(data.lianDao2_reason!=null){
             	  for(var i =0;i<lianDao2Machine.length;i++){
             		  $("#"+lianDao2Machine[i]).css("background-color","rgb(50, 205, 50)");
             	  }
             	  for(var i=0;i<data.lianDao2_reason.length;i++){
             		  if(data.lianDao2_reason[i]=="3_H5"){
             			  $("#3_H5").css("background-color","rgb(220, 20, 60)");
             			  $("#3_H6").css("background-color","rgb(220, 20, 60)");
             		  }
             		  $("#"+data.lianDao2_reason[i]).css("background-color","rgb(220, 20, 60)");
             	  }
               }
               //链道3
               var lianDao3Machine = ["M4_36",
						                  "M4_37",
						                  "M4_38",
						                 "M4_42",
						                 "M4_43",
						                 "M4_39",
						                 "M4_44",
						                 "M4_40",
						                 "M4_49",
						                 "M4_45",
						                  "M4_41",
						                  "M4_46",
						                  "M4_47",
						                  "M4_48",
						                  "M5_1",
						                 "M5_2",
						                  "M5_3",
						                  "M5_4",
						                  "M5_5",
						                  "M5_6",
						                  "M5_7"
						                 ];
               if(data.lianDao3_reason!=null){
             	  for(var i =0;i<lianDao3Machine.length;i++){
             		  $("#"+lianDao3Machine[i]).css("background-color","rgb(50, 205, 50)");
             	  }
             	  for(var i=0;i<data.lianDao3_reason.length;i++){
             		  $("#"+data.lianDao3_reason[i]).css("background-color","rgb(220, 20, 60)");
             	  }
               }
               /* --------------END-----------链道电机----------------------------------- */
           },
       });
      // ajax循环
      setInterval(function() {
/* --------------------------START--------------实时请求循环----------------------------------------------- */
			$.ajax({
              url: "/lineview/getStatus",
              type: "get",
              data:  {
                  "time" : new Date()
              },
              success: function(data) {
            	  //三大指标
            	  $("#oee").html(data.oee);
            	  $("#gyl").html(data.gyl);
            	  $("#lef").html(data.lef);
            	  // 遍历响应回来的数组，为div拼接
                  $('#div1').empty();
                  for ( var i1 = 0; i1 < data.bottleWashingMachine.length; i1++) {
                	  var color = data.bottleWashingMachine[i1].color;
	               	   if(color=="rgb(50, 205, 50)"){
	               		color = "rgb(144, 238, 144)";
	               	   }
                      refreshStatus($('#div1'), color, data.bottleWashingMachine[i1].width,"洗瓶机");
                  }
                  $('#div1_1').empty();
                  for ( var i1 = 0; i1 < data.lianDao1.length; i1++) {
                	  var color = data.lianDao1[i1].color;
	               	   if(color=="rgb(50, 205, 50)"){
	               		color = "rgb(144, 238, 144)";
	               	   }
               	   refreshStatus($('#div1_1'), color, data.lianDao1[i1].width,"洗瓶机至灌酒机链道");
                  }
                  
                  $('#div1_1_1').empty();
                  for ( var i1 = 0; i1 < data.lianDao4.length; i1++) {
               	   var color = data.lianDao4[i1].color;
               	   if(color=="rgb(50, 205, 50)"){
               		   color = "rgb(144, 238, 144)";
               	   }
               	   refreshStatus($('#div1_1_1'), color, data.lianDao4[i1].width,"洗瓶机至灌酒机链道");
                  }
                  // 页面机台背景框
                  $("#XPJ_D").css("border","10px solid "+ data.bottleWashingMachine[(data.bottleWashingMachine.length - 1)].color);
                  
                  $('#div2').empty();
                  for ( var i2 = 0; i2 < data.fillingMachine.length; i2++) {
                	  var color = data.fillingMachine[i2].color;
	               	   if(color=="rgb(50, 205, 50)"){
	               		color = "rgb(144, 238, 144)";
	               	   }
                      refreshStatus($('#div2'), color, data.fillingMachine[i2].width,"灌酒机");
                  }
                  $('#div2_2').empty();
                  for ( var i2 = 0; i2 < data.lianDao2.length; i2++) {
                	  var color = data.lianDao2[i2].color;
	               	   if(color=="rgb(50, 205, 50)"){
	               		color = "rgb(144, 238, 144)";
	               	   }
               	   refreshStatus($('#div2_2'), color, data.lianDao2[i2].width,"灌酒机只杀菌机链道");
                  }
                      // 页面机台背景框
                  $("#GJJ_D").css("border","10px solid "+ data.fillingMachine[(data.fillingMachine.length - 1)].color);
                  $('#div3').empty();
                  for ( var i3 = 0; i3 < data.sterilizationMachine.length; i3++) {
                	  var color = data.sterilizationMachine[i3].color;
	               	   if(color=="rgb(50, 205, 50)"){
	               		color = "rgb(144, 238, 144)";
	               	   }
                      refreshStatus($('#div3'), color, data.sterilizationMachine[i3].width,"杀菌机");
                  }
                  $('#div3_3').empty();
                  for ( var i3 = 0; i3 < data.lianDao3.length; i3++) {
                	  var color = data.lianDao3[i3].color;
	               	   if(color=="rgb(50, 205, 50)"){
	               		color = "rgb(144, 238, 144)";
	               	   }
               	   refreshStatus($('#div3_3'), color, data.lianDao3[i3].width,"杀菌机至贴标机链道");
                  }
                  // 页面机台背景框
                  $("#SJJ_D").css("border","10px solid "+ data.sterilizationMachine[(data.sterilizationMachine.length - 1)].color);
                  $('#div4').empty();
                  for ( var i4 = 0; i4 < data.labelingMachine.length; i4++) {
                	  var color = data.labelingMachine[i4].color;
	               	   if(color=="rgb(50, 205, 50)"){
	               		color = "rgb(144, 238, 144)";
	               	   }
                      refreshStatus($('#div4'), color, data.labelingMachine[i4].width,"贴标机-1");
                  }
                  // 页面机台背景框
                  $("#TBJ_B_D").css("border","10px solid "+ data.labelingMachine[(data.labelingMachine.length - 1)].color);
                  /* --------------START-----------链道电机----------------------------------- */
                  /**
                   * 判断数组中是否包含那个元素
                   */
                  Array.prototype.in_array = function(e) {
                	  for(var i=0; i<this.length && this[i]!=e; i++);
                	  return !(i==this.length);
                	};
                  //链道1
                  var lianDao1Machine = ["M2_8",
											"M2_10",
											"M2_12",
											"M2_14",
											"M2_15",
											"M2_16",
											"M2_17",
											"M2_18",
											"M3_19",
											"M3_20",
											"M3_21"];
                  if(data.lianDao1_reason!=null){
                	  for(var i =0;i<lianDao1Machine.length;i++){
                		  $("#"+lianDao1Machine[i]).css("background-color","rgb(50, 205, 50)");
                	  }
                	  for(var i=0;i<data.lianDao1_reason.length;i++){
                		  $("#"+data.lianDao1_reason[i]).css("background-color","rgb(220, 20, 60)");
                	  }
                  }
                  //链道1_1
                  var lianDao1_1Machine = ["M26"];
                  if(data.lianDao4_reason!=null){
                	  $("#M23").css("background-color","rgb(50, 205, 50)");
         			  $("#M24").css("background-color","rgb(50, 205, 50)");
         			  $("#M25").css("background-color","rgb(50, 205, 50)");
         			  $("#M26").css("background-color","rgb(50, 205, 50)");
                	  for(var i=0;i<data.lianDao4_reason.length;i++){
                		  if(data.lianDao4_reason[i]=="M26"){
                 			  $("#M23").css("background-color","rgb(220, 20, 60)");
                 			  $("#M24").css("background-color","rgb(220, 20, 60)");
                 			  $("#M25").css("background-color","rgb(220, 20, 60)");
                 			  $("#M26").css("background-color","rgb(220, 20, 60)");
                 		  }
                	  }
                  }
                  //链道2
                  var lianDao2Machine = ["3_H5",
											"3_H6",
											"M2_29",
											"M2_30",
											"M2_31",
											"M2_32",
											"M2_33"];
                  if(data.lianDao2_reason!=null){
                	  for(var i =0;i<lianDao2Machine.length;i++){
                		  $("#"+lianDao2Machine[i]).css("background-color","rgb(50, 205, 50)");
                	  }
                	  for(var i=0;i<data.lianDao2_reason.length;i++){
                		  if(data.lianDao2_reason[i]=="3_H5"){
                 			  $("#3_H5").css("background-color","rgb(220, 20, 60)");
                 			  $("#3_H6").css("background-color","rgb(220, 20, 60)");
                 		  }
                		  $("#"+data.lianDao2_reason[i]).css("background-color","rgb(220, 20, 60)");
                	  }
                  }
                  //链道3
                  var lianDao3Machine = ["M4_36",
						                  "M4_37",
						                  "M4_38",
						                 "M4_42",
						                 "M4_43",
						                 "M4_39",
						                 "M4_44",
						                 "M4_40",
						                 "M4_49",
						                 "M4_45",
						                  "M4_41",
						                  "M4_46",
						                  "M4_47",
						                  "M4_48",
						                  "M5_1",
						                 "M5_2",
						                  "M5_3",
						                  "M5_4",
						                  "M5_5",
						                  "M5_6",
						                  "M5_7"
						                 ];
                  if(data.lianDao3_reason!=null){
                	  for(var i =0;i<lianDao3Machine.length;i++){
                		  $("#"+lianDao3Machine[i]).css("background-color","rgb(50, 205, 50)");
                	  }
                	  for(var i=0;i<data.lianDao3_reason.length;i++){
                		  $("#"+data.lianDao3_reason[i]).css("background-color","rgb(220, 20, 60)");
                	  }
                  }
                  /* --------------END-----------链道电机----------------------------------- */
              },
          });
/* -----------------------------END--------实时请求循环------------------------------------------- */
      }, 2500);
   } 
/** **********拼接DIV**************** */
function refreshStatus(el, div_color, div_width,machine_name) {
   // 当横向宽度道德一定值时，再次增加时需要将左端相应减少
   $(el).append($('<div name="'+machine_name+'" style="height: 100%;float:left; width: ' + div_width.toFixed(2)
               + 'px; background-color: ' + div_color + '"></div>'));
}
/**
 * 添加备注弹窗
 */
function jumpWindowForNote(obj) {
	//获取开始时间、结束时间
		//1、自己的宽度、父节点的宽度、存储外边距距离的对象、
		var selfEleWidth = parseInt($(obj).css("width"));
		var parentEleWidth = parseInt($(obj.parentElement).css("width"));
		var parentObjMargin = $(obj).offset();
		var margin_right=parentEleWidth-(parentObjMargin.left+selfEleWidth);//当前元素右边框距离右边距的距离
		var left_margin_right = parentEleWidth-parentObjMargin.left;//当前元素左边框距离右边距的距离
		var perSecondShowEle = 1000/1800;//每秒展示的div宽度（基础div宽度）
		var currentTime = new Date().getTime();//当前系统时间的毫秒值
		var rightMarginShowTime = parseInt(margin_right*perSecondShowEle*1000);//当前元素距离右边边框显示数据的时间，整数，毫秒
		var leftMarginShowTime = parseInt(left_margin_right*perSecondShowEle*1000);//当前元素左边距距离右边边框显示数据的时间，整数，毫秒
		var endTime=new Date(currentTime-rightMarginShowTime).Format("yyyy-MM-dd hh:mm:ss");//开始时间
		var startTime = $("current_startTime").val()//结束时间
		var banzhu_msg=$("#show_banzu").html();//班组信息
		var banzhu_id = parseInt(banzhu_msg);//班组ID，参数提交
		var show_models = $("#show_models").html();
		if (banzhu_msg==""||show_models=="模式未选择") {
			//未选择班组，参数不足，无法提交
			swal({
				type : 'error',
				title : '错误！',
				html : '时间: ' + new Date().toLocaleString()+'<br/>'
						+'未选择班组或未选择模式，无法添加备注，请选择班组之后添加！',
				showCancelButton : true,
				showConfirmButton : false,
				cancelButtonText : '关闭'
			});
		}else{
			//选择班组之后可以进行添加
			// 备注弹窗
			swal(
					{
						title : '添加备注',
						html : '<hr style=" height:2px;border:none;border-top:2px dotted #185598;" >'
								+ '<form method="post" style="font-size:15px;" id="note">'
								+ '<dl>'
								+ '<dt>当前模式：<font id="current_model" style="color:#CD2626;">'+show_models+'</font></dt>'
								+ '<dt>负责人：<font >'+$("#show_banzu").html()+'&emsp;'+$("#worker_name").html()+'</font></dt>'
								+ '<dt>注：时间填写格式2017-09-08 12:00:00</dt>'
								+ '<dt>开始时间：<input id="current_startTime" style="color:#104E8B;" type="text"/></dt>'
								+ '<dt>结束时间：<input id="current_endTime" style="color:#104E8B;" type="text"/></dt>'
								+ '<dt style="margin-left:-2%;">停&emsp;机&emsp;分&emsp;类：'
								+ '<select id="Result_stop_machine">'
								+ '<option >请选择</option>'
								+ '<option value="内部原因">内部原因</option>'
								+ '<option value="外部原因">外部原因</option>'
								+ '</select>'
								+ '</dt>'
								+ '<dt style="margin-left:-2%;">一级子停机分类：'
								+ '<select id="Result_one_stop_machine">'
								+ '<option >请选择</option>'
								+ '<option value="电气原因">电气原因</option>'
								+ '<option value="机械原因">机械原因</option>'
								+ '<option value="仪表原因">仪表原因</option>'
								+ '<option value="自控原因">自控原因</option>'
								+ '<option value="仪表原因">仪表原因</option>'
								+ '<option value="其他原因">其他原因</option>'
								+ '</select>'
								+ '</dt>'
								+ '<dt style="margin-left:-2%;">二级子停机分类：'
								+ '<select id="Result_two_stop_machine">'
								+ '<option >请选择</option>'
								+ '<option value="洗瓶机">洗瓶机&emsp;</option>'
								+ '<option value="灌酒机">灌酒机&emsp;</option>'
								+ '<option value="杀菌机">杀菌机&emsp;</option>'
								+ '<option value="贴标机">贴标机&emsp;</option>'
								+ '</select>'
								+ '</dt>>>'
								+ '<font>故障详细描述:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</font><br/><textarea cols="25" id="Result_stop_machine_description" name="editor1" rows="5"></textarea>'
								+ '</dl>'
								+ '</form>',
						showCancelButton : true,
						confirmButtonText : '提交',
						cancelButtonText : '取消',
						showLoaderOnConfirm : true,
						preConfirm : function() {
							return new Promise(function(resolve) {
								setTimeout(function() {
									resolve();
								}, 1000);
							});
						},
						allowOutsideClick : false
					}).then(
					function(isConfirm) {
						if (isConfirm) {
							// 备注内容：停机分类、一级子停机分类、二级子停机分类、详细描述
							var banzu_id_machine=banzhu_id;//班组ID
							var start_time_machine= $("#current_startTime").val();//开始时间
							var end_time_machine= $("#current_endTime").val();//结束时间
							var Result_stop_machine = $("#Result_stop_machine").val();//停机分类
							var Result_one_stop_machine = $("#Result_one_stop_machine")//一级停机分类
									.val();
							var Result_two_stop_machine = $("#Result_two_stop_machine")//二级停机分类
									.val();
							var Result_stop_machine_description = $("#Result_stop_machine_description").val();//故障详细描述
							// 此处填写上述表单提交操作，如果Ajax返回成功提交，则进入下面的弹窗，否则显示添加失败
							$.ajax({
								cache : true,
								type : "POST",
								url : "a/remark-save.action",
								data : 	
										"remark.teamid="+banzu_id_machine
										+ "&remark.startTime="+start_time_machine
										+ "&remark.stopTime="+end_time_machine
										+ "&remark.outageClassification=" + Result_stop_machine
										+ "&remark.firstClassStopClassification="+ Result_one_stop_machine
										+ "&remark.secondClassStopClassification="+ Result_two_stop_machine
										+ "&remark.faultDetails="+ Result_stop_machine_description,
								async : true,
								error : function(request) {
									// 失败
									swal({
										type : 'error',
										title : '您的备注添加失败!',
										html : '时间: ' + new Date().toLocaleString(),
										showCancelButton : true,
										showConfirmButton : false,
										cancelButtonText : '关闭'
									});
								},
								success : function() {
									// 成功
									swal({
										type : 'success',
										title : '您的备注已经添加完成!',
										html : '添加时间: ' + new Date().toLocaleString(),
										showCancelButton : true,
										showConfirmButton : false,
										cancelButtonText : '关闭'
									});
								}
							});
						}
					});
		}
};
/**
 * 点击关注模式按钮则只显示被选中的，若全部不选，则全部显示
 */
	$('#btn_care').click(function() {
		var els = $('#machine_list').find('input');
		// 未被选中元素长度
		var nochoosed_element_length = 0;
		// 未被选中元素索引数组
		var nochoosed_elements = [];
		for ( var i = 0; i < els.length; i++) {
			var isCheck = $(els).eq(i).prop("checked");
			if (isCheck == false) {
				nochoosed_element_length++;
				// 将未选中的元素的索引添加到集合中
				nochoosed_elements.push(i);
			}
		}
		// 遍历未被选中的元素索引集合，将其整体隐藏
		if (nochoosed_element_length != els.length) {
			for ( var j = 0; j < nochoosed_elements.length; j++) {
				$(els).eq(nochoosed_elements[j]).parent().hide();
//				$("#machine" + (nochoosed_elements[j] + 1)).hide();
			}
		} else {
			swal({
				title : "注意!",
				text : "请点击机台名称前的选择框选择需要关注的机台!",
				type : "warning",
				confirmButtonText : "关闭"
			});
		}
	});
	$('#btn_exit_care').click(function() {
		var els = $('#machine_list').find('input');
		for ( var i = 0; i < els.length; i++) {
			$(els).eq(i).parent().show();
		}
	});
	$("#btn_add_note").click(function(){
		/**
		 * 测试备注添加弹窗
		 */
		jumpWindowForNote(this);
		/**************/
	});
});
