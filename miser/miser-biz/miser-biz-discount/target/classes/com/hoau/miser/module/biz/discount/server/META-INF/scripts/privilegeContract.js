discount.privilegeContract.state = 'PRICE_SATUS';
discount.privilegeContract.active = 'IS_ACTIVE';
var customerContractId = null;
/**
 * 判断【是否返券】
 * @param contractForm 合同编辑Form
 * @param hasCoupon 是否返券
 */
function verifyHasCoupon(contractForm, hasCoupon) {
	var contractForm = contractForm.getForm();
	var contract_dd = contractForm.findField('ddMinFreightDiscount');
	var contract_du = contractForm.findField('duMinFreightDiscount');
	if(hasCoupon != null && hasCoupon == 'Y') {
		contract_dd.setHidden(false);
		contract_du.setHidden(false);
		contract_dd.allowBlank=false;
		contract_du.allowBlank=false;
	} else if(hasCoupon != null && hasCoupon == 'N') {
		contract_dd.setHidden(true);
		contract_du.setHidden(true);
		contract_dd.setValue('');
		contract_du.setValue('');
		contract_dd.allowBlank=true;
		contract_du.allowBlank=true;
	}
}
/**
 * 判断【是否返券】
 * @param contractDetailGrid 合同明细Grid
 * @param hasCoupon 是否返券
 */
function verifyDetailHasCoupon(contractDetailEditForm, hasCoupon) {
	var contractDetailForm = contractDetailEditForm.getForm();
	var contractDetail_dd = contractDetailForm.findField('ddMinFreightDiscount');
	var contractDetail_du = contractDetailForm.findField('duMinFreightDiscount');
	var contractDetail_maxCouponScale = contractDetailForm.findField('maxCouponScale');
	if(hasCoupon != null && hasCoupon == 'Y') {
		contractDetail_dd.setHidden(true);
		contractDetail_du.setHidden(true);
		contractDetail_dd.setValue('');
		contractDetail_du.setValue('');
		contractDetail_dd.allowBlank=true;
		contractDetail_du.allowBlank=true;
		contractDetail_maxCouponScale.setHidden(false);
		contractDetail_maxCouponScale.allowBlank=false;
		contractDetail_maxCouponScale.setDisabled(false);
	} else if(hasCoupon != null && hasCoupon == 'N') {
		contractDetail_dd.setHidden(false);
		contractDetail_du.setHidden(false);
		contractDetail_du.allowBlank=false;
		contractDetail_du.allowBlank=false;
		contractDetail_maxCouponScale.setHidden(true);
		contractDetail_maxCouponScale.allowBlank=true;
		contractDetail_maxCouponScale.setDisabled(true);
	}
}
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
		if(!Ext.isEmpty(codeValue) && !Ext.isEmpty(nameValue)) {
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
function loadData(selection, window) {
	// 子表信息
	var params = {
		'privilegeContractDetailVo' : {
			'privilegeContractDetailEntity' : {
				'customerContractId' : selection.get('id')
			}
		},
		'page' : 1,
		'limit' : 20,
		'start' : 0
	};
	var successFunOne = function(json) {
		// 修改加载明细列表
		var sectionSubList = json.privilegeContractDetailVo.privilegeContractDetailList;
		window.privilegeContractEntity = selection.getData();
		if(!Ext.isEmpty(sectionSubList)) {
			window.privilegeContractDetailEntityList = sectionSubList;
			if(selection.get("hasCoupon") == 'Y') {
				window.privilegeContractEntity.ddMinFreightDiscount = sectionSubList[0].ddMinFreightDiscount;
				window.privilegeContractEntity.duMinFreightDiscount = sectionSubList[0].duMinFreightDiscount;
			}
		}
		window.show(); // 显示修改窗口
	};
	var failureFun = function(json) {
		if (Ext.isEmpty(json)) {
			miser.showErrorMes(discount.privilegeContract
					.i18n('miser.common.timeout')); // 请求超时
		} else {
			var message = json.message;
			miser.showErrorMes(message); // 提示失败原因
		}
	};
	// 主表信息
	miser.requestJsonAjax('privilegeContract!queryListByParamSub.action', params,
			successFunOne, failureFun);
}
/**
 * 越发越惠 客户合同 列表model
 */
Ext.define('Miser.model.privilegeContractEntity', {
	extend : 'Ext.data.Model',
	fields : [
	          {name : 'id', type : 'string'}, // 主键
	          {name : 'customerCode', type : 'string'}, // 客户编号 
	          {name : 'customerName', type : 'string'}, // 客户名称
	          {name: 'contactName', type: 'string'}, // 联系人
	          {name : 'contactPhone', type : 'string'}, // 联系电话
	          {name : 'contractStartTime', type : 'date', dateFormat : 'time'}, // 合同开始时间
	          {name : 'contractEndTime', type : 'date', dateFormat : 'time'}, // 合同结束时间 
	          {name : 'privilegeStartTime', type : 'date', dateFormat : 'time'}, // 越发越惠开始时间 
	          {name : 'privilegeEndTime', type : 'date', dateFormat : 'time'}, // 越发越惠结束时间 
	          {name : 'state', type : 'string'}, // 越发越惠当前状态 
	          {name : 'hasCoupon', type : 'string'}, // 是否返券 
	          {name : 'remark', type : 'string'}, // 备注
	          {name : 'active', type : 'string'}, // 是否有效
	          {name : 'modifyUser', type : 'string'}, // 最后修改人
	          {name : 'modifyDate', type : 'date', dateFormat : 'time'}, // 最后修改时间
	          {name : 'ddMinFreightDiscount', type : 'number'}, // 定日达纯运费最低折扣
	          {name : 'duMinFreightDiscount', type : 'number'}, // 经济快运纯运费最低折扣
	          {name : 'seaDivision', type : 'string'}, // 事业部
	          {name : 'seaBigregion', type : 'string'}, // 大区
	          {name : 'seaRoadarea', type : 'string'}, // 路区
	          {name : 'seaSalesdepartment', type : 'string'}, // 门店
	          {name : 'privilegeStartAndEndTime', type : 'date', dateFormat : 'time'}, // 有效时间
	          {name : 'commitmentProduct', type : 'number'} // 承诺产值
	          ]
});
/**
 * 越发越惠 客户合同明细 列表model
 */
Ext.define('Miser.model.privilegeContractDetailEntity', {
	extend : 'Ext.data.Model',
	fields : [
	          {name : 'id', type : 'string'}, 
	          {name : 'customerContractId', type : 'string'}, // 越发越惠客户合同ID
	          {name : 'startAmount', type : 'number'}, // 发货金额段起
	          {name : 'endAmount', type : 'number'}, // 发货金额段止
	          {name : 'ddMinFreightDiscount', type : 'number'}, // 定日达纯运费最低折扣
	          {name : 'duMinFreightDiscount', type : 'number'}, // 经济快运纯运费最低折扣
	          {name : 'maxCouponScale', type : 'number'}, // 最高返券比例
	          {name : 'dataOrign', type : 'string'}, // 数据来源
	          {name : 'remark', type : 'string'}, // 备注
	          {name : 'active', type : 'string'}, // 是否有效
	          {name : 'modifyUser', type : 'string'}, // 最后修改人
	          {name : 'modifyDate', type : 'date', dateFormat : 'time'}// 最后修改时间
	          ]
});
/**
 * 越发越惠客户合同列表store
 */
Ext.define('Miser.store.privilegeContractStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.model.privilegeContractEntity',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : 'privilegeContract!queryListByParam.action',
		reader : {
			type : 'json',
			rootProperty : 'privilegeContractVo.privilegeContractList',
			totalProperty : 'totalCount' // 总个数
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('queryForm');
			if (queryForm != null) {
				var params = {
					'privilegeContractVo.privilegeContractEntity.customerCode' : queryForm
							.getForm().findField('customerCode').getValue(),
					'privilegeContractVo.privilegeContractEntity.customerName' : queryForm
							.getForm().findField('customerName').getValue(),
					'privilegeContractVo.privilegeContractEntity.hasCoupon' : queryForm
							.getForm().findField('hasCoupon').getValue(),
					'privilegeContractVo.privilegeContractEntity.state' : queryForm
							.getForm().findField('state').getValue(),
					'privilegeContractVo.privilegeContractEntity.seaDivision' : queryForm
							.getForm().findField('seaDivision').getValue(),
					'privilegeContractVo.privilegeContractEntity.seaBigregion' : queryForm
							.getForm().findField('seaBigregion').getValue(),
					'privilegeContractVo.privilegeContractEntity.seaRoadarea' : queryForm
							.getForm().findField('seaRoadarea').getValue(),
					'privilegeContractVo.privilegeContractEntity.seaSalesdepartment' : queryForm
							.getForm().findField('seaSalesdepartment').getValue(),
					'privilegeContractVo.privilegeContractEntity.privilegeStartAndEndTime' : queryForm
							.getForm().findField('privilegeStartAndEndTime').getValue()
				};
				Ext.apply(store.proxy.extraParams, params);
			}
		}
	}
});
/**
 * 越发越惠 客户合同明细 列表store
 */
