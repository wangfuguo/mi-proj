extrafee.extrafeeAddValueFee.calculationType = 'AddValueFeecalculation';
extrafee.extrafeeAddValueFee.lockType = 'LOCK_TYPE';
extrafee.extrafeeAddValueFee.state='PRICE_SATUS';
// 状态
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
				'extrafeeAddValueFeeVo.extrafeeAddValueFeeEntity.code': queryForm.getForm().findField('code').getValue(),
                'extrafeeAddValueFeeVo.extrafeeAddValueFeeEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
                'extrafeeAddValueFeeVo.extrafeeAddValueFeeEntity.state': queryForm.getForm().findField('state').getValue()
        	};
    }
	Ext.Ajax.request( {  
	       url : 'extrafeeAddValueFee!excelExport.action',  
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
 * 增值列表model
 */
Ext.define('Miser.model.ExtrafeeAddValueFeeEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'code',
		// 分段项目编号
		type : 'string'
	}, {
		name : 'codeName',
		// 分段项目编号
		type : 'string'
	},{
        name: 'transTypeCode',
        // 运输类型编号
        type: 'string'
    },{
        name: 'transTypeName',
        // 运输类型
        type: 'string'
    }, {
		name : 'calculationType',
		// 段止
		type : 'string'
	}, {
		name : 'weightPrice',
		// 计算方式
		type : 'number'
	}, {
		name : 'lightPrice',
		// 费用类型
		type : 'number'
	}, {
		name : 'lowestMoney',
		// 费用
		type : 'number'
	}, {
		name : 'lockType',
		// 是否单独进行结算
		type : 'number'
	}, {
		name : 'effectiveTime',
		// 最后修改时间
		type : 'date',
		dateFormat : 'time'
	}, {
		name : 'invalidTime',
		// 最后修改时间
		type : 'date',
		dateFormat : 'time'
	}, {
		name : 'remark',
		// 备注
		type : 'string'
	}, {
		name : 'active',
		// 是否有效
		type : 'string'
	}, {
		name : 'state',
		// 状态
		type : 'string'
	}, {
		name : 'modifyUser',
		// 最后修改人
		type : 'string'
	}, {
		name : 'modifyDate',
		// 最后修改时间
		type : 'date',
		dateFormat : 'time'
	} ]
});

/**
 * 增加表单
 */
Ext
		.define(
				'Miser.view.extrafee.ExtrafeeAddValueFeeAddForm',
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
							width: 280,
							name : 'code',
							allowBlank : false,
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.code'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype: 'dynamicPriceAddvalueFeeItemscombselector'
						},
						{
							colspan: 2,
							width: 280,
							allowBlank : false,
							xtype : 'transtypecombselector',
							showAll : false,
							name : 'transTypeCode',
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.transTypeCode'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						},
						{
							xtype : "numberfield",
							step:1000,
							minValue: 0,
							maxValue: 99999999.99,
							minText:'输入有误',
							name : 'weightPrice',
							allowBlank : true,
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.weightPrice'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, {
							xtype : "numberfield",
							step:1000,
							minValue: 0,
							maxValue: 99999999.99,
							minText:'输入有误',
							name : 'lightPrice',
							allowBlank : false,
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.lightPrice'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, {
							xtype : "numberfield",
							step:1000,
							minValue: 0,
							maxValue: 99999999.99,
							minText:'输入有误',
							name : 'lowestMoney',
							allowBlank : false,
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.lowestMoney'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, 
						{
						
						xtype : "textfield",
						name : 'lockType',
						allowBlank : false,
						fieldLabel : extrafee.extrafeeAddValueFee
								.i18n('extrafee.extrafeeAddValueFee.lockType'),
						beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
						xtype : 'dataDictionarySelector',
						termsCode : extrafee.extrafeeAddValueFee.lockType
						}, {
							xtype: 'datetimefield',
				            format: 'Y-m-d H:i:s',
							minThisDate : true,
							name : 'effectiveTime',
							allowBlank : false,
							fieldLabel : extrafee.extrafeeAddValueFee.i18n('extrafee.extrafeeAddValueFee.effectiveTime'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, {
							xtype : "textfield",
							name : 'calculationType',
							allowBlank : false,
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.calculationType'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype : 'dataDictionarySelector',
							termsCode : extrafee.extrafeeAddValueFee.calculationType
						}, {
							colspan: 2,
							width: 540,
							labelSeparator : ':&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
							fieldLabel : extrafee.extrafeeAddValueFee
							.i18n('extrafee.extrafeeAddValueFee.remark'), // '备注',
							name : 'remark',
							xtype : 'textarea'
						}
						];
						me.callParent([ cfg ]);
					}
				});
