discount.privilegeCouponCheck.checkState = 'CHECK_STATE';

// 越发越惠 客户合同-折扣-返券 列表model
Ext.define('Miser.model.privilegeCouponCheckEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		// 主键
		type : 'string'
	}, {
		name : 'contractCode',
		// 合同CODE
		type : 'string'
	}, {
		name : 'customerCode',
		// 客户编号
		type : 'string'
	}, {
		name : 'customerName',
		// 客户名称
		type : 'string'
	},{
		name: 'contactName',
		// 联系人
		type: 'string'
	}, {
		name : 'contactPhone',
		// 联系电话
		type : 'string'
	}, {
		name : 'privilegeStartTime',
		// 越发越惠开始时间
		type : 'date',
		dateFormat : 'time'
	}, {
		name : 'privilegeEndTime',
		// 越发越惠结束时间
		type : 'date',
		dateFormat : 'time'
	}, {
		name : 'recordMonth',
		// 执行年月
		type : 'date',
		dateFormat : 'time'
	}, {
		name : 'currentMonthAmount',
		// 当月产值
		type : 'number'
	}, {
		name : 'couponId',
		// 返券ID
		type : 'string'
	}, {
		name : 'hasCoupon',
		// 是否返券
		type : 'string'
	}, {
		name : 'couponScale',
		// 返券比例
		type : 'number'
	}, {
		name : 'couponAmount',
		// 返券值
		type : 'number'
	}, {
		name : 'couponState',
		// 返券状态
		type : 'string'
	}, {
		name : 'seaDivision',
		// 事业部
		type : 'string'
	}, {
		name : 'seaBigregion',
		// 大区
		type : 'string'
	}, {
		name : 'seaRoadarea',
		// 路区
		type : 'string'
	}, {
		name : 'seaSalesdepartment',
		// 门店
		type : 'string'
	}, {
		name : 'acceptorName',
		// 收券人姓名
		type : 'string'
	}, {
		name : 'relationshipWithCustomer',
		// 与客户关系
		type : 'string'
	}, {
		name : 'acceptorPhone',
		// 收券人手机
		type : 'string'
	}, {
		name : 'creditCardNo',
		// 收券人银行账号
		type : 'string'
	}, {
		name : 'identityCardNo',
		// 收券人身份证号
		type : 'string'
	}, {
		name : 'bankName',
		// 收券人开户行
		type : 'string'
	}, {
		name : 'subbranchName',
		// 开户支行
		type : 'string'
	}, {
		name : 'alipayAccount',
		// 支付宝账号
		type : 'string'
	}, {
		name : 'checkState',
		// 备注类型，1为审核成功，2为审核失败，3为返券失败登记
		type : 'string'
	}, {
		name : 'checkRemark',
		// 备注说明
		type : 'string'
	}]
});



//越发越惠客户合同列表store
Ext.define('Miser.store.privilegeCouponCheckStore', {
					extend : 'Ext.data.Store',
					model : 'Miser.model.privilegeCouponCheckEntity',
					pageSize : 20,
					proxy : {
						type : 'ajax',
						actionMethods : 'post',
						url : 'privilegeCouponCheck!queryListByParam.action',
						reader : {
							type : 'json',
							rootProperty : 'privilegeCouponCheckVoList',
							totalProperty : 'totalCount' // 总个数
						}
					},
					listeners : {
						beforeload : function(store, operation, eOpts) {
							var queryForm = Ext.getCmp('queryForm');
							if (queryForm != null) {
								var params = {
									'privilegeCouponCheckVo.customerCode' : queryForm
											.getForm().findField('customerCode').getValue(),
									'privilegeCouponCheckVo.customerName' : queryForm
											.getForm().findField('customerName').getValue(),
									'privilegeCouponCheckVo.recordMonth' : queryForm
											.getForm().findField('recordMonth').getValue(),
									'privilegeCouponCheckVo.checkState' : queryForm
											.getForm().findField('checkState').getValue(),
									'privilegeCouponCheckVo.seaDivision' : queryForm
											.getForm().findField('seaDivision').getValue(),
									'privilegeCouponCheckVo.seaBigregion' : queryForm
											.getForm().findField('seaBigregion').getValue(),
									'privilegeCouponCheckVo.seaRoadarea' : queryForm
											.getForm().findField('seaRoadarea').getValue(),
									'privilegeCouponCheckVo.seaSalesdepartment' : queryForm
											.getForm().findField('seaSalesdepartment').getValue()
								};
								Ext.apply(store.proxy.extraParams, params);
							}
						}

					}
});

