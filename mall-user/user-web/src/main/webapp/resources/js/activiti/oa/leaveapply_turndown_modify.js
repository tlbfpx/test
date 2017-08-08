$(function(){
    $("#leaveapplyTurndownModifyForm").validate({
    rules:{
    	startDate:{
            required:true
        },
        endDate:{
            required:true
        },
        reason:{
            required:true
        }
    },
    messages: {
    	startDate: "请选择开始日期",
    	endDate: "请选择结束日期",
    	reason: "请输入请假理由"
    },
    onkeyup:false,
    focusCleanup:true,
    success:"",
    submitHandler:function(form){
    	var options = {
            type: $(form).attr("method"),
            url: $(form).attr("action"),
            success : formResponse,
            dataType: 'json',  
                error : function(xhr, status, err) {
                }  
            };   
            $(form).ajaxSubmit(options);
        }
    });
});



/*表单回调函数*/
function formResponse(responseText, statusText){
    if(responseText.code == 200){
        layer.msg("提交成功", {
            shift: 2,
            time : 2000,
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