/**
 * 修改表单
 */
Ext.define(
				'Miser.view.extrafee.ExtrafeeAddValueFeeUpdateForm',
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
							xtype : 'textfield',
							hidden : 'true',
							name : 'transTypeCode'
						},
						{
							name : 'state',
							fieldLabel : 'state',
							xtype : 'textfield',
							hidden : true
						},
						{
							name : 'code',
							xtype : 'textfield',
							hidden : true
						},
						
						{
							colspan: 2,
							width: 280,
							name : 'codeName',
							xtype : 'textfield',
							fieldLabel : extrafee.extrafeeAddValueFee
							.i18n('extrafee.extrafeeAddValueFee.code'),
							readOnly : true
						},
						{
							colspan: 2,
							width: 280,
							xtype : 'textfield',
							name : 'transTypeName',
							readOnly :true,
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.transTypeCode'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						},
						{
							xtype : "numberfield",
							step:1000,
							minValue: 0,
							maxValue: 99999999.99,
							minText:'输入有误',
							name : 'weightPrice',
							allowBlank : false,
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.weightPrice'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, {
							xtype : "numberfield",
							step:1000,
							minValue: 0,
							maxValue: 99999999.99,
							minText:'输入有误',
							name : 'lightPrice',
							allowBlank : false,
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.lightPrice'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, {
							xtype : "numberfield",
							step:1000,
							minValue: 0,
							maxValue: 99999999.99,
							minText:'输入有误',
							name : 'lowestMoney',
							allowBlank : false,
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.lowestMoney'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, 
						{
							name : 'lockType',
							allowBlank : false,
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.lockType'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype : 'dataDictionarySelector',
							termsCode : extrafee.extrafeeAddValueFee.lockType
						}, {
							xtype: 'datetimefield',
				            format: 'Y-m-d H:i:s',
							minThisDate : true,
							name : 'effectiveTime',
							allowBlank : false,
							fieldLabel : extrafee.extrafeeAddValueFee.i18n('extrafee.extrafeeAddValueFee.effectiveTime'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, {
							xtype : "textfield",
							name : 'calculationType',
							allowBlank : false,
							fieldLabel : extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.calculationType'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype : 'dataDictionarySelector',
							termsCode : extrafee.extrafeeAddValueFee.calculationType
						}, {
							colspan: 2,
							width: 540,
							labelSeparator : ':&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
							fieldLabel : extrafee.extrafeeAddValueFee
							.i18n('extrafee.extrafeeAddValueFee.remark'), // '备注',
							name : 'remark',
							xtype : 'textarea'
						}
						];
						me.callParent([ cfg ]);
					}
				});
