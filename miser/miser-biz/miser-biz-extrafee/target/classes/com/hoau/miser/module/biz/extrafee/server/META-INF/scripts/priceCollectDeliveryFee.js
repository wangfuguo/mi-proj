Ext.define('Miser.model.extrafee.PriceColectDeliveryFeeEntityModel',{
	extend:'Ext.data.Model',
	fields: [{
		name:'id',
		type:'string'
	},{
		//出发价格城市编码
		name:'beginPriceCityCode',
		type:'string'
	},{
		//运输类型编码
        name: 'transTypeCode',
        type: 'string'
    },{
    	//运输类型名称
        name: 'transTypeName',
        type: 'string'
    },{
    	//代收类型标识  0 24小时汇  1 72小时汇
		name:'collectDeliveryType',
		type:'string'
	},{
		//代收手续费率
		name:'collectDeliveryRate',
		type:'string'
	},{
		//费率锁定方式
		name:'rateLockType',
		type:'string'		
	},{
		//最低代收手续费
		name:'lowestCollectDeliveryFee',
		type:'string'
	},{
		//最高代收手续费
		name:'highestCollectDeliveryFee',
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
		name:'status',
		type:'string'
	},{
		name:'createTime',
		dateFormat:'time',
		type:'date'
	},{
		name:'createUserCode',
		type:'string'
	},{
		name:'modifyTime',
		dateFormat:'time',
		type:'date'
	},{
		name:'modifyUserCode',
		type:'string'
	}
	]
});

Ext.define('Miser.store.extrafee.PriceCollectDeliveryFeeEntityStore',{
	extend:'Ext.data.Store',
	model:'Miser.model.extrafee.PriceColectDeliveryFeeEntityModel',
	pageSize:20,
	proxy:{
		type: 'ajax',
		actionMethods: 'post',
		url:'../extrafee/priceCollectDeliveryFeeAction!query.action',
		reader: {
			type: 'json',
			rootProperty: 'priceCollectDeliveryFeeVo.entityList',
			totalProperty: 'totalCount'
		}
	},
	 listeners: {
		beforeload: function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('queryForm');
			
			if (queryForm != null) {
			  var params = {
					  'priceCollectDeliveryFeeVo.entity.beginPriceCityCode': queryForm.getForm().findField('beginPriceCityCode').getValue(),
					  'priceCollectDeliveryFeeVo.entity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),					  
					  'priceCollectDeliveryFeeVo.entity.effectiveTime': queryForm.getForm().findField('effectiveTime').getValue(),
					  'priceCollectDeliveryFeeVo.entity.status': queryForm.getForm().findField('status').getValue(),
					  'priceCollectDeliveryFeeVo.entity.collectDeliveryType': queryForm.getForm().findField('collectDeliveryType').getValue(),
					  'priceCollectDeliveryFeeVo.entity.rateLockType': queryForm.getForm().findField('rateLockType').getValue(),
					  'priceCollectDeliveryFeeVo.entity.active':'Y'
			  };
			  Ext.apply(store.proxy.extraParams, params);
			}
		}
	}
});


