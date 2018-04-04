function strlen(str){
    var len = 0;
    for (var i=0; i<str.length; i++) { 
     var c = str.charCodeAt(i); 
    //单字节加1 
     if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
       len++; 
     } 
     else { 
      len+=2; 
     } 
    } 
    return len;
}

var failureFun = function (json) {
	if (Ext.isEmpty(json)) {
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.requestTimeOut')); //请求超时
	} else {
		var message = json.message;
		miser.showErrorMes(message);
	}
};


//当前状态
Ext.define('Miser.model.priceCustomer.baseCommonModel',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'name',
		type : 'string'
	},{
		name : 'value',
		type : 'string'
	}]
});
Ext.define('Miser.priceCustomer.statusStore', {
	extend : 'Ext.data.Store',
	model: 'Miser.model.priceCustomer.baseCommonModel',
    data : [
		          //生效中
		          {'name':pricecard.priceCustomer.i18n('pricecard.priceCustomer.statusEffective'),'value':'EFFECTIVE'},
		            
                  //已过期
                  {'name':pricecard.priceCustomer.i18n('pricecard.priceCustomer.statusPassed'),'value':'PASSED'},
                  
                   //待生效
                  {'name':pricecard.priceCustomer.i18n('pricecard.priceCustomer.statusWait'),'value':'WAIT'},
                  
                   //已废弃
                  {'name':pricecard.priceCustomer.i18n('pricecard.priceCustomer.statusDeleted'),'value':'DELETED'}
              ]
});
Ext.define('Miser.priceCustomer.statusSelector', {
	extend : 'Ext.form.ComboBox',
	alias : 'widget.priceCustomer_status_combselector',
	listWidth : this.width,// 下拉列表宽度，从外面传入
	multiSelect : false,// 从外面传入
	displayField : 'name',// 显示名称
	valueField : 'value',// 值
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config); 
		me.store = Ext.create('Miser.priceCustomer.statusStore');
		me.callParent([cfg]);
	}
});

// 状态
function statusTypeRender(value) {
	if (Ext.isEmpty(value)) {
		return value;
	}
	if ('PASSED' == value) {
		value = pricecard.priceCustomer.i18n('pricecard.priceCustomer.statusPassed');
	} else if ('EFFECTIVE' == value) {
		value = pricecard.priceCustomer.i18n('pricecard.priceCustomer.statusEffective');
	} else if ('WAIT' == value) {
		value = pricecard.priceCustomer.i18n('pricecard.priceCustomer.statusWait');
	} else if ('DELETED' == value) {
		value = pricecard.priceCustomer.i18n('pricecard.priceCustomer.statusDeleted');
	}
	return value;
}

/**
 * 客户 model
 */
Ext.define('Miser.model.bseCustomerEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	},{
		// 客户编号
		name : 'code',
		type : 'string'
	},{
		// 客户名称
		name : 'name',
		type : 'string'
	},{
		//客户所属公司编码
		name : 'corpCode',    
		type : 'string'
	},{
		//合同开始时间
		name : 'contractStartTime',    
		type : 'date',
		dateFormat : 'time'
	}, {    
		//合同结束时间
		name : 'contractEndTime',
		type : 'date',
		dateFormat : 'time'
	}, {
		//价格开始时间
		name : 'pcStartTime',    
		type : 'date',
		dateFormat : 'time'
	}, {
		//价格结束时间
		name : 'pcEndTime',
		type : 'date',
		dateFormat : 'time'
	},{
		//是否启用客户价格
		name : 'useCustomerPc',
		type : 'string'
	},{
		//是否启用客户折扣
		name:'useCustomerDiscount', 
		type:'string'
	},{
		// 备注
		name : 'remark',
		type : 'string'
	}, {
		// 是否有效
		name : 'active', 
		type : 'string'
	}, {
		// 最后修改人
		name : 'modifyUserCode',
		type : 'string'
	}, {
		// 最后修改时间
		name : 'modifyTime',
		type : 'date',
		dateFormat : 'time'
	}]
});

/**
 * 客户专属价格主表 对应数据表 t_price_customer_sub
 * */
Ext.define('Miser.model.priceCustomerEntity',{
	extend : 'Ext.data.Model',
	fields:[{
		name:'id',
		type:'string'
	},{
		//扩展 所属事业部编码
		name:'divisionCode',
		type:'string'
	},{
		//扩展 所属事业部名称
		name:'divisionName',
		type:'string'
	},{
		//扩展 所属大区编码
		name:'bigRegionCode',
		type:'string'
	},{
		//扩展 所属大区名称
		name:'bigRegionName',
		type:'string'
	},{
		//扩展 所属路区编码
		name:'roadAreaCode',
		type:'string'
	},{
		//扩展 所属路区名称
		name:'roadAreaName',
		type:'string'
	},{
		//扩展 所属门店编码
		name:'saleDepartCode',
		type:'string'
	},{
		//扩展 所属门店名称
		name:'saleDepartName',
		type:'string'
	},{
		//物流代码
		name:'logistCode',
		type:'string'
	},{
		//客户编码
		name:'customerCode',
		type:'string'
	},{
		//客户名称
		name:'customerName',
		type:'string'
	},{
		//生效时间
		name:'effectiveTime',
		type:'date',
		dateFormat : 'time'
	},{
		//失效时间
		name:'invalidTime',
		type:'date',
		dateFormat : 'time'
	},{
		//备注
		name:'remark',
		type:'string'
	},{
		//创建时间
		name:'createTime',
		type:'date',
		dateFormat : 'time'
	},{
		//创建人
		name:'createUserCode',
		type:'string'
	},{
		//扩展 创建人名称
		name:'createUserName',
		type:'string'
	},{
		//更新时间
		name:'modifyTime',
		type:'date',
		dateFormat : 'time'
	},{
		//更新人
		name:'modifyUserCode',
		type:'string'
	},{
		//扩展 修改人名称
		name:'modifyUserName',
		type:'string'
	},{
		//是否可用
		name:'active',
		type:'string'
	},{
		//状态
		name:'status',
		type:'string'
	},{
		//是否新客户
		name:'customerType',
		type:'string'
	}]
});

/**
 * 客户专属价格 model 对应数据表 t_price_customer_sub
 */
Ext.define('Miser.model.priceCustomerSubEntity', {
	extend : 'Ext.data.Model',
	fields:[{
		name:'id',
		type:'string'
	},{
		name:'parentId',//主表的主键
		type:'string'
	}
	,{
		name:'transTypeCode',//运输类型
		type:'string'
	}
	,{
		name:'transTypeName',//运输类型名称
		type:'string'
	},{
		name:'sendPriceCity',//出发价格城市 
		type:'string'
	},{
		name:'sendPriceCityName',//出发价格城市名字
		type:'string'
	},{
		name:'arrivePriceCity',//到达价格城市
		type:'string'
	},{
		name:'arrivePriceCityName',//到达价格城市名字
		type:'string'
	},{
		name:'weightPrice',//重量单价
		type:'string'
	},{
		name:'volumePrice',//体积单价 
		type:'string'
	},{
		name:'addFee',//附加费
		type:'string'
	},{
		name:'lowestFee',//最低收费
		type:'string'
	},{
		name:'fuelFeeSection',//燃油费分段编号
		type:'string'
	},{
		name:'fuelFeeSectionName',//燃油费分段名称
		type:'string'
	},{
		name : 'remark',// 备注
		type : 'string'
	},{
		name : 'active', // 是否有效
		type : 'string'
	},{
		name:'createUserCode', //创建人
		type:'string'
	},{
		name:'createUserName',//创建人名字
		type:'string'
	},{
		name : 'modifyUserCode',// 最后修改人
		type : 'string'
	},{
		name:'modifyUserName',//最后修改人名字
		type:'string'
	},{
		name : 'modifyTime',// 最后修改时间
		type : 'date',
		dateFormat : 'time'
	},{
		name : 'freightFeeSection',// 运费优惠分段
		type : 'string'
	},{
		name:'freightFeeSectionName',//运费优惠分段名称
		type:'string'
	},{
		name:'weightDiscount',//重货折扣
		type:'string'		
	},{
		name:'volumeDiscount',//轻货折扣
		type:'string'
	}]
});


/**
 * 客户专属价格首页查询结果 store
 */
Ext.define('Miser.store.priceCustomerStore',{
			extend : 'Ext.data.Store',
			model : 'Miser.model.priceCustomerEntity',
			pageSize : 20,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : '../pricecard/priceCustomer!listCustomerInfo.action',
				reader : {
					type : 'json',
					rootProperty : 'priceCustomerVo.priceCustomerEntityList',
					totalProperty : 'totalCount' // 总个数
				}
			},
			listeners : {
				beforeload : function(store, operation, eOpts) {
					var queryForm = Ext.getCmp('queryForm');
					if (queryForm != null) {
						
						var params = {
								'priceCustomerVo.priceCustomerEntity.divisionCode':queryForm.getForm().findField('divisionCode').getValue(),
								'priceCustomerVo.priceCustomerEntity.bigRegionCode':queryForm.getForm().findField('bigRegionCode').getValue(),
								'priceCustomerVo.priceCustomerEntity.roadAreaCode':queryForm.getForm().findField('roadAreaCode').getValue(),
								'priceCustomerVo.priceCustomerEntity.saleDepartCode':queryForm.getForm().findField('saleDepartCode').getValue(),
								'priceCustomerVo.priceCustomerEntity.customerCode':queryForm.getForm().findField('customerCode').getValue(),
								'priceCustomerVo.priceCustomerEntity.customerName':queryForm.getForm().findField('customerName').getValue(),
								'priceCustomerVo.priceCustomerEntity.status':queryForm.getForm().findField('status').getValue(),
								'priceCustomerVo.priceCustomerEntity.effectiveTime':queryForm.getForm().findField('effectiveTime').getValue()
						};
						
						Ext.apply(store.proxy.extraParams, params);
					}
				}
			}
		});


/**
 * 客户列表store
 */
Ext.define('Miser.store.bseCustomerStore',{
			extend : 'Ext.data.Store',
			model : 'Miser.model.bseCustomerEntity',
			pageSize : 20,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : 'priceCustomer!queryCustomerListByParam.action',
				reader : {
					type : 'json',
					rootProperty : 'bseCustomerVo.bseCustomerEntityList',
					totalProperty : 'totalCount' // 总个数
				}
			},
			listeners : {
				beforeload : function(store, operation, eOpts) {
					var queryForm = Ext.getCmp('queryForm');
					if (queryForm != null) {
						var params = {
							'bseCustomerVo.code' : queryForm.getForm().findField('code').getValue(),
							'bseCustomerVo.name' : queryForm.getForm().findField('name').getValue()
						};
						Ext.apply(store.proxy.extraParams, params);
					}
				}
			}
		});



Ext.define('Miser.store.priceCustomerSubStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.model.priceCustomerSubEntity',
	pageSize : 1000000,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : 'priceCustomer!queryCustomerSubListByParam.action',
		reader : {
			type : 'json',
			rootProperty : 'priceCustomerSubVo.priceCustomerSubEntityList',
			totalProperty : 'totalCount' // 总个数
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var parentId = operation.priceCustomerId;
			if(parentId != ''  ||  parentId != undefined)
			{
				var params = {
						'priceCustomerSubVo.priceCustomerSubEntity.parentId' :parentId
				};
				Ext.apply(store.proxy.extraParams, params);
			}
		}
	}
});
 
/**
 * 查询表单
 */
Ext.define(
			'Miser.view.bseCustomer.QueryForm',
			{
				extend : 'Ext.form.Panel',
				id : 'queryForm',
				frame : true,
				width : document.documentElement.clientWidth-10,
				title : pricecard.priceCustomer.i18n('pricecard.priceCustomer.queryCondition'),// '查询条件',
				collapsible : true,
				region : 'north',
				defaults : {
					labelWidth : 0.3,
					columnWidth : 0.6,
					margin : '8 10 5 10',
					labelAlign : 'right'
				},
				constructor : function(config) {
					var me = this, cfg = Ext.apply({}, config);
							me.items = [
									{
										layout : 'column',
										defaults : {
											labelWidth : 70,
											labelAlign : "left"
										},
										items : [{
							                name: 'linkorg',
								            labelWidth: 5,
								            flex:8,	
								            // 是否加载默认值（当前用户部门）
								            loaddefault:true,          
								            width:document.documentElement.clientWidth-10,
								            //height:40,
								            
								            //事业部
								            divisionLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.divisionCodeLabel')+':',
								            divisionName: 'divisionCode',
								            divisionIsBlank:true,
								            divisionLabelWidth:100,
								            divisionWidth:275,
								            margin:'5px 0px 5px 0px',
								            
								            // 大区
								            bigregionLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.bigRegionCodeLabel')+':',
								            bigregionName: 'bigRegionCode',
								            bigregionIsBlank:true,
								            bigregionLabelWidth:100,
								            bigregionWidth:275,
								            margin:'5px 0px 5px 0px',
								            
								            // 路区
								            roadareaLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.roadAreaCodeLabel')+':',
								            roadareaName: 'roadAreaCode',
								            roadareaIsBlank:true,
								            roadareaLabelWidth:100,
								            roadareaWidth:275,
								            margin:'5px 0px 5px 0px',
								            
								            
								            // 门店
								            salesdepartmentLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.saleDepartCodeLabel')+':',
								            salesdepartmentName: 'saleDepartCode',
								            salesdepartmentIsBlank:true,	       
								            salesdepartmentLabelWidth:100,
								            salesdepartmentWidth:275,
								            margin:'5px 0px 5px 0px',
								            
								            
								            type: 'D-B-R-S',								            
										    formid:'queryForm',
								            loadParentOrg:true,
							            	linkcustomerfield:'customerName',
								            xtype: 'linkorgcombselector'
										}]
									},
									{
										layout : 'column',
										defaults : {
											labelWidth : 70,
											labelAlign : "right"
										},
										defaults : {
											margins : '10 10 10 10'
										},
										items : [ {
											name : 'customerCode',
											fieldLabel :pricecard.priceCustomer.i18n('pricecard.priceCustomer.customerCodeLabel') ,//'客户编号'
											xtype : 'textfield',
											margin:'5px 0px 5px 0px',
											labelAlign:'right'
										},{
											name : 'customerName',
											fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.customerNameLabel'),//'客户名称'
											divisionCodeField:'divisionCode',
											bigRegionCodeField:'bigRegionCode',
											roadAreaCodeField:'roadAreaCode',
											orgCodeField : 'saleDepartCode',
											formid : 'queryForm',
											xtype : 'bsecustomercombselector',
											margin:'5px 0px 5px 0px',
											labelAlign:'right'
										},{
											name : 'status',
											fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.curStatusLabel'),//'当前状态'
											xtype : 'priceCustomer_status_combselector',
											margin:'5px 0px 5px 0px',
											labelAlign:'right'
										},{
							    			name: 'effectiveTime',
							    			fieldLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.effectiveTimeLabel'),//'有效时间点'
							    			xtype : 'datetimefield',
							    			format : 'Y-m-d H:i:s',
							    			margin:'5px 0px 5px 0px',
							    			labelAlign:'right'
										}]
									}
							],
							me.buttons = [
									{
										text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.query'),//查询
										handler : function() {
											
											var queryForm = Ext.getCmp('queryForm');
											
											//客户编号查询为精准查询，忽略区域查询限制 手动将其他涉及区域的字段设值为 null
											var customerCodeVal = queryForm.getForm().findField('customerCode').getValue();
											if( customerCodeVal != undefined && customerCodeVal != '')
											{
												queryForm.getForm().findField('divisionCode').setValue(null);
												queryForm.getForm().findField('bigRegionCode').setValue(null);
												queryForm.getForm().findField('roadAreaCode').setValue(null);
												queryForm.getForm().findField('saleDepartCode').setValue(null);
											}
											else
											{
												//因 customerName 使用的控件 默认的值为编码 这里需要手动获取其显示中文值  再设值到 value 中
												var customerNameVal = queryForm.getForm().findField('customerName').getRawValue();
												queryForm.getForm().findField('customerName').setValue(customerNameVal);
												
												if(customerNameVal != null && customerNameVal != '' ){
													
													//客户名称可以模糊查询，但至少必须与所属事业部组合查询，否则提示：请选择所属事业部后查询
													var divisionCodeVal = queryForm.getForm().findField('divisionCode').getValue();
													if(divisionCodeVal == null || divisionCodeVal == ''){
														miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.divisionCodeQryCtrl'));//请选择所属事业部后查询
														return;
													}
													
													//所有模糊查询需要要求输入4个字符以上才能查询，否则提示：模糊查询必须输入4个字符以上才能查询
													if( strlen(customerNameVal) < 4){
														miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.likeQryMoreFourChars'));//"模糊查询必须输入4个字符以上才能查询"
														return;
													}												
												}						
											}
											
											if (me.getForm().isValid()) {
												me.up().getBseCustomerGrid().getPagingToolbar().moveFirst();
												var gridObj = me.up().getBseCustomerGrid();
												var selModelObj = gridObj.selModel;
												selModelObj.deselectAll(true);												
											}
										}
									},
									{
										text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.reset'),//重置
										handler : function() {
											me.getForm().reset();
										}
									} ];
					me.callParent([ cfg ]);
				}
});


