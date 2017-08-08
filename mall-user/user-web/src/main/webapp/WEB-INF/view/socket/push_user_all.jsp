<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<html>
<head>
    <title>websocket服务器消息推送</title>

    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    
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
	    function setConnected(connected) {
	        document.getElementById('connect').disabled = connected;
	        document.getElementById('disconnect').disabled = !connected;
	        document.getElementById('sendMsg').disabled = !connected;
	        if(connected){
	            $("#connect").removeClass("btn-success").addClass("btn-default");
	            $("#disconnect").removeClass("btn-default").addClass("btn-danger");
	            $("#sendMsg").removeClass("btn-default").addClass("btn-primary");
	        }else{
	            $("#connect").removeClass("btn-default").addClass("btn-success");
	            $("#disconnect").removeClass("btn-danger").addClass("btn-default");
	            $("#sendMsg").removeClass("btn-primary").addClass("btn-default");
	        }
	    }
    
    
    
	    var ws = null;
	    function connect() {
	    	var userName = $("#userName").val();
	    	log(1,"正在为您连接消息推送服务...");
	        if ('WebSocket' in window) {
	            ws= new WebSocket("ws://" + $("#serviceUrl").val() + "/websck?loginUserName=" + userName);
	            //console.log("ws://" + $("#serviceUrl").val() + "/websck?loginUserName=" + userName);
	        }else if ('MozWebSocket' in window) {
	            ws = new MozWebSocket("ws://" + $("#serviceUrl").val() + "/websck?loginUserName=" + userName);
	        }else {
	            ws = new SockJS("http://" + $("#serviceUrl").val() + "/sockjs/websck?loginUserName=" + userName);
	            //console.log("http://" + $("#serviceUrl").val() + "/sockjs/websck?loginUserName=" + userName);
	        }
	        ws.onopen = function () {
	        	log(1,"消息推送服务已成功连接...");
	        	setConnected(true);
	        };
	        
	        //消息监听
	        ws.onmessage = function (event) {
	            //var pop=new Pop("收到了一条服务器推送消息", "http://www.2b2b92b.com",event.data);
	            log(2,"您好【" + userName + "】，服务器给您推送了一条消息，内容为： " + event.data);
	        };
	        ws.onerror = function (evnt) {
	        	setConnected(false);
	        };
	        ws.onclose = function (event) {
	            //console.log("socket服务连接已关闭.");
	            log(1,"您好【" + userName + "】，消息推送服务连接已关闭");
	            setConnected(false);
	        };
	    }
        
	    
	    //关闭连接
        function disconnect() {
            if (ws != null) {
                ws.close();
                ws = null;
            }
            setConnected(false);
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
        
        //发送消息
        function sendMsg(){
        	var message = $("#message").val();
        	log(3,"您刚刚发送了一条自定义消息，内容为：" + message);
        	$.ajax({
                type: "POST",
                url: $("#pushAllUserUrl").val(),
                cache: false,  //禁用缓存
                data: {
                	"message" : message
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
    <div>
        <br>
	    <div id="connect-container">
	        <div>
	            <textarea id="message" style="width: 90%;height: 200px;">调皮的二蛋儿，刚刚给你们所有人发了一条消息，哈哈！</textarea>
	        </div>
	        <div>
                <button id="connect" onclick="connect();" class="btn btn-success radius">连接服务，监听消息</button>
                <button id="disconnect" disabled="disabled" onclick="disconnect();" class="btn btn-default radius">断开连接</button>
	            <button id="sendMsg" onclick="sendMsg();" class="btn btn-default radius" disabled="disabled">推送消息给所有在线用户</button>
	        </div>
	        <div style="margin-top: 30px;width: 90%;">
	           <p style="margin-left: 10px;margin-top: 20px;color: red;font-weight: bold;">测试之前请认真阅读下方的功能说明：<span style="color: gray;">(此功能今后会加入到分布式服务 [消息服务总线系统]中，独立维护与管理)</span></p>
	           <p style="margin-left: 10px;">1、此功能为推送消息给所有在线用户，<span style="color: red;">请先点击连接推送服务，才可发起消息推送。</span></p>
	           <p style="margin-left: 10px;">2、此功能代码中，手动编码指定了当前用户名为 <span style="color: red;">zhangsan</span> (非当前系统登录用户)，方便模拟客户端进行测试。</p>
	           <p style="margin-left: 10px;">3、由于项目中/WEB-INF/view/index.jsp中存在一个消息监听服务(客户端用户名为wangwu)，故当你在此页面推送消息的时候，系统右下角会实时出现提示框(提示框是在index,jsp中实现的)，意思就是wangwu也会收到该消息。</p>
	           <p style="margin-left: 10px;">4、同时，当前页面也是一个独立的客户端连接(zhangsan)，也在监听消息，故推送消息的时候，页面右侧控制台也会显示出后台推送过来的消息。这2个不同客户端接收到的消息内容是相同的，即左侧自定义的消息内容。</p>
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
    
    <input type="hidden" id="serviceUrl" value="${serviceUrl}" des="socket服务器域名地址"/>
    <input type="hidden" id="userName" value="zhangsan" des="当前在线用户的用户名"/>
    <input type="hidden" id="pushAllUserUrl" value="${ctx}/socket/pushAllUser" des="推送消息给所有在线用户"/>
</body>

</html>