Ext.define('Miser.view.extrafee.PriceCollectDeliveryFeeEntityChangeForm',{
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
			//出发价格城市
			name:'beginPriceCityCode',
			allowBlank:false,
			xtype : 'startPriceCityselector',
			priceCityScope:'STANDARD',
			fieldLabel:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.beginPriceCityCode'),
			beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
			width:250
		},{
			//运输类型
	    	xtype:'transtypecombselector',
	        name: 'transTypeCode',
	        showAll:false,
	        allowBlank:false,
	        width: 250,
	        fieldLabel:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.transTypeCode'), 
	        beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
	    },{
	    	//代收类型
			name:'collectDeliveryType',
			allowBlank:false,
			fieldLabel:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.collectDeliveryType'),
			beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
			xtype : 'dataDictionarySelector',
			termsCode : 'COLLECTDELIVERYTYPE',
			width:250
		},{
			//代收手续费率
			name:'collectDeliveryRate',
			xtype:'numberfield',
			width:250,
			fieldLabel:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.collectDeliveryRate'),
		},{
			//最低代收手续费
			name:'lowestCollectDeliveryFee',
			xtype:'numberfield',
			width:250,
			fieldLabel:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.lowestCollectDeliveryFee')
			
		},{
			//最高代收手续费
			name:'highestCollectDeliveryFee',
			xtype:'numberfield',
			width:250,
			fieldLabel:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.highestCollectDeliveryFee')
		},{
			//费率锁定方式
			name:'rateLockType',
			width:250,
			xtype : 'dataDictionarySelector',
			termsCode : 'LOCK_TYPE',			
			fieldLabel:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.rateLockType'),
			renderer:function(value){
				 if(value == 4 ){
					 return null;
				 }
			 }
		},{
			name:'effectiveTime',
			xtype : 'datefield',
			value:new Date(),
			format : 'Y-m-d H:i:s',
			allowBlank:false,
			fieldLabel:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.effectiveTime'),
			beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
			width:250
		},{
			name:'remark',
			fieldLabel:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.remark'),
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

Ext.define('Miser.view.extrafee.PriceCollectDeliveryFeeEntityAddWindow',{
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
			me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().reset();
		},
		beforeshow: function(me) {
			var fielsds = me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			
			var effectiveTime = me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().findField('effectiveTime');
            effectiveTime.setMinValue(new Date(new Date().getTime()));
            effectiveTime.setValue(new Date(new Date().getTime()+6*60*1000));
		}
	},
	priceCollectDeliveryFeeEntityChangeForm:null,
	getPriceCollectDeliveryFeeEntityChangeForm:function() {
		if(Ext.isEmpty(this.priceCollectDeliveryFeeEntityChangeForm)){
			this.priceCollectDeliveryFeeEntityChangeForm=Ext.create('Miser.view.extrafee.PriceCollectDeliveryFeeEntityChangeForm');
		}
		return this.priceCollectDeliveryFeeEntityChangeForm;
	},
	 submitAddForm: function(confirm) {
		var me=this;
		if(me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().isValid()) {
			var effectiveTime = me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().findField('effectiveTime');
        	if(effectiveTime.getValue().getTime() < new Date().getTime()){
        		miser.showInfoMsg("请选择当前时间之后的时间");
        		effectiveTime.focus();
        		return;
        	}
			var addEntity=new Miser.model.extrafee.PriceColectDeliveryFeeEntityModel();
			me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().updateRecord(addEntity);
			 var curData=addEntity.data;
			 curData.id='';
			 var params = {
			         'priceCollectDeliveryFeeVo': {
			             'entity': curData,
				         'confirm':confirm
		              }
		     };
		     var successFun = function(json) {
                var message = json.message;
                if(message=='添加成功'){
                	
                	miser.showInfoMsg(message); // 提示新增成功
                	me.close();
                    me.parent.getStore().load(); // 成功之后重新查询刷新结果集
                    
                }else if (message==extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.isConfirm')){
                	
                	miser.showQuestionMes(message,//是否删除
                            function(e) {
                                if (e == 'yes') { // 询问是否删除，是则发送请求
                                	 me.submitAddForm(1);
                                	 me.close();
                                }
                     });
                }else if (message==extrafee.priceCollectDeliveryFee.i18n('miser.base.stateChange')){
                	
                	miser.showInfoMsg(message); // 提示状态信息发生变更
                	me.close();
                    me.parent.getStore().load(); // 重新查询刷新结果集
                    
                }else{
					alert(message);
				}
            };
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(extrafee.priceCollectDeliveryFee.i18n('miser.extrafee.timeout')); // 请求超时
				}else{
					var message = json.message;
					miser.showErrorMes(message);
				}
			};
			miser.requestJsonAjax('../extrafee/priceCollectDeliveryFeeAction!add.action', params, successFun, failureFun);
		}
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.fbar = [{
			text:extrafee.priceCollectDeliveryFee.i18n('miser.common.save'),//'保存',
			handler: function() {
				me.submitAddForm(0);
			}
		},{
			text:extrafee.priceCollectDeliveryFee.i18n('miser.common.reset'),// '重置',
			handler: function() {
				me.getPriceCollectDeliveryFeeEntityChangeForm().reset();
			}
		},{
			text: extrafee.priceCollectDeliveryFee.i18n('miser.common.cancel'),// '取消',
			handler: function() {
				me.close();
			}
		}];
		me.items = [me.getPriceCollectDeliveryFeeEntityChangeForm()];
		me.callParent([cfg]);
   }
});


