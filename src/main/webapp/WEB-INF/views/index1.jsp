<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<%@include file="/WEB-INF/views/common/base.jsp"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="author" content="" />
	<meta name="copyright" content="" />
    <link rel="shortcut icon" href="/favicon.ico"> 
    <link href="${path}/commons/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/commons/css/font-awesome.css" rel="stylesheet">
    <link href="${path}/commons/css/animate.css" rel="stylesheet">
    <link href="${path}/commons/css/style.css" rel="stylesheet">
    
    <link rel="stylesheet" href="${path}/commons/css/font.css">
<link rel="stylesheet" href="${path}/commons/css/xadmin.css">
<link rel="stylesheet" href="${path }/commons/xingzuo/css/vendors.7ca45e05.chunk.css">
<link rel="stylesheet" href="${path }/commons/xingzuo/css/antd.a4a3bf60.chunk.css">
<link rel="stylesheet" href="${path }/commons/xingzuo/css/umi.8635c427.chunk.css">
<link rel="stylesheet" type="text/css" href="${path }/commons/xingzuo/css/layouts__index.b60c2a3a.chunk.css">
<script charset="utf-8" src="${path }/commons/xingzuo/js/layouts__index.6b9c77c7.async.js"></script>
<link rel="stylesheet" type="text/css" href="${path }/commons/xingzuo/css/p__login__index.31ec14c6.chunk.css">
<script charset="utf-8" src="${path }/commons/xingzuo/js/p__login__index.0ee0ed11.async.js"></script>
<link rel="stylesheet" type="text/css" href="${path }/commons/xingzuo/css/p__index.1b4b2fe3.chunk.css">
<script charset="utf-8" src="${path }/commons/xingzuo/js/p__index.82bb646a.async.js"></script>
<!-- <link rel="stylesheet" href="/commons/css/theme5.css"> -->
<script src="${path }/commons/lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="${path }/commons/js/xadmin.js"></script>

    <style type="text/css">
    	.img-circle{ width: 100%; border-radius: 0;}
    </style>
<script>
    // 是否开启刷新记忆tab功能
    // var is_remember = false;
</script>
</head>
 <body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i></div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <img alt="头像"  class="img-circle" src="${path}/commons/picture/main.jpg" />
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li class="divider"></li>
                                <li><a href="${path}/signout">安全退出</a></li>
                            </ul>
                        </div>
                        <div class="logo-element" title="Credit System Manager">CSM</div>
                    </li>
                    <li>
                        <a href="${path}/tz/first" class="J_menuItem">
                            <i class="fa fa-th-large"></i>
                            <span class="nav-label">首页</span>
                        </a>
                    </li>
                    <li>
                        <a href="${path}/tz/admin/first" class="J_menuItem">
                            <i class="fa fa-th-large"></i>
                            <span class="nav-label">后台首页</span>
                        </a>
                    </li>
                     <li>
                        <a href="${path}/tz/regist" class="J_menuItem">
                            <i class="fa fa-th-large"></i>
                            <span class="nav-label">会员列表</span>
                        </a>
                    </li>
                    <li>
                        <a href="${path}/tz/history" class="J_menuItem">
                            <i class="fa fa-handshake-o"></i>
                            <span class="nav-label">历史数据</span>
                        </a>
                    </li>
                    <li>
                        <a href="${path}/tz/admin/history" class="J_menuItem">
                            <i class="fa fa-handshake-o"></i>
                            <span class="nav-label">后台历史数据</span>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                    <ul class="nav navbar-top-links navbar-right">
                    </ul>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i></button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="${path}/tz/first">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i></button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose" data-toggle="dropdown">关闭<span class="caret"></span></button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>当前选项卡</a></li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部</a></li>
                        <li class="J_tabCloseOther"><a>关闭其他</a></li>
                    </ul>
                </div>
                <a href="${path}/signout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i>退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${path}/tz/first" frameborder="0" data-id="${path}/tz/first" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">&copy; 2017-2017 <a href="/" target="_blank">Chemers</a>
                </div>
            </div>
        </div>
        <!--右侧部分结束-->
    </div>

    <!-- 全局js -->
    <script src="${path}/commons/js/jquery.min.js"></script>
    <script src="${path}/commons/js/bootstrap.min.js"></script>
    <script src="${path}/commons/js/jquery.metismenu.js"></script>
    <script src="${path}/commons/js/jquery.slimscroll.min.js"></script>
    <script src="${path}/commons/js/contabs.js"></script>
    <script src="${path}/commons/js/pace.min.js"></script>
    <script>	
    	
    	$().ready( function(){
    			var businessAvatar = getCookie("businessAvatar");
    			if( businessAvatar == null ){
    				addCookie("businessAvatar", "http://avatartest.oss-cn-hangzhou.aliyuncs.com/upload/image/avatar/201905/e79c2a47-9c8e-421c-8280-fca0c388870e.png", {expires: 7 * 24 * 60 * 60});
    			}else{
    				$("#avatar").attr("src", getCookie("businessAvatar"));
    			}
    	})
    </script>
</body>
</html>