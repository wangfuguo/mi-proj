extrafee.priceExtrafeeStandard.calculationType = 'CALCULATION_TYPE';
extrafee.priceExtrafeeStandard.lockType = 'LOCK_TYPE';
extrafee.priceExtrafeeStandard.type = 'ADDITIONAL_TYPE';
extrafee.priceExtrafeeStandard.state='PRICE_SATUS';
//状态
function activeTypeRender(value) {
	if (Ext.isEmpty(value)) {
		return value;
	}
	if ('Y' == value) {
		value = '是';
	} else if ('N' == value) {
		value = '否';
	}
	return value;
}
//下载
function down(){
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params=null;
	var queryForm = Ext.getCmp('queryPanel');
    if (queryForm != null) {
        	params = {
        		'priceExtrafeeStandardVo.priceExtrafeeStandardEntity.type': queryForm.getForm().findField('type').getValue(),
                'priceExtrafeeStandardVo.priceExtrafeeStandardEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
                'priceExtrafeeStandardVo.priceExtrafeeStandardEntity.state': queryForm.getForm().findField('state').getValue()
        	};
    }
	Ext.Ajax.request( {  
	       url : 'priceExtrafeeStandard!excelExport.action',  
	       method : 'get',  
	       params : params,  
	          success : function(response, options) {  
	             //隐藏进度条   
	               Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              var msg="本次导出共"+responseArray.count+"条数据！";
	              miser.showInfoMsg(msg);
	              miser.requestExportAjax(responseArray.filePath);
	             },
	        failure : function() { 
	            Ext.Msg.hide();  
	            Ext.MessageBox.show({title: '失败',msg: '下载失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
	        }
	    });  
	
}

/**
 * 标准附加费列表model
 */
Ext.define('Miser.model.priceExtrafeeStandardEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
        name: 'transTypeCode',
        // 运输类型编号
        type: 'string'
    },{
        name: 'transTypeName',
        // 运输类型
        type: 'string'
    }, {
		name : 'type',
		type : 'string'
	}, {
		name : 'money',
		type : 'number'
	}, {
		name : 'lockType',
		type : 'number'
	}, {
		name : 'effectiveTime',
		type : 'date',
		dateFormat : 'time'
	}, {
		name : 'invalidTime',
		type : 'date',
		dateFormat : 'time'
	}, {
		name : 'remark',
		type : 'string'
	}, {
		name : 'active',
		type : 'string'
	}, {
		name : 'state',
		// 状态
		type : 'string'
	}, {
		name : 'modifyUser',
		type : 'string'
	}, {
		name : 'modifyDate',
		type : 'date',
		dateFormat : 'time'
	} ]
});

/**
 * 增加表单
 */
