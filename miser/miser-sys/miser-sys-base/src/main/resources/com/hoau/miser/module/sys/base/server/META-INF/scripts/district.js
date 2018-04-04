


/**
 * 定义功能树的store
 */
Ext.define('Miser.base.district.districtTreeStore',{
	extend: 'Ext.data.TreeStore',
	root: {
		text:'行政区域',
		id : 'Root',
		expanded: true
	},
	proxy:{
		type:'ajax',
		url:'districtAction!loadDistrictTree.action',
		actionMethods:'POST',
		reader:{
			type:'json',
			rootProperty:'nodes'
		}
	}
});


/**
 * 组织信息
 */
Ext.define('Miser.base.district.DistrictEntity', {
	 extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'code',//组织编码
        type : 'string'
    },{
        name : 'name',//组织名称
        type : 'string'
    },{
        name : 'pinyin',//拼音
        type : 'string'
    },{
    	name : 'simplePinyin',//拼音简写（首字母）
        type : 'string'
    },{
        name : 'parentName',//上级组织名称
        type : 'string'
    },{
        name : 'parentCode',//上级组织标编码
        type : 'string'
    },{
        name : 'active',//是否可用
        type : 'string'
    }]
});

/**
 * 左侧树结构面板
 */
Ext.define('Miser.base.district.DistrictTreePanle', {
   
	extend : 'Ext.tree.Panel',
    height : 500,
    minWidth : 200,
	autoScroll:true,
	oldFullPath:null,//刷新之前展开的路径
	useArrows: true,
	rootVisible: true,  
	viewConfig: {plugins: { ptype: 'treeviewdragdrop', appendOnly: true } },
	layoutConfig : {
		// 展开折叠是否有动画效果
	animate : true
	},
	oldId:null,
	districtInfoPanel:null,
	getDistrictInfoPanel:function(){
    	if(Ext.isEmpty(this.districtInfoPanel)){
    		this.districtInfoPanel = Ext.create('Miser.base.district.DistrictInfoPanel');
    	}
    	return this.districtInfoPanel;
    },
	resTextfield: null,
	getResTextfield: function(){
		var me = this;
		if(this.resTextfield==null){
			this.resTextfield = Ext.create('Ext.form.field.Text',{
				height:25,
				columnWidth: 0.7,
		        emptyText: '行政区域名称',//'输入功能名',
		        /*margin:'0 0 0 19',*/
		        name: 'name',
		      /*  regex:  /^(\w|[\u4E00-\u9FA5])*$/,
		        regexText: '非法字符',*/
		        listeners: {
		        	specialkey: function(field, e){
	                    if (e.getKey() == e.ENTER) {
	                        me.getQueryButton().handler();
	                    }
	                }
		        }
			});
		}
		return this.resTextfield;
	},
	queryButton: null,
	getQueryButton: function(){
		var me = this;
		if(this.queryButton==null){
			this.queryButton = Ext.create('Ext.button.Button',{ 
		    	height:25,
		    	id:'queryButton',
		    	margin:'0 0 0 3',
		    	columnWidth: 0.3,
		    	text: '查询',
		    	handler: function(){
		    		var field = me.getResTextfield(),
		    			queryValue = field.getValue();
		    		if(!Ext.isEmpty(queryValue)){
		    			var params = {'node' : queryValue};
		    			var successFun = function(json) {
		    				var view = me.getView(),
							position = false,
    						pathList = json.pathList;
	    					me.expandNodes = [];
	    					me.collapseAll();
	    					if(pathList.length==0){
	    						Ext.toast('没有找到行政组织','温馨提示','t');
	    						return;
	    					}
	    					for(var i=0;i<pathList.length;i++){
	    						miser.log(pathList[i]);
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
	    								divEl.highlight("ff0000", { attr: 'color', duration:10000 });
										if(!position){
											divHtmlEl.scrollIntoView();
											position = true;
										}
	    							}else{
	    								miser.log('展开失败');
	    							}
	    						});	    						
	    					}
		    	        };
		    	        var failureFun = function(json) {
		    	            if (Ext.isEmpty(json)) {
		    	                miser.showErrorMes('请求超时'); // 请求超时
		    	            } else {
		    	                var message = json.message;
		    	                miser.showErrorMes(message);
		    	            }
		    	        };
		    	        //Ajax请求得到所有查询到的节点全路径
		    	        miser.requestJsonAjax('districtAction!queryTreePathForName.action', params, successFun, failureFun);
		    		}
		    	}
		    });
		}
		return this.queryButton;
	},
	
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Miser.base.district.districtTreeStore');
		
		me.dockedItems = [{
		    xtype: 'toolbar',
		    dock: 'top',
		    layout: 'column',
		    id: 'base_orgAdministrativeToolbar_id',
		    items: [me.getResTextfield(),me.getQueryButton()]
		}];
		
		me.callParent([cfg]);
     }
});

Ext.define('Miser.base.district.DistrictInfoPanel', {
	extend:'Ext.panel.Panel', 
	frame:true,
	id:'miser_base_orgAdministrative_OrgInfoPanel_Id',
	height:500,
	layout:'auto',
	//定义mask 罩
	autoScroll:true,
	myMask:null,
	getMyMask:function(){
		if(Ext.isEmpty(this.myMask)){
			this.myMask = new Ext.LoadMask(this, {msg:"Please wait..."});
		}
		return this.myMask;
	},
	//行政区域架构基本信息FORM
    disAuxiliaryInfoForm:null,
	getDisAuxiliaryInfoForm:function(){
	if(Ext.isEmpty(this.disAuxiliaryInfoForm)){
    		this.disAuxiliaryInfoForm = Ext.create('Miser.base.district.DisAuxiliaryInfoForm');
    	}
    	return this.disAuxiliaryInfoForm;
	},
	constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.items = [me.getDisAuxiliaryInfoForm,//组织架构基本信息
			            ];
			me.callParent([cfg]);
	}
});



/**
 * @description 行政区域主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_base-queryOrgBiz_content')) {
		return;
	};
	//base.searchConfigParams();//获取配置参数值
	var districtTreeSearchPanel= Ext.create('Miser.base.district.DistrictTreePanle',{width:250});//树查询panel
//	var districtInfoPanel = Ext.create('Miser.base.district.DistrictInfoPanel_1',{columnWidth:1});//详细信息PANEL
	Ext.create('Ext.panel.Panel', {
        renderTo : Ext.getBody(),
        layout:'column', 
		//树查询panel
		getDistrictTreeSearchPanel : function() {
			return districtTreeSearchPanel;
		},
		//行政组织详细信息PANEL
//		getDistrictInfoPanel : function() {
//			return districtInfoPanel;
//		},
		items : [districtTreeSearchPanel/*,districtInfoPanel*/] 
	});
});