// 增加窗体
Ext.define('Miser.view.extrafee.ExtrafeeAddValueFeeAddWindow', {
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
			me.getExtrafeeAddValueFeeAddForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getExtrafeeAddValueFeeAddForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			me.getExtrafeeAddValueFeeAddForm().getForm().findField("effectiveTime").setValue(new Date(new Date().getTime() + 5*60*1000));
		}
	},
	ExtrafeeAddValueFeeAddForm : null,
	getExtrafeeAddValueFeeAddForm : function() {
		if (Ext.isEmpty(this.ExtrafeeAddValueFeeAddForm)) {
			this.ExtrafeeAddValueFeeAddForm = Ext
					.create('Miser.view.extrafee.ExtrafeeAddValueFeeAddForm');
		}
		return this.ExtrafeeAddValueFeeAddForm;
	},
	submitExtrafeeAddValueFeeAddForm : function() {
		var me = this;
		if (me.getExtrafeeAddValueFeeAddForm().getForm().isValid()) { // 校验form是否通过校验
			var ExtrafeeAddValueFeeEntity = new Miser.model.ExtrafeeAddValueFeeEntity();
			me.getExtrafeeAddValueFeeAddForm().getForm().updateRecord(
					ExtrafeeAddValueFeeEntity); // 将FORM中数据设置到MODEL里面
			var curData = ExtrafeeAddValueFeeEntity.data;
			var params = {
				'extrafeeAddValueFeeVo' : {
					'extrafeeAddValueFeeEntity' : curData
				}
			}
			params.extrafeeAddValueFeeVo.extrafeeAddValueFeeEntity.isAlert='0';
			var successFun = function(json) {
				var message = json.message;
				if(!Ext.isEmpty(message)&&message=='miser.base.isAlert'){
					miser.showQuestionMes('该增值费存在相同的待生效数据，是否覆盖？',// 是否删除
							function(e) {
								if (e == 'yes') {
									params.extrafeeAddValueFeeVo.extrafeeAddValueFeeEntity.isAlert='1';
									miser.requestJsonAjax('extrafeeAddValueFee!addExtrafeeAddValueFee.action',
											params, successFun, failureFun);// 发送AJAX请求
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
					miser.showErrorMes(extrafee.extrafeeAddValueFee
							.i18n('miser.extrafee.timeout')); // 请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message); // 提示失败原因
				}
			};
			miser.requestJsonAjax('extrafeeAddValueFee!addExtrafeeAddValueFee.action',
					params, successFun, failureFun); // 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : extrafee.extrafeeAddValueFee.i18n('miser.common.save'),// '保存',
			// 保存
			/* margin : '0 0 0 55', */
			handler : function() {
				me.submitExtrafeeAddValueFeeAddForm();
			}
		}, {
			text : extrafee.extrafeeAddValueFee.i18n('miser.common.cancel'),// '取消',
			// 取消
			handler : function() {
				me.close();
			}
		} ];
		me.items = [ me.getExtrafeeAddValueFeeAddForm()];
		me.callParent([ cfg ]);
	}
});
/**
 * 修改增值窗口
 */