/**
 * 客户查询结果列表
 */
Ext.define(
				'Miser.view.priceCustomer.Grid',
				{
					extend : 'Ext.grid.Panel',
					id:"priceCustomerGrid",
					frame : true,
					autoScroll : true,
					height : miser.getBrowserHeight() - 120,
					stripeRows : true,
					selType : "rowmodel",
					emptyText : pricecard.priceCustomer.i18n('pricecard.priceCustomer.qryResultIsNull'), //'查询结果为空'
					columnLines : true,
					viewConfig : {
						enableTextSelection : true
					},
					priceCustomerAddWindow : null,
					getPriceCustomerAddWindow : function(cfg) {
						if (Ext.isEmpty(this.priceCustomerAddWindow)) {
							this.priceCustomerAddWindow = Ext.create('Miser.view.pricecard.priceCustomerAddWindow',cfg);
							this.priceCustomerAddWindow.parent = this; // 父元素
						}
						return this.priceCustomerAddWindow;
					},
					priceCustomerUpdateWindow:null,
					getPriceCustomerUpdateWindow:function(cfg){
						if (Ext.isEmpty(this.priceCustomerUpdateWindow)) {
							//this.priceCustomerUpdateWindow = Ext.create('Miser.view.pricecard.priceCustomerUpdateWindow',cfg);
							
							if(globalPriceCustomerUpdateWindow == null){
								globalPriceCustomerUpdateWindow = Ext.create('Miser.view.pricecard.priceCustomerUpdateWindow');
							}
							
							this.priceCustomerUpdateWindow = globalPriceCustomerUpdateWindow;
							this.priceCustomerUpdateWindow.parent = this; // 父元素
						}
						return this.priceCustomerUpdateWindow;
					},
					priceCustomerViewWindow:null,
					getPriceCustomerViewWindow:function(cfg){
						if (Ext.isEmpty(this.priceCustomerViewWindow)) {
							this.priceCustomerViewWindow = Ext.create('Miser.view.pricecard.priceCustomerViewWindow',cfg);
							this.priceCustomerViewWindow.parent = this; // 父元素
						}
						return this.priceCustomerViewWindow;
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
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.sequenceNo'),//'序号'
									width : 60,
									xtype : 'rownumberer',
									align : 'center'
								},
								{
									dataIndex : 'id',
									hidden : true
								},
								{
									//扩展 所属事业部编码
								   dataIndex:'divisionCode'	,
								   hidden:true
								},
								{
									 //扩展 所属事业部名称
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.divisionCodeLabel'),
									width : 100,
									dataIndex : 'divisionName',
									align : 'center'
								},
								{
									//扩展 所属大区编码
									dataIndex:'bigRegionCode',
									hidden:true
								},
								{
									//扩展 所属大区名称
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.bigRegionCodeLabel'),
									width : 150,
									dataIndex : 'bigRegionName',
									align : 'center'									
								},
								{
									//扩展 所属路区编码
									dataIndex:'roadAreaCode',
									hidden:true									
								},
								{
									//扩展 所属路区名称
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.roadAreaCodeLabel'),
									width : 100,
									dataIndex : 'roadAreaName',
									align : 'center'										
								},
								{
									//扩展 所属门店编码
									dataIndex:'saleDepartCode',
									hidden:true
								},
								{
									//扩展 所属门店名称
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.saleDepartCodeLabel'),
									width : 200,
									dataIndex : 'saleDepartName',
									align : 'center'									
								},
								{
									//物流代码
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.logistCode'),
									width : 100,
									dataIndex : 'logistCode',
									align : 'center'										
								},
								{
									dataIndex : 'customerCode',
									width : 100,
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.customerCodeLabel')
								},
								{
									dataIndex : 'customerName',
									width : 200,
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.customerNameLabel'),
									align : 'center'
								},
							    {
									//生效时间  
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.effectiveTimeLabel2'),
									dataIndex : 'effectiveTime',
									width : 150,
									align : 'center',
						        	renderer: function(value) {
						        		 return dateRender(value,'Y-m-d H:i:s');
						            }									
							    },
							    {
									//失效时间
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.invalidTimeLabel2'),
									dataIndex : 'invalidTime',
									width : 150,
									align : 'center',
						        	renderer: function(value) {
						        		 return dateRender(value,'Y-m-d H:i:s');
						            }								    	
							    },
							    {
									//扩展 状态编码
									dataIndex:'active',
									hidden:true
							    },
							    {
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.statusLabel'),//状态
									dataIndex : 'status',
									width : 100,
									align : 'center',
									renderer : function(value) {
										return statusTypeRender(value);
									}
							    },
								{
									text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.remarkLabel') ,//'备注'
									dataIndex : 'remark',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.createTimeLabel'),//'创建时间',
									dataIndex : 'createTime',
									width : 150,
									align : 'center',
						        	renderer: function(value) {
						        		 return dateRender(value,'Y-m-d H:i:s');
						            }										
								},
								{
									//扩展 创建人
									dataIndex:'createUserCode',
									hidden:true
								},
								{
									text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.createUserNameLabel'),// '创建人',
									dataIndex : 'createUserName',
									width : 140,
									align : 'center'
								},
								{
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.modifyUserNameLabel'),//'修改人',
									dataIndex : 'modifyUserName',
									width : 140,
									align : 'center'
								},
								{
									//扩展 创建人
									dataIndex:'modifyUserCode',
									hidden:true
								},
								{
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.modifyTimeLabel'),//'修改时间',
									dataIndex : 'modifyTime',
									width : 150,
						        	renderer: function(value) {
						        		 return dateRender(value,'Y-m-d H:i:s');
						            }
								}],
						me.store = Ext.create('Miser.store.priceCustomerStore', {
									autoLoad : false
						});
						me.selModel = Ext.create('Ext.selection.CheckboxModel',
								{ // 多选框
									mode : 'MULTI',
									checkOnly : true
								});
						me.tbar = [
								{
									id : 'miser_biz_pricecard_priceCustomer_AddBtn',
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.addBtn'),//'新增',
									xtype : 'addbutton',
									width : 80,
									handler : function() {
										
										var cfg = {};
				 
										// 获取选中的数据
										var selections = me.getSelectionModel().getSelection();
										if (selections.length == 1)
										{
											me.getPriceCustomerAddWindow(cfg).getPriceCustomerAddForm().id = 'priceCustomerSelectedAddForm';
											me.getPriceCustomerAddWindow(cfg).setPriceCustomerEntity(null);
											me.getPriceCustomerAddWindow(cfg).setPriceCustomerEntity(selections[0].data);
											
											var params = {
													'bseCustomerVo':{
														'code':selections[0].data.customerCode
													}
											};
											
											var successFun = function (json) {
												var bseCustomerEntityList = json.bseCustomerVo.bseCustomerEntityList;
												var bseCustomerEntity = bseCustomerEntityList[0];
												
												me.getPriceCustomerAddWindow(cfg).setBseCustomerEntity(null);
												me.getPriceCustomerAddWindow(cfg).setBseCustomerEntity(bseCustomerEntity);
												me.getPriceCustomerAddWindow(cfg).show();
											};
											
											miser.requestJsonAjax('../pricecard/bseCustomer!queryBseCustomer.action?limit=20', params, successFun, failureFun);
										}
										else  
										{
											//没有勾选记录时 需要从后台获取当前客户的基础信息
											var orgObj = getUserContext().getCurrentUserDept();
											if(!Ext.isEmpty(orgObj))
											{
												var isDivision = orgObj.isDivision;
												var isBigRegion = orgObj.isBigRegion;
												var isRoadArea = orgObj.isRoadArea;
												var isSales = orgObj.isSalesDepartment;		
												
												var addFormObj = me.getPriceCustomerAddWindow(cfg).getPriceCustomerAddForm().getForm();
												
												var divisionObj = addFormObj.findField('divisionCode');
												var bigRegionObj = addFormObj.findField('bigRegionCode');
												var roadArea = addFormObj.findField('roadAreaCode');
												var saleDepart = addFormObj.findField('saleDepartCode');
												
												
												if(isDivision == 'Y')
												{
													//所属事业部
													var divisionRecord=Ext.data.Record({
														  name :  orgObj.divisionName,
														  code :   orgObj.divisionCode
														}
													);
													divisionObj.getStore().add(divisionRecord);
													divisionObj.setValue(orgObj.divisionCode);
													divisionObj.disable();	
												}
												else if(isBigRegion == 'Y')
												{
													//所属事业部
													var divisionRecord=Ext.data.Record({
														  name :  orgObj.divisionName,
														  code :   orgObj.divisionCode
														}
													);
													divisionObj.getStore().add(divisionRecord);
													divisionObj.setValue(orgObj.divisionCode);
													divisionObj.disable();	
													
													//所属大区
													var bigRegionRecord=Ext.data.Record({
														name :  orgObj.bigRegionName,
														code : orgObj.bigRegionCode
														}
													);
													bigRegionObj.getStore().add(bigRegionRecord);
													bigRegionObj.setValue(orgObj.bigRegionCode);
													bigRegionObj.disable();														
													
												}
												else if(isRoadArea == 'Y')
												{
													//所属事业部
													var divisionRecord=Ext.data.Record({
														  name :  orgObj.divisionName,
														  code :   orgObj.divisionCode
														}
													);
													divisionObj.getStore().add(divisionRecord);
													divisionObj.setValue(orgObj.divisionCode);
													divisionObj.disable();	
													
													//所属大区
													var bigRegionRecord=Ext.data.Record({
														name :  orgObj.bigRegionName,
														code : orgObj.bigRegionCode
														}
													);
													bigRegionObj.getStore().add(bigRegionRecord);
													bigRegionObj.setValue(orgObj.bigRegionCode);
													bigRegionObj.disable();	
													
													//所属路区
													var roadAreaRecord=Ext.data.Record({
														name :  orgObj.name,
														code :  orgObj.code
														}
													);
													roadArea.getStore().add(roadAreaRecord);
													roadArea.setValue(orgObj.code);
													roadArea.disable();	
												}
												else if(isSales == 'Y')
												{
													//所属事业部
													var divisionRecord=Ext.data.Record({
														  name :  orgObj.divisionName,
														  code :   orgObj.divisionCode
														}
													);
													divisionObj.getStore().add(divisionRecord);
													divisionObj.setValue(orgObj.divisionCode);
													divisionObj.disable();	
													
													//所属大区
													var bigRegionRecord=Ext.data.Record({
														name :  orgObj.bigRegionName,
														code : orgObj.bigRegionCode
														}
													);
													bigRegionObj.getStore().add(bigRegionRecord);
													bigRegionObj.setValue(orgObj.bigRegionCode);
													bigRegionObj.disable();	
													
													//所属路区
													var roadAreaRecord=Ext.data.Record({
														name :  orgObj.parentName,
														code :  orgObj.parentCode
														}
													);
													roadArea.getStore().add(roadAreaRecord);
													roadArea.setValue(orgObj.parentCode);
													roadArea.disable();	
													
													//所属门店
													var saleDepartRecord=Ext.data.Record({
														name :  orgObj.name,
														code :  orgObj.code
														}
													);
													saleDepart.getStore().add(saleDepartRecord);
													saleDepart.setValue(orgObj.code);
													saleDepart.disable();
												}
												
												me.getPriceCustomerAddWindow(cfg).show();
											}
										}
									}
								},
								{
									id : 'miser_biz_pricecard_priceCustomer_UpdateBtn',
									text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.updateBtn'),//'修改',
									disabled : true,
									xtype : 'updatebutton',
									width : 80,
									handler : function() {
										
										var cfg = {};
										
										// 获取选中的数据
										var selections = me.getSelectionModel().getSelection();
										var id = selections[0].get('id');
										
										var params = {
												'priceCustomerVo':{
													'priceCustomerEntity':{
														'id':id
													}
												}
										};
										
										var successFun = function (json) {
											
											//后台返回的组织实体
											var priceCustomerEntity = json.priceCustomerVo.priceCustomerEntity;
											
											me.getPriceCustomerUpdateWindow(cfg).setPriceCustomerEntity(null);
											me.getPriceCustomerUpdateWindow(cfg).setPriceCustomerEntity(priceCustomerEntity);
											
											me.getPriceCustomerUpdateWindow(cfg).show();
										};
										
										miser.requestJsonAjax('../pricecard/priceCustomer!queryPriceCustomerAndSubList.action', params, successFun, failureFun);								
									}
								},
								{
									id : 'miser_biz_pricecard_priceCustomer_DelBtn',
									text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.deleteBtn'),// '作废',
									xtype : 'deletebutton',
									disabled : true,
									width : 80,
									handler : function() {
										
										//获取当前 选中的记录
										var selections = me.getSelectionModel().getSelection();
										var destoryIdStr = '';
										
										//当前允许存在的状态 只有 只有待生效和已生效的数据 两种状态 但操作时只能对同一种状态的数据进行操作
										var firstRecordStatus = null;
										
										//是否存在不同状态的记录 默认为 不存在 false
										var isExistDiffStatus = false;
										
										//是否存在  待生效 已生效 状态以外的数据
										var isExistInvalidStatus = false;
										
										for(var i=0;i<selections.length;i++){
											var curId = selections[i].get('id');
											var curStatus = null;
											if(i==0){
												firstRecordStatus = selections[i].get('status');
												
												//第一条记录时  以满足下面的 if() 判断
												curStatus = firstRecordStatus;
											}
											else{
												curStatus = selections[i].get('status');												
											}
											
											if( !(curStatus == 'WAIT' || curStatus == 'EFFECTIVE') ){
												isExistInvalidStatus = true;
												break;
											}
							 
											if( firstRecordStatus != null &&  firstRecordStatus != curStatus){
												isExistDiffStatus = true;
												break;
											}
											
											destoryIdStr += "'"+curId+"',";
										}
										
										if(isExistInvalidStatus){
											miser.showErrorMes('只有待生效和已生效的数据才允许修改');
											return;
										}
										
										//存在不同状态的记录
										if(isExistDiffStatus){
											//'请选择同种状态的数据进行该项操作'
							    			miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.selectDiffStatusRecordToOper'));
							    			return;
										}
										else{
													if(destoryIdStr != '')
													{
														miser.showQuestionMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.destoryConfirmTipMsg'),
														                     function(e) {
																				if (e == 'yes') {
		
																					destoryIdStr = destoryIdStr.substring(0,destoryIdStr.length - 1);
																					
																					var params = {
																							'priceCustomerVo':{
																								'destoryIdStr':destoryIdStr	
																							}
																					};
																					
																					var successFun = function (json) {
																						
																						var msg = json.message;
																						if(msg == "miser_priceCustomer_StatusChange"){
																							
																							//'状态已发生变化，请刷新后重新操作'
																							miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.statusHasChanged'));
																						}
																						else if(msg == "miser_priceCustomer_destorySuccess"){
																							
																							//提示消息 "作废成功"
																							miser.showInfoMsg(pricecard.priceCustomer.i18n('pricecard.priceCustomer.destroySuccess'));
																							
																							//重新刷新页面
																							me.store.reload();
																						}
																					};
																					
																					miser.requestJsonAjax('../pricecard/priceCustomer!destoryPriceCustomerAndSubList.action', params, successFun, failureFun);		
																				}
																		});
													}
											   }
										}
								},
								{
									id : 'miser_biz_pricecard_priceCustomer_ViewBtn',
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.viewBtnLabel'),//'查看详情',
									xtype : 'searchbutton',
									disabled : true,
									width : 100,
									handler : function() {

									    var cfg = {};
										
										// 获取选中的数据
										var selections = me.getSelectionModel().getSelection();
										var id = selections[0].get('id');
										
										var params = {
												'priceCustomerVo':{
													'priceCustomerEntity':{
														'id':id
													}
												}
										};
										
										var successFun = function (json) {
											
											//后台返回的组织实体
											var priceCustomerEntity = json.priceCustomerVo.priceCustomerEntity;
											me.getPriceCustomerViewWindow(cfg).setPriceCustomerEntity(null);
											me.getPriceCustomerViewWindow(cfg).setPriceCustomerEntity(priceCustomerEntity);
											
											me.getPriceCustomerViewWindow(cfg).show();
										};
										
										miser.requestJsonAjax('../pricecard/priceCustomer!queryPriceCustomerAndSubList.action', params, successFun, failureFun);										
									}
								},{
									text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.exprortResultList'),//'导出结果列表',
									handler : function() {
										Ext.Msg.wait('处理中，请稍后...', '提示');
										//导出
										var queryForm = Ext.getCmp('queryForm');
										
										//客户编号查询为精准查询，忽略区域查询限制 手动将其他涉及区域的字段设值为 null
										var customerCodeVal = queryForm.getForm().findField('customerCode').getValue();
										if( customerCodeVal != undefined && customerCodeVal != '')
										{
											queryForm.getForm().findField('divisionCode').setValue(null);
											queryForm.getForm().findField('bigRegionCode').setValue(null);
											queryForm.getForm().findField('roadAreaCode').setValue(null);
											queryForm.getForm().findField('saleDepartCode').setValue(null);
										}
										else
										{
											//因 customerName 使用的控件 默认的值为编码 这里需要手动获取其显示中文值  再设值到 value 中
											var customerNameVal = queryForm.getForm().findField('customerName').getRawValue();
											queryForm.getForm().findField('customerName').setValue(customerNameVal);
											
											if(customerNameVal != null && customerNameVal != '' ){
												
												//客户名称可以模糊查询，但至少必须与所属事业部组合查询，否则提示：请选择所属事业部后查询
												var divisionCodeVal = queryForm.getForm().findField('divisionCode').getValue();
												if(divisionCodeVal == null || divisionCodeVal == ''){
													miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.divisionCodeQryCtrl'));//请选择所属事业部后查询
													return;
												}
												
												//所有模糊查询需要要求输入4个字符以上才能查询，否则提示：模糊查询必须输入4个字符以上才能查询
												if( strlen(customerNameVal) < 4){
													miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.likeQryMoreFourChars'));//"模糊查询必须输入4个字符以上才能查询"
													return;
												}												
											}
											
											//状态
											var statusVal = queryForm.getForm().findField('status').getValue();
											
											//有效时间点
											var effectiveTimeVal = queryForm.getForm().findField('effectiveTime').getValue();
										}
										
										var queryForm = Ext.getCmp('queryForm');
										if (queryForm != null) {
											
											var params = {
													'priceCustomerVo.priceCustomerEntity.divisionCode':queryForm.getForm().findField('divisionCode').getValue(),
													'priceCustomerVo.priceCustomerEntity.bigRegionCode':queryForm.getForm().findField('bigRegionCode').getValue(),
													'priceCustomerVo.priceCustomerEntity.roadAreaCode':queryForm.getForm().findField('roadAreaCode').getValue(),
													'priceCustomerVo.priceCustomerEntity.saleDepartCode':queryForm.getForm().findField('saleDepartCode').getValue(),
													'priceCustomerVo.priceCustomerEntity.customerCode':queryForm.getForm().findField('customerCode').getValue(),
													'priceCustomerVo.priceCustomerEntity.customerName':queryForm.getForm().findField('customerName').getValue(),
													'priceCustomerVo.priceCustomerEntity.status':queryForm.getForm().findField('status').getValue(),
													'priceCustomerVo.priceCustomerEntity.effectiveTime':queryForm.getForm().findField('effectiveTime').getValue()
											};
										}
										
										Ext.Ajax.request( {  
										       url : '../pricecard/priceCustomer!excelExportCustomer.action',  
										       method : 'post',
										       timeout:180000,
										       params : params,  
										          success : function(response, options) {  
										              // 隐藏进度条
										              Ext.Msg.hide();   
										              var responseArray = Ext.util.JSON.decode(response.responseText); 
										              // addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
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
								}];
						me.bbar = me.getPagingToolbar();
								me.selModel = Ext.create(
												'Ext.selection.CheckboxModel',
												{
													listeners : {
														selectionchange : function(sm, selections) {
															
															//控制 修改按钮  查看详情按钮 只能对单条记录
															if(selections.length == 1)
															{
																//“已失效” “已废弃”的数据不能修改  
																//modify by dengyin 2016-4-15 9:59:16  已过期的数据仍允许修改 便于复制新增
																//if(selections[0].get('status') == 'PASSED' || selections[0].get('status') == 'DELETED')
																if(selections[0].get('status') == 'DELETED')
																{
																	Ext.getCmp('miser_biz_pricecard_priceCustomer_UpdateBtn').setDisabled(true);
																}
																else
																{
																	Ext.getCmp('miser_biz_pricecard_priceCustomer_UpdateBtn').setDisabled(false);
																}
																
																Ext.getCmp('miser_biz_pricecard_priceCustomer_ViewBtn').setDisabled(false);
															}
															else
															{
																Ext.getCmp('miser_biz_pricecard_priceCustomer_UpdateBtn').setDisabled(true);
																Ext.getCmp('miser_biz_pricecard_priceCustomer_ViewBtn').setDisabled(true);
															}
															
															
															//作废按钮 
															//只有待生效(WAIT)和已生效(EFFECTIVE)的数据，在客户专属价格管理界面选中某客户或在客户详情中，作废按钮才能可用，否则灰色禁用
															var isToDisableDelBtn = true;
															for(var i=0;i<selections.length;i++)
															{
																var curStatus = selections[i].get('status');
																if(curStatus == 'WAIT' || curStatus == 'EFFECTIVE' )
																{
																	isToDisableDelBtn = false;
																}
																else
																{
																	//只要有一条数据 不为 待生效 已生效 作废按钮都不能使用
																	isToDisableDelBtn = true;
																	break;
																}
															}
															
															Ext.getCmp('miser_biz_pricecard_priceCustomer_DelBtn').setDisabled(isToDisableDelBtn);
														}
													}
												}),
								me.callParent([ cfg ]);
					},
					listeners:{
						itemdblclick:function(view,model,item,index,e,eOpts){
							var me = this;
							var id = model.data.id;
						    var cfg = {};
							var params = {
									'priceCustomerVo':{
										'priceCustomerEntity':{
											'id':id
										}
									}
							};
							
							var successFun = function (json) {
								//后台返回的组织实体
								var priceCustomerEntity = json.priceCustomerVo.priceCustomerEntity;
								me.getPriceCustomerViewWindow(cfg).setPriceCustomerEntity(null);
								me.getPriceCustomerViewWindow(cfg).setPriceCustomerEntity(priceCustomerEntity);
								
								me.getPriceCustomerViewWindow(cfg).show();
							};
							miser.requestJsonAjax('../pricecard/priceCustomer!queryPriceCustomerAndSubList.action', params, successFun, failureFun);		
						}
					}
				});

/**
 * 增加表单
 */
Ext.define('Miser.view.pricecard.priceCustomerAddForm',{
					extend : 'Ext.form.Panel',
					id:'priceCustomerAddForm',
					frame : true,
					region : 'north',
					autoDestroy:true,
					defaults : {
						labelWidth : 0.4,
						columnWidth : 0.6,
						margin : '8 10 5 10',
						labelAlign : 'right'
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						
						me.items = [
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
										name : 'id',
										fieldLabel : 'id',
										xtype : 'textfield',
										hidden : true
									 },
									 {
										    name: 'linkorg',
										    labelWidth: 5,
	
								            flex:8,	
								            // 是否加载默认值（当前用户部门）
								            loaddefault:false,          
								            width:1100,  
								            
								            //事业部
								            divisionLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.divisionCodeLabel')+':',
								            divisionName: 'divisionCode',
								            divisionIsBlank:true,
								            divisionLabelWidth:100,
								            divisionWidth:275,
								            
								            // 大区
								            bigregionLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.bigRegionCodeLabel')+':',
								            bigregionName: 'bigRegionCode',
								            bigregionIsBlank:true,
								            bigregionLabelWidth:100,
								            bigregionWidth:275,
								            
								            // 路区
								            roadareaLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.roadAreaCodeLabel')+':',
								            roadareaName: 'roadAreaCode',
								            roadareaIsBlank:true,
								            roadareaLabelWidth:100,
								            roadareaWidth:275,
								            
								            
								            // 门店
								            salesdepartmentLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.saleDepartCodeLabel')+':',
								            salesdepartmentName: 'saleDepartCode',
								            salesdepartmentIsBlank:true,	       
								            salesdepartmentLabelWidth:100,
								            salesdepartmentWidth:275,
										    
										    
										    type: 'D-B-R-S',
										    formid:'priceCustomerAddForm',
							            	linkcustomerfield:'customerName',
							            	linkcustomercodefield:'customerCode',
										    xtype: 'linkorgcombselector'
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
									items : [ {
										name : 'customerCode',
										fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.customerCodeLabel'),//'客户编号',
										divisionCodeField:'divisionCode',
										bigRegionCodeField:'bigRegionCode',
										roadAreaCodeField:'roadAreaCode',
										orgCodeField : 'saleDepartCode',										
										nameField:'customerName',
										formid:'priceCustomerAddForm',										
										xtype : 'bsecustomercodecombselector',										
										allowBlank:false,
										labelAlign:'right'
									},{
										name : 'customerName',
										fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.customerNameLabel'),//'客户名称',
										divisionCodeField:'divisionCode',
										bigRegionCodeField:'bigRegionCode',
										roadAreaCodeField:'roadAreaCode',
										orgCodeField : 'saleDepartCode',
										codeField:'customerCode',
										formid : 'priceCustomerAddForm',
										xtype : 'bsecustomercombselector',
										allowBlank:false,
										labelAlign:'right'
									},{
						    			name: 'effectiveTime',
						    			fieldLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.effectiveTimeLabel'),//'有效时间',
						    			xtype : 'datetimefield',
						    			format : 'Y-m-d H:i:s',
						    			allowBlank:false,
						    			labelAlign:'right'
									},{
						    			name: 'invalidTime',
						    			fieldLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.invalidTimeLabel'),//'失效时间',
						    			xtype : 'datetimefield',
						    			format : 'Y-m-d H:i:s',
						    			allowBlank:false,
						    			labelAlign:'right'
									} ]
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
									items:[
										 {
											 fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.isNewCustomerLabel'),//是否使用新价格城市
											 name:'customerType',
											 xtype: 'yesnocombselector',
											 isShowAll : false,
											 labelAlign:'right',
											 allowBlank:false
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
										fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.remarkLabel'),//'备注',
										name : 'remark',
										width : document.documentElement.clientWidth - 25,
										xtype : 'textarea',
										labelAlign:'right'
									} ]
								}];
						me.callParent([ cfg ]);
					}
				});

