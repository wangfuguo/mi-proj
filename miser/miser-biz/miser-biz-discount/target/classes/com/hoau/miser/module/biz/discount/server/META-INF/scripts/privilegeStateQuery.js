discount.privilegeStateQuery.wether = 'IS_ACTIVE'; // 是否删除
discount.privilegeStateQuery.activityState = 'ACTIVITY_STATE'; // 有效状态
discount.privilegeStateQuery.privilegeCouponState = 'PRIVILEGE_COUPON_STATE'; // 返券状态
// 最后查询条件
var lastQueryCondition = null;

/**
 * 返券状态Renderer
 * @param value
 * @param record
 * @returns {String}
 */
function couponStateRender(value, record) {
	value = Ext.String.trim(value);
	var hasCoupon = record.record.data['hasCoupon'];
	var shouldPayAmountSumValue = record.record.data['shouldPayAmountSum'];
	if (Ext.isEmpty(value)
			&& hasCoupon == "Y"
			&& (Ext.isEmpty(shouldPayAmountSumValue) || parseFloat(shouldPayAmountSumValue) == 0.0)) {
		value = '可申请';
	} else if ('PENDING_CHECK' == value) {
		value = '待审核';
	} else if ('CHECK_SUCCESS' == value) {
		value = '审核完成';
	} else if ('CHECK_REJECT' == value) {
		value = '审核失败';
	} else if ('COUPON_FAIL' == value) {
		value = '返券失败';
	} else {
		value = '不可申请';
	}
	return value;
}

Ext.define('Miser.model.couponAcceptorEntity', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'acceptorName',
						type : 'string'
					}, {
						name : 'acceptorPhone',
						type : 'string'
					}, {
						name : 'identityCardNo',
						type : 'string'
					}, {
						name : 'bankName',
						type : 'string'
					}, {
						name : 'subbranchName',
						type : 'string'
					}, {
						name : 'creditCardNo',
						type : 'string'
					}, {
						name : 'relationshipWithCustomer',
						type : 'string'
					}, {
						name : 'alipayAccount',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'createUser',
						type : 'string'
					}, {
						name : 'createDate',
						type : 'date',
						dateFormat : 'time'
					}, {
						name : 'modifyUser',
						type : 'string'
					}, {
						name : 'modifyDate',
						type : 'date',
						dateFormat : 'time'
					}, {
						name : 'contractCode',
						type : 'string'
					}, {
						name : 'recordMonth',
						type : 'date',
						dateFormat : 'time'
					}]
		});
/**
 * 越发越惠 状态查询 列表model
 */
Ext.define('Miser.model.privilegeStateQueryEntity', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'contractCode',
						type : 'string'
					}, {
						name : 'customerCode',
						type : 'string'
					}, {
						name : 'customerName',
						type : 'string'
					}, {
						name : 'privilegeStartTime',
						type : 'date',
						dateFormat : 'time'
					}, {
						name : 'privilegeEndTime',
						type : 'date',
						dateFormat : 'time'
					}, {
						name : 'tpdId',
						type : 'string'
					}, {
						name : 'recordMonth',
						type : 'date',
						dateFormat : 'time'
					}, {
						name : 'lastLastMonthAmount',
						type : 'number'
					}, {
						name : 'lastMonthAmount',
						type : 'number'
					}, {
						name : 'lastTwoMonthsAverageAmount',
						type : 'number'
					}, {
						name : 'currentMonthAmount',
						type : 'number'
					}, {
						name : 'currentDuDiscount',
						type : 'number'
					}, {
						name : 'currentDdDiscount',
						type : 'number'
					}, {
						name : 'tpcId',
						type : 'string'
					}, {
						name : 'hasCoupon',
						type : 'string'
					}, {
						name : 'couponScale',
						type : 'number'
					}, {
						name : 'couponAmount',
						type : 'number'
					}, {
						name : 'shouldPayAmountSum',
						type : 'number'
					}, {
						name : 'couponState',
						type : 'string'
					}, {
						name : 'checkRemark',
						type : 'string'
					}, {
						name : 'checkTime',
						type : 'date',
						dateFormat : 'time'
					}, {
						name : 'couponRemark',
						type : 'string'
					}, {
						name : 'couponTime',
						type : 'date',
						dateFormat : 'time'
					},{
						name:'contactName',
						type : 'string'
					},{
						name:'contactPhone',
						type : 'string'
					},{
						name:'contactPhone',
						type : 'string'
					},{
						name:'contractStartTime',
						type : 'date',
						dateFormat : 'time'
					},{
						name:'contractEndTime',
						type : 'date',
						dateFormat : 'time'
					},{
						name:'remark',
						type : 'string'
					}]
		});

/**
 * 越发越惠 状态查询明细 列表model
 */
Ext.define('Miser.model.privilegeStateQueryDetailEntity', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'customerContractId',
						type : 'string'
					}, {
						name : 'startAmount',
						// 发货金额段起
						type : 'number'
					}, {
						name : 'endAmount',
						// 发货金额段止
						type : 'number'
					}, {
						name : 'ddMinFreightDiscount',
						// 定日达纯运费最低折扣
						type : 'number'
					}, {
						name : 'duMinFreightDiscount',
						// 经济快运纯运费最低折扣
						type : 'number'
					}, {
						name : 'maxCouponScale',
						// 最高返券比例
						type : 'number'
					}, {
						name : 'dataOrign',
						// 数据来源
						type : 'string'
					}, {
						name : 'remark',
						// 备注
						type : 'string'
					}, {
						name : 'active',
						// 是否有效
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
					}]
		});

/**
 * 状态查询 查询表单
 */