Ext.define('Miser.view.extrafee.ExtrafeeAddValueFeeUpdateWindow', {
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
	extrafeeAddValueFeeEntity : null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getExtrafeeAddValueFeeUpdateForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getExtrafeeAddValueFeeUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			me.getExtrafeeAddValueFeeUpdateForm().getForm().loadRecord(new Miser.model.ExtrafeeAddValueFeeEntity(me.extrafeeAddValueFeeEntity));
			me.getExtrafeeAddValueFeeUpdateForm().getForm().findField("effectiveTime").setValue(new Date(new Date().getTime() + 5*60*1000));
		}
	},
	ExtrafeeAddValueFeeUpdateForm : null,
	getExtrafeeAddValueFeeUpdateForm : function() {
		if (Ext.isEmpty(this.ExtrafeeAddValueFeeUpdateForm)) {
			this.ExtrafeeAddValueFeeUpdateForm = Ext.create('Miser.view.extrafee.ExtrafeeAddValueFeeUpdateForm');
		}
		return this.ExtrafeeAddValueFeeUpdateForm;
	},
	submitDriver : function() {
		var me = this;
		if (me.getExtrafeeAddValueFeeUpdateForm().getForm().isValid()) { // 校验form是否通过校验
			var ExtrafeeAddValueFeeEntity = new Miser.model.ExtrafeeAddValueFeeEntity();
			me.getExtrafeeAddValueFeeUpdateForm().getForm().updateRecord(
					ExtrafeeAddValueFeeEntity); // 将FORM中数据设置到MODEL里面
			var curData = ExtrafeeAddValueFeeEntity.data;
			var params = {
				'extrafeeAddValueFeeVo' : {
					'extrafeeAddValueFeeEntity' : curData
				}
			}
			params.extrafeeAddValueFeeVo.extrafeeAddValueFeeEntity.isAlert='0';
			var successFun = function(json) {
				var message = json.message;
				if(!Ext.isEmpty(message)&&message=='miser.base.invalid'){
					miser.showErrorMes('无法修改已作废或已过期数据'); // 请求超时
				}else if(!Ext.isEmpty(message)&&message=='miser.base.isAlert'){
					miser.showQuestionMes('该增值费存在相同的待生效数据，是否覆盖？',// 是否删除
							function(e) {
								if (e == 'yes') {
									params.extrafeeAddValueFeeVo.extrafeeAddValueFeeEntity.isAlert='1';
									miser.requestJsonAjax('extrafeeAddValueFee!updateExtrafeeAddValueFee.action',
											params, successFun, failureFun); // 发送AJAX请求
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
			miser.requestJsonAjax('extrafeeAddValueFee!updateExtrafeeAddValueFee.action',
					params, successFun, failureFun); // 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : extrafee.extrafeeAddValueFee.i18n('miser.common.save'), // 保存
			margin : '0 0 0 305',
			handler : function() {
				me.submitDriver();
			}
		}, {
			text : extrafee.extrafeeAddValueFee.i18n('miser.common.cancel'), // 取消
			handler : function() {
				me.close();
			}
		} ];
		me.items = [ me.getExtrafeeAddValueFeeUpdateForm()];
		me.callParent([ cfg ]);
	}
});
/**
 * 增值列表store
 */
Ext.define('Miser.store.ExtrafeeAddValueFeeStore',
			{
				extend : 'Ext.data.Store',
				model : 'Miser.model.ExtrafeeAddValueFeeEntity',
				pageSize : 20,
				proxy : {
					type : 'ajax',
					actionMethods : 'post',
					url : 'extrafeeAddValueFee!queryListByParam.action',
					reader : {
						type : 'json',
						rootProperty : 'extrafeeAddValueFeeVo.extrafeeAddValueFeeList',
						totalProperty : 'totalCount' // 总个数
					}
				},
				listeners : {
				 beforeload: function (store, operation, opts) {
		            var queryPanel = Ext.getCmp('queryPanel');
		            if (queryPanel != null) {
		                var params = {
		                    'extrafeeAddValueFeeVo.extrafeeAddValueFeeEntity.code': queryPanel.getForm().findField('code').getValue(),
		                    'extrafeeAddValueFeeVo.extrafeeAddValueFeeEntity.transTypeCode': queryPanel.getForm().findField('transTypeCode').getValue(),
		                    'extrafeeAddValueFeeVo.extrafeeAddValueFeeEntity.state': queryPanel.getForm().findField('state').getValue()
		                };
		                Ext.apply(store.proxy.extraParams, params);
		            }
		        }
				}
	});
//定义查询面板
Ext.define('Miser.view.ExtrafeeAddValueFee.QueryPanel', {
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
            name: 'code',
            fieldLabel: extrafee.extrafeeAddValueFee.i18n('extrafee.extrafeeAddValueFee.code'),
            xtype: 'dynamicPriceAddvalueFeeItemscombselector'
        }, {
            name: 'transTypeCode',
            fieldLabel: extrafee.extrafeeAddValueFee
									.i18n('extrafee.extrafeeAddValueFee.transTypeCode'),
            xtype: 'transtypecombselector'
        }, {
            name: 'state',
            fieldLabel: '状态',
            xtype: 'dataDictionarySelector',
            anyRecords:all,
            termsCode : extrafee.extrafeeAddValueFee.state
        }];
        me.buttons = [{
            text: '查询',
            handler: function () {
                if (me.getForm().isValid()) {
                    me.up().getExtrafeeAddValueFeeGrid().getPagingToolbar().moveFirst();
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
Ext.define('Miser.view.ExtrafeeAddValueFee.Grid', {
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
	ExtrafeeAddValueFeeAddWindow : null,
	getExtrafeeAddValueFeeAddWindow : function() {
		if (Ext.isEmpty(this.ExtrafeeAddValueFeeAddWindow)) {
			this.ExtrafeeAddValueFeeAddWindow = Ext
					.create('Miser.view.extrafee.ExtrafeeAddValueFeeAddWindow');
			this.ExtrafeeAddValueFeeAddWindow.parent = this; // 父元素
		}
		return this.ExtrafeeAddValueFeeAddWindow;
	},
	ExtrafeeAddValueFeeUpdateWindow : null,
	getExtrafeeAddValueFeeUpdateWindow : function() {
		if (Ext.isEmpty(this.ExtrafeeAddValueFeeUpdateWindow)) {
			this.ExtrafeeAddValueFeeUpdateWindow = Ext
					.create('Miser.view.extrafee.ExtrafeeAddValueFeeUpdateWindow');
			this.ExtrafeeAddValueFeeUpdateWindow.parent = this; // 父元素
		}
		return this.ExtrafeeAddValueFeeUpdateWindow;
	},
	updateExtrafeeAddValueFee : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		var id = selections[0].get('id');
		var params = {
			'extrafeeAddValueFeeVo' : {
				'extrafeeAddValueFeeEntity' : {
					'id' : id
				}
			}
		};
		var successFun = function(json) {
			var updateWindow = me.getExtrafeeAddValueFeeUpdateWindow(); // 获得修改窗口
			updateWindow.extrafeeAddValueFeeEntity = json.extrafeeAddValueFeeVo.extrafeeAddValueFeeEntity;
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
		miser.requestJsonAjax('extrafeeAddValueFee!queryExtrafeeAddValueFeeById.action',params, successFun, failureFun);
	},
	deleteExtrafeeAddValueFee : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断是否至少选中了一条
			miser.showWoringMessage(extrafee.extrafeeAddValueFee.i18n('extrafee.extrafeeAddValueFee.oneselected')); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		miser.showQuestionMes('删除',// 是否删除
						function(e) {
							if (e == 'yes') { // 询问是否删除，是则发送请求
								var id = selections[0].get('id');
								var params = {
									'extrafeeAddValueFeeVo' : {
										'extrafeeAddValueFeeEntity' : {
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
										miser.showErrorMes(extrafee.extrafeeAddValueFee.i18n('miser.extrafee.timeout')); // 请求超时
									} else {
										var message = json.message;
										miser.showErrorMes(message);
									}
								};
								miser.requestJsonAjax('extrafeeAddValueFee!deleteExtrafeeAddValueFee.action',params,successFun,failureFun);
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
			dataIndex : 'codeName',
			width : 100,
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.code'),
			align : 'center'
		},
		{
			dataIndex : 'transTypeName',
			width : 120,
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.transTypeCode'),
			align : 'center'
		},
		{
			dataIndex : 'weightPrice',
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.weightPrice'),
			width : 120,
			align : 'center'
		},
		{
			dataIndex : 'lightPrice',
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.lightPrice'),
			width : 100,
			align : 'center'
		},
		{
			dataIndex : 'lowestMoney',
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.lowestMoney'),
			width : 100,
			align : 'center'
		},
		{
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.lockType'),
			dataIndex : 'lockType',
			width : 100,
			renderer : function(value) {
				return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(extrafee.extrafeeAddValueFee.lockType),value);
			},
			align : 'center'
		},
		{
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.calculationType'),
			dataIndex : 'calculationType',
			width : 100,
			renderer : function(value) {
				return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(extrafee.extrafeeAddValueFee.calculationType),value);
			},
			align : 'center'
		},
		{
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.effectiveTime'),
			dataIndex : 'effectiveTime',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 100,
			align : 'center'
		},
		{
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.invalidTime'),
			dataIndex : 'invalidTime',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 100,
			align : 'center'
		},{
			text : extrafee.extrafeeAddValueFee
			.i18n('extrafee.extrafeeAddValueFee.active'), // '是否有效',
				dataIndex : 'active',
				renderer : function(value) {
					return activeTypeRender(value);
				},
				width : 100,
			align : 'center'
		},{
			text : extrafee.extrafeeAddValueFee
			.i18n('extrafee.extrafeeAddValueFee.state'),
				dataIndex : 'state',
				width : 100,
				renderer : function(value) {
					return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(extrafee.extrafeeAddValueFee.state),value);
				},
			align : 'center'
		},
		{
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.remark'), // '备注',
			dataIndex : 'remark',
			width : 100,
			align : 'center'
		},
		{
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.createUser'), // '创建人',
			dataIndex : 'createUser',
			width : 100,
			align : 'center'
		},
		{
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.createDate'), // '创建时间',
			dataIndex : 'createDate',
			renderer : function(value) {
				return dateRender(value);
			},
			width : 100,
			align : 'center'
		},
		{
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.modifyUser'), // '修改用户',
			dataIndex : 'modifyUser',
			width : 100,
			align : 'center'
		},
		{
			text : extrafee.extrafeeAddValueFee
					.i18n('extrafee.extrafeeAddValueFee.modifyDate'), // '修改时间',
			dataIndex : 'modifyDate',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 150,
			align : 'center'
		}
        ],
        me.store = Ext.create('Miser.store.ExtrafeeAddValueFeeStore', {
            autoLoad: true
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', { // 多选框
            mode: 'MULTI',
            checkOnly: true
        });
        me.tbar = [
                   {
					text : extrafee.extrafeeAddValueFee.i18n('extrafee.extrafeeAddValueFee.add'),
					xtype : 'addbutton',
					// 新增
					width: 80,
					handler : function() {
						me.getExtrafeeAddValueFeeAddWindow().show();
					}
					},
					{
						id:'miser_biz_extrafee_extrafeeAddValue_Update_id',
						text : extrafee.extrafeeAddValueFee
								.i18n('extrafee.extrafeeAddValueFee.update'),
						xtype : 'updatebutton',
						width: 80,
						handler : function() {
							me.updateExtrafeeAddValueFee();
						}
					},{
					id:'miser_biz_extrafee_extrafeeAddValue_del_id',
					text : extrafee.extrafeeAddValueFee
							.i18n('miser.common.delete'),// '删除',
					xtype : 'deletebutton',
					width : 80,
					handler : function() {
						me.deleteExtrafeeAddValueFee();
					}
					}
					,{
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
								Ext.getCmp('miser_biz_extrafee_extrafeeAddValue_Update_id').setDisabled(true);
								Ext.getCmp('miser_biz_extrafee_extrafeeAddValue_del_id').setDisabled(true);
							}else {
								Ext.getCmp('miser_biz_extrafee_extrafeeAddValue_Update_id').setDisabled(false);
								Ext.getCmp('miser_biz_extrafee_extrafeeAddValue_del_id').setDisabled(false);
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
	var queryPanel = Ext.create('Miser.view.ExtrafeeAddValueFee.QueryPanel');
	var ExtrafeeAddValueFeeGrid = Ext.create('Miser.view.ExtrafeeAddValueFee.Grid');
	Ext.create('Ext.panel.Panel', {
		renderTo : Ext.getBody(),
		getQueryPanel: function () {
            return queryPanel;
        },
		getExtrafeeAddValueFeeGrid : function() {
			return ExtrafeeAddValueFeeGrid;
		},
		items : [ queryPanel,ExtrafeeAddValueFeeGrid ]
	});
});