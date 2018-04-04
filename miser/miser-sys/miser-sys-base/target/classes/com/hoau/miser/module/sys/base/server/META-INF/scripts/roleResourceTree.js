/**
 * 全局变量及函数
 */
base.role.resourceCodes=new Array();
base.role.deleteResourceCodes=new Array();

base.role.resourceAppCodes=new Array();
base.role.deleteAppResourceCodes=new Array();
// 当前操作的角色
base.role.currRoleCode="";

base.role.currAppRoleCode="";
/**
 * 字符串数组操作
 */
base.role.removeStr = function(p_array, str){
	if(p_array==null || p_array.length==0){
		return p_array;
	}
	
	for(var i=0,l=p_array.length;i<l;i++){
		if(p_array[i]==str){
			p_array.splice(i,1);
			return p_array;
		}
	}
	return p_array;
};


/**
 * 字符串数组操作
 */
base.role.removeAppStr = function(p_array, str){
	if(p_array==null || p_array.length==0){
		return p_array;
	}
	
	for(var i=0,l=p_array.length;i<l;i++){
		if(p_array[i]==str){
			p_array.splice(i,1);
			return p_array;
		}
	}
	return p_array;
};

//扩展方法去掉数组指定元素
Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

// checked所有父结点
base.role.checkParent = function(node,checked){
	var parentNode = node.parentNode;
	if(parentNode){
		if(parentNode.data.checked==false){
			base.role.resourceCodes.push(parentNode.data.id);
			base.role.removeStr(base.role.deleteResourceCodes,parentNode.data.id);
			parentNode.set('checked',true);
		}
		base.role.checkParent(parentNode,checked);
	}
};

base.role.checkAppParent = function(node,checked){
	var parentNode = node.parentNode;
	if(parentNode){
		if(parentNode.data.checked==false){
			base.role.resourceAppCodes.push(parentNode.data.id);
			base.role.removeAppStr(base.role.deleteAppResourceCodes,parentNode.data.id);
			parentNode.set('checked',true);
		}
		base.role.checkAppParent(parentNode,checked);
	}
};

base.role.setRoleCode = function(roleCode){
	var resourceVo=new Object();
	resourceVo.currRoleCode= roleCode;
	var params = {'resourceVo':resourceVo};
	var r_url = "setCurrRoleCode.action";
	r_url=	base.realPath(r_url) ;
	Ext.Ajax.request({
		url: r_url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				treeData= result.nodes;
				return treeData;
			}else{
			}
		}
	});
};

base.role.setAppRoleCode = function(roleCode){
	var resourceVo=new Object();
	resourceVo.currAppRoleCode= roleCode;
	var params = {'resourceVo':resourceVo};
	var r_url = "setCurrRoleCode.action";
	r_url=	base.realPath(r_url) ;
	Ext.Ajax.request({
		url: r_url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				treeData= result.nodes;
				return treeData;
			}else{
			}
		}
	});
};

// 重新加载树结点
base.role.reloadTreeNode = function(){
	// 重新加载树结点：
	var resourceTree=Ext.getCmp('Miser_base_role_RoleTree_Id'),
		treeStore = resourceTree.getStore(),
		rootNode = treeStore.getRootNode()
	treeStore.load({ 
	    node: rootNode,
	    callback: function(records, operation, success){
	    	rootNode.expand();	    	
	    }
	});
	// 加载树结点时，初始化已选树结点，作废的树结点
	base.role.resourceCodes=new Array();
	base.role.deleteResourceCodes=new Array();
};

//重新加载树结点
base.role.reloadAppTreeNode = function(){
	// 重新加载树结点：
	var resourceTree=Ext.getCmp('Miser_base_role_AppRoleTree_Id'),
		treeStore = resourceTree.getStore();
		rootNode = treeStore.getRootNode()
	treeStore.load({ 
	    node: rootNode,
	    callback: function(records, operation, success){
	    	rootNode.expand();	    	
	    }
	});
	// 加载树结点时，初始化已选树结点，作废的树结点
	base.role.resourceAppCodes=new Array();
	base.role.deleteAppResourceCodes=new Array();
};

// 重新加载查看的树结点
base.role.reloadSeeTreeNode = function(){
	// 重新加载树结点：
	var resourceTree=Ext.getCmp('Miser_base_role_SeeRoleTree_Id'),
		treeStore = resourceTree.getStore();
		rootNode = treeStore.getRootNode()
	treeStore.load({ 
	    node: rootNode,
	    callback: function(records, operation, success){
	    	rootNode.expand();	    	
	    }
	});
	// 加载树结点时，初始化已选树结点，作废的树结点
	base.role.resourceCodes=new Array();
	base.role.deleteResourceCodes=new Array();
};

