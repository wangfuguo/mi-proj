discount.discountPrivilege.state = 'PRICE_SATUS';
discount.discountPrivilege.active = 'IS_ACTIVE';

/**
 * 越发越惠列表model
 */
Ext.define('Miser.model.DiscountPrivilegeEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'startAmount',
		// 分段项目编号
		type : 'number'
	}, {
		name : 'endAmount',
		// 分段项目编号
		type : 'number'
	},{
        name: 'ddMinFreightDiscount',
        // 运输类型编号
        type: 'number'
    },{
        name: 'duMinFreightDiscount',
        // 运输类型
        type: 'number'
    }, {
		name : 'hasCoupon',
		// 段止
		type : 'string'
	}, {
		name : 'maxCouponScale',
		// 计算方式
		type : 'number'
	},  {
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
	},{
		name : 'createUser',
		// 最后修改人
		type : 'string'
	}, {
		name : 'createDate',
		// 最后修改时间
		type : 'date',
		dateFormat : 'time'
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
 * 查询表单
 */
Ext.define("Miser.view.discount.QueryForm", {
	id: "queryForm",
	extend: "Ext.form.Panel",
	title: discount.discountPrivilege.i18n('miser.common.queryCondition'),
	collapsible: true,
	frame: true,
	region : 'north',
	width: document.documentElement.clientWidth - 20,
	defaults: {
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
	layout: {
		type: 'table',
		columns: 4
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			layout: 'column',
			border: false,
			items: [{
				name : 'hasCoupon',
				fieldLabel : discount.discountPrivilege
						.i18n('discount.discountPrivilege.hasCoupon'),
				xtype : 'dataDictionarySelector',
				anyRecords:all,
				termsCode : discount.discountPrivilege.active
			}, {
				name : 'state',
				fieldLabel : discount.discountPrivilege
						.i18n('discount.discountPrivilege.state'),
				xtype : 'dataDictionarySelector',
				anyRecords:all,
				termsCode : discount.discountPrivilege.state
			}]
		}];
		me.buttons = [{
			text : discount.discountPrivilege.i18n('miser.common.query'),
			handler : function() {
				if (me.getForm().isValid()) {
					me.up().getDiscountPrivilegeGrid().getPagingToolbar().moveFirst();
				}
			}
		}, {
			text : discount.discountPrivilege.i18n('miser.common.reset'),
			handler : function() {
				me.getForm().reset();
			}
		}];
		me.callParent([cfg]);
	}
});

/**
 * 增加表单
 */
