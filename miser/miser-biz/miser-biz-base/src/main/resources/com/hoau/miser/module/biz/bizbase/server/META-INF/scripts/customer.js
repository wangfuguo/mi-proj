bizbase.customer.active = 'IS_ACTIVE';

//定义运输类型数据类型
Ext.define('Miser.biz.base.customer.CustomerEntity', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'code', type: 'string'},
        {name: 'name', type: 'string'},
        {name: 'orgCode', type: 'string'},
        {name: 'orgName', type: 'string'},
        {name: 'logisticName', type: 'string'},
        {name: 'contractStartTime', type: 'date', dateFormat:'time'},
        {name: 'contractEndTime', type: 'date', dateFormat:'time'},
        {name: 'pcStartTime', type: 'date', dateFormat:'time'},
        {name: 'pcEndTime', type: 'date', dateFormat:'time'},
        {name: 'useCustomerPc', type: 'string'},
        {name: 'useCustomerDiscount', type: 'string'},
        {name: 'showMoneyOnSignBill', type: 'string'},
        {name: 'ydjUseDrd', type: 'string'},
        {name: 'remark', type: 'string'},
        {name: 'active', type: 'string'},
        {name: 'createDate', type: 'date', dateFormat:'time'},
        {name: 'createUser', type: 'string'},
        {name: 'modifyDate', type: 'date', dateFormat:'time'},
        {name: 'modifyUser', type: 'string'}
    ]
});

//定义主表格数据源
Ext.define('Miser.biz.base.customer.CustomerStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.biz.base.customer.CustomerEntity',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: 'customerAction!queryCustomers.action',
        reader: {
            type: 'json',
            rootProperty: 'customerVo.customerEntities',
            totalProperty: 'totalCount'
        }
    },
    listeners: {
        beforeload: function (store, operation, opts) {
            var queryPanel = Ext.getCmp('miser_bizbase_customer_queryPanel');
            if (queryPanel != null) {
                var params = {
                    'customerVo.queryParam.code': queryPanel.getForm().findField('code').getValue(),
                    'customerVo.queryParam.name': queryPanel.getForm().findField('name').getValue(),
                    'customerVo.queryParam.active': queryPanel.getForm().findField('active').getValue(),
                    'customerVo.queryParam.orgCode': queryPanel.getForm().findField('orgCode').getValue()
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

//导出数据
function download() {
    Ext.Msg.wait('处理中，请稍后...', '提示');
    var params = null;
    var queryForm = Ext.getCmp('miser_bizbase_customer_queryPanel');
    if (queryForm != null) {
        params = {
            'customerVo.queryParam.code': queryForm.getForm().findField('code').getValue(),
            'customerVo.queryParam.name': queryForm.getForm().findField('name').getValue(),
            'customerVo.queryParam.active': queryForm.getForm().findField('active').getValue(),
            'customerVo.queryParam.orgCode': queryForm.getForm().findField('orgCode').getValue()
        };
    }
    Ext.Ajax.setTimeout(900000);
    Ext.Ajax.request({
        url: '../bizbase/customerAction!excelExport.action',
        method: 'post',
        params: params,
        success: function (response, options) {
            //隐藏进度条
            Ext.Msg.hide();
            var responseArray = Ext.util.JSON.decode(response.responseText);
            //addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
            var msg = "本次导出共" + responseArray.count + "条数据！";
            miser.showInfoMsg(msg);
            Ext.Ajax.setTimeout(30000);
            miser.requestExportAjax(responseArray.filePath);
        },
        failure: function () {
            Ext.Msg.hide();
            Ext.Ajax.setTimeout(30000);
            Ext.MessageBox.show({title: '失败', msg: '导出失败', buttons: Ext.MessageBox.OK, icon: Ext.MessageBox.ERROR});
        },

    });

}

//定义查询面板
Ext.define('Miser.biz.base.customer.QueryPanel', {
    extend: 'Ext.form.Panel',
    id: 'miser_bizbase_customer_queryPanel',
    title: bizbase.customer.i18n('miser.common.queryCondition'),
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
                labelAlign : "right",
                margins : '10 10 10 10'
            },
            items : [{
                name: 'code',
                fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.code'),
                xtype: 'textfield',
                margin:'5px 0px 5px 0px',
                labelAlign:'right'
            }, {
                name: 'name',
                fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.name'),
                xtype: 'textfield',
                margin:'5px 0px 5px 0px',
                labelAlign:'right'
            }, {
                name: 'active',
                fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.active'),
                xtype: 'yesnocombselector',
                isShowAll : false,
                margin:'5px 0px 5px 0px',
                labelAlign:'right'
            }, {
                name: 'orgCode',
                fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.orgCode'),
                xtype: 'dynamicorgcombselector',
                isSalesDepartment : 'Y',
                margin:'5px 0px 5px 0px',
                labelAlign:'right'
            }]
        }];
        me.buttons = [{
            text: '查询',
            handler: function () {
                if (me.getForm().isValid()) {
                    me.up().getCustomerGrid().getPagingToolbar().moveFirst();
                }
            }
        }, {
            text: '清空',
            handler: function () {
                me.getForm().reset();
            }
        }, {
            id: 'customer_downbutton_id',
            text: bizbase.customer.i18n('miser.biz.base.customer.export'),
            alias: 'widget.downloadbutton',
            glyph: 0xf019,
            disabled: false,
            cls: 'download-btn-item',
            handler: function () {
                download();
            }
        }];
        me.callParent([cfg]);
    }
});

