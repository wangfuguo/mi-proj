extrafee.priceUpstairs.upstairsType = 'UPSTAIRS_TYPE';
extrafee.priceUpstairs.upstairsStatus = 'PRICE_SATUS';
extrafee.priceUpstairs.upstairsLockType = 'LOCK_TYPE';
extrafee.priceUpstairs.upstairsActive = 'IS_ACTIVE';

extrafee.priceUpstairs.sectionedItem = 'SECTION_USED_TYPE';
extrafee.priceUpstairs.sectionAccodingItem = 'SECTION_ACCODING_ITEM';
extrafee.priceUpstairs.priceType = 'PRICE_TYPE';
//定义上楼费数据类型
Ext.define('Miser.biz.extrafee.upstairs.PriceUpstairsEntity', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'type', type: 'string'},
        {name: 'transportType', type: 'string'},
        {name: 'transportTypeName', type: 'string'},
        {name: 'sectionItemCode', type: 'string'},
        {name: 'sectionItemName', type: 'string'},
        {name: 'lockType', type: 'string'},
        {name: 'effectiveTime', type: 'date', dateFormat:'time'},
        {name: 'invalidTime', type: 'date', dateFormat:'time'},
        {name: 'remark', type: 'string'},
        {name: 'active', type: 'string'},
        {name: 'createDate', type: 'date', dateFormat:'time'},
        {name: 'createUser', type: 'string'},
        {name: 'modifyDate', type: 'date', dateFormat:'time'},
        {name: 'modifyUser', type: 'string'},
        {name: 'status', type: 'string'}
    ]
});