//增加窗体
Ext.define('Miser.view.pricecard.priceCustomerAddWindow', {
	extend : 'Ext.window.Window',
	title : pricecard.priceCustomer.i18n('pricecard.priceCustomer.addCustomerWinTitle'),//'新增客户专属价格',
	closable : true,
	parent : null,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	autoDestroy:true,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getPriceCustomerAddForm().getForm().reset(); // 表格重置
			me.getPriceCustomerSubAddGrid().store.removeAll();
			me.priceCustomerEntity = null;
			me.bseCustomerEntity = null;
			
			me.getPriceCustomerSubAddGrid().isEdit = true;
			
			me.getPriceCustomerAddForm().getForm().findField('divisionCode').store.load();
			me.getPriceCustomerAddForm().getForm().findField('bigRegionCode').store.load();
			me.getPriceCustomerAddForm().getForm().findField('roadAreaCode').store.load();
			me.getPriceCustomerAddForm().getForm().findField('saleDepartCode').store.load();
			
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getPriceCustomerAddForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			
			if(me.bseCustomerEntity != null)
			{
				
				//所属事业部
				var divisionObj = me.getPriceCustomerAddForm().getForm().findField('divisionCode');
				var divisionRecord=Ext.data.Record({
					  name :  me.bseCustomerEntity.divisionName,
					  code :  me.bseCustomerEntity.divisionCode
					}
				);
				divisionObj.getStore().add(divisionRecord);
				divisionObj.setValue(me.bseCustomerEntity.divisionCode);
	 
				
				//所属大区
				var bigRegionObj = me.getPriceCustomerAddForm().getForm().findField('bigRegionCode');
				var bigRegionRecord=Ext.data.Record({
					name :  me.bseCustomerEntity.bigRegionName,
					code :  me.bseCustomerEntity.bigRegionCode
					}
				);
				bigRegionObj.getStore().add(bigRegionRecord);
				bigRegionObj.setValue(me.bseCustomerEntity.bigRegionCode);
				
				
				//所属路区
				var roadArea = me.getPriceCustomerAddForm().getForm().findField('roadAreaCode');
				var roadAreaRecord=Ext.data.Record({
					name :  me.bseCustomerEntity.roadAreaName,
					code :  me.bseCustomerEntity.roadAreaCode
					}
				);
				roadArea.getStore().add(roadAreaRecord);
				roadArea.setValue(me.bseCustomerEntity.roadAreaCode);
				
				
				//所属门店
				var saleDepart = me.getPriceCustomerAddForm().getForm().findField('saleDepartCode');
				var saleDepartRecord=Ext.data.Record({
					name :  me.bseCustomerEntity.orgName,
					code :  me.bseCustomerEntity.orgCode
					}
				);
				saleDepart.getStore().add(saleDepartRecord);
				saleDepart.setValue(me.bseCustomerEntity.orgCode);
				
				//客户编号
				var customerCodeObj = me.getPriceCustomerAddForm().getForm().findField('customerCode');
				customerCodeObj.setValue(me.bseCustomerEntity.code);
				
				//客户名称
				var customerNameObj = me.getPriceCustomerAddForm().getForm().findField('customerName');
				var customerNameRecord=Ext.data.Record({
					name :  me.bseCustomerEntity.name,
					code :  me.bseCustomerEntity.code
					}
				);
				customerNameObj.getStore().add(customerNameRecord);
				customerNameObj.setValue(me.bseCustomerEntity.code);
			}
			
			//新增时 默认为 是
			var customerTypeObj = me.getPriceCustomerAddForm().getForm().findField('customerType');
			customerTypeObj.setValue('Y');
			customerTypeObj.setReadOnly(false);
		}
	},
	priceCustomerAddForm : null,
	getPriceCustomerAddForm : function() {
		if (Ext.isEmpty(this.priceCustomerAddForm)) {
			this.priceCustomerAddForm = Ext.create('Miser.view.pricecard.priceCustomerAddForm');
		}
		return this.priceCustomerAddForm;
	},
	priceCustomerSubAddGrid : null,
	getPriceCustomerSubAddGrid : function() {
		if (Ext.isEmpty(this.priceCustomerSubAddGrid)) {
			this.priceCustomerSubAddGrid = Ext.create('Miser.view.priceCustomerSub.AddGrid');
			this.priceCustomerSubAddGrid.parent = this; // 父元素
		}
		return this.priceCustomerSubAddGrid;
	},
	priceCustomerEntity:null,
	setPriceCustomerEntity:function(priceCustomerEntity){
		this.priceCustomerEntity = priceCustomerEntity;
	},
	getPriceCustomerEntity:function(priceCustomerEntity){
		return this.priceCustomerEntity;
	},
	bseCustomerEntity:null,
	setBseCustomerEntity:function(bseCustomerEntity){
		this.bseCustomerEntity = bseCustomerEntity;
	},
	getBseCustomerEntity:function(){
		return this.bseCustomerEntity;
	},
	submitPriceCustomerAddForm : function() {
		var me = this;
		
		if (me.getPriceCustomerAddForm().getForm().isValid()) { // 校验form是否通过校验
			
			Ext.Msg.wait('处理中，请稍后...', '提示');
			
			var priceCustomerEntity = new Miser.model.priceCustomerEntity();
			me.getPriceCustomerAddForm().getForm().updateRecord(priceCustomerEntity); // 将FORM中数据设置到MODEL里面
			
			var curData = priceCustomerEntity.data;
			curData.customerName =  me.getPriceCustomerAddForm().getForm().findField('customerName').rawValue;
			
			if(curData.invalidTime <= curData.effectiveTime)
			{
				//'失效时间 必须大于 生效时间'
				miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.invalidTimeThanEffectiveTime'));
				return;
			}
			
			//因为 Ext 创建 Entity 时会动态设置 id
			curData.id = '';
			curData.active = 'Y';
			
			var priceCustomerSubEntityItems = this.getPriceCustomerSubAddGrid().store.data.items;
			
			var priceCustomerSubEntityList = priceCustomerSubDetailCheck(priceCustomerSubEntityItems);
			
			if(priceCustomerSubEntityList == null){
				return;
			}
 
			var params = {
					'priceCustomerVo' : {
						'priceCustomerEntity' : curData,
						'priceCustomerSubEntityList' : priceCustomerSubEntityList
					}
				};
			
			var successFun = function(json) {
				Ext.Msg.hide();
				
				var message = json.message;
				
				// 提示新增成功
				miser.showInfoMsg(message); 
				me.close();
				
				//每次提交后 需要将当前表单中的 store 清空 不然 提交后 再次点击 新增 仍然会展示上次动态增加的行记录
				me.getPriceCustomerSubAddGrid().store.removeAll();
				
				// 成功之后重新查询刷新结果集
				me.parent.getStore().load();
				
				var priceCustomerGridObj = Ext.getCmp('priceCustomerGrid');
				var selModelObj = priceCustomerGridObj.selModel;
				selModelObj.deselectAll(true);
				
			};
			var failureFun = function(json) {
				Ext.Msg.hide();
				
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.requestTimeOut'));
				} else {
					var message = json.message;
					showPriceCustomerTipMsg(message);
				}
			};
			miser.requestJsonAjax('priceCustomer!addPriceCustomer.action',params, successFun, failureFun); // 发送AJAX请求
		}
		else {
			miser.showErrorMes('表单校验不通过,请检查是否填写完整');
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.saveBtn'),//'保存',
			handler : function() {
				me.submitPriceCustomerAddForm();
			}
		},{
			text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.cancelBtn'),//'取消',
			handler : function() {
				me.close();
			}
		}];
		me.items = [ me.getPriceCustomerAddForm(),me.getPriceCustomerSubAddGrid()];
		me.callParent([ cfg ]);
	}
});