discount.privilegeCouponCheck.state = 'PRICE_SATUS';
discount.privilegeCouponCheck.active = 'IS_ACTIVE';
var customerContractId = null;
//新增时修改列的行号
var adddataIndex = null;
// 越发越惠返券审核 查询表单
Ext.define('Miser.discount.privilegeCouponCheck.QueryForm', {
						extend : 'Ext.form.Panel',
						id : 'queryForm',
						frame : true,
						width : document.documentElement.clientWidth - 20,
						title : discount.privilegeCouponCheck
								.i18n('miser.common.queryCondition'),// '查询条件',
						collapsible : true,
						region : 'north',
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
									me.items = [{
												layout : 'hbox',
												items : [ {
													loaddefault:true,
										            width : document.documentElement.clientWidth - 25,
										            divisionLabelWidth: 100,
										            bigregionLabelWidth: 100,
										            roadareaLabelWidth: 100,
										            salesdepartmentLabelWidth: 100,
										            divisionLabel: discount.privilegeCouponCheck.i18n('miser.common.division') + ':',
													divisionName: 'seaDivision',
										            divisionIsBlank:true,
										            bigregionLabel: discount.privilegeCouponCheck.i18n('miser.common.bigregion') + ':',
										            bigregionName: 'seaBigregion',
										            bigregionIsBlank:true,
										            roadareaLabel: discount.privilegeCouponCheck.i18n('miser.common.roadarea') + ':',
										            roadareaName: 'seaRoadarea',
										            roadareaIsBlank:true,
										            salesdepartmentLabel: discount.privilegeCouponCheck.i18n('miser.common.salesdepartment') + ':',
										            salesdepartmentName: 'seaSalesdepartment',
										            salesdepartmentIsBlank:true,
										            type: 'D-B-R-S',
										            xtype: 'linkorgcombselector'
										    	} ]
											},
											{
												layout : 'hbox',
												items : [
														{
															name : 'customerCode',
															fieldLabel : discount.privilegeCouponCheck
																	.i18n('discount.privilegeCouponCheck.customerCode'), // 越发越惠客户编号
															xtype : 'textfield'
														},
														{
															name : 'customerName',
															fieldLabel : discount.privilegeCouponCheck
																	.i18n('discount.privilegeCouponCheck.customerName'),
															xtype : 'textfield'
														},
														{
															name : 'recordMonth',
															fieldLabel : discount.privilegeCouponCheck
																	.i18n('discount.privilegeCouponCheck.recordMonth'),
															xtype : 'datefield',
															format: 'Y-m'
														},
														{
															name : 'checkState',
															fieldLabel : discount.privilegeCouponCheck
																	.i18n('discount.privilegeCouponCheck.checkState'),
															xtype : 'dataDictionarySelector',anyRecords:all,
															termsCode : discount.privilegeCouponCheck.checkState
														} ]
											} ],
									me.buttons = [
											{
												text : discount.privilegeCouponCheck.i18n('miser.common.query'),
												handler : function() {
													if (me.getForm().isValid()) {
														for(var i=0; i<ORG_FIELDS.length; i++) {
															me.getForm().findField(ORG_FIELDS[i]).enable();
														}
														me.up().getprivilegeCouponCheckGrid().getPagingToolbar().moveFirst();
														setORG(me);
													}
												}
											},
											{
												text : discount.privilegeCouponCheck.i18n('miser.common.reset'),
												handler : function() {
													me.getForm().reset();
													setORG(me);
												}
											} ];
							me.callParent([ cfg ]);
						}
});