Ext.define('Miser.view.extrafee.PriceCollectDeliveryFeeEntityUpdateWindow',{
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
			me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().reset();
		},
		beforeshow: function(me) {
			
			var fielsds = me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().loadRecord(new Miser.model.extrafee.PriceColectDeliveryFeeEntityModel(me.updateEntity))
			

            //出发价格城市
			var beginPriceCityCodeObj = me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().findField('beginPriceCityCode');
			var beginPriceCityCodeRecord=Ext.data.Record({
				  name :  me.updateEntity.beginPriceCityName,
				  code :  me.updateEntity.beginPriceCityCode
				}
			);
			beginPriceCityCodeObj.getStore().add(beginPriceCityCodeRecord);
			beginPriceCityCodeObj.setValue(me.updateEntity.beginPriceCityCode);
			beginPriceCityCodeObj.setReadOnly(true);
			
			
            //运输类型
			var transTypeCodeObj = me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().findField('transTypeCode');
			var transTypeRecord=Ext.data.Record({
				  name : me.updateEntity.transTypeName,
				  code : me.updateEntity.transTypeCode
				}
			);
			transTypeCodeObj.getStore().add(transTypeRecord);
			transTypeCodeObj.setValue(me.updateEntity.transTypeCode);
			transTypeCodeObj.setReadOnly(true);			
			
			
			//代收类型
			var collectDeliveryTypeObj = me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().findField('collectDeliveryType');
			var collectDeliveryTypeRecord=Ext.data.Record({
				code:me.updateEntity.collectDeliveryType,
				name:me.updateEntity.collectDeliveryTypeName 
			});
			
			collectDeliveryTypeObj.getStore().add(collectDeliveryTypeRecord);
			collectDeliveryTypeObj.setValue(me.updateEntity.collectDeliveryType);
			collectDeliveryTypeObj.setReadOnly(true);
			
			//生效时间
			var effectiveTimeObj = me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().findField('effectiveTime');
			effectiveTimeObj.setMinValue(new Date(new Date().getTime()));
		}
	},
	priceCollectDeliveryFeeEntityChangeForm:null,
	getPriceCollectDeliveryFeeEntityChangeForm:function() {
		if(Ext.isEmpty(this.priceCollectDeliveryFeeEntityChangeForm)){
			this.priceCollectDeliveryFeeEntityChangeForm=Ext.create('Miser.view.extrafee.PriceCollectDeliveryFeeEntityChangeForm')
		}
		return this.priceCollectDeliveryFeeEntityChangeForm;
	},
	 submitUpdateForm: function(confirm) {
		var me=this
		if(me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().isValid()) {
			var effectiveTime = me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().findField('effectiveTime');
        	if(effectiveTime.getValue().getTime()<new Date().getTime()){
        		miser.showInfoMsg("请选择当前时间之后的时间");
        		effectiveTime.focus();
        		return;
        	}
        	me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().findField('transTypeCode').enable();
			var updateEntity=new Miser.model.extrafee.PriceColectDeliveryFeeEntityModel();
			me.getPriceCollectDeliveryFeeEntityChangeForm().getForm().updateRecord(updateEntity);
			
			var curData=updateEntity.data;
			 var params = {
			         'priceCollectDeliveryFeeVo': {
			             'entity': curData,
			             'confirm':confirm
			        }
			     }
			 var successFun = function(json) {
	                var message = json.message;
	                if(message=='更新成功'){
	                	miser.showInfoMsg(message); // 提示新增成功
	                	me.close();
	                    me.parent.getStore().load(); // 成功之后重新查询刷新结果集
	                }else if (message==extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.isConfirm')){
	                	miser.showQuestionMes(message,//是否删除
	                            function(e) {
	                                if (e == 'yes') { // 询问是否删除，是则发送请求
	                                	 me.submitUpdateForm(1);
	                                	 me.close();
	                                }
	                     });
	                }else if (message==extrafee.priceCollectDeliveryFee.i18n('miser.base.stateChange')){
	                	miser.showInfoMsg(message); // 提示状态信息发生变更
	                	me.close();
	                    me.parent.getStore().load(); // 重新查询刷新结果集
	                }else{
						alert(message);
					}
	            };
	            var failureFun = function(json) {
	                if (Ext.isEmpty(json)) {
	                    miser.showErrorMes(extrafee.priceCollectDeliveryFee.i18n('miser.extrafee.timeout')); // 请求超时
	                } else {
	                    var message = json.message;
	                    miser.showErrorMes(message); // 提示失败原因
	                }
	            };
			miser.requestJsonAjax('../extrafee/priceCollectDeliveryFeeAction!update.action', params, successFun, failureFun);
		}
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.fbar = [{
			text:extrafee.priceCollectDeliveryFee.i18n('miser.common.save'),//'保存',
			handler: function() {
				me.submitUpdateForm(0);
			}
		},{
			text:extrafee.priceCollectDeliveryFee.i18n('miser.common.reset'),// '重置',
			handler: function() {
				me.close();
				me.parent.updateMethod();
			}
		},{
			text: extrafee.priceCollectDeliveryFee.i18n('miser.common.cancel'),// '取消',
			handler: function() {
				me.close();
			}
		}];
		me.items = [me.getPriceCollectDeliveryFeeEntityChangeForm()];
		me.callParent([cfg]);
   }
});