/**
 * 修改表单
 */
Ext.define('Miser.view.pricecard.priceCustomerUpdateForm',{
					extend : 'Ext.form.Panel',
					id:'priceCustomerUpdateForm',
					frame : true,
					autoDestroy:true,
					region : 'north',
					defaults : {
						labelWidth : 0.4,
						columnWidth : 0.6,
						margin : '8 10 5 10',
						labelAlign : 'right'
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						
						me.items = [
									{
										layout : 'hbox',
										defaults : {
											labelWidth : 70,
											labelAlign : "right"
										},
										defaults : {
											margins : '10 10 5 5'
										},
										items : [  {
											name : 'id',
											fieldLabel : 'id',
											xtype : 'textfield',
											hidden : true
										 },
										 {
											 name: 'linkorg',
											 labelWidth: 5,

											 flex:8,	
											 // 是否加载默认值（当前用户部门）
											 loaddefault:false,          
											 width:1100,  

											 //事业部
											 divisionLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.divisionCodeLabel')+':',
											 divisionName: 'divisionCode',
											 divisionIsBlank:true,
											 divisionLabelWidth:100,
											 divisionWidth:275,

											 // 大区
											 bigregionLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.bigRegionCodeLabel')+':',
											 bigregionName: 'bigRegionCode',
											 bigregionIsBlank:true,
											 bigregionLabelWidth:100,
											 bigregionWidth:275,

											 // 路区
											 roadareaLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.roadAreaCodeLabel')+':',
											 roadareaName: 'roadAreaCode',
											 roadareaIsBlank:true,
											 roadareaLabelWidth:100,
											 roadareaWidth:275,


											 // 门店
											 salesdepartmentLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.saleDepartCodeLabel')+':',
											 salesdepartmentName: 'saleDepartCode',
											 salesdepartmentIsBlank:true,	       
											 salesdepartmentLabelWidth:100,
											 salesdepartmentWidth:275,

											 type: 'D-B-R-S',
											 formid:'priceCustomerAddForm',
											 linkcustomerfield:'customerName',
											 xtype: 'linkorgcombselector'
											} ]
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
									items : [ {
										name : 'customerCode',
										fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.customerCodeLabel'),//'客户编号',
										xtype : 'textfield',
										labelAlign:'right'
									},{
										name : 'customerName',
										fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.customerNameLabel'),//'客户名称',
										xtype : 'textfield',
										labelAlign:'right'
									},{
						    			name: 'effectiveTime',
						    			fieldLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.effectiveTimeLabel'),//'有效时间',
						    			xtype : 'datetimefield',
						    			format : 'Y-m-d H:i:s',
						    			labelAlign:'right'
									},{
						    			name: 'invalidTime',
						    			fieldLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.invalidTimeLabel'),//'失效时间',
						    			xtype : 'datetimefield',
						    			format : 'Y-m-d H:i:s',
						    			labelAlign:'right'
									} ]
								},
								{
									layout : 'hbox',
									defaults : {
										labelWidth : 70,
										labelAlign : "right"
									},
									defaults:{
										margins : '10 10 5 5'
									},
									items:[
										{
										    fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.isNewCustomerLabel'),//是否新客户
										    name:'customerType',
										    xtype: 'yesnocombselector',
										    isShowAll : false,
										    labelAlign:'right',
										    allowBlank:false
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
									items : [{
										fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.remarkLabel'),//'备注',
										name : 'remark',
										width : document.documentElement.clientWidth - 25,
										xtype : 'textarea',
										labelAlign:'right'
									} ]
								}];
						me.callParent([ cfg ]);
					}
				});

//修改窗体
Ext.define('Miser.view.pricecard.priceCustomerUpdateWindow', {
	extend : 'Ext.window.Window',
	title : pricecard.priceCustomer.i18n('pricecard.priceCustomer.modifyCustomerWinTitle'),//'修改客户价格',
	closable : true,
	parent : null,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	autoDestroy:true,
	standardImple:false,//针对于导入新价格城市标识 界面初始化时默认为 false 点击 导入新价格城市设置为 true 点击导入时设置为 false
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getPriceCustomerUpdateForm().getForm().reset(); // 表格重置
			me.getPriceCustomerSubAddGrid().store.removeAll();
			me.getPriceCustomerSubAddGrid().isEdit = true;
			
			
			me.priceCustomerEntity = null;
			me.priceCustomerSubEntityList = null;
			
			me.getPriceCustomerUpdateForm().getForm().findField('divisionCode').store.load();
			me.getPriceCustomerUpdateForm().getForm().findField('bigRegionCode').store.load();
			me.getPriceCustomerUpdateForm().getForm().findField('roadAreaCode').store.load();
			me.getPriceCustomerUpdateForm().getForm().findField('saleDepartCode').store.load();
			
			Ext.getCmp('searchArrivePriceCityForUpdate').setValue(null);
			
			//重新设置为初始值
			me.standardImple = false;
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getPriceCustomerUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			
			
			//初始化到 界面组件中
			//为组件填充预先需要填充的值
			if(me.priceCustomerEntity != null)
			{
				//设置隐藏字段 id 用于 grid 查询
				var priceCustomerIdObj = me.getPriceCustomerUpdateForm().getForm().findField('id');
				priceCustomerIdObj.setValue(me.priceCustomerEntity.id);
				
				//所属事业部
				var divisionObj = me.getPriceCustomerUpdateForm().getForm().findField('divisionCode');
				var divisionRecord=Ext.data.Record({
					  name :  me.priceCustomerEntity.divisionName,
					  code :  me.priceCustomerEntity.divisionCode
					}
				);
				divisionObj.getStore().add(divisionRecord);
				divisionObj.setValue(me.priceCustomerEntity.divisionCode);
				divisionObj.setReadOnly(true);
				
				//所属大区
				var bigRegionObj = me.getPriceCustomerUpdateForm().getForm().findField('bigRegionCode');
				var bigRegionRecord=Ext.data.Record({
					name :  me.priceCustomerEntity.bigRegionName,
					code :  me.priceCustomerEntity.bigRegionCode
					}
				);
				bigRegionObj.getStore().add(bigRegionRecord);
				bigRegionObj.setValue(me.priceCustomerEntity.bigRegionCode);
				bigRegionObj.setReadOnly(true);
				
				
				//所属路区
				var roadArea = me.getPriceCustomerUpdateForm().getForm().findField('roadAreaCode');
				var roadAreaRecord=Ext.data.Record({
					name :  me.priceCustomerEntity.roadAreaName,
					code :  me.priceCustomerEntity.roadAreaCode
					}
				);
				roadArea.getStore().add(roadAreaRecord);
				roadArea.setValue(me.priceCustomerEntity.roadAreaCode);
				roadArea.setReadOnly(true);
				
				
				//所属门店
				var saleDepart = me.getPriceCustomerUpdateForm().getForm().findField('saleDepartCode');
				var saleDepartRecord=Ext.data.Record({
					name :  me.priceCustomerEntity.saleDepartName,
					code :  me.priceCustomerEntity.saleDepartCode
					}
				);
				saleDepart.getStore().add(saleDepartRecord);
				saleDepart.setValue(me.priceCustomerEntity.saleDepartCode);
				saleDepart.setReadOnly(true);
				
				
				//客户编号
				var customerCodeObj = me.getPriceCustomerUpdateForm().getForm().findField('customerCode');
				customerCodeObj.setValue(me.priceCustomerEntity.customerCode);
				customerCodeObj.setReadOnly(true);
				
				//客户名称
				var customerNameObj = me.getPriceCustomerUpdateForm().getForm().findField('customerName');
				customerNameObj.setValue(me.priceCustomerEntity.customerName);	
				customerNameObj.setReadOnly(true);
				
				//有效时间
				var effectiveTimeObj = me.getPriceCustomerUpdateForm().getForm().findField('effectiveTime');
				effectiveTimeObj.setValue(new Date(me.priceCustomerEntity.effectiveTime));	
				
				if(me.priceCustomerEntity.status == 'EFFECTIVE' 
					|| me.priceCustomerEntity.status == 'WAIT'){
					effectiveTimeObj.setReadOnly(false);
				}
				
				//失效时间
				var invalidTimeObj = me.getPriceCustomerUpdateForm().getForm().findField('invalidTime');
				invalidTimeObj.setValue(new Date(me.priceCustomerEntity.invalidTime));	
				
				//备注
				var remarkObj = me.getPriceCustomerUpdateForm().getForm().findField('remark');
				remarkObj.setValue(me.priceCustomerEntity.remark);
				
				//是否新客户
				var customerTypeObj = me.getPriceCustomerUpdateForm().getForm().findField('customerType');
				
				/*
				var customerTypeRecord=Ext.data.Record({
					  name :  me.priceCustomerEntity.customerType == 'Y' ? '是':'否',
					  value :  me.priceCustomerEntity.customerType
					}
				);
				customerTypeObj.getStore().add(customerTypeRecord);
				*/
				
				customerTypeObj.setValue(me.priceCustomerEntity.customerType);
				customerTypeObj.setReadOnly(true);
			}
			
			//设置客户专属价格的明细记录
			me.getPriceCustomerSubAddGrid().store.reload({
				'priceCustomerId':me.priceCustomerEntity.id
			});
			
			Ext.getCmp('searchArrivePriceCityForUpdate').setValue(null);
			
			//重新设置为初始值
			me.standardImple = false;			
		}
	},
	priceCustomerEntity:null,
	priceCustomerSubEntityList:null,
	setPriceCustomerEntity:function(priceCustomerEntity){
		this.priceCustomerEntity = priceCustomerEntity;
	},
	getPriceCustomerEntity:function(){
		return this.priceCustomerEntity;
	},
	setPriceCustomerSubEntityList:function(priceCustomerSubEntityList){
		this.priceCustomerSubEntityList = priceCustomerSubEntityList;
	},
	getPriceCustomerSubEntityList:function(priceCustomerSubEntityList){
		return this.priceCustomerSubEntityList;
	},	
	priceCustomerUpdateForm : null,
	getPriceCustomerUpdateForm : function() {
		if (Ext.isEmpty(this.priceCustomerUpdateForm)) {
			this.priceCustomerUpdateForm = Ext.create('Miser.view.pricecard.priceCustomerUpdateForm');
		}
		return this.priceCustomerUpdateForm;
	},
	priceCustomerSubAddGrid : null,
	getPriceCustomerSubAddGrid : function() {
		if (Ext.isEmpty(this.priceCustomerSubAddGrid)) {
			//this.priceCustomerSubAddGrid = Ext.create('Miser.view.priceCustomerSub.AddGrid');
			this.priceCustomerSubAddGrid = Ext.create('Miser.view.priceCustomerSub.UpdateGrid');
			this.priceCustomerSubAddGrid.parent = this; // 父元素
		}
		return this.priceCustomerSubAddGrid;
	},
	submitPriceCustomerUpdateForm : function() {
		var me = this;
		if (me.getPriceCustomerUpdateForm().getForm().isValid()) { // 校验form是否通过校验
			
			Ext.Msg.wait('处理中，请稍后...', '提示');
			
			var priceCustomerEntity = new Miser.model.priceCustomerEntity();
			me.getPriceCustomerUpdateForm().getForm().updateRecord(priceCustomerEntity); // 将FORM中数据设置到MODEL里面
			var curData = priceCustomerEntity.data;
			
			//针对于点击 导入新价格城市时设置
			if(me.standardImple){
				curData.customerType = 'Y';
			}
			
			if(curData.invalidTime <= curData.effectiveTime)
			{
				//'失效时间 必须大于 生效时间'
				miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.invalidTimeThanEffectiveTime'));
				return;
			}			
			
			var priceCustomerSubEntityItems = this.getPriceCustomerSubAddGrid().store.data.items;
			var priceCustomerSubEntityList = priceCustomerSubDetailCheck(priceCustomerSubEntityItems);
			
			if(priceCustomerSubEntityList == null){
				return;
			}
 
			var params = {
					'priceCustomerVo' : {
						'priceCustomerEntity' : curData,
						'priceCustomerSubEntityList' : priceCustomerSubEntityList
					}
				};
			
			var successFun = function(json) {
				Ext.Msg.hide(); 
				var message = json.message;
				
				// 提示新增成功
				miser.showInfoMsg(message); 
				me.close();
				
				//每次提交后 需要将当前表单中的 store 清空 不然 提交后 再次点击 新增 仍然会展示上次动态增加的行记录
				me.getPriceCustomerSubAddGrid().store.removeAll();
				
				// 成功之后重新查询刷新结果集
				me.parent.getStore().load();
				
				//去掉之前选中的记录
				var priceCustomerGridObj = Ext.getCmp('priceCustomerGrid');
				var selModelObj = priceCustomerGridObj.selModel;
				selModelObj.deselectAll(true);
				
			};

			var failureFun = function(json) {
				Ext.Msg.hide(); 
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.requestTimeOut'));
				} else {
					var message = json.message;
					showPriceCustomerTipMsg(message);
				}
			};
			
			miser.requestJsonAjax('priceCustomer!updatePriceCustomer.action',params, successFun, failureFun); // 发送AJAX请求
		}
		else {
			miser.showErrorMes('表单校验不通过,请检查是否填写完整');
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			id:'searchArrivePriceCityForUpdate',
			//fieldLabel:pricecard.priceCustomer.i18n('pricecard.priceCustomer.arrivePriceCity'),
			xtype:'textfield'
		},{
			text:'搜索',
			handler : function(){
				var subViewGrid = me.getPriceCustomerSubAddGrid();
				subCustomerDetailSearch(subViewGrid,'searchArrivePriceCityForUpdate');
			}
		},{
			xtype:'tbfill'
		},{
			text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.saveBtn'),//'保存',
			handler : function() {
				me.submitPriceCustomerUpdateForm();
			}
		},{
			text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.cancelBtn'),//'取消',
			handler : function() {
				me.close();
			}
		}];
		me.items = [ me.getPriceCustomerUpdateForm(),me.getPriceCustomerSubAddGrid()];
		me.callParent([ cfg ]);
	}
});

