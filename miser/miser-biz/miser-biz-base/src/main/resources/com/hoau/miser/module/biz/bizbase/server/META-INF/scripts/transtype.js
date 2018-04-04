
//定义运输类型数据类型
Ext.define('Miser.biz.base.transtype.TranstypeEntity', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'code', type: 'string'},
        {name: 'name', type: 'string'},
        {name: 'otherName', type: 'string'},
        {name: 'symbol', type: 'string'},
        {name: 'orderNo', type: 'string'},
        {name: 'remark', type: 'string'},
        {name: 'invalid', type: 'string'},
        {name: 'createDate', type: 'date', dateFormat:'time'},
        {name: 'createUser', type: 'string'},
        {name: 'modifyDate', type: 'date', dateFormat:'time'},
        {name: 'modifyUser', type: 'string'},
        {name: 'parentCode', type: 'string'},
        {name: 'parentName', type: 'string'}
    ]
});

//定义主表格数据源
Ext.define('Miser.biz.base.transtype.TranstypeStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.biz.base.transtype.TranstypeEntity',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: 'transtypeAction!queryTranstypes.action',
        reader: {
            type: 'json',
            rootProperty: 'transtypeVo.transtypeEntities',
            totalProperty: 'totalCount'
        }
    },
    listeners: {
        beforeload: function (store, operation, opts) {
            var queryPanel = Ext.getCmp('queryPanel');
            if (queryPanel != null) {
                var params = {
                    'transtypeVo.transtypeEntity.code': queryPanel.getForm().findField('code').getValue(),
                    'transtypeVo.transtypeEntity.name': queryPanel.getForm().findField('name').getValue(),
                    'transtypeVo.transtypeEntity.invalid': queryPanel.getForm().findField('invalid').getValue(),
                    'transtypeVo.transtypeEntity.active': queryPanel.getForm().findField('active').getValue(),
                    'transtypeVo.transtypeEntity.containsRoot': 'Y'
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
    }
});

//通用Ajax调用失败回调方法
var failFun = function (json) {
    if (Ext.isEmpty(json)) {
        miser.showErrorMes('连接超时');
    }
    else {
        var message = json.message;
        miser.showErrorMes(message);
    }
};

//定义查询面板
Ext.define('Miser.biz.base.transtype.QueryPanel', {
    extend: 'Ext.form.Panel',
    id: 'queryPanel',
    title: bizbase.transtype.i18n('miser.biz.base.transtype.querypanel.title'),
    frame: true,
	width : document.documentElement.clientWidth,
    collapsible: true,
	region : 'north',
	defaults : {
		labelWidth : 0.25,
		columnWidth : 0.75,
		margin : '8 10 5 10',
		labelAlign : 'left'
	},
    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.items = [{
			layout : 'column',
			defaults : {
				labelWidth : 70,
				labelAlign : "right"
			},
			defaults : {
				margins : '10 10 10 10'
			},
			items : [{
				name: 'code',
				fieldLabel: bizbase.transtype.i18n('miser.biz.base.transtype.code'),
				xtype: 'textfield',
				margin:'5px 0px 5px 0px',
				labelAlign:'right'
			}, {
				name: 'name',
				fieldLabel: bizbase.transtype.i18n('miser.biz.base.transtype.name'),
				xtype: 'textfield',
				margin:'5px 0px 5px 0px',
				labelAlign:'right'
			}, {
				name: 'invalid',
				fieldLabel: bizbase.transtype.i18n('miser.biz.base.transtype.invalid'),
				xtype: 'yesnocombselector',
				isShowAll : true,
				margin:'5px 0px 5px 0px',
				labelAlign:'right'
			}, {
				name: 'active',
				fieldLabel: bizbase.transtype.i18n('miser.biz.base.transtype.active'),
				xtype: 'yesnocombselector',
				isShowAll : false,
				margin:'5px 0px 5px 0px',
				labelAlign:'right'
        	}]
        }];
        me.buttons = [{
            text: '查询',
            handler: function () {
                if (me.getForm().isValid()) {
                    me.up().getTranstypeGrid().getPagingToolbar().moveFirst();
                }
            }
        }, {
            text: '清空',
            handler: function () {
                me.getForm().reset();
            }
        }];
        me.callParent([cfg]);
    }
});

