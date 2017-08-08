<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="ctx" value="<%=basePath %>"/>

<link rel="stylesheet" type="text/css" href="${ctx}/resources/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/static/h-ui.admin/css/style.css" />
    
<script type="text/javascript" src="${ctx}/resources/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/resources/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${ctx}/resources/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/static/h-ui.admin/js/H-ui.admin.js"></script> 

<!--[if lt IE 9]>
<script type="text/javascript" src="${ctx}/resources/lib/html5shiv.js"></script>
<script type="text/javascript" src="${ctx}/resources/lib/respond.min.js"></script>
<![endif]-->

<!--[if IE 6]>
<script type="text/javascript" src="${ctx}/resources/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<!--/meta 作为公共模版分离出去-->



<!-- 提示信息插件 -->
<link rel="stylesheet" href="${ctx}/resources/lib/msgbox/msgbox.css" />
<script type="text/javascript" src="${ctx}/resources/lib/msgbox/msgbox.js"></script>

<style>

.btn{
    border-radius:3px;
}
.content-pane > .content{
    font-size: 12px;
}

.tr_lock_color{
    background-color: khaki;
}
.tr_disable_color{
    background-color: #FFB6C1;
}
</style>



<!-- 确认信息提示插件 -->
<link href="${ctx}/resources/lib/jquery-confirm/jquery-confirm.css" rel="stylesheet" type="text/css"/>
<!-- 参考 http://demo.jb51.net/js/2016/jquery_confirm/  -->
<script src="${ctx}/resources/lib/jquery-confirm/jquery-confirm.js"></script>


<script type="text/javascript">
	//jquery datatable通用的提示信息配置
	var lang = {
	    "sProcessing": "请求处理中...",
	    "sLengthMenu": "每页 _MENU_ 条",
	    "sZeroRecords": "没有匹配结果",
	    "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项",
	    "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
	    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	    "sInfoPostFix": "",
	    "sSearch": "搜索:",
	    "sUrl": "",
	    "sEmptyTable": "无符合条件的记录",
	    "sLoadingRecords": "载入中...",
	    "sInfoThousands": ",",
	    "oPaginate": {
	        "sFirst": "首页",
	        "sPrevious": "上页",
	        "sNext": "下页",
	        "sLast": "末页",
	        "sJump": "跳转"
	    }
	};
</script>