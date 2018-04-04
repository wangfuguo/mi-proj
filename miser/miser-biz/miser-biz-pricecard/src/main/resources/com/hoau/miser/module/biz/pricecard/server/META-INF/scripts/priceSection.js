Ext.apply(Ext.form.VTypes,{
	strLenCheck:function(val,field){
	    var len = 0;
	    for (var i=0; i<val.length; i++) { 
		     var c = val.charCodeAt(i); 
		    
		     //单字节加1 
		     if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
		       len++; 
		     } else { 
		      len+=2; 
		     } 
	    } 
	    
	    if(len > field.maxCharLen){
	    	return false;
	    } else {
	    	return true;
	    }
	},
	strLenCheckText:'输入字符过长'
});


pricecard.priceSection.sectionedItem = 'SECTION_USED_TYPE';
pricecard.priceSection.sectionAccodingItem = 'SECTION_ACCODING_ITEM';
pricecard.priceSection.active = 'IS_ACTIVE';
pricecard.priceSection.priceType = 'PRICE_TYPE';
var sectionId = null;
var uploadsectionId = null;
// 新增时修改列的行号
var adddataIndex = null;
// 状态
function stateTypeRender(value) {
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
	var queryForm = Ext.getCmp('queryForm');
    if (queryForm != null) {
        	params = {
        	'priceSectionVo.priceSectionEntity.name': queryForm.getForm().findField('name').getValue(),
            'priceSectionVo.priceSectionEntity.shortCode': queryForm.getForm().findField('shortCode').getValue(),
            'priceSectionVo.priceSectionEntity.suitProduct': queryForm.getForm().findField('suitProduct').getValue(),
            'priceSectionVo.priceSectionEntity.sectionedItem': queryForm.getForm().findField('sectionedItem').getValue(),
            'priceSectionVo.priceSectionEntity.active': queryForm.getForm().findField('active').getValue(),
            'priceSectionVo.priceSectionEntity.suitoa': queryForm.getForm().findField('suitoa').getValue()
        };
    }
	Ext.Ajax.request( {  
	       url : '../pricecard/priceSection!excelExport.action',  
	       timeout: 280000,
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
	            Ext.MessageBox.show({title: '失败',msg: '导出失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
	        }
	    });  
	
}
/**
 * 优惠分段列表model
 */
Ext.define('Miser.model.priceSectionEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'code',
		// 优惠分段编号
		type : 'string'
	}, {
		name : 'shortCode',
		// 优惠分段编号
		type : 'string'
	}, {
		name : 'name',
		// 优惠分段名称
		type : 'string'
	},{
		name: 'suitProductName',
		type: 'string'
	}, {
		name : 'suitProduct',
		type : 'string'
	}, {
		name : 'sectionedItem',
		type : 'string'
	}, {
		name : 'remark',
		// 备注
		type : 'string'
	}, {
		name : 'active',
		// 是否有效
		type : 'string'
	},{
		name : 'suitoa',
		// 是否适用oa
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
 * 优惠分段列表model
 */
Ext.define('Miser.model.priceSectionSubEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'sectionId',
		// 分段项目编号
		type : 'string'
	}, {
		name : 'startValue',
		// 段起
		type : 'number'
	}, {
		name : 'endValue',
		// 段止
		type : 'number'
	}, {
		name : 'sectionAccodingItem',
		// 计算方式
		type : 'string'
	}, {
		name : 'priceType',
		// 费用类型
		type : 'string'
	}, {
		name : 'price',
		// 费用
		type : 'number'
	}, {
		name : 'calcAlone',
		// 是否单独进行结算
		type : 'string'
	}, {
		name : 'minPrice',
		// 最低收费
		type : 'number'
	}, {
		name : 'maxPrice',
		// 最高收费
		type : 'number'
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
	} ]
});

/**
 * 查询表单
 */
Ext
		.define(
				'Miser.view.priceSection.QueryForm',
				{
					extend : 'Ext.form.Panel',
					id : 'queryForm',
					frame : true,
					width : document.documentElement.clientWidth - 20,
					title : pricecard.priceSection
							.i18n('miser.common.queryCondition'),// '查询条件',
					collapsible : true,
					region : 'north',
					autoScroll : true,
					defaults : {
						labelWidth : 0.3,
						columnWidth : 0.6,
						margin : '8 10 5 10',
						labelAlign : 'right'
					},
					// defaultType: 'textfield',
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
								me.items = [
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
											name : 'name',
											fieldLabel : pricecard.priceSection
													.i18n('pricecard.priceSection.name'),// '优惠分段名称',
											xtype : 'textfield', 
											margin:'5px 0px 5px 0px',
											labelAlign:'right'
										},{
											name : 'shortCode',
											fieldLabel : pricecard.priceSection
													.i18n('pricecard.priceSection.shortCode'),
											xtype : 'textfield',  
											margin:'5px 0px 5px 0px',
											labelAlign:'right'
										},{
											name : 'suitProduct',
											fieldLabel : pricecard.priceSection
													.i18n('pricecard.priceSection.suitProduct'),
											xtype : 'transtypecombselector',
											containsRoot : 'Y', 
											margin:'5px 0px 5px 0px',
											labelAlign:'right'
										},{
							    			name : 'sectionedItem',
											fieldLabel : pricecard.priceSection
													.i18n('pricecard.priceSection.sectionedItem'),
											xtype : 'dataDictionarySelector',anyRecords:all,
											termsCode : pricecard.priceSection.sectionedItem, 
							    			margin:'5px 0px 5px 0px',
							    			labelAlign:'right'
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
												name : 'active',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.active'),
												xtype : 'dataDictionarySelector',anyRecords:all,
												termsCode : pricecard.priceSection.active,  
        										margin:'5px 0px 5px 0px',
							    				labelAlign:'right'
											},
											{
												name : 'suitoa',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.suitoa'),
												xtype: 'yesnocombselector',
											 	isShowAll : true,
												labelAlign:'right',
        										margin:'5px 0px 5px 0px',
							    				labelAlign:'right'
											} ]
										}

								],
								me.buttons = [
										{
											text : pricecard.priceSection
													.i18n('miser.common.query'),
											handler : function() {
												if (me.getForm().isValid()) {
													me.up().getPriceSectionGrid().getPagingToolbar().moveFirst();
													;
												}
											}
										},
										{
											text : pricecard.priceSection
													.i18n('miser.common.reset'),
											handler : function() {
												me.getForm().reset();
											}
										},
										{
								        	id:'downbutton_id',
								        	text: pricecard.priceSection
											.i18n('pricecard.priceSection.downbutton'),//导出
								            alias: 'widget.downloadbutton',
								        	glyph : 0xf019,
								            disabled:false,
								        	cls: 'download-btn-item',
								            handler: function() {
								            	down();
								            }
								        }];
						me.callParent([ cfg ]);
					}
				});

/**
 * 增加表单
 */
Ext
		.define(
				'Miser.view.pricecard.priceSectionAddForm',
				{
					extend : 'Ext.form.Panel',
					frame : true,
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
									items : [
											{
												name : 'id',
												fieldLabel : 'id',
												xtype : 'textfield',
												hidden : true
											},
											{
												name : 'code',
												fieldLabel : 'code',
												xtype : 'textfield',
												hidden : true
											},
											{
												name : 'name',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.name'),
												xtype : 'textfield',
												allowBlank : false,
												beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
												vtype:'strLenCheck',
												maxCharLen:50,
												vtypeText:'优惠分段名称不能超过50个字符'
											},
											{
												name : 'shortCode',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.shortCode'),
												xtype : 'textfield',
												allowBlank : false,
												beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
												vtype:'strLenCheck',
												maxCharLen:20,
												vtypeText:'优惠分段简码不能超过20个字符'												
											},
											{
												name : 'suitProduct',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.suitProduct'),
												xtype : 'transtypecombselector',
												showAll : false,
												containsRoot : 'Y',
												allowBlank : false,
												beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
											}]
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
												name : 'sectionedItem',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.sectionedItem'),
												allowBlank : false,
												beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
												xtype : 'dataDictionarySelector',
												termsCode : pricecard.priceSection.sectionedItem
											},
											{
												name : 'suitoa',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.suitoa'),
												 xtype: 'yesnocombselector',
											 	isShowAll : false,
												labelAlign:'right',
											 	allowBlank:false
											}
									]
								}
								,
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
										fieldLabel : pricecard.priceSection
												.i18n('pricecard.priceSection.remark'), // '备注',
										name : 'remark',
										width : document.documentElement.clientWidth *0.963,
										xtype : 'textarea'
									} ]
								} ];
						me.callParent([ cfg ]);
					}
				});