Ext.define('Miser.store.privilegeContractDetailStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.model.privilegeContractDetailEntity',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : 'privilegeContract!queryListByParamSub.action',
		reader : {
			type : 'json',
			rootProperty : 'privilegeContractDetailVo.privilegeContractDetailList',
			totalProperty : 'totalCount' // 总个数
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			if (customerContractId != null) {
				var params = {
					'privilegeContractDetailVo.privilegeContractDetailEntity.id' : customerContractId
				};
				Ext.apply(store.proxy.extraParams, params);
			}
		}

	}
});
/**
 * 新增越发越惠客户合同明细store 无action请求
 */
Ext.define('Miser.store.privilegeContractDetailAddStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.model.privilegeContractDetailEntity'
});
/**
 * 客户合同 查询表单
 */
Ext.define('Miser.discount.privilegeContract.QueryForm', {
	extend : 'Ext.form.Panel',
	id : 'queryForm',
	frame : true,
	width : document.documentElement.clientWidth - 20,
	title : discount.privilegeContract.i18n('miser.common.queryCondition'),
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
			layout : 'column',
			border : false,
			items : [{
				loaddefault:true,
				width : document.documentElement.clientWidth - 25,
				
				divisionLabelWidth: 100,
				divisionLabel: discount.privilegeContract.i18n('miser.common.division') + ':',
				divisionName: 'seaDivision',
	            divisionIsBlank:true,
	            
	            bigregionLabelWidth: 100,
	            bigregionLabel: discount.privilegeContract.i18n('miser.common.bigregion') + ':',
	            bigregionName: 'seaBigregion',
	            bigregionIsBlank:true,
	            
	            roadareaLabelWidth: 100,
	            roadareaLabel: discount.privilegeContract.i18n('miser.common.roadarea') + ':',
	            roadareaName: 'seaRoadarea',
	            roadareaIsBlank:true,
	            
	            salesdepartmentLabelWidth: 100,
	            salesdepartmentLabel: discount.privilegeContract.i18n('miser.common.salesdepartment') + ':',
	            salesdepartmentName: 'seaSalesdepartment',
	            salesdepartmentIsBlank:true,
	            type: 'D-B-R-S',
	            xtype: 'linkorgcombselector'}]
		}, {
				layout : 'column',
				border : false,
				items : [{
					name : 'customerCode',
					fieldLabel : discount.privilegeContract
							.i18n('discount.privilegeContract.customerCode'), // 越发越惠客户编号
					xtype : 'textfield'
				},
				{
					name : 'customerName',
					fieldLabel : discount.privilegeContract
							.i18n('discount.privilegeContract.customerName'),
					xtype : 'textfield'
				},
				{
					name : 'hasCoupon',
					fieldLabel : discount.privilegeContract
							.i18n('discount.privilegeContract.hasCoupon'),
					xtype : 'dataDictionarySelector',anyRecords:all,
					termsCode : discount.privilegeContract.active
				},
				{
					name : 'state',
					fieldLabel : discount.privilegeContract
							.i18n('discount.privilegeContract.state'),
					xtype : 'dataDictionarySelector',anyRecords:all,
					termsCode : discount.privilegeContract.state
				}]
		}, {
				layout : 'column',
				border : false,
				items : [ {
					name : 'privilegeStartAndEndTime',
					fieldLabel : discount.privilegeContract
							.i18n('discount.privilegeContract.effectiveTime'),
					xtype : 'datefield',
					format: 'Y-m-d'
				} ]
		}];
		me.buttons = [{
			text : discount.privilegeContract.i18n('miser.common.query'),
			handler : function() {
				if (me.getForm().isValid()) {
					for(var i=0; i<ORG_FIELDS.length; i++) {
						var field = me.getForm().findField(ORG_FIELDS[i]);
						field.enable();
					}
					me.up().getPrivilegeContractGrid().getPagingToolbar().moveFirst();
					setORG(me);
				}
			}
		}, {
			text : discount.privilegeContract.i18n('miser.common.reset'),
			handler : function() {
				me.getForm().reset();
				setORG(me);
			}
		}];
		me.callParent([ cfg ]);
	}
});
/**
 * 越发越惠 客户合同 表格
 */