Ext.define('Miser.view.extrafee.PriceCollectDeliveryFeeEntity.QueryForm',{
	extend:'Ext.form.Panel',
	id:'queryForm',
	frame: true, 
	title:extrafee.priceCollectDeliveryFee.i18n('miser.common.queryCondition'),//查询条件
	height: 165,
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
			//出发价格城市
			name : 'beginPriceCityCode',
			fieldLabel : extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.beginPriceCityCode'),
			xtype : 'startPriceCityselector',
			priceCityScope:'STANDARD'
		},{
			//运输类型
		    name: 'transTypeCode',
		    fieldLabel: extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.transTypeCode'),
		    xtype : 'transtypecombselector',			
		},{
			//有效时间点
			name: 'effectiveTime',
			fieldLabel: extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.effectiveTime'),
			xtype : 'datetimefield',
			format : 'Y-m-d H:i:s',
			margin:'5px 0px 5px 0px',
			labelAlign:'right'			
		},{
		    name: 'status',
		    fieldLabel: extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.status'),
		    xtype : 'dataDictionarySelector',
		    anyRecords:all,
			termsCode : 'PRICE_SATUS'
		}
		]},{
	      	layout: 'hbox',
			defaults: {
				labelWidth: 70,
				labelAlign: "right"
			},items:[{
				//代收类型
				name : 'collectDeliveryType',
				fieldLabel : extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.collectDeliveryType'),
				xtype : 'dataDictionarySelector',
				anyRecords:all,
				termsCode : 'COLLECTDELIVERYTYPE'
			},{
				//费率锁定方式
			    name: 'rateLockType',
			    fieldLabel: extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.rateLockType'),
			    xtype : 'dataDictionarySelector',
			    termsCode : 'LOCK_TYPE',
			    anyRecords : all
			}
		]}
	],
	me.buttons = [{
		text:extrafee.priceCollectDeliveryFee.i18n('miser.common.query'),
		handler: function() {
			if (me.getForm().isValid()) {
				me.up().getFormGridName().getPagingToolbar().moveFirst();
			}
		}
	},
	{
		text:extrafee.priceCollectDeliveryFee.i18n('miser.common.reset'),
		handler: function() {
			me.getForm().reset();
		}
	}];
	me.callParent([cfg]);
   }
});

