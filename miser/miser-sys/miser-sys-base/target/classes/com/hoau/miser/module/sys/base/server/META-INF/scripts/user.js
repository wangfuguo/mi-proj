/**
 * 人员信息实体
 */
Ext.define('Miser.base.user.UserEntity', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        type: 'string'
    },
    {
        name: 'orgCode',
        type: 'string'
    },
    {
        name: 'userName',
        type: 'string'
    },
    {
        name: 'empCode',
        type: 'string'
    },
    {
        name: 'empName',
        type: 'string'
    },
    {
        name: 'title',
        type: 'string'
    },
    {
        name: 'password',
        type: 'string'
    },
    {
        name: 'beginDate',
        type: 'date',
        convert: dateConvert
        //format:'Y-m-d'
    },
    {
        name: 'endDate',
        type: 'date',
        convert: dateConvert
    },
    {
        name: 'isEmp',
        type: 'string'
    }]
});
/**
 * role的Model
 */
Ext.define('Miser.model.bse.roleModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        type: 'string'
    },
    {
        name: 'code',
        type: 'string'
    },
    {
        name: 'name',
        type: 'string'
    },
    {
        name: 'notes',
        type: 'string'
    },
    {
        name: 'active',
        type: 'string'
    }]
});



Ext.define('Miser.base.user.QueryForm', {
    extend: 'Ext.form.Panel',
    id: 'queryForm',
    frame: true,
    title: '用户查询',
    height: 113,
    collapsible: true,
    layout: 'column',
    region: 'north',
    defaults: {
        margin: '8 10 5 10',
        labelWidth: 100,
        columnWidth: 0.23,
        labelAlign: 'right'
    },
    defaultType: 'textfield',
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.items = [{
            name: 'userName',
            fieldLabel: '用户名',
            xtype: 'textfield'
        },
        {
            name: 'empCode',
            fieldLabel: '员工工号',
            xtype: 'textfield'
        },
        {
            name: 'empName',
            fieldLabel: '员工姓名',
            xtype: 'textfield'
        }],
        me.buttons = [{
            text: '查询',
            handler: function() {
                if (me.getForm().isValid()) {
                    me.up().getUserGrid().getPagingToolbar().moveFirst();
                }
            }
        },
        {
            text: '清空',
            handler: function() {
                me.getForm().reset();
            }
        }];
        me.callParent([cfg]);
    }
});

/**
 * 用户信息表格
 */