/**
 * 修改表单
 */
Ext
		.define(
				'Miser.view.pricecard.priceSectionUpdateForm',
				{
					extend : 'Ext.form.Panel',
					frame : true,
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
									items : [
											{
												name : 'id',
												fieldLabel : 'id',
												xtype : 'textfield',
												hidden : true
											},
											{
												name : 'code',
												fieldLabel : 'code',
												xtype : 'textfield',
												hidden : true
											},
											{
												name : 'name',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.name'),
												xtype : 'textfield',
												allowBlank : false,
												beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
												vtype:'strLenCheck',
												maxCharLen:50,
												vtypeText:'优惠分段名称不能超过50个字符'
											},
											{
												name : 'shortCode',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.shortCode'),
												xtype : 'textfield',
												allowBlank : false,
												beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
												vtype:'strLenCheck',
												maxCharLen:20,
												vtypeText:'优惠分段简码不能超过20个字符'
											},
											{
												name : 'suitProduct',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.suitProduct'),
												xtype : 'textfield',
												xtype : 'transtypecombselector',
												showAll : false,
												containsRoot : 'Y',
												beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
											}]
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
												name : 'sectionedItem',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.sectionedItem'),
												allowBlank : false,
												beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
												xtype : 'dataDictionarySelector',
												termsCode : pricecard.priceSection.sectionedItem
											},
											{
												name : 'suitoa',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.suitoa'),
												 xtype: 'yesnocombselector',
											 	isShowAll : false,
												labelAlign:'right',
											 	allowBlank:false
											}
									]
								}
								,
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
										fieldLabel : pricecard.priceSection
												.i18n('pricecard.priceSection.remark'), // '备注',
										name : 'remark',
										width : document.documentElement.clientWidth *0.963,
										xtype : 'textarea'
									} ]
								} ];
						me.callParent([ cfg ]);
					}
				});
/**
 * 查询表单
 */
Ext
		.define(
				'Miser.view.pricecard.priceSectionQueryForm',
				{
					extend : 'Ext.form.Panel',
					frame : true,
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
									items : [
											{
												name : 'id',
												fieldLabel : 'id',
												xtype : 'textfield',
												hidden : true
											},
											{
												name : 'code',
												fieldLabel : 'code',
												xtype : 'textfield',
												hidden : true
											},
											{
												name : 'name',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.name'),
												xtype : 'textfield',
												readOnly:true

											},
											{
												name : 'shortCode',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.shortCode'),
												xtype : 'textfield',
												readOnly:true
											},
											{
												name : 'suitProductName',
												fieldLabel : pricecard.priceSection.i18n('pricecard.priceSection.suitProduct'),
												readOnly:true,
												xtype : 'textfield'
											},
											{
												name : 'suitProduct',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.suitProduct'),
												xtype : 'textfield',
												hidden : true
											},
											{
												name : 'sectionedItem',
												fieldLabel : pricecard.priceSection
														.i18n('pricecard.priceSection.sectionedItem'),
												readOnly:true,
												xtype : 'dataDictionarySelector',
												termsCode : pricecard.priceSection.sectionedItem
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
										fieldLabel : pricecard.priceSection
												.i18n('pricecard.priceSection.remark'), // '备注',
										name : 'remark',
										width : document.documentElement.clientWidth *0.963,
										xtype : 'textarea'
									} ]
								} ];
						me.callParent([ cfg ]);
					}
				});
// 增加窗体
Ext.define('Miser.view.pricecard.priceSectionAddWindow', {
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
			me.getPriceSectionAddForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getPriceSectionAddForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			getGirdAddStore().removeAll();
		}
	},
	priceSectionAddForm : null,
	getPriceSectionAddForm : function() {
		if (Ext.isEmpty(this.priceSectionAddForm)) {
			this.priceSectionAddForm = Ext
					.create('Miser.view.pricecard.priceSectionAddForm');
		}
		return this.priceSectionAddForm;
	},
	submitPriceSectionAddForm : function() {
		var me = this;
		if (me.getPriceSectionAddForm().getForm().isValid()) { // 校验form是否通过校验
			Ext.Msg.wait('处理中，请稍后...', '提示');
			var priceSectionEntity = new Miser.model.priceSectionEntity();
			me.getPriceSectionAddForm().getForm().updateRecord(
					priceSectionEntity); // 将FORM中数据设置到MODEL里面
			var curData = priceSectionEntity.data;
			var lstAddRecord = new Array();
			getGirdAddStore().each(function(record) {
				lstAddRecord.push(record.data);
			});
			if(Ext.isEmpty(lstAddRecord)){
				miser.showWoringMessage('请填写明细信息'); // 请选择一条进行作废操作！
				return; // 没有则提示并返回
			}
			var params = {
				'priceSectionVo' : {
					'priceSectionEntity' : curData
				},
				'priceSectionSubList' : Ext.encode(lstAddRecord)
			}
			var successFun = function(json) {
				Ext.Msg.hide();
				var message = json.message;
				miser.showInfoMsg(message); // 提示新增成功
				me.close();
				me.parent.getStore().load(); // 成功之后重新查询刷新结果集
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					Ext.Msg.hide();
					miser.showErrorMes(pricecard.priceSection
							.i18n('miser.pricecard.timeout')); // 请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message); // 提示失败原因
				}
			};
			miser.requestJsonAjax('priceSection!addPriceSection.action',
					params, successFun, failureFun); // 发送AJAX请求
		}
	},
	priceSectionSubAddGrid : null,
	getPriceSectionSubAddGrid : function() {
		if (Ext.isEmpty(this.priceSectionSubAddGrid)) {
			this.priceSectionSubAddGrid = Ext
					.create('Miser.view.priceSectionSub.AddGrid');
			this.priceSectionSubAddGrid.parent = this; // 父元素
		}
		return this.priceSectionSubAddGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : pricecard.priceSection.i18n('miser.common.save'),// '保存',
			// 保存
			/* margin : '0 0 0 55', */
			handler : function() {
				me.submitPriceSectionAddForm();
			}
		}, {
			text : pricecard.priceSection.i18n('miser.common.cancel'),// '取消',
			// 取消
			handler : function() {
				me.close();
			}
		} ];
		me.items = [ me.getPriceSectionAddForm(),
				me.getPriceSectionSubAddGrid() ];
		me.callParent([ cfg ]);
	}
});
/**
 * 修改优惠分段窗口
 */
