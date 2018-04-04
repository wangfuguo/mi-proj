
/**
 * 包装费model
 */
Ext.define('Miser.model.bizbase.PricePackageFeeItemsModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        // 
        type: 'string'
    },{
        name: 'code',
        // 编号
        type: 'string'
    },{
        name: 'name',
        // 编号
        type: 'string'
    },{
        name: 'active',
        // 编号
        type: 'string'
    },{
        name: 'invalid',
        // 编号
        type: 'string'
    },
    {
    	name: 'remark',
    	// 备注
    	type: 'string'
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
    },{
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
 * 包装费store
 */
Ext.define('Miser.store.bizbase.PricePackageFeeItemsStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.model.bizbase.PricePackageFeeItemsModel',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../bizbase/pricePackageFeeItems!queryPricePackageFeeItems.action',
        reader: {
            type: 'json',
            rootProperty: 'ppfItemsVo.ppfItemsList',
            totalProperty: 'totalCount' // 总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('queryForm');
            if (queryForm != null) {
                var params = {
                	'ppfItemsVo.ppfItemsEntity.name': queryForm.getForm().findField('name').getValue(),
                	'ppfItemsVo.ppfItemsEntity.active': queryForm.getForm().findField('active').getValue(),
                	'ppfItemsVo.ppfItemsEntity.invalid': queryForm.getForm().findField('invalid').getValue()
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
         
    	
    }
    
});


/**
 * 查询表单
 */
Ext.define('Miser.view.bizbase.pricePackageFeeItems.QueryForm', {
    extend: 'Ext.form.Panel',
    id: 'queryForm',
    frame: true,
    title : bizbase.pricePackageFeeItems.i18n('miser.common.queryCondition'),// '查询条件',
    height: 100,
    collapsible: true,
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
            name: 'name',
            fieldLabel: bizbase.pricePackageFeeItems.i18n('bizbase.pricePackageFeeItems.name'),//'名称',
            xtype: 'textfield'
        },{
            name: 'active',
            fieldLabel: bizbase.pricePackageFeeItems.i18n('bizbase.base.active'),
            isShowAll:true,
            value : 'Y',
            xtype: 'yesnocombselector'
        },{
            name: 'invalid',
            fieldLabel: bizbase.pricePackageFeeItems.i18n('bizbase.base.invalid'),
            isShowAll:true,
            value : 'N',
            xtype: 'yesnocombselector'
        }]}],
        me.buttons = [{
            text: bizbase.pricePackageFeeItems.i18n('miser.common.query'),
            handler: function() {
                if (me.getForm().isValid()) {
                    me.up().getPricePackageFeeItemsGrid().getPagingToolbar().moveFirst();
                }
            }
        },
        {
            text: bizbase.pricePackageFeeItems.i18n('miser.common.reset'),
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
Ext.define('Miser.view.bizbase.pricePackageFeeItemsAddForm', {
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
        	width: 250,
        	hidden:true
        },{
        	name: 'code',
        	width: 250,
        	fieldLabel: bizbase.pricePackageFeeItems.i18n('bizbase.pricePackageFeeItems.code'),  //'编码',
        },{
        	name: 'name',
        	width: 250,
        	allowBlank:false,
        	maxLength: 25,
        	fieldLabel: bizbase.pricePackageFeeItems.i18n('bizbase.pricePackageFeeItems.name'),  //'名称',
        	beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	fieldLabel: bizbase.pricePackageFeeItems.i18n('bizbase.pricePackageFeeItems.remark'),  //'名称',
        	name: 'remark',
        	maxLength: 200,
        	width: 250
        }
        ];
        me.callParent([cfg]);
    }
});