//定义主表格
Ext.define('Miser.biz.base.customer.CustomerGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    stripeRows: true,
    region: 'center',
    height: document.documentElement.clientHeight - 120,
    emptyText: bizbase.customer.i18n('miser.common.queryNoResult'),
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

    customerEditWindow: null,
    getCustomerEditWindow: function () {
        if (this.customerEditWindow == null) {
            this.customerEditWindow = Ext.create('Miser.biz.base.customer.CustomerEditWindow');
            this.customerEditWindow.parent = this;
        }
        return this.customerEditWindow;
    },

    updateCustomer: function () {
        var me = this;
        var selections = me.getSelection();
        if (selections.length != 1) {
            miser.showWoringMessage('请选择一条进行修改');
            return;
        }
        var id = selections[0].get('id');
        var active = selections[0].get('active');
        if (active == 'N') {
            miser.showWoringMessage('不可修改状态为无效的记录');
            return;
        }
        var params = {'customerVo': {'queryParam': {'id': id}}};
        var successFun = function (json) {
            var customerEditWindow = me.getCustomerEditWindow();
            customerEditWindow.customerEntity = json.customerVo.customerEntity;
            customerEditWindow.show();
            customerEditWindow.getCustomerForm().getForm().findField('id').setValue(customerEditWindow.customerEntity.id);
            customerEditWindow.getCustomerForm().getForm().findField('code').setValue(customerEditWindow.customerEntity.code);
            customerEditWindow.getCustomerForm().getForm().findField('name').setValue(customerEditWindow.customerEntity.name);
            customerEditWindow.getCustomerForm().getForm().findField('orgCode').setCombValue(customerEditWindow.customerEntity.orgName + '   ' + customerEditWindow.customerEntity.logisticName, customerEditWindow.customerEntity.orgCode);
            customerEditWindow.getCustomerForm().getForm().findField('orgCode').getStore().load();
            if (customerEditWindow.customerEntity.contractStartTime) {
                customerEditWindow.getCustomerForm().getForm().findField('contractStartTime').setValue(new Date(customerEditWindow.customerEntity.contractStartTime));
            }
            if (customerEditWindow.customerEntity.contractEndTime) {
                customerEditWindow.getCustomerForm().getForm().findField('contractEndTime').setValue(new Date(customerEditWindow.customerEntity.contractEndTime));
            }
            if (customerEditWindow.customerEntity.pcStartTime) {
                customerEditWindow.getCustomerForm().getForm().findField('pcStartTime').setValue(new Date(customerEditWindow.customerEntity.pcStartTime));
            }
            if (customerEditWindow.customerEntity.pcEndTime) {
                customerEditWindow.getCustomerForm().getForm().findField('pcEndTime').setValue(new Date(customerEditWindow.customerEntity.pcEndTime));
            }
            customerEditWindow.getCustomerForm().getForm().findField('useCustomerPc').setValue(customerEditWindow.customerEntity.useCustomerPc);
            customerEditWindow.getCustomerForm().getForm().findField('useCustomerDiscount').setValue(customerEditWindow.customerEntity.useCustomerDiscount);
            customerEditWindow.getCustomerForm().getForm().findField('showMoneyOnSignBill').setValue(customerEditWindow.customerEntity.showMoneyOnSignBill);
            customerEditWindow.getCustomerForm().getForm().findField('ydjUseDrd').setValue(customerEditWindow.customerEntity.ydjUseDrd);
            customerEditWindow.getCustomerForm().getForm().findField('remark').setValue(customerEditWindow.customerEntity.remark);
            customerEditWindow.getCustomerForm().getForm().findField('code').setReadOnly(true);
            customerEditWindow.getCustomerForm().getForm().findField('name').setReadOnly(true);
            customerEditWindow.getCustomerForm().getForm().findField('contractStartTime').setReadOnly(true);
            customerEditWindow.getCustomerForm().getForm().findField('contractEndTime').setReadOnly(true);
            customerEditWindow.getCustomerForm().getForm().findField('orgCode').setReadOnly(true);
        };

        miser.requestJsonAjax('customerAction!queryCustomerById.action', params, successFun, failFun);
    },

    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.columns = [
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.rownumberer'),//序号
                dataIndex: 'valueSeq',
                xtype: 'rownumberer',
                align: 'center',
                width: 60
            },
            {
                dataIndex: 'id',
                width: 100,
                hidden : true,
                align: 'center'
            },
            {
                dataIndex: 'code',
                width: 80,
                align: 'center',
                text: bizbase.customer.i18n('miser.biz.base.customer.code')//编码
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.name'),//名称
                dataIndex: 'name',
                align: 'center',
                width: 300
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.logisticName'),//物流代码
                dataIndex: 'logisticName',
                align: 'center',
                width: 80
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.contractStartTime'), //合同开始时间
                dataIndex: 'contractStartTime',
                xtype: 'datecolumn',
                format: 'Y-m-d H:i:s',
                align: 'center',
                width: 160
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.contractEndTime'), //合同结束时间
                dataIndex: 'contractEndTime',
                xtype: 'datecolumn',
                format: 'Y-m-d H:i:s',
                align: 'center',
                width: 160
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.pcStartTime'), //价格开始时间
                dataIndex: 'pcStartTime',
                xtype: 'datecolumn',
                format: 'Y-m-d H:i:s',
                align: 'center',
                width: 160
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.pcEndTime'), //价格结束时间
                dataIndex: 'pcEndTime',
                xtype: 'datecolumn',
                format: 'Y-m-d H:i:s',
                align: 'center',
                width: 160
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.useCustomerPc'), //是否启用客户价格
                dataIndex: 'useCustomerPc',
                width: 115,
                align: 'center',
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('IS_ACTIVE'), value);
                }
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.useCustomerDiscount'), //是否启用客户价格
                dataIndex: 'useCustomerDiscount',
                width: 115,
                align: 'center',
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('IS_ACTIVE'), value);
                }
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.showMoneyOnSignBill'), //签收单是否显示运费金额
                dataIndex: 'showMoneyOnSignBill',
                width: 115,
                align: 'center',
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('IS_ACTIVE'), value);
                }
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.ydjUseDrd'), //易到家产品使用定日达价格
                dataIndex: 'ydjUseDrd',
                width: 115,
                align: 'center',
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('IS_ACTIVE'), value);
                }
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.orgName'), //组织
                dataIndex: 'orgName',
                align: 'center',
                width: 300
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.active'), //是否有效
                dataIndex: 'active',
                width: 100,
                align: 'center',
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('IS_ACTIVE'), value);
                }
            },
            {
                text: bizbase.customer.i18n('miser.biz.base.customer.remark'), //备注
                dataIndex: 'remark',
                align: 'center',
                width: 100
            }
        ];
        me.store = Ext.create('Miser.biz.base.customer.CustomerStore', {
            autoLoad : false
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', {
            listeners: {
                selectionchange: function (sm, selections) {
                    Ext.getCmp('miser_biz_base_customer_updatebutton_id').setDisabled(selections.length !== 1);
                }
            }
        });
        me.tbar = [{
            id : 'miser_biz_base_customer_updatebutton_id',
            text: bizbase.customer.i18n('miser.common.update'),
            width : 80,
            disabled : true,
            xtype: 'updatebutton',
            handler: function () {
                me.updateCustomer();
            }
        }];
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    }
});