Ext.define('Miser.view.pricecard.priceSectionUpdateWindow', {
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
	priceSectionEntity : null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getPriceSectionUpdateForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getPriceSectionUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			getGirdAddStore().removeAll();
			me.getPriceSectionUpdateForm().getForm().loadRecord(
					new Miser.model.priceSectionEntity(me.priceSectionEntity));
			var codeCombo = me.getPriceSectionUpdateForm().getForm().findField('suitProduct');
			codeCombo.setValue(me.priceSectionEntity.suitProduct);
			codeCombo.getStore().load();
		}
	},
	priceSectionUpdateForm : null,
	getPriceSectionUpdateForm : function() {
		if (Ext.isEmpty(this.priceSectionUpdateForm)) {
			this.priceSectionUpdateForm = Ext
					.create('Miser.view.pricecard.priceSectionUpdateForm');
		}
		return this.priceSectionUpdateForm;
	},
	submitDriver : function() {
		var me = this;
		if (me.getPriceSectionUpdateForm().getForm().isValid()) { // 校验form是否通过校验
			var priceSectionEntity = new Miser.model.priceSectionEntity();
			me.getPriceSectionUpdateForm().getForm().updateRecord(
					priceSectionEntity); // 将FORM中数据设置到MODEL里面
			var curData = priceSectionEntity.data;
			var lstUpdateRecord = new Array();
			getGirdAddStore().each(function(record) {
				lstUpdateRecord.push(record.data);
			});
			if(Ext.isEmpty(lstUpdateRecord)){
				miser.showWoringMessage('请填写明细信息'); // 请选择一条进行作废操作！
				return; // 没有则提示并返回
			}
			var params = {
				'priceSectionVo' : {
					'priceSectionEntity' : curData
				},
				'priceSectionSubList' : Ext.encode(lstUpdateRecord)
			}
			var successFun = function(json) {
				var message = json.message;
				miser.showInfoMsg(message); // 提示修改成功
				me.close();
				me.parent.getStore().load(); // 成功之后重新查询刷新结果集
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes('连接超时'); // 请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message); // 提示失败原因
				}
			};
			miser.requestJsonAjax('priceSection!updatePriceSection.action',
					params, successFun, failureFun); // 发送AJAX请求
		}
	},
	priceSectionSubUpdateGrid : null,
	getPriceSectionSubUpdateGrid : function() {
		if (Ext.isEmpty(this.priceSectionSubUpdateGrid)) {
			this.priceSectionSubUpdateGrid = Ext
					.create('Miser.view.priceSectionSub.UpdateGrid');
			this.priceSectionSubUpdateGrid.parent = this; // 父元素
		}
		return this.priceSectionSubUpdateGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : pricecard.priceSection.i18n('miser.common.save'), // 保存
			margin : '0 0 0 305',
			handler : function() {
				me.submitDriver();
			}
		}, {
			text : pricecard.priceSection.i18n('miser.common.cancel'), // 取消
			handler : function() {
				me.close();
			}
		} ];
		me.items = [ me.getPriceSectionUpdateForm(),
				me.getPriceSectionSubUpdateGrid() ];
		me.callParent([ cfg ]);
	}
});

/**
 * 查询优惠分段窗口
 */
Ext.define('Miser.view.pricecard.priceSectionQueryWindow', {
	extend : 'Ext.window.Window',
	title : '查询',// 修改
	closable : true,
	parent : null, // 父元素
	modal : true,
	resizable : false, // 可以调整窗口的大小
	closeAction : 'hide', // 点击关闭是隐藏窗口
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	priceSectionEntity : null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getPriceSectionUpdateForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getPriceSectionUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			getGirdAddStore().removeAll();
			me.getPriceSectionUpdateForm().getForm().loadRecord(
					new Miser.model.priceSectionEntity(me.priceSectionEntity));
			//如果不是待生效或生效中的数据，修改按钮设置不可编辑
			if (me.priceSectionEntity.active == 'N') {
				Ext.getCmp('Miser_biz_PriceSection_ShowDetail_Update_Button').setDisabled(true);
			} else {
				Ext.getCmp('Miser_biz_PriceSection_ShowDetail_Update_Button').setDisabled(false);
			}
		}
	},
	priceSectionUpdateForm : null,
	getPriceSectionUpdateForm : function() {
		if (Ext.isEmpty(this.priceSectionUpdateForm)) {
			this.priceSectionUpdateForm = Ext
					.create('Miser.view.pricecard.priceSectionQueryForm');
		}
		return this.priceSectionUpdateForm;
	},
	priceSectionSubUpdateGrid : null,
	getPriceSectionSubUpdateGrid : function() {
		if (Ext.isEmpty(this.priceSectionSubUpdateGrid)) {
			this.priceSectionSubUpdateGrid = Ext
					.create('Miser.view.priceSectionSub.QueryGrid');
			this.priceSectionSubUpdateGrid.parent = this; // 父元素
		}
		return this.priceSectionSubUpdateGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			id : 'Miser_biz_PriceSection_ShowDetail_Update_Button',
			text : pricecard.priceSection.i18n('miser.common.update'), // 保存
			margin : '0 0 0 305',
			handler : function() {
				loadMore(uploadsectionId, getIPriceSectionUpdateWindow());
				me.close();
			}
		} ];
		me.items = [ me.getPriceSectionUpdateForm(),
				me.getPriceSectionSubUpdateGrid() ];
		me.callParent([ cfg ]);
	}
});

/**
 * 优惠分段信息表格
 */