var globalPriceCustomerUpdateWindow = null;

 
/**
 * 查看表单
 */
Ext.define('Miser.view.pricecard.priceCustomerViewForm',{
					extend : 'Ext.form.Panel',
					id:'priceCustomerViewForm',
					frame : true,
					autoDestroy:true,
					region : 'north',
					defaults : {
						labelWidth : 0.4,
						columnWidth : 0.6,
						margin : '8 10 5 10',
						labelAlign : 'right'
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						
						me.items = [
									{
										layout : 'hbox',
										defaults : {
											labelWidth : 70,
											labelAlign : "right"
										},
										defaults : {
											margins : '10 10 5 5'
										},
										items : [  {
											name : 'id',
											fieldLabel : 'id',
											xtype : 'textfield',
											hidden : true
										 },
										 {
											    name: 'linkorg',
											    labelWidth: 5,
											    
									            flex:8,	
									            // 是否加载默认值（当前用户部门）
									            loaddefault:false,          
									            width:1100,  
									            
									            //事业部
									            divisionLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.divisionCodeLabel')+':',
									            divisionName: 'divisionCode',
									            divisionIsBlank:true,
									            divisionLabelWidth:100,
									            divisionWidth:275,
									            
									            // 大区
									            bigregionLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.bigRegionCodeLabel')+':',
									            bigregionName: 'bigRegionCode',
									            bigregionIsBlank:true,
									            bigregionLabelWidth:100,
									            bigregionWidth:275,
									            
									            // 路区
									            roadareaLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.roadAreaCodeLabel')+':',
									            roadareaName: 'roadAreaCode',
									            roadareaIsBlank:true,
									            roadareaLabelWidth:100,
									            roadareaWidth:275,
									            
									            
									            // 门店
									            salesdepartmentLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.saleDepartCodeLabel')+':',
									            salesdepartmentName: 'saleDepartCode',
									            salesdepartmentIsBlank:true,	       
									            salesdepartmentLabelWidth:100,
									            salesdepartmentWidth:275,
											    
											    type: 'D-B-R-S',
											    xtype: 'linkorgcombselector'
											} ]
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
									items : [ {
										name : 'customerCode',
										fieldLabel :pricecard.priceCustomer.i18n('pricecard.priceCustomer.customerCodeLabel'),// '客户编号',
										xtype : 'textfield',
										labelAlign:'right'
									},{
										name : 'customerName',
										fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.customerNameLabel'),//'客户名称',
										xtype : 'textfield',
										labelAlign:'right'
									},{
						    			name: 'effectiveTime',
						    			fieldLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.effectiveTimeLabel'),//'有效时间',
						    			xtype : 'datetimefield',
						    			format : 'Y-m-d H:i:s',
						    			labelAlign:'right'
									},{
						    			name: 'invalidTime',
						    			fieldLabel: pricecard.priceCustomer.i18n('pricecard.priceCustomer.invalidTimeLabel'),//'失效时间',
						    			xtype : 'datefield',
						    			format : 'Y-m-d H:i:s',
						    			labelAlign:'right'
									} ]
								},
								{
									layout : 'hbox',
									defaults : {
										labelWidth : 70,
										labelAlign : "right"
									},
									defaults:{
										margins : '10 10 5 5'
									},
									items:[
									       {
		                                         fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.isNewCustomerLabel'),//是否新客户
		                                         name:'customerType',
		                                         xtype: 'yesnocombselector',
		                                         isShowAll : false,
		                                         labelAlign:'right',
		                                         allowBlank:false
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
											fieldLabel : pricecard.priceCustomer.i18n('pricecard.priceCustomer.remarkLabel'),//'备注',
											name : 'remark',
											width : document.documentElement.clientWidth - 25,
											xtype : 'textarea',
											labelAlign:'right'
										}  ]
								}];
						me.callParent([ cfg ]);
					}
				});

//查看窗体
Ext.define('Miser.view.pricecard.priceCustomerViewWindow', {
	extend : 'Ext.window.Window',
	title : pricecard.priceCustomer.i18n('pricecard.priceCustomer.viewWindowTitle'),//'客户专属价格详情',
	closable : true,
	parent : null,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	autoDestroy:true,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getPriceCustomerViewForm().getForm().reset(); // 表格重置
			
			Ext.getCmp('miser_biz_pricecard_priceCustomer_ViewDelBtn').setDisabled(false);
			Ext.getCmp('miser_biz_pricecard_priceCustomer_ViewModifyBtn').setDisabled(false);
			
			
			me.priceCustomerEntity = null;
			me.priceCustomerSubEntityList = null;
			
			me.getPriceCustomerSubViewGrid().store.removeAll();
			
			//关闭前将搜索框填入的值清空
			Ext.getCmp('searchArrivePriceCity').setValue(null);
			
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getPriceCustomerViewForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			
			//关闭前将搜索框填入的值清空
			Ext.getCmp('searchArrivePriceCity').setValue(null);
			
			//初始化到 界面组件中
			//为组件填充预先需要填充的值
			if(me.priceCustomerEntity != null)
			{
				//待生效 已失效的数据 才能被作废
				if(me.priceCustomerEntity.status == 'WAIT' 
					 || me.priceCustomerEntity.status == 'EFFECTIVE')
				{
					Ext.getCmp('miser_biz_pricecard_priceCustomer_ViewDelBtn').setDisabled(false);
				}
				else
				{
					Ext.getCmp('miser_biz_pricecard_priceCustomer_ViewDelBtn').setDisabled(true);
				}
				 
				//已失效 已废弃的数据  不能再修改 生效中 待生效的数据 允许修改
				if(me.priceCustomerEntity.status == 'PASSED'
					|| me.priceCustomerEntity.status == 'DELETED'){
					Ext.getCmp('miser_biz_pricecard_priceCustomer_ViewModifyBtn').setDisabled(true);
				}
				else{
					Ext.getCmp('miser_biz_pricecard_priceCustomer_ViewModifyBtn').setDisabled(false);
				}
				
				//设置隐藏字段 id 用于 grid 查询
				var priceCustomerIdObj = me.getPriceCustomerViewForm().getForm().findField('id');
				priceCustomerIdObj.setValue(me.priceCustomerEntity.id);
				
				//所属事业部
				var divisionObj = me.getPriceCustomerViewForm().getForm().findField('divisionCode');
				var divisionRecord=Ext.data.Record({
					  name :  me.priceCustomerEntity.divisionName,
					  code :  me.priceCustomerEntity.divisionCode
					}
				);
				divisionObj.getStore().add(divisionRecord);
				divisionObj.setValue(me.priceCustomerEntity.divisionCode);
				
				//所属大区
				var bigRegionObj = me.getPriceCustomerViewForm().getForm().findField('bigRegionCode');
				var bigRegionRecord=Ext.data.Record({
					name :  me.priceCustomerEntity.bigRegionName,
					code :  me.priceCustomerEntity.bigRegionCode
					}
				);
				bigRegionObj.getStore().add(bigRegionRecord);
				bigRegionObj.setValue(me.priceCustomerEntity.bigRegionCode);
				
				
				//所属路区
				var roadArea = me.getPriceCustomerViewForm().getForm().findField('roadAreaCode');
				var roadAreaRecord=Ext.data.Record({
					name :  me.priceCustomerEntity.roadAreaName,
					code :  me.priceCustomerEntity.roadAreaCode
					}
				);
				roadArea.getStore().add(roadAreaRecord);
				roadArea.setValue(me.priceCustomerEntity.roadAreaCode);
				
				
				//所属门店
				var saleDepart = me.getPriceCustomerViewForm().getForm().findField('saleDepartCode');
				var saleDepartRecord=Ext.data.Record({
					name :  me.priceCustomerEntity.saleDepartName,
					code :  me.priceCustomerEntity.saleDepartCode
					}
				);
				saleDepart.getStore().add(saleDepartRecord);
				saleDepart.setValue(me.priceCustomerEntity.saleDepartCode);
				
				
				//客户编号
				var customerCodeObj = me.getPriceCustomerViewForm().getForm().findField('customerCode');
				customerCodeObj.setValue(me.priceCustomerEntity.customerCode);
				
				//客户名称
				var customerNameObj = me.getPriceCustomerViewForm().getForm().findField('customerName');
				customerNameObj.setValue(me.priceCustomerEntity.customerName);	
				
				//有效时间
				var effectiveTimeObj = me.getPriceCustomerViewForm().getForm().findField('effectiveTime');
				effectiveTimeObj.setValue(new Date(me.priceCustomerEntity.effectiveTime));
				
				//失效时间
				var invalidTimeObj = me.getPriceCustomerViewForm().getForm().findField('invalidTime');
				invalidTimeObj.setValue(new Date(me.priceCustomerEntity.invalidTime));	
				
				//备注
				var remarkObj = me.getPriceCustomerViewForm().getForm().findField('remark');
				remarkObj.setValue(me.priceCustomerEntity.remark);	
				
				//是否新客户
				var customerTypeObj = me.getPriceCustomerViewForm().getForm().findField('customerType');
				customerTypeObj.setValue(me.priceCustomerEntity.customerType);
				
				var textFieldObjArr = [divisionObj,bigRegionObj,roadArea,saleDepart,customerCodeObj,customerNameObj,effectiveTimeObj,invalidTimeObj,remarkObj,customerTypeObj];
				for(var m=0;m<textFieldObjArr.length;m++){
					var curTextFieldObj = textFieldObjArr[m]; 
					curTextFieldObj.setReadOnly(true);
				}
				
			}
			
			//设置客户专属价格的明细记录
			me.getPriceCustomerSubViewGrid().store.reload({
				'priceCustomerId':me.priceCustomerEntity.id
			});
		}
	},
	priceCustomerEntity:null,
	priceCustomerSubEntityList:null,
	setPriceCustomerEntity:function(priceCustomerEntity){
		this.priceCustomerEntity = priceCustomerEntity;
	},
	getPriceCustomerEntity:function(){
		return this.priceCustomerEntity;
	},
	setPriceCustomerSubEntityList:function(priceCustomerSubEntityList){
		this.priceCustomerSubEntityList = priceCustomerSubEntityList;
	},
	getPriceCustomerSubEntityList:function(priceCustomerSubEntityList){
		return this.priceCustomerSubEntityList;
	},	
	priceCustomerViewForm : null,
	getPriceCustomerViewForm : function() {
		if (Ext.isEmpty(this.priceCustomerViewForm)) {
			this.priceCustomerViewForm = Ext.create('Miser.view.pricecard.priceCustomerViewForm');
		}
		return this.priceCustomerViewForm;
	},
 
	priceCustomerAddWindow : null,
	getPriceCustomerAddWindow : function(cfg) {
		if (Ext.isEmpty(this.priceCustomerAddWindow)) {
			this.priceCustomerAddWindow = Ext.create('Miser.view.pricecard.priceCustomerAddWindow',cfg);
			this.priceCustomerAddWindow.parent = this; // 父元素
		}
		return this.priceCustomerAddWindow;
	},
	priceCustomerUpdateWindow:null,
	getPriceCustomerUpdateWindow:function(cfg){
		if (Ext.isEmpty(this.priceCustomerUpdateWindow)) {
			//this.priceCustomerUpdateWindow = Ext.create('Miser.view.pricecard.priceCustomerUpdateWindow',cfg);
			
			if(globalPriceCustomerUpdateWindow == null){
				globalPriceCustomerUpdateWindow = Ext.create('Miser.view.pricecard.priceCustomerUpdateWindow');
			}
			
			this.priceCustomerUpdateWindow = globalPriceCustomerUpdateWindow;
			this.priceCustomerUpdateWindow.parent = this; // 父元素
		}
		return this.priceCustomerUpdateWindow;
	},
	priceCustomerSubViewGrid : null,
	getPriceCustomerSubViewGrid : function() {
		if (Ext.isEmpty(this.priceCustomerSubViewGrid)) {
			this.priceCustomerSubViewGrid = Ext.create('Miser.view.priceCustomerSub.ViewGrid');
			this.priceCustomerSubViewGrid.parent = this; // 父元素
		}
		return this.priceCustomerSubViewGrid;
	},	 
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			id:'searchArrivePriceCity',
			//fieldLabel:pricecard.priceCustomer.i18n('pricecard.priceCustomer.arrivePriceCity'),
			xtype:'textfield'
		},{
			text:'搜索',
			handler : function(){
				var subViewGrid = me.getPriceCustomerSubViewGrid();
				subCustomerDetailSearch(subViewGrid,'searchArrivePriceCity');
			}
		},{
			xtype:'tbfill'
		},{
			text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.addBtn'),//'新增',
			handler : function() {
				
				var viewForm = me.getPriceCustomerViewForm();
				var customerCode =  viewForm.getForm().findField('customerCode').getValue();
				
				me.close();
				
				var params = {
						'bseCustomerVo':{
							'code':customerCode
						}
				};
				
				var successFun = function (json) {
					var bseCustomerEntityList = json.bseCustomerVo.bseCustomerEntityList;
					var bseCustomerEntity = bseCustomerEntityList[0];
					
					/*这里需要注意更改其 addForm.id
					 * 若不更改 在主界面点击新增后 再到详情页面里 点击新增 此时 表单就会重复
					 * 原因是 两处都是去 create 同一个定义的 *AddWindow , 而这个 *AddWindow 里
					 * 引用的是同一个 *AddForm ,且这个 *AddForm 里有固定设置 id
					 */ 
					me.getPriceCustomerAddWindow(cfg).getPriceCustomerAddForm().id = 'priceCustomerViewAddForm';
					me.getPriceCustomerAddWindow(cfg).setBseCustomerEntity(bseCustomerEntity);
				 
					me.getPriceCustomerAddWindow(cfg).show();
				};
				
				miser.requestJsonAjax('../pricecard/bseCustomer!queryBseCustomer.action?limit=20', params, successFun, failureFun);
				
			}
		},{
			id:'miser_biz_pricecard_priceCustomer_ViewModifyBtn',
			text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.updateBtn'),//'修改',
			handler : function() {			
				
				var cfg = {};
				
				var viewForm = me.getPriceCustomerViewForm();
				var id =  viewForm.getForm().findField('id').getValue();
				me.close();
				
				var params = {
						'priceCustomerVo':{
							'priceCustomerEntity':{
								'id':id
							}
						}
				};
				
				var successFun = function (json) {
					
					//设置 id 同上面 新增 一个问题 在 查看详情 点击 修改后 再在外面点击 修改 会出现重复表单
					me.getPriceCustomerUpdateWindow(cfg).getPriceCustomerUpdateForm().id = 'priceCustomerViewUpdateForm';
					
					//后台返回的组织实体
					var priceCustomerEntity = json.priceCustomerVo.priceCustomerEntity;
					me.getPriceCustomerUpdateWindow(cfg).setPriceCustomerEntity(null);
					me.getPriceCustomerUpdateWindow(cfg).setPriceCustomerEntity(priceCustomerEntity);
					
					me.getPriceCustomerUpdateWindow(cfg).show();
				};
				
				miser.requestJsonAjax('../pricecard/priceCustomer!queryPriceCustomerAndSubList.action', params, successFun, failureFun);			
			}
		},{
			id:'miser_biz_pricecard_priceCustomer_ViewDelBtn',
			text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.deleteBtn'),//'作废',
			handler : function() {		
				
				//获取当前 选中的记录
				var viewForm = me.getPriceCustomerViewForm();
				var id =  viewForm.getForm().findField('id').getValue();
				id = "'"+id+"'";
				
				me.close();
				
				if(id != '')
				{
					
					//'您确认作废当前的价格记录吗？确认后数据状态会变更为已失效，并会保留您的操作记录'
					miser.showQuestionMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.destoryConfirmTipMsg'),
		                     function(e) {
								if (e == 'yes') {
									
									var params = {
											'priceCustomerVo':{
											    'destoryIdStr':id	
											}
									};
									
									var successFun = function (json) {
										
										var msg = json.message;
										if(msg == "miser_priceCustomer_StatusChange"){
											
											//'状态已发生变化，请刷新后重新操作'
											miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.statusHasChanged'));
										}
										else if(msg == "miser_priceCustomer_destorySuccess"){
											
											//提示消息  "作废成功"
											miser.showInfoMsg(pricecard.priceCustomer.i18n('pricecard.priceCustomer.destroySuccess'));
											
											//重新刷新页面
											me.store.reload();
										}
									};
									
									miser.requestJsonAjax('../pricecard/priceCustomer!destoryPriceCustomerAndSubList.action', params, successFun, failureFun);		
								}
						});
				}

			}		
		},{
			text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.exprortResultList'),//'导出结果列表',
			handler : function() {		
				//获取当前 选中的记录
				var viewForm = me.getPriceCustomerViewForm();
				var id =  viewForm.getForm().findField('id').getValue();

				//添加 customerType 标识 若客户使用新标准价格城市 则需要区分导出SQL
				//若不区分 本为使用新价格城市只有473条 但导出为680条
				var customerType = viewForm.getForm().findField('customerType').getValue();
				
				down(id,'view',customerType);
			}	
		},{
			text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.closeBtn'),//'关闭',
			handler : function() {
				me.close();
			}
		}];
		me.items = [ me.getPriceCustomerViewForm(),me.getPriceCustomerSubViewGrid()];
		me.callParent([ cfg ]);
	}
});

