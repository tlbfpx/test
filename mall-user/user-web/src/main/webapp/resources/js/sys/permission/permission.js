var table;
$(function () {
    //==================初始化表格===================
    //dom自定义布局属性：
    //    Dom说明
			/*
				定义表格控件在页面的显示顺序。
				每个控件元素在数据表都有一个关联的单个字母。
				l - 每页显示行数的控件
				f - 检索条件的控件
				t - 表格控件
				i - 表信息总结的控件
				p - 分页控件
				r - 处理中的控件
				还可以在控件元素外添加DIV和Class，语法如下
				< and > - DIV元素
				<"class" and > - DIV和Class
				<"#id" and > - DIV和ID
			*/
    table = $(".table-sort").dataTable({
    	dom: '<"top">t<"float_left_l"l><"float_left_i"i><"float_left_r"r>p<"clear">',//设置排版规则(提示语、分页、搜索、页数分组等的位置设置)
        language:lang,  //提示信息
        aLengthMenu: [10,15,20,50],
        autoWidth: false,  //自动调整列宽
        filter: true, //查询结果搜索栏
        destroy : true,
        bSort : true,
        processing: false,  //加载提示
        serverSide: true,  //服务器端分页
        orderMulti: false,  //启用多列排序
        order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
        pagingType: "full_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
        stateSave: false,//状态保存 - 再次加载页面时还原表格状态
        deferRender : true,//控制表格的延迟渲染，可以提高初始化的速度。
        ajax: function (data, callback, settings) {
        	var param = {};
            //封装请求参数
            param.draw = data.draw;
           	param.start = data.start;//开始的记录序号
           	param.page = (data.start / data.length)+1;//当前页码
            param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.status = $("#status").val();
            param.name = $("#name").val();
            //设置排序参数
            if(data.order.length > 0){
            	param.order = data.order[0].dir;//排序方式
                param.sort = data.columns[data.order[0].column].name;//排序字段名
            }
            //ajax请求数据
            $.ajax({
                type: "POST",
                url: $("#getPermissionListUrl").val(),
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                timeout:10000,
                beforeSend: function(){//请求之前显示loading效果
                	$(".dataTable tr:gt(0)").remove();
                    $(".dataTable thead").append("<tr style='line-height:38px;'><td style='text-align:center;' colspan='9'></td></tr>");
                    $(".dataTable tr:eq(1) td").html("<img src='" + $("#basePathUrl").val() + "/resources/images/loading.gif'/><span style='margin-left:5px;vertical-align:middle;'>数据加载中...</span>");
                },
                success: function (result) {
                	$(".dataTable tr:gt(0)").remove();//移除loading
                    
                	//封装返回数据
                    var returnData = {};
                    returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    returnData.recordsTotal = result.recordsTotal;//返回数据全部记录
                    returnData.recordsFiltered = result.recordsFiltered;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = result.data;//返回的数据列表
                    callback(returnData);
                },
                error:function(jqXHR, textStatus, errorThrown){
                	$(".dataTable tr:gt(0)").remove();
                    $(".dataTable thead").append("<tr style='line-height:38px;'><td style='text-align:center;' colspan='9'></td></tr>");
                    if(textStatus=="timeout"){  
                        $(".dataTable tr:eq(1) td").html("<span style='margin-left:5px;vertical-align:middle;color:red;'>请求数据超时，稍后请刷新重试</span>"); 
                    }else{
                        $(".dataTable tr:eq(1) td").html("<span style='margin-left:5px;vertical-align:middle;color:red;'>请求数据异常，稍后请刷新重试</span>");
                    }
                },
            });
        },
        
        
        //列表表头字段 //visible:是否显示该列，bSortable：是否排序
        //这里的name属性有特别的作用，用于映射数据库对应的字段名。
        //      比如statusName为枚举的值，但数据库对应的字段为status，所以上面在设置请求排序参数的时候，应该获取的是name值，而不是data属性值。
        columns: [
            { "data": "id" , "bSortable": false ,width : "10%"},
            { "data": "name" , "bSortable": false, width : "20%"},
            { "data": "description" , "bSortable": false, width : "30%"},
            { "data": "parentName" , "bSortable": false, width : "30%"},
            { "data": "statusName" , name : "status", width : "20%"}
        ],
        
        //targets：表示具体需要操作的目标列，下标从0开始
        //data: 表示我们需要的某一列数据对应的属性名
        //render:返回需要显示的内容。在此我们可以修改列中样式，增加具体内容
        "columnDefs": [
            {
                "targets": [4],
                "data": "status",
                "render": function(data, type, full) {
                	if(full.status == "ENABLE"){
                		return "<span class=\"label label-success radius\">" + full.statusName + "</span>";
                	}else if(full.status == "DISABLE"){
                		return "<span class=\"label label-danger radius\">" + full.statusName + "</span>";
                	}else if(full.status == "LOCK"){
                        return "<span class=\"label label-warning radius\">" + full.statusName + "</span>";
                    }
                    return data + "";
                }
            },
            {
                "targets": [5],
                "data": "id",
                "render": function(data, type, full) {
                	return "<a style=\"text-decoration:none\" class=\"ml-5\" onClick=\"permissionEdit('编辑权限信息','" + $("#toEditPermissionUrl").val() + "?permissionId=" + data + "','','','700')\" href=\"javascript:;\" title=\"编辑权限信息\"><i class=\"Hui-iconfont\">&#xe6df;</i></a>"
                             + "<a style=\"text-decoration:none;margin-left:15px;\" class=\"ml-5\" onClick=\"permissionDelete('" + data + "')\" href=\"javascript:;\" title=\"删除\"><i class=\"Hui-iconfont\">&#xe6e2;</i></a>";
                }
            }
        ],
        
        //创建行的回调
        createdRow : function(row, data, dataIndex){
        	$(row).children('td').attr('style', 'text-align: center;');//设置表格列居中显示
        	//$(row).unbind("mouseenter").unbind("mouseleave");
        	
        	if(data.status == "DISABLE"){
        		$(row).children('td').addClass("tr_disable_color");
        	}else if(data.status == "LOCK"){
                $(row).children('td').addClass("tr_lock_color");
            }
        },
        
        //表格初始化完毕的回调
        initComplete: function(oSettings, json) { 
        }
    }).api();
    //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
    
    $("#searchBtn").click(function (){
    	table.draw();
    });
});