Ext
		.define(
				'Miser.view.priceSection.Grid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					autoScroll : true,
					height : document.documentElement.clientHeight*0.75,
					stripeRows : true,
					selType : "rowmodel",
					emptyText : '查询结果为空',
					columnLines : true,
					viewConfig : {
						enableTextSelection : true
					},
					priceSectionAddWindow : null,
					getPriceSectionAddWindow : function() {
						if (Ext.isEmpty(this.priceSectionAddWindow)) {
							this.priceSectionAddWindow = Ext
									.create('Miser.view.pricecard.priceSectionAddWindow');
							this.priceSectionAddWindow.parent = this; // 父元素
						}
						return this.priceSectionAddWindow;
					},
					priceSectionSubGridWindow : null,
					getpriceSectionSubGridWindow : function() {
						if (Ext.isEmpty(this.priceSectionSubGridWindow)) {
							this.priceSectionSubGridWindow = Ext
									.create('Miser.view.pricecard.priceSectionSubGridWindow');
							this.priceSectionSubGridWindow.parent = this; // 父元素
						}
						return this.priceSectionSubGridWindow;
					},
					priceSectionUpdateWindow : null,
					getPriceSectionUpdateWindow : function() {
						if (Ext.isEmpty(this.priceSectionUpdateWindow)) {
							this.priceSectionUpdateWindow = Ext
									.create('Miser.view.pricecard.priceSectionUpdateWindow');
							this.priceSectionUpdateWindow.parent = this;
						}
						return this.priceSectionUpdateWindow;
					},
					priceSectionQueryWindow : null,
					getPriceSectionQueryWindow : function() {
						if (Ext.isEmpty(this.priceSectionQueryWindow)) {
							this.priceSectionQueryWindow = Ext
									.create('Miser.view.pricecard.priceSectionQueryWindow');
							this.priceSectionQueryWindow.parent = this; // 父元素
						}
						return this.priceSectionQueryWindow;
					},
					updatePriceSection : function() {
						var me = this;
						var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
						if (selections.length != 1) { // 判断选中了一条
							miser.showWoringMessage('请选择一条进行修改操作'); // 请选择一条进行作废操作！
							return; // 没有则提示并返回
						}
						try{
						var flag = selections[0].get('active');
						if ('N' == flag) {
							miser.showWoringMessage('该数据已作废,不可修改'); // 请选择一条进行作废操作！
							return; // 没有则提示并返回
						}
						}catch(e){}
						var id = selections[0].get('id');
						uploadsectionId=id;
						var updateWindow = me.getPriceSectionUpdateWindow(); // 获得修改窗口
						loadMore(id, updateWindow);
					},
					queryPriceSectionSubList : function() {
						var me = this;
						var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
						if (selections.length != 1) { // 判断选中了一条
							miser.showWoringMessage('请选择一条进行修改操作'); // 请选择一条进行作废操作！
							return; // 没有则提示并返回
						}
						var id = selections[0].get('id');
						uploadsectionId=id;
						var QueryWindow = me.getPriceSectionQueryWindow(); // 获得修改窗口
						loadMore(id, QueryWindow);
						// 设置form不可用
						var fielsds = QueryWindow.getPriceSectionUpdateForm();
						fielsds.items.each(function(items) {
							items.items.each(function(item) {
								item.disabled = true;
							});
						});
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
					deletePriceSection : function() {
						var me = this;
						var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
						if (selections.length != 1) { // 判断是否至少选中了一条
							miser
									.showWoringMessage(pricecard.priceSection
											.i18n('pricecard.priceSection.oneselected')); // 请选择一条进行作废操作！
							return; // 没有则提示并返回
						}
						// miser.showQuestionMes(pricecard.priceSection.i18n('miser.pricecard.isdelete'),//是否删除
						miser
								.showQuestionMes(
										'删除',// 是否删除
										function(e) {
											if (e == 'yes') { // 询问是否删除，是则发送请求
												var id = selections[0]
														.get('id');
												var params = {
													'priceSectionVo' : {
														'priceSectionEntity' : {
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
														miser
																.showErrorMes(pricecard.priceSection
																		.i18n('pricecard.priceSection.timeout')); // 请求超时
													} else {
														var message = json.message;
														miser
																.showErrorMes(message);
													}
												};
												miser
														.requestJsonAjax(
																'priceSection!deletePriceSection.action',
																params,
																successFun,
																failureFun);
											}
										});
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
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
											dataIndex : 'name',
											width : 120,
											text : pricecard.priceSection
													.i18n('pricecard.priceSection.name'),
											align : 'center'
										// '优惠分段名称'
										},
										{
											dataIndex : 'shortCode',
											width : 120,
											text : pricecard.priceSection
													.i18n('pricecard.priceSection.shortCode'),
											align : 'center'
										// '优惠分段编码'
										},
										{
											text : pricecard.priceSection
													.i18n('pricecard.priceSection.suitProduct'), // '使用运输类型',
											dataIndex : 'suitProductName',
											width : 120,
											align : 'center'
										},
										{
											text : pricecard.priceSection
													.i18n('pricecard.priceSection.sectionedItem'), // '使用费用类型',
											dataIndex : 'sectionedItem',
											width : 120,
											renderer : function(value) {
												return miser
														.changeCodeToNameStore(
																getDataDictionary()
																		.getDataDictionaryStore(
																				pricecard.priceSection.sectionedItem),
																value);
											},
											align : 'center'
										},
										{
											text : pricecard.priceSection
													.i18n('pricecard.priceSection.remark'), // '备注',
											dataIndex : 'remark',
											width : 100,
											align : 'center'
										},
										{
											text : pricecard.priceSection
													.i18n('pricecard.priceSection.active'), // '是否有效',
											dataIndex : 'active',
											renderer : function(value) {
												return stateTypeRender(value);
											},
											width : 100,
											align : 'center'
										},{
											text : pricecard.priceSection
													.i18n('pricecard.priceSection.suitoa'), // '是否适用oa',
											dataIndex : 'suitoa',
											renderer : function(value) {
												return stateTypeRender(value);
											},
											width : 100,
											align : 'center'
										},
										{
											text : pricecard.priceSection
													.i18n('pricecard.priceSection.modifyUser'), // '修改用户',
											dataIndex : 'modifyUser',
											width : 100,
											align : 'center'
										},
										{
											text : pricecard.priceSection
													.i18n('pricecard.priceSection.modifyDate'), // '修改时间',
											dataIndex : 'modifyDate',
											renderer : function(value) {
												return dateRender(value);
											},
											align : 'center'
										} ], me.store = Ext.create(
										'Miser.store.PriceSectionStore', {
											autoLoad : false
										});
						me.selModel = Ext.create('Ext.selection.CheckboxModel',
								{ // 多选框
									mode : 'MULTI',
									checkOnly : true
								});
						me.tbar = [
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.add'),// '新增',
									xtype : 'addbutton',
									// 新增
									width : 80,
									handler : function() {
										me.getPriceSectionAddWindow().show();
									}
								},
								{
									id : 'miser_biz_pricecard_priceSection_SectionUpdate_id',
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.update'),// '修改',
									xtype : 'updatebutton',
									width : 80,
									handler : function() {
										me.updatePriceSection();
									}
								},
								{
									id : 'miser_biz_pricecard_priceSection_del_id',
									text : pricecard.priceSection
											.i18n('miser.common.delete'),// '删除',
									xtype : 'deletebutton',
									disabled : true,
									// 作废
									width : 80,
									handler : function() {
										me.deletePriceSection();
									}
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.detailBtn'),// '明细列表',
									xtype : 'searchbutton',
									width : 100,
									handler : function() {
										me.queryPriceSectionSubList();
									}
								} ];
						me.bbar = me.getPagingToolbar();
								me.selModel = Ext.create(
												'Ext.selection.CheckboxModel',
												{
													listeners : {
														selectionchange : function(
																sm, selections) {
															try{
															var flag = selections[0].get('active');
															if ('N' == flag) {
																Ext.getCmp('miser_biz_pricecard_priceSection_SectionUpdate_id').setDisabled(true);
																Ext.getCmp('miser_biz_pricecard_priceSection_del_id').setDisabled(true);
															} else {
																Ext.getCmp('miser_biz_pricecard_priceSection_SectionUpdate_id').setDisabled(false);
																Ext.getCmp('miser_biz_pricecard_priceSection_del_id').setDisabled(false);
															}
															}catch(e){}
														}
													}
												}),
								// Ext.setGlyphFontFamily('FontAwesome');
								me.callParent([ cfg ]);
					}
				});
var IpriceSectionUpdateWindow=null;
function getIPriceSectionUpdateWindow(){
if (Ext.isEmpty(IpriceSectionUpdateWindow)) {
		IpriceSectionUpdateWindow = Ext.create('Miser.view.pricecard.priceSectionUpdateWindow');
}
return IpriceSectionUpdateWindow;
}
function loadMore(id, updateWindow) {
	var params = {
		'priceSectionVo' : {
			'priceSectionEntity' : {
				'id' : id
			}
		}
	};
	var successFun = function(json) {
		updateWindow.priceSectionEntity = json.priceSectionVo.priceSectionEntity;
		updateWindow.show(); // 显示修改窗口
		updateWindow.getPriceSectionUpdateForm().getForm().findField('id')
				.setValue(updateWindow.priceSectionEntity.id);
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
	miser.requestJsonAjax('priceSection!queryPriceSectionById.action', params,
			successFun, failureFun);
	// 子表信息
	var paramsOne = {
		'priceSectionSubVo' : {
			'priceSectionSubEntity' : {
				'sectionId' : id
			}
		},
		'page' : 1,
		'limit' : 20,
		'start' : 0
	};
	var successFunOne = function(json) {
		// 修改加载明细列表
		var SectionSubList = json.priceSectionSubVo.priceSectionSubList;
		for (var i = 0; i < SectionSubList.length; i++) {
			var priceSectionSubEntity = new Miser.model.priceSectionSubEntity();
			getGirdAddStore().add(SectionSubList[i]);
		}
	};
	// 主表信息
	miser.requestJsonAjax('priceSection!queryListByParamSub.action', paramsOne,
			successFunOne, failureFun);
}
/**
 * 弹出明细列表window
 */
Ext.define('Miser.view.pricecard.priceSectionSubGridWindow', {
	extend : 'Ext.window.Window',
	title : '明细列表',// 明细列表
	closable : true,
	parent : null, // 父元素
	modal : true,
	height : miser.getBrowserHeight() - 120,
	resizable : false, // 可以调整窗口的大小
	closeAction : 'hide', // 点击关闭是隐藏窗口
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	priceSectionSubGrid : null,
	getPriceSectionSubGrid : function() {
		if (Ext.isEmpty(this.priceSectionSubGrid)) {
			this.priceSectionSubGrid = Ext
					.create('Miser.view.priceSectionSub.Grid');
			this.priceSectionSubGrid.parent = this; // 父元素
		}
		return this.priceSectionSubGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getPriceSectionSubGrid() ];
		me.callParent([ cfg ]);
	}
});
/**
 * 优惠分段列表store
 */
Ext.define('Miser.store.priceSectionSubStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.model.priceSectionSubEntity',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : 'priceSection!queryListByParamSub.action',
		reader : {
			type : 'json',
			rootProperty : 'priceSectionSubVo.priceSectionSubList',
			totalProperty : 'totalCount' // 总个数
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			if (sectionId != null) {
				var params = {
					'priceSectionSubVo.priceSectionSubEntity.id' : sectionId
				};
				Ext.apply(store.proxy.extraParams, params);
			}
		}

	}
});

/**
 * 增加表单
 */