// 越发越惠 返券申请审核 表格
Ext.define('Miser.discount.privilegeCouponCheck.Grid', {
					extend : 'Ext.grid.Panel',
					frame : true,
					autoScroll : true,
					height : miser.getBrowserHeight() - 120,
					stripeRows : true,
					selType : "rowmodel",
					emptyText : discount.privilegeCouponCheck.i18n('miser.common.emptyText'),
					columnLines : true,
					id : 'checkGrid',
					viewConfig : {
						enableTextSelection : true
					},
					privilegeCouponCheckWindow : null,
					getPrivilegeCouponCheckWindow : function() {
						if (Ext.isEmpty(this.privilegeCouponCheckWindow)) {
							this.privilegeCouponCheckWindow = Ext
									.create('Miser.discount.privilegeCouponCheck.CheckWindow');
						}
						return this.privilegeCouponCheckWindow;
					},
					privilegeCouponCheckRemarkWindow : null,
					getPrivilegeCouponCheckRemarkWindow : function() {
						if(Ext.isEmpty(this.privilegeCouponCheckRemarkWindow)) {
							this.privilegeCouponCheckRemarkWindow = Ext.create(
									'Miser.discount.privilegeCouponCheck.RemarkWindow', 
									{'title': discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.checkFailRecord'), 
									 'remarkLable' : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.checkFailRecordReason')});
							this.privilegeCouponCheckRemarkWindow.checkState = 'COUPON_FAIL';
						}
						return this.privilegeCouponCheckRemarkWindow;
					},
					//审核
					privilegeCouponCheck : function() {
						var me = this;
						var selection = me.getSelectionModel().getLastSelected();
						if (Ext.isEmpty(selection)) {
							miser.showWoringMessage(discount.privilegeCouponCheck.i18n('miser.common.pickUpOne'));
							return;
						}
						var checkWindow = me.getPrivilegeCouponCheckWindow();
						checkWindow.privilegeCouponCheckVo = selection.data;
						// 设置form不可用
						var fields = checkWindow.getPrivilegeCouponCheckForm();
						fields.items.each(function(items) {
							items.items.each(function(item) {
								item.disabled = true;
							});
						});
						checkWindow.show();
					},
					// 返券失败登记
					privilegeCouponCheckFailRecord: function() {
						var me = this;
						var selection = me.getSelectionModel().getLastSelected();
						if (Ext.isEmpty(selection)) {
							miser.showWoringMessage(discount.privilegeCouponCheck.i18n('miser.common.pickUpOne'));
							return;
						}
						var remarkWindow = me.getPrivilegeCouponCheckRemarkWindow();
						remarkWindow.show();
					},
					pagingToolbar : null,
					getPagingToolbar : function() {
						if (Ext.isEmpty(this.pagingToolbar)) {
							this.pagingToolbar = Ext.create(
									'Ext.toolbar.Paging', {
										store : this.store,
										pageSize : 20
									});
						}
						return this.pagingToolbar;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.columns = [
								{
									text : discount.privilegeCouponCheck.i18n('miser.common.serial'),
									width : 60,
									xtype : 'rownumberer',
									align : 'center'
								},
								{
									dataIndex : 'contractCode',
									hidden : true
								},
								{
									dataIndex : 'customerCode',
									width : 120,
									align : 'center',
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.customerCode') // '客户编号'
								},
								{
									dataIndex : 'customerName',
									width : 120,
									align : 'center',
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.customerName') // '客户名称'
								},
								{
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.contactName'), // '联系人',
									dataIndex : 'contactName',
									width : 120,
									align : 'center'
								},
								{
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.contactPhone'), // '联系电话',
									dataIndex : 'contactPhone',
									width : 120,
									align : 'center'
								},
								{
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.privilegeStartTime'), // '越发越惠开始时间',
									dataIndex : 'privilegeStartTime',
									width : 120,
									xtype : 'datecolumn',
									format: 'Y-m-d H:i:s',
									align : 'center'
								},
								{
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.privilegeEndTime'), // '越发越惠结束时间',
									dataIndex : 'privilegeEndTime',
									width : 120,
									xtype : 'datecolumn',
									format: 'Y-m-d H:i:s',
									align : 'center'
								},
								{
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.recordMonth'), // 执行年月
									dataIndex : 'recordMonth',
									width : 120,
									xtype : 'datecolumn',
									format: 'Y-m',
									align : 'center'
								},
								{
									dataIndex : 'currentMonthAmount', // 当月产值
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.currentMonthAmount'),
									width : 120,
									xtype : 'numbercolumn',
									align : 'center'
								},
								{
									dataIndex : 'couponId', // 返券ID
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.couponId'),
									width : 120,
									hidden : true,
									align : 'center'
								},
								{
									dataIndex : 'hasCoupon', // 是否返券
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.hasCoupon'),
									width : 120,
									align : 'center',
									renderer : function(value) {
										return booleanTypeRender(value);
									}
								},
								{
									dataIndex : 'couponScale', // 返券比例
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.couponScale'),
									width : 120,
									xtype : 'numbercolumn',
									align : 'center'
								},
								{
									dataIndex : 'couponAmount', // 返券值
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.couponAmount'),
									width : 120,
									xtype : 'numbercolumn',
									align : 'center'	
								},
								{
									dataIndex : 'couponState', // 返券状态
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.couponState'),
									width : 120,
									align : 'center',
									renderer : function(value) {
										return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(discount.privilegeCouponCheck.checkState), value);
									}
								},
								{
									dataIndex : 'seaSalesdepartment', // 所属门店
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.seaSalesdepartment'),
									width : 120,
									align : 'center'
								},
								{
									dataIndex : 'seaRoadarea', // 所属路区
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.seaRoadarea'),
									width : 120,
									align : 'center'	
								},
								{
									dataIndex : 'seaBigregion', // 所属大区
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.seaBigregion'),
									width : 120,
									align : 'center'	
								},
								{
									dataIndex : 'seaDivision', // 所属事业部
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.seaDivision'),
									width : 120,
									align : 'center'	
								},
								{
									dataIndex : 'acceptorName', // 收券人姓名
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.acceptorName'),
									width : 120,
									align : 'center'	
								},
								{
									dataIndex : 'relationshipWithCustomer', // 与客户关系
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.relationshipWithCustomer'),
									width : 120,
									align : 'center'	
								},
								{
									dataIndex : 'acceptorPhone', // 收券人手机
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.acceptorPhone'),
									width : 120,
									align : 'center'	
								},
								{
									dataIndex : 'identityCardNo', // 收券人身份证号
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.identityCardNo'),
									width : 120,
									align : 'center'	
								},
								{
									dataIndex : 'creditCardNo', // 收券人银行账号
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.creditCardNo'),
									width : 120,
									align : 'center'
								},
								{
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.bankName'), // 收券人开户行
									dataIndex : 'bankName',
									width : 120,
									align : 'center'
								},
								{
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.subbranchName'), // 开户支行
									dataIndex : 'subbranchName',
									width : 100,
									align : 'center'
								},
								{
									text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.alipayAccount'), // 开户支行
									dataIndex : 'alipayAccount',
									width : 100,
									align : 'center'
								}];
						me.store = Ext.create('Miser.store.privilegeCouponCheckStore', {autoLoad : false});
						me.selModel = Ext.create('Ext.selection.CheckboxModel',
								{ // 多选框
									mode : 'MULTI',
									checkOnly : true
								});
						me.tbar = [
								{
									id : 'miser_discount_privilegeCouponCheck_check_id',
									text : discount.privilegeCouponCheck
											.i18n('discount.privilegeCouponCheck.check'),
									xtype : 'updatebutton',
									width : 80,
									handler : function() {
										me.privilegeCouponCheck();
									}
								},
								{
									id : 'miser_discount_privilegeCouponCheck_couponFailRegister_id',
									text : discount.privilegeCouponCheck
											.i18n('discount.privilegeCouponCheck.couponFailRegister'),
									xtype : 'updatebutton',
									width : 130,
									handler : function() {
										me.privilegeCouponCheckFailRecord();
									}
								}];
						me.bbar = me.getPagingToolbar();
						me.selModel = Ext.create('Ext.selection.CheckboxModel', {
							listeners : {
								selectionchange : function(sm, selections) {
									try {
										var flag = selections[0].get('couponState');
										// PENDING_CHECK为待审核状态
										Ext.getCmp('miser_discount_privilegeCouponCheck_check_id').setDisabled('PENDING_CHECK' != flag);
										// CHECK_SUCCESS为审核成功状态
										Ext.getCmp('miser_discount_privilegeCouponCheck_couponFailRegister_id').setDisabled('CHECK_SUCCESS' != flag);
									} catch(e) { }
								}
							}
						}),
						me.callParent([ cfg ]);
					}
});
/**
 * 越发越惠 返券申请审核Window
 */
Ext.define('Miser.discount.privilegeCouponCheck.CheckWindow', {
	extend : 'Ext.window.Window',
	title : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.couponCheck'),
	id : 'checkWindow',
	closable : true,
	parent : null, // 父元素
	modal : true,
	resizable : false, // 可以调整窗口的大小
	closeAction : 'hide', // 点击关闭是隐藏窗口
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	privilegeCouponCheckVo : null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getPrivilegeCouponCheckForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fields = me.getPrivilegeCouponCheckForm().getForm().getFields();
			if (!Ext.isEmpty(fields)) {
				fields.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			me.getPrivilegeCouponCheckForm().getForm().loadRecord(
					new Miser.model.privilegeCouponCheckEntity(me.privilegeCouponCheckVo));
		}
	},
	privilegeCouponCheckForm : null,
	getPrivilegeCouponCheckForm : function() {
		if (Ext.isEmpty(this.privilegeCouponCheckForm)) {
			this.privilegeCouponCheckForm = Ext
					.create('Miser.discount.privilegeCouponCheck.CheckForm');
			this.privilegeCouponCheckForm.parent = this;
		}
		return this.privilegeCouponCheckForm;
	},
	privilegeCouponCheckRemarkWindow : null,
	getPrivilegeCouponCheckRemarkWindow : function(title, remarkLable) {
		this.privilegeCouponCheckRemarkWindow = Ext.create(
				'Miser.discount.privilegeCouponCheck.RemarkWindow', 
				{'title' : title, 'remarkLable' : remarkLable});
		return this.privilegeCouponCheckRemarkWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getPrivilegeCouponCheckForm()];
		me.fbar = [{
			text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.agree'),
			// 同意
			handler : function() {
				var checkRemarkWindow = me.getPrivilegeCouponCheckRemarkWindow(discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.checkSuccessFormTitle'));
				checkRemarkWindow.checkState = 'CHECK_SUCCESS';
				checkRemarkWindow.show();
			}
		}, {
			text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.reject'),
			// 不同意
			handler : function() {
				var checkRemarkWindow = me.getPrivilegeCouponCheckRemarkWindow(discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.checkRejectFormTitle'));
				checkRemarkWindow.checkState = 'CHECK_REJECT';
				checkRemarkWindow.show();
			}
		}];
		me.callParent([ cfg ]);
	}
});

/**
 * 返券人编辑Form
 */
Ext.define('Miser.discount.privilegeCouponCheck.CheckForm', {
					extend : 'Ext.form.Panel',
					width : 580,
					id : 'checkForm',
					labelAlign : 'left',
					frame : true,
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						var row1 = {
								layout : 'column',
								border : false,
								items : [{
											name : 'couponId',
											xtype: 'hiddenfield'
										},{
											name : 'contractCode',
											xtype: 'hiddenfield'
										},{
											columnWidth : .55,
											layout : 'form',
											border : false,
											name : 'seaDivision',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.seaDivision'),
											xtype: 'textfield'
										},
										{
											columnWidth : .44,
											layout : 'form',
											border : false,
											name : 'seaBigregion',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.seaBigregion'),
											xtype: 'textfield'
										} ]
							};
						var row2 = {
								layout : 'column',
								border : false,
								items : [
										{
											columnWidth : .55,
											layout : 'form',
											border : false,
											name : 'seaRoadarea',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.seaRoadarea'),
											xtype: 'textfield'
										},
										{
											columnWidth : .44,
											layout : 'form',
											border : false,
											name : 'seaSalesdepartment',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.seaSalesdepartment'),
											xtype: 'textfield'
										} ]
							};
						var row3 = {
								layout : 'column',
								border : false,
								items : [
										{
											columnWidth : .55,
											layout : 'form',
											border : false,
											name : 'customerCode',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.customerCode'),
											xtype: 'textfield'
										},
										{
											columnWidth : .44,
											layout : 'form',
											border : false,
											name : 'customerName',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.customerName'),
											xtype: 'textfield'
										} ]
							};
						var row4 = {
								layout : 'column',
								border : false,
								items : [
										{
											columnWidth : .55,
											layout : 'form',
											border : false,
											name : 'contactName',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.contactName'),
											xtype: 'textfield'
										},
										{
											columnWidth : .44,
											layout : 'form',
											border : false,
											name : 'contactPhone',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.contactPhone'),
											xtype: 'textfield'
										} ]
							};
						var row5 = {
								layout : 'column',
								border : false,
								items : [
										{
											columnWidth : .55,
											layout : 'form',
											border : false,
											name : 'couponScale',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.couponScale'),
											xtype : 'numberfield'
										},
										{
											columnWidth : .44,
											layout : 'form',
											border : false,
											name : 'couponAmount',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.couponAmount'),
											xtype : 'numberfield'
										} ]
							};
						var row6 = {
								layout : 'column',
								border : false,
								items : [
										{
											columnWidth : .55,
											layout : 'form',
											border : false,
											name : 'recordMonth',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.recordMonth'),
											xtype: 'datefield',
								            format: 'Y-m'
										},
										{
											columnWidth : .44,
											layout : 'form',
											border : false,
											name : 'currentMonthAmount',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.currentMonthAmount'),
											xtype: 'numberfield'
										} ]
							};
						var row7 = {
								layout : 'column',
								border : false,
								items : [
										{
											columnWidth : .55,
											layout : 'form',
											border : false,
											xtype : "textfield",
											name : 'acceptorName',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck
													.i18n('discount.privilegeCouponCheck.acceptorName')
										},
										{
											columnWidth : .44,
											layout : 'form',
											border : false,
											xtype : "textfield",
											name : 'relationshipWithCustomer',
											readOnly:true,
											fieldLabel : discount.privilegeCouponCheck
													.i18n('discount.privilegeCouponCheck.relationshipWithCustomer')
										} ]
							};

							var row8 = {
									layout : 'column',
									border : false,
									items : [
											{
												columnWidth : .55,
												layout : 'form',
												border : false,
												xtype : "textfield",
												name : 'acceptorPhone',
												readOnly:true,
												fieldLabel : discount.privilegeCouponCheck
														.i18n('discount.privilegeCouponCheck.acceptorPhone')
											} ]
								};
							var row9 = {
									layout : 'column',
									border : false,
									items : [
											{
												columnWidth : .98,
												layout : 'form',
												border : false,
												xtype : "textfield",
												name : 'identityCardNo',
												readOnly:true,
												fieldLabel : discount.privilegeCouponCheck
														.i18n('discount.privilegeCouponCheck.identityCardNo')
											} ]
								};
							var row10 = {
									layout : 'column',
									border : false,
									items : [
											{
												columnWidth : .98,
												border : false,
												xtype : "textfield",
												readOnly : true,
												name : 'alipayAccount',
												fieldLabel : discount.privilegeCouponCheck
															.i18n('discount.privilegeCouponCheck.alipayAccount')
											}]
								};
							var row11 = {
									layout : 'column',
									border : false,
									items : [
											{
												columnWidth : .98,
												layout : 'form',
												border : false,
												xtype : "textfield",
												name : 'creditCardNo',
												readOnly:true,
												fieldLabel : discount.privilegeCouponCheck
														.i18n('discount.privilegeCouponCheck.creditCardNo')
											} ]
								};
							var row12 = {
									layout : 'column',
									border : false,
									items : [
											{
												columnWidth : .98,
												layout : 'form',
												border : false,
												xtype : "textfield",
												name : 'bankName',
												readOnly:true,
												fieldLabel : discount.privilegeCouponCheck
														.i18n('discount.privilegeCouponCheck.bankName')
											} ]
								};
							var row13 = {
									layout : 'column',
									border : false,
									items : [
											{
												columnWidth : .98,
												layout : 'form',
												border : false,
												xtype : "textfield",
												name : 'subbranchName',
												readOnly:true,
												fieldLabel : discount.privilegeCouponCheck
														.i18n('discount.privilegeCouponCheck.subbranchName')
											} ]
								};
							var row14 = {
									layout : 'column',
									border : false,
									items : [
											{
												columnWidth : .98,
												layout : 'form',
												border : false,
												xtype : 'dataDictionarySelector',
												termsCode: discount.privilegeCouponCheck.checkState,
												name : 'couponState',
												readOnly:true,
												fieldLabel : discount.privilegeCouponCheck
														.i18n('discount.privilegeCouponCheck.couponState')
											} ]
								};
						me.items = [ row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11, row12, row13, row14 ];
						me.callParent([ cfg ]);
					}
});