Ext.define(
				'Miser.view.extrafee.priceExtrafeeStandardAddForm',
				{
					extend : 'Ext.form.Panel',
					header: false,
					frame: true,
					closable: true,
					width: 600,
					defaults: {
						labelWidth: 100,
						margin: '8 10 5 10'
					},
					layout: {
				        type: 'table',
				        columns: 2
				    },
					defaultType: 'textfield',
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ 
						 {
								name : 'id',
								fieldLabel : 'ID',
								xtype : 'textfield',
								hidden : true
						},
						{
							colspan: 2,
							xtype : 'textfield',
							name : 'type',
							width: 300,
							allowBlank : false,
							fieldLabel : extrafee.priceExtrafeeStandard
									.i18n('extrafee.priceExtrafeeStandard.type'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype : 'dataDictionarySelector',
							termsCode : extrafee.priceExtrafeeStandard.type,
							listeners : {
					        change : function(field,newValue,oldValue){
					        	if(newValue=='INSURANCE'){
					        		Ext.getCmp("Addmoney").setValue('（单位：‰）');
					        	}else{
					        		Ext.getCmp("Addmoney").setValue('');
					        	}
					       }
						   }
						}, {
							colspan: 2,
							xtype : 'textfield',
							name : 'transTypeCode',
							allowBlank : false,
							width: 300,
							fieldLabel : extrafee.priceExtrafeeStandard
									.i18n('extrafee.priceExtrafeeStandard.transTypeCode'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype : 'transtypecombselector',
							showAll : false
						}, {
							xtype : "numberfield",
							step:100,
							width: 300,
							name : 'money',
							allowBlank : false,
							fieldLabel : extrafee.priceExtrafeeStandard.i18n('extrafee.priceExtrafeeStandard.money'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						},{
							xtype : 'textfield',
							readOnly:true,
							id:'Addmoney'
						}, {
							colspan: 2,
							xtype : "textfield",
							name : 'lockType',
							width: 300,
							allowBlank : false,
							fieldLabel : extrafee.priceExtrafeeStandard
									.i18n('extrafee.priceExtrafeeStandard.lockType'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype : 'dataDictionarySelector',
							termsCode : extrafee.priceExtrafeeStandard.lockType
						}, {
							colspan: 2,
							xtype: 'datetimefield',
							minThisDate : true,
				            format: 'Y-m-d H:i:s',
							name : 'effectiveTime',
							width: 300,
							allowBlank : false,
							fieldLabel : extrafee.priceExtrafeeStandard.i18n('extrafee.priceExtrafeeStandard.effectiveTime'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, {
							colspan: 2,
							width: 500,
							labelSeparator : ':&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
							fieldLabel : extrafee.priceExtrafeeStandard
							.i18n('extrafee.priceExtrafeeStandard.remark'), // '备注',
							xtype : 'textarea',
							name : 'remark',
							xtype : 'textarea'
						} ];
						me.callParent([ cfg ]);
					}
				});

/**
 * 修改表单
 */
Ext.define(
				'Miser.view.extrafee.priceExtrafeeStandardUpdateForm',
				{
					extend : 'Ext.form.Panel',
					header: false,
					frame: true,
					closable: true,
					width: 600,
					defaults: {
						labelWidth: 100,
						margin: '8 10 5 10'
					},
					layout: {
				        type: 'table',
				        columns: 2
				    },
					defaultType: 'textfield',
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [
		            {
						name : 'state',
						fieldLabel : 'state',
						xtype : 'hiddenfield'
					},{
							name : 'id',
							fieldLabel : 'ID',
							xtype : 'hiddenfield'
					},
					{
						colspan: 2,
						xtype : 'textfield',
						name : 'type',
						id:'updateType',
						width: 300,
						readOnly :true,
						fieldLabel : extrafee.priceExtrafeeStandard
								.i18n('extrafee.priceExtrafeeStandard.type'),
						beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
						xtype : 'dataDictionarySelector',
						termsCode : extrafee.priceExtrafeeStandard.type
					}, {
						colspan: 2,
						xtype : 'textfield',
						name : 'transTypeName',
						fieldLabel : extrafee.priceExtrafeeStandard
								.i18n('extrafee.priceExtrafeeStandard.transTypeCode'),
						beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
						readOnly :true,
						width: 300
					},{
						xtype : 'hiddenfield',
						hidden : 'true',
						name : 'transTypeCode'
					}, {
						xtype : "numberfield",
						step:100,
						width: 300,
						name : 'money',
						allowBlank : false,
						fieldLabel : extrafee.priceExtrafeeStandard.i18n('extrafee.priceExtrafeeStandard.money'),
						beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
					},{
							xtype : 'textfield',
							readOnly:true,
							id:'Updatemoney'
					}, {
						colspan: 2,
						xtype : "textfield",
						name : 'lockType',
						width: 300,
						allowBlank : false,
						fieldLabel : extrafee.priceExtrafeeStandard
								.i18n('extrafee.priceExtrafeeStandard.lockType'),
						beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
						xtype : 'dataDictionarySelector',
						termsCode : extrafee.priceExtrafeeStandard.lockType
					}, {
						colspan: 2,
						xtype: 'datetimefield',
			            format: 'Y-m-d H:i:s',
						minThisDate : true,
						name : 'effectiveTime',
						width: 300,
						allowBlank : false,
						fieldLabel : extrafee.priceExtrafeeStandard.i18n('extrafee.priceExtrafeeStandard.effectiveTime'),
						beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
					}, {
						colspan: 2,
						width: 500,
						labelSeparator : ':&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
						fieldLabel : extrafee.priceExtrafeeStandard
						.i18n('extrafee.priceExtrafeeStandard.remark'), // '备注',
						xtype : 'textarea',
						name : 'remark',
						xtype : 'textarea'
					} ];
						me.callParent([ cfg ]);
					}
				});
// 增加窗体
Ext.define('Miser.view.extrafee.priceExtrafeeStandardAddWindow', {
	extend : 'Ext.window.Window',
	title : '新增',// '新增词条',
	closable : true,
	parent : null,
	// 父元素
	modal : true,
	resizable : false,
	// 可以调整窗口的大小
	closeAction : 'hide',
	// 点击关闭是隐藏窗口
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getpriceExtrafeeStandardAddForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getpriceExtrafeeStandardAddForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			me.getpriceExtrafeeStandardAddForm().getForm().findField("effectiveTime").setValue(new Date(new Date().getTime() + 5*60*1000));
		}
	},
	priceExtrafeeStandardAddForm : null,
	getpriceExtrafeeStandardAddForm : function() {
		if (Ext.isEmpty(this.priceExtrafeeStandardAddForm)) {
			this.priceExtrafeeStandardAddForm = Ext
					.create('Miser.view.extrafee.priceExtrafeeStandardAddForm');
		}
		return this.priceExtrafeeStandardAddForm;
	},
	submitpriceExtrafeeStandardAddForm : function() {
		var me = this;
		if (me.getpriceExtrafeeStandardAddForm().getForm().isValid()) { // 校验form是否通过校验
			var priceExtrafeeStandardEntity = new Miser.model.priceExtrafeeStandardEntity();
			me.getpriceExtrafeeStandardAddForm().getForm().updateRecord(
					priceExtrafeeStandardEntity); // 将FORM中数据设置到MODEL里面
			var curData = priceExtrafeeStandardEntity.data;
			var params = {
				'priceExtrafeeStandardVo' : {
					'priceExtrafeeStandardEntity' : curData
				}
			}
			params.priceExtrafeeStandardVo.priceExtrafeeStandardEntity.isAlert='0';
			var successFun = function(json) {
				var message = json.message;
				if(!Ext.isEmpty(message)&&message=='miser.base.isAlert'){
					miser.showQuestionMes('该附加费存在相同的待生效数据，是否覆盖？',// 是否删除
							function(e) {
								if (e == 'yes') {
									params.priceExtrafeeStandardVo.priceExtrafeeStandardEntity.isAlert='1';
									miser.requestJsonAjax('priceExtrafeeStandard!addPriceExtrafeeStandard.action',
											params, successFun, failureFun);
								}
							});
				}else{
					miser.showInfoMsg(message); // 提示修改成功
					me.close();
					me.parent.getStore().load(); // 成功之后重新查询刷新结果集
				}
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(extrafee.priceExtrafeeStandard
							.i18n('miser.extrafee.timeout')); // 请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message); // 提示失败原因
				}
			};
			miser.requestJsonAjax('priceExtrafeeStandard!addPriceExtrafeeStandard.action',
					params, successFun, failureFun); // 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : extrafee.priceExtrafeeStandard.i18n('miser.common.save'),// '保存',
			// 保存
			/* margin : '0 0 0 55', */
			handler : function() {
				me.submitpriceExtrafeeStandardAddForm();
			}
		}, {
			text : extrafee.priceExtrafeeStandard.i18n('miser.common.cancel'),// '取消',
			// 取消
			handler : function() {
				me.close();
			}
		} ];
		me.items = [ me.getpriceExtrafeeStandardAddForm()];
		me.callParent([ cfg ]);
	}
});
/**
 * 修改标准附加费窗口
 */
