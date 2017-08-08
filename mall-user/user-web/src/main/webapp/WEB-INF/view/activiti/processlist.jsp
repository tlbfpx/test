<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    
    <title>已部署的工作流列表</title>
    
    <!-- 静态css、js资源 -->
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript" src="${ctx}/resources/js/activiti/processlist.js"></script>
    
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
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> activiti工作流管理 <span class="c-gray en">&gt;</span> 已部署的工作流列表
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新选项卡" ><i class="Hui-iconfont">&#xe68f;</i></a> 
        <!-- <a class="btn btn-danger radius r" style="line-height:1.6em;margin-top:3px;margin-right: 10px;" href="javascript:removeIframe();" title="关闭选项卡" ><i class="Hui-iconfont">&#xe6a6;</i></a> -->
    </nav>
    <div class="page-container">
        <div class="f-14 c-green">上传流程定义文件（请上传 *.bpmn 类型文件）</div>
        <div class="text-l" style="margin-top: 10px;">
	        <form id="upload" class="Huiform" method="post" action="${ctx}/activiti/uploadWorkFlow" target="_self" enctype="multipart/form-data">
	            <span class="btn-upload form-group">
	               <input class="input-text upload-url" type="text" name="uploadfile-2" id="uploadfile-2" readonly="" style="width:200px">
	               <a href="javascript:void();" class="btn btn-primary upload-btn"><i class="Hui-iconfont"> &#xe695;</i> 选择文件（*.bpmn）</a>
	               <input type="file" name="uploadFile"  id="fileupload" class="input-file" accept=".bpmn">
	            </span>
	            <button type="submit" class="btn btn-success"><i class="Hui-iconfont">&#xe642;</i> 上传</button>
	        </form>
	    </div>
	    <hr style="margin-top: 30px;border-style: solid;border-color: rgb(222, 222, 222);border-width: 1px;"/>
        <div class="mt-20" style="margin-top: 30px;">
            <table id="processListTable" class="table table-border table-bordered table-bg table-hover table-sort table-responsive">
                <thead>
                    <tr class="text-c">
                        <th width="80">ID</th>
                        <th>DeploymentId</th>
                        <th>name</th>
                        <th>ResourceName</th>
                        <th>diagramResourceName</th>
                        <th>key</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    
	<input des="删除工作流实例url" type="hidden" name="deleteUserUrl" id="deleteProcessUrl" value="${ctx}/activiti/deleteProcess" />
	<input des="获取已部署的工作流列表url" type="hidden" name="getProcessListUrl" id="getProcessListUrl" value="${ctx}/activiti/processList" />
	<input des="查看文件内容url" type="hidden" name="showProcessResourceUrl" id="showProcessResourceUrl" value="${ctx}/activiti/showProcessResource" />
    <input type="hidden" des="项目根路径" name="basePathUrl" id="basePathUrl" value="${ctx}" />
    
    <!--请在下方写此页面业务相关的脚本-->
    <script type="text/javascript" src="${ctx}/resources/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
    <script type="text/javascript" src="${ctx}/resources/lib/laypage/1.2/laypage.js"></script>
</body>
</html>