Ext
		.define(
				'Miser.view.pricecard.priceSectionSubAddForm',
				{
					extend : 'Ext.form.Panel',
					 header: false,
					    frame: true,
					    collapsible: true,
					    width: 600,
					    fieldDefaults: {
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
						} ,
						{
							name : 'sectionId',
							fieldLabel : 'sectionId',
							xtype : 'textfield',
							hidden : true
						},
						{
							name : 'sectionAccodingItem',
							width: 275,
							colspan: 2,
							allowBlank : false,
							fieldLabel : pricecard.priceSection.i18n('pricecard.priceSection.sectionAccodingItem'), // '分段依据'
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype : 'dataDictionarySelector',
							termsCode : pricecard.priceSection.sectionAccodingItem
						}, {
							xtype : "numberfield",
							step:100,
							name : 'startValue',
							width: 275,
							allowBlank : false,
							fieldLabel : pricecard.priceSection.i18n('pricecard.priceSection.startValue'), // '段起',
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						},
						{
							xtype : "numberfield",
							step:100,
							name : 'endValue',
							width: 275,
							allowBlank : false,
							fieldLabel : pricecard.priceSection.i18n('pricecard.priceSection.endValue'), // '段止'
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, 
						{
							xtype : "textfield",
							name : 'priceType',
							allowBlank : false,
							fieldLabel : pricecard.priceSection.i18n('pricecard.priceSection.priceType'), // '费用类型'
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
							xtype : 'dataDictionarySelector',
							termsCode : pricecard.priceSection.priceType
						},
						{
							xtype : "numberfield",
							step:1000,
							name : 'price',
							allowBlank : false,
							fieldLabel : pricecard.priceSection.i18n('pricecard.priceSection.price'), // '费用'
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, 
						{
							xtype : "numberfield",
							step:1000,
							name : 'minPrice',
							allowBlank : false,
							fieldLabel : pricecard.priceSection.i18n('pricecard.priceSection.minPrice'), // '最低收费'
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						},
						{
							xtype : "numberfield",
							step:1000,
							name : 'maxPrice',
							allowBlank : false,
							fieldLabel : pricecard.priceSection
									.i18n('pricecard.priceSection.maxPrice'), // '最高收费'
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, {
							colspan: 2,
							xtype : "textfield",
							name : 'calcAlone',
							allowBlank : false,
							xtype : 'dataDictionarySelector',
							termsCode : pricecard.priceSection.active,
							fieldLabel : pricecard.priceSection.i18n('pricecard.priceSection.calcAlone'), // '是否单独进行结算'
							beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
						}, {
							colspan: 2,
							fieldLabel : pricecard.priceSection.i18n('pricecard.priceSection.remark'), // '备注',
							xtype : 'textarea',
							name : 'remark',
							width : document.documentElement.clientWidth * 0.51
						}
						];
						me.callParent([ cfg ]);
					}
				});

/**
 * 修改优惠分段窗口
 */
Ext
		.define(
				'Miser.view.pricecard.priceSectionSubUpdateWindow',
				{
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
					priceSectionSubEntity : null,
					listeners : {
						beforehide : function(me) { // 隐藏WINDOW的时候清除数据
							me.getpriceSectionSubAddForm().getForm().reset(); // 表格重置
						},
						beforeshow : function(me) { // 显示WINDOW的时候清除数据
							var fielsds = me.getpriceSectionSubAddForm()
									.getForm().getFields();
							if (!Ext.isEmpty(fielsds)) {
								fielsds.each(function(item, index, length) {
									item.clearInvalid();
									item.unsetActiveError();
								});
							}
							fielsds = null;
							me
									.getpriceSectionSubAddForm()
									.getForm()
									.loadRecord(
											new Miser.model.priceSectionSubEntity(
													me.priceSectionSubEntity));
						}
					},
					priceSectionSubAddForm : null,
					getpriceSectionSubAddForm : function() {
						if (Ext.isEmpty(this.priceSectionSubAddForm)) {
							this.priceSectionSubAddForm = Ext
									.create('Miser.view.pricecard.priceSectionSubAddForm');
						}
						return this.priceSectionSubAddForm;
					},
					submitDriver : function() {
						var me = this;
						me.getpriceSectionSubAddForm().getForm().findField(
								'shortCode').setValue(sectionId);
						if (me.getpriceSectionSubAddForm().getForm().isValid()) { // 校验form是否通过校验
							var priceSectionSubEntity = new Miser.model.priceSectionSubEntity();
							me.getpriceSectionSubAddForm().getForm()
									.updateRecord(priceSectionSubEntity); // 将FORM中数据设置到MODEL里面
							var params = {
								'priceSectionSubVo' : {
									'priceSectionSubEntity' : priceSectionSubEntity.data
								}
							};
							var successFun = function(json) {
								var message = json.message;
								miser.showInfoMsg(message); // 提示修改成功
								me.close();
								me.parent.getStore().load(); // 成功之后重新查询刷新结果集
							};
							var failureFun = function(json) {
								if (Ext.isEmpty(json)) {
									miser.showErrorMes('连接超时'); // 请求超时
								} else {
									var message = json.message;
									miser.showErrorMes(message); // 提示失败原因
								}
							};
							miser
									.requestJsonAjax(
											'priceSection!updatePriceSectionSub.action',
											params, successFun, failureFun); // 发送AJAX请求
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.fbar = [
								{
									text : pricecard.priceSection
											.i18n('miser.common.save'), // 保存
									margin : '0 0 0 305',
									handler : function() {
										me.submitDriver();
									}
								},
								{
									text : pricecard.priceSection
											.i18n('miser.common.reset'), // 重置
									handler : function() {
										me
												.getpriceSectionSubAddForm()
												.getForm()
												.loadRecord(
														new Miser.model.RoleEntity(
																{
																	'code' : me.roleEntity.code
																}));
									}
								},
								{
									text : pricecard.priceSection
											.i18n('miser.common.cancel'), // 取消
									handler : function() {
										me.close();
									}
								} ];
						me.items = [ me.getpriceSectionSubAddForm() ];
						me.callParent([ cfg ]);
					}
				});
// 增加窗体
Ext
		.define(
				'Miser.view.pricecard.priceSectionSubAddWindow',
				{
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
							me.getPriceSectionSubAddForm().getForm().reset(); // 表格重置
						},
						beforeshow : function(me) { // 显示WINDOW的时候清除数据
							var fielsds = me.getPriceSectionSubAddForm()
									.getForm().getFields();
							if (!Ext.isEmpty(fielsds)) {
								fielsds.each(function(item, index, length) {
									item.clearInvalid();
									item.unsetActiveError();
								});
							}
							fielsds = null;
						}
					},
					priceSectionSubAddForm : null,
					getPriceSectionSubAddForm : function() {
						if (Ext.isEmpty(this.priceSectionSubAddForm)) {
							this.priceSectionSubAddForm = Ext
									.create('Miser.view.pricecard.priceSectionSubAddForm');
						}
						return this.priceSectionSubAddForm;
					},
					submitPriceSectionSubAddForm : function() {
						var me = this;
						if (me.getPriceSectionSubAddForm().getForm().isValid()) { // 校验form是否通过校验
							var priceSectionSubEntity = new Miser.model.priceSectionSubEntity();
							me.getPriceSectionSubAddForm().getForm()
									.updateRecord(priceSectionSubEntity); // 将FORM中数据设置到MODEL里面
							var index = getGirdAddStore().count();
							priceSectionSubEntity.id = index;
							getGirdAddStore().insert(index,
									priceSectionSubEntity);
							me.close();
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.fbar = [
								{
									text : pricecard.priceSection
											.i18n('miser.common.save'),// '保存',
									// 保存
									/* margin : '0 0 0 55', */
									handler : function() {
										me.submitPriceSectionSubAddForm();
									}
								},
								{
									text : pricecard.priceSection
											.i18n('miser.common.reset'),// '重置',
									// 重置
									handler : function() {
										me.getPriceSectionSubAddForm().reset();
									}
								},
								{
									text : pricecard.priceSection
											.i18n('miser.common.cancel'),// '取消',
									// 取消
									handler : function() {
										me.close();
									}
								} ];
						me.items = [ me.getPriceSectionSubAddForm() ];
						me.callParent([ cfg ]);
					}
				});
// 修改窗体
Ext
		.define(
				'Miser.view.pricecard.priceSection_SectionSubUpdateWindow',
				{
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
					listeners : {
						beforehide : function(me) { // 隐藏WINDOW的时候清除数据
							me.getPriceSectionSubAddForm().getForm().reset(); // 表格重置
						},
						beforeshow : function(me) { // 显示WINDOW的时候清除数据
							var fielsds = me.getPriceSectionSubAddForm()
									.getForm().getFields();
							if (!Ext.isEmpty(fielsds)) {
								fielsds.each(function(item, index, length) {
									item.clearInvalid();
									item.unsetActiveError();
								});
							}
							fielsds = null;
						}
					},
					priceSectionSubAddForm : null,
					getPriceSectionSubAddForm : function() {
						if (Ext.isEmpty(this.priceSectionSubAddForm)) {
							this.priceSectionSubAddForm = Ext
									.create('Miser.view.pricecard.priceSectionSubAddForm');
						}
						return this.priceSectionSubAddForm;
					},
					submitPriceSectionSubAddForm : function() {
						var me = this;
						if (me.getPriceSectionSubAddForm().getForm().isValid()) { // 校验form是否通过校验
							var priceSectionSubEntity = new Miser.model.priceSectionSubEntity();
							// getGirdAddStore().remove(getGirdAddStore().getAt(adddataIndex));
							me.getPriceSectionSubAddForm().getForm()
									.updateRecord(priceSectionSubEntity); // 将FORM中数据设置到MODEL里面
							priceSectionSubEntity = priceSectionSubEntity.data;
							// 将form数据填充到行
							getGirdAddStore().getAt(adddataIndex).set(
									"startValue",
									priceSectionSubEntity.startValue + '');
							getGirdAddStore().getAt(adddataIndex).set(
									"endValue",
									priceSectionSubEntity.endValue + '');
							getGirdAddStore().getAt(adddataIndex).set(
									"sectionAccodingItem",
									priceSectionSubEntity.sectionAccodingItem
											+ '');
							getGirdAddStore().getAt(adddataIndex).set(
									"priceType",
									priceSectionSubEntity.priceType + '');
							getGirdAddStore().getAt(adddataIndex).set("price",
									priceSectionSubEntity.price + '');
							getGirdAddStore().getAt(adddataIndex).set(
									"calcAlone",
									priceSectionSubEntity.calcAlone + '');
							getGirdAddStore().getAt(adddataIndex).set(
									"minPrice",
									priceSectionSubEntity.minPrice + '');
							getGirdAddStore().getAt(adddataIndex).set(
									"maxPrice",
									priceSectionSubEntity.maxPrice + '');
							getGirdAddStore().getAt(adddataIndex).set("remark",
									priceSectionSubEntity.remark + '');
							me.close();
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.fbar = [
								{
									text : pricecard.priceSection
											.i18n('miser.common.update'),// '保存',
									// 保存
									/* margin : '0 0 0 55', */
									handler : function() {
										me.submitPriceSectionSubAddForm();
									}
								},
								{
									text : pricecard.priceSection
											.i18n('miser.common.reset'),// '重置',
									// 重置
									handler : function() {
										me.getPriceSectionSubAddForm().reset();
									}
								},
								{
									text : pricecard.priceSection
											.i18n('miser.common.cancel'),// '取消',
									// 取消
									handler : function() {
										me.close();
									}
								} ];
						me.items = [ me.getPriceSectionSubAddForm() ];
						me.callParent([ cfg ]);
					}
				});

/**
 * 优惠分段列表store
 */
Ext
		.define(
				'Miser.store.PriceSectionStore',
				{
					extend : 'Ext.data.Store',
					model : 'Miser.model.priceSectionEntity',
					pageSize : 20,
					proxy : {
						type : 'ajax',
						actionMethods : 'post',
						url : 'priceSection!queryListByParam.action',
						reader : {
							type : 'json',
							rootProperty : 'priceSectionVo.priceSectionList',
							totalProperty : 'totalCount' // 总个数
						}
					},
					listeners : {
						beforeload : function(store, operation, eOpts) {
							var queryForm = Ext.getCmp('queryForm');
							if (queryForm != null) {
								var params = {
									'priceSectionVo.priceSectionEntity.name' : queryForm
											.getForm().findField('name')
											.getValue(),
									'priceSectionVo.priceSectionEntity.shortCode' : queryForm
											.getForm().findField('shortCode')
											.getValue(),
									'priceSectionVo.priceSectionEntity.suitProduct' : queryForm
											.getForm().findField('suitProduct')
											.getValue(),
									'priceSectionVo.priceSectionEntity.sectionedItem' : queryForm
											.getForm().findField(
													'sectionedItem').getValue(),
									'priceSectionVo.priceSectionEntity.active' : queryForm
											.getForm().findField('active')
											.getValue(),
									'priceSectionVo.priceSectionEntity.suitoa' : queryForm
											.getForm().findField('suitoa')
											.getValue()
								};
								Ext.apply(store.proxy.extraParams, params);
							}
						}

					}

				});
var girdStore = Ext.create('Miser.store.priceSectionSubStore');

Ext
		.define(
				'Miser.view.priceSectionSub.Grid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					autoScroll : true,
					width : document.documentElement.clientWidth - 150,
					height : document.documentElement.clientHeight  * 0.55,
					stripeRows : true,
					// 交替行效果
					region : 'center',
					emptyText : '查询结果为空',
					priceSectionSubAddWindow : null,
					getpriceSectionSubAddWindow : function() {
						if (Ext.isEmpty(this.priceSectionSubAddWindow)) {
							this.priceSectionSubAddWindow = Ext
									.create('Miser.view.pricecard.priceSectionSubAddWindow');
							this.priceSectionSubAddWindow.parent = this; // 父元素
						}
						return this.priceSectionSubAddWindow;
					},
					priceSectionSubUpdateWindow : null,
					getpriceSectionSubUpdateWindow : function() {
						if (Ext.isEmpty(this.priceSectionSubUpdateWindow)) {
							this.priceSectionSubUpdateWindow = Ext
									.create('Miser.view.pricecard.priceSectionSubUpdateWindow');
							this.priceSectionSubUpdateWindow.parent = this; // 父元素
						}
						return this.priceSectionSubUpdateWindow;
					},
					updatepriceSectionSub : function() {
						var me = this;
						var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
						if (selections.length != 1) { // 判断选中了一条
							miser.showWoringMessage('请选择一条进行修改操作'); // 请选择一条进行作废操作！
							return; // 没有则提示并返回
						}
						var id = selections[0].get('id');
						var params = {
							'priceSectionSubVo' : {
								'priceSectionSubEntity' : {
									'id' : id
								}
							}
						};
						var successFun = function(json) {
							var updateWindow = me.getpriceSectionSubUpdateWindow(); // 获得修改窗口
							updateWindow.priceSectionSubEntity = json.priceSectionSubVo.priceSectionSubEntity;
							updateWindow.show(); // 显示修改窗口
							updateWindow.getpriceSectionSubAddForm().getForm().findField('id').setValue(updateWindow.priceSectionSubEntity.id);
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
								'priceSection!queryPriceSectionSubById.action',
								params, successFun, failureFun);
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
					deletepriceSectionSub : function() {
						var me = this;
						var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
						if (selections.length != 1) { // 判断是否至少选中了一条
							miser
									.showWoringMessage(pricecard.priceSection
											.i18n('pricecard.priceSection.oneselected')); // 请选择一条进行作废操作！
							return; // 没有则提示并返回
						}
						// miser.showQuestionMes(pricecard.priceSection.i18n('miser.pricecard.isdelete'),//是否删除
						miser
								.showQuestionMes(
										'删除',// 是否删除
										function(e) {
											if (e == 'yes') { // 询问是否删除，是则发送请求
												var id = selections[0]
														.get('id');
												var params = {
													'priceSectionSubVo' : {
														'priceSectionSubEntity' : {
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
														miser
																.showErrorMes(pricecard.priceSection
																		.i18n('pricecard.priceSection.timeout')); // 请求超时
													} else {
														var message = json.message;
														miser
																.showErrorMes(message);
													}
												};
												miser
														.requestJsonAjax(
																'priceSection!deletePriceSectionSub.action',
																params,
																successFun,
																failureFun);
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
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
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
									dataIndex : 'sectionId',
									width : 100,
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.shortCode'),
									align : 'center'
								// '优惠分段名称'
								},
								{
									dataIndex : 'startValue',
									width : 120,
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.startValue'),
									align : 'center'
								// '优惠分段编码'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.endValue'),
									dataIndex : 'endValue',
									width : 120,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.sectionAccodingItem'),
									dataIndex : 'sectionAccodingItem',
									width : 100,
									renderer : function(value) {
										return miser
												.changeCodeToNameStore(
														getDataDictionary()
																.getDataDictionaryStore(
																		pricecard.priceSection.sectionAccodingItem),
														value);
									},
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.priceType'),
									dataIndex : 'priceType',
									width : 100,
									renderer : function(value) {
										return miser
												.changeCodeToNameStore(
														getDataDictionary()
																.getDataDictionaryStore(
																		pricecard.priceSection.priceType),
														value);
									},
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.price'),
									dataIndex : 'price',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.calcAlone'),
									dataIndex : 'calcAlone',
									width : 150,
									renderer : function(value) {
										return stateTypeRender(value);
									},
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.minPrice'),
									dataIndex : 'minPrice',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.maxPrice'),
									dataIndex : 'maxPrice',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.remark'), // '备注',
									dataIndex : 'remark',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.active'), // '是否有效',
									dataIndex : 'active',
									renderer : function(value) {
										return stateTypeRender(value);
									},
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.suitoa'), // '是否适用oa',
									dataIndex : 'suitoa',
									renderer : function(value) {
										return stateTypeRender(value);
									},
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.modifyUser'), // '修改用户',
									dataIndex : 'modifyUser',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.modifyDate'), // '修改时间',
									dataIndex : 'modifyDate',
									renderer : function(value) {
										return dateRender(value);
									},
									width : 100,
									align : 'center'
								} ];
						me.store = girdStore;
						me.tbar = [
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.add'),// '新增',
									xtype : 'addbutton',
									// 新增
									width : 80,
									handler : function() {
										me.getpriceSectionSubAddWindow().show();
									}
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.update'),// '修改',
									xtype : 'updatebutton',
									width : 80,
									handler : function() {
										me.updatepriceSectionSub();
									}
								},
								{
									id : 'miser_biz_pricecard_priceSectionSub_del_id',
									text : pricecard.priceSection
											.i18n('miser.common.delete'),// '删除',
									xtype : 'deletebutton',
									disabled : true,
									// 作废
									width : 80,
									handler : function() {
										me.deletepriceSectionSub();
									}
								} ];
						me.bbar = me.getPagingToolbar();
								me.selModel = Ext
										.create(
												'Ext.selection.CheckboxModel',
												{
													listeners : {
														selectionchange : function(
																sm, selections) {
															Ext
																	.getCmp(
																			'miser_biz_pricecard_priceSectionSub_del_id')
																	.setDisabled(
																			selections.length === 0);
														}
													}
												}), me.callParent([ cfg ]);
					}
				});

/**
 * 新增优惠分段store 无action请求
 */
Ext.define('Miser.store.priceSectionSubAddStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.model.priceSectionSubEntity'
});
var girdAddStore = null;
function getGirdAddStore() {
	if (Ext.isEmpty(girdAddStore)) {
		girdAddStore = Ext.create('Miser.store.priceSectionSubAddStore');
	}
	return girdAddStore;
}

/**
 * 新增时候的grid
 */
Ext
		.define(
				'Miser.view.priceSectionSub.AddGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					autoScroll : true,
					width : document.documentElement.clientWidth - 150,
					height : document.documentElement.clientHeight  * 0.55,
					stripeRows : true,
					// 交替行效果
					region : 'center',
					priceSectionSubAddWindow : null,
					getpriceSectionSubAddWindow : function() {
						if (Ext.isEmpty(this.priceSectionSubAddWindow)) {
							this.priceSectionSubAddWindow = Ext
									.create('Miser.view.pricecard.priceSectionSubAddWindow');
							this.priceSectionSubAddWindow.parent = this; // 父元素
						}
						return this.priceSectionSubAddWindow;
					},
					priceSectionSubUpdateWindow : null,
					getpriceSectionSectionSubUpdateWindow : function() {
						if (Ext.isEmpty(this.priceSectionSubUpdateWindow)) {
							this.priceSectionSubUpdateWindow = Ext
									.create('Miser.view.pricecard.priceSection_SectionSubUpdateWindow');
							this.priceSectionSubUpdateWindow.parent = this; // 父元素
						}
						return this.priceSectionSubUpdateWindow;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.columns = [
								{
									dataIndex : 'id',
									hidden : true
								// '优惠分段编码'
								},
								{
									dataIndex : 'sectionId',
									hidden : true
								// '优惠分段编码'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.sectionAccodingItem'),
									dataIndex : 'sectionAccodingItem',
									width : 100,
									renderer : function(value) {
										return miser
												.changeCodeToNameStore(
														getDataDictionary()
																.getDataDictionaryStore(
																		pricecard.priceSection.sectionAccodingItem),
														value);
									},
									align : 'center'
								},
								{
									dataIndex : 'startValue',
									width : 120,
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.startValue'),
									align : 'center'
								// '优惠分段编码'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.endValue'),
									dataIndex : 'endValue',
									width : 120,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.calcAlone'),
									dataIndex : 'calcAlone',
									width : 150,
									renderer : function(value) {
										return stateTypeRender(value);
									},
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.priceType'),
									dataIndex : 'priceType',
									width : 100,
									renderer : function(value) {
										return miser
												.changeCodeToNameStore(
														getDataDictionary()
																.getDataDictionaryStore(
																		pricecard.priceSection.priceType),
														value);
									},
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.price'),
									dataIndex : 'price',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.minPrice'),
									dataIndex : 'minPrice',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.maxPrice'),
									dataIndex : 'maxPrice',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.remark'), // '备注',
									dataIndex : 'remark',
									width : 100,
									align : 'center'
								} ];
						me.store = getGirdAddStore();
						me.tbar = null;
						me.tbar = [
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.add'),// '新增',
									xtype : 'addbutton',
									// 新增
									width : 80,
									handler : function() {
										me.getpriceSectionSubAddWindow().show();
									}
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.update'),// '修改',
									xtype : 'updatebutton',
									width : 80,
									handler : function() {
										var selections = me.getSelectionModel()
												.getSelection(); // 获取选中的数据
										if (selections.length != 1) { // 判断选中了一条
											miser
													.showWoringMessage('请选择一条进行修改操作'); // 请选择一条进行作废操作！
											return; // 没有则提示并返回
										}
										var selectData = selections[0];
										adddataIndex = getGirdAddStore().indexOf(selectData);
										var sectionSectionSubUpdateWindow = me.getpriceSectionSectionSubUpdateWindow();
										sectionSectionSubUpdateWindow.getPriceSectionSubAddForm().getForm().loadRecord(selectData);
										sectionSectionSubUpdateWindow.show();
									}
								},
								{
									id : 'miser_biz_pricecard_priceSectionSubAdd_del_id',
									text : pricecard.priceSection
											.i18n('miser.common.delete'),// '删除',
									xtype : 'deletebutton',
									disabled : true,
									// 作废
									width : 80,
									handler : function() {
										var selections = me.getSelectionModel()
												.getSelection(); // 获取选中的数据
										if (selections.length != 1) { // 判断选中了一条
											miser
													.showWoringMessage('请选择一条进行修改操作'); // 请选择一条进行作废操作！
											return; // 没有则提示并返回
										}
										for (var i = 0; i < selections.length; i++) {
											getGirdAddStore().remove(
													selections[0]);
										}
									}
								} ];
								me.selModel = Ext
										.create(
												'Ext.selection.CheckboxModel',
												{
													listeners : {
														selectionchange : function(
																sm, selections) {
															Ext
																	.getCmp(
																			'miser_biz_pricecard_priceSectionSubAdd_del_id')
																	.setDisabled(
																			selections.length === 0);
														}
													}
												}), me.callParent([ cfg ]);
					}
				});
