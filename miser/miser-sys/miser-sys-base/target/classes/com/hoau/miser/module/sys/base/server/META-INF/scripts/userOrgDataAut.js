/**
 * 获取浏览器高度
 */
miser.getBrowserHeight = function() {
    var browserHeight = document.documentElement.clientHeight;
    return browserHeight;
};
/**
 * 用户部门数据权限信息model
 */

Ext.define('Miser.base.userOrgDataAut.UserOrgDataAutEntity', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        //ID
        type: 'string'
    },
    {
        name: 'userName',
        //用户名
        type: 'string'
    },
    {
        name: 'orgCode',
        //部门编码
        type: 'string'
    },
    {
        name: 'includeSubOrg',
        //是否包含子部门
        type: 'string'
    },
    {
      //修改日期
       name: 'modifyDate', 
       type : 'date', 
       dateFormat:'time'
    },
    {
        name: 'orgName',
        //部门名稱
        type: 'string'
    },
    {
        name: 'empName',
        //員工名稱
        type: 'string'
    } ]
});

/**
 * 用户部门stores
 */
Ext.define('Miser.base.userOrgDataAut.UserOrgDataAutStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.base.userOrgDataAut.UserOrgDataAutEntity',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: 'userOrgDataAutAction!queryUserOrgDataAut.action',
        reader: {
            type: 'json',
            rootProperty: 'userOrgDataAutVo.userOrgDataAutList',
            totalProperty: 'totalCount' //总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('miser_base_userOrgDataAut_UserOrgDataAutQueryForm_id');
            if (queryForm != null) {
                var params = {
                    'userOrgDataAutVo.userOrgDataAutEntity.userName': queryForm.getForm().findField('userName').getValue(),
                    'userOrgDataAutVo.userOrgDataAutEntity.orgCode': queryForm.getForm().findField('orgCode').getValue()
                    };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
    }
});


/**
 * 查询表单
 */
Ext.define('Miser.base.userOrgDataAut.UserOrgDataAutQueryForm', {
    extend: 'Ext.form.Panel',
    id: 'miser_base_userOrgDataAut_UserOrgDataAutQueryForm_id',
    frame: true,
    title: base.userOrgDataAut.i18n('miser.common.queryCondition'),//查询条件
    //查询条件
    height: 120,
    collapsible: true,
    layout: 'hbox',
    defaults: {
        colspan: 1,
        margin: '8 10 5 10'
    },
    defaultType: 'textfield',
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.items = [{
        	xtype : 'dynamicusercombselector',
        	name: 'userName',
			fieldLabel:base.userOrgDataAut.i18n('bse.userOrgDataAut.userName')//'用户名'			
        },
        {
        	xtype : 'dynamicorgcombselector',
			name: 'orgCode',
			type : 'ALL',
			fieldLabel:base.userOrgDataAut.i18n('bse.userOrgDataAut.orgName')//'部门名称'
        }],
        me.buttons = [{
            text:base.userOrgDataAut.i18n('miser.common.query'),//查询
            handler: function() {
                if (me.getForm().isValid()) {
                    me.up().getUserOrgDataAutGrid().getPagingToolbar().moveFirst();
                }
            }
        },
        {
            text: base.userOrgDataAut.i18n('miser.common.reset'),//重置
            handler: function() {
                me.getForm().reset();
            }
        }];
        me.callParent([cfg]);
    }
});




/**
 * 角色信息表格
 */
Ext.define('Miser.base.userOrgDataAut.UserOrgDataAutGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    height: miser.getBrowserHeight() - 120,
    stripeRows: true,
    // 交替行效果
    selType: "rowmodel",
    // 选择类型设置为：行选择
    emptyText:base.userOrgDataAut.i18n('miser.common.resultisNull'),//查询结果为空
    columnLines: true,
    viewConfig: {
        enableTextSelection: true
    },
    userOrgDataAutAddWindow: null,
    getUserOrgDataAutAddWindow: function() {
        if (this.userOrgDataAutAddWindow == null) {
            this.userOrgDataAutAddWindow = Ext.create('Miser.base.userOrgDataAut.UserOrgDataAutAddWindow');
            this.userOrgDataAutAddWindow.parent = this; //父元素
        }
        return this.userOrgDataAutAddWindow;
    },    
     deleteUserOrgDateAut:function() {
         var me = this;
         var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
         if (selections.length < 1) { // 判断是否至少选中了一条
             miser.showWoringMessage('请选择一条进行删除操作'); // 请选择一条进行作废操作！
             return; // 没有则提示并返回
         }
         var idList = new Array();
         for (var i = 0; i < selections.length; i++) {
         	idList.push({'id':selections[i].get('id')});
         }
         miser.showQuestionMes('是否要删除',
         function(e) {
             if (e == 'yes') { // 询问是否删除，是则发送请求
                 var params = {
                     'userOrgDataAutVo' : {                    	 
                    		 'userOrgDataAutList':idList                   			 
                    		 
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
                     } else {
                         var message = json.message;
                         miser.showErrorMes(message);
                     }
                 };
                 miser.requestJsonAjax('userOrgDataAutAction!deleteUserOrgDataAut.action', params, successFun, failureFun);
             }
         });
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
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.columns = [
        {
            dataIndex: 'id',
            flex : 1,
            hidden: true,
            text:'ID'//用户名
        },
        {
            dataIndex: 'empName',
            flex : 1,
            text:base.userOrgDataAut.i18n('bse.userOrgDataAut.userName')//'用户名'
        },
        {
            text: '部门名称',//部门编码
            dataIndex: 'orgName',
            flex : 1
        },
        {
            text: base.userOrgDataAut.i18n('bse.userOrgDataAut.includeSubOrg'),//'是否包含子部门',
            dataIndex: 'includeSubOrg',//是否包含子部门
            flex : 1,
            renderer:function(value){
  				return booleanTypeRender(value);
			}
        },
        {
        	text: base.userOrgDataAut.i18n('bse.userOrgDataAut.modifyDate'),//'修改日期',
            dataIndex: 'modifyDate',//修改时间
            flex : 1,
            xtype :'datecolumn', 
            format :'Y-m-d H:i:s'
        }],
        me.store = Ext.create('Miser.base.userOrgDataAut.UserOrgDataAutStore', {
            autoLoad: false
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', { //多选框
        	 listeners: {
 	            selectionchange: function(sm, selections) {
 	                Ext.getCmp('miser_base_userOrgDateAut_userOrgDateAut_id').setDisabled(selections.length === 0);
 	            }
 	        }
        });
        me.tbar = [{
        	xtype: 'addbutton',
            text: base.userOrgDataAut.i18n('miser.common.add'),//新增
            width: 80,
            handler: function() {
                me.getUserOrgDataAutAddWindow().show();
            }
        },    
        '-', {
        	id:'miser_base_userOrgDateAut_userOrgDateAut_id',
        	xtype: 'deletebutton',
            text:base.userOrgDataAut.i18n('miser.common.delete'),//删除
            width: 80,
            disabled:true,
            handler: function() {
                me.deleteUserOrgDateAut();
                
            }       
        }];
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    }
});