//增加窗体
Ext.define('Miser.view.bizbase.pricePackageFeeItemsAddWindow', {
    extend: 'Ext.window.Window',
    title: '新增',// '新增词条',
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
            me.getPricePackageFeeItemsAddForm().getForm().reset(); // 表格重置
        },
        beforeshow: function(me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getPricePackageFeeItemsAddForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function(item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
            var codeField=me.getPricePackageFeeItemsAddForm().getForm().findField('code');
            codeField.hide();
        }
    },
    pricePackageFeeItemsAddForm: null,
    getPricePackageFeeItemsAddForm: function() {
        if (Ext.isEmpty(this.pricePackageFeeItemsAddForm)) {
            this.pricePackageFeeItemsAddForm = Ext.create('Miser.view.bizbase.pricePackageFeeItemsAddForm');
        }
        return this.pricePackageFeeItemsAddForm;
    },
    submitItemsAddForm: function() {
        var me = this;
        if (me.getPricePackageFeeItemsAddForm().getForm().isValid()) { // 校验form是否通过校验
            var ppfiEntity = new Miser.model.bizbase.PricePackageFeeItemsModel;
            me.getPricePackageFeeItemsAddForm().getForm().updateRecord(ppfiEntity); // 将FORM中数据设置到MODEL里面
            var curData=ppfiEntity.data;
            //清空自动生成的id
            curData.id='';
            var params = {
                'ppfItemsVo': {
                    'ppfItemsEntity': curData
                }
            }
            var successFun = function(json) {
                var message = json.message;
                miser.showInfoMsg(message); // 提示新增成功
                me.close();
                me.parent.getStore().load(); // 成功之后重新查询刷新结果集
            };
            var failureFun = function(json) {
                if (Ext.isEmpty(json)) {
                    miser.showErrorMes(bizbase.pricePackageFeeItems.i18n('miser.pricecard.timeout')); // 请求超时
                } else {
                    var message = json.message;
                    miser.showErrorMes(message); // 提示失败原因
                }
            };
            miser.requestJsonAjax('../bizbase/pricePackageFeeItems!addPricePackageFeeItems.action', params, successFun, failureFun); // 发送AJAX请求
        }
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
            text: bizbase.pricePackageFeeItems.i18n('miser.common.save'),//'保存',
            // 保存
            /* margin : '0 0 0 55', */
            handler: function() {
                me.submitItemsAddForm();
            }
        },
        {
            text:bizbase.pricePackageFeeItems.i18n('miser.common.reset'),// '重置',
            // 重置
            handler: function() {
                me.getPricePackageFeeItemsAddForm().reset();
            }
        },
        {
            text: bizbase.pricePackageFeeItems.i18n('miser.common.cancel'),// '取消',
            // 取消
            handler: function() {
                me.close();
            }
        }];
        me.items = [me.getPricePackageFeeItemsAddForm()];
        me.callParent([cfg]);
    }
});


/**
 * 修改包装费
 */
	Ext.define('Miser.view.bizbase.pricePackageFeeItemsUpdateWindow',{
		extend : 'Ext.window.Window',
		title : '修改',//修改
		closable : true,
		parent : null, // 父元素
		modal : true,
		resizable : false, // 可以调整窗口的大小
		closeAction : 'hide', // 点击关闭是隐藏窗口
		layout : {
			type : 'vbox',
			align : 'stretch'
		},
		ppfiEntity: null,
		listeners : {
			beforehide : function(me) { // 隐藏WINDOW的时候清除数据
				me.getPricePackageFeeItemsAddForm().getForm().reset(); // 表格重置
			},
			beforeshow : function(me) { // 显示WINDOW的时候清除数据
				var fielsds = me.getPricePackageFeeItemsAddForm().getForm().getFields();
				if (!Ext.isEmpty(fielsds)) {
					fielsds.each(function(item, index, length) {
						item.clearInvalid();
						item.unsetActiveError();
					});
				}
				fielsds = null;
				me.getPricePackageFeeItemsAddForm().getForm().loadRecord(new Miser.model.bizbase.PricePackageFeeItemsModel(me.ppfiEntity));
				var code = me.getPricePackageFeeItemsAddForm().getForm().findField('code');
				code.setReadOnly(true);
			}
		},
		pricePackageFeeItemsAddForm: null,
	    getPricePackageFeeItemsAddForm: function() {
	        if (Ext.isEmpty(this.pricePackageFeeItemsAddForm)) {
	            this.pricePackageFeeItemsAddForm = Ext.create('Miser.view.bizbase.pricePackageFeeItemsAddForm');
	        }
	        return this.pricePackageFeeItemsAddForm;
	    },
		submitDriver:function(){
			var me = this;
			if (me.getPricePackageFeeItemsAddForm().getForm().isValid()) { //校验form是否通过校验
				var ppfiEntity =new Miser.model.bizbase.PricePackageFeeItemsModel;
				me.getPricePackageFeeItemsAddForm().getForm().updateRecord(ppfiEntity); //将FORM中数据设置到MODEL里面
				var curData=ppfiEntity.data;
	            var params = {
	                'ppfItemsVo': {
	                    'ppfItemsEntity': curData
	                }
	            }
				var successFun = function (json) {
					var message = json.message;
					miser.showInfoMsg(message); //提示修改成功
					me.close();
					me.parent.getStore().load(); //成功之后重新查询刷新结果集
				};
				var failureFun = function (json) {
					if (Ext.isEmpty(json)) {
						miser.showErrorMes('连接超时'); //请求超时
					} else {
						var message = json.message;
						miser.showErrorMes(message); //提示失败原因
					}
				};
				miser.requestJsonAjax('../bizbase/pricePackageFeeItems!updatePricePackageFeeItems.action', params, successFun, failureFun); //发送AJAX请求
			}
		},
		constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.fbar = [{
				text :bizbase.pricePackageFeeItems.i18n('miser.common.save'), //保存
				margin : '0 0 0 305',
				handler : function() {
					 me.submitDriver();
				}
			}, {
				text :bizbase.pricePackageFeeItems.i18n('miser.common.reset'), //重置
				handler : function() {
					 me.getPricePackageFeeItemsAddForm().getForm().loadRecord(
					 new Miser.model.bizbase.PricePackageFeeItemsModel({
						 'code' : me.ppfiEntity.code
					 }));
				}
			},{
				text :bizbase.pricePackageFeeItems.i18n('miser.common.cancel'), //取消
				handler : function() {
					me.close();
				}
			} ];
			me.items = [ me.getPricePackageFeeItemsAddForm() ];
			me.callParent([ cfg ]);
		}
	});
	