/**
 * 修改时候的grid
 */
Ext
		.define(
				'Miser.view.priceSectionSub.UpdateGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					autoScroll : true,
					width : document.documentElement.clientWidth - 150,
					height : document.documentElement.clientHeight   * 0.6,
					stripeRows : true,
					// 交替行效果
					region : 'center',
					priceSectionSubAddWindow : null,
					getpriceSectionSubAddWindow : function() {
						if (Ext.isEmpty(this.priceSectionSubAddWindow)) {
							this.priceSectionSubAddWindow = Ext
									.create('Miser.view.pricecard.priceSectionSubAddWindow');
							this.priceSectionSubAddWindow.parent = this; // 父元素
						}
						return this.priceSectionSubAddWindow;
					},
					priceSectionSubUpdateWindow : null,
					getpriceSectionSectionSubUpdateWindow : function() {
						if (Ext.isEmpty(this.priceSectionSubUpdateWindow)) {
							this.priceSectionSubUpdateWindow = Ext
									.create('Miser.view.pricecard.priceSection_SectionSubUpdateWindow');
							this.priceSectionSubUpdateWindow.parent = this; // 父元素
						}
						return this.priceSectionSubUpdateWindow;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.columns = [
								{
									dataIndex : 'id',
									hidden : true
								// '优惠分段编码'
								},
								{
									dataIndex : 'sectionId',
									hidden : true
								// '优惠分段编码'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.sectionAccodingItem'),
									dataIndex : 'sectionAccodingItem',
									width : 100,
									renderer : function(value) {
										return miser
												.changeCodeToNameStore(
														getDataDictionary()
																.getDataDictionaryStore(
																		pricecard.priceSection.sectionAccodingItem),
														value);
									},
									align : 'center'
								},
								{
									dataIndex : 'startValue',
									width : 120,
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.startValue'),
									align : 'center'
								// '优惠分段编码'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.endValue'),
									dataIndex : 'endValue',
									width : 120,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.calcAlone'),
									dataIndex : 'calcAlone',
									width : 150,
									renderer : function(value) {
										return stateTypeRender(value);
									},
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.priceType'),
									dataIndex : 'priceType',
									width : 100,
									renderer : function(value) {
										return miser
												.changeCodeToNameStore(
														getDataDictionary()
																.getDataDictionaryStore(
																		pricecard.priceSection.priceType),
														value);
									},
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.price'),
									dataIndex : 'price',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.minPrice'),
									dataIndex : 'minPrice',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.maxPrice'),
									dataIndex : 'maxPrice',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.remark'), // '备注',
									dataIndex : 'remark',
									width : 100,
									align : 'center'
								}];
						me.store = getGirdAddStore();
						me.tbar = null;
						me.tbar = [
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.add'),// '新增',
									xtype : 'addbutton',
									// 新增
									width : 80,
									handler : function() {
										me.getpriceSectionSubAddWindow().show();
									}
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.update'),// '修改',
									xtype : 'updatebutton',
									width : 80,
									handler : function() {
										var selections = me.getSelectionModel()
												.getSelection(); // 获取选中的数据
										if (selections.length != 1) { // 判断选中了一条
											miser
													.showWoringMessage('请选择一条进行修改操作'); // 请选择一条进行作废操作！
											return; // 没有则提示并返回
										}
										var selectData = selections[0];
										adddataIndex = getGirdAddStore()
												.indexOf(selectData);
										var sectionSectionSubUpdateWindow = me
												.getpriceSectionSectionSubUpdateWindow();
										sectionSectionSubUpdateWindow
												.getPriceSectionSubAddForm()
												.getForm().loadRecord(
														selectData);
										sectionSectionSubUpdateWindow.show();
									}
								},
								{
									id : 'miser_biz_pricecard_priceSectionSubUpdate_del_id',
									text : pricecard.priceSection
											.i18n('miser.common.delete'),// '删除',
									xtype : 'deletebutton',
									disabled : true,
									// 作废
									width : 80,
									handler : function() {
										var selections = me.getSelectionModel()
												.getSelection(); // 获取选中的数据
										if (selections.length != 1) { // 判断选中了一条
											miser
													.showWoringMessage('请选择一条进行修改操作'); // 请选择一条进行作废操作！
											return; // 没有则提示并返回
										}
										for (var i = 0; i < selections.length; i++) {
											getGirdAddStore().remove(
													selections[0]);
										}
									}
								} ];
								me.selModel = Ext
										.create(
												'Ext.selection.CheckboxModel',
												{
													listeners : {
														selectionchange : function(
																sm, selections) {
															Ext
																	.getCmp(
																			'miser_biz_pricecard_priceSectionSubUpdate_del_id')
																	.setDisabled(
																			selections.length === 0);
														}
													}
												}), me.callParent([ cfg ]);
					}
				});
