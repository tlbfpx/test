$(function(){
	    $("#form-role-add").validate({
        rules:{
        	name:{
                required:true
            },
            description:{
                required:true
            }
        },
        messages: {
        	name: "请输入角色名称",
        	description: "请输入角色描述"
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
        layer.msg("添加成功,2秒后自动关闭窗口", {
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
 
function checkPermission() {
    var zTree = $.fn.zTree.getZTreeObj("permissionTree"), 
    type = {"Y" : "ps","N" : "s"};
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
    var roles = $("input[name='permissions']").val();
    $.fn.zTree.init($("#permissionTree"), setting, eval("(" + roles + ")"));
    checkPermission();
    
    
    var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
    treeObj.expandAll(false);//设置所有节点收缩
    var topNode = treeObj.getNodeByParam("id", -1);//父节点id为-1
    treeObj.expandNode(topNode,true,false);//设置顶部节点展开状态
    //treeObj.setting.callback.onClick(null, treeObj.setting.treeId, topNode);
});

// 验证是否选择了权限
function check_param() {
    var numberval = 0;
    var treeRole = $.fn.zTree.getZTreeObj("permissionTree"), 
        roles = treeRole.getCheckedNodes(true), 
        pmss = "";
    if (roles.length > 0) {
        for (var i = 0; i < roles.length; i++) {
            pmss += roles[i].id + ",";
        }
        if (pmss.length > 0) {
            pmss = pmss.substring(0, pmss.length - 1);
        }
        $("#permissionIds").val(pmss);
    } else {
        $("#permissionIds").val("");
        numberval++;
    }
    if (numberval > 0) {
        return false;
    }
    return true;
}