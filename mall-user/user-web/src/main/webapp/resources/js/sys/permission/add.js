$(function(){
	    $("#form-permission-add").validate({
        rules:{
        	name:{
                required:true
            },
            description:{
                required:true
            },
            code:{
                required:true
            }
        },
        messages: {
        	name: "请输入权限名称",
        	description: "请输入权限描述",
        	code: "请输入权限代码"
        },
        onkeyup:false,
        focusCleanup:true,
        success:"",
        submitHandler:function(form){
        	var parentId = $("#parentId").val();
            if(parentId == null || parentId == ""){
                layer.msg("请选择权限所属层级", {
                    shift: 2,
                    icon: 5
                });
                return false;
            }
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
        	onClick : function(event, treeId, treeNode){//节点被点击后，设置选择的节点id
            	$("#parentId").val(treeNode.id);
            }
        }
    };
    var roles = $("input[name='permissions']").val();
    $.fn.zTree.init($("#permissionTree"), setting, eval("(" + roles + ")"));
    checkPermission();
    
    var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
    treeObj.expandAll(false);
});
