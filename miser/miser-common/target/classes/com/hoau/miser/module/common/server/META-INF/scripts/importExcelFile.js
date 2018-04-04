var uploadT={
		oufileName:null,
		excelWindow:function(url,fn){
			if(Ext.isEmpty(fn)){
				Ext.toast('请传入回调函数','温馨提示','t');
				return;
			}
			//没有url则默认为上传文件
			var flag=false;
			if(Ext.isEmpty(url)){
				flag=true;
				url='../common/FileUpLoadAction!upLoadFile.action';
			}
			var fp=new Ext.FormPanel({
				renderTo : Ext.getBody(),
				fileUpload : true,
				width : 523,
				frame : true,
				autoHeight : true,
				bodyStyle : 'padding: 10px 10px 0 10px;',
				labelWidth : 50,
				defaults : {
					anchor : '95%',
					allowBlank : false,
					msgTarget : 'side'
				},
				items : [ new Ext.form.FileUploadField({
					buttonText : '浏览...',
					name : 'serviceXls',
					regex: /^.*?\.(xlsx|xls|zip)$/,
					regexText:'只能上传 *.xlsx,*.xls,*.zip',
					emptyText : '请选择一个文件',
					width : 500,
					buttonCfg : {
						width : 40,
						iconCls : 'upload-icon'
					}
				}) ],
				buttons : [ {
					text : '上传',
					handler : function() {
						if (fp.getForm().isValid()) {
							fp.getForm().submit(
									{
										method : 'post',
										url : url,	//后台处理的action
										waitMsg : '操作处理中，请稍等...',
										waitTitle : '提示',
										success : function(fp, action) {
											if(flag){
												fn(action.result.outFileName,true);
											}else{
												fn(true);
											}
											 xwindow.destroy();
										},
										failure : function(fp, action) {
											if(flag){
												fn('服务器异常',false);
											}else{
												fn(false);
											}
											fn(action.result.outFileName,true);
											xwindow.destroy();
										}
									});
						}
					}
				}]
			});
			var xwindow=new Ext.Window({
			renderTo : Ext.getBody(),
			closeAction : "hide",
			plain : true,
			width : 540,
			title : "上传数据",
			modal : true,
			items : [fp]
		})
			xwindow.show();
		}
}
