<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<header class="navbar-wrapper">
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid cl"> <span class="logo navbar-logo f-l mr-10 hidden-xs" onclick="javascript:window.location.reload();">用户管理中心系统</span> 
            <span class="logo navbar-slogan f-l mr-10 hidden-xs">1.0</span> 
            <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
            <nav class="nav navbar-nav">
                <ul class="cl">
                    <li class="dropDown dropDown_hover" style="margin-left: 20px;"><a href="javascript:;" class="dropDown_A"><i class="Hui-iconfont">&#xe602;</i> CAS单点登录中央认证系统 <i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" onclick="article_add('OA办公系统','article-add.html')"><i class="Hui-iconfont">&#xe616;</i> OA办公系统</a></li>
                            <li><a href="javascript:;" onclick="picture_add('资源文件管理系统','picture-add.html')"><i class="Hui-iconfont">&#xe613;</i> 资源文件管理系统</a></li>
                            <li><a href="javascript:;" onclick="product_add('用户管理中心系统','product-add.html')"><i class="Hui-iconfont">&#xe620;</i> 用户管理中心系统</a></li>
                            <li><a href="javascript:;" onclick="member_add('支付对账系统','member-add.html','','510')"><i class="Hui-iconfont">&#xe60d;</i> 支付对账系统</a></li>
                            <li><a href="javascript:;" onclick="member_add('文件内容系统','member-add.html','','510')"><i class="Hui-iconfont">&#xe60d;</i> 文件内容系统</a></li>
                            <li><a href="javascript:;" onclick="member_add('消息服务总线系统','member-add.html','','510')"><i class="Hui-iconfont">&#xe60d;</i> 消息服务总线系统</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
            <ul class="cl">
                <li>欢迎你</li>
                <li class="dropDown dropDown_hover">
                    <a href="#" class="dropDown_A"><shiro:principal/> <i class="Hui-iconfont">&#xe6d5;</i></a>
                    <ul class="dropDown-menu menu radius box-shadow">
                        <li><a href="javascript:;" onClick="myselfinfo()">个人信息</a></li>
                        <li><a href="${ctx}/login/logout">退出</a></li>
                </ul>
            </li>
                <li id="Hui-msg"> <a href="#" title="系统消息"><span class="badge badge-danger">3</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
                <li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
                    <ul class="dropDown-menu menu radius box-shadow">
                        <li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
                        <li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
                        <li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
                        <li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
                        <li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
                        <li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
    </div>
</div>
</header>