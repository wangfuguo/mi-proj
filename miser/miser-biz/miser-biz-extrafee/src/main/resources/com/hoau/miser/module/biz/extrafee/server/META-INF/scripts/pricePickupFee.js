extrafee.pricePickupFee.state = 'PRICE_SATUS';

/**
 * 全局变量：查询条件。
 * 导出时用，从查询表单取参数时，可能会出现用户在查询后修改了表单，
 * 那么再进行导出时就会出现：导出的数据和客户在页面上看到的数据不一致
 */
lastQueryCondition = null;
/**
 * 导出提货费
 */
function doExport(){
	if(lastQueryCondition == null) {
		Ext.MessageBox.show({title: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.tips'), 
			msg: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.needQueryFirst'), 
			buttons: Ext.MessageBox.OK, icon: Ext.MessageBox.ERROR});
		return;
	}
	Ext.Msg.wait(extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.handling'), 
			extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.tips'));
	var params=null;
	Ext.Ajax.request({
		url : '../extrafee/pricePickupFee!doExport.action',  
		method : 'post',
		params : lastQueryCondition,  
		success : function(response, options) {  
			//隐藏进度条
			Ext.Msg.hide();
			var responseArray = Ext.util.JSON.decode(response.responseText);
			if(!responseArray.result) {
				Ext.MessageBox.show({title: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.tips'), 
					msg: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.downloadFail'), 
					buttons: Ext.MessageBox.OK, icon: Ext.MessageBox.ERROR});
				return;
			}
			miser.showInfoMsg(extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.successTips', [responseArray.count]));
			miser.requestExportAjax(responseArray.filePath);
	   },
	   failure : function() { 
	        Ext.Msg.hide();  
	        Ext.MessageBox.show({title: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.tips'), 
	        	msg: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.fail'), 
	        	buttons: Ext.MessageBox.OK, icon: Ext.MessageBox.ERROR});  
	   }
	});
}
/**
 * 提货费model
 */
Ext.define('Miser.model.extrafee.PricePickupFeeModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        // 
        type: 'string'
    },{
        name: 'priceCityName',
        type: 'string'
    },{
        name: 'priceCityCode',
        type: 'string'
    },{
        name: 'transportType',
        // 运输类型code
        type: 'string'
    },{
        name: 'transportTypeName',
        // 运输类型名称
        type: 'string'
    },
    {
    	name: 'weightPrice',
    	// 重货单价
    	type: 'string'
    },{
    	name: 'minMoney',
    	// 最低收费
    	type: 'string'
    },
   {
    	name: 'volumnPrice',
    	// 轻货单价
    	type: 'string'
    },
    {
    	name: 'effectiveTime', 
    	// 生效时间
    	type : 'date', 
    	dateFormat:'time'
    },
    {
    	name: 'invalidTime', 
    	//时效时间
    	type : 'date', 
    	dateFormat:'time'
    },
    {
    	name: 'remark',
    	//备注
    	type : 'string', 
    },{
    	name:'state',
    	type:'string',
    },
    {
    	name: 'modifyUser',
    	// 最后修改人
    	type: 'string'
    },
    {
    	name: 'modifyDate',
    	// 最后修改时间
    	type : 'date', 
    	dateFormat:'time'
    },
    {
    	name: 'createUser',
    	// 最后修改人
    	type: 'string'
    },
    {
    	name: 'createDate',
    	// 最后修改时间
    	type : 'date', 
    	dateFormat:'time'
    }
    ]
});

/**
 *提货费store
 */
Ext.define('Miser.store.extrafee.PricePickupFeeStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.model.extrafee.PricePickupFeeModel',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../extrafee/pricePickupFee!queryPricePickupFee.action',
        reader: {
            type: 'json',
            rootProperty: 'pricePickupFeeVo.pricePickupFeeList',
            totalProperty: 'totalCount' // 总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('queryForm');
            if (queryForm != null) {
                var params = {
                	'pricePickupFeeVo.pricePickupFeeEntity.priceCityCode': queryForm.getForm().findField('name').getValue(),
                	'pricePickupFeeVo.pricePickupFeeEntity.transportType': queryForm.getForm().findField('transportType').getValue(),
                	'pricePickupFeeVo.pricePickupFeeEntity.state': queryForm.getForm().findField('state').getValue(),
                };
                lastQueryCondition = params;
                Ext.apply(store.proxy.extraParams, params);
            }
        }
    }
});