var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    clicksToMoveEditor: 1,
    saveBtnText:pricecard.priceCustomer.i18n('pricecard.priceCustomer.saveBtn'),//'保存',
    cancelBtnText:pricecard.priceCustomer.i18n('pricecard.priceCustomer.cancelBtn'),//'取消',
    errorSummary:false,
    listeners:{
    	edit:function(e,o){
    		var data = e.grid.store.getAt(o.rowIdx).data;
    		var items = e.grid.store.data.items;
    		var dataKeyStr = data.sendPriceCity+"="+data.arrivePriceCity+"="+data.transTypeCode;
    		var existCount = 0;
    		for(var i=0;i<items.length;i++)
    		{
    			var curSubData = items[i].data;
    			var curSubDataKeyStr = curSubData.sendPriceCity+"="+curSubData.arrivePriceCity+"="+curSubData.transTypeCode;
    			if( dataKeyStr == curSubDataKeyStr)
    			{
    				existCount ++;
    			}
    		}
    		
    		if(existCount > 1)
    		{
				var ind = e.grid.store.find("sendPriceCity",data.sendPriceCity)+1;
				miser.showWoringMessage('已经存在一条相同信息在：第' + ind + "行");
				rowEditing.startEdit(o.rowIdx,0);
    		}
    	},
    	beforeedit:function(editor,e,eOpts){
    		var data = editor.grid.store.getAt(e.rowIdx).data;
    		
            //出发价格城市
    		if(data.sendPriceCityName !=""){
    			Ext.getCmp('sendPriceCity_add').setCombValue(data.sendPriceCityName,data.sendPriceCity);
    		}
    		
    		//到达价格城市
    		if(data.arrivePriceCityName != ""){
    			Ext.getCmp('arrivePriceCity_add').setCombValue(data.arrivePriceCityName,data.arrivePriceCity);
    		}
    		
    		//运输类型
    		if(data.transTypeName != ""){
    			Ext.getCmp('transTypeCode_add').setCombValue(data.transTypeName,data.transTypeCode);	
    		}
    		
    		//燃油服务费
    		if(data.fuelFeeSectionName !=""){
    			Ext.getCmp('fuelFeeSection_add').setCombValue(data.fuelFeeSectionName,data.fuelFeeSection);	
    		}
    		
    		//运费优惠分段
    		if(data.freightFeeSectionName !=""){
    			Ext.getCmp('freightFeeSection_add').setCombValue(data.freightFeeSectionName,data.freightFeeSection);	
    		}    		
    		return true;
    	},
		dblclick: function(e,o){
			this.cancelEdit();
		},
		cancelEdit:function (e,o){
			//e.grid.store.removeAt(o.rowIdx);
			
			//注意这里的 索引 要与 新增的索引一致
			e.grid.store.removeAt(0);
			
			Ext.getCmp('sendPriceCity_add').onTrigger1Click();
			Ext.getCmp('arrivePriceCity_add').onTrigger1Click();
			Ext.getCmp('transTypeCode_add').onTrigger1Click();
			Ext.getCmp('fuelFeeSection_add').onTrigger1Click();
			Ext.getCmp('freightFeeSection_add').onTrigger1Click();
			
		},
		afteredit:function(e,o){
			Ext.getCmp('sendPriceCity_add').onTrigger1Click();
			Ext.getCmp('arrivePriceCity_add').onTrigger1Click();
			Ext.getCmp('transTypeCode_add').onTrigger1Click();
			Ext.getCmp('fuelFeeSection_add').onTrigger1Click();
			Ext.getCmp('freightFeeSection_add').onTrigger1Click();
		}
    }
});




var rowEditingUpdate = Ext.create('Ext.grid.plugin.RowEditing', {
    clicksToMoveEditor: 1,
    saveBtnText:pricecard.priceCustomer.i18n('pricecard.priceCustomer.saveBtn'),//'保存',
    cancelBtnText:pricecard.priceCustomer.i18n('pricecard.priceCustomer.cancelBtn'),//'取消',
    errorSummary:false,
    listeners:{
    	edit:function(e,o){
    		var data = e.grid.store.getAt(o.rowIdx).data;
    		var items = e.grid.store.data.items;
    		var dataKeyStr = data.sendPriceCity+"="+data.arrivePriceCity+"="+data.transTypeCode;
    		var existCount = 0;
    		for(var i=0;i<items.length;i++)
    		{
    			var curSubData = items[i].data;
    			var curSubDataKeyStr = curSubData.sendPriceCity+"="+curSubData.arrivePriceCity+"="+curSubData.transTypeCode;
    			if( dataKeyStr == curSubDataKeyStr)
    			{
    				existCount ++;
    			}
    		}
    		
    		if(existCount > 1)
    		{
				var ind = e.grid.store.find("sendPriceCity",data.sendPriceCity)+1;
				miser.showWoringMessage('已经存在一条相同信息在：第' + ind + "行");
				rowEditing.startEdit(o.rowIdx,0);
    		}
    	},
    	beforeedit:function(editor,e,eOpts){
       		//点击 修改时 如下下拉框的值 会置空 需要单独重新填充值
    		var data = editor.grid.store.getAt(e.rowIdx).data;
    		
			Ext.getCmp('sendPriceCity_update').onTrigger1Click();
			Ext.getCmp('arrivePriceCity_update').onTrigger1Click();
			Ext.getCmp('transTypeCode_update').onTrigger1Click();
			Ext.getCmp('fuelFeeSection_update').onTrigger1Click();
			Ext.getCmp('freightFeeSection_update').onTrigger1Click();
    		
            //出发价格城市
    		if(data.sendPriceCityName !=""){
    			Ext.getCmp('sendPriceCity_update').setCombValue(data.sendPriceCityName,data.sendPriceCity);
    		}
    		
    		//到达价格城市
    		if(data.arrivePriceCityName != ""){
    			Ext.getCmp('arrivePriceCity_update').setCombValue(data.arrivePriceCityName,data.arrivePriceCity);
    		}
    		
    		//运输类型
    		if(data.transTypeName != ""){
    			Ext.getCmp('transTypeCode_update').setCombValue(data.transTypeName,data.transTypeCode);	
    		}
    		
    		//燃油服务费
    		if(data.fuelFeeSectionName !=""){
    			Ext.getCmp('fuelFeeSection_update').setCombValue(data.fuelFeeSectionName,data.fuelFeeSection);	
    		}
    		
    		//运费优惠分段
    		if(data.freightFeeSectionName !=""){
    			Ext.getCmp('freightFeeSection_update').setCombValue(data.freightFeeSectionName,data.freightFeeSection);	
    		}
    		return true;
    	},
		dblclick: function(e,o){
			this.cancelEdit();
		},
		cancelEdit:function (e,o){
			//修改的时候 点击 取消 不应该删除该条记录 与 新增不一样
			//e.grid.store.removeAt(o.rowIdx);
			
			Ext.getCmp('sendPriceCity_update').onTrigger1Click();
			Ext.getCmp('arrivePriceCity_update').onTrigger1Click();
			Ext.getCmp('transTypeCode_update').onTrigger1Click();
			Ext.getCmp('fuelFeeSection_update').onTrigger1Click();
			Ext.getCmp('freightFeeSection_update').onTrigger1Click();
			
		},
		afteredit:function(){
			Ext.getCmp('sendPriceCity_update').onTrigger1Click();
			Ext.getCmp('arrivePriceCity_update').onTrigger1Click();
			Ext.getCmp('transTypeCode_update').onTrigger1Click();
			Ext.getCmp('fuelFeeSection_update').onTrigger1Click();
			Ext.getCmp('freightFeeSection_update').onTrigger1Click();
		}
    }
});


/**
 * 新增时候的grid
 */
var priceCustomerSubAddGrid = Ext.define('Miser.view.priceCustomerSub.AddGrid',
	{
		extend : 'Ext.grid.Panel',
		frame : true,
		autoScroll : true,
		width : document.documentElement.clientWidth - 150,
		height : document.documentElement.clientHeight - 360,
		stripeRows : true,// 交替行效果
		region : 'center',
		isEdit:true,
		plugins: [rowEditing],
		constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.columns = [
					{
						dataIndex : 'sendPriceCity',
						width : 120,
					    text:pricecard.priceCustomer.i18n('pricecard.priceCustomer.sendPriceCity'),//'出发价格城市',
					    editor:{
					    	xtype:'startPriceCityselector',
					    	 id :"sendPriceCity_add",
					    	 //allowBlank :false,
							 priceCityScope:'STANDARD',
							 initPriceCityScope:function(){
		                         var customerTypeObj = me.parent.getPriceCustomerAddForm().getForm().findField('customerType');
		                         var customerTypeVal = customerTypeObj.getValue();
								 var toSetValue = 'STANDARD';
								 if('N' == customerTypeVal ){ //是老客户
									 toSetValue = 'CUSTOMER';
								 }
								 return toSetValue;
							 }
					    },
					    
						renderer: function(value,o,m){
							/*
							 if(m.data.sendPriceCityName!=""){
								 return m.data.sendPriceCityName;
							 }
							 */
							
							  var sto = Ext.getCmp('sendPriceCity_add');
							  var display = value;
							  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
							  {
								  display = getDisplayTextCommon(sto.getStore(),value);
								  if(display == value){
									  display = m.data.sendPriceCityName;
								  }
							  }
							  else
							 {
								  display = m.data.sendPriceCityName;
							 }
							  m.data.sendPriceCityName = display;
							 return m.data.sendPriceCityName;
						 }
					},
					{
						dataIndex : 'arrivePriceCity',
						width:120,
						text:pricecard.priceCustomer.i18n('pricecard.priceCustomer.arrivePriceCity'),//'到达价格城市',
						editor:{
							xtype:'arrivePriceCityselector',
							id :"arrivePriceCity_add",
							allowBlank :false,
							priceCityScope:'STANDARD',
                            initPriceCityScope:function(){
                                var customerTypeObj = me.parent.getPriceCustomerAddForm().getForm().findField('customerType');
                                var customerTypeVal = customerTypeObj.getValue();
								var toSetValue = 'STANDARD';
								 if('N' == customerTypeVal ){ //是老客户
									 toSetValue = 'CUSTOMER';
								 }
								 return toSetValue;
                            }							
						},
						renderer: function(value,o,m){
							/*
							 if(m.data.arrivePriceCityName!=""){
								 return m.data.arrivePriceCityName;
							 }
							 */
							
							  var sto = Ext.getCmp('arrivePriceCity_add');
							  var display = value;
							  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
							  {
								  display = getDisplayTextCommon(sto.getStore(),value);
							      if(display == value){
										display = m.data.arrivePriceCityName;
								  }								  
							  }
							  else
							 {
								  display = m.data.arrivePriceCityName;
							 }
							  
							  m.data.arrivePriceCityName = display;
							 return m.data.arrivePriceCityName;
						 }
					},
					{
						dataIndex : 'transTypeCode',
						width : 100,
						text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.transTypeCode'),//'运输类型',
						editor: {
							xtype:'transtypecombselector',
							allowBlank: false,
							showAll:false,
							containsRoot:false,
							id :"transTypeCode_add"
						},
						
						renderer: function(value,o,m){
							  var sto = Ext.getCmp('transTypeCode_add');
							  var display = value;
							  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
							  {
								  display = getDisplayTextCommon(sto.getStore(),value);
								  if(display == value){
									  display = m.data.transTypeName;
								  }
							  }
							  else
							 {
								  display = m.data.transTypeName;
							 }
							 m.data.transTypeName = display;
							 return m.data.transTypeName;
						 }				
					},
					{
						dataIndex : 'weightPrice',
						width : 80,
						text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.weightPrice'),//'重量单价',
			            editor: {
			                allowBlank: false,
							minValue: 0,
							decimalPrecision:2,
			                xtype: 'numberfield'
			            }						
					},
					{
						dataIndex : 'volumePrice',
						width : 80,
						text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.volumePrice'),// '体积单价',
			            editor: {
			                allowBlank: false,
							minValue: 0,
							decimalPrecision:2,
			                xtype: 'numberfield'
			            }
					},
					{
						dataIndex : 'addFee',
						text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.addFee'),//'附加费',
						width : 80,
			            editor: {
							minValue: 0,
							decimalPrecision:2,
			                xtype: 'numberfield'
			            }
					},
					{						
						dataIndex : 'lowestFee',
						text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.lowestFee'),//'最低收费',
						width : 80,
			            editor: {
							minValue: 0,
							decimalPrecision:2,
			                xtype: 'numberfield'
			            }
					},
					{
						dataIndex : 'fuelFeeSection',
						text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.fuelFeeSection'),//'燃油服务费',
						width : 120,
						editor: {
							xtype:'dynamicPriceSectioncombselector',
							showAll:false,
							containsRoot:false,
							id :"fuelFeeSection_add",
							sectionedItem:'FUEL'
						},
						
						renderer: function(value,o,m){
							  var sto = Ext.getCmp('fuelFeeSection_add');
							  var display = value;
							  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
							  {
								  display = getDisplayTextCommon(sto.getStore(),value);
								  if(display == value){
									  display = m.data.fuelFeeSectionName;
								  }
							  }
							  else
							 {
								  display = m.data.fuelFeeSectionName;
							 }
							  
							  m.data.fuelFeeSectionName = display;
							 return m.data.fuelFeeSectionName;
						 }
					},
					{
						dataIndex : 'freightFeeSection',
						text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.freightFeeSection'),//'运费优惠分段',
						width : 120,
						editor: {
							xtype:'dynamicPriceSectioncombselector',
							showAll:false,
							containsRoot:false,
							id :"freightFeeSection_add",
							sectionedItem:'FREIGHT'
						},
						
						renderer: function(value,o,m){
							  var sto = Ext.getCmp('freightFeeSection_add');
							  var display = value;
							  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
							  {
								  display = getDisplayTextCommon(sto.getStore(),value);
								  if(display == value){
									  display = m.data.freightFeeSectionName;
								  }
							  }
							  else
							 {
								  display = m.data.freightFeeSectionName;
							 }
							  m.data.freightFeeSectionName = display;
							 return m.data.freightFeeSectionName;
						 }
					},
					{
						dataIndex : 'weightDiscount',
						text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.weightDiscount'),// '重货折扣',
						width : 80,
			            editor: {
							minValue: 0,
							decimalPrecision:2,
			                xtype: 'numberfield'
			            }
					},
					{
						dataIndex : 'volumeDiscount',
						text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.volumeDiscount'),// '轻货折扣',
						width : 80,
			            editor: {
							minValue: 0,
							decimalPrecision:2,
			                xtype: 'numberfield'
			            }						
					}];
			me.store = Ext.create('Miser.store.priceCustomerSubStore');
			me.tbar = ['->',{
						text :'新增',
						xtype : 'addbutton',
						width : 80,
						handler : function() {
							
							//先判断是否已经勾选 是否为新客户
							var customerTypeObj = me.parent.getPriceCustomerAddForm().getForm().findField('customerType');
							var customerTypeVal = customerTypeObj.getValue();
							if(customerTypeVal == 'Y' || customerTypeVal =='N'){
								rowEditing.cancelEdit();
				                var r = Ext.create('Miser.model.priceCustomerSubEntity');
				                //me.store.insert(me.store.data.length+1, r);
				                me.store.insert(0, r);
				                //rowEditing.startEdit(me.store.data.length-1, 0);
				                rowEditing.startEdit(0, 0);
							} else {
								miser.showInfoMsg('请先确定是否使用新价格城市');
							}

						}
					},'-',{
						//id : 'miser_biz_pricecard_priceCustomerSub_UpdateBtn',
						text :'修改',
						//disabled : true,
						xtype : 'updatebutton',
						width : 80,
						handler : function() {
					 
			                var selections = me.getSelectionModel().getSelection();
			                
			                if(selections.length == 0)
			                {
			                	miser.showErrorMes('未勾选用于操作的记录');
			                	return;
			                }
			                
			                if(selections.length > 1)
			                {
			                	miser.showErrorMes('只能对单条记录进行修改操作');
			                	return;
			                }
			                
			                var lineNum = -1;
			                for(var j=0;j<selections.length;j++)
			                {
			                	var curSelection = selections[j];
			                	linenum =me.store.indexOfId(curSelection.get('id'));
			                }
			                
			                rowEditing.startEdit(linenum,0);			                
						}
					},'-',{
						//id : 'miser_biz_pricecard_priceCustomerSub_DelBtn',
						text : '删除',
						xtype : 'deletebutton',
						//disabled : true,
						width : 80,
						handler : function() {
 
			                var sm = me.getSelectionModel();
			                var selections = sm.getSelection();
			                
			                if(selections.length == 0)
			                {
			                	miser.showErrorMes('未勾选用于操作的记录');
			                	return;
			                }
			                
			                rowEditing.cancelEdit();
			                me.store.remove(sm.getSelection());
			                if (me.store.getCount() > 0) {
			                	sm.select(0);
			                }
						}
					},'-',
				{
					text :'批量导入',
					xtype : 'addbutton',
					width : 100,
					handler : function() {
						
						//先判断是否已经勾选 是否为新客户
						var customerTypeObj = me.parent.getPriceCustomerAddForm().getForm().findField('customerType');
						var customerTypeVal = customerTypeObj.getValue();
						if(customerTypeVal == 'Y' || customerTypeVal =='N'){
							uploadT.excelWindow(null,imple,me);
						} else {
							miser.showInfoMsg('请先确定是否使用新价格城市');
						}
						
					}
				},'-',
				{
					xtype: 'downloadbutton',
					text: '导入模板下载',
					width : 130,
					handler:function(){
						miser.requestExportAjax('/excelTemplate/priceCustomer.xls');
					}
				} ];
					me.selModel = Ext.create('Ext.selection.CheckboxModel',{
										listeners : {
											selectionchange : function(sm, selections) {
												//Ext.getCmp('miser_biz_pricecard_priceCustomerSub_DelBtn').setDisabled(selections.length === 0);
												
												//Ext.getCmp('miser_biz_pricecard_priceCustomerSub_UpdateBtn').setDisabled(selections.length !== 1);
											}
										}
									}), me.callParent([ cfg ]);
		}
	});