Ext
		.define(
				'Miser.view.discount.DiscountPrivilegeAddForm',
				{
					extend : 'Ext.form.Panel',
					 header: false,
					    frame: true,
					    collapsible: true,
					    width: 600,
					    defaults: {
					        labelWidth: 150,
					        margin: '8 10 5 10'
					    },
					    layout: {
					        type: 'table',
					        columns: 3
					    },
					    defaultType: 'textfield',
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ {
							name : 'id',
							fieldLabel : 'ID',
							xtype : 'hiddenfield'
						},
						{
							colspan: 2,
							name : 'startAmount',
							width: 325,
							allowBlank : false,
							fieldLabel : discount.discountPrivilege.i18n('discount.discountPrivilege.startAndEndAmount'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype: 'numberfield',
							minValue: 0.01,
							maxValue: 99999999.00,
							step:1000
						},
						{
							xtype : "numberfield",
							step:1000,
							minValue: 0.01,
							maxValue: 99999999.00,
							name : 'endAmount',
							width: 200,
							allowBlank : false
						}, {
							colspan: 3,
							name : 'ddMinFreightDiscount',
							allowBlank : false,
							fieldLabel : discount.discountPrivilege
									.i18n('discount.discountPrivilege.ddMinFreightDiscount'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype : 'numberfield',
							minValue: 0.01,
							maxValue: 999.99,
							step:0.1
						}, {
							colspan: 3,
							xtype : "numberfield",
							minValue: 0.01,
							maxValue: 999.99,
							step:0.1,
							name : 'duMinFreightDiscount',
							allowBlank : true,
							fieldLabel : discount.discountPrivilege
									.i18n('discount.discountPrivilege.duMinFreightDiscount'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, {
							colspan: 3,
							allowBlank : false,
							fieldLabel : discount.discountPrivilege
									.i18n('discount.discountPrivilege.hasCoupon'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype      : 'fieldcontainer',
				            defaultType: 'radiofield',
				            layout: 'hbox',
				            items: [
				                {
				                    boxLabel  : '是',
				                    name      : 'hasCoupon',
				                    checked:true,
				                    inputValue: 'Y',
									listeners : {
							        change : function(field,newValue,oldValue){
							        	var maxCouponScaleAdd=Ext.getCmp("maxCouponScaleAdd");
							        	if(newValue){
							        		maxCouponScaleAdd.allowBlank=false;
							        		maxCouponScaleAdd.setReadOnly(false);
							        	}else{
							        		maxCouponScaleAdd.setReadOnly(true);
							        		maxCouponScaleAdd.allowBlank=true;
							        		maxCouponScaleAdd.setValue('');
							        	}
							       }
							}
				                }, {
				                    boxLabel  : '否',
				                    name      : 'hasCoupon',
				                    inputValue: 'N'
				                }
				            ]
							}, {
								colspan: 3,
								xtype : "numberfield",
								step:0.1,
								minValue: 0.01,
								id:'maxCouponScaleAdd',
								name : 'maxCouponScale',
								allowBlank : false,
								fieldLabel : discount.discountPrivilege
										.i18n('discount.discountPrivilege.maxCouponScale'),
								beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
							}, {
								colspan: 3,
								xtype: 'datetimefield',
					            format: 'Y-m-d H:i:s',
//								minThisDate : true,
								name : 'effectiveTime',
								allowBlank : false,
								fieldLabel : discount.discountPrivilege.i18n('discount.discountPrivilege.effectiveTime'),
								beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
							}, {
								colspan: 3,
								width: 540,
								fieldLabel : discount.discountPrivilege
								.i18n('discount.discountPrivilege.remark'), // '备注',
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
				'Miser.view.discount.DiscountPrivilegeUpdateForm',
				{
					extend : 'Ext.form.Panel',
					 header: false,
					    frame: true,
					    collapsible: true,
					    width: 600,
					    defaults: {
					        labelWidth: 150,
					        margin: '8 10 5 10'
					    },
					    layout: {
					        type: 'table',
					        columns: 3
					    },
					    defaultType: 'textfield',
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ {
							name : 'id',
							fieldLabel : 'ID',
							xtype : 'hiddenfield'
						},{
							name : 'state',
							fieldLabel : 'state',
							xtype : 'hiddenfield'
						},
						{
							colspan: 2,
							name : 'startAmount',
							width: 325,
							allowBlank : false,
							fieldLabel : discount.discountPrivilege.i18n('discount.discountPrivilege.startAndEndAmount'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype: 'numberfield',
							minValue: 0,
							readOnly:true,
							step:1000
						},
						{
							xtype : "numberfield",
							step:1000,
							name : 'endAmount',
							minValue: 0.01,
							width: 200,
							readOnly:true,
							allowBlank : false
						}, {
							colspan: 3,
							name : 'ddMinFreightDiscount',
							allowBlank : false,
							minValue: 0.01,
							readOnly:true,
							fieldLabel : discount.discountPrivilege
									.i18n('discount.discountPrivilege.ddMinFreightDiscount'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype : 'numberfield',
							step:0.1
						}, {
							colspan: 3,
							xtype : "numberfield",
							step:0.1,
							minValue: 0.01,
							name : 'duMinFreightDiscount',
							allowBlank : true,
							readOnly:true,
							fieldLabel : discount.discountPrivilege
									.i18n('discount.discountPrivilege.duMinFreightDiscount'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, {
							colspan: 3,
							allowBlank : false,
							fieldLabel : discount.discountPrivilege
									.i18n('discount.discountPrivilege.hasCoupon'),
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype      : 'fieldcontainer',
				            defaultType: 'radiofield',
				            layout: 'hbox',
				            items: [
				                {
				                    boxLabel  : '是',
				                    name      : 'hasCoupon',
				                    checked:true,
				                    inputValue: 'Y',
									listeners : {
							        change : function(field,newValue,oldValue){
							        	var maxCouponScaleUpdate=Ext.getCmp("maxCouponScaleUpdate");
							        	if(newValue){
							        		maxCouponScaleUpdate.setReadOnly(false);
							        		maxCouponScaleUpdate.allowBlank=false;
							        	}else{
							        		maxCouponScaleUpdate.setReadOnly(true);
							        		maxCouponScaleUpdate.allowBlank=true;
							        		maxCouponScaleUpdate.setValue('');
							        	}
							       }
								   }
				                }, {
				                    boxLabel  : '否',
				                    name      : 'hasCoupon',
				                    inputValue: 'N'
				                }
				            ]
							}, {
								colspan: 3,
								xtype : "numberfield",
								step:0.1,
								minValue: 0.01,
								name : 'maxCouponScale',
								id:'maxCouponScaleUpdate',
								allowBlank : false,
								fieldLabel : discount.discountPrivilege
										.i18n('discount.discountPrivilege.maxCouponScale'),
								beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
							}, {
								colspan: 3,
								xtype: 'datetimefield',
					            format: 'Y-m-d H:i:s',
//								minThisDate : true,
								name : 'effectiveTime',
								allowBlank : false,
								fieldLabel : discount.discountPrivilege.i18n('discount.discountPrivilege.effectiveTime'),
								beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
							}, {
								colspan: 3,
								width: 540,
								fieldLabel : discount.discountPrivilege
								.i18n('discount.discountPrivilege.remark'), // '备注',
								xtype : 'textarea',
								name : 'remark',
								xtype : 'textarea'
							} ];
						me.callParent([ cfg ]);
					}
				});
// 增加窗体
Ext.define('Miser.view.discount.DiscountPrivilegeAddWindow', {
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
			me.getDiscountPrivilegeAddForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getDiscountPrivilegeAddForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			me.getDiscountPrivilegeAddForm().getForm().findField("effectiveTime").setValue(new Date(new Date().getTime() + 5*60*1000));
		}
	},
	DiscountPrivilegeAddForm : null,
	getDiscountPrivilegeAddForm : function() {
		if (Ext.isEmpty(this.DiscountPrivilegeAddForm)) {
			this.DiscountPrivilegeAddForm = Ext
					.create('Miser.view.discount.DiscountPrivilegeAddForm');
		}
		return this.DiscountPrivilegeAddForm;
	},
	submitDiscountPrivilegeAddForm : function() {
		var me = this;
		if (me.getDiscountPrivilegeAddForm().getForm().isValid()) { // 校验form是否通过校验
			var DiscountPrivilegeEntity = new Miser.model.DiscountPrivilegeEntity();
			me.getDiscountPrivilegeAddForm().getForm().updateRecord(
					DiscountPrivilegeEntity); // 将FORM中数据设置到MODEL里面
			var curData = DiscountPrivilegeEntity.data;
			var params = {
				'discountPrivilegeVo' : {
					'discountPrivilegeEntity' : curData
				}
			};
			// 比较当前时间
			console.log(params.discountPrivilegeVo.discountPrivilegeEntity.effectiveTime);
			console.log(new Date());
			console.log(params.discountPrivilegeVo.discountPrivilegeEntity.effectiveTime<new Date());
			if(params.discountPrivilegeVo.discountPrivilegeEntity.effectiveTime<new Date()) {
				miser.showQuestionMes('生效时间将修改为当前时间？', function(e) {

					if (e == 'yes') {
						me.getDiscountPrivilegeAddForm().getForm().findField("effectiveTime").setValue(new Date());
						params.discountPrivilegeVo.discountPrivilegeEntity.isAlert='0';
						if(parseFloat(params.discountPrivilegeVo.discountPrivilegeEntity.startAmount)>= parseFloat(params.discountPrivilegeVo.discountPrivilegeEntity.endAmount)){
							miser.showErrorMes('发货金额分段数据有误');
						}else{

							var successFun = function(json) {
								var message = json.message;
								if(!Ext.isEmpty(message)&&message=='miser.base.invalid'){
									miser.showErrorMes('无法修改已作废或已过期数据'); // 请求超时
								}else if(!Ext.isEmpty(message)&&message=='miser.base.isAlert'){
									miser.showQuestionMes('该越发越惠存在相同的待生效数据，是否覆盖？',// 是否删除
											function(e) {
												if (e == 'yes') {
													params.discountPrivilegeVo.discountPrivilegeEntity.isAlert='1';
													miser.requestJsonAjax('discountPrivilege!addDiscountPrivilege.action',
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
									miser.showErrorMes(discount.discountPrivilege
											.i18n('miser.discount.timeout')); // 请求超时
								} else {
									var message = json.message;
									miser.showErrorMes(message); // 提示失败原因
								}
							};
							miser.requestJsonAjax('discountPrivilege!addDiscountPrivilege.action',
									params, successFun, failureFun); // 发送AJAX请求
						}
					}
				});
			} else {
				params.discountPrivilegeVo.discountPrivilegeEntity.isAlert='0';
				if(parseFloat(params.discountPrivilegeVo.discountPrivilegeEntity.startAmount)>= parseFloat(params.discountPrivilegeVo.discountPrivilegeEntity.endAmount)){
					miser.showErrorMes('发货金额分段数据有误');
				}else{

					var successFun = function(json) {
						var message = json.message;
						if(!Ext.isEmpty(message)&&message=='miser.base.invalid'){
							miser.showErrorMes('无法修改已作废或已过期数据'); // 请求超时
						}else if(!Ext.isEmpty(message)&&message=='miser.base.isAlert'){
							miser.showQuestionMes('该越发越惠存在相同的待生效数据，是否覆盖？',// 是否删除
									function(e) {
										if (e == 'yes') {
											params.discountPrivilegeVo.discountPrivilegeEntity.isAlert='1';
											miser.requestJsonAjax('discountPrivilege!addDiscountPrivilege.action',
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
							miser.showErrorMes(discount.discountPrivilege
									.i18n('miser.discount.timeout')); // 请求超时
						} else {
							var message = json.message;
							miser.showErrorMes(message); // 提示失败原因
						}
					};
					miser.requestJsonAjax('discountPrivilege!addDiscountPrivilege.action',
							params, successFun, failureFun); // 发送AJAX请求
				}
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : discount.discountPrivilege.i18n('miser.common.save'),// '保存',
			// 保存
			/* margin : '0 0 0 55', */
			handler : function() {
				me.submitDiscountPrivilegeAddForm();
			}
		}, {
			text : discount.discountPrivilege.i18n('miser.common.cancel'),// '取消',
			// 取消
			handler : function() {
				me.close();
			}
		} ];
		me.items = [ me.getDiscountPrivilegeAddForm()];
		me.callParent([ cfg ]);
	}
});
/**
 * 修改越发越惠窗口
 */
Ext.define('Miser.view.discount.DiscountPrivilegeUpdateWindow', {
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
	discountPrivilegeEntity : null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getDiscountPrivilegeUpdateForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getDiscountPrivilegeUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			me.getDiscountPrivilegeUpdateForm().getForm().loadRecord(new Miser.model.DiscountPrivilegeEntity(me.discountPrivilegeEntity));
			//me.getDiscountPrivilegeUpdateForm().getForm().findField("effectiveTime").setValue(new Date(new Date().getTime() + 5*60*1000));
		}
	},
	DiscountPrivilegeUpdateForm : null,
	getDiscountPrivilegeUpdateForm : function() {
		if (Ext.isEmpty(this.DiscountPrivilegeUpdateForm)) {
			this.DiscountPrivilegeUpdateForm = Ext.create('Miser.view.discount.DiscountPrivilegeUpdateForm');
		}
		return this.DiscountPrivilegeUpdateForm;
	},
	submitDriver : function() {
		var me = this;
		if (me.getDiscountPrivilegeUpdateForm().getForm().isValid()) { // 校验form是否通过校验
			var DiscountPrivilegeEntity = new Miser.model.DiscountPrivilegeEntity();
			me.getDiscountPrivilegeUpdateForm().getForm().updateRecord(
					DiscountPrivilegeEntity); // 将FORM中数据设置到MODEL里面
			var curData = DiscountPrivilegeEntity.data;
			var params = {
				'discountPrivilegeVo' : {
					'discountPrivilegeEntity' : curData
				}
			}
			params.discountPrivilegeVo.discountPrivilegeEntity.isAlert='0';
			var successFun = function(json) {
				var message = json.message;
				if(!Ext.isEmpty(message)&&message=='miser.base.isAlert'){
					miser.showQuestionMes('该越发越惠存在相同的待生效数据，是否覆盖？',// 是否删除
							function(e) {
								if (e == 'yes') {
									params.discountPrivilegeVo.discountPrivilegeEntity.isAlert='1';
									miser.requestJsonAjax('discountPrivilege!updateDiscountPrivilege.action',
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
			miser.requestJsonAjax('discountPrivilege!updateDiscountPrivilege.action',
					params, successFun, failureFun); // 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : discount.discountPrivilege.i18n('miser.common.save'), // 保存
			margin : '0 0 0 305',
			handler : function() {
				me.submitDriver();
			}
		}, {
			text : discount.discountPrivilege.i18n('miser.common.cancel'), // 取消
			handler : function() {
				me.close();
			}
		} ];
		me.items = [ me.getDiscountPrivilegeUpdateForm()];
		me.callParent([ cfg ]);
	}
});
/**
 * 越发越惠列表store
 */
Ext.define('Miser.store.DiscountPrivilegeStore',
			{
				extend : 'Ext.data.Store',
				model : 'Miser.model.DiscountPrivilegeEntity',
				pageSize : 20,
				proxy : {
					type : 'ajax',
					actionMethods : 'post',
					url : 'discountPrivilege!queryListByParam.action',
					reader : {
						type : 'json',
						rootProperty : 'discountPrivilegeVo.discountPrivilegeList',
						totalProperty : 'totalCount' // 总个数
					}
				},
				listeners : {
					beforeload : function(store, operation, eOpts) {
						var queryForm = Ext.getCmp("queryForm");
						if(queryForm != null) {
							var params = {
								"discountPrivilegeVo.discountPrivilegeEntity.hasCoupon": queryForm.getForm().findField("hasCoupon").getValue(),
								"discountPrivilegeVo.discountPrivilegeEntity.state": queryForm.getForm().findField("state").getValue()
							};
							Ext.apply(store.proxy.extraParams, params);							
						}
					}
				}
	});

/**
 * 标准价格信息表格
 */
Ext.define('Miser.view.DiscountPrivilege.Grid', {
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
	DiscountPrivilegeAddWindow : null,
	getDiscountPrivilegeAddWindow : function() {
		if (Ext.isEmpty(this.DiscountPrivilegeAddWindow)) {
			this.DiscountPrivilegeAddWindow = Ext
					.create('Miser.view.discount.DiscountPrivilegeAddWindow');
			this.DiscountPrivilegeAddWindow.parent = this; // 父元素
		}
		return this.DiscountPrivilegeAddWindow;
	},
	DiscountPrivilegeUpdateWindow : null,
	getDiscountPrivilegeUpdateWindow : function() {
		if (Ext.isEmpty(this.DiscountPrivilegeUpdateWindow)) {
			this.DiscountPrivilegeUpdateWindow = Ext
					.create('Miser.view.discount.DiscountPrivilegeUpdateWindow');
			this.DiscountPrivilegeUpdateWindow.parent = this; // 父元素
		}
		return this.DiscountPrivilegeUpdateWindow;
	},
	updateDiscountPrivilege : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		var id = selections[0].get('id');
		var params = {
			'discountPrivilegeVo' : {
				'discountPrivilegeEntity' : {
					'id' : id
				}
			}
		};
		var successFun = function(json) {
			var updateWindow = me.getDiscountPrivilegeUpdateWindow(); // 获得修改窗口
			updateWindow.discountPrivilegeEntity = json.discountPrivilegeVo.discountPrivilegeEntity;
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
		miser.requestJsonAjax('discountPrivilege!queryDiscountPrivilegeById.action',params, successFun, failureFun);
	},
	deleteDiscountPrivilege : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断是否至少选中了一条
			miser.showWoringMessage(discount.discountPrivilege.i18n('discount.discountPrivilege.oneselected')); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		Ext.Msg.prompt('作废操作:', '请填写作废原因:', function(btn, text)
       {
            if (btn == 'ok')
            {
                    if(Ext.isEmpty(text))
                    {
                        miser.showErrorMes('输入不可为空！');
                    }else
                    {
                         var id = selections[0].get('id');
								var params = {
									'discountPrivilegeVo' : {
										'discountPrivilegeEntity' : {
											'id' : id,
											'remark':text
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
										miser.showErrorMes(discount.discountPrivilege.i18n('miser.discount.timeout')); // 请求超时
									} else {
										var message = json.message;
										miser.showErrorMes(message);
									}
								};
								miser.requestJsonAjax('discountPrivilege!deleteDiscountPrivilege.action',params,successFun,failureFun);
                    }
            };
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
			dataIndex : 'startAmount',
			width : 100,
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.startAmount'),
			align : 'center'
		},
		{
			dataIndex : 'endAmount',
			width : 120,
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.endAmount'),
			align : 'center'
		},
		{
			dataIndex : 'ddMinFreightDiscount',
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.ddMinFreightDiscount'),
			width : 120,
			align : 'center'
		},
		{
			dataIndex : 'duMinFreightDiscount',
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.duMinFreightDiscount'),
			width : 100,
			align : 'center'
		},
		{
			dataIndex : 'hasCoupon',
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.hasCoupon'),
					renderer : function(value) {
						return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(discount.discountPrivilege.active),value);
					},
			width : 100,
			align : 'center'
		},
		{
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.maxCouponScale'),
			dataIndex : 'maxCouponScale',
			width : 100,
			align : 'center'
		},
		{
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.effectiveTime'),
			dataIndex : 'effectiveTime',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 100,
			align : 'center'
		},
		{
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.invalidTime'),
			dataIndex : 'invalidTime',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 100,
			align : 'center'
		},{
			text : discount.discountPrivilege
			.i18n('discount.discountPrivilege.active'), // '是否有效',
				dataIndex : 'active',
				renderer : function(value) {
					return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(discount.discountPrivilege.active),value);
				},
				width : 100,
			align : 'center',
			hidden : true
		},{
			text : discount.discountPrivilege
			.i18n('discount.discountPrivilege.state'),
				dataIndex : 'state',
				width : 100,
				renderer : function(value) {
					return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(discount.discountPrivilege.state),value);
				},
			align : 'center'
		},
		{
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.remark'), // '备注',
			dataIndex : 'remark',
			width : 100,
			align : 'center'
		},
		{
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.createUser'), // '创建人',
			dataIndex : 'createUser',
			width : 100,
			align : 'center'
		},
		{
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.createDate'), // '创建时间',
			dataIndex : 'createDate',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 150,
			align : 'center'
		},
		{
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.modifyUser'), // '修改用户',
			dataIndex : 'modifyUser',
			width : 100,
			align : 'center'
		},
		{
			text : discount.discountPrivilege
					.i18n('discount.discountPrivilege.modifyDate'), // '修改时间',
			dataIndex : 'modifyDate',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 150,
			align : 'center'
		}
        ],
        me.store = Ext.create('Miser.store.DiscountPrivilegeStore', {
            autoLoad: true
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', { // 多选框
            mode: 'MULTI',
            checkOnly: true
        });
        me.tbar = [
                   {
					text : discount.discountPrivilege.i18n('miser.common.add'),
					xtype : 'addbutton',
					// 新增
					width : 80,
					handler : function() {
						me.getDiscountPrivilegeAddWindow().show();
					}
					},
					{
						id:'miser_biz_discount_discountAddValue_Update_id',
						text : discount.discountPrivilege
								.i18n('miser.common.update'),
						xtype : 'updatebutton',
						width : 80,
						handler : function() {
							me.updateDiscountPrivilege();
						}
					},{
					id:'miser_biz_discount_discountAddValue_del_id',
					text : discount.discountPrivilege
							.i18n('miser.common.invalid'),// '删除',
					xtype : 'deletebutton',
					width : 80,
					handler : function() {
						me.deleteDiscountPrivilege();
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
								Ext.getCmp('miser_biz_discount_discountAddValue_Update_id').setDisabled(true);
								Ext.getCmp('miser_biz_discount_discountAddValue_del_id').setDisabled(true);
							}else {
								Ext.getCmp('miser_biz_discount_discountAddValue_Update_id').setDisabled(false);
								Ext.getCmp('miser_biz_discount_discountAddValue_del_id').setDisabled(false);
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
	var queryForm = Ext.create("Miser.view.discount.QueryForm");
	var DiscountPrivilegeGrid = Ext.create('Miser.view.DiscountPrivilege.Grid');
	var mainPanel=Ext.create('Ext.panel.Panel', {
		renderTo : Ext.getBody(),
		getDiscountPrivilegeGrid : function() {
			return DiscountPrivilegeGrid;
		},
		items : [ queryForm, DiscountPrivilegeGrid ]
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
		DiscountPrivilegeGrid.setWidth(document.documentElement.clientWidth
				- 20);
	};
	
	
	
	
});