Ext.define('Miser.base.user.UserGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    width: '100%',
    height: document.documentElement.clientHeight - 150,
    stripeRows: true,
    // 交替行效果
    region: 'center',
    emptyText: '查询结果为空',
    selModel: Ext.create('Ext.selection.CheckboxModel', {
        listeners: {
            selectionchange: function(sm, selections) {
                Ext.getCmp('miser_base_user_rolesettingbtn_id').setDisabled(selections.length !== 1);
                Ext.getCmp('miser_base_user_deletebtn_id').setDisabled(selections.length === 0);
                try{
                var idEmp = selections[0].get('isEmp');
                var length=selections.length;
                if(idEmp==='N' && length === 1){
                	Ext.getCmp('updateEmployee_id').setDisabled(false);	 
                }else{
                	Ext.getCmp('updateEmployee_id').setDisabled(true);
                }         
                }catch(e){}
                
            }
        }
    }),
    userAddWindow: null,
    getUserAddWindow: function() {
        if (this.userAddWindow == null) {
            this.userAddWindow = Ext.create('Miser.base.user.UserAddWindow');
            this.userAddWindow.parent = this; // 父元素
        }
        return this.userAddWindow;
    },
    employeeAddWindow: null,
    getEmployeeAddWindow: function() {
        if (this.employeeAddWindow == null) {
            this.employeeAddWindow = Ext.create('Miser.base.user.EmployeeAddWindow');
            this.employeeAddWindow.parent = this; // 父元素
        }
        return this.employeeAddWindow;
    },
    employeeUpdateWindow: null,
    getEmployeeUpdateWindow: function() {
        if (this.employeeUpdateWindow == null) {
            this.employeeUpdateWindow = Ext.create('Miser.base.user.EmployeeUpdateWindow');
            this.employeeUpdateWindow.parent = this; // 父元素
        }
        return this.employeeUpdateWindow;
    },   
    updatEmployee : function(){
    	var me = this;
    	var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
        if (selections.length != 1) { // 判断是否选中了一条
            miser.showWoringMessage('请选择一条进行修改'); // 请选择一条进行作废操作！
            return; // 没有则提示并返回
        }        
        var id = selections[0].get('id');
		var params = {
				'userVo' :{
					'userEntity' : {
						'id' : id
					}
				}
			};
		
		var successFun = function (json) {			
		var updateWindow = me.getEmployeeUpdateWindow(); //获得修改窗口
			updateWindow.userEntity = json.userVo.userEntity;
			updateWindow.show(); //显示修改窗口
			updateWindow.getEmpUpdateForm().getForm().findField('id').setValue(updateWindow.userEntity.id);
			updateWindow.getEmpUpdateForm().getForm().findField('userName').setValue(updateWindow.userEntity.userName);
			updateWindow.getEmpUpdateForm().getForm().findField('empName').setValue(updateWindow.userEntity.empName);
			updateWindow.getEmpUpdateForm().getForm().findField('title').setValue(updateWindow.userEntity.title);
			updateWindow.getEmpUpdateForm().getForm().findField('beginDate').setValue(updateWindow.userEntity.beginDate);
			updateWindow.getEmpUpdateForm().getForm().findField('endDate').setValue(updateWindow.userEntity.endDate);
			updateWindow.getEmpUpdateForm().getForm().findField('orgCode').setCombValue(updateWindow.userEntity.orgName,updateWindow.userEntity.orgCode);
		};
		var failureFun = function (json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes('请求超时'); //请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax('userAction!queryEmployeeInfoById.action', params, successFun, failureFun);
    },
    roleAddWindow: null,
    getRoleAddWindow: function() {
        if (this.roleAddWindow == null) {
            this.roleAddWindow = Ext.create('Miser.base.user.OrgRoleWindow');
            this.roleAddWindow.parent = this; // 父元素
        }
        return this.roleAddWindow;

    },
    addRole: function() {
        var me = this;
        var selections = me.getSelectionModel().getSelection(); //获取选中的数据
        if (selections.length == 0) {
            Ext.MessageBox.alert('提示', '请选择需要配置的用户');
            return;
        } else if (selections.length > 1) {
            Ext.MessageBox.alert('提示', '只能选择一个用户进行配置');
            return;
        }
        var win = me.getRoleAddWindow();
        win.userName = selections[0].get('userName');
        win.getDeptGrid().getStore().load();
        win.show(); //显示部门角色窗口	
    },
    deleteUser: function() {
        var me = this;
        var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
        if (selections.length < 1) { // 判断是否至少选中了一条
            miser.showWoringMessage('请选择一条进行删除操作'); // 请选择一条进行作废操作！
            return; // 没有则提示并返回
        }
        var userName = new Array();
        for (var i = 0; i < selections.length; i++) {
            userName.push(selections[i].get('userName'));
        }
        miser.showQuestionMes('是否要删除',
        function(e) {
            if (e == 'yes') { // 询问是否删除，是则发送请求
                var params = {
                    'userVo': {
                        'userNameList': userName
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
                miser.requestJsonAjax('userAction!deleteUser.action', params, successFun, failureFun);
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
        me.columns = [{
            text: '序号',
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },
        {
            dataIndex: 'id',
            hidden: true
        },
        {
            text: '用户名',
            dataIndex: 'userName',
            align: 'center',
            flex: 1
        },
        {
            text: '员工工号',
            dataIndex: 'empCode',
            align: 'center',
            flex: 1
        },
        {
            text: '员工姓名',
            dataIndex: 'empName',
            align: 'center',
            flex: 1
        },
        {
            text: '称谓',
            dataIndex: 'title',
            align: 'center',
            flex: 1
        },
        {
            text: '启用时间',
            dataIndex: 'beginDate',
            align: 'center',
            flex: 1,
            renderer: function(value) {
                return dateRender(value);
            }
        },
        {
            text: '结束时间',
            dataIndex: 'endDate',
            align: 'center',
            flex: 1,
            renderer: function(value) {
                return dateRender(value);
            }
        }],
        me.tbar = [{
        	id: 'miser_base_user_rolesettingbtn_id',
        	xtype: 'settingsbutton',
            text: '角色配置',
            disabled:true,
            width: 100,
            handler: function() {
                me.addRole();
            }
        },{
            text: '新增用户',
            xtype: 'addbutton',
            width: 100,
            handler: function() {
                me.getUserAddWindow().show();
            }
        },{
            id: 'miser_base_user_deletebtn_id',
            xtype: 'deletebutton',
            text: '删除',
            width: 80,
            disabled:true,
            handler: function() {
                me.deleteUser();
            }
        }];
        me.store = Ext.create('Miser.base.user.UserStore');
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    }
});


//角色部门
Ext.define('Miser.base.user.OrgRoleWindow', {
    extend: 'Ext.window.Window',
    id:'miser_base_user_orgrolewindow_id',
    title: '角色部门',
    height: 500,
    width: 400,
    closable: true,
    parent: null,
    // 父元素
    modal: true,
    resizable: false,
    // 可以调整窗口的大小
    closeAction: 'hide',
    layout: {
		type:'vbox',
		padding:'5',
		align:'stretch'
	},
	listeners: {
        beforehide: function(me) { // 隐藏WINDOW的时候清除数据
            me.getRolePanel().getForm().reset(); // 表单数据清除
           /* me.getSearchDept().getForm().reset();*/
            me.getDeptGrid().getStore().removeAll();// 表格数据清除
        }
    },
	//所选用户名
	userName: null,
	getUserName:function(){
        return this.userName;
	},
	//所选部门
	deptCode: null,
    //部门搜索框
    searchDept: null,
    getSearchDept: function() {
        var me = this;
        if (this.searchDept == null) {
            this.searchDept = Ext.create('Miser.base.user.DeptQueryPanel');
        }
        return this.searchDept;
    },
    selectUserOrgRole: function() {
        var me = this;
        var params = {
            'userVo': {
                'userName': me.userName,
                'orgCode': me.deptCode
            }
        };
        var successFun = function(res) {
        	//获取后台返回角色信息
            var result = res.userVo.roleEntityList;
            var roleCodes = new Array();
            //遍历角色信息
            Ext.Array.forEach(result,function(role,index,array){ 
        		 roleCodes.push(role.code);
    		});
    		//添加已有角色信息
            Ext.getCmp('miser_base_user_itemselector_id').setValue(roleCodes);
        };

        var failureFun = function(json) {
            if (Ext.isEmpty(json)) {
                miser.showErrorMes('请求超时'); //请求超时
            } else {
                var message = json.message;
                miser.showErrorMes(message);
            }
        };
        miser.requestJsonAjax('userAction!queryOrgRoleByUserName.action', params, successFun, failureFun);
    },
    deptGrid: null,
    getDeptGrid: function() {
        var me = this;
        if (this.deptGrid == null) {
            this.deptGrid = Ext.create('Miser.base.user.UserDeptGrid');
        }
        return this.deptGrid;
    },
    rolePanel: null,
    getRolePanel: function() {
        var me = this;
        if (this.rolePanel == null) {
            this.rolePanel = Ext.create('Miser.base.user.RolePanel');
        }
        return this.rolePanel;
    },
    submitUserOrgRole: function() {
        var me = this;
        if(Ext.isEmpty(me.userName)||Ext.isEmpty(me.deptCode)){
        	return;
        }
        var userOrgRoleList = new Array();
        //获取分配角色信息
        var roleCodes = Ext.getCmp('miser_base_user_itemselector_id').getValue();
        Ext.Array.forEach(roleCodes,function(roleCode,index,array){ 
        		 userOrgRoleList.push(roleCode);
    		}
        );
        var params = {
            'userVo': {
                'userName': me.userName,
                'orgCode': me.deptCode,
                'roleCodes': userOrgRoleList
            }
        }
        var successFun = function(json) {
            var message = json.message;
            miser.showInfoMsg(message); //提示新增成功
            me.close();
            //me.parent.getStore().load(); //成功之后重新查询刷新结果集
        };
        var failureFun = function(json) {
            if (Ext.isEmpty(json)) {
                miser.showErrorMes('连接超时'); //请求超时
            } else {
                var message = json.message;
                miser.showErrorMes(message); //提示失败原因
            }
        };
        //发送AJAX请求    
        miser.requestJsonAjax('userAction!updateUserOrgRole.action', params, successFun, failureFun);
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.items = [/*me.getSearchDept(),*/ me.getDeptGrid(), me.getRolePanel()];
        me.fbar = [{
            text: '取消',
            // 取消
            handler: function() {
                me.close();
            }
        },
        {
            text: '保存',
            // 保存
            handler: function() {
                me.submitUserOrgRole();
            }
        }];
        me.callParent([cfg]);

    }
});
//部门查询panel
Ext.define('Miser.base.user.DeptQueryPanel', {
    id: 'miser_base_user_deptqueryForm_id',
    extend: 'Ext.form.Panel',
    labelWidth: 75,
    frame: false,
    height: 30,
    layout: 'column',
    margin: '0 0 5 0',
    items: [{
        xtype: 'textfield',
        name: 'deptName',
        columnWidth: .5,
        margin: '2 0 0 0',
        allowBlank: true,
        emptyText: '请输入部门',
        anchor: '-250 100%'
    },
    {
        border: false,
        xtype: 'container',
        columnWidth: .2,
        defaults: {
            margin: '0 0 0 10'
        },
        items: [{
            xtype: 'button',
            width: 80,
            text: '查询',
            //'查询',
            handler: function() {
                Ext.getCmp('miser_base_user_deptgrid_id').getStore().load();
            }
        }]
    }]
});

/**
 * 用户部门信息store
 */
Ext.define('Miser.base.user.UserDeptStore', {
    extend: 'Ext.data.Store',
    fields: ['orgCode', 'orgName'],
    proxy: {
        type: 'ajax',
        actionMethods: 'POST',
        url: 'userOrgDataAutAction!queryUserOrgDataAutEntityByUserName.action',
        reader: {
            type: 'json',
            rootProperty: 'userOrgDataAutVo.userOrgDataAutList',
            totalProperty: 'totalCount'
        }
    },
    listeners: {
        'beforeload': function(store, operation, eOpts) {
            var userName = Ext.getCmp('miser_base_user_orgrolewindow_id').userName;
            var params = {
                'userOrgDataAutVo.userOrgDataAutEntity.userName': userName
            }
            Ext.apply(store.proxy.extraParams, params);
        }
    }
});

//用户部门信息表格
Ext.define('Miser.base.user.UserDeptGrid', {
    extend: 'Ext.grid.Panel',
    id: 'miser_base_user_deptgrid_id',
    height: 150,
    autoScroll: true,
   /* pagingToolbar: null,
    getPagingToolbar: function() {
        if (this.pagingToolbar == null) {
            this.pagingToolbar = Ext.create('Ext.toolbar.Paging', {
                store: this.store,
                pageSize: 20
            });
        }
        return this.pagingToolbar;
    },*/
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.columns = [{
            header: '部门编码',
            dataIndex: 'orgCode'
        },
        {
            header: '部门名称',
            dataIndex: 'orgName',
            flex: 1
        }],
        me.store = Ext.create('Miser.base.user.UserDeptStore');
        me.selModel = Ext.create('Ext.selection.CheckboxModel', {
            mode: 'SINGLE',
            checkOnly: true
        }),
        /*me.bbar = me.getPagingToolbar();*/
        me.listeners = {
            'itemclick': function(view, record, item, index, e, eOpts) {
            	me.up().deptCode=record.get('orgCode');
            	me.up().selectUserOrgRole();
            }
        },
        me.callParent([cfg]);
    }
});
//角色信息model
Ext.define('Miser.base.user.RoleModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'name',
        type: 'string'
    },
    {
        name: 'code',
        type: 'string'
    },
    {
        name: 'id',
        type: 'string'
    },
    {
        name: 'active',
        type: 'string'
    },
    {
        name: 'notes',
        type: 'string'
    }]
});
//角色信息store
Ext.define('Miser.base.user.RoleStore',{
	extend: 'Ext.data.Store',
	autoLoad: true,
    model: 'Miser.base.user.RoleModel',
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: 'roleAction!queryAllRoleInfo.action',
        reader: {
            type: 'json',
            rootProperty: 'roleVo.roleEntityList'
        }
    }
});