//重新加载查看的树结点
base.role.reloadAppSeeTreeNode = function(){
	// 重新加载树结点：
	var resourceTree=Ext.getCmp('Miser_base_role_SeeAppRoleTree_Id'),
		treeStore = resourceTree.getStore();
		rootNode = treeStore.getRootNode()
	treeStore.load({ 
	    node: rootNode,
	    callback: function(records, operation, success){
	    	rootNode.expand();	    	
	    }
	});
	// 加载树结点时，初始化已选树结点，作废的树结点
	base.role.resourceAppCodes=new Array();
	base.role.deleteAppResourceCodes=new Array();
};

/**
 * 权限配置窗口
 */
Ext.define('Miser.base.role.ResourceWindow', {
    extend: 'Ext.window.Window',
    closable: true,
    parent: null,
    title: "角色权限配置",
    closeAction : 'hide',
    width: 400,
    height: 400,
    layout: "fit",
    modal:true,
    roleTab: null,
	getRoleTab: function(){
		if(this.roleTab==null){
			this.roleTab = Ext.create("Miser.view.role.RoleTab");
		}
		return this.roleTab;
	},
    listeners: {
        beforeshow: function(me) { 
        	// 显示WINDOW的时候重新加载树
        	base.role.reloadTreeNode();
        }
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
            text: '提交',
            handler: function(){
            	var roleVo = new Object();
            	roleVo.resourceCodes = base.role.resourceCodes.join(",");
				roleVo.deleteResourceCodes = base.role.deleteResourceCodes.join(",");
				var selectArr = Ext.getCmp('roleGrid').getSelectionModel().getSelection();
				roleVo.currRoleCode = selectArr.length > 0 ? selectArr[0].get('code') : '';
				var params = {'roleVo':roleVo};
				 var successFun = function(json) {
	                    var message = json.message;
	                    miser.showInfoMsg(message);
	                    base.role.reloadTreeNode();
	              };
	              var failureFun = function(json) {
	                    if (Ext.isEmpty(json)) {
	                        miser.showErrorMes('请求超时'); // 请求超时
	                    } else {
	                        var message = json.message;
	                        miser.showErrorMes(message);
	                    }
	                };
	                miser.requestJsonAjax('roleAction!updateRoleResource.action', params, successFun, failureFun);
            }
        }];
        me.items = [me.getRoleTab()];
        me.callParent([cfg]);
    }
});

//角色权限TAB
Ext.define('Miser.view.role.RoleTab',{
	extend:'Ext.tab.Panel',
	frame : false,
	activeTab : 0,
	columnWidth: 1,
	webRoleTreeItem:null,
	getWebRoleTreeItem:function(){
		var me=this;
		if(Ext.isEmpty(me.webRoleTreeItem)){
			me.webRoleTreeItem = Ext.create('Miser.base.role.RoleTree');
		}
		return me.webRoleTreeItem;
	},
	AppRoleTreeItem:null,
	getAppRoleTreeItem:function(){
		var me=this;
		/*if(Ext.isEmpty(me.AppRoleTreeItem)){
			me.AppRoleTreeItem= Ext.create('Miser.base.role.AppRoleTree')
		}*/
		return me.AppRoleTreeItem;
	},
	listeners: {
		tabchange : function(tabPanel,newCard,oldCard,eOpts ){
			/*if(base.resource.SEARCH_TYPE_App == newCard.getItemId()){
				base.resource.search(null);
			}*/
		 }
	}, 
	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [ 
				    {
						title : 'WEB角色权限',
						itemId:'WEB',
						tabConfig : {
							width : 120
							},
						items : [
								 me.getWebRoleTreeItem()
						       ]
					},
					{
						title : 'APP角色权限',
						itemId:'APP',
						tabConfig : {
							width : 120
							},
						items : [
						         me.getAppRoleTreeItem()
							   	]
					}
				 ]
	    me.callParent([cfg]);
	}
});

/**
 * 资源权限树store
 */
