

		//状态下拉框-start
Ext.define('Miser.model.baseinfo.baseCommonModel',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'name',
		type : 'string'
	},{
		name : 'value',
		type : 'string'
	}]
});

//状态下拉框-end

//状态
function stateTypeRender(value){
	if(Ext.isEmpty(value)){
		return value;
	}
	if('1' == value){
		value= '已失效';
	}else if('2' == value){
		value= '生效中';
	}else if('3' == value){
		value= '待生效';
	}else if('4' == value){
		value= '已作废';
	}
	return value;
}

Ext.define('Miser.model.extrafee.PriceDeliveryFeeCityTypeEntityModel',{
	extend:'Ext.data.Model',
	fields: [{
		name:'cityType',
		type:'string'
	},{
		name:'cityTypeName',
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
		name:'sectionItemCode',
		type:'string'
	},{
		name:'sectionItemName',
		type:'string'
	},{
		name:'effectiveTime',
		dateFormat:'time',
		type:'date'
	},{
		name:'invalidTime',
		dateFormat:'time',
		type:'date'
	},{
		name:'remark',
		type:'string'
	},{
		name:'active',
		type:'string'
	},{
		name:'state',
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
Ext.define('Miser.store.extrafee.PriceDeliveryFeeCityTypeEntityStore',{
	extend:'Ext.data.Store',
	model:'Miser.model.extrafee.PriceDeliveryFeeCityTypeEntityModel',
	pageSize:20,
	proxy:{
		type: 'ajax',
		actionMethods: 'post',
		url:'../extrafee/priceDeliveryFeeCityType!queryListByParam.action',
		reader: {
			type: 'json',
			rootProperty: 'priceDeliveryFeeCityTypeVo.pdfctList',
			totalProperty: 'totalCount'
		}
	},
	 listeners: {
		beforeload: function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('queryForm');
			
			if (queryForm != null) {
			  var params = {
					  'priceDeliveryFeeCityTypeVo.pdfctEntity.cityType': queryForm.getForm().findField('cityType').getValue(),
					  'priceDeliveryFeeCityTypeVo.pdfctEntity.state': queryForm.getForm().findField('state').getValue(),
					  'priceDeliveryFeeCityTypeVo.pdfctEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
					  'priceDeliveryFeeCityTypeVo.pdfctEntity.active':'Y'
			    };
			  Ext.apply(store.proxy.extraParams, params);
			}
		}
	}
});
Ext.define('Miser.view.extrafee.PriceDeliveryFeeCityTypeEntity.QueryForm',{
	extend:'Ext.form.Panel',
	id:'queryForm',
	frame: true,
	title:extrafee.priceDeliveryFeeCityType.i18n('miser.common.queryCondition'),//标题
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
			name : 'cityType',
			fieldLabel : extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.cityType'),
			xtype : 'dataDictionarySelector',anyRecords:all,
			anyRecords : all,
			termsCode : 'CITYTYPE'
		},{
		    name: 'state',
		    fieldLabel: '状态',// '当前状态',
		    xtype : 'dataDictionarySelector',anyRecords:all,
			termsCode : 'PRICE_SATUS'
		},{
	         name: 'transTypeCode',
	         fieldLabel: '运输类型',// '词条名称',
	         xtype : 'transtypecombselector'
	     }]}
	],
	me.buttons = [{
		text:extrafee.priceDeliveryFeeCityType.i18n('miser.common.query'),
		handler: function() {
			if (me.getForm().isValid()) {
				me.up().getFormGridName().getPagingToolbar().moveFirst();
			}
		}
	},
	{
		text:extrafee.priceDeliveryFeeCityType.i18n('miser.common.reset'),
		handler: function() {
			me.getForm().reset();
		}
	}];
	me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PriceDeliveryFeeCityTypeEntityChangeForm',{
	extend:'Ext.form.Panel',
	header: false,
	frame: true,
	closable: true,
	width: 350,
	fieldDefaults: {
		labelWidth: 100,
		margin: '8 10 5 10',
	},
	defaultType: 'textfield',
	constructor: function(config) {
	  var me = this,
	  cfg = Ext.apply({},config);
	  me.items = [{
		name:'cityType',
		allowBlank:false,
		xtype : 'dataDictionarySelector',
		termsCode : 'CITYTYPE',
		fieldLabel:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.cityType'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
    	xtype:'transtypecombselector',
        name: 'transTypeCode',
        showAll:false,
        allowBlank:false,
        width: 250,
        fieldLabel:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.transTypeName'),  // '运输类型'
        beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
    },{
		name:'sectionItemCode',
		allowBlank:false,
		fieldLabel:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.sectionItemCode'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		xtype: 'dynamicPriceSectioncombselector',
		sectionedItem :'DELIVERY',
		width:250
	},{
		name:'effectiveTime',
		xtype : 'datefield',
		value:new Date(),
		format : 'Y-m-d H:i:s',
		allowBlank:false,
		fieldLabel:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.effectiveTime'),//这里是注释
		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
		width:250
	},{
		name:'remark',
		fieldLabel:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.remark'),//这里是注释
		colspan: 3,
    	xtype: 'textareafield',
        grow: true,
        maxLength: 200,
        width: 520,
        anchor: '100%'
	},{
		name:'id',
		hidden:true,
		width:250
	}
	  ];
	me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PriceDeliveryFeeCityTypeEntityAddWindow',{
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
			me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().reset();
		},
		beforeshow: function(me) {
			var fielsds = me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			var effectiveTime = me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().findField('effectiveTime');
            effectiveTime.setMinValue(new Date(new Date().getTime()));
            effectiveTime.setValue(new Date(new Date().getTime()+6*60*1000));
		}
	},
	priceDeliveryFeeCityTypeEntityChangeForm:null,
	getPriceDeliveryFeeCityTypeEntityChangeForm:function() {
		if(Ext.isEmpty(this.priceDeliveryFeeCityTypeEntityChangeForm)){
			this.priceDeliveryFeeCityTypeEntityChangeForm=Ext.create('Miser.view.extrafee.PriceDeliveryFeeCityTypeEntityChangeForm')
		}
		return this.priceDeliveryFeeCityTypeEntityChangeForm
	},
	 submitAddForm: function(isConfirm) {
		var me=this;
		if(me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().isValid()) {
			var effectiveTime = me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().findField('effectiveTime');
        	if(effectiveTime.getValue().getTime()<new Date().getTime()){
        		miser.showInfoMsg("请选择当前时间之后的时间");
        		effectiveTime.focus();
        		return;
        	}
			var addEntity=new Miser.model.extrafee.PriceDeliveryFeeCityTypeEntityModel;
			me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().updateRecord(addEntity);
			 var curData=addEntity.data;
			 curData.id='';
			 var params = {
			         'priceDeliveryFeeCityTypeVo': {
			             'pdfctEntity': curData
			             ,'isConfirm':isConfirm
		                }
		            };
		     var successFun = function(json) {
		                var message = json.message;
		                if(message=='添加成功'){
		                	miser.showInfoMsg(message); // 提示新增成功
		                	me.close();
		                    me.parent.getStore().load(); // 成功之后重新查询刷新结果集
		                }else if (message==extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.isConfirm')){
		                	miser.showQuestionMes(message,//是否删除
		                            function(e) {
		                                if (e == 'yes') { // 询问是否删除，是则发送请求
		                                	 me.submitAddForm("1");
		                                	 me.close();
		                                }
		                     });
		                }else if (message==extrafee.priceDeliveryFeeCityType.i18n('miser.base.stateChange')){
		                	miser.showInfoMsg(message); // 提示状态信息发生变更
		                	me.close();
		                    me.parent.getStore().load(); // 重新查询刷新结果集
		                }else{
							alert(message);
						}
		            };
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(extrafee.priceDeliveryFeeCityType.i18n('miser.extrafee.timeout')); // 请求超时
				}else{
					var message = json.message;
					miser.showErrorMes(message);
				}
			};
			miser.requestJsonAjax('../extrafee/priceDeliveryFeeCityType!addPriceDeliveryFeeCityType.action', params, successFun, failureFun);
		}
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.fbar = [{
			text:extrafee.priceDeliveryFeeCityType.i18n('miser.common.save'),//'保存',
			handler: function() {
				me.submitAddForm('0');
			}
		},{
			text:extrafee.priceDeliveryFeeCityType.i18n('miser.common.reset'),// '重置',
			handler: function() {
				me.getPriceDeliveryFeeCityTypeEntityChangeForm().reset();
			}
		},{
			text: extrafee.priceDeliveryFeeCityType.i18n('miser.common.cancel'),// '取消',
			handler: function() {
				me.close();
			}
		}];
		me.items = [me.getPriceDeliveryFeeCityTypeEntityChangeForm()];
		me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PriceDeliveryFeeCityTypeEntityUpdateWindow',{
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
			me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().reset();
		},
		beforeshow: function(me) {
			var fielsds = me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().loadRecord(new Miser.model.extrafee.PriceDeliveryFeeCityTypeEntityModel(me.updateEntity))
			var cityType = me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().findField('cityType');
			cityType.setReadOnly(true);
			//这块是处理下拉框赋值的问题
			var sectionItemCodeCombo = me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().findField('sectionItemCode');
			var sectionItemCodeComboRecord=Ext.data.Record({
				code: me.updateEntity.sectionItemCode,
				name: me.updateEntity.sectionItemName
			}
			);
			sectionItemCodeCombo.getStore().add(sectionItemCodeComboRecord);
			sectionItemCodeCombo.setValue(me.updateEntity.sectionItemCode);
			 var effectiveTime = me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().findField('effectiveTime');
	            effectiveTime.setMinValue(new Date(new Date().getTime()));
	          //运输类型
				var codeCombo = me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().findField('transTypeCode');
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
	priceDeliveryFeeCityTypeEntityChangeForm:null,
	getPriceDeliveryFeeCityTypeEntityChangeForm:function() {
		if(Ext.isEmpty(this.priceDeliveryFeeCityTypeEntityChangeForm)){
			this.priceDeliveryFeeCityTypeEntityChangeForm=Ext.create('Miser.view.extrafee.PriceDeliveryFeeCityTypeEntityChangeForm')
		}
		return this.priceDeliveryFeeCityTypeEntityChangeForm;
	},
	 submitUpdateForm: function(isConfirm) {
		var me=this
		if(me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().isValid()) {
			var effectiveTime = me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().findField('effectiveTime');
        	if(effectiveTime.getValue().getTime()<new Date().getTime()){
        		miser.showInfoMsg("请选择当前时间之后的时间");
        		effectiveTime.focus();
        		return;
        	}
        	me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().findField('transTypeCode').enable();
			var updateEntity=new Miser.model.extrafee.PriceDeliveryFeeCityTypeEntityModel;
			me.getPriceDeliveryFeeCityTypeEntityChangeForm().getForm().updateRecord(updateEntity);
			
			var curData=updateEntity.data;
			 var params = {
			         'priceDeliveryFeeCityTypeVo': {
			             'pdfctEntity': curData
			             ,'isConfirm':isConfirm
			        }
			     }
			 var successFun = function(json) {
	                var message = json.message;
	                if(message=='更新成功'){
	                	miser.showInfoMsg(message); // 提示新增成功
	                	me.close();
	                    me.parent.getStore().load(); // 成功之后重新查询刷新结果集
	                }else if (message==extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.isConfirm')){
	                	miser.showQuestionMes(message,//是否删除
	                            function(e) {
	                                if (e == 'yes') { // 询问是否删除，是则发送请求
	                                	 me.submitUpdateForm("1");
	                                	 me.close();
	                                }
	                     });
	                }else if (message==extrafee.priceDeliveryFeeCityType.i18n('miser.base.stateChange')){
	                	miser.showInfoMsg(message); // 提示状态信息发生变更
	                	me.close();
	                    me.parent.getStore().load(); // 重新查询刷新结果集
	                }else{
						alert(message);
					}
	            };
	            var failureFun = function(json) {
	                if (Ext.isEmpty(json)) {
	                    miser.showErrorMes(extrafee.priceDeliveryFeeCityType.i18n('miser.extrafee.timeout')); // 请求超时
	                } else {
	                    var message = json.message;
	                    miser.showErrorMes(message); // 提示失败原因
	                }
	            };
			miser.requestJsonAjax('../extrafee/priceDeliveryFeeCityType!updatePriceDeliveryFeeCityType.action', params, successFun, failureFun);
		}
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.fbar = [{
			text:extrafee.priceDeliveryFeeCityType.i18n('miser.common.save'),//'保存',
			handler: function() {
				me.submitUpdateForm('0');
			}
		},{
			text:extrafee.priceDeliveryFeeCityType.i18n('miser.common.reset'),// '重置',
			handler: function() {
				me.close();
				me.parent.updateMethod();
			}
		},{
			text: extrafee.priceDeliveryFeeCityType.i18n('miser.common.cancel'),// '取消',
			handler: function() {
				me.close();
			}
		}];
		me.items = [me.getPriceDeliveryFeeCityTypeEntityChangeForm()];
		me.callParent([cfg]);
   }
});
Ext.define('Miser.view.extrafee.PriceDeliveryFeeCityTypeEntity.Grid',{
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
	priceDeliveryFeeCityTypeEntityAddWindow:null,
	getPriceDeliveryFeeCityTypeEntityAddWindow:function() {
		if(this.priceDeliveryFeeCityTypeEntityAddWindow== null) {
			this.priceDeliveryFeeCityTypeEntityAddWindow=Ext.create('Miser.view.extrafee.PriceDeliveryFeeCityTypeEntityAddWindow');
			this.priceDeliveryFeeCityTypeEntityAddWindow.parent = this;
		}
		return this.priceDeliveryFeeCityTypeEntityAddWindow;
	},
	priceDeliveryFeeCityTypeEntityUpdateWindow:null,
	getPriceDeliveryFeeCityTypeEntityUpdateWindow:function() {
		if(this.priceDeliveryFeeCityTypeEntityUpdateWindow== null) {
			this.priceDeliveryFeeCityTypeEntityUpdateWindow=Ext.create('Miser.view.extrafee.PriceDeliveryFeeCityTypeEntityUpdateWindow');
			this.priceDeliveryFeeCityTypeEntityUpdateWindow.parent = this;
		}
		return this.priceDeliveryFeeCityTypeEntityUpdateWindow;
	},
	updateMethod:function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var id = selections[0].get('id');
		var state=selections[0].get('state');
		if(state!='EFFECTIVE'&&state!='WAIT'){
			miser.showWoringMessage('只有待生效和生效中的数据才能进行该项操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var params = {
			'priceDeliveryFeeCityTypeVo': {
			     'pdfctEntity': {
			     	'id':id
			     	}
			    }
			 };
		var successFun = function (json) {
				var updateWindow = me.getPriceDeliveryFeeCityTypeEntityUpdateWindow();
				updateWindow.updateEntity =json.priceDeliveryFeeCityTypeVo.pdfctEntity;
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
		miser.requestJsonAjax('../extrafee/priceDeliveryFeeCityType!queryPriceDeliveryFeeCityTypeById.action', params, successFun, failureFun);
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
		if (selections.length < 1) { // 判断是否至少选中了一条
            miser.showWoringMessage(extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.oneselected')); // 请选择一条进行作废操作！
            return; // 没有则提示并返回
        }
        
        //state类型
        var stateType='';
        var objects='';
        var objectLeng=0;
        for(var i=0;i<selections.length;i++){
        	var object="{"
        	var state=selections[i].get('state');
    		if(state!='EFFECTIVE'&&state!='WAIT'){
    			miser.showErrorMes('只有待生效和生效中的数据才能进行该项操作');
    			return;
    		}
    		if(stateType==''){
    			stateType=state;
    		}else if(stateType!=state) {
    			miser.showErrorMes('请选择同种状态的数据进行该项操作');
    			return;
    		}
        	object+="\"id\":\""+selections[i].get('id')+"\",\"state\":\""+selections[0].get('state')+"\",\"active\":\""+selections[0].get('active')+"\"";
        	object+="}"
        	if(i!=selections.length-1)
        	object+=","
        	objects+=object;
        	objectLeng++;
        }
        var msg="您确认作废选中的"+objectLeng+"条信息吗？确认后状态会变为";
        if(stateType==3){//待生效数据
        	msg+="已作废";
        }else{
        	msg+="已失效";
        }
        msg+="并会保留您的操作记录.";
		miser.showQuestionMes(msg,
		function(e) {
			if (e == 'yes') {
				var params = {
					'priceDeliveryFeeCityTypeVo': {
			     				'selects' :"["+ objects+"]"
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
				miser.requestJsonAjax('../extrafee/priceDeliveryFeeCityType!deletePriceDeliveryFeeCityType.action', params, successFun, failureFun);
			 }
		});
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.columns = [{
            text: extrafee.priceDeliveryFeeCityType.i18n('miser.base.rownumberer'),
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },{
			dataIndex:'cityType',
			width:100,
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.cityTypeName'),
			renderer : function(value) {
				return miser
						.changeCodeToNameStore(
								getDataDictionary()
										.getDataDictionaryStore('CITYTYPE'),
								value);
			}
		},{
			dataIndex:'sectionItemName',
			width:100,
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.sectionItemName')
		},{
			dataIndex:'effectiveTime',
			width:200,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.effectiveTime')
		},{
			dataIndex:'invalidTime',
			width:200,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.invalidTime')
		},{
			dataIndex:'remark',
			width:200,
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.remark')
		},{
			dataIndex:'active',
			width:100,
			renderer: function(value) {
                return booleanTypeRender(value);
            },
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.active')
		},{
			dataIndex:'state',
			width:100,
			renderer : function(value) {
				return miser
						.changeCodeToNameStore(
								getDataDictionary()
										.getDataDictionaryStore('PRICE_SATUS'),
								value);
			},
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.state')
		},{
			dataIndex:'id',
			width:200,
			hidden:true,
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.id')
		},{
			dataIndex:'transTypeName',
			width:100,
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.transTypeName')
		},{
			dataIndex:'createDate',
			width:100,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.createDate')
		},{
			dataIndex:'createUser',
			width:150,
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.createUser')
		},{
			dataIndex:'modifyDate',
			width:100,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.modifyDate')
		},{
			dataIndex:'modifyUser',
			width:150,
			text:extrafee.priceDeliveryFeeCityType.i18n('extrafee.priceDeliveryFeeCityType.modifyUser')
		}
		],
		me.store = Ext.create('Miser.store.extrafee.PriceDeliveryFeeCityTypeEntityStore', {
				autoLoad: false
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
				mode: 'MULTI',
				checkOnly: true
		});
		me.tbar = [{
				text:extrafee.priceDeliveryFeeCityType.i18n('bizbase.base.button.addentry'),
				xtype: 'addbutton',
				width: 80,
				handler: function() {
					me.getPriceDeliveryFeeCityTypeEntityAddWindow().show();
				}
			},
			'-',{
				text:extrafee.priceDeliveryFeeCityType.i18n('bizbase.base.button.updateentry'),
				xtype: 'updatebutton',
				width: 80,
				handler: function() {
					me.updateMethod();
				}
			},
			'-',{
				text:extrafee.priceDeliveryFeeCityType.i18n('bizbase.base.button.deleteentry'),
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
	var queryForm = Ext.create('Miser.view.extrafee.PriceDeliveryFeeCityTypeEntity.QueryForm');
	var formGridName = Ext.create('Miser.view.extrafee.PriceDeliveryFeeCityTypeEntity.Grid');
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