Ext.define('Miser.view.privilegeStateQuery.QueryForm', {
	extend : 'Ext.form.Panel',
	id : 'queryForm',
	frame : true,
	width : document.documentElement.clientWidth - 20,
	title : discount.privilegeStateQuery.i18n('miser.common.queryCondition'),// '查询条件',
	collapsible : true,
	frame : true,
	defaults : {
		labelWidth : 0.3,
		columnWidth : 0.6,
		margin : '8 10 5 10',
		labelAlign : 'right'
	},
	fieldDefaults: {
		labelWidth : 100,
		labelAlign : 'right',
        margin : '5 0 5 0'
    },
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var row1 = {
			layout : 'column',
			border : false,
			items : [{
						loaddefault : true,
						width : document.documentElement.clientWidth - 25,
						divisionLabelWidth : 100,
						bigregionLabelWidth : 100,
						roadareaLabelWidth : 100,
						salesdepartmentLabelWidth : 100,
						//名称
						divisionLabel : '事业部:',
						divisionName : 'seaDivision',
						divisionIsBlank : true,
						//名称
						bigregionLabel : '大区:',
						bigregionName : 'seaBigregion',
						bigregionIsBlank : true,
						// 
						roadareaLabel : '路区:',
						roadareaName : 'seaRoadarea',
						roadareaIsBlank : true,
						//省名称
						salesdepartmentLabel : '门店:',
						salesdepartmentName : 'seaSalesdepartment',
						salesdepartmentIsBlank : true,
						// 县名称
						type : 'D-B-R-S',
						xtype : 'linkorgcombselector'
					}]
		};
		var row2 = {
			layout : 'column',
			border : false,
			items : [{
				name : 'customerCode',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.customerCode'), // 越发越惠客户编号
				xtype : 'textfield'
			}, {
				name : 'customerName',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.customerName'),
				xtype : 'textfield'
			}, {
				name : 'hasCoupon',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.hasCoupon'),
				xtype : 'dataDictionarySelector',
				termsCode : discount.privilegeStateQuery.wether,
				anyRecords : all
			}, {
				name : 'contractStartAndEndTime',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.contractStartAndEndTime'),
				xtype : 'datefield',
				format : 'Y-m'
			}]
		};
		var row3 = {
			layout : 'column',
			border : false,
			items : [{
				name : 'couponState',		
				fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.couponState'),
				xtype : 'dataDictionarySelector',
				termsCode: discount.privilegeStateQuery.privilegeCouponState,
				anyRecords : all
			}]
		}
		me.items = [row1, row2,row3], me.buttons = [{
			text : discount.privilegeStateQuery.i18n('miser.common.query'),
			handler : function() {
				if (me.getForm().isValid()) {
					me.up().getprivilegeStateQueryGrid().getPagingToolbar()
							.moveFirst();
					setORG(me);
				}
			}
		}, {
			text : discount.privilegeStateQuery.i18n('miser.common.reset'),
			handler : function() {
				me.getForm().reset();
				setORG(me);
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 状态查询 修改表单
 */
Ext.define('Miser.view.discount.privilegeStateQueryUpdateForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	width : document.documentElement.clientWidth - 25,
	region : 'north',
	defaults : {
		labelWidth : 0.4,
		columnWidth : 0.6,
		margin : '8 10 5 10',
		labelAlign : 'right'
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			layout : 'hbox',
			defaults : {
				labelWidth : 70,
				labelAlign : "right"
			},
			defaults : {
				margins : '10 10 5 5'
			},
			items : [{
				name : 'customerCode',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.customerCode'),
				xtype : 'textfield',
				readOnly : true

			}, {
				name : 'contactName',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.contactName'),
				xtype : 'textfield',
				readOnly : true
			}, {
				name : 'contactPhone',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.contactPhone'),
				xtype : 'textfield',
				readOnly : true
			}, {
				name : 'hasCoupon',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.hasCoupon'),
				allowBlank : false,
				readOnly : true,
				xtype : 'textfield'
			}]
		}, {
			layout : 'hbox',
			defaults : {
				labelWidth : 70,
				labelAlign : "right"
			},
			defaults : {
				margins : '10 10 5 5'
			},
			items : [{
				name : 'contractStartTime',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.contractStartTime'),
				allowBlank : false,
				readOnly : true,
				xtype : 'datefield',
				format : 'Y-m-d H:i:s'
			}, {
				name : 'contractEndTime',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.contractEndTime'),
				readOnly : true,
				xtype : 'datefield',
				format : 'Y-m-d H:i:s'
			}, {
				name : 'privilegeStartTime',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.privilegeStartTime'),
				readOnly : true,
				xtype : 'datefield',
				format : 'Y-m-d H:i:s'
			}, {
				readOnly : true,
				name : 'privilegeEndTime',
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.privilegeEndTime'),
				xtype : 'datefield',
				format : 'Y-m-d H:i:s'
			}]
		}, {
			layout : 'hbox',
			defaults : {
				labelWidth : 70,
				labelAlign : "right"
			},
			defaults : {
				margins : '10 10 5 5'
			},
			items : [{
				fieldLabel : discount.privilegeStateQuery
						.i18n('discount.privilegeStateQuery.remark'), // '备注',
				name : 'remark',
				width : document.documentElement.clientWidth - 25,
				xtype : 'textarea',
				readOnly : true
			}]
		}];
		me.callParent([cfg]);
	}
});
/**
 * 新增越发越惠状态查询store 无action请求
 */
Ext.define('Miser.store.privilegeStateQueryDetailAddStore', {
			extend : 'Ext.data.Store',
			model : 'Miser.model.privilegeStateQueryDetailEntity'
});

/**
 * 修改时候的grid
 */
Ext.define('Miser.view.privilegeStateQueryDetail.UpdateGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	autoScroll : true,
	width : document.documentElement.clientWidth - 25,
	height : document.documentElement.clientHeight - 360,
	stripeRows : true,
	// 交替行效果
	region : 'center',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
					dataIndex : 'id',
					hidden : true
				}, {
					dataIndex : 'customerContractId',
					hidden : true
				}, {
					dataIndex : 'startAmount',
					width : 120,
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.startAmount'),
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.endAmount'),
					dataIndex : 'endAmount',
					width : 120,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.ddMinFreightDiscount'),
					dataIndex : 'ddMinFreightDiscount',
					width : 100,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.duMinFreightDiscount'),
					dataIndex : 'ddMinFreightDiscount',
					width : 100,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.maxCouponScale'),
					dataIndex : 'maxCouponScale',
					width : 100,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.dataOrign'),
					dataIndex : 'dataOrign',
					width : 100,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.state'),
					dataIndex : 'state',
					width : 100,
					renderer : function(value) {
						return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(discount.privilegeStateQuery.activityState), value);
					},
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.remark'), // '备注',
					dataIndex : 'remark',
					width : 100,
					align : 'center'
				}];
		me.store = Ext.create(
		'Miser.store.privilegeStateQueryStore', {
			autoLoad : false
		});
		me.tbar = null;
		me.tbar = [];
		me.callParent([cfg]);
	}
});
Ext.define('Miser.view.discount.privilegeStateQueryUpdateWindow', {
	extend : 'Ext.window.Window',
	title : '合同详情',
	closable : true,
	parent : null,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	privilegeStateQueryEntity : null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getprivilegeStateQueryUpdateForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getprivilegeStateQueryUpdateForm().getForm()
					.getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
							item.clearInvalid();
							item.unsetActiveError();
						});
			}
			fielsds = null;
			try{
			me.getprivilegeStateQueryUpdateForm().getForm().findField('customerCode').setValue(me.privilegeStateQueryEntity.customerCode);
			me.getprivilegeStateQueryUpdateForm().getForm().findField('privilegeStartTime').setValue(new Date(me.privilegeStateQueryEntity.privilegeStartTime));
			me.getprivilegeStateQueryUpdateForm().getForm().findField('privilegeEndTime').setValue(new Date(me.privilegeStateQueryEntity.privilegeEndTime));
			me.getprivilegeStateQueryUpdateForm().getForm().findField('remark').setValue(me.privilegeStateQueryEntity.remark);
			me.getprivilegeStateQueryUpdateForm().getForm().findField('contactName').setValue(me.privilegeStateQueryEntity.contactName);
			me.getprivilegeStateQueryUpdateForm().getForm().findField('contactPhone').setValue(me.privilegeStateQueryEntity.contactPhone);
			me.getprivilegeStateQueryUpdateForm().getForm().findField('contractStartTime').setValue(new Date(me.privilegeStateQueryEntity.contractStartTime));
			me.getprivilegeStateQueryUpdateForm().getForm().findField('contractEndTime').setValue(new Date(me.privilegeStateQueryEntity.contractEndTime));
			me.getprivilegeStateQueryUpdateForm().getForm().findField('hasCoupon').setValue(me.privilegeStateQueryEntity.hasCoupon=='Y'?'是':'否');
			}catch(e){}
			
		}
	},
	privilegeStateQueryUpdateForm : null,
	getprivilegeStateQueryUpdateForm : function() {
		if (Ext.isEmpty(this.privilegeStateQueryUpdateForm)) {
			this.privilegeStateQueryUpdateForm = Ext
					.create('Miser.view.discount.privilegeStateQueryUpdateForm');
		}
		return this.privilegeStateQueryUpdateForm;
	},
	privilegeStateQueryDetailUpdateGrid : null,
	getprivilegeStateQueryDetailUpdateGrid : function() {
		if (Ext.isEmpty(this.privilegeStateQueryDetailUpdateGrid)) {
			this.privilegeStateQueryDetailUpdateGrid = Ext
					.create('Miser.view.privilegeStateQueryDetail.UpdateGrid');
			this.privilegeStateQueryDetailUpdateGrid.parent = this; // 父元素
		}
		return this.privilegeStateQueryDetailUpdateGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
					text : discount.privilegeStateQuery
							.i18n('miser.common.cancel'), // 取消
					handler : function() {
						me.close();
					}
				}];
		me.items = [me.getprivilegeStateQueryUpdateForm(),
				me.getprivilegeStateQueryDetailUpdateGrid()];
		me.callParent([cfg]);
	}
});

