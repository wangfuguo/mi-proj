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




//下载
function down(){
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params=null;
	var queryForm = Ext.getCmp('Miser_base_outLine_queryForm');
    if (queryForm != null) {
        	params = {
        	'outLineVo.outLineEntity.provinceCode': queryForm.getForm().findField('provinceName').getValue(),
            'outLineVo.outLineEntity.cityCode': queryForm.getForm().findField('cityName').getValue(),
            'outLineVo.outLineEntity.areaCode': queryForm.getForm().findField('areaName').getValue()
        };
    }
	Ext.Ajax.request( {  
	       url : '../bizbase/outLineAction!excelExport.action',  
	       method : 'post',  
	       params : params,  
	          success : function(response, options) {  
	             //隐藏进度条   
	               Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              //addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
	              var msg="本次导出共"+responseArray.count+"条数据！";
	              miser.showInfoMsg(msg);
	              miser.requestExportAjax(responseArray.filePath);
	             },
	        failure : function() { 
	            Ext.Msg.hide();  
	            Ext.MessageBox.show({title: '失败',msg: '修改失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
	        }
	    });  
}


function imple(outFileName,flag){
	if(flag){
		miser.showInfoMsg('上传完成');
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request( {  
	       url : '../bizbase/outLineAction!outLineImport.action',  
	       method : 'post',  
	       params : {  
	    	   'outLineVo.filePath' : outFileName  
	          },  
	          success : function(response, options) {  

	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              var msg=responseArray.success;
	              if(msg == '' || msg == undefined){
	            	  msg = responseArray.error;
	            	  msg = "导入数据不合理，请检查后再导入:"+msg;
	              }
	              
	               //隐藏进度条   
	               Ext.Msg.hide(); 
	               
	              miser.showInfoMsg(msg);
	               
	               Ext.getCmp('outLineGrid').getStore().load();
	              
	             },
	        failure : function() { 
	            Ext.Msg.hide();  
	            Ext.MessageBox.show({title: '失败',msg: '导入失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
	        }
	    });  
	}else{
		miser.showErrorMes('服务器异常');
	}
}


/**
 * 偏线
 */
Ext.define('Miser.model.bizbase.OutLineEntityModel',{
	extend:'Ext.data.Model',
	fields:[
	        { name:'id',type:'string'},//主键
	        { name:'provinceCode',type:'string'}, //行政省份编码
	        { name:'provinceName',type:'string'}, //行政省份名称
	        { name:'cityCode',type:'string'}, //城市编码
	        { name:'cityName',type:'string'}, //城市名称
	        { name:'areaCode',type:'string'}, //区县编码
	        { name:'areaName',type:'string'}, //区县名称
	        
	        { name:'lowestFee',type:'string'}, //外发系数最低收费
	        { name:'weightRate',type:'string'}, //重量外发系数
	        { name:'volumeRate',type:'string'}, //体积外发系数
	        
	        {name:'remark',type : 'string'},// 备注
	        {name:'active',type : 'string'},// 是否有效
	        {name:'createUserCode',type:'string'},//创建人
	        {name:'createUserName',type:'string'},//创建人名字
	        {name:'createTime', type : 'date', dateFormat : 'time'},// 创建时间
	        {name:'modifyUserCode',type : 'string'},// 最后修改人
	        {name:'modifyUserName',type:'string'},//最后修改人名字
	        {name:'modifyTime', type : 'date', dateFormat : 'time'}// 最后修改时间
	]
});

Ext.define('Miser.base.outLine.OutLineQueryForm', {
    extend: 'Ext.form.Panel',
    id: 'Miser_base_outLine_queryForm',
    frame: true,
    title: bizbase.outLine.i18n('miser.biz.base.outLine.queryCondition'),//查询条件
    height: 100,
    collapsible: true,
    layout: 'column',    
    region: 'north',
    defaults:{
		margin : '8 10 5 10',
		labelAlign : 'right'
    },
    defaultType: 'textfield',
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        me.items = [{layout: {
            type: 'hbox',
            pack: 'start',
            align: 'stretch'
        	},
        	name: '',
            labelWidth: 20,
            cityLabel: bizbase.outLine.i18n('miser.biz.base.outLine.cityName'),
            cityName: 'cityName',
            cityIsBlank:true,
            //名称
            provinceLabel:bizbase.outLine.i18n('miser.biz.base.outLine.provinceName'),
            provinceName: 'provinceName',
            provinceIsBlank:true,
            //省名称
            areaLabel: bizbase.outLine.i18n('miser.biz.base.outLine.areaName'),
            areaName: 'areaName',
            areaIsBlank:true,
            
            // 县名称
            type: 'P-C-C',
            xtype: 'linkregincombselector'
        }],
        me.buttons = [{
            text:bizbase.outLine.i18n('miser.biz.base.outLine.query'),//查询
            handler: function() {
                if (me.getForm().isValid()) {
                    me.up().getOutLineGrid().getPagingToolbar().moveFirst();
                }
            }
        },
        {
        	id:'downbutton_id',
        	text: bizbase.outLine.i18n('miser.biz.base.outLine.downbutton'),//导出
            alias: 'widget.downloadbutton',
        	glyph : 0xf019,
            disabled:false,
        	cls: 'download-btn-item',
            handler: function() {
            	down();
            }
        }];
        me.callParent([cfg]);
    }
});

/**修改表单*/
Ext.define('Miser.biz.bizbase.OutLineUpdateForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	autoDestroy:true,
	region : 'north',
	defaults : {
		labelWidth : 0.4,
		columnWidth : 0.6,
		margin : '8 10 5 10',
		labelAlign : 'right'
	},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        
        me.items = [
				{
					layout:'hbox',
					defaults : {
						labelWidth : 70,
						labelAlign : "right"
					},
					defaults : {
						margins : '10 10 5 5'
					},
					
				    labelWidth: 20,
				    cityLabel: bizbase.outLine.i18n('miser.biz.base.outLine.cityName1'),
				    cityName: 'cityName',
				    cityIsBlank:true,
 
				    provinceLabel:bizbase.outLine.i18n('miser.biz.base.outLine.provinceName1'),
				    provinceName: 'provinceName',
				    provinceIsBlank:true,
				   
				    
				    areaLabel: bizbase.outLine.i18n('miser.biz.base.outLine.areaName1'),
				    areaName: 'areaName',
				    areaIsBlank:true,
				    
				    // 县名称
				    type: 'P-C-C',
				    xtype: 'linkregincombselector'
				},
				{
					layout:'hbox',
					defaults : {
						labelAlign : "right"
					},
					defaults : {
						margins : '10 10 5 5'
					},
					items:[
					    {
					    	name:'id',
					    	hidden : true					    	
					    },
						{
							name : 'lowestFee',
							fieldLabel : bizbase.outLine.i18n('miser.biz.base.outLine.lowestFee'),//外发系数最低收费
			                allowBlank: false,
							minValue: 0,
							decimalPrecision:2,
			                xtype: 'numberfield'			
						},
						{
							name : 'weightRate',
							fieldLabel : bizbase.outLine.i18n('miser.biz.base.outLine.weightFee'),//重量外发系数
			                allowBlank: false,
							minValue: 0,
							decimalPrecision:2,
			                xtype: 'numberfield'					
						}
				
				    ]
				},
				{
					layout : 'hbox',
					defaults : {
						labelWidth : 70,
						labelAlign : "right"
					},
					defaults : {
						margins : '10 10 5 5'
					},
					items : [
						{
							name : 'volumeRate',
							fieldLabel : bizbase.outLine.i18n('miser.biz.base.outLine.volumnFee'),//体积外发系数
			                allowBlank: false,
							minValue: 0,
							decimalPrecision:2,
			                xtype: 'numberfield'
						},        
					 {
						fieldLabel : bizbase.outLine.i18n('miser.biz.base.outLine.remarks'),//'备注',
						name : 'remark',
						width : 270,
						xtype : 'textarea',
						labelAlign:'right'
					} ]
				}
        ];
        me.callParent([cfg]);
    }
});




Ext.define('Miser.biz.bizbase.OutLineUpdateWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	parent : null,
	title:bizbase.outLine.i18n('miser.biz.base.outLine.outLineUpdate'),
	resizable : false,
	// 可以调整窗口的大小
	closeAction : 'hide',
	// 点击关闭是隐藏窗口
	outLineEntity: null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getOutLineUpdateForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getOutLineUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			
			if(me.outLineEntity != null ){
				var provinceObj = me.getOutLineUpdateForm().getForm().findField('provinceName');
	            var provinceRecord=Ext.data.Record({
	            	districtName : me.outLineEntity.provinceName,
	            	districtCode :  me.outLineEntity.provinceCode
	                }
	            );
	            provinceObj.getStore().add(provinceRecord);
	            provinceObj.setValue(me.outLineEntity.provinceCode);
	            provinceObj.setReadOnly(true);


	            var cityObj = me.getOutLineUpdateForm().getForm().findField('cityName');
	            var cityRecord=Ext.data.Record({
	            	districtName : me.outLineEntity.cityName,
	            	districtCode :  me.outLineEntity.cityCode
	                }
	            );
	            cityObj.getStore().add(cityRecord);
	            cityObj.setValue(me.outLineEntity.cityCode);
	            cityObj.setReadOnly(true);


	            var areaObj = me.getOutLineUpdateForm().getForm().findField('areaName');
	            var areaRecord=Ext.data.Record({
	            	districtName : me.outLineEntity.areaName,
	            	districtCode :  me.outLineEntity.areaCode
	                }
	            );
	            areaObj.getStore().add(areaRecord);
	            areaObj.setValue(me.outLineEntity.areaCode);
	            areaObj.setReadOnly(true);
	            
	            var lowestFeeObj = me.getOutLineUpdateForm().getForm().findField('lowestFee');
	            lowestFeeObj.setValue(me.outLineEntity.lowestFee);
	            
	            var weightRateObj = me.getOutLineUpdateForm().getForm().findField('weightRate');
	            weightRateObj.setValue(me.outLineEntity.weightRate);
	            
	            var volumeRateObj = me.getOutLineUpdateForm().getForm().findField('volumeRate');
	            volumeRateObj.setValue(me.outLineEntity.volumeRate);	            
	            
	            var remarkObj = me.getOutLineUpdateForm().getForm().findField('remark');
	            remarkObj.setValue(me.outLineEntity.remark);
			}

			
		}
	},
	outLineUpdateForm:null,
	getOutLineUpdateForm:function(){
    	if(Ext.isEmpty(this.outLineUpdateForm)){
    		this.outLineUpdateForm = Ext.create('Miser.biz.bizbase.OutLineUpdateForm');
    	}
    	return this.outLineUpdateForm;
    },
    submitDriver:function(){
		var me = this;
		if (me.getOutLineUpdateForm().getForm().isValid()) { //校验form是否通过校验
			var outLineEntity =new Miser.model.bizbase.OutLineEntityModel;
			me.getOutLineUpdateForm().getForm().updateRecord(outLineEntity); //将FORM中数据设置到MODEL里面
			
			//因在修改界面中没有将 id 等属性展示出来 只是展示出 需要修改的字段 在这里需要根据 id 修改
			//需要单独将 id 补充进去
			outLineEntity.data.id = me.outLineEntity.id;
			
			var curData=outLineEntity.data;
            var params = {
                'outLineVo': {
                    'outLineEntity': curData
                }
            }
			var successFun = function (json) {
				var message = json.message;
				miser.showInfoMsg(message); //提示修改成功
				me.close();
				 Ext.getCmp('outLineGrid').getStore().reload(); //成功之后重新查询刷新结果集
			};
			var failureFun = function (json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes('连接超时'); //请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message); //提示失败原因
				}
			};
			miser.requestJsonAjax('../bizbase/outLineAction!updateOutLine.action', params, successFun, failureFun); //发送AJAX请求
		}
	},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
           text: bizbase.outLine.i18n('miser.biz.base.outLine.save'),//'保存',
            handler: function() {
                me.submitDriver();
            }
        },
        {
            text: bizbase.outLine.i18n('miser.biz.base.outLine.cancel'),// '取消',
            // 取消
            handler: function() {
            	miser.showQuestionMes('确认退出？',// 是否删除
						function(e) {
							if (e == 'yes') {
								 me.close();
							}
						});
            }
        }];
        me.items = [me.getOutLineUpdateForm()];
        me.callParent([cfg]);
    }
});