/**
 * 备注Window
 */
Ext.define('Miser.discount.privilegeCouponCheck.RemarkWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	parent : null, // 父元素
	modal : true,
	resizable : false, // 可以调整窗口的大小
	closeAction : 'close',
	autoDestroy : true,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			if(!Ext.isEmpty(me.privilegeCouponCheckRemarkForm)) {
				me.privilegeCouponCheckRemarkForm.getForm().reset()
			}
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			if(!Ext.isEmpty(me.privilegeCouponCheckRemarkForm)) {
				me.privilegeCouponCheckRemarkForm.getForm().reset()
			}
		}
	},
	privilegeCouponCheckRemarkForm : null,
	getPrivilegeCouponCheckRemarkForm : function(remarkLable) {
		this.privilegeCouponCheckRemarkForm = Ext.create(
				'Miser.discount.privilegeCouponCheck.RemarkForm', {'remarkLable' : remarkLable});
		this.privilegeCouponCheckRemarkForm.parent = this;
		return this.privilegeCouponCheckRemarkForm;
	},
	checkState : null,
	submitCheck : function(checkState) {
		var me = this;
		if (me.privilegeCouponCheckRemarkForm.getForm().isValid()) { // 校验form是否通过校验
			var checkGrid = Ext.getCmp('checkGrid');
			var selection = checkGrid.getSelectionModel().getLastSelected();
			var checkRemark = me.privilegeCouponCheckRemarkForm.getForm().findField('checkRemark').getValue();
			var params = {
				'privilegeCouponCheckVo' : {
					'checkState' : checkState,
					'contractCode' : selection.data.contractCode,
					'recordMonth' : selection.data.recordMonth,
					'checkRemark' : checkRemark
				}
			}
			var successFun = function(json) {
				var message = json.message;
				miser.showInfoMsg(message); // 提示审核成功
				// 更新grid的返券状态
				var checkGrid = Ext.getCmp('checkGrid');
				checkGrid.getStore().load();
				me.close();
				var checkWindow = Ext.getCmp('checkWindow');
				if(!Ext.isEmpty(checkWindow)) {
					checkWindow.close();
				}
			};
			var failtureFun = function(json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(discount.privilegeCouponCheck.i18n('miser.discount.timeout')); // 请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message); // 提示失败原因
				}
			};
			miser.requestJsonAjax('privilegeCouponCheck!couponCheck.action',
					params, successFun, failtureFun); // 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getPrivilegeCouponCheckRemarkForm(config.remarkLable)];
		me.title = config.title;
		me.fbar = [{
			text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.submit'),
			// 提交
			handler : function() {
				me.submitCheck(me.checkState);
			}
		}, {
			text : discount.privilegeCouponCheck.i18n('discount.privilegeCouponCheck.cancel'),
			// 取消
			handler : function() {
				me.close();
			}
		}];
		me.callParent([ cfg ]);
	}
});

