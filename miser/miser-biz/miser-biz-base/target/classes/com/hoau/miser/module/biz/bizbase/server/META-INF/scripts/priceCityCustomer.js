//文件上传excel
var uploadT={
	oufileName:null,
	excelWindow:function(url,fn,parent){
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
									fn(action.result.outFileName,true,parent);
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

//后台读excel数据，再返回前台
function imple(outFileName,flag,parent){
	if(flag){
		miser.showInfoMsg('上传完成');
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request( {  
		   timeout: 180000,
	       url : 'priceCityCustomerAction!excelImpl.action',  
	       method : 'post', 
	       params : { 
	    	    'priceCityVo.filePath' : outFileName
	          },  
           success : function(response, options) {  
              Ext.Msg.hide();   
              var responseArray = Ext.util.JSON.decode(response.responseText);
              // succCount:成功条数,failCount:失败条数, list:明细记录.
              if(responseArray.repeatTip == ""){//如果没有重复，填充表单
            	  var msg="成功：" + responseArray.succCount + "条；重复未导入：" + responseArray.failCount + "条；";
            	  miser.showInfoMsg(msg);         
            	  parent.getpriceCityGrid().getPagingToolbar().moveFirst();
            	  if(responseArray.filePath != ""){
            		  miser.requestExportAjax(responseArray.filePath);
            	  }
              }else{
            	  Ext.MessageBox.show({title: '失败',msg: '导入失败:' + responseArray.repeatTip,buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR}); 
            	  //miser.showInfoMsg("导入失败：" + responseArray.repeatTip);
              }
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

//价格城市城市类型
bizbase.priceCityCustomer.priceCityType='PRICECITYONE';
//定义上楼费数据类型
Ext.define('bizbase.priceCity.priceCityEntity', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'code', type: 'string'},
        {name: 'name', type: 'string'},
        {name: 'type', type: 'string'},
        {name: 'remark', type: 'string'},
        {name: 'active', type: 'string'},
        {name: 'invalid', type: 'string'},
        {name: 'createDate', type: 'date', dateFormat:'time'},
        {name: 'createUser', type: 'string'},
        {name: 'modifyDate', type: 'date', dateFormat:'time'},
        {name: 'modifyUser', type: 'string'}
    ]
});

//定义主表格数据源
Ext.define('bizbase.priceCityCustomer.priceCityStore', {
    extend: 'Ext.data.Store',
    model: 'bizbase.priceCity.priceCityEntity',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: 'priceCityCustomerAction!queryPriceCities.action',
        reader: {
            type: 'json',
            rootProperty: 'priceCityVo.priceCityEntities',
            totalProperty: 'totalCount'
        }
    },
    listeners: {
        beforeload: function (store, operation, opts) {
            var queryPanel = Ext.getCmp('queryPanel');
            if (queryPanel != null) {
                var params = {
                    'priceCityVo.queryParam.code': queryPanel.getForm().findField('code').getValue(),
                    'priceCityVo.queryParam.name': queryPanel.getForm().findField('name').getValue(),
                    'priceCityVo.queryParam.invalid': queryPanel.getForm().findField('invalid').getValue(),
                    'priceCityVo.queryParam.active': queryPanel.getForm().findField('active').getValue(),
                    'priceCityVo.queryParam.priceCityScope': 'CUSTOMER'
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
Ext.define('bizbase.priceCityCustomer.QueryPanel', {
    extend: 'Ext.form.Panel',
    id: 'queryPanel',
    title: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.querypanel.title'),
    frame: true,
	width : document.documentElement.clientWidth,
    collapsible: true,
	region : 'north',
	defaults : {
		labelWidth : 0.2,
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
				name: 'name',
				fieldLabel: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.name'),
				xtype: 'textfield',
				margin:'5px 0px 5px 0px',
				labelAlign:'right'
			}, {
				name: 'code',
				fieldLabel: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.code'),
				xtype: 'textfield',
				margin:'5px 0px 5px 0px',
				labelAlign:'right'
			}, {
				name: 'invalid',
				fieldLabel: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.invalid'),
				xtype: 'yesnocombselector',
				isShowAll : true,
				margin:'5px 0px 5px 0px',
				labelAlign:'right'
			}, {
				name: 'active',
				fieldLabel: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.active'),
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
                    me.up().getpriceCityGrid().getPagingToolbar().moveFirst();
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
Ext.define('bizbase.priceCityCustomer.priceCityGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    stripeRows: true,
    region: 'center',
    height: document.documentElement.clientHeight - 120,
    emptyText: bizbase.priceCityCustomer.i18n('miser.common.queryNoResult'),
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

    priceCityEditWindow: null,
    getpriceCityEditWindow: function () {
        if (this.priceCityEditWindow == null) {
            this.priceCityEditWindow = Ext.create('bizbase.priceCityCustomer.priceCityEditWindow');
            this.priceCityEditWindow.parent = this;
        }
        return this.priceCityEditWindow;
    },

    updatepriceCity: function () {
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
        var params = {'priceCityVo': {'queryParam': {'id': id}}};
        var successFun = function (json) {
            var priceCityEditWindow = me.getpriceCityEditWindow();
            priceCityEditWindow.priceCityEntity = json.priceCityVo.priceCityEntity;
            priceCityEditWindow.show();
            priceCityEditWindow.getpriceCityForm().getForm().findField('id').setValue(priceCityEditWindow.priceCityEntity.id);
            priceCityEditWindow.getpriceCityForm().getForm().findField('code').setValue(priceCityEditWindow.priceCityEntity.code);
            priceCityEditWindow.getpriceCityForm().getForm().findField('name').setValue(priceCityEditWindow.priceCityEntity.name);
            priceCityEditWindow.getpriceCityForm().getForm().findField('type').setValue(priceCityEditWindow.priceCityEntity.type);
            priceCityEditWindow.getpriceCityForm().getForm().findField('remark').setValue(priceCityEditWindow.priceCityEntity.remark);
            priceCityEditWindow.getpriceCityForm().getForm().findField('code').setReadOnly(true);
        };

        miser.requestJsonAjax('priceCityCustomerAction!queryPriceCityById.action', params, successFun, failFun);
    },

    deletepriceCity: function () {
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
        miser.showQuestionMes('确定删除价格城市?', function (e) {
        	if (e =='yes') {
                var id = selections[0].get('id');
                var param = {'priceCityVo': {'queryParam': {'id': id}}};

                var successFun = function (json) {
                    var msg = json.message;
                    miser.showInfoMsg(msg);
                    me.getStore().load();
                };

                miser.requestJsonAjax('priceCityCustomerAction!invalidPriceCity.action', param, successFun, failFun);	
        	}
        });
    },

    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.columns = [
            {
                text: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.rownumberer'),//序号
                dataIndex: 'valueSeq',
                xtype: 'rownumberer',
                align: 'center',
                width: 60
            },
            {
                dataIndex: 'id',
                width: 100,
                hidden : true,
                text: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.id')//主键
            },
            {
                dataIndex: 'code',
                width: 100,
                text: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.code')//编码
            },
            {
                text: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.name'),//名称
                dataIndex: 'name',
                width: 100
            },
            {
                text: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.type'), //上级
                dataIndex: 'type',
                width: 100,
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(bizbase.priceCityCustomer.priceCityType), value);
                }
            },
            {
                text: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.invalid'), //是否作废
                dataIndex: 'invalid',
                width: 100,
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('IS_ACTIVE'), value);
                }
            },
            {
                text: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.active'), //是否有效
                dataIndex: 'active',
                width: 100,
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('IS_ACTIVE'), value);
                }
            },
            {
                text: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.remark'), //备注
                dataIndex: 'remark',
                width: 150
            },
		{
			text : bizbase.priceCityCustomer
					.i18n('bizbase.priceCityCustomer.createUser'), // '创建人',
			dataIndex : 'createUser',
			width : 120
		},
		{
			text : bizbase.priceCityCustomer
					.i18n('bizbase.priceCityCustomer.createDate'), // '创建时间',
			dataIndex : 'createDate',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 150
		},
		{
			text : bizbase.priceCityCustomer
					.i18n('bizbase.priceCityCustomer.modifyUser'), // '修改用户',
			dataIndex : 'modifyUser',
			width : 120
		},
		{
			text : bizbase.priceCityCustomer
					.i18n('bizbase.priceCityCustomer.modifyDate'), // '修改时间',
			dataIndex : 'modifyDate',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
			width : 150
		}
        ];
        me.store = Ext.create('bizbase.priceCityCustomer.priceCityStore', {
            autoLoad : false
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', {
            listeners: {
                selectionchange: function (sm, selections) {
                    Ext.getCmp('miser_biz_base_priceCity_updatebutton_id').setDisabled(selections.length !== 1);
                    Ext.getCmp('miser_biz_base_priceCity_deletebutton_id').setDisabled(selections.length === 0);
                }
            }
        });
        me.tbar = [{
            text: bizbase.priceCityCustomer.i18n('miser.common.add'),
            xtype: 'addbutton',
            width: 80,
            handler: function () {
                me.getpriceCityEditWindow().show();
            }
        }, {
            id : 'miser_biz_base_priceCity_updatebutton_id',
            text: bizbase.priceCityCustomer.i18n('miser.common.update'),
            xtype: 'updatebutton',
            width: 80,
            handler: function () {
                me.updatepriceCity();
            }
        }, {
            id : 'miser_biz_base_priceCity_deletebutton_id',
            text: bizbase.priceCityCustomer.i18n('miser.common.invalidate'),
            xtype: 'deletebutton',
            width: 80,
            handler: function () {
                me.deletepriceCity();
            }
        },{
			text: bizbase.priceCityCustomer.i18n('bizbase.pricecityCustomer.excelExpot'),
			xtype: 'downloadbutton',
			width: 130,
			handler: function() {
				Ext.Msg.wait('处理中，请稍后...', '提示');
				
				var queryPanel = Ext.getCmp('queryPanel');
	            if (queryPanel != null) {
	                var params = {
	                    'priceCityVo.queryParam.code': queryPanel.getForm().findField('code').getValue(),
	                    'priceCityVo.queryParam.name': queryPanel.getForm().findField('name').getValue(),
	                    'priceCityVo.queryParam.invalid': queryPanel.getForm().findField('invalid').getValue(),
	                    'priceCityVo.queryParam.priceCityScope': 'CUSTOMER'
	                };
	            }
	            
				Ext.Ajax.request( {  
				       url : 'priceCityAction!excelExport.action',  
				       method : 'post',  
				       params : params,  
				       success : function(response, options) {  
				              // 隐藏进度条
				              Ext.Msg.hide();   
				              var responseArray = Ext.util.JSON.decode(response.responseText); 
				              // addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
				              var msg="本次导出共"+responseArray.count+"条数据！";
				              miser.showInfoMsg(msg);
				              miser.requestExportAjax(responseArray.filePath);
				        },
				        failure : function() { 
				            Ext.Msg.hide();  
				            Ext.MessageBox.show({title: '失败',msg: '下载失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
				        }
				});  
			}
		},{
			text: bizbase.priceCityCustomer.i18n('bizbase.pricecityCustomer.excelImpl'),//'导入',
			xtype: 'uploadbutton',
			width: 130,
			handler: function() {
				uploadT.excelWindow(null,imple,Ext.getCmp("priceCityPanel"));
			}
		},{
			text: bizbase.priceCityCustomer.i18n('bizbase.pricecityCustomer.excelImplTempl'),//'导入模板下载',
			xtype: 'downloadbutton',
			width: 130,
			handler: function() {
				miser.requestExportAjax('/excelTemplate/priceCityCustomerDefin.xls');
			}
		}];
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    }
});

//定义维护数据窗口
Ext.define('bizbase.priceCityCustomer.priceCityEditWindow', {
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
            me.getpriceCityForm().getForm().reset(); // 表格重置
        },
        beforeshow: function (me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getpriceCityForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function (item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
            me.getpriceCityForm().getForm().findField('code').setReadOnly(false);
        }
    },
    //获取维护数据的表单
    priceCityForm: null,
    getpriceCityForm: function () {
        if (this.priceCityForm == null) {
            this.priceCityForm = Ext.create('bizbase.priceCityCustomer.priceCityForm');
        }
        return this.priceCityForm;
    },
    //定义保存数据方法
    postpriceCityData: function () {
        var me = this;
        if (me.getpriceCityForm().getForm().isValid()) {
            var model = new bizbase.priceCity.priceCityEntity();
            me.getpriceCityForm().getForm().updateRecord(model);
            var param = {'priceCityVo': {'queryParam': model.data}};

            var successFun = function (json) {
                var msg = json.message;
                miser.showInfoMsg(msg);
                me.close();
                me.parent.getStore().load();
            };

            miser.requestJsonAjax('priceCityCustomerAction!updateCustomerPriceCity.action', param, successFun, failFun);
        }
    },

    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.fbar = [{
            text: '保存',
            handler: function () {
                me.postpriceCityData();
            }
        }, {
            text: '重置',
            handler: function () {
                me.getpriceCityForm().reset();
            }
        }];
        me.items = [me.getpriceCityForm()];
        me.callParent([cfg]);
    }
});

//定义维护数据表单
Ext.define('bizbase.priceCityCustomer.priceCityForm', {
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
            fieldLabel: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.code'),
            xtype: 'textfield',
            maxLength : 20,
            allowBlank: false
        }, {
            name: 'name',
            fieldLabel: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.name'),
            xtype: 'textfield',
            maxLength : 25, 
            allowBlank: false
        }, {
            name: 'type',
            fieldLabel: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.type'),
            xtype : 'dataDictionarySelector',
			termsCode : bizbase.priceCityCustomer.priceCityType
        }, {
            name: 'remark',
            fieldLabel: bizbase.priceCityCustomer.i18n('bizbase.priceCityCustomer.remark'),
            xtype: 'textfield'
        }];

        me.callParent([cfg]);
    }

});

//创建主面板
Ext.onReady(function () {
    Ext.QuickTips.init();
    var queryPanel = Ext.create('bizbase.priceCityCustomer.QueryPanel');
    var priceCityGrid = Ext.create('bizbase.priceCityCustomer.priceCityGrid');
    var mainPanel = Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        id: 'priceCityPanel',
        getQueryPanel: function () {
            return queryPanel;
        },

        getpriceCityGrid: function () {
            return priceCityGrid;
        },
        items: [queryPanel, priceCityGrid]
    });
    
    //设置窗口大小改变时的响应
    window.onresize = function(){
    
        //控制整个 panel 的宽度
    	mainPanel.setWidth(document.documentElement.clientWidth);
    	
    	//控制整个 查询表单 的宽度
    	queryPanel.setWidth(document.documentElement.clientWidth);
        
        //设置区域一行表单的宽度
//    	var items = queryPanel.items;
//		document.getElementById(items.items[1].items.items[0].getEl().el.parent().id).style.width = (document.documentElement.clientWidth - 10)+'px';
		
		
		//设置查询结果的宽度
    	priceCityGrid.setWidth(document.documentElement.clientWidth);
    };

});