//提交的方法名称
var method = "";
var listParam = "";
var saveParam = "";
$(function(){
	//加载表格数据
	$('#grid').datagrid({
		url:name + '_listByPage' + listParam,
		columns:columns,
		singleSelect: true,//如果为true，则只允许选择一行。
		pagination: true,//如果为true，则在DataGrid控件底部显示分页工具栏。
        resizable:true,//如果为true，允许列改变大小。
		width:"100%",
        align:"center",
		pageList:[5,10,20,30,50],
        rownumbers:true,//如果为true，则显示一个行号列。
        ctrlSelect:true,//在启用多行选择的时候允许使用Ctrl键+鼠标点击的方式进行多选操作
        checkOnSelect:true,//如果为true，单击复选框将永远选择行。如果为false，选择行将不选中复选框。
		toolbar: [{
			text: '新增',
			iconCls: 'icon-add',
			handler: function(){
				//设置保存按钮提交的方法为add
				method = "add";
				//关闭编辑窗口
				$('#editDlg').dialog('open');
				//清空表单内容
                $('#editForm').form('clear');
			}
		}]
	});

	//点击查询按钮
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
        var formData=$('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formData);
	});

	var h = 300;

	var w = 200;
	if(typeof(height) != "undefined"){
		h = height;
	}
	if(typeof(width) != "undefined"){
		w = width;
	}
	//初始化编辑窗口
	$('#editDlg').dialog({
		title: '编辑',//窗口标题
		width: w,//窗口宽度
		height: h,//窗口高度
		closed: true,//窗口是是否为关闭状态, true：表示关闭
		modal: true//模式窗口
	});

	//点击保存按钮
	$('#btnSave').bind('click',function(){
        var dataCollect=$('#editForm').serialize();
        var formData = DataDeal.formToJson(dataCollect);
		//做表单字段验证，当所有字段都有效的时候返回true。该方法使用validatebox(验证框)插件。
		var isValid = $('#editForm').form('validate');
		if(isValid == false){
			return;
		}

		$.ajax({
			url: name + '_' + method + saveParam,
			data: formData,
			dataType: 'json',
            contentType:'application/json;charset=UTF-8',
			type: 'post',
			success:function(rtn){
				$.messager.alert("提示",rtn.message,'info',function(){
					//成功的话，我们要关闭窗口
					$('#editDlg').dialog('close');
					//刷新表格数据
					$('#grid').datagrid('reload');
				});
			}
		});
	});

});


/**
 * 删除
 */
function del(uuid){
	$.messager.confirm("确认","确认要删除吗？",function(yes){
		if(yes){
			$.ajax({
				url: name + '_delete?id=' + uuid,
				dataType: 'json',
				type: 'post',
				success:function(rtn){
					$.messager.alert("提示",rtn.message,'info',function(){
						//刷新表格数据
						$('#grid').datagrid('reload');
					});
				}
			});
		}
	});
}

var DataDeal = {
    //将从form中通过$('#refer').serialize()获取的值转成json
    formToJson: function (data) {
        data=data.replace(/&/g,"\",\"");
        data=data.replace(/=/g,"\":\"");
        data="{\""+data+"\"}";
        return data;
    }
};
/**
 * 修改
 */
function edit(uuid){
	//弹出窗口
	$('#editDlg').dialog('open');

	//清空表单内容
	$('#editForm').form('clear');

	//设置保存按钮提交的方法为update
	method = "update";

	//加载数据
	$('#editForm').form('load',name + '_get?id=' + uuid);
}