Ext.define('Miser.view.extrafee.PriceCollectDeliveryFeeEntity.Grid',{
	extend:'Ext.grid.Panel',
	id:'resultGrid',
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
	priceCollectDeliveryFeeEntityAddWindow:null,
	getPriceCollectDeliveryFeeEntityAddWindow:function() {
		if(this.priceCollectDeliveryFeeEntityAddWindow== null) {
			this.priceCollectDeliveryFeeEntityAddWindow=Ext.create('Miser.view.extrafee.PriceCollectDeliveryFeeEntityAddWindow');
			this.priceCollectDeliveryFeeEntityAddWindow.parent = this;
		}
		return this.priceCollectDeliveryFeeEntityAddWindow;
	},
	priceCollectDeliveryFeeEntityUpdateWindow:null,
	getPriceCollectDeliveryFeeEntityUpdateWindow:function() {
		if(this.priceCollectDeliveryFeeEntityUpdateWindow== null) {
			this.priceCollectDeliveryFeeEntityUpdateWindow=Ext.create('Miser.view.extrafee.PriceCollectDeliveryFeeEntityUpdateWindow');
			this.priceCollectDeliveryFeeEntityUpdateWindow.parent = this;
		}
		return this.priceCollectDeliveryFeeEntityUpdateWindow;
	},
	updateMethod:function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var id = selections[0].get('id');
		var state=selections[0].get('status');
		if(state!='WAIT'&&state!='EFFECTIVE'){
			miser.showWoringMessage('只有待生效和生效中的数据才能进行该项操作');
			return; //没有则提示并返回
		}
		var params = {
			'priceCollectDeliveryFeeVo': {
			     'entity': {
			     	'id':id
			      }
			    }
			 };
		var successFun = function (json) {
				var updateWindow = me.getPriceCollectDeliveryFeeEntityUpdateWindow();
				updateWindow.updateEntity =json.priceCollectDeliveryFeeVo.entity;
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
		miser.requestJsonAjax('../extrafee/priceCollectDeliveryFeeAction!queryEntityById.action', params, successFun, failureFun);
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
            miser.showWoringMessage(extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.oneselected')); // 请选择一条进行作废操作！
            return; // 没有则提示并返回
        }
        
        //state类型
        var stateType='';
        var selectedIdStr = '';
        var objectLeng=0;
        for(var i=0;i<selections.length;i++){
        	var status=selections[i].get('status');
    		if(status!='EFFECTIVE'&&status!='WAIT'){
    			miser.showErrorMes('只有待生效和生效中的数据才能进行该项操作');
    			return;
    		}
    		if(stateType==''){
    			stateType=status;
    		}else if(stateType!=status) {
    			miser.showErrorMes('请选择同种状态的数据进行该项操作');
    			return;
    		}
    		
    		selectedIdStr += "'"+selections[i].get('id')+"',";
 
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
				
				console.log("selectedIdStr:"+selectedIdStr);
				
				var tmpSelectedIdStr = selectedIdStr.substring(0,selectedIdStr.length-1);
				
				console.log("tmpSelectedIdStr:"+tmpSelectedIdStr);
				
				var params = {
					'priceCollectDeliveryFeeVo': {
			     			'selectedIdStr' :tmpSelectedIdStr
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
				miser.requestJsonAjax('../extrafee/priceCollectDeliveryFeeAction!delete.action', params, successFun, failureFun);
			 }
		});
	},
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.columns = [{
            text: extrafee.priceCollectDeliveryFee.i18n('miser.base.rownumberer'),
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },{
        	//出发价格城市
			dataIndex:'beginPriceCityName',
			width:100,
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.beginPriceCityCode')
		},{
			//运输类型
			dataIndex:'transTypeName',
			width:100,
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.transTypeCode')
		},{
			//代收类型
			dataIndex:'collectDeliveryName',
			width:100,
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.collectDeliveryType')			
		},{
			//代收手续费率
			dataIndex:'collectDeliveryRate',
			width:100,
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.collectDeliveryRate')			
		},{
			//费率锁定方式
			dataIndex:'rateLockName',
			width:100,
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.rateLockType')
		},{
			//最低化收手续费
			dataIndex:'lowestCollectDeliveryFee',
			width:100,
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.lowestCollectDeliveryFee')
		},{
			//最高化收手续费
			dataIndex:'highestCollectDeliveryFee',
			width:100,
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.highestCollectDeliveryFee')
		},{
			dataIndex:'status',
			width:100,
			 
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.status'),
			renderer: function (value) {
                return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('PRICE_SATUS'), value);
            }
	
		},{
			dataIndex:'effectiveTime',
			width:200,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.effectiveTime')
		},{
			dataIndex:'invalidTime',
			width:200,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.invalidTime')
		},{
			dataIndex:'remark',
			width:200,
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.remark')
		},{
			dataIndex:'active',
			width:100,
			renderer: function(value) {
                return booleanTypeRender(value);
            },
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.active')
		},{
			dataIndex:'id',
			width:200,
			hidden:true,
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.id')
		},{
			dataIndex:'createTime',
			width:100,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.createTime')
		},{
			dataIndex:'createUserCode',
			width:150,
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.createUserCode')
		},{
			dataIndex:'modifyTime',
			width:100,
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.modifyTime')
		},{
			dataIndex:'modifyUserCode',
			width:150,
			text:extrafee.priceCollectDeliveryFee.i18n('extrafee.priceCollectDeliveryFee.modifyUserCode')
		}
		],
		me.store = Ext.create('Miser.store.extrafee.PriceCollectDeliveryFeeEntityStore', {
				autoLoad: false
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
				mode: 'MULTI',
				checkOnly: true
		});
		me.tbar = [{
				text:extrafee.priceCollectDeliveryFee.i18n('bizbase.base.button.addentry'),
				xtype: 'addbutton',
				width: 80,
				handler: function() {
					me.getPriceCollectDeliveryFeeEntityAddWindow().show();
				}
			},
			'-',{
				text:extrafee.priceCollectDeliveryFee.i18n('bizbase.base.button.updateentry'),
				xtype: 'updatebutton',
				width: 80,
				handler: function() {
					me.updateMethod();
				}
			},
			'-',{
				text:extrafee.priceCollectDeliveryFee.i18n('bizbase.base.button.deleteentry'),
				id: 'del_id',
				xtype: 'deletebutton',
				disabled : true,
				width: 80,
				handler: function() {
					me.deleteMethod();
				}
			},
			'-',{
	            alias: 'widget.downloadbutton',
	        	glyph : 0xf019,
	            disabled:false,
	        	cls: 'download-btn-item',
	            text: '导入模板下载',
	            width: 130,
	            handler: function() {
	            	miser.requestExportAjax('/excelTemplate/priceCollectDeliveryFee.xlsx');
	            }
			},
			'-',{
	        	text: '导入',
				xtype: 'uploadbutton',
	            width: 80,
	            handler:function(){
	            	uploadT.excelWindow(null,imple);
	            }
			},
			'-',{
	        	text: '导出',
	            alias: 'widget.downloadbutton',
	        	glyph : 0xf019,
	            disabled:false,
	        	cls: 'download-btn-item',
	            handler: function() {
	            	down();
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

function down(){
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params=null;
	var queryForm = Ext.getCmp('queryForm');
    if (queryForm != null) {
		  params = {
				  'priceCollectDeliveryFeeVo.entity.beginPriceCityCode': queryForm.getForm().findField('beginPriceCityCode').getValue(),
				  'priceCollectDeliveryFeeVo.entity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),					  
				  'priceCollectDeliveryFeeVo.entity.effectiveTime': queryForm.getForm().findField('effectiveTime').getValue(),
				  'priceCollectDeliveryFeeVo.entity.status': queryForm.getForm().findField('status').getValue(),
				  'priceCollectDeliveryFeeVo.entity.collectDeliveryType': queryForm.getForm().findField('collectDeliveryType').getValue(),
				  'priceCollectDeliveryFeeVo.entity.rateLockType': queryForm.getForm().findField('rateLockType').getValue(),
				  'priceCollectDeliveryFeeVo.entity.active':'Y'
		  };
    }
	Ext.Ajax.request( {  
	       url : '../extrafee/priceCollectDeliveryFeeAction!excelExport.action',  
	       method : 'post',  
	       params : params,  
	          success : function(response, options) {  
	               Ext.Msg.hide();   
	               var responseArray = Ext.util.JSON.decode(response.responseText); 
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

//文件上传
var uploadT={
		oufileName:null,
		excelWindow:function(url,fn){
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
				buttons : [ {
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
												fn(action.result.outFileName,true);
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

function imple(fileName,flag){
	if(flag){
		miser.showInfoMsg('上传完成');
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request( {  
	       url : '../extrafee/priceCollectDeliveryFeeAction!excelImport.action',  
	       method : 'post', 
	       timeout:3600000,
	       params : {  
	    	     'priceCollectDeliveryFeeVo.filePath' : fileName  
	       },  
	          success : function(response, options) {  

	               //隐藏进度条   
	               Ext.Msg.hide(); 
	               
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              var msg=responseArray.success;
	              
	              if(!Ext.isEmpty(responseArray.error))
	              {
	              	Ext.MessageBox.show({title: '失败',msg: '导入失败:<br/>' + responseArray.error + ";<br/>请修改excel重新上传.",buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});
	              }
	              else
	              {
	              	miser.showInfoMsg(msg);         
	              }       
	               
	               Ext.getCmp('resultGrid').getStore().load();
	              
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

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext.create('Miser.view.extrafee.PriceCollectDeliveryFeeEntity.QueryForm');
	var formGridName = Ext.create('Miser.view.extrafee.PriceCollectDeliveryFeeEntity.Grid');
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