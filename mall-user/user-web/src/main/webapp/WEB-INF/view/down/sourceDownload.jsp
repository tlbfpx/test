<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE html>
<head>
<title>项目源码下载</title>
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/WEB-INF/view/common/common.jsp"%>
<link href="${ctx}/resources/css/style.css" rel="stylesheet">
</head>
<body>
	<div class="page-content clearfix">
		<div class="alert alert-block alert-success">
			<button type="button" class="close" data-dismiss="alert">
				<i class="icon-remove"></i>
			</button>
			<i class="icon-bullhorn green"></i><strong class="green" style="margin-left: 10px;">以下项目资源均为本人整理，并通过测试成功运行，为需要学习的朋友提供很好的学习资源。</strong>
			<strong style="margin-left: 10px;">【免费分享，提倡开源】</strong>
		</div>
		<div class="alert alert-block alert-success">
			<button type="button" class="close" data-dismiss="alert">
				<i class="icon-remove"></i>
			</button>
			<i class="icon-bullhorn orange"></i><strong class="orange"
				style="margin-left: 10px;">以下资源均分享于百度云盘，如若发现链接不可用，请联系QQ
				842324724 或者Email：842324724@qq.com</strong>
		</div>
		<div class="state-overview clearfix">
            <c:forEach items="${sourceList}" var="source">
                <div class="col-lg-3 col-sm-6">
	                <section class="panel">
	                    <a target="_blank" href="${source.downUrl}"
	                        title="点击即可下载">
	                        <div class="symbol terques">
	                            <i class="${source.iconName}"></i>
	                        </div>
	                        <div class="value" style="width: 70%; padding-top: 40px;">
	                            <p style="font-size: 12px; color: #000000;">${source.name}</p>
	                        </div>
	                    </a>
	                </section>
	            </div>
            </c:forEach>
		</div>
	</div>
</body>
</html>