//分配角色panel
Ext.define('Miser.base.user.RolePanel',{
	extend: 'Ext.form.Panel',
	header: false,
    layout: 'fit',
    height: 250,
    items: [{
        xtype: 'itemselector',
        name: 'itemselector',
        anchor: '100%',
        id: 'miser_base_user_itemselector_id',
        store: Ext.create('Miser.base.user.RoleStore'),
        displayField: 'name',
        valueField: 'code',
        msgTarget: 'side',
        fromTitle: '待分配角色',
        toTitle: '已分配角色'
   }]
});

/**
 * 用户信息store
 */
Ext.define('Miser.base.user.UserStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.base.user.UserEntity',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: 'userAction!queryUserList.action',
        reader: {
            type: 'json',
            rootProperty: 'userVo.userEntityList',
            totalProperty: 'totalCount' //总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('queryForm');
            if (queryForm != null) {
                var params = {
                    'userVo.userEntity.userName': queryForm.getForm().findField('userName').getValue(),
                    'userVo.userEntity.empCode': queryForm.getForm().findField('empCode').getValue(),
                    'userVo.userEntity.empName': queryForm.getForm().findField('empName').getValue()
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
    }
});

/**
 * 新增华宇用户
 */
Ext.define('Miser.base.user.UserAddWindow', {
    extend: 'Ext.window.Window',
    title: '新增用户',
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
            me.getUserForm().getForm().reset(); // 表格重置
        },
        beforeshow: function(me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getUserForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function(item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;

        }
    },
    userForm: null,
    getUserForm: function() {
        if (Ext.isEmpty(this.userForm)) {
            this.userForm = Ext.create('Miser.base.user.UserForm');
        }
        return this.userForm;
    },
    submitUserValue: function() {
        var me = this;
        if (me.getUserForm().getForm().isValid()) { // 校验form是否通过校验
            var userModel = new Miser.base.user.UserEntity();
            me.getUserForm().getForm().updateRecord(userModel); // 将FORM中数据设置到MODEL里面
            var params = {
                'userVo': {
                    'userEntity': userModel.data
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
                    miser.showErrorMes('连接超时'); // 请求超时
                } else {
                    var message = json.message;
                    miser.showErrorMes(message); // 提示失败原因
                }
            };
            miser.requestJsonAjax('userAction!addUserEntity.action', params, successFun, failureFun); //  发送AJAX请求
        }
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
            text: '取消',
            // 取消
            handler: function() {
                me.close();
            }
        },
        {
            text: '重置',
            // 重置
            handler: function() {
                me.getUserForm().reset();
            }
        },
        {
            text: '保存',
            // 保存
            /* margin : '0 0 0 55', */
            handler: function() {
                me.submitUserValue();
            }
        }];
        me.items = [me.getUserForm()];
        me.callParent([cfg]);
    }
});