Ext.define('Miser.discount.privilegeContract.Grid', {
					extend : 'Ext.grid.Panel',
					frame : true,
					autoScroll : true,
					height : miser.getBrowserHeight() - 120,
					stripeRows : true,
					selType : "rowmodel",
					emptyText : discount.privilegeContract.i18n('miser.common.emptyText'),
					columnLines : true,
					viewConfig : {
						enableTextSelection : true
					},
					privilegeContractAddWindow : null,
					getPrivilegeContractAddWindow : function() {
						if (Ext.isEmpty(this.privilegeContractAddWindow)) {
							this.privilegeContractAddWindow = Ext
									.create('Miser.discount.privilegeContract.AddWindow');
							this.privilegeContractAddWindow.parent = this; // 父元素
						}
						return this.privilegeContractAddWindow;
					},
					privilegeContractUpdateWindow : null,
					getPrivilegeContractUpdateWindow : function() {
						if (Ext.isEmpty(this.privilegeContractUpdateWindow)) {
							this.privilegeContractUpdateWindow = Ext
									.create('Miser.discount.privilegeContract.UpdateWindow');
							this.privilegeContractUpdateWindow.parent = this; // 父元素
						}
						return this.privilegeContractUpdateWindow;
					},
					privilegeContractQueryWindow : null,
					getPrivilegeContractQueryWindow : function() {
						if (Ext.isEmpty(this.privilegeContractQueryWindow)) {
							this.privilegeContractQueryWindow = Ext
									.create('Miser.discount.privilegeContract.QueryWindow');
							this.privilegeContractQueryWindow.parent = this; // 父元素
						}
						return this.privilegeContractQueryWindow;
					},
					updatePrivilegeContract : function() {
						var me = this;
						var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
						if (selections.length != 1) { // 判断选中了一条
							miser.showWoringMessage(discount.privilegeContract
									.i18n('miser.common.pickUpOne')); //
							return; // 没有则提示并返回
						}
						var flag = selections[0].get('active');
						if ('N' == flag) {
							miser.showWoringMessage(discount.privilegeContract
									.i18n('miser.common.invalideData')); //
							return; // 没有则提示并返回
						}
						var updateWindow = me.getPrivilegeContractUpdateWindow(); // 获得修改窗口
						loadData(selections[0], updateWindow);
					},
					queryPrivilegeContractDetailList: function() {
						var me = this;
						var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
						if (selections.length != 1) { // 判断选中了一条
							miser.showWoringMessage(discount.privilegeContract
									.i18n('miser.common.pickUpOne'));
							return; // 没有则提示并返回
						}
						var queryWindow = me.getPrivilegeContractQueryWindow(); // 获得修改窗口
						console.info(selections[0]);
						loadData(selections[0], queryWindow);
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
					deleteprivilegeContract: function() {
						var me = this;
						var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
						if (selections.length != 1) { // 判断是否至少选中了一条
							miser.showWoringMessage(discount.privilegeContract
								.i18n('miser.common.pickUpOne')); // 请选择一条进行作废操作！
							return; // 没有则提示并返回
						}
						miser.showQuestionMes(discount.privilegeContract.i18n('miser.common.delete'), function(e) {
							if (e == 'yes') { // 询问是否删除，是则发送请求
								var id = selections[0].get('id');
								var params = {
									'privilegeContractVo' : {
										'privilegeContractEntity' : {
											'id' : id
										}
									}
								};
								var successFun = function(json) {
									var message = json.message;
									miser.showInfoMsg(message);
									me.getStore().load();
								};
								var failureFun = function(json) {
									if (Ext.isEmpty(json)) {
										miser.showErrorMes(discount.privilegeContract
											.i18n('discount.privilegeContract.timeout')); // 请求超时
									} else {
										var message = json.message;
										miser.showErrorMes(message);
									}
								};
								miser.requestJsonAjax(
									'privilegeContract!deletePrivilegeContract.action',
									params, successFun, failureFun);
							}
						});
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
								me.columns = [
										{
											text : discount.privilegeContract.i18n('miser.common.serial'),
											width : 60,
											xtype : 'rownumberer',
											align : 'center'
										},
										{
											dataIndex : 'id',
											hidden : true,
											align : 'center'
										},
										{
											dataIndex : 'customerCode',
											width : 120,
											align : 'center',
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.customerCode') // '客户编号'
										},
										{
											dataIndex : 'customerName',
											width : 120,
											align : 'center',
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.customerName') // '客户名称'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.contactName'), // '联系人',
											dataIndex : 'contactName',
											hidden : true,
											width : 120,
											align : 'center'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.contactPhone'), // '联系电话',
											dataIndex : 'contactPhone',
											hidden : true,
											width : 120,
											align : 'center'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.contractStartTime'), // '合同开始时间',
											dataIndex : 'contractStartTime',
											xtype : 'datecolumn',
											format: 'Y-m-d H:i:s',
											width : 120,
											align : 'center'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.contractEndTime'), // '合同结束时间',
											dataIndex : 'contractEndTime',
											width : 120,
											xtype : 'datecolumn',
											format: 'Y-m-d H:i:s',
											align : 'center'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.privilegeStartTime'), // '越发越惠开始时间',
											dataIndex : 'privilegeStartTime',
											width : 120,
											xtype : 'datecolumn',
											format: 'Y-m-d H:i:s',
											align : 'center'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.privilegeEndTime'), // '越发越惠结束时间',
											dataIndex : 'privilegeEndTime',
											width : 120,
											xtype : 'datecolumn',
											format: 'Y-m-d H:i:s',
											align : 'center'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.state'), // '越发越惠当前状态',
											dataIndex : 'state',
											width : 120,
											align : 'center',
											renderer : function(value) {
												if(value == "EFFECTIVE") {
													return discount.privilegeContract.i18n('miser.common.effective');
												} else if(value == "PASSED") {
													return discount.privilegeContract.i18n('miser.common.passed');
												} else if(value == "WAIT") {
													return discount.privilegeContract.i18n('miser.common.wait');
												} else if(value == "DELETED") {
													return discount.privilegeContract.i18n('miser.common.deleted');
												}
											}
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.hasCoupon'), // '是否返券',
											dataIndex : 'hasCoupon',
											renderer : function(value) {
												return booleanTypeRender(value);
											},
											width : 120,
											align : 'center'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.commitmentProduct'), // '承诺产值',
											dataIndex : 'commitmentProduct',
											width : 120,
											align : 'center'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.remark'), // '备注',
											dataIndex : 'remark',
											width : 100,
											align : 'center'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.active'), // '是否有效',
											dataIndex : 'active',
											renderer : function(value) {
												return booleanTypeRender(value);
											},
											width : 100,
											align : 'center'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.modifyUser'), // '修改用户',
											dataIndex : 'modifyUser',
											width : 100,
											align : 'center'
										},
										{
											text : discount.privilegeContract
													.i18n('discount.privilegeContract.modifyDate'), // '修改时间',
											dataIndex : 'modifyDate',
											xtype : 'datecolumn',
											format: 'Y-m-d H:i:s',
											align : 'center'
										} ], me.store = Ext.create(
										'Miser.store.privilegeContractStore', {
											autoLoad : false
										});
						me.selModel = Ext.create('Ext.selection.CheckboxModel',
								{ // 多选框
									mode : 'MULTI',
									checkOnly : true
								});
						me.tbar = [
								{
									text : discount.privilegeContract
											.i18n('miser.common.add'),// '新增',
									xtype : 'addbutton',
									width : 80,
									handler : function() {
										me.getPrivilegeContractAddWindow().show();
									}
								},
								{
									id : 'miser_discount_privilegeContract_update_id',
									text : discount.privilegeContract
											.i18n('miser.common.update'),// '修改',
									xtype : 'updatebutton',
									disabled : true,
									width : 80,
									handler : function() {
										me.updatePrivilegeContract();
									}
								},
								{
									id : 'miser_discount_privilegeContract_delete_id',
									text : discount.privilegeContract
											.i18n('miser.common.delete'),// '删除',
									xtype : 'deletebutton',
									disabled : true,
									width : 80,
									handler : function() {
										me.deleteprivilegeContract();
									}
								},
								{
									id : 'miser_discount_privilegeContract_detailBtn_id',
									text : discount.privilegeContract
											.i18n('discount.privilegeContract.detailBtn'),// '明细列表',
									xtype : 'searchbutton',
									disabled : true,
									width : 130,
									handler : function() {
										me.queryPrivilegeContractDetailList();
									}
								} ];
						me.bbar = me.getPagingToolbar();
						me.selModel = Ext.create('Ext.selection.CheckboxModel', {
							listeners : {
								selectionchange : function(sm, selections) {
									if(selections.length >= 1) {
										for(var i=0; i<selections.length; i++) {
											var flag = selections[i].get('active');
											if ('N' == flag) {
												Ext.getCmp('miser_discount_privilegeContract_update_id').setDisabled(true);
												Ext.getCmp('miser_discount_privilegeContract_delete_id').setDisabled(true);
												Ext.getCmp('miser_discount_privilegeContract_detailBtn_id').setDisabled(false);
												return;
											}
										}
										Ext.getCmp('miser_discount_privilegeContract_update_id').setDisabled(false);
										Ext.getCmp('miser_discount_privilegeContract_delete_id').setDisabled(false);
										Ext.getCmp('miser_discount_privilegeContract_detailBtn_id').setDisabled(false);
									} else {
										Ext.getCmp('miser_discount_privilegeContract_update_id').setDisabled(true);
										Ext.getCmp('miser_discount_privilegeContract_delete_id').setDisabled(true);
										Ext.getCmp('miser_discount_privilegeContract_detailBtn_id').setDisabled(true);
									}
								}
							}
						}),
						me.callParent([ cfg ]);
					}
});
/**
 * 客户合同 增加表单
 */