//定义主表格
Ext.define('Miser.biz.base.transtype.TranstypeGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    stripeRows: true,
    region: 'center',
    height: document.documentElement.clientHeight - 120,
    emptyText: bizbase.transtype.i18n('miser.common.queryNoResult'),
    viewConfig: {
        enableTextSelection: true
    },

    pagingToolbar: null,
    getPagingToolbar: function () {
        if (this.pagingToolbar == null) {
            this.pagingToolbar = Ext.create('Ext.toolbar.Paging', {
                store: this.store,
                pageSize: 20
            });
        }
        return this.pagingToolbar;
    },

    transtypeEditWindow: null,
    getTranstypeEditWindow: function () {
        if (this.transtypeEditWindow == null) {
            this.transtypeEditWindow = Ext.create('Miser.biz.base.transtype.TranstypeEditWindow');
            this.transtypeEditWindow.parent = this;
        }
        return this.transtypeEditWindow;
    },

    updateTranstype: function () {
        var me = this;
        var selections = me.getSelection();
        if (selections.length != 1) {
            miser.showWoringMessage('请选择一条进行修改');
            return;
        }
        var id = selections[0].get('id');
        var invalid = selections[0].get('invalid');
        var code = selections[0].get('code');
        if (invalid == 'Y') {
        	miser.showWoringMessage('不可修改状态为无效的记录');
        	return;
        }
        if (code == '00000000000000000000') {
        	miser.showWoringMessage('不可修改顶级记录');
        	return;
        }
        var params = {'transtypeVo': {'transtypeEntity': {'id': id}}};
        var successFun = function (json) {
            var transtypeEditWindow = me.getTranstypeEditWindow();
            transtypeEditWindow.transtypeEntity = json.transtypeVo.transtypeEntities[0];
            transtypeEditWindow.show();
            transtypeEditWindow.getTranstypeForm().getForm().findField('id').setValue(transtypeEditWindow.transtypeEntity.id);
            transtypeEditWindow.getTranstypeForm().getForm().findField('code').setValue(transtypeEditWindow.transtypeEntity.code);
            transtypeEditWindow.getTranstypeForm().getForm().findField('name').setValue(transtypeEditWindow.transtypeEntity.name);
            transtypeEditWindow.getTranstypeForm().getForm().findField('otherName').setValue(transtypeEditWindow.transtypeEntity.otherName);
            transtypeEditWindow.getTranstypeForm().getForm().findField('symbol').setValue(transtypeEditWindow.transtypeEntity.symbol);
            transtypeEditWindow.getTranstypeForm().getForm().findField('orderNo').setValue(transtypeEditWindow.transtypeEntity.orderNo);
            transtypeEditWindow.getTranstypeForm().getForm().findField('remark').setValue(transtypeEditWindow.transtypeEntity.remark);
            transtypeEditWindow.getTranstypeForm().getForm().findField('code').setReadOnly(true);
            transtypeEditWindow.getTranstypeForm().getForm().findField('parentCode').getStore().load();
            transtypeEditWindow.getTranstypeForm().getForm().findField('parentCode').setCombValue(transtypeEditWindow.transtypeEntity.parentName, transtypeEditWindow.transtypeEntity.parentCode);
           // transtypeEditWindow.getTranstypeForm().getForm().findField('parentCode').setReadOnly(true);
        };

        miser.requestJsonAjax('transtypeAction!queryTranstypeById.action', params, successFun, failFun);
    },

    deleteTranstype: function () {
        var me = this;
        var selections = me.getSelectionModel().getSelection();
        if (selections.length != 1) {
            miser.showWoringMessage('请选择一条进行作废');
            return;
        }
        var invalid = selections[0].get('invalid');
        if (invalid == 'Y') {
        	miser.showWoringMessage('不可重复作废');
        	return;
        }
        miser.showQuestionMes('确定删除运输类型?', function (e) {
        	if (e =='yes') {
                var id = selections[0].get('id');
                var param = {'transtypeVo': {'transtypeEntity': {'id': id}}};

                var successFun = function (json) {
                    var msg = json.message;
                    miser.showInfoMsg(msg);
                    me.getStore().load();
                };

                miser.requestJsonAjax('transtypeAction!deleteTranstype.action', param, successFun, failFun);	
        	}
        });
    },

    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.columns = [
            {
                text: bizbase.transtype.i18n('miser.biz.base.transtype.rownumberer'),//序号
                dataIndex: 'valueSeq',
                xtype: 'rownumberer',
                align: 'center',
                width: 60
            },
            {
                dataIndex: 'id',
                width: 100,
                hidden : true,
                align: 'center',
                text: bizbase.transtype.i18n('miser.biz.base.transtype.id')//主键
            },
            {
                dataIndex: 'code',
                width: 200,
                align: 'center',
                text: bizbase.transtype.i18n('miser.biz.base.transtype.code')//编码
            },
            {
                text: bizbase.transtype.i18n('miser.biz.base.transtype.name'),//名称
                dataIndex: 'name',
                align: 'center',
                width: 100
            },
            {
                dataIndex: 'parentCode',
                align: 'center',
                hidden : true
            },
            {
                text: bizbase.transtype.i18n('miser.biz.base.transtype.parentCode'), //上级
                dataIndex: 'parentName',
                align: 'center',
                width: 100
            },
            {
                text: bizbase.transtype.i18n('miser.biz.base.transtype.otherName'),//别名
                dataIndex: 'otherName',
                align: 'center',
                width: 80
            },
            {
                text: bizbase.transtype.i18n('miser.biz.base.transtype.symbol'), //代号
                dataIndex: 'symbol',
                align: 'center',
                width: 80
            },
            {
                text: bizbase.transtype.i18n('miser.biz.base.transtype.orderNo'), //排序号
                dataIndex: 'orderNo',
                align: 'center',
                width: 80
            },
            {
                text: bizbase.transtype.i18n('miser.biz.base.transtype.invalid'), //是否作废
                dataIndex: 'invalid',
                align: 'center',
                width: 100,
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('IS_ACTIVE'), value);
                }
            },
            {
                text: bizbase.transtype.i18n('miser.biz.base.transtype.active'), //是否有效
                dataIndex: 'active',
                align: 'center',
                width: 100,
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('IS_ACTIVE'), value);
                }
            },
            {
                text: bizbase.transtype.i18n('miser.biz.base.transtype.remark'), //备注
                dataIndex: 'remark',
                align: 'center',
                width: 100
            },
            {
                text: bizbase.transtype.i18n('miser.biz.base.transtype.modifyDate'),
                dataIndex: 'modifyDate',//修改时间
                xtype: 'datecolumn',
                align: 'center',
                format: 'Y-m-d'
            }
        ];
        me.store = Ext.create('Miser.biz.base.transtype.TranstypeStore', {
            autoLoad : false
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', {
            listeners: {
                selectionchange: function (sm, selections) {
                    Ext.getCmp('miser_biz_base_transtype_updatebutton_id').setDisabled(selections.length !== 1);
                    Ext.getCmp('miser_biz_base_transtype_deletebutton_id').setDisabled(selections.length === 0);
                }
            }
        });
        me.tbar = [{
            text: bizbase.transtype.i18n('miser.common.add'),
            xtype: 'addbutton',
            width : 80,
            handler: function () {
                me.getTranstypeEditWindow().show();
            }
        }, {
            id : 'miser_biz_base_transtype_updatebutton_id',
            text: bizbase.transtype.i18n('miser.common.update'),
            xtype: 'updatebutton',
            width : 80,
            handler: function () {
                me.updateTranstype();
            }
        }, {
            id : 'miser_biz_base_transtype_deletebutton_id',
            text: bizbase.transtype.i18n('miser.common.invalidate'),
            xtype: 'deletebutton',
            width : 80,
            handler: function () {
                me.deleteTranstype();
            }
        }];
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    }
});