Ext.define('Miser.view.extrafee.priceExtrafeeStandardUpdateWindow', {
	extend : 'Ext.window.Window',
	title : '修改',// 修改
	closable : true,
	parent : null, // 父元素
	modal : true,
	resizable : false, // 可以调整窗口的大小
	closeAction : 'hide', // 点击关闭是隐藏窗口
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	priceExtrafeeStandardEntity : null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getpriceExtrafeeStandardUpdateForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getpriceExtrafeeStandardUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			me.getpriceExtrafeeStandardUpdateForm().getForm().loadRecord(new Miser.model.priceExtrafeeStandardEntity(me.priceExtrafeeStandardEntity));
			var updateFiledValue=Ext.getCmp("updateType").getValue();
			if(updateFiledValue=='INSURANCE'){
        		Ext.getCmp("Updatemoney").setValue('（单位：‰）');
        	}else{
        		Ext.getCmp("Updatemoney").setValue('');
        	}
		}
	},
	priceExtrafeeStandardUpdateForm : null,
	getpriceExtrafeeStandardUpdateForm : function() {
		if (Ext.isEmpty(this.priceExtrafeeStandardUpdateForm)) {
			this.priceExtrafeeStandardUpdateForm = Ext.create('Miser.view.extrafee.priceExtrafeeStandardUpdateForm');
		}
		return this.priceExtrafeeStandardUpdateForm;
	},
	submitDriver : function() {
		var me = this;
		if (me.getpriceExtrafeeStandardUpdateForm().getForm().isValid()) { // 校验form是否通过校验
			var priceExtrafeeStandardEntity = new Miser.model.priceExtrafeeStandardEntity();
			me.getpriceExtrafeeStandardUpdateForm().getForm().updateRecord(
					priceExtrafeeStandardEntity); // 将FORM中数据设置到MODEL里面
			var curData = priceExtrafeeStandardEntity.data;
			var params = {
				'priceExtrafeeStandardVo' : {
					'priceExtrafeeStandardEntity' : curData
				}
			}
			params.priceExtrafeeStandardVo.priceExtrafeeStandardEntity.isAlert='0';
			var successFun = function(json) {
				var message = json.message;
				if(!Ext.isEmpty(message)&&message=='miser.base.invalid'){
					miser.showErrorMes('无法修改已作废或已过期数据'); // 请求超时
				}else if(!Ext.isEmpty(message)&&message=='miser.base.isAlert'){
					miser.showQuestionMes('该附加费存在相同的待生效数据，是否覆盖？',// 是否删除
							function(e) {
								if (e == 'yes') {
									params.priceExtrafeeStandardVo.priceExtrafeeStandardEntity.isAlert='1';
									miser.requestJsonAjax('priceExtrafeeStandard!updatePriceExtrafeeStandard.action',
											params, successFun, failureFun);
								}
							});
				}else{
					miser.showInfoMsg(message); // 提示修改成功
					me.close();
					me.parent.getStore().load(); // 成功之后重新查询刷新结果集
				}
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes('连接超时'); // 请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message); // 提示失败原因
				}
			};
			miser.requestJsonAjax('priceExtrafeeStandard!updatePriceExtrafeeStandard.action',
					params, successFun, failureFun); // 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : extrafee.priceExtrafeeStandard.i18n('miser.common.save'), // 保存
			margin : '0 0 0 305',
			handler : function() {
				me.submitDriver();
			}
		}, {
			text : extrafee.priceExtrafeeStandard.i18n('miser.common.cancel'), // 取消
			handler : function() {
				me.close();
			}
		} ];
		me.items = [ me.getpriceExtrafeeStandardUpdateForm()];
		me.callParent([ cfg ]);
	}
});
/**
 * 标准附加费列表store
 */