/**
 * 新增华宇用户表单
 */
Ext.define('Miser.base.user.UserForm', {
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
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.items = [{
        	name: 'isEmp',
        	//默认选中状态
        	value: miser.booleanType.yes,
            fieldLabel: '是否本公司员工',
            xtype: 'yesnocheckbox',
            readOnly:true
        },
        {
            name: 'empCode',
            fieldLabel: '员工工号',
            xtype: 'dynamicemployeecombselector',
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
            allowBlank: false,
            listeners : {
	            select: function(combo, records, eOpts) {
	                combo.up().getForm().findField('empName').setValue(records.data.employeeName);
	                combo.up().getForm().findField('userName').setValue(records.data.account);
	                combo.up().getForm().findField('title').setValue(records.data.jobName);
	            }
        	}
        },
        {
            name: 'userName',
            fieldLabel: '用戶名',
            xtype: 'textfield',
            editable: false,
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
            allowBlank: false
        },
        {
            name: 'password',
            fieldLabel: '密码',
            xtype: 'textfield',
            hidden:true
        },
        {
            name: 'empName',
            fieldLabel: '员工姓名',
            xtype: 'textfield',
            editable: false,
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
            allowBlank: false
        },
        {
            name: 'title',
            xtype: 'textfield',
            fieldLabel: '称谓'
        },
        {
            xtype: 'datefield',
            name: 'beginDate',
            fieldLabel: '启用日期',
            format: 'Y-m-d',
            editable: false,
            value: new Date(),
            hidden:true
            // vtype: 'daterange',
            //daterange类型为上代码定义的类型
            // endDateField: 'endTime' //必须跟endDate的id名相同                  
        },
        {
            xtype: 'datefield',
            name: 'endDate',
            format: 'Y-m-d',
            fieldLabel: '结束时间',
            editable: false,
            value: Ext.Date.parse("2999-01-01", "Y-m-d"),
            hidden:true
            //vtype: 'daterange',
            //daterange类型为上代码定义的类型
            //startDateField: 'beginTime' //必须跟beginDate的id名相同   	
        }];
        me.callParent([cfg]);
    }
});




