
var templateMsg = {
	templateFormId : "templateForm",
	formValidateInit : function(){
		$("#templateForm").validate({
	        rules:{
	        	openId:{
	                required:true
	            },
	            orderNo:{
	                required:true
	            },
	            goodName:{
	                required:true
	            }
	        },
	        messages: {
	        	openId: "请输入扫码接收到的openId",
	        	orderNo: "请输入订单编号",
	            realName: "请输入商品名称"
	        },
	        onkeyup:false,
	        focusCleanup:true,
	        success:"",
	        submitHandler:function(form){
	        	var options = {
	                type: $(form).attr("method"),
	                url: $(form).attr("action"),
	                success : templateMsg.formResponse,
	                dataType: 'json',  
	                error : function(xhr, status, err) {
	                }  
	            };   
	            $(form).ajaxSubmit(options);
	        }
	    });
	},
	formResponse : function(responseText, statusText){
		if(responseText.code == 200){
	    	layer.msg('消息已成功发送到消息队列，请等待接收', {
	            shift: 2,
	            icon: 6
	        });
	    }else{
	        layer.msg(responseText.msg, {
	            shift: 2,
	            icon: 5
	        });
	        return false;
	    }
	}
}


$(document).ready(function(){
	templateMsg.formValidateInit();
	
	$("#tab-system").Huitab({
		index:0
	});
});