//定义维护数据窗口
Ext.define('Miser.biz.base.transtype.TranstypeEditWindow', {
    extend: 'Ext.window.Window',
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
        beforehide: function (me) { // 隐藏WINDOW的时候清除数据
            me.getTranstypeForm().getForm().reset(); // 表格重置
        },
        beforeshow: function (me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getTranstypeForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function (item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
            me.getTranstypeForm().getForm().findField('parentCode').setReadOnly(false);
            me.getTranstypeForm().getForm().findField('code').setReadOnly(false);
        }
    },
    //获取维护数据的表单
    transtypeForm: null,
    getTranstypeForm: function () {
        if (this.transtypeForm == null) {
            this.transtypeForm = Ext.create('Miser.biz.base.transtype.TranstypeForm');
        }
        return this.transtypeForm;
    },
    //定义保存数据方法
    postTranstypeData: function () {
        var me = this;
        if (me.getTranstypeForm().getForm().isValid()) {
            var model = new Miser.biz.base.transtype.TranstypeEntity();
            me.getTranstypeForm().getForm().updateRecord(model);
            var param = {'transtypeVo': {'transtypeEntity': model.data}};

            var successFun = function (json) {
                var msg = json.message;
                miser.showInfoMsg(msg);
                me.close();
                me.parent.getStore().load();
            };

            miser.requestJsonAjax('transtypeAction!postTranstype.action', param, successFun, failFun);
        }
    },

    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.fbar = [{
            text: '保存',
            handler: function () {
                me.postTranstypeData();
            }
        }, {
            text: '重置',
            handler: function () {
                me.getTranstypeForm().reset();
            }
        }];
        me.items = [me.getTranstypeForm()];
        me.callParent([cfg]);
    }
});