//定义主表格数据源
Ext.define('Miser.biz.extrafee.upstairs.PriceUpstairsStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.biz.extrafee.upstairs.PriceUpstairsEntity',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: 'priceUpstairsAction!queryUpstairsPrices.action',
        reader: {
            type: 'json',
            rootProperty: 'priceUpstairsVo.priceUpstairsEntities',
            totalProperty: 'totalCount'
        }
    },
    listeners: {
        beforeload: function (store, operation, opts) {
            var queryPanel = Ext.getCmp('queryPanel');
            if (queryPanel != null) {
                var params = {
                    'priceUpstairsVo.priceUpstairsEntity.type': queryPanel.getForm().findField('type').getValue(),
                    'priceUpstairsVo.priceUpstairsEntity.transportType': queryPanel.getForm().findField('transportType').getValue(),
                    'priceUpstairsVo.priceUpstairsEntity.status': queryPanel.getForm().findField('status').getValue()
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
    }
});

/**
 * 优惠分段列表model
 */
Ext.define('Miser.model.priceSectionSubEntity', {
	extend : 'Ext.data.Model',
	fields : [{name : 'id',type : 'string'}, 
	          {name : 'sectionId', type : 'string'}, 	// 分段项目编号
	          {name : 'startValue', type : 'number'}, // 段起
	          {name : 'endValue', type : 'number'}, // 段止
	          {name : 'sectionAccodingItem', type : 'string'}, // 计算方式 
	          {name : 'priceType',	type : 'string'}, // 费用类型
	          {name : 'price', type : 'number'}, // 费用
	          {name : 'calcAlone', type : 'string'}, // 是否单独进行结算
	          {name : 'minPrice', type : 'number'}, // 最低收费
	          {name : 'maxPrice', type : 'number'},//最高收费 
	          {name : 'remark', type : 'string'}, // 备注
	          {name : 'active', type : 'string'}, // 是否有效
	          {name : 'modifyUser', type : 'string'}, // 最后修改人
	          {name : 'modifyDate', type : 'date', dateFormat : 'time'} // 最后修改时间
	          ]
});

/**
 * 新增优惠分段store 无action请求
 */
Ext.define('Miser.store.priceSectionSubStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.model.priceSectionSubEntity'
});
var girdStore = null;
function getGirdStore() {
	if (Ext.isEmpty(girdStore)) {
		girdStore = Ext.create('Miser.store.priceSectionSubStore');
	}
	return girdStore;
}

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
Ext.define('Miser.biz.extrafee.upstairs.QueryPanel', {
    extend: 'Ext.form.Panel',
    id: 'queryPanel',
    title: extrafee.priceUpstairs.i18n('miser.extrafee.upstairs.querypanel.title'),
    frame: true,
    height: 110,
    collapsible: true,
    layout: 'column',
    defaults: {
        colspan: 1,
        margin: '8 10 5 10'
    },
    defaultType: 'textfield',
    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.items = [{
            name: 'type',
            fieldLabel: '上楼类型',
            xtype: 'dataDictionarySelector',
            anyRecords:all,
            termsCode : extrafee.priceUpstairs.upstairsType
        }, {
            name: 'transportType',
            fieldLabel: '运输类型',
            xtype: 'transtypecombselector'
        }, {
            name: 'status',
            fieldLabel: '状态',
            xtype: 'dataDictionarySelector',anyRecords:all,
            termsCode : extrafee.priceUpstairs.upstairsStatus
        }];
        me.buttons = [{
            text: '查询',
            handler: function () {
                if (me.getForm().isValid()) {
                    me.up().getUpstairsPricesGrid().getPagingToolbar().moveFirst();
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
Ext.define('Miser.biz.extrafee.upstairs.UpstairsPricesGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    width: '100%',
    stripeRows: true,
    region: 'center',
    height : document.documentElement.clientHeight - 120,
    emptyText: extrafee.priceUpstairs.i18n('miser.common.queryNoResult'),
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

    upstairsPriceEditWindow: null,
    getUpstairsPriceEditWindow: function () {
        if (this.upstairsPriceEditWindow == null) {
            this.upstairsPriceEditWindow = Ext.create('Miser.biz.extrafee.upstairs.UpstairsPriceEditWindow');
            this.upstairsPriceEditWindow.parent = this;
        }
        return this.upstairsPriceEditWindow;
    },

    updateUpstairsPrice: function () {
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
        var params = {'priceUpstairsVo': {'priceUpstairsEntity': {'id': id}}};
        var successFun = function (json) {
            var upstairsPriceEditWindow = me.getUpstairsPriceEditWindow();
            upstairsPriceEditWindow.priceUpstairsEntity = json.priceUpstairsVo.priceUpstairsEntity;
            upstairsPriceEditWindow.show();
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('id').setValue(upstairsPriceEditWindow.priceUpstairsEntity.id);
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('type').getStore().load();
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('type').setValue(upstairsPriceEditWindow.priceUpstairsEntity.type);
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('transportType').getStore().load();
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('transportType').setCombValue(upstairsPriceEditWindow.priceUpstairsEntity.transportTypeName, upstairsPriceEditWindow.priceUpstairsEntity.transportType);
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('sectionItemCode').getStore().load();
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('sectionItemCode').setCombValue(upstairsPriceEditWindow.priceUpstairsEntity.sectionItemName, upstairsPriceEditWindow.priceUpstairsEntity.sectionItemCode);
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('lockType').getStore().load();
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('lockType').setValue(upstairsPriceEditWindow.priceUpstairsEntity.lockType);
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('remark').setValue(upstairsPriceEditWindow.priceUpstairsEntity.remark);
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('type').setReadOnly(true);
            upstairsPriceEditWindow.getUpstairsPriceForm().getForm().findField('transportType').setReadOnly(true);
        };

        miser.requestJsonAjax('priceUpstairsAction!queryUpstairsPriceById.action', params, successFun, failFun);
    },

    deleteUpstairsPrice: function () {
        var me = this;
        var selections = me.getSelectionModel().getSelection();
        if (selections.length !== 1) {
            miser.showWoringMessage('请选择一条进行删除');
            return;
        }
        var active = selections[0].get('active');
        if (active == 'N') {
        	miser.showWoringMessage('不可重复删除');
        	return;
        }
        miser.showQuestionMes('确定删除选中上楼费?', function (e) {
			if (e == 'yes') {
	            var id = selections[0].get('id');
	            var param = {'priceUpstairsVo': {'priceUpstairsEntity': {'id': id}}};
	            
	            var checkSuccessFun = function (json) {
	                var msg = json.message;

	                var saveDataFun = function () {

	                    var saveSuccessFun = function (json) {
	                        var msg = json.message;
	                        miser.showInfoMsg(msg);
	                        me.getStore().load();
	                    };
	                    
	                    miser.requestJsonAjax('priceUpstairsAction!deleteUpstairsPrice.action', param, saveSuccessFun, failFun);
	                };
	                
	                //如果提示冲突
	                if (msg != null && msg == extrafee.priceUpstairs.i18n('extrafee.upstairs.exists.wait')) {
	                	miser.showQuestionMes(msg, function(e){
							if (e == 'yes') {
								saveDataFun();
							}
	                	});
	                }
	                else {
	                	saveDataFun();
	                }
	            };

	            miser.requestJsonAjax('priceUpstairsAction!checkDeleteData.action', param, checkSuccessFun, failFun);
			}
        });
    },

	upstairsQueryWindow : null,
	getUpstairsQueryWindow : function() {
		if (Ext.isEmpty(this.upstairsQueryWindow)) {
			this.upstairsQueryWindow = Ext.create('Miser.biz.extrafee.upstairs.UpstairsQueryWindow');
			this.upstairsQueryWindow.parent = this; // 父元素
		}
		return this.upstairsQueryWindow;
	},
    
    showDetails:function(){
        var me = this;
        var selections = me.getSelectionModel().getSelection();
        if (selections.length !== 1) {
            miser.showWoringMessage('请选择一条记录后查看');
            return;
        }
        var id = selections[0].get('id');
        var param = {'priceUpstairsVo': {'priceUpstairsEntity': {'id': id}}};
        var queryUpstairsDetailSuccessFun = function (json) {
            var upstairsQueryWindow = me.getUpstairsQueryWindow();
            upstairsQueryWindow.priceUpstairsEntity = json.priceUpstairsVo.priceUpstairsEntity;
            upstairsQueryWindow.show();
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('id').setValue(upstairsQueryWindow.priceUpstairsEntity.id);
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('type').getStore().load();
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('type').setValue(upstairsQueryWindow.priceUpstairsEntity.type);
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('transportType').getStore().load();
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('transportType').setCombValue(upstairsQueryWindow.priceUpstairsEntity.transportTypeName, upstairsQueryWindow.priceUpstairsEntity.transportType);
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('sectionItemCode').getStore().load();
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('sectionItemCode').setCombValue(upstairsQueryWindow.priceUpstairsEntity.sectionItemName, upstairsQueryWindow.priceUpstairsEntity.sectionItemCode);
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('lockType').getStore().load();
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('lockType').setValue(upstairsQueryWindow.priceUpstairsEntity.lockType);
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('effectiveTime').setValue(new Date(upstairsQueryWindow.priceUpstairsEntity.effectiveTime));
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('invalidTime').setValue(new Date(upstairsQueryWindow.priceUpstairsEntity.invalidTime));
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('remark').setValue(upstairsQueryWindow.priceUpstairsEntity.remark);
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('type').disable();
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('transportType').disable();
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('sectionItemCode').disable();
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('lockType').disable();
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('effectiveTime').disable();
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('invalidTime').disable();
            upstairsQueryWindow.getPriceUpstairsQueryForm().getForm().findField('remark').disable();
    		// 修改加载明细列表
    		var SectionSubList = json.priceUpstairsVo.priceUpstairsEntity.sectionSubEntities;
    		for (var i = 0; i < SectionSubList.length; i++) {
    			var priceSectionSubEntity = new Miser.model.priceSectionSubEntity();
    			getGirdStore().add(SectionSubList[i]);
    		}
        };
        
        miser.requestJsonAjax('priceUpstairsAction!queryUpstairsDetails.action', param, queryUpstairsDetailSuccessFun, failFun);
    },
    
    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.columns = [
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.rownumberer'),//序号
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
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.id')//主键
            },
            {
                dataIndex: 'type',
                width: 100,
                align: 'center',
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.type'),//编码
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(extrafee.priceUpstairs.upstairsType), value);
                }
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.transportType'),//运输类型编码
                dataIndex: 'transportType',
                width: 200,
                align: 'center',
                hidden : true
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.transportType'),//运输类型名称
                dataIndex: 'transportTypeName',
                align: 'center',
                width: 100
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.sectionItemCode'),//分段编码
                dataIndex: 'sectionItemCode',
                align: 'center',
                width: 200,
                hidden : true
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.sectionItemName'),//分段名称
                dataIndex: 'sectionItemName',
                align: 'center',
                width: 120
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.lockType'),//锁定方式
                dataIndex: 'lockType',
                align: 'center',
                width: 100,
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(extrafee.priceUpstairs.upstairsLockType), value);
                }
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.effectiveTime'), //生效时间
                dataIndex: 'effectiveTime',
                xtype: 'datecolumn',
                format: 'Y-m-d H:i:s',
                align: 'center',
                width: 160
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.invalidTime'), //失效时间
                dataIndex: 'invalidTime',
                xtype: 'datecolumn',
                format: 'Y-m-d H:i:s',
                align: 'center',
                width: 160
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.status'), //状态
                dataIndex: 'status',
                align: 'center',
                width: 80,
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(extrafee.priceUpstairs.upstairsStatus), value);
                }
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.active'), //是否有效
                dataIndex: 'active',
                width: 100,
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(extrafee.priceUpstairs.upstairsActive), value);
                }
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.modifyUserCode'), //修改人
                dataIndex: 'modifyUser',
                align: 'center',
                width: 120
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.modifyDate'), //修改时间
                dataIndex: 'modifyDate',
                xtype: 'datecolumn',
                format: 'Y-m-d H:i:s',
                align: 'center',
                width: 160
            },
            {
                text: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.remark'), //备注
                dataIndex: 'remark',
                align: 'center',
                width: 180
            }
        ];
        me.store = Ext.create('Miser.biz.extrafee.upstairs.PriceUpstairsStore', {
            autoLoad : false
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', {
            listeners: {
                selectionchange: function (sm, selections) {
                	var buttonDisable = selections.length !== 1;
                	if (!buttonDisable) {
                		buttonDisable = selections[0].get("status") == 'DELETED' || selections[0].get("status") == 'PASSED'
                	}
                    Ext.getCmp('miser_biz_extrafee_upstairs_updatebutton_id').setDisabled(buttonDisable);
                    Ext.getCmp('miser_biz_extrafee_upstairs_deletebutton_id').setDisabled(buttonDisable);
                }
            }
        });
        me.tbar = [{
            text: extrafee.priceUpstairs.i18n('miser.common.add'),
            xtype: 'addbutton',
            width: 80,
            handler: function () {
                me.getUpstairsPriceEditWindow().show();
            }
        }, {
            id : 'miser_biz_extrafee_upstairs_updatebutton_id',
            text: extrafee.priceUpstairs.i18n('miser.common.update'),
            xtype: 'updatebutton',
            width: 80,
            handler: function () {
                me.updateUpstairsPrice();
            }
        }, {
            id : 'miser_biz_extrafee_upstairs_deletebutton_id',
            text: extrafee.priceUpstairs.i18n('miser.common.delete'),
            xtype: 'deletebutton',
            width: 80,
            handler: function () {
                me.deleteUpstairsPrice();
            }
        }, {
            text: extrafee.priceUpstairs.i18n('miser.common.showDetail'),
            xtype: 'searchbutton',
            width : 100,
            handler: function () {
                me.showDetails();
            }
        }];
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    }
});