Ext.define('Miser.discount.privilegeContract.AddForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	width : document.documentElement.clientWidth - 20,
	region : 'north',
	defaults : {
		labelWidth : 0.3,
		columnWidth : 0.6,
		margin : '8 10 5 10',
		labelAlign : 'right'
	},
	fieldDefaults: {
		labelWidth : 70,
		labelAlign : 'right',
        margin : '5 5 0 0'
    },
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			layout : 'hbox',
			items : [{
				name : 'customerCode',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.customerName'),
				xtype : 'bsecustomercombselector',
				allowBlank : false,
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ]
			}, {
				name : 'contractStartTime',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.contractStartTime'),
				allowBlank : false,
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ],
				xtype: 'datetimefield',
	            format: 'Y-m-d H:i:s'
			}, {
				name : 'contractEndTime',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.contractEndTime'),
				allowBlank : false,
				xtype: 'datetimefield',
	            format: 'Y-m-d H:i:s'
			}, {
				id:'discount_privilegeContract_addForm_hasCoupon_id',
				name : 'hasCoupon',
				value : 'N',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.hasCoupon'),
				allowBlank : false,
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ],
				xtype : 'dataDictionarySelector',
				termsCode : discount.privilegeContract.active
			}, ]
		}, {
			layout : 'hbox',
			items : [{
				name : 'privilegeStartTime',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.privilegeStartTime'),
				allowBlank : false,
				xtype: 'datetimefield',
	            format: 'Y-m-d H:i:s'
			}, {
				allowBlank : false,
				name : 'privilegeEndTime',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.privilegeEndTime'),
				xtype: 'datetimefield',
	            format: 'Y-m-d H:i:s'
			}, {
         		xtype: 'numberfield',
				step:0.1,
				name : 'ddMinFreightDiscount',
				hidden : true,
				minValue: 0.01,
				fieldLabel : discount.privilegeContract.i18n('discount.privilegeContract.ddMinFreightDiscount'), 
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ]
			}, {
				xtype: 'numberfield',
				step:0.1,
				maxValue: 1,
				minValue: 0.01,
				name : 'duMinFreightDiscount',
				hidden : true,
				fieldLabel : discount.privilegeContract.i18n('discount.privilegeContract.duMinFreightDiscount'), 
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ]
			}]
		}, {
			layout : 'hbox',
			items : [{
				fieldLabel : discount.privilegeContract.i18n('discount.privilegeContract.commitmentProduct'), // '承诺产值',
				name : 'commitmentProduct',
				xtype : 'numberfield'
			}]
		}, {
			layout : 'hbox',
			items : [{
				fieldLabel : discount.privilegeContract.i18n('discount.privilegeContract.remark'), // '备注',
				name : 'remark',
				width : document.documentElement.clientWidth * 0.884,
				xtype : 'textarea'
			}]
		}];
		me.callParent([ cfg ]);
	}
});
/**
 * 客户合同 修改表单
 */
Ext.define('Miser.discount.privilegeContract.UpdateForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	width : document.documentElement.clientWidth - 20,
	region : 'north',
	defaults : {
		labelWidth : 0.4,
		columnWidth : 0.6,
		margin : '8 10 5 10',
		labelAlign : 'right'
	},
	fieldDefaults: {
		labelWidth : 70,
		labelAlign : 'right',
        margin : '5 5 0 0'
    },
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			layout : 'hbox',
			items : [{
				name : 'id',
				fieldLabel : 'id',
				xtype : 'textfield',
				hidden : true
			},{   
				name : 'contactName',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.contactName'),
				xtype : 'textfield',
				hidden : true,
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ]
			}, {
				name : 'contactPhone',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.contactPhone'),
				xtype : 'textfield',
				hidden : true,
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ]
			}, {
				id:'discount_privilegeContract_updateForm_customerCode_id',
				disabled : true,
				name : 'customerCode',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.customerName'),
				xtype : 'bsecustomercombselector',
				allowBlank : false,
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ]
			}, {
				id:'discount_privilegeContract_updateForm_contractStartTime_id',
				disabled : true,
				name : 'contractStartTime',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.contractStartTime'),
				allowBlank : false,
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ],
				xtype: 'datetimefield',
	            format: 'Y-m-d H:i:s'
			}, {
				id:'discount_privilegeContract_updateForm_contractEndTime_id',
				disabled : true,
				name : 'contractEndTime',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.contractEndTime'),
				allowBlank : false,
				xtype: 'datetimefield',
	            format: 'Y-m-d H:i:s'
			}, {
				id:'discount_privilegeContract_updateForm_hasCoupon_id',
				name : 'hasCoupon',
				value : 'N',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.hasCoupon'),
				allowBlank : false,
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ],
				xtype : 'dataDictionarySelector',
				termsCode : discount.privilegeContract.active
			}]
		}, {
			layout : 'hbox',
			items : [{
				name : 'privilegeStartTime',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.privilegeStartTime'),
				allowBlank : false,
				xtype: 'datetimefield',
	            format: 'Y-m-d H:i:s'
			},
			{
				allowBlank : false,
				name : 'privilegeEndTime',
				fieldLabel : discount.privilegeContract
						.i18n('discount.privilegeContract.privilegeEndTime'),
				xtype: 'datetimefield',
	            format: 'Y-m-d H:i:s'
			}, {
         		xtype: 'numberfield',
				step:0.1,
				name : 'ddMinFreightDiscount',
				hidden : true,
				minValue: 0.01,
				fieldLabel : discount.privilegeContract.i18n('discount.privilegeContract.ddMinFreightDiscount'), 
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ]
			}, {
				xtype: 'numberfield',
				step:0.1,
				name : 'duMinFreightDiscount',
				hidden : true,
				minValue: 0.01,
				fieldLabel : discount.privilegeContract.i18n('discount.privilegeContract.duMinFreightDiscount'), 
				beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ]
			}]
		}, {
			layout : 'hbox',
			items : [{
				fieldLabel : discount.privilegeContract.i18n('discount.privilegeContract.commitmentProduct'), // '承诺产值',
				name : 'commitmentProduct',
				xtype : 'numberfield'
			}]
		}, {
			layout : 'hbox',
			items : [{
				fieldLabel : discount.privilegeContract.i18n('discount.privilegeContract.remark'), // '备注',
				name : 'remark',
				width : document.documentElement.clientWidth * 0.884,
				xtype : 'textarea'
			}]
		}];
		me.callParent([ cfg ]);
	}
});
/**
 * 合同明细列表Grid
 */