/**
 * 增加表单
 */
Ext.define('Miser.view.discount.StateQueryAddForm', {
	extend : 'Ext.form.Panel',
	header : false,
	frame : true,
	collapsible : true,
	width : 600,
	defaults : {
		labelWidth : 100,
		margin : '8 10 5 10'
	},
	layout : {
		type : 'table',
		columns : 2
	},
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
					name : 'id',
					xtype : 'hiddenfield'
				}, {
					name : 'contractCode',
					xtype : 'hiddenfield'
				}, {
					name : 'seaDivision',
					readOnly : true,
					width : 200,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.seaDivision'),
					xtype : 'textfield'
				}, {
					name : 'seaBigregion',
					readOnly : true,
					width : 200,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.seaBigregion'),
					xtype : 'textfield'
				}, {
					name : 'seaRoadarea',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.seaRoadarea'),
					xtype : 'textfield'
				}, {
					name : 'seaSalesdepartment',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.seaSalesdepartment'),
					xtype : 'textfield'
				}, {
					name : 'customerCode',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.customerCode'),
					xtype : 'textfield'
				}, {
					name : 'customerName',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.customerName'),
					xtype : 'textfield'
				}, {
					name : 'contactName',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.contactName'),
					xtype : 'textfield'
				}, {

					name : 'contactPhone',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.contactPhone'),
					xtype : 'textfield'
				}, {
					name : 'couponScale',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.couponScale'),
					xtype : 'numberfield'
				}, {
					name : 'couponAmount',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.couponAmount'),
					xtype : 'numberfield'
				}, {
					name : 'recordMonth',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.recordMonth'),
					xtype : 'datetimefield',
					format : 'Y-m'
				}, {
					name : 'currentMonthAmount',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.currentMonthAmount'),
					xtype : 'numberfield'
				}, {
					xtype : "textfield",
					name : 'acceptorName',
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.acceptorName')
				}, {
					xtype : "textfield",
					name : 'relationshipWithCustomer',
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.relationshipWithCustomer')
				}, {
					xtype : "textfield",
					name : 'acceptorPhone',
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.acceptorPhone')
				}, {
					xtype : "textfield",
					name : 'identityCardNo',
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.identityCardNo')
				}, {
					xtype : "textfield",
					name : 'alipayAccount',
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.alipayAccount')
				}, {
					xtype : "textfield",
					name : 'creditCardNo',
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.creditCardNo')
				}, {
					xtype : "textfield",
					name : 'bankName',
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.bankName')
				}, {
					xtype : "textfield",
					name : 'subbranchName',
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.subbranchName')
				}];
		me.callParent([cfg]);
	}
});

