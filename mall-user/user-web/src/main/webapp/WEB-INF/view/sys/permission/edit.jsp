<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    
    <title>权限信息修改</title>
    
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript" src="${ctx}/resources/js/sys/permission/edit.js"></script>
    
</head>
<body>
    <article class="page-container">
        <form action="${ctx}/permission/updatePermission" method="post" class="form form-horizontal" id="form-permission-edit">
            <input des="权限id" type="hidden" value="${permission.id}" name="id" id="id"/>
            <input des="存放选中的权限id" type="hidden" id="parentId" name="parentId" value="${permission.parentId}"/>
    
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限名称：</label>
                <div class="formControls col-xs-8 col-sm-9">
                    <input type="text" class="input-text" value="${permission.name}" placeholder="请输入权限名称" id="name" name="name">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限描述：</label>
                <div class="formControls col-xs-8 col-sm-9">
                    <input type="text" class="input-text" value="${permission.description}" placeholder="请输入权限描述" name="description" id="description">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限代码：</label>
                <div class="formControls col-xs-8 col-sm-9">
                    <input type="text" class="input-text" value="${permission.code}" placeholder="请输入权限代码" name="code" id="code">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限状态：</label>
                <div class="formControls col-xs-8 col-sm-9">
                    <span class="select-box">
                        <select class="select" name="status" id="status">
                            <c:forEach items="${status}" var="s">
                                <option value="${s}" <c:if test="${permission.status == s}">selected="selected"</c:if> >${s.description}</option>
                            </c:forEach>
                        </select>
                    </span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">权限级别：</label>
                <div class="formControls col-xs-8 col-sm-9">
                    <!-- 权限tree -->
                    <ul id="permissionTree" class="ztree" style="padding-left: 0px;"></ul>
                </div>
            </div>
            <div class="row cl">
                <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                    <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
                </div>
            </div>
        </form>
    </article>
    
    <script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
    <script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
    <script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/messages_zh.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/resources/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script> 
    
    <input type="hidden" name="permissions" value='${permissions}' des="权限列表json字符串，这里value必须是单引号，避免与permissions值中的双引号冲突，出现问题"/>
</body>
</html>