Ext.define('Miser.discount.privilegeContractDetail.Grid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	autoScroll : true,
	width : document.documentElement.clientWidth - 150,
	height : document.documentElement.clientHeight - 150,
	stripeRows : true,
	// 交替行效果
	region : 'center',
	emptyText : discount.privilegeContract
	.i18n('miser.common.emptyText'),
	privilegeContractDetailAddWindow : null,
	getPrivilegeContractDetailAddWindow : function() {
		if (Ext.isEmpty(this.privilegeContractDetailAddWindow)) {
			this.privilegeContractDetailAddWindow = Ext
				.create('Miser.discount.privilegeContractDetail.EditWindow');
			this.privilegeContractDetailAddWindow.parent = this; // 父元素
		}
		return this.privilegeContractDetailAddWindow;
	},
	privilegeContractDetailUpdateWindow : null,
	getPrivilegeContractDetailUpdateWindow : function() {
		if (Ext.isEmpty(this.privilegeContractDetailUpdateWindow)) {
			this.privilegeContractDetailUpdateWindow = Ext
					.create('Miser.discount.privilegeContractDetail.EditWindow', 
							{'operation' : 'UPDATE'});
			this.privilegeContractDetailUpdateWindow.parent = this; // 父元素
		}
		return this.privilegeContractDetailUpdateWindow;
	},
	addPrivilegeContractDetail: function() {
		var me = this;
		var contractDetailAddWindow = me.getPrivilegeContractDetailAddWindow();
		verifyDetailHasCoupon(contractDetailAddWindow.getPrivilegeContractDetailEditForm(), me.parent.hasCoupon);
		contractDetailAddWindow.editCallback = function() {
			if (contractDetailAddWindow.getPrivilegeContractDetailEditForm().getForm().isValid()) { // 校验form是否通过校验
				var privilegeContractDetailEntity = new Miser.model.privilegeContractDetailEntity();
				contractDetailAddWindow.getPrivilegeContractDetailEditForm().getForm()
						.updateRecord(privilegeContractDetailEntity); // 将FORM中数据设置到MODEL里面
				var contractDetailStore = contractDetailAddWindow.parent.getStore();
				var index = contractDetailStore.count();
				privilegeContractDetailEntity.id = index;
				contractDetailStore.add(privilegeContractDetailEntity);
				var element = Ext.getCmp('discount_privilegeContract_' 
						+ me.operation.toLowerCase() + 'Form_hasCoupon_id');
				element.setDisabled(true);
				contractDetailAddWindow.close();
			}
		};
		contractDetailAddWindow.show();
	},
	updatePrivilegeContractDetail: function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断选中了一条
			miser.showWoringMessage(discount.privilegeContract
					.i18n('miser.common.pickUpOne')); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		var updateDetailWindow = me.getPrivilegeContractDetailUpdateWindow(); // 获得修改窗口
		updateDetailWindow.privilegeContractDetailEntity = {
			'id' : selections[0].get('id'),
			'startAmount' : selections[0].get('startAmount'),
			'endAmount' : selections[0].get('endAmount'),
			'ddMinFreightDiscount' : selections[0].get('ddMinFreightDiscount'),
			'duMinFreightDiscount' : selections[0].get('duMinFreightDiscount'),
			'maxCouponScale' : selections[0].get('maxCouponScale'),
			'remark' : selections[0].get('remark')
		};
		verifyDetailHasCoupon(updateDetailWindow.getPrivilegeContractDetailEditForm(), me.parent.hasCoupon);
		updateDetailWindow.editCallback = function() {
			var updateDetailWindow = me.getPrivilegeContractDetailUpdateWindow();
			if (updateDetailWindow.getPrivilegeContractDetailEditForm().getForm().isValid()) { // 校验form是否通过校验
				var privilegeContractDetailEntity = new Miser.model.privilegeContractDetailEntity();
				updateDetailWindow.getPrivilegeContractDetailEditForm().getForm()
						.updateRecord(privilegeContractDetailEntity); // 将FORM中数据设置到MODEL里面
				var contractDetailStore = updateDetailWindow.parent.getStore();
				contractDetailStore.remove(selections[0]);
				contractDetailStore.add(privilegeContractDetailEntity);
				updateDetailWindow.close();
			}
		};
		updateDetailWindow.show(); // 显示修改窗口
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
	deletePrivilegeContractDetail : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length != 1) { // 判断是否至少选中了一条
			miser.showWoringMessage(discount.privilegeContract
							.i18n('miser.common.pickUpOne')); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		miser.showQuestionMes(discount.privilegeContract.i18n('miser.common.delete'), function(e) {
			if (e == 'yes') { // 询问是否删除，是则发送请求
				me.getStore().remove(selections[0]);
				var count = me.getStore().count();
				if(count == 0) {
					var element = Ext.getCmp('discount_privilegeContract_' 
							+ me.operation.toLowerCase() + 'Form_hasCoupon_id');
					element.setDisabled(false);
				}
			}
		});
	},
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create(
					'Ext.toolbar.Paging', {
						store : this.store,
						pageSize : 20
					});
		}
		return this.pagingToolbar;
	},
	operation : 'ADD', // ADD：新增，UPDATE：更新，QUERY：查询
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		if(!Ext.isEmpty(config) && !Ext.isEmpty(config.operation)) {
			me.operation = config.operation;			
		}
		me.columns = [{
					text : discount.privilegeContract.i18n('miser.common.serial'),
					width : 60,
					xtype : 'rownumberer',
					align : 'center'
				},
				{
					dataIndex : 'id',
					hidden : true,
					align : 'center'
				},
				{
					dataIndex : 'customerContractId',
					width : 100,
					text : discount.privilegeContract
							.i18n('discount.privilegeContract.shortCode'),
					hidden : true,
					align : 'center'
				},
				{
					dataIndex : 'startAmount',
					width : 120,
					align : 'center',
					text : discount.privilegeContract
							.i18n('discount.privilegeContract.startAmount')
				},
				{
					text : discount.privilegeContract
							.i18n('discount.privilegeContract.endAmount'),
					dataIndex : 'endAmount',
					width : 120,
					align : 'center'
				},
				{
					text : discount.privilegeContract
							.i18n('discount.privilegeContract.ddMinFreightDiscount'),
					dataIndex : 'ddMinFreightDiscount',
					width : 100,
					align : 'center'
				},
				{
					text : discount.privilegeContract
							.i18n('discount.privilegeContract.duMinFreightDiscount'),
					dataIndex : 'duMinFreightDiscount',
					width : 100,
					align : 'center'
				},
				{
					text : discount.privilegeContract
							.i18n('discount.privilegeContract.maxCouponScale'),
					dataIndex : 'maxCouponScale',
					width : 100,
					align : 'center'
				},
				{
					text : discount.privilegeContract
							.i18n('discount.privilegeContract.dataOrign'),
					dataIndex : 'dataOrign',
					width : 100,
					align : 'center'
				},
				{
					text : discount.privilegeContract
							.i18n('discount.privilegeContract.remark'), // '备注',
					dataIndex : 'remark',
					width : 100,
					align : 'center'
				},
				{
					text : discount.privilegeContract
							.i18n('discount.privilegeContract.active'), // '是否有效',
					dataIndex : 'active',
					renderer : function(value) {
						return booleanTypeRender(value);
					},
					hidden : true,
					width : 100,
					align : 'center'
				},
				{
					text : discount.privilegeContract
							.i18n('discount.privilegeContract.modifyUser'), // '修改用户',
					dataIndex : 'modifyUser',
					width : 100,
					align : 'center'
				},
				{
					text : discount.privilegeContract
							.i18n('discount.privilegeContract.modifyDate'), // '修改时间',
					dataIndex : 'modifyDate',
					xtype : 'datecolumn',
					format: 'Y-m-d H:i:s',
					width : 100,
					align : 'center'
				}];
		if(me.operation == 'ADD') {
			me.store = Ext.create('Miser.store.privilegeContractDetailAddStore');
		} else {
			me.store = Ext.create('Miser.store.privilegeContractDetailStore');			
		}
		if(me.operation != 'QUERY') {
			me.tbar = [{
				text : discount.privilegeContract
						.i18n('miser.common.add'),// '新增',
				xtype : 'addbutton',
				// 新增
				width : 100,
				handler : function() {
					me.addPrivilegeContractDetail();
				}
			}, {
				id : 'miser_discount_privilegeContractDetail_' + me.operation + '_update_id',
				text : discount.privilegeContract
						.i18n('miser.common.update'),// '修改',
				xtype : 'updatebutton',
				disabled : true,
				width : 120,
				handler : function() {
					me.updatePrivilegeContractDetail();
				}
			}, {
				id : 'miser_discount_privilegeContractDetail_' + me.operation + '_delete_id',
				text : discount.privilegeContract.i18n('miser.common.delete'),
				xtype : 'deletebutton',
				disabled : true,
				width : 80,
				handler : function() {
					me.deletePrivilegeContractDetail();
				}
			}];
		}
		me.bbar = me.getPagingToolbar();
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			listeners : {
				selectionchange : function(sm, selections) {
					Ext.getCmp('miser_discount_privilegeContractDetail_' + me.operation + '_delete_id')
							.setDisabled(selections.length === 0);
					Ext.getCmp('miser_discount_privilegeContractDetail_' + me.operation + '_update_id')
					.setDisabled(selections.length === 0);
				}
			}
		});
		me.callParent([ cfg ]);
	}
});
/**
 * 越发越惠 客户合同明细 编辑表单
 */