//定义维护数据表单
Ext.define('Miser.biz.base.transtype.TranstypeForm', {
    extend: 'Ext.form.Panel',
    header: false,
    frame: true,
    collapsible: true,
    width: 300,
    fieldDefaults: {
        labelWidth: 100,
        margin: '8 10 5 10'
    },
    defaultType: 'textfield',

    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);

        me.items = [{
            name: 'id',
            fieldLabel: 'ID',
            xtype: 'textfield',
            hidden: true
        }, {
            name: 'code',
            fieldLabel: bizbase.transtype.i18n('miser.biz.base.transtype.code'),
            xtype: 'textfield',
            maxLength : 20, 
            allowBlank: false
        }, {
            name: 'name',
            fieldLabel: bizbase.transtype.i18n('miser.biz.base.transtype.name'),
            xtype: 'textfield',
            maxLength : 25, 
            allowBlank: false
        }, {
            name: 'otherName',
            fieldLabel: bizbase.transtype.i18n('miser.biz.base.transtype.otherName'),
            xtype: 'textfield'
        }, {
            name: 'symbol',
            fieldLabel: bizbase.transtype.i18n('miser.biz.base.transtype.symbol'),
            xtype: 'textfield'
        }, {
            name: 'orderNo',
            fieldLabel: bizbase.transtype.i18n('miser.biz.base.transtype.orderNo'),
            xtype: 'numberfield',
        }, {
            name: 'parentCode',
            fieldLabel: bizbase.transtype.i18n('miser.biz.base.transtype.parentCode'),
            xtype : 'transtypecombselector',
            showAll : false,
            containsRoot : 'Y',
        }, {
            name: 'remark',
            fieldLabel: bizbase.transtype.i18n('miser.biz.base.transtype.remark'),
            xtype: 'textfield'
        }];

        me.callParent([cfg]);
    }

});

//创建主面板
Ext.onReady(function () {
    Ext.QuickTips.init();
    var queryPanel = Ext.create('Miser.biz.base.transtype.QueryPanel');
    var transtypeGrid = Ext.create('Miser.biz.base.transtype.TranstypeGrid');
    var mainPanel = Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        id: 'transtypePanel',
        getQueryPanel: function () {
            return queryPanel;
        },

        getTranstypeGrid: function () {
            return transtypeGrid;
        },
        items: [queryPanel, transtypeGrid]
    });
    
    //设置窗口大小改变时的响应
    window.onresize = function(){
    
        //控制整个 panel 的宽度
    	mainPanel.setWidth(document.documentElement.clientWidth);
    	
    	//控制整个 查询表单 的宽度
    	queryPanel.setWidth(document.documentElement.clientWidth - 10);
        
        //设置区域一行表单的宽度
    	var items = queryPanel.items;
		document.getElementById(items.items[1].items.items[0].getEl().el.parent().id).style.width = (document.documentElement.clientWidth - 10)+'px';
		
		
		//设置查询结果的宽度
    	transtypeGrid.setWidth(document.documentElement.clientWidth - 10);
    };

});