//增加窗体
Ext.define('Miser.view.discount.privilegeStateQueryAddWindow', {
			extend : 'Ext.window.Window',
			title : '新增',// '新增词条',
			closable : true,
			parent : null, // 父元素
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
			privilegeStateQueryEntity : null,
			listeners : {
				beforehide : function(me) { // 隐藏WINDOW的时候清除数据
					me.getStateQueryAddForm().getForm().reset(); // 表格重置
				},
				beforeshow : function(me) { // 显示WINDOW的时候清除数据
					var fielsds = me.getStateQueryAddForm().getForm()
							.getFields();
					if (!Ext.isEmpty(fielsds)) {
						fielsds.each(function(item, index, length) {
									item.clearInvalid();
									item.unsetActiveError();
								});
					}
					fielsds = null;
				}
			},
			StateQueryAddForm : null,
			getStateQueryAddForm : function() {
				if (Ext.isEmpty(this.StateQueryAddForm)) {
					this.StateQueryAddForm = Ext
							.create('Miser.view.discount.StateQueryAddForm');
				}
				return this.StateQueryAddForm;
			},
			submitStateQueryAddForm : function() {
				var me = this;
				if (me.getStateQueryAddForm().getForm().isValid()) { // 校验form是否通过校验
					var StateQueryEntity = new Miser.model.couponAcceptorEntity();
					me.getStateQueryAddForm().getForm()
							.updateRecord(StateQueryEntity); // 将FORM中数据设置到MODEL里面
					var curData = StateQueryEntity.data;
					var params = {
						'couponAcceptorVo' : {
							'couponAcceptorEntity' : curData
						}
					}
					var successFun = function(json) {
						var message = json.message;
						miser.showInfoMsg(message); // 提示修改成功
						me.close();
						me.parent.getStore().load(); // 成功之后重新查询刷新结果集
					};
					var failureFun = function(json) {
						if (Ext.isEmpty(json)) {
							miser.showErrorMes(discount.privilegeStateQuery
									.i18n('miser.discount.timeout')); // 请求超时
						} else {
							var message = json.message;
							miser.showErrorMes(message); // 提示失败原因
						}
					};
					miser.requestJsonAjax(
							'privilegeStateQuery!addCouponAcceptor.action',
							params, successFun, failureFun); // 发送AJAX请求
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.fbar = [{
					text : discount.privilegeStateQuery
							.i18n('miser.common.save'),// '保存',
					// 保存
					/* margin : '0 0 0 55', */
					handler : function() {
						me.submitStateQueryAddForm();
					}
				}, {
					text : discount.privilegeStateQuery
							.i18n('miser.common.cancel'),// '取消',
					// 取消
					handler : function() {
						me.close();
					}
				}];
				me.items = [me.getStateQueryAddForm()];
				me.callParent([cfg]);
			}
		});

Ext.define('Miser.view.discount.privilegeStateQueryUpdateOneWindow', {
			extend : 'Ext.window.Window',
			title : '修改',
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
			privilegeStateQueryEntity : null,
			listeners : {
				beforehide : function(me) { // 隐藏WINDOW的时候清除数据
					me.getStateQueryAddForm().getForm().reset(); // 表格重置
				},
				beforeshow : function(me) { // 显示WINDOW的时候清除数据
					var fielsds = me.getStateQueryAddForm().getForm()
							.getFields();
					if (!Ext.isEmpty(fielsds)) {
						fielsds.each(function(item, index, length) {
									item.clearInvalid();
									item.unsetActiveError();
								});
					}
					fielsds = null;
				}
			},
			StateQueryAddForm : null,
			getStateQueryAddForm : function() {
				if (Ext.isEmpty(this.StateQueryAddForm)) {
					this.StateQueryAddForm = Ext
							.create('Miser.view.discount.StateQueryAddForm');
				}
				return this.StateQueryAddForm;
			},
			submitStateQueryAddForm : function() {
				var me = this;
				if (me.getStateQueryAddForm().getForm().isValid()) { // 校验form是否通过校验
					var StateQueryEntity = new Miser.model.couponAcceptorEntity();
					me.getStateQueryAddForm().getForm()
							.updateRecord(StateQueryEntity); // 将FORM中数据设置到MODEL里面
					var curData = StateQueryEntity.data;
					var params = {
						'couponAcceptorVo' : {
							'couponAcceptorEntity' : curData
						}
					}
					var successFun = function(json) {
						var message = json.message;
						miser.showInfoMsg(message); // 提示修改成功
						me.close();
						me.parent.getStore().load(); // 成功之后重新查询刷新结果集
					};
					var failureFun = function(json) {
						if (Ext.isEmpty(json)) {
							miser.showErrorMes(discount.privilegeStateQuery
									.i18n('miser.discount.timeout')); // 请求超时
						} else {
							var message = json.message;
							miser.showErrorMes(message); // 提示失败原因
						}
					};
					miser.requestJsonAjax(
							'privilegeStateQuery!updateCouponAcceptor.action',
							params, successFun, failureFun); // 发送AJAX请求
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.fbar = [{
					text : discount.privilegeStateQuery
							.i18n('miser.common.save'),// '保存',
					// 保存
					/* margin : '0 0 0 55', */
					handler : function() {
						me.submitStateQueryAddForm();
					}
				}, {
					text : discount.privilegeStateQuery
							.i18n('miser.common.cancel'),// '取消',
					// 取消
					handler : function() {
						me.close();
					}
				}];
				me.items = [me.getStateQueryAddForm()];
				me.callParent([cfg]);
			}
		});
Ext.define('Miser.view.discount.StateQueryForm', {
	extend : 'Ext.form.Panel',
	header : false,
	frame : true,
	id : 'StateQueryFormId',
	collapsible : true,
	width : 600,
	defaults : {
		labelWidth : 100
	},
	layout : {
		type : 'table',
		columns : 2
	},
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
					name : 'htid',
					xtype : 'hiddenfield'
				}, {
					name : 'tpdId',
					xtype : 'hiddenfield'
				}, {
					name : 'tpcId',
					xtype : 'hiddenfield'
				}, {
					name : 'seaDivision',
					readOnly : true,
					width : 200,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.seaDivision'),
					xtype : 'textfield'
				}, {
					name : 'seaBigregion',
					readOnly : true,
					width : 200,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.seaBigregion'),
					xtype : 'textfield'
				}, {
					name : 'seaRoadarea',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.seaRoadarea'),
					xtype : 'textfield'
				}, {
					name : 'seaSalesdepartment',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.seaSalesdepartment'),
					xtype : 'textfield'
				}, {
					name : 'customerCode',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.customerCode'),
					xtype : 'textfield'
				}, {
					name : 'customerName',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.customerName'),
					xtype : 'textfield'
				}, {
					name : 'contactName',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.contactName'),
					xtype : 'textfield'
				}, {

					name : 'contactPhone',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.contactPhone'),
					xtype : 'textfield'
				}, {
					name : 'couponScale',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.couponScale'),
					xtype : 'textfield'
				}, {
					name : 'couponAmount',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.couponAmount'),
					xtype : 'textfield'
				}, {
					name : 'recordMonth',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.recordMonth'),
					xtype : 'datetimefield',
					format : 'Y-m'
				}, {
					name : 'currentMonthAmount',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.currentMonthAmount'),
					xtype : 'textfield'
				}, {
					xtype : "textfield",
					name : 'acceptorName',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.acceptorName')
				}, {
					xtype : "textfield",
					name : 'relationshipWithCustomer',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.relationshipWithCustomer')
				}, {
					xtype : "textfield",
					name : 'acceptorPhone',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.acceptorPhone')
				}, {
					xtype : "textfield",
					name : 'identityCardNo',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.identityCardNo')
				}, {
					xtype : "textfield",
					name : 'alipayAccount',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.alipayAccount')
				}, {
					xtype : "textfield",
					name : 'creditCardNo',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.creditCardNo')
				}, {
					xtype : "textfield",
					name : 'bankName',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.bankName')
				}, {
					xtype : "textfield",
					name : 'subbranchName',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.subbranchName')
				}, {
					xtype : 'datetimefield',
					format : 'Y-m-d H:i:s',
					name : 'createDate',
					readOnly : true,
					fieldLabel : '申请返券时间'
				}, {
					xtype : "textfield",
					name : 'couponState',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.couponState')
				}, {
					xtype : "textfield",
					name : 'checkRemark',
					hidden : true,
					readOnly : true,
					fieldLabel : '审核原因'
				}, {
					xtype : 'datetimefield',
					format : 'Y-m-d H:i:s',
					name : 'checkTime',
					hidden : true,
					readOnly : true,
					fieldLabel : '审核时间'
				}, {
					xtype : "textfield",
					name : 'couponRemark',
					hidden : true,
					readOnly : true,
					fieldLabel : '返券失败原因'
				}, {
					xtype : 'datetimefield',
					format : 'Y-m-d H:i:s',
					name : 'couponTime',
					hidden : true,
					readOnly : true,
					fieldLabel : '失败操作时间'
				}, {
					colspan : 2,
					width : 600,
					xtype : 'textarea',
					name : 'stateRecord',
					readOnly : true,
					fieldLabel : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.stateRecord')
				}];
		me.callParent([cfg]);
	}
});
Ext.define('Miser.view.discount.privilegeStateQueryWindow', {
			extend : 'Ext.window.Window',
			title : '查看',
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
			privilegeStateQueryEntity : null,
			listeners : {
				beforehide : function(me) { // 隐藏WINDOW的时候清除数据
					me.getStateQueryForm().getForm().reset(); // 表格重置
				},
				beforeshow : function(me) { // 显示WINDOW的时候清除数据
					var fielsds = me.getStateQueryForm().getForm().getFields();
					if (!Ext.isEmpty(fielsds)) {
						fielsds.each(function(item, index, length) {
									item.clearInvalid();
									item.unsetActiveError();
								});
					}
					fielsds = null;
				}
			},
			StateQueryForm : null,
			getStateQueryForm : function() {
				if (Ext.isEmpty(this.StateQueryForm)) {
					this.StateQueryForm = Ext
							.create('Miser.view.discount.StateQueryForm');
				}
				return this.StateQueryForm;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.fbar = [{
					text : discount.privilegeStateQuery
							.i18n('miser.common.cancel'),// '取消',
					// 取消
					handler : function() {
						me.close();
					}
				}];
				me.items = [me.getStateQueryForm()];
				me.callParent([cfg]);
			}
		});

