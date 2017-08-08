$(function(){
        $("#form-user-update").validate({
        rules:{
            userName:{
                required:true,
                minlength:5,
                maxlength:20
            },
            realName:{
                required:true
            }
        },
        messages: {
        	userName: "请输入登录账户，长度5-20位",
        	realName: "请输入真实用户名"
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
        layer.msg("更新成功,2秒后自动关闭窗口", {
            shift: 2,
            time : 2000,
            icon: 6
        },function(){
            //关闭弹出框
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        
        $("#searchBtn", parent.document).click();
    }else{
        layer.msg(responseText.msg, {
            shift: 2,
            icon: 5
        });
        return false;
    }
}
 
function checkRole() {
    var zTree = $.fn.zTree.getZTreeObj("roleTree"), 
    type = {"Y" : "ps","N" : "ps"};
    zTree.setting.check.chkboxType = type;
}

$(document).ready(function() {
    var setting = {
        check : {
            enable : true
        },
        data : {
            simpleData : {
                enable : true
            }
        },
        callback : {
            onCheck : function(event, treeId, treeNode){//节点被点击后，设置选择的节点id
                check_param();
            }
        }
    };
    var roles = $("input[name='allRoles']").val();
    $.fn.zTree.init($("#roleTree"), setting, eval("(" + roles + ")"));
    checkRole();
});

// 验证是否选择了角色
function check_param() {
    var numberval = 0;
    var treeRole = $.fn.zTree.getZTreeObj("roleTree"), 
        roles = treeRole.getCheckedNodes(true), 
        pmss = "";
    if (roles.length > 0) {
        for (var i = 0; i < roles.length; i++) {
            pmss += roles[i].id + ",";
        }
        if (pmss.length > 0) {
            pmss = pmss.substring(0, pmss.length - 1);
        }
        $("#roleIds").val(pmss);
    } else {
        $("#roleIds").val("");
        numberval++;
    }
    if (numberval > 0) {
        return false;
    }
    return true;
}