/**
 * 偏线表格
 */
Ext.define('Miser.biz.bizbase.OutLineGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    id : 'outLineGrid',
    autoScroll: true,
    width: '300%',
    height: document.documentElement.clientHeight - 150,
    stripeRows: true,
    region: 'center',
    emptyText:bizbase.outLine.i18n('miser.biz.base.outLine.resultisNull'),
    viewConfig: {
        enableTextSelection: true
    },
    outLineUpdateWindow:null,
    getOutLineUpdateWindow:function(){
    	if(Ext.isEmpty(this.outLineUpdateWindow)){
    		this.outLineUpdateWindow = Ext.create('Miser.biz.bizbase.OutLineUpdateWindow');
    	}
    	return this.outLineUpdateWindow;
    },
    updatePpfiItems : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var id = selections[0].get('id');
		var params = {
				'outLineVo' :{
					'outLineEntity' : {
						'id':id
					}
				}
			};
		var successFun = function (json) {
			var updateWindow = me.getOutLineUpdateWindow(); //获得修改窗口
			updateWindow.outLineEntity = json.outLineVo.outLineEntity;
			updateWindow.show(); //显示修改窗口
		};
		var failureFun = function (json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes('请求超时'); //请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax('../bizbase/outLineAction!queryOutLineInUpdate.action', params, successFun, failureFun);
	},
    selModel: Ext.create('Ext.selection.CheckboxModel', {
        listeners: {
            selectionchange: function(sm, selections) {
                var length=selections.length;
                if( length == 1){
                	Ext.getCmp('updateBtn').setDisabled(false);
                }else{
                	Ext.getCmp('updateBtn').setDisabled(true);	
                }                
                
            }
        }
    }),
    pagingToolbar: null,
    getPagingToolbar: function() {
        if (this.pagingToolbar == null) {
            this.pagingToolbar = Ext.create('Ext.toolbar.Paging', {
                store: this.store,
                pageSize: 20
            });
        }
        return this.pagingToolbar;
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({}, config);
        me.columns = [
        {
			dataIndex : 'id',
			hidden : true
        },              
        {
            text: bizbase.outLine.i18n('miser.biz.base.outLine.rownumberer'),
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },
        {
			dataIndex : 'provinceCode',
			hidden : true
        },  
        {
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.provinceName1'),
            dataIndex: 'provinceName',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
			dataIndex : 'cityCode',
			hidden : true
        },          
        {
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.cityName1'),
            dataIndex: 'cityName',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
			dataIndex : 'areaCode',
			hidden : true
        },             
        {
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.areaName1'),
            dataIndex: 'areaName',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.lowestFee'),
            dataIndex: 'lowestFee',
            width: 200,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.weightFee'),
            dataIndex: 'weightRate',
            width: 200,
            align: 'center',
            flex: 1
        },           
        {
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.volumnFee'),
            dataIndex: 'volumeRate',
            width: 200,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.remarks'),
            dataIndex: 'remark',
            width: 200,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.createUserName'),
            dataIndex: 'createUserName',
            width: 80,
            align: 'center',
            flex: 1
        },
        {        	
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.createTime'),
            dataIndex: 'createTime',
            width: 100,
            align: 'center',
            flex: 1,
            xtype :'datecolumn', 
            format :'Y-m-d H:i:s'
        },
        {
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.modifyUserName'),
            dataIndex: 'modifyUserName',
            width: 80,
            align: 'center',
            flex: 1
        },
        {        	
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.modifyTime'),
            dataIndex: 'modifyTime',
            width: 100,
            align: 'center',
            flex: 1,
            xtype :'datecolumn', 
            format :'Y-m-d H:i:s'
        }
        ],
        me.tbar = [
        	{
        	id:'updateBtn',
        	text:bizbase.outLine.i18n('miser.biz.base.outLine.outLineUpdate'),
            xtype: 'updatebutton',
            width: 80,
            disabled:true,
            handler: function() {
            	 me.updatePpfiItems();
            }
        },{
			xtype: 'uploadbutton',
			text:bizbase.outLine.i18n('miser.biz.base.outLine.export'),
            width: 80,
            handler:function(){
            	uploadT.excelWindow(null,imple);
            }
        },{
	            id: 'aging_city_downbutton_id',
	            alias: 'widget.downloadbutton',
	        	glyph : 0xf019,
	            disabled:false,
	        	cls: 'download-btn-item',
	            text: bizbase.outLine.i18n('miser.biz.base.outLine.templateDownLoad'),
	            width: 130,
	            handler: function() {
	            	miser.requestExportAjax('/excelTemplate/outLine.xlsx');
	            }
           },{
	            id: 'outline_deleteBtn',
	            xtype:'deletebutton',
	            text: '作废',
	            width: 100,
	            handler: function() {
	            	
					//获取当前 选中的记录
					var selections = me.getSelectionModel().getSelection();
					var destoryIdStr = '';
					
					for(var j=0;j<selections.length;j++){
						var curId = selections[j].get('id');
						destoryIdStr += "'"+curId+"',";
					}
					
					if(destoryIdStr != '')
					{
						miser.showQuestionMes('是否作废当前选中的记录?',
							 function(e) {
								if (e == 'yes') {
									destoryIdStr = destoryIdStr.substring(0,destoryIdStr.length - 1);
									
									var params = {
											'outLineVo':{
												'destoryIdStr':destoryIdStr	
											}
									};
									
									
									var failureFun = function (json) {
										if (Ext.isEmpty(json)) {
											miser.showErrorMes('连接超时'); //请求超时
										} else {
											var message = json.message;
											miser.showErrorMes(message); //提示失败原因
										}
									};									
									
									var successFun = function (json) {
										var msg = json.message;
										miser.showInfoMsg('作废成功');					
										me.store.reload();
									};
									
									miser.requestJsonAjax('../bizbase/outLineAction!destrory.action', params, successFun, failureFun);		
								}
						});
					} else {
						miser.showInfoMsg('当前并无选中的记录');		
					}					
	            }            	
            }];
        me.store = Ext.create('Miser.biz.bizbase.OutLineStore', {
            autoLoad: false
        });
        me.bbar
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    },
    listeners:{
		itemdblclick:function(view,model,item,index,e,eOpts){
			var me = this;
			me.updatePpfiItems();
		}
	}
});