Ext.define('Miser.base.role.ResourceTreeStore', {
	extend: 'Ext.data.TreeStore',
	root: {
		text:'WEB系统',
		id : 'web_1'//,
		//expanded: true
	},
	proxy:{
		type:'ajax',
		url:'resourceAction!queryResourceByParentResChecked.action',
		actionMethods:'POST',
		reader:{
			type:'json',
			rootProperty:'nodes',
		},
		default:{expanded : true }
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			// 选中的数据
	    	var selectArr = Ext.getCmp('roleGrid').getSelectionModel().getSelection();
			var searchParams = store.proxy.extraParams;
			var params = {
					'resourceVo.currRoleCode' : selectArr.length > 0 ? selectArr[0].get('code') : ''
			}
			Ext.apply(store.proxy.extraParams, params);  
		}
	}
});

//定义web权限树
Ext.define('Miser.base.role.RoleTree',{
	extend:'Ext.tree.Panel',
	id: 'Miser_base_role_RoleTree_Id',
	animate: false,
	autoScroll : true,
	useArrows: true,
	frame: true,
	rootVisible: true,
	height : 300,
	deSelectParentFunction : function(node) {
		if (node.data.id == this.store.root.id)
			return;
		var parentNode = node.parentNode;
		if (parentNode.hasChildNodes()) {
			for (var i = 0; i < parentNode.childNodes.length; i++) {
				var childNode = parentNode.childNodes[i];
				if (childNode.data.checked == true) {
					return;
				}
			}
		}
		if(parentNode.data.id != this.store.root.id){
			var a_code=parentNode.data.id;
			base.role.deleteResourceCodes.push(a_code);
			base.role.removeStr(base.role.resourceCodes, a_code);
			parentNode.set("checked", false);		
		}
		this.deSelectParentFunction(parentNode);
	},
	deSelectChildFunction : function(node) {
		if (node.hasChildNodes()) {
			for (var i = 0; i < node.childNodes.length; i++) {
				var childNode = node.childNodes[i];
				childNode.set("checked", false);
				var a_code=childNode.data.id;
				base.role.deleteResourceCodes.push(a_code);
				base.role.removeStr(base.role.resourceCodes, a_code);
				this.deSelectChildFunction(childNode);
			}
		}
	},
	selectChildsFunction : function(node) {
		if(node.hasChildNodes()) {
			for(var i = 0; i < node.childNodes.length; i++) {
				node.childNodes[i].set("checked", true);
				base.role.resourceCodes.push(node.childNodes[i].data.id);
				base.role.removeStr(base.role.deleteResourceCodes, node.childNodes[i].data.id);	
			}
		}
	},
	checkChange : function(node, checked) {
		var a_code=node.data.id;
		if (checked == true) {
			base.role.checkParent(node, true);
			this.selectChildsFunction(node, true);
			base.role.resourceCodes.push(a_code);
			base.role.removeStr(base.role.deleteResourceCodes, a_code);	
		} else {
		    //去除用户取消的编码
			//this.selectChildFunction(node, false);
			// 判断父节点下是否还有子节点是选中状态
			this.deSelectParentFunction(node);
			//如果是取消树结点，先判断是否还有下级已选择的树结点，如果有，则递归作废
			this.deSelectChildFunction(node);
			base.role.deleteResourceCodes.push(a_code);
			base.role.removeStr(base.role.resourceCodes, a_code);
			/*var resourceNum=0;
			var resourceVo = new Object();
			resourceVo.resourceEntityDetail = new Object();
			resourceVo.resourceEntityDetail.code = a_code;
			resourceVo.currRoleCode = base.role.currRoleCode;
			// 将角色名称发送到后台：
			var params = {'resourceVo':resourceVo};
			var r_url = "queryResourceCountByRoleResource.action";
			r_url=	base.realPath(r_url) ;*/
			/*Ext.Ajax.request({
				url : r_url,
				jsonData:params,
				success : function(response) {    								 
					json = Ext.decode(response.responseText);
					if(json && json.resourceVo && json.resourceVo.resourceNum){
						resourceNum = json.resourceVo.resourceNum;
					}
				}
			});*/
			/*if(resourceNum>1){
				var tip1=base.role.i18n('Miser.base.tipInfo');
				var tip2=base.role.i18n('Miser.base.areYouSureToVoid');
				Ext.MessageBox.buttonText.yes = base.role.i18n('Miser.base.sure');
				Ext.MessageBox.buttonText.no = base.role.i18n('Miser.base.cancel');
				Ext.Msg.confirm(tip1, tip2, function(btn,text) {
					if (btn == 'yes') {
						var r_url = "deleteResourceByRoleResource.action";
						r_url=	base.realPath(r_url) ;
						Ext.Ajax.request({
							url : r_url,
							jsonData:params,
							success : function(response) {    								 
								json = Ext.decode(response.responseText);
								if(json && json.resourceVo && json.resourceVo.resourceNum){
									resourceNum = json.resourceVo.resourceNum;
									if(resourceNum>0){
										// 执行完作废操作后，提示用户
										msgTip=base.role.i18n('Miser.base.voidSuccessNo');
										Ext.Msg.alert(base.role.i18n('Miser.base.tips'), msgTip);			
									}				
								}
							}
						});	
					}
					// 点击之后，重新加载树结点
					node.parentNode.set('expanded', false);
					node.parentNode.set('loading', false);
					node.parentNode.set('loaded', false);
					node.parentNode.expand();
				});
			}*/
			
		}
	},
//	itemExpand : function(node) {
//		if(node.data.checked==null||node.data.checked==node.raw.checked){
//			return;
//		}
//		//得到更改后的选中判断值
//		var checked = node.data.checked;
//
//		/* ***** 选中时将子节点全部选中****** */
//		var parentNode = node;
//		if (parentNode.hasChildNodes()) {
//			for (var i = 0; i < parentNode.childNodes.length; i++) {
//				var childNode = parentNode.childNodes[i];
//				childNode.set("checked", checked);
//				this.selectChildFunction(childNode, checked);
//			}
//		}
//	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Miser.base.role.ResourceTreeStore');
		me.tbar = [{
			xtype: 'textfield',
			id: 'Miser_base_role_RoleTree_QueryText_Id',
			name:'name',
			fieldLabel:'权限名称',
			labelWidth: 70
		},{
			text: '查询',
			margin:'5 10 5 5',
			handler: function(){
				var a_name= Ext.getCmp('Miser_base_role_RoleTree_QueryText_Id');
				var resourceVo = new Object();
				resourceVo.resourceEntityDetail.name = a_name.getValue();
				// 将角色名称发送到后台：
				var params = {'resourceVo':resourceVo};
			}
		}];/*,{
			text: '查询',
			margin:'5 10 5 5',
			// 查询按钮的响应事件：
			handler: function() {
				// 根据权限名称查询树结构
				var a_resourceTree = Ext.getCmp("Miser_base_role_RoleTree_Id");
				var a_name= Ext.getCmp('Miser_base_role_RoleTree_QueryText_Id');
				var resourceVo = new Object();
				resourceVo.resourceEntityDetail = new Object();
				resourceVo.resourceEntityDetail.name = a_name.getValue();
				// 将角色名称发送到后台：
				var params = {'resourceVo':resourceVo};
				// "../base/queryResourceFullPathByName.action"
				var r_url = "queryResourceFullPathByName.action";
				r_url=	base.realPath(r_url) ;
				Ext.Ajax.request({
					// 请求全路径的标杆编码的集合“.”分隔和查询到的第一个部门的全路径
					url : r_url,
					jsonData:params,
					success : function(response) {
						view = a_resourceTree.getView(),
						json = Ext.decode(response.responseText);
						var pathList =json.resourceVo.allFullPath;//获取权限的全部路径
						me.expandNodes = [];
						me.collapseAll();//开始时展开全部
						if(Ext.isEmpty(pathList)){
							base.showWoringMessage(base.role.i18n('Miser.base.queryNoResult'));//查询无信息！
							return;
						}
						//循环得到的路径set集合
						for(var i=0;i<=pathList.length;i++){
							//根据路径进行展开
							me.expandPath(pathList[i],'id','/',function(success, lastNode){
								if(success){
									var nodeHtmlEl = view.getNode(lastNode),
										nodeEl = Ext.get(nodeHtmlEl);
									if(Ext.isEmpty(nodeEl)){
										me.expandNodes.push(lastNode);
										return;
									}
									var divHtmlEl = nodeEl.query('div')[0],
									    divEl = Ext.get(divHtmlEl);
									divEl.highlight("ff0000", { attr: 'color', duration: 5000 });
								}
							});
						}
					}
				});
			}
		}];*/
		// 监听鼠标事件
		me.listeners = {
			/*itemexpand : function(node){
				me.itemExpand(node);
			},*/
			// 当树结点被选择（checked)
			checkchange : me.checkChange
		};
		me.callParent([cfg]);
	}
});