Ext.define('Miser.discount.privilegeContractDetail.EditForm', {
					extend : 'Ext.form.Panel',
					width : 500,
					labelAlign : 'left',
					frame : true,
					fieldDefaults : {
						labelWidth : 120,
						margin : '8 10 5 10'
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						var row1 = {
							layout : 'column',
							border : false,
							labelSeparator : '：',
							items : [
									{
										layout : 'form',
										border : false,
										items : [ {
											name : 'id',
											fieldLabel : 'ID',
											xtype : 'textfield',
											hidden : true
										} ]
									},
									{
										layout : 'form',
										border : false,
										items : [ {
											name : 'customerContractId',
											fieldLabel : 'customerContractId',
											xtype : 'textfield',
											hidden : true
										} ]
									},
									{
										columnWidth : .55,
										layout : 'form',
										border : false,
										items : [ {
											name : 'startAmount',
											minValue: 0.01,
											fieldLabel : discount.privilegeContract
											.i18n('discount.privilegeContract.startAndEndAmount'),
											beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ],
											xtype: 'numberfield',
											allowBlank : false,
											step:100
										} ]
									},
									{
										columnWidth : .43,
										layout : 'form',
										border : false,
										items : [ {
											name : 'endAmount',
											xtype: 'numberfield',
											minValue: 0.01,
											allowBlank : false,
											afterLabelTpl : ['-'],
											step:100
										} ]
									} ]
						};
						var row2 = {
							layout : 'column',
							border : false,
							labelSeparator : '：',
							items : [
									{
										columnWidth : .98,
										layout : 'form',
										border : false,
										items : [ {
											xtype: 'numberfield',
											step:0.1,
											name : 'ddMinFreightDiscount',
											minValue: 0.01,
											allowBlank : false,
											fieldLabel : discount.privilegeContract
													.i18n('discount.privilegeContract.ddMinFreightDiscount'), 
											beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ]
										} ]
									} ]
						};

						var row3 = {
							layout : 'column',
							border : false,
							labelSeparator : '：',
							items : [
									{
										columnWidth : .98,
										layout : 'form',
										border : false,
										items : [ {
											xtype: 'numberfield',
											step:0.1,
											name : 'duMinFreightDiscount',
											minValue: 0.01,
											allowBlank : false,
											fieldLabel : discount.privilegeContract
													.i18n('discount.privilegeContract.duMinFreightDiscount'), 
											beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ]
										} ]
									} ]
						};
						
						var row4 = {
								layout : 'column',
								border : false,
								labelSeparator : '：',
								items : [
										{
											columnWidth : .98,
											layout : 'form',
											border : false,
											items : [ {
												xtype: 'numberfield',
												step:0.1,
												name : 'maxCouponScale',
												minValue: 0.01,
												hidden : true,
												fieldLabel : discount.privilegeContract
														.i18n('discount.privilegeContract.maxCouponScale'),
												beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="' + discount.privilegeContract.i18n('miser.common.required') + '">*</span>' ]
											} ]
										} ]
							};
						var row5 = {
								layout : 'column',
								border : false,
								labelSeparator : '：',
								items : [
										{
											columnWidth : .98,
											layout : 'form',
											border : false,
											items : [ {
												fieldLabel : discount.privilegeContract
												.i18n('discount.privilegeContract.remark'), // '备注',
												xtype : 'textarea',
												name : 'remark',
												width : document.documentElement.clientWidth * 0.41
											} ]
										} ]
							};
						me.items = [ row1, row2, row3, row4, row5 ];
						me.callParent([ cfg ]);
					}
});
/******** 窗口 ********/
/**
 * 越发越惠 客户合同 增加窗口
 */