//定义维护数据窗口
Ext.define('Miser.biz.base.customer.CustomerEditWindow', {
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
            me.getCustomerForm().getForm().reset(); // 表格重置
        },
        beforeshow: function (me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getCustomerForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function (item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
        }
    },
    //获取维护数据的表单
    customerForm: null,
    getCustomerForm: function () {
        if (this.customerForm == null) {
            this.customerForm = Ext.create('Miser.biz.base.customer.CustomerForm');
        }
        return this.customerForm;
    },
    //定义保存数据方法
    postCustomerData: function () {
        var me = this;
        if (me.getCustomerForm().getForm().isValid()) {
            var model = new Miser.biz.base.customer.CustomerEntity();
            me.getCustomerForm().getForm().updateRecord(model);
            var param = {'customerVo': {'queryParam': model.data}};

            var successFun = function (json) {
                var msg = json.message;
                miser.showInfoMsg(msg);
                me.close();
                me.parent.getStore().load();
            };

            miser.requestJsonAjax('customerAction!updateCustomer.action', param, successFun, failFun);
        }
    },

    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.fbar = [{
            text: '保存',
            handler: function () {
                me.postCustomerData();
            }
        }, {
            text: '重置',
            handler: function () {
                me.getCustomerForm().reset();
            }
        }];
        me.items = [me.getCustomerForm()];
        me.callParent([cfg]);
    }
});

