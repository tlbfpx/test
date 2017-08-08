<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<link rel="Bookmark" href="${ctx}/resources/images/favicon.ico" >
	<link rel="Shortcut Icon" href="${ctx}/resources/images/favicon.ico" />
	
	<title>用户管理中心系统</title>
	<meta name="keywords" content="java后台管理系统,hui,web">
	<meta name="description" content="个人开发的java后台管理系统——用户管理中心。分布式服务的某个服务模块。更多项目介绍请浏览：http://git.oschina.net/zhoubang85/zb ">
	
	<%@ include file="/WEB-INF/view/common/common.jsp"%>
	
	<script src="https://cdn.bootcss.com/jquery-migrate/1.1.1/jquery-migrate.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/lib/custom/rb-pop.js"></script>
    <script src="${ctx}/resources/lib/sockjs/0.3.4/sockjs-0.3.min.js"></script>
    
    <style type="text/css">
        *{margin:0;padding:0;}
        #pop{background:#fff;width:260px;border:1px solid #C1C1C1;font-size:12px;position: fixed;right:10px;bottom:10px;z-index: 999999999;}
        #popHead{line-height:15px;background:#f6f0f3;border-bottom:1px solid #e0e0e0;position:relative;font-size:12px;padding:0 0 0 10px;}
        #popHead h5{font-size:12px;color:#666;line-height:15px;height:15px;}
        #popHead #popClose{position:absolute;right:10px;top:1px;}
        #popHead a#popClose:hover{color:#f00;cursor:pointer;}
        #popContent{padding:5px 10px;}
        #popTitle a{line-height:24px;font-size:14px;font-family:'微软雅黑';color:#333;font-weight:bold;text-decoration:none;}
        #popTitle a:hover{color:#f60;}
        #popIntro{text-indent:20px;line-height:160%;margin:5px 0;color:#666;}
        #popMore{text-align:right;border-top:1px dotted #ccc;line-height:24px;margin:8px 0 0 0;}
        #popMore a{color:#f60;}
        #popMore a:hover{color:#f00;}
        .layui-layer-btn-c{text-align: center;}
    </style>
    
    <script type="text/javascript">
        var userName = null;
        var sessionId = null;
        $(document).ready(function(){
        	userName= $("#userName").val();
        	sessionId = $("#sessionId").val();
            connect();//连接系统消息推送服务
            account_offline_service_connect();//账户下线通知服务
            
            var date = new Date();
            var year = date.getFullYear();
            var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
            var day = date.getDate();
            var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            var minute =  date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            var timeDate = year+'-' + month+'-' + day + "&nbsp;" + hour + ':' + minute + ':' + second;
            
            //accountOfflineNotice(userName + "_" + sessionId , "您好，当前账号【" + userName + "】 已在其他客户端登录，您的此次会话将要被迫下线，谢谢配合。<br/><br/>登录操作时间：" + timeDate);
            
            clientLoginServiceConnect();//客户端登陆消息推送服务
        });
    </script>
    <script type="text/javascript">
        function connect() {
        	var ws = null;
            if ('WebSocket' in window) {
                ws= new WebSocket("ws://" + $("#serviceUrl").val() + "/websck?loginUserName=" + userName);
            }else if ('MozWebSocket' in window) {
                ws = new MozWebSocket("ws://" + $("#serviceUrl").val() + "/websck?loginUserName=" + userName);
            }else {
                ws = new SockJS("http://" + $("#serviceUrl").val() + "/sockjs/websck?loginUserName=" + userName);
            }
            ws.onopen = function () {
            	//console.log("消息推送服务已连接");
            };
            
            //消息监听
            ws.onmessage = function (event) {
                //console.log("收到了一条推送消息：" + event.data);
                var pop=new Pop("收到一条推送消息", "",event.data);
            };
            ws.onerror = function (evnt) {
            };
            ws.onclose = function (event) {
            	//console.log("socket服务连接已关闭");
            	var pop=new Pop("消息推送服务连接已关闭", "",event.data);
            };
        }
        
        //客户端登陆消息推送服务
        function clientLoginServiceConnect() {
            var ws = null;
            if ('WebSocket' in window) {
                ws= new WebSocket("ws://" + $("#serviceUrl").val() + "/websck_notice_client_login");
            }else if ('MozWebSocket' in window) {
                ws = new MozWebSocket("ws://" + $("#serviceUrl").val() + "/websck_notice_client_login");
            }else {
                ws = new SockJS("http://" + $("#serviceUrl").val() + "/sockjs/websck_notice_client_login");
            }
            ws.onopen = function () {
                console.log("新客户端登录消息推送通知已连接");
            };
            
            //消息监听
            ws.onmessage = function (event) {
                console.log("有新客户端登录了系统:" + event.data);
                var pop=new Pop("有新客户端登录了系统", "",event.data);
            };
            ws.onerror = function (evnt) {
            };
            ws.onclose = function (event) {
                console.log("客户端登陆消息推送服务连接已关闭:" + event.data);
                var pop=new Pop("客户端登陆消息推送服务连接已关闭", "",event.data);
            };
        }
    </script>
    
    
    <script type="text/javascript">
	    function account_offline_service_connect() {
	        var ws = null;
	        if ('WebSocket' in window) {
	            ws= new WebSocket("ws://" + $("#serviceUrl").val() + "/websck_notice_offline?loginUserName=" + userName + "_" + sessionId);
	        }else if ('MozWebSocket' in window) {
	            ws = new MozWebSocket("ws://" + $("#serviceUrl").val() + "/websck_notice_offline?loginUserName=" + userName + "_" + sessionId);
	        }else {
	            ws = new SockJS("http://" + $("#serviceUrl").val() + "/sockjs/websck_notice_offline?loginUserName=" + userName + "_" + sessionId);
	        }
	        ws.onopen = function () {
	            //console.log("消息推送服务已连接");
	        };
	        
	        //消息监听
	        ws.onmessage = function (event) {
	            //var pop=new Pop("收到一条推送消息", "",event.data);
	        	//示范一个公告层
	        	layer.open({
	        	    type: 1,
	        	    title: ["账号下线通知","line-height:30px;height:30px;color:#EB1C27;font-weight:bold;"], //不显示标题栏
	        	    closeBtn: false,
	        	    area: "450px;",
	        	    shade: 0.8,
	        	    id: 'LAY_layuipro', //设定一个id，防止重复弹出
	        	    resize: false,
	        	    btn: ["哎呀妈呀，退出系统吧"],
	        	    btnAlign: 'c',
	        	    moveType: 1, //拖拽模式，0或者1
	        	    content: '<div style="padding: 50px; line-height: 25px; background-color: #393D49; color: #fff;">' + event.data + '</div>',
	        	    success: function(layero){
	        	    	  var btn = layero.find('.layui-layer-btn');
	        	    	  btn.find('.layui-layer-btn0').attr({href: "/login/logout",target: ""}).parent().addClass("layui-layer-btn-c");
	        	    }
	        	});
	        };
	        ws.onerror = function (evnt) {
	        };
	        ws.onclose = function (event) {
	            var pop=new Pop("消息推送服务连接已关闭", "",event.data);
	        };
	    }
	    
	    //通知当前账户在其他客户端登录的会话下线操作
        function accountOfflineNotice(userName,message){
            $.ajax({
                type: "POST",
                url: $("#accountOfflineNoticeUrl").val(),
                cache: false,  //禁用缓存
                data: {
                    "message" : message,
                    "loginUserName" : userName
                },
                dataType: "json",
                timeout:10000,
                success: function (result) {
                    if(result.code == 200){
                        //推送消息成功
                    }
                }
            });
        }
    </script>
