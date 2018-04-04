
Ext.define('Miser.model.extrafee.PricePackageFeeStandardEntityModel',{
	extend:'Ext.data.Model',
	fields: [{
		name:'ppFeeItems_code',
		mapping:'ppFeeItems.code',
		type:'string'
	},{
        name: 'ppFeeItems_name',
        mapping:'ppFeeItems.name',
        // 出发价格城市
        type: 'string'
    },{
		name:'calculationType',
		type:'string'
	},{
		name:'money',
		type:'float'
	},{
		name:'rate',
		type:'float'
	},{
		name:'minMoney',
		type:'float'
	},{
		name:'maxMoney2',
		type:'float'
	},{
		name:'lockType',
		type:'string'
	},{
		name:'remark',
		type:'string'
	},{
		name:'effectiveTime',
		dateFormat:'time',
		type:'date'
	},{
		name:'active',
		type:'string'
	},{
        name: 'transTypeCode',
        // 运输类型
        type: 'string'
    },{
        name: 'transTypeName',
        // 运输类型
        type: 'string'
    },{
		name:'id',
		type:'string'
	},{
		name:'createDate',
		dateFormat:'time',
		type:'date'
	},{
		name:'createUser',
		type:'string'
	},{
		name:'modifyDate',
		dateFormat:'time',
		type:'date'
	},{
		name:'modifyUser',
		type:'string'
	}
	]
});
Ext.define('Miser.store.extrafee.PricePackageFeeStandardEntityStore',{
	extend:'Ext.data.Store',
	model:'Miser.model.extrafee.PricePackageFeeStandardEntityModel',
	pageSize:20,
	proxy:{
		type: 'ajax',
		actionMethods: 'post',
		url:'../extrafee/ppFeeStandard!queryListByParam.action',
		reader: {
			type: 'json',
			rootProperty: 'ppfeeStandardVO.ppFeeStandardList',
			totalProperty: 'totalCount'
		}
	},
	 listeners: {
		beforeload: function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('queryForm');
			if (queryForm != null) {
			  var params = {
					'ppfeeStandardVO.ppFeeStandardEntity.ppFeeItems.code': queryForm.getForm().findField('ppFeeItemsCode').getValue(),
					'ppfeeStandardVO.ppFeeStandardEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
					'ppfeeStandardVO.ppFeeStandardEntity.active':queryForm.getForm().findField('active').getValue()
			    };
			  Ext.apply(store.proxy.extraParams, params);
			}
		}
	}
});
Ext.define('Miser.view.extrafee.PricePackageFeeStandardEntity.QueryForm',{
	extend:'Ext.form.Panel',
	id:'queryForm',
	frame: true,
	title:extrafee.ppFeeStandard.i18n('miser.common.queryCondition'),//标题
	height: 100,
	closable: true,
	region: 'north',
	defaults: {
	 labelWidth: 100,
	 columnWidth: 0.6,
	 margin: '8 10 5 10',
	 labelAlign : 'right'
	},
	constructor: function(config) {
	  var me = this,
	  cfg = Ext.apply({},config);
	  me.items = [{
	      	layout: 'hbox',
			defaults: {
				labelWidth: 70,
				labelAlign: "right"
		},items:[{
         name: 'active',
         fieldLabel: '是否有效',// '词条名称',
         isShowAll:true,
         value : 'Y',
         xtype: 'yesnocombselector'
     },{
         name: 'ppFeeItemsCode',
         fieldLabel: '包装费项目',// '词条名称',
         xtype: 'dynamicpricepackagefeeitemscombselector'
     },{
         name: 'transTypeCode',
         fieldLabel: '运输类型',// '词条名称',
         xtype : 'transtypecombselector'
     }]}
	],
	me.buttons = [{
		text:extrafee.ppFeeStandard.i18n('miser.common.query'),
		handler: function() {
			if (me.getForm().isValid()) {
				
				me.up().getFormGridName().getPagingToolbar().moveFirst();
			}
		}
	},
	{
		text:extrafee.ppFeeStandard.i18n('miser.common.reset'),
		handler: function() {
			me.getForm().reset();
		}
	}];
	me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PricePackageFeeStandardEntityChangeForm',{
	extend:'Ext.form.Panel',
	header: false,
	frame: true,
	closable: true,
	width: 600,
	fieldDefaults: {
		labelWidth: 100,
		margin: '8 10 5 10',
	},
	layout: {
        type: 'table',
        columns: 2
    },
	defaultType: 'textfield',
	constructor: function(config) {
	  var me = this,
	  cfg = Ext.apply({},config);
	  me.items = [{
		name:'ppFeeItems_code',
		xtype: 'dynamicpricepackagefeeitemscombselector',
		allowBlank:false,
		fieldLabel:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.name'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name : 'transTypeCode',
		allowBlank : false,
		showAll:false,
		fieldLabel : extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.transTypeCode'),
		beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
		xtype : 'transtypecombselector',
		width:250
	},{
		name:'calculationType',
		xtype : 'dataDictionarySelector',
		termsCode:'CALCULATION_TYPE',
		allowBlank:false,
		fieldLabel:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.calculationType'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'lockType',
		xtype : 'dataDictionarySelector',
		termsCode:'LOCK_TYPE',
		allowBlank:false,
		fieldLabel:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.lockType'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'money',
		negativeText : '最小值为0',
		xtype :'numberfield',
		decimalPrecision:2,
		maxValue:99999999.99,
		minValue: 0,
		allowBlank:false,
		fieldLabel:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.money'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'rate',
		negativeText : '最小值为0',
		xtype :'numberfield',
		decimalPrecision:2,
		maxValue:99999999.99,
		minValue: 0,
		allowBlank:false,
		fieldLabel:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.rate'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'minMoney',
		colspan: 2,
		xtype :'numberfield',
		negativeText : '最小值为0',
		decimalPrecision:2,
		maxValue:99999999.99,
		minValue: 0,
		allowBlank:false,
		fieldLabel:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.minMoney'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'maxMoney2',
		hidden:true,
		xtype :'numberfield',
		maxValue:99999999.99,
		negativeText : '最小值为0',
		decimalPrecision:2,
		minValue: 0,
		//allowBlank:false,
		fieldLabel:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.maxMoney2'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'effectiveTime',
		xtype : 'datefield',
		value:new Date(),
		format : 'Y-m-d H:i:s',
		allowBlank:false,
		colspan: 2,
		fieldLabel:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.effectiveTime'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'remark',
		xtype: 'textareafield',
		maxLength: 200,
        grow: true,
        colspan: 2, 
        width: 575,
        anchor: '100%',
		fieldLabel:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.remark')//这里是注释
	},{
		name:'id',
		hidden:true,
		width:250
	}
	  ];
	me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PricePackageFeeStandardEntityAddWindow',{
	extend:'Ext.window.Window',
	title:'新增',
	closable: true,
	parent: null,
	modal: true,
	resizable: false,
	closeAction: 'hide',
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	 listeners: {
		beforehide: function(me) {
			me.getPricePackageFeeStandardEntityChangeForm().getForm().reset();
		},
		beforeshow: function(me) {
			var fielsds = me.getPricePackageFeeStandardEntityChangeForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			var effectiveTime = me.getPricePackageFeeStandardEntityChangeForm().getForm().findField('effectiveTime');
            effectiveTime.setMinValue(new Date(new Date().getTime()));
            effectiveTime.setValue(new Date(new Date().getTime()+6*60*1000));
		}
	},
	pricePackageFeeStandardEntityChangeForm:null,
	getPricePackageFeeStandardEntityChangeForm:function() {
		if(Ext.isEmpty(this.pricePackageFeeStandardEntityChangeForm)){
			this.pricePackageFeeStandardEntityChangeForm=Ext.create('Miser.view.extrafee.PricePackageFeeStandardEntityChangeForm')
		}
		return this.pricePackageFeeStandardEntityChangeForm
	},
	 submitAddForm: function() {
		var me=this;
		if(me.getPricePackageFeeStandardEntityChangeForm().getForm().isValid()) {
			var effectiveTime = me.getPricePackageFeeStandardEntityChangeForm().getForm().findField('effectiveTime');
        	if(effectiveTime.getValue().getTime()<new Date().getTime()){
        		miser.showInfoMsg("请选择当前时间之后的时间");
        		effectiveTime.focus();
        		return;
        	}
			var addEntity=new Miser.model.extrafee.PricePackageFeeStandardEntityModel;
			me.getPricePackageFeeStandardEntityChangeForm().getForm().updateRecord(addEntity);
			var codeCombo = me.getPricePackageFeeStandardEntityChangeForm().getForm().findField('ppFeeItems_code'); 
			var curData=addEntity.data;
			 var ppFeeItems=new Object(); 
			 curData.id='';
			 ppFeeItems.code=curData.ppFeeItems_code;
			 ppFeeItems.name=codeCombo.getRawValue();
			 curData.ppFeeItems=ppFeeItems;
			 var params = {
			         'ppfeeStandardVO': {
			             'ppFeeStandardEntity': curData
			        }
			     }
			var successFun = function(json) {
				var message = json.message;
				miser.showInfoMsg(message);
				me.close();
				me.parent.getStore().load();
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(extrafee.ppFeeStandard.i18n('miser.extrafee.timeout')); // 请求超时
				}else{
					var message = json.message;
					miser.showErrorMes(message);
				}
			};
			miser.requestJsonAjax('../extrafee/ppFeeStandard!addFeeStandard.action', params, successFun, failureFun);
		}
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.fbar = [{
			text:extrafee.ppFeeStandard.i18n('miser.common.save'),//'保存',
			handler: function() {
				me.submitAddForm();
			}
		},{
			text:extrafee.ppFeeStandard.i18n('miser.common.reset'),// '重置',
			handler: function() {
				me.getPricePackageFeeStandardEntityChangeForm().reset();
			}
		},{
			text: extrafee.ppFeeStandard.i18n('miser.common.cancel'),// '取消',
			handler: function() {
				me.close();
			}
		}];
		me.items = [me.getPricePackageFeeStandardEntityChangeForm()];
		me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PricePackageFeeStandardEntityUpdateWindow',{
	extend:'Ext.window.Window',
	title:'修改',
	closable: true,
	parent: null,
	modal: true,
	resizable: false,
	closeAction: 'hide',
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	updateEntity:null,
	 listeners: {
		beforehide: function(me) {
			me.getPricePackageFeeStandardEntityChangeForm().getForm().reset();
		},
		beforeshow: function(me) {
			var fielsds = me.getPricePackageFeeStandardEntityChangeForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
		  	fielsds = null;
			me.getPricePackageFeeStandardEntityChangeForm().getForm().loadRecord(new Miser.model.extrafee.PricePackageFeeStandardEntityModel(me.updateEntity))
			//这块是处理下拉框赋值的问题
			var codeCombo = me.getPricePackageFeeStandardEntityChangeForm().getForm().findField('ppFeeItems_code');
			var newcodeComboRecord=Ext.data.Record({
				code: me.updateEntity.ppFeeItems.code,
			    name: me.updateEntity.ppFeeItems.name
				}
			);
			codeCombo.getStore().add(newcodeComboRecord);
			codeCombo.setValue(me.updateEntity.ppFeeItems.code);
			codeCombo.disable();
			var effectiveTime = me.getPricePackageFeeStandardEntityChangeForm().getForm().findField('effectiveTime');
            effectiveTime.setMinValue(new Date(new Date().getTime()));
            //这块是处理运输类型下拉框赋值的问题
			var codeCombo = me.getPricePackageFeeStandardEntityChangeForm().getForm().findField('transTypeCode');
			var newcodeComboRecord=Ext.data.Record({
				code: me.updateEntity.transTypeCode,
			    name: me.updateEntity.transTypeName
				}
			);
			codeCombo.getStore().add(newcodeComboRecord);
			codeCombo.setValue(me.updateEntity.transTypeCode);
			codeCombo.disable();
		}
	},
	pricePackageFeeStandardEntityChangeForm:null,
	getPricePackageFeeStandardEntityChangeForm:function() {
		if(Ext.isEmpty(this.pricePackageFeeStandardEntityChangeForm)){
			this.pricePackageFeeStandardEntityChangeForm=Ext.create('Miser.view.extrafee.PricePackageFeeStandardEntityChangeForm')
		}
		return this.pricePackageFeeStandardEntityChangeForm;
	},
	 submitUpdateForm: function() {
		var me=this
		if(me.getPricePackageFeeStandardEntityChangeForm().getForm().isValid()) {
			var effectiveTime = me.getPricePackageFeeStandardEntityChangeForm().getForm().findField('effectiveTime');
        	if(effectiveTime.getValue().getTime()<new Date().getTime()){
        		miser.showInfoMsg("请选择当前时间之后的时间");
        		effectiveTime.focus();
        		return;
        	}
			var updateEntity=new Miser.model.extrafee.PricePackageFeeStandardEntityModel;
			me.getPricePackageFeeStandardEntityChangeForm().getForm().findField('ppFeeItems_code').enable();
			me.getPricePackageFeeStandardEntityChangeForm().getForm().findField('transTypeCode').enable();
			me.getPricePackageFeeStandardEntityChangeForm().getForm().updateRecord(updateEntity);
			 var curData=updateEntity.data;
			 var ppFeeItems=new Object(); 
			 var codeCombo = me.getPricePackageFeeStandardEntityChangeForm().getForm().findField('ppFeeItems_code'); 
			 ppFeeItems.code=curData.ppFeeItems_code;
			 ppFeeItems.name=codeCombo.getRawValue();
			 curData.ppFeeItems=ppFeeItems;
			 var params = {
			         'ppfeeStandardVO': {
			             'ppFeeStandardEntity': curData
			        }
			     }
			var successFun = function(json) {
				var message = json.message;
				miser.showInfoMsg(message);
				me.close();
				me.parent.getStore().load();
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes('请求超时'); // 请求超时
				}else{
					var message = json.message;
					miser.showErrorMes(message);
				}
			};
			miser.requestJsonAjax('../extrafee/ppFeeStandard!updateFeeStandard.action', params, successFun, failureFun);
		}
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.fbar = [{
			text:extrafee.ppFeeStandard.i18n('miser.common.save'),//'保存',
			handler: function() {
				me.submitUpdateForm();
			}
		},{
			text:extrafee.ppFeeStandard.i18n('miser.common.reset'),// '重置',
			handler: function() {
				me.close();
				me.parent.updateMethod();
			}
		},{
			text: extrafee.ppFeeStandard.i18n('miser.common.cancel'),// '取消',
			handler: function() {
				me.close();
			}
		}];
		me.items = [me.getPricePackageFeeStandardEntityChangeForm()];
		me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PricePackageFeeStandardEntity.Grid',{
	extend:'Ext.grid.Panel',
	frame: true,
	autoScroll: true,
	height: miser.getBrowserHeight() - 120,
	stripeRows: true,
	selType: 'rowmodel',
	emptyText: '查询结果为空',
	columnLines: true,
	viewConfig: {
		enableTextSelection: true
	},
	pricePackageFeeStandardEntityAddWindow:null,
	getPricePackageFeeStandardEntityAddWindow:function() {
		if(this.pricePackageFeeStandardEntityAddWindow== null) {
			this.pricePackageFeeStandardEntityAddWindow=Ext.create('Miser.view.extrafee.PricePackageFeeStandardEntityAddWindow');
			this.pricePackageFeeStandardEntityAddWindow.parent = this;
		}
		return this.pricePackageFeeStandardEntityAddWindow;
	},
	pricePackageFeeStandardEntityUpdateWindow:null,
	getPricePackageFeeStandardEntityUpdateWindow:function() {
		if(this.pricePackageFeeStandardEntityUpdateWindow== null) {
			this.pricePackageFeeStandardEntityUpdateWindow=Ext.create('Miser.view.extrafee.PricePackageFeeStandardEntityUpdateWindow');
			this.pricePackageFeeStandardEntityUpdateWindow.parent = this;
		}
		return this.pricePackageFeeStandardEntityUpdateWindow;
	},
	updateMethod:function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();
		if (selections.length != 1) {
			miser.showWoringMessage('请选择一条进行修改操作');
			return;
		}
		var id = selections[0].get('id');
		var active=selections[0].get('active');
		if(active!='Y'){
			miser.showWoringMessage('只有生效中的数据才能进行该项操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var params = {
			'ppfeeStandardVO': {
			     'ppFeeStandardEntity': {
			     	'id':id
			     	}
			    }
			 };
		var successFun = function (json) {
				var updateWindow = me.getPricePackageFeeStandardEntityUpdateWindow();
				updateWindow.updateEntity =json.ppfeeStandardVO.ppFeeStandardEntity;
				updateWindow.show();
		};
		var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes('请求超时'); // 请求超时
				}else{
					var message = json.message;
					miser.showErrorMes(message);
				}
		};
		miser.requestJsonAjax('../extrafee/ppFeeStandard!queryFeeStandardById.action', params, successFun, failureFun);
	},
	pagingToolbar: null,
	getPagingToolbar: function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Ext.toolbar.Paging', {
				store: this.store,
				pageSize: 20
			})
		}
		return this.pagingToolbar;
	},
	deleteMethod: function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection();
		if (selections.length != 1) {
			miser.showWoringMessage(extrafee.ppFeeStandard.i18n('bizbase.base.oneselected'));
			return;
		}
		miser.showQuestionMes('删除',
		function(e) {
			if (e == 'yes') {
				var params = {
					'ppfeeStandardVO': {
			     			'ppFeeStandardEntity': {
			     				'id':selections[0].get('id')
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
						miser.showErrorMes('请求超时'); // 请求超时
					}else{
						var message = json.message;
						miser.showErrorMes(message);
					}
				};
				miser.requestJsonAjax('../extrafee/ppFeeStandard!deleteFeeStandard.action', params, successFun, failureFun);
			 }
		});
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.columns = [{
            text: extrafee.ppFeeStandard.i18n('miser.base.rownumberer'),
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },{
			dataIndex:'ppFeeItems_name',
			width:150,
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.name')
		},{
			dataIndex:'transTypeName',
			width:150,
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.transTypeName')
		}
		,{
			dataIndex:'calculationType',
			width:150,
			renderer : function(value) {
				return miser
						.changeCodeToNameStore(
								getDataDictionary()
										.getDataDictionaryStore("CALCULATION_TYPE"),
								value);
			},
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.calculationType')
		},{
			dataIndex:'money',
			width:100,
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.money')
		},{
			dataIndex:'rate',
			width:100,
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.rate')
		},{
			dataIndex:'minMoney',
			width:100,
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.minMoney')
		},{
			dataIndex:'maxMoney2',
			width:100,
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.maxMoney2')
		},{
			dataIndex:'lockType',
			width:150,
			renderer : function(value) {
				return miser
						.changeCodeToNameStore(
								getDataDictionary()
										.getDataDictionaryStore("LOCK_TYPE"),
								value);
			},
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.lockType')
		},{
			dataIndex:'remark',
			width:200,
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.remark')
		},{
			dataIndex:'effectiveTime',
			width:200,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.effectiveTime')
		},{
			dataIndex:'active',
			width:100,
			renderer: function(value) {
                return booleanTypeRender(value);
            },
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.active')
		},{
			dataIndex:'id',
			width:200,
			hidden:true,
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.id')
		},{
			dataIndex:'createDate',
			width:200,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.createDate')
		},{
			dataIndex:'createUser',
			width:200,
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.createUser')
		},{
			dataIndex:'modifyDate',
			width:200,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.modifyDate')
		},{
			dataIndex:'modifyUser',
			width:200,
			text:extrafee.ppFeeStandard.i18n('extrafee.ppFeeStandard.modifyUser')
		}
		],
		me.store = Ext.create('Miser.store.extrafee.PricePackageFeeStandardEntityStore', {
				autoLoad: false
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
				mode: 'MULTI',
				checkOnly: true
		});
		me.tbar = [{
				text:extrafee.ppFeeStandard.i18n('bizbase.base.button.addentry'),
				xtype: 'addbutton',
				width: 80,
				handler: function() {
					me.getPricePackageFeeStandardEntityAddWindow().show();
				}
			},
			'-',{
				text:extrafee.ppFeeStandard.i18n('bizbase.base.button.updateentry'),
				xtype: 'updatebutton',
				width: 80,
				handler: function() {
					me.updateMethod();
				}
			},
			'-',{
				text:extrafee.ppFeeStandard.i18n('bizbase.base.button.deleteentry'),
				id: 'del_id',
				xtype: 'deletebutton',
				disabled : true,
				width: 80,
				handler: function() {
					me.deleteMethod();
				}
			}
			];
		me.bbar = me.getPagingToolbar();
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			listeners: {
				selectionchange: function(sm, selections) {
			 		Ext.getCmp('del_id').setDisabled(selections.length === 0);
			 		}
			}
		}),
		me.callParent([cfg]);
	}
});
Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext.create('Miser.view.extrafee.PricePackageFeeStandardEntity.QueryForm');
	var formGridName = Ext.create('Miser.view.extrafee.PricePackageFeeStandardEntity.Grid');
	Ext.create('Ext.panel.Panel', {
		renderTo: Ext.getBody(),
		getQueryForm: function() {
			return queryForm;
		},
		getFormGridName: function() {
			return formGridName;
		},
		items: [queryForm, formGridName]
	});
});