<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta charset="utf-8">

<title>微信模板消息</title>

<%@ include file="/WEB-INF/view/common/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/resources/js/wechat/template/templateMsgSend.js"></script>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>微信开发
		<span class="c-gray en">&gt;</span>模板消息 <a
			class="btn btn-success radius r"
			style="line-height: 1.6em; margin-top: 3px"
			href="javascript:location.replace(location.href);" title="刷新"><i
			class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<div id="tab-system" class="HuiTab">
			<div class="tabBar cl">
				<span>> 第一步：关注公众号</span> <span>> 第二步：模拟一条订单数据</span> <span>> 第三步：查看库中订单同步记录</span>
			</div>
			<div class="tabCon">
				<div class="row cl">
					<img alt="扫码关注此测试公众号" height="200px" width="200px" style="margin-top:10px;" src="${ctx}/resources/images/wechat_qrcode.jpg"/> 
					<span style="vertical-align: middle; float: left; margin-right: 50px; margin-top: 10px;margin-left:15px;">
						<h5 style="color: green;">测试微信模板消息之前，请先获取您的微信openid。</h5>
						<br />【操作步骤】：<br />
						<br /> 1、请先关注页面中的测试微信公众号，请扫码关注（关注后才能获取您的openid）。<br />
						<br /> 2、关注后，您会收到一条微信消息，里面将会有您的openid。<br />
						<br /> 3、将openid填入到需要发送的模板消息的接收人输入框中。<br />
						<br /> 4、点击“发送模板消息”按钮，发送模板消息。<br />
						<br /> 5、等待接收微信模板消息。<br />
						<br /> 6、如若正常接收，已经ok。<br />
						<br />
					</span>
				</div>
			</div>
			<div class="tabCon">
				<div class="row cl">
				    <form id="templateForm" action="${ctx}/wechat/template/send" method="post" class="form form-horizontal">
						<span style="float: left; margin-left:10px; width: 25%;">
							<div class="row cl">
                                <label class="form-label col-xs-12 col-sm-12" style="color: green;">
                                   <span>请先将扫码关注所收到的openId输入到下面的文本框内：</span>
                                </label>
                            </div>
	                        <div class="row cl">
                                <label class="form-label col-xs-4 col-sm-4" style="padding-right:5px;"><span class="c-red">*</span>微信openId：</label>
                                <div class="formControls col-xs-8 col-sm-8" style="padding-left:0px;">
                                    <input type="text" class="input-text valid" placeholder="请输入您的openId" name="openId" id="openId" value="">
                                </div>
                            </div>
						</span>
						<span style="float: left; width: 35%;">
							<div class="row cl">
                                <label class="form-label col-xs-4 col-sm-4" style="padding-right:5px;">&nbsp;</label>
                                <div class="formControls col-xs-8 col-sm-8" style="padding-left:0px;color: green;">
                                    <span>模拟一条订单数据：</span>
                                </div>
                            </div>
							<div class="row cl">
								<label class="form-label col-xs-4 col-sm-4" style="padding-right:5px;"><span class="c-red">*</span>订单号：</label>
								<div class="formControls col-xs-8 col-sm-8" style="padding-left:0px;">
									<input type="text" class="input-text" value="" placeholder="请输入订单号" id="orderNo" name="orderNo">
								</div>
							</div>
							<div class="row cl">
								<label class="form-label col-xs-4 col-sm-4" style="padding-right:5px;"><span class="c-red">*</span>订单总金额(分)：</label>
								<div class="formControls col-xs-8 col-sm-8" style="padding-left:0px;">
									<input type="text" class="input-text" placeholder="请输入订单总金额" id="totalFee" name="totalFee" value="5000" readonly="readonly">
								</div>
							</div>
							<div class="row cl">
								<label class="form-label col-xs-4 col-sm-4" style="padding-right:5px;"><span class="c-red">*</span>商品名称：</label>
								<div class="formControls col-xs-8 col-sm-8" style="padding-left:0px;">
									<input type="text" class="input-text" placeholder="请输入商品名称" id="goodName" name="goodName" value="">
								</div>
							</div>
							<div class="row cl">
								<label class="form-label col-xs-4 col-sm-4" style="padding-right:5px;">订单来源：</label>
								<div class="formControls col-xs-8 col-sm-8" style="padding-left:0px;">
									<span class="select-box"> <select class="select" name="orderSource" id="orderSource">
											<c:forEach items="${orderSource}" var="os">
												<option value="${os}">${os.description}</option>
											</c:forEach>
									</select>
									</span>
								</div>
							</div>
							<div class="row cl">
                                <label class="form-label col-xs-4 col-sm-4" style="padding-right:5px;"></label>
                                <div class="formControls col-xs-8 col-sm-8" style="padding-left:0px;">
                                    <button type="submit" style="margin-top: 20px;" class="btn btn-primary font14 form-control-static pull-left" data-loading-text="开始发送模板消息...">
                                        <i class="icon-ok"></i>&nbsp;点击发送模板消息
                                    </button>
                                </div>
                            </div>
						</span>
						<span style="float: right; width: 30%;">
                            <div class="row cl">
                                <label class="form-label col-xs-9 col-sm-9" style="color: green;text-align: left;">
                                   <span>说明：点击发送模板消息按钮后，您将会收到一条以下内容的微信模板消息：</span>
                                </label>
                            </div>
                            <div class="row cl" style="padding-left: 15px;">
                                <div class="formControls col-xs-8 col-sm-8" style="padding-left:0px;">
                                    <img alt="" src="${ctx}/resources/images/templateMsgImg.jpg" style="margin-top: 10px; width: 300px; height: 500px;">
                                </div>
                            </div>
	                    </span>
                    </form>
				</div>
			</div>
			<div class="tabCon">
				<div class="row cl" style="text-align: center;color: #666666;line-height: 50px;font-weight: bold;">
					<span style="margin-left: 10px;">（暂未开发此功能，敬请期待）</span>
				</div>
			</div>
		</div>
	</div>


	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
	<script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/validate-methods.js"></script>
	<script type="text/javascript" src="${ctx}/resources/lib/jquery.validation/1.14.0/messages_zh.js"></script>
</body>
</html>
