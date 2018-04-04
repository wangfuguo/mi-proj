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
	       url : 'timeCityAction!excelImpl.action',  
	       method : 'post', 
	       params : { 
	    	    'timeCityVo.filePath' : outFileName
	          },  
           success : function(response, options) {  
              Ext.Msg.hide();   
              var responseArray = Ext.util.JSON.decode(response.responseText);
              // succCount:成功条数,failCount:失败条数, list:明细记录.
              if(responseArray.repeatTip == ""){//如果没有重复，填充表单
            	  var msg="成功：" + responseArray.succCount + "条；重复未导入：" + responseArray.failCount + "条；";
            	  miser.showInfoMsg(msg);         
            	  parent.gettimeCityGrid().getPagingToolbar().moveFirst();
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
bizbase.timeCity.timeCityType='TIMECITYONE';
//定义上楼费数据类型
Ext.define('bizbase.timeCity.timeCityEntity', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'code', type: 'string'},
        {name: 'name', type: 'string'},
        {name: 'type', type: 'string'},
        {name: 'remark', type: 'string'},
        {name: 'active', type: 'string'},
        {name: 'invalid', type: 'string'},
        {name: 'createTime', type: 'date', dateFormat:'time'},
        {name: 'createUserCode', type: 'string'},
        {name: 'modifyTime', type: 'date', dateFormat:'time'},
        {name: 'modifyUserCode', type: 'string'}
    ]
});