Ext.define('Miser.discount.privilegeContract.AddWindow', {
	extend : 'Ext.window.Window',
	title : discount.privilegeContract.i18n('miser.common.add'),
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
			var element = Ext.getCmp('discount_privilegeContract_addForm_hasCoupon_id');
			element.setDisabled(false);
			me.getPrivilegeContractAddForm().getForm().reset(); // 表格重置
			me.getPrivilegeContractDetailAddGrid().getStore().removeAll();
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fields = me.getPrivilegeContractAddForm().getForm().getFields();
			if (!Ext.isEmpty(fields)) {
				fields.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fields = null;
			verifyHasCoupon(me.getPrivilegeContractAddForm(), me.hasCoupon);
			var element = Ext.getCmp('discount_privilegeContract_addForm_hasCoupon_id');
			element.on('change', function(mx, value, eOpts) {
				me.hasCoupon = value;
				verifyHasCoupon(me.getPrivilegeContractAddForm(), value);
			});
		}
	},
	hasCoupon : 'N',
	privilegeContractAddForm : null,
	getPrivilegeContractAddForm : function() {
		if (Ext.isEmpty(this.privilegeContractAddForm)) {
			this.privilegeContractAddForm = Ext
					.create('Miser.discount.privilegeContract.AddForm');
		}
		return this.privilegeContractAddForm;
	},
	submitPrivilegeContractAddForm : function() {
		var me = this;
		if (me.getPrivilegeContractAddForm().getForm().isValid()) { // 校验form是否通过校验
			var element = Ext.getCmp('discount_privilegeContract_addForm_hasCoupon_id');
			element.setDisabled(false);
			var privilegeContractEntity = new Miser.model.privilegeContractEntity();
			// 将FORM中数据设置到MODEL里面
			me.getPrivilegeContractAddForm().getForm().updateRecord(privilegeContractEntity); 
			var contractData = privilegeContractEntity.data;
			var contractDetail = new Array();
			me.getPrivilegeContractDetailAddGrid().getStore().each(function(record) {
				contractDetail.push(record.data);
			});
			if(Ext.isEmpty(contractDetail)){
				miser.showErrorMes(discount.privilegeContract
						.i18n('miser.common.dataNotIntact'));
				return;
			}
			console.info(contractData);
			var params = {
				'privilegeContractVo' : {
					'privilegeContractEntity' : contractData
				},
				'privilegeContractDetailList' : Ext.encode(contractDetail)
			}
			var successFun = function(json) {
				var message = json.message;
				miser.showInfoMsg(message); // 提示新增成功
				me.close();
				me.parent.getStore().load(); // 成功之后重新查询刷新结果集
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(discount.privilegeContract
							.i18n('miser.discount.timeout')); // 请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message); // 提示失败原因
				}
			};
			miser.requestJsonAjax('privilegeContract!addPrivilegeContract.action',
					params, successFun, failureFun); // 发送AJAX请求
		}
	},
	privilegeContractDetailAddGrid : null,
	getPrivilegeContractDetailAddGrid : function() {
		if (Ext.isEmpty(this.privilegeContractDetailAddGrid)) {
			this.privilegeContractDetailAddGrid = Ext
					.create('Miser.discount.privilegeContractDetail.Grid', 
							{'operation' : 'ADD'});
			this.privilegeContractDetailAddGrid.parent = this; // 父元素
		}
		return this.privilegeContractDetailAddGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			text : discount.privilegeContract.i18n('miser.common.save'),// '保存',
			handler : function() {
				me.submitPrivilegeContractAddForm();
			}
		}, {
			text : discount.privilegeContract.i18n('miser.common.cancel'),// '取消',
			handler : function() {
				me.close();
			}
		}];
		me.items = [ me.getPrivilegeContractAddForm(),
				me.getPrivilegeContractDetailAddGrid() ];
		me.callParent([ cfg ]);
	}
});
/**
 * 越发越惠 客户合同 更新窗口
 */
Ext.define('Miser.discount.privilegeContract.UpdateWindow', {
	extend : 'Ext.window.Window',
	title : discount.privilegeContract.i18n('miser.common.update'),// 修改
	closable : true,
	parent : null, // 父元素
	modal : true,
	resizable : false, // 可以调整窗口的大小
	closeAction : 'hide', // 点击关闭是隐藏窗口
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	privilegeContractEntity: null,
	privilegeContractDetailEntityList: null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getPrivilegeContractUpdateForm().getForm().reset(); // 表格重置
			me.getPrivilegeContractDetailUpdateGrid().getStore().removeAll();
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fields = me.getPrivilegeContractUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fields)) {
				fields.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fields = null;
			me.getPrivilegeContractUpdateForm().getForm().loadRecord(
					new Miser.model.privilegeContractEntity(me.privilegeContractEntity));
			var customerCode = me.getPrivilegeContractUpdateForm().getForm().findField("customerCode");
			customerCode.setValue(me.privilegeContractEntity.customerCode);
			customerCode.getStore().load();
			if(!Ext.isEmpty(me.privilegeContractDetailEntityList)) {
				for (var i = 0; i < me.privilegeContractDetailEntityList.length; i++) {
					me.getPrivilegeContractDetailUpdateGrid().getStore().add(me.privilegeContractDetailEntityList[i]);
				}
			}
			me.hasCoupon = me.privilegeContractEntity.hasCoupon;
			verifyHasCoupon(me.getPrivilegeContractUpdateForm(), me.privilegeContractEntity.hasCoupon);
			var element = Ext.getCmp('discount_privilegeContract_updateForm_hasCoupon_id');
			element.setDisabled(true);
			element.on('change', function(mx, value, eOpts) {
				me.hasCoupon = value;
				verifyHasCoupon(me.getPrivilegeContractUpdateForm(), value);
			});
			element = Ext.getCmp('discount_privilegeContract_updateForm_contractEndTime_id');
			element.setDisabled(true);
			element = Ext.getCmp('discount_privilegeContract_updateForm_contractStartTime_id');
			element.setDisabled(true);
			element = Ext.getCmp('discount_privilegeContract_updateForm_customerCode_id');
			element.setDisabled(true);
		}
	},
	hasCoupon : null,
	privilegeContractUpdateForm : null,
	getPrivilegeContractUpdateForm : function() {
		if (Ext.isEmpty(this.privilegeContractUpdateForm)) {
			this.privilegeContractUpdateForm = Ext
					.create('Miser.discount.privilegeContract.UpdateForm');
		}
		return this.privilegeContractUpdateForm;
	},
	submitDriver : function() {
		var me = this;
		if (me.getPrivilegeContractUpdateForm().getForm().isValid()) { // 校验form是否通过校验
			var element = Ext.getCmp('discount_privilegeContract_updateForm_hasCoupon_id');
			element.setDisabled(false);
			element = Ext.getCmp('discount_privilegeContract_updateForm_contractEndTime_id');
			element.setDisabled(false);
			element = Ext.getCmp('discount_privilegeContract_updateForm_contractStartTime_id');
			element.setDisabled(false);
			element = Ext.getCmp('discount_privilegeContract_updateForm_customerCode_id');
			element.setDisabled(false);
			var privilegeContractEntity = new Miser.model.privilegeContractEntity();
			// 将FORM中数据设置到MODEL里面
			me.getPrivilegeContractUpdateForm().getForm().updateRecord(privilegeContractEntity);
			var contractData = privilegeContractEntity.data;
			var contractDetail = new Array();
			me.getPrivilegeContractDetailUpdateGrid().getStore().each(function(record) {
				contractDetail.push(record.data);
			});
			var params = {
				'privilegeContractVo' : {
					'privilegeContractEntity' : contractData
				},
				'privilegeContractDetailList' : Ext.encode(contractDetail)
			}
			var successFun = function(json) {
				var message = json.message;
				miser.showInfoMsg(message); // 提示修改成功
				me.close();
				me.parent.getStore().load(); // 成功之后重新查询刷新结果集
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(discount.privilegeContract.i18n('miser.common.timeout')); // 请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message); // 提示失败原因
				}
			};
			miser.requestJsonAjax('privilegeContract!updatePrivilegeContract.action',
					params, successFun, failureFun); // 发送AJAX请求
		}
	},
	privilegeContractDetailUpdateGrid : null,
	getPrivilegeContractDetailUpdateGrid : function() {
		if (Ext.isEmpty(this.privilegeContractDetailUpdateGrid)) {
			this.privilegeContractDetailUpdateGrid = Ext
					.create('Miser.discount.privilegeContractDetail.Grid', 
							{'operation' : 'UPDATE'});
			this.privilegeContractDetailUpdateGrid.parent = this; // 父元素
		}
		return this.privilegeContractDetailUpdateGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : discount.privilegeContract.i18n('miser.common.save'), // 保存
			margin : '0 0 0 305',
			handler : function() {
				me.submitDriver();
			}
		}, {
			text : discount.privilegeContract.i18n('miser.common.cancel'), // 取消
			handler : function() {
				me.close();
			}
		} ];
		me.items = [ me.getPrivilegeContractUpdateForm(),
				me.getPrivilegeContractDetailUpdateGrid() ];
		me.callParent([ cfg ]);
	}
});
/**
 * 越发越惠 客户合同 查询窗口
 */