//定义维护数据窗口
Ext.define('Miser.biz.extrafee.upstairs.UpstairsPriceEditWindow', {
    extend: 'Ext.window.Window',
    closable: true,
    parent: null,
    // 父元素
    modal: true,
    // 可以调整窗口的大小
    resizable: false,
    // 点击关闭是隐藏窗口
    closeAction: 'hide',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    listeners: {
        beforehide: function (me) { // 隐藏WINDOW的时候清除数据
            me.getUpstairsPriceForm().getForm().reset(); // 表格重置
        },
        beforeshow: function (me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getUpstairsPriceForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function (item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
            me.getUpstairsPriceForm().getForm().findField("type").setReadOnly(false);
            me.getUpstairsPriceForm().getForm().findField("transportType").setReadOnly(false);
            me.getUpstairsPriceForm().getForm().findField("effectiveTime").setValue(new Date(new Date().getTime() + 5*60*1000));
        }
    },
    //获取维护数据的表单
    upstairsPriceForm: null,
    getUpstairsPriceForm: function () {
        if (this.upstairsPriceForm == null) {
            this.upstairsPriceForm = Ext.create('Miser.biz.extrafee.upstairs.UpstairsPriceForm');
        }
        return this.upstairsPriceForm;
    },
    //定义保存数据方法
    saveUpstairsPriceData: function () {
        var me = this;
        if (me.getUpstairsPriceForm().getForm().isValid()) {
            var model = new Miser.biz.extrafee.upstairs.PriceUpstairsEntity();
            me.getUpstairsPriceForm().getForm().updateRecord(model);
            var param = {'priceUpstairsVo': {'priceUpstairsEntity': model.data}};

            var checkSuccessFun = function (json) {
                var msg = json.message;
                
                var saveDataFun = function () {

                    var saveSuccessFun = function (json) {
                        var msg = json.message;
                        miser.showInfoMsg(msg);
                        me.close();
                        me.parent.getStore().load();
                    };
                    
                    miser.requestJsonAjax('priceUpstairsAction!saveUpstairsPrice.action', param, saveSuccessFun, failFun);
                }
                
                //如果提示冲突
                if (msg != null && msg == extrafee.priceUpstairs.i18n('extrafee.upstairs.exists.wait')) {
                	miser.showQuestionMes(msg, function(e){
						if (e == 'yes') {
							saveDataFun();
						}
                	});
                }
                else {
                	saveDataFun();
                }
            };

            miser.requestJsonAjax('priceUpstairsAction!checkAddData.action', param, checkSuccessFun, failFun);
        }
    },

    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.fbar = [{
            text: '保存',
            handler: function () {
                me.saveUpstairsPriceData();
            }
        }, {
            text: '重置',
            handler: function () {
                me.getUpstairsPriceForm().reset();
            }
        }];
        me.items = [me.getUpstairsPriceForm()];
        me.callParent([cfg]);
    }
});

