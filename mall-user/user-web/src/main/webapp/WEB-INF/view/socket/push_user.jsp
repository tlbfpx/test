<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/view/common/common.jsp"%>

<!DOCTYPE>
<html>
<head>
<title>WebSocket/SockJS 服务器推送</title>
    <style type="text/css">
        #connect-container {
            float: left;
            width: 45%;
        }
        
        #connect-container textarea{
            margin-left: 10px;
            padding: 5px;
        }
        
        #connect-container button {
            margin-left: 10px;
            margin-top: 10px;
        }
        
        #connect-container div {
            padding: 5px;
            padding-top:0px;
            margin-left: 10px;
        }

        #console-container {
            float: left;
            width: 50%;
        }

        #console {
            border: 1px solid #CCCCCC;
            border-right-color: #999999;
            border-bottom-color: #999999;
            height: 80%;
            overflow-y: scroll;
        }

        #console p {
            padding: 0;
            margin: 0;
        }
    </style>

    <script src="${ctx}/resources/lib/sockjs/0.3.4/sockjs-0.3.min.js"></script>

    <script type="text/javascript">
        var ws = null;

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('echo').disabled = !connected;
            if(connected){
            	$("#connect").removeClass("btn-success").addClass("btn-default");
                $("#disconnect").removeClass("btn-default").addClass("btn-danger");
                $("#echo").removeClass("btn-default").addClass("btn-primary");
            }else{
            	$("#connect").removeClass("btn-default").addClass("btn-success");
                $("#disconnect").removeClass("btn-danger").addClass("btn-default");
                $("#echo").removeClass("btn-primary").addClass("btn-default");
            }
        }

        function connect() {
        	log(1,"正在为您连接消息推送服务...");
            if ('WebSocket' in window) {
                ws= new WebSocket("ws://${serviceUrl}/websck?loginUserName=" + $("#userName").val());
                //console.log("ws://${serviceUrl}/websck" + $("#userName").val());
            }else if ('MozWebSocket' in window) {
                ws = new MozWebSocket("ws://${serviceUrl}/websck?loginUserName="  + $("#userName").val());
            }else {
                ws = new SockJS("http://${serviceUrl}/sockjs/websck?loginUserName=" + $("#userName").val());
                //console.log("http://${serviceUrl}/sockjs/websck" + $("#userName").val());
            }
            ws.onopen = function () {
                setConnected(true);
                log(1,"消息推送服务连接已成功...");
            };
            
            //消息监听
            ws.onmessage = function (event) {
            	log(2,"您好【" + $("#userName").val() + "】，服务器给您推送了一条消息，内容为：" + event.data);
            };
            ws.onerror = function (evnt) {
            };
            ws.onclose = function (event) {
                setConnected(false);
                log(1,"消息推送服务连接已关闭.");
                $("#connect").removeClass("btn-default").addClass("btn-success");
            };
        }

        function disconnect() {
            if (ws != null) {
                ws.close();
                ws = null;
            }
            setConnected(false);
        }

        function echo() {
            if (ws != null) {
                var message = $("#message").val();
                var userName = $("#userName").val();
                //ws.send(message);
                
                log(3,"您刚刚发送了一条自定义消息，内容为：" + message);
                sendMsg(userName,message);//发送消息给指定用户
            } else {
                log(1,"消息推送服务未建立连接，请先连接服务.");
            }
        }
        
        //发送消息
        function sendMsg(userName,message){
            $.ajax({
                type: "POST",
                url: $("#pushUserUrl").val(),
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
      
        function log(type,message) {
            var console = document.getElementById('console');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            
            //对不同类型消息进行颜色处理
            if(type == 1){//系统提示
            	var sp = document.createElement('span');
            	sp.append("[系统提示]: ");
            	sp.style.color="red";
            	sp.style.marginLeft="5px";
            	p.appendChild(sp);
            }else if(type == 2){
            	var sp = document.createElement('span');
                sp.append("[收到消息]: ");
                sp.style.color="orange";
                sp.style.marginLeft="5px";
                p.appendChild(sp);
            }else{
            	var sp = document.createElement('span');
                sp.append("[发送消息]: ");
                sp.style.color="green";
                sp.style.marginLeft="5px";
                p.appendChild(sp);
            }
            p.append(message);
            console.appendChild(p);
            while (console.childNodes.length > 25) {
                console.removeChild(console.firstChild);
            }
            console.scrollTop = console.scrollHeight;
        }
    </script>
</head>
<body>
<div>
<br>
    <div id="connect-container">
        <div>
            <textarea id="message" style="width: 90%;height: 200px;">hello！此消息是发送给自己的~</textarea>
        </div>
        <div>
            <button id="connect" onclick="connect();" class="btn btn-success radius">连接服务</button>
            <button id="disconnect" disabled="disabled" onclick="disconnect();" class="btn btn-default radius">断开连接</button>
            <button id="echo" onclick="echo();" disabled="disabled" class="btn btn-default radius">推送消息给自己</button>
        </div>
        <div style="margin-top: 30px;width: 90%;">
               <p style="margin-left: 10px;margin-top: 20px;color: red;font-weight: bold;">测试之前请认真阅读下方的功能说明：<span style="color: gray;">(此功能今后会加入到分布式服务 [消息服务总线系统]中，独立维护与管理)</span></p>
               <p style="margin-left: 10px;">1、此功能为推送消息给当前用户(也就是自己)，<span style="color: red;">请先点击连接推送服务，才可发起消息推送。</span></p>
               <p style="margin-left: 10px;">2、此功能代码中，手动编码指定了当前用户名为 <span style="color: red;">lisi</span> (非当前系统登录用户)，目的是方便功能测试。</p>
               <p style="margin-left: 10px;">3、当点击连接服务的时候，当前页面已经开始监听消息服务了，后台有消息推送过来的时候，就会实时显示在右侧控制台。</p>
        </div>
        <div style="width: 90%;">
	       <p style="margin-left: 10px;color: red;font-weight: bold;">消息推送接口测试地址(假如域名为http://server.zhoubang85.com)：</p>
	       <p style="margin-left: 10px;">推送消息给指定用户：http://server.zhoubang85.com/socket/pushUser?loginUserName=zhangsan</p>
	       <p style="margin-left: 10px;">推送消息给所有用户：http://server.zhoubang85.com/socket/pushAllUser</p>
	       <p style="margin-left: 10px;color: green;">其中，参数loginUserName表示的是推送给哪个客户端(参数值其实就是用户名，功能实现上当然不止这一个实现方式)。
	       <br/>以上接口地址可在浏览器中直接访问，结合对应的消息推送页面查看效果。</p>
	    </div>
    </div>
    <div id="console-container">
        <div id="console"></div>
    </div>
</div>

<input type="hidden" id="userName" value="lisi" des="当前在线用户的用户名"/>
<input type="hidden" id="pushUserUrl" value="${ctx}/socket/pushUser" des="推送消息给指定在线用户"/>
</body>

</html>