/**
 * 新增特许经用户
 */
Ext.define('Miser.base.user.EmployeeAddWindow', {
    extend: 'Ext.window.Window',
    title: '新增特许经营用户',
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
            me.getEmployeeForm().getForm().reset(); // 表格重置
        },
        beforeshow: function(me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getEmployeeForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function(item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;

        }
    },
    employeeForm: null,
    getEmployeeForm: function() {
        if (Ext.isEmpty(this.employeeForm)) {
            this.employeeForm = Ext.create('Miser.base.user.EmployeeForm');
        }
        return this.employeeForm;
    },
    submitEmployeeValue: function() {
        var me = this;
        if (me.getEmployeeForm().getForm().isValid()) { // 校验form是否通过校验
            var userModel = new Miser.base.user.UserEntity();
            me.getEmployeeForm().getForm().updateRecord(userModel); // 将FORM中数据设置到MODEL里面
            var params = {
                'userVo': {
                    'userEntity': userModel.data
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
                    miser.showErrorMes('连接超时'); // 请求超时
                } else {
                    var message = json.message;
                    miser.showErrorMes(message); // 提示失败原因
                }
            };
            miser.requestJsonAjax('userAction!addEmployeeEntity.action', params, successFun, failureFun); //  发送AJAX请求
        }
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
            text: '取消',
            // 取消
            handler: function() {
                me.close();
            }
        },
        {
            text: '重置',
            // 重置
            handler: function() {
                me.getEmployeeForm().reset();
            }
        },
        {
            text: '保存',
            // 保存
            /* margin : '0 0 0 55', */
            handler: function() {
            	var passWord=me.getEmployeeForm().getForm().findField('password').getValue();
            	var pass=me.getEmployeeForm().getForm().findField('pass').getValue();
            	if(passWord!=pass){
            		Ext.Msg.alert('提示','两次密码输入不同！');
            	}else{
            		 me.submitEmployeeValue();
            	}
               
            }
        }];
        me.items = [me.getEmployeeForm()];
        me.callParent([cfg]);
    }
});



