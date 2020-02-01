<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理平台</title>
<%@include file="/WEB-INF/views/common/base.jsp"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
<link rel="stylesheet" href="/commons/css/font.css">
<link rel="stylesheet" href="/commons/css/xadmin.css">
<link rel="stylesheet" href="/commons/css/theme5.css">
<script src="/commons/lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="/commons/js/xadmin.js"></script>
</head>
	<body>
   		<div class="layui-fluid">
            <div class="layui-row">
                <form class="layui-form" >
                <input type="hidden" id="historyInfoPoid" name="historyInfoPoid" value="${historyInfoPo.id }" class="layui-input">
                 <div class="layui-form-item">
                      <label for="nickName" class="layui-form-label">
                          <span class="x-red"></span>时间
                      </label>
                       <div class="layui-input-inline">
                       		<input type="text" id="dayTime" name="dayTime" disabled="" value="${historyInfoPo.dayTime }" class="layui-input">
                       </div>
                  </div>
                  <div class="layui-form-item">
                      <label for="nickName" class="layui-form-label">
                          <span class="x-red"></span>现金交易人数
                      </label>
                       <div class="layui-input-inline">
                       		<input type="text" id="moneyRechargeNum" name="moneyRechargeNum" value="${historyInfoPo.moneyRechargeNum }" class="layui-input">
                       </div>
                  </div>
                  <div class="layui-form-item">
                      <label for="channelName" class="layui-form-label">
                          <span class="x-red"></span>首充金额
                      </label>
                       <div class="layui-input-inline">
                            <input type="text" id="fristMoney" name="fristMoney" value="${historyInfoPo.fristMoney }" class="layui-input">
                       </div>
                  </div>
                  <div class="layui-form-item">
                      <label for="registNum" class="layui-form-label">
                          <span class="x-red">*</span>首充人数
                      </label>
                      <div class="layui-input-inline">
                          <input type="text" id="fristNum" name="fristNum" required="" lay-verify="required"
                          autocomplete="off" class="layui-input" value="${historyInfoPo.fristNum }">
                      </div>
                  </div>
                  <div class="layui-form-item">
                      <label for="applicantsNum" class="layui-form-label">
                          <span class="x-red">*</span>注册人数
                      </label>
                      <div class="layui-input-inline">
                          <input type="text" id="regiestNum" name="regiestNum" disabled="" required="" lay-verify="applicantsNum"
                          autocomplete="off" class="layui-input" value="${historyInfoPo.regiestNum }">
                      </div>
                  </div>
                  <div class="layui-form-item">
                      <label for="loanNum" class="layui-form-label">
                          <span class="x-red">*</span>用券人数
                      </label>
                      <div class="layui-input-inline">
                          <input type="text" id="useTicktNum" name="useTicktNum" required="" lay-verify="loanNum"
                          autocomplete="off" class="layui-input" value="${historyInfoPo.useTicktNum }">
                      </div>
                  </div>
               	   
                  <div class="layui-form-item">
                      <label for="L_repass" class="layui-form-label">
                      </label>
                      <button  class="layui-btn" lay-filter="add" lay-submit="">
                          	修改
                      </button>
                  </div>
              </form>
            </div>
        </div>
        <script>
        layui.use(['form', 'layer'],
            function() {
                $ = layui.jquery;
                var form = layui.form,
                layer = layui.layer;

                //自定义验证规则
                form.verify({
                    /* nikename: function(value) {
                        if (value.length < 5) {
                            return '昵称至少得5个字符啊';
                        }
                    },
                    pass: [/(.+){6,12}$/, '密码必须6到12位'],
                    repass: function(value) {
                        if ($('#L_pass').val() != $('#L_repass').val()) {
                            return '两次密码不一致';
                        }
                    } */
                });

                //监听提交
                form.on('submit(add)',
                function(data) {
                    console.log(data.field);
                    //发异步，把数据提交给php
                    $.ajax({
		    			type : "POST",
		    			url : "/tz/admin/edithistory",
		    			dataType : "json",
		    			data : data.field,
		    			success : function(res) {
		    				if(res.success){
		    					layer.alert("修改成功", {
		                            icon: 6
		                         },
		                         function() {
		                             //关闭当前frame
		                             xadmin.close();

		                             // 可以对父窗口进行刷新 
		                             xadmin.father_reload();
		                            
		                         }); 
		    				}else{
		    					layer.alert(res.msg, {
		                            icon: 6
		                         },
		                         function() {
		                             //关闭当前frame
		                             xadmin.close();

		                             // 可以对父窗口进行刷新 
		                             xadmin.father_reload();
		                            
		                         }); 
		    				}
		    			},
		    			error : function(data) {
		    				alert("修改失败");
		    			}
		    		}); 
                    
                    return false;
                });
                    
            });</script>
	</body>
</html>