Ext.define('Miser.store.priceExtrafeeStandardStore',
			{
				extend : 'Ext.data.Store',
				model : 'Miser.model.priceExtrafeeStandardEntity',
				pageSize : 20,
				proxy : {
					type : 'ajax',
					actionMethods : 'post',
					url : 'priceExtrafeeStandard!queryListByParam.action',
					reader : {
						type : 'json',
						rootProperty : 'priceExtrafeeStandardVo.priceExtrafeeStandardList',
						totalProperty : 'totalCount' // 总个数
					}
				},
		    listeners: {
		        beforeload: function (store, operation, opts) {
		            var queryPanel = Ext.getCmp('queryPanel');
		            if (queryPanel != null) {
		                var params = {
		                    'priceExtrafeeStandardVo.priceExtrafeeStandardEntity.type': queryPanel.getForm().findField('type').getValue(),
		                    'priceExtrafeeStandardVo.priceExtrafeeStandardEntity.transTypeCode': queryPanel.getForm().findField('transTypeCode').getValue(),
		                    'priceExtrafeeStandardVo.priceExtrafeeStandardEntity.state': queryPanel.getForm().findField('state').getValue()
		                };
		                Ext.apply(store.proxy.extraParams, params);
		            }
		        }
		    }
	});
//定义查询面板
Ext.define('Miser.view.priceExtrafeeStandard.QueryPanel', {
    extend: 'Ext.form.Panel',
    id: 'queryPanel',
    title: '查询条件',
    frame: true,
    height: 110,
    collapsible: true,
    layout: 'column',
    defaults: {
        colspan: 1,
        margin: '8 10 5 10'
    },
    defaultType: 'textfield',
    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.items = [{
            name: 'type',
            fieldLabel: extrafee.priceExtrafeeStandard.i18n('extrafee.priceExtrafeeStandard.type'),
            xtype: 'dataDictionarySelector',anyRecords:all,
            termsCode : extrafee.priceExtrafeeStandard.type
        }, {
            name: 'transTypeCode',
            fieldLabel: '运输类型',
            xtype: 'transtypecombselector'
        }, {
            name: 'state',
            fieldLabel: '状态',
            xtype: 'dataDictionarySelector',
			anyRecords : all,
            termsCode : extrafee.priceExtrafeeStandard.state
        }];
        me.buttons = [{
            text: '查询',
            handler: function () {
                if (me.getForm().isValid()) {
                    me.up().getPriceExtrafeeStandardGrid().getPagingToolbar().moveFirst();
                }
            }
        }, {
            text: '清空',
            handler: function () {
                me.getForm().reset();
            }
        }];
        me.callParent([cfg]);
    }
});
/**
 * 标准价格信息表格
 */
