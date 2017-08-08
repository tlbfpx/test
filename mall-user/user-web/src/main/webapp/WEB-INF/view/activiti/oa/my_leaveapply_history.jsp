<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    
    <title>请假历史记录</title>
    
    <!-- 静态css、js资源 -->
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript" src="${ctx}/resources/js/activiti/oa/my_leaveapply_history.js"></script>
    
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
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> activiti工作流管理 <span class="c-gray en">&gt;</span> 请假历史记录
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新选项卡" ><i class="Hui-iconfont">&#xe68f;</i></a> 
    </nav>
    <div class="page-container">
        <div class="mt-20">
            <table id="myLeaveApplyHistoryListTable" class="table table-border table-bordered table-bg table-hover table-sort table-responsive">
                <thead>
                    <tr class="text-c">
                        <th>业务key</th>
                        <th>流程实例ID</th>
                        <th>申请人</th>
                        <th>请假类型</th>
                        <th>申请时间</th>
                        <th>处理记录</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    
    
    <!-- 请假申请处理记录，以弹出框显示记录 -->
    <div class="modal fade" id="leaveApplyHandleRecordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 800px;margin-left: -15%;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">处理记录</h4>
                </div>
                <div class="modal-body">
                    <table id="leaveApplyHandleRecordTable" class="table table-condensed table-hover table-striped" style="font-size: 12px;">
                        <thead id="leaveApplyHandleRecordThead">
                        </thead>
                    </table>
                </div>
                <div class="modal-footer">
                    <!-- <button type="button" class="btn btn-default" data-dismiss="modal">我知道了</button> -->
                    <button type="button" data-dismiss="modal" class="btn btn-primary">确定</button>
                </div>
            </div>
        </div>
    </div>
    
	<input des="获取我的请假申请历史列表url" type="hidden" name="getMyLeaveApplyHistoryListUrl" id="getMyLeaveApplyHistoryListUrl" value="<c:url value="/oa/leaveapply/history/list"/>" />
	<input des="获取请假申请的处理记录" type="hidden" name="getLeaveApplyHandleRecordUrl" id="getLeaveApplyHandleRecordUrl" value="<c:url value="/oa/leaveapply/handle/record"/>" />
    <input type="hidden" des="项目根路径" name="basePathUrl" id="basePathUrl" value="${ctx}" />
    
    <!--请在下方写此页面业务相关的脚本-->
    <script type="text/javascript" src="${ctx}/resources/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
    <script type="text/javascript" src="${ctx}/resources/lib/laypage/1.2/laypage.js"></script>
</body>
</html>
