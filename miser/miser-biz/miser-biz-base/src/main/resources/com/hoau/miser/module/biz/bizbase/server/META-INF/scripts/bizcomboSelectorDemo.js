//文件上传
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
function aaaa(outFileName,flag){
	if(flag){
		miser.showInfoMsg('上传完成');
		alert(outFileName+"-----------上传文件，返回文件地址")
	}else{
		miser.showErrorMes('服务器异常');
	}
	
}
//解析excel回调函数
function bbbb(flag){
	if(flag){
		miser.showInfoMsg('解析完成');
	}else{
		miser.showErrorMes('服务器异常');
	}
	
}
//上传文件回调函数
function aaaa(outFileName){
	miser.showInfoMsg('上传完成');
	alert(outFileName+"-----------上传文件，返回文件地址")
}
Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_biz_base-selector_content',
		layout : 'auto',
		items : [{
			xtype : 'transtypecombselector',
			id : 'transtypecombselector',
			fieldLabel : '运输类型22(transtypecombselector)',
			labelWidth : 160 
		},{
			name:'division',
			fieldLabel:'事业部',
			labelWidth:160,
			xtype:'dynamicorgcombselector',
			isDivision:'Y'
		},{
			name:'bigregion',
			fieldLabel:'大区',
			labelWidth:160,
			xtype:'dynamicorgcombselector',
			isBigRegion:'Y'
		},{
			name:'dynamicorgcombselector3',
			fieldLabel:'路区',
			labelWidth:160,
			xtype:'dynamicorgcombselector',
			isRoadArea:'Y'
		},{
			name:'dynamicorgcombselector4',
			fieldLabel:'网点',
			labelWidth:160,
			xtype:'dynamicorgcombselector',
			isSalesDepartment:'Y'
		},{
	        	name: 't1',
	            labelWidth: 5,
	            flex:2.5,
	            nationIsBlank : true,// 国家长度
				nationLabel : '国家',// 国家label
				nationName : 'nname',// 国家名称--对应name
	            cityLabel: '市',
	            cityName: 'sendCityCode',
	            cityIsBlank:true,
	            //名称
	            provinceLabel: '省',
	            provinceName: 'sendProvinceCode',
	            provinceIsBlank:true,
	            //省名称
	            areaLabel: '县',
	            areaName: 'sendAreaCode',
	            areaIsBlank:true,
	            // 县名称
	            type: 'N-P-C-C',
	            xtype: 'linkregincombselector'
	    	},{
	        	name: 't2',
	            labelWidth: 5,
	            flex:2.5,	
	            loaddefault:true,          
	            width:450,  
	            //名称
	            divisionLabel: '事业部',
	            divisionName: 'seaDivision',
	            divisionIsBlank:true,
	            //名称
	            bigregionLabel: '大区',
	            bigregionName: 'seaBigregion',
	            bigregionIsBlank:true,
	            // 
	            roadareaLabel: '路区',
	            roadareaName: 'seaRoadarea',
	            roadareaIsBlank:true,
	            //省名称
	            salesdepartmentLabel: '网点',
	            salesdepartmentName: 'seaSalesdepartment',
	            salesdepartmentIsBlank:true,
	            // 县名称
	            type: 'D-B-R-S',
	            xtype: 'linkorgcombselector'
	    	},{
	         name: 'ppFeeItemsCode',
	         fieldLabel: '包装费项目(dynamicpricepackagefeeitemscombselector)',// '词条名称',
	         labelWidth : 160,
	         xtype: 'dynamicpricepackagefeeitemscombselector'
	     },{
	         name: 'ppAddFeeItemsCode',
	         fieldLabel: '附加费项目(dynamicPriceAddvalueFeeItemscombselector)',// '词条名称',
	         labelWidth : 160,
	         xtype: 'dynamicPriceAddvalueFeeItemscombselector'
	     },{
	    	 id:'priceSection',
	         name: 'priceSection',
	         fieldLabel: '优惠分段(dynamicPriceSectioncombselector)',// '优惠分段名称',
	         labelWidth : 160,
	         xtype: 'dynamicPriceSectioncombselector',
	         termsCode:'123'
	     },{
			xtype : 'toolbar',
			labelWidth : 60,
			width :1000,
			layout : 'column',
			items:[{
				xtype: 'settingsbutton',
	            text: '配置',
	            width: 80,
	            handler : function() {
	            	do_printpreview('demo',{"fnumber" : "123456756"});
				}
			},{
				xtype: 'addbutton',
	            text: '新增',
	            width: 80
			},{
				xtype: 'updatebutton',
	            text: '修改',
	            width: 80
			},{
				xtype: 'deletebutton',
	            text: '删除',
	            width: 80
			},{
				xtype: 'searchbutton',
	            text: '查询',
	            width: 80
			},{
				xtype: 'uploadbutton',
	            text: '上传(导入excel)',
	            width: 200,
	            handler:function(){
	            	uploadT.excelWindow('../common/FileUpLoadAction!impexcel.action',bbbb);
	            }
			},{
				xtype: 'uploadbutton',
	            text: '优惠分段赋值',
	            width: 200,
	            handler:function(){
	            	var codeCombo = Ext.getCmp('priceSection');
	    			codeCombo.setValue('1234');
	    			codeCombo.getStore().load();
	            }
			},{
				xtype: 'uploadbutton',
	            text: '上传(返回文件地址)',
	            width: 200,
	            handler:function(){
	            	//参数二，传入callbackFunction
	            	uploadT.excelWindow(null,aaaa);
	            }
			},{
				xtype: 'downloadbutton',
	            text: '下载',
	            width: 80,
	            handler:function(){
	            	var successFun=function(json){
	            		if(json.flag){
	            			alert('-------------请确保服务器资源文件夹下有文件（webapp下）')
	            		}else{
	            			alert('-------------文件不存在')
	            		}
	            	}
	            	miser.requestJsonAjax('../common/FileUpLoadAction!isExistFile.action', {'fileName':'1.txt'}, successFun);
	            }
			},{
				xtype: 'downloadbutton',
	            text: '价格城市(导入模板)下载',
	            width: 200,
	            handler:function(){
	            	miser.requestExportAjax('/excelTemplate/priceCity.xls');
	            }
			},{
				xtype: 'downloadbutton',
	            text: '时效城市(导入模板)下载',
	            width: 200,
	            handler:function(){
	            	miser.requestExportAjax('/excelTemplate/agingCity.xls');
	            }
			}]
		},{
			xtype: 'rating',
			listeners:{
				change:function(picker, value){
					 console.log('Rating ' + value);
				}
			}
		}],
		renderTo: Ext.getBody()
	});
	
});