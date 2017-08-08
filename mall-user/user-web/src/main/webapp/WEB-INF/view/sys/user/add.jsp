<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    
    <title>新增用户</title>
    
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript" src="${ctx}/resources/js/sys/user/add.js"></script>
</head>
<body>
	<article class="page-container">
	    <form action="${ctx}/user/addUser" method="post" class="form form-horizontal" id="form-user-add">
            <!-- 存放选中的角色id -->
            <input type="hidden" id="roleIds" name="roleIds" value="${roleIds}"/>
        
	        <div class="row cl">
	            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>登录账户：</label>
	            <div class="formControls col-xs-8 col-sm-9">
	                <input type="text" class="input-text" value="" placeholder="请输入登录账户" id="userName" name="userName">
	            </div>
	        </div>
	        <div class="row cl">
	            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>密码：</label>
	            <div class="formControls col-xs-8 col-sm-9">
	                <input type="password" class="input-text" value="" placeholder="请输入登录密码" id="password" name="password">
	            </div>
	        </div>
	        <div class="row cl">
	            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户姓名：</label>
	            <div class="formControls col-xs-8 col-sm-9">
	                <input type="text" class="input-text" placeholder="请输入用户真实姓名" name="realName" id="realName">
	            </div>
	        </div>
	        <div class="row cl">
	            <label class="form-label col-xs-4 col-sm-3">所属角色：</label>
	            <div class="formControls col-xs-8 col-sm-9">
                    <!-- 角色tree  start -->
	                <ul id="roleTree" class="ztree" style="padding-top: 5px;padding-left: 0px;"></ul>
	                <!-- 角色tree  end -->
	            </div>
	        </div>
	        <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">账户状态：</label>
                <div class="formControls col-xs-8 col-sm-9"> <span class="select-box">
                    <select class="select" name="status">
                        <c:forEach items="${userStatus}" var="s">
                            <option value="${s}">${s.description}</option>
                        </c:forEach>
                    </select>
                    </span> </div>
            </div>
	        <div class="row cl">
	            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
	                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	            </div>
	        </div>
	    </form>
	</article>
	
	<!--请在下方写此页面业务相关的脚本--> 
	<script type="text/javascript" src="${ctx}/resources/lib/My97DatePicker/4.8/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
	<script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
	<script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/messages_zh.js"></script>
	<link rel="stylesheet" href="${ctx}/resources/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${ctx}/resources/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script> 
	
    <input type="hidden" name="allRoles" value='${allRoles}' des="角色列表json字符串，这里value必须是单引号，避免与allRoles值中的双引号冲突，出现问题"/>
</body>
</html>