Ext.define('Miser.view.privilegeStateQuery.Grid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	autoScroll : true,
	height : miser.getBrowserHeight() - 120,
	stripeRows : true,
	selType : "rowmodel",
	emptyText : '查询结果为空',
	columnLines : true,
	viewConfig : {
		enableTextSelection : true
	},
	privilegeStateQueryUpdateWindow : null,
	getprivilegeStateQueryUpdateWindow : function() {
		if (Ext.isEmpty(this.privilegeStateQueryUpdateWindow)) {
			this.privilegeStateQueryUpdateWindow = Ext
					.create('Miser.view.discount.privilegeStateQueryUpdateWindow');
			this.privilegeStateQueryUpdateWindow.parent = this; // 父元素
		}
		return this.privilegeStateQueryUpdateWindow;
	},
	updateprivilegeStateQuery : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断选中了一条
			miser.showWoringMessage('选择一条数据'); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		var id = selections[0].get('htid');
		var updateWindow = me.getprivilegeStateQueryUpdateWindow(); // 获得修改窗口
		loadMore(id, updateWindow);
	},
	downloadPrivilegeStateQuery : function() {
		if(lastQueryCondition == null) {
			Ext.MessageBox.show({title: discount.privilegeStateQuery.i18n('discount.privilegeStateQuery.tips'), 
				msg: discount.privilegeStateQuery.i18n('discount.privilegeStateQuery.needQueryFirst'), 
				buttons: Ext.MessageBox.OK, icon: Ext.MessageBox.ERROR});
			return;
		}
		Ext.Msg.wait(discount.privilegeStateQuery.i18n('discount.privilegeStateQuery.handling'), 
				discount.privilegeStateQuery.i18n('discount.privilegeStateQuery.tips'));
		var params=null;
		Ext.Ajax.request({
			url : '../discount/privilegeStateQuery!doExport.action',  
			method : 'post',
			params : lastQueryCondition,  
			success : function(response, options) {  
				//隐藏进度条
				Ext.Msg.hide();
				var responseArray = Ext.util.JSON.decode(response.responseText);
				if(!responseArray.result) {
					Ext.MessageBox.show({title: discount.privilegeStateQuery.i18n('discount.privilegeStateQuery.tips'), 
						msg: discount.privilegeStateQuery.i18n('discount.privilegeStateQuery.downloadFail'), 
						buttons: Ext.MessageBox.OK, icon: Ext.MessageBox.ERROR});
					return;
				}
				miser.showInfoMsg(discount.privilegeStateQuery.i18n('discount.privilegeStateQuery.successTips', [responseArray.count]));
			miser.requestExportAjax(responseArray.filePath);
		   },
		   failure : function() { 
		        Ext.Msg.hide();  
		        Ext.MessageBox.show({title: discount.privilegeStateQuery.i18n('discount.privilegeStateQuery.tips'), 
		        	msg: discount.privilegeStateQuery.i18n('discount.privilegeStateQuery.fail'), 
		        	buttons: Ext.MessageBox.OK, icon: Ext.MessageBox.ERROR});  
		   }
		});
	},
	addcheck : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断选中了一条
			miser.showWoringMessage('请选择一条进行申请操作操作'); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		var contractCode = selections[0].get('contractCode');
		var recordMonth = selections[0].get('recordMonth');
		var params = {
			'couponAcceptorVo' : {
				'couponAcceptorEntity' : {
					'contractCode' : contractCode,
					'recordMonth' : recordMonth
				}
			}
		};
		var successFun = function(json) {
			var updateWindow = me.getStateQueryAddwindow(); // 获得修改窗口
			updateWindow.getStateQueryAddForm().getForm()
					.loadRecord(selections[0]);
			if(json.couponAcceptorVo.couponAcceptorEntity != null) {
				updateWindow
				.getStateQueryAddForm()
				.getForm()
				.loadRecord(new Miser.model.couponAcceptorEntity(json.couponAcceptorVo.couponAcceptorEntity));
			}
			updateWindow.show();
		};
		var failureFun = function(json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes('请求超时'); // 请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax(
				'privilegeStateQuery!queryCouponAcceptorById.action', params,
				successFun, failureFun);
	},
	updatecheck : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断选中了一条
			miser.showWoringMessage('请选择一条进行申请操作操作'); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}

		var contractCode = selections[0].get('contractCode');
		var recordMonth = selections[0].get('recordMonth');
		var params = {
			'couponAcceptorVo' : {
				'couponAcceptorEntity' : {
					'contractCode' : contractCode,
					'recordMonth' : recordMonth
				}
			}
		};
		var successFun = function(json) {
			var updateWindow = me.getStateQueryUpdatewindow(); // 获得修改窗口
			updateWindow.getStateQueryAddForm().getForm()
					.loadRecord(selections[0]);
			if(json.couponAcceptorVo.couponAcceptorEntity != null) {
				updateWindow
				.getStateQueryAddForm()
				.getForm()
				.loadRecord(new Miser.model.couponAcceptorEntity(json.couponAcceptorVo.couponAcceptorEntity));
			}
			updateWindow.show();

		};
		var failureFun = function(json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes('请求超时'); // 请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax(
				'privilegeStateQuery!queryCouponAcceptorById.action', params,
				successFun, failureFun);
	},
	querycheck : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断选中了一条
			miser.showWoringMessage('请选择一条进行申请操作操作'); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}

		var contractCode = selections[0].get('contractCode');
		var recordMonth = selections[0].get('recordMonth');
		var params = {
			'couponAcceptorVo' : {
				'couponAcceptorEntity' : {
					'contractCode' : contractCode,
					'recordMonth' : recordMonth
				}
			}
		};
		var successFun = function(json) {
			var updateWindow1 = me.getStateQuerywindow(); // 获得修改窗口
			updateWindow1.getStateQueryForm().getForm()
					.loadRecord(selections[0]);
			if(json.couponAcceptorVo.couponAcceptorEntity != null) {
				updateWindow1
				.getStateQueryForm()
				.getForm()
				.loadRecord(new Miser.model.couponAcceptorEntity(json.couponAcceptorVo.couponAcceptorEntity));
				if(!Ext.isEmpty(json.couponAcceptorVo.couponAcceptorEntity.conponStateList)) {
					var length = json.couponAcceptorVo.couponAcceptorEntity.conponStateList.length;
					var stateRecord = "";
					for(var i=0; i<length; i++) {
						var conponStateData = json.couponAcceptorVo.couponAcceptorEntity.conponStateList[i];
						var content = conponStateData.executeStateDes + " 操作人: " 
							+ conponStateData.executeUser + " 操作时间: " + new Date(conponStateData.executeTime).format("yyyy/MM/dd hh:mm:ss");	
						stateRecord += content + " \n";
					}
					updateWindow1.getStateQueryForm().getForm().findField("stateRecord").setValue(stateRecord);
				}
			}
			var couponState = updateWindow1.getStateQueryForm().getForm()
					.findField('couponState');
			var value = '';
			updateWindow1.getStateQueryForm().getForm()
					.findField('checkRemark').setHidden(true);
			updateWindow1.getStateQueryForm().getForm()
					.findField('couponRemark').setHidden(true);
			updateWindow1.getStateQueryForm().getForm().findField('couponTime')
					.setHidden(true);
			updateWindow1.getStateQueryForm().getForm().findField('checkTime')
					.setHidden(true);
			var fieldvalue = Ext.String.trim(couponState.getValue());
			if ('PENDING_CHECK' == fieldvalue) {
				value = '待审核';
			} else if ('CHECK_SUCCESS' == fieldvalue) {
				value = '审核完成';
				updateWindow1.getStateQueryForm().getForm()
						.findField('checkTime').setHidden(false);
				updateWindow1.getStateQueryForm().getForm()
						.findField('checkRemark').setHidden(false);
			} else if ('CHECK_REJECT' == fieldvalue) {
				value = '审核失败';
				updateWindow1.getStateQueryForm().getForm()
						.findField('checkTime').setHidden(false);
				updateWindow1.getStateQueryForm().getForm()
						.findField('checkRemark').setHidden(false);
			} else if ('COUPON_FAIL' == fieldvalue) {
				value = '返券失败';
				updateWindow1.getStateQueryForm().getForm()
						.findField('couponRemark').setHidden(false);
				updateWindow1.getStateQueryForm().getForm()
						.findField('couponTime').setHidden(false);
			}
			couponState.setValue(value);
			updateWindow1.show();
		};
		var failureFun = function(json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes('请求超时'); // 请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax(
				'privilegeStateQuery!queryCouponAcceptorById.action', params,
				successFun, failureFun);
	},
	stateQueryUpdateWindow : null,
	getStateQueryUpdatewindow : function() {
		if (Ext.isEmpty(this.stateQueryUpdateWindow)) {
			this.stateQueryUpdateWindow = Ext
					.create('Miser.view.discount.privilegeStateQueryUpdateOneWindow');
			this.stateQueryUpdateWindow.parent = this; // 父元素
		}
		return this.stateQueryUpdateWindow;
	},
	stateQueryWindow : null,
	getStateQuerywindow : function() {
		if (Ext.isEmpty(this.stateQueryWindow)) {
			this.stateQueryWindow = Ext
					.create('Miser.view.discount.privilegeStateQueryWindow');
		}
		return this.stateQueryWindow;
	},
	stateQueryAddWindow : null,
	getStateQueryAddwindow : function() {
		if (Ext.isEmpty(this.stateQueryAddWindow)) {
			this.stateQueryAddWindow = Ext
					.create('Miser.view.discount.privilegeStateQueryAddWindow');
			this.stateQueryAddWindow.parent = this; // 父元素
		}
		return this.stateQueryAddWindow;
	},
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Ext.toolbar.Paging', {
						store : this.store,
						pageSize : 20
					});
		}
		return this.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
					text : '序号',
					width : 60,
					xtype : 'rownumberer',
					align : 'center'
				}, {
					dataIndex : 'id',
					hidden : true
				}, {
					dataIndex : 'tpdId',
					hidden : true
				}, {
					dataIndex : 'tpcId',
					hidden : true
				}, {
					dataIndex : 'contractCode',
					hidden : true
				}, {
					dataIndex : 'seaSalesdepartment',
					hidden : true
				}, {
					dataIndex : 'seaRoadarea',
					hidden : true
				}, {
					dataIndex : 'seaBigregion',
					hidden : true
				}, {
					dataIndex : 'seaDivision',
					hidden : true
				}, {
					dataIndex : 'customerCode',
					width : 120,
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.customerCode'),
					align : 'center'
				}, {
					dataIndex : 'customerName',
					width : 120,
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.customerName'),
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.privilegeStartTime'),
					dataIndex : 'privilegeStartTime',
					width : 120,
					xtype : 'datecolumn',
					format : 'Y-m-d H:i:s',
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.privilegeEndTime'),
					dataIndex : 'privilegeEndTime',
					width : 120,
					xtype : 'datecolumn',
					format : 'Y-m-d H:i:s',
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.recordMonth'),
					dataIndex : 'recordMonth',
					width : 120,
					xtype : 'datecolumn',
					format : 'Y-m',
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.lastLastMonthAmount'),
					dataIndex : 'lastLastMonthAmount',
					xtype : 'numbercolumn',
					width : 120,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.lastMonthAmount'),
					dataIndex : 'lastMonthAmount',
					xtype : 'numbercolumn',
					width : 120,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.lastTwoMonthsAverageAmount'),
					dataIndex : 'lastTwoMonthsAverageAmount',
					xtype : 'numbercolumn',
					width : 120,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.currentMonthAmount'),
					dataIndex : 'currentMonthAmount',
					xtype : 'numbercolumn',
					width : 120,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.currentDuDiscount'),
					dataIndex : 'currentDuDiscount',
					xtype : 'numbercolumn',
					width : 120,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.currentDdDiscount'),
					dataIndex : 'currentDdDiscount',
					xtype : 'numbercolumn',
					width : 120,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.hasCoupon'),
					dataIndex : 'hasCoupon',
					width : 120,
					renderer : function(value) {
						return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(discount.privilegeStateQuery.wether), value);
					},
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.couponScale'),
					dataIndex : 'couponScale',
					xtype : 'numbercolumn',
					width : 120,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.couponAmount'),
					dataIndex : 'couponAmount',
					xtype : 'numbercolumn',
					width : 120,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.shouldPayAmountSum'),
					dataIndex : 'shouldPayAmountSum',
					xtype : 'numbercolumn',
					width : 120,
					align : 'center'
				}, {
					text : discount.privilegeStateQuery
							.i18n('discount.privilegeStateQuery.couponState'),
					dataIndex : 'couponState',
					width : 120,
					renderer : function(value, record) {
						return couponStateRender(value, record);
					},
					align : 'center'
				}], me.store = Ext.create(
				'Miser.store.privilegeStateQueryStore', {
					autoLoad : false
				});
		me.tbar = [
		{
			text : discount.privilegeStateQuery
					.i18n('discount.privilegeStateQuery.apply'),
			xtype : 'addbutton',
			id : 'apply_privilegeStateQuery',
			width : 100,
			handler : function() {
				me.addcheck();
			}
		}, {
			text : discount.privilegeStateQuery
					.i18n('discount.privilegeStateQuery.updateticket'),
			xtype : 'updatebutton',
			id : 'updateticket_privilegeStateQuery',
			width : 130,
			handler : function() {
				me.updatecheck();
			}
		}, 
		{
			text : discount.privilegeStateQuery
					.i18n('discount.privilegeStateQuery.queryapply'),
			xtype : 'searchbutton',
			id : 'queryapply_privilegeStateQuery',
			width : 130,
			handler : function() {
				me.querycheck();
			}
		}, {
			text : discount.privilegeStateQuery
					.i18n('discount.privilegeStateQuery.queryDetail'),
			xtype : 'searchbutton',
			width : 130,
			handler : function() {
				me.updateprivilegeStateQuery();
			}
		}, {
			text : discount.privilegeStateQuery
			.i18n('discount.privilegeStateQuery.export'),
			xtype : 'downloadbutton',
			width : 130,
			handler : function() {
				me.downloadPrivilegeStateQuery();
			}
		}];
		me.bbar = me.getPagingToolbar();
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
					listeners : {
						selectionchange : function(sm, selections) {
							try {
								var tpcId = selections[0].get('tpcId');
								var tpdId = selections[0].get('tpdId');
								var hasCoupon = selections[0].get('hasCoupon');
								var shouldPayAmountSumValue = selections[0]
										.get('shouldPayAmountSum');
								var couponState = selections[0]
										.get('couponState');
								couponState = Ext.String.trim(couponState);
								Ext.getCmp('apply_privilegeStateQuery')
										.setDisabled(true);
								Ext.getCmp('queryapply_privilegeStateQuery')
										.setDisabled(true);
								Ext.getCmp('updateticket_privilegeStateQuery')
										.setDisabled(true);
								if (hasCoupon != "Y") {
									return;
								}
								if (Ext.isEmpty(couponState)
										&& hasCoupon == "Y" && (Ext.isEmpty(shouldPayAmountSumValue) 
												|| parseFloat(shouldPayAmountSumValue) == 0.0)) {
									Ext.getCmp('apply_privilegeStateQuery')
											.setDisabled(false);
								} else if ('PENDING_CHECK' == couponState) {
									Ext
											.getCmp('queryapply_privilegeStateQuery')
											.setDisabled(false);
									Ext
											.getCmp('updateticket_privilegeStateQuery')
											.setDisabled(false);
								} else if ('CHECK_SUCCESS' == couponState) {
									Ext
											.getCmp('queryapply_privilegeStateQuery')
											.setDisabled(false);
								} else if ('CHECK_REJECT' == couponState) {
									Ext.getCmp('apply_privilegeStateQuery')
											.setDisabled(false);
									Ext
											.getCmp('queryapply_privilegeStateQuery')
											.setDisabled(false);
								} else if ('COUPON_FAIL' == couponState) {
									Ext.getCmp('apply_privilegeStateQuery')
											.setDisabled(false);
									Ext
											.getCmp('queryapply_privilegeStateQuery')
											.setDisabled(false);
								}
							} catch (e) {
							}
						}
					}
				}), me.callParent([cfg]);
	}
});
function loadMore(id, updateWindow) {
	updateWindow.getprivilegeStateQueryDetailUpdateGrid().store.removeAll();
			//me.privilegeStateQueryUpdateForm=null;
	var params = {
		'privilegeContractVo' : {
			'privilegeContractEntity' : {
				'id' : id
			}
		}
	};
	var successFun = function(json) {
		updateWindow.privilegeStateQueryEntity = json.privilegeContractVo.privilegeContractEntity;
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
	// 主表信息
	miser.requestJsonAjax(
			'privilegeStateQuery!queryPrivilegeContractById.action', params,
			successFun, failureFun);
	// 子表信息
	var paramsOne = {
		'privilegeContractDetailVo' : {
			'privilegeContractDetailEntity' : {
				'customerContractId' : id
			}
		},
		'page' : 1,
		'limit' : 20,
		'start' : 0
	};
	var successFunOne = function(json) {
		// 修改加载明细列表
		var SectionSubList = json.privilegeContractDetailVo.privilegeContractDetailList;
		for (var i = 0; i < SectionSubList.length; i++) {
			updateWindow.getprivilegeStateQueryDetailUpdateGrid().store.add(SectionSubList[i]);
		}
	};
	// 主表信息
	miser.requestJsonAjax('privilegeStateQuery!queryListByParamSub.action',
			paramsOne, successFunOne, failureFun);
}

/**
 * 越发越惠状态查询列表store
 */
Ext.define('Miser.store.privilegeStateQueryStore', {
			extend : 'Ext.data.Store',
			model : 'Miser.model.privilegeStateQueryEntity',
			pageSize : 20,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : 'privilegeStateQuery!queryListByParam.action',
				reader : {
					type : 'json',
					rootProperty : 'privilegeStateQueryVoList',
					totalProperty : 'totalCount' // 总个数
				}
			},
			listeners : {
				beforeload : function(store, operation, eOpts) {
					var queryForm = Ext.getCmp('queryForm');
					if (queryForm != null) {
						var params = {
							'privilegeStateQueryVo.seaRoadarea' : queryForm
									.getForm().findField('seaRoadarea')
									.getValue(),
							'privilegeStateQueryVo.seaBigregion' : queryForm
									.getForm().findField('seaBigregion')
									.getValue(),
							'privilegeStateQueryVo.seaDivision' : queryForm
									.getForm().findField('seaDivision')
									.getValue(),
							'privilegeStateQueryVo.seaSalesdepartment' : queryForm
									.getForm().findField('seaSalesdepartment')
									.getValue(),
							'privilegeStateQueryVo.customerCode' : queryForm
									.getForm().findField('customerCode')
									.getValue(),
							'privilegeStateQueryVo.customerName' : queryForm
									.getForm().findField('customerName')
									.getValue(),
							'privilegeStateQueryVo.hasCoupon' : queryForm
									.getForm().findField('hasCoupon')
									.getValue(),
							'privilegeStateQueryVo.recordMonth' : queryForm
									.getForm()
									.findField('contractStartAndEndTime')
									.getValue(),
							'privilegeStateQueryVo.couponState':queryForm.getForm().findField('couponState').getValue()
						};
						lastQueryCondition = params;
						Ext.apply(store.proxy.extraParams, params);
					}
				}

			}

		});