//定义维护数据表单
Ext.define('Miser.biz.extrafee.upstairs.UpstairsPriceForm', {
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

        var upstairsType = getDataDictionary().getDataDictionaryStore(extrafee.priceUpstairs.upstairsType);
        var upstairsLockType = getDataDictionary().getDataDictionaryStore(extrafee.priceUpstairs.upstairsLockType);

        me.items = [{
            name: 'id',
            fieldLabel: 'ID',
            xtype: 'textfield',
            hidden: true
        }, {
            name: 'type',
            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.type'),
            xtype: 'combo',
            store: upstairsType,
            displayField: 'valueName',
            valueField: 'valueCode',
            allowBlank: false
        }, {
            name: 'transportType',
            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.transportType'),
            xtype: 'transtypecombselector',
            showAll : false,
            allowBlank: false
        }, {
            name: 'sectionItemCode',
            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.sectionItemName'),
            xtype: 'dynamicPriceSectioncombselector',
            allowBlank: false,
            sectionedItem : 'UPSTAIRS'
        }, {
            name: 'lockType',
            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.lockType'),
            xtype: 'combo',
            store: upstairsLockType,
            displayField: 'valueName',
            valueField: 'valueCode',
            allowBlank: false
        }, {
            name: 'effectiveTime',
            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.effectiveTime'),
            xtype : 'datetimefield',
			format:'Y-m-d H:i:s',
			minValue:new Date(),
			allowBlank:false
        }, {
            name: 'remark',
            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.remark'),
            xtype: 'textfield'
        }];

        me.callParent([cfg]);
    }

});