Ext.define('Miser.view.priceCustomerSub.UpdateGrid',
		{
			extend : 'Ext.grid.Panel',
			frame : true,
			autoScroll : true,
			width : document.documentElement.clientWidth - 150,
			height : document.documentElement.clientHeight - 360,
			stripeRows : true,// 交替行效果
			region : 'center',
			isEdit:true,
			plugins: [rowEditingUpdate],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.columns = [
						{
							dataIndex : 'sendPriceCity',
							width : 120,
						    text:pricecard.priceCustomer.i18n('pricecard.priceCustomer.sendPriceCity'),//'出发价格城市',
	                        editor:{
	                             xtype:'startPriceCityselector',
	                             id :"sendPriceCity_update",
	                             //allowBlank :false,
	                             priceCityScope:'STANDARD',
	                             initPriceCityScope:function(){
	                                 var customerTypeObj = me.parent.getPriceCustomerUpdateForm().getForm().findField('customerType');
	                                 var customerTypeVal = customerTypeObj.getValue();
									 var toSetValue = 'STANDARD';
									 if('N' == customerTypeVal ){ //是老客户
										 toSetValue = 'CUSTOMER';
									 }
									 return toSetValue;
	                             }	                             
	                        },						    
							renderer: function(value,o,m){
 
								  var sto = Ext.getCmp('sendPriceCity_update');
								  var display = value;
								  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
								  {
									  display = getDisplayTextCommon(sto.getStore(),value);
									  if(display == value){
										  display = m.data.sendPriceCityName;
									  }
								  }
								  else
								 {
									  display = m.data.sendPriceCityName;
								 }
								  m.data.sendPriceCityName = display;
								 return m.data.sendPriceCityName;
							 }
						},
						{
							dataIndex : 'arrivePriceCity',
							width:120,
							text:pricecard.priceCustomer.i18n('pricecard.priceCustomer.arrivePriceCity'),//'到达价格城市',
	                        editor:{
	                            xtype:'arrivePriceCityselector',
	                            id :"arrivePriceCity_update",
	                            allowBlank :false,
	                            priceCityScope:'STANDARD',
	                            initPriceCityScope:function(){
	                                 var customerTypeObj = me.parent.getPriceCustomerUpdateForm().getForm().findField('customerType');
	                                 var customerTypeVal = customerTypeObj.getValue();
									 var toSetValue = 'STANDARD';
									 if('N' == customerTypeVal ){ //是老客户
										 toSetValue = 'CUSTOMER';
									 }
									 return toSetValue;
	                             }	 	                            
	                        },							
							renderer: function(value,o,m){
								  var sto = Ext.getCmp('arrivePriceCity_update');
								  var display = value;
								  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
								  {
									  display = getDisplayTextCommon(sto.getStore(),value);
									  if(display == value){
										  display = m.data.arrivePriceCityName;
									  }
								  }
								  else
								 {
									  display = m.data.arrivePriceCityName;
								 }
								  
								  m.data.arrivePriceCityName = display;
								 return m.data.arrivePriceCityName;
							 }
						},
						{
							dataIndex : 'transTypeCode',
							width : 100,
							text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.transTypeCode'),//'运输类型',
							editor: {
								xtype:'transtypecombselector',
								allowBlank: false,
								showAll:false,
								containsRoot:false,
								id :"transTypeCode_update"
							},
								
							renderer: function(value,o,m){
								  var sto = Ext.getCmp('transTypeCode_update');
								  var display = value;
								  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
								  {
									  display = getDisplayTextCommon(sto.getStore(),value);
									  if(display == value){
										  display = m.data.transTypeName;
									  }
								  }
								  else
								 {
									  display = m.data.transTypeName;
								 }
								  
								  m.data.transTypeName = display;
								  
								 return m.data.transTypeName;
							 }				
						},
						{
							dataIndex : 'weightPrice',
							width : 80,
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.weightPrice'),//'重量单价',
				            editor: {
				                allowBlank: false,
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }						
						},
						{
							dataIndex : 'volumePrice',
							width : 80,
							text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.volumePrice'),// '体积单价',
				            editor: {
				                allowBlank: false,
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }
						},
						{
							dataIndex : 'addFee',
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.addFee'),//'附加费',
							width : 80,
				            editor: {
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }
						},
						{						
							dataIndex : 'lowestFee',
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.lowestFee'),//'最低收费',
							width : 80,
				            editor: {
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }
						},
						{
							dataIndex : 'fuelFeeSection',
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.fuelFeeSection'),//'燃油服务费',
							width : 120,
	                        editor: {
	                            xtype:'dynamicPriceSectioncombselector',
	                            showAll:false,
	                            containsRoot:false,
	                            id :"fuelFeeSection_update",
	                            sectionedItem:'FUEL'
	                        },							
							
							renderer: function(value,o,m){
 
								  var sto = Ext.getCmp('fuelFeeSection_update');
								  var display = value;
								  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
								  {
									  display = getDisplayTextCommon(sto.getStore(),value);
									  if(display == value){
										  display = m.data.fuelFeeSectionName;
									  }
								  }
								  else
								 {
									  display = m.data.fuelFeeSectionName;
								 }
								  
								  m.data.fuelFeeSectionName = display;
								 return m.data.fuelFeeSectionName;
							 }
						},
						{
							dataIndex : 'freightFeeSection',
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.freightFeeSection'),//'运费优惠分段',
							width : 120,
	                        editor: {
	                            xtype:'dynamicPriceSectioncombselector',
	                            showAll:false,
	                            containsRoot:false,
	                            id :"freightFeeSection_update",
	                            sectionedItem:'FREIGHT'
	                        },							
							renderer: function(value,o,m){
 
								  var sto = Ext.getCmp('freightFeeSection_update');
								  var display = value;
								  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
								  {
									  display = getDisplayTextCommon(sto.getStore(),value);
									  if(display == value){
										  display = m.data.freightFeeSectionName;
									  }
								  }
								  else
								 {
									  display = m.data.freightFeeSectionName;
								 }
								  m.data.freightFeeSectionName = display;
								 return m.data.freightFeeSectionName;
							 }
						},
						{
							dataIndex : 'weightDiscount',
							text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.weightDiscount'),// '重货折扣',
							width : 80,
				            editor: {
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }
						},
						{
							dataIndex : 'volumeDiscount',
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.volumeDiscount'),// '轻货折扣',
							width : 80,
				            editor: {
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }						
						}];
				me.store = Ext.create('Miser.store.priceCustomerSubStore');
				me.tbar = ['->',
				       {
							text : '导出',
							handler : function() {
								
								//从父对象中取出当前的主表记录ID
								var priceCustomerId = me.parent.priceCustomerEntity.id;
								down(priceCustomerId,'update','N');
							}	
						},
						{
							text : '导出新价格城市',
							width : 120,
							handler : function() {
								
								//从父对象中取出当前的主表记录ID
								var priceCustomerId = me.parent.priceCustomerEntity.id;
								down(priceCustomerId,'update','Y');
							}	
						},												
						{
							xtype: 'downloadbutton',
						    text: '导入模板下载',
						    handler:function(){
						    	miser.requestExportAjax('/excelTemplate/priceCustomer.xls');
						    }
						},    				           
						{
							text :'导入',
							xtype : 'addbutton',
							width : 120,
							handler : function() {
								
								//设置修改界面的 导入类型
								me.parent.standardImple = false;
								
								var priceCustomerEntity = me.parent.getPriceCustomerEntity();
								var updateForm = me.parent.getPriceCustomerUpdateForm();
								
								//点击 导入时 需要将 是否使用新价格城市的值 设置为客户当前自身的值
								updateForm.getForm().findField('customerType').setValue(priceCustomerEntity.customerType);

								uploadT.excelWindow(null,imple,me);
							}
						},
						{
							text :'导入新价格城市',
							xtype : 'addbutton',
							width : 120,
							handler : function() {
								
								//设置修改界面的 导入类型
								me.parent.standardImple = true;
								
								//点击 导入时 设置 表单中 是否使用新价格城市为 Y 便于导入时 出发 到达 store 加载为 standard 的
								var updateForm = me.parent.getPriceCustomerUpdateForm();
								updateForm.getForm().findField('customerType').setValue('Y');								
								
	                            uploadT.excelWindow(null,imple,me);
							}
						},'-',{
							text :'新增',
							xtype : 'addbutton',
							width : 80,
							handler : function() {
								rowEditingUpdate.cancelEdit();
				                var r = Ext.create('Miser.model.priceCustomerSubEntity');
				                //me.store.insert(me.store.data.length+1, r);
				                me.store.insert(0, r);
				                //rowEditingUpdate.startEdit(me.store.data.length-1, 0);
				                rowEditingUpdate.startEdit(0, 0);
							}
						},'-',{
							//id : 'miser_biz_pricecard_priceCustomerSub_UpdateBtn',
							text :'修改',
							//disabled : true,
							xtype : 'updatebutton',
							width : 80,
							handler : function() {
				                var selections = me.getSelectionModel().getSelection();
				                if(selections.length == 0)
				                {
				                	miser.showErrorMes('未勾选用于操作的记录');
				                	return;
				                }
				                
				                if(selections.length > 1)
				                {
				                	miser.showErrorMes('只能对单条记录进行修改操作');
				                	return;
				                }
				                
				                var lineNum = -1;
				                for(var j=0;j<selections.length;j++)
				                {
				                	var curSelection = selections[j];
				                	linenum =me.store.indexOfId(curSelection.get('id'));
				                }
				                
				                rowEditingUpdate.startEdit(linenum,0);			                
							}
						},'-',{
							text : '删除',
							xtype : 'deletebutton',
							width : 80,
							handler : function() {
				                var sm = me.getSelectionModel();
				                var selections = sm.getSelection();
				                if(selections.length == 0)
				                {
				                	miser.showErrorMes('未勾选用于操作的记录');
				                	return;
				                }
				                rowEditingUpdate.cancelEdit();
				                me.store.remove(sm.getSelection());
				                if (me.store.getCount() > 0) {
				                	sm.select(0);
				                }
							}
						} ];
						me.selModel = Ext.create('Ext.selection.CheckboxModel',{
											listeners : {
												selectionchange : function(sm, selections) {
												}
											}
										}), me.callParent([ cfg ]);
			}
		});



Ext.define('Miser.view.priceCustomerSub.ViewGrid',
		{
			extend : 'Ext.grid.Panel',
			frame : true,
			autoScroll : true,
			width : document.documentElement.clientWidth - 150,
			height : document.documentElement.clientHeight - 360,
			stripeRows : true,// 交替行效果
			region : 'center',
			plugins: [rowEditing],
			multiSelect:true,
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.columns = [
						{
							dataIndex : 'sendPriceCity',
							width : 120,
						    text:pricecard.priceCustomer.i18n('pricecard.priceCustomer.sendPriceCity'),//'出发价格城市',
	                        editor:{
	                            xtype:'startPriceCityselector',
	                             id :"sendPriceCity",
	                             allowBlank :false,
	                             priceCityScope:'STANDARD'
	                        },						    
							renderer: function(value,o,m){
								  var sto = Ext.getCmp('sendPriceCity');
								  var display = value;
								  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
								  {
									  display = getDisplayTextCommon(sto.getStore(),value);
									  if(display == value){
										  display = m.data.sendPriceCityName;
									  }
								  }
								  else
								 {
									  display = m.data.sendPriceCityName;
								 }
								 
								  m.data.sendPriceCityName = display;
								  
								 return m.data.sendPriceCityName;
							 }
						},
						{
							dataIndex : 'arrivePriceCity',
							width:120,
							text:pricecard.priceCustomer.i18n('pricecard.priceCustomer.arrivePriceCity'),//'到达价格城市',
	                        editor:{
	                            xtype:'arrivePriceCityselector',
	                            id :"arrivePriceCity",
	                            allowBlank :false,
	                            priceCityScope:'STANDARD'
	                        },
	                        
							renderer: function(value,o,m){
								  var sto = Ext.getCmp('arrivePriceCity');
								  var display = value;
								  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
								  {
									  display = getDisplayTextCommon(sto.getStore(),value);
									  if(display == value){
										  display = m.data.arrivePriceCityName;
									  }
								  }
								  else
								 {
									  display = m.data.arrivePriceCityName;
								 }
								  
								 m.data.arrivePriceCityName = display;
								 return m.data.arrivePriceCityName;
							 }
						},
						{
							dataIndex : 'transTypeCode',
							width : 100,
							text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.transTypeCode'),//'运输类型',
							editor: new Ext.create('Miser.commonTranstypeSelector.TrantypeSelector',{
								  id :"transTypeCode",
								  forceSelection :true,
								  typeAhead :true,
								  triggerAction :'all',
								  allowBlank :false
							 }),
							renderer: function(value,o,m){
								  var sto = Ext.getCmp('transTypeCode');
								  var display = value;
								  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
								  {
									  display = getDisplayTextCommon(sto.getStore(),value);
									  if(display == value){
										  display = m.data.transTypeName;
									  }
								  }
								  else
								 {
									  display = m.data.transTypeName;
								 }
								 m.data.transTypeName = display;
								 return m.data.transTypeName;
							 }				
						},
						{
							dataIndex : 'weightPrice',
							width : 80,
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.weightPrice'),//'重量单价',
				            editor: {
				                allowBlank: false,
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }						
						},
						{
							dataIndex : 'volumePrice',
							width : 80,
							text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.volumePrice'),// '体积单价',
				            editor: {
				                allowBlank: false,
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }
						},
						{
							dataIndex : 'addFee',
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.addFee'),//'附加费',
							width : 80,
				            editor: {
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }
						},
						{						
							dataIndex : 'lowestFee',
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.lowestFee'),//'最低收费',
							width : 80,
				            editor: {
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }
						},
						{
							dataIndex : 'fuelFeeSection',
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.fuelFeeSection'),//'燃油服务费',
							width : 120,
	                        editor: {
	                            xtype:'dynamicPriceSectioncombselector',
	                            showAll:false,
	                            containsRoot:false,
	                            id :"fuelFeeSection",
	                            sectionedItem:'FUEL'
	                        },
	                        
							renderer: function(value,o,m){
								  var sto = Ext.getCmp('fuelFeeSection');
								  var display = value;
								  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
								  {
									  display = getDisplayTextCommon(sto.getStore(),value);
									  if(display == value){
										  display = m.data.fuelFeeSectionName;
									  }
								  }
								  else
								 {
									  display = m.data.fuelFeeSectionName;
								 }
								 
								 m.data.fuelFeeSectionName = display;
								 return m.data.fuelFeeSectionName;
							 }
						},
						{
							dataIndex : 'freightFeeSection',
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.freightFeeSection'),//'运费优惠分段',
							width : 120,
	                        editor: {
	                            xtype:'dynamicPriceSectioncombselector',
	                            showAll:false,
	                            containsRoot:false,
	                            id :"freightFeeSection",
	                            sectionedItem:'FREIGHT'
	                        },
	                        
							renderer: function(value,o,m){
								  var sto = Ext.getCmp('freightFeeSection');
								  var display = value;
								  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
								  {
									  display = getDisplayTextCommon(sto.getStore(),value);
									  if(display == value){
										  display = m.data.freightFeeSectionName;
									  }
								  }
								  else
								 {
									  display = m.data.freightFeeSectionName;
								 }
								 
								  m.data.freightFeeSectionName = display;
								  
								 return m.data.freightFeeSectionName;
							 }
						},
						{
							dataIndex : 'weightDiscount',
							text :pricecard.priceCustomer.i18n('pricecard.priceCustomer.weightDiscount'),// '重货折扣',
							width : 80,
				            editor: {
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }
						},
						{
							dataIndex : 'volumeDiscount',
							text : pricecard.priceCustomer.i18n('pricecard.priceCustomer.volumeDiscount'),// '轻货折扣',
							width : 80,
				            editor: {
								minValue: 0,
								decimalPrecision:2,
				                xtype: 'numberfield'
				            }						
						}];
				me.store = Ext.create('Miser.store.priceCustomerSubStore');
				me.callParent([ cfg ]);
			}
		});