/**
 * 查询时候的grid
 */
Ext
		.define(
				'Miser.view.priceSectionSub.QueryGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					autoScroll : true,
					width : document.documentElement.clientWidth - 150,
					height : document.documentElement.clientHeight   * 0.6,
					stripeRows : true,
					// 交替行效果
					region : 'center',
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
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
								// '优惠分段编码'
								},
								{
									dataIndex : 'sectionId',
									hidden : true
								// '优惠分段编码'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.sectionAccodingItem'),
									dataIndex : 'sectionAccodingItem',
									width : 100,
									renderer : function(value) {
										return miser
												.changeCodeToNameStore(
														getDataDictionary()
																.getDataDictionaryStore(
																		pricecard.priceSection.sectionAccodingItem),
														value);
									},
									align : 'center'
								},
								{
									dataIndex : 'startValue',
									width : 120,
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.startValue'),
									align : 'center'
								// '优惠分段编码'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.endValue'),
									dataIndex : 'endValue',
									width : 120,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.calcAlone'),
									dataIndex : 'calcAlone',
									width : 150,
									renderer : function(value) {
										return stateTypeRender(value);
									},
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.priceType'),
									dataIndex : 'priceType',
									width : 100,
									renderer : function(value) {
										return miser
												.changeCodeToNameStore(
														getDataDictionary()
																.getDataDictionaryStore(
																		pricecard.priceSection.priceType),
														value);
									},
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.price'),
									dataIndex : 'price',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.minPrice'),
									dataIndex : 'minPrice',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.maxPrice'),
									dataIndex : 'maxPrice',
									width : 100,
									align : 'center'
								},
								{
									text : pricecard.priceSection
											.i18n('pricecard.priceSection.remark'), // '备注',
									dataIndex : 'remark',
									width : 100,
									align : 'center'
								} ];
						me.store = getGirdAddStore();
						me.callParent([ cfg ]);
					}
				});
Ext.onReady(function() {
	/**
	 * 数据字典页面
	 */
	Ext.QuickTips.init();
	var queryForm = Ext.create('Miser.view.priceSection.QueryForm'); // 查询FORM
	var priceSectionGrid = Ext.create('Miser.view.priceSection.Grid');
	var mainPanel = Ext.create('Ext.panel.Panel', {
		renderTo : Ext.getBody(),
		getQueryForm : function() {
			return queryForm;
		},
		getPriceSectionGrid : function() {
			return priceSectionGrid;
		},
		items : [ queryForm, priceSectionGrid ]
	});
	
	 //设置窗口大小改变时的响应
    window.onresize = function(){
    
        //控制整个 panel 的宽度
    	mainPanel.setWidth(document.documentElement.clientWidth);
    	
    	//控制整个 查询表单 的宽度
    	queryForm.setWidth(document.documentElement.clientWidth);
        
		//设置查询结果的宽度
    	priceSectionGrid.setWidth(document.documentElement.clientWidth - 20);
    };
});