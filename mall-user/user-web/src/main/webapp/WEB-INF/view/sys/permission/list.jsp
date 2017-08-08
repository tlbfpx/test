<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    
    <title>权限列表</title>
    
    <!-- 静态css、js资源 -->
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript" src="${ctx}/resources/js/sys/permission/permission.js"></script>
    
    <style type="text/css">
        .float_left_l {
            float : left;
            display: inline;
            padding-top: 10px;
        }
        .float_left_i {
            float : left;
            display: inline;
            margin-left: 20px;
        }
        .float_left_r{
            padding-top: 10px;
            margin-left: 30px;
            float: left;
        }
        
        /*当查询数据为空的时候，让提示信息居中显示，覆盖yui默认的td左对齐样式*/
        .table td{
            text-align: center;
        }
    </style>
</head>
<body>
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 权限管理 <span class="c-gray en">&gt;</span> 权限列表  
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新选项卡" ><i class="Hui-iconfont">&#xe68f;</i></a> 
    </nav>
    <div class="page-container">
        <div class="text-c" style="text-align: right;">
            <span>权限名称：</span>
            <input type="text" name="name" id="name" placeholder="" style="width:120px" class="input-text">
            
            <span>权限状态：</span>
            <span class="select-box inline" style="margin-right: 20px;">
                <select name="status" id="status" class="select">
                    <option value="">全部</option>
                    <c:forEach items="${status}" var="s">
                        <option value="${s}">${s.description}</option>
                    </c:forEach>
                </select>
            </span>
            
            <button name="searchBtn" id="searchBtn" style="margin-left: 20px;" class="btn btn-success radius" type="submit"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
        </div>
        <div class="cl pd-5 bg-1 bk-gray mt-20">
            <span class="l">
                <shiro:hasPermission name="role:add"> 
                    <a class="btn btn-primary radius" data-title="新增权限" onClick="permissionAdd('新增权限','${ctx}/permission/toAddView','','','700')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 新增权限</a>
                </shiro:hasPermission>
            </span> 
        </div>
        <div class="mt-20">
            <table id="permissionListTable" class="table table-border table-bordered table-bg table-hover table-sort table-responsive">
                <thead>
                    <tr class="text-c">
                        <th width="80">ID</th>
                        <th>权限名称</th>
                        <th>权限描述</th>
                        <th>父级权限</th>
                        <th>权限状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    
    
    
    <input type="hidden" des="获取权限列表url" id="getPermissionListUrl" value="${ctx}/permission/list"/>
    <input type="hidden" des="编辑权限页面url" id="toEditPermissionUrl" value="${ctx}/permission/editPermissionView"/>
    <input type="hidden" des="删除权限url" id="deletePermissionUrl" value="${ctx}/permission/deletePermission"/>
    
    <!-- 标识是否有权限删除、编辑权限 -->
    <shiro:hasPermission name="permission:edit">
        <input type="hidden" des="是否拥有编辑权限的权限" id="havePermissionEditPermission" value="true"/>
    </shiro:hasPermission>
    <shiro:hasPermission name="permission:delete">
        <input type="hidden" des="是否拥有删除权限的权限" id="havePermissionDeletePermission" value="true"/>
    </shiro:hasPermission>
    
    <input type="hidden" des="项目根路径" name="basePathUrl" id="basePathUrl" value="${ctx}" />
    
    <script type="text/javascript" src="${ctx}/resources/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
    <script type="text/javascript" src="${ctx}/resources/lib/laypage/1.2/laypage.js"></script>
</body>
</html>