</head>
<body>
    <!-- 页面顶部¨ -->
    <%@ include file="head.jsp"%>
    
    <!-- 左侧菜单 -->
    <%@ include file="left.jsp"%>
    
		
    <div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
    <section class="Hui-article-box">
        <div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
            <div class="Hui-tabNav-wp">
	            <ul id="min_title_list" class="acrossTab cl">
	                <li class="active">
                        <span title="我的桌面" data-href="welcome.html">我的桌面</span>
                        <em></em>
                    </li>
               </ul>
            </div>
            <div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
        </div>
        <div id="iframe_box" class="Hui-article">
            <div class="show_iframe">
                <div style="display:none" class="loading"></div>
                <iframe scrolling="yes" frameborder="0" src="${ctx}/main"></iframe>
           </div>
        </div>
    </section>
    	
    <div class="contextMenu" id="Huiadminmenu">
        <ul>
            <li id="closethis">关闭当前 </li>
            <li id="closeall">关闭全部 </li>
       </ul>
    </div>
	
	
	<!--右下角pop弹窗 end-->
    <div id="pop" style="display:none;">
        <div id="popHead">
           <a id="popClose" title="关闭">关闭</a>
           <h5>温馨提示</h5>
        </div>
        <div id="popContent">
            <dl>
                <dt id="popTitle"><a href="javascript:void(0);" target="_blank"></a></dt>
                <dd id="popIntro">欢迎您！</dd>
            </dl>
            <p id="popMore"><a href="javascript:void(0);" target="_blank">查看 »</a></p>
        </div>
    </div>
	 
	<script type="text/javascript" src="${ctx}/resources/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
	<script type="text/javascript">
	   /*个人信息*/
		function myselfinfo(){
		    layer.open({
		        type: 1,
		        area: ['300px','200px'],
		        fix: false, //不固定
		        maxmin: true,
		        shade:0.4,
		        title: '查看信息',
		        content: '<div>管理员信息</div>'
		    });
		}
	</script>
	
	<input type="hidden" id="serviceUrl" value="${serviceUrl}" des="socket服务器域名地址"/>
	<input type="hidden" id="sessionId" value="${sessionId}" des="当前登录用户sessionId"/>
	<input type="hidden" id="userName" value="${userName}" des="当前在线用户的用户名"/>
	<input type="hidden" id="accountOfflineNoticeUrl" value="${ctx}/socket/accountOfflineNotice" des="通知当前相同账户在其他客户端登陆的会话下线通知"/>
</body>
</html>