var CURRENT_USER_ORG = null;
function setORG(queryForm) {
	if (CURRENT_USER_ORG == null) {
		miser.requestJsonAjax(
				'../bizbase/orgAction!queryCurrentUserORG.action', {},
				function(data) {
					if (!Ext.isEmpty(data) && !Ext.isEmpty(data['orgVo'])) {
						var orgEntity = data['orgVo']['orgEntity'];
						if (!Ext.isEmpty(orgEntity)) {
							CURRENT_USER_ORG = {
								'seaDivisionCode' : orgEntity['divisionCode'],
								'seaDivisionName' : orgEntity['divisionName'],
								'seaBigregionCode' : orgEntity['bigRegionCode'],
								'seaBigregionName' : orgEntity['bigRegionName'],
								'seaRoadareaCode' : orgEntity['roadAreaCode'],
								'seaRoadareaName' : orgEntity['roadAreaName'],
								'seaSalesdepartmentCode' : orgEntity['salesDepartmentCode'],
								'seaSalesdepartmentName' : orgEntity['salesDepartmentName']
							};
							setQueryFormORG(queryForm, CURRENT_USER_ORG);
						}
					}
				}, function(data) {
					if (Ext.isEmpty(data)) {
						miser.showErrorMes(discount.privilegeContract
								.i18n('miser.discount.timeout')); // 请求超时
					} else {
						var message = data.message;
						miser.showErrorMes(message); // 提示失败原因
					}
				});
	} else {
		setQueryFormORG(queryForm, CURRENT_USER_ORG);
	}
}
/**
 * 设置查询FORM的组织数据
 */
