<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    
    <title>人员信息修改</title>
    
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript" src="${ctx}/resources/js/sys/user/edit.js"></script>
    
</head>
<body>
    <article class="page-container">
        <form action="${ctx}/user/updateUser" method="post" class="form form-horizontal" id="form-user-update">
            <input type="hidden" des="存放选中的角色id" id="roleIds" name="roleIds" value="${roleIds}"/>
            <input type="hidden" des="存放用户userId" value='${user.id}' id="userId" name="userId"/>
            
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>登录账户：</label>
                <div class="formControls col-xs-8 col-sm-9">
                    <input type="text" class="input-text" value="${user.userName}" placeholder="请输入登录账户" id="userName" name="userName" <c:if test="${userHaveAdminRole == true}">disabled="disabled"</c:if>>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户姓名：</label>
                <div class="formControls col-xs-8 col-sm-9">
                    <input type="text" class="input-text" value="${user.realName}" placeholder="请输入用户真实姓名" name="realName" id="realName">
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
                    <select class="select" name="status" <c:if test="${userHaveAdminRole == true}">disabled="disabled"</c:if>>
                        <c:forEach items="${userStatus}" var="s">
                            <option value="${s}" <c:if test="${user.status == s}">selected="selected"</c:if> >${s.description}</option>
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
    
    <input type="hidden" des="角色列表json字符串，这里value必须是单引号，避免与allRoles值中的双引号冲突，出现问题" name="allRoles" value='${allRoles}'/>
</body>
</html>
