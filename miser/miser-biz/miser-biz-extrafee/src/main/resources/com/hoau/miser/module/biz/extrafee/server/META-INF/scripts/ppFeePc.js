
Ext.define('Miser.model.extrafee.PricePackageFeePcEntityModel',{
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
        name: 'priceCity',
        // 出发价格城市
        type: 'string'
    },{
        name: 'priceCityName',
        // 出发价格城市
        type: 'string'
    },{
        name: 'transTypeCode',
        // 运输类型
        type: 'string'
    },{
        name: 'transTypeName',
        // 运输类型
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
Ext.define('Miser.store.extrafee.PricePackageFeePcEntityStore',{
	extend:'Ext.data.Store',
	model:'Miser.model.extrafee.PricePackageFeePcEntityModel',
	pageSize:20,
	proxy:{
		type: 'ajax',
		actionMethods: 'post',
		url:'../extrafee/ppFeePc!queryListByParam.action',
		reader: {
			type: 'json',
			rootProperty: 'ppFeePcVo.ppFeePcList',
			totalProperty: 'totalCount'
		}
	},
	 listeners: {
		beforeload: function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('queryForm');
			if (queryForm != null) {
			  var params = {
					'ppFeePcVo.ppFeePcEntity.priceCity': queryForm.getForm().findField('priceCity').getValue(),
					'ppFeePcVo.ppFeePcEntity.ppFeeItems.code': queryForm.getForm().findField('ppFeeItemsCode').getValue(),
					'ppFeePcVo.ppFeePcEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
					'ppFeePcVo.ppFeePcEntity.active':queryForm.getForm().findField('active').getValue()
					
			    };
			  Ext.apply(store.proxy.extraParams, params);
			}
		}
	}
});
Ext.define('Miser.view.extrafee.PricePackageFeePcEntity.QueryForm',{
	extend:'Ext.form.Panel',
	id:'queryForm',
	frame: true,
	title:extrafee.ppFeePc.i18n('miser.common.queryCondition'),//标题
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
         name: 'priceCity',
         fieldLabel: '价格城市',// '词条名称',
         xtype: 'startPriceCityselector',
     },{
         name: 'transTypeCode',
         fieldLabel: '运输类型',// '词条名称',
         xtype : 'transtypecombselector'
     }]}
	],
	me.buttons = [{
		text:extrafee.ppFeePc.i18n('miser.common.query'),
		handler: function() {
			if (me.getForm().isValid()) {
				
				me.up().getFormGridName().getPagingToolbar().moveFirst();
			}
		}
	},
	{
		text:extrafee.ppFeePc.i18n('miser.common.reset'),
		handler: function() {
			me.getForm().reset();
		}
	}];
	me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PricePackageFeePcEntityChangeForm',{
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
		colspan: 2,
		allowBlank:false,
		fieldLabel:extrafee.ppFeePc.i18n('extrafee.ppFeePc.code'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'priceCity',
		xtype : 'startPriceCityselector',
		allowBlank:false,
		fieldLabel:extrafee.ppFeePc.i18n('extrafee.ppFeePc.priceCity'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
    	xtype:'transtypecombselector',
        name: 'transTypeCode',
        showAll:false,
        allowBlank:false,
        width: 250,
        fieldLabel:extrafee.ppFeePc.i18n('extrafee.ppFeePc.transTypeName'),  // '运输类型'
        beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
    },{
		name:'calculationType',
		xtype : 'dataDictionarySelector',
		termsCode:'CALCULATION_TYPE',
		allowBlank:false,
		fieldLabel:extrafee.ppFeePc.i18n('extrafee.ppFeePc.calculationType'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'lockType',
		xtype : 'dataDictionarySelector',
		termsCode:'LOCK_TYPE',
		allowBlank:false,
		fieldLabel:extrafee.ppFeePc.i18n('extrafee.ppFeePc.lockType'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'money',
		xtype :'numberfield',
		decimalPrecision:2,
		maxValue:99999999.99,
		minValue: 0,
		allowBlank:false,
		fieldLabel:extrafee.ppFeePc.i18n('extrafee.ppFeePc.money'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'rate',
		xtype :'numberfield',
		decimalPrecision:2,
		minValue: 0,
		allowBlank:false,
		fieldLabel:extrafee.ppFeePc.i18n('extrafee.ppFeePc.rate'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'minMoney',
		xtype :'numberfield',
		maxValue:99999999.99,
		decimalPrecision:2,
		minValue: 0,
		allowBlank:false,
		fieldLabel:extrafee.ppFeePc.i18n('extrafee.ppFeePc.minMoney'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'maxMoney2',
		xtype :'numberfield',
		maxValue:99999999.99,
		decimalPrecision:2,
		minValue: 0,
		allowBlank:false,
		fieldLabel:extrafee.ppFeePc.i18n('extrafee.ppFeePc.maxMoney2'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'effectiveTime',
		xtype : 'datefield',
		value:new Date(),
		format : 'Y-m-d H:i:s',
		allowBlank:false,
		colspan: 2,
		fieldLabel:extrafee.ppFeePc.i18n('extrafee.ppFeePc.effectiveTime'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'remark',
		maxLength: 200,
		xtype: 'textareafield',
        grow: true,
        colspan: 2, 
        width: 575,
        anchor: '100%',
		fieldLabel:extrafee.ppFeePc.i18n('extrafee.ppFeePc.remark')//这里是注释
	},{
		name:'id',
		hidden:true,
		width:250
	}
	  ];
	me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PricePackageFeePcEntityAddWindow',{
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
			me.getPricePackageFeePcEntityChangeForm().getForm().reset();
		},
		beforeshow: function(me) {
			var fielsds = me.getPricePackageFeePcEntityChangeForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			var effectiveTime = me.getPricePackageFeePcEntityChangeForm().getForm().findField('effectiveTime');
            effectiveTime.setMinValue(new Date(new Date().getTime()));
            effectiveTime.setValue(new Date(new Date().getTime()+6*60*1000));
		}
	},
	PricePackageFeePcEntityChangeForm:null,
	getPricePackageFeePcEntityChangeForm:function() {
		if(Ext.isEmpty(this.PricePackageFeePcEntityChangeForm)){
			this.PricePackageFeePcEntityChangeForm=Ext.create('Miser.view.extrafee.PricePackageFeePcEntityChangeForm')
		}
		return this.PricePackageFeePcEntityChangeForm
	},
	 submitAddForm: function() {
		var me=this;
		if(me.getPricePackageFeePcEntityChangeForm().getForm().isValid()) {
			var effectiveTime = me.getPricePackageFeePcEntityChangeForm().getForm().findField('effectiveTime');
        	if(effectiveTime.getValue().getTime()<new Date().getTime()){
        		miser.showInfoMsg("请选择当前时间之后的时间");
        		effectiveTime.focus();
        		return;
        	}
			var addEntity=new Miser.model.extrafee.PricePackageFeePcEntityModel;
			me.getPricePackageFeePcEntityChangeForm().getForm().updateRecord(addEntity);
			var codeCombo = me.getPricePackageFeePcEntityChangeForm().getForm().findField('ppFeeItems_code'); 
			var curData=addEntity.data;
			 var ppFeeItems=new Object(); 
			 curData.id='';
			 ppFeeItems.code=curData.ppFeeItems_code;
			 ppFeeItems.name=codeCombo.getRawValue();
			 curData.ppFeeItems=ppFeeItems;
			 var params = {
			         'ppFeePcVo': {
			             'ppFeePcEntity': curData
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
					miser.showErrorMes(extrafee.ppFeePc.i18n('miser.extrafee.timeout')); // 请求超时
				}else{
					var message = json.message;
					miser.showErrorMes(message);
				}
			};
			miser.requestJsonAjax('../extrafee/ppFeePc!addFeePc.action', params, successFun, failureFun);
		}
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.fbar = [{
			text:extrafee.ppFeePc.i18n('miser.common.save'),//'保存',
			handler: function() {
				me.submitAddForm();
			}
		},{
			text:extrafee.ppFeePc.i18n('miser.common.reset'),// '重置',
			handler: function() {
				me.getPricePackageFeePcEntityChangeForm().reset();
			}
		},{
			text: extrafee.ppFeePc.i18n('miser.common.cancel'),// '取消',
			handler: function() {
				me.close();
			}
		}];
		me.items = [me.getPricePackageFeePcEntityChangeForm()];
		me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PricePackageFeePcEntityUpdateWindow',{
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
			me.getPricePackageFeePcEntityChangeForm().getForm().reset();
		},
		beforeshow: function(me) {
			var fielsds = me.getPricePackageFeePcEntityChangeForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
		  	fielsds = null;
			me.getPricePackageFeePcEntityChangeForm().getForm().loadRecord(new Miser.model.extrafee.PricePackageFeePcEntityModel(me.updateEntity))
			//这块是处理下拉框赋值的问题
			var codeCombo = me.getPricePackageFeePcEntityChangeForm().getForm().findField('ppFeeItems_code');
			var newcodeComboRecord=Ext.data.Record({
				code: me.updateEntity.ppFeeItems.code,
			    name: me.updateEntity.ppFeeItems.name
				}
			);
			codeCombo.getStore().add(newcodeComboRecord);
			codeCombo.setValue(me.updateEntity.ppFeeItems.code);
			codeCombo.disable();
			//运输类型
			var codeCombo = me.getPricePackageFeePcEntityChangeForm().getForm().findField('transTypeCode');
			var newcodeComboRecord=Ext.data.Record({
				code: me.updateEntity.transTypeCode,
			    name: me.updateEntity.transTypeName
				}
			);
			codeCombo.getStore().add(newcodeComboRecord);
			codeCombo.setValue(me.updateEntity.transTypeCode);
			codeCombo.disable();
			
			
			//出发价格城市 这块是处理下拉框赋值的问题
			var sendPriceCityCombo = me.getPricePackageFeePcEntityChangeForm().getForm().findField('priceCity');
			var sendPriceCityComboRecord=Ext.data.Record({
				code: me.updateEntity.priceCity,
				name: me.updateEntity.priceCityName
				}
			);
			sendPriceCityCombo.getStore().add(sendPriceCityComboRecord);
			sendPriceCityCombo.setValue(me.updateEntity.priceCity);
			sendPriceCityCombo.disable();
			
			var effectiveTime = me.getPricePackageFeePcEntityChangeForm().getForm().findField('effectiveTime');
            effectiveTime.setMinValue(new Date(new Date().getTime()));
		}
	},
	PricePackageFeePcEntityChangeForm:null,
	getPricePackageFeePcEntityChangeForm:function() {
		if(Ext.isEmpty(this.PricePackageFeePcEntityChangeForm)){
			this.PricePackageFeePcEntityChangeForm=Ext.create('Miser.view.extrafee.PricePackageFeePcEntityChangeForm')
		}
		return this.PricePackageFeePcEntityChangeForm;
	},
	 submitUpdateForm: function() {
		var me=this
		if(me.getPricePackageFeePcEntityChangeForm().getForm().isValid()) {
			var effectiveTime = me.getPricePackageFeePcEntityChangeForm().getForm().findField('effectiveTime');
        	if(effectiveTime.getValue().getTime()<new Date().getTime()){
        		miser.showInfoMsg("请选择当前时间之后的时间");
        		effectiveTime.focus();
        		return;
        	}
			var updateEntity=new Miser.model.extrafee.PricePackageFeePcEntityModel;
			me.getPricePackageFeePcEntityChangeForm().getForm().findField('ppFeeItems_code').enable();
			me.getPricePackageFeePcEntityChangeForm().getForm().findField('priceCity').enable();
			me.getPricePackageFeePcEntityChangeForm().getForm().findField('transTypeCode').enable();
			me.getPricePackageFeePcEntityChangeForm().getForm().updateRecord(updateEntity);
			 var curData=updateEntity.data;
			 var ppFeeItems=new Object(); 
			 var codeCombo = me.getPricePackageFeePcEntityChangeForm().getForm().findField('ppFeeItems_code'); 
			 ppFeeItems.code=curData.ppFeeItems_code;
			 ppFeeItems.name=codeCombo.getRawValue();
			 curData.ppFeeItems=ppFeeItems;
			 var params = {
			         'ppFeePcVo': {
			             'ppFeePcEntity': curData
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
			miser.requestJsonAjax('../extrafee/ppFeePc!updateFeePc.action', params, successFun, failureFun);
		}
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.fbar = [{
			text:extrafee.ppFeePc.i18n('miser.common.save'),//'保存',
			handler: function() {
				me.submitUpdateForm();
			}
		},{
			text:extrafee.ppFeePc.i18n('miser.common.reset'),// '重置',
			handler: function() {
				me.close();
				me.parent.updateMethod();
			}
		},{
			text: extrafee.ppFeePc.i18n('miser.common.cancel'),// '取消',
			handler: function() {
				me.close();
			}
		}];
		me.items = [me.getPricePackageFeePcEntityChangeForm()];
		me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PricePackageFeePcEntity.Grid',{
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
	PricePackageFeePcEntityAddWindow:null,
	getPricePackageFeePcEntityAddWindow:function() {
		if(this.PricePackageFeePcEntityAddWindow== null) {
			this.PricePackageFeePcEntityAddWindow=Ext.create('Miser.view.extrafee.PricePackageFeePcEntityAddWindow');
			this.PricePackageFeePcEntityAddWindow.parent = this;
		}
		return this.PricePackageFeePcEntityAddWindow;
	},
	PricePackageFeePcEntityUpdateWindow:null,
	getPricePackageFeePcEntityUpdateWindow:function() {
		if(this.PricePackageFeePcEntityUpdateWindow== null) {
			this.PricePackageFeePcEntityUpdateWindow=Ext.create('Miser.view.extrafee.PricePackageFeePcEntityUpdateWindow');
			this.PricePackageFeePcEntityUpdateWindow.parent = this;
		}
		return this.PricePackageFeePcEntityUpdateWindow;
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
			'ppFeePcVo': {
			     'ppFeePcEntity': {
			     	'id':id
			     	}
			    }
			 };
		var successFun = function (json) {
				var updateWindow = me.getPricePackageFeePcEntityUpdateWindow();
				updateWindow.updateEntity =json.ppFeePcVo.ppFeePcEntity;
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
		miser.requestJsonAjax('../extrafee/ppFeePc!queryFeePcById.action', params, successFun, failureFun);
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
			miser.showWoringMessage(extrafee.ppFeePc.i18n('bizbase.base.oneselected'));
			return;
		}
		miser.showQuestionMes('删除',
		function(e) {
			if (e == 'yes') {
				var params = {
					'ppFeePcVo': {
			     			'ppFeePcEntity': {
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
				miser.requestJsonAjax('../extrafee/ppFeePc!deleteFeePc.action', params, successFun, failureFun);
			 }
		});
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.columns = [
		{
            text: extrafee.ppFeePc.i18n('miser.base.rownumberer'),
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },{
			dataIndex:'ppFeeItems_name',
			width:200,
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.name')
		},{
			dataIndex:'calculationType',
			width:200,
			renderer : function(value) {
				return miser
						.changeCodeToNameStore(
								getDataDictionary()
										.getDataDictionaryStore("CALCULATION_TYPE"),
								value);
			},
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.calculationType')
		},{
			dataIndex:'priceCityName',
			width:150,
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.priceCityName')
		},{
			dataIndex:'transTypeName',
			width:150,
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.transTypeName')
		},{
			dataIndex:'money',
			width:100,
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.money')
		},{
			dataIndex:'rate',
			width:100,
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.rate')
		},{
			dataIndex:'minMoney',
			width:100,
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.minMoney')
		},{
			dataIndex:'maxMoney2',
			width:100,
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.maxMoney2')
		},{
			dataIndex:'lockType',
			width:100,
			renderer : function(value) {
				return miser
						.changeCodeToNameStore(
								getDataDictionary()
										.getDataDictionaryStore("LOCK_TYPE"),
								value);
			},
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.lockType')
		},{
			dataIndex:'remark',
			width:200,
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.remark')
		},{
			dataIndex:'effectiveTime',
			width:200,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.effectiveTime')
		},{
			dataIndex:'active',
			width:100,
			renderer: function(value) {
                return booleanTypeRender(value);
            },
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.active')
		},{
			dataIndex:'id',
			width:200,
			hidden:true,
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.id')
		},{
			dataIndex:'createDate',
			width:200,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.createDate')
		},{
			dataIndex:'createUser',
			width:200,
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.createUser')
		},{
			dataIndex:'modifyDate',
			width:200,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.modifyDate')
		},{
			dataIndex:'modifyUser',
			width:200,
			text:extrafee.ppFeePc.i18n('extrafee.ppFeePc.modifyUser')
		}
		],
		me.store = Ext.create('Miser.store.extrafee.PricePackageFeePcEntityStore', {
				autoLoad: false
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
				mode: 'MULTI',
				checkOnly: true
		});
		me.tbar = [{
				text:extrafee.ppFeePc.i18n('bizbase.base.button.addentry'),
				xtype: 'addbutton',
				width: 80,
				handler: function() {
					me.getPricePackageFeePcEntityAddWindow().show();
				}
			},
			'-',{
				text:extrafee.ppFeePc.i18n('bizbase.base.button.updateentry'),
				xtype: 'updatebutton',
				width: 80,
				handler: function() {
					me.updateMethod();
				}
			},
			'-',{
				text:extrafee.ppFeePc.i18n('bizbase.base.button.deleteentry'),
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
	var queryForm = Ext.create('Miser.view.extrafee.PricePackageFeePcEntity.QueryForm');
	var formGridName = Ext.create('Miser.view.extrafee.PricePackageFeePcEntity.Grid');
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