/**
 * 包装费信息表格
 */
Ext.define('Miser.view.bizbase.pricePackageFeeItems.Grid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    height: miser.getBrowserHeight() - 120,
    stripeRows: true,
    // 交替行效果
    selType: "rowmodel",
    // 选择类型设置为：行选择
    emptyText: '查询结果为空',
    // 查询结果为空
    columnLines: true,
    viewConfig: {
        enableTextSelection: true
    },
    pricePackageFeeItemsAddWindow: null,
    getPricePackageFeeItemsAddWindow: function() {
        if (this.pricePackageFeeItemsAddWindow == null) {
            this.pricePackageFeeItemsAddWindow = Ext.create('Miser.view.bizbase.pricePackageFeeItemsAddWindow');
            this.pricePackageFeeItemsAddWindow.parent = this; // 父元素
        }
        return this.pricePackageFeeItemsAddWindow;
    },
    pricePackageFeeItemsUpdateWindow: null,
    getPricePackageFeeItemsUpdateWindow: function() {
        if (this.pricePackageFeeItemsUpdateWindow == null) {
            this.pricePackageFeeItemsUpdateWindow = Ext.create('Miser.view.bizbase.pricePackageFeeItemsUpdateWindow');
            this.pricePackageFeeItemsUpdateWindow.parent = this; //父元素
        }
        return this.pricePackageFeeItemsUpdateWindow;
    },
    updatePpfiItems : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var id = selections[0].get('id');
		var active=selections[0].get('active');
		if(active!='Y'){
			miser.showWoringMessage('只有生效中的数据才能进行该项操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var params = {
				'ppfItemsVo' :{
					'ppfItemsEntity' : {
						'id' : id
					}
				}
			};
		var successFun = function (json) {
			var updateWindow = me.getPricePackageFeeItemsUpdateWindow(); //获得修改窗口
			updateWindow.ppfiEntity = json.ppfItemsVo.ppfItemsEntity;
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
		miser.requestJsonAjax('../bizbase/pricePackageFeeItems!queryPricePackageFeeItemsById.action', params, successFun, failureFun);
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
            miser.showWoringMessage(bizbase.pricePackageFeeItems.i18n('bizbase.base.oneselected')); // 请选择一条进行作废操作！
            return; // 没有则提示并返回
        }
        var active=selections[0].get('active');
        var invalid=selections[0].get('invalid');
        if(!(active=='Y'&&invalid=='N')){
        	miser.showWoringMessage("仅有效未作废的信息才能进行该项操作");
        	return;
        }
        miser.showQuestionMes('删除',//是否删除
        function(e) {
            if (e == 'yes') { // 询问是否删除，是则发送请求
                var params = {
                		'ppfItemsVo': {
                            'ppfItemsEntity':{
                            	'id': selections[0].get('id')
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
                        miser.showErrorMes(bizbase.pricePackageFeeItems.i18n('bizbase.base.timeout')); // 请求超时
                    } else {
                        var message = json.message;
                        miser.showErrorMes(message);
                    }
                };
                miser.requestJsonAjax('../bizbase/pricePackageFeeItems!deletePricePackageFeeItems.action', params, successFun, failureFun);
            }
        });
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.columns = [{
            text: bizbase.pricePackageFeeItems.i18n('miser.base.rownumberer'),
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },{
            dataIndex: 'id',
            width: 100,
            hidden:true
        },{
            dataIndex: 'code',
            width: 100,
            text:bizbase.pricePackageFeeItems.i18n('bizbase.pricePackageFeeItems.code')  // '编号'
        },
        {
            dataIndex: 'name',
            width: 120,
            text: bizbase.pricePackageFeeItems.i18n('bizbase.pricePackageFeeItems.name')  //'名称'
        }
        ,{
        	text: bizbase.pricePackageFeeItems.i18n('bizbase.pricePackageFeeItems.remark'),  //'备注',
        	dataIndex: 'remark',
        	width: 100
        },{
        	text: bizbase.pricePackageFeeItems.i18n('bizbase.base.active'),  //'备注',
        	dataIndex: 'active',
        	renderer: function(value) {
                return booleanTypeRender(value);
            },
        	width: 100
        },{
        	text: bizbase.pricePackageFeeItems.i18n('bizbase.base.invalid'),  //'备注',
        	dataIndex: 'invalid',
        	renderer: function(value) {
                return booleanTypeRender(value);
            },
        	width: 100
        },{
        	text: bizbase.pricePackageFeeItems.i18n('bizbase.base.createUser'),  //'修改用户',
        	dataIndex: 'createUser',
        	width: 100
        }
        ,{
        	text: bizbase.pricePackageFeeItems.i18n('bizbase.base.createDate'),  //'修改时间',
        	dataIndex: 'createDate',
        	renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
        	width: 100
        }
        ,{
        	text: bizbase.pricePackageFeeItems.i18n('bizbase.base.modifyUser'),  //'修改用户',
        	dataIndex: 'modifyUser',
        	width: 100
        }
        ,{
        	text: bizbase.pricePackageFeeItems.i18n('bizbase.base.modifyDate'),  //'修改时间',
        	dataIndex: 'modifyDate',
        	renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
        	width: 100
        }
        ],
        me.store = Ext.create('Miser.store.bizbase.PricePackageFeeItemsStore', {
            autoLoad: false
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', { // 多选框
            mode: 'MULTI',
            checkOnly: true
        });
        me.tbar = [{
            text:bizbase.pricePackageFeeItems.i18n('bizbase.base.button.addentry'),//'新增'
            xtype: 'addbutton',
            // 新增
            width: 80,
            //hidden:!pricecard.priceStandard.isPermission('dataDictionary/dataDictionaryAddButton'),
            handler: function() {
                me.getPricePackageFeeItemsAddWindow().show();
            }
        },
        '-', {
            text:bizbase.pricePackageFeeItems.i18n('bizbase.base.button.updateentry'),// '修改',
            xtype: 'updatebutton',
            width: 80,
            handler: function() {
                me.updatePpfiItems();
            }
        },
        '-', {
        	id : 'miser_bizbase_pricePackageFeeItems_del_id',
            text:bizbase.pricePackageFeeItems.i18n('bizbase.base.button.deleteentry'),// '删除',
            xtype: 'deletebutton',
            disabled : true,
            // 作废
            width: 80,
            handler: function() {
                me.deletePpfiItems();
            }
        }];
        me.bbar = me.getPagingToolbar();
        me.selModel = Ext.create('Ext.selection.CheckboxModel', {
        listeners: {
            selectionchange: function(sm, selections) {
                Ext.getCmp('miser_bizbase_pricePackageFeeItems_del_id').setDisabled(selections.length === 0);
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
    var queryForm = Ext.create('Miser.view.bizbase.pricePackageFeeItems.QueryForm'); //查询FORM
    var pricePackageFeeItemsGrid = Ext.create('Miser.view.bizbase.pricePackageFeeItems.Grid');
    Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        getQueryForm: function() {
            return queryForm;
        },
        getPricePackageFeeItemsGrid: function() {
            return pricePackageFeeItemsGrid;
        },
        items: [queryForm, pricePackageFeeItemsGrid]
    });
});