/**
 * 查询表单
 */
Ext.define('Miser.view.extrafee.pricePickupFee.QueryForm', {
    extend: 'Ext.form.Panel',
    id: 'queryForm',
    frame: true,
    title: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.queryCondition'),//查询条件
    collapsible: true,
	width : document.documentElement.clientWidth,
	region : 'north',
	defaults : {
		labelWidth : 0.25,
		columnWidth : 0.75,
		margin : '8 10 5 10',
		labelAlign : 'left'
	},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        me.items = [{
			layout : 'column',
			defaults : {
				labelWidth : 70,
				labelAlign : "right"
			},
			defaults : {
				margins : '10 10 10 10'
			},
			items:[ {
				name: 'name',
				fieldLabel:'出发价格城市',
				xtype:'startPriceCityselector',
				labelAlign:'right'
			}, {
				name: 'transportType',
				fieldLabel: '运输类型',
				xtype: 'transtypecombselector',
				labelAlign:'right'
			}, {
				name: 'state',
				fieldLabel: '状态',
				xtype: 'dataDictionarySelector',anyRecords:all,
				termsCode : extrafee.pricePickupFee.state,
				labelAlign:'right'
			}]
        }];
        me.buttons = [{
            text: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.query'),
            handler: function() {
                if (me.getForm().isValid()) {
                    me.up().getPricePickupFeeGrid().getPagingToolbar().moveFirst();
                }
            }
        },
        {
            text: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.reset'),
            handler: function() {
                me.getForm().reset();
            }
        }];
        me.callParent([cfg]);
    }
});
/**
 * 增加表单
 */