Ext.define('Miser.discount.privilegeContract.QueryWindow', {
	extend : 'Ext.window.Window',
	title : discount.privilegeContract.i18n('discount.privilegeContract.contractDetail'),
	closable : true,
	parent : null, // 父元素
	modal : true,
	resizable : false, // 可以调整窗口的大小
	closeAction : 'hide', // 点击关闭是隐藏窗口
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	privilegeContractDetailEntityList: null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getPrivilegeContractDetailUpdateGrid().getStore().removeAll();
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			if(!Ext.isEmpty(me.privilegeContractDetailEntityList)) {
				for (var i = 0; i < me.privilegeContractDetailEntityList.length; i++) {
					me.getPrivilegeContractDetailUpdateGrid().getStore().add(me.privilegeContractDetailEntityList[i]);
				}
			}
		}
	},
	privilegeContractDetailUpdateGrid : null,
	getPrivilegeContractDetailUpdateGrid : function() {
		if (Ext.isEmpty(this.privilegeContractDetailUpdateGrid)) {
			this.privilegeContractDetailUpdateGrid = Ext
					.create('Miser.discount.privilegeContractDetail.Grid', 
							{'operation' : 'QUERY'});
			this.privilegeContractDetailUpdateGrid.parent = this; // 父元素
		}
		return this.privilegeContractDetailUpdateGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getPrivilegeContractDetailUpdateGrid()];
		me.callParent([ cfg ]);
	}
});
/**
 * 越发越惠 客户合同明细 编辑Window
 */
Ext.define('Miser.discount.privilegeContractDetail.EditWindow', {
	extend : 'Ext.window.Window',
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
			me.getPrivilegeContractDetailEditForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fields = me.getPrivilegeContractDetailEditForm().getForm().getFields();
			if (!Ext.isEmpty(fields)) {
				fields.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fields = null;
			if(me.privilegeContractDetailEntity != null && me.operation == 'UPDATE') {
				me.getPrivilegeContractDetailEditForm().getForm().loadRecord(
						new Miser.model.privilegeContractDetailEntity(
								me.privilegeContractDetailEntity));
			}
		}
	},
	privilegeContractDetailEntity : null,
	privilegeContractDetailEditForm : null,
	getPrivilegeContractDetailEditForm : function() {
		if (Ext.isEmpty(this.privilegeContractDetailEditForm)) {
			this.privilegeContractDetailEditForm = Ext
					.create('Miser.discount.privilegeContractDetail.EditForm');
			this.privilegeContractDetailEditForm.parent = this;
		}
		return this.privilegeContractDetailEditForm;
	},
	verifyFormData: function(editForm) {
		var startAmount = editForm.findField("startAmount").getValue();
		var endAmount = editForm.findField("endAmount").getValue();
		if(parseFloat(startAmount) >= parseFloat(endAmount)) {
			return false;
		}
		return true;
	},
	editCallback: null,
	operation : 'ADD', // ADD：新增，UPDATE：更新
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		if(!Ext.isEmpty(config) && !Ext.isEmpty(config.operation)) {
			me.operation = config.operation;			
		}
		if(me.operation == 'ADD') {
			me.title = discount.privilegeContract.i18n('miser.common.add');			
		} else {
			me.title = discount.privilegeContract.i18n('miser.common.update');
		}
		me.fbar = [{
			text : discount.privilegeContract
					.i18n('miser.common.save'),// '保存',
			handler : function() {
				var result = me.verifyFormData(me.getPrivilegeContractDetailEditForm().getForm());
				if(result) {
					me.editCallback();					
				} else {
					miser.showErrorMes(discount.privilegeContract
							.i18n('discount.privilegeContract.startAndEndAmount.rangeError'));
				}
			}
		}, {
			text : discount.privilegeContract
					.i18n('miser.common.reset'),// '重置',
			handler : function() {
				me.getPrivilegeContractDetailEditForm().reset();
			}
		}, {
			text : discount.privilegeContract
					.i18n('miser.common.cancel'),// '取消',
			handler : function() {
				me.close();
			}
		}];
		me.items = [me.getPrivilegeContractDetailEditForm()];
		me.callParent([cfg]);
	}
});


var mainPanel = null;
Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext.create('Miser.discount.privilegeContract.QueryForm'); // 查询FORM
	setORG(queryForm);
	var privilegeContractGrid = Ext.create('Miser.discount.privilegeContract.Grid', {'operation' : 'ADD'});
	mainPanel = Ext.create('Ext.panel.Panel', {
		renderTo : Ext.getBody(),
		getQueryForm : function() {
			queryForm.parent = this;
			return queryForm;
		},
		getPrivilegeContractGrid : function() {
			privilegeContractGrid.parent = this;
			return privilegeContractGrid;
		},
		items : [queryForm, privilegeContractGrid]
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
		privilegeContractGrid.setWidth(document.documentElement.clientWidth
				- 20);
	};
	
	
	
});