/**
 * 用户部门数据权限信息表单
 */
Ext.define('Miser.base.userOrgDataAut.UserOrgDataAutForm', {
    extend: 'Ext.form.Panel',
    header: false,
    frame: true,
    collapsible: true,
    width: 280,
    fieldDefaults: {
        labelWidth: 80,
        margin: '8 10 5 10'
    },
    defaultType: 'textfield',
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.items = [{
        	xtype : 'dynamicusercombselector',
        	name: 'userName',
			fieldLabel:base.userOrgDataAut.i18n('bse.userOrgDataAut.userName'),//'用户名',
			isEmp :'Y'
           
        },
        {
        	xtype : 'dynamicorgcombselector',
			name: 'orgCode',
			type : 'ALL',
			fieldLabel:base.userOrgDataAut.i18n('bse.userOrgDataAut.orgName')//'部门名称'
        },
        {    
        	 xtype : 'yesnocheckbox',
        	 name: 'includeSubOrg',
        	  labelWidth: 100,
            fieldLabel:base.userOrgDataAut.i18n('bse.userOrgDataAut.includeSubOrg')//'是否含子部门'
           
        }
        ];
        me.callParent([cfg]);
    }
});


/**
 * 新增角色窗口
 */
Ext.define('Miser.base.userOrgDataAut.UserOrgDataAutAddWindow', {
    extend: 'Ext.window.Window',
    title: '新增用户部门数据权限信息',//新增用户部门数据权限信息
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
            me.getUserOrgDataAutForm().getForm().reset(); // 表格重置
        },
        beforeshow: function(me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getUserOrgDataAutForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function(item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
        }
    },
    userOrgDataAutForm: null,
    getUserOrgDataAutForm: function() {
        if (Ext.isEmpty(this.userOrgDataAutForm)) {
            this.userOrgDataAutForm = Ext.create('Miser.base.userOrgDataAut.UserOrgDataAutForm');
        }
        return this.userOrgDataAutForm;
    },
    submitUserOrgDataAut: function() {
    	
        var me = this;
        if (me.getUserOrgDataAutForm().getForm().isValid()) { //校验form是否通过校验
        	
            var userOrgDataAutModel = new Miser.base.userOrgDataAut.UserOrgDataAutEntity;
            me.getUserOrgDataAutForm().getForm().updateRecord(userOrgDataAutModel); //将FORM中数据设置到MODEL里面
            var params = {
                'userOrgDataAutVo': {
                    'userOrgDataAutEntity': userOrgDataAutModel.data
                }
            }
            var successFun = function(json) {
                var message = json.message;
                miser.showInfoMsg(message); //提示新增成功
                me.close();
                me.parent.getStore().load(); //成功之后重新查询刷新结果集
            };
            var failureFun = function(json) {
                if (Ext.isEmpty(json)) {
                    miser.showErrorMes('连接超时'); //请求超时
                } else {
                    var message = json.message;
                    miser.showErrorMes(message); //提示失败原因
                }
            };
            miser.requestJsonAjax('userOrgDataAutAction!addUserOrgDataAut.action', params, successFun, failureFun); //发送AJAX请求
          
        }
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [
        {
            text:base.userOrgDataAut.i18n('miser.common.reset'),//重置
            handler: function() {
                me.getUserOrgDataAutForm().reset();
            }
        },
        {
            text:base.userOrgDataAut.i18n('miser.common.cancel'),//取消
            handler: function() {
                me.close();
            }
        },
        {
        	text:base.userOrgDataAut.i18n('miser.common.save'),//保存
            margin : '0 0 0 55',
            handler: function() {
                me.submitUserOrgDataAut();
            }
        }];
        me.items = [me.getUserOrgDataAutForm()];
        me.callParent([cfg]);
    }
});




Ext.onReady(function() {
    Ext.QuickTips.init();
    var queryForm = Ext.create('Miser.base.userOrgDataAut.UserOrgDataAutQueryForm'); //查询FORM
    var userOrgDataAutGrid = Ext.create('Miser.base.userOrgDataAut.UserOrgDataAutGrid');
    Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        getQueryForm: function() {
            return queryForm;
        },
        getUserOrgDataAutGrid: function() {
            return userOrgDataAutGrid;
        },
        items: [queryForm, userOrgDataAutGrid]
    });
});