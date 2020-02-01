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
        <link rel="stylesheet" href="${path}/commons2/css/font.css">
        <link rel="stylesheet" href="${path}/commons2/css/xadmin.css">
        <!-- <link rel="stylesheet" href="${path}/commons2/css/theme5.css"> -->
        <script src="${path}/commons2/lib/layui/layui.js" charset="utf-8"></script>
        <script type="text/javascript" src="${path}/commons2/js/xadmin.js"></script>
        <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
        <!--[if lt IE 9]>
          <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
          <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <script>
            // 是否开启刷新记忆tab功能
            // var is_remember = false;
        </script>
    </head>
    <body class="index">
        <!-- 顶部开始 -->
        <div class="container">
            <div class="logo">
                <a href="javascript:;">大师兄淘金</a></div>
            <div class="left_open">
                <a><i title="展开左侧栏" class="iconfont">&#xe699;</i></a>
            </div>
            <ul class="layui-nav right" lay-filter="">
                <li class="layui-nav-item">
                    <a href="javascript:;">${name }</a>
                </li>
                <li class="layui-nav-item to-index">
                    <a href="/signout">退出</a></li>
            </ul>
        </div>
        <!-- 顶部结束 -->
        <!-- 中部开始 -->
        <!-- 左侧菜单开始 -->
        <div class="left-nav">
            <div id="side-nav">
                <ul id="nav">
                 	<li>
                        <a href="javascript:;" onclick="xadmin.add_tab('首页','/tz/first')">
                            <i class="iconfont left-nav-li" lay-tips="首页">&#xe6b4;</i>
                            <cite>首页</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                    </li>
                   
                    <c:if test="${ismaster}">
	                    <li>
	                        <a href="javascript:;" onclick="xadmin.add_tab('后台首页','${path}/tz/admin/first')">
	                            <i class="iconfont left-nav-li" lay-tips="后台首页">&#xe6b4;</i>
	                            <cite>后台首页</cite>
	                            <i class="iconfont nav_right">&#xe697;</i></a>
	                    </li>
                    </c:if>
                    <li>
                        <a href="javascript:;" onclick="xadmin.add_tab('会员列表','/tz/regist')">
                            <i class="iconfont left-nav-li" lay-tips="会员列表">&#xe6b8;</i>
                            <cite>会员管理</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                    </li>
                    <li>
                        <a href="javascript:;" onclick="xadmin.add_tab('历史记录','/tz/history')">
                            <i class="iconfont left-nav-li" lay-tips="历史记录">&#xe723;</i>
                            <cite>历史记录</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                    </li>
                    <c:if test="${ismaster}">
                    <li>
                        <a href="javascript:;" onclick="xadmin.add_tab('后台历史记录','${path}/tz/admin/history')">
                            <i class="iconfont left-nav-li" lay-tips="后台历史记录">&#xe723;</i>
                            <cite>后台历史记录</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                    </li>
                    </c:if>
                </ul>
            </div>
        </div>
        <!-- <div class="x-slide_left"></div> -->
        <!-- 左侧菜单结束 -->
        <!-- 右侧主体开始 -->
        <div class="page-content">
            <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
                <ul class="layui-tab-title">
                    <li class="home">
                        <i class="layui-icon">&#xe68e;</i>首页</li></ul>
                <div class="layui-unselect layui-form-select layui-form-selected" id="tab_right">
                    <dl>
                        <dd data-type="this">关闭当前</dd>
                        <dd data-type="other">关闭其它</dd>
                        <dd data-type="all">关闭全部</dd></dl>
                </div>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <iframe src='${path}/tz/first' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
                    </div>
                </div>
                <div id="tab_show"></div>
            </div>
        </div>
        <div class="page-content-bg"></div>
        <style id="theme_style"></style>
        <!-- 右侧主体结束 -->
        <!-- 中部结束 -->
    </body>

</html>