var ORG_FIELDS = ['seaDivision', 'seaBigregion', 'seaRoadarea',
		'seaSalesdepartment'];
function setQueryFormORG(queryForm, ORG_data) {
	var form = queryForm.getForm();

	var length = ORG_FIELDS.length;
	for (var i = 0; i < length; i++) {
		var codeValue = ORG_data[ORG_FIELDS[i] + 'Code'];
		var nameValue = ORG_data[ORG_FIELDS[i] + 'Name'];
		if (!Ext.isEmpty(codeValue) && !Ext.isEmpty(nameValue)) {
			var field = form.findField(ORG_FIELDS[i]);
			var record = Ext.data.Record({
						code : codeValue,
						name : nameValue
					});
			field.getStore().add(record);
			field.setValue(codeValue);
			field.disable();
		}
	}
}
Ext.onReady(function() {
	/**
	 * 数据字典页面
	 */
	Ext.QuickTips.init();
	var queryForm = Ext.create('Miser.view.privilegeStateQuery.QueryForm'); // 查询FORM
	setORG(queryForm);
	var privilegeStateQueryGrid = Ext
			.create('Miser.view.privilegeStateQuery.Grid');
	var mainPanel = Ext.create('Ext.panel.Panel', {
				renderTo : Ext.getBody(),
				getQueryForm : function() {
					return queryForm;
				},
				getprivilegeStateQueryGrid : function() {
					return privilegeStateQueryGrid;
				},
				items : [queryForm, privilegeStateQueryGrid]
			});
	//设置窗口大小改变时的响应
	window.onresize = function() {

		//控制整个 panel 的宽度
		mainPanel.setWidth(document.documentElement.clientWidth);
		var items = queryForm.items;
		var targetObj = items.items[0].items.items[0];
		targetObj.setWidth(document.documentElement.clientWidth);
		document.getElementById(items.items[1].items.items[0].getEl().el
				.parent().id).style.width = (document.documentElement.clientWidth)
				+ 'px';
		//控制整个 查询表单 的宽度
		queryForm.setWidth(document.documentElement.clientWidth);

		//设置查询结果的宽度
		privilegeStateQueryGrid.setWidth(document.documentElement.clientWidth
				- 20);
	};
});