Ext.define('Miser.view.priceExtrafeeStandard.Grid', {
    extend: 'Ext.grid.Panel',
    frame: true,
	autoScroll: true,
	stripeRows: true,
	height : document.documentElement.clientHeight,
	selType: 'rowmodel',
	emptyText: '查询结果为空',
	columnLines: true,
	viewConfig: {
		enableTextSelection: true
	},
	priceExtrafeeStandardAddWindow : null,
	getpriceExtrafeeStandardAddWindow : function() {
		if (Ext.isEmpty(this.priceExtrafeeStandardAddWindow)) {
			this.priceExtrafeeStandardAddWindow = Ext
					.create('Miser.view.extrafee.priceExtrafeeStandardAddWindow');
			this.priceExtrafeeStandardAddWindow.parent = this; // 父元素
		}
		return this.priceExtrafeeStandardAddWindow;
	},
	priceExtrafeeStandardUpdateWindow : null,
	getpriceExtrafeeStandardUpdateWindow : function() {
		if (Ext.isEmpty(this.priceExtrafeeStandardUpdateWindow)) {
			this.priceExtrafeeStandardUpdateWindow = Ext
					.create('Miser.view.extrafee.priceExtrafeeStandardUpdateWindow');
			this.priceExtrafeeStandardUpdateWindow.parent = this; // 父元素
		}
		return this.priceExtrafeeStandardUpdateWindow;
	},
	updatepriceExtrafeeStandard : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		var id = selections[0].get('id');
		var params = {
			'priceExtrafeeStandardVo' : {
				'priceExtrafeeStandardEntity' : {
					'id' : id
				}
			}
		};
		var successFun = function(json) {
			var updateWindow = me.getpriceExtrafeeStandardUpdateWindow(); // 获得修改窗口
			updateWindow.priceExtrafeeStandardEntity = json.priceExtrafeeStandardVo.priceExtrafeeStandardEntity;
			updateWindow.show(); // 显示修改窗口
		};
		var failureFun = function(json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes('请求超时'); // 请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax('priceExtrafeeStandard!queryPriceExtrafeeStandardById.action',params, successFun, failureFun);
	},
	deletepriceExtrafeeStandard : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断是否至少选中了一条
			miser.showWoringMessage(extrafee.priceExtrafeeStandard.i18n('extrafee.priceExtrafeeStandard.oneselected')); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		miser.showQuestionMes('删除',// 是否删除
						function(e) {
							if (e == 'yes') { // 询问是否删除，是则发送请求
								var id = selections[0].get('id');
								var params = {
									'priceExtrafeeStandardVo' : {
										'priceExtrafeeStandardEntity' : {
											'id' : id
										}
									}
								};
								var successFun = function(json) {
									var message = json.message;
									if(!Ext.isEmpty(message)&&message=='miser.base.invalid'){
										miser.showErrorMes('无法修改已作废或已过期数据'); // 请求超时
									}else{
										miser.showInfoMsg(message);
										me.getStore().load(); 
									}
								};
								var failureFun = function(json) {
									if (Ext.isEmpty(json)) {
										miser.showErrorMes(extrafee.priceExtrafeeStandard.i18n('miser.extrafee.timeout')); // 请求超时
									} else {
										var message = json.message;
										miser.showErrorMes(message);
									}
								};
								miser.requestJsonAjax('priceExtrafeeStandard!deletePriceExtrafeeStandard.action',params,successFun,failureFun);
							}
						});
	},
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
        cfg = Ext.apply({},
        config);
        me.columns = [
        {
				text : '序号',
				width : 60,
				xtype : 'rownumberer',
				align : 'center'
		},
		{
			dataIndex : 'id',
			hidden : true
		},
		{
			dataIndex : 'type',
			width : 120,
			text : extrafee.priceExtrafeeStandard.i18n('extrafee.priceExtrafeeStandard.type'),
					renderer : function(value) {
						return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(extrafee.priceExtrafeeStandard.type),value);
					},
			align : 'center'
		},
		{
			dataIndex : 'transTypeName',
			text : extrafee.priceExtrafeeStandard.i18n('extrafee.priceExtrafeeStandard.transTypeCode'),
			width : 120,
			align : 'center'
		},
		{
			dataIndex : 'money',
			text : extrafee.priceExtrafeeStandard
					.i18n('extrafee.priceExtrafeeStandard.money'),
			width : 100,
			align : 'center'
		},
		{
			text : extrafee.priceExtrafeeStandard
					.i18n('extrafee.priceExtrafeeStandard.lockType'),
			dataIndex : 'lockType',
			width : 100,
			renderer : function(value) {
				return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(extrafee.priceExtrafeeStandard.lockType),value);
			},
			align : 'center'
		},
		{
			text : extrafee.priceExtrafeeStandard
					.i18n('extrafee.priceExtrafeeStandard.effectiveTime'),
			dataIndex : 'effectiveTime',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 150,
			align : 'center'
		},
		{
			text : extrafee.priceExtrafeeStandard
					.i18n('extrafee.priceExtrafeeStandard.invalidTime'),
			dataIndex : 'invalidTime',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 150,
			align : 'center'
		},{
			text : extrafee.priceExtrafeeStandard
			.i18n('extrafee.priceExtrafeeStandard.active'), // '是否有效',
				dataIndex : 'active',
				renderer : function(value) {
					return activeTypeRender(value);
				},
				width : 100,
			align : 'center'
		},{
			text : extrafee.priceExtrafeeStandard
			.i18n('extrafee.priceExtrafeeStandard.state'),
				dataIndex : 'state',
				width : 100,
				renderer : function(value) {
					return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(extrafee.priceExtrafeeStandard.state),value);
				},
			align : 'center'
		},
		{
			text : extrafee.priceExtrafeeStandard
					.i18n('extrafee.priceExtrafeeStandard.remark'), // '备注',
			dataIndex : 'remark',
			width : 100,
			align : 'center'
		},{
			text : extrafee.priceExtrafeeStandard
					.i18n('extrafee.priceExtrafeeStandard.createUser'), // '创建人',
			dataIndex : 'createUser',
			width : 100,
			align : 'center'
		},
		{
			text : extrafee.priceExtrafeeStandard
					.i18n('extrafee.priceExtrafeeStandard.createDate'), // '创建时间',
			dataIndex : 'createDate',
			renderer : function(value) {
				return dateRender(value);
			},
			width : 100,
			align : 'center'
		},
		{
			text : extrafee.priceExtrafeeStandard
					.i18n('extrafee.priceExtrafeeStandard.modifyUser'), // '修改用户',
			dataIndex : 'modifyUser',
			width : 100,
			align : 'center'
		},
		{
			text : extrafee.priceExtrafeeStandard
					.i18n('extrafee.priceExtrafeeStandard.modifyDate'), // '修改时间',
			dataIndex : 'modifyDate',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 150,
			align : 'center'
		}
        ],
        me.store = Ext.create('Miser.store.priceExtrafeeStandardStore', {
            autoLoad: true
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', { // 多选框
            mode: 'MULTI',
            checkOnly: true
        });
        me.tbar = [
                   {
					text : extrafee.priceExtrafeeStandard.i18n('extrafee.priceExtrafeeStandard.add'),
					xtype : 'addbutton',
					// 新增
					width: 80,
					handler : function() {
						me.getpriceExtrafeeStandardAddWindow().show();
					}
					},
					{
						id:'miser_biz_extrafee_priceExtrafeeStandard_Update_id',
						text : extrafee.priceExtrafeeStandard
								.i18n('extrafee.priceExtrafeeStandard.update'),
						xtype : 'updatebutton',
						width: 80,
						handler : function() {
							me.updatepriceExtrafeeStandard();
						}
					},{
					id:'miser_biz_extrafee_priceExtrafeeStandard_del_id',
					text : extrafee.priceExtrafeeStandard
							.i18n('miser.common.delete'),// '删除',
					xtype : 'deletebutton',
					width : 80,
					handler : function() {
						me.deletepriceExtrafeeStandard();
					}
					},{
						xtype: 'downloadbutton',
			            text: '导出',
			            width: 80,
			            handler:function(){
			            	down();
			            }
					}
             ];
        me.bbar = me.getPagingToolbar();
        me.selModel = Ext.create(
				'Ext.selection.CheckboxModel',
				{
					listeners : {
						selectionchange : function(
								sm, selections) {
							try{
							var value = selections[0].get('state');
							if('PASSED' == value||'DELETED' == value){
								Ext.getCmp('miser_biz_extrafee_priceExtrafeeStandard_Update_id').setDisabled(true);
								Ext.getCmp('miser_biz_extrafee_priceExtrafeeStandard_del_id').setDisabled(true);
							}else {
								Ext.getCmp('miser_biz_extrafee_priceExtrafeeStandard_Update_id').setDisabled(false);
								Ext.getCmp('miser_biz_extrafee_priceExtrafeeStandard_del_id').setDisabled(false);
							}
							}catch(e){}
						}
					}
				}),
        me.callParent([cfg]);
    }
});
Ext.onReady(function() {
	/**
	 * 数据字典页面
	 */
	Ext.QuickTips.init();
	var queryPanel = Ext.create('Miser.view.priceExtrafeeStandard.QueryPanel');
	var priceExtrafeeStandardGrid = Ext.create('Miser.view.priceExtrafeeStandard.Grid');
	Ext.create('Ext.panel.Panel', {
		renderTo : Ext.getBody(), 
		getQueryPanel: function () {
            return queryPanel;
        },
		getPriceExtrafeeStandardGrid : function() {
			return priceExtrafeeStandardGrid;
		},
		items : [ queryPanel,priceExtrafeeStandardGrid ]
	});
});