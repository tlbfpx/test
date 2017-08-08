<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/common.jsp"%>

<!DOCTYPE>
<html>
<head>
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>WebSocket/SockJS tomcat日志实时监控</title>
    <style type="text/css">
#connect-container {
	float: left;
	width: 400px
}

#connect-container div {
	padding: 5px;
}

#console-container {
	float: left;
	margin-left: 15px;
	width: 400px;
}

#console {
	border: 1px solid #CCCCCC;
	border-right-color: #999999;
	border-bottom-color: #999999;
	height: 500px;
	width: 1000px;
	padding: 5px;
}

#console p {
	padding: 0;
	margin: 0;
}

/*滚动条样式设置*/
/*参考：http://www.pengyaou.com/showCenter/USHXMAIQHDYSA_112.html */
#console::-webkit-scrollbar {
	width: 8px;
	height:10px;
}
#console::-webkit-scrollbar-track {
	background-color: #fff;
	-webkit-border-radius: 2em;
	-moz-border-radius: 2em;
	border-radius: 2em;
	border: 1px solid #ccc;
}
#console::-webkit-scrollbar-thumb {
	background-color: #ED5565;
	background-image: -webkit-linear-gradient(45deg,  rgba(26,179,148, .9) 25%,  transparent 25%,  transparent 50%,  rgba(26,179,148, .9) 50%,  rgba(26,179,148, .9) 75%,  transparent 75%,  transparent);
	-webkit-border-radius: 2em;
	-moz-border-radius: 2em;
	border-radius: 2em;
}
/*滚动条样式设置*/
</style>

    <script src="${ctx}/resources/lib/sockjs/0.3.4/sockjs-0.3.min.js"></script>

    <script type="text/javascript">
        var ws = null;

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('echo').disabled = !connected;
        }

        function connect() {

            if ('WebSocket' in window) {
                //ws= new WebSocket("ws://192.168.10.115:8080/zb-web/websck_log?loginUserName=admin");
                ws= new WebSocket("ws://${serviceUrl}/websck_log?loginUserName=admin");
                console.log("ws://${serviceUrl}/websck_log");
            }else if ('MozWebSocket' in window) {
                ws = new MozWebSocket("ws://${serviceUrl}/websck_log?loginUserName=admin");
            }else {
                ws = new SockJS("http://${serviceUrl}/sockjs/websck_log?loginUserName=admin");
                console.log("http://${serviceUrl}/sockjs/websck_log");
            }
            ws.onopen = function () {
                setConnected(true);
                log('[系统提示]: 连接已打开.');
            };
            ws.onmessage = function (event) {
                //log('[接收到消息]: ' + event.data);
            	log(event.data);
            };
            ws.onerror = function (evnt) {
            };
            ws.onclose = function (event) {
                setConnected(false);
                log('[系统提示]: 连接已关闭.');
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
                var message = document.getElementById('message').value;
                //log('[发送消息]: ' + message);
                ws.send(message);
            } else {
                alert('connection not established, please connect.');
            }
        }

        function log(message) {
            var console = document.getElementById('console');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.style.minWidth = '2000px';
            p.appendChild(document.createTextNode(message));
            console.appendChild(p);
            /* while (console.childNodes.length > 25) {
                console.removeChild(console.firstChild);
            } */
            console.scrollTop = console.scrollHeight;
        }
        
        function clearContent(){
        	$("#console").html("");
        }
    </script>
</head>
<body>
<div>
    <div class="row">
        <div class="alert alert-block alert-success" style="margin-left: 20px;">
            <button type="button" class="close" data-dismiss="alert">
                <i class="icon-remove"></i>
            </button>
            <i class="icon-volume-up green"></i><span style="margin-left: 10px;color: green;">该功能使用了socket服务器推送技术，实时监控服务器tomcat日志信息，并输出到页面。</span><br/>
            <i class="icon-volume-up green"></i><span style="margin-left: 10px;color: green;">此功能是基于socket服务器推送展示监控内容的，并未使用ajax轮询查询。</span><br/><br/>
            <i class="icon-share-alt green"></i><span class="orange" style="margin-left: 10px;">【相关说明】：</span><br/>
            <i class="green"></i><span style="margin-left: 10px;">由于websocket是基于tomcat8实现的，对于低版本tomcat不适应，同时如果客户端浏览器版本低，也不会成功，为了解决这样的问题，最终使用了sockjs技术。</span><br/>
            <i class="green"></i><span style="margin-left: 10px;">sockjs是一个可以模拟websocket推送的服务，兼容大多数浏览器，无论客户端浏览器是否支持HTML5的websocket，它都可以支持消息推送。</span><br/>
        </div>
        <div class="alert alert-block alert-danger" style="margin-left: 20px;">
            <button type="button" class="close" data-dismiss="alert">
                <i class="icon-remove"></i>
            </button>
            <i class="icon-volume-up"></i><span style="margin-left: 10px;">页面目前比较简单，后期会完善该实时监控功能，敬请期待。</span>
        </div>
     </div>
    <div id="connect-container" style="margin-left: 20px;float: left;">
        <div>
            <button id="connect" class="btn btn-success" onclick="connect();">连接服务</button>
            <button id="disconnect" disabled="disabled" class="btn btn-danger" onclick="disconnect();" style="margin-left: 20px;">断开连接</button>
            <button id="disconnect" class="btn btn-warning" onclick="clearContent();" style="margin-left: 20px;">清空监控内容</button>
        </div>
        <div>
            <textarea id="message" style="width: 350px;border:1px solid #438EB9;min-height: 150px;">这是一条发送给服务器的消息，注意观察右侧监控区域内的tomcat日志实时输出信息哈~</textarea>
        </div>
        <div>
            <button id="echo" onclick="echo();" class="btn btn-primary" disabled="disabled">发送消息</button>
        </div>
    </div>
    <div id="console-container" style="width:65%;float: left;">
        <div id="console" style="width:100%;border:1px solid #000000;background-color: black;color: white;overflow: auto;">(请先点击左侧连接服务...)</div>
    </div>
</div>
</body>

</html>