//定义主表格数据源
Ext.define('bizbase.timeCity.timeCityStore', {
    extend: 'Ext.data.Store',
    model: 'bizbase.timeCity.timeCityEntity',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: 'timeCityAction!query.action',
        reader: {
            type: 'json',
            rootProperty: 'timeCityVo.timeCityList',
            totalProperty: 'totalCount'
        }
    },
    listeners: {
        beforeload: function (store, operation, opts) {
            var queryPanel = Ext.getCmp('queryPanel');
            if (queryPanel != null) {
                var params = {
                    'timeCityVo.timeCityEntity.code': queryPanel.getForm().findField('code').getValue(),
                    'timeCityVo.timeCityEntity.name': queryPanel.getForm().findField('name').getValue(),
                    'timeCityVo.timeCityEntity.invalid': queryPanel.getForm().findField('invalid').getValue()
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
Ext.define('bizbase.timeCity.QueryPanel', {
    extend: 'Ext.form.Panel',
    id: 'queryPanel',
    title: bizbase.timeCity.i18n('bizbase.priceCity.querypanel.title'),
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
				fieldLabel: bizbase.timeCity.i18n('bizbase.priceCity.name'),
				xtype: 'textfield',
				margin:'5px 0px 5px 0px',
				labelAlign:'right'
			}, {
				name: 'code',
				fieldLabel: bizbase.timeCity.i18n('bizbase.priceCity.code'),
				xtype: 'textfield',
				margin:'5px 0px 5px 0px',
				labelAlign:'right'
			}, {
				name: 'invalid',
				fieldLabel: bizbase.timeCity.i18n('bizbase.priceCity.invalid'),
				xtype: 'yesnocombselector',
				isShowAll : true,
				margin:'5px 0px 5px 0px',
				labelAlign:'right'
			}]
        }];
        me.buttons = [{
            text: '查询',
            handler: function () {
                if (me.getForm().isValid()) {
                    me.up().gettimeCityGrid().getPagingToolbar().moveFirst();
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
Ext.define('bizbase.timeCity.timeCityGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    stripeRows: true,
    region: 'center',
    height: document.documentElement.clientHeight - 120,
    emptyText: bizbase.timeCity.i18n('miser.common.queryNoResult'),
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

    timeCityEditWindow: null,
    gettimeCityEditWindow: function () {
        if (this.timeCityEditWindow == null) {
            this.timeCityEditWindow = Ext.create('bizbase.timeCity.timeCityEditWindow');
            this.timeCityEditWindow.parent = this;
        }
        return this.timeCityEditWindow;
    },

    updatetimeCity: function () {
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
        var params = {'timeCityVo': {'timeCityEntity': {'id': id}}};
        var successFun = function (json) {
            var timeCityEditWindow = me.gettimeCityEditWindow();
            timeCityEditWindow.timeCityEntity = json.timeCityVo.timeCityEntity;
            timeCityEditWindow.show();
            timeCityEditWindow.gettimeCityForm().getForm().findField('id').setValue(timeCityEditWindow.timeCityEntity.id);
            timeCityEditWindow.gettimeCityForm().getForm().findField('code').setValue(timeCityEditWindow.timeCityEntity.code);
            timeCityEditWindow.gettimeCityForm().getForm().findField('name').setValue(timeCityEditWindow.timeCityEntity.name);
            timeCityEditWindow.gettimeCityForm().getForm().findField('type').setValue(timeCityEditWindow.timeCityEntity.type);
            timeCityEditWindow.gettimeCityForm().getForm().findField('remark').setValue(timeCityEditWindow.timeCityEntity.remark);
            timeCityEditWindow.gettimeCityForm().getForm().findField('code').setReadOnly(true);
        };

        miser.requestJsonAjax('timeCityAction!queryById.action', params, successFun, failFun);
    },

    deletetimeCity: function () {
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
        miser.showQuestionMes('确定作废时效城市?', function (e) {
        	if (e =='yes') {
                var id = selections[0].get('id');
                var param = {'timeCityVo': {'timeCityEntity': {'id': id}}};

                var successFun = function (json) {
                    var msg = json.message;
                    miser.showInfoMsg(msg);
                    me.getStore().load();
                };

                miser.requestJsonAjax('timeCityAction!invalidTimeCity.action', param, successFun, failFun);	
        	}
        });
    },

    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.columns = [
            {
                text: bizbase.timeCity.i18n('bizbase.priceCity.rownumberer'),//序号
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
                text: bizbase.timeCity.i18n('bizbase.priceCity.id')//主键
            },
            {
                dataIndex: 'code',
                width: 100,
                align: 'center',
                text: bizbase.timeCity.i18n('bizbase.priceCity.code')//编码
            },
            {
                text: bizbase.timeCity.i18n('bizbase.priceCity.name'),//名称
                dataIndex: 'name',
                align: 'center',
                width: 100
            },
            {
                text: bizbase.timeCity.i18n('bizbase.priceCity.type'), //上级
                dataIndex: 'type',
                width: 100,
                align: 'center',
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(bizbase.timeCity.timeCityType), value);
                }
            },
            {
                text: bizbase.timeCity.i18n('bizbase.priceCity.invalid'), //是否作废
                dataIndex: 'invalid',
                width: 100,
                align: 'center',
                renderer: function (value) {
                    return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('IS_ACTIVE'), value);
                }
            },
            {
                text: bizbase.timeCity.i18n('bizbase.priceCity.remark'), //备注
                dataIndex: 'remark',
                align: 'center',
                width: 150
            },
		{
			text : bizbase.timeCity.i18n('bizbase.priceCity.createUser'), // '创建人',
			dataIndex : 'createUserCode',
            align: 'center',
			width : 120
		},
		{
			text : bizbase.timeCity.i18n('bizbase.priceCity.createDate'), // '创建时间',
			dataIndex : 'createTime',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
            align: 'center',
			width : 150
		},
		{
			text : bizbase.timeCity.i18n('bizbase.priceCity.modifyUser'), // '修改用户',
			dataIndex : 'modifyUserCode',
            align: 'center',
			width : 120
		},
		{
			text : bizbase.timeCity.i18n('bizbase.priceCity.modifyDate'), // '修改时间',
			dataIndex : 'modifyTime',
			xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
            align: 'center',
			width : 150
		}
        ];
        me.store = Ext.create('bizbase.timeCity.timeCityStore', {
            autoLoad : false
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', {
            listeners: {
                selectionchange: function (sm, selections) {
                    Ext.getCmp('miser_biz_base_timeCity_updatebutton_id').setDisabled(selections.length !== 1);
                    Ext.getCmp('miser_biz_base_timeCity_deletebutton_id').setDisabled(selections.length === 0);
                }
            }
        });
        me.tbar = [{
            text: bizbase.timeCity.i18n('miser.common.add'),
            xtype: 'addbutton',
            width: 80,
            handler: function () {
                me.gettimeCityEditWindow().show();
            }
        }, {
            id : 'miser_biz_base_timeCity_updatebutton_id',
            text: bizbase.timeCity.i18n('miser.common.update'),
            xtype: 'updatebutton',
            width: 80,
            handler: function () {
                me.updatetimeCity();
            }
        }, {
            id : 'miser_biz_base_timeCity_deletebutton_id',
            text: bizbase.timeCity.i18n('miser.common.invalidate'),
            xtype: 'deletebutton',
            width: 80,
            handler: function () {
                me.deletetimeCity();
            }
        },{
			text: '导出价格城市',//'导出价格城市',
			xtype: 'downloadbutton',
			width: 130,
			handler: function() {
				Ext.Msg.wait('处理中，请稍后...', '提示');
				
				var queryPanel = Ext.getCmp('queryPanel');
	            if (queryPanel != null) {
	                var params = {
	                    'timeCityVo.timeCityEntity.code': queryPanel.getForm().findField('code').getValue(),
	                    'timeCityVo.timeCityEntity.name': queryPanel.getForm().findField('name').getValue(),
	                    'timeCityVo.timeCityEntity.invalid': queryPanel.getForm().findField('invalid').getValue()
	                };
	            }
	            
				Ext.Ajax.request( {  
				       url : 'timeCityAction!excelExport.action',  
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
			text:  '导入',//'导入',
			xtype: 'uploadbutton',
			width: 130,
			handler: function() {
				uploadT.excelWindow(null,imple,Ext.getCmp("timeCityPanel"));
			}
		},{
			text: '导入模板下载',//'导入模板下载',
			xtype: 'downloadbutton',
			width: 130,
			handler: function() {
				miser.requestExportAjax('/excelTemplate/timeCityDefin.xls');
			}
		}];
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    }
});

//定义维护数据窗口
Ext.define('bizbase.timeCity.timeCityEditWindow', {
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
            me.gettimeCityForm().getForm().reset(); // 表格重置
        },
        beforeshow: function (me) { // 显示WINDOW的时候清除数据
            var fielsds = me.gettimeCityForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function (item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
            me.gettimeCityForm().getForm().findField('code').setReadOnly(true);
            me.gettimeCityForm().getForm().findField('code').setValue('<自动生成>');
        }
    },
    //获取维护数据的表单
    timeCityForm: null,
    gettimeCityForm: function () {
        if (this.timeCityForm == null) {
            this.timeCityForm = Ext.create('bizbase.timeCity.timeCityForm');
        }
        return this.timeCityForm;
    },
    //定义保存数据方法
    posttimeCityData: function () {
        var me = this;
        if (me.gettimeCityForm().getForm().isValid()) {
            var model = new bizbase.timeCity.timeCityEntity();
            me.gettimeCityForm().getForm().updateRecord(model);
            var param = {'timeCityVo': {'timeCityEntity': model.data}};

            var successFun = function (json) {
                var msg = json.message;
                miser.showInfoMsg(msg);
                me.close();
                me.parent.getStore().load();
            };

            miser.requestJsonAjax('timeCityAction!updateTimeCity.action', param, successFun, failFun);
        }
    },

    constructor: function (config) {
        var me = this;
        var cfg = Ext.apply({}, config);
        me.fbar = [{
            text: '保存',
            handler: function () {
                me.posttimeCityData();
            }
        }, {
            text: '重置',
            handler: function () {
//                me.gettimeCityForm().reset();
            	me.gettimeCityForm().getForm().findField('name').setValue('');
            	me.gettimeCityForm().getForm().findField('type').setValue('');
            	me.gettimeCityForm().getForm().findField('remark').setValue('');
                
            }
        }];
        me.items = [me.gettimeCityForm()];
        me.callParent([cfg]);
    }
});

//定义维护数据表单
Ext.define('bizbase.timeCity.timeCityForm', {
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
            fieldLabel: bizbase.timeCity.i18n('bizbase.priceCity.code'),
            xtype: 'textfield',
            maxLength : 20,
            allowBlank: false
        }, {
            name: 'name',
            fieldLabel: bizbase.timeCity.i18n('bizbase.priceCity.name'),
            xtype: 'textfield',
            maxLength : 25, 
            allowBlank: false
        }, {
            name: 'type',
            fieldLabel: bizbase.timeCity.i18n('bizbase.priceCity.type'),
            xtype : 'dataDictionarySelector',
			termsCode : bizbase.timeCity.timeCityType
        }, {
            name: 'remark',
            fieldLabel: bizbase.timeCity.i18n('bizbase.priceCity.remark'),
            xtype: 'textfield'
        }];

        me.callParent([cfg]);
    }

});

//创建主面板
Ext.onReady(function () {
    Ext.QuickTips.init();
    var queryPanel = Ext.create('bizbase.timeCity.QueryPanel');
    var timeCityGrid = Ext.create('bizbase.timeCity.timeCityGrid');
    var mainPanel = Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        id: 'timeCityPanel',
        getQueryPanel: function () {
            return queryPanel;
        },

        gettimeCityGrid: function () {
            return timeCityGrid;
        },
        items: [queryPanel, timeCityGrid]
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
    	timeCityGrid.setWidth(document.documentElement.clientWidth);
    };

});