/* 权限-添加*/
function permissionAdd(title,url,id,w,h){
	layer_show(title,url,w,h);
}

/*权限-编辑*/
function permissionEdit(title,url,id,w,h){
	var havePermissionEditPermission = $("#havePermissionEditPermission").val();
	if(havePermissionEditPermission != undefined && havePermissionEditPermission != ""){
		layer_show(title,url,w,h);
	}else{
		layer.msg("您暂无权限修改权限", {
            shift: 2,
            icon: 5
        });
	}
}


/*权限-删除*/
function permissionDelete(permissionId){
	var havePermissionDeletePermission = $("#havePermissionDeletePermission").val();
	if(havePermissionDeletePermission != undefined && havePermissionDeletePermission != ""){
		$.confirm({
	        icon: '',
	        content: '确定删除该权限信息？',
	        confirm: function(){
	            $.ajax({
	                type : "post",
	                url : $("#deletePermissionUrl").val(),
	                dataType : "json",
	                data : {
	                    "permissionId" : permissionId
	                },
	                success : function(result) {
	                    if (result.code == 200) {
	                        layer.msg('删除成功', {
	                            shift : 2,
	                            icon : 6
	                        });
	                        table.draw();
	                    } else {
	                        layer.msg(result.msg, {
	                            shift : 2,
	                            icon : 5
	                        });
	                    }
	                }
	            });
	        },
	        cancel: function(){
	        }
	    });
	}else{
		layer.msg("您暂无权限删除的权限", {
            shift: 2,
            icon: 5
        });
	}
}