/**
 * 备注说明Form
 */
Ext.define('Miser.discount.privilegeCouponCheck.RemarkForm', {
	extend : 'Ext.form.Panel',
	width : 580,
	labelAlign : 'center',
	frame : true,
	bodyStyle:'padding:5px 0px 5px 0px',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var row1 = {
				layout : 'column',
				border : false,
				items : [{
					columnWidth : 1,
					layout : 'form',
					border : false,
					xtype : 'label',
					name : 'remarkLable',
					text : config.remarkLable
				}]
		};
		var row2 = {
				layout : 'column',
				border : false,
				items : [{
					columnWidth : 1,
					layout : 'form',
					border : false,
					name : 'checkRemark',
					xtype : 'textareafield'
				}]
		};
		me.items = [ row1, row2 ];
		me.callParent([ cfg ]);
	}
});

/**
 * 获取当前用户的组织信息，并调用setQueryFormORG方法设置查询FORM的组织数据
 */
var CURRENT_USER_ORG = null;
function setORG(queryForm) {
	if(CURRENT_USER_ORG == null) {
		miser.requestJsonAjax('../bizbase/orgAction!queryCurrentUserORG.action',
		{}, function(data) {
			if(!Ext.isEmpty(data) && !Ext.isEmpty(data['orgVo'])) {
				var orgEntity = data['orgVo']['orgEntity'];
				if(!Ext.isEmpty(orgEntity)) {
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
var ORG_FIELDS = ['seaDivision', 'seaBigregion', 'seaRoadarea', 'seaSalesdepartment'];
function setQueryFormORG(queryForm, ORG_data) {
	var form = queryForm.getForm();
	var length = ORG_FIELDS.length;
	for(var i=0; i<length; i++) {
		var codeValue = ORG_data[ORG_FIELDS[i] + 'Code'];
		var nameValue = ORG_data[ORG_FIELDS[i] + 'Name'];
		if(codeValue != null && codeValue != '' && nameValue != null && nameValue != '') {
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
	Ext.QuickTips.init();
	var queryForm = Ext.create('Miser.discount.privilegeCouponCheck.QueryForm'); // 查询FORM
	var privilegeCouponCheckGrid = Ext.create('Miser.discount.privilegeCouponCheck.Grid');
	
	var mainPanel=Ext.create('Ext.panel.Panel', {
		renderTo : Ext.getBody(),
		getQueryForm : function() {
			return queryForm;
		},
		getprivilegeCouponCheckGrid : function() {
			return privilegeCouponCheckGrid;
		},
		items : [ queryForm, privilegeCouponCheckGrid ]
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
		privilegeCouponCheckGrid.setWidth(document.documentElement.clientWidth
				- 20);
	};
	
	
});