/**
 * 查询优惠分段窗口
 */
Ext.define('Miser.biz.extrafee.upstairs.UpstairsQueryWindow', {
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
			me.getPriceUpstairsQueryForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getPriceUpstairsQueryForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			getGirdStore().removeAll();
		}
	},
	priceUpstarirsQueryForm : null,
	getPriceUpstairsQueryForm : function() {
		if (Ext.isEmpty(this.priceUpstarirsQueryForm)) {
			this.priceUpstarirsQueryForm = Ext.create('Miser.biz.extrafee.upstairs.UpstairsQueryForm');
		}
		return this.priceUpstarirsQueryForm;
	},
	priceUpstairsQueryGrid : null,
	getPriceUpstairsQueryGrid : function() {
		if (Ext.isEmpty(this.priceUpstairsQueryGrid)) {
			this.priceUpstairsQueryGrid = Ext.create('Miser.biz.extrafee.upstairs.QueryGrid');
			this.priceUpstairsQueryGrid.parent = this; // 父元素
		}
		return this.priceUpstairsQueryGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getPriceUpstairsQueryForm(),
				me.getPriceUpstairsQueryGrid() ];
		me.callParent([ cfg ]);
	}
});

/**
 * 查询表单
 */
Ext.define('Miser.biz.extrafee.upstairs.UpstairsQueryForm',{
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
		
        var upstairsType = getDataDictionary().getDataDictionaryStore(extrafee.priceUpstairs.upstairsType);
        var upstairsLockType = getDataDictionary().getDataDictionaryStore(extrafee.priceUpstairs.upstairsLockType);
        
		me.items = [{
			layout : 'hbox',
			defaults : {
				labelWidth : 70,
				labelAlign : "right"
			},
			defaults : {
				margins : '10 10 5 5'
			},
			items : [{
	            name: 'id',
	            fieldLabel: 'ID',
	            xtype: 'textfield',
	            hidden: true
	        }, {
	            name: 'type',
	            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.type'),
	            xtype: 'combo',
	            store: upstairsType,
	            displayField: 'valueName',
	            valueField: 'valueCode',
	            allowBlank: false
	        }, {
	            name: 'transportType',
	            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.transportType'),
	            xtype: 'transtypecombselector',
	            showAll : false,
	            allowBlank: false
	        }, {
	            name: 'sectionItemCode',
	            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.sectionItemName'),
	            xtype: 'dynamicPriceSectioncombselector',
	            allowBlank: false,
	            sectionedItem : 'UPSTAIRS'
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
			items : [{
	            name: 'lockType',
	            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.lockType'),
	            xtype: 'combo',
	            store: upstairsLockType,
	            displayField: 'valueName',
	            valueField: 'valueCode',
	            allowBlank: false
	        }, {
	            name: 'effectiveTime',
	            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.effectiveTime'),
	            xtype : 'datetimefield',
				format:'Y-m-d H:i:s',
				minValue:new Date(),
				allowBlank:false
	        },{
	            name: 'invalidTime',
	            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.invalidTime'),
	            xtype : 'datetimefield',
				format:'Y-m-d H:i:s',
				minValue:new Date(),
				allowBlank:false
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
			items : [{
	            name: 'remark',
	            width : document.documentElement.clientWidth * 0.75,
	            fieldLabel: extrafee.priceUpstairs.i18n('miser.biz.extrafee.upstairs.remark'),
	            xtype: 'textfield'
	        }]
		} ];
		me.callParent([ cfg ]);
	}
});

/**
 * 查询时候的grid
 */
Ext.define('Miser.biz.extrafee.upstairs.QueryGrid', {
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
		me.columns = [{
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
			dataIndex : 'startValue',
			width : 120,
			text : extrafee.priceUpstairs.i18n('pricecard.priceSection.startValue')
			// '优惠分段编码'
		},
		{
			text : extrafee.priceUpstairs.i18n('pricecard.priceSection.endValue'),
			dataIndex : 'endValue',
			width : 120
		},
		{
			text : extrafee.priceUpstairs.i18n('pricecard.priceSection.sectionAccodingItem'),
			dataIndex : 'sectionAccodingItem',
			width : 100,
			renderer : function(value) {
				return miser.changeCodeToNameStore(
						getDataDictionary()
						.getDataDictionaryStore(extrafee.priceUpstairs.sectionAccodingItem), value);
			}
		},
		{
			text : extrafee.priceUpstairs.i18n('pricecard.priceSection.priceType'),
			dataIndex : 'priceType',
			width : 100,
			renderer : function(value) {
				return miser.changeCodeToNameStore(
						getDataDictionary()
						.getDataDictionaryStore(extrafee.priceUpstairs.priceType), value);
			}
		},
		{
			text : extrafee.priceUpstairs.i18n('pricecard.priceSection.price'),
			dataIndex : 'price',
			width : 100
		},
		{
			text : extrafee.priceUpstairs.i18n('pricecard.priceSection.calcAlone'),
			dataIndex : 'calcAlone',
			width : 100,
			renderer : function(value) {
				return miser.changeCodeToNameStore(
						getDataDictionary()
						.getDataDictionaryStore(extrafee.priceUpstairs.upstairsActive), value);
			}
		},
		{
			text : extrafee.priceUpstairs.i18n('pricecard.priceSection.minPrice'),
			dataIndex : 'minPrice',
			width : 100
		},
		{
			text : extrafee.priceUpstairs.i18n('pricecard.priceSection.maxPrice'),
			dataIndex : 'maxPrice',
			width : 100
		},
		{
			text : extrafee.priceUpstairs.i18n('pricecard.priceSection.remark'), // '备注',
			dataIndex : 'remark',
			width : 100
		} ];
		me.store = getGirdStore();
		me.callParent([ cfg ]);
	}
});

//创建主面板
Ext.onReady(function () {
    Ext.QuickTips.init();
    var queryPanel = Ext.create('Miser.biz.extrafee.upstairs.QueryPanel');
    var upstairsPricesGrid = Ext.create('Miser.biz.extrafee.upstairs.UpstairsPricesGrid');
    Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        id: 'upstairsPricesPanel',
        getQueryPanel: function () {
            return queryPanel;
        },

        getUpstairsPricesGrid: function () {
            return upstairsPricesGrid;
        },
        items: [queryPanel, upstairsPricesGrid]
    });

});