/**
 * 偏线store
 */
Ext.define('Miser.biz.bizbase.OutLineStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.model.bizbase.OutLineEntityModel',
    id:'outLineStore',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: 'outLineAction!queryOutLineList.action',
        reader: {
            type: 'json',
            rootProperty: 'outLineVo.outLineEntities',
            totalProperty: 'totalCount' //总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('Miser_base_outLine_queryForm');
            if (queryForm != null) {
                var params = {
                		 'outLineVo.outLineEntity.provinceCode': queryForm.getForm().findField('provinceName').getValue(),
                         'outLineVo.outLineEntity.cityCode': queryForm.getForm().findField('cityName').getValue(),
                         'outLineVo.outLineEntity.areaCode': queryForm.getForm().findField('areaName').getValue()
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
    }
});


Ext.onReady(function() {
    var queryForm = Ext.create("Miser.base.outLine.OutLineQueryForm");
    var outLineGrid = Ext.create("Miser.biz.bizbase.OutLineGrid");
    Ext.create('Ext.Viewport', {
        header: {
            titlePosition: 2,
            titleAlign: 'center'
        },
        border: "0 0 0 0",
        renderTo: Ext.getBody(),
        layout: {
            type: 'border'
        },
        getQueryForm: function() {
            return queryForm;
        },
        getOutLineGrid: function() {
            return outLineGrid;
        },
        items: [queryForm, outLineGrid]
    });
	
});