Ext.define('Miser.view.extrafee.pricePickupFeeAddForm', {
    extend: 'Ext.form.Panel',
    header: false,
    frame: true,
    collapsible: true,
    width: 350,
    fieldDefaults: {
        labelWidth: 100,
        margin: '8 10 5 10'
    },
    defaultType: 'textfield',
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        
        me.items = [{
        	name: 'id',
        	hidden:true
        },{
        	name: 'priceCityCode',
        	colspan: 2,
        	xtype:'startPriceCityselector',
        	allowBlank:false,
        	fieldLabel: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.priceCityCode'),  //'编码',
        	beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	name: 'transportType',
        	allowBlank:false,
        	flex:1,
        	showAll:false,
            allowBlank:false,
        	xtype:'transtypecombselector',
        	fieldLabel: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.transportTypeName'),  //运输类型,
        	beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	fieldLabel: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.weightPrice'),  //'名称',
        	name: 'weightPrice',
        	xtype :'numberfield',
        	allowBlank:false,
    		decimalPrecision:2,
    		minValue: 0,
        	beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	fieldLabel: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.volumnPrice'),  //'名称',
        	name: 'volumnPrice',
        	allowBlank:false,
        	xtype :'numberfield',
    		decimalPrecision:2,
    		minValue: 0,
        	beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	fieldLabel: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.minMoney'),  //'名称',
        	name: 'minMoney',
        	allowBlank:false,
        	xtype :'numberfield',
    		decimalPrecision:2,
    		minValue: 0,
        	beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },
        {
        	fieldLabel: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.effectiveTime'),  //生效时间,
        	name:'effectiveTime',
    		xtype : 'datefield',
    		value:new Date(new Date().getTime()+5*60*1000),
    		format : 'Y-m-d H:i:s',
    		colspan: 2,
    		allowBlank:false,
    		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	fieldLabel: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.invalidTime'),  //失效时间,
        	name:'invalidTime',
    		xtype : 'datefield',
    		value:new Date(new Date().getTime()+10*60*1000),
    		format : 'Y-m-d H:i:s',
    		colspan: 2,
    		allowBlank:false,
    		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },
        {
        	name:'remark',
        	fieldLabel: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.remark'),  //备注
        	xtype: 'textarea'
        }
           ];
        me.callParent([cfg]);
    }
});
//增加窗体
Ext.define('Miser.view.extrafee.pricePickupFeeAddWindow', {
    extend: 'Ext.window.Window',
    title:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.add'),  //新增
    closable: true,
    parent: null,
    // 父元素
    modal: true,
    resizable: false,
    // 可以调整窗口的大小
    closeAction: 'hide',
    // 点击关闭是隐藏窗口
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    listeners: {
        beforehide: function(me) { // 隐藏WINDOW的时候清除数据
            me.getPricePickupFeeAddForm().getForm().reset(); // 表格重置
        },
        beforeshow: function(me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getPricePickupFeeAddForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function(item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
            me.getPricePickupFeeAddForm().getForm().findField("effectiveTime").setValue(new Date(new Date().getTime()+5*60*1000));
            me.getPricePickupFeeAddForm().getForm().findField("invalidTime").setValue(new Date(new Date().getTime()+10*60*1000));
        }
    },
    pricePickupFeeAddForm: null,
    getPricePickupFeeAddForm: function() {
        if (Ext.isEmpty(this.pricePickupFeeAddForm)) {
            this.pricePickupFeeAddForm = Ext.create('Miser.view.extrafee.pricePickupFeeAddForm');
        }
        return this.pricePickupFeeAddForm;
    },
    submitItemsAddForm: function(isConfirm) {
        var me = this;
        if (me.getPricePickupFeeAddForm().getForm().isValid()) { // 校验form是否通过校验
            var pricePickupFeeEntity = new Miser.model.extrafee.PricePickupFeeModel;
            me.getPricePickupFeeAddForm().getForm().updateRecord(pricePickupFeeEntity); // 将FORM中数据设置到MODEL里面
            var curData=pricePickupFeeEntity.data;
            //清空自动生成的id
            curData.id='';
            var params = {
                'pricePickupFeeVo': {
                    'pricePickupFeeEntity': curData,
                    'isConfirm':isConfirm
                }
            }
            var successFun = function(json) {
                var message = json.message;
                if(message=='更新成功'){
					miser.showInfoMsg(message); //提示修改成功
					me.parent.getStore().reload(); //成功之后重新查询刷新结果集
					me.close();
				}else if (message=='extrafee.deliverry.isConfirm'){
					miser.showQuestionMes('该线路存在相同的待生效数据，是否覆盖?',//是否删除
                            function(e) {
                                if (e == 'yes') { // 询问是否删除，是则发送请求
                                	 me.submitItemsAddForm("1");
                                	 me.parent.getStore().reload(); // 重新查询刷新结果集
                                	 me.close();
                                }
                     });
				}else if (message=='miser.base.stateChange'){
                	miser.showInfoMsg('操作信息状态变更,请重新操作'); // 提示状态信息发生变更
                    me.parent.getStore().reload(); // 重新查询刷新结果集
                    me.close();
                }else if(message=='extrafee.deliverry.downSysdate'){
                	miser.showInfoMsg('生效日期小于当前日期,请重新操作'); // 提示状态信息发生变更
                } else{
//					alert(message);
					me.parent.getStore().reload(); // 重新查询刷新结果集
					me.close();
				}
            };
            var failureFun = function(json) {
                if (Ext.isEmpty(json)) {
//                    miser.showErrorMes(extrafee.pricePickupFee.i18n('miser.pricecard.timeout')); // 请求超时
                } else {
                    var message = json.message;
                    miser.showErrorMes(message); // 提示失败原因
                }
            };
            miser.requestJsonAjax('../extrafee/pricePickupFee!addPricePickupFee.action', params, successFun, failureFun); // 发送AJAX请求
        }
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
//        	text:'提交',
            text: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.save'),//'保存',
            // 保存
            /* margin : '0 0 0 55', */
            handler: function() {
                me.submitItemsAddForm();
            }
        },
        {
//        	text:'取消',
            text: extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.cancel'),// '取消',
            // 取消
            handler: function() {
                me.close();
            }
        }];
        me.items = [me.getPricePickupFeeAddForm()];
        me.callParent([cfg]);
    }
});
/**
 * 修改提货费
 */
	Ext.define('Miser.view.extrafee.pricePickupFeeUpdateWindow',{
		extend : 'Ext.window.Window',
		title:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.modify'),// 修改
		closable : true,
		parent : null, // 父元素
		modal : true,
		resizable : false, // 可以调整窗口的大小
		closeAction : 'hide', // 点击关闭是隐藏窗口
		layout : {
			type : 'vbox',
			align : 'stretch'
		},
		pricePickupFeeEntity: null,
		listeners : {
			beforehide : function(me) { // 隐藏WINDOW的时候清除数据
				me.getPricePickupFeeAddForm().getForm().reset(); // 表格重置
			},
			beforeshow : function(me) { // 显示WINDOW的时候清除数据
				var fielsds = me.getPricePickupFeeAddForm().getForm().getFields();
				if (!Ext.isEmpty(fielsds)) {
					fielsds.each(function(item, index, length) {
						item.clearInvalid();
						item.unsetActiveError();
					});
					me.getPricePickupFeeAddForm().getForm().findField("effectiveTime").setValue(new Date(new Date().getTime()+5*60*1000));
				}
				fielsds = null;
				me.getPricePickupFeeAddForm().getForm().loadRecord(new Miser.model.extrafee.PricePickupFeeModel(me.pricePickupFeeEntity));
				var transportTypeCombo = me.getPricePickupFeeAddForm().getForm().findField('transportType');
				transportTypeCombo.setRawValue(me.pricePickupFeeEntity.transportTypeName);
				transportTypeCombo.disable();
				var codeCombo = me.getPricePickupFeeAddForm().getForm().findField('priceCityCode');
				var newcodeComboRecord=Ext.data.Record({
					code: me.pricePickupFeeEntity.priceCityCode,
				    name: me.pricePickupFeeEntity.priceCityName
					}
				);
				codeCombo.getStore().add(newcodeComboRecord);
				codeCombo.setRawValue(me.pricePickupFeeEntity.priceCityName);
//				codeCombo.setReadOnly(true);
				codeCombo.disable();
			}
		},
		pricePickupFeeAddForm: null,
	    getPricePickupFeeAddForm: function() {
	        if (Ext.isEmpty(this.pricePickupFeeAddForm)) {
	            this.pricePickupFeeAddForm = Ext.create('Miser.view.extrafee.pricePickupFeeAddForm');
	        }
	        return this.pricePickupFeeAddForm;
	    },
		submitDriver:function(isConfirm){
			var me = this;
			var code=me.pricePickupFeeEntity.priceCityCode;
			var transportType=me.pricePickupFeeEntity.transportType;
			var state=me.pricePickupFeeEntity.state;
			if (me.getPricePickupFeeAddForm().getForm().isValid()) { //校验form是否通过校验
				var pricePickupFeeEntity =new Miser.model.extrafee.PricePickupFeeModel;
				me.getPricePickupFeeAddForm().getForm().updateRecord(pricePickupFeeEntity); //将FORM中数据设置到MODEL里面
				var curData=pricePickupFeeEntity.data;
				curData.priceCityCode=code;
				curData.transportType=transportType;
				curData.state=state;
	            var params = {
	                'pricePickupFeeVo': {
	                    'pricePickupFeeEntity': curData,
	                    'isConfirm':isConfirm
	                }
	            }
				var successFun = function (json) {
					var message = json.message;
					if(message=='biz.base.addSuccess'){
						miser.showInfoMsg('添加成功'); //提示修改成功
						me.parent.getStore().reload(); //成功之后重新查询刷新结果集
						me.close();
					}else if (message=='extrafee.deliverry.isConfirm'){
						miser.showQuestionMes('该线路存在相同的待生效数据，是否覆盖?',//是否删除
	                            function(e) {
	                                if (e == 'yes') { // 询问是否删除，是则发送请求
	                                	 me.submitDriver("1");
	                                	 me.parent.getStore().reload(); // 重新查询刷新结果集
	                                	 me.close();
	                                }
	                     });
					}else if (message=='extrafee.pickUp.notModify'){
	                	miser.showInfoMsg('不能修改已失效的数据'); // 提示状态信息发生变更
//	                	me.parent.getStore().reload(); // 重新查询刷新结果集
//	                	me.close();
	                }else if(message=='extrafee.deliverry.downSysdate'){
	                	miser.showInfoMsg('生效日期小于当前日期,请重新操作'); 
	                }else{
//						alert(message);
	                	me.parent.getStore().reload(); // 重新查询刷新结果集
	                	me.close();
					}
				};
				var failureFun = function (json) {
					if (Ext.isEmpty(json)) {
						miser.showErrorMes('连接超时'); //请求超时
					} else {
						var message = json.message;
						miser.showErrorMes(message); //提示失败原因
					}
				};
				miser.requestJsonAjax('../extrafee/pricePickupFee!updatePricePickupFee.action', params, successFun, failureFun); //发送AJAX请求
			}
		},
		constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.fbar = [{
				text :extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.save'), //保存
				margin : '0 0 0 305',
				handler : function() {
					 me.submitDriver();
				}
			},{
				text :extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.cancel'), //取消
				handler : function() {
					me.close();
				}
			} ];
			me.items = [ me.getPricePickupFeeAddForm() ];
			me.callParent([ cfg ]);
		}
	});
