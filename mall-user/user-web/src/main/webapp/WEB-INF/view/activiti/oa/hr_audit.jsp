<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    
    <title>人事审批</title>
    
    <!-- 静态css、js资源 -->
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript" src="${ctx}/resources/js/activiti/oa/hr_audit.js"></script>
    
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
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> activiti工作流管理 <span class="c-gray en">&gt;</span> 人事审批
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新选项卡" ><i class="Hui-iconfont">&#xe68f;</i></a> 
    </nav>
    <div class="page-container">
        <div class="mt-20">
            <table id="hrAuditListTable" class="table table-border table-bordered table-bg table-hover table-sort table-responsive">
                <thead>
                    <tr class="text-c">
                        <th>申请人</th>
                        <th>请假类型</th>
                        <th>请假开始时间</th>
                        <th>请假结束时间</th>
                        <th>请假原因</th>
                        <th>任务ID</th>
                        <th>任务名称</th>
                        <th>流程实例ID</th>
                        <th>任务创建时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    
	<input des="获取人事需要审批的请假流程列表url" type="hidden" name="getHrAuditListUrl" id="getHrAuditListUrl" value="<c:url value="/oa/hr/auditlist"/>" />
	<input des="审批请假申请页面url" type="hidden" name="toHrAuditHandleUrl" id="toHrAuditHandleUrl" value="<c:url value="/oa/hr/audit/view"/>" />
    <input type="hidden" des="项目根路径" name="basePathUrl" id="basePathUrl" value="${ctx}" />
    
    <!--请在下方写此页面业务相关的脚本-->
    <script type="text/javascript" src="${ctx}/resources/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
    <script type="text/javascript" src="${ctx}/resources/lib/laypage/1.2/laypage.js"></script>
</body>
</html>