/**
 * 新增特许经营用户表单
 */
Ext.define('Miser.base.user.EmployeeForm', {
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
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.items = [
        {
            name: 'userName',
            fieldLabel: '用戶名',
            regex: /(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6})$/,
            regexText: '该输入项只能输入字母和数字且必须为6位',
            allowBlank: false
        },
        {
            name: 'empName',
            fieldLabel: '姓名',
            xtype: 'textfield',
            allowBlank: false
        },
        {
            name: 'password',
            fieldLabel: '密码',
            inputType: 'password',
            regex: /^[A-Za-z0-9]+$/,
            regexText: '该输入项只能输入字母和数字',
            allowBlank: false
        },
        {
            name: 'pass',
            fieldLabel: '再次输入密码',
            inputType: 'password',
            regex: /^[A-Za-z0-9]+$/,
            regexText: '该输入项只能输入字母和数字',
            allowBlank: false
        },
        {
            name: 'title',
            xtype: 'textfield',
            fieldLabel: '称谓'
        },
        {
            xtype: 'datefield',
            name: 'beginDate',
            fieldLabel: '启用日期',
            format: 'Y-m-d',
            value: new Date(),
            hidden:true
        },
        {
            xtype: 'datefield',
            name: 'endDate',
            format: 'Y-m-d',
            fieldLabel: '结束时间',
            value: Ext.Date.parse("2999-01-01", "Y-m-d"),
            hidden:true
        },
        {
        	xtype : 'dynamicfranchisecombselector',
            name: 'orgCode',
            fieldLabel: '所属组织'
        }];
        me.callParent([cfg]);
    }
});


