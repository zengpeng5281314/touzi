<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台登录-X-admin2.2</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" href="${path}/commons2/css/font.css">
<link rel="stylesheet" href="${path}/commons2/css/login.css">
<link rel="stylesheet" href="${path}/commons2/css/xadmin.css">
<script type="text/javascript"
	src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="${path}/commons2/lib/layui/layui.js" charset="utf-8"></script>
<!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="login-bg">

	<div class="login layui-anim layui-anim-up">
		<div class="message">x-admin2.0-管理登录</div>
		<div id="darkbannerwrap"></div>

		<form method="post" class="layui-form">
			<input name="username" placeholder="用户名" type="text"
				lay-verify="required" class="layui-input">
			<hr class="hr15">
			<input name="password" lay-verify="required" placeholder="密码"
				type="password" class="layui-input">
			<hr class="hr15">
			<input value="登录" lay-submit lay-filter="login" style="width: 100%;"
				type="submit">
			<hr class="hr20">
		</form>
	</div>

	<script>
		$(function() {
			layui.use('form', function() {
				var form = layui.form;
				// layer.msg('玩命卖萌中', function(){
				//   //关闭后的操作
				//   });
				//监听提交
				form.on('submit(login)', function(data) {
					// alert(888)
					//layer.msg(JSON.stringify(data.field), function() {
					//	location.href = 'index.html'
					//});
					//return false;
					
					
					//发异步，把数据提交给php
                    $.ajax({
		    			type : "POST",
		    			url : "/dologin",
		    			dataType : "json",
		    			data : data.field,
		    			success : function(res) {
		    				if(res.success){
		    					layer.alert("登录成功", {
		                            icon: 6
		                         },
		                         function() {
		                        	 location.href = "/admin/index";
		                            
		                         }); 
		    				}else{
		    					layer.alert("登录失败", {
		                            icon: 6
		                         },
		                         function() {
		                        	 location.href = "/login";
		                         }); 
		    				}
		    			},
		    			error : function(data) {
		    				layer.alert("登录失败", {
	                            icon: 6
	                         },
	                         function() {
	                        	 location.href = "/login";
	                         }); 
		    			}
		    		}); 
                  return false;
				});
			});
		})
	</script>
	<!-- 底部结束 -->
</body>
</html>