//定义维护数据表单
Ext.define('Miser.biz.base.customer.CustomerForm', {
    extend: 'Ext.form.Panel',
    header: false,
    frame: true,
    collapsible: true,
    width: 650,
    defaults: {
        labelWidth: 130,
        margin: '8 10 5 10'
    },
    layout: {
        type: 'table',
        columns: 2
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
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.code'),
            width : 300,
            xtype: 'textfield',
            colspan: 2
        }, {
            name: 'orgCode',
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.orgCode'),
            width : 625,
            colspan: 2,
            xtype: 'dynamicorgcombselector',
            isSalesDepartment:'Y'
        }, {
            name: 'name',
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.name'),
            xtype: 'textfield',
            width : 625,
            colspan: 2
        }, {
            name: 'contractStartTime',
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.contractStartTime'),
            xtype: 'datetimefield',
            format:'Y-m-d H:i:s'
        }, {
            name: 'contractEndTime',
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.contractEndTime'),
            xtype: 'datetimefield',
            format:'Y-m-d H:i:s'
        }, {
            name: 'pcStartTime',
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.pcStartTime'),
            xtype: 'datetimefield',
            format:'Y-m-d H:i:s'
        }, {
            name: 'pcEndTime',
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.pcEndTime'),
            xtype: 'datetimefield',
            format:'Y-m-d H:i:s'
        }, {
            name: 'useCustomerPc',
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.useCustomerPc'),
            xtype : 'dataDictionarySelector',
            termsCode : bizbase.customer.active
        }, {
            name: 'useCustomerDiscount',
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.useCustomerDiscount'),
            xtype : 'dataDictionarySelector',
            termsCode : bizbase.customer.active
        }, {
            name: 'showMoneyOnSignBill',
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.showMoneyOnSignBill'),
            xtype : 'dataDictionarySelector',
            termsCode : bizbase.customer.active
        }, {
            name: 'ydjUseDrd',
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.ydjUseDrd'),
            xtype : 'dataDictionarySelector',
            termsCode : bizbase.customer.active
        }, {
            name: 'remark',
            fieldLabel: bizbase.customer.i18n('miser.biz.base.customer.remark'),
            xtype: 'textfield'
        }];

        me.callParent([cfg]);
    }

});

//创建主面板
Ext.onReady(function () {
    Ext.QuickTips.init();
    var queryPanel = Ext.create('Miser.biz.base.customer.QueryPanel');
    var customerGrid = Ext.create('Miser.biz.base.customer.CustomerGrid');
    var mainPanel = Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        id: 'customerPanel',
        getQueryPanel: function () {
            return queryPanel;
        },

        getCustomerGrid: function () {
            return customerGrid;
        },
        items: [queryPanel, customerGrid]
    });

    //设置窗口大小改变时的响应
    window.onresize = function(){

        //控制整个 panel 的宽度
        mainPanel.setWidth(document.documentElement.clientWidth);

        //控制整个 查询表单 的宽度
        queryPanel.setWidth(document.documentElement.clientWidth - 10);

        //设置区域一行表单的宽度
        var items = queryPanel.items;
//		document.getElementById(items.items[1].items.items[0].getEl().el.parent().id).style.width = (document.documentElement.clientWidth - 10)+'px';


        //设置查询结果的宽度
        customerGrid.setWidth(document.documentElement.clientWidth - 10);
    };

});