/**
 * 修改特许经用户
 */
Ext.define('Miser.base.user.EmployeeUpdateWindow', {
    extend: 'Ext.window.Window',
    title: '修改特许经营用户',
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
            me.getEmpUpdateForm().getForm().reset(); // 表格重置
        },
        beforeshow: function(me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getEmpUpdateForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function(item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
        }
    },
    empUpdateForm: null,
    getEmpUpdateForm: function() {
        if (Ext.isEmpty(this.empUpdateForm)) {
            this.empUpdateForm = Ext.create('Miser.base.user.EmployeeUpdateForm');
        }
        return this.empUpdateForm;
    },
    submitEmployeeValue: function() {
        var me = this;
        if (me.getEmpUpdateForm().getForm().isValid()) { // 校验form是否通过校验
            var userModel = new Miser.base.user.UserEntity();
            me.getEmpUpdateForm().getForm().updateRecord(userModel); // 将FORM中数据设置到MODEL里面
            var params = {
                'userVo': {
                    'userEntity': userModel.data
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
                    miser.showErrorMes('连接超时'); // 请求超时
                } else {
                    var message = json.message;
                    miser.showErrorMes(message); // 提示失败原因
                }
            };
            miser.requestJsonAjax('userAction!updateEmployeeById.action', params, successFun, failureFun); //  发送AJAX请求
        }
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
            text: '取消',
            // 取消
            handler: function() {
                me.close();
            }
        },
        {
            text: '重置',
            // 重置
            handler: function() {
                me.getEmpUpdateForm().reset();
            }
        },
        {
            text: '保存',
            // 保存
            /* margin : '0 0 0 55', */
            handler: function() {            	
            		 me.submitEmployeeValue();            	              
            }
        }];
        me.items = [me.getEmpUpdateForm()];
        me.callParent([cfg]);
    }
});



/**
 *修改特许经营用户表单
 */
Ext.define('Miser.base.user.EmployeeUpdateForm', {
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
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.items = [{
            name: 'id',
            fieldLabel: 'ID',
            xtype: 'textfield',
            hidden:true
        },
        {
            name: 'userName',
            fieldLabel: '用戶名',
            regex: /^[0-9a-zA-Z]{6}$/,
            regexText: '该输入项只能输入字母和数字且必须为6位',
            allowBlank: false
        },
        {
            name: 'empName',
            fieldLabel: '姓名',
            xtype: 'textfield',
            allowBlank: false
        },
        {
            name: 'title',
            xtype: 'textfield',
            fieldLabel: '称谓'
        },
        {
            xtype: 'datefield',
            name: 'beginDate',
            fieldLabel: '启用日期',
            format: 'Y-m-d',
            hidden:true
        },
        {
            xtype: 'datefield',
            name: 'endDate',
            format: 'Y-m-d',
            fieldLabel: '结束时间',
            hidden:true
        },
        {
        	xtype : 'dynamicfranchisecombselector',
            name: 'orgCode',
            fieldLabel: '所属组织'
        }];
        me.callParent([cfg]);
    }
});



Ext.onReady(function() {
    var queryForm = Ext.create("Miser.base.user.QueryForm");
    var userGrid = Ext.create("Miser.base.user.UserGrid");
    //Ext.setGlyphFontFamily('FontAwesome');
    Ext.create('Ext.Viewport', {
        header: {
            titlePosition: 2,
            titleAlign: 'center'
        },
        border: "0 0 0 0",
        renderTo: Ext.getBody(),
        layout: {
            type: 'border'
        },
        getQueryForm: function() {
            return queryForm;
        },
        getUserGrid: function() {
            return userGrid;
        },
        items: [
        queryForm, userGrid]
    });
	
});