/**
 提货费信息表格
 */
Ext.define('Miser.view.extrafee.pricePickupFee.Grid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    height: document.documentElement.clientHeight - 110,
    stripeRows: true,
    // 交替行效果
    selType: "rowmodel",
    // 选择类型设置为：行选择
    emptyText:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.isEmpty'),
    // 查询结果为空
    columnLines: true,
    viewConfig: {
        enableTextSelection: true
    },
    pricePickupFeeAddWindow: null,
    getPricePickupFeeAddWindow: function() {
        if (this.pricePickupFeeAddWindow == null) {
            this.pricePickupFeeAddWindow = Ext.create('Miser.view.extrafee.pricePickupFeeAddWindow');
            this.pricePickupFeeAddWindow.parent = this; // 父元素
        }
        return this.pricePickupFeeAddWindow;
    },
    pricePickupFeeUpdateWindow: null,
    getPricePickupFeeUpdateWindow: function() {
        if (this.pricePickupFeeUpdateWindow == null) {
            this.pricePickupFeeUpdateWindow = Ext.create('Miser.view.extrafee.pricePickupFeeUpdateWindow');
            this.pricePickupFeeUpdateWindow.parent = this; //父元素
        }
        return this.pricePickupFeeUpdateWindow;
    },
    updatePpfiItems : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var priceCityCode = selections[0].get('priceCityCode');
		var transportType = selections[0].get('transportType');
		var state = selections[0].get('state');
		var params = {
				'pricePickupFeeVo' :{
					'pricePickupFeeEntity' : {
						'priceCityCode' : priceCityCode,
						'transportType':transportType,
						'state':state
					}
				}
			};
		var successFun = function (json) {
			var updateWindow = me.getPricePickupFeeUpdateWindow(); //获得修改窗口
			updateWindow.pricePickupFeeEntity = json.pricePickupFeeVo.pricePickupFeeEntity;
			updateWindow.show(); //显示修改窗口
		};
		var failureFun = function (json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes('请求超时'); //请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax('../extrafee/pricePickupFee!queryPricePickupFeeByEntity.action', params, successFun, failureFun);
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
    deletePpfiItems: function() {
        var me = this;
        var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
        if (selections.length != 1) { // 判断是否至少选中了一条
//            miser.showWoringMessage(extrafee.pricePickupFee.i18n('extrafee.base.oneselected')); // 请选择一条进行作废操作！
            return; // 没有则提示并返回
        }
        var priceCityCode = selections[0].get('priceCityCode');
		var transportType = selections[0].get('transportType');
		var state = selections[0].get('state');
        miser.showQuestionMes('您确认作废选中的'+selections.length+'条提货费记录吗？',//是否删除
        function(e) {
            if (e == 'yes') { // 询问是否删除，是则发送请求
                var params = {
                		'pricePickupFeeVo': {
                            'pricePickupFeeEntity':{
                            	'priceCityCode' : priceCityCode,
        						'transportType':transportType,
        						'state':state
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
//                        miser.showErrorMes(extrafee.pricePickupFee.i18n('extrafee.base.timeout')); // 请求超时
                    } else {
                        var message = json.message;
                        miser.showErrorMes(message);
                    }
                };
                miser.requestJsonAjax('../extrafee/pricePickupFee!deletePricePickupFee.action', params, successFun, failureFun);
            }
        });
    },
    downloadPpfiItems: function() {
    	doExport();
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.columns = [{
            dataIndex: 'priceCityCode',
            width: 100,
            hidden:true
        },{
            text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.no'),
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },
        {
            dataIndex: 'priceCityName',
            width: 120,
            text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.priceCityName'),
        }
        ,{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.transportTypeName'),
        	dataIndex: 'transportTypeName',
        	width: 100
        }
        ,{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.weightPrice'),
        	dataIndex: 'weightPrice',
        	width: 100
        }
        ,{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.volumnPrice'),
        	dataIndex: 'volumnPrice',
        	width: 100
        },{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.minMoney'),
        	dataIndex: 'minMoney',
        	width: 100
        },{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.state'),
        	dataIndex: 'state',
            renderer: function (value) {
                return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(extrafee.pricePickupFee.state), value);
            },
        	width: 100
        },{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.effectiveTime'),
        	dataIndex: 'effectiveTime',
        	renderer: function(value) {
        		 return dateRender(value,'Y-m-d H:i:s');
            },
        	width: 150
        },{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.invalidTime'),
        	dataIndex: 'invalidTime',
        	renderer: function(value) {
        		 return dateRender(value,'Y-m-d H:i:s');
            },
        	width: 150
        },{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.remark'),
        	dataIndex: 'remark',
        	width: 100
        },{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.createUser'),
        	dataIndex: 'createUser',
        	width: 120
        },{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.createDate'),
        	dataIndex: 'createDate',
        	renderer: function(value) {
                return dateRender(value,'Y-m-d H:i:s');
            },
        	width: 150
        }
        ,{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.modifyUser'),
        	dataIndex: 'modifyUser',
        	width: 100
        }
        ,{
        	text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.modifyDate'),
        	dataIndex: 'modifyDate',
        	renderer: function(value) {
                return dateRender(value,'Y-m-d H:i:s');
            },
        	width: 150
        }
        ],
        me.store = Ext.create('Miser.store.extrafee.PricePickupFeeStore', {
            autoLoad: false
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', { // 多选框
            mode: 'MULTI',
            checkOnly: true
        });
        me.tbar = [{
            text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.add'),//'新增'
            xtype: 'addbutton',
            width: 80,
            handler: function() {
                me.getPricePickupFeeAddWindow().show();
            }
        },
        '-', {
        	id:'miser_bizbase_pricePickupFee_update_id',
            text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.modify'),// '修改',
            xtype: 'updatebutton',
            width: 80,
            handler: function() {
                me.updatePpfiItems();
            }
        },
        '-', {
        	id : 'miser_bizbase_pricePickupFee_del_id',
            text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.deleteentry'),// '删除',
            xtype: 'deletebutton',
            disabled : true,
            width: 80,
            handler: function() {
                me.deletePpfiItems();
            }
        },
        '-', {
            text:extrafee.pricePickupFee.i18n('extrafee.pricePickupFee.export'),// '导出',
            xtype: 'downloadbutton',
            width: 80,
            handler: function() {
                me.downloadPpfiItems();
            }
        }];
        me.bbar = me.getPagingToolbar();
        me.selModel = Ext.create('Ext.selection.CheckboxModel', {
        listeners: {
            selectionchange: function(sm, selections) {
            	var buttonDisable = selections.length !== 1;
            	if (!buttonDisable) {
            		buttonDisable = selections[0].get("state") == 'PASSED' || selections[0].get("state") == 'DELETED'
            	}
                Ext.getCmp('miser_bizbase_pricePickupFee_del_id').setDisabled(buttonDisable);
                Ext.getCmp('miser_bizbase_pricePickupFee_update_id').setDisabled(buttonDisable);
            }
        }
    }),
        // Ext.setGlyphFontFamily('FontAwesome');
        me.callParent([cfg]);
    }
});
Ext.onReady(function() {
    /**
	 * 数据字典页面
	 */
    Ext.QuickTips.init();
    var queryForm = Ext.create('Miser.view.extrafee.pricePickupFee.QueryForm'); //查询FORM
    var pricePickupFeeGrid = Ext.create('Miser.view.extrafee.pricePickupFee.Grid');
    var mainPanel = Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        getQueryForm: function() {
            return queryForm;
        },
        getPricePickupFeeGrid: function() {
            return pricePickupFeeGrid;
        },
        items: [queryForm, pricePickupFeeGrid]
    });
    
    //设置窗口大小改变时的响应
    window.onresize = function(){
    
        //控制整个 panel 的宽度
    	mainPanel.setWidth(document.documentElement.clientWidth);
    	
    	//控制整个 查询表单 的宽度
    	queryForm.setWidth(document.documentElement.clientWidth - 10);
        
        //设置区域一行表单的宽度
    	var items = queryForm.items;
		document.getElementById(items.items[1].items.items[0].getEl().el.parent().id).style.width = (document.documentElement.clientWidth - 10)+'px';
		
		
		//设置查询结果的宽度
		pricePickupFeeGrid.setWidth(document.documentElement.clientWidth - 10);
    };
});