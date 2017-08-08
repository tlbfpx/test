<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
    
    <title>人员列表</title>
	
    <!-- 静态css、js资源 -->
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript" src="${ctx}/resources/js/sys/user/user.js"></script>
    
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
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 用户管理 <span class="c-gray en">&gt;</span> 用户列表  
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新选项卡" ><i class="Hui-iconfont">&#xe68f;</i></a> 
        <!-- <a class="btn btn-danger radius r" style="line-height:1.6em;margin-top:3px;margin-right: 10px;" href="javascript:removeIframe();" title="关闭选项卡" ><i class="Hui-iconfont">&#xe6a6;</i></a> -->
	</nav>
	<div class="page-container">
	    <div class="text-c" style="text-align: right;">
            <span>所属角色：</span>
            <!-- <button onclick="removeIframe()" class="btn btn-primary radius">关闭选项卡</button> -->
            <span class="select-box inline" style="margin-right: 20px;">
                <select  name="roleId" id="roleId" class="select">
                    <option value="">全部</option>
                        <c:forEach items="${roles}" var="role">
                            <option value="${role.id}">${role.name}</option>
                        </c:forEach>
                </select>
            </span>
            <!-- <button onclick="removeIframe()" class="btn btn-primary radius">关闭选项卡</button> -->
            <span>账户状态：</span>
            <span class="select-box inline" style="margin-right: 20px;">
                <select name="status" id="status" class="select">
		            <option value="">全部</option>
                    <c:forEach items="${status}" var="s">
                        <option value="${s}">${s.description}</option>
                    </c:forEach>
		        </select>
	        </span>
	        <!-- 
	        <span>创建时间范围：</span>
	        <input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'logmax\')||\'%y-%M-%d\'}' })" id="logmin" class="input-text Wdate" style="width:120px;">
	        -
	        <input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'logmin\')}',maxDate:'%y-%M-%d' })" id="logmax" class="input-text Wdate" style="width:120px;margin-right: 20px;">
	         -->
	         
	        <span>人员姓名：</span>
            <input type="text" name="realName" id="realName" placeholder="" style="width:120px" class="input-text">
	        <button name="searchBtn" id="searchBtn" style="margin-left: 20px;" class="btn btn-success radius" type="submit"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	    </div>
	    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="batchDeleteUser()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a class="btn btn-primary radius" data-title="添加用户" onClick="userAdd('添加用户','${ctx}/user/toAddView','','','510')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a></span> <!-- <span class="r">共有数据：<strong>54</strong> 条</span> --> </div>
	    <div class="mt-20">
	        <table id="userListTable" class="table table-border table-bordered table-bg table-hover table-sort table-responsive">
	            <thead>
	                <tr class="text-c">
	                    <th width="80"><input type="checkbox" id="selectAll" value="" /></th>
	                    <th width="80">ID</th>
	                    <th>账户名</th>
	                    <th>真实姓名</th>
	                    <th>角色名称</th>
	                    <th>系统来源</th>
	                    <th>账户状态</th>
	                    <th>创建时间</th>
	                    <th>最近更新时间</th>
	                    <th>操作</th>
	                </tr>
	            </thead>
	            <tbody>
	            </tbody>
	        </table>
	    </div>
	</div>
	
	<input type="hidden" des="获取用户列表url" id="getUserListUrl" value="${ctx}/user/list"/>
    <input type="hidden" des="删除用户url" name="deleteUserUrl" id="deleteUserUrl" value="<c:url value="/user/deleteUser"/>" />
    <input type="hidden" des="批量删除用户url" name="deleteUsersUrl" id="deleteUsersUrl" value="<c:url value="/user/deleteUsers"/>" />
    <input type="hidden" des="编辑用户url" name="toEditUserUrl" id="toEditUserUrl" value="<c:url value="/user/toEditView"/>" />
    <shiro:hasPermission name="user:edit">
        <input type="hidden" des="是否拥有用户编辑权限" id="haveUserEditPermission" value="true"/>
    </shiro:hasPermission>
    <shiro:hasPermission name="user:delete">
        <input type="hidden" des="是否拥有用户删除权限" id="haveUserDeletePermission" value="true"/>
    </shiro:hasPermission>
    
    <input type="hidden" des="项目根路径" name="basePathUrl" id="basePathUrl" value="${ctx}" />
    
	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript" src="${ctx}/resources/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
	<script type="text/javascript" src="${ctx}/resources/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="${ctx}/resources/lib/laypage/1.2/laypage.js"></script>
</body>
</html>