/**
 * 获取显示名称
 * @param id
 * @param store
 * @returns {String}
 */
function getDisplayText(store,value){
	var displayText = "";
	 //获取当前id="combo_process"的comboBox选择的值
	// var store = Ext.getCmp(id).getStore();
	  var index = store.find("code", value);
	  var record = store.getAt(index);
	  
	  // 如果下拉列表没有被选择，那么record也就不存在，这时候，返回传进来的value，
	  // 而这个value就是grid的deal_with_name列的value，所以直接返回
	  if (record == null) {
	  //返回默认值，这是与网上其他解决办法不同的。这个方法才是正确的。我研究了很久才发现。
	   displayText = value;
	  } else {
	   displayText = record.data.priceCityName;//获取record中的数据集中的process_name字段的值
	  }
	return displayText;
}

function getDisplayTextCommon(store,value){
	var displayText = "";
	
	 //获取当前id="combo_process"的comboBox选择的值
	  //var store = Ext.getCmp(id).getStore();
	  var index = store.find("code", value);
	  var record = store.getAt(index);
	  
	  // 如果下拉列表没有被选择，那么record也就不存在，这时候，返回传进来的value，
	  // 而这个value就是grid的deal_with_name列的value，所以直接返回
	  if (record == null) {
	  //返回默认值，这是与网上其他解决办法不同的。这个方法才是正确的。我研究了很久才发现。
	   displayText = value;
	  } else {
	   displayText = record.data.name;//获取record中的数据集中的process_name字段的值
	   
	   //针对于 出发价格城市 到达价格城市 不是取 name 属性值
	   if(displayText == undefined)
	   {
		   displayText = record.data.priceCityName;
	   }
	  }
	return displayText;
}

function getDisplayTextByValue(value,store){
	 //获取当前id="combo_process"的comboBox选择的值
	  var index = store.find("code", value);
	  var record = store.getAt(index);
	  var displayText = "";
	  // 如果下拉列表没有被选择，那么record也就不存在，这时候，返回传进来的value，
	  // 而这个value就是grid的deal_with_name列的value，所以直接返回
	  if (record == null) {
	  //返回默认值，这是与网上其他解决办法不同的。这个方法才是正确的。我研究了很久才发现。
	   displayText = value;
	  } else {
	   displayText = record.data.name;//获取record中的数据集中的process_name字段的值
	  }
	return displayText;
}


//创建页面元素
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	//查询FORM
	var queryForm = Ext.create('Miser.view.bseCustomer.QueryForm');
	
	var bseCustomerGrid = Ext.create('Miser.view.priceCustomer.Grid');
	
    var mainPanel = Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        getQueryForm: function() {
            return queryForm;
        },
		getBseCustomerGrid : function() {
			return bseCustomerGrid;
		},
        items: [queryForm,bseCustomerGrid]
    });
    
    //设置窗口大小改变时的响应
    window.onresize = function(){
    
        //控制整个 panel 的宽度
    	mainPanel.setWidth(document.documentElement.clientWidth);
    	
    	//控制整个 查询表单 的宽度
    	queryForm.setWidth(document.documentElement.clientWidth - 10);
        
        //设置区域一行表单的宽度
    	var items = queryForm.items;
    	var targetObj = items.items[0].items.items[0];
		targetObj.setWidth(document.documentElement.clientWidth - 10);
		
		//设置区域下一行表单的宽度
		document.getElementById(items.items[1].items.items[0].getEl().el.parent().id).style.width = (document.documentElement.clientWidth - 10)+'px';
		
		//设置查询结果的宽度
    	bseCustomerGrid.setWidth(document.documentElement.clientWidth - 10);
    };
    
});


//文件上传
var uploadT={
		oufileName:null,
		excelWindow:function(url,fn,parent){
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
				buttons : [ 
				  {
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
												fn(action.result.outFileName,true,parent);
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

function imple(outFileName,flag,parent){
	if(flag){
		
		//获取修改界面导入操作类型
		var standardImple = parent.parent.standardImple;
		
		//获取是否为 新客户
		var customerTypeVal = null;
		
		//undefined 是指新增界面点击导入时无此值定义 false为点击导入
		if(standardImple == undefined || standardImple == false){

			var targetFormObj = Ext.getCmp('priceCustomerAddForm');

			if( targetFormObj != undefined){
				customerTypeVal = targetFormObj.getForm().findField('customerType').getValue();
			}

			if(customerTypeVal ==null || customerTypeVal == '' || customerTypeVal == undefined){
				targetFormObj = Ext.getCmp('priceCustomerUpdateForm');
			}
			
			if( targetFormObj != undefined){
				customerTypeVal = targetFormObj.getForm().findField('customerType').getValue();
			}
			
			if(customerTypeVal == null || customerTypeVal == '' || customerTypeVal == undefined){
				miser.showInfoMsg('请先确定是否使用新价格城市');
				return;
			}
		}
		else {
			customerTypeVal = 'Y';
		}
		
		miser.showInfoMsg('上传完成');
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request( {  
	       url : '../pricecard/priceCustomer!excelImpl.action?customerType='+customerTypeVal,  
	       method : 'post',
	       timeout:1800000,
	       params : {  
	    	   'priceCustomerVo.filePath' : outFileName  
	          },  
	          success : function(response, options) {  
	              Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText);
	              // succCount:成功条数,failCount:失败条数, list:明细记录.
	              var msg="成功：" + responseArray.succCount + "条；失败：" + responseArray.failCount + "条；";
	              if(!Ext.isEmpty(responseArray.error))
	              {
	              	Ext.MessageBox.show({title: '失败',msg: '导入失败:<br/>' + responseArray.error + ";<br/>请修改excel重新上传.",buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});
	              }
	              else
	              {
	              	miser.showInfoMsg(msg);         
	              	parent.store.loadData(responseArray.list,false);
	              }
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

/*
 * id:导出明细的父表主键
 * exportType:是从修改页面 还是查看页面导出
 * customerType:是导出老价格城市 还是导出新价格城市
 */
function down(id,exportType,customerType)
{
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params={
		'priceCustomerVo.priceCustomerEntity.id':id,
		'priceCustomerVo.priceCustomerEntity.customerType':Ext.isEmpty(customerType) ? 'N':customerType,
		'priceCustomerVo.exportType':exportType
	 };
	 Ext.Ajax.request( {  
	       url : '../pricecard/priceCustomer!excelExport.action',  
	       method : 'post',  
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

function showPriceCustomerTipMsg(message){
	if(message == 'priceCustomer.effectiveTimeInvalid')
	{
		//生效时间必须晚于当前时间一小时
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.effectiveAfterOneHour'));
	}
	else if(message == 'priceCustomer.priceDiscountExistRepeatTimezone')
	{
		//"该客户已有正在生效的专属价格，且有效时间重合，不能提交！"
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.validRecrodExistRepeatTime'));
	}
	else if(message == 'priceCustomer.EffectiveTimeDoesNotBetweenContractStartEndTime'){
		//生效时间必须在客户合同的开始时间和结束时间区间内
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.EffectiveTimeDoesNotBetweenContractStartEndTime'));
	}
	else if(message == 'priceCustomer.ContractStartOrEndTimeInvalid'){
		//客户合同的开始时间或结束时间为空不合法
		miser.showErrorMes(pricecard.priceCustomer.i18n('priceCustomer.ContractStartOrEndTimeInvalid'));
	}
	else if(message == 'priceCustomer.subDetailSendPriceCityNameCannotNull'){
		//出发价格城市名称不能为空
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.subDetailIsInvalid'));
	}
	else if(message == 'priceCustomer.subDetailSendPriceCityCodeCannotNull'){
		//出发价格城市编码不能为空
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.subDetailIsInvalid'));
	}
	else if(message == 'priceCustomer.subDetailArrivePriceCityNameCannotNull'){
		//到达价格城市名称不能为空
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.subDetailIsInvalid'));
	}
	else if(message == 'priceCustomer.subDetailArrivePriceCityCodeCannotNull'){
		//到达价格城市编码不能为空
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.subDetailIsInvalid'));						
	}
	else if(message == 'priceCustomer.subDetailTransTypeNameCannotNull'){
		//运输类型名称不能为空
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.subDetailIsInvalid'));
	}
	else if(message == 'priceCustomer.subDetailTransTypeCodeCannotNull'){
		//运输类型编码不能为空
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.subDetailIsInvalid'));
	}
	else if(message == 'priceCustomer.subDetailWeightPriceCannotNull'){
		//重量单价不能为空
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.subDetailIsInvalid'));
	}
	else if(message == 'priceCustomer.subDetailVolumePriceCannotNull'){
		//体积单价不能为空
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.subDetailIsInvalid'));
	}
	else if(message == 'priceCustomer.passedInvalidTimeMustThanCurTime'){
		//失效时间必须大于当前时间(已过期数据修改时 会加此校验)
		miser.showErrorMes(pricecard.priceCustomer.i18n('pricecard.priceCustomer.passedInvalidTimeMustThanCurTime'));
	}	
	else
	{
		miser.showErrorMes(message);
	}
}

//新增 修改时 客户端对明细数据的校验
function priceCustomerSubDetailCheck(priceCustomerSubEntityItems){
	
	if(priceCustomerSubEntityItems.length == 0){
		miser.showInfoMsg(pricecard.priceCustomer.i18n('pricecard.priceCustomer.subDetailCannotBeNull'));
		return;
	}

	//EXT 封装了很多属性 不能直接将上面一行返回的数据提交到后台 需要将后台明确需要的数据取出传递
	var priceCustomerSubEntityList = [];
	
	var fieldCheckNullObj = {
			'arrivePriceCity':true,
			'transTypeCode':true,
			'weightPrice':true,
			'volumePrice':true
			
	};
	for(var i=0;i<priceCustomerSubEntityItems.length;i++)
	{
		var curItem = priceCustomerSubEntityItems[i];
		var curItemData = curItem.data;
		curItemData.id = '';
		curItemData.active = 'Y';
		
		//到达价格城市是否为空
		if(Ext.isEmpty(curItemData.arrivePriceCity)){
			fieldCheckNullObj.arrivePriceCity = false;
			break;
		}
		
		//运输类型是否为空
		if(Ext.isEmpty(curItemData.transTypeCode)){
			fieldCheckNullObj.transTypeCode = false;
			break;
		}
		
		//重量单价
		if(Ext.isEmpty(curItemData.weightPrice)){
			fieldCheckNullObj.weightPrice = false;
			break;
		}				
		
		//体积单价
		if(Ext.isEmpty(curItemData.volumePrice)){
			fieldCheckNullObj.volumePrice = false;
			break;
		}				
		
		priceCustomerSubEntityList.push(curItemData);
	}
	
	//到达价格城市为空
	if(!fieldCheckNullObj.arrivePriceCity){
		showPriceCustomerTipMsg('priceCustomer.subDetailArrivePriceCityNameCannotNull');
		return null;
	}
	
	//运输类型为空
	if(!fieldCheckNullObj.transTypeCode){
		showPriceCustomerTipMsg('priceCustomer.subDetailTransTypeNameCannotNull');
		return null;
	}
	
	//重量单价为空
	if(!fieldCheckNullObj.weightPrice){
		showPriceCustomerTipMsg('priceCustomer.subDetailWeightPriceCannotNull');
		return null;
	}	
	
	//体积单价为空
	if(!fieldCheckNullObj.volumePrice){
		showPriceCustomerTipMsg('priceCustomer.subDetailVolumePriceCannotNull');
		return null;
	}		
	
	return priceCustomerSubEntityList;
}

//用于实现修改页面 查看详情页面的根据 到达价格城市 搜索功能
function subCustomerDetailSearch(grid,searchId){
	var subViewGrid = grid
	var sm = subViewGrid.getSelectionModel();
	var searchArrivePriceCityVal = Ext.String.trim(Ext.getCmp(searchId).getValue());
	if(Ext.isEmpty(searchArrivePriceCityVal)){
		miser.showErrorMes('请填写搜索的内容');
		return;
	}
	
	//用于存放最后匹配搜索的值的数据集
	var toSelectedRowDataArr = [];
	
	//用于存放未匹配搜索值的数据集
	var notSelectedRowDataArr = [];

    //用于存放需要搜索的字段
	var keyArr = ['arrivePriceCityName','transTypeName','weightPrice','volumePrice','addFee','lowestFee','freightFeeSectionName','fuelFeeSectionName','weightDiscount','volumeDiscount'];
	
	//存放搜索字段的值集合
	var curFieldVal = null;

	var isToSelected = false;

	//循环遍历搜索匹配搜索的值的数据
	for(var j=0;j<subViewGrid.store.data.items.length;j++){

		valArr = [];

		var curRowData = subViewGrid.store.data.items[j];

		for(var k=0;k<keyArr.length;k++){
			curFieldVal = curRowData.data[keyArr[k]];
			if(!Ext.isEmpty(curFieldVal)){
				var existedIdx = curFieldVal.indexOf(searchArrivePriceCityVal);
				if(existedIdx != -1){
					isToSelected = true;
					toSelectedRowDataArr.push(curRowData);
					break;
				}
			}
		}

		if(isToSelected == false){
			notSelectedRowDataArr.push(curRowData);
		}

		isToSelected = false;
	}
	
	//将当前 grid store 的数据全移除 界面此时会自动刷新为空
	subViewGrid.store.removeAll();
	
	//将要重新添加的两个数组合并 toSelectedRowDataArr 的明细放前面
	//注意是将 返回值返回 而不是直接加入到 toSelectedRowDataArr
	var storeLocalReloadDataArr = toSelectedRowDataArr.concat(notSelectedRowDataArr);
	
	//重新加载对应明细数据
	subViewGrid.store.loadData(storeLocalReloadDataArr);

	//调用 select 选中传入的数据明细
	sm.select(toSelectedRowDataArr);

	toSelectedRowDataArr = [];
	notSelectedRowDataArr = [];
	storeLocalReloadDataArr = [];
	isToSelected = false;
}