<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="<c:url value='/operator_style/mainpagestyle/css/bootstrap.min.css'/>" rel='stylesheet' type='text/css' />
<!-- 定制 主题设计 -->
<link href="<c:url value='/operator_style/mainpagestyle/css/style.css'/>" rel='stylesheet' type='text/css' />
<link href="<c:url value='/operator_style/mainpagestyle/css/font-awesome.css'/>" rel="stylesheet"> 
<script src="<c:url value='/operator_style/js/jquery-3.2.1.js'/>"> </script>
<!-- 主要样式 -->
<script src="<c:url value='/operator_style/mainpagestyle/js/jquery.metisMenu.js'/>"></script>
<script src="<c:url value='/operator_style/mainpagestyle/js/jquery.slimscroll.min.js'/>"></script>
<!-- 定制和插件样式 -->
<link href="<c:url value='/operator_style/mainpagestyle/css/custom.css'/>" rel="stylesheet">
<script src="<c:url value='/operator_style/mainpagestyle/js/custom.js'/>"></script>
<script src="<c:url value='/operator_style/mainpagestyle/js/screenfull.js'/>"></script>
		<script>
		$(function () {
			$('#supported').text('Supported/allowed: ' + !!screenfull.enabled);
			if (!screenfull.enabled) {
				return false;
			}
			$('#toggle').click(function () {
				screenfull.toggle($('#container')[0]);
			});
		});
		</script>
<!-- ------------- -->
<!--饼图--->
<script src="<c:url value='/operator_style/mainpagestyle/js/pie-chart.js'/>" type="text/javascript"></script>
 <script type="text/javascript">
        $(document).ready(function () {
            setInterval(function(){
            	$('#demo-pie-1').pieChart({
                    barColor: '#3bb2d0',
                    trackColor: '#eee',
                    lineCap: 'round',
                    lineWidth: 8,
                    onStep: function (from, to, percent) {
                        $(this.element).find('.pie-value').text(Math.round(percent) + '%');
                    }
                });
            },10000);
            setInterval(function(){
	            $('#demo-pie-2').pieChart({
	                barColor: '#fbb03b',
	                trackColor: '#eee',
	                lineCap: 'butt',
	                lineWidth: 8,
	                onStep: function (from, to, percent) {
	                    $(this.element).find('.pie-value').text(Math.round(percent) + '%');
	                }
	            });
            },10000);
            setInterval(function(){
	            $('#demo-pie-3').pieChart({
	                barColor: '#ed6498',
	                trackColor: '#eee',
	                lineCap: 'square',
	                lineWidth: 8,
	                onStep: function (from, to, percent) {
	                    $(this.element).find('.pie-value').text(Math.round(percent) + '%');
	                }
	            });
       		},10000);
        });
    </script>
<!--自定义图标-->
<script src="<c:url value='/operator_style/mainpagestyle/js/skycons.js'/>"></script>
<!-- button按钮样式设计 -->
<style type="text/css">
	button{
	  background:#1AAB8A;
	  color:#fff;
	  border:none;
	  position:relative;
	  height:30px;
	  font-size:1.6em;
	  padding:0 2em;
	  cursor:pointer;
	  transition:800ms ease all;
	  outline:none;
	}
	button:hover{
	  background:#fff;
	  color:#1AAB8A;
	}
	button:before,button:after{
	  content:'';
	  position:absolute;
	  top:0;
	  right:0;
	  height:2px;
	  width:0;
	  background: #1AAB8A;
	  transition:400ms ease all;
	}
	button:after{
	  right:inherit;
	  top:inherit;
	  left:0;
	  bottom:0;
	}
	button:hover:before,button:hover:after{
	  width:100%;
	  transition:800ms ease all;
	}
</style>