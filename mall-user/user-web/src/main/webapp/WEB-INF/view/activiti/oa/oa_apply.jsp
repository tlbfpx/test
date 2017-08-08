<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    
    <title>OA请假申请</title>
    
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript" src="${ctx}/resources/js/activiti/oa/oa_apply.js"></script>
</head>
<body>
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> activiti工作流管理 <span class="c-gray en">&gt;</span> OA请假申请
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新选项卡" ><i class="Hui-iconfont">&#xe68f;</i></a> 
    </nav>
    
    <article class="page-container">
        <form action="${ctx}/oa/saveOaApply" method="post" class="form form-horizontal" id="oaApplyForm">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-1">请假类型：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <span class="select-box">
	                    <select class="select" name="type" id="type">
	                        <c:forEach items="${leaveTypeList}" var="type">
	                            <option value="${type}">${type.description}</option>
	                        </c:forEach>
	                    </select>
                    </span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-1">开始日期：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" onfocus="WdatePicker({minDate:'%y-%M-%d', maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}' })" id="startDate" name="startDate" class="input-text Wdate">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-1">结束日期：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'startDate\')}'})" id="endDate" name="endDate" class="input-text Wdate">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-1"><span class="c-red">*</span>请假理由：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <textarea rows="10" cols="100" id="reason" name="reason"></textarea>
                </div>
            </div>
            <div class="row cl">
                <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-1">
                    <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
                    <input style="margin-left: 20px;" class="btn btn-danger radius" type="reset" value="&nbsp;&nbsp;重置&nbsp;&nbsp;">
                </div>
            </div>
        </form>
    </article>
    
    <!--请在下方写此页面业务相关的脚本--> 
    <script type="text/javascript" src="${ctx}/resources/lib/My97DatePicker/4.8/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
    <script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
    <script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/messages_zh.js"></script>
    <script type="text/javascript" src="${ctx}/resources/lib/laypage/1.